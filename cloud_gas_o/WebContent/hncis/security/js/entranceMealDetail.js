jQuery(document).ready(function(){
	initMenus();

	if($("#hid_eeno").val() != ""){
		$("#eeno").val($("#hid_eeno").val());
		$("#eeno_temp").val($("#hid_eeno").val());
		$("#apply_date").val($("#hid_apply_date").val());
	}else{
		$("#eeno").val($("#sses_eeno").val());
		$("#req_yn").val("Y");
	}
	
	if($("#doc_no").val() == ""){
		$("#hid_doc_no").val($("#temp_doc_no").val());
	}
	
	if($("#hid_doc_no").val() != ""){
		$("#doc_no").val($("#hid_doc_no").val());
	}
	
	$(".inputOnlyNumber").numeric();
	
	sess_auth = $("#work_auth").val();
	
	setInsaInfo();
});

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
	
//	init();
	getCommonCode("type:X3001:Z;", "N", "init()");		//목적
}


/**
 * process init() loading
 */
function init(){
	initAfterMenus();
	
	readOnlyStyle("eeno", 1);
	
	if($("#hid_eeno").val() != ""){
		$("#type").val($("#hid_type").val());
		chagneType($("#hid_type").val());
	}else{
		if($("#hid_param1").val() == ""){
			$("#type").val(5);
			chagneType("5");
		}else{
			$("#type").val($("#hid_param1").val());
			chagneType($("#hid_param1").val());
		}
	}
}

function retrieve(gubun){
	switch(gubun){
		case "back" :
		    doBack();
		    break;
		case "done" :
			doDone();
			break;
		case "doneCancel" :
			doDoneCancel();
			break;
	}
}

function processValidation(gubun){
	var pgs_st_cd = $("#pgs_st_cd").val();
	var cancel_yn = $("#cancel_yn").val();
	var flag      = false;
	
	if(pgs_st_cd == ""){
		if(gubun == "save"){
			flag  = true;
		}else{
			alertUI("Please save before the next step");
		}
	}else if(gubun == "save" ){
		if( sess_mstu == "M" || sess_auth == 5 ){
			flag = true;
		}else if($("#eeno").val() == sess_empno && pgs_st_cd == "0"){
			flag = true;
		}else if($("#eeno").val() != sess_empno){
			alertUI("You can't save.");
		}else if(pgs_st_cd != "0"){
			alertUI("You can't "+gubun+" in this status");
		}
	}else if((sess_mstu == "M" || sess_auth == 5) && gubun == "edit"){
		flag = true;
	}else if(pgs_st_cd == "0"){
		if(gubun != "save"){
			if(gubun == "request" || gubun == "edit" || gubun == "delete" || gubun == "travelerDelete" || gubun == "scheduleDelete" || gubun == "forceConfirm" ){
				flag = true;
			}else{
				alertUI("Please request before the next step.");
			}
		}else{
			alertUI("Data is already stored.");
		}
	}else if(pgs_st_cd == "A"){
		if( sess_mstu == "M" || sess_auth == 5 ){
			if(gubun == "delete"){
				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
			}else{
				flag = true;
			}
		}else{
			if(gubun == "cancel"){
				flag = true;
			}else if(cancel_yn == "Y"){
				if(gubun == "requestCancel"){
					flag = true;
				}else{
					alertUI("You can't cancel the request in this status.");
				}
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "Z"){
		if( sess_mstu == "M" || sess_auth == 5 ){
			if(gubun == "delete"){
				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
			}else{
				flag = true;
			}
		}else{
			if(gubun == "confirm" || gubun == "reject" || gubun == "confirmCancel" || gubun == "cancel"){
				flag = true;
				if(gubun == "confirm"){
					if((sess_mstu == "M" || sess_auth == 5)){
						flag = true;
					}else if((sess_mstu == "M" || sess_auth == 6)){
						flag = true;
					}else{
						flag = false;
						alertUI("You can't comfirm.");
					}
				}
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "3"){
		if( sess_mstu == "M" || sess_auth == 5 ){
			if(gubun == "delete"){
				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
			}else{
				flag = true;
			}
		}else{
			if(gubun == "confirmCancel" || gubun == "cancel"){
				flag = true;
			}else if(gubun == "requestCancel" && $("#type").val() == "5" && $("#eeno").val() == sess_empno){
				flag = true;
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "C"){
		if( sess_mstu == "M" || sess_auth == 5 ){
			if(gubun == "delete"){
				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
			}else{
				flag = true;
			}
		}else{
			alertUI("You can't "+gubun+" in this status.");
		}
	}
	return flag;
}

function chagneType(value){
	SubmitClear();
	var src = "";
	if(value == "1"){
		src = ctxPath + "/hncis/security/entranceMeal_vehicle_entrance.gas";
	}else if(value == "2"){
		src = ctxPath + "/hncis/security/entranceMeal_material_leave.gas";
	}else if(value == "3"){
		src = ctxPath + "/hncis/security/entranceMeal_it_devices.gas";
	}else if(value == "4"){
		src = ctxPath + "/hncis/security/entranceMeal_film.gas";
	}else if(value == "5"){
		src = ctxPath + "/hncis/security/entranceMeal_entrance.gas";
	}
	
	if(value == "5"){
		$("#confirm").attr("style","display:none;");
		$("#reject").attr("style","display:none;");
	}else{
		$("#confirm").attr("style","");
		$("#reject").attr("style","");
	}
	$("#ifra").attr("src", src);
}

function doSearchRequestInfo(ifId){
	var params = {
		if_id : ifId,
		type  : $("#type").val()
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};
	
	doCommonAjax("doSearchToSecurityRequest.do", paramData,"loadCallBack1(jsonData.sendResult);");
}

function loadCallBack1(result){
}

function SubmitClear(){
}

function sbcReadonlySet(){
	if($("#pgs_st_cd").val() == "Z" || $("#pgs_st_cd").val() == "3"){
		readOnlyStyle("snb_rson_sbc", 1);
	}else{
		readOnlyStyle("snb_rson_sbc", 1);
	}
}

function doBack(){
    var form = $("<form/>");
    form.attr("method" , "post");
    form.attr("id"     , "submitForm").attr("name", "submitForm");
    form.attr("action" , "entranceMealMgmt.gas");
    var inp1 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val($("#hid_cond").val());
    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
    form.append(inp1, token);
    $("body").append(form);
    form.submit();
    form.remove();
}

function doNew(){
	var doc_no = getTmpDocNo();
	$("#hid_doc_no").val(doc_no);
	$("#doc_no").val(doc_no);
	$("#temp_doc_no").val(doc_no);
	
	chagneType($("#type").val());
	$("#reason").val("");
	$("#remark").html("");
}

function doDone(){
	
	if($("#type").val() != "5" && $("#pgs_st_cd").val() != "3"){
		alertUI("You can't Done.");
		return;
	}
	
	var keyData = {
		doc_no			 : $("#doc_no").val(),
		eeno			 : $("#eeno").val(),
		pgs_st_cd		 : 'D'
	};
	
	confirmUI("Do you want to done?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(keyData)
					};
					doCommonAjax("doDoneSecurityRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);ifra.doSearch('N');");
			}
		});
	});
}

function doDoneCancel(){
	
	if($("#type").val() != "5" && $("#pgs_st_cd").val() != "D"){
		alertUI("You can't Done Cancel.");
		return;
	}
	
	var keyData = {
		doc_no			 : $("#doc_no").val(),
		eeno			 : $("#eeno").val(),
		pgs_st_cd		 : '3'
	};
	
	confirmUI("Do you want to done cancel?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(keyData)
					};
					doCommonAjax("doDoneCancelSecurityRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);ifra.doSearch('N');");
			}
		});
	});
}
