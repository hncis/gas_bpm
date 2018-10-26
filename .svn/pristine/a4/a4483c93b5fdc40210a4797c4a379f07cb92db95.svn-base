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
	setInsaInfo();
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
	
	getCommonCode("type:X3008:Z;workShift:X3031:S;r_workShift_new:X3031:S;", "N", "comboCallBack();");
}

function comboCallBack(){
	getCommonCode("r_workShift:X3010:S;", "N", "comboCallBack2();", "doSearchWorkShiftComboList.do");
}

function comboCallBack2(){
	changeType("1");
	
	doSearch();
}

function retrieve(gubun){
	switch(gubun){
		case "confirm" :
			doConfirm();
			break;
		case "confirmCancel" :
			doSearchShuttleBusRequestCheck();
			break;
		case "reject" :
			doReject();
			break;
		case "back" :
		    doBack();
		    break;
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
	$("#workShift").val($.trim(result.work_shift));
	
	$("#type").val($("#req_type").val()-1);
	changeType(parseInt($("#req_type").val()-1));

	$("#fileTemp").val(result.org_fil_nm);
	$("#fil_nm").val(result.fil_nm);
	
	if(parseInt($("#req_type").val()-1) == 2){
		$("#btn_mail").attr("style", "inline-display:block");
	}
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

function doConfirm(){
	if($("#pgs_st_cd").val() != "Z"){
		alertUI("You can't comfirm.");
		return;
	}
	
	if($("#remark_answer").val() == ""){
		alertUI("Please enter the Remarks answer.");
		return;
	}
	
	var bsicInfo = {
		key_mode      : "confirm",
		eeno	      : $("#eeno").val(),
		doc_no		  : $("#doc_no").val(),
		ptt_ymd		  : $("#apply_date").val(),
		bef_doc_no	  : $("#bef_doc_no").val(),
		bef_yn		  : "N",
		key_pgs_st_cd : "3",
		final_yn	  : "Y",
		req_type	  : "",
		remark_answer : overLineHtml(changeToUni($("#remark_answer").val())),
		updr_eeno     : sess_empno
	};
	
	confirmUI("확정 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmShuttleBus.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doConfirmCancel(){
	if($("#rq_imtr_sbc").val() == ""){
		alertUI("Please enter the reason for confirm cancel in Note.");
		return;
	}
	
	var bsicInfo = {
		key_mode      : "confirmCancel",
		eeno	      : $("#eeno").val(),
		doc_no		  : $("#doc_no").val(),
		ptt_ymd		  : $("#apply_date").val(),
		bef_doc_no	  : $("#bef_doc_no").val(),
		bef_yn		  : "Y",
		key_pgs_st_cd : "0",
		final_yn	  : "S",
		req_type	  : "",
		snb_rson_sbc  : $("#snb_rson_sbc").val(),
		remark_answer : overLineHtml(changeToUni($("#remark_answer").val())),
		updr_eeno     : sess_empno
	};
	
	confirmUI("신청 취소사유를 입력하세요.\n신청 취소하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmCancelShuttleBus.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function confirmCallBack(result){
	setBottomMsg(result.message, true);
	
	doSearch("N");
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

function doSearchShuttleBusRequestCheck(){
	var keyData = {
		eeno		: $("#eeno").val(),
		final_yn	: "S",
		doc_no		: $("#doc_no").val()
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusRequestCheck.do", paramData, "doMovePage(jsonData.sendResult)");
}

function doMovePage(result){
	if(result == null || typeof(result.doc_no) == "undefined"){
		doConfirmCancel();
	}else{
		alertUI("You can't Comfirm Cancel");
	}
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

function doReject(){
	if(!($("#pgs_st_cd").val() == 'Z' || $("#pgs_st_cd").val() == '3')){
		alertUI("You can't reject in this status.");
		return;
	}
	
//	if($("#snb_rson_sbc").val() == ""){
//		alertUI("Please enter the reason for confirm cancel in Note.");
//		return;
//	}
	
	if($("#work_auth").val() == '5' || sess_mstu == "M"){
	
	}else if($("#work_auth").val() == '6'){
		if(!($("#pgs_st_cd").val() == 'Z' || $("#pgs_st_cd").val() == '3')){
			alertUI("You can't reject in this status."); 
			return;
		}
	}else{
		alertUI("반려할 수 없습니다.");
		return;
	}
	
	if($("#snb_rson_sbc").val() == ""){
		alertUI("Please enter the reason for reject.");
		$("#snb_rson_sbc").focus();
		return;
	}
	
	var keyData = {
		doc_no			 : $("#doc_no").val(),
		eeno			 : $("#eeno").val(),
		pgs_st_cd		 : '2',
		final_yn		 : "N",
		snb_rson_sbc  	 : changeToUni($("#snb_rson_sbc").val()),
		updr_eeno		 : sess_empno
	};
	
	confirmUI("반려 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
				};
				doCommonAjax("doRejectShuttleBusRequstNew.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}

function sendEmail(){
	var params = {
		doc_no : $("#doc_no").val(),
		affr_scn_cd : "SB",
		eeno   : $("#eeno").val()
	};
	
	var file = [];
	
	if( $("#fil_nm").val() != ""){
		data = {
			affr_scn_cd			: "shuttleBus",
			ogc_fil_nm			: $("#fil_nm").val(),
			fil_nm				: $("#fileTemp").val()
		};
		file.push(data);
	}
	
	confirmUI("Do you want to SendEmail?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(params),
						fileList	: util.jsonToList(file)
					};
					doCommonAjax("doSearchShuttleBusSendMail.do", paramData, "sendMailCallBack(jsonData.sendResult);");
			}
		});
	});
}

function sendMailCallBack(result){
	alertUI(result.message);
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xsb04_file.gas", width = "460", height = "453";
	
	$("#file_doc_no").val($("#doc_no").val());
	$("#hid_use_yn").val("Y");
	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}