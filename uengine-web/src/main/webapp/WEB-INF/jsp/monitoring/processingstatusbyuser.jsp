
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-docexport.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>

<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
.center-ui{text-align: center;}
.f1f1c1-color{background-color: #f1f1c1;}

</style>

<script>
var barChartData = {
		labels: ['황제성', '김명민', '박주영', '차용인', '이재성'],
		datasets: [{
			label: '<spring:message code="menu.monitoring.column.handingnumber" />',
			backgroundColor: window.chartColors.red,
			stack: 'Stack 0',
			data: [
				10,
				9,
				6,
				15,
				10
			]
		}, {
			label: '<spring:message code="menu.monitoring.column.averhandingnumber" />',
			backgroundColor: window.chartColors.blue,
			stack: 'Stack 1',
			data: [
				5,
				5,
				5,
				5,
				5
			]
		}, {
			label: '<spring:message code="menu.monitoring.column.delaynumber" />',
			backgroundColor: window.chartColors.green,
			stack: 'Stack 2',
			data: [
				0,
				0,
				0,
				0,
				2
			]
		}]

	};
  
  var getComboBoxData =  function(){
	  //'demo'
	  $.ajax({
			type : "POST",
			url : contextPath+"/monitoring/combovaluelist/demo",
			cache : false,
			dataType : "JSON",
			success : function(result) {
				let data = result.datas;
				for(eachData in data){
					$("#department").append($('<option>',{ value: data[eachData].groupId, text: data[eachData].groupName }));	
				}
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            alert('There is an error : method(group)에 에러가 있습니다.');
	        }
		});
  }
  
  $( function() {
	  getComboBoxData();	  
	  wordExportBefore('<spring:message code="menu.monitoring.content.psbu" />');
	  $( "#init_start_date" ).datepicker({
	      showOn: "button",
	      buttonImage: "../resources/images/icons/calendar.gif",
	      buttonImageOnly: true,
	      buttonText: "Select date"
	    });
	  $("#init_start_date").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	  $( "#init_end_date" ).datepicker({
	      showOn: "button",
	      buttonImage: "../resources/images/icons/calendar.gif",
	      buttonImageOnly: true,
	      buttonText: "Select date"
	    });
	  $("#init_end_date").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	  setDate(1, 'week');
	  
	  
	  $("#btnExcelExport").click(function (e) {
		  var uri = $("#dvData").excelexportjs({
			    containerid: "dvData" 
			  , datatype: 'table'
			  , encoding: "UTF-8"
			  , returnUri: true
			  , worksheetName: '<spring:message code="menu.monitoring.content.psbu" />'
			  });    
		
		$(this).attr('download', '<spring:message code="menu.monitoring.content.psbu" />.xls').attr('href', uri);
		});
	  
	  $("#btnWordExport").click(function (e) {
		  $("#page-content").wordExport();
	  })
	  
	  
	  var ctx = document.getElementById('canvas').getContext('2d');
		window.myBar = new Chart(ctx, {
			type: 'bar',
			data: barChartData,
			options: {
				title: {
					display: true,
					text: '<spring:message code="menu.monitoring.content.psbu" />'
				},
				tooltips: {
					mode: 'index',
					intersect: false
				},
				responsive: true,
				scales: {
					xAxes: [{
						stacked: true,
					}],
					yAxes: [{
						stacked: true
					}]
				}
			}
		});
  } );
  
  function setDate(num, type){
		 
	  var minusDate = 0;
	  var typeNum = 0;
	  if(type == 'month'){
		  typeNum = 31;
	  }else if(type == 'week'){
		  typeNum = 7;
	  }else{
		
	  }
	  if(num == 0 || num == null ||isNaN(num)){
		  minusDate = 1*typeNum;
	  }else{
		  minusDate = num*typeNum;  
	  }
	  if(minusDate != 0){
		  var today = $("#init_end_date").datepicker('getDate');
		  var setData = new Date();
		  setData.setDate(today.getDate()- minusDate); 
		  $("#init_start_date").val($.datepicker.formatDate('yy-mm-dd', setData));  
	  }
	  
  }
  
</script>
<!-- 사용자별 처리현황 -->
<title><spring:message code="menu.monitoring.content.psbu" /></title>
</head>
<body>
	<div id="divTabItem_MonitorFilter" align="center">
        <table width="95%">
            <tr>
                <td colspan="5" bgcolor="#97aac6" height="2"></td>
            </tr>
            <tr>
                <td style=" padding-top: 10px; padding-bottom: 10px;"><spring:message code="partname.label" /></td>
                <td>
		<select name="department" id="department" style="height: 21.979166px;" >
		<option value="all"><spring:message code="menu.all.label" /></option>
		</select></td>
            </tr>
            <tr bgcolor="#b9cae3"><td colspan="4" height="1"></td></tr>
            <tr>
                <td style=" padding-top: 10px; padding-bottom: 10px;"><spring:message code="menu.monitoring.label.period" /></td>
                <td class="formcon">
	        	<input type="text" id="init_start_date" value="" size="8"/>
	        	~ <input type="text" id="init_end_date" value="" size="8"/>
	        	<button onclick="setDate(1, 'week')" style="margin-left: 15px;">1<spring:message code="menu.monitoring.label.week" /></button>
	        	<button onclick="setDate(1, 'month')" style="margin-left: 1px;">1<spring:message code="menu.monitoring.label.month" /></button>
	        	<button onclick="setDate(3, 'month')"style="margin-left: 1px;">3<spring:message code="menu.monitoring.label.month" /></button>
	        	<button onclick="setDate(6, 'month')"style="margin-left: 1px;">6<spring:message code="menu.monitoring.label.month" /></button>
	        	<a  id="btnExcelExport" href="" download="" target="_blank" style="margin-left: 30px; cursor:pointer;">
	        		<img  src="<c:url value='/resources/images/icons/excel.svg' />" width="18" height="20" border="0">
	        	</a>
	        	<a  id="btnWordExport" href="javascript:void(0)" download="" target="_blank" style="margin-left: 10px; cursor:pointer;">
	        		<img  src="<c:url value='/resources/images/icons/microsoft-word.svg' />" width="18" height="20" border="0">
	        	</a>
	        	
	        	<button style="background-color: #F5F5F5; margin-left: 60px;" ><img src="<c:url value='/resources/images/icons/Search.svg' />" align="absmiddle" width="18" height="20" border="0"><spring:message code="menu.monitoring.label.search" /></button>
	        </td>

            </tr>
            
            <tr>
                <td colspan="5" bgcolor="#97aac6" height="2"></td>
            </tr>
        </table>
</div>
<div id="page-content">
<div style="width: 50%; margin:auto;">
		<canvas id="canvas"></canvas>
	</div>
<div class="container-fluid" style="width:100%; padding-top: 30px;  ">
<table id="dvData" class="table" style="width:100%; border:1px solid #E0E0E0;">
  <tr>
    <th class="center-ui f1f1c1-color"  colspan="2"><spring:message code="menu.monitoring.column.user" /></th>
    <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.handingnumber" /></th>
    <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.averhandingnumber" /></th>
    <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.delaynumber" /></th>
  </tr>
  <tr>
    <td rowspan="5" style="vertical-align: middle; border: 1px solid #E0E0E0;">도시개발과</td>
    <td>황재성</td>
    <td class="center-ui">10</td>
    <td class="center-ui">5</td>
    <td class="center-ui">-</td>
  </tr>
  <tr>
    <td>김명민</td>
    <td class="center-ui">9</td>
    <td class="center-ui">5</td>
    <td class="center-ui">-</td>
  </tr>
  <tr>
    <td>박주영</td>
    <td class="center-ui">6</td>
    <td class="center-ui">5</td>
    <td class="center-ui">-</td>
  </tr>
  <tr>
    <td>차용인</td>
    <td class="center-ui">15</td>
    <td class="center-ui">5</td>
    <td class="center-ui">-</td>
  </tr>
  <tr>
    <td>이재성</td>
    <td class="center-ui">10</td>
    <td class="center-ui">5</td>
    <td class="center-ui" style="color: red;">2</td>
  </tr>
  
   
</table>
</div>
</div>
</body>

</html>