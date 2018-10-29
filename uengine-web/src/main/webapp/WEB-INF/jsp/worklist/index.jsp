<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	var fn_setGrid = function() {
		
		$("#myWorkGridTable").jqGrid({
			colNames : ["<spring:message code="process.name.label" />",
			            "<spring:message code="instance.name.label" />",
			            "<spring:message code="work.title.label" />",
			            "<spring:message code="work.startdate.label" />",
			            "<spring:message code="work.enddate.label" />",
			            "<spring:message code="work.duedate.label" />",
			            "<spring:message code="work.initrsnm.label" />",
			            "상태2",
			            "instanceId",
			            "tracingTag",
			            "taskId"],
			colModel : [ {
					name : "processName",
					sorttype : "String"
				}, {
					name : "instanceName",
					sorttype : "String"
				}, {
					name : "title",
					sorttype : "String"
				}, {
					name : "startDate",
					sorttype : "String"
				}, {
					name : "endDate",
					sorttype : "String"
				}, {
					name : "dueDate",
					sorttype : "String"
				}, {
					name : "initRsNm",
					sorttype : "String"
				}, {
					name : "status",
					sorttype : "String"
				}, {
					name : "instanceId",
					hidden : true
				}, {
					name : "tracingTag",
					hidden : true
				}, {
					name : "taskId",
					hidden : true
				}
			],
			jsonReader : {
				repeatitems : false
			},
			loadonce: true,
			viewrecords : true,
			autowidth : true,
			height : 'auto',
			rownumbers: true,
			rowNum: rowCount,
			pager: "#pager",
			scrollOffset: 0,
			ondblClickRow: function(rowId){
				var form = $("#workListForm");
				$("#instanceId").val($(this).getRowData(rowId).instanceId);
				$("#tracingTag").val($(this).getRowData(rowId).tracingTag);
				$("#taskId").val($(this).getRowData(rowId).taskId);
				fn_viewWorkItem(form, "worklist");
			}
		});
	}
	
	var fn_setTitle = function(title){
		$("#gridTitle").text(title);
	}

	$(document).ready(function() {
		fn_setGrid();
		fn_getWorlist('${sessionScope.loggedUser.userId}', 'NEW', 'myWorkGridTable', 0);
		fn_setTitle('<spring:message code="my.new.work.label" />');
		var worklistCount = fn_setMyWorklistCount('${sessionScope.loggedUser.userId}');
		
		
		$(window).bind('resize', function(){
			var newWidth = $("body").prop("clientWidth")-$("#content-left").width()-$("#content-left").width()-90;
			$("#myWorkGridTable").setGridWidth(newWidth, true);
		});
	});
	
	
	
</script>
<title>BPM</title>
</head>
<body>
	<form:form name="workListForm" id="workListForm" method="post" target="_blank">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="userId" type="hidden" id="userId" name="userId" value="${sessionScope.loggedUser.userId}" /> 
		<form:input path="instanceId" type="hidden" id="instanceId" name="instanceId" value="" /> 
		<form:input path="tracingTag" type="hidden" id="tracingTag" name="tracingTag" value="" /> 
		<form:input path="taskId" type="hidden" id="taskId" name="taskId" value="" /> 
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
				<td width=230px style="vertical-align: top">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<span class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.worklist.label" /></span></span>
							<a href="javascript:fn_getWorlist('${sessionScope.loggedUser.userId}', 'NEW', 'myWorkGridTable',0);fn_setTitle('<spring:message code="my.new.work.label" />');" class="list-group-item"><spring:message code="my.new.work.label" />		<span id="myNewWorkCount" class="badge">0</span></a>
							<a href="javascript:fn_getWorlist('${sessionScope.loggedUser.userId}', 'CONFIRMED', 'myWorkGridTable',0);fn_setTitle('<spring:message code="my.confirmed.work.label" />');" class="list-group-item"><spring:message code="my.confirmed.work.label" /><span id="myConfiremdWorkCount" class="badge">0</span></a>
							<a href="javascript:fn_getWorlist('${sessionScope.loggedUser.userId}', 'COMPLETED', 'myWorkGridTable',0);fn_setTitle('<spring:message code="my.completed.work.label" />');" class="list-group-item"><spring:message code="my.completed.work.label" /><span id="myCompletedWorkCount" class="badge">0</span></a>
							<a href="javascript:fn_getWorlist('${sessionScope.loggedUser.userId}', 'DELEGATED', 'myWorkGridTable',0);fn_setTitle('<spring:message code="my.delegated.work.label" />');" class="list-group-item"><spring:message code="my.delegated.work.label" /><span id="myDelegatedWorkCount" class="badge">0</span></a>
							<a href="javascript:fn_getWorlist('${sessionScope.loggedUser.userId}', 'DELAYED', 'myWorkGridTable',0);fn_setTitle('<spring:message code="my.delayed.work.label" />');" class="list-group-item"><spring:message code="my.delayed.work.label" /><span id="myDelayedWorkCount" class="badge">0</span></a>
						</div>
					</div>
				</td>
				<td style="vertical-align: top;">
					<div id="content-middle" class="content-middle" style="width : 100%;">
						<div id="gridPanelDiv" class="panel-group">
							<div class="panel panel-primary">
								<div class="panel-heading"><span id="gridTitle" class="glyphicon glyphicon-edit"><span></div>
								<div class="panel-body">
									<table id="myWorkGridTable" width="100%"></table>
									<div id="pager"></div></div>
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