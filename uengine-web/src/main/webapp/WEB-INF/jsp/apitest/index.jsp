<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
</style>

<script>
	$(document).ready(function() {
		$("#contentsFrame").css('height', $(window).height()-200);
		$(window).resize(function(){
			$("#contentsFrame").css('height', $(window).height()-200);
		});		
	});
</script>
<title>API Test</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
				<td width=230px style="vertical-align: top">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<span class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.apitest.label" /></span></span>
							<a href="serviceExecute.do" target="contentsFrame" class="list-group-item"><spring:message code="menu.apitest.service.execute.label" /></a>
							<a href="flowControl.do" target="contentsFrame" class="list-group-item"><spring:message code="menu.apitest.flow.control.label" /></a>
						</div>
					</div>
				</td>
				<td width="100%">
					<div id="iframeArea">
					<iframe id="contentsFrame" src="<c:url value='/apitest/serviceExecute.do' />" name="contentsFrame" style="width: 100%; border: 0px; overflow-y: hidden;"></iframe>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- /.container -->
</body>
</html>