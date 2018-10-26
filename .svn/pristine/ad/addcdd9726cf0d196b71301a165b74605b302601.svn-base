var fnMerge;
var comboVal;
var comboVal1;
var comboVal2;
var comboEmp = '<option role="option" value=""></option>';
var gridName1 = "htmlTable";
var datarow = {stap_ymd:"",sta_hhmm:"",transport_cd:"",purpose:"",stap_cd:"",stap_adr:"",arvp_cd:"",arvp_adr:"",rem_sbc:"",eeno:"",eenm:"",ops_nm:"",svca_amt:"",doc_no:"",seq:"",add_flag:""};

function fnSetDocumentReady(){
	init();
}

/**
 * process init() loading
 */
function init(){
	cn = ['신청일자','시간','교통수단','목적','장소','장소','사번','이름','부서','Reason','금액','Doc No','Seq','']; 
	cm = [
  		{name:"stap_ymd", index:"stap_ymd", sortable:false, formatter:"string",	width:65, readonly:"true", align:"center", editable:false,	frozen : false,
			editoptions: { 
				readonly : true,
	            dataInit: function(element) {
	     		    $(element).datepicker({
	     		    	dateFormat: 'yy-mm-dd',
	     		    	onSelect: function(dataText, inst){
	     		    	}
			    	});
	            }
			}
		},
		{name:"sta_hhmm", index:"sta_hhmm", sortable:false,	formatter:"string",	width:60, align:"center", editable:false, frozen : false,
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
		{name:'transport_cd',index:'transport_cd',sortable:false,width:115,align:'center',editable:false},
		{name:'purpose',index:'purpose', formatter: "string",width:138,align:'left',editable:false,sortable:false},
		{name:'stap_adr',index:'stap_adr', formatter: "string",width:150,align:'left',editable:false,sortable:false},
		{name:'arvp_adr',index:'arvp_adr', formatter: "string",width:150,align:'left',editable:false,sortable:false},   
		{name:'eeno',index:'eeno', formatter: "string",width:60,align:'left',editable:false,sortable:false,
			editoptions:{
	            dataInit: function(element) {
	     		    $(element).keyup(function(){

	     		    	if(!isNumeric(element.value)){
	     		    		element.value = selectNum(element.value);
	     		    	}
	     		    });
	            }
			}
		},
        {name:"eenm", index:"eenm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
        {name:"ops_nm", index:"ops_nm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
		{name:'rem_sbc',index:'rem_sbc', formatter: "string",width:240,align:'left',editable:false,sortable:false,hidden:true},
		{name:'svca_amt',index:'svca_amt', formatter: "string",width:69,align:'left',editable:false,sortable:false},
		{name:'doc_no',index:'doc_no', formatter: "string",width:60,align:'left',editable:false,sortable:false,hidden:true},
		{name:'seq',index:'seq', formatter: "string",width:60,align:'left',editable:false,sortable:false,hidden:true},
		{name:'add_flag',index:'add_flag', formatter: "string",width:60,align:'left',editable:false,sortable:false,hidden:true}
	];
	
	var params = {
		if_id    : $("#if_id").val(),
		corp_cd	 : sess_corp_cd,
		locale   : sess_locale
	};
	
	gridParam = {
		viewEdit : [{
			gridName     : "htmlTable",
			url          : "/hncis/taxi/doSearchListTxToRequestApprove.do",
			colNames     : cn,
			colModel     : cm,
			width        : "1122",
			height       : "100%",
			sortname     : "stap_ymd",
			sortorder    : "desc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			paramJson    : params,
			selectCellFc : "setTsGridValue(rowid, iCol, cellcontent);",
			completeFc   : "fnInitGridComplete();"
		}]
	};
	
	commonJqGridInit(gridParam, "N");
	
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
		              {startColumnName: 'stap_adr', numberOfColumns: 1, titleText: '출발'},
		              {startColumnName: 'arvp_adr', numberOfColumns: 1, titleText: '도착'}
		              ]
	});
	
//	doSearch();
}

function fnInitGridComplete(){
	addGridRow(5, 'htmlTable', 'datarow');
	
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	
	if($("#hid_cond").val() != ""){
		initAfterMenus();
		doSearch("Y");
	}
}

function doSearch(msgFlag){
	var params = {
		if_id    : $("#if_id").val(),
		corp_cd	 : sess_corp_cd,
		locale   : sess_locale
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};
	
	doCommonAjax("doSearchToSubmit.do", paramData, "loadCallBack(jsonData.sendResult);");
}

/**
 * callback
 */
function loadCallBack(result, msgFlag){
	loadJsonSet(result);
	
	$("#budget").val(result.cost_center);
	$("#budget_type").val(result.budget_code);
	if(result.budget_code == "D"){
		$("#budget_sel").attr("style", "width:120px;");
		$("#budget_sel").val(result.budget_no);
		$("#budget_wbs").attr("style", "display:none;");
		$("#budget_ipt").attr("style", "display:none;");
	}else if(result.budget_code == "I"){
		$("#budget_sel").attr("style", "width:120px;");
		$("#budget_sel").val(result.budget_no);
		$("#budget_wbs").attr("style", "display:none;");
		$("#b_ipt").html("<input type='text' id='budget_ipt' style='width:120px;' maxLength='7' class='disabled' readOnly>");
		$(".iptOnlyNumber").numeric();
	}else if(result.budget_code == "W"){
		$("#budget_sel").attr("style", "display:none;");
		$("#budget_wbs").attr("style", "width:120px;");
		$("#budget_wbs").val(result.budget_no);
		$("#b_ipt").html("<input type='text' id='budget_ipt' style='width:120px;' class='disabled' readOnly>");
	}
	$("#budget_ipt").val(result.budg_text);
	
	$("#amount").val(result.amount.replace(".",","));
	
	fnSubmitInfoSettings(result.pgs_st_cd, $("#if_id").val(), result.code, "snb_rson_sbc", "1");
	
//	displaySubmit(document ,result.code, 1);	

//	displaySubmit(document ,result.code, 1);
}

/**
 * techical method
 */
function change_info(type){
	document.getElementById("czk").style.display = "none"; 
	document.getElementById("kor").style.display = "none"; 
	document.getElementById("eng").style.display = "none"; 
	
	document.getElementById(type).style.display = "";
	
	document.getElementById("ttl_czk").style.background = "white";
	document.getElementById("ttl_kor").style.background = "white";
	document.getElementById("ttl_eng").style.background = "white";
	
	var tmp = eval("document.all.ttl_" + type);
	tmp.style.background = "#B6C4EB";
}

function setTsGridValue(rowId, iCol, cellcontent){
	var colNm = jQuery('#htmlTable').jqGrid('getGridParam', 'colModel')[iCol].index;

	setMiltiComboList(rowId,'transport_cd');
}
