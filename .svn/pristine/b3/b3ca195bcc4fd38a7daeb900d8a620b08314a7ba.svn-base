function fnSetDocumentReady(){
	initMenus();
	sess_auth = $("#work_auth").val();
	
	$(".inputOnlyNumber").numeric();
	
	readOnlyStyle("eeno", 1);
	readOnlyStyle("ee_nm", 1);
	readOnlyStyle("poa_nm", 1);
	readOnlyStyle("ops_nm", 1);
	readOnlyStyle("ops_cd", 1);
	readOnlyStyle("keyOpsCd", 1);
	readOnlyStyle("ptt_ymd", 1);
	readOnlyStyle("doc_no", 1);
	readOnlyStyle("tel_no", 1);
	readOnlyStyle("pgs_st_cd", 1);
	readOnlyStyle("pgs_st_nm", 1);
	readOnlyStyle("car_no", 1);
	readOnlyStyle("tic_no", 1);
	readOnlyStyle("tic_aet", 1);
	readOnlyStyle("tic_desc", 1);
	readOnlyStyle("tic_pint", 1);
	readOnlyStyle("tic_amt", 1);
	readOnlyStyle("tic_ymd", 1);
	readOnlyStyle("tic_time", 1);
	readOnlyStyle("region_nm", 1);
	readOnlyStyle("tic_place", 1);
	readOnlyStyle("tic_city", 1);
	readOnlyStyle("drv_lmt_ymd", 1);
	readOnlyStyle("vehl_nm", 1);
	readOnlyStyle("tic_code", 1);
	readOnlyStyle("tic_payment", 1);
	
	if($("#hid_doc_no").val() != ""){
		retrieve("search");
	}
}

function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
		   	doSearch();
			break;
	   case "apply" :
		    doAccept(btnFlag);
		    break;
	   case "print" :
		    doPrint();
		    break;
	   case "back" :
		   doBack();
		   break;
	}
}

function fnParamJson(){
	var paramVo = {
		doc_no : $("#hid_doc_no").val()
	};
	return paramVo;
}

function doSearch(){
	var paramData = {
		paramJson : util.jsonToString(fnParamJson())
	};
	doCommonAjax("doSearchByXtm03ViewInfo.do", paramData, "loadCallBack(jsonData.sendResult)");
}


function doAccept(btnFlag){
	var params = [];
	if($("#hid_doc_no").val() == ""){
		alertUI("The document number is blank");
		return;		
	}else if($("#hid_doc_no").val() != $("#doc_no").val()){
		alertUI("The document number is wrong");
		return;
	}else if($("#pgs_st_cd").val() != "1"){
		alertUI("You can't accept in this status");
		return;
	}
	
	var list = {
		doc_no : $("#hid_doc_no").val()
	};
	params.push(list);
	
	confirmUI("Do you want to Accept?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramBtn : btnFlag, 
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doActionByXtm01.do", paramData, "acceptCallBack(jsonData.sendResult)");
			}
		});
	});
}

function acceptCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function doPrint(){
	$.printPreview.loadPrintPreview();
}

function loadCallBack(result){
	$("#doc_no").val(result.doc_no);
	$("#eeno").val(result.eeno);
	$("#ee_nm").val(result.eenm);
	$("#poa_nm").val(result.pos_nm);
	$("#ops_nm").val(result.dept_nm);
	$("#tel_no").val(result.tel_no);
	$("#ptt_ymd").val(result.ptt_ymd);
	$("#pgs_st_cd").val(result.pgs_st_cd);
	$("#pgs_st_nm").val(result.pgs_st_nm);
	$("#vehl_nm").val(result.vehl_nm);
	$("#car_no").val(result.car_no);
	$("#tic_no").val(result.tic_no);
	$("#tic_aet").val(result.tic_aet);
	$("#tic_desc").val(result.tic_desc);
	$("#tic_pint").val(result.tic_pint);
	$("#tic_amt").val(result.tic_amt.replace(".",","));
	$("#tic_ymd").val(result.tic_ymd);
	$("#tic_time").val(result.tic_time);
	$("#region_nm").val(result.region_nm);
	$("#tic_place").val(result.tic_place);
	$("#tic_city").val(result.tic_city);
	$("#drv_lmt_ymd").val(result.drv_lmt_ymd);
	$("#tic_remarks").val(result.tic_remarks);
	$("#tic_code").val(result.tic_code);
	$("#tic_payment").val(result.tic_payment);
	setBottomMsg(result.message);
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