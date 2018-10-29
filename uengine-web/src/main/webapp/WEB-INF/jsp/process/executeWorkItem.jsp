<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<script>
	var orgTreeData;
	var definitionObjectArray;
	$(document).ready(function() {
		$("#flowchartFrame").css('height', $(window).height()-265);
		$(window).resize(function(){
			$("#flowchartFrame").css('height', $(window).height()-265);
		});
		$("#tabs").tabs();
		$("#flowchartA").click(function(){
			$("#myWorkForm").attr("action","<c:url value='/chart/viewInstanceFlowChart.do' />");
			$("#myWorkForm").attr("target","flowchartFrame");
			$("#myWorkForm").submit();
		});
		
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
		$("#divFlowchartArea").hide();
		try{
			formatDrawAreas();
		} catch(e){ alert(e); }
		$.ajax({
	        url: "<c:url value='/chart/flowchart/instance/get/' />",
	        data: $("#myWorkForm").serialize(),
	        method: "POST"
	    }).then(function(data) {
	       $("#divFlowchartArea").html(data);
	       if ( $("#viewOption").val() && $("#viewOption").val() == "vertical" )
			   $("#divFlowchartArea").show("blind",1000);
	       else
	    	   $("#divFlowchartArea").show("slide",1000);
			setTimeout("drawAll()", 2000);
		   	
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
			<ul>
				<li><a href="#workItemHandlerDiv"><spring:message 	code="process.procvars.label" /></a></li>
				<li><a id="flowchartA" href="#flowchartDiv"><spring:message 	code="process.flowchart.label" /></a></li>
			</ul>
			<div id="workItemHandlerDiv">workItemHandlerDiv</div>
			<div id="flowchartDiv">
				<iframe frameborder="0" id="flowchartFrame" name="flowchartFrame" style="width:100%; overflow-x: hidden;"></iframe></iframe>
			</div>
		</div>
		
	</div>
	<!-- /.container -->
</body>
</html>