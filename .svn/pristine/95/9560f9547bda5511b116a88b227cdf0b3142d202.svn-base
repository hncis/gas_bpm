function fnSetDocumentReady(){
	init();
}

var params; 
var comboVal;
var lastsel;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var gridPage = "htmlPager";
var datarow  = {cnf_title:""};
function init(){
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchTitleByXsm.do",
			colNames     : ["상용구 제목"],
			colModel     : [
			                {name:"cnf_title", index:"cnf_title", sortable:false, formatter:"string", width:330, align:"left", editable:false, frozen:false}
			                ],
			height       : 230,
			width		 : 380,
			rowNum       : 10,
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : gridPage,
			completeFc   : "addGridRow(10);"
		}]
	};
	
	commonJqGridInit(gridParam);
	
	jQuery("#"+gridName).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("cnf_title", rowid, gridName) != ""){
				var array = {
					cnf_title : getColValue("cnf_title", rowid, gridName)
				};
				opener.setCnfTitle(array);
				selfClose();
			}
		}
	}).trigger('reloadGrid');
}


function doSearch(){
	doCommonSearch("doSearchTitleByXsm.do", util.jsonToString(""), "loadCallBack();", gridName);
}

function loadCallBack(){
	addGridRow();
}