var gridParam;
var gridName1 = "htmlTable";
var cn = ["Company", "Photographer", "Brand", "Model", "Serial #", "day", "hour", "day", "hour",
          "seq", "eeno", "apply_date", "if_id", "pgs_st_nm", "pgs_st_cd", "purpose", "area", "exposure", "reason", "remark", ""];
var datarow = {company:"", photographer:"", brand:"", model:"", serial:"", day1:"", hour1:"",day2:"", hour2:"",
			   seq: "", eeno: "", apply_date: "", if_id: "", pgs_st_nm: "", pgs_st_cd: "", purpose:"", area:"", exposure:"", reason:"", remark:"", doc_no:"" };
var cm =
[
	{name:"company",	index:"company"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"photographer",index:"photographer", sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"brand",		index:"brand"		, sortable:false,		formatter:"string",		width:172,	align:"left",	editable:true},
	{name:"model",		index:"model"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"serial",		index:"serial"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"day1",		index:"day1"		, sortable:false,		formatter:"string",		width:110,	align:"center",	editable:true,	frozen : false,
		editoptions: {
			readonly : true,
            dataInit: function(element) {
     		    $(element).datepicker({
     		    	dateFormat: 'dd/mm/yy',
     		    	onSelect: function(dataText, inst){
     		    	}
		    	});
            }
		}
	 },
	{name:"hour1",		index:"hour1"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"day2",		index:"day2"		, sortable:false,		formatter:"string",		width:110,	align:"center",	editable:true,	frozen : false,
		editoptions: {
			readonly : true,
            dataInit: function(element) {
     		    $(element).datepicker({
     		    	dateFormat: 'dd/mm/yy',
     		    	onSelect: function(dataText, inst){
     		    	}
		    	});
            }
		}
	},
	{name:"hour2",		index:"hour2"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:true},
	{name:"seq",		index:"seq"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"eeno",		index:"eeno"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"apply_date",	index:"apply_date"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"if_id",		index:"if_id"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"purpose",	index:"purpose"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"area",		index:"area"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"exposure",	index:"exposure"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"reason",		index:"reason"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"remark",		index:"remark"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"doc_no",		index:"doc_no"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true}
];

function fnSetDocumentReady(){
	getCommonCode("exposure:X3002:Z;", "N", "comboArea()");		//목적
}

function comboArea(){
	getCommonCode("area:X3004:Z;", "N", "initSub()", "/hncis/security/getSearchFilmComboArea.do");		//목적
}

function initSub(){
	var apply_date_temp = "";
	
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = dateConversionKr(parent.$("#apply_date").val());
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		apply_date 	 : apply_date_temp,
		if_id 		 : parent.$("#if_id").val(),
		type		 : "4",
		corp_cd		 : sess_corp_cd
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestFilm.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			width		 : "1122",
			sortname     : "",
			sortorder    : "",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			paramJson    : params,
			completeFc   : "addGridRow(5);loadDataSet();"
		}]
	};
	
	commonJqGridInit(gridParam);

	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
          {startColumnName: 'day1', numberOfColumns: 2, titleText: 'Start'},
          {startColumnName: 'day2', numberOfColumns: 2, titleText: 'End'}
		]
	});
}

function loadDataSet(){
	$("#exposure").val(getColValue("exposure", 1));
	$("#area").val(getColValue("area", 1));

	parent.$("#purpose").val(getColValue("purpose", 1));
	parent.$("#apply_date").val(getColValue("apply_date", 1));
	parent.$("#eeno").val(getColValue("eeno", 1));
	parent.$("#pgs_st_cd").val(getColValue("pgs_st_cd", 1));
	parent.$("#pgs_st_nm").val(getColValue("pgs_st_nm", 1));
	parent.$("#doc_no").val(getColValue("doc_no", 1));
	parent.$("#reason").val(getColValue("reason", 1));
	
	resizeIframe();
	
	if(getColValue("if_id", 1)!= ""){
		parent.doSearchRequestInfo(getColValue("if_id", 1));
	}
	
	doSaerchSecurityRemark();
}

function doSaerchSecurityRemark(){
	var keyData = { 
		doc_no : parent.$("#doc_no").val() ,
		corp_cd	: sess_corp_cd
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSaerchSecurityRemark.do", paramData, "searchRemarkCallBack(jsonData.sendResult)");
}

function searchRemarkCallBack(result){
//	alertUI(result.remark);
	parent.$("#remark").val(result.remark);
	
	parent.setInsaInfo();
}

function gridRowAdd(){
	var gridRowId = jQuery("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow); 
}

function doCudAction(gubun) {
	var url = "";
	var callBack = "actionCallBack(jsonData.sendResult);";
	var params = [];
	var ids = jQuery("#"+gridName1).getDataIDs();

	if(gubun == "save"){
		url = "doInsertSecurityRequestFilm.do";
	}else if(gubun == "delete"){
		if(sess_mstu != "M" && sess_auth != 5 && parent.$("#eeno").val() != sess_empno && $("#pgs_st_cd").val() !== "0"){
			alertUI("삭제할 수 없습니다.");
			return;
		}
		url = "doDeleteSecurityRequestFilm.do";
	}
	
	var d= new Date();
	var m = d.getMonth() +1;
	var day = d.getDate();
	if(m < 10){ m = "0" + m; }
	if(day < 10){ day = "0" + day; }
	var app_dt = d.getFullYear() + "/" + m + "/" + day;
	if(parent.$("#apply_date").val() != ""){
		app_dt = dateConversionKr(parent.$("#apply_date").val());
	}
	
	if(gubun == "delete"){
		list = {
			eeno     		: parent.$("#eeno").val(),
			apply_date		: app_dt,
			type			: "4",
			corp_cd			: sess_corp_cd
		};
		params.push(list);
	}else{
		for(var i = 0; i < ids.length; i++){
			rowId = ids[i];
			if(rowId){
				if(getColValue("company", rowId) != "" && getColValue("photographer", rowId) != ""){
					if(getColValue("brand", rowId) == ""){
						alertUI(rowId + " line : 상표를 입력하세요.");
						return;
					}

					list = {
							seq				: getColValue("seq", rowId),
							eeno     		: parent.$("#eeno").val(),
							apply_date		: app_dt,
							purpose 		: parent.$("#purpose").val(),
							type			: "4",
							area			: $("#area").val(),
							exposure		: $("#exposure").val(),
							company   		: getColValue("company", rowId),
							photographer	: getColValue("photographer", rowId),
							brand   		: getColValue("brand", rowId),
							model			: getColValue("model", rowId),
							serial			: getColValue("serial", rowId),
							day1			: getColValue("day1", rowId),
							hour1			: getColValue("hour1", rowId),
							day2			: getColValue("day2", rowId),
							hour2			: getColValue("hour2", rowId),
							pgs_st_cd   	: "0",
							remark			: parent.$("#remark").val(),
							ipe_eeno    	: sess_empno,
							updr_eeno   	: sess_empno,
							corp_cd			: sess_corp_cd
					};
					params.push(list);
				}
			}
		}
	}
	
	confirmUI("Do you want to "+gubun+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax(url, paramData, callBack);
			}
		});
	});
}


function actionCallBack(result){
	setBottomMsg(result.message, true);
	
	doSearch("N");
}


function doSearch(msgFlag){
	var apply_date_temp = "";
	
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = dateConversionKr(parent.$("#apply_date").val());
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		apply_date 	 : apply_date_temp,
		type		 : "4",
		corp_cd		 : sess_corp_cd
	};
	doCommonSearch("doSearchSecurityRequestFilm.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
}

function doApprove(gubun){
	var url = "";
	var callBack = "";
	var pgs_st_cd = "";
	if(gubun == "request"){
		url = "doApproveSecurityRequest.do";
		callBack = "approveCallBack('"+gubun+"', jsonData.sendResult);";
		pgs_st_cd = "A";
	}else if(gubun == "requestCancel"){
		url = "doApproveCancelSecurityRequest.do";
		callBack = "approveCallBack('"+gubun+"', jsonData.sendResult);";
	}
	
	var tmpCode = "1";
	if($("#exposure").val() == "O"){
		tmpCode = "2";
	}
	
	var bsicInfo;
	if(gubun == "request"){
		bsicInfo = {
			key_mode      : gubun,
			key_eeno      : parent.$("#eeno").val(),
			key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
			key_pgs_st_cd : pgs_st_cd,
			type		  : "4",
			code		  : tmpCode,
			ops_cd		  : $("#area").val(),
			updr_eeno     : sess_empno,
			corp_cd		  : sess_corp_cd
		};
	}else{
		bsicInfo = {
			if_id     : getColValue("if_id", 1),
			updr_eeno : sess_empno,
			corp_cd	  : sess_corp_cd
		};
	}
	
	confirmUI("Do you want to "+gubun+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax(url, paramData, callBack);
			}
		});
	});
}
 
function approveCallBack(gubun, result){
	setBottomMsg(result.message, true);
	parent.SubmitClear();
	doSearch("N");
}

function doConfirm(){
	var bsicInfo = {
		key_mode      : "confirm",
		key_eeno      : parent.$("#eeno").val(),
		key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
		key_pgs_st_cd : "3",
		type		  : "4",
		updr_eeno     : sess_empno,
		corp_cd		  : sess_corp_cd
	};
	
	confirmUI("확정 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmSecurityRequestVehicle.do", paramData, "confirmCallBack(jsonData.sendResult);");
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
		key_eeno      : parent.$("#eeno").val(),
		key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
		key_pgs_st_cd : "0",
		type		  : "4",
		snb_rson_sbc  : parent.$("#reason").val(),
		updr_eeno     : sess_empno,
		corp_cd		  : sess_corp_cd
	};
	
	confirmUI("신청 취소사유를 입력하세요.\n신청 취소하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmCancelSecurityRequestVehicle.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function confirmCallBack(result){
	setBottomMsg(result.message, true);
	parent.SubmitClear();
	doSearch("N");
}

function resizeIframe(){
	var doc = document.getElementById("contents");
	parent.document.getElementById("ifra").height = doc.offsetHeight + "px";
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xve_file.gas", width = "460", height = "453";
	$("#file_doc_no").val(parent.$("#doc_no").val());
	$("#file_eeno").val("00000000");
	$("#hid_use_yn").val("N");

	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}