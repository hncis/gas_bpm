var lastsel;
var fnMerge;

function fnSetDocumentReady(){
	initMenus();
	sess_auth = $("#work_auth").val();
	
	$(".inputOnlyNumber").numeric();
	
	setComboInfo();
}

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var paramValue  = "key_pgs_st_cd:XTM02:Y;";
	getCommonCode(paramValue, "N", "init();", url);
}

var gridParam;
var gridName = "htmlTable";
var datarow = {doc_no:"", ptt_ymd:"", pgs_st_cd:"", pgs_st_nm:"", eeno:"", eenm:"", car_no:"", vehl_nm:"",
			tic_no:"", tic_aet:"", tic_desc:"", tic_pint:"", tic_amt:"", tic_ymd:"", tic_time:"", tic_place:"", tic_city:"", drv_lmt_ymd:""};
function init(){
	var cn = ["", "", "", "Status", "User ID", "Name", "Number Plate", "Vehicle", "No.", "AET", "Description", "Point", "Amout", "Date", "Time", "Place", "City", "Deadlie To<br>Indicate The<br>Driver"];
	var cm = [{name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"ptt_ymd", index:"ptt_ymd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"pgs_st_cd", index:"pgs_st_cd", sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden:true},
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"eeno", index:"eeno", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false},
	          {name:"eenm", index:"eenm", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"car_no", index:"car_no", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"vehl_nm", index:"vehl_nm", sortable:false, formatter:"string", width:150, align:"left", editable:false, frozen:false},
	          {name:"tic_no", index:"tic_no", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
	          {name:"tic_aet", index:"tic_aet", sortable:false, formatter:"string", width:100, align:"left", editable:false, frozen:false},
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
			  {name:"tic_place", index:"tic_place", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
			  {name:"tic_city", index:"tic_city", sortable:false, formatter:"string", width:120, align:"left", editable:false, frozen:false},
	          {name:"drv_lmt_ymd", index:"drv_lmt_ymd", sortable:false, formatter:"string", width:80, align:"left", editable:false, frozen:false}
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
			cellEdit     : false,
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
		groupHeaders:[{startColumnName: 'tic_no', numberOfColumns: 9, titleText: 'Traffic Ticket Information'}]
	});
	
	$("#key_pgs_st_cd option:eq(1)").remove();
	$("#key_pgs_st_cd option:eq(1)").remove();
	$("#key_pgs_st_cd option:eq(1)").remove();
	$("#key_pgs_st_cd option:eq(1)").remove();
	$("#key_pgs_st_cd option:eq(1)").remove();
	$("#key_pgs_st_cd").val("5");
}

function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
		   	doSearch();
			break;
	   case "hrreportDone" :
	   case "hrreportDoneCancel" :
		    doAction(btnFlag);
		   	break;
	}
}

function fnParamJson(){
	var params = {
		eeno : $("#key_eeno").val(),
		eenm : $("#key_eenm").val(),
		tic_no : $("#key_tic_no").val(),
		pgs_st_cd : $("#key_pgs_st_cd").val()
	};
	return params;
}

function doSearch(){
	doCommonSearch("doSearchByXtm04.do", util.jsonToString(fnParamJson()), "loadCallBack();", gridName, "N");
}

function loadCallBack(){
	addGridRow();
	//setMerge();
}

function setMerge(){
    groupTable($("#"+gridName+" tr:has(td)"), 1, 7);
    $("#"+gridName+" .deleted").remove();
}

function doAction(btnFlag){
	var params = [];
	var isChk = "N";
	var btnNm = "";
	if(btnFlag == "hrreportDone"){
		btnNm = "HR Report Done";
	}else if(btnFlag == "hrreportDoneCancel"){
		btnNm = "HR Report Done Cancel";
	}
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(btnFlag == "hrreportDone"){
					if(pgsStCd != '5'){
						isChk = "Y";
					}
				}else if(btnFlag == "hrreportDoneCancel"){
					if(pgsStCd != '6'){
						isChk = "Y";
					}
				}
				if(isChk == "Y"){
					isChk = "N";
					alertUI("You can't "+btnNm+" in this status");
					return;
				}
				list = {
					doc_no : getColValue("doc_no", rowId, gridName)
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("Do you want to "+btnNm+"?");
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
	setBottomMsg(result.message, true);
	doSearch();
}

function setInsaInfo(){
	if($("#key_eeno").val() != ""){
		if($("#key_eeno").val().length > 8) return;
		if($("#key_eeno").val().length == 8){
			var keyData = { xusr_empno : $("#key_eeno").val() };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
		}
	}
}

function insaCallBack(result){
	setBottomMsg(result.message, false);
	$("#key_eeno").val(result.xusr_empno);
	$("#key_eenm").val(result.xusr_name);
}

function clearInsa(){
	if($("#key_eeno").val() == ""){
		$("#key_eeno").val("");
		$("#key_eenm").val("");
	}
}