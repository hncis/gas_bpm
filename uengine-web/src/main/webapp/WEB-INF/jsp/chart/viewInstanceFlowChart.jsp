<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<script>
	$(document).ready(function() {
		window.onresize = drawAll;
		fn_drawFlowchart();
		$(".btn-group > .btn").click(function(){
			$(this).addClass("active").siblings().removeClass("active");	
			
			var ids = $(this).attr("id").split("-");
			
			$("#viewType").val(ids[0]);
			$("#viewOption").val(ids[1]);
			fn_drawFlowchart();
		});
		$("#contentsFrame").css('height', $(window).height()-105);
		$(window).resize(function(){
			$("#contentsFrame").css('height', $(window).height()-105);
		});
	});
	
	var fn_drawFlowchart = function(){
		//$("#divFlowchartArea").hide();
		try{
			formatDrawAreas();
		} catch(e){ alert(e); }
		$.ajax({
	        url: "<c:url value='/chart/flowchart/instance/get' />",
	        data: JSON.stringify($("#myWorkForm").serializeArray()),
	        contentType : "application/json;charset=utf-8;",
	        dataType : "json",
	        method: "POST"
	    }).then(function(data) {
	    	console.log(data);
	       $("#divFlowchartArea").html(data.chartString);
			setTimeout("drawAll()", 1000);
		   	
	    });
	}
	
	
	
</script>

<title>Process Manager</title>
</head>
<body>
	<form:form name="myWorkForm" id="myWorkForm">
		<form:input type="hidden" id="defName"		    path="defName"		   />
		<form:input type="hidden" id="instanceId"		path="instanceId"		   />
		<form:input type="hidden" id="tracingTag" 	    path="tracingTag"		   />
		<form:input type="hidden" id="taskId"		    path="taskId"			   />
		<form:input type="hidden" id="userId"	    path="userId"		   />
		<form:input type="hidden" id="viewType"	    path="viewType"		   />
		<form:input type="hidden" id="viewOption"	    path="viewOption"		   />
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<div id="tabs">
			<div class="btn-group" data-toggle="buttons">
				<button type="button" id="swimlane-horizontal" class="btn btn-default active"><spring:message code="flowchart.type.swimlane.horizontal.label" /></button>
				<button type="button" id="swimlane-vertical" class="btn btn-default"><spring:message code="flowchart.type.swimlane.vertical.label" /></button>
				<button type="button" id="horizontal-horizontall" class="btn btn-default"><spring:message code="flowchart.type.horizontal.label" /></button>
				<button type="button" id="vertical-vertical" class="btn btn-default"><spring:message code="flowchart.type.vertical.label" /></button>
			</div>
			<div id="canvasForLoopLines" style="position:absolute;left:0px;top:0px;z-index: -1"></div>
			<div id="divFlowchartArea" style="display: block;vertical-align: middle; text-align: center;"></div>
		</div>
		
	</div>
	<!-- /.container -->
</body>
</html>