function fnSetDocumentReady(){
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
var datarow  = {seq:"", nat_g_scn_cd_old:"", nat_g_scn_cd:"", poa_g1_amt:"", poa_g2_amt:"", poa_g3_amt:"", poa_g4_amt:"", poa_g6_amt:""};
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
			            	{name:"poa_g1_amt",		index:"poa_g1_amt"		, sortable:false,		formatter: 'string', width:"150",	align:"left",	editable:true,	frozen:false},
				            {name:"poa_g6_amt",		index:"poa_g6_amt"		, sortable:false,		formatter: 'string', width:"290",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g2_amt",		index:"poa_g2_amt"		, sortable:false,		formatter: 'string', width:"115",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g3_amt",		index:"poa_g3_amt"		, sortable:false,		formatter: 'string', width:"115",	align:"left",	editable:true,	frozen:false},
			            	{name:"poa_g4_amt",		index:"poa_g4_amt"		, sortable:false,		formatter: 'string', width:"105",	align:"left",	editable:true,	frozen:false}
			            	],
			height       : "100%",
			width        : "970",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();",
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
	}
}

function doSearch(msgFlag){
	
	var params = {
		nat_g_scn_cd 	: 	$("#nat_g_scn_cd").val()
	};
	
	doCommonSearch("doSearchMaintenanceExpenseManagement.do", util.jsonToString(params), "loadCallBack();", gridName, msgFlag);
}

function loadCallBack(){
	addGridRow();
}
function celldbClickAction(rowId,iRow,iCol,e){
	
}
