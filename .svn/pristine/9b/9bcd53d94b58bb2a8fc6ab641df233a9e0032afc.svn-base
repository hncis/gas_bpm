var gridParam;
var gridName1 = "htmlTable";
var datarow = {company:"", carrier:"", brand:"", description:"", asset_num:"", quantity:"", return_sel:"", day1:"", hour1:"",day2:"", hour2:"",
		seq: "", eeno: "", apply_date: "", if_id: "", pgs_st_nm: "", pgs_st_cd: "", purpose:"", reason:"", remark:"", doc_no:"", snb_rson_sbc:""};

function initComboMaterial(){
	getCommonCode('return_sel:X3003;', 'Y', 'initSub();');
}

function initSub(){
	var cn = ["Company", "Name", "Brand", "Description", "Asset Number", "Quantity", "Return", "day", "hour", "day", "hour",
	          "seq", "eeno", "apply_date", "if_id", "pgs_st_nm", "pgs_st_cd", "purpose", "reason", "remark", "doc_no", ""];
	var cm =
	[
		{name:"company",	index:"company"		, sortable:false,		formatter:"string",		width:86,	align:"left",	editable:true},
		{name:"carrier",	index:"carrier"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		{name:"brand",		index:"brand"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		{name:"description",index:"description"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		{name:"asset_num",	index:"asset_num"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		{name:"quantity",	index:"quantity"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		{name:"return_sel",	index:"return_sel"	, sortable:false,		formatter: "select",	width:85,	align:'center',	editable:true,	edittype:'select', 
			editoptions:{value:getComboValue('return_sel'), dataInit: function(elem) {$(elem).width(80);}
		        },
		        editrules:{required:true}
		},
		{name:"day1",		index:"day1"		, sortable:false,		formatter:"string",		width:88,	align:"center",	editable:true,	frozen : false,
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
		{name:"hour1",		index:"hour1"		, sortable:false,	formatter:"string",		width:89,	align:"center",	editable:true,	frozen : false,
			editoptions: {maxlength:"4", 
	            dataInit: function(element) {
	     		    $(element).keyup(function(){

	     		    	if(!isNumeric(element.value)){
	     		    		element.value = selectNum(element.value);
	     		    	}

	     		    	if(trimChar(element.value, ":").length == 4){
	     		    		if(element.value.length > 4){
	     		    			element.value = "";
	     		    		}else{
	     		    			element.value = element.value.substring(0, 2)+":"+element.value.substring(2, 4);
	     		    		}
	     				}else{
	     					element.value = trimChar(element.value, ":");
	     				}
	     		    });
	            }
	        }
		},
		{name:"day2",		index:"day2"		, sortable:false,		formatter:"string",		width:88,	align:"center",	editable:true,	frozen : false,
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
		{name:"hour2",		index:"hour2"		, sortable:false,	formatter:"string",		width:89,	align:"center",	editable:true,	frozen : false,
			editoptions: {maxlength:"4", 
	            dataInit: function(element) {
	     		    $(element).keyup(function(){

	     		    	if(!isNumeric(element.value)){
	     		    		element.value = selectNum(element.value);
	     		    	}

	     		    	if(trimChar(element.value, ":").length == 4){
	     		    		if(element.value.length > 4){
	     		    			element.value = "";
	     		    		}else{
	     		    			element.value = element.value.substring(0, 2)+":"+element.value.substring(2, 4);
	     		    		}
	     				}else{
	     					element.value = trimChar(element.value, ":");
	     				}
	     		    });
	            }
	        }
		},
		{name:"seq",		index:"seq"			, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"eeno",		index:"eeno"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"apply_date",	index:"apply_date"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"if_id",		index:"if_id"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"purpose",	index:"purpose"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"reason",		index:"reason"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"remark",		index:"remark"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"doc_no",		index:"doc_no"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		{name:"snb_rson_sbc",index:"snb_rson_sbc", sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true}
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
		type		 : "2"
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestMaterial.do",
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

	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
          {startColumnName: 'day1', numberOfColumns: 2, titleText: 'Start'},
          {startColumnName: 'day2', numberOfColumns: 2, titleText: 'End'}
		]
	});
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
	if(getColValue("if_id", 1)!= ""){
		parent.doSearchRequestInfo(getColValue("if_id", 1));
	}

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
		type		 : "2"
	};
	doCommonSearch("doSearchSecurityRequestMaterial.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
}

function resizeIframe(){
	var doc = document.getElementById("contents");
	parent.document.getElementById("ifra").height = doc.offsetHeight + "px";
}
