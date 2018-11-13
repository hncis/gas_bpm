
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
	  $("#dataListTbody").empty();
	  $("#dataListTbody").prepend("<tr id='dataList'></tr>");
	  getProcessingstatusbytask(false);
  }
  
  var getProcessingstatusbytask = function(init){
	  var inputPartCode;
	  if(init){
		  inputPartCode = null;
	  } else {
		  inputPartCode = $("#department option:selected").val();
		  if(inputPartCode == "all"){
		  	 inputPartCode = null;
		  }
	  }
	  
	  $("#partCode").val(inputPartCode);
	  $("#searchFromDate").val($("#init_start_date").val());
	  $("#searchToDate").val($("#init_end_date").val());
	  
	  var sendData = $("#processingstatusbytask").serialize();
	  $.ajax({
			type : "POST",
			data : sendData,
			url : contextPath+"/monitoring/processingstatusbytask",
			cache : false,
			dataType : "JSON",
			success : function(result) {
				let data = result.datas;
				makeTableList(data);
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            alert('There is an error : method(group)에 에러가 있습니다.');
	        }
		});
  }
  
  var makeTableList = function(data){
	  var dvData = $("#dvData");
		var counter = 0;
		for(eachData in data){
			isInit = true;
			var oneDepthData = data[eachData].depthOneArray;
			counter = 0;
			for(var depthOneObject in oneDepthData){
				var twoDepthData = oneDepthData[depthOneObject];
				for(var depthTwoObject in twoDepthData){
					if(depthTwoObject == 'depthTwoArray'){
						counter += twoDepthData[depthTwoObject].length;
					}
					
				}
			}
			var makeTr = document.createElement("tr");
			var makeTd = document.createElement("td");
			makeTd.style.textAlign="center";
			makeTd.style.verticalAlign="middle";
			makeTd.style.backgroundColor="#f1f1c1";
			makeTd.rowSpan = counter;
			makeTd.innerHTML = data[eachData].partName;
			makeTr.appendChild(makeTd);
			
			for(var depthOneObject in oneDepthData){
				var twoDepthData = oneDepthData[depthOneObject];
				for(var depthTwoObject in twoDepthData){
					if(depthTwoObject == 'depthTwoArray' && (twoDepthData[depthTwoObject].length != 0)){
						var processPathTd = document.createElement("td");
						processPathTd.style.textAlign="center";
						processPathTd.innerHTML = twoDepthData.processType;
						processPathTd.rowSpan = twoDepthData[depthTwoObject].length;
						makeTr.appendChild(processPathTd);
						var threeDepthData = twoDepthData[depthTwoObject];
						for(depthThreeObject in threeDepthData){
							var pathTd = document.createElement("td");
							pathTd.style.textAlign="center";
							pathTd.innerHTML = threeDepthData[depthThreeObject].path;
							makeTr.appendChild(pathTd);
							var workingDayAVGTd = document.createElement("td");
							workingDayAVGTd.innerHTML = threeDepthData[depthThreeObject].workingDayAVG;
							workingDayAVGTd.style.textAlign="center";
							makeTr.appendChild(workingDayAVGTd);
							var workingDayMaxTd = document.createElement("td");
							workingDayMaxTd.innerHTML = threeDepthData[depthThreeObject].workingDayMax;
							workingDayMaxTd.style.textAlign="center";
							makeTr.appendChild(workingDayMaxTd);
							var workingDayMinTd = document.createElement("td");
							workingDayMinTd.innerHTML = threeDepthData[depthThreeObject].workingDayMin;
							workingDayMinTd.style.textAlign="center";
							makeTr.appendChild(workingDayMinTd);
							var totalCountTd = document.createElement("td");
							totalCountTd.style.textAlign="center";
							totalCountTd.innerHTML = threeDepthData[depthThreeObject].totalCount;
							makeTr.appendChild(totalCountTd);
							$("#dataList").before(makeTr);
							makeTr = document.createElement("tr");
							makeTr.id ="makeTr";
						}								
					} 
				}
				
			}
			
		}  
  }
  
  var getComboBoxData =  function(){
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
	  $("#init_end_date").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	  setDate(1, 'week');
	  getProcessingstatusbytask(true);
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
<table id="dvColume" class="table" style="width:100%; border:1px solid #E0E0E0; ">
  <tbody>
	  <tr>
	    <th class="center-ui" style="background-color: #f1f1c1;"><spring:message code="menu.monitoring.label.rightsection" /></th>
	    <th class="center-ui" colspan="2"><spring:message code="old.worklist.process.name.label" /></th>
	    <th class="center-ui"><spring:message code="menu.monitoring.column.averagenumber" /></th>
	    <th class="center-ui"><spring:message code="menu.monitoring.column.minpracticedays" /></th>
	    <th class="center-ui"><spring:message code="menu.monitoring.column.maxpracticedays" /></th>
	    <th class="center-ui"><spring:message code="menu.count.label" /></th>
	  </tr>
  </tbody>
  <tbody id ="dataListTbody">
	  <tr id="dataList"></tr>
  </tbody>
  
  </table>
  
</div>
<form name="processingstatusbytask" id="processingstatusbytask" method="post" action="">
	<input type="hidden" name="partCode" id="partCode">
	<input type="hidden" name="searchFromDate" id="searchFromDate">
	<input type="hidden" name="searchToDate" id="searchToDate">
</form>

</body>

</html>