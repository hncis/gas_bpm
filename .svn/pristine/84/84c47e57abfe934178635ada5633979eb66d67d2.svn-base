function fnSetDocumentReady(){
	init();
}

/**
 * process init() loading
 */
var params; 
var comboVal;
var lastsel;
var fnMerge;
var gridParam;
var gridName1 = "htmlTable";
var cn        = ["Name of job", "Id", "Name", "Department", "Phone number", "Sort"];
var datarow   = {xdsm_gumn_nm:"",xdsm_empno:"",xdsm_empnm:"",xdsm_dept_name:"",xdsm_hpno:"",xdsm_mgmt_scn_cd:""};
var cm =
[
	{name:"xdsm_gumn_nm", index:"xdsm_gumn_nm", sortable:false, formatter:"string", width:240, align:"left",	editable:false,	frozen:false},
	{name:"xdsm_empno", index:"xdsm_empno", sortable:false, formatter:"string", width:100, align:"left",	editable:false,	frozen:false, hidden:true},
	{name:"xdsm_empnm", index:"xdsm_empnm", sortable:false, formatter:"string", width:190, align:"left",	editable:false,	frozen:false},
	{name:"xdsm_dept_name", index:"xdsm_dept_name", sortable:false, formatter:"string", width:120, align:"left",	editable:false,	frozen:false},
	{name:"xdsm_hpno", index:"xdsm_hpno", sortable:false, formatter:"string", width:141, align:"left",	editable:false,	frozen:false},
	{name:"xdsm_mgmt_scn_cd", index:"xdsm_mgmt_scn_cd", sortable:false, formatter:"string", width:141, align:"left",	editable:false,	frozen:false, hidden:true}
];
function init(){
	//set grid parameter
	
	var params = {
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "/doSearchJobManagement.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			width		 : 736,
			rownumbers   : true,
			rowNum		 : 15,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(15);",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
	
	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("dcd", rowid, gridName1) != ""){
				opener.$("#"+dcd).val(getColValue("dcd", rowid, gridName1));
				opener.$("#"+dcdNm).val(getColValue("dcdName", rowid, gridName1));
				selfClose();
			}
		}
	}).trigger('reloadGrid');
}


function doSearch(){
	if($("#key_dept_name").val() == ""){
		alertUI("Please enter your department name");
		$("#key_dept_name").focus();
		return;
	}
	
	var params = {
		dcdName       : $("#key_dept_name").val()
	};
	
	//doCommonSearch("../../doSearchDeptCode.do", util.jsonToString(params), "loadCallBack();", gridName1, "N");
}

/**
 * callback
 */
function loadCallBack(){
	addGridRow();
}


function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
	}
}
