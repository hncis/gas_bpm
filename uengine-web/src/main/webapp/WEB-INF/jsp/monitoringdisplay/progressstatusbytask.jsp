
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>
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

var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	theme: "light2", // "light1", "light2", "dark1", "dark2"
	title:{
		text: "<spring:message code="menu.monitoring.content.progressstatus" />"
	},
	axisY: {
		title: "<spring:message code="menu.monitoring.content.pstn" />"
	},
	data: [{        
		type: "column",  
		showInLegend: true, 
		legendMarkerColor: "grey",
		legendText: "<spring:message code="old.worklist.process.name.label" />",
		dataPoints: [      
			{ y: 13, label: "휴양소" },
			{ y: 32,  label: "회원가입" },
			{ y: 24,  label: "근무복" },
			{ y: 34,  label: "선물" },
			{ y: 66,  label: "도서" },
			{ y: 22, label: "교육신청" },
			{ y: 11,  label: "경조사" },
			{ y: 12,  label: "명함" },
			{ y: 33,  label: "전산용품" },
			{ y: 35,  label: "사무용품" },
			{ y: 75,  label: "통근버스" },
			{ y: 32,  label: "물품관리" },
			{ y: 12,  label: "출장-국내출장" },
			{ y: 23,  label: "출장-해외출장" },
			{ y: 77,  label: "픽업" },
			{ y: 22,  label: "교통비" },
			{ y: 11,  label: "차량신청" },
			{ y: 66,  label: "주유비" },
			{ y: 34,  label: "운행일지" },
			{ y: 74,  label: "방문" },
			{ y: 85,  label: "방문-차량출입" },
			{ y: 22,  label: "회의실" },
			{ y: 33,  label: "증명서" },
			{ y: 43,  label: "연차" }
		]
	}]
});
chart.render();

}
</script>
<title><spring:message code="menu.monitoring.content.pgsbt" /></title>
</head>
<body>
	<div id="chartContainer" style="width: 75%; margin: 0px auto;"></div>
</body>

</html>