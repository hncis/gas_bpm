var gridParam;
var gridName1 = "htmlTable";
var datarow = {company:"", carrier:"", brand:"", model:"", serial:"", quantity:"", network:"", day1:"", hour1:"",day2:"", hour2:"",
		seq: "", eeno: "", apply_date: "", if_id: "", pgs_st_nm: "", pgs_st_cd: "", purpose:"", reason:"", remark:"", doc_no:"", snb_rson_sbc:""};

function initComboIt(){
	getCommonCode('network:X3003;', 'Y', 'initSub();');
}

function initSub(){
	var cn = ["Company", "Name", "Brand", "Model", "Serial #", "Quantity", "Network", "day", "hour", "day", "hour",
	          "seq", "eeno", "apply_date", "if_id", "pgs_st_nm", "pgs_st_cd", "purpose", "reason", "remark", "doc_no", ""];
	var cm =
		[
		 {name:"company",	index:"company"		, sortable:false,		formatter:"string",		width:86,	align:"left",	editable:true},
		 {name:"carrier",	index:"carrier"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		 {name:"brand",		index:"brand"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		 {name:"model",		index:"model"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		 {name:"serial",	index:"serial"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		 {name:"quantity",	index:"quantity"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true},
		 {name:"network",	index:"network"		, sortable:false,		formatter: "select",	width:85,	align:'center',	editable:true,	edittype:'select', 
			editoptions:{value:getComboValue('network'), dataInit: function(elem) {$(elem).width(80);}
		        },
		        editrules:{required:true}
		 },
		 {name:"day1",		index:"day1"		, sortable:false,		formatter:"string",		width:89,	align:"center",	editable:true,	frozen : false,
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
		 {name:"hour1",		index:"hour1"		, sortable:false,	formatter:"string",		width:88,	align:"center",	editable:true,	frozen : false,
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
		 {name:"day2",		index:"day2"		, sortable:false,		formatter:"string",		width:89,	align:"center",	editable:true,	frozen : false,
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
		 {name:"hour2",		index:"hour2"		, sortable:false,	formatter:"string",		width:88,	align:"center",	editable:true,	frozen : false,
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
		 {name:"apply_date",index:"apply_date"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"if_id",		index:"if_id"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"purpose",	index:"purpose"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"reason",	index:"reason"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"remark",	index:"remark"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
		 {name:"doc_no",	index:"doc_no"		, sortable:false,		formatter:"string",		width:85,	align:"left",	editable:true, hidden:true},
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
		type		 : "3"
	};
	
	//set grid parameter
	gridParam = { 
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityRequestDevices.do",
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

function doCudAction(gubun) {
	var url = "";
	var callBack = "actionCallBack(jsonData.sendResult);";
	var params = [];
	var ids = jQuery("#"+gridName1).getDataIDs();

	if(gubun == "save"){
		url = "doInsertSecurityRequestDevices.do";
	}else if(gubun == "delete"){
		if(sess_mstu != "M" && sess_auth != 5 && parent.$("#eeno").val() != sess_empno && $("#pgs_st_cd").val() !== "0"){
			alertUI("삭제할 수 없습니다.");
			return;
		}
		url = "doDeleteSecurityRequestDevices.do";
	}
	
	var d= new Date();
	var m = d.getMonth() +1;
	var day = d.getDate();
	if(m < 10){ m = "0" + m; }
	if(day < 10){ day = "0" + day; }
	var app_dt = d.getFullYear() + "/" + m + "/" + day;
	if(parent.$("#apply_date").val() != ""){
		app_dt = dateConversionKr(parent.$("#apply_date").val());
	}
	
	if(gubun == "delete"){
		list = {
			eeno     		: parent.$("#eeno").val(),
			apply_date		: app_dt,
			doc_no			: parent.$("#hid_doc_no").val(),
			type			: "3"
		};
		params.push(list);
	}else{
		for(var i = 0; i < ids.length; i++){
			rowId = ids[i];
			if(rowId){
				if(getColValue("company", rowId) != "" && getColValue("carrier", rowId) != ""){
					if(getColValue("brand", rowId) == ""){
						alertUI(rowId + " line : 상표를 입력하세요.");
						return;
					}
					if(getColValue("day1", rowId) == ""){
						alertUI(rowId + " line : 시작일을 입력하세요.");
						return false;
					}
					if(getColValue("hour1", rowId) == ""){
						alertUI(rowId + " line : 시작시간을 입력하세요.");
						return false;
					}
					if(getColValue("day2", rowId) == ""){
						alertUI(rowId + " line : 종료일을 입력하세요.");
						return false;
					}
					if(getColValue("hour2", rowId) == ""){
						alertUI(rowId + " line : 종료시간을 입력하세요.");
						return false;
					}
					
					var st_dt = selectNum(getColValue("day1", rowId)) + selectNum(getColValue("hour1", rowId));
					var end_dt = selectNum(getColValue("day2", rowId)) + selectNum(getColValue("hour2", rowId));
					
					if(st_dt > end_dt){
						alertUI(rowId + " line : 시작일은 종료일보다 클 수 없습니다.");
						return false;
					}
					
					list = {
							seq				: $.trim(getColValue("seq", rowId)),
							eeno     		: parent.$("#eeno").val(),
							doc_no			: parent.$("#hid_doc_no").val(),
							apply_date		: app_dt,
							purpose 		: parent.$("#purpose").val(),
							type			: "3",
							company   		: getColValue("company", rowId),
							carrier   		: getColValue("carrier", rowId),
							brand   		: getColValue("brand", rowId),
							model			: getColValue("model", rowId),
							serial			: getColValue("serial", rowId),
							quantity   		: getColValue("quantity", rowId),
							network			: getColValue("network", rowId),
							day1			: getColValue("day1", rowId),
							hour1			: getColValue("hour1", rowId),
							day2			: getColValue("day2", rowId),
							hour2			: getColValue("hour2", rowId),
							pgs_st_cd   	: "0",
							reason			: parent.$("#reason").val(),
							remark			: overLineHtml(changeToUni(parent.$("#remark").val())),
							ipe_eeno    	: sess_empno,
							updr_eeno   	: sess_empno
					};
					params.push(list);
				}
			}
		}
	}
	
	if(params.length == 0){
		alertUI("Please enter Company, Carrier, Brand!");
		return;
	}
	
	confirmUI("Do you want to "+gubun+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax(url, paramData, callBack);
			}
		});
	});
}


function actionCallBack(result){
	setBottomMsg(result.message, true);
	parent.$("#hid_doc_no").val(result.code1);
	parent.$("#doc_no").val(result.code1);
	if(null != result.code2 && result.code2 != ""){
		parent.$("#eeno").val(result.code2);
		parent.$("#eeno_temp").val(result.code2);
	}
	if(null != result.code && result.code != ""){
		parent.$("#apply_date").val(result.code.substring(8,10)+"/"+result.code.substring(5,7)+"/"+result.code.substring(0,4));
	}
	
	parent.SubmitClear();
	
	doSearch("N");
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
		type		 : "3"
	};
	doCommonSearch("doSearchSecurityRequestDevices.do", util.jsonToString(params), "addGridRow(5);loadDataSet();", gridName1, msgFlag);
}

function doApprove(gubun){
	var url = "";
	var callBack = "";
	var pgs_st_cd = "";
	if(gubun == "request"){
		url = "doApproveSecurityRequest.do";
		callBack = "approveCallBack('"+gubun+"', jsonData.sendResult);";
		pgs_st_cd = "A";
	}else if(gubun == "requestCancel"){
		url = "doApproveCancelSecurityRequest.do";
		callBack = "approveCallBack('"+gubun+"', jsonData.sendResult);";
	}
	
	var bsicInfo;
	if(gubun == "request"){
		bsicInfo = {
			key_mode      : gubun,
			key_eeno      : parent.$("#eeno").val(),
			key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
			key_pgs_st_cd : pgs_st_cd,
			doc_no		  : parent.$("#hid_doc_no").val(),
			type		  : "3",
			updr_eeno     : sess_empno
		};
	}else{
		bsicInfo = {
			if_id     : getColValue("if_id", 1),
			updr_eeno : sess_empno
		};
	}
	confirmUI("Do you want to "+gubun+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax(url, paramData, callBack);
			}
		});
	});
}
 
function approveCallBack(gubun, result){
	setBottomMsg(result.message, true);
	parent.SubmitClear();
	doSearch("N");
}

function doConfirm(){
	var bsicInfo = {
		key_mode      : "confirm",
		key_eeno      : parent.$("#eeno").val(),
		key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
		key_pgs_st_cd : "3",
		type		  : "3",
		doc_no		  : parent.$("#hid_doc_no").val(),
		updr_eeno     : sess_empno
	};
	
	confirmUI("확정 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmSecurityRequestVehicle.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doConfirmCancel(){
	if($("#rq_imtr_sbc").val() == ""){
		alertUI("Please enter the reason for confirm cancel in Note.");
		return;
	}
	
	var bsicInfo = {
		key_mode      : "confirmCancel",
		key_eeno      : parent.$("#eeno").val(),
		key_req_date  : dateConversionKr(parent.$("#apply_date").val()),
		key_pgs_st_cd : "0",
		type		  : "3",
		doc_no		  : parent.$("#hid_doc_no").val(),
		snb_rson_sbc  : parent.$("#snb_rson_sbc").val(),
		updr_eeno     : sess_empno
	};
	
	confirmUI("신청 취소사유를 입력하세요.\n신청 취소하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doConfirmCancelSecurityRequestVehicle.do", paramData, "confirmCallBack(jsonData.sendResult);");
			}
		});
	});
}

function confirmCallBack(result){
	setBottomMsg(result.message, true);
	parent.SubmitClear();
	doSearch("N");
}

function resizeIframe(){
	var doc = document.getElementById("contents");
	parent.document.getElementById("ifra").height = doc.offsetHeight + "px";
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xve_file.gas", width = "460", height = "453";
	
	if(parent.$("#hid_doc_no").val() == ""){
		$("#file_doc_no").val(parent.$("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val(parent.$("#hid_doc_no").val());
	}
	$("#file_eeno").val("00000000");
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){ 
		if( $("#pgs_st_cd").val() == ""){
			$("#hid_use_yn").val("Y"); 
		}else if(sess_empno == $("#eeno").val()){
			if($("#pgs_st_cd").val() == "0"){
				$("#hid_use_yn").val("Y"); 
			}else{
				$("#hid_use_yn").val("N");   
			}
		}else{
			$("#hid_use_yn").val("N");  
		}
	}else{
		$("#hid_use_yn").val("Y");
	}

	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

function fnCopy(gubun) {
	if(parent.$("pgs_st_cd").val() == ""){
		alertUI("can't copy");
		return;
	}
	var url = "doInsertSecurityRequestDevicesCopy.do";
	var callBack = "actionCallBack(jsonData.sendResult);";
	var params = [];
	var ids = jQuery("#"+gridName1).getDataIDs();
	
	var d= new Date();
	var m = d.getMonth() +1;
	var day = d.getDate();
	if(m < 10){ m = "0" + m; }
	if(day < 10){ day = "0" + day; }
	var app_dt = d.getFullYear() + "/" + m + "/" + day;
	
	for(var i = 0; i < ids.length; i++){
		rowId = ids[i];
		if(rowId){
			if(getColValue("company", rowId) != "" && getColValue("carrier", rowId) != "" && getColValue("brand", rowId) != ""){
				list = {
						seq				: "",
						eeno     		: parent.$("#eeno").val(),
						doc_no			: "",
						apply_date		: app_dt,
						purpose 		: parent.$("#purpose").val(),
						type			: "3",
						company   		: getColValue("company", rowId),
						carrier   		: getColValue("carrier", rowId),
						brand   		: getColValue("brand", rowId),
						model			: getColValue("model", rowId),
						serial			: getColValue("serial", rowId),
						quantity   		: getColValue("quantity", rowId),
						network			: getColValue("network", rowId),
						day1			: getColValue("day1", rowId),
						hour1			: getColValue("hour1", rowId),
						day2			: getColValue("day2", rowId),
						hour2			: getColValue("hour2", rowId),
						pgs_st_cd   	: "0",
						reason			: parent.$("#reason").val(),
						remark			: overLineHtml(changeToUni(parent.$("#remark").val())),
						ipe_eeno    	: sess_empno,
						updr_eeno   	: sess_empno
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("복사 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax(url, paramData, callBack);
			}
		});
	});
}