function fnSetDocumentReady(){
	sess_auth = $("#work_auth").val();
	doSearch();
}

function doSearch(){
	var params = {   
		key_bod_indx : $("#M_INDX").val()
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};        
	doCommonAjax("../../hncis/board/doSearchDetailBDToNotice.do", paramData, "loadCallBack(jsonData.sendResult);");
}

/**
 * callBack
 */
function loadCallBack(result){

	$("#p_t").text(result.bod_title);
	$("#p_c").html(decodeURIComponent(result.bod_content));
	
}