function fnSetDocumentReady(){
	initMenus();
	$(".inputOnlyNumber").numeric();
	init();
	
}

function init(){
	doSearch();
}

function doSearchDays(){

	var param = {
			eeno   : $("#eeno").val(),
			corp_cd	: sess_corp_cd
	};

	var paramData = {
		paramJson : util.jsonToString(param)
	};

	doCommonAjax("doSearchLvToRemainDaysInfo.do", paramData, "doSearchRemainDaysInfoCallBack(jsonData.sendResult);");
	
}

function doSearchRemainDaysInfoCallBack(result){
	
	//입사년차
	$("#join_year").val(result.join_year+"년차");
	//잔여일수
	var rmDays = Number(result.rm_days) -  Number(result.use_days);
	$("#rm_days").val(rmDays);
	//사용일수
	$("#use_days").val(result.use_days);
	
	var joinYmd = result.join_ymd;
	
	$("#join_ymd").val(joinYmd.substring(0, 4)+ "-"+joinYmd.substring(4, 6)+ "-"+joinYmd.substring(6, 8));
	
	if($("#init_yn").val() == 'Y'){
		$("#init_yn").val('N');
		doSearchList();
	}
}

function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
	   case "search" :
		    doSearch("N");
			break;
	}
}

function doSearch(msgFlag){
	var param = {
			if_id 		: $("#M_DOC_ID").val(),
			corp_cd		: sess_corp_cd,
			loc_cd      : sess_locale
	};

	var paramData = {
		paramJson : util.jsonToString(param)
	};

	doCommonAjax("doSearchLvToRequestInfo.do", paramData, "doSearchCallBack(jsonData.sendResult);");
}

/**
 * callback
 */
function doSearchCallBack(result){
	//loadJsonSet(result);
	
	$("#eeno").val(result.eeno);
	$("#keyEenm").val(result.eenm);
	$("#keyPosition").val(result.step_nm);
	$("#keyOpsNm").val(result.dept_nm);
	$("#req_date").val(result.req_ymd);
	$("#doc_no").val(result.doc_no);
	$("#pgs_st_cd").val(result.pgs_st_cd);
	$("#pgs_st_nm").val(result.pgs_st_nm);
	$("#keyTelNo").val(result.tel_no);
	$("#snb_rson_sbc").val(result.snb_rson_sbc);
	$("#fr_ymd").val(result.fr_ymd);
	$("#to_ymd").val(result.to_ymd);
	$("#rem_sbc").val(result.rem_sbc);
	$("#ipe_eeno").val(result.ipe_eeno);
	$("#if_id").val(result.if_id);
	
	$("#fr_ymd_h").val(result.fr_ymd_h);
	$("#to_ymd_h").val(result.to_ymd_h);
	
	fnSubmitInfoSettings($("#pgs_st_cd").val(), $("#if_id").val(), result.code, "snb_rson_sbc", "1");
	
	setBottomMsg(result.message, false);
	
	doSearchDays();
}




