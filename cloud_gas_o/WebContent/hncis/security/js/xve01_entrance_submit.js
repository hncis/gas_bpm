var gridParam;
var comboVal2;
var gridName1 = "htmlTable";
var datarow = {full_name:"", et_rg:"", company:"", visit_purp:"", et_type:"", access:"", et_date:"", et_status:"", et_remark:"",
		seq: "", eeno: "", apply_date: "", if_id: "", pgs_st_nm: "", pgs_st_cd: "", purpose:"", reason:"", remark:"", doc_no:""};

function fnSetDocumentReady(){
	getCommonCode("et_type:X3006:Z;", "Y", "comboSearch(jsonData.sendResult);");
}

function comboSearch(jsonData){
	comboVal2 = jsonData;
	getCommonCode('access:X3003;', 'Y', 'initSub();');
}

function initSub(){
	var cn = ["날짜",  "회사", "이름", "직위", "연락처", "유형", "차량여부",  "상태", "비고",
	          "seq", "eeno", "apply_date", "if_id", "pgs_st_nm", "pgs_st_cd", "purpose", "reason", "remark", "doc_no", ""];
	var cm =
	[
		{name:"et_date",		index:"et_date"		, sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false,
			 editoptions: {
				 readonly : true,
				 dataInit: function(element) {
					 $(element).datepicker({
						 dateFormat: 'yy-mm-dd',
						 onSelect: function(dataText, inst){
						 }
					 });
				 }
			 }
		 },
		 {name:"company",	index:"company"		, sortable:false,		formatter:"string",		width:160,	align:"left",	editable:false},
		 {name:"full_name",	index:"full_name"	, sortable:false,		formatter:"string",		width:160,	align:"left",	editable:false},
		 {name:"et_rg",		index:"et_rg"		, sortable:false,		formatter:"string",		width:100,	align:"left",	editable:false},
		 {name:"visit_purp", index:"visit_purp"	, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:false},
		 {name:"et_type",   index:"et_type"		, sortable:false,		formatter:"string",		width:110,	align:"left",	editable:false, hidden:true},
 		 {name:"access",	index:"access"		, sortable:false,		formatter: "select",	width:100,	align:'center',	editable:false,	edittype:'select',
			editoptions:{value:getComboValue('access'), dataInit: function(elem) {$(elem).width(95);}
		        },
		        editrules:{required:true}
		 },
		 {name:"et_status",	index:"et_status"	, sortable:false,		formatter:"string",		width:100,	align:"left",	editable:false, hidden:true},
		 {name:"et_remark",	index:"et_remark"	, sortable:false,		formatter:"string",		width:332,	align:"left",	editable:false},
		 {name:"seq",		index:"seq"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"eeno",		index:"eeno"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"apply_date",	index:"apply_date"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"if_id",		index:"if_id"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"purpose",	index:"purpose"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"reason",		index:"reason"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"remark",		index:"remark"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"doc_no",		index:"doc_no"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		 {name:"snb_rson_sbc",		index:"snb_rson_sbc"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true}
	];
	
	var params = {
		if_id 		 : parent.$("#if_id").val(),
		type		 : "5",
		corp_cd		 : sess_corp_cd
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestEntrance.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			width        : "1122",
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
}

function loadDataSet(){
	parent.$("#purpose").val(getColValue("purpose", 1));
	parent.$("#apply_date").val(getColValue("apply_date", 1));
	parent.$("#eeno").val(getColValue("eeno", 1));
	parent.$("#pgs_st_cd").val(getColValue("pgs_st_cd", 1));
	parent.$("#pgs_st_nm").val(getColValue("pgs_st_nm", 1));
	parent.$("#doc_no").val(getColValue("doc_no", 1));
	parent.$("#reason").val(getColValue("reason", 1));
	
	resizeIframe();
	
	parent.doSearchRequestInfo(parent.$("#if_id").val());
	
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
		url = "doInsertSecurityRequestEntrance.do";
	}else if(gubun == "delete"){
		if(sess_mstu != "M" && sess_auth != 5 && parent.$("#eeno").val() != sess_empno && $("#pgs_st_cd").val() !== "0"){
			alertUI("삭제할 수 없습니다.");
			return;
		}
		url = "doDeleteSecurityRequestEntrance.do";
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
			doc_no			: parent.$("#hid_doc_no").val(),
			type			: "5",
			corp_cd			: sess_corp_cd
		};
		params.push(list);
	}else{
		for(var i = 0; i < ids.length; i++){
			rowId = ids[i];
			if(rowId){
				if(getColValue("full_name", rowId) != "" && getColValue("et_rg", rowId) != ""){
					if(getColValue("company", rowId) == ""){
						alertUI(rowId + " line : Please enter Company!");
						return;
					}

					list = {
							seq				: getColValue("seq", rowId),
							eeno     		: parent.$("#eeno").val(),
							doc_no			: parent.$("#hid_doc_no").val(),
							apply_date		: app_dt,
							purpose 		: parent.$("#purpose").val(),
							type			: "5",
							full_name		: getColValue("full_name", rowId),
							et_rg			: getColValue("et_rg", rowId),
							company			: getColValue("company", rowId),
							visit_purp		: getColValue("visit_purp", rowId),
							et_type			: getColValue("et_type", rowId),
							access			: getColValue("access", rowId),
							et_date			: getColValue("et_date", rowId),
							et_remark		: getColValue("et_remark", rowId),
							pgs_st_cd   	: "0",
							remark			: overLineHtml(changeToUni(parent.$("#remark").val())),
							ipe_eeno    	: sess_empno,
							updr_eeno   	: sess_empno,
							corp_cd			: sess_corp_cd
					};
					params.push(list);
				}
			}
		}
	}
	
	if(params.length == 0){
		alertUI("Please enter Full Name, RG, Company!");
		return;
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
		doc_no		 : parent.$("#hid_doc_no").val(),
		apply_date 	 : apply_date_temp,
		type		 : "5",
		corp_cd		 : sess_corp_cd
	};
	doCommonSearch("doSearchSecurityRequestEntrance.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
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
	
	var bsicInfo;
	if(gubun == "request"){
		bsicInfo = {
			key_mode      : gubun,
			key_eeno      : parent.$("#eeno").val(),
			key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
			key_pgs_st_cd : pgs_st_cd,
			doc_no		  : parent.$("#hid_doc_no").val(),
			type		  : "5",
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
		type		  : "5",
		doc_no		  : parent.$("#hid_doc_no").val(),
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
					doCommonAjax("doConfirmSecurityRequestMaterial.do", paramData, "confirmCallBack(jsonData.sendResult);");
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
		type		  : "5",
		doc_no		  : parent.$("#hid_doc_no").val(),
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
					doCommonAjax("doConfirmCancelSecurityRequestMaterial.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function confirmCallBack(result){
	setBottomMsg(result.message, true);
	parent.SubmitClear();
	doSearch("N");
}

function getComboValueType(comboName){
	var returnVal="";
	if(typeof(comboVal2) == 'undefined'){
		returnVal = ":";
	}
	else{
		var i = 0;
		$.each(eval(comboVal2),function(targetNm,optionData){
			if(targetNm == comboName){
				$.each(eval(optionData),function(index,optionData){
					if(i == 0){
						returnVal = returnVal + optionData.value + ":" + optionData.name;
					}else{
						returnVal = returnVal + ";" + optionData.value + ":" + optionData.name;
					}
					i++;
				});
			}
	      });
		if(typeof(returnVal) == 'undefined'){
			returnVal = '';
		}
		else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
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