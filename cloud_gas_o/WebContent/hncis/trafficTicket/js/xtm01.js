var saveCode;
var comboVal;
var comboVal1;
var comboVal2;
var comboVal3;
var comboVal4;
var lastsel;
var fnMerge;
var descInfo;

function fnSetDocumentReady(){
	initMenus('1850');
	$('#GS').slideDown('fast');
	if($("#hid_doc_no").val() != ""){
		$("#doc_no").val($("#hid_doc_no").val());
		$("#eeno").val($("#hid_eeno").val());
	}
	$(".inputOnlyNumber").numeric();
	sess_auth = $("#work_auth").val();
	setComboInfo();
}

function setComboInfo(){
	getCommonCode("key_pgs_st_cd:XTM02:Y;key_region_cd:X0004:Y;", "N", "fnGetDescInfo();", "/getCommonCombo.do");
}

function fnGetDescInfo(){
	getCommonCode("desc_info", "N", "setGridCombo(jsonData.sendResult);", "/hmc/trafficTicket/getCommonTrafficTicketDescCombo.do");
}

function setGridCombo(result){
	descInfo = result.desc_info;
	getCommonCode("region_cd:X0004:Z;", "Y", "init();", "/getCommonCombo.do");
}

var gridParam;
var gridName = "htmlTable";
var datarow = {tmp_car_no:"", tmp_eeno:"", doc_no:"", ptt_ymd:"", car_no:"", vehl_nm:"", eeno:"", eenm:"", pos_nm:"", dept_nm:"",po_no:"",
				tic_no:"", tic_aet:"",tic_code:"", tic_desc:"", tic_pint:"", tic_amt:"", tic_ymd:"", tic_time:"", region_cd:"", tic_place:"", tic_city:"", drv_lmt_ymd:"", pgs_st_cd:"", pgs_st_nm:"", tic_remarks:"", tic_payment:""};
function init(){
	var cn = ["", "", "", "", "Number Plate", "Vehicle", "User ID", "Name", "Position", "Dept", "PO No.",
	          "No.", "AIT", "Code", "Description", "Point", "Amout(R$)", "Date", "Time", "Region", "Place", "City", "Deadline To<br>Indicate The<br>Driver", "HR Payment", "", "Status", "Remarks"];
	var cm = [{name:'tmp_car_no', index:'tmp_car_no', formatter:'string', width:0, align:'center', editable:false, sortable:false, hidden:true},
	          {name:'tmp_eeno', index:'tmp_eeno', formatter:'string', width:0, align:'center', editable:false, sortable:false, hidden:true},
	          {name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"ptt_ymd", index:"ptt_ymd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"car_no", index:"car_no", sortable:false, formatter:"string", width:100, align:"left", editable:true, frozen:false},
	          {name:"vehl_nm", index:"vehl_nm", sortable:false, formatter:"string", width:150, align:"left", editable:false, frozen:false},
	          {name:"eeno", index:"eeno", sortable:false, formatter:"string", width:80, align:"left", editable:true, frozen:false},
	          {name:"eenm", index:"eenm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"pos_nm", index:"pos_nm", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"dept_nm", index:"dept_nm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"po_no", index:"po_no", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
	          {name:"tic_no", index:"tic_no", sortable:false, formatter:"string", width:100, align:"left", editable:true, frozen:false, hidden:true},
	          {name:"tic_aet", index:"tic_aet", sortable:false, formatter:"string", width:100, align:"left", editable:true, frozen:false},
	          {name:"tic_code", index:"tic_code", sortable:false, formatter:"string", width:100, align:"left", editable:true, frozen:false},
	          {name:"tic_desc", index:"tic_desc", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"tic_pint", index:"tic_pint", sortable:false, formatter:"string", width:80, align:"right", editable:false, frozen:false},
	          {name:"tic_amt", index:"tic_amt", sortable:false, formatter:numFormat, width:80, align:"right", editable:false, frozen:false,
	        	formatoptions: {
	  			    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
			  	},
  				editoptions: {dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = element.value = selectNum(element.value, ",");
		     		    	}
		     		    });
		            }
  				}
			  },
	          {name:"tic_ymd", index:"tic_ymd", sortable:false, formatter:"string", width:80, align:"left", editable:true, frozen:false,
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
	          {name:"tic_time",		index:"tic_time"		, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:true,	frozen : false,
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
	          {name:"region_cd", index:"region_cd", sortable:false, formatter:"select", width:100, align:"left", editable:true, frozen:false, edittype:'select',
	        	  editoptions:{value:getComboValue('region_cd'),
						dataInit: function(elem) {
							$(elem).width(100);
						}
	        	  }
	          },
	          {name:"tic_place", index:"tic_place", sortable:false, formatter:"string", width:120, align:"left", editable:true, frozen:false},
	          {name:"tic_city", index:"tic_city", sortable:false, formatter:"string", width:120, align:"left", editable:true, frozen:false},
	          {name:"drv_lmt_ymd", index:"drv_lmt_ymd", sortable:false, formatter:"string", width:80, align:"left", editable:true, frozen:false,
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
	          {name:"tic_payment", index:"tic_payment", sortable:false, formatter:"string", width:80, align:"left", editable:true, frozen:false,
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
	          {name:"pgs_st_cd", index:"pgs_st_cd", sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden: true},
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false},
	          {name:"tic_remarks", index:"tic_remarks", sortable:false, formatter:"string", width:200, align:"center", editable:true, frozen:false}
	];
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "/doSearchToEmpty.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			rownumbers   : false,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			shrinkToFit  : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');",
			selectCellFc : ""
		}]
	};
	commonJqGridInit(gridParam, "N");
	
	jQuery("#"+gridName).jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'tic_aet', numberOfColumns: 9, titleText: 'Traffic Ticket Information'}]
	});
	
	$("#" + gridName).setColProp('car_no', {editoptions:{maxlength:"7", dataEvents:[{type:"keyup",
    	fn:function(e){
    		var row = $(e.target).closest("tr.jqgrow");
    		var rowId = row.attr("id");
    		var cbs = $("#jqg_" + gridName + "_" + rowId);
    		if(!cbs.is(":checked")){
    			//$("#" + gridName).setSelection(rowId, true);
    			jQuery("#" + gridName).jqGrid("setSelection", rowId, true);
    		}
    		gridCarInfo(rowId);
    	}
		}]
	}});
	
	$("#" + gridName).setColProp('eeno', {editoptions:{dataEvents:[{type:"keyup",
    	fn:function(e){
    		var row = $(e.target).closest("tr.jqgrow");
    		var rowId = row.attr("id");
    		var cbs = $("#jqg_" + gridName + "_" + rowId);
    		if(!cbs.is(":checked")){
    			//$("#" + gridName).setSelection(rowId, true);
    			jQuery("#" + gridName).jqGrid("setSelection", rowId, true);
    		}
    		gridInsaInfo(rowId);
    	}
		}]
	}});
	
	$("#" + gridName).setColProp('tic_code', {editoptions:{maxlength:"6", dataEvents:[{type:"keyup",
    	fn:function(e){
    		var row = $(e.target).closest("tr.jqgrow");
    		var rowId = row.attr("id");
    		var cbs = $("#jqg_" + gridName + "_" + rowId);
    		if(!cbs.is(":checked")){
    			//$("#" + gridName).setSelection(rowId, true);
    			jQuery("#" + gridName).jqGrid("setSelection", rowId, true);
    		}
    		gridTransitoInfo(rowId);
    	}
		}]
	}});
}

function gridCarInfo(rowId){
	if(getColValue("car_no", rowId, gridName) != ""){
		if(getColValue("car_no", rowId, gridName).length > 7) return;
		if(getColValue("car_no", rowId, gridName).length == 7){
			$("#"+gridName).jqGrid("setCell", rowId, "tmp_eeno", " ");
			$("#"+gridName).jqGrid("setCell", rowId, "eenm", " ");
			$("#"+gridName).jqGrid("setCell", rowId, "pos_nm", " ");
			$("#"+gridName).jqGrid("setCell", rowId, "dept_nm", " ");
			
			if(getColValue("car_no", rowId, gridName) == getColValue("tmp_car_no", rowId, gridName)){ return; }
			$("#"+gridName).jqGrid("setCell", rowId, "tmp_car_no", getColValue("car_no", rowId, gridName));
			
			var keyData = { car_no : getColValue("car_no", rowId, gridName) };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("doSearchToCarInfo.do", paramData, "gridCarCallBack(jsonData.sendResult, '"+rowId+"')");
		}
	}
}

function gridCarCallBack(result, rowId){
	setBottomMsg(result.message, false);
	if(result.vehl_nm == ""){
		$("#"+gridName).jqGrid("setCell", rowId, "vehl_nm", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "eeno", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "eenm", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "pos_nm", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "dept_nm", " ");
		
	}else{
		$("#"+gridName).jqGrid("setCell", rowId, "vehl_nm", result.vehl_nm);
		$("#"+gridName).jqGrid("setCell", rowId, "eeno", result.eeno);
		$("#"+gridName).jqGrid("setCell", rowId, "eenm", result.eenm);
		$("#"+gridName).jqGrid("setCell", rowId, "pos_nm", result.pos_nm);
		$("#"+gridName).jqGrid("setCell", rowId, "dept_nm", result.dept_nm);
	}
	setRowEditMode(gridName, rowId);
}

function gridInsaInfo(rowId){
	if(getColValue("eeno", rowId, gridName) != ""){
		if(getColValue("eeno", rowId, gridName).length > 8) return;
		if(getColValue("eeno", rowId, gridName).length == 8){
			if(getColValue("eeno", rowId, gridName) == getColValue("tmp_eeno", rowId, gridName)){ return; }
			$("#"+gridName).jqGrid("setCell", rowId, "tmp_eeno", getColValue("eeno", rowId, gridName));
			
			var keyData = { xusr_empno : getColValue("eeno", rowId, gridName) };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToUserInfo.do", paramData, "gridInsaCallBack(jsonData.sendResult, '"+rowId+"')");
		}
	}
}

function gridInsaCallBack(result, rowId){
	$("#"+gridName).jqGrid("setCell", rowId, "eenm", result.xusr_name);
	$("#"+gridName).jqGrid("setCell", rowId, "pos_nm", result.xusr_step_name);
	$("#"+gridName).jqGrid("setCell", rowId, "dept_nm", result.xusr_dept_name);
}

function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
			doSearch();
			break;
	   case "save" :
		   	doSave();
			break;
	   case "delete" :
	   case "emailSend" :
	   case "done" :
	   case "doneCancel" :
	   case "payment" :
	   case "hrreport" :
	   case "hrreportCancel" :
		    doAction(btnFlag); 
		    break;
	   case "back" :
		    doBack();
		    break;
	   case "addrow" :
			fnAddRow();
			break;
	}
}

function fnParamJson(){
	var params = {
		car_no : $("#key_car_no").val().toUpperCase(),
		eeno : $("#key_eeno").val(),
		eenm : $("#key_eenm").val(),
		tic_aet : $("#key_tic_aet").val(),
		region_cd :$("#key_region_cd").val(),
		pgs_st_cd : $("#key_pgs_st_cd").val()
	};
	return params;
}

function doSearch(){
	doCommonSearch("doSearchByXtm01.do", util.jsonToString(fnParamJson()), "searchCallBack();");
}

function searchCallBack(){
	setBottomMsg("Succes search complete", false);
	addGridRow(15, gridName, datarow);
}

function doSave(){
	var paramsI = [];
	var paramsU = [];
	var actMode = "";
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(getColValue("car_no", rowId, gridName) == ""){
					alertUI("Please enter number plate.");
					return;
				}else if(getColValue("vehl_nm", rowId, gridName) == ""){
					alertUI("Number plate is wrong.");
					return;
				}else if(getColValue("tic_aet", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket AET.");
					return;
				}else if(getColValue("tic_desc", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket code.");
					return;
				}else if(getColValue("tic_pint", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket point.");
					return;
				}else if(getColValue("tic_amt", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket amount.");
					return;
				}else if(getColValue("tic_ymd", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket date.");
					return;
				}else if(getColValue("tic_time", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket time.");
					return;
				}else if(getColValue("region_cd", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket region.");
					return;
				}else if(getColValue("tic_place", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket place.");
					return;
				}else if(getColValue("tic_ymd", rowId, gridName) == ""){
					alertUI("Please enter traffic ticket city.");
					return;
				}else if(getColValue("drv_lmt_ymd", rowId, gridName) == ""){
					alertUI("Please enter deadline to indicate the dirver.");
					return;
				}else if(getColValue("tic_payment", rowId, gridName) == ""){
					alertUI("Please enter HR Payment.");
					return;
				}
				
				if(getColValue("doc_no", rowId, gridName) != ""){
					actMode = "modify";
					if(pgsStCd != '0'){
						alertUI("You can't modify in this status");
						return;
					}	
					list = {
						doc_no : getColValue("doc_no", rowId, gridName),
						car_no : getColValue("car_no", rowId, gridName),
						eeno : getColValue("eeno", rowId, gridName),
						tic_no : getColValue("tic_no", rowId, gridName),
						tic_aet : getColValue("tic_aet", rowId, gridName),
						tic_code : getColValue("tic_code", rowId, gridName),
						tic_desc : getColValue("tic_desc", rowId, gridName),
						tic_pint : getColValue("tic_pint", rowId, gridName),
						tic_amt : getColValue("tic_amt", rowId, gridName).replace(",", "."),
						tic_ymd : dateConversionKr(trimChar(getColValue("tic_ymd", rowId, gridName), "/")),
						tic_time : getColValue("tic_time", rowId, gridName),
						region_cd : getColValue("region_cd", rowId, gridName),
						tic_place : getColValue("tic_place", rowId, gridName),
						tic_city : getColValue("tic_city", rowId, gridName),
						tic_remarks : getColValue("tic_remarks", rowId, gridName),
						drv_lmt_ymd : dateConversionKr(trimChar(getColValue("drv_lmt_ymd", rowId, gridName), "/")),
						tic_payment : dateConversionKr(trimChar(getColValue("tic_payment", rowId, gridName), "/"))
					};
					paramsU.push(list);
				}else{
					actMode = "save";
					if(pgsStCd != ''){
						alertUI("You can't save in this status");
						return;
					}
					list = {
						car_no  : getColValue("car_no", rowId, gridName),
						ptt_ymd  : dateConversionKr(trimChar(getColValue("ptt_ymd", rowId, gridName), "/")), 
						eeno : getColValue("eeno", rowId, gridName),
						tic_no : getColValue("tic_no", rowId, gridName),
						tic_aet : getColValue("tic_aet", rowId, gridName),
						tic_code : getColValue("tic_code", rowId, gridName),
						tic_desc : getColValue("tic_desc", rowId, gridName),
						tic_pint : getColValue("tic_pint", rowId, gridName),
						tic_amt : getColValue("tic_amt", rowId, gridName).replace(",", "."),
						tic_ymd : dateConversionKr(trimChar(getColValue("tic_ymd", rowId, gridName), "/")),
						tic_time : getColValue("tic_time", rowId, gridName),
						region_cd : getColValue("region_cd", rowId, gridName),
						tic_place : getColValue("tic_place", rowId, gridName),
						tic_city : getColValue("tic_city", rowId, gridName),
						tic_remarks : getColValue("tic_remarks", rowId, gridName),
						drv_lmt_ymd : dateConversionKr(trimChar(getColValue("drv_lmt_ymd", rowId, gridName), "/")),
						tic_payment : dateConversionKr(trimChar(getColValue("tic_payment", rowId, gridName), "/"))
					};
					paramsI.push(list);
				}
			}
		}
	}
	
	confirmUI("Do you want to "+actMode+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramsI : util.jsonToList(paramsI),
						paramsU : util.jsonToList(paramsU)
					};
					doCommonAjax("doInsertByXtm01.do", paramData, "actionCallBack(jsonData.sendResult)");
			}
		});
	});
}

function doAction(btnFlag){
	var params = [];
	var isChk = "N";
	var btnNm = "";
	if(btnFlag == "delete"){
		btnNm = "Delete";
	}else if(btnFlag == "emailSend"){
		btnNm = "Email Send";
	}else if(btnFlag == "done"){
		btnNm = "Done";
	}else if(btnFlag == "doneCancel"){
		btnNm = "Done Cancel";
	}else if(btnFlag == "payment"){
		btnNm = "Payment";
	}else if(btnFlag == "hrreport"){
		btnNm = "HR Report";
	}else if(btnFlag == "hrreportCancel"){
		btnNm = "HR Report Cancel";
	}
	
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return false;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
//				if(btnFlag == "delete" || btnFlag == "emailSend"){
				
				if(getColValue("doc_no", rowId, gridName) == ""){
					alertUI("There is no data");
					return;
				}
				
				if(btnFlag == "delete"){
					if(pgsStCd != '0'){
						isChk = "Y";
					}
				}else if(btnFlag == "done"){
					if(pgsStCd != '2'){
						isChk = "Y";
					}
				}else if(btnFlag == "doneCancel"){
					if(pgsStCd != '3'){
						isChk = "Y";
					}
				}else if(btnFlag == "payment"){
					if(pgsStCd != '3'){
						isChk = "Y";
					}else if(getColValue("tic_amt", rowId, gridName).replace(",", ".") <= 0){
						alertUI("Amount is zero.");
						return;
					}
				}else if(btnFlag == "hrreport"){
					if(pgsStCd != '4'){
						isChk = "Y";
					}
				}else if(btnFlag == "hrreportCancel"){
					if(pgsStCd != '5'){
						isChk = "Y";
					}
				}
				if(isChk == "Y"){
					isChk = "N";
					alertUI("You can't "+btnNm+" in this status");
					return;
				}
				list = {
					doc_no    : getColValue("doc_no", rowId, gridName),
					tic_amt   : getColValue("tic_amt", rowId, gridName).replace(",", "."),
					cost_cd   : sess_cost_cd,
					eeno      : getColValue("eeno", rowId, gridName),
					pgs_st_cd : pgsStCd
				};
				params.push(list);
			}
		}
	}
	
	
	confirmUI("Do you want to "+btnNm+"?");
	fnStartLoading();
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramBtn : btnFlag, 
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doActionByXtm01.do", paramData, "actionCallBack(jsonData.sendResult)");
			}
		});
	});
}

function actionCallBack(result){
	fnEndLoading();
	setBottomMsg(result.message, true);
	doSearch();
}

function doBack(){
    var form = $("<form/>");
    form.attr("method" , "post");
    form.attr("id"     , "submitForm").attr("name", "submitForm");
    form.attr("action" , $("#hid_view_nm").val() + ".gas");
    var inp1 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val($("#hid_cond").val());
    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
    form.append(inp1, token);
    $("body").append(form);
    form.submit();
    form.remove();
}

function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	jQuery("#"+gridName).jqGrid("addRowData", gridRowId+1, datarow);
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

function fnAddRow(){
	var gridRowId = $("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
}

function gridTransitoInfo(rowId){
	if(getColValue("tic_code", rowId, gridName) != ""){
		if(getColValue("tic_code", rowId, gridName).length > 6) return;
		if(getColValue("tic_code", rowId, gridName).length == 6){
			var keyData = { 
				tic_code : getColValue("tic_code", rowId, gridName) 
			};
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("doSearchToTransitoInfo.do", paramData, "gridTransitoCallBack(jsonData.sendResult, '"+rowId+"')");
		}
	}
}

function gridTransitoCallBack(result, rowId){
	setBottomMsg(result.message, false);
	if(result.tic_desc == ""){
		$("#"+gridName).jqGrid("setCell", rowId, "tic_desc", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "tic_pint", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "tic_amt", " ");
	}else{
		$("#"+gridName).jqGrid("setCell", rowId, "tic_desc", result.tic_desc);
		$("#"+gridName).jqGrid("setCell", rowId, "tic_pint", result.tic_pint);
		$("#"+gridName).jqGrid("setCell", rowId, "tic_amt", result.tic_amt);
	}
	setRowEditMode(gridName, rowId);
}