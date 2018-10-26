function fnSetDocumentReady(){
	initMenus();
	$('#BT').slideDown('fast');
	setComboInfo();
}

function setComboInfo(){
	var url        = "/hncis/businessTravel/doComboListToRequest.do";
	var paramValue    = "nat_g_scn_cd:X0024:S;";
	getCommonCode(paramValue, "N", "setComboInfoGrid();", url);
}

function setComboInfoGrid(){
	var url        = "/getCommonCombo.do";
	getCommonCode('nat_g_scn_cd:X0024', 'Y', 'init();');
}

var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {seq:"", nat_g_scn_cd_old:"", nat_g_scn_cd:"", poa_g1_amt:"", poa_g6_amt:"", poa_g2_amt:"", poa_g3_amt:"", poa_g4_amt:""};
function init(){
	var params = {
		};
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchMaintenanceExpenseManagement.do",
			colNames     : ["", "", "Region", "Supplier", "Address", "Zip Code", "Phone", "Work Time"],
			colModel     : [{name:"seq",	index:"seq"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:"nat_g_scn_cd_old",	index:"nat_g_scn_cd_old"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:'nat_g_scn_cd',		index:'nat_g_scn_cd'		, sortable:false,		formatter: "select",	width:140,	align:'center',	editable:true,	edittype:'select', 
			    				editoptions:{value:getComboValue('nat_g_scn_cd'), dataInit: function(elem) {$(elem).width(140);}
			    			        },
			    			        editrules:{required:true}
			    			},
			            	{name:"poa_g1_amt",		index:"poa_g1_amt"		, sortable:false,		formatter: 'string', width:"160",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g6_amt",		index:"poa_g6_amt"		, sortable:false,		formatter: 'string', width:"290",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g2_amt",		index:"poa_g2_amt"		, sortable:false,		formatter: 'string', width:"120",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g3_amt",		index:"poa_g3_amt"		, sortable:false,		formatter: 'string', width:"140",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g4_amt",		index:"poa_g4_amt"		, sortable:false,		formatter: 'string', width:"100",	align:"left",	editable:true,	frozen:false}
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
	
	commonJqGridInit(gridParam);
	jQuery("#"+gridName).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	setGridColumnOptions(gridName);
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "save" :
			doSave();
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
		nat_g_scn_cd 	: 	$("#nat_g_scn_cd").val(),
		corp_cd			: sess_corp_cd
	};
	
	doCommonSearch("doSearchMaintenanceExpenseManagement.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}

function doSave(){
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
				if(getColValue("nat_g_scn_cd" , rowId, gridName) == ""){
					alertUI("Please select country group.");
					return;
				}else if(getColValue("poa_g1_amt" , rowId, gridName) == ""){
					alertUI("Please enter the cost of Director.");
					return;
				}else if(getColValue("poa_g2_amt" , rowId, gridName) == ""){
					alertUI("Please enter the cost of General Manager.");
					return;
				}else if(getColValue("poa_g3_amt" , rowId, gridName) == ""){
					alertUI("Please enter the cost of Manager.");
					return;
				}else if(getColValue("poa_g4_amt" , rowId, gridName) == ""){
					alertUI("Please enter the cost of Others.");
					return;
				}
				
				if(getColValue("nat_g_scn_cd_old", rowId, gridName) != ""){
					list ={
						nat_g_scn_cd   		: getColValue("nat_g_scn_cd" , rowId, gridName),
						nat_g_scn_cd_old	: getColValue("nat_g_scn_cd_old" , rowId, gridName),
						seq   				: getColValue("seq" , rowId, gridName),
						poa_g1_amt   		: getColValue("poa_g1_amt" , rowId, gridName),
						poa_g2_amt   		: getColValue("poa_g2_amt" , rowId, gridName),
						poa_g3_amt   		: getColValue("poa_g3_amt" , rowId, gridName),
						poa_g4_amt   		: getColValue("poa_g4_amt" , rowId, gridName),
						poa_g6_amt   		: getColValue("poa_g6_amt" , rowId, gridName),
						updr_eeno   		: sess_empno,
						corp_cd				: sess_corp_cd
					};
					paramsU.push(list);
				}else{
					list ={
						nat_g_scn_cd   		: getColValue("nat_g_scn_cd" , rowId, gridName),
						seq   				: getColValue("seq" , rowId, gridName),
						poa_g1_amt   		: getColValue("poa_g1_amt" , rowId, gridName),
						poa_g2_amt   		: getColValue("poa_g2_amt" , rowId, gridName),
						poa_g3_amt   		: getColValue("poa_g3_amt" , rowId, gridName),
						poa_g4_amt   		: getColValue("poa_g4_amt" , rowId, gridName),
						poa_g6_amt   		: getColValue("poa_g6_amt" , rowId, gridName),
						ipe_eeno    		: sess_empno,
						updr_eeno   		: sess_empno,
						corp_cd				: sess_corp_cd
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
				doCommonAjax("doSaveMaintenanceExpenseManagement.do", paramData, "saveCallBack(jsonData.sendResult);");
			}
		});
	});
}

function saveCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
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
				if(getColValue("seq", rowId, gridName) == ""){
					alertUI(rowId + " line : There is no data.");
					return;
				}
				
				list ={
					nat_g_scn_cd   		: getColValue("nat_g_scn_cd_old" , rowId, gridName),
					seq   				: getColValue("seq" , rowId, gridName),
					corp_cd				: sess_corp_cd
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
					doCommonAjax("doDeleteMaintenanceExpenseManagement.do", paramData, "deleteCallBack(jsonData.sendResult);");
			}
		});
	});
}

function deleteCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	for(var i = 1; i <= 10 - gridRowId ; i++){
		jQuery("#"+gridName).jqGrid("addRowData", gridRowId+i, datarow);
	}
}

function loadCallBack(){
	addGridRow();
}
function celldbClickAction(rowId,iRow,iCol,e){
	
}

function fnAddRow(){
	var gridRowId = $("#"+gridName).getDataIDs().length;
	jQuery("#"+gridName).jqGrid("addRowData", gridRowId+1, datarow);
}
