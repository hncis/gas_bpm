var excelCn = ["Apply Date", "Status", "Requestor", "ID No.", "Reservation Date/Time", "Rooms", "Coffee", "Porcelain", "QTY", "Total", "Cost Center", "Remarks", "PO No."];
var excelCm = ["ptt_ymd", "pgs_st_nm", "req_eeno_nm", "req_eeno", "req_info", "room_place", "room_meal", "tot", "room_qty", "tot_amt", "cost_cd", "note", "po_no"];
var excelFm = ["string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string"];

jQuery(document).ready(function(){
	initMenus();
	
	$("#key_from_date").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_date").datepicker({ dateFormat: "dd/mm/yy" }); 
	
	$(".inputOnlyNumber").numeric();
	
	setComboInfo();
	
});

function setComboInfo(){
	var url        = "/getCommonCombo.do";
//	var pgs_st_cd  = "key_pgs_st_cd:RM02:A;key_plac_work:X0004:A;";
	var pgs_st_cd  = "key_pgs_st_cd:RM02:A;ipt_pgs_st_cd:RM01:A";
	var paramValue = pgs_st_cd;
	getCommonCode(paramValue, "N", "beforeInit();", url);
}

function beforeInit(){
//	$("#key_plac_work").val("1");
//	$("#key_plac_work").attr("disabled", true);
	
	$("#ipt_pgs_st_cd").val("3");
	
	init();
}


/**
 * process init() loading
 */
var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {ptt_ymd:"", req_eeno_nm:"", req_eeno:"", req_info:"", room_place:"", room_meal:"", tot:"", room_qty:"", tot_amt:"", cost_cd:"", pgs_st_nm:"", note:"", doc_no:"",pgs_st_cd2:"",po_no:""};
var comboVal;
var comboVal1; 
function init(){
	//set grid parameter
	var params = {
			req_eeno 		: $("#key_eeno").val(),
			req_eeno_nm 	: $("#key_eenm").val(),
			fr_ymd 			: dateConversionKr(selectNum($("#key_from_date").val())),
			to_ymd 			: dateConversionKr(selectNum($("#key_to_date").val())),
			req_dept_nm     : $("#key_ops_nm").val(),
			pgs_st_cd		: $("#ipt_pgs_st_cd").val(),
			pgs_st_cd2  	: $("#key_pgs_st_cd").val(),
			room_plant   	: "1"
		};

	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchGridRmToMealsMgmtList.do",
			colNames     : ["Apply Date", "Status", "Requestor", "ID No.", "Reservation Date/Time", "Rooms", "Coffee", "Porcelain", "QTY", "Total", "Cost Center", "Remarks", "Doc No.", "Status", "PO No."],
			colModel     : [
			                {name:"ptt_ymd",		index:"ptt_ymd"			, sortable:false,		formatter:"string",	width:"120",	align:"center",	editable:false,	frozen:false},
			                {name:"pgs_st_nm",		index:"pgs_st_nm"		, sortable:false,		formatter:"string",	width:"60",	align:"center",	editable:false,	frozen:false},
			                {name:"req_eeno_nm",	index:"req_eeno_nm"		, sortable:false,		formatter:"string",	width:"150",align:"left",	editable:false,	frozen:false},
			                {name:"req_eeno",		index:"req_eeno"		, sortable:false,		formatter:"string",	width:"80",	align:"center",	editable:false,	frozen:false},
			            	{name:"req_info",		index:"req_info"		, sortable:false,		formatter:"string",	width:"190",align:"center",	editable:false,	frozen:false},
			    			{name:"room_place",		index:"room_place"		, sortable:false,		formatter:"string",	width:"120",align:"left",	editable:false,	frozen:false},
			    			{name:"room_meal",		index:"room_meal"		, sortable:false,		formatter:"string",	width:"120",align:"left",	editable:false,	frozen:false},
			    			{name:"tot",			index:"tot"				, sortable:false,		formatter:"string",	width:"70",	align:"center",	editable:false,	frozen:false},
			    			{name:"room_qty",		index:"room_qty"		, sortable:false,		formatter:"string",	width:"50",	align:"center",	editable:false,	frozen:false},
			    			{name:"tot_amt",	index:"tot_amt"	, sortable:false,formatter: 'currency', formatoptions: {
			    			    decimalSeparator:",",
			    			    thousandsSeparator:".",
			    			    decimalPlaces:2,
			    			    defaultValue:"",
			    			    suffix:" R$"
			    			  },		width:80,	align:"right",	editable:false,	frozen : false},
			    			{name:"cost_cd",		index:"cost_cd"			, sortable:false,		formatter:"string",	width:"80",	align:"center",	editable:false,	frozen:false},
			    			{name:"note",			index:"note"			, sortable:false,		formatter:"string",	width:"180",	align:"left",	editable:false,	frozen:false},
			            	{name:"doc_no",			index:"doc_no"			, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			            	{name:"pgs_st_cd2",		index:"pgs_st_cd2"		, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			            	{name:"po_no",			index:"po_no"			, sortable:false,		formatter:"string",	width:"80",		align:"center",	editable:false,	frozen:false}
			            	],
			height       : "100%",
			rownumbers   : true, 
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
	
	//method overliding
	jQuery("#"+gridName).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	setGridColumnOptions(gridName);
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "cancel" :
			doCancel();
			break;
		case "submit" :
			doSubmit();
			break;
		case "ongoing" :
			doOngoing();
			break;
		case "done" :
			doDone();
			break;
		case "excel" :
			doExcel();
			break;
	}
}

function doSearch(msgFlag){
	var params = {
			req_eeno 		: $("#key_eeno").val(),
			req_eeno_nm 	: $("#key_eenm").val(),
			fr_ymd 			: dateConversionKr(selectNum($("#key_from_date").val())),
			to_ymd 			: dateConversionKr(selectNum($("#key_to_date").val())),
			req_dept_nm     : $("#key_ops_nm").val(),
			pgs_st_cd		: $("#ipt_pgs_st_cd").val(),
			pgs_st_cd2  	: $("#key_pgs_st_cd").val(),
			room_plant   	: "1"
	}; 
	    
	doCommonSearch("doSearchGridRmToMealsMgmtList.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}
function loadCallBack(){
	addGridRow();
}

function doSubmit(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return false;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(getColValue("pgs_st_cd2" , rowId, gridName) != "A" || getColValue("pgs_st_cd2" , rowId, gridName) == "E"){
				alertUI("You can't submit.");
				return;
			}
			if(rowId){
				list ={
						doc_no    : getColValue("doc_no", rowId, gridName),
						pgs_st_cd2 : 'B'
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("Do you want to submit?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doUpdateRmToMealsMgmtSubmit.do", paramData, "submitCallBack(jsonData.sendResult);");
			}
		});
	});
}

function submitCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

function doOngoing(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return false;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(getColValue("pgs_st_cd2" , rowId, gridName) != "B" || getColValue("pgs_st_cd2" , rowId, gridName) == "E"){
				alertUI("You can't on going.");
				return;
			}
			if(rowId){
				list ={
						doc_no    : getColValue("doc_no", rowId, gridName),
						pgs_st_cd2 : 'C'
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("Do you want to on going?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doUpdateRmToMealsMgmtOngoing.do", paramData, "ongoingCallBack(jsonData.sendResult);");
			}
		});
	});
}

function ongoingCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}


function doDone(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return false;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(getColValue("pgs_st_cd2" , rowId, gridName) != "C" || getColValue("pgs_st_cd2" , rowId, gridName) == "E"){
				alertUI("You can't done.");
				return;
			}
			if(rowId){
				list ={
						doc_no    : getColValue("doc_no", rowId, gridName),
						pgs_st_cd2 : 'D'
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("Do you want to done?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doUpdateRmToMealsMgmtDone.do", paramData, "doneCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doneCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

function doCancel(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return false;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(getColValue("pgs_st_cd2" , rowId, gridName) == "E"){
				alertUI("You can't cancel.");
				return;
			}
			if(rowId){
				list ={
					doc_no     : getColValue("doc_no", rowId, gridName),
					pgs_st_cd2 : 'E'
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("Do you want to cancel?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doUpdateRmToMealsMgmtCancel.do", paramData, "cancelCallBack(jsonData.sendResult);");
			}
		});
	});
}

function cancelCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

function doExcel(){
	var keyData = {
			req_eeno 		: $("#key_eeno").val(),
			req_eeno_nm 	: $("#key_eenm").val(),
			fr_ymd 			: dateConversionKr(selectNum($("#key_from_date").val())),
			to_ymd 			: dateConversionKr(selectNum($("#key_to_date").val())),
			req_dept_nm     : $("#key_ops_nm").val(),
			pgs_st_cd2  	: $("#key_pgs_st_cd").val(),
			room_plant   	: "1"
	};
	
	var params = [
		{name : "fileName",		value : "excel" },
		{name : "header",		value : util.jsonToArray(excelCn)},
		{name : "headerName",	value : util.jsonToArray(excelCm)},
		{name : "fomatter",		value : util.jsonToArray(excelFm)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "firstUseYn",	value : "Y"},
		{name : "page",			value : "1"}
	];
	
	gridToExcel("#"+gridName, "doExcelToMealsMgmtList.excel", params);
}