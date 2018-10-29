<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<script>
	WEB_CONTEXT_ROOT='${contextRoot}';
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
</script>

<script>

	$(document).ready(function() {        
		fn_login();
	});

	fn_login = function(){
        $("#userId").val(getParameterByName('userId')); //파라미터값 전달 받기
		$.ajax({
			type : "POST",
			url : "<c:url value='/login/loginProc.do?isSSO=true' />",
			cache : false,
			data : $("#loginForm").serialize(),
			success:function(data){
				var result =  eval("("+data+")");
				if ( result.status == 'success' ) {
					document.location.href=contextPath+"/"+result.redirectUrl; // --> /bpm/main/
				} else {
					alert('로그인 실패');
				}
			},
			error:function(data){
				alert("서버와의 통신에 실패했습니다.");
			}
		});
	}
	
</script>

<title>Go to the [uEngine-BPM 3.7] Main Page</title>
</head>
<body>
	<form:form commandName="loginVO" id="loginForm" name="loginForm">
		<input type="hidden" id="userId" name="userId" value="" />
	</form:form>

</body>
</html>