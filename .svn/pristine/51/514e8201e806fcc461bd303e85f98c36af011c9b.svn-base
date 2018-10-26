function fnSetDocumentReady(){
	initMenus();
	if(sess_mstu == "M" || $("#work_auth").val() == '5'){
		$("#snb_rson_sbc").removeClass("disabled");
		$("#snb_rson_sbc").attr("readonly", false);
	}
	$("#po_numb_obj").keyup(function(){$(this).val( $(this).val().replace(/[^0-9]/g,"") );} );
	
	$(".inputOnlyNumber").numeric();
	//공통 콤보 조회
	getCommonCode("po_serv_desc:PO01:S;po_addt_serv:PO02:S;po_type:PO03:S;", "N", "init();");
}

function init(){
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){
		readOnlyStyle("po_trk_no", 1);
		readOnlyStyle("req_eeno", 1);
	}else{
		readOnlyStyle("po_trk_no", 2);
		readOnlyStyle("req_eeno", 2);
	}
	
	if($('#M_DOC_NO').val() != ""){
		doSearch(); 
	}else{
		setInitUserInfo();
		changeAddtComb();
		changeTypeComb();
	}
}

//유저정보 세팅
function setInitUserInfo(){
	
	$("#eeno").val(sess_empno);
	$("#eeno_nm").val(sess_name);
	$("#pos_nm").val(sess_step_name);
	$("#dept_nm").val(sess_dept_name);
	$("#ptt_ymd").val(getCurrentToDateAddDayEn("/",0));
	$("#tel_no").val(sess_tel_no);
	$('#cost_cd').val(sess_cost_cd);
	
}

function changeAddtComb(){
	if($("#po_addt_serv").val() == "AR"){
		$("#as_type_nm").text("Contents of Declaration");
	}else if($("#po_addt_serv").val() == "MP"){
		$("#as_type_nm").text("Full Name");
	}else if($("#po_addt_serv").val() == "VD"){
		$("#as_type_nm").text("Declared Value R$");
	}else if($("#po_addt_serv").val() == "TG"){
		$("#as_type_nm").text("");
	}else{
		$("#as_type_nm").text(""); 
	}
}

function changeTypeComb(){
	
	if($("#po_type").val() == 'EVL'){
		$("#divType1").show();
		$("#divType2").hide();
		$("#divType3").hide();
		$("#po_pb_len").val("");
		$("#po_pb_wdth").val("");
		$("#po_pb_hght").val("");
		$("#po_rc_dmtr").val("");
		$("#po_rc_len").val("");
	}else if($("#po_type").val() == 'POB'){
		$("#divType1").hide();
		$("#divType2").show();
		$("#divType3").hide();
		$("#po_obj_size").val("");
		$("#po_rc_dmtr").val("");
		$("#po_rc_len").val("");
	}else if($("#po_type").val() == 'ROC'){
		$("#divType1").hide();
		$("#divType2").hide();
		$("#divType3").show();
		$("#po_obj_size").val("");
		$("#po_pb_len").val("");
		$("#po_pb_wdth").val("");
		$("#po_pb_hght").val("");
	}else{
		$("#divType1").hide();
		$("#divType2").hide();
		$("#divType3").hide();
		$("#po_obj_size").val("");
		$("#po_pb_len").val("");
		$("#po_pb_wdth").val("");
		$("#po_pb_hght").val("");
		$("#po_rc_dmtr").val("");
		$("#po_rc_len").val("");
	}
}

function retrieve(btnFlag){    
	var f = document.frm;
	switch(btnFlag){
	   case "search" :
		    doSearch("N");
			break;
	   case "new" :
		    doNew();
			break;
	   case "save" :
		   doSave();
			break;
	   case "request" :
		   doRequest();
			break;
	   case "requestCancel" :
		   doRequestCancel();
			break;
	   case "confirm" :
		   doConfirm();
			break;
	   case "confirmCancel" :
		   doConfirmCancel();
			break;
	   case "reject" :
		   doReject();
		   break;
	   case "delete" :
		   doDelete();
			break;
	   case "back" :
		    doBack();
		    break;
	   case "print" :
		    doPrint();
		    break;
	}
}

function doSave(){
	
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't save.");
				return;
			};
			//입력 상태인지 체크
			if($("#pgs_st_cd").val() != '0'){
				alertUI("You can't save in this status.");
				return;
			}
		}else{
			//입력 상태인지 체크
			if($("#pgs_st_cd").val() == '2'){
				alertUI("You can't save in this status.");
				return;
			}
		}
	}
	
	if($("#req_eeno").val().length != 8){
		alertUI("Please enter User Id.");
		$("#req_eeno").focus();
		return;
	}
	if($("#po_serv_desc").val() == ""){
		alertUI("Please select Service Description.");
		$("#po_serv_desc").focus();
		return;
	}
//	if($("#po_addt_serv").val() == ""){
//		alertUI("Please select Additional Services.");
//		$("#po_addt_serv").focus();
//		return;
//	}
	if($("#po_type").val() == ""){
		alertUI("Please select Type.");
		$("#po_type").focus();
		return;
	}
	   
	var keyData = {
			doc_no				: $("#temp_doc_no").val(), 
			po_trk_no      		: $("#po_trk_no").val(),
			po_serv_desc      	: $("#po_serv_desc").val(),
			po_numb_obj      	: $("#po_numb_obj").val(),
			po_addt_serv      	: $("#po_addt_serv").val(),
			po_cont_decl      	: overLineHtml(changeToUni($("#po_cont_decl").val())),
			po_type      		: $("#po_type").val(),
			po_obj_size      	: overLineHtml(changeToUni($("#po_obj_size").val())),
			po_pb_len      		: overLineHtml(changeToUni($("#po_pb_len").val())),
			po_pb_wdth      	: overLineHtml(changeToUni($("#po_pb_wdth").val())),
			po_pb_hght      	: overLineHtml(changeToUni($("#po_pb_hght").val())),
			po_rc_dmtr      	: overLineHtml(changeToUni($("#po_rc_dmtr").val())),
			po_rc_len      		: overLineHtml(changeToUni($("#po_rc_len").val())),
			po_comp_nm      	: overLineHtml(changeToUni($("#po_comp_nm").val())),
			po_email      		: $("#po_email").val(),
			po_phone      		: $("#po_phone").val(),
			po_addr      		: overLineHtml(changeToUni($("#po_addr").val())),
			po_numb      		: $("#po_numb").val(),
			po_cmplt      		: overLineHtml(changeToUni($("#po_cmplt").val())),
			po_nghb      		: overLineHtml(changeToUni($("#po_nghb").val())),
			po_city      		: overLineHtml(changeToUni($("#po_city").val())),
			po_state      		: overLineHtml(changeToUni($("#po_state").val())),
			po_zip_cd      		: overLineHtml(changeToUni($("#po_zip_cd").val())),
			po_obj_desc      	: overLineHtml(changeToUni($("#po_obj_desc").val())),
			po_invc      		: overLineHtml(changeToUni($("#po_invc").val())),
			po_drvr_indc      	: overLineHtml(changeToUni($("#po_drvr_indc").val())),
			po_ait      		: overLineHtml(changeToUni($("#po_ait").val())),
			po_prd_indc      	: overLineHtml(changeToUni($("#po_prd_indc").val())),
			remark		      	: changeToUni($("#remark").val().replace(/\n/g, '＠')),
			pgs_st_cd			: '0',
			ipe_eeno			: sess_empno,
			updr_eeno			: sess_empno
			
	};  
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doSavePoToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);saveCallBack(jsonData.sendResult);");
			}
		});
	});
}
function saveCallBack(result){

	if(result.code1 == "Y"){
		$("#M_DOC_NO").val(result.code);
		doSearch("N");
	}
}

function doSearch(msgFlag){
	
	setFormClear();
	setInitUserInfo();

	var keyData = {
			doc_no		: $('#M_DOC_NO').val()
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoPoToRequest.do", paramData, "loadCallBack(jsonData.sendResult,'"+msgFlag+"');");
}

/**
 * callback
 */
function loadCallBack(result,msgFlag){
	
	loadJsonSet(result);
	
	$("#eeno").val(result.req_eeno);
	$("#eeno_nm").val(result.req_eeno_nm);
	$("#pos_nm").val(result.req_step_nm);
	$("#dept_nm").val(result.req_dept_nm);
	$("#ptt_ymd").val(result.ptt_ymd);
	$("#tel_no").val(result.req_tel_no);
	$("#pgs_st_nm").val(result.pgs_st_nm);
	$("#remark").val(result.remark.replace(/＠/g, "\n"));
	
	changeAddtComb();
	changeTypeComb();
	
	if(msgFlag != 'N'){ 
		setBottomMsg(result.message, false);
	}
}

function setFormClear(){
	$("#eeno").val("");
	$("#eeno_nm").val("");
	$("#pos_nm").val("");
	$("#dept_nm").val("");
	$("#ptt_ymd").val("");
	$("#doc_no").val("");
	$("#tel_no").val("");
	$("#pgs_st_nm").val("");
	$("#doc_no").val("");
	$("#po_trk_no").val("");
	$("#po_serv_desc").val("");
	$("#po_numb_obj").val("");
	$("#po_addt_serv").val("");
	$("#po_cont_decl").val("");
	$("#po_type").val("");
	$("#po_obj_size").val("");
	$("#po_pb_len").val(""); 
	$("#po_pb_wdth").val("");
	$("#po_pb_hght").val("");
	$("#po_rc_dmtr").val(""); 
	$("#po_rc_len").val("");  
	$("#po_comp_nm").val(""); 
	$("#po_email").val("");   
	$("#po_phone").val("");   
	$("#po_addr").val("");   
	$("#po_numb").val("");     
	$("#po_cmplt").val("");    
	$("#po_nghb").val("");    
	$("#po_city").val("");     
	$("#po_state").val("");    
	$("#po_zip_cd").val("");   
	$("#po_obj_desc").val(""); 
	$("#po_invc").val("");    
	$("#po_drvr_indc").val("");
	$("#po_ait").val("");     
	$("#po_prd_indc").val("");
}

function doRequest(){
	
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't request.");
				return;
			};
		}
		//입력 상태인지 체크
		if($("#pgs_st_cd").val() != '0'){
			alertUI("You can't request in this status.");
			return;
		}
	}
	
	if($("#doc_no").val() == ""){
		alertUI("You can't request.");
		return;
	}
	
	   
	var keyData = {
			doc_no	: $("#doc_no").val()
	};  
	
	confirmUI("신청 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doUpdatePoToRequestForRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);requestCallBack(jsonData.sendResult);");
			}
		});
	});
}
function requestCallBack(result){
	
	if(result.code1 == "Y"){
		doSearch("N");
	}
}

function doRequestCancel(){
	
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't request cancel.");
				return;
			};
		}
		//입력 상태인지 체크
		if($("#pgs_st_cd").val() != 'Z'){
			alertUI("You can't request in this status.");
			return;
		}
	}
	
	if($("#doc_no").val() == ""){
		alertUI("You can't request cancel.");
		return;
	}
	
	   
	var keyData = {
			doc_no	: $("#doc_no").val()
	};  
	
	confirmUI("신청취소 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doUpdatePoToRequestForRequestCancel.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);requestCancelCallBack(jsonData.sendResult);");
			}
		});
	});
}
function requestCancelCallBack(result){
	
	if(result.code1 == "Y"){
		doSearch("N");
	}
}

function doConfirm(){
	
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't confirm.");
				return;
			};
		}
		//입력 상태인지 체크
		if($("#pgs_st_cd").val() != 'Z'){
			alertUI("You can't confirm in this status.");
			return;
		}
	}
	
	
	if($("#doc_no").val() == ""){
		alertUI("You can't confirm.");
		return;
	}
	
	if($("#po_trk_no").val() == ""){
		alertUI("Please enter Tracking No.");
		$("#po_trk_no").focus();
		return;
	}
	   
	var keyData = {
			doc_no		: $("#doc_no").val(),
			po_trk_no  	: $("#po_trk_no").val()
	};  
	
	confirmUI("확정 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doUpdatePoToRequestForConfirm.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}
function confirmCallBack(result){
	if(result.code1 == "Y"){
		doSearch("N");
	}
}

function doConfirmCancel(){
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't confirm cancel.");
				return;
			};
		}
		//입력 상태인지 체크
		if($("#pgs_st_cd").val() != '3'){
			alertUI("You can't confirm in this status.");
			return;
		}
	}
	
	if($("#doc_no").val() == ""){
		alertUI("You can't confirm cancel.");
		return;
	}
	
	   
	var keyData = {
			doc_no	: $("#doc_no").val()
	};  
	
	confirmUI("확정취소 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doUpdatePoToRequestForConfirmCancel.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);confirmCancelCallBack(jsonData.sendResult);");
			}
		});
	});
}
function confirmCancelCallBack(result){
	
	if(result.code1 == "Y"){
		doSearch("N");
	}
}

function doDelete(){
	
	//데이터 수정일때 권한 체크
	if($("#doc_no").val() != ""){
		//일반사용자 일때 체크
		if($("#work_auth").val() < 5 && sess_mstu != "M"){
			//입력자 본인이지 체크
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't delete.");
				return;
			};
			//입력 상태인지 체크
			if($("#pgs_st_cd").val() != '0'){
				alertUI("삭제할 수 없는 상태입니다..");
				return;
			}
		}else{
			//입력 상태인지 체크
			if($("#pgs_st_cd").val() == '2'){
				alertUI("삭제할 수 없는 상태입니다..");
				return;
			}
		}
		
	}
	
	
	if($("#doc_no").val() == ""){
		alertUI("You can't delete.");
		return;
	}
	
	   
	var keyData = {
			doc_no		: $("#doc_no").val()
	};  
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson 			: util.jsonToString(keyData)
					};
					doCommonAjax("doDeletePoToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);deleteCallBack(jsonData.sendResult);");
			}
		});
	});
}
function deleteCallBack(result){
	if(result.code1 == "Y"){
		doSearch("N");
	}
}


function cearInsa(){
	if($("#req_eeno").val() == ""){
		$("#req_eeno").val("");
		$("#eeno_nm").val("");
		$("#pos_nm").val("");
		$("#dept_nm").val("");
		$("#tel_no").val("");
	}
}

function setInsaInfo(){
	if($("#req_eeno").val() != ""){
		var keyData = { xusr_empno : $("#req_eeno").val() };
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
}

function doNew(){
	setFormClear();
	setInitUserInfo();
	$("#M_DOC_NO").val("");
	$("#temp_doc_no").val(getTmpDocNo());
}

/**
 * callback
 */
function insaCallBack(result){
	$("#req_eeno").val(result.xusr_empno);
	$("#eeno_nm").val(result.xusr_name);
	$("#pos_nm").val(result.xusr_step_name);
	$("#dept_nm").val(result.xusr_dept_name); 
	$("#tel_no").val(result.xusr_tel_no);
		
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

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xpo01_file.gas", width = "460", height = "453";
	
	if($("#M_DOC_NO").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val($("#M_DOC_NO").val());
	}
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){ 
		if( $("#pgs_st_cd").val() == ""){
			$("#hid_use_yn").val("Y"); 
		}else if(sess_empno == $("#eeno").val()){
			if($("#pgs_st_cd").val() == "0"){
				$("#hid_use_yn").val("Y"); 
			}else{
				$("#hid_use_yn").val("N");   
			}
		}else{
			$("#hid_use_yn").val("N");  
		}
	}else{
		$("#hid_use_yn").val("Y");
	}

	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

function doReject(){
	if($("#pgs_st_cd").val() != 'Z'){
		alertUI("You can't reject in this status.");
		return;
	}
	
	if($("#work_auth").val() == '5' || sess_mstu == 'M'){
	}
	else if($("#work_auth").val() == '6'){
		if(!($("#pgs_st_cd").val() == '3' || $("#pgs_st_cd").val() == 'Z')){
			alertUI("You can't reject in this status."); 
			return;
		}
	}
	else{
		alertUI("반려할 수 없습니다.");
		return;
	}

	var snbRsonSbc = changeToUni($.trim($("#snb_rson_sbc").val()));
	if(snbRsonSbc == ""){
		alertUI("Please enter the reason for reject.");
		$("#snb_rson_sbc").focus();
		return;
	}
	var keyData = {
			doc_no			 : $("#doc_no").val(),
			pgs_st_cd		 : '2',
			snb_rson_sbc	 : snbRsonSbc,
			updr_eeno		 : sess_empno
		};

	confirmUI("반려 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
					};
					doCommonAjax("doRejectToRequestForRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);rejectCallBack(jsonData.sendResult);");
			}
		});
	});
}

function rejectCallBack(result){
	doSearch("N");
}

function doPrint(){
	$.printPreview.loadPrintPreview();
}