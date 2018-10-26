jQuery(document).ready(function(){
	setComboInfo();
	
	$("#sap_exp").css('visibility', 'hidden');
	$("#vendor").css('visibility', 'hidden');
	$("#cancel").css('visibility', 'hidden');
	
	submitBtnChange();
});

var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;
var gridName1 = "htmlTable";
var excelCn   = ["Apply Date", "ID no", "Name", "Grade"];
var datarow   = {doc_no:"",eeno:"",seq:"",prvs_scn_cd:"", prvs_dtl_cd:"", strt_ymd:"", fnh_ymd:"", stl_way_cd:"", curr_cd:"", apl_xr:"", prvs_amt:"", 
		natv_curr_amt:"", arsl_refl_yn:"", evds_attc_fil_nm:"", rem_sbc:""};

function init(){
	
	var cn        = ["DocNo.", "Eeno", "Seq", "Category", "Item", "From", "To", "Means of form", "Currency", "Conversion Rate", "Payment", "National Payment", "Sap", "Receipt", "Remark"];
	var cm =
		[
			{name:"doc_no",			index:"doc_no"	, sortable:false,			formatter:"string",		width:100,	align:"center",	editable:false,	frozen:true, 	hidden:true},
			{name:"eeno",			index:"eeno"	, sortable:false,			formatter:"string",		width:0,	align:"center",	editable:false,	frozen:true, 	hidden:true},
			{name:"seq",			index:"seq"		, sortable:false,			formatter:"string",		width:100,	align:"left",	editable:false,	frozen:false, 	hidden:true},
			{name:'prvs_scn_cd',	index:'prvs_scn_cd', sortable:false,		formatter:"select",	width:200,	align:'center',	editable:false,	frozen:true,	edittype:'select', 
				editoptions:{value:getComboValue('prvs_scn_cd'), dataInit: function(elem) {$(elem).width(190);},
		        	dataEvents:[{type:'change', 
						fn:function(e){
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
	                        var cbs = jQuery("#jqg_"+gridName1+"_"+rowId);
	                        if(!cbs.is(":checked")){
	                        	jQuery("#"+gridName1).jqGrid("setSelection", rowId, true);
	                        }
	                        
		                    multiComboController(gridName1, $(e.target).closest('tr.jqgrow'), 0, "prvs_scn_cd:prvs_dtl_cd", "comboVal1", "prvs_dtl_cd");
		        		}
		        	},
		        	{type:'focus', 
						fn:function(e){
//		                    multiComboController(gridName1, $(e.target).closest('tr.jqgrow'), 0, "prvs_scn_cd:prvs_dtl_cd", "comboVal1", "prvs_dtl_cd");
		        		}
		        	}]
			    },
			    editrules:{required:true}
			},
			{name:'prvs_dtl_cd',	index:'prvs_dtl_cd', sortable:false,		formatter: "select",	width:200,		align:'center',	editable:false,	frozen:true,
				edittype:'select', 
				editoptions:{value:getComboValueMulti('REPORT'), 
					dataInit: function(elem) {
								$(elem).width(190);
					},
					dataEvents:[{type:'change', 
						fn:function(e){
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
	                        var cbs = jQuery("#jqg_"+gridName1+"_"+rowId);
	                        if(!cbs.is(":checked")){
	                        	jQuery("#"+gridName1).jqGrid("setSelection", rowId, true);
	                        }
		        		}
		        	},
		        	{type:'focus', 
						fn:function(e){
//		                    multiComboController(gridName1, $(e.target).closest('tr.jqgrow'), 0, "prvs_scn_cd:prvs_dtl_cd", "comboVal1", "prvs_dtl_cd");
		        		}
		        	}]
				},
			    editrules:{required:true}
			},
			{name:"strt_ymd",	index:"strt_ymd"	, sortable:false,	formatter:"string",		width:70,	align:"center",	editable:false,	frozen : false,
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
			{name:"fnh_ymd",	index:"fnh_ymd"	, sortable:false,	formatter:"string",		width:70,	align:"center",	editable:false,	frozen : false,
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
			{name:'stl_way_cd',	index:'stl_way_cd', sortable:false,		formatter: "select",	width:90,	align:'center',	editable:false,	edittype:'select', 
				editoptions:{value:getComboValue('stl_way_cd'), dataInit: function(elem) {$(elem).width(90);}
			        },
			        editrules:{required:true}
			},
			{name:'curr_cd',	index:'curr_cd', sortable:false,		formatter: "select",	width:70,	align:'center',	editable:false,	edittype:'select', 
				editoptions:{value:getComboValue('curr_cd'), dataInit: function(elem) {$(elem).width(50);}
			        },
			        editrules:{required:true}
			},
			{name:"apl_xr",		index:"apl_xr"		, sortable:false,	formatter:numFormat, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:70,	align:"right",	editable:false,	frozen : false,
				editoptions: {maxlength:"8", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    	
		     		    	var row = $(element).closest('tr.jqgrow');
	     		    		var rowId = row.attr('id');
	     		    		var tempPay = fixNumberScale(getColValue('prvs_amt', rowId).replace(",", ".") * element.value.replace(",", "."), 2);
	     		    		
	     		    		jQuery("#"+gridName1).setCell(rowId, 'natv_curr_amt',  tempPay);
		     		    });
		            }
		        }
			},
			{name:"prvs_amt",		index:"prvs_amt"		, sortable:false,	formatter: numFormat, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },
			  width:70,	align:"right",	editable:false,	frozen : false,
				editoptions: {maxlength:"10", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    	
		     		    	var row = $(element).closest('tr.jqgrow');
	     		    		var rowId = row.attr('id');
	     		    		var tempPay = fixNumberScale(getColValue('apl_xr', rowId).replace(",", ".") * element.value.replace(",", "."), 2);
	     		    		
	     		    		jQuery("#"+gridName1).setCell(rowId, 'natv_curr_amt',  tempPay);
		     		    });
		            }
		        }
			},
			{name:"natv_curr_amt",		index:"natv_curr_amt"		, sortable:false,	formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:"",
			    suffix:" R$"
			  },width:70,	align:"right",	editable:false,	frozen : false
			},
			{name:'arsl_refl_yn',	index:'arsl_refl_yn'		, sortable:false,	formatter:"select",		width:45, 	align:"center",	editable:false, 	edittype:'select',
				editoptions:{value:"Y:Y;N:N"
		    	,dataInit: function(elem) {
	        	$(elem).width(40);
	    		}}
			},
			{name:"evds_attc_fil_nm",	index:"evds_attc_fil_nm", sortable:false,	formatter:"string",		width:80,	align:"center",	editable:false,	frozen : false},
			{name:"rem_sbc",	index:"rem_sbc", sortable:false,	formatter:"string",		width:190,	align:"left",	editable:false,	frozen : false}
		];
	
	var params = {
			doc_no     : $("#hid_doc_no").val(),
			eeno       : $("#hid_eeno").val()
		};
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchBTToReport.do",
			colNames     : cn,
			colModel     : cm,
			width        : "930",
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : false,
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "searchCallBack();",
			selectCellFc : "setBTGridValue(rowid, iCol, cellcontent);setChangeImg();",
			selectRowFc	 : "selectRowControll(rowid);",
			selectAllFc  : "selectAllControll();",
			paramJson    : params,
			rowNum       : "20"
		}]
	};
	
	commonJqGridInit(gridParam, "N");
	jQuery("#"+gridName1).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
//	setGridColumnOptions();
}

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var paramValue = "key_pgs_st_cd:X0009:A;prvs_scn_cd:X0013;";
	getCommonCode('prvs_scn_cd:X0013;stl_way_cd:X0015;curr_cd:X0016', 'Y', 'setMultiCombo();');
}

function setMultiCombo(){
	
	var params = {
			codknd	: 'X0014'
		};

	doCommonAjax("doSearchBTToMultiComboByReport.do", params,"comboCallBack(jsonData.sendResult);");
}

function comboCallBack(jsonData){
	comboVal1 = jsonData;
	beforeInit();
}

function beforeInit(){
//	$("#key_aply_eeno").val(sess_empno);
//	$("#key_ops_cd").val(sess_dept_code);
//	$("#key_ops_nm").val(sess_dept_name);
	$("#key_ops_nm").attr("readonly", true);
	$("#deptBtn").attr("disabled", true);
	
	init();
}

function doSearch(msgFlag){
	
	var params = {
		eeno       : $("#key_aply_eeno").val(),
		ee_nm      : $("#key_ee_nm").val().toUpperCase(),
		fromDate   : dateConversionKr($("#key_from_date").val()),
		toDate     : dateConversionKr($("#key_to_date").val()),
		ops_cd     : $("#key_ops_cd").val(),
		pgs_st_cd  : $("#key_pgs_st_cd").val(),
		doc_no     : $("#key_doc_no").val(),
		ad_req_yn  : $("#key_req_yn").val()
	};
	
	doCommonSearch("doSearchBTToList.do", util.jsonToString(params), "loadCallBack();", gridName1, msgFlag);
}

function loadCallBack(){
	addGridRow();
}

//function setMiltiComboList(rowId,colNm){	
//	if(typeof(comboVal1) == 'undefined'){return;}
//	if(colNm == 'prvs_dtl_cd'){
//		var colValue = getColValue('prvs_scn_cd',rowId);
//		if(colValue == ''){
//			$("select#" + rowId +"_"+ colNm).html('<option role="option" value=""></option>');
//		}
//		else{
//			comboVal2="";
//			$.each(eval(comboVal1),function(targetNm,optionData){
//				$.each(eval(optionData),function(index,optionData){
//	    			if(targetNm=='REPORT'&&colValue == optionData.key){
//	    				comboVal2 += '<option role="option" value="' +                                                   
//			            optionData.value + '">' +                                                   
//						optionData.name + '</option>';
//	        		}
//				});
//			});
//			$("select#" + rowId +"_"+ colNm).html(comboVal2);
//		}
//	}
//	setChangeImg();
//};

function getMiltiComboListVal(rowId,colNm){	
	
	var returnVal = "";
	if(typeof(comboVal1) == 'undefined'){return returnVal;}
	if(colNm == 'prvs_dtl_cd'){
		var colValue = getColValue('prvs_scn_cd',rowId);
		if(colValue == ''){
			return returnVal;
		}
		else{
			returnVal="";
			$.each(eval(comboVal1),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm=='REPORT'&&colValue == optionData.key){
	    				returnVal += '<option role="option" value="' +                                                   
			            optionData.value + '">' +                                                   
						optionData.name + '</option>';
	        		}
				});
			});
		}
	}
	return returnVal;
};

function getComboValueMulti(comboName){

	var returnVal;
	if(typeof(comboVal1) == 'undefined'){
		returnVal = ":";
	}
	else{
		var i = 0;
		$.each(eval(comboVal1),function(targetNm,optionData){
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

function setBTGridValue(rowId, iCol, cellcontent){
//	setMiltiComboList(rowId,'prvs_dtl_cd');
	multiComboController(gridName1, rowId, 0, "prvs_scn_cd:prvs_dtl_cd", "comboVal1", "prvs_dtl_cd", rowId);
	
//	var colNm = jQuery('#'+gridName1).jqGrid('getGridParam', 'colModel')[iCol].index;
	
//	if(colNm == 'prvs_scn_cd' || colNm == 'prvs_dtl_cd' ){
//		setMiltiComboList(rowId,colNm);
//	}
}

function retrieve(gubn){
	switch(gubn){
		case "save" :
			doSave();
			break;
		case "delete" :
			doDelete();
			break;
		case "excel" :
			doExcel();
			break;
		case "vendor" :
			doVendor();
			break;
		case "cancel" :
			doCancel();
			break;
		case "submit" :
			doSubmit();
			break;
		case "print" :
			doPrint();
			break;
	}
}

function doSave(){
	var params = [];
	var reportInfoI = [];
	var reportInfoU = [];
	var msg = "";
	
	if($("#hid_pgs_st_cd").val() != "0"){
		alertUI("Expense Data can not be saved.");
		return;
	}
	
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				if(!(Number(sess_auth) > 4 || sess_mstu == "M")){
					if(getColValue("arsl_refl_yn", rowId) == "Y"){
						alertUI(rowId + " line : Data can not be saved.");
						return;
					}
				}
				if(getColValue("prvs_dtl_cd", rowId) == ""){
					alertUI(rowId + " line : Choice Item.");
					return;
				}
				if(getColValue("strt_ymd", rowId) == ""){
					alertUI(rowId + " line : Enter From Date.");
					return;
				}
				if(getColValue("fnh_ymd", rowId) == ""){
					alertUI(rowId + " line : Enter To Date.");
					return;
				}
				if(getColValue("stl_way_cd", rowId) == ""){
					alertUI(rowId + " line : Choice Means of Payment.");
					return;
				}
				if(getColValue("curr_cd", rowId) == ""){
					alertUI(rowId + " line : Choice Currency.");
					return;
				}
				if(getColValue("apl_xr", rowId) == ""){
					alertUI(rowId + " line :  Enter To Conversion Rate.");
					return;
				}
				if(getColValue("prvs_amt", rowId) == ""){
					alertUI(rowId + " line :  Enter To Payment.");
					return;
				}
				
				if(getColValue("doc_no", rowId, gridName1) == $("#hid_doc_no").val()){
					data = {
						doc_no			: getColValue("doc_no", rowId, gridName1),
						eeno			: getColValue("eeno", rowId, gridName1),
						seq				: getColValue("seq", rowId, gridName1),
						prvs_scn_cd		: getColValue("prvs_scn_cd", rowId, gridName1),
						prvs_dtl_cd		: getColValue("prvs_dtl_cd", rowId, gridName1),
						strt_ymd		: dateConversionKr(trimChar(getColValue("strt_ymd", rowId, gridName1), "/")),
						fnh_ymd			: dateConversionKr(trimChar(getColValue("fnh_ymd", rowId, gridName1), "/")),
						stl_way_cd		: getColValue("stl_way_cd", rowId, gridName1),
						curr_cd			: getColValue("curr_cd", rowId, gridName1),
						apl_xr			: getColValue("apl_xr", rowId, gridName1).replace(",", "."),
						prvs_amt		: getColValue("prvs_amt", rowId, gridName1).replace(",", "."),
						natv_curr_amt	: getColValue("natv_curr_amt", rowId, gridName1),
						rem_sbc			: getColValue("rem_sbc", rowId, gridName1),
						ipe_eeno		: sess_empno,
						updr_eeno		: sess_empno
					};
					reportInfoU.push(data);
				}else{
					data = {
						doc_no			: $("#hid_doc_no").val(),
						eeno			: $("#hid_eeno").val(),
						seq				: getColValue("seq", rowId, gridName1),
						prvs_scn_cd		: getColValue("prvs_scn_cd", rowId, gridName1),
						prvs_dtl_cd		: getColValue("prvs_dtl_cd", rowId, gridName1),
						strt_ymd		: dateConversionKr(trimChar(getColValue("strt_ymd", rowId, gridName1), "/")),
						fnh_ymd			: dateConversionKr(trimChar(getColValue("fnh_ymd", rowId, gridName1), "/")),
						stl_way_cd		: getColValue("stl_way_cd", rowId, gridName1),
						curr_cd			: getColValue("curr_cd", rowId, gridName1),
						apl_xr			: getColValue("apl_xr", rowId, gridName1).replace(",", "."),
						prvs_amt		: getColValue("prvs_amt", rowId, gridName1).replace(",", "."),
						natv_curr_amt	: getColValue("natv_curr_amt", rowId, gridName1),
						rem_sbc			: getColValue("rem_sbc", rowId, gridName1),
						ipe_eeno		: sess_empno,
						updr_eeno		: sess_empno
					};
					reportInfoI.push(data);
				}
			}
		}
		
		var schedulerInfo = {
				doc_no			: $("#hid_doc_no").val(),
				eeno			: $("#hid_eeno").val()
			};
		
		if(msg != ""){
			alertUI(msg);
			return;
		}
		
		confirmUI("저장 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							schedulerInfo 	: util.jsonToString(schedulerInfo),
							reportInfoI		: util.jsonToList(reportInfoI),
							reportInfoU		: util.jsonToList(reportInfoU)
					};
					doCommonAjax("doSaveBTToReport.do", paramData, "saveCallBack(jsonData.sendResult);");
				}
			});
		});
	}
}

function doDelete(){
	var params = [];
	var reportInfoD = [];
	var msg = "";
	
	if($("#hid_pgs_st_cd").val() != "0"){
		alert("Expense Data can not be deleted.");
		return;
	}
	
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				
				if(getColValue("arsl_refl_yn", rowId) == "Y"){
					alertUI(rowId + " line : Data can not be deleted.");
					return;
				}
				
				if(getColValue("doc_no", rowId, gridName1) == $("#hid_doc_no").val()){
					data = {
						doc_no			: getColValue("doc_no", rowId, gridName1),
						eeno			: getColValue("eeno", rowId, gridName1),
						seq				: getColValue("seq", rowId, gridName1)
					};
					reportInfoD.push(data);
				}
			}
		}
		
		confirmUI("삭제 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							reportInfoD		: util.jsonToList(reportInfoD)
					};
					doCommonAjax("doDeleteBTToReport.do", paramData, "deleteCallBack(jsonData.sendResult);");
				}
			});
		});
	}
}

function saveCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function deleteCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function doSearch(){
	var params = {
		doc_no     : $("#hid_doc_no").val(),
		eeno       : $("#hid_eeno").val()
	};
	
	doCommonSearch("doSearchBTToReport.do", util.jsonToString(params), "searchCallBack();", gridName1, "N");
}

var maxSeq  = 0;

function searchCallBack(){
	
	addGridRow(20);
	
	var gridRow  = jQuery("#"+gridName1);
	var ids      = gridRow.getDataIDs();
	maxSeq  = 0;

	for(var i=0;i<ids.length;i++){
		if(getColValue("seq", i+1, gridName1)!=""){
			if(getColValue("seq", i+1, gridName1) > maxSeq){
				maxSeq = getColValue("seq", i+1, gridName1);
			}
		}
		var dt = jQuery("#htmlTable").getRowData(ids[i]);
		if(dt['arsl_refl_yn'] == "Y"){
			var cbs = jQuery("#jqg_htmlTable_"+ids[i]);
			cbs.attr("disabled", true);
		}
	}
	
	setChangeImg();
}

function setChangeImg(){
	var gridRow  = jQuery("#"+gridName1);
	var ids      = gridRow.getDataIDs();

	for(var i=0;i<ids.length;i++){
		if(getColValue("doc_no", i+1, gridName1)!=""){
			var imgSrc = "<img src='../../images/hncis_bttn/open-n.gif' onClick='doFileAttach("+getColValue("seq", i+1, gridName1)+");'/>";
			gridRow.jqGrid("setRowData", i+1, {evds_attc_fil_nm:imgSrc});
		}
	}
}

var win;
function doFileAttach(seq){
	if(win != null){ win.close(); }
	var url = "businessTravel_file.gas", width = "460", height = "350";
		
	$("#file_seq").val(seq);
	
	win = newPopWin("about:blank", width, height, "win_file");
	
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
	
}

function doExcel(){

	var params = [];
	var reportCash = [];
	var reportCard = [];
	var msg = "";
	
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				
				if(getColValue("doc_no", rowId, gridName1) == $("#hid_doc_no").val()){
					if(getColValue("stl_way_cd", rowId, gridName1) == "BT001"){			// CARD
						data = {
							doc_no			: getColValue("doc_no", rowId, gridName1),
							eeno			: getColValue("eeno", rowId, gridName1),
							seq				: getColValue("seq", rowId, gridName1),
							arsl_refl_yn	: "Y"
						};
						reportCard.push(data);
					}else if(getColValue("stl_way_cd", rowId, gridName1) == "BT002"){	// CASH
						data = {
							doc_no			: getColValue("doc_no", rowId, gridName1),
							eeno			: getColValue("eeno", rowId, gridName1),
							seq				: getColValue("seq", rowId, gridName1),
							arsl_refl_yn	: "Y"
						};
						reportCash.push(data);
					}
				}
			}
		}
		
		if(msg != ""){
			alertUI(msg);
			return;
		}
		
		confirmUI("Do you want to export Sap Data?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var params = [
						      		{name : "fileName",		value : "ExpenseExcel" },
						      		{name : "headerLength",	value : "13" },
						      		{name : "reportCard",	value : util.jsonToList(reportCard)},
						      		{name : "reportCash",	value : util.jsonToList(reportCash)}
						      	];
						
						gridToExcel("#"+gridName1, "doExpenseExcelToList.excel", params);
						
						setTimeout("doSearch();", 3000);
				}
			});
		});
	}
}

function doVendor(){

	var params = [];
	var reportVendor = [];
	var msg = "";
	
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				
				if(getColValue("doc_no", rowId, gridName1) == $("#hid_doc_no").val()){
					if(getColValue("stl_way_cd", rowId, gridName1) == "BT003"){
						data = {
							doc_no			: getColValue("doc_no", rowId, gridName1),
							eeno			: getColValue("eeno", rowId, gridName1),
							seq				: getColValue("seq", rowId, gridName1),
							arsl_refl_yn	: "Y"
						};
						reportVendor.push(data);
					}
				}
			}
		}
		
		if(msg != ""){
			alertUI(msg);
			return;
		}
		
		confirmUI("Do you want to export vendor Data?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							reportVendor		: util.jsonToList(reportVendor)
					};
					doCommonAjax("doExpenseVendorToList.do", paramData, "vendorCallBack(jsonData.sendResult);");
				}
			});
		});
	}
}

function doCancel(){

	var params = [];
	var reportCancel = [];
	var msg = "";
	
	var selectRow = jQuery("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				
				if(getColValue("doc_no", rowId, gridName1) == $("#hid_doc_no").val()){
					if(getColValue("arsl_refl_yn", rowId, gridName1) == "Y"){
						data = {
							doc_no			: getColValue("doc_no", rowId, gridName1),
							eeno			: getColValue("eeno", rowId, gridName1),
							seq				: getColValue("seq", rowId, gridName1),
							arsl_refl_yn	: "N"
						};
						reportCancel.push(data);
					}
				}
			}
		}
		
		if(msg != ""){
			alertUI(msg);
			return;
		}
		
		confirmUI("Do you want to Sap Data Cancel?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							reportCancel		: util.jsonToList(reportCancel)
					};
					doCommonAjax("doExpenseDataCancelToList.do", paramData, "cancelCallBack(jsonData.sendResult);");
				}
			});
		});
	}
}

function cancelCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function vendorCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function chk_auth(){
	var frm = document.mainForm;
	
	with(frm){
		if(Number(sess_auth) > 4 || sess_mstu == "M"){
			$("#sap_exp").css('visibility', '');
			$("#vendor").css('visibility', '');
			$("#cancel").css('visibility', '');
		}
	}
}

var disableArray = new Array();
var disableCnt = 0;

function selectAllControll(){	
	var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
	disableCnt = 0;
	
	if(Number(sess_auth) > 4 || sess_mstu == "M"){
		return;
	}
	
	if(selectRow.length > 0){
		for(i=0; i<selectRow.length; i++){			
			if(getColValue("arsl_refl_yn", selectRow[i], "htmlTable") == "Y"){
				disableArray[disableCnt] = selectRow[i];
				disableCnt ++;
			}
		}
	}
	
	for(i=0; i<disableArray.length; i++){
		jQuery("#htmlTable").jqGrid("setSelection", disableArray[i], false);
	}
	disableArray.length = 0;
}

function selectRowControll(rowId){	
	if(Number(sess_auth) > 4 || sess_mstu == "M"){
		return;
	}
	
	if(getColValue("arsl_refl_yn", rowId, "htmlTable") == "Y"){
		jQuery("#htmlTable").jqGrid("setSelection", rowId, false);
	}
}

function doSubmit() {
	
	if($("#hid_pgs_st_cd").val() != "0"){
		alertUI("Expense Data can not submit.");
		return;
	}
	
	var expenseInfo = {
		doc_no			: $("#hid_doc_no").val(),
		eeno			: $("#hid_eeno").val(),
		pgs_st_cd		: "Z",
		updr_eeno       : sess_empno
	};
	
	confirmUI("Do you want to Submit Expenses?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						expenseInfo 		: util.jsonToString(expenseInfo)
					};
					doCommonAjax("doSubmitBTToReport.do", paramData, "submitCallBack(jsonData.sendResult);");
			}
		});
	});
}

function submitCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
	$("#hid_pgs_st_cd").val("Z");
	
	submitBtnChange();
}

function submitBtnChange(){
	if($("#hid_pgs_st_cd").val() == "Z"){
		//$("#btn_s").attr('disabled', 'disabled');
		$("#btn_d").attr('disabled', 'disabled');
		$("#btn_submit").attr('disabled', 'disabled');
	}else{
		$("#btn_s").removeAttr('disabled');
		$("#btn_d").removeAttr('disabled');
		$("#btn_submit").removeAttr('disabled');
	}
}

function doPrint(){
	
	var expenseInfo = {
		doc_no			: $("#hid_doc_no").val(),
		eeno			: $("#hid_eeno").val()
	};
	
	var frm = document.mainForm;
	frm.expenseInfo.value = util.jsonToString(expenseInfo);
	frm.action = "doExpensePrint.do";
	frm.submit();
	
}

function multiComboController(gridName, row, multiCnt, arrayStr, comboValArrayStr, comboKeyArrayStr, iRow){
	var rowId;
	if(typeof(row) == "object"){
		rowId = row.attr('id');
	}else{
		rowId = iRow;
	}
	var arrCol = arrayStr.split(":");
	var arrCombo = comboValArrayStr.split(":");
	var arrComboKey = comboKeyArrayStr.split(":");
	var colValue = getColValue(arrCol[multiCnt],rowId, gridName);
	var comboVal = arrCombo[multiCnt];
	
	if(typeof(comboVal) == 'undefined'){return;}
	if(multiCnt == 0){
		if(colValue == ''){
			$("select#" + rowId +"_"+ arrCol[multiCnt]).html('<option role="option" value=""></option>');
		}else{
			var comboValTemp="";
			$.each(eval(comboVal),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm=="REPORT"&&colValue == optionData.key){
	    				comboValTemp += '<option role="option" value="' +
			            optionData.value + '">' +
						optionData.name + '</option>';
	        		}
				});
			});
			var orgSelect = jQuery('#' + rowId + '_'+arrCol[multiCnt+1])[0];
			var orgValue = getColValue(arrCol[multiCnt+1],rowId, gridName);
			$("select#" + rowId +"_"+ arrCol[multiCnt+1]).html(comboValTemp);
			jQuery(orgSelect).val(orgValue);
		}
	}else if(multiCnt == 1){
		if(colValue == ''){
			$("select#" + rowId +"_"+ arrCol[multiCnt]).html('<option role="option" value=""></option>');
		}else{
			var comboValTemp="";
			$.each(eval(comboVal),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm==arrComboKey[multiCnt]&&colValue == optionData.key){
	    				comboValTemp += '<option role="option" value="' +
			            optionData.value + '">' +
						optionData.name + '</option>';
	        		}
				});
			});
			var orgSelect = jQuery('#' + rowId + '_'+arrCol[multiCnt+1])[0];
			var orgValue = getColValue(arrCol[multiCnt+1],rowId, gridName);
			$("select#" + rowId +"_"+ arrCol[multiCnt+1]).html(comboValTemp);
			jQuery(orgSelect).val(orgValue);
		}
	}else if(multiCnt == 2){
		
	}
}