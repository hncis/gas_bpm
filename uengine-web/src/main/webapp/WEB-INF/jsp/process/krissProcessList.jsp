<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<style>
.form-control {font-size:12px;height:28px; padding:0px	}
/*
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
*/
</style>
<script>

	var totalCount = 0;
	var pageSize = 15;
	var pagerSize = 10;
	var pageNo = ${pageNo};
	
	var dateFormat = "yy-mm-dd";
	
	$(document).ready(function() {
		$("#folderName").selectmenu({
			width: "auto"
		});
		$("#searchOption").selectmenu({
			width: "auto"
		});
		$("#status").selectmenu({
			width: "auto"
		});
		var from = $("#fromDate").datepicker({
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			dateFormat: dateFormat,
			resional: "ko-KR"
		}).on("change", function(){
			to.datepicker("option","minDate", $(this).val());
		});
		var to = $("#toDate").datepicker({
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			dateFormat: dateFormat
		}).on("change", function(){
			from.datepicker("option","maxDate", $(this).val());
		});
		
		
		fn_getMyProcessList("${command.pageNo}");
		
	});
	
	var fn_callPaging= function(){
		$("#totalCount").val(totalCount);
		$("#pageSize").val(pageSize);
		$("#pagerSize").val(pagerSize);
		$("#pageNo").val(pageNo);
		if ( totalCount == 0 ) {
			pagerFrame.attr("src","");
		} else {
			$("#pagerForm").attr("action","<c:url value='/common/paging.do' />");
			$("#pagerForm").submit();
		}
	}
	
//	var fn_getMyWorklistCount = function() {
	var fn_getMyProcessListCount = function() {
		$.ajax({
			type : "GET",
			url : "<c:url value='/process/kriss/count/process' />",
			cache : false,
			dataType : "JSON",
			success : function(data) {
				$("#totalCountSpan").html(data);	
				totalCount = data;
				fn_callPaging();
			},
			error : function(data, msg) {
				if(window.console){
					console.log(data);
					console.log(msg);
				}
			}
		});
	}
	
	var fn_getMyProcessList = function(page) {
		$("#pagerFrame").attr("src","");
		$.ajax({
			type : "GET",
			url : "<c:url value='/process/kriss/list/process/' />"+page+"/"+pageSize,
			cache : false,
			dataType : "JSON",
			beforeSend: function(){
				$("#loadingDiv").show();
				$("#contentTbody").html("");
			},
			complete: function(){
				$("#loadingDiv").hide();
			},
			success : function(data) {
				pageNo = page;
				fn_getMyProcessListCount();
			    var contentTbody = $("#contentTbody");
			    for ( var i = 0; i < data.length; i++ ){
			    	var tr = $("<tr></tr>")
			    	//folderName
			    	var folderNamneChild = $("<th></th>").attr("scope","row").attr("style","vertical-align: middle; padding:2px;");
			    	var folderNameFont = $("<font></font>").attr("size","2").html(data[i].folderName);
			    	folderNameFont.addClass("list_font");
			    	folderNamneChild.append(folderNameFont);
			    	tr.append(folderNamneChild);
			    	//defName
			    	var defNameChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var defNameFont = $("<font></font>").attr("size","2").html(data[i].defName);
			    	defNameFont.addClass("list_font");
			    	defNameChild.append(defNameFont);
			    	tr.append(defNameChild);
			    	//processCode
			    	var processCodeChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var processCodeFont = $("<font></font>").attr("size","2").html(data[i].processCode);
			    	processCodeFont.addClass("list_font");
			    	processCodeChild.append(processCodeFont);
			    	tr.append(processCodeChild);
			    	//duration
			    	var durationChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var durationFont = $("<font></font>").attr("size","2").html(data[i].duration);
			    	durationFont.addClass("list_font");
			    	durationChild.append(durationFont);
			    	tr.append(durationChild);
			    	//start
			    	var iconChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var iconImg = $("<img></img>").attr("src","<c:url value='/resources/images/icons/btn_process.gif' />");
			    	iconChild.append(iconImg);
			    	tr.append(iconChild);
			    	
			    	contentTbody.append(tr);
			    }
				
			},
			error : function(data, msg) {
				alert("system error!");
			}
		});
	}
	
	
</script>
<title>BPM</title>
</head>
<body style="font-size: 12px;">
	<form:form name="workListForm" id="workListForm" method="post" target="_blank">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="userId" type="hidden" id="userId" name="userId" value="${sessionScope.loggedUser.userId}" /> 
		<form:input path="instanceId" type="hidden" id="instanceId" name="instanceId" value="" /> 
		<form:input path="tracingTag" type="hidden" id="tracingTag" name="tracingTag" value="" /> 
		<form:input path="taskId" type="hidden" id="taskId" name="taskId" value="" /> 
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr height="20px">
				<td>
					<div class="well well-sm" style="padding:0px 0px 0px 10px;font-family: 돋움, 굴림, Gulim, Verdana, Arial; font-size: 12px;">
						<img src="<c:url value='/resources/images/kriss_old/lo_folder.gif' />" align="absmiddle" width="18" height="20" border="0">
                        <font color="#999999">	<spring:message code="old.menu.home.label" 	/>		&gt; 
                        						<spring:message code="old.menu.bpm.manager.label" 	/>			&gt; 
                        						<spring:message code="old.menu.worklist.label" 		/>			&gt;	</font> 
                        <font color="#6E98CF">	<spring:message code="old.menu.process.list.label" 		/>
                        </font>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table">
						<tr style="border-bottom: 1px solid #ddd;">
							<td class="active" style="vertical-align: middle"><spring:message code="old.worklist.work.type.label" 	/></td>
							<td style="vertical-align: middle" >
								<select id="folderName" name="folderName" style="width:auto;">
								<!-- <select id="status" name="status" class="form-control" style="width:auto;"> -->
									<option value="all"><spring:message code="menu.all.label" 	/></option>
						      		<c:forEach var="paramvalues" items="${folderList }">
						      			<option value="${paramvalues.defId }">${paramvalues.defName }</option>
						      		</c:forEach>
						      	</select>
							</fieldset>
							</td>
							<td class="active" style="vertical-align: middle">
								<select id="searchOption" name="searchOption" style="width:100px;">
									<option value="defName"><spring:message code="process.name.label" 	/></option>
									<option value="info"><spring:message code="old.worklist.process.code.label" 	/></option>
						      	</select>
							</td>
							<td  style="vertical-align: middle">
								<div class="controlgroup">
									<input type="text" id="searchKeyword" name="searchKeyword" style="width:200px"/>
								</div>
							</td>
							<td class="active" style="vertical-align: middle; text-align:center;">
								<button type="button" class="btn btn-primary">검색</button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top;">
					<table class="table table-striped table-inverse">
					  <thead>
					    <tr>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.process.type.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.process.name.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.process.code.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.process.duration.label"	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.process.start.label" 	/>				</font></th>
					    </tr>
					  </thead>
					  <tbody id="contentTbody">
						<div id="loadingDiv" style="text-align: center; width:100%; display: none;">
							<img id="loadingImage" src="${pageContext.request.contextPath}/resources/images/flowchart/images/loading_animation.gif" />
						</div>
					  </tbody>
					</table>
				</td>
			</tr>
			<tr><td style="text-align: right; padding: 10px;">
				<span class="label label-default">totalCount : <span id="totalCountSpan"></span>
			</td></tr>
			<tr><td>
				<form:form name="pagerForm" id="pagerForm" command="command" target="pagerFrame" action="" method="POST">
					<form:input type="hidden" path="pageNo" name="pageNo" id="pageNo" />
					<form:input type="hidden" path="totalCount" name="totalCount" id="totalCount" />
					<form:input type="hidden" path="pagerSize" name="pagerSize" id="pagerSize" />
					<form:input type="hidden" path="pageSize" name="pageSize" id="pageSize" />
					<form:input type="hidden" path="callbackFunc" name="callbackFunc" id="callbackFunc" value="fn_getMyProcessList" />
				</form:form>
				<iframe name="pagerFrame" id="pagerFrame" frameborder="0" scrolling="no" style="height: auto; width: 100%"></iframe>
<!-- 				<iframe name="pagerFrame" id="pagerFrame" frameborder="0" scrolling="no" style="height: 28px; width: 100%"></iframe> -->
			</td></tr>
		</table>
	</div>
	<!-- /.container -->
</body>
</html>