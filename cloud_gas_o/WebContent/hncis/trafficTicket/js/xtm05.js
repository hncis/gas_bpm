function fnSetDocumentReady(){
	initMenus();
	$('#GS').slideDown('fast');
	
	init();
}

var params;
var fnMerge;
var comboVal;
var gridParam;
var gridName = "htmlTable";
var datarow  = {desc_code_old:"",desc_code:"",desc_name:"",tic_pint:"",tic_amt:"",sort:"",use_flag:"",tic_desc:""};
function init(){
	
	//set grid parameter
	var params = {
		desc_code : $("#key_desc_code").val()
	};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchByXtm05.do",
			colNames     : ["", "Code", "Desc.", "Point", "Amount", "Sort Number", "Use Type"],
			colModel     : [{name:"desc_code_old",	index:"desc_code_old"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:"desc_code",		index:"desc_code"		, sortable:false,		formatter:"string",	width:"100",	align:"left",	editable:true},
			                {name:"tic_desc",		index:"tic_desc"		, sortable:false,		formatter:"string",	width:"530",	align:"left",	editable:true},	
			            	{name:"tic_pint",		index:"tic_pint"		, sortable:false,		formatter:"string",	width:"80",	align:"right",	editable:true,	frozen:false},
			            	{name:"tic_amt",		index:"tic_amt"			, sortable:false,		formatter:numFormat,width:"80",	align:"right",	editable:true,	frozen:false},
			            	{name:"sort",			index:"sort"			, sortable:false,		formatter:"string",	width:"80",	align:"center",	editable:true,	frozen:false},
			            	{name:"use_flag",		index:"use_flag"		, sortable:false,		edittype:"select", formatter:"select",	width:"80", align:"center",	editable:true,	
			            		editoptions:{value:"Y:Y;N:N"}
			            	}
			            	],
			height       : "100%",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "save" :
			doInsert();
			break;
		case "delete" :
			doDelete();
			break;
		case "addrow" :
			fnAddRow();
			break;
	}
}

function doSearch(msgFlag){
	
	var params = {
		desc_code : $("#key_desc_code").val()
	}; 
	   
	doCommonSearch("doSearchByXtm05.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}

function doInsert(){
	var paramsI = [];	
	var paramsU = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("desc_code" , rowId, gridName) == ""){
					alertUI("Please enter code.");
					return;
				}else if(getColValue("desc_name" , rowId, gridName) == ""){
					alertUI("Please enter Text.");
					return;
				}
				
				if(getColValue("desc_code_old", rowId, gridName) != ""){
					list ={
						desc_code_old : getColValue("desc_code_old", rowId, gridName),
						desc_code     : getColValue("desc_code"    , rowId, gridName),
						tic_desc	  : getColValue("tic_desc"    , rowId, gridName),
						tic_pint      : getColValue("tic_pint"     , rowId, gridName),
						tic_amt       : getColValue("tic_amt"      , rowId, gridName),
						sort          : getColValue("sort"         , rowId, gridName),
						use_flag      : getColValue("use_flag"     , rowId, gridName),
						ipe_eeno      : sess_empno,
						updr_eeno     : sess_empno
					};
					paramsU.push(list);
				}else{
					list ={
						desc_code     : getColValue("desc_code", rowId, gridName),
						tic_desc	  : getColValue("tic_desc"    , rowId, gridName),
						tic_pint      : getColValue("tic_pint" , rowId, gridName),
						tic_amt       : getColValue("tic_amt"  , rowId, gridName),
						sort          : getColValue("sort"     , rowId, gridName),
						use_flag      : getColValue("use_flag" , rowId, gridName),
						ipe_eeno      : sess_empno
					};
					paramsI.push(list);
				}
			}
		}
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						iParams : util.jsonToList(paramsI),
						uParams	: util.jsonToList(paramsU)
					};
					doCommonAjax("doSaveByXtm05.do", paramData, "submitCallBack('save', jsonData.sendResult);");
			}
		});
	});
}

function doDelete(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("desc_code_old"  , rowId, gridName) == ""){
					alertUI(rowId + " line : There is no data.");
					return;
				}
				list ={
					desc_code : getColValue("desc_code_old", rowId, gridName)
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doDeleteByXtm05.do", paramData, "submitCallBack('delete', jsonData.sendResult);");
			}
		});
	});
}

function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	for(var i = 1; i <= 10 - gridRowId ; i++){
		jQuery("#"+gridName).jqGrid("addRowData", gridRowId+i, datarow);
	}
}

/**
 * callback
 */
function loadCallBack(){
	addGridRow();
}

function submitCallBack(id, result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

function fnAddRow(){
	var gridRowId = $("#"+gridName).getDataIDs().length;
	jQuery("#"+gridName).jqGrid("addRowData", gridRowId+1, datarow);
}
