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
	
	$(document).ready(function() {
		$("#folderName").selectmenu({
			width: "auto"
		});
		$("#searchOption").selectmenu({
			width: "auto"
		});
		
		fn_getMyWorkGrouplist();
		
	});
	
	
	var fn_getMyWorkGrouplist = function() {
		$.ajax({
			type : "GET",
			url : "<c:url value='/worklist/kriss/list/mywork/group' />",
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
			    	//title
			    	var titleChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var titleFont = $("<font></font>").attr("size","2").html(data[i].title);
			    	titleFont.addClass("list_font");
			    	titleChild.append(titleFont);
			    	tr.append(titleChild);
			    	//instanceKeyword
			    	var instanceKeywordChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var instanceKeywordFont = $("<font></font>").attr("size","2").html(data[i].instanceKeyword);
			    	instanceKeywordFont.addClass("list_font");
			    	instanceKeywordChild.append(instanceKeywordFont);
			    	tr.append(instanceKeywordChild);
			    	//groupCount
			    	var groupCountChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var groupCountFont = $("<font></font>").attr("size","2").html(data[i].groupCount);
			    	groupCountFont.addClass("list_font");
			    	groupCountChild.append(groupCountFont);
			    	tr.append(groupCountChild);
			    	//list
			    	var listChild = $("<td></td>").attr("style","vertical-align: middle; padding:2px;");
			    	var listFont = $("<font></font>").attr("size","2").html("목록보기");
			    	listFont.addClass("list_font");
			    	listChild.append(listFont);
			    	tr.append(listChild);
			    	
			    	contentTbody.append(tr);
			    	
			    	if ( i == 0){
			    		$("#totalCountSpan").html(data[i].totalCount);
			    		$("#groupTotalSpan").html(data.length);
			    	}
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
                        <font color="#6E98CF">	<spring:message code="old.menu.my.work.group.label" 		/>
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
									<option value="title"><spring:message code="menu.activity.title.label" 	/></option>
									<option value="instanceKeyword"><spring:message code="menu.instance.keyword.label" 	/></option>
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
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="old.worklist.work.type.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="process.name.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="menu.activity.title.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="menu.instance.keyword.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="menu.count.label" 	/>				</font></th>
					      <th style="background-color: #454545; height: 20px"><font size="2" color="#ffffff"><spring:message code="menu.worklist.label" 	/>				</font></th>
					    </tr>
					  </thead>
						<div id="loadingDiv" style="text-align: center; width:100%; display: none;">
							<img id="loadingImage" src="${pageContext.request.contextPath}/resources/images/flowchart/images/loading_animation.gif" />
						</div>
					  <tbody id="contentTbody">
					  </tbody>
					</table>
				</td>
			</tr>
			<tr><td style="text-align: right; padding: 10px;">
				<span class="label label-default">groupCount : <span id="groupTotalSpan">0</span>&nbsp;
				<span class="label label-default">totalCount : <span id="totalCountSpan">0</span>
			</td></tr>
		</table>
	</div>
	<!-- /.container -->
</body>
</html>