function fnSetDocumentReady(){
//	$(".sub_title").hide();
	init();
}

/**
 * process init() loading
 */
function init(){
	getCommonCode("type:X3001:Z;", "N", "initSearch()");
}

function initSearch(){
	var keyData = { 
			if_id : $("#if_id").val(),
			corp_cd	: sess_corp_cd
	};
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	doCommonAjax("doSearchSecurtyRequestType.do", paramData, "initCallBack(jsonData.sendResult)");
}

function initCallBack(result){
//	displaySubmit(document ,result.code, 1);
	chagneType(result.type);
}

function chagneType(value){
	$("#type").val(value);
	var src = "";
	if(value == "1"){
		src = ctxPath + "/hncis/security/xve01_vehicle_entrance_submit.gas?csrfToken=" + $("#csrfToken").val();
	}else if(value == "2"){
		src = ctxPath + "/hncis/security/xve01_material_leave_submit.gas?csrfToken=" + $("#csrfToken").val();
	}else if(value == "3"){
		src = ctxPath + "/hncis/security/xve01_it_devices_submit.gas?csrfToken=" + $("#csrfToken").val();
	}else if(value == "4"){
		src = ctxPath + "/hncis/security/xve01_film_submit.gas?csrfToken=" + $("#csrfToken").val();
	}else if(value == "5"){
		src = ctxPath + "/hncis/security/xve01_entrance_submit.gas?csrfToken=" + $("#csrfToken").val();
	}
	
	$("#ifra").attr("src", src);
}

function doSearchRequestInfo(ifId){
	var params = {
		if_id : ifId,
		type  : $("#type").val(),
		corp_cd	: sess_corp_cd
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};
	
	doCommonAjax("doSearchToSecurityRequest.do", paramData,"loadCallBack1(jsonData.sendResult);");
}

function loadCallBack1(result){
	fnSubmitInfoSettings($("#pgs_st_cd").val(), $("#if_id").val(), result.code, "snb_rson_sbc", $("#approveStepLevel").val());
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
