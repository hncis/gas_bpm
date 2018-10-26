function fnSetDocumentReady(){
//	change_info("czk");
//	$(".sub_title").hide();
	setComboInfo();
}

function setComboInfo(){
	var url        = "doComboListToRequest.do";
	var olv_knd    = "olv_cd:03:3:S;";
	var card_knd   = "bc_type:02::S;";
	var card_type  = "bc_knd:11::S;";
	var card_prt   = "bc_prt:12::S;";
	var req_disc   = "bcr_reasn:05::S;";
	var paramValue = olv_knd + card_knd + card_type + card_prt + req_disc;
	getCommonCode(paramValue, "N", "init();", url);
}

/**
 * process init() loading
 */
function init(){
	
	getCommonCode("qty:X3018:S;", "N", "doSearch('Y');");
}

function doSearch(msgFlag){
	var params = {
		if_id    : $("#if_id").val(),
		corp_cd	 : sess_corp_cd,
		locale   : sess_locale
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};
	
	doCommonAjax("doSearchToSubmit.do", paramData, "loadCallBack(jsonData.sendResult);");
}

/**
 * callback
 */
function loadCallBack(result, msgFlag){
	loadJsonSet(result);
//	ctrlStep(document.frm, result.olv_cd);
//	ctrlAddr(document.frm, result.adr_knd);
	
//	displaySubmit(document ,result.code, 1);
	
	fnSubmitInfoSettings($("#pgs_st_cd").val(), $("#if_id").val(), result.code, "snb_rson_sbc", "1");
	
	setInsaInfo();
}

var saveCode = "";
function setInsaInfo(){
	if($("#eeno").val() != ""){
		$("#nSource").val($("#eeno").val());
		if(saveCode == $("#nSource").val()){ return; }
		saveCode = $("#nSource").val();
		
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
	setBottomMsg(result.message, false);
	$("#eeno").val(result.xusr_empno);
	$("#keyEenm").val(result.xusr_name);
	$("#keyOpsCd").val(result.xusr_dept_code);
	$("#keyOpsNm").val(result.xusr_dept_name);
	$("#odu_regn_cd").val(result.xusr_plac_work);
	$("#keyTelNo").val(result.xusr_tel_no);
	$("#keyPosition").val(result.xusr_step_name);
	$("#h_email").val(result.xusr_mail_adr);
}

/**
 * techical method
 */
function change_info(type){
	document.getElementById("czk").style.display = "none"; 
	document.getElementById("kor").style.display = "none"; 
	document.getElementById("eng").style.display = "none"; 
	
	document.getElementById(type).style.display = "";
	
	document.getElementById("ttl_czk").style.background = "white";
	document.getElementById("ttl_kor").style.background = "white";
	document.getElementById("ttl_eng").style.background = "white";
	
	var tmp = eval("document.all.ttl_" + type);
	tmp.style.background = "#B6C4EB";
}

function ctrlStep(frm, code){
	if(code == "") return;
	
	frm.olv_cd.value      = code;
	frm.olv_kcd.value     = code;
	frm.olv_ecd.value     = code;
	
	if(code != ""){
		frm.olv_nm.value  = frm.olv_cd.options[frm.olv_cd.selectedIndex].text;
		frm.olv_knm.value = frm.olv_kcd.options[frm.olv_kcd.selectedIndex].text;
		frm.olv_enm.value = frm.olv_ecd.options[frm.olv_ecd.selectedIndex].text;
	}else{
		frm.olv_nm.value  = "";
		frm.olv_knm.value = "";
		frm.olv_enm.value = "";
	}
}

function ctrlAddr(frm, code){
	if(code == "") return;
	
	frm.adr_knd.value       = code;
	frm.adr_kr_knd.value    = code;
	frm.adr_en_knd.value    = code;
	
	frm.bc_adr_knd.value    = code;
	frm.bc_adr_kr_knd.value = code;
	frm.bc_adr_en_knd.value = code;
	
	try{
		frm.pbz_adr.value    = frm.bc_adr_knd.options[frm.bc_adr_knd.selectedIndex].text;
		frm.pbz_kr_adr.value = frm.bc_adr_kr_knd.options[frm.bc_adr_kr_knd.selectedIndex].text;
		frm.pbz_en_adr.value = frm.bc_adr_en_knd.options[frm.bc_adr_en_knd.selectedIndex].text;
	}catch(e){
		alertUI(e.message + " , " + e.name)
		alertUI("Detailed address does not exist.");
		frm.bc_adr_knd.value    = "";
		frm.bc_adr_kr_knd.value = "";
		frm.bc_adr_en_knd.value = "";
		frm.pbz_adr.value       = "";
		frm.pbz_kr_adr.value    = "";
		frm.pbz_en_adr.value    = "";
	}
	
//	// postNo
//	bcmForm.ZIP_CD.value = code;
//	var tmpPost = "";
//	try{
//		tmpPost = bcmForm.ZIP_CD.options[bcmForm.ZIP_CD.selectedIndex].text;
//		bcmForm.H_POST_NO.value = tmpPost.substring(0,3) + '-' + tmpPost.substring(3,6);
//		bcmForm.C_POST_NO.value = tmpPost.substring(0,3) + '-' + tmpPost.substring(3,6);
//		bcmForm.E_POST_NO.value = tmpPost.substring(0,3) + '-' + tmpPost.substring(3,6);
//	}catch(e){
//		alertUI("해당주소의 우편번호가 존재하지 않습니다.");
//		bcmForm.ZIP_CD.value    = "";
//		bcmForm.H_POST_NO.value = "";
//		bcmForm.C_POST_NO.value = "";
//		bcmForm.E_POST_NO.value = "";
//	}
}