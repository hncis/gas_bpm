var fnMerge;
var gridParam;
var gridName1 = "htmlTable";
var datarow = {route_name:"", route_cidade:"", route_sort:"",  route_point:"", route_time:""};

function fnSetDocumentReady(){
	initCombo();
}

function initCombo(){
	getCommonCode("key_linha:Z:0", "N", "init()", "/hncis/shuttleBus/getSearchShuttleBusLineCombo.do");
}

function init(){
	var cn = ["Name", "CIDADE", "PONTO.", "PONTO DE PARADA DO ONIBUS", "HORARIO"];
	var cm =
		[
		 {name:"route_name",	index:"route_name",		sortable:false,		formatter:"string",		width:250,	align:"left",	editable:false,	frozen : false},
		 {name:"route_cidade",	index:"route_cidate",	sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false},
		 {name:"route_sort",	index:"route_sort",		sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false},
		 {name:"route_point",	index:"route_point",	sortable:false,		formatter:"string",		width:430,	align:"left",	editable:false,	frozen : false},
		 {name:"route_time",	index:"route_time",		sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false}
	];
	
	var params = {
		type 		: $("#key_linha").val(),
		f_levl		: "1"
	};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchPonteExistentePopupList.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			paramJson    : params,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();"
		}]
	};
	
	//core jqGrid call...
	commonJqGridInit(gridParam);
	jQuery("#"+gridName1).jqGrid("setFrozenColumns");
	jQuery("#"+gridName1).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});

}

function doSearch(){
	var params = {
		type 		: $("#key_linha").val(),
		f_levl		: "1"
	};
	
	doCommonSearch("doSearchPonteExistentePopupList.do", util.jsonToString(params), "addGridRow();", gridName1, "N");
}

