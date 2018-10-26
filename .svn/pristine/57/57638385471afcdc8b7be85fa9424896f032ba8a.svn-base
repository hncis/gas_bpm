
var firstYn = "Y";
var authType = "N";

function fnSetDocumentReady(){
	getCommonCode("room_plant:X0004;fr_time:TIME;to_time:TIME;budget_type:X3012;tot:X3020:A;rsvt_day:XRM02:A", "N", "searchBudgetCombo();");	
}

function searchBudgetCombo(){
	getCommonCode("budget_sel:XRM:S;budget_wbs:XRM;", "N", "doSearch()", "/hncis/system/getPurchaseOrderCombo.do");
}

function calMealPriceInfo(){
	var mealPrice = 0;
	$.each(eval(mealPrice_info),function(targetNm,optionData){
		$.each(eval(optionData),function(idx,optionData1){
			if($("#room_meal").val() == optionData1.value){
				mealPrice = optionData1.price;
			}
		});
	});
	
	if($("#room_meal").val() == "N"){
		$("#room_qty").val(0);
		$("#amont").val(0);
	}
	else{
		var roomQty = $("#room_qty").val() == ""? 0 :  $("#room_qty").val();
		$("#amont").val(numberComma(Number(roomQty) * Number(mealPrice)));
	}
}

function doSearch(msgFlag){
	var keyData = {
		if_id		: $('#if_id').val(),
		corp_cd		: sess_corp_cd,
		loc_cd		: sess_locale
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoRmToRequestForAprv.do", paramData, "loadCallBack(jsonData.sendResult,'"+msgFlag+"');");
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
	
	if(msgFlag != 'N'){ 
		setBottomMsg(result.message, false);
	}
	
	if(result.fr_ymd != result.to_ymd || result.rsvt_day != ""){
		$("#isMulti").attr('checked', true) ;
	}else{
		$("#isMulti").attr('checked', false) ;
	}
	$("#isMulti").attr("disabled", true);
	$("#room_plant").attr("disabled", true);
	$("#fr_time").attr("disabled", true);
	$("#to_time").attr("disabled", true);
	
	if($("#room_plant").val() == "2"){
		$(".plant_hidden_area").hide();
	}else{
		$(".plant_hidden_area").show();
	}
	
	fnSubmitInfoSettings($("#pgs_st_cd").val(), $("#if_id").val(), result.code, "snb_rson_sbc", "1");
}
