var gridParam;
var comboVal2;
var gridName1 = "htmlTable";
var datarow = {full_name:"", et_rg:"", company:"", visit_purp:"", et_type:"", access:"", et_date:"", et_status:"", et_remark:"",
		seq: "", eeno: "", apply_date: "", if_id: "", pgs_st_nm: "", pgs_st_cd: "", purpose:"", reason:"", remark:"", doc_no:"", snb_rson_sbc:""};

function initComboMaterial(){
	getCommonCode("et_type:X3006:Z;", "Y", "comboSearch(jsonData.sendResult);");
}

function comboSearch(jsonData){
	comboVal2 = jsonData;
	getCommonCode('access:X3003;', 'Y', 'initSub();');
}

function initSub(){
	var cn = ["Full Name", "ID", "Company", "Visit Purpose", "Type", "Vehicle Access", "Date", "Status", "Remarks",
	          "seq", "eeno", "apply_date", "if_id", "pgs_st_nm", "pgs_st_cd", "purpose", "reason", "remark", "doc_no", ""];
	var cm =
	[
		{name:"full_name",	index:"full_name"	, sortable:false,		formatter:"string",		width:150,	align:"left",	editable:false},
		{name:"et_rg",		index:"et_rg"		, sortable:false,		formatter:"string",		width:100,	align:"left",	editable:false},
		{name:"company",	index:"company"		, sortable:false,		formatter:"string",		width:100,	align:"left",	editable:false},
		{name:"visit_purp", index:"visit_purp"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"et_type",	index:"et_type"		, sortable:false,		formatter: "select",	width:100,	align:'center',	editable:false,	edittype:'select', 
			editoptions:{value:getComboValueType('et_type'), dataInit: function(elem) {$(elem).width(95);}
			},
			editrules:{required:true}
		},
		{name:"access",	index:"access"	, sortable:false,		formatter: "select",	width:100,	align:'center',	editable:false,	edittype:'select', 
			editoptions:{value:getComboValue('access'), dataInit: function(elem) {$(elem).width(95);}
		        },
		        editrules:{required:true}
		},
		{name:"et_date",		index:"et_date"		, sortable:false,		formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false,
			editoptions: {
				readonly : true,
	            dataInit: function(element) {
	     		    $(element).datepicker({
	     		    	dateFormat: 'dd/mm/yy',
	     		    	onSelect: function(dataText, inst){
	     		    	}
			    	});
	            }
			}
		},
		{name:"et_status",	index:"et_status"	, sortable:false,		formatter:"string",		width:100,	align:"left",	editable:false},
		{name:"et_remark",	index:"et_remark"	, sortable:false,		formatter:"string",		width:215,	align:"left",	editable:false},
		{name:"seq",		index:"seq"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"eeno",		index:"eeno"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"apply_date",	index:"apply_date"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"if_id",		index:"if_id"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"purpose",	index:"purpose"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"reason",		index:"reason"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"remark",		index:"remark"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"doc_no",		index:"doc_no"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true},
		{name:"snb_rson_sbc",		index:"snb_rson_sbc"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:false, hidden:true}
	];
	
	var apply_date_temp = "";
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = dateConversionKr(parent.$("#apply_date").val());
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		apply_date 	 : apply_date_temp,
		doc_no 		 : parent.$("#hid_doc_no").val(),
		type		 : "5"
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestEntrance.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "",
			sortorder    : "",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			paramJson    : params,
			completeFc   : "addGridRow(5);loadDataSet();"
		}]
	};
	
	commonJqGridInit(gridParam);
}

function loadDataSet(){
	parent.$("#apply_date").val(getColValue("apply_date", 1));
	parent.$("#purpose").val(getColValue("purpose", 1));
	parent.$("#reason").val(getColValue("reason", 1));
	parent.$("#pgs_st_cd").val(getColValue("pgs_st_cd", 1));
	parent.$("#pgs_st_nm").val(getColValue("pgs_st_nm", 1));
	if(getColValue("doc_no", 1) != ""){
		parent.$("#doc_no").val(getColValue("doc_no", 1));
		parent.$("#hid_doc_no").val(getColValue("doc_no", 1));
	}
	parent.$("#snb_rson_sbc").val(getColValue("snb_rson_sbc", 1));
	
	resizeIframe();
	
	parent.sbcReadonlySet();
//	if(getColValue("if_id", 1)!= ""){
//		parent.doSearchRequestInfo(getColValue("if_id", 1));
//	}
	
	doSaerchSecurityRemark();
}

function doSaerchSecurityRemark(){
	var keyData = { 
		doc_no : parent.$("#doc_no").val() 
	};
	
	paramData = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("doSaerchSecurityRemark.do", paramData, "searchRemarkCallBack(jsonData.sendResult)");
}

function searchRemarkCallBack(result){
	parent.$("#remark").val(result.remark);
}

function gridRowAdd(){
	var gridRowId = jQuery("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
	
	resizeIframe();
}


function doSearch(msgFlag){
	var apply_date_temp = "";
	
	if(parent.$("#eeno_temp").val() == "" || parent.$("#eeno_temp").val() != parent.$("#eeno").val()){
		apply_date_temp = "";
	}else{
		apply_date_temp = dateConversionKr(parent.$("#apply_date").val());
	}
	
	var params = {
		eeno	     : parent.$("#eeno").val(),
		doc_no		 : parent.$("#hid_doc_no").val(),
		apply_date 	 : apply_date_temp,
		type		 : "5"
	};
	doCommonSearch("doSearchSecurityRequestEntrance.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
}

function getComboValueType(comboName){
	var returnVal="";
	if(typeof(comboVal2) == 'undefined'){
		returnVal = ":";
	}
	else{
		var i = 0;
		$.each(eval(comboVal2),function(targetNm,optionData){
			if(targetNm == comboName){
				$.each(eval(optionData),function(index,optionData){
					if(i == 0){
						returnVal = returnVal + optionData.value + ":" + optionData.name;
					}else{
						returnVal = returnVal + ";" + optionData.value + ":" + optionData.name;
					}
					i++;
				});
			}
	      });
		if(typeof(returnVal) == 'undefined'){
			returnVal = '';
		}
		else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
}

function resizeIframe(){
	var doc = document.getElementById("contents");
	parent.document.getElementById("ifra").height = doc.offsetHeight + "px";
}
