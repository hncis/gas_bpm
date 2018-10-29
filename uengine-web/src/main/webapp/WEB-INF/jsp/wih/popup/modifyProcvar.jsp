<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<script>
var procVars=${command.procVarsJson};
var lastsel;
var isAddDel = false;
var fn_deleteRow = function(rowId,value){
	lastsel = null;
	if ( procVars.length == 1 ){
		procVars[0].value="";
	} else{
		procVars.splice(rowId-1,1);
	}
	$("#procVarValListGridTable").jqGrid('setGridParam',{
		datatype: "local",
		data:procVars
		}
	);
	$("#procVarValListGridTable").trigger("reloadGrid");
}
var fn_addRow = function(rowId,value,nullIgnore){
	lastsel = null;
	if(!nullIgnore && (!value || value == "")){
		isAddDel = false;
		return;
	}
	procVars.splice(rowId,0,{"name":"dummy",value:""});
	$("#procVarValListGridTable").jqGrid('setGridParam',{
		datatype: "local",
		data:procVars
		}
	);
	$("#procVarValListGridTable").trigger("reloadGrid");
	isAddDel = false;
}
var fn_setGrid = function(data) {
	$("#procVarValListGridTable").jqGrid({
		colNames : ["값","삭제"],
		colModel : [ {
				name : "value",
				sorttype : "String",
				editable: true
			}, {
				name : "delete",
				fixed : true,
				formatter : function(cellvalue, options, rowObject){
					return "<span onClick=\"fn_addRow('"+options.rowId+"','"+rowObject.value+"', false);\" style='cursor:pointer;' class='glyphicon glyphicon-plus-sign' />&nbsp;"
					+"<span onClick=\"fn_deleteRow('"+options.rowId+"','"+rowObject.value+"');\" style='cursor:pointer;' class='glyphicon glyphicon-minus-sign' />";
				}
			}
		],
		jsonReader : {
			repeatitems : false
		},
		datatype: "local",
		data: data,
		viewrecords : true,
		autowidth : true,
		rowNum: 1000,
		height : "auto",
		rownumbers: true,
		scrollOffset: 0,
		editurl: "<c:url value='/service/gridSaveDummy' />",
		onSelectRow: function(id){
			$("#procVarValListGridTable").jqGrid('editRow',id, {
				keys:true,
				aftersavefunc:function(id,resp,options){
					console.log(id);
					console.log(resp);
					console.log(options);
					console.log(procVars);
					
					/*
					procVars.splice(id-1,0,{"rowId":id,"value":options.value});
					$("#procVarValListGridTable").jqGrid('setGridParam',{
						datatype: "local",
						data:procVars
						}
					);
					*/
					$("#procVarValListGridTable").trigger("reloadGrid");
				} 
			});
			$("#procVarValListGridTable").jqGrid('saveRow',lastsel);
		lastsel=id;
		},
		ondblClickRow: function(rowId){
			
		},
		afterEditCell:function(res){
			alert(res);
		}
	});
}
	$(document).ready(function() {
		$("#tabs").tabs();
		fn_setGrid(procVars);
	});
	
</script>

<title>Process Manager</title>
</head>
<body>
	<form:form name="myWorkForm" id="myWorkForm" command="command">
		<form:input type="hidden" id="processName"		    path="processName"		   />
		<form:input type="hidden" id="instanceId"		path="instanceId"		   />
		<form:input type="hidden" id="tracingTag" 	    path="tracingTag"		   />
		<form:input type="hidden" id="taskId"		    path="taskId"			   />
		<form:input type="hidden" id="userId"	    path="userId"		   />
		<form:input type="hidden" id="viewType"	    path="viewType"		   />
		<form:input type="hidden" id="viewOption"	    path="viewOption"		   />
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<div class="alert alert-info">
			<Strong>${command.processName }(${command.instanceName })</Strong>
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#workItemHandlerDiv">${command.procVarDisplayName }</a></li>
			</ul>
			<div id="procVarGridDiv">
				<table id="procVarValListGridTable" name="procVarValListGridTable" />
				<div id="pager"></div>
			</div>
		</div>
		
	</div>
	<!-- /.container -->
</body>
</html>