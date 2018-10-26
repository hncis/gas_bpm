<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctxPath     = request.getContextPath();
    String p_initId = request.getParameter("p_initId");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>파워텍</title>
    <script src="<%=ctxPath%>/daumeditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=ctxPath%>/daumeditor/css/popup.css" type="text/css"  charset="utf-8"/>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery/jquery-ui.custom.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery/jquery.form.01.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/calendar/moment.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/calendar/fullcalendar.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/calendar/lang-all.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/util/common.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/util/jsonUtil.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/daumeditor/js/editor_creator.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=ctxPath%>/daumeditor/js/editor_loader.js"  charset="utf-8"></script>
    <script type="text/javascript">
    var p_initId = '<%=p_initId%>';
    opener.Editor.switchEditor(p_initId); 
    </script>
</head>
<body onload="initUploader();">
    <div class="wrapper">
		<form id="tx_editor_form" method="post" enctype="multipart/form-data" action="/doFileUpload.do" accept-charset="utf-8">
		<div class="header">
			<h1>사진 첨부</h1>
		</div>	
		<div class="body">
			<dl class="alert">
			    <input type="file" name="uploadFile" id="uploadFile"  title="파일 선택" style="width:440px;font-size:1em;color:#666;border:1px solid #d5d5d5">
			</dl>
		</div>
		<div class="footer">
			<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
			<ul>
				<li class="submit"><a href="#" onclick="doSave();" title="등록" class="btnlink">등록</a> </li>
				<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
			</ul>
		</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">

	function doSave(){	
	    
		doCommonAjaxToFile("/doSaveCommonEditorFile.do", "doSaveCallback(xhr);");
	}
	
	function doSaveCallback(message){
		if(message.result != ""){	
			if (typeof(execAttach) == 'undefined') { //Virtual Function
		        return;
		    }
			
			var _mockdata = {
				'imageurl': 'http://'+jQuery(location).attr("host")+'/upload/editor/'+message.code01,
				'filename': message.code01,
				'filesize': 640,
				'imagealign': 'C'
			};
			execAttach(_mockdata);
			closeWindow();
			
		}else{
			alert(result.message);
		}
		
	}

	function initUploader(){
	    var _opener = PopupUtil.getOpener();
	    if (!_opener) {
	        alert('<fmt:message key="MSG.VAL.0041"/>');
	        return;
	    }
	    
	    var _attacher = getAttacher('image', _opener);
	    registerAction(_attacher);
	}
</script>