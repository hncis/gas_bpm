var comboVal;
jQuery(document).ready(function(){
	initMenus();
	
	$(".inputOnlyNumber").numeric();

	getCommonCode("key_type:X3001;key_area:X0004:A;", "N", "setCondition()");		//목적
});

function setCondition(){

	if($("#hid_cond").val() != ""){
		var hidCond = $("#hid_cond").val().split("|");
		if(hidCond[0] != "") $("#key_from_date").val(hidCond[0]);
		if(hidCond[1] != "") $("#key_to_date").val(hidCond[1]);
		if(hidCond[2] != "") $("#key_eeno").val(hidCond[2]);
		if(hidCond[3] != "") $("#key_eenm").val(hidCond[3]);
		if(hidCond[4] != "") $("#key_ops_cd").val(hidCond[4]);
		if(hidCond[5] != "") $("#key_ops_nm").val(hidCond[5]);
		if(hidCond[6] != "") $("#key_area").val(hidCond[6]);
		if(hidCond[7] != "") $("#key_type").val(hidCond[7]);
		if(hidCond[8] != "") $("#hid_page").val(hidCond[8]);
	}else{
		$("#key_type").val("5");
	}
	
	init();
}

/**
 * process init() loading
 */
var fnMerge;
var gridParam;
var gridName1 = "htmlTable";
var gridName2 = "htmlTable1";
var datarow = {apply_date:"", ee_nm:"", step_code:"", step_name:"", ops_cd:"", ops_nm:"", type:"", type_nm:"", pgs_st_cd:"", pgs_st_nm:"", eeno:"", doc_no:"", num_req:"", start_ymd:"", end_ymd:"", driver_lic:"", plate:"", company:"", brand:""};

var cn = ["Start Date", "End Date", "Status_code", "Status", "Plate", "Name", "Driver License", "Company", "Brand", "type_cd", "type", "number of request", "eeno", "doc_no", ""];
var cm =
[
	{name:"start_ymd",		index:"start_ymd"	, sortable:false,		formatter:"string",		width:70,	align:"center",	editable:false,	frozen : false},
	{name:"end_ymd",		index:"end_ymd"		, sortable:false,		formatter:"string",		width:70,	align:"center",	editable:false,	frozen : false},
	{name:"pgs_st_cd",		index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:50,	align:"center",	editable:false,	frozen : false,	hidden: true},
	{name:"pgs_st_nm",		index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:80,	align:"center",	editable:false,	frozen : false},
	{name:"plate",			index:"plate"		, sortable:false,		formatter:"string",		width:130,	align:"left",	editable:false,	frozen : false},
	{name:"ee_nm",			index:"ee_nm"		, sortable:false,		formatter:"string",		width:165,	align:"left",	editable:false,	frozen : false},
	{name:"driver_lic",		index:"driver_lic"	, sortable:false,		formatter:"string",		width:130,	align:"left",	editable:false,	frozen : false},
	{name:"company",		index:"company"		, sortable:false,		formatter:"string",		width:130,	align:"left",	editable:false,	frozen : false},
	{name:"brand",			index:"brand"		, sortable:false,		formatter:"string",		width:130,	align:"left",	editable:false,	frozen : false},
	{name:"type",			index:"type"		, sortable:false,		formatter:"string",		width:200,	align:"left",	editable:false,	frozen : false,	hidden: true},
	{name:"type_nm",		index:"type_nm"		, sortable:false,		formatter:"string",		width:220,	align:"left",	editable:false,	frozen : false},
	{name:"num_req",		index:"num_req"		, sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false},
	{name:"eeno",			index:"eeno"		, sortable:false,		formatter:"string",		width:130,	align:"center",	editable:false,	frozen : false, hidden: true},
	{name:"doc_no",			index:"doc_no"		, sortable:false,		formatter:"string",		width:130,	align:"center",	editable:false,	frozen : false, hidden: true},
	{name:"apply_date",		index:"apply_date"	, sortable:false,		formatter:"string",		width:130,	align:"center",	editable:false,	frozen : false, hidden: true}
];

cn_visitor =[ 'Dept','ID','Name','Visitor Name','ID','Company','Date','', 'Status','Doc No'];
cm_visitor = [
		{name:'ops_nm',		index:'ops_nm', 	formatter:'string',	width:140,	align:'center',	editable:false,	sortable:false}, 
		{name:'eeno',		index:'eeno', 		formatter:'string',	width:70,	align:'center',	editable:false,	sortable:false},
		{name:'eenm',		index:'eenm', 		formatter:'string',	width:120,	align:'left',	editable:false,	sortable:false},
		{name:'vstr_nm',	index:'vstr_nm', 	formatter:'string',	width:120,	align:'left',	editable:false,	sortable:false},
		{name:'vstr_id',	index:'vstr_id', 	formatter:'string',	width:70,	align:'left',	editable:false,	sortable:false},
		{name:'vstr_cmpy',	index:'vstr_cmpy',	formatter:'string',	width:100,	align:'left',	editable:false,	sortable:false},
		{name:'et_date', 		index:'et_date', 	formatter:"string", width:63, 	align:'center',	editable:false, sortable:false},
        {name:'pgs_st_cd', 	index:'pgs_st_cd',	formatter:"string", width:63, 	align:'center',	editable:false, sortable:false, hidden:true},
        {name:'pgs_st_nm', 	index:'pgs_st_nm', 	formatter:"string", width:63, 	align:'center',	editable:false, sortable:false},
		{name:'doc_no',		index:'doc_no', 	formatter:'string', width:70,	align:'left',	editable:false,	sortable:false,	hidden:true}
];

function init(){
	$("#key_from_date").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_date").datepicker({ dateFormat: "dd/mm/yy" });
	
	var params = {
		key_from_date	: dateConversionKr($("#key_from_date").val()),
		key_to_date		: dateConversionKr($("#key_to_date").val()),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val()
	};
	
	
	///////// Visitor가 아닌 경우 ////////////
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "applyDate",
			sortorder    : "desc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			paramJson    : params,
			pager		 : "htmlPager1",
			page		 : $("#hid_page").val(),
			completeFc   : "addGridRow();initAfterMenus();"
		}]
	};
	
	//core jqGrid call...
	commonJqGridInit(gridParam);
	
	jQuery("#"+gridName1).jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){
			if(getColValue("doc_no",rowid, gridName1) != ""){
	            var form = $("<form/>");
	            form.attr("method" , "post");
	            form.attr("id"     , "hideForm");
	            form.attr("action" , "entranceMealDetail.gas");
	            var inp1 = $("<input type='hidden' id='hid_apply_date' name='hid_apply_date'/>").val(getColValue("apply_date", rowid, gridName1));
	            var inp2 = $("<input type='hidden' id='hid_eeno' name='hid_eeno'/>").val(getColValue("eeno", rowid, gridName1));
	            var inp3 = $("<input type='hidden' id='hid_type' name='hid_type'/>").val(getColValue("type", rowid, gridName1));
	            var inp4 = $("<input type='hidden' id='hid_doc_no' name='hid_doc_no'/>").val(getColValue("doc_no", rowid, gridName1));
	            var cond = "";
	            cond += $("#key_from_date").val();
	            cond += "|" + $("#key_to_date").val();
				cond += "|" + $("#key_eeno").val();
				cond += "|" + $("#key_eenm").val();
	            cond += "|" + $("#key_ops_cd").val();
	            cond += "|" + $("#key_ops_nm").val();
	            cond += "|" + $("#key_area").val();
	            cond += "|" + $("#key_type").val();
	            cond += "|" + $("#page_htmlPager").val();
	            
	            var inp5 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val(cond);
	            var inp6 = $("<input type='hidden' id='hid_view_nm' name='hid_view_nm'/>").val($("#menu_id").val());
	            var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
	            form.append(inp1, inp2, inp3, inp4, inp5, inp6, token);
	            $("body").append(form);
	            form.submit();
	            form.remove();
			}
		}
	}).trigger('reloadGrid');
	
	//method overliding
	jQuery("#"+gridName1).jqGrid("setFrozenColumns");
	jQuery("#"+gridName1).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	init1();
}

var datarow1 = {ops_nm:"",eeno:"",ee_nm:"",full_name:"",et_rg:"",company:"",et_date:"",pgs_st_cd:"",pgs_st_nm:"",doc_no:""};
function init1(){
	
	
	var params = { 
	};
	 
	cn_vi =[ 'Dept','ID','Name','Visitor Name','ID','Company','Date','Status','Doc No'],

	cm_vi = [
			{name:'ops_nm',index:'ops_nm', formatter:'string', width:180,align:'center',editable:false,sortable:false}, 
			{name:'eeno',index:'eeno', formatter:'string',width:80,align:'center',editable:false,sortable:false, hidden:true},
			{name:'ee_nm',index:'ee_nm', formatter:'string',width:180,align:'left',editable:false,sortable:false},
			{name:'full_name',index:'full_name', formatter:'string',width:180,align:'left',editable:false,sortable:false},
			{name:'et_rg',index:'et_rg', formatter:'string',width:120,align:'left',editable:false,sortable:false},
			{name:'company',index:'company', formatter:'string',width:130,align:'left',editable:false,sortable:false},
			{name:'et_date', index:'et_date', formatter:"string", width:80, align:'center',editable:false, frozen : false,sortable:false,
				editoptions: {
					readonly : true,
	            dataInit: function(element) {
	     		    $(element).datepicker({
	     		    	dateFormat: 'dd/mm/yy',
	     		    	onSelect: function(dataText, inst){
	     		    	}
			    	});
	            }
	        }},
			{name:'pgs_st_nm',index:'pgs_st_nm', formatter:'string',width:100,align:'left',editable:false,sortable:false},
			{name:'doc_no',index:'doc_no', formatter:'string', width:70,align:'left' ,hidden:true,editable:false,sortable:false}
		],   
		gridParam_vi = {
			viewEdit : [{
				gridName     : "htmlTable1",
				url          : "",
				colNames     : cn_vi,
				colModel     : cm_vi,
				height       : "100%",
				sortname     : "ops_nm",
				sortorder    : "desc",
				rowNum		 : 15,
				rownumbers   : true,
				multiselect  : false,
				cellEdit     : true,
				fnMerge      : false,
				pager		 : "htmlPager1",
				paramJson    : params
			}]
		};
	commonJqGridInit(gridParam_vi);  
	

	jQuery("#htmlTable1").jqGrid('navGrid',"#htmlPager1",{edit:false,add:false,del:false,search:false,refresh:false});
	
	jQuery("#htmlTable1").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
		              {startColumnName: 'ops_nm', numberOfColumns: 3, titleText: 'Requester'},
						{startColumnName: 'full_name', numberOfColumns: 3, titleText: 'Visitor'}
		              ]
	});
	addGridRow();
	initAfterMenus();
	changeTypeComb();
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

function retrieve(gubn){
	switch(gubn){
		case "search" :
			if($("#key_type").val() == "5"){
				doVisitorSearch();
			}else{
				doSearch();
			}
			break;
		case "excel" :
			doExcel();
			break;
	}
}

function doSearch(msgFlag){
	var params = {
		key_from_date	: dateConversionKr(selectNum($("#key_from_date").val())),
		key_to_date		: dateConversionKr(selectNum($("#key_to_date").val())),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val(),
		plate			: $("#key_eeno").val()
	};
	
//	doCommonSearch("doSearchSecurityRequestConfirmList.do", util.jsonToString(params), "loadCallBack();", gridName1, msgFlag);
	doCommonSearch("doSearchSecurityRequestList.do", util.jsonToString(params), "loadCallBack();", gridName1, msgFlag);
}


/**
 * callback
 */
function loadCallBack(){
	addGridRow();
	setTimeout("doSearch()","60000");
}

function deptFind(){
	var param = "?dcd=key_ops_cd&dcdNm=key_ops_nm&csrfToken="+$("#csrfToken").val();
	newPopWin(ctxPath+"/hncis/popup/deptPopup.gas"+param, "440", "510", "pop_dept");
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

function doExcel(){
	if($("#key_type").val() == "5"){
		doExcel_type5();
	}else if($("#key_type").val() == "1"){
		doExcel_type1();
	}else{
		doExcel_type2();
	}
}

function doExcel_type5(){
	var excelCn = ["Dept", "ID", "Name", "Visitor Name", "ID.", "Company", "Date", "Status", "Doc No"];
	var excelCm = ["ops_nm", "eeno", "ee_nm", "full_name", "et_rg", "company", "et_date", "pgs_st_nm", "doc_no"];
	var excelFm = ["string", "string", "string", "string", "string", "string", "string", "string", "string"];

	var keyData = {
		key_from_date	: dateConversionKr(selectNum($("#key_from_date").val())),
		key_to_date		: dateConversionKr(selectNum($("#key_to_date").val())),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val()
	};
	
	var params = [
		{name : "fileName",		value : "excel" },
		{name : "header",		value : util.jsonToArray(excelCn)},
		{name : "headerName",	value : util.jsonToArray(excelCm)},
		{name : "fomatter",		value : util.jsonToArray(excelFm)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "firstUseYn",	value : "Y"},
		{name : "page",			value : "1"}
	];
	
	gridToExcel("#"+gridName1, "doExcelSecurityRequestConfirmList.excel", params);
}

function doExcel_type1(){
	var excelCn = ["Start Date", "End Date", "Status", "Plate", "Name", "Driver License", "Type", "Number Of Request", "Doc No"];
	var excelCm = ["start_ymd", "end_ymd", "pgs_st_nm", "plate", "ee_nm", "driver_lic", "type_nm", "num_req", "doc_no"];
	var excelFm = ["string", "string", "string", "string", "string", "string", "string", "string", "string"];

	var keyData = {
		key_from_date	: dateConversionKr(selectNum($("#key_from_date").val())),
		key_to_date		: dateConversionKr(selectNum($("#key_to_date").val())),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val(),
		plate			: $("#key_eeno").val()
	};
	
	var params = [
		{name : "fileName",		value : "excel" },
		{name : "header",		value : util.jsonToArray(excelCn)},
		{name : "headerName",	value : util.jsonToArray(excelCm)},
		{name : "fomatter",		value : util.jsonToArray(excelFm)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "firstUseYn",	value : "Y"},
		{name : "page",			value : "1"}
	];
	
	gridToExcel("#"+gridName1, "doExcelSecurityRequestConfirmList.excel", params);
}

function doExcel_type2(){
	var excelCn = ["Start Date", "End Date", "Status", "Name", "Company", "Brand", "Type", "Number Of Request", "Doc No"];
	var excelCm = ["start_ymd", "end_ymd", "pgs_st_nm", "ee_nm", "company", "brand", "type_nm", "num_req", "doc_no"];
	var excelFm = ["string", "string", "string", "string", "string", "string", "string", "string", "string"];

	var keyData = {
		key_from_date	: dateConversionKr(selectNum($("#key_from_date").val())),
		key_to_date		: dateConversionKr(selectNum($("#key_to_date").val())),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val(),
		plate			: $("#key_eeno").val()
	};
	
	var params = [
		{name : "fileName",		value : "excel" },
		{name : "header",		value : util.jsonToArray(excelCn)},
		{name : "headerName",	value : util.jsonToArray(excelCm)},
		{name : "fomatter",		value : util.jsonToArray(excelFm)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "paramJson",	value : util.jsonToString(keyData)},
		{name : "firstUseYn",	value : "Y"},
		{name : "page",			value : "1"}
	];
	
	gridToExcel("#"+gridName1, "doExcelSecurityRequestConfirmList.excel", params);
}

function fnChangeGrid(seq){
	if(seq == "5"){
		$("#grid1").attr("style", "display: none;");
		$("#grid2").attr("style", "");
	}else{
		$("#grid1").attr("style", "");
		$("#grid2").attr("style", "display: none;");
	}
}


function doVisitorSearch(msgFlag){

	var params = {
		key_from_date	: dateConversionKr(selectNum($("#key_from_date").val())),
		key_to_date		: dateConversionKr(selectNum($("#key_to_date").val())),
		eeno			: $("#key_eeno").val(),
		key_eenm		: $("#key_eenm").val(),
		ops_cd    		: $("#key_ops_cd").val(),
		pgs_st_cd  		: "3",
		work_area		: $("#key_area").val(),
		type			: $("#key_type").val()
	};

	doCommonSearch("doSearchSecurityVistiorCustomer.do", util.jsonToString(params), "loadCallBack1();", "htmlTable1", msgFlag);
}

/**
 * callback
 */
function loadCallBack1(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow(null, "htmlTable1","datarow1"); 
	
	var ids = jQuery("#htmlTable1").getDataIDs();
	
	var deptCd = "";
	var empNo = "";
	var docNo = "";
	var colNm = "#7e9db9";
	var falg = "N";
	
	for(i=0; i<ids.length; i++){
		rowId = ids[i];
		if(rowId)	{
			if(i == 0){
				deptCd = getColValue("ops_nm", rowId, "htmlTable1");
				empNo = getColValue("eeno", rowId, "htmlTable1");
				docNo = getColValue("doc_no", rowId, "htmlTable1");
				if(getColValue("doc_no", rowId, "htmlTable1") != ''){ 
					jQuery("#htmlTable1").setCell (rowId,"full_name",'',{background:'#eeeeee'});
					jQuery("#htmlTable1").setCell (rowId,"et_date",'',{background:'#eeeeee'});
					jQuery("#htmlTable1").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
				}
			} 
			else{
				if(deptCd==getColValue("ops_nm", rowId, "htmlTable1")){
					$("#htmlTable1").setCell(rowId,'ops_nm',null);
				}
				else{
					deptCd = getColValue("ops_nm", rowId, "htmlTable1");
				}
				if(empNo==getColValue("eeno", rowId, "htmlTable1")){
					$("#htmlTable1").setCell(rowId,'eeno',null);
					$("#htmlTable1").setCell(rowId,'ee_nm',null); 
				}
				else{
					empNo = getColValue("eeno", rowId, "htmlTable1");
				}
				if(getColValue("doc_no", rowId, "htmlTable1") != ''){
					if(docNo==getColValue("doc_no", rowId, "htmlTable1")){
						if(falg == "Y"){
							jQuery("#htmlTable1").setCell (rowId,"full_name",'',{background:'#D5D5D5'});
							jQuery("#htmlTable1").setCell (rowId,"et_date",'',{background:'#D5D5D5'});
							jQuery("#htmlTable1").setCell (rowId,"pgs_st_cd",'',{background:'#D5D5D5'});
						}
						else{ 
							jQuery("#htmlTable1").setCell (rowId,"full_name",'',{background:'#eeeeee'});
							jQuery("#htmlTable1").setCell (rowId,"et_date",'',{background:'#eeeeee'});
							jQuery("#htmlTable1").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
						}
					}
					else{ 
						if(falg == "N"){
							jQuery("#htmlTable1").setCell (rowId,"full_name",'',{background:'#D5D5D5'});
							jQuery("#htmlTable1").setCell (rowId,"et_date",'',{background:'#D5D5D5'});
							jQuery("#htmlTable1").setCell (rowId,"pgs_st_cd",'',{background:'#D5D5D5'});
							docNo = getColValue("doc_no", rowId, "htmlTable1");
							falg = "Y";
						}
						else{
							jQuery("#htmlTable1").setCell (rowId,"full_name",'',{background:'#eeeeee'});
							jQuery("#htmlTable1").setCell (rowId,"et_date",'',{background:'#eeeeee'});
							jQuery("#htmlTable1").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
							docNo = getColValue("doc_no", rowId, "htmlTable1");
							falg = "N"; 
						}
					} 
				}
			}
		}
	}
}


function changeTypeComb(){
	$("#tb_div1").hide();
	$("#tb_div2").hide();
	if($("#key_type").val() == "5"){
		$(".plant_hidden_area").show();
		$("#id_title").text("ID No.");
		$("#tb_div2").show();
		doVisitorSearch();
	}else if($("#key_type").val() == "1"){
		$(".plant_hidden_area").show();
		$("#id_title").text("Plate");
		$("#" + gridName1).jqGrid("showCol", ["plate", "driver_lic"]);
		$("#" + gridName1).jqGrid("hideCol", ["company","brand"]);
		$("#tb_div1").show();
		doSearch();
	}else if($("#key_type").val() == "2"){
		$(".plant_hidden_area").hide();
		$("#" + gridName1).jqGrid("hideCol", ["plate", "driver_lic"]);
		$("#" + gridName1).jqGrid("showCol", ["company","brand"]);
		$("#tb_div1").show();
		doSearch();
	}else if($("#key_type").val() == "3"){
		$(".plant_hidden_area").hide();
		$("#" + gridName1).jqGrid("hideCol", ["plate", "driver_lic"]);
		$("#" + gridName1).jqGrid("showCol", ["company","brand"]);
		$("#tb_div1").show();
		doSearch();
	}else if($("#key_type").val() == "4"){
		$(".plant_hidden_area").hide();
		$("#" + gridName1).jqGrid("hideCol", ["plate", "driver_lic"]);
		$("#" + gridName1).jqGrid("showCol", ["company","brand"]);
		$("#tb_div1").show();
		doSearch();
	}
}