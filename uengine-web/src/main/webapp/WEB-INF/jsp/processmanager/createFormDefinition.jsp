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
		$("#editorFrame").css('height', $(window).height()-265);
		$(window).resize(function(){
			$("#editorFrame").css('height', $(window).height()-265);
		});
		
		
	});

	
	
</script>

<title>Process Manager</title>
</head>
<body style="overflow:hidden">
	<form:form name="definitionForm" id="definitionForm" action="" method="post" target="editorFrame">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="folderId" type="hidden" id="folderId" name="folderId" value="" />
	
		<!-- Page Content -->
		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading"><span id="gridTitle" class="glyphicon glyphicon-edit"><spring:message code="process.information.label" /><span></div>
				<div class="panel-body">
					<table class="table table-reflow">
					  <tbody>
					    <tr>
					      <td class="active" width="200px"><spring:message code="form.name.label" /></td>
					      <td id="processNameTd"><form:input path="defName" type="text" id="defName" name="defName" value="" /></td>
					    </tr>
					    <tr style="border-bottom: 1px solid #ddd">
					      <td class="active"><spring:message code="form.description.label" /></td>
					      <td id="processDescriptionTd"><form:input path="description" type="text" id="description" name="description" value="" /></td>
					    </tr>
					    <tr style="border-bottom: 1px solid #ddd">
					      <td class="active"><spring:message code="form.alias.label" /></td>
					      <td id="processDescriptionTd"><form:input path="alias" type="text" id="alias" name="alias" value="" /></td>
					    </tr>
					  </tbody>
					</table>
				</div>
			</div>
		<table width="100%" class="table table-reflow">
			<tr>
				<td colspan="2">
					<div id="flowchartArea"><iframe frameborder="0" id="editorFrame" name="editorFrame" style="width:100%; overflow-x: hidden;"></iframe></div>
				</td>
			</tr>
		</table>
		</div>
	</form:form>
		
</body>
</html>