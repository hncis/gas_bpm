<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/kdhecmaster/include/common.html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Daum에디터 - 이미지 첨부</title> 
<script src="../../js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="../../css/popup.css" type="text/css"  charset="utf-8"/>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<form id="tx_editor_form"  id="tx_editor_form" method="post" enctype="multipart/form-data" action="/doFileUpload.do" accept-charset="utf-8">
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
// <![CDATA[
            
	function doSave(){
		
		doCommonAjaxToFile("/doSaveCommonEditorFile.do", "doSaveCallback(xhr);");
	}
	
	function doSaveCallback(result){
		if(result.code == "S"){
			alert('<fmt:message key="SAVE.0000"/>');
			
			if (typeof(execAttach) == 'undefined') { //Virtual Function
		        return;
		    }
			
			var _mockdata = {
				'imageurl': 'http://211.181.100.157/upload/editor/'+result.code1,
				'filename': 'editor_bi.gif',
				'filesize': 640,
				'imagealign': 'C',
				'originalurl': 'http://cfile284.uf.daum.net/original/116E89154AA4F4E2838948',
				'thumburl': 'http://cfile284.uf.daum.net/P150x100/116E89154AA4F4E2838948'
			};
			execAttach(_mockdata);
			closeWindow();
			
		}else{
			alert(result.message);
		}
		
	}
	
	function done() {
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		
		var _mockdata = {
			'imageurl': 'http://cfile284.uf.daum.net/image/116E89154AA4F4E2838948',
			'filename': 'editor_bi.gif',
			'filesize': 640,
			'imagealign': 'C',
			'originalurl': 'http://cfile284.uf.daum.net/original/116E89154AA4F4E2838948',
			'thumburl': 'http://cfile284.uf.daum.net/P150x100/116E89154AA4F4E2838948'
		};
		execAttach(_mockdata);
		closeWindow();
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
// ]]>
</script>