<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
</head>
<script>
	$("button").click(function(){
		alert(0);
	})
</script>
<body style="text-align: center; font-size: 10px; margin: 0px;">
	<c:if test="${command.hasFirstPage == true}">
		<button onClick="javascript:parent.${command.callbackFunc}(${command.firstPage });return false;" type="button" class="btn btn-default" style="vertical-align: top;">
			<span class="glyphicon glyphicon-backward"></span>
		</button>
	</c:if>
	<c:if test="${command.hasPrevPage == true}">
		<button onClick="javascript:parent.${command.callbackFunc}(${command.prevMaxPage });return false;" type="button" class="btn btn-default" style="vertical-align: top;">
			<span class="glyphicon glyphicon-triangle-left"></span>
		</button>
	</c:if>
	<ul class="pagination" style="margin: 0px">
		<c:forEach var="paramvalues" items="${command.pageGroup }">
			<c:if test="${paramvalues == command.pageNo}">
				<li class="active"><a href="javascript:return false;">${paramvalues }</a></li>
			</c:if >
			<c:if test="${paramvalues != command.pageNo}">
				<li><a href="javascript:parent.${command.callbackFunc}(${paramvalues });">${paramvalues }</a></li>
			</c:if >
		</c:forEach>
	</ul>
	<c:if test="${command.hasNextPage == true}">
		<button onClick="javascript:parent.${command.callbackFunc}(${command.nextMinPage });return false;" type="button" class="btn btn-default" style="vertical-align: top;">
			<span class="glyphicon glyphicon-triangle-right"></span>
		</button>
	</c:if>
	<c:if test="${command.hasLastPage == true}">
		<button type="button" onClick="javascript:parent.${command.callbackFunc}(${command.lastPage });return false;" class="btn btn-default" style="vertical-align: top;">
			<span class="glyphicon glyphicon-forward"></span>
		</button>
	</c:if>
</body>



