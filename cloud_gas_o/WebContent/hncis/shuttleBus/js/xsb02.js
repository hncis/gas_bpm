var eenoChangeFlag = false;

function fnSetDocumentReady(){
	initMenus();
	
	if($("#hid_eeno").val() != ""){
		$("#eeno").val($("#hid_eeno").val());
		$("#eeno_temp").val($("#hid_eeno").val());
		$("#apply_date").val($("#hid_apply_date").val());
	}else{
		$("#eeno").val($("#sses_eeno").val());
		$("#req_yn").val("Y");
	}
	
	sess_auth = $("#work_auth").val();
	setInsaInfo("N");
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

function setInsaInfo(gubn){
	if($("#eeno").val() != ""){
		$("#nSource").val($("#eeno").val());
		if(saveCode == $("#nSource").val()){ return; }
		saveCode = $("#nSource").val();
		
		eenoChangeFlag = gubn == "Y" ? true : false;
		
		var keyData = { xusr_empno : $("#eeno").val() };
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
	
	getCommonCode("type:X3008:Z;workShift:X3031:S;r_workShift_new:X3031:S;", "N", "comboCallBack();");		//목적
}

function comboCallBack(){
	getCommonCode("r_workShift:X3010:S;", "N", "comboCallBack2();", "doSearchWorkShiftComboList.do");
}

function comboCallBack2(){
	changeType("1");
	
	if(eenoChangeFlag){
		doSearchDocNo();
	}else{
		doSearch();
	}
}

function retrieve(gubun){
	switch(gubun){
		case "save" :
			doSearchShuttleBusRequestCheck();
			break;
		case "back" :
		    doBack();
		    break;
	}
}

function doSearchDocNo(){
	var keyData = {
		eeno : $("#eeno").val()
	};
	
	var paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusDocNo.do", paramData, "searchDocNoCallBack(jsonData.sendResult);");
}

function searchDocNoCallBack(result){
	if(result.doc_no == ""){
		alertUI("There is no Data of Saved.");
		doNew();
	}else{
		$("#hid_doc_no").val(result.doc_no);
		$("#hid_eeno")  .val($("#eeno").val());
		doSearch();
	}
}

function doSearch(){
	var keyData = {
		doc_no	 	 : $("#hid_doc_no").val(),
		eeno	     : $("#hid_eeno").val()
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
//	$("#workShift").val(result.work_shift);	
	$("#workShift").val($.trim(result.work_shift));	

	$("#type").val($("#req_type").val()-1);
	changeType(parseInt($("#req_type").val()-1));
	
	$("#fileTemp").val(result.org_fil_nm);
	
//	if($("#pgs_st_cd").val() != "0" && $("#if_id").val() != ""){
//		displaySubmit(document ,result.code, 1);	
//	}else{
//		displaySubmitClear(document);
//	}

	doBeforeSearch();
}

function doBeforeSearch(){
	var doc_no = $("#bef_doc_no").val();
//	if(doc_no == ""){
//		doc_no = $("#doc_no").val();
//	}

	var keyData = {
		doc_no	 	 : doc_no,
		eeno	     : $("#hid_eeno").val()
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusBeforeView.do", paramData, "searchCallBack2(jsonData.sendResult)");
}

function searchCallBack2(result){
	loadJsonSet(result);
	
	if($.trim(result.r_workShift).length == 1){
		$("#r_workShift_new").val($.trim(result.r_workShift));
		$("#r_workShift_new").attr("style", "inline-display:block; width:300px");
		$("#r_workShift").attr("style", "display:none");
	}else{
		$("#r_workShift").val($.trim(result.r_workShift));
		$("#r_workShift").attr("style", "inline-display:block; width:300px");
		$("#r_workShift_new").attr("style", "display:none");
	}
}

function changeType(value){
	if(value == "0"){
		value = "1";
	}
	
	for(var i=1; i<8; i++){
		$("#row"+i).attr("style", "display:none");
	}
	
	if(value == "1"){
		$("#row6").attr("style", "inline-display:block");
		$("#row7").attr("style", "inline-display:block");
	}else if(value == "2"){
		$("#row2").attr("style", "inline-display:block");
		$("#row3").attr("style", "inline-display:block");
		$("#row4").attr("style", "inline-display:block");
		$("#row5").attr("style", "inline-display:block");
		$("#row7").attr("style", "inline-display:block");
	}else if(value == "3"){
		$("#row7").attr("style", "inline-display:block");
	}else if(value == "4"){
		$("#row7").attr("style", "inline-display:block");
	}
}

function doSearchShuttleBusRequestCheck(){
	if(!($("#final_yn").val() == "Y" || $("#final_yn").val() == "S")){
		return;
	}
	
//	if(!(sess_mstu == "M" || sess_auth == 5 || $("#ipe_eeno").val() == sess_empno)){
	if(!(sess_mstu == "M" || sess_auth == 5 || $("#keyOpsCd").val() == sess_dept_code)){
		return;
	}
	
	var keyData = {
		eeno		: $("#eeno").val(),
		final_yn	: "S"
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusRequestCheck.do", paramData, "doMovePage(jsonData.sendResult)");
	
}

function doMovePage(result){
	if(result.register == "NO"){
		alertUI("Can not Change.");
		return;
	}
	if(result.doc_no == null || result.doc_no == "" || result.doc_no == $("#doc_no").val()){
		var hfrm = document.hideForm;
		$("#hid_eeno").val($("#eeno").val());
		if(result.doc_no == null || result.doc_no == ""){
			$("#hid_final_yn").val("Y");
			$("#hid_bef_doc_no").val($("#doc_no").val());
		}else{
			$("#hid_final_yn").val("S");
			$("#hid_doc_no").val($("#doc_no").val());
		}
		$("#hid_csrfToken").val($("#csrfToken").val());
		hfrm.method = "post";
		hfrm.action = "xsb06.gas";
		hfrm.submit();	
	}else{
		alertUI("Data is already request");
	}
}

function doFileDown(){
	var fileInfo = {
		doc_no    		: $("#doc_no").val(),
		eeno    		: $("#eeno").val()
	};
	
	var frm = document.frm;
	frm.fileInfo.value = util.jsonToString(fileInfo);
	frm.action = "doShuttleBusFileDown.do";
	frm.submit();
}

function doBack(){
    var form = $("<form/>");
    form.attr("method" , "post");
    form.attr("id"     , "submitForm").attr("name", "submitForm");
    form.attr("action" , $("#hid_view_nm").val() + ".gas");
    var inp1 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val($("#hid_cond").val());
    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
    form.append(inp1, token);
    $("body").append(form);
    form.submit();
    form.remove();
}

function doNew(){
	initMenus();

	if($("#hid_eeno").val() != ""){
		$("#eeno").val($("#hid_eeno").val());
		$("#eeno_temp").val($("#hid_eeno").val());
		$("#apply_date").val($("#hid_apply_date").val());
	}else{
		$("#eeno").val($("#sses_eeno").val());
		$("#req_yn").val("Y");
	}
	
	sess_auth = $("#work_auth").val();
	setInsaInfo("N");
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xsb04_file.gas", width = "460", height = "453";
	
	$("#file_doc_no").val($("#doc_no").val());
	$("#hid_use_yn").val("N");
	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}