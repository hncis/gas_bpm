jQuery(document).ready(function(){
	sess_auth = $("#work_auth").val();
	
	if($("#dispatcherYN").val() == "Y"){
		dispatcherSubmit();
	}else{
		init();
	}
});

/**
 * process init() loading
 */
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
			{name:"fil_nm",			index:"fil_nm"		, sortable:false,			formatter:"string",		width:180,	align:"left",	editable:false,	frozen:false},
			{name:"fil_mgn_qty",	index:"fil_mgn_qty"		, sortable:false,			formatter:"string",		width:60,	align:"right",	editable:false,	frozen:false},
			{name:"file_down",		index:"file_down"		, sortable:false,			formatter:"string",		width:95,	align:"center",	editable:false,	frozen:false}			
		];
	
	var params = {
			doc_no     	: $("#file_doc_no").val(),
			eeno       	: $("#file_eeno").val(),
			seq       	: $("#file_seq").val(),
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
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "searchCallBack();",
			selectCellFc : "setChangeImg();",
			paramJson    : params,
			rowNum       : "20"
		}]
	};
	
	commonJqGridInit(gridParam);
	setGridColumnOptions();
	//jQuery("#"+gridName1).jqGrid("setFrozenColumns");
}

function searchCallBack(){
	addGridRow(10);
	setChangeImg();
}

function retrieve(gubn){
	
	switch(gubn){
		case "save" :
			doSave();
			break;
		case "delete" :
			doDelete();
			break;
	}
}

function doSave(){
	
	var fileInfo = {
		doc_no    		: $("#file_doc_no").val(),
		eeno    		: $("#file_eeno").val(),
		pgs_st_cd  		: $("#file_pgs_st_cd").val(),
		seq		    	: $("#file_seq").val(),
		affr_scn_cd   	: "BT",
		csrfToken		: $("#csrfToken").val(),
		ipe_eeno   		: "OUTCOMP"
	};
	
	
	
	if($("#file_name").val() == ""){
		alertUI("파일을 첨부해 주세요.");
		return;
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var frm = document.mainForm;
				frm.fileInfo.value = util.jsonToString(fileInfo);
				frm.action = "doSaveBTToOutCompFileHotel.do";
				frm.submit();
			}
		});
	});
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
	
	if($("#dispatcherYN").val() == "Y"){
		if($("#message").val() != ""){
			alertUI($("#message").val());
		}
	}
}

function doFileDown(file_name){
	
	var fileInfo = {
		doc_no    		: $("#file_doc_no").val(),
		eeno    		: $("#file_eeno").val(),
		seq		    	: $("#file_seq").val(),
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
	frm.hid_csrfToken.value = $("#csrfToken").val();
	frm.action = "./xbt01_file.gas";
	frm.submit();
}

function doDelete(){
	
	var fileInfo = [];
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(getColValue("doc_no", rowId, gridName1) == ""){
				alertUI("File Attach " + rowId + " Line : There is no Data.");
				return;
			}
			if(rowId){
				data = {
					doc_no			: getColValue("doc_no", rowId, gridName1),
					eeno			: getColValue("eeno", rowId, gridName1),
					seq				: getColValue("seq", rowId, gridName1),
					affr_scn_cd		: "BT",
					ogc_fil_nm		: getColValue("ogc_fil_nm", rowId, gridName1)
				};
				fileInfo.push(data);
			}
		}
		
		confirmUI("삭제 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							fileInfo		: util.jsonToList(fileInfo)
					};
					doCommonAjax("doDeleteBTToFile.do", paramData, "deleteCallBack(jsonData.sendResult);");
				}
			});
		});
	}
}

function deleteCallBack(result){
	alertUI(result.message);
	$("#dispatcherYN").val("N");
	doSearch();
}

function doSearch(){
	
	var params = {
			doc_no     	: $("#file_doc_no").val(),
			eeno       	: $("#file_eeno").val(),
			seq       	: $("#file_seq").val(),
			affr_scn_cd : 'BT'
		};
	
	doCommonSearch("doSearchBTToFile.do", util.jsonToString(params), "searchCallBack();", gridName1);
}