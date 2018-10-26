jQuery(document).ready(function(){
	initMenus();
	$(".inputOnlyNumber").numeric();
	setComboInfo();
});

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var pgs_st_cd  = "key_pgs_st_cd:X0031:A;key_plac_work:X0004:;";
	var paramValue = pgs_st_cd;
	getCommonCode(paramValue, "N", "beforeInit();", url);
}

function beforeInit(){
	readOnlyStyle("key_ops_nm", 1);
	setCondition();
}

function setCondition(){
	if($("#hid_cond").val() != ""){
		var hidCond = $("#hid_cond").val().split("|");
		if(hidCond[0] != "") $("#key_eeno").val(hidCond[0]);
		if(hidCond[1] != "") $("#key_eenm").val(hidCond[1]);
		if(hidCond[2] != "") $("#key_from_date").val(hidCond[2]);
		if(hidCond[3] != "") $("#key_to_date").val(hidCond[3]);
		if(hidCond[4] != "") $("#key_ops_cd").val(hidCond[4]);
		if(hidCond[5] != "") $("#key_ops_nm").val(hidCond[5]);
		if(hidCond[6] != "") $("#key_pgs_st_cd").val(hidCond[6]);
		if(hidCond[7] != "") $("#key_plac_work").val(hidCond[7]);
		if(hidCond[8] != "") $("#hid_page").val(hidCond[8]);
	}else{
		$("#key_pgs_st_cd").val("Z");
	}
	
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
var cn        = ["DocNo.", "", "Apply Date", "Status", "ID No.", "Name", "Position", "Dept.", "Place Type", "Place To Visit","Start Date","End Date","PO No.","Status","Status", ""];
var excelCn   = ["Apply Date", "Status", "ID no", "Name", "Position", "Dept.", "Place Type", "Place To Visit", "Start Date", "End Date"];
var datarow   = {doc_no:"",dom_abrd_scn_cd:"",ptt_ymd:"", eeno:"", ee_nm:"", poa_nm:"", ops_nm:"", dest_nat_cd:"", strt_ymd:"",fnh_ymd:"",pgs_st_nm:"",pgs_st_cd:"",acpc_pgs_st_cd:"",dom_abrd_scn_nm:"",po_no:"",ven_pgs_st_cd:""};
var cm =
	[
		{name:"doc_no",			index:"doc_no"	, sortable:false,			formatter:"string",	width:90,	align:"center",	editable:false,	frozen:false, hidden:true},
		{name:"dom_abrd_scn_cd",index:"dom_abrd_scn_cd"	, sortable:false,	formatter:"string",	width:90,	align:"center",	editable:false,	frozen:false, hidden:true},
		{name:"ptt_ymd",		index:"ptt_ymd"	, sortable:false,			formatter:"string",	width:70,	align:"center",	editable:false,	frozen:false},
		{name:"pgs_st_nm",		index:"pgs_st_nm", sortable:false,			formatter:"string",	width:82,	align:"center",	editable:false,	frozen:false},
		{name:"eeno",			index:"eeno"	, sortable:false,			formatter:"string",	width:65,	align:"center",	editable:false,	frozen:false},
		{name:"ee_nm",			index:"ee_nm"	, sortable:false,			formatter:"string",	width:125,	align:"left",	editable:false,	frozen:false},
		{name:"poa_nm",			index:"poa_nm"	, sortable:false,			formatter:"string",	width:96,	align:"left",	editable:false,	frozen:false},
		{name:"ops_nm",			index:"ops_nm"	, sortable:false,			formatter:"string",	width:115,	align:"left",	editable:false,	frozen:false},
		{name:"dom_abrd_scn_nm",index:"dom_abrd_scn_nm", sortable:false,	formatter:"string",	width:110,	align:"center",	editable:false,	frozen:false},
		{name:"dest_nat_cd",	index:"dest_nat_cd"	, sortable:false,		formatter:"string",	width:131,	align:"left",	editable:false,	frozen:false},
		{name:"strt_ymd",		index:"strt_ymd", sortable:false,			formatter:"string",	width:68,	align:"center",	editable:false,	frozen:false},
		{name:"fnh_ymd",		index:"fnh_ymd"	, sortable:false,			formatter:"string",	width:68,	align:"center",	editable:false,	frozen:false},
		{name:"po_no",			index:"po_no"	, sortable:false,			formatter:"string",	width:69,	align:"center",	editable:false,	frozen:false, hidden:true},
		{name:"pgs_st_cd",		index:"pgs_st_cd", sortable:false,			formatter:"string",	width:72,	align:"center",	editable:false,	frozen:false, hidden:true},
		{name:"acpc_pgs_st_cd",	index:"acpc_pgs_st_cd", sortable:false,		formatter:"string",	width:72,	align:"center",	editable:false,	frozen:false, hidden:true},
		{name:"ven_pgs_st_cd",	index:"ven_pgs_st_cd", sortable:false,		formatter:"string",	width:72,	align:"center",	editable:false,	frozen:false, hidden:true}
	];
function init(){

	var params = {
			eeno       : $("#key_eeno").val(),
			strt_ymd   : dateConversionKr(trimChar($("#key_from_date").val(), "/")),
			fnh_ymd    : dateConversionKr(trimChar($("#key_to_date").val(), "/")),
			ops_cd     : $("#key_ops_cd").val(),
			pgs_st_cd  : $("#key_pgs_st_cd").val(),
			plac_work   : $("#key_plac_work").val()
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchBTToCustomerList.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();loadCallBack()",
			paramJson    : params,
			rowNum      : "20"
		}]
	};
	commonJqGridInit(gridParam);
	
	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			var hid_doc_no = getColValue("doc_no", iRow, gridName1);
			var hid_eeno   = getColValue("eeno",  iRow, gridName1);
			
			if(hid_doc_no != ""){
				var hfrm = document.hideForm;
				$("#hid_doc_no").val(hid_doc_no);
				$("#hid_eeno").val(hid_eeno);
				$("#hid_csrfToken").val($("#csrfToken").val());
				$("#param1").val(getColValue("dom_abrd_scn_cd",  iRow, gridName1));
				hfrm.action = "businessTravelMgmt.gas";
				hfrm.submit();
			}
		}
	}).trigger('reloadGrid');
	
	jQuery("#"+gridName1).jqGrid("setFrozenColumns");
	jQuery("#"+gridName1).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	$("#key_from_date").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_date").datepicker({ dateFormat: "dd/mm/yy" });
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "excel" :
			doExcel();
			break;
		case "check" :
			doCheck();
			break;
		case "checkCancel" :
			doCheckCancel();
			break;
	}
}

function doSearch(msgFlag){

	var params = {
		eeno       : $("#key_eeno").val(),
		ee_nm      : $("#key_eenm").val().toUpperCase(),
		strt_ymd   : dateConversionKr(trimChar($("#key_from_date").val(), "/")),
		fnh_ymd    : dateConversionKr(trimChar($("#key_to_date").val(), "/")),
		ops_cd     : $("#key_ops_cd").val(),
		pgs_st_cd  : $("#key_pgs_st_cd").val(),
		//doc_no     : $("#key_doc_no").val(),
		plac_work   : $("#key_plac_work").val()
	};
	
	doCommonSearch("doSearchBTToCustomerList.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName1, msgFlag);
}

function loadCallBack(){
	
	var gridRowId = jQuery("#"+gridName1).getDataIDs().length;
	for(var i=1;i<=gridRowId;i++){
		if(getColValue("pgs_st_cd", i, gridName1) == 'Z' && getColValue("ven_pgs_st_cd", i, gridName1) == 'Y'){
			var cols = jQuery("#"+gridName1).jqGrid('getGridParam', 'colModel');	
			for (var col in cols){
				jQuery("#"+gridName1).setCell (i,cols[col].index,'',{background:'#FFA7A7'});
			 }
		}else if(getColValue("pgs_st_cd", i, gridName1) == 'Z' && getColValue("ven_pgs_st_cd", i, gridName1) != 'Y'){
			var cols = jQuery("#"+gridName1).jqGrid('getGridParam', 'colModel');	
			for (var col in cols){
				jQuery("#"+gridName1).setCell (i,cols[col].index,'',{background:'#FFFF7E'}); 
			 } 
		}  
	}
	
	addGridRow();
}

function deptFind(){
	var param = "?dcd=key_ops_cd&dcdNm=key_ops_nm&hid_csrfToken="+$("#csrfToken").val();
	newPopWin(ctxPath+"/hncis/popup/deptPopup.gas"+param, "440", "510", "pop_dept");
}

function deptClear(){
	if($("#key_ops_nm").val() == ""){
		$("#key_ops_cd").val("");
	}
}

function deptSearch(){
	if($("#key_ops_cd").val() != ""){
		if($("#key_ops_cd").val().length > 8) return;
		if($("#key_ops_cd").val().length == 8){
			
			var keyData = { xorg_orga_c : $("#key_ops_cd").val() };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToDeptInfo.do", paramData, "deptCallBack(jsonData.sendResult)");
		}
	}else{
		$("#key_ops_nm").val("");
	}
}

function deptCallBack(result){
	$("#key_ops_nm").val(result.xorg_orga_e);
}

function doCheck(){
	var paramList = [];
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	var bsicInfo = {
		mode : "check"
	};
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName1);
				if(pgsStCd != "Z"){
					alertUI("You can't check in this status");
					return;
				}
				
				var domAbrdScnCd = getColValue("dom_abrd_scn_cd", rowId, gridName1);
				list = {
					doc_no         : getColValue("doc_no", rowId, gridName1),
					ven_pgs_st_cd  : "Y",
					acpc_pgs_st_cd : domAbrdScnCd == "PT002" ? "0" : "C",
					updr_eeno      : "vendor",
					mode           : "check"
				};
				paramList.push(list);
			}
		}
	}
	
	confirmUI("Do you want to Ok?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo),
						uParams  : util.jsonToList(paramList)
					};
					doCommonAjax("doVendorCheckBTToConfirmList.do", paramData, "checkCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doCheckCancel(){
	var paramList = [];
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	var bsicInfo = {
		mode : "checkCancel"
	};
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName1);
				if(pgsStCd != "C"){
					alertUI("You can't check cancel in this status");
					return;
				}
				list = {
					doc_no         : getColValue("doc_no", rowId, gridName1),
					pgs_st_cd      : "Z",
					acpc_pgs_st_cd : "0",
					updr_eeno      : "vendor",
					mode           : "checkCancel"
				};
				paramList.push(list);
			}
		}
	}
	
	confirmUI("Do you want to check cancel?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo),
						uParams  : util.jsonToList(paramList)
					};
					doCommonAjax("doCheckBTToConfirmList.do", paramData, "checkCallBack(jsonData.sendResult);");
			}
		});
	});
}

function checkCallBack(result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

var saveCode = "";
function cearInsa(){
	if($("#key_eeno").val() == ""){
		saveCode = "";
		$("#key_eenm").val("");
		$("#key_eeno").val("");
	}
}

function setInsaInfo(gubn){
	if(gubn == "N"){
		$("#eeno_temp").val(" ");
	}
	if($("#key_eeno").val() != ""){
		if($("#key_eeno").val().length > 8) return;
//		if($("#pgs_st_cd").val() != "") return;
		if($("#key_eeno").val().length == 8){
//			$("#nSource").val($("#eeno").val());
//			if(saveCode == $("#nSource").val()){ return; }
//			saveCode = $("#nSource").val();
			
			var keyData = { xusr_empno : $("#key_eeno").val() };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
		}
	}
}


function insaCallBack(result){
	$("#eeno").val(result.xusr_empno);
	$("#key_eenm").val(result.xusr_name);
}
