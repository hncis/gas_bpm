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

	fn_login = function() {
        $("#userId").val(getParameterByName('userId')); //파라미터값 전달 받기
		$.ajax({
			type : "POST",
			url : "<c:url value='/login/loginProc.do?isSSO=true' />",
			cache : false,
			data : $("#loginForm").serialize(),
			success:function(data) {
				var result =  eval("("+data+")");
				if (result.status == 'success') {
			        $("#instanceId").val(getParameterByName('instanceId')); //파라미터값 전달 받기
					$("#viewType").val("vertical");
					$("#viewOption").val("vertical");
					fn_drawFlowchart();
				} else {
					alert("로그인 실패");
				}
			},
			error:function(data) {
				alert("서버와의 통신에 실패했습니다.");
			}
		});
	}
	
	var fn_drawFlowchart = function() {
		try {
			window.onresize = drawAll;
			formatDrawAreas();
		} catch(e) {
			alert(e);
		}
		$.ajax({
	        url: "<c:url value='/chart/flowchart/instance/get' />",
	        data: JSON.stringify($("#flowchartForm").serializeArray()),
	        contentType : "application/json;charset=utf-8;",
	        dataType : "json",
	        method: "POST"
	    }).then(function(data) {
			$("#divFlowchartArea").html(data.chartString);
			setTimeout("drawAll()",1000);
	    });
	}
</script>

<title>Process Manager</title>
</head>
<body>
	<form:form commandName="loginVO" id="loginForm" name="loginForm">
		<input type="hidden" id="userId" name="userId" value="" />
	</form:form>
	<form name="flowchartForm" id="flowchartForm" target="flowchartFrame" action="" method="GET">
		<input type="hidden" id="instanceId" name="instanceId" value="" />
		<input type="hidden" id="viewType" name="viewType" value="" />
		<input type="hidden" id="viewOption" name="viewOption" value="" />
	</form>
	<div class="container-fluid">
		<div id="divFlowchartArea" style="display: block; vertical-align: middle; text-align: center;"></div>
	</div>
</body>
</html>