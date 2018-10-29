<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:choose>
<c:when test="${sessionScope.loggedUser != null}">
	<jsp:forward page="/main/"></jsp:forward>
</c:when>
<c:otherwise>
	<jsp:forward page="/login/loginForm.do"></jsp:forward>
</c:otherwise>
</c:choose>
