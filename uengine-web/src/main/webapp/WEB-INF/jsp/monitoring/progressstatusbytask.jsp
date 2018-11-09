
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>
<script type="text/javascript" src="<c:url value='/resources/js/monitoringDataSelectToastChart.js'/>"></script>
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

window.onload = function () {
	fn_toastColumnChartByTask('progressstatusbytask',
			'chartContainer',
			'<spring:message code="old.worklist.process.name.label" />',
			'<spring:message code="menu.monitoring.content.pstn" />',
			'<spring:message code="menu.monitoring.content.pgsbt" />');
}
</script>
<title><spring:message code="menu.monitoring.content.pgsbt" /></title>
</head>
<body>
	<div id="chartContainer" style="width: 80%;"></div>
</body>

</html>