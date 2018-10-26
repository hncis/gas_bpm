function fnSetDocumentReady(){
	initMenus();
	
	$(".sub_title").hide();
	
	if($("#hid_eeno").val() != ""){
		$("#eeno").val($("#hid_eeno").val());
		$("#eeno_temp").val($("#hid_eeno").val());
		$("#apply_date").val($("#hid_apply_date").val());
	}else{
		$("#eeno").val($("#sses_eeno").val());
		$("#req_yn").val("Y");
	}
	
	sess_auth = $("#work_auth").val();
	
	getCommonCode("type:X3008:Z;", "N", "comboCallBack();");		//목적
}

function comboCallBack(){
	getCommonCode("workShift:X3010:S;r_workShift:X3010:S;", "N", "comboCallBack2();", "doSearchWorkShiftComboList.do");
}

function comboCallBack2(){
	changeType("1");
	
	doSearch();
}

var saveCode = "";
function cearInsa(){
	if($("#eeno").val() == ""){
		saveCode = "";
		$("#nSource").val("");
		$("#eeno").val("");
		$("#keyEenm").val("");
		$("#keyOpsCd").val("");
		$("#keyOpsNm").val("");
	}
}

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
	$("#eeno").val(result.xusr_empno);
	$("#keyEenm").val(result.xusr_name);
	$("#keyOpsCd").val(result.xusr_dept_code);
	$("#keyOpsNm").val(result.xusr_dept_name);
	$("#odu_regn_cd").val(result.xusr_plac_work);
	$("#keyTelNo").val(result.xusr_tel_no);
	$("#keyPosition").val(result.xusr_step_name);
	
//	getCommonCode("type:X3008:Z;", "N", "comboCallBack();");		//목적
}

function retrieve(gubun){
	switch(gubun){
		case "change" :
//			ifra.doCudAction(gubun);
			break;
	}
}

function doSearch(){
	var keyData = {
		if_id 	: $("#if_id").val(),
		corp_cd	: sess_corp_cd
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusRequstNew.do", paramData, "searchCallBack(jsonData.sendResult)");
}

function searchCallBack(result){
	loadJsonSet(result);
	
	$("#apply_date").val(result.ptt_ymd);
	$("#busStop").val(result.bus_stop);
	$("#zipCode").val(result.zip_code);	
	$("#workShift").val($.trim(result.work_shift));
	
	displaySubmit(document ,result.code, 1);

	$("#fileTemp").val(result.org_fil_nm);
	
	doBeforeSearch();
}

function doBeforeSearch(){
	var doc_no = $("#bef_doc_no").val();
//	if(doc_no == ""){
//		doc_no = $("#doc_no").val();
//	}

	var keyData = {
		doc_no	 	 : doc_no,
		eeno	     : $("#eeno").val(),
		corp_cd		 : sess_corp_cd
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusBeforeView.do", paramData, "searchCallBack2(jsonData.sendResult)");
}

function searchCallBack2(result){
	loadJsonSet(result);

	$("#r_workShift").val($.trim(result.r_workShift));
	
	doSapInfoSearch();
}

function doSapInfoSearch(){
	var keyData = {
		key_eeno	: $("#eeno").val(),
		corp_cd		: sess_corp_cd
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusSapInformationView.do", paramData, "searchCallBack3(jsonData.sendResult)");
}

function searchCallBack3(result){
	loadJsonSet(result);

	setInsaInfo();
}

function changeType(value){
	for(var i=1; i<8; i++){
		$("#row"+i).attr("style", "display:none");
	}
	
	if(value == "1"){
		$("#row6").attr("style", "");
	}else if(value == "2"){
		$("#row2").attr("style", "");
		$("#row3").attr("style", "");
		$("#row4").attr("style", "");
		$("#row5").attr("style", "");
		$("#row7").attr("style", "");
	}else if(value == "3"){
		$("#row7").attr("style", "");
	}else if(value == "4"){
	}
}

function doFileDown(){
	var fileInfo = {
		doc_no    		: $("#doc_no").val(),
		eeno    		: $("#eeno").val(),
		corp_cd			: sess_corp_cd
	};
	
	var frm = document.frm;
	frm.fileInfo.value = util.jsonToString(fileInfo);
	frm.action = "doShuttleBusFileDown.do";
	frm.submit();
}
