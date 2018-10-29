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
		$.ajax({
	        url: "<c:url value='/service/flowchart/pd/' />"+$("#defVerId").val(),
	        method: "GET",
	        dataType : "json",
	    }).then(function(data) {
	       $("#flowchartArea").html(data.chart);
			setTimeout("drawAll()", 1000);
		   	$(window).bind("resize", function() {
		   		drawAll();
		   	}).trigger("resize");
	    });
		
	});

	
	
</script>

<title>Process Manager</title>
</head>
<body style="overflow-x: hidden;">
	<form:form name="definitionForm" id="definitionForm" action="" method="post" target="flowchartFrame">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="defId" type="hidden" id="defId" name="defId" value="${defId}" />
		<form:input path="defVerId" type="hidden" id="defVerId" name="defVerId" value="" />
		<form:input path="folderId" type="hidden" id="folderId" name="folderId" value="" />
	</form:form>
<div id="flowchartArea" style="vertical-align: middle;"></div>		
</body>
</html>