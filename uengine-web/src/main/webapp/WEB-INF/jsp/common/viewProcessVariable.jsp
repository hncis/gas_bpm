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
<%
	//BPM API 연동
	
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
									  	
									  
										<tr style="border-bottom: 1px solid #ddd">
									<%  
										for(int i=0; i<wholeActivitiesDeeply.size(); i++) {
										
										ActivityInstanceContext aic = (ActivityInstanceContext)wholeActivitiesDeeply.get(i);
										Activity activity = (Activity)aic.getActivity();
										ProcessInstance aicInstance = (ProcessInstance)aic.getInstance();
										
										if (aicInstance != null) instance = aicInstance;
											
										//Activity activity = currentRunningActivities.get(i);
										//Activity activity = wholeActivitiesDeeply.get(i);
										
										if ( activity instanceof HumanActivity ) {
											
										HumanActivity hWorkItem = (HumanActivity) activity;
										try {
											// 아래 메소드를 불러야 변수의 값을 가지고 온다 이유는 모름
											instance.getProperty(hWorkItem.getTracingTag(), "_task id");
											} catch (Exception e) {}
											
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

										
			
									
										<!-- 프로세스 변수 이름 루프 시작 -->
										<%
										if (pdr != null) {
											ProcessVariable[] pvdrs = null;
										    if(rootInstance != null){
										    	pvdrs = rootInstance.getProcessDefinition().getProcessVariables();
										    } 
											if (pvdrs != null) {
												for(int i=0; i<pvdrs.length; i++) {
													ProcessVariable pvd = pvdrs[i];
													String resultPvDispName = pvd.getDisplayName().getText();
													String resultPvName = pvd.getName();
													%>
											<tr style="border-bottom: 1px solid #ddd">
												<td class="active" width="200px" style="color:blue"><%=resultPvDispName%> (<%=resultPvName%>)</td>
												<td style="font-size: 15px">
													<label id="<%=resultPvName%>"><%=UEngineUtil.isNotEmpty(rootInstId) ? pmr.getProcessVariable(rootInstId, "", resultPvName) : ""%></label>										
												</td>
											</tr>
											<%
										    		List<String> pvList = new ArrayList<String>();								
													String resultPvValue = UEngineUtil.getString(request.getParameter(resultPvName));
													if (UEngineUtil.isNotEmpty(resultPvValue)) {
														//String[] values = resultPvValue.split("\\|");
														String[] values = resultPvValue.split("\\,");
														for ( int j = 0; j < values.length; j++ ) {
															String value = values[j].trim();
															if ( !UEngineUtil.isNotEmpty(value) ) {
																continue;
															}
															pvList.add(value);
														}
														
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
									 	
										  
									  </tbody>
									</table>
							</div>	
			<%
			
		}catch(Exception e){
			e.getStackTrace();
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