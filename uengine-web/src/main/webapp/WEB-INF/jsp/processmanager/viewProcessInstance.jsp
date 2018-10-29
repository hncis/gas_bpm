<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<script>
	var orgTreeData;
	var definitionObjectArray;
	$(document).ready(function() {
		$("#tabs").tabs();
		$("#flowchartFrame").css('height', $(window).height()-265);
		$(window).resize(function(){
			$("#flowchartFrame").css('height', $(window).height()-265);
		});
		
        $("#instanceForm").submit();
        
        $("#definitionChangeBtn").click(function(){
			$("#instanceForm").attr("action","<c:url value='/processmanager/processDesignerInstance.jnlp' />");
	        $("#instanceForm").submit();
		});
	});

</script>

<title>Process Manager</title>
</head>
<body style="overflow:hidden">
	<form:form name="instanceForm" id="instanceForm" action="viewProcessInstanceFlowChart.do" method="post" target="flowchartFrame">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="defId" type="hidden" id="defId" name="defId" value="${defId}" />
		<form:input path="instId" type="hidden" id="instId" name="instId" />
		<form:input path="defVerId" type="hidden" id="defVerId" name="defVerId" />
	<!-- Page Content -->
	<table width="100%" class="table table-reflow">
		<tr>
			<td colspan="2">
				<span id="defTitle" class="glyphicon glyphicon-duplicate"></span>
			</td>
		</tr>
		<tr>
			<th>
				<span class="glyphicon glyphicon-bullhorn">인스턴스 정보</span>
			</th>
			<td>
				<button id="definitionChangeBtn" type="button" class="btn btn-info">
					인스턴스 변경
				</button>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tabs">
					<ul>
						<li><a href="#flowchartArea"><spring:message 	code="process.flowchart.label" /></a></li>
						<li><a href="#procVarsArea"><spring:message 	code="process.procvars.label" /></a></li>
						<li><a href="#rolesArea"><spring:message 		code="process.roles.label" /></a></li>
					</ul>
					<div id="flowchartArea"><iframe frameborder="0" id="flowchartFrame" name="flowchartFrame" style="width:100%; overflow-x: hidden;"></iframe></div>
					<div id="procVarsArea">procVarsArea</div>
					<div id="rolesArea">rolesArea</iframe></div>
				</div>
			</td>
		</tr>
	</table>
		
	</form:form>
</body>
</html>