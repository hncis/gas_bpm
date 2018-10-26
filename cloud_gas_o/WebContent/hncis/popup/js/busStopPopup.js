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
var cn        = ["Bus Stop", "Fare","", ""];
var datarow   = {stp_nam:"",bus_fre:"", bus_stp_id:"", stp_cde:""};
var cm =
[
	{name:"stp_nam",		index:"stp_nam"	, sortable:false,			formatter:"string",	width:280,	align:"left",	editable:false,	frozen:false},
	{name:"bus_fre",	index:"bus_fre"	, sortable:false,			formatter:"string",	width:59,	align:"left",	editable:false,	frozen:false},
	{name:"bus_stp_id",	index:"bus_stp_id"	, sortable:false,			formatter:"string",	width:0,	align:"left",	editable:false, hidden: true, frozen:false},
	{name:"stp_cde",	index:"stp_cde"	, sortable:false,			formatter:"string",	width:0,	align:"left",	editable:false, hidden: true, frozen:false}

];
function init(){
	//set grid parameter
	
	var params = {
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "",
			colNames     : cn,
			colModel     : cm,
			height       : 232,
			width		 : 374,
			rowNum       : 10,
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(10);",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
	doSearch();
	
/*	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("stp_nam", rowid, gridName1) != ""){
				opener.$("#"+dcd).val(getColValue("dcd", rowid, gridName1));
				opener.$("#"+dcdNm).val(getColValue("dcdName", rowid, gridName1));
				selfClose();
			}
		}
	}).trigger('reloadGrid');*/
	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("stp_nam", rowid, gridName1) != ""){
				var stp_nam_val = getColValue("stp_nam", rowid, gridName1);
				var stp_cde_val = getColValue("stp_cde", rowid, gridName1);
				var bus_fre_val = getColValue("bus_fre", rowid, gridName1);
				if($("#onText").val()=="Y"){
					opener.$("#bst_cd").val(stp_cde_val);
					opener.$("#bst_nm").val(stp_nam_val);
					opener.$("#fare").val(bus_fre_val);
				}else{
					opener.$("#"+gridName1).jqGrid('setRowData', rowId, {
						stp_nam:stp_nam_val, 
						stp_cde: stp_cde_val,
						bus_fre: bus_fre_val
						});
				}

				selfClose();
			}
		}
	}).trigger('reloadGrid');
}


function doSearch(){
/*	if($("#busStpNme").val() == ""){
		alertUI("Please enter bus stop name");
		$("#busStpNme").focus();
		return;
	}*/
	
	var busStopCondition = "%"+$("#busStpNme").val()+"%";
	var params = {
		stp_nam       : busStopCondition
	};
	
	doCommonSearch("/sk/hncis/busTransportation/doSearchDirectBusStop.do", util.jsonToString(params), "loadCallBack();", gridName1, "N");
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

function afterClose(){
	
}