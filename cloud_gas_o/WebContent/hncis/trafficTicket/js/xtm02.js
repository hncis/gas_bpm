var lastsel;
var fnMerge;

function fnSetDocumentReady(){
	initMenus();
	sess_auth = $("#work_auth").val();
	
	$("#key_from_ymd").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_ymd").datepicker({ dateFormat: "dd/mm/yy" });	
	$(".inputOnlyNumber").numeric();
	
	if(sess_auth != 6 && sess_auth != 5 && sess_mstu != "M"){
		$("#key_eeno").val(sess_empno);
		$("#key_eenm").val(sess_name);
		
		readOnlyStyle("key_eeno", 1);
		readOnlyStyle("key_eenm", 1);
	}else{
		readOnlyStyle("key_eeno", 2);
		readOnlyStyle("key_eenm", 2);
	}
	
	setComboInfo();
}

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var paramValue  = "key_pgs_st_cd:XTM02:Y;key_region_cd:X0004:Y;";
	getCommonCode(paramValue, "N", "setCondition();", url);
}

function setCondition(){
	$("#key_pgs_st_cd option:eq(1)").remove();
	if($("#hid_cond").val() != ""){
		var hidCond = $("#hid_cond").val().split("|");
		if(hidCond[0] != "") $("#key_eeno").val(hidCond[0]);
		if(hidCond[1] != "") $("#key_eenm").val(hidCond[1]);
		if(hidCond[2] != "") $("#key_region_cd").val(hidCond[2]);
		if(hidCond[3] != "") $("#key_tic_no").val(hidCond[3]);
		if(hidCond[4] != "") $("#key_pgs_st_cd").val(hidCond[4]);
		if(hidCond[5] != "") $("#key_from_ymd").val(hidCond[5]);
		if(hidCond[6] != "") $("#key_to_ymd").val(hidCond[6]);
	}
	
	init();
}

var gridParam;
var gridName = "htmlTable";
var datarow = {doc_no:"", ptt_ymd:"", eeno:"", eenm:"", car_no:"", vehl_nm:"",po_no:"",
				tic_no:"", tic_aet:"", tic_code:"", tic_desc:"", tic_pint:"", tic_amt:"", tic_ymd:"", tic_time:"", region_nm:"", tic_place:"", tic_city:"", drv_lmt_ymd:"", pgs_st_nm:"", tic_remarks:"", tic_payment:""};
function init(){
	var cn = ["", "", "Status", "User ID", "Name", "Number Plate", "Vehicle", "PO No.", "No.", "AIT", "Code", "Description", "Point", "Amout", "Date", "Time", "Region", "Place", "City", "Deadlie To<br>Indicate The<br>Driver", "HR Payment", "Remarks"];
	var cm = [{name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"ptt_ymd", index:"ptt_ymd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"eeno", index:"eeno", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
	          {name:"eenm", index:"eenm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"car_no", index:"car_no", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"vehl_nm", index:"vehl_nm", sortable:false, formatter:"string", width:150, align:"left", editable:false, frozen:false},
	          {name:"po_no", index:"po_no", sortable:false, formatter:"string", width:150, align:"left", editable:false, frozen:false},
	          {name:"tic_no", index:"tic_no", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false, hidden:true},
	          {name:"tic_aet", index:"tic_aet", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"tic_code", index:"tic_code", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"tic_desc", index:"tic_desc", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"tic_pint", index:"tic_pint", sortable:false, formatter:"string", width:80, align:"right", editable:false, frozen:false},
	          {name:"tic_amt",	index:"tic_amt"	, sortable:false,formatter:numFormat, formatoptions: {
	        	  decimalSeparator:",",
	        	  thousandsSeparator:".",
	        	  decimalPlaces:2,
	        	  defaultValue:""
	          },	width:80,	align:"right",	editable:false,	frozen : false,
				editoptions: {maxlength:"10", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    });
		            }
		        }
			  },
			  {name:"tic_ymd", index:"tic_ymd", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
			  {name:"tic_time", index:"tic_time", sortable:false, formatter:"string", width:60, align:"left", editable:false, frozen:false},
			  {name:"region_nm", index:"region_nm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
			  {name:"tic_place", index:"tic_place", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
			  {name:"tic_city", index:"tic_city", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"drv_lmt_ymd", index:"drv_lmt_ymd", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
	          {name:"tic_payment", index:"tic_payment", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
	          {name:"tic_remarks", index:"tic_remarks", sortable:false, formatter:"string", width:200, align:"left", editable:false, frozen:false}
	];
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchByXtm02.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			paramJson    : fnParamJson(),
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			shrinkToFit  : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);"
		}]
	};
	commonJqGridInit(gridParam, "N");
	
	jQuery("#"+gridName).jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'tic_aet', numberOfColumns: 9, titleText: 'Traffic Ticket Information'}]
	});
}

function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
		   	doSearch();
			break;
	   case "excel" :
		   doExcel();
		   break;
	}
}

function fnParamJson(){
	var params = {
		eeno : $("#key_eeno").val(),
		eenm : $("#key_eenm").val(),
		region_cd : $("#key_region_cd").val(),
		from_ymd : dateConversionKr(selectNum($("#key_from_ymd").val())),
		to_ymd : dateConversionKr(selectNum($("#key_to_ymd").val())),
		tic_aet : $("#key_tic_no").val().toUpperCase(),
		pgs_st_cd : $("#key_pgs_st_cd").val()
	};
	return params;
}

function doSearch(){
	doCommonSearch("doSearchByXtm02.do", util.jsonToString(fnParamJson()), "loadCallBack();", gridName, "N");
}

var excelCn = ["Status", "User ID", "Name", "Number Plate", "Vehicle", "No.", "AET", "Description", "Point", "Amout", "Date", "Time", "Region", "Place", "City", "Deadlie To<br>Indicate The<br>Driver", "Remarks"];
var excelCm = ["pgs_st_nm", "eeno", "eenm", "car_no", "vehl_nm", "tic_no", "tic_aet", "tic_desc", "tic_pint", "tic_amt", "tic_ymd", "tic_time", "region_nm", "tic_place", "tic_city", "drv_lmt_ymd", "tic_remarks"];
var excelFm = ["string", "string", "string", "string", "string", "string", "string", "string", "integer", "integer", "string", "string", "string", "string", "string", "string", "string"];
function doExcel(){
	var params = [
		{name : "fileName",		value : "traffic_ticket_excel" },
		{name : "header",		value : util.jsonToArray(excelCn)},
		{name : "headerName",	value : util.jsonToArray(excelCm)},
		{name : "fomatter",		value : util.jsonToArray(excelFm)},
		{name : "paramJson",	value : util.jsonToString(fnParamJson())},
		{name : "firstUseYn",	value : "Y"},
		{name : "page",			value : "1"}
	];
	gridToExcel("#"+gridName, "doSearchByXtm02ExcelList.excel", params);
}

function loadCallBack(){
	addGridRow();
	//setMerge();
}

function setMerge(){
    groupTable($("#"+gridName+" tr:has(td)"), 1, 7);
    $("#"+gridName+" .deleted").remove();
}

function celldbClickAction(rowId,iRow,iCol,e){
	if(iCol > 2){
		if(getColValue("doc_no",rowId, gridName) != ""){
            var form = $("<form/>");
            form.attr("method" , "post");
            form.attr("id"     , "hideForm");
            form.attr("action" , "xtm03.gas");
            var inp1 = $("<input type='hidden' id='hid_doc_no' name='hid_doc_no'/>").val(getColValue("doc_no",rowId, gridName));
            var inp2 = $("<input type='hidden' id='hid_eeno' name='hid_eeno'/>").val(getColValue("eeno",rowId, gridName));
            var inp3 = $("<input type='hidden' id='hid_view_nm' name='hid_view_nm'/>").val($("#menu_id").val());
            var cond = "";
            cond += $("#key_eeno").val();
            cond += "|" + $("#key_eenm").val();
            cond += "|" + $("#key_region_cd").val();
            cond += "|" + $("#key_tic_no").val();
            cond += "|" + $("#key_pgs_st_cd").val();
            cond += "|" + $("#key_from_ymd").val();
            cond += "|" + $("#key_to_ymd").val();
            
            var inp4 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val(cond);
            var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
            form.append(inp1, inp2, inp3, inp4, token);
            $("body").append(form);
            form.submit();
            form.remove();
		}
	}
}

function clearInsa(){
	if($("#key_eeno").val() == ""){
		$("#key_eeno").val("");
		$("#key_eenm").val("");
	}
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
	$("#key_eeno").val(result.xusr_empno);
	$("#key_eenm").val(result.xusr_name);
}