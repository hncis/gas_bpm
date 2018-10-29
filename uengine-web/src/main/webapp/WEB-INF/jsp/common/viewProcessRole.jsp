<%@page import="org.uengine.web.service.dao.BpmServiceDAO"%>
<%@page import="org.uengine.web.organization.vo.UserVO"%>
<%@page import="org.uengine.web.service.service.BpmServiceImpl"%>
<%@page import="org.uengine.web.service.service.BpmService"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.uengine.kernel.SystemActivity"%>
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
<%@page import="org.uengine.kernel.ProcessInstance"%>
<%@page import="org.uengine.kernel.EJBProcessInstance"%>
<%@page import="org.uengine.kernel.RoleMapping"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>

<%@page import="kr.go.nyj.util.CommonUtil"%>
<%@page import="kr.go.nyj.util.ActivityInstanceContext"%>
<%!

public boolean isExistRoleInfo(List<Map<String,String>> roleInfoList,
		Map<String, String> roleInfo){
	boolean resultValue = false;
	Iterator<Map<String, String>> iterator = roleInfoList.iterator();
	while(iterator.hasNext()){
		Map<String, String> roleInfoObject = iterator.next();
		String roleInfoObjectGetEndpoint =  roleInfoObject.get("endpoint");
		String roleInfoGetEndpoint =  roleInfo.get("endpoint");
		String roleInfoObjectGetResultRoleName =  roleInfoObject.get("resultRoleName");
		String roleInfoGetResultRoleName =  roleInfo.get("resultRoleName");
		if(roleInfoObjectGetEndpoint.equals(roleInfoGetEndpoint)){
			if(roleInfoObjectGetResultRoleName.equals(roleInfoGetResultRoleName)){
				resultValue = true;	
			}
		}
	}
	return resultValue;
}


%>

<%
	//BPM API 연동
	String INSTN_ID = null; //flowchart
	String rootInstId = UEngineUtil.getString(request.getParameter("rootInstId"));
		boolean hasRootInstance = false;
	ProcessManagerRemote pmr = null;
	try {
	    pmr = (new ProcessManagerFactoryBean()).getProcessManagerForReadOnly();	
	    ProcessDefinitionRemote pdr = null;
	    ProcessInstance rootInstance = null;
	    ProcessInstance instance = null;
	    String pdAlias = null;
	    Vector<ActivityInstanceContext> wholeActivitiesDeeply = null;
	    if(UEngineUtil.isNotEmpty(rootInstId) && UEngineUtil.isDigit(rootInstId)) {
            pdr = pmr.getProcessDefinitionRemoteWithInstanceId(rootInstId);
	        pdAlias = pdr.getAlias();
	        rootInstance = pmr.getProcessInstance(rootInstId);
	       
	        //rootInstance = CommonUtil.getExistingProcessInstance(pmr, pdAlias, instanceKey, user_id, action_code);
	        if ( rootInstance != null ) {					
				wholeActivitiesDeeply = CommonUtil.getActivitiesDeeply(pmr, rootInstance);

	        }
	    }
	    
	    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
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
		fn_setTitle('<spring:message code="menu.apitest.flow.control.label" />');

	});
		
	
</script>
<title>API Test</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
	
	<table class="table table-reflow">
	  <tbody>
	    <tr>
	      <td  width="100%"> <h4><%=rootInstance.getProcessDefinition().getName().toString()%> (InstanceId: <%=rootInstId %>)</h4></td>
	      <td>
		<div id="procroleDiv" style="float:left">
		     </div>
	      </td>
	    </tr>
	    </tbody>
    </table>
		<table width=100%>
			
			<tr>
				<td style="vertical-align: top;">
					
							<div >
								
									
									<table class="table table-reflow">
									  <tbody>
									  	
									  
										
									<% 
										
										List<Map<String,String>> roleInfoList = new ArrayList<Map<String,String>>();
										ProcessManagerBean processManagerBean = new ProcessManagerBean();
										for(int i=0; i<wholeActivitiesDeeply.size(); i++) {
										
										ActivityInstanceContext aic = (ActivityInstanceContext)wholeActivitiesDeeply.get(i);
										Activity activity = (Activity)aic.getActivity();
										ProcessInstance aicInstance = (ProcessInstance)aic.getInstance();
										
										if (aicInstance != null) instance = aicInstance;
											
										//Activity activity = currentRunningActivities.get(i);
										//Activity activity = wholeActivitiesDeeply.get(i);
										
										if ( activity instanceof HumanActivity ) {
											
										HumanActivity hWorkItem = (HumanActivity) activity;

										String TASK_ID = "";
										String ActivityRoleUser = null;
										try {TASK_ID = UEngineUtil.getString(hWorkItem.getTaskIds(instance)[0]);} catch (Exception e) {TASK_ID = "";}
										try{
											ActivityRoleUser = hWorkItem.getRole().getMapping(instance).getEndpoint();
												
										}catch(Exception e){
											
										}
										
										String ActivityRole = hWorkItem.getRole().getDisplayName().getText();
										
										Map<String, String> roleInfo = new HashMap<String, String>();
										Role role = hWorkItem.getRole();
										String resultRoleDispName = role.getDisplayName().getText(); // 
										String resultRoleName = role.getName();
										RoleMapping rm = null;
										
										try{
											rm = pmr.getRoleMappingObject(rootInstId, resultRoleName, false);
											if(rm == null)
												rm = hWorkItem.getRole().getMapping(instance);
										}catch(Exception e){
											rm = null;
										}
										
										
										StringBuffer endpoint = new StringBuffer();
										if (rm != null) {
											do {
												if (endpoint.length() > 0) endpoint.append(", ");
												roleInfo.put("endpoint", rm.getEndpoint());
												roleInfo.put("resultRoleDispName", resultRoleDispName);
												roleInfo.put("resultRoleName", resultRoleName);
												if(!isExistRoleInfo(roleInfoList, roleInfo))
												roleInfoList.add(roleInfo);
											} while (rm.next());														
										}else{
											if (endpoint.length() > 0) endpoint.append(", ");
											roleInfo.put("endpoint", "미지정됨(지정요망)");
											roleInfo.put("resultRoleDispName", resultRoleDispName);
											roleInfo.put("resultRoleName", resultRoleName);
											roleInfoList.add(roleInfo);
										}
									} 
								}
		
											String resultDelegatorValue = UEngineUtil.getString(request.getParameter("delegators"));
											if (UEngineUtil.isNotEmpty(resultDelegatorValue)) {
												//String[] values = resultDelegatorValue.split("\\|");
												String[] values = resultDelegatorValue.split("\\,");
												for ( int j = 0; j < values.length; j++ ) {
													String value = values[j].trim();
													if ( !UEngineUtil.isNotEmpty(value) ) {
														continue;
													}
												
												}
											}
											
										%>
										<!-- 프로세스 참여자 -->
										<!-- 프로세스 참여자 이름 루프 시작 -->
										<%
										int roofCounter = 0;
										if (pdr != null) {  
										    	 
											if (wholeActivitiesDeeply.size() != 0 ) {
												
												Iterator<Map<String,String>> iterator = roleInfoList.iterator();
												BpmServiceDAO bpmServiceDao = new BpmServiceDAO();
												while(iterator.hasNext()){
													Map<String, String> roleObject = iterator.next();
													String roleDispName = roleObject.get("resultRoleDispName");
													String roleName = roleObject.get("resultRoleName");
													String roleEndpoint = roleObject.get("endpoint");
													UserVO userInfo = bpmServiceDao.selecUserVOByUserId(processManagerBean, roleEndpoint);
												
													
													if(roofCounter == 0){
												%>
													<tr style="border-bottom: 1px solid #ddd">		
												<%}else if(roofCounter >0 && (roofCounter % 2 == 0)){ %>
													</tr>
													<tr style="border-bottom: 1px solid #ddd">
												<%}else{} %>
												
												<td width="50%">
													<fieldset class="block-labels">
														<table width="100%" border="0" cellspacing="5" cellpadding="0" class="tableborder">
															<tbody>
															
															<!-- border-style: groove  #c7d3e4 1px solid-->
																<tr style="border: 1px solid gray;">
																	<td style="vertical-align: middle; middle;padding-left: 5%; width: 150px;"><img width="90px"
																		height="100px" src="<c:url value='${portraitPath}'/>" /></td>
																	<td style="padding: 5px"><p>
																			<span class="glyphicon glyphicon-expand"><spring:message code="userid.label" />: <%= roleEndpoint%></span>
																		</p>
																		<p>
																			<span class="glyphicon glyphicon-expand"><spring:message code="process.roles.label" />: <%=roleDispName%>(<%=roleName%>) </span>
																		</p>
																		<p>
																			<span class="glyphicon glyphicon-expand"><spring:message code="username.label" />: <%=userInfo.getEmpName() %></span>
																		</p>
																		<p>
																			<span class="glyphicon glyphicon-expand"><spring:message code="jikname.label" />: <%=userInfo.getJikName() %></span>
																		</p>
																		<p>
																			<span class="glyphicon glyphicon-expand"><spring:message code="partname.label" />: <%=userInfo.getPartName()%></span>
																		</p></td>
																</tr>
															</tbody>
														</table>
													</fieldset>
												</td>
												<%
												roofCounter++;
												}
											}
											else if (wholeActivitiesDeeply.size() == 0) {
											%>	
											
												<td class="active"></td>
												<td >No Roles declared.</td>							
											<%
											}
										}
										else {
											%>	
											
											
												<td class="active"></td>
												<td >No Roles declared.</td>
																			
											<%
											}
											%>
										<!-- 프로세스 참여자 이름 루프 끝 -->		
									 	
										  </tr>
									  </tbody>
									</table>
							</div>	
			<%
			
		}catch(Exception e){
			e.getStackTrace();	
			System.out.println(e);
			%>
										<tr style="border-bottom: 1px solid #ddd">
											<td class="active"><spring:message code="menu.apitest.content.notinstance" /></td>
											<td id="noInstanceTd">ProcessInstance <%=rootInstId %> cannot be found !!!</td>
										</tr>				
			<%
		} finally {
			if(pmr!=null){
				try{
					pmr.remove();
				}catch(Exception e){}
			}
		}
			%>		
		</table>
	</div>
	<!-- /.container -->
</body>

</html>