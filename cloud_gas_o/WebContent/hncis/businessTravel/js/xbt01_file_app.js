function fnSetDocumentReady(){
	sess_auth = $("#work_auth").val();
	
	if($("#dispatcherYN").val() == "Y"){
		dispatcherSubmit();
	}else{
		init();
	}	
}

var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;
var gridName1 = "htmlTable";
var datarow   = {doc_no:"",eeno:"",seq:"",affr_scn_cd:"",ogc_fil_nm:"", fil_nm:"", fil_mgn_qty:"", file_down:""};

function init(){
	
	var cn        = ["DocNo.", "Eeno", "Seq", "Affr_scn_cd", "Ogc_fil_nm", "File Name", "File Size", "Download"];
	var cm =
		[
			{name:"doc_no",			index:"doc_no"	, sortable:false,			formatter:"string",		width:0,	align:"center",	editable:false,	frozen:true, 	hidden:true},
			{name:"eeno",			index:"eeno"	, sortable:false,			formatter:"string",		width:0,	align:"center",	editable:false,	frozen:true, 	hidden:true},
			{name:"seq",			index:"seq"		, sortable:false,			formatter:"string",		width:0,	align:"left",	editable:true,	frozen:false, 	hidden : true},
			{name:"affr_scn_cd",	index:"affr_scn_cd"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen:false, 	hidden : true},
			{name:"ogc_fil_nm",		index:"ogc_fil_nm"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen:false, 	hidden : true},
			{name:"fil_nm",			index:"fil_nm"		, sortable:false,			formatter:"string",		width:205,	align:"left",	editable:true,	frozen:false},
			{name:"fil_mgn_qty",	index:"fil_mgn_qty"		, sortable:false,			formatter:"string",		width:60,	align:"right",	editable:true,	frozen:false},
			{name:"file_down",		index:"file_down"		, sortable:false,			formatter:"string",		width:95,	align:"center",	editable:true,	frozen:false}			
		];
	
	var params = {
			doc_no     	: $("#hid_doc_no").val(),
			eeno       	: $("#hid_eeno").val(),
			seq       	: $("#hid_seq").val(),
			affr_scn_cd : 'BT'
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchBTToFile.do",
			colNames     : cn,
			colModel     : cm,
			width        : "400",
			height       : "100%",
			sortname     : "file_nm",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "searchCallBack();",
			paramJson    : params,
			rowNum       : "20"
		}]
	};
	
	commonJqGridInit(gridParam);
	setGridColumnOptions();
}

function searchCallBack(){
	addGridRow(10);
	setChangeImg();
}

function setChangeImg(){
	var gridRow  = jQuery("#"+gridName1);
	var ids      = gridRow.getDataIDs();
	
	for(var i=0;i<ids.length;i++){
		if(getColValue("doc_no", i+1, gridName1)!=""){
			var imgSrc = "";			
			imgSrc = "<img src='../../images/hncis_bttn/download_n.gif' onClick='doFileDown(\""+getColValue("ogc_fil_nm", i+1, gridName1)+"\");'/>";
			gridRow.jqGrid("setRowData", i+1, {file_down:imgSrc});
		}
	}
}

function doFileDown(file_name){
	
	var fileInfo = {
		doc_no    		: $("#hid_doc_no").val(),
		eeno    		: $("#hid_eeno").val(),
		seq		    	: $("#hid_seq").val(),
		affr_scn_cd   	: "BT",
		ogc_fil_nm   	: file_name
	};
	
	var frm = document.mainForm;
	frm.fileInfo.value = util.jsonToString(fileInfo);
	frm.action = "doFileDown.do";
	frm.submit();
	
}

function dispatcherSubmit(){
	$("#dispatcherYN").val("N");
	var frm = document.dispatcherForm;
	frm.action = "./xbt01_file.gas";
	frm.submit();
}

function doSearch(){
	
	var params = {
			doc_no     	: $("#hid_doc_no").val(),
			eeno       	: $("#hid_eeno").val(),
			seq       	: $("#hid_seq").val(),
			affr_scn_cd : 'BT'
		};
	
	doCommonSearch("doSearchBTToFile.do", util.jsonToString(params), "searchCallBack();", gridName1);
}