function fnSetDocumentReady(){
	
}

function fnSetCookies(chkFlag){
	var useDay = chkFlag ? 1 : -1;
	
	setCookie("event_pop", "use_pop", useDay);
}

function setCookie(cName, cValue, cDay){
	var expire = new Date();
	expire.setDate(expire.getDate() + cDay);
	cookies = cName + "=" + escape(cValue) + "; path=/";
	if(typeof cDay != 'undefined') cookies += ";expires=" + expire.toGMTString() + ";";
	document.cookie = cookies;
	
	selfClose();
}

var comment_win;
function fnCommentPop(){
	opener.pageLocationHref(ctxPath, '/hncis/event/comment_event.gas');
//	if(comment_win != null){ comment_win.close(); }
//	
//	var url = "", width = "1260", height = "423";
//	url = ctxPath+"/hncis/event/comment_event.gas";
//	comment_win = newPopWin("about:blank", width, height, "win_comment");
//	
//	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
//	document.hideForm.action = url;
//	document.hideForm.target = "win_comment";
//	document.hideForm.method = "post"; 
//	document.hideForm.submit();
}

var quiz_win;
function fnQuizPop(){
	opener.pageLocationHref(ctxPath, '/hncis/event/quiz_event.gas');
//	if(quiz_win != null){ quiz_win.close(); }
//	
//	var url = "", width = "1260", height = "700";
//	url = ctxPath+"/hncis/event/quiz_event.gas";
//	quiz_win = newPopWin("about:blank", width, height, "win_quiz");
//	
//	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
//	document.hideForm.action = url;
//	document.hideForm.target = "win_quiz";
//	document.hideForm.method = "post"; 
//	document.hideForm.submit();
}

function selfClose(){
	if(navigator.appVersion.indexOf("MSIE 7.0") >= 0 || navigator.appVersion.indexOf("MSIE 8.0") >= 0){
		window.open('about:blank','_self').close();
	}else{
		window.close();
	}
}