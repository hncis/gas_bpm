
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
  var drawProcessingstatusbyuserWindow = function(){
	  var inputPartCode = $("#department option:selected").val();
	  console.log("inputPartCode: " + inputPartCode);
	  if(inputPartCode == "select"){
		alert("부서를 선택하여 주세요");
	  	return;
	  }
	  $("#partCode").val(inputPartCode);
	  $("#searchFromDate").val($("#init_start_date").val());
	  $("#searchToDate").val($("#init_end_date").val());
	  var sendData = $("#processingstatusbytask").serialize();
	  $.ajax({
			type : "POST",
			data : sendData,
			url : contextPath+"/monitoring/processingstatusbyuser",
			cache : false,
			dataType : "JSON",
			success : function(result) {
				makeEmpColumnChart((result.data[0]), "chart");
				makeTable((result.data[1]).tableData, inputPartCode);
				console.log((result.data[1]).tableData, inputPartCode);
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            alert('There is an error : method(group)에 에러가 있습니다.');
	        }
		});
  }
  var makeEmpColumnChart =  function(data, chartName){
	  $("#wholeWindow").empty();
	  $("#wholeWindow").prepend("<div id='chart'></div>");
	  var container = document.getElementById(chartName);
	  var options = {
	      chart: {
	          width: 500,
	          height: 300,
	          title: '<spring:message code="menu.monitoring.content.psbu" />'
	      },
	      yAxis: {
	          title: '<spring:message code="menu.count.label" />',
	          min: 0
	          
	      },
	      xAxis: {
	          title: '<spring:message code="menu.monitoring.column.user" />'
	      },
	      legend: {
	          align: 'top'
	      }
	  };
	  tui.chart.columnChart(container, data, options);
  }
  var makeTable = function(data, partCode){
	  $("#dataListTbody").empty();
	  $("#dataListTbody").prepend("<tr id='dataList'></tr>");
	  if (data.length != 0 ) {
		  for(eachObject in data){
			  var makeTr = document.createElement("tr");		  
			  if(eachObject == 0){
				  var makePartNameTd = document.createElement("td");
				  makePartNameTd.rowSpan = data.length;
				  makePartNameTd.style.verticalAlign = 'middle';
				  makePartNameTd.style.borderBottom = '1px solid #E0E0E0';
				  makePartNameTd.innerHTML = partCode;
				  makeTr.appendChild(makePartNameTd);
			  }
			 
			  var makeNameTd = document.createElement("td");
			  makeNameTd.innerHTML = data[eachObject].name;
			  makeNameTd.style.borderBottom = '1px solid #E0E0E0';
			  var makePassedCountTd = document.createElement("td");
			  makePassedCountTd.className = "center-ui";
			  makePassedCountTd.innerHTML = data[eachObject].passedCount;
			  makePassedCountTd.style.borderBottom = '1px solid #E0E0E0';
			  var makePassedDayAVGTd = document.createElement("td");
			  makePassedDayAVGTd.className = "center-ui";
			  makePassedDayAVGTd.innerHTML = data[eachObject].passedDayAVG;
			  makePassedDayAVGTd.style.borderBottom = '1px solid #E0E0E0';
			  var makeDelayedDayAVG = document.createElement("td");
			  makeDelayedDayAVG.className = "center-ui";
			  makeDelayedDayAVG.innerHTML = data[eachObject].delayedDayAVG;
			  makeDelayedDayAVG.style.borderBottom = '1px solid #E0E0E0';
			  makeTr.appendChild(makeNameTd);
			  makeTr.appendChild(makePassedCountTd);
			  makeTr.appendChild(makePassedDayAVGTd);
			  makeTr.appendChild(makeDelayedDayAVG);
			  $("#dataList").before(makeTr);
		  } 
	  } else {
		  var makeTr = document.createElement("tr");
		  var nullPointTd = document.createElement("td");
		  nullPointTd.colSpan = 5;
		  nullPointTd.style.verticalAlign = 'middle';
		  nullPointTd.style.border = '1px solid #E0E0E0';
		  nullPointTd.className = "center-ui";
		  nullPointTd.innerHTML = "데이터가 존재하지 않습니다";
		  makeTr.appendChild(nullPointTd);
		  $("#dataList").before(makeTr);
	  }
	  
  }
  
  function initSetting(){
	  $("#dataListTbody").empty();
	  $("#dataListTbody").prepend("<tr id='dataList'></tr>");
	  var makeTr = document.createElement("tr");
	  var initTd = document.createElement("td");
	  initTd.colSpan = 5;
	  initTd.style.verticalAlign = 'middle';
	  initTd.style.border = '1px solid #E0E0E0';
	  initTd.className = "center-ui";
	  initTd.innerHTML = "부서를 선택하세요";
	  makeTr.appendChild(initTd);
	  $("#dataList").before(makeTr);
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
		  $("#tableList").wordExport();
	  });
	  $("#searchButton").click(function (e){
		  drawProcessingstatusbyuserWindow();
	  });
	  initSetting();
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
		<option value="select"><spring:message code="menu.select.label" /></option>
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
	        	
	        	<button id="searchButton" style="background-color: #F5F5F5; margin-left: 60px;" ><img src="<c:url value='/resources/images/icons/Search.svg' />" align="absmiddle" width="18" height="20" border="0"><spring:message code="menu.monitoring.label.search" /></button>
	        	</td>
            </tr>
            <tr>
                <td colspan="5" bgcolor="#97aac6" height="2"></td>
            </tr>
        </table>
</div>
<div id="page-content">
<div id="wholeWindow" style="width: 50%; margin:auto;">
		<div id='chart'></div>
	</div>
<div id="tableList" class="container-fluid" style="width:100%; padding-top: 30px;  ">
<table id="dvData" class="table" style="width:100%; border-top:1px solid #E0E0E0; border-collapse: collapse;">
  <tbody style="border-top:1px solid #E0E0E0;">
    <tr>
      <th class="center-ui f1f1c1-color"  colspan="2"><spring:message code="menu.monitoring.column.user" /></th>
      <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.handingnumber" /></th>
      <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.averhandingnumber" /></th>
      <th class="center-ui f1f1c1-color"><spring:message code="menu.monitoring.column.delaynumber" /></th>
    </tr>
  </tbody>
  <tbody id="dataListTbody" style="border-top:1px solid #E0E0E0;">
  </tbody>
</table>
</div>
</div>
<form name="processingstatusbytask" id="processingstatusbytask" method="post" action="">
	<input type="hidden" name="partCode" id="partCode">
	<input type="hidden" name="searchFromDate" id="searchFromDate">
	<input type="hidden" name="searchToDate" id="searchToDate">
</form>
</body>

</html>