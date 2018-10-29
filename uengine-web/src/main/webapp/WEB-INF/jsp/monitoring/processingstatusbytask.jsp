
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-docexport.jspf"%>

<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
.center-ui{text-align: center;}

</style>

<script>

  var clickevent = function(){
	  alert('test');
  }
  
  $( function() {
	  wordExportBefore('<spring:message code="menu.monitoring.content.pcsbt" />');
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
	  setDate(1, 'week');
	  
	  
	  $("#btnExcelExport").click(function (e) {
		  var uri = $("#dvData").excelexportjs({
			    containerid: "dvData" 
			  , datatype: 'table'
			  , encoding: "UTF-8"
			  , returnUri: true
			  , worksheetName: "<spring:message code="menu.monitoring.content.pcsbt" />"
			  });    
		
		$(this).attr('download', '<spring:message code="menu.monitoring.content.pcsbt" />.xls').attr('href', uri);
		});
	  
	  $("#btnWordExport").click(function (e) {
		  $("#page-content").wordExport();
	  })
  } );
  
  function setDate(num, type){
	 
	  var addDate = 0;
	  var typeNum = 0;
	  if(type == 'month'){
		  typeNum = 31;
	  }else if(type == 'week'){
		  typeNum = 7;
	  }else{
		
	  }
	  if(num == 0 || num == null ||isNaN(num)){
		  addDate = 1*typeNum;
	  }else{
		  addDate = num*typeNum;  
	  }
	  if(addDate != 0){
		  var today = $("#init_start_date").datepicker('getDate');
		  var setData = new Date();
		  setData.setDate(today.getDate()+ addDate); 
		  $("#init_end_date").val($.datepicker.formatDate('yy-mm-dd', setData));  
	  }
	  
  }
  
</script>
<!-- 업무별 처리현황 -->
<title><spring:message code="menu.monitoring.content.pcsbt" /></title>
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
		<option value="a1">도시개발과</option>
		<option value="a2">유기농업과</option>
		<option value="a3">산림녹지과</option>
		<option value="a4">건축1과</option>
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
	        	
	        	<button style="background-color: #F5F5F5; margin-left: 60px;" onclick="clickevent()"><img src="<c:url value='/resources/images/icons/Search.svg' />" align="absmiddle" width="18" height="20" border="0"><spring:message code="menu.monitoring.label.search" /></button>
	        </td>

            </tr>
            
            <tr>
                <td colspan="5" bgcolor="#97aac6" height="2"></td>
            </tr>
        </table>
</div>

<div id="page-content" class="container-fluid" style="width:100%; padding-top: 30px;   ">
<table id="dvData" class="table" style="width:100%; border:1px solid #E0E0E0; ">
  <tr>
    <th class="center-ui" style="background-color: #f1f1c1;"><spring:message code="menu.monitoring.label.rightsection" /></th>
    <th class="center-ui" colspan="2"><spring:message code="old.worklist.process.name.label" /></th>
    <th class="center-ui"><spring:message code="menu.monitoring.column.averagenumber" /></th>
    <th class="center-ui"><spring:message code="menu.monitoring.column.minpracticedays" /></th>
    <th class="center-ui"><spring:message code="menu.monitoring.column.maxpracticedays" /></th>
    <th class="center-ui"><spring:message code="menu.count.label" /></th>
  </tr>
  <tr>
    <td style="background-color: #f1f1c1;" rowspan="5">도시개발과</td>
    <td rowspan="5">복지</td>
    <td>휴양소</td>
    <td class="center-ui">33</td>
    <td class="center-ui">25</td>
    <td class="center-ui">50</td>
    <td class="center-ui">23</td>
  </tr>
  <tr>
    <td>근무복</td>
    <td class="center-ui">12</td>
    <td class="center-ui">10</td>
    <td class="center-ui">22</td>
    <td class="center-ui">21</td>
  </tr>
  <tr>
    <td>선물</td>
    <td class="center-ui">11</td>
    <td class="center-ui">9</td>
    <td class="center-ui">16</td>
    <td class="center-ui">32</td>
  </tr>
  <tr>
    <td>도서</td>
    <td class="center-ui">11</td>
    <td class="center-ui">8</td>
    <td class="center-ui">16</td>
    <td class="center-ui">45</td>
  </tr>
  <tr>
    <td>교육신청</td>
    <td class="center-ui">13</td>
    <td class="center-ui">8</td>
    <td class="center-ui">15</td>
    <td class="center-ui">56</td>
  </tr>
  
   <tr>
    <td style="background-color: #f1f1c1;" rowspan="2">유기농업과</td>
    <td rowspan="2">지원</td>
    <td>명함</td>
    <td class="center-ui">35</td>
    <td class="center-ui">25</td>
    <td class="center-ui">31</td>
    <td class="center-ui">7</td>
  </tr>
  <tr>
    <td>전산용품</td>
    <td class="center-ui">40</td>
    <td class="center-ui">25</td>
    <td class="center-ui">32</td>
    <td class="center-ui">34</td>
  </tr>
  
  <tr>
    <td style="background-color: #f1f1c1;" rowspan="2">산림녹지과</td>
    <td rowspan="2">출장/차량</td>
    <td>출장-국내출장</td>
    <td class="center-ui">41</td>
    <td class="center-ui">27</td>
    <td class="center-ui">47</td>
    <td class="center-ui">5</td>
  </tr>
  <tr>
    <td>출장-해외출장</td>
    <td class="center-ui">46</td>
    <td class="center-ui">29</td>
    <td class="center-ui">45</td>
    <td class="center-ui">7</td>
  </tr>
  
  <tr>
    <td style="background-color: #f1f1c1;" >건축1과</td>
    <td>일반</td>
    <td>방문</td>
    <td class="center-ui">25</td>
    <td class="center-ui">19</td>
    <td class="center-ui">27</td>
    <td class="center-ui">45</td>
  </tr>
</table>
</div>
</body>

</html>