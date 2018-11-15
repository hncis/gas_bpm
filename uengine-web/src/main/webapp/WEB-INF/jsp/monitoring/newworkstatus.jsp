
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>
<script type="text/javascript" src="<c:url value='/resources/js/monitoringDataSelectToastChart.js'/>"></script>

	<div id="chartContainer" style="width: 80%; height: 350px;"></div>
	<script>
	window.onload = function() {
		fn_toastStackedColumn('newworkstatus', 'chartContainer',
				'<spring:message code="menu.monitoring.content.newworkstatus" />');
	};

	
</script>
