<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<script>

	function searchResultEvent(){
		$("#instanceForm").attr("target", "instanceListFrame");
		$("#instanceForm").attr("action", "/processmanager/instanceList.do");
		var searchInput = $("#searchKeyword").val();
		var statusValue = $("#status option:selected").val();
		var searchResult = document.getElementById("instanceListFrame").contentWindow.document.getElementById("searchResult");
		var status = document.getElementById("instanceListFrame").contentWindow.document.getElementById("status");
		searchResult.value = searchInput;
		status.value = statusValue;
		document.getElementById('instanceListFrame').contentWindow.realtimeSearchResult();
	}
	var orgTreeData;
	var definitionObjectArray;
	$(document).ready(function() {
		$("#instanceListFrame").css('height', $(window).height()-200);
		$(window).resize(function(){
			$("#instanceListFrame").css('height', $(window).height()-200);
		});
		
		
		
		$("#status").change(function(){
			searchResultEvent();
		});
		$("#searchSubmit").click(function(){
			searchResultEvent();
		});
	});
	
	
	
</script>

<title>Process Manager</title>
</head>
<body style="overflow:hidden">
	
	
		<!-- Page Content -->
		<div class="container-fluid">
			<table width=100%>
			    <tr>
			    	<td style="vertical-align: top;">
				    	<div id="content-middle" class="content-middle" style="width : 100%;">
							<div id="gridPanelDiv" class="panel-group">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<span id="gridTitle" class="glyphicon glyphicon-edit"><spring:message code="process.information.label" /></span>
									</div>
									<div class="panel-body">
										<table class="table table-reflow" id="myWorkGridTable">
										  <tbody>
										    <tr style="border-bottom: 1px solid #ddd">
										    <div class="row">
										    	<div class="col-md-1">
										      		<td class="active" ><spring:message code="instance.status.label" /></td>
										      	</div>
										      	<div class="col-md-1">
												      <td>
													      <select  id="status" name="status" class="form-control">
												      		  <option value="All"><spring:message code="instance.status.all" /></option>
															  <option value="Running"><spring:message code="instance.status.running" /></option>
															  <option value="Completed"><spring:message code="instance.status.completed" /></option>
															  <option value="Stopped"><spring:message code="instance.status.stopped" /></option>
															  <option value="Failed"><spring:message code="instance.status.cancelled" /></option>
													      </select>
												      </td>
												 </div>
												 <div class="col-md-1">
												      <td>
													      <input  id="searchKeyword" name="searchKeyword" class="form-control" />
												      </td>
												   </div>
												   <div class="col-md-1">
												   		<td>
													      <button id="searchSubmit" name="searchSubmit" value="submit" class="form-control" >
													    	 <spring:message code="process.search.label" />
													      </button>
												      </td>
												   </div>
										      </div>
										    </tr>
										  </tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
	<form:form name="instanceForm" id="instanceForm" action="" method="post" target="instanceListFrame">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="defId" type="hidden" id="defId" name="defId" value="" />
		<form:input path="status" type="hidden" id="status" name="status" value="" /> 
		<form:input path="searchResult" type="hidden" id="searchResult" name="searchResult" value="" /> 
		<div id="instanceListArea"><iframe src="<c:url value='/processmanager/instanceList.do' />" frameborder="0" id="instanceListFrame" name="editorFrame" style="width:100%; overflow-x: hidden;"></iframe></div>
	</form:form>
		</div>
	
</body>
</html>