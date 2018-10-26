var eenoChangeFlag = false;

function fnSetDocumentReady(){
	initMenus();
	
	getCommonCode("line:Z:0", "N", "fnSearhcCombo();fnChangeLine(1);", "/hncis/shuttleBus/getSearchShuttleBusLineCombo.do");
}

function fnSearhcCombo(){
	getCommonCode("workShift:X3010:S;", "N", "setInsaInfo('N');", "doSearchWorkShiftComboList.do");
}

var saveCode = "";
function clearInsa(){
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
		var keyData = { xusr_empno : $("#eeno").val() };
		
		eenoChangeFlag = gubn == "Y" ? true : false;
		
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
	$("#keyTelNo").val(result.xusr_tel_no);
	$("#keyPosition").val(result.xusr_step_name);
	$("#address").val(result.xusr_street);
	$("#numb").val(result.xusr_house);
	$("#complement").val("");
	$("#district").val(result.xusr_district);
	$("#city").val(result.xusr_city);
	$("#zipCode").val(result.xusr_postal_code);
	$("#workShift").val(result.xusr_work_schedule_nm);
	
	init();
}

function init(){
	initAfterMenus();
}

function retrieve(gubun){
	switch(gubun){
		case "Search" :
			doSearch();
			break;
		case "save" :
			doSave();
			break;
		case "confirm" :
			doConfirm();
			break;
		case "new" :
			doNew();
			break;
	}
}

function doSave(){
	var params = {
		eeno			: $("#eeno").val(),
		line 			: $("#line").val(),
		bus_stop   		: $("#busStop").val(),
		address			: overLineHtml(changeToUni($("#address").val())),
		numb    		: $("#numb").val(),
		complement    	: overLineHtml(changeToUni($("#complement").val())),
		district 	  	: overLineHtml(changeToUni($("#district").val())),
		city			: overLineHtml(changeToUni($("#city").val())),
		zip_code		: $("#zipCode").val(),
		work_shift		: overLineHtml(changeToUni($("#workShift").val())),
		doc_no			: $("#doc_no").val(),
		ptt_ymd			: $("#apply_date").val(),
		pgs_st_cd		: "0",
		req_type		: "1",
		final_yn		: "S",
		ipe_eeno    	: sess_empno,
		updr_eeno   	: sess_empno
	};
	
	confirmUI("Do you want to Save ?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(params)
					};
					doCommonAjax("doSaveShuttleBusRequstInclusion.do", paramData, "doSaveCallBack(jsonData.sendResult)");
			}
		});
	});
}

function doSaveCallBack(result){
	setBottomMsg(result.message, true);
	$("#doc_no").val(result.code);
	
	doSearch();
}


function doSearch(){
	var keyData = {
		doc_no	 	 : $("#doc_no").val(),
		eeno	     : $("#eeno").val()
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
}

function doConfirm(){
	if($("#pgs_st_cd").val() != "0"){
		alertUI("You can't comfirm.");
		return;
	}
	
	var bsicInfo = {
		key_mode      : "confirm",
		eeno	      : $("#eeno").val(),
		doc_no		  : $("#doc_no").val(),
		ptt_ymd		  : $("#apply_date").val(),
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

function confirmCallBack(result){
	setBottomMsg(result.message, true);
	
	doSearch("N");
}

function doClear(){
	$("#apply_date").val("");
	$("#doc_no").val("");
	$("#pgs_st_cd").val("");
	$("#pgs_st_nm").val("");
	$("#if_id").val("");
	$("#ipe_eeno").val("");
	
	$("#line").val("");
	$("#busStop").val("");
	$("#address").val("");
	$("#numb").val("");
	$("#complement").val("");
	$("#district").val("");
	$("#city").val("");
	$("#zipCode").val("");
	$("#workShift").val("");
}

function doNew(){
	$("#eeno").val(sess_empno);
	$("#apply_date").val("");
	$("#doc_no").val("");
	$("#pgs_st_cd").val("");
	$("#pgs_st_nm").val("");
	
	$("#line").val("");
	$("#busStop").val("");
	$("#address").val("");
	$("#numb").val("");
	$("#complement").val("");
	$("#district").val("");
	$("#city").val("");
	$("#busStop").val("");
	$("#zipCode").val("");	
	$("#workShift").val("");	
	
	setInsaInfo();
}

function fnChangeLine(value){
	getCommonCode("busStop:"+value+":1", "N", "fnChangeTime()", "/hncis/shuttleBus/getSearchShuttleBusLineCombo.do");
}

function fnChangeTime(){
	var keyData = {
		type  		: $("#line").val(),
		route_name  : $("#busStop").val()
	};
	
	var paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("getSearchShuttleBusLineTime.do", paramData, "fnChangeTimeCallBack(jsonData.sendResult);");
}

function fnChangeTimeCallBack(result){
	$("#bus_time").val(result.route_time);
}

function cearInsa(){
	if($("#eeno").val() == ""){
		$("#eeno").val("");
		$("#keyEenm").val("");
		$("#keyPosition").val("");
		$("#keyTelNo").val("");
		$("#keyOpsCd").val("");
		$("#keyOpsNm").val("");
	}
}