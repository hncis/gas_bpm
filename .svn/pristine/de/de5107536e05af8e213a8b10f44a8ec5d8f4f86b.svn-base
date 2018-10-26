function fnSetDocumentReady(){
	$(".sub_title").hide();
	getCommonCode("cro_purp_cd:XBV01:S;", "N", "init()"); 
}

function init(){
	doSearch();
}

function doSearch(msgFlag){
	var keyData = {
		if_id 		: $("#if_id").val(),
		corp_cd		: sess_corp_cd,
		locale		: sess_locale
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoBvToRequestForApprove.do", paramData, "loadCallBack(jsonData.sendResult,'"+msgFlag+"');");
}

/**
 * callback
 */
function loadCallBack(result,msgFlag){
	loadJsonSet(result);
	
	fnSubmitInfoSettings($("#pgs_st_cd").val(), $("#if_id").val(), result.code, "snb_rson_sbc", "1");
	
	setInsaInfo();
}

function setInsaInfo(){
	if($("#eeno").val() != ""){
		var keyData = { 
				xusr_empno : $("#eeno").val(),
				corp_cd		: sess_corp_cd
		};
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
}

function insaCallBack(result){
	
//	setBottomMsg(result.message, false);
	$("#eeno").val(result.xusr_empno);
	$("#keyEenm").val(result.xusr_name);
	$("#keyOpsCd").val(result.xusr_dept_code);
	$("#keyOpsNm").val(result.xusr_dept_name);
	$("#odu_regn_cd").val(result.xusr_plac_work);
	$("#keyTelNo").val(result.xusr_tel_no);
	$("#keyPosition").val(result.xusr_step_name);
}


