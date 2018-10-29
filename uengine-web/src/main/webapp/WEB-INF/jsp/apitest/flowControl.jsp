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
	/**
	 * 프로세스를 플로우 컨트롤 한다.
	 *  
	 * @param actionCode 액션코드 (API)
	 * @param loginUserId 현재로그인사용자아이디
	 * @param processCode  프로세스코드
	 * @param statusCode  액티비티코드
	 * @param bizKey  프로세스개선 요청서번호
	 * @param roleMap  프로세스 참여자 정보
	 * @param pvMap  프로세스 변수 정보
	 * @param delegatorRoleCode  위임자 역할 코드
	 * @param delegators  위임자 정보
	 * @param isSystemCall  시스템호출여부
	 * @param dueDate  처리기한 일자
	 * @param comment  전자결재 의견
	 * @param isSubProcess 서브프로세스여부
	 * @throws Exception
	 */
	public static String flowControl(String actionCode, 
			String loginUserId, 
			String processCode, 
			String statusCode, 
			String bizKey, 
			Map<String, List<String>> roleMap, 
			Map<String, List<String>> pvMap,
			String delegatorRoleCode,
			List<String> delegators,
			boolean isSystemCall,
			String dueDate,
			String comment,
			boolean isSubProcess) throws Exception {
	
		int iActionCode = Integer.parseInt(actionCode);
	
		if (isSubProcess) { //서브 프로세스
			if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_COMELETE)) { //현단계 완료 및 다음단계 자동진행
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, comment, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_SAVEONLY)) { //현단계 저장
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_COLLECTION)) { //현단계 회수
				return BpmUtil.collectTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_REJECT)) { //현단계 반려
				return BpmUtil.rejectTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, comment, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_DELEGATE)) { //현단계 위임
				return BpmUtil.deleagteTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, delegatorRoleCode, delegators, isSystemCall, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_COMPENSATE)) { //statusCode 단계로 되돌리기
				return BpmUtil.compensateTask(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_SUSPEND)) { //현단계 일시중지
				return BpmUtil.suspendTask(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_RESUME)) { //현단계 재개
				return BpmUtil.resumeTask(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_SKIP)) { //현단계 건너뛰기
				return BpmUtil.skipTask(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_DELETE)) { //프로세스 삭제
				return BpmUtil.deleteProcess(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_COMPLETE)) { //프로세스 완료
				return BpmUtil.completeProcess(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_STOP)) { //프로세스 중지
				return BpmUtil.stopProcess(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_CANCEL)) { //프로세스 취소
				return BpmUtil.cancelProcess(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_RESTART)) { //프로세스 재시작
				return BpmUtil.restartProcess(processCode, bizKey, statusCode, loginUserId, isSubProcess);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_CHANGE_DUEDATE)) { //프로세스 처리기한 일자 변경
				return BpmUtil.changeDueDateProcess(processCode, bizKey, dueDate, loginUserId, isSubProcess);
			}
			else {
				return "액션코드 (API) 없음";
			}
		}
		else { //메인 프로세스
			if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_COMELETE)) { //현단계 완료 및 다음단계 자동진행
				return BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, comment);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_SAVEONLY)) { //현단계 저장
				return BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_COLLECTION)) { //현단계 회수
				return BpmUtil.collectTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_REJECT)) { //현단계 반려
				return BpmUtil.rejectTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, comment);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_DELEGATE)) { //현단계 위임
				return BpmUtil.deleagteTask(processCode, bizKey, statusCode, loginUserId, roleMap, pvMap, delegatorRoleCode, delegators);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_COMPENSATE)) { //statusCode 단계로 되돌리기
				return BpmUtil.compensateTask(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_SUSPEND)) { //현단계 일시중지
				return BpmUtil.suspendTask(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_RESUME)) { //현단계 재개
				return BpmUtil.resumeTask(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_ACTIVITY_SKIP)) { //현단계 건너뛰기
				return BpmUtil.skipTask(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_DELETE)) { //프로세스 삭제
				return BpmUtil.deleteProcess(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_COMPLETE)) { //프로세스 완료
				return BpmUtil.completeProcess(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_STOP)) { //프로세스 중지
				return BpmUtil.stopProcess(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_CANCEL)) { //프로세스 취소
				return BpmUtil.cancelProcess(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_RESTART)) { //프로세스 재시작
				return BpmUtil.restartProcess(processCode, bizKey, statusCode, loginUserId);
			}
			else if (iActionCode == Integer.parseInt(BpmUtil.ACTION_CODE_PROCESS_CHANGE_DUEDATE)) { //프로세스 처리기한 일자 변경
				return BpmUtil.changeDueDateProcess(processCode, bizKey, dueDate, loginUserId);
			}
			else {
				return "액션코드 (API) 없음";
			}
		}		
		
	}
%>

<%
	//BPM API 연동
	Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
	Map<String, List<String>> pvMap = new HashMap<String, List<String>>();
	String delegatorRoleCode = UEngineUtil.getString(request.getParameter("delegatorRoleCode"));
	List<String> delegators = new ArrayList<String>();
	String dueDate = UEngineUtil.getString(request.getParameter("dueDate"));
	String user_id = UEngineUtil.getString(request.getParameter("user_id"));
	String instance_key = UEngineUtil.getString(request.getParameter("instance_key"));
	String activity_code = UEngineUtil.getString(request.getParameter("activity_code"));
	String action_code = UEngineUtil.getString(request.getParameter("action_code"));
	

	String clickedSubmit = UEngineUtil.getString(request.getParameter("clickedSubmit")); //전송버튼을 눌렀으면 1
	String rootInstId = UEngineUtil.getString(request.getParameter("rootInstId"));
	String instanceKey = UEngineUtil.getString(request.getParameter("instance_key"));
	
	String prevInstanceId = UEngineUtil.getString(request.getParameter("prevInstanceId"));
	String prevInstanceKey = UEngineUtil.getString(request.getParameter("prevInstanceKey"));

    String INSTN_ID = null; //flowchart
    String selectedInstanceId = UEngineUtil.getString(request.getParameter("selectedInstanceId")); //액티비티 코드에서 선택된 인스턴스
    
	String actionCode = UEngineUtil.getString(request.getParameter("action_code"));
	boolean hasRootInstance = false;
	ProcessManagerRemote pmr = null;
	try {
	    pmr = (new ProcessManagerFactoryBean()).getProcessManagerForReadOnly();			    
	    ProcessDefinitionRemote pdr = null;
	    ProcessInstance rootInstance = null;
	    ProcessInstance instance = null;
	    String pdAlias = null;
	    
		if(!"".equals(instanceKey)) {
			if( ( !"".equals(prevInstanceId) && !rootInstId.equals(prevInstanceId) ) && instanceKey.equals(prevInstanceKey)) {
				rootInstId = rootInstId;
			} 
			else if( ( !"".equals(prevInstanceKey) && !instanceKey.equals(prevInstanceKey))) {
				rootInstId = CommonUtil.getExistingProcessInstanceIdByTPSNumber(instanceKey, pmr);		
			} 
			else if( ( "".equals(rootInstId) && !"".equals(instanceKey))) {
				rootInstId = CommonUtil.getExistingProcessInstanceIdByTPSNumber(instanceKey, pmr);
			}
	    }
		
		//List<Activity> currentRunningActivities = null;
	    Vector<ActivityInstanceContext> wholeActivitiesDeeply = null;
	    //List<Activity> wholeActivitiesDeeply = null;
	    String initEp = null;
	    
	    if(UEngineUtil.isNotEmpty(rootInstId) && UEngineUtil.isDigit(rootInstId)) {
            pdr = pmr.getProcessDefinitionRemoteWithInstanceId(rootInstId);
	        pdAlias = pdr.getAlias();
	        rootInstance = pmr.getProcessInstance(rootInstId);
	        instanceKey = rootInstance.getName();
	        //rootInstance = CommonUtil.getExistingProcessInstance(pmr, pdAlias, instanceKey, user_id, action_code);
	        if ( rootInstance != null ) {
	        	hasRootInstance = true;
				//currentRunningActivities = rootInstance.getCurrentRunningActivities(); //Running, Timeout (서브프로세스 찾지 않는다)
				//wholeActivitiesDeeply = rootInstance.getActivitiesDeeply("Running");
				//wholeActivitiesDeeply = rootInstance.getActivitiesDeeply("Completed+Running+Timeout+Suspended");
				//wholeActivitiesDeeply = rootInstance.getActivitiesDeeply(null);
				//wholeActivitiesDeeply = CommonUtil.getAllChildActivities(rootInstance.getProcessDefinition(), rootInstance.getProcessTransactionContext());			
				
				wholeActivitiesDeeply = CommonUtil.getActivitiesDeeply(pmr, rootInstance);
				
				initEp = (String) ((EJBProcessInstance)rootInstance).getProcessInstanceDAO().get("initEp");
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

		//selectedInstanceId 초기값 셋팅
		var checkedActivityCodeValue = $("#activity_code:checked").val();
		var checkedInstanceIdValue = $("#"+checkedActivityCodeValue+"_instnid").val();
		$("#selectedInstanceId").val(checkedInstanceIdValue);
		
		//selectedInstanceId 변경값 셋팅
 	    $("input:radio[name=activity_code]").click(function() {
			$("#selectedInstanceId").val($("#"+$("#activity_code:checked").val()+"_instnid").val());	
			//alert( $("#selectedInstanceId").val() );
        })
        
        
		//action_code 초기 선택 셋팅 - 위임(05)이면 위임자 역할 코드, 위임자 입력필드 노출 아니면 숨김
        if ('05' == $('#action_code option:selected').val()) {
         	$('#delegatorRoleCodeTr').show();
         	$('#delegatorTr').show();
         	$('#delegators').focus();
         }
         else {
         	$('#delegatorRoleCodeTr').hide();
         	$('#delegatorTr').hide();
         }
		
        //action_code 변경 선택 셋팅 - 위임(05)이면 위임자 역할 코드, 위임자 입력필드 노출 아니면 숨김
        $('#action_code').change(function() {
            if ('05' == $('#action_code option:selected').val()) {
            	$('#delegatorRoleCodeTr').show();
            	$('#delegatorTr').show();
            	$('#delegators').focus();
            }
            else {
            	$('#delegatorRoleCodeTr').hide();
            	$('#delegatorTr').hide();
            }
        })
        
        //action_code 초기 선택 셋팅 - 프로세스 처리기한 일자 변경(11)이면 처리기한 일자 입력필드 노출 아니면 숨김
        if ('11' == $('#action_code option:selected').val()) {
         	$('#dueDateTr').show();
         	$('#dueDate').focus();
         }
         else {
         	$('#dueDateTr').hide();
         }
		
        //action_code 변경 선택 셋팅 - 프로세스 처리기한 일자 변경(11)이면 처리기한 일자 입력필드 노출 아니면 숨김
        $('#action_code').change(function() {
            if ('11' == $('#action_code option:selected').val()) {
            	$('#dueDateTr').show();
            	$('#dueDate').focus();
            }
            else {
            	$('#dueDateTr').hide();
            }
        })
        
        //2017-01-11 chonk
		$('.btn-group > .btn').click(function() {
			$(this).addClass('active').siblings().removeClass('active');
			if ('selectedHuman' == $(this).attr('id')) {
				$("tr[id^='displayedActivity']").hide();
			} else {
				$("tr[id^='displayedActivity']").show();
			}
		});
	});
		
	
</script>
<title>API Test</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
				<td style="vertical-align: top;">
					<div id="content-middle" class="content-middle" style="width : 100%;">
						<div id="gridPanelDiv" class="panel-group">
							<div class="panel panel-primary">
								<div class="panel-heading"><span id="apiTestTitle" class="glyphicon glyphicon-edit"></span></div>
								<div class="panel-body">
									<table id="apiTestContent" width="100%">
									</table>
									<table class="table table-reflow">
									  <tbody>
									  <!-- form start -->
										<form id="form" name="form" method="POST">
									    <input type="hidden" id="clickedSubmit" name="clickedSubmit"  value="<%=clickedSubmit%>"/>
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active" width="200px"><spring:message code="menu.apitest.content.instanceid" />
										      <button id="ssBtn" type="button" class="btn btn-info" onclick="searchSimple();"><spring:message code="button.search" /></button>
										  </td>
									      <td id="instanceIdTd">
										      <input type="text" name="rootInstId" id="rootInstId" value="<%=rootInstId %>" class="form-control" /><input type="hidden" name="prevInstanceId" id="prevInstanceId" value="<%=rootInstId %>"/>
									      </td>
									    </tr>
									    <tr style="border-bottom: 1px solid #ddd">
									      <td class="active"><spring:message code="menu.apitest.content.worksystemno" />
										      <button id="ssBtn" type="button" class="btn btn-info" onclick="searchInstanceByKey();"><spring:message code="button.search" /></button>
										  </td>
									      <td id="instanceKeyTd">
										      <input type="text" name="instance_key" id="instance_key" value="<%=instanceKey %>" class="form-control" /><input type="hidden" id="prevInstanceKey" name="prevInstanceKey" value="<%=instanceKey%>" /> 
									      </td>
									    </tr>
									<% if ( !UEngineUtil.isNotEmpty(rootInstId) ) { %>
									<% } else if ( !hasRootInstance ) { %>
										<tr style="border-bottom: 1px solid #ddd">
											<td class="active"><spring:message code="menu.apitest.content.notinstance" /></td>
											<td id="noInstanceTd">ProcessInstance <%=rootInstId %> cannot be found !!!</td>
										</tr>				
										</form>
									<% } else { %>
									    <tr style="border-bottom: 1px solid #ddd">
										   <td class="active"><spring:message code="menu.apitest.content.worker" /></td>
										   <td id="workerTd">
											   <input type="text" id="user_id" name="user_id" value="<%=initEp%>" class="form-control" />
											   <input type="hidden" id="process_code" name="process_code"  value="<%=pdAlias%>"/>									      	
										   </td>
									    </tr>
										
							<%-- <%if(wholeActivitiesDeeply !=null && wholeActivitiesDeeply.size() > 0) {%> --%>
										<tr style="border-bottom: 1px solid #ddd">
											<td class="active"><spring:message code="menu.apitest.content.activitycode" />
												<br></br>
												<div class="btn-group">
													<button type="button" id="selectedHuman" class="btn btn-default active">Human</button>
													<button type="button" id="selectedAll" class="btn btn-default">All</button>
												</div>
											</td>
									        <td id="activityCodeTd">
									        <%if(wholeActivitiesDeeply !=null && wholeActivitiesDeeply.size() > 0) {%>
												<table border="1">
													<tr>
														<th><spring:message code="menu.apitest.content.select" /></th>
														<th>ActivityCode</th>
														<th>ActivityName</th>
														<th>ActivityRole</th>
														<th>ActivityStatus</th>
														<th>ActivityType</th>
														<th>InstanceId</th>
														<th>TracingTag</th>
														<th>TaskId</th>
														<th>ProcessCode</th>
													</tr>
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
												INSTN_ID = instance.getInstanceId();
												//String INSTN_ID = aicInstance.getInstanceId();
												String ACT_ID = hWorkItem.getTracingTag();
												String TASK_ID = "";
												try {TASK_ID = UEngineUtil.getString(hWorkItem.getTaskIds(instance)[0]);} catch (Exception e) {TASK_ID = "";}
												//try {TASK_ID = UEngineUtil.getString(hWorkItem.getTaskIds(aicInstance)[0]);} catch (Exception e) {TASK_ID = "";}
												String ActivityName = hWorkItem.getName().getText();
												String ActivityCode = hWorkItem.getExtValue1();
												String ActivityRole = hWorkItem.getRole().getDisplayName().getText();
												String ActivityStatus = hWorkItem.getStatus(instance);		
												//String ActivityStatus = hWorkItem.getStatus(aicInstance);		
												String ActivityType = UEngineUtil.getClassNameOnly(hWorkItem.getClass());
												String ProcessCode = instance.getProcessDefinition().getAlias();
											%>
											<%--<tr <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " class=\"flowchart-activity-c flowchart-activity-status-Running\"" : " class=\"flowchart-activity-c\""%> --%>
											<tr class='flowchart-activity-c flowchart-activity-status-<%=ActivityStatus%>'>
												<td><input type="radio" id="activity_code" name="activity_code" value="<%=ActivityCode%>" <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " checked" : ""%>></td>
												<td><%=ActivityCode%> <input type=hidden id="<%=ActivityCode%>_activitycode" name="<%=ActivityCode%>_activitycode" value="<%=ActivityCode%>"> </td>
												<td><%=ActivityName%> <input type=hidden id="<%=ActivityCode%>_activityname" name="<%=ActivityCode%>_activityname" value="<%=ActivityName%>"> </td>
												<td><%=ActivityRole%> <input type=hidden id="<%=ActivityCode%>_activityrole"  name="<%=ActivityCode%>_activityrole" value="<%=ActivityRole%>"> </td>
												<td><%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " <font size=-2 color=#027dbc><strong><blink>"+ActivityStatus+"</blink></strong></font>" : ActivityStatus%> <input type=hidden id="<%=ActivityCode%>_activitystatus" name="<%=ActivityCode%>_activitystatus" value="<%=ActivityStatus%>"> </td>
												<td><%=ActivityType%></td>
												<td><%=INSTN_ID%> <input type=hidden id="<%=ActivityCode%>_instnid" name="<%=ActivityCode%>_instnid" value="<%=INSTN_ID%>"> </td>
												<td><%=ACT_ID%> <input type=hidden id="<%=ActivityCode%>_actid" name="<%=ActivityCode%>_actid" value="<%=ACT_ID%>"> </td>
												<td><%=TASK_ID%> <input type=hidden id="<%=ActivityCode%>_taskid" name="<%=ActivityCode%>_taskid" value="<%=TASK_ID%>"> </td>
												<td><%=ProcessCode%> <input type=hidden id="<%=ActivityCode%>_processcode" name="<%=ActivityCode%>_processcode" value="<%=ProcessCode%>"> </td>
											</tr>
										<%}else if(activity instanceof SystemActivity){
											Activity hWorkItem = activity;
											String ActivityCode = null;
											ActivityCode = hWorkItem.getStatusCode();
											String ActivityName = hWorkItem.getName().getText();
											String ActivityRole = "";
											INSTN_ID = instance.getInstanceId();
											String ACT_ID = hWorkItem.getTracingTag();
											String ActivityStatus = hWorkItem.getStatus(instance);
											String ActivityType = UEngineUtil.getClassNameOnly(hWorkItem.getClass());
											String ProcessCode = instance.getProcessDefinition().getAlias();
											%>
											<%--<tr <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " class=\"flowchart-activity-c flowchart-activity-status-Running\"" : " class=\"flowchart-activity-c\""%> --%>
											<tr class='flowchart-activity-c flowchart-activity-status-<%=ActivityStatus%>'>
												<td><input type="radio" id="activity_code" name="activity_code" value="<%=ActivityCode%>" <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " checked" : ""%>></td>
												<td><%=ActivityCode%> <input type=hidden id="<%=ActivityCode%>_activitycode" name="<%=ActivityCode%>_activitycode" value="<%=ActivityCode%>"> </td>
												<td><%=ActivityName%> <input type=hidden id="<%=ActivityCode%>_activityname" name="<%=ActivityCode%>_activityname" value="<%=ActivityName%>"> </td>
												<td><%=ActivityRole%> <input type=hidden id="<%=ActivityCode%>_activityrole"  name="<%=ActivityCode%>_activityrole" value="<%=ActivityRole%>"> </td>
												<td><%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " <font size=-2 color=#027dbc><strong><blink>"+ActivityStatus+"</blink></strong></font>" : ActivityStatus%> <input type=hidden id="<%=ActivityCode%>_activitystatus" name="<%=ActivityCode%>_activitystatus" value="<%=ActivityStatus%>"> </td>
												<td><%=ActivityType%></td>
												<td><%=INSTN_ID%> <input type=hidden id="<%=ActivityCode%>_instnid" name="<%=ActivityCode%>_instnid" value="<%=INSTN_ID%>"> </td>
												<td><%=ACT_ID%> <input type=hidden id="<%=ActivityCode%>_actid" name="<%=ActivityCode%>_actid" value="<%=ACT_ID%>"> </td>
												<td><%=ProcessCode%> <input type=hidden id="<%=ActivityCode%>_processcode" name="<%=ActivityCode%>_processcode" value="<%=ProcessCode%>"> </td>
											</tr>
										<%
											} else {
												Activity hWorkItem = activity;
												//String INSTN_ID = instance.getInstanceId();
												INSTN_ID = instance.getInstanceId();
												String ACT_ID = hWorkItem.getTracingTag();
												String TASK_ID = "";
												String ActivityName = hWorkItem.getName().getText();
												String ActivityCode = null;
												String ActivityRole = "";
												String ActivityStatus = hWorkItem.getStatus(instance);
												String ActivityType = UEngineUtil.getClassNameOnly(hWorkItem.getClass());
												String ProcessCode = instance.getProcessDefinition().getAlias();
											%>
											<%--<tr <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " class=\"flowchart-activity-c flowchart-activity-status-Running\"" : " class=\"flowchart-activity-c\""%>> --%>
											<tr id="displayedActivity<%=i%>" style="display: none;" class='flowchart-activity-c flowchart-activity-status-<%=ActivityStatus%>'>
												<td><input type="radio" id="activity_code" name="activity_code" value="<%=ActivityCode%>" <%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " checked" : ""%>></td>
												<td><%=ActivityCode%></td>
												<td><%=ActivityName%></td>
												<td><%=ActivityRole%></td>
												<td><%=(Activity.STATUS_RUNNING.equals(ActivityStatus)||Activity.STATUS_TIMEOUT.equals(ActivityStatus)||Activity.STATUS_SUSPENDED.equals(ActivityStatus)) ? " <font size=-2 color=#027dbc><strong><blink>"+ActivityStatus+"</blink></strong></font>" : ActivityStatus%></td>
												<td><%=ActivityType%></td>
												<td><%=INSTN_ID%></td>
												<td><%=ACT_ID%></td>
												<td><%=TASK_ID%></td>
												<td><%=ProcessCode%></td>
											</tr>
										<%} %>
									<%} 
										 //2016-12-20 freshka
										if (UEngineUtil.isNotEmpty(selectedInstanceId)) {
											instance = pmr.getProcessInstance(selectedInstanceId);
										}
									%>
											</table>
											<%} else if ("Completed".equals(rootInstance.getStatus())) {%>
											<b>Process Completed !!! </b>
											<%} else {%> 
											No Running Activities.
											<%} %>
											</td>
										</tr>
										<input type="hidden" id="selectedInstanceId" name="selectedInstanceId" />
							<%-- <%} %> --%>
																
										<tr style="border-bottom: 1px solid #ddd">
									        <td class="active"><spring:message code="menu.apitest.content.actioncode" /></td>
											<td id="actionCodeTd">												
												<select id="action_code" name="action_code">
													<!-- <option value="">선택</option> -->	
													<%						
													java.lang.reflect.Field[] fields = BpmUtil.class.getFields();
													for (int i=0; i<fields.length; i++) {
														String fieldName = fields[i].getName();
														String actionCodePrefix = "ACTION_CODE_";
														if(fieldName.startsWith(actionCodePrefix) && fields[i].getType() == String.class){		
															String fieldValue = fields[i].get(null)+"";
															fieldName = fieldName.substring(actionCodePrefix.length());
														%>
													<option value="<%=fieldValue%>" <%=(fieldValue.equals(actionCode)) ? " selected=\"selected\"" : ""%>>[<%=fieldValue%>] <%=fieldName%></option>				
													<% }
													 }%>
												</select>

											<button id="sendBtn" type="button" class="btn btn-info" onclick="submitForm();"><spring:message code="button.submit" /></button>
											<br>
											 <!-- (01:완료, 02:저장, 03:회수, 04:반려, 05:위임, 06:프로세스삭제, 07:프로세스완료, 08:프로세스중지, 09:프로세스취소, 10:프로세스재시작) -->
											 <!-- 51:되돌리기, 52:일시중지, 53:재시작, 54:취소, -->
											 </td>			 
										</tr>			
									 	<!-- </form> -->
									 	
									 	
									    <!-- 위임자 지정 -->
								      	<tr style="border-bottom: 1px solid #ddd; display:none" id="delegatorRoleCodeTr" >
											<td class="active">위임자 역할 코드</td>
											<td>
												<input type="text" id="delegatorRoleCode" name="delegatorRoleCode" value="<%=delegatorRoleCode %>" class="form-control"  />
											</td>
										</tr>
									    <tr style="border-bottom: 1px solid #ddd; display:none" id="delegatorTr" >
											<td class="active">위임자 (다중값구분자는 콤마)</td>
											<td>
												<input type="text" id="delegators" name="delegators" value="" class="form-control"  />
											</td>
										</tr>
										<%						
											String resultDelegatorValue = UEngineUtil.getString(request.getParameter("delegators"));
											if (UEngineUtil.isNotEmpty(resultDelegatorValue)) {
												//String[] values = resultDelegatorValue.split("\\|");
												String[] values = resultDelegatorValue.split("\\,");
												for ( int j = 0; j < values.length; j++ ) {
													String value = values[j].trim();
													if ( !UEngineUtil.isNotEmpty(value) ) {
														continue;
													}
													delegators.add(value);
												}
											}
										%>

										<!-- 프로세스 처리기한 일자 변경 -->
								      	<tr style="border-bottom: 1px solid #ddd; display:none" id="dueDateTr" >
											<td class="active">처리기한 일자 (yyyyMMdd)</td>
											<td>
												<input type="text" id="dueDate" name="dueDate" value="<%=dueDate %>" class="form-control"  />
											</td>
										</tr>
										
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
											ProcessVariable[] pvdrs = null;
										    if(rootInstance != null){
										    	pvdrs = rootInstance.getProcessDefinition().getProcessVariables();
										    }else{
										    	pvdrs = pdr.getProcessVariableDescriptors();
										    }	 
											if (pvdrs != null) {
												for(int i=0; i<pvdrs.length; i++) {
													ProcessVariable pvd = pvdrs[i];
													String resultPvDispName = pvd.getDisplayName().getText();
													String resultPvName = pvd.getName();
													%>
											<tr style="border-bottom: 1px solid #ddd">
												<td class="active" style="color:blue"><%=resultPvDispName%> (<%=resultPvName%>)</td>
												<td>
													<input type="text" id="<%=resultPvName%>" name="<%=resultPvName%>" value="<%=UEngineUtil.isNotEmpty(rootInstId) ? pmr.getProcessVariable(rootInstId, "", resultPvName) : ""%>" class="form-control"  />
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
										    Role[] roles =null;
										    if(rootInstance != null){
										    	roles = rootInstance.getProcessDefinition().getRoles();
										    }else{
												roles = pdr.getRoles();
										    }	 
											if (roles != null ) {
												for(int r=0; r<roles.length; r++) {
													Role role = roles[r];
													String resultRoleDispName = role.getDisplayName().getText();
													String resultRoleName = role.getName();
													
													RoleMapping rm = pmr.getRoleMappingObject(rootInstId, resultRoleName);
													StringBuffer endpoint = new StringBuffer();
													if (rm != null) {
														do {
															if (endpoint.length() > 0) endpoint.append(", ");
															endpoint.append(rm.getEndpoint());
														} while (rm.next());														
													}
											%>			
											<tr style="border-bottom: 1px solid #ddd">
												<td class="active" style="color:blue"><%=resultRoleDispName%> (<%=resultRoleName%>)</td>
												<td>
													<input type="text" id="<%=resultRoleName%>" name="<%=resultRoleName%>" value="<%=UEngineUtil.isNotEmpty(rootInstId) ? UEngineUtil.getString(endpoint.toString()) : ""%>" class="form-control" <%="Initiator".equals(resultRoleName)?"ReadOnly":""%> />
												</td>
											</tr>
											<%
										    		List<String> roleList = new ArrayList<String>();								
													String resultRoleValue = UEngineUtil.getString(request.getParameter(resultRoleName));
													if (UEngineUtil.isNotEmpty(resultRoleValue)) {
														//String[] values = resultRoleValue.split("\\|");
														String[] values = resultRoleValue.split("\\,");
														for ( int j = 0; j < values.length; j++ ) {
															String value = values[j].trim();
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
									      <td class="active" width="200px"><spring:message code="menu.apitest.content.apiexecuteresult" /></td>
									      <td>
									      	<div id="resultDiv" style="float:left">
									      	<%
									 		
									 		if (UEngineUtil.isNotEmpty(rootInstId) && hasRootInstance) { 
									 			if (UEngineUtil.isNotEmpty(clickedSubmit)) { 
									 				if (instance.isSubProcess()) { //[서브 프로세스] 단계 제어
									 					out.print(flowControl(action_code, user_id, instance.getProcessDefinition().getAlias(), activity_code, instance.getName(), roleMap, pvMap, delegatorRoleCode, delegators, true, dueDate, null, true));
											        }
											        else { //[메인 프로세스] 단계 제어
											        	out.print(flowControl(action_code, user_id, pdAlias, activity_code, instance_key, roleMap, pvMap, delegatorRoleCode, delegators, true, dueDate, null, false));
											        }
									 			}										        
									 		}
										    %>
										     </div>
									      </td>
									    </tr>
									  </tbody>
									</table>
								</div>
							</div>	
													
			<%
				}// if ( !hasRootInstance ) {
			%>	
							<% if(UEngineUtil.isNotEmpty(rootInstId) && hasRootInstance && "Running".equals(rootInstance.getStatus())) { %>
							<div class="panel panel-primary">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<iframe id="flowchart" style="width: 100%; height: 100%; padding-bottom: 30px;" src="/bpm/processmanager/viewProcessInstanceFlowChart.do?instId=<%=INSTN_ID%>"; name="innerworkarea" frameborder="0"></iframe>
										<%-- <iframe id="flowchart" style="width: 100%; height: 95%; padding-bottom: 30px;" src="/bpm/processmanager/viewProcessInstance.do?instId=<%=INSTN_ID%>"; name="innerworkarea" frameborder="0"></iframe> --%>										
									</tr>
								</table>							    
							</div>		
							<% } %>
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
									

								
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- /.container -->
</body>
<script type="text/javascript">
function searchSimple() {
	var mainForm = document.form;
	if (mainForm.rootInstId.value) {
		$("#clickedSubmit").val(""); 
		mainForm.submit();
	} else {
		alert("Please. Insert search InstanceId");
	}
}

function searchInstanceByKey() {
	var mainForm = document.form;
	if (mainForm.instance_key.value) {
		$("#clickedSubmit").val(""); 
		mainForm.submit();
	} else {
		alert("Please. Insert search InstanceKey");
	}
}

function submitForm() {
	var mainForm = document.form;
	$("#clickedSubmit").val("1"); 
	mainForm.submit();
}
</script>
</html>