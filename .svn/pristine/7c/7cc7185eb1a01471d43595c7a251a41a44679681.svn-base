function fnSetDocumentReady(){
	initMenus();
	
	$("#key_from_date").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_date").datepicker({ dateFormat: "dd/mm/yy" }); 
	
	$(".inputOnlyNumber").numeric();
	
	getCommonCode("key_po_serv_desc:PO01:A;key_pgs_st_cd:PO04:A;key_regn_cd:X0004:A;", "N", "beforeInit();");
}

function beforeInit(){
	$("#key_regn_cd").val(sess_plac_work);
	
	if(!(sess_mstu == "M" || $("#work_auth").val() > 4)){
		$("#key_eeno").val(sess_empno);
		$("#key_eenm").val(sess_name);
		$("#key_ops_cd").val(sess_dept_code);
		$("#key_ops_nm").val(sess_dept_name);
		
		$("#deptBtn").attr("disabled", true);
		readOnlyStyle("key_ops_cd", 1);
		readOnlyStyle("key_ops_nm", 1);
//		readOnlyStyle("key_eeno", 1);
//		readOnlyStyle("key_eenm", 1);
	}

	
	$("#key_plac_work").val(sess_plac_work);
	
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
		if(hidCond[7] != "") $("#key_po_serv_desc").val(hidCond[7]);
		if(hidCond[8] != "") $("#key_regn_cd").val(hidCond[8]);
		if(hidCond[9] != "") $("#hid_page").val(hidCond[9]);
	}
	
	init();
}

/**
 * process init() loading
 */
var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {ptt_ymd:"", pgs_st_nm:"", req_eeno_nm:"", req_step_nm:"", req_dept_nm:"", po_trk_no:"", po_serv_desc:"", po_numb_obj:"", po_addt_serv:"", snb_rson_sbc:"", doc_no:"", cost_cd:"", po_comp_nm:"", po_zip_cd:""};
var comboVal;
var comboVal1; 
function init(){
	//set grid parameter
	var params = {
			req_eeno 		: $("#key_eeno").val(),
			req_eeno_nm 	: $("#key_eenm").val(),
			fr_ymd 			: dateConversionKr(selectNum($("#key_from_date").val())),
			to_ymd 			: dateConversionKr(selectNum($("#key_to_date").val())),
			req_dept_cd     : $("#key_ops_cd").val(),
			pgs_st_cd  		: $("#key_pgs_st_cd").val(),
			po_serv_desc   	: $("#key_po_serv_desc").val(),
			regn_cd 		: $("#key_regn_cd").val()
		};

	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchGridPoToList.do",
			colNames     : ["Apply Date", "Status", "Name", "Position", "Dept.", "Cost Center", "Tracking No.", "Service Description", "Number of Objects", "Additional Services","Company Name", "Zip Code", "Return Reason", "Doc No."],
			colModel     : [
			                {name:"ptt_ymd",		index:"ptt_ymd"			, sortable:false,		formatter:"string",	width:"75",		align:"center",	editable:false,	frozen:false},
			                {name:"pgs_st_nm",		index:"pgs_st_nm"		, sortable:false,		formatter:"string",	width:"70",		align:"center",	editable:false,	frozen:false},
			                {name:"req_eeno_nm",	index:"req_eeno_nm"		, sortable:false,		formatter:"string",	width:"130",	align:"left",	editable:false,	frozen:false},
			                {name:"req_step_nm",	index:"req_step_nm"		, sortable:false,		formatter:"string",	width:"80",		align:"left",	editable:false,	frozen:false},
			                {name:"req_dept_nm",	index:"req_dept_nm"		, sortable:false,		formatter:"string",	width:"110",	align:"left",	editable:false,	frozen:false},
			                {name:"cost_cd",		index:"cost_cd"			, sortable:false,		formatter:"string",	width:"70",		align:"center",	editable:false,	frozen:false},
			                {name:"po_trk_no",		index:"po_trk_no"		, sortable:false,		formatter:"string",	width:"140",	align:"center",	editable:false,	frozen:false},
			                {name:"po_serv_desc",	index:"po_serv_desc"	, sortable:false,		formatter:"string",	width:"150",	align:"center",	editable:false,	frozen:false},
			                {name:"po_numb_obj",	index:"po_numb_obj"		, sortable:false,		formatter:"string",	width:"60",		align:"center",	editable:false,	frozen:false},
			                {name:"po_addt_serv",	index:"po_addt_serv"	, sortable:false,		formatter:"string",	width:"145",	align:"center",	editable:false,	frozen:false},
			                {name:"po_comp_nm",		index:"po_comp_nm"		, sortable:false,		formatter:"string",	width:"150",	align:"left",	editable:false,	frozen:false},
			                {name:"po_zip_cd",		index:"po_zip_cd"		, sortable:false,		formatter:"string",	width:"100",	align:"left",	editable:false,	frozen:false},
			                {name:"snb_rson_sbc",	index:"snb_rson_sbc"	, sortable:false,		formatter:"string",	width:"300",	align:"left",	editable:false,	frozen:false},
			            	{name:"doc_no",			index:"doc_no"			, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true}
			            	],
			height       : "100%",
			rownumbers   : true, 
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
	
	jQuery("#"+gridName).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			var hid_doc_no = getColValue("doc_no", iRow, gridName);

			if(hid_doc_no != ""){
	            var form = $("<form/>");
	            form.attr("method" , "post");
	            form.attr("id"     , "hideForm");
	            form.attr("action" , "xpo01.gas");
	            var inp1 = $("<input type='hidden' id='hid_doc_no' name='hid_doc_no'/>").val(hid_doc_no);
	            var cond = "";
	            cond += $("#key_eeno").val();
	            cond += "|" + $("#key_eenm").val();
	            cond += "|" + $("#key_from_date").val();
	            cond += "|" + $("#key_to_date").val();
	            cond += "|" + $("#key_ops_cd").val();
				cond += "|" + $("#key_ops_nm").val();
	            cond += "|" + $("#key_pgs_st_cd").val();
	            cond += "|" + $("#key_po_serv_desc").val();
	            cond += "|" + $("#key_regn_cd").val();
	            cond += "|" + $("#page_htmlPager").val();
	            
	            var inp2 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val(cond);
	            var inp3 = $("<input type='hidden' id='hid_view_nm' name='hid_view_nm'/>").val($("#menu_id").val());
	            var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
	            form.append(inp1, inp2, inp3, token);
	            $("body").append(form);
	            form.submit();
	            form.remove();
			}
		}
	}).trigger('reloadGrid');
	
	//method overliding
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
			req_eeno 		: $("#key_eeno").val(),
			req_eeno_nm 	: $("#key_eenm").val(),
			fr_ymd 			: dateConversionKr(selectNum($("#key_from_date").val())),
			to_ymd 			: dateConversionKr(selectNum($("#key_to_date").val())),
			req_dept_cd     : $("#key_ops_cd").val(),
			pgs_st_cd  		: $("#key_pgs_st_cd").val(),
			po_serv_desc   	: $("#key_po_serv_desc").val(),
			regn_cd 		: $("#key_regn_cd").val()
	}; 

	doCommonSearch("doSearchGridPoToList.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
}
function loadCallBack(){
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

function setInsaInfo(){
	if($("#key_eeno").val() != ""){
		var keyData = { xusr_empno : $("#key_eeno").val() };
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
}

function insaCallBack(result){
	setBottomMsg(result.message, false);
	$("#key_eenm").val(result.xusr_name);
}

function clearInsa(){
	if($("#key_eeno").val() == ""){
		$("#key_eenm").val("");
	}
}