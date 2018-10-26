var gridParam;
var gridName1 = "htmlTable";
var cn = ["시작일", "종료일", "차종", "차량등록번호", "색상", "차량번호", "운전자명", "운전자 면허", "종류", "유효기간", "Seq",
          "eeno", "apply_date", "purpose", "parking_area", "corp_company", "biz_number", "activity", "charge", "phone", "address", "pgs_st_cd", "if_id", "pgs_st_nm", "reason", "remark", ""];
var datarow = {start_ymd:"", end_ymd:"", model:"", renavam:"", color:"", plate:"", driver_name:"", driver_lic:"", category:"", cnh_exp:"", seq:"", 
			   eeno:"", apply_date:"", purpose:"", parking_area:"", corp_company:"", biz_number:"", activity:"", charge:"", phone:"", address:"", pgs_st_cd:"", if_id:"", pgs_st_nm:"", reason:"", remark:"", doc_no:"" };
var cm = [
	{name:"start_ymd",		index:"start_ymd"		, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"end_ymd",		index:"end_ymd"			, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"model",			index:"model"			, sortable:false,		formatter:"string",		width:237,	align:"left",	editable:true},
	{name:"renavam",		index:"renavam"			, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"color",			index:"color"			, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"plate",			index:"plate"			, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"driver_name",	index:"driver_name"		, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"driver_lic",		index:"driver_lic"		, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"category",		index:"category"		, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},
	{name:"cnh_exp",		index:"cnh_exp"			, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true},	
	{name:"seq",			index:"seq"				, sortable:false,		formatter:"string",		width:90,	align:"left",	editable:true, hidden:true},
	{name:"eeno",			index:"eeno"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"apply_date",		index:"apply_date"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"purpose",		index:"purpose"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"parking_area",	index:"parking_area"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"corp_company",	index:"corp_company"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"biz_number",		index:"biz_number"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"activity",		index:"activity"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"charge",			index:"charge"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"phone",			index:"phone"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"address",		index:"address"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"pgs_st_cd",		index:"pgs_st_cd"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"if_id",			index:"if_id"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"pgs_st_nm",		index:"pgs_st_nm"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"reason",			index:"reason"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"remark",			index:"remark"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
	{name:"doc_no",			index:"doc_no"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true}
];
				
function fnSetDocumentReady(){
	var apply_date_temp = "";
	var flag = true;
	if(parent.$("#if_id").val() != ""){
		flag = false;
	}
	
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = parent.$("#apply_date").val();
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		apply_date 	 : apply_date_temp,
		if_id 		 : parent.$("#if_id").val(),
		type		 : "1",
		corp_cd		 : sess_corp_cd,
		locale       : sess_locale
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestVehicle.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			width		 : "1122",
			sortname     : "",
			sortorder    : "",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : flag,
			fnMerge      : false,
			paramJson    : params,
			completeFc   : "addGridRow(5);loadDataSet();"
		}]
	};
	
	commonJqGridInit(gridParam);
}

function loadDataSet(){
	resizeIframe();
	
	parent.$("#pgs_st_cd").val(getColValue("pgs_st_cd", 1));
	parent.$("#pgs_st_nm").val(getColValue("pgs_st_nm", 1));
	parent.$("#doc_no").val(getColValue("doc_no", 1));
	parent.$("#purpose").val(getColValue("purpose", 1));
	parent.$("#apply_date").val(getColValue("apply_date", 1));
	parent.$("#eeno").val(getColValue("eeno", 1));
	parent.$("#reason").val(getColValue("reason", 1));
	
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
	parent.$("#remark").val(result.remark);
	
	parent.setInsaInfo();
}


function doSearch(msgFlag){
	var apply_date_temp = "";
	
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = parent.$("#apply_date").val();
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		apply_date 	 : apply_date_temp,
		corp_cd		 : sess_corp_cd
	};
	doCommonSearch("doSearchSecurityRequestVehicle.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
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