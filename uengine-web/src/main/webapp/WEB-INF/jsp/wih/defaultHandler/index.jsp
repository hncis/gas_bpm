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
		$("#flowchartFrame").css('height', $(window).height()-265);
		$(window).resize(function(){
			$("#flowchartFrame").css('height', $(window).height()-265);
		});
		$("#tabs").tabs();
		$("#flowchartA").click(function(){
			$("#myWorkForm").attr("action","<c:url value='/worklist/viewInstanceFlowChart.do' />");
			$("#myWorkForm").attr("target","flowchartFrame");
			$("#myWorkForm").submit();
		});
		
		$("button[id$='_modifyBtn']").click(function(){
			var parameterJson = JSON.stringify($("#parameterForm").serializeArray());
			var id = $(this).attr("id");
			var procVarName = id.substring(0, id.lastIndexOf("_"));
			var procVarDisplayName = $("#"+procVarName+"_displayName").val();
			$("#parameterJson").val(parameterJson);
			$("#procVarName").val(procVarName);
			$("#procVarDisplayName").val(procVarDisplayName);
			window.open("","modify_popup","width=600,height=400");
			$("#myWorkForm").attr("target","modify_popup");
			$("#myWorkForm").attr("action","<c:url value='/wih/popup/modifyProcvar.do' />");
			$("#myWorkForm").submit();
			
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
	<form:form name="myWorkForm" id="myWorkForm" command="command" method="post">
		<form:input type="hidden" id="processName"		    path="processName"		   />
		<form:input type="hidden" id="instanceId"		path="instanceId"		   />
		<form:input type="hidden" id="instanceId"		path="instanceName"		   />
		<form:input type="hidden" id="instanceId"		path="title"		   />
		<form:input type="hidden" id="tracingTag" 	    path="tracingTag"		   />
		<form:input type="hidden" id="taskId"		    path="taskId"			   />
		<form:input type="hidden" id="userId"	    path="userId"		   />
		<form:input type="hidden" id="viewType"	    path="viewType"		   />
		<form:input type="hidden" id="viewOption"	    path="viewOption"		   />
		<form:input type="hidden" id="parameterJson"	    path="parameterJson"		   />
		<form:input type="hidden" id="procVarName"	    path="procVarName"		   />
		<form:input type="hidden" id="procVarDisplayName"	    path="procVarDisplayName"		   />
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<div class="alert alert-info">
			<Strong>${command.processName }(${command.instanceName })</Strong>&nbsp;-&nbsp;${command.title }
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#workItemHandlerDiv"><spring:message 	code="workitem.form.label" /></a></li>
				<li><a id="flowchartA" href="#flowchartDiv"><spring:message 	code="process.flowchart.label" /></a></li>
			</ul>
			<div id="workItemHandlerDiv">
				<form id="parameterForm" name="parameterForm" />
					<c:forEach var="params" items="${command.parameters }">
						<p>${params.variable.displayNameString } : ${params.variable.valueString }
						<input type="hidden" id="${params.variable.name }_displayName" name="${params.variable.name }_displayName" value="${params.variable.displayNameString }" />
						<input type="hidden" id="${params.variable.name }_typeClassName" name="${params.variable.name }_typeClassName" value="${params.typeClassName }" />
						<button id="${params.variable.name }_modifyBtn" type="button" class="btn btn-primary btn-xs">변경</button>
						<div id="${params.variable.name }_values" style="display:none;">
						<c:forEach var="paramvalues" items="${params.variable.value }">
							<input type="hidden" id="${params.variable.name }" name="${params.variable.name }" value="${paramvalues }" />
						</c:forEach>
						</div>
						</p>
					</c:forEach>
				</form>
			</div>
			<div id="flowchartDiv">
				<iframe frameborder="0" id="flowchartFrame" name="flowchartFrame" style="width:100%; overflow-x: hidden;"></iframe></iframe>
			</div>
		</div>
		
	</div>
	<!-- /.container -->
</body>
</html>