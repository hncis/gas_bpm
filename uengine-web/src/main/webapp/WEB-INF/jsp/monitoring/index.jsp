<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<style>
/*
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
*/
</style>
<script>
	
	var fn_setTitle = function(title){
		$("#gridTitle").text(title);
	}
	
	var changePanelBodySize = function(isStatusboard){
		if(isStatusboard){
			$("#panel-body").css("height", "750px");	
		}else{
			$("#panel-body").css("height", "600px");
		}
		
	}

	$(document).ready(function() {
		fn_setTitle('<spring:message code="menu.monitoring.content.statusboard" />');
		changePanelBodySize(true)
	});
	
	
	
</script>
<title>BPM</title>
</head>
<body style="overflow-y: hidden">
	<form:form name="workListForm" id="workListForm" method="post" target="_blank">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="userId" type="hidden" id="userId" name="userId" value="${sessionScope.loggedUser.userId}" /> 
		<form:input path="instanceId" type="hidden" id="instanceId" name="instanceId" value="" /> 
		<form:input path="tracingTag" type="hidden" id="tracingTag" name="tracingTag" value="" /> 
		<form:input path="taskId" type="hidden" id="taskId" name="taskId" value="" /> 
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid" >
		<table width=100%>
			<tr>
				<td width=230px style="vertical-align: top">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<span class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.monitoring.label" /></span></span>
							<a href="csboard.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.statusboard" />');changePanelBodySize(true);"><spring:message code="menu.monitoring.content.statusboard" /></a>
							<a href="processingstatusbytask.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.pcsbt" />');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.pcsbt" /></a>
							<a href="processingstatusbyuser.do" target="contentsFrame"class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.psbu" />');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.psbu" /></a>							
							<a href="processingdelaystatus.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.delaystatus"/>');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.delaystatus" /></a>
							<c:choose>
							 <c:when test="${sessionScope.loggedUser.userId == 'test_ko'}">
							<a href="csboarddisplay.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.statusboard" /> (<spring:message code="common.display.label"/>)');changePanelBodySize(true);"><spring:message code="menu.monitoring.content.statusboard" />(<spring:message code="common.display.label"/>)</a>
							<a href="processingstatusbytaskdisplay.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.pcsbt" /> (<spring:message code="common.display.label"/>)');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.pcsbt" />(<spring:message code="common.display.label"/>)</a>
							<a href="processingstatusbyuserdisplay.do" target="contentsFrame"class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.psbu" /> (<spring:message code="common.display.label"/>)');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.psbu" />(<spring:message code="common.display.label"/>)</a>							
							<a href="processingdelaystatusdisplay.do" target="contentsFrame" class="list-group-item" onclick="fn_setTitle('<spring:message code="menu.monitoring.content.delaystatus"/> (<spring:message code="common.display.label"/>) ');changePanelBodySize(false);"><spring:message code="menu.monitoring.content.delaystatus" />(<spring:message code="common.display.label"/>)</a>							 
							 </c:when>
							</c:choose>
						</div>
					</div>
				</td>
				<td style="vertical-align: top; overflow-y: hidden">
					<div id="content-middle" class="content-middle" style="width : 100%; height: 100%;">
						<div id="gridPanelDiv" class="panel-group">
							<div class="panel panel-primary">
								<div class="panel-heading"><span id="gridTitle" class="glyphicon glyphicon-edit"></span></div>
								<div id="panel-body" class="panel-body" style="width: 100%; height: 100%; overfl: overflow-y: hidden;">
									<iframe  id="contentsFrame" src="<c:url value='/monitoring/csboard.do' />" name="contentsFrame" style="width: 100%; height: 100%; border: 0px; overflow-y: hidden;"></iframe>
								</div>
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