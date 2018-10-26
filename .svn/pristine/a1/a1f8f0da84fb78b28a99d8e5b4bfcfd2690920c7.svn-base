var eenoChangeFlag = false;

function fnSetDocumentReady(){
	initMenus();
	
	if($("#work_auth").val() != 5 && sess_mstu != "M"){
		$("#eeno").val($("#sses_eeno").val());
	}
	
	sess_auth = $("#work_auth").val();
	
	getCommonCode("type:X3008:Z;workShift:X3031:S;r_workShift_new:X3031:S;", "N", "comboCallBack();");
//	setInsaInfo('N');
}


function comboCallBack(){
	getCommonCode("r_workShift:X3010:S;", "N", "setInsaInfo('N');", "doSearchWorkShiftComboList.do");
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
		$("#keyPosition").val("");
		$("#keyTelNo").val("");
	}
}

function setInsaInfo(gubn){
	changeType("1");
	
	if($("#eeno").val() != ""){
		$("#nSource").val($("#eeno").val());
		if(saveCode == $("#nSource").val()){ return; }
		doClear();
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
	
	comboCallBack2();
}

function comboCallBack2(){
	doAuthCheck();
}

function doAuthCheck(){
	if($("#work_auth").val() != 5 && sess_mstu != "M"){
		if($("#keyOpsCd").val() != sess_dept_code){
			alertUI("Not the same department.");
			$("#eeno").val("");
			cearInsa();
			return;
		}
	}
	
	var keyData = {
		eeno		: $("#eeno").val(),
		final_yn	: "S"
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusRequestCheck.do", paramData, "doSaveCheckCallBack(jsonData.sendResult)");
}

function doSaveCheckCallBack(result){
	if(result.register == "NO"){
		alertUI("Can not Request.");
		$("#eeno").val("");
		cearInsa();
		return;
	}
	
	if(result.final_yn == "S"){
		$("#doc_no").val(result.doc_no);
		doSearch(result.doc_no, $("#eeno").val());
	}else if(result.final_yn == "Y"){
		$("#bef_doc_no").val(result.doc_no);
		doBeforeSearch();
	}
}

function doSearch(doc_no, eeno){
	var keyData = {
		doc_no	 	 : doc_no,
		eeno	     : eeno
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
	$("#hid_fil_nm").val(result.fil_nm);
	$("#hid_org_fil_nm").val(result.org_fil_nm);
	$("#hid_fil_size").val(result.fil_size);
	
	$("#type").val($("#req_type").val()-1);
	changeType(parseInt($("#req_type").val()-1));
	
	$("#fileTemp").val(result.org_fil_nm);
	
	if(Number(sess_auth) > 4 || sess_mstu == "M"){
		readOnlyStyle("snb_rson_sbc", 2);
	}else{
		readOnlyStyle("snb_rson_sbc", 1);
	}

	doBeforeSearch();
}

function doBeforeSearch(){
	var doc_no = $("#bef_doc_no").val();
//	if(doc_no == ""){
//		doc_no = $("#doc_no").val();
//		$("#bef_doc_no").val($("#hid_doc_no").val());
//	}

	var keyData = {
		doc_no	 	 : doc_no,
		eeno	     : $("#eeno").val()
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSearchShuttleBusBeforeView.do", paramData, "searchCallBack2(jsonData.sendResult)");
}

function searchCallBack2(result){
	if(result.r_line == "" || result.r_busStop == ""){
		alertUI("There is no line or bus stop.");
		$("#eeno").val("");
		cearInsa();
		return;
	}
	
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
	$("#hid_workShift").val($.trim(result.r_workShift));
}

function retrieve(gubun){
	switch(gubun){
		case "save" :
			if(!processValidation(gubun))return;
			doSave();
			break;
		case "delete" :
			if(!processValidation(gubun))return;
			doDelete();
			break;
		case "request" :
			if(!processValidation(gubun))return;
			doRequest();
			break;
		case "requestCancel" :
			if(!processValidation(gubun))return;
			doRequestCancel();
			break;
		case "confirm" :
			if(!processValidation(gubun))return;
			doConfirm();
			break;
		case "confirmCancel" :
			if(!processValidation(gubun))return;
			doConfirmCancel();
			break;
		case "print" :
			doPrint();
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
		$("#doc_no").val(result.doc_no);
		$("#hid_eeno")  .val($("#eeno").val());
		doSearch("","");
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
	if(result == null || typeof(result.doc_no) == "undefined" || result.doc_no == $("#doc_no").val()){
		var hfrm = document.hideForm;
		$("#hid_eeno").val($("#eeno").val());
		$("#hid_doc_no").val($("#doc_no").val());
		$("#hid_csrfToken").val($("#csrfToken").val());
		hfrm.method = "post";
		hfrm.action = "xsb06.gas";
		hfrm.submit();	
	}
}

function doSave(){
	var eeno = $("#eeno").val();
	var line = $("#r_line").val();
	if(line == ""){
		line = $("#line").val();
	}
	var bus_stop = $("#r_busStop").val();
	if(bus_stop == ""){
		bus_stop = $("#busStop").val();
	}
	var address = $("#r_address").val();
	var numb = $("#r_numb").val();
	var complement = $("#r_complement").val();
	var district = $("#r_district").val();
	var city = $("#r_city").val();
	var zip_code = $("#r_zipCode").val();
	var work_shift = $("#hid_workShift").val();
	var org_fil_nm = $("#r_org_fil_nm").val();
	var fil_nm = $("#r_fil_nm").val();
	var fil_size = $("#r_fil_size").val();
	
	if($("#type").val() == "1"){
		work_shift = $("#workShift").val();
	}else if($("#type").val() == "2"){
		address = $("#address").val();
		numb = $("#numb").val();
		complement = $("#complement").val();
		district = $("#district").val();
		city = $("#city").val();
		zip_code = $("#zipCode").val();
		org_fil_nm = $("#hid_org_fil_nm").val();
		fil_nm = $("#hid_fil_nm").val();
		fil_size = $("#hid_fil_size").val();
	}else if($("#type").val() == "3"){
		org_fil_nm = $("#hid_org_fil_nm").val();
		fil_nm = $("#hid_fil_nm").val();
		fil_size = $("#hid_fil_size").val();
	}else if($("#type").val() == "4"){
		
	}
	
	if($("#bef_doc_no").val() == ""){
		work_shift = $("#workShift").val();
		address = $("#address").val();
		numb = $("#numb").val();
		complement = $("#complement").val();
		district = $("#district").val();
		city = $("#city").val();
		zip_code = $("#zipCode").val();
		org_fil_nm = $("#hid_org_fil_nm").val();
		fil_nm = $("#hid_fil_nm").val();
		fil_size = $("#hid_fil_size").val();
	}
	
	var docNo = $("#doc_no").val();
	if($("#req_type").val() != "1" && $("#req_type").val() != parseInt($("#type").val())+1){
		docNo = "";
	}
	
	var fileInfo = {
		eeno			: eeno,
		line 			: line,
		bus_stop   		: bus_stop,
		address			: overLineHtml(changeToUni(address)),
		numb    		: numb,
		complement    	: overLineHtml(changeToUni(complement)),
		district 	  	: overLineHtml(changeToUni(district)),
		city			: overLineHtml(changeToUni(city)),
		zip_code		: overLineHtml(changeToUni(zip_code)),
		work_shift		: overLineHtml(changeToUni(work_shift)),
		remark			: overLineHtml(changeToUni($("#remark").val())),
		doc_no			: docNo,
		org_fil_nm		: org_fil_nm,
		fil_nm			: fil_nm,
		fil_size		: fil_size,
		ptt_ymd			: $("#apply_date").val(),
		pgs_st_cd		: "0",
		bef_doc_no		: $("#bef_doc_no").val(),
		req_type		: parseInt($("#type").val())+1,
		final_yn		: "S",
		ipe_eeno    	: sess_empno,
		updr_eeno   	: sess_empno
	};
	
	confirmUI("Do you want to Save ?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var frm = document.frm;
				$("#fileInfo").val(util.jsonToString(fileInfo));
				frm.target = "ifr";
				frm.action = "doSaveShuttleBusRequstNew.do";
				frm.submit();
			}
		});
	});
}

function doDelete(){
	var params = {
			eeno			: $("#eeno").val(),
			line 			: $("#line").val(),
			bus_stop   		: $("#busStop").val(),
			address			: $("#address").val(),
			numb    		: $("#numb").val(),
			complement    	: $("#complement").val(),
			district 	  	: $("#district").val(),
			city			: $("#city").val(),
			zip_code		: $("#zipCode").val(),
			work_shift		: $("#workShift").val(),
			remark			: overLineHtml(changeToUni($("#remark").val())),
			doc_no			: $("#doc_no").val(),
			org_fil_nm		: $("#hid_org_fil_nm").val(),
			fil_nm			: $("#hid_fil_nm").val(),
			ptt_ymd			: $("#apply_date").val()
	};
	
	confirmUI("Do you want to Delete ?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(params)
					};
					doCommonAjax("doDeleteShuttleBusRequstNew.do", paramData, "doDeleteCallback(jsonData.sendResult)");
			}
		});
	});
}

function doDeleteCallback(result){
	setBottomMsg(result.message, true);
	saveCode = "";
	setInsaInfo('N');
	
//	$("#apply_date").val("");
//	$("#doc_no").val("");
//	doClear();
//	 var form = $("<form/>");
//    form.attr("method" , "post");
//    form.attr("id"     , "submitForm").attr("name", "submitForm");
//    form.attr("action" , "xsb01.gas");
//    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
//    form.append(token);
//    $("body").append(form);
//    form.submit();
//    form.remove();
}

function doRequest(){
	var params = {
		eeno			: $("#eeno").val(),
		doc_no 			: $("#doc_no").val(),
		ptt_ymd			: $("#apply_date").val(),
		final_yn		: "S",
		req_type		: "1",
		pgs_st_cd		: "Z",
		updr_eeno		: sess_empno
	};
	
	confirmUI("신청 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(params)
					};
					doCommonAjax("doRequestShuttleBusRequstNew.do", paramData, "doRequestCallBack(jsonData.sendResult)");
			}
		});
	});
}

function doRequestCallBack(result){
	setBottomMsg(result.message, true);
	doSearch($("#doc_no").val(), $("#eeno").val());
}

function doRequestCancel(){
	var params = {
		if_id     		: $("#if_id").val(),
		doc_no	 		: $("#doc_no").val(),
		eeno	  		: $("#eeno").val(),
		final_yn  		: "S",
		updr_eeno 		: sess_empno,
		pgs_st_cd		: "0",
		updr_eeno		: sess_empno
	};
	
	confirmUI("신청취소 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(params)
					};
					doCommonAjax("doRequestShuttleBusNewRequstCancel.do", paramData, "doRequestCallBack(jsonData.sendResult)");
			}
		});
	});
}

function doConfirm(){
	if($("#pgs_st_cd").val() != "Z"){
		alertUI("You can't comfirm.");
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
	
	doSearch("", "");
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
//		}else if($("#ipe_eeno").val() != sess_empno){
//			alertUI("You can't save.");
		}else if(pgs_st_cd != "0"){
			alertUI("You can't "+gubun+" in this status");
		}else if($("#eeno").val() == sess_empno && pgs_st_cd == "0"){
			flag = true;
		}else if($("#keyOpsCd").val() == sess_dept_code){
			flag = true;
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
		if(gubun == "requestCancel"){
			if($("#ipe_eeno").val() == sess_empno){
				flag = true;
			}else if(sess_mstu == "M" || sess_auth == 5){
				flag = true;
			}else{
				flag = false;
			}
		}else if( sess_mstu == "M" || sess_auth == 5 ){
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

function doClear(){
	$("#apply_date").val("");
	$("#doc_no").val("");
	$("#pgs_st_cd").val("");
	$("#pgs_st_nm").val("");
	$("#if_id").val("");
	$("#ipe_eeno").val("");
	$("#snb_rson_sbc").val("");
	
	$("#r_line_nm").val("");
	$("#r_line").val("");
	$("#r_busStop_nm").val("");
	$("#r_busStop").val("");
	$("#r_address").val("");
	$("#r_numb").val("");
	$("#r_complement").val("");
	$("#r_district").val("");
	$("#r_city").val("");
	$("#r_zipCode").val("");
	$("#r_workShift").val("");
	
	$("#line").val("");
	$("#busStop").val("");
	$("#address").val("");
	$("#numb").val("");
	$("#complement").val("");
	$("#district").val("");
	$("#city").val("");
	$("#zipCode").val("");
	$("#workShift").val("");
	$("#remark").html("");
	$("#fileTemp").val("");

	$("#hid_fil_nm").val("");
	$("#hid_org_fil_nm").val("");
	$("#bus_time").val("");
}

function doFileDown(){
	if($("#hid_org_fil_nm").val() != "" && $("#fileTemp").val() == $("#hid_org_fil_nm").val()){
		var fileInfo = {
			doc_no    		: $("#doc_no").val(),
			eeno    		: $("#eeno").val()
		};
		
		var frm = document.frm;
		frm.fileInfo.value = util.jsonToString(fileInfo);
		frm.action = "doShuttleBusFileDown.do";
		frm.submit();
	}
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
	setInsaInfo('N');
}

function doPrint(){
	$.printPreview.loadPrintPreview();	
}