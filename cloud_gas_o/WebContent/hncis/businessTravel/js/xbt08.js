function fnSetDocumentReady(){
	initMenus();
	$('#BT').slideDown('fast');
	init();
	
	sess_auth = $("#work_auth").val();
	chk_auth();
}

var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {cost_cd:"", cost_nm:"", budg_no:"", balance_amt:"", budg_result:"", prvs_amt:"", natv_curr_amt:""};
function init(){
	var params = {
		};
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "/doSearchToEmpty.do",
			colNames     : ["Cost Center", "Cost Center Name", "Budget No.", "Balance", "Result", "Commitment", "Actual"],
			colModel     : [{name:"cost_cd",	index:"cost_cd"	, sortable:false,		formatter:"string",	width:"80",		align:"center",	editable:false,	frozen:false},
			                {name:"cost_nm",	index:"cost_nm"	, sortable:false,		formatter:"string",	width:"150",		align:"center",	editable:false,	frozen:false},
			                {name:"budg_no",	index:"budg_no"	, sortable:false,		formatter:"string",	width:"100",		align:"center",	editable:false,	frozen:false},
			                {name:"balance_amt",		index:"balance_amt"		, sortable:false,		formatter: 'currency', formatoptions: {
			    			    decimalSeparator:",",
			    			    thousandsSeparator:".",
			    			    decimalPlaces:2,
			    			    defaultValue:"",
			    			    suffix:" R$"
			    			  },width:"80",	align:"right",	editable:false,	frozen:false},
			    			{name:"budg_result",	index:"budg_result"	, sortable:false,		formatter:"string",	width:"250",		align:"left",	editable:false,	frozen:false},
			                {name:"prvs_amt",		index:"prvs_amt"		, sortable:false,		formatter: 'currency', formatoptions: {
			    			    decimalSeparator:",",
			    			    thousandsSeparator:".",
			    			    decimalPlaces:2,
			    			    defaultValue:"",
			    			    suffix:" R$"
			    			  },width:"160",	align:"right",	editable:false,	frozen:false},
			    			{name:"natv_curr_amt",		index:"natv_curr_amt"		, sortable:false,		formatter: 'currency', formatoptions: {
				    			    decimalSeparator:",",
				    			    thousandsSeparator:".",
				    			    decimalPlaces:2,
				    			    defaultValue:"",
				    			    suffix:" R$"
				    		},width:"150",	align:"right",	editable:false,	frozen:false}
			            	],
			height       : "100%",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();",
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
		case "wbs" :
			doSearchWbs();
			break;
		case "io" :
			doSearchIo();
			break;
	}
}

function doSearch(msgFlag){
	
	if($("#cost_cd").val() == ""){
		alertUI("Please enter the cost center");
		$("#cost_cd").focus();
		return;
	}else if($("#budg_no").val() == ""){
		alertUI("Please enter the budget No.");
		$("#budg_no").focus();
		return;
	}
	var d = new Date();
	var params = {
		cost_cd 	: 	$("#cost_cd").val()	,
		budg_no 	: 	$("#budg_no").val()	,
		budg_type	:   "D",
		key_year	: d.getFullYear(),
		corp_cd		: sess_corp_cd
	};
	
	doCommonSearch("doSearchBudgetViewNew.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}

function doSearchWbs(msgFlag){
	if($("#cost_cd").val() == ""){
		alertUI("Please enter the cost center");
		$("#cost_cd").focus();
		return;
	}else if($("#budg_no").val() == ""){
		alertUI("Please enter the WBS Code.");
		$("#budg_no").focus();
		return;
	}
	
	var params = {
		cost_cd 	: 	$("#cost_cd").val()	,
		budg_no 	: 	$("#budg_no").val()	,
		budg_type	:   "W",
		corp_cd		: sess_corp_cd
	};
	
	doCommonSearch("doSearchBudgetViewNew.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}

function doSearchIo(msgFlag){
	if($("#cost_cd").val() == ""){
		alertUI("Please enter the cost center");
		$("#cost_cd").focus();
		return;
	}else if($("#budg_no").val() == ""){
		alertUI("Please enter the Internal Order Code.");
		$("#budg_no").focus();
		return;
	}
	
	var params = {
		cost_cd 	: 	$("#cost_cd").val()	,
		budg_no 	: 	$("#budg_no").val()	,
		budg_type	:   "I",
		corp_cd		: sess_corp_cd
	};
	
	doCommonSearch("doSearchBudgetViewNew.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}

function loadCallBack(){
	addGridRow();
}

function chk_auth(){
	var frm = document.mainForm;
	$("#cost_cd").val(sess_cost_cd);
	with(frm){
		if(Number(sess_auth) > 4 || sess_mstu == "M"){
			readOnlyStyle("cost_cd", 2);
		}else{
			readOnlyStyle("cost_cd", 1);
		}
	}
}