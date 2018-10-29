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
.ui-jqgrid {border-width: 0px}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-width: 0px;}
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
			            "<spring:message code="work.startdate.label" />"],
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
				}
			],
			jsonReader : {
				repeatitems : false
			},
			viewrecords : true,
			autowidth : true,
			height : 'auto',
			rownumbers: true,
			rowNum: 5,
			scrollOffset: 0,
		});
		
		$("#myRunningInstanceGridTable").jqGrid({
			colNames : ["<spring:message code="process.name.label" />",
			            "<spring:message code="instance.name.label" />",
			            "<spring:message code="instance.initiator.label" />",
			            "<spring:message code="instance.startdate.label" />"],
			colModel : [ {
					name : "processName",
					sorttype : "String"
				}, {
					name : "instanceName",
					sorttype : "String"
				}, {
					name : "initRsNm",
					sorttype : "String"
				}, {
					name : "startDate",
					sorttype : "String"
				}
			],
			jsonReader : {
				repeatitems : false
			},
			viewrecords : true,
			autowidth : true,
			height : 'auto',
			rownumbers: true,
			rowNum: 5,
			scrollOffset: 0,
		});
		
		$("#myCompletedInstanceGridTable").jqGrid({
			colNames : ["<spring:message code="process.name.label" />",
			            "<spring:message code="instance.name.label" />",
			            "<spring:message code="instance.initiator.label" />",
			            "<spring:message code="instance.enddate.label" />"],
			colModel : [ {
					name : "processName",
					sorttype : "String"
				}, {
					name : "instanceName",
					sorttype : "String"
				}, {
					name : "initRsNm",
					sorttype : "String"
				}, {
					name : "endDate",
					sorttype : "String"
				}
			],
			jsonReader : {
				repeatitems : false
			},
			loadComplete : function(){
				$(".s-ico").html("");
			},
			sortable: false,
			viewrecords : true,
			autowidth : true,
			height : 'auto',
			rownumbers: true,
			rowNum: 5,
			scrollOffset: 0,
		});
	}
	
	
	
	$(document).ready(function() {
		fn_setGrid();
		fn_getWorlist('${sessionScope.loggedUser.userId}','NEW','myWorkGridTable', 5);
		fn_getMyInstanceList('${sessionScope.loggedUser.userId}','Running','myRunningInstanceGridTable', 5);
		fn_getMyInstanceList('${sessionScope.loggedUser.userId}','Completed','myCompletedInstanceGridTable', 5);
		var worklistCount = fn_setMyWorklistCount('${sessionScope.loggedUser.userId}');
		var instanceCount = fn_setMyInstanceCount('${sessionScope.loggedUser.userId}');
		
		
		$(window).bind('resize', function(){
			var newWidth = $("body").prop("clientWidth")-$("#content-left").width()-$("#content-left").width()-90;
			$("#myWorkGridTable").setGridWidth(newWidth, true);
			$("#myRunningInstanceGridTable").setGridWidth(newWidth, true);
			$("#myCompletedInstanceGridTable").setGridWidth(newWidth, true);
		});
	});
	
	
	
</script>
<title>BPM</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
	<table width=100%>
		<tr>
			<td width=230px style="vertical-align: top">
				<div id="content-left" class="content-left">
					<div class="panel panel-default">
						<div class="panel-heading">
								<span class="glyphicon glyphicon-user"> <spring:message code="logon.infomation.label" /></span>
						</div>
						<div class="panel-body">
							<table>
								<tr>
									<td style="vertical-align: middle;"><img width="90px"
										height="100px" src="<c:url value='${portraitPath}'/>" /></td>
									<td style="padding: 5px"><p>
											<span class="glyphicon glyphicon-expand"><spring:message code="userid.label" />:${sessionScope.loggedUser.userId}</span>
										</p>
										<p>
											<span class="glyphicon glyphicon-expand"><spring:message code="username.label" />:${sessionScope.loggedUser.userName}</span>
										</p>
										<p>
											<span class="glyphicon glyphicon-expand"><spring:message code="jikname.label" />:${sessionScope.loggedUser.jikName}</span>
										</p>
										<p>
											<span class="glyphicon glyphicon-expand"><spring:message code="partname.label" />:${sessionScope.loggedUser.partName}</span>
										</p></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="list-group">
						<span class="list-group-item active"><spring:message code="my.work.label" /></span>
						<a href="#" class="list-group-item"><spring:message code="my.new.work.label" />		<span id="myNewWorkCount" class="badge">0</span></a>
						<a href="#" class="list-group-item"><spring:message code="my.confirmed.work.label" /><span id="myConfiremdWorkCount" class="badge">0</span></a>
						<a href="#" class="list-group-item"><spring:message code="my.completed.work.label" /><span id="myCompletedWorkCount" class="badge">0</span></a>
						<span class="list-group-item active"><spring:message code="my.process.label" /></span>
						<a href="#" class="list-group-item"><spring:message code="my.running.instance.label" />		<span id="myRunningInstanceCount" class="badge">0</span></span></a>
						<a href="#" class="list-group-item"><spring:message code="my.completed.instance.label" /><span id="myCompletedInstanceCount" class="badge">0</span></a>
					</div>
				</div>
			</td>
			<td style="vertical-align: top;">
				<div id="content-middle" class="content-middle" style="width : 100%;">
					<div id="gridPanelDiv" class="panel-group">
						<div class="panel panel-primary">
							<div class="panel-heading"><span class="glyphicon glyphicon-edit"><spring:message code="my.work.label" /><span></div>
							<div class="panel-body"><table id="myWorkGridTable" width="100%"></table></div>
						</div>
						<div class="panel panel-success">
							<div class="panel-heading"><span class="glyphicon glyphicon-share"><spring:message code="my.running.instance.label" /></span></div>
							<div class="panel-body"><table id="myRunningInstanceGridTable" width="100%"></table></div>
						</div>
						<div class="panel panel-info">
							<div class="panel-heading"><span class="glyphicon glyphicon-check"><spring:message code="my.completed.instance.label" /></span></div>
							<div class="panel-body"><table id="myCompletedInstanceGridTable" width="100%"></table></div>
						</div>
					</div>
				</div>
			</td>
			<td width=230px>
				<div class="content-right" style="width:100%"></div>
			</td>
		</tr>
	</table>
		
		
		

	</div>
	<!-- /.container -->
</body>
</html>