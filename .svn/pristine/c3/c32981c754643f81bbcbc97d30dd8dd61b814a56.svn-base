function fnSetDocumentReady(){
	initMenus();
	$('#EM').slideDown('fast');
	
	getCommonCode("key_code_knd:XEMCD;", "N", "init();");    
}

/**
 * process init() loading
 */
var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {xcod_knd_old:"", xcod_code_old:"", xcod_knd:"", xcod_code:"", xcod_hname:"", xcod_order:"", xcod_aply_flag:""};
function init(){
 
	//set grid parameter
	var params = {
			xcod_knd : 	$("#key_code_knd").val().toUpperCase()
		};
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "/hncis/system/doSearchCodeManagement.do",
			colNames     : ["", "", "Code Kind", "Code", "Text", "Sort Number", "Use Type"],
			colModel     : [{name:"xcod_knd_old",	index:"xcod_knd_old"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:"xcod_code_old",	index:"xcod_code_old"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:"xcod_knd",		index:"xcod_knd"		, sortable:false,		formatter:"string",	width:"120",	align:"center",	editable:true,	frozen:false, hidden : true},
			            	{name:"xcod_code",		index:"xcod_code"		, sortable:false,		formatter:"string",	width:"200",	align:"center",	editable:true,	frozen:false},
			            	{name:"xcod_hname",		index:"xcod_hname"		, sortable:false,		formatter:"string",	width:"460",	align:"left",	editable:true,	frozen:false},
			            	{name:"xcod_order",		index:"xcod_order"		, sortable:false,		formatter:"string",	width:"150",	align:"center",	editable:true,	frozen:false},
			            	{name:"xcod_aply_flag",	index:"xcod_aply_flag"	, sortable:false,		edittype:"select", formatter:"select",	width:"150", align:"center",	editable:true,	
			            		editoptions:{value:"Y:Y;N:N"}
			            	}
			            	],
			height       : "100%",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			completeFc   : "addGridRow();initAfterMenus();",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);",
			pager		 : "htmlPager",
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
		case "save" :
			doInsert();
			break;
		case "edit" :
			doModify();
			break;
		case "delete" :
			doDelete();
			break;
	}
}

function doSearch(msgFlag){
	
	if($("#key_code_knd").val() == ""){
		alertUI("Please enter code.");
		$("#key_code_knd").focus();
		return;
	}
	var params = {
		xcod_knd : 	$("#key_code_knd").val().toUpperCase(),
		locale	 : sess_locale
	}; 
	   
	doCommonSearch("../../hncis/system/doSearchCodeManagement.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
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
				if(getColValue("xcod_code" , rowId, gridName) == ""){
					alertUI("Please enter code.");
					return;
				}else if(getColValue("xcod_hname" , rowId, gridName) == ""){
					alertUI("Please enter Text.");
					return;
				}
				
				if(getColValue("xcod_knd_old", rowId, gridName) != ""){
					list ={
						xcod_knd      	: getColValue("xcod_knd"  , rowId, gridName),
						xcod_knd_old   	: getColValue("xcod_knd_old"  , rowId, gridName),
						xcod_code   	: getColValue("xcod_code" , rowId, gridName),
						xcod_code_old  	: getColValue("xcod_code_old" , rowId, gridName),
						xcod_hname      : changeToUni(getColValue("xcod_hname", rowId, gridName)),
						xcod_order      : getColValue("xcod_order", rowId, gridName),
						xcod_aply_flag  : getColValue("xcod_aply_flag", rowId, gridName),
						ipe_eeno    	: sess_empno,
						updr_eeno   	: sess_empno,
						locale			: sess_locale
					};
					paramsU.push(list);
				}else{
					list ={
						xcod_knd      	: $("#key_code_knd").val().toUpperCase(),
						xcod_code   	: getColValue("xcod_code" , rowId, gridName),
						xcod_hname      : changeToUni(getColValue("xcod_hname", rowId, gridName)),
						xcod_order      : getColValue("xcod_order", rowId, gridName),
						xcod_aply_flag  : getColValue("xcod_aply_flag", rowId, gridName),
						ipe_eeno    	: sess_empno,
						updr_eeno   	: sess_empno,
						locale			: sess_locale
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
						paramsI : util.jsonToList(paramsI),
						paramsU	: util.jsonToList(paramsU)
					};
				doCommonAjax("/hncis/system/doInsertCodeManagement.do", paramData, "submitCallBack('save', jsonData.sendResult);");
			}
		});
	});
}

function doModify(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("xcod_knd" , rowId, gridName) == ""){
					alertUI("Please enter code kind.");
					return;
				}else if(getColValue("xcod_code" , rowId, gridName) == ""){
					alertUI("Please enter code.");
					return;
				}else if(getColValue("xcod_hname" , rowId, gridName) == ""){
					alertUI("Please enter Text.");
					return;
				}
				list ={
					xcod_knd      	: getColValue("xcod_knd"  , rowId, gridName),
					xcod_code   	: getColValue("xcod_code" , rowId, gridName),
					xcod_hname      : getColValue("xcod_hname", rowId, gridName),
					xcod_order      : getColValue("xcod_order", rowId, gridName),
					xcod_aply_flag  : getColValue("xcod_aply_flag", rowId, gridName),
					updr_eeno   	: sess_empno
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("수정하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("/hncis/system/doModifyCodeManagement.do", paramData, "submitCallBack('edit', jsonData.sendResult);");
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
				list ={
					xcod_knd      	: getColValue("xcod_knd_old"  , rowId, gridName),
					xcod_code   	: getColValue("xcod_code_old" , rowId, gridName)
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
					doCommonAjax("/hncis/system/doDeleteCodeManagement.do", paramData, "submitCallBack('delete', jsonData.sendResult);");
			}
		});
	});
}

/**
 * addRow $.IDs.length + 1
 * datarow colModel keyName:"", ....
 */
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
function celldbClickAction(rowId,iRow,iCol,e){
	if(getColValue("xcod_knd",rowId) == "00000"){
		$("#key_code_knd").val(getColValue("xcod_code",rowId));
		doSearch();
	}
}
