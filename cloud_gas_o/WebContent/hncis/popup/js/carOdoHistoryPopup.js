function fnSetDocumentReady(){
	getCommonCode("keyCarType:X1001:A;", "N", "init();");
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
var cn        = ["Car Type", "Car No", "Mileage", "Driver ID", "Update"];
var datarow   = {car_type_nm:"",car_rgst_no:"",odo_mlg:"", car_mng_id:"", odo_lst_udt:""};
var cm =
[
	{name:"car_type_nm", index:"car_type_nm", sortable:false, formatter:"string",	width:80,	align:"center",	editable:false,	frozen:false},
	{name:"car_rgst_no", index:"car_rgst_no", sortable:false, formatter:"string",	width:80,	align:"left",	editable:false,	frozen:false},
	{name:"odo_mlg", index:"odo_mlg", sortable:false, formatter:"string",	width:100,	align:"right",	editable:false,	frozen:false},
	{name:"car_mng_id", index:"car_mng_id", sortable:false, formatter:"string",	width:80,	align:"center",	editable:false,	frozen:false},
	{name:"odo_lst_udt", index:"odo_lst_udt", sortable:false, formatter:"string",	width:109,	align:"center",	editable:false,	frozen:false}
];
function init(){
	//set grid parameter
	
	var params = {
			car_mng_id : $("#keyCarMngId").val(),
			car_type : $("#keyCarType").val(), 
			car_rgst_no : $("#keyCarNo").val()
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "/hncis/poolCar/doSelectToODOHistoryListPerDriverId.do",
			colNames     : cn,
			colModel     : cm,
			height       : 232,
			width		 : 500,
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
	
	/*
	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("car_mng_id", rowid, gridName1) != ""){
				opener.$("#keyEeno").val(getColValue("car_mng_id", rowid, gridName1));
				opener.doSearchUserInfo();
				selfClose();
			}
		}
	}).trigger('reloadGrid');
	 */
}//end method


function doSearch(){
	
	/*
	if($("#key_driver_name").val() == ""){
		alertUI("Please enter your driver name.");
		$("#key_driver_name").focus();
		return;
	}
	*/
	var params = {
			car_mng_id : $("#keyCarMngId").val(),
			car_type : $("#keyCarType").val(), 
			car_rgst_no : $("#keyCarNo").val()
		};
		
	doCommonSearch("../../hncis/poolCar/doSelectToODOHistoryListPerDriverId.do", util.jsonToString(params), "loadCallBack();", gridName1, "N");
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
