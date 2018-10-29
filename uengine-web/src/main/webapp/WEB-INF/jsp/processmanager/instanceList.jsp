<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
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
		
		$("#instanceListGridTable").jqGrid({
			colNames : ["상태",
			            "아이디",
			            "defVerId",
			            "인스턴스명",
			            "프로세스명",
			            "정보",
			            "시작자",
			            "현상태",
			            "현담당자",
			            "시작일",
			            "시작일",
			            "삭제"],
			colModel : [ {
					name : "status",
					sorttype : "String"
				}, {
					name : "instId",
					sorttype : "String"
				}, {
					name : "defVerId",
					sorttype : "String",
					hidden : true
				}, {
					name : "name",
					sorttype : "String"
				}, {
					name : "defName",
					sorttype : "String"
				}, {
					name : "info",
					sorttype : "String"
				}, {
					name : "initRsNm",
					sorttype : "String"
				}, {
					name : "currStatusNames",
					sorttype : "String"
				}, {
					name : "currRsNm",
					sorttype : "String"
				}, {
					name : "strStartedDate",
					sorttype : "String"
				}, {
					name : "strFinishedDate",
					sorttype : "String"
				}, {
					name : "delete",
					fixed : true,
					formatter : function(cellvalue, options, rowObject){
						if (rowObject.isSubprocess=="0")
							return "<span onClick=\"fn_deleteInstance('"+options.rowId+"','"+rowObject.instId+"');\" style='cursor:pointer;' class='glyphicon glyphicon-trash' />";
						else
							return "삭제불가(서브프로세스)";
					}
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
			editurl: 'server.php',
			ondblClickRow: function(rowId){
				if ( window.console){
					console.log($(this).getRowData(rowId));
				}
				$("#instId").val($(this).getRowData(rowId).instId);
				$("#defVerId").val($(this).getRowData(rowId).defVerId);
				$("#instanceForm").serialize();
				//$("#instanceForm").attr("action", "http://localhost:8080/bpm/processmanager/viewProcessFlowChart.do");
				$("#instanceForm").attr("action","<c:url value='/processmanager/viewProcessInstance.do' />");
				$("#instanceForm").submit();
			}
		});
	}
	
	var fn_deleteInstance = function(rowId, instId){
		$.ajax({
	        url: "<c:url value='/processmanager/put/instance/remove/' />"+instId,
	        method: "POST",
	        contentType : "application/json;charset=utf-8;",
	        dataType : "json",
	    }).then(function(data) {
	    	$("#instanceListGridTable").jqGrid("delRowData", rowId);
			$("#instanceListGridTable").trigger("reloadGrid");
	    });
		
	}
	
	function realtimeSearchResult(){
		fn_setGrid();
		fn_getInstanceList($("#instanceForm").serializeArray(), "instanceListGridTable");
		$(window).bind('resize', function(){
			var newWidth = $("body").prop("clientWidth")-$("#content-left").width()-$("#content-left").width()-90;
			$("#myWorkGridTable").setGridWidth(newWidth, true);
		});
	}
	

	
	$(document).ready(function() {
		fn_setGrid();
		fn_getInstanceList($("#instanceForm").serializeArray(), "instanceListGridTable");
		
		$(window).bind('resize', function(){
			var newWidth = $("body").prop("clientWidth")-$("#content-left").width()-$("#content-left").width()-90;
			$("#myWorkGridTable").setGridWidth(newWidth, true);
		});
		
	});
	
	
	
</script>
<title>BPM</title>
</head>
<body>
	<form:form name="instanceForm" id="instanceForm" method="post" >
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="defId" type="hidden" id="defId" name="defId" value="" /> 
		<form:input path="defVerId" type="hidden" id="defVerId" name="defVerId" value="" /> 
		<form:input path="instId" type="hidden" id="instId" name="instId" value="" /> 
		<form:input path="status" type="hidden" id="status" name="status" value="" />
		<form:input path="searchResult" type="hidden" id="searchResult" name="searchResult" value="" /> 
	</form:form>
	<!-- Page Content -->
	<div>
		<table id="instanceListGridTable" width="100%"></table>
		<div id="pager"></div>
	</div>
	<!-- /.container -->
</body>
</html>