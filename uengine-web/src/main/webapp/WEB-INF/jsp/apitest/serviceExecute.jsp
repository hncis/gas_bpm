<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@page import="org.uengine.bpmutil.util.BpmUtil"%>
<%@page import="org.uengine.util.dao.DefaultConnectionFactory"%>
<%@page import="org.uengine.util.dao.GenericDAO"%>
<%@page import="org.uengine.util.dao.IDAO"%>
<%@page import="org.uengine.util.UEngineUtil"%>

<%@page import="org.uengine.processmanager.ProcessManagerBean"%>
<%@page import="org.uengine.processmanager.ProcessManagerRemote"%>
<%@page import="org.uengine.processmanager.ProcessManagerFactoryBean"%>
<%@page import="org.uengine.processmanager.ProcessDefinitionRemote"%>
<%@page import="org.uengine.kernel.ProcessVariable"%>
<%@page import="org.uengine.kernel.Role"%>
<%@page import="org.uengine.kernel.ProcessDefinition"%>
<%@page import="org.uengine.kernel.Activity"%>
<%@page import="org.uengine.kernel.HumanActivity"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%! 
	/**
	 * 프로세스를 플로우 컨트롤 한다.
	 *  
	 * @param isComplete 단계완료여부
	 * @param loginUserId 현재로그인사용자아이디
	 * @param processCode  프로세스코드
	 * @param statusCode  액티비티코드
	 * @param bizKey  프로세스개선 요청서번호
	 * @param roleMap  프로세스 참여자 정보
	 * @param varMap  프로세스 변수 정보
	 * @param comment  전자결재 의견
	 * @param isSubProcess 서브프로세스여부
	 * @throws Exception
	 */
	public static String flowControl(boolean isComplete, 
			String loginUserId, 
			String processCode, 
			String statusCode, 
			String bizKey, 
			Map<String, List<String>> roleMap, 
			Map<String, List<String>> varMap, 
			String comment,
			boolean isSubProcess) throws Exception {
		if (isSubProcess) { //서브 프로세스
			if (isComplete) {
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment, isSubProcess); //현단계 완료 (Completed) 및 다음단계 자동진행
			}
			else {
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, isSubProcess); //첫단계 진행 (Running)
			}		
		}
		else { //메인 프로세스
			if (isComplete) {
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment); //현단계 완료 (Completed) 및 다음단계 자동진행
			}
			else {
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap); //첫단계 진행 (Running)
			}
		}
	}

%>

<%
	ProcessManagerRemote pmr = null;
	try {
		String pdAlias = (request.getParameter("process_code"));
	    String pd = (request.getParameter("defid"));		
	    String folder = (request.getParameter("parentfolder"));
	    String pdVer = (request.getParameter("prodverid"));
	    pmr = (new ProcessManagerFactoryBean()).getProcessManagerForReadOnly();	    
	    
	    String pi = null;   //  SubProcess ? ??.
	    String instanceIds = (request.getParameter("instanceId"));
	    String selectedInstanceId = (request.getParameter("selectedInstanceId"));
	    String[] arrInstanceIds = null;
	    if (instanceIds != null && instanceIds.indexOf(";") > -1) {
	        arrInstanceIds = instanceIds.split(";");
	        if (selectedInstanceId != null && !"".equals(selectedInstanceId)) {
	            pi = selectedInstanceId;
	        } else {
	            pi = arrInstanceIds[0];
	        }
	    } else {
	        pi = instanceIds;
	    }	    
	    /***********************************************************************/
	    /*                            Get version list                         */
	    /***********************************************************************/

	    ProcessDefinitionRemote[] arrPdr = null;
	    if (pi == null) {      
	        if (pd != null) {
		        arrPdr = pmr.findAllVersions(pd);	
		        if (arrPdr != null && arrPdr.length > 0) {
		            String versionID = null;
		            int version = -1;
		            for (int i=0; i<arrPdr.length; i++) {
		                versionID = arrPdr[i].getId();
		                version = arrPdr[i].getVersion();
		                if (arrPdr[i].isProduction()) {
		                    if (pdVer == null || "".equals(pdVer) || "null".equals(pdVer)) {
		                        pdVer = versionID;
		                    }
		                }
		            }
		            if (pdVer == null || "".equals(pdVer) || "null".equals(pdVer)) {
		                pdVer = versionID;
		            }
		        }
	        }
	    }
	    
	    ProcessDefinitionRemote pdr;    
	    if (pi != null) {
	        pdr = pmr.getProcessDefinitionRemoteWithInstanceId(pi);
	    } else {
	    	if (pdVer != null && !pdVer.equals("0")) pdr = pmr.getProcessDefinitionRemote(pdVer);
	    	else pdr = null;
	    }
	    
/* 	    ProcessInstance instance = null;
	    if (pi != null) {
	        instance = pmr.getProcessInstance(pi);
	    } */
	    String iActivityCode = null;
	    if (pdr != null && UEngineUtil.isNotEmpty(pdVer)) {
	    	iActivityCode = ((HumanActivity) pmr.getProcessDefinition(pdVer).getActivity(pdr.getInitiatorHumanActivityTracingTag())).getExtValue1();
	    }

	    //BPM API 연동
		Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
		Map<String, List<String>> pvMap = new HashMap<String, List<String>>();
		
		String user_id = UEngineUtil.getString(request.getParameter("user_id"));
		String instance_key = UEngineUtil.getString(request.getParameter("instance_key"));
		String activity_code = UEngineUtil.getString(request.getParameter("activity_code"));
		String action_code = UEngineUtil.getString(request.getParameter("action_code"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="/WEB-INF/include/include-header.jspf"%> --%>
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
</style>

<script>
	var fn_setTitle = function(title){
		$("#apiTestTitle").html(title);
	}

	$(document).ready(function() {
		fn_setTitle('<spring:message code="menu.apitest.service.execute.label" />');
	});
	
/* 	var fn_getProcessInstance = function() {
		$("#instId").val($(this).getRowData(rowId).instId);
		$("#defVerId").val($(this).getRowData(rowId).defVerId);
		$("#instanceForm").submit();
	} */

	function refresh(){
	    var processInfo = document.getElementById("process_info").value;
		var processInfos = processInfo.split("^");
		var processDefinitionAlias = processInfos[0]
		var processDefinitionID = processInfos[1];
		var processDefinitionVersionID = processInfos[2];
		var folderID = processInfos[3];
		location="?defalias=" + processDefinitionAlias + "&process_code=" + processDefinitionAlias + "&defid=" + processDefinitionID + "&prodverid=" + processDefinitionVersionID + "&parentfolder=" + folderID;
	}	
</script>
<title>API Test</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
<%-- 				<td width=230px style="vertical-align: top">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<span class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.apitest.label" /></span></span>
							<a href="javascript:fn_setTitle('<spring:message code="menu.apitest.service.execute.label" />');" class="list-group-item"><spring:message code="menu.apitest.service.execute.label" /></a>
							<a href="javascript:fn_setTitle('<spring:message code="menu.apitest.flow.control.label" />');" class="list-group-item"><spring:message code="menu.apitest.flow.control.label" /></a>
						</div>
					</div>
				</td> --%>
				<td style="vertical-align: top;">
					<div id="content-middle" class="content-middle" style="width : 100%;">
						<div id="gridPanelDiv" class="panel-group">
							<div class="panel panel-primary">
								<div class="panel-heading"><span id="apiTestTitle" class="glyphicon glyphicon-edit"><span></div>
								<div class="panel-body">
									<table id="apiTestContent" width="100%">
									</table>
									<table class="table table-reflow">
									  <tbody>
									  <!-- form start -->
										<form id="form" name="form" method="POST">
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active" width="200px"><spring:message code="menu.apitest.content.processid" /></td>
									      <td>
									      	<div id="processNameDiv" style="float:left">										      
					<input type="hidden" id="process_code" name="process_code"  value="<%=pdAlias%>"/>
					<input type="hidden" id="defid" name="defid"  value="<%=pd%>"/>
					<input type="hidden" id="prodverid" name="prodverid"  value="<%=pdVer%>"/>
					<input type="hidden" id="parentfolder" name="parentfolder"  value="<%=folder%>"/>			      
										      
					<select id="process_info" name="process_info" onchange="refresh();">
						<option value="0^0^0^0"><spring:message code="menu.apitest.content.select" /></option>	
						<%
 						StringBuffer sql = new StringBuffer();
						sql.append("	SELECT alias, defid, prodverid, parentfolder, name \n");
						sql.append("	FROM bpm_procdef  \n");
						sql.append("	WHERE isFolder=0   \n");
						sql.append("	AND isdeleted = 0   \n");
						sql.append("ORDER BY alias   \n");
						
						IDAO idao = GenericDAO.createDAOImpl(DefaultConnectionFactory.create(), sql.toString(), IDAO.class);
						idao.select();
						while(idao.next()) { %>
						<option value="<%=idao.getString("alias")%>^<%=idao.getString("defid")%>^<%=idao.getString("prodverid")%>^<%=idao.getString("parentfolder")%>" <%=(idao.getString("alias").equals(pdAlias)) ? " selected=\"selected\"" : ""%>><%=idao.getString("name")%> (<%=idao.getString("alias")%>)</option>				
						<% }
							idao.releaseResource();
						%>
					</select>
										      </div>
									      </td>
									    </tr>
									    
							<% if (pdAlias != null && !"0".equals(pdAlias)) { %>
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active"><spring:message code="menu.apitest.content.processinitiator" /></td>
									      <td id="processInitiatorTd"><input type="text" id="user_id" name="user_id" value="${sessionScope.loggedUser.userId}" class="form-control" ReadOnly /></td>
									    </tr>
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active"><spring:message code="menu.apitest.content.worksystemno" /></td>
									      <td id="instanceKeyTd"><input type="text" id="instance_key" name="instance_key" value="APP<%=new SimpleDateFormat("yyyyMMddHHmmssF").format(new Date())%>" class="form-control" ReadOnly /></td>
									    </tr>
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active"><spring:message code="menu.apitest.content.activitycode" /></td>
									      <td id="activityCodeTd"><input type="text" id="activity_code" name="activity_code" value="<%=iActivityCode%>" class="form-control" ReadOnly /></td>
									    </tr>
									    <input type="hidden" id="action_code" name="action_code" value="<%=BpmUtil.ACTION_CODE_SAVEONLY %>"/>
									 	<!-- </form> -->
								
									    <!-- 프로세스 변수 -->
									    <!-- <form id="process_vars" name="process_vars"> -->
									    <tr>
									      <td class="active" width="200px">[<spring:message code="menu.apitest.content.processvariable" />]</td>
									      <td>
									      	<div id="procvarDiv" style="float:left">
										     </div>
									      </td>
									    </tr>
									
										<!-- 프로세스 변수 이름 루프 시작 -->
										<%
										if (pdr != null) {
											ProcessVariable[] pvdrs = pdr.getProcessVariableDescriptors();

											if (pvdrs != null) {
												for(int i=0; i<pvdrs.length; i++) {
													ProcessVariable pvd = pvdrs[i];
													String resultPvDispName = pvd.getDisplayName().getText();
													String resultPvName = pvd.getName();
													Object resultPvValueOfObj = pvd.getDefaultValue() != null ? pvd.getDefaultValue() : "";
													String resultPvValue = UEngineUtil.getString(resultPvValueOfObj.toString());
													%>
											<tr style="border-bottom: 1px solid #ddd">
												<td class="active" style="color:blue"><%=resultPvDispName%> (<%=resultPvName%>)</td>
												<td>
													<input type="text" id="<%=resultPvName%>" name="<%=resultPvName%>" value="<%=resultPvValue%>" class="form-control" />
												</td>
											</tr>
											<%
										    		List<String> pvList = new ArrayList<String>();								
													String resultPvValue2 = UEngineUtil.getString(request.getParameter(resultPvName));
													String resultPvValue3 = UEngineUtil.isNotEmpty(resultPvValue2) ? resultPvValue2 : resultPvValue;
													if (UEngineUtil.isNotEmpty(resultPvValue3)) {
														String[] values = resultPvValue3.split("\\|");
														for ( int j = 0; j < values.length; j++ ) {
															String value = values[j];
															if ( !UEngineUtil.isNotEmpty(value) ) {
																continue;
															}
															pvList.add(value);
														}
														pvMap.put(resultPvName, pvList);
													}
												}
											}
											else if (pvdrs.length == 0) {
												%>
											<tr style="border-bottom: 1px solid #ddd">
											<tr>
												<td class="active"></td>
												<td >No process variables declared.</td>
											</tr>
											<%
											}
										}
										else { %>
										<tr style="border-bottom: 1px solid #ddd">
										<tr>
											<td class="active"></td>
											<td >No process variables declared.</td>
										</tr>
										<%											
										}
										%>
										<!-- 프로세스 변수 이름 루프 끝 -->
									 	<!-- </form> -->
									    
									    <!-- 프로세스 참여자 -->
									    <!-- <form id="process_roles" name="process_roles"> -->
									    <tr>
									      <td class="active" width="200px">[<spring:message code="menu.apitest.content.processparticipant" />]</td>
									      <td>
									      	<div id="procroleDiv" style="float:left">
										     </div>
									      </td>
									    </tr>
			
										<!-- 프로세스 참여자 이름 루프 시작 -->
										<%
										if (pdr != null) {
										    Role[] roles = pdr.getRoles();
											if (roles != null ) {
												for(int r=0; r<roles.length; r++) {
													Role role = roles[r];
													String resultRoleDispName = role.getDisplayName().getText();
													String resultRoleName = role.getName();
											%>			
											<tr style="border-bottom: 1px solid #ddd">
												<td class="active" style="color:blue"><%=resultRoleDispName%> (<%=resultRoleName%>)</td>
												<td>
													<input type="text" id="<%=resultRoleName%>" name="<%=resultRoleName%>" value="${sessionScope.loggedUser.userId}"  class="form-control" <%="Initiator".equals(resultRoleName)?"ReadOnly":""%> />
												</td>
											</tr>
											<%
										    		List<String> roleList = new ArrayList<String>();								
													String resultRoleValue = UEngineUtil.getString(request.getParameter(resultRoleName));
													if (UEngineUtil.isNotEmpty(resultRoleValue)) {
														String[] values = resultRoleValue.split("\\|");
														for ( int j = 0; j < values.length; j++ ) {
															String value = values[j];
															if ( !UEngineUtil.isNotEmpty(value) ) {
																continue;
															}
															roleList.add(value);
														}
														roleMap.put(resultRoleName, roleList);
													}
												}
											}
											else if (roles.length == 0) {
											%>	
											<tr style="border-bottom: 1px solid #ddd">
											<tr>
												<td class="active"></td>
												<td >No Roles declared.</td>
											</tr>										
											<%
											}
										}
										else {
											%>	
											<tr style="border-bottom: 1px solid #ddd">
											<tr>
												<td class="active"></td>
												<td >No Roles declared.</td>
											</tr>										
											<%
											}
											%>
										<!-- 프로세스 참여자 이름 루프 끝 -->									    
									 	<!-- </form> -->
									 	</form>						 	
									 	
									    <!-- 결과값 -->
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active" width="200px"><spring:message code="menu.apitest.content.result" /></td>
									      <td>
									      	<div id="resultDiv" style="float:left">
									      	<%
									 		//[메인 프로세스] 프로세스개선요청 - 첫번째 [프로세스개선요청서작성] 단계 시작
									 		if (UEngineUtil.isNotEmpty(instance_key)) { 
									 			out.print(flowControl(false, user_id, pdAlias, activity_code, instance_key, roleMap, pvMap, null, false));
									 		}
										    %>
										     </div>
									      </td>
									    </tr>
									    <% } %>
									  </tbody>
									</table>
								</div>
							</div>							
					
			<%
			
		}catch(Exception e){
			e.getStackTrace();
		} finally {
			if(pmr!=null){
				try{
					pmr.remove();
				}catch(Exception e){}
			}
		}
			%>	
									
							<div>							
							    <!-- 전송 버튼 -->
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 		<tr>
							 			<td align="right" style="padding:10px 0;">													
								      		<button id="sendBtn" type="button" class="btn btn-info" onclick="document.form.submit();"><spring:message code="button.submit" /></button>
											<%-- <input type="submit" class="btn btn-info" value="<spring:message code="button.submit" />"/> --%>
										</td>
									</tr>
								</table>
							</div>
								
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- /.container -->
</body>
</html>