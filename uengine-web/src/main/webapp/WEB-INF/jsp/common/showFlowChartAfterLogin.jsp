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
		
/* 		$(".btn-group > .btn").click(function(){
			$(this).addClass("active").siblings().removeClass("active");							
			var ids = $(this).attr("id").split("-");						
			$("#viewType").val(ids[0]);
			$("#viewOption").val(ids[1]);
			fn_drawFlowchart();
		}); */
		
		$("#definitionChangeBtn").click(function(){
			$("#definitionForm").attr("action","<c:url value='/processmanager/processDesigner.jnlp' />");
	        $("#definitionForm").submit();
		});
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
				 	$("#isAdmin").val(result.isAdmin);
				 	if ("true" == $("#isAdmin").val()) {
			        	$("#definitionChangeTr").show();
			        }
			        $("#defId").val(getParameterByName('defId')); //파라미터값 전달 받기
			        $("#defVerId").val(getParameterByName('defVerId')); //파라미터값 전달 받기	        
					$("#viewType").val("horizontal");
					$("#viewOption").val("horizontal");
					fn_drawFlowchart();
				} else {
					alert('로그인 실패');
				}
			},
			error:function(data){
				alert("서버와의 통신에 실패했습니다.");
			}
		});
	}
	
	var fn_drawFlowchart = function(){
		$("#divFlowchartArea").hide();
		try{
			formatDrawAreas();
		} catch(e){}
		$.ajax({
	        url: "<c:url value='/chart/flowchart/definition/get/' />",
	        data: $("#definitionForm").serialize(),
	        method: "POST"
	    }).then(function(data) {
	       $("#divFlowchartArea").html(data);
	       if ( $("#viewOption").val() && $("#viewOption").val() == "vertical" )
			   $("#divFlowchartArea").show("blind",1000);
	       else
	    	   $("#divFlowchartArea").show("slide",1000);
			setTimeout("drawAll()", 2000);
		   	
	    });
	}
	
</script>

<title>Show FlowChart</title>
</head>
<body>
	<form:form commandName="loginVO" id="loginForm" name="loginForm">
		<input type="hidden" id="userId" name="userId" value="" />
	</form:form>
	
	<form name="definitionForm" id="definitionForm" target="flowchartFrame" action="" method="GET">
		<input type="hidden" id="defName" name="defName" value="" />
		<input type="hidden" id="instanceId" name="instanceId" value="" />
		<input type="hidden" id="defId" name="defId" value="" />
		<input type="hidden" id="defVerId" name="defVerId" value="" />
		<input type="hidden" id="parentPdver" name="parentPdver" value="" />
		<input type="hidden" id="viewType" name="viewType" value="" />
		<input type="hidden" id="viewOption" name="viewOption" value="" />
		<input type="hidden" id="lastInstanceId" name="lastInstanceId" value="" />
		<input type="hidden" id="initiate" name="initiate" value="" />
		<input type="hidden" id="viewInstanceId" name="viewInstanceId" value="" />
		<input type="hidden" id="isAdmin" name="isAdmin" value="" />
	</form>
	
	<!-- Page Content -->
	<table>
		<tr id="definitionChangeTr" style="display: none;">
			<td>
				<button id="definitionChangeBtn" type="button" class="btn btn-info">
					프로세스 변경
				</button>
			</td>
		</tr>
		<tr>
			<td>
				<%-- 
				<div class="btn-group" data-toggle="buttons">
					<button type="button" id="swimlane-horizontal" class="btn btn-default active"><spring:message code="flowchart.type.swimlane.horizontal.label" /></button>
					<button type="button" id="swimlane-vertical" class="btn btn-default"><spring:message code="flowchart.type.swimlane.vertical.label" /></button>
					<button type="button" id="horizontal-horizontal" class="btn btn-default"><spring:message code="flowchart.type.horizontal.label" /></button>
					<button type="button" id="vertical-vertical" class="btn btn-default"><spring:message code="flowchart.type.vertical.label" /></button>
				</div>
				 --%>
				<div id="divFlowchartArea" style="display: none; vertical-align: middle; text-align: center; "></div>
			</td>
		</tr>
	</table>	
</body>
</html>