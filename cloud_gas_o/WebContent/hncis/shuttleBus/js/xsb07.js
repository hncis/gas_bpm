var gridName1 = "htmlTable1";
var gridName2 = "htmlTable2";

var datarow1 = {fret_name:"",fret_sort:"",fret_use_yn:"", fret_type:"", fret_seq:"", fret_cnt:""};
var datarow2 = {route_name:"",route_cidade:"",route_time:"",route_sort:"",route_use_yn:"",seq:"", exist_type:""};

var gridParam_fretado, gridParam_existente;

function fnSetDocumentReady(){
	getCommonCode('fret_use_yn:X3003;route_use_yn:X3003;route_cidade:X3007', 'Y', 'init();');
}

function init(){
	cn_fretado = [ "Name", "Sort", "Use Y/N", "type", "seq", ""],
	cm_fretado = [
	    {name:"fret_name",	 	index:"fret_name",   	formatter:"string", width:210, 	align:"left",	editable:true, sortable:false},
	    {name:"fret_sort",	 	index:"fret_sort",		formatter:"string", width:80, 	align:"center",	editable:true, sortable:false},
	    {name:"fret_use_yn",	index:"fret_use_yn"		, sortable:false,		formatter: "select",	width:80,	align:'center',	editable:true,	edittype:'select', 
			editoptions:{value:getComboValue('fret_use_yn'), dataInit: function(elem) {$(elem).width(80);}
		        },
		        editrules:{required:true}
		},
	    {name:"fret_type",		index:"fret_type", 		formatter:"string", width:0, 	align:"left",	editable:true, sortable:false, hidden:true},
	    {name:"fret_seq",		index:"fret_seq", 		formatter:"string", width:0, 	align:"left",	editable:true, sortable:false, hidden:true},
	    {name:"fret_cnt",		index:"fret_cnt", 		formatter:"string", width:0, 	align:"left",	editable:true, sortable:false, hidden:true}
	];
	
	cn_existente = [ "Name", "CIDADE", "Time", "Sort", "Use Y/N", "seq", "type"],
	cm_existente = [
		{name:"route_name",		index:"route_name",		formatter:"string", width:200, 	align:"left",	editable:true, sortable:false},
		{name:"route_cidade",	index:"route_cidade",	sortable:false,		formatter: "select",	width:120,	align:'center',	editable:true,	edittype:'select', 
			editoptions:{value:getComboValue('route_cidade'), dataInit: function(elem) {$(elem).width(120);}
			},
			editrules:{required:true}
		},
		{name:"route_time",		index:"route_time",		formatter:"string", width:85, 	align:"center",	editable:true, sortable:false},
		{name:"route_sort",		index:"route_sort",		formatter:"string", width:60, 	align:"center",	editable:true, sortable:false},
		{name:"route_use_yn",	index:"route_use_yn"		, sortable:false,		formatter: "select",	width:60,	align:'center',	editable:true,	edittype:'select', 
			editoptions:{value:getComboValue('route_use_yn'), dataInit: function(elem) {$(elem).width(60);}
		        },
		        editrules:{required:true}
		},
		{name:"seq",			index:"seq",			formatter:"string", width:350, 	align:"left",	editable:true, sortable:false, hidden:true},
		{name:"route_time",		index:"route_time", 	formatter:"string", width:350, 	align:"left",	editable:true, sortable:false, hidden:true}
	];
	
	var params = {
		f_levl	: '0',
	};
	
	gridParam_fretado = {
		viewEdit : [{
			gridName      : gridName1,
			url           : "doSearchTransporteFretadoList.do",
			colNames      : cn_fretado,
			colModel      : cm_fretado,
			width         : "430",
			height        : 438,
			rownumbers    : false,
			multiselect   : true,
			cellEdit      : true,
			fnMerge       : false,
			paramJson     : params,
			rowNum        : 100,
			selectCellFc  : "setExistGridValue(rowid, iCol, cellcontent);",
			completeFc	  : "addGridRow(19, 'htmlTable1', 'datarow1');loadCallBack();"
		}]
	};
	
	gridParam_existente = {
		viewEdit : [{
			gridName      : gridName2,
			url           : "/doSearchToEmpty.do",
			colNames      : cn_existente,
			colModel      : cm_existente,
			width         : "575",
			height        : 438,
			rownumbers    : false,
			multiselect   : true,
			cellEdit      : true,
			fnMerge       : false,
			rowNum        : 100,
			completeFc	  : "addGridRow(19, 'htmlTable2', 'datarow2')"
		}]
	};

	commonJqGridInit(gridParam_fretado);
	commonJqGridInit(gridParam_existente);
}

function loadCallBack(){
//	$("#hid_type").val(getColValue("fret_type", 1, gridName1));
//	jQuery("#"+gridName1).setRowData (1,false,{background:'#B2EBF4'});
//	previSRow1 = 1;
//	doExistenteSearch();
}

function setExistGridValue(rowid, iCol, cellcontent){
	if(iCol != 0){
		$("#hid_type").val(getColValue("fret_type", rowid, gridName1));
		doExistenteSearch();
	}
}

function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
	    case "fretado_save" :
		    doFretadoSave();
			break;
	    case "exist_save" :
		    doExistSave();
			break;
	    case "fretado_delete" :
		    doFretadoDelete();
			break;
	    case "exist_delete" :
		    doExistDelete();
			break;
	    case "interval" :
	    	doIntervalTime();
	    	break;
	}
}

function doSearchFretado(){
	var params = {
		f_levl	: "0"
	};
	
	doCommonSearch("doSearchTransporteFretadoList.do", util.jsonToString(params), "doFretadoSearchCallBack();", gridName1);
}

function doFretadoSearchCallBack(){
	addGridRow(19, 'htmlTable1', 'datarow1');
}

function doExistenteSearch(rowid, iRow, iCol, e){
	if($("#hid_type").val() == ""){
		return;
	}
	
	var params = {
		type : $("#hid_type").val(),
		f_levl	  : "1"
	};
	
	doCommonSearch("doSearchPonteExistenteList.do", util.jsonToString(params), "doExistenteSearchCallBack();", gridName2);
}

function doExistenteSearchCallBack(){
	addGridRow(19, 'htmlTable2', 'datarow2');
}

function fnGridRowAdd(targetGrid){
	var gridRowId = $("#"+targetGrid).getDataIDs().length;
	switch(targetGrid){
	case "htmlTable1" :
		jQuery("#"+targetGrid).jqGrid("addRowData", gridRowId+1, datarow1);
		break;
	case "htmlTable2" :
		jQuery("#"+targetGrid).jqGrid("addRowData", gridRowId+1, datarow2);
		break;
	}
}

function doFretadoSave(){
	var params = [];
	
	var selectRow = $("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	for(var i=0; i<selectRow.length; i++){
		if(selectRow[i]){
			if(getColValue("fret_name", selectRow[i], gridName1) == ""){
				alertUI(selectRow[i] + " Line : Please input Transporte Fretado Name");
				return;
			}
			if(getColValue("fret_sort", selectRow[i], gridName1) == ""){
				alertUI(selectRow[i] + " Line : Please input Transporte Fretado Sort");
				return;
			}
			
			data = {
				fret_name	: getColValue("fret_name", selectRow[i], gridName1),
				fret_sort	: getColValue("fret_sort", selectRow[i], gridName1),
				fret_use_yn	: getColValue("fret_use_yn", selectRow[i], gridName1),
				fret_type	: getColValue("fret_type", selectRow[i], gridName1),
				fret_seq	: getColValue("fret_seq", selectRow[i], gridName1),
				f_levl		: "0",
				ipe_eeno	: sess_empno,
				updr_eeno	: sess_empno
			};
			
			params.push(data);
		}
	}
	
	if(params.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doSaveTransporteFretadoList.do", paramData, "doFretadoCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doExistSave(){
	var data;
	var params = [];
	
	var selectRow = $("#"+gridName2).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	if($("#hid_type").val() == ""){
		alertUI("Please select 'Transporte Fretado'");
		return;
	}
	
	for(var i=0; i<selectRow.length; i++){
		if(getColValue("route_name", selectRow[i], gridName1) == ""){
			alertUI(selectRow[i] + " Line : Please input Ponto Existente Name");
			return;
		}
		if(getColValue("route_time", selectRow[i], gridName1) == ""){
			alertUI(selectRow[i] + " Line : Please input Ponto Existente Time");
			return;
		}
		if(getColValue("route_sort", selectRow[i], gridName1) == ""){
			alertUI(selectRow[i] + " Line : Please input Ponto Existente Sort");
			return;
		}
		
		if(selectRow[i]){
			data = {
				route_name		: changeToUni(getColValue("route_name", selectRow[i], gridName2)),
				route_cidade	: getColValue("route_cidade", selectRow[i], gridName2),
				route_time		: getColValue("route_time", selectRow[i], gridName2),
				route_sort		: getColValue("route_sort", selectRow[i], gridName2),
				route_use_yn	: getColValue("route_use_yn", selectRow[i], gridName2),
				seq				: getColValue("seq", selectRow[i], gridName2),
				type			: $("#hid_type").val(),
				f_levl			: "1",
				ipe_eeno		: sess_empno,
				updr_eeno		: sess_empno
			};
			
			params.push(data);
		}
	}
	
	if(params.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
				doCommonAjax("doSavePontoExistenteList.do", paramData, "doExistenteCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doFretadoDelete(){
	var dParams = [];
	var dData;
	
	var selectRow = $("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	for(var i=0; i<selectRow.length; i++){
		if(selectRow[i]){
			if(getColValue("fret_cnt", selectRow[i], gridName1) != "0"){
				alertUI(selectRow[i] + " Line : Has Ponto Existente Data.");
				return;
			}
			
			dData = {
				fret_seq  : getColValue("fret_seq", selectRow[i], gridName1),
				fret_type : getColValue("fret_type", selectRow[i], gridName1),
				f_levl	  : "0"
			};
			
			dParams.push(dData);
		}
	}
	
	if(dParams.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(dParams)
					};
					doCommonAjax("doDeleteTransporteFretadoList.do", paramData, "doFretadoCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doExistDelete(tgName){
	var dParams = [];
	var dData;
	
	var selectRow = $("#"+gridName2).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
//	var tmpPrnCd = $("#"+gridName1).jqGrid("getGridParam", "selrow");
	
	for(var i=0; i<selectRow.length; i++){
		if(selectRow[i]){
			dData = {
				seq  	: getColValue("seq", selectRow[i], gridName2),
				type 	: $("#hid_type").val(),
				f_levl	: "1"
			};
			
			dParams.push(dData);
		}
	}
	
	if(dParams.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(dParams)
					};
					doCommonAjax("doDeletePontoExistenteList.do", paramData, "doExistenteCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doFretadoCallBack(result){
	setBottomMsg(result.message, true);
	doSearchFretado();
}

function doExistenteCallBack(result){
	setBottomMsg(result.message, true);
	doExistenteSearch("N");
}

function doIntervalTime(){
	var time = selectNum($("#key_time").val());
	if(time.length != 4){
		return;
	}
	var hour = time.substring(0,2);
	var min = time.substring(2,4);
	var intVal = $("#key_interval").val();
	
	var ids = jQuery("#"+gridName2).getDataIDs();
	for(var i = 0; i < ids.length; i++){
		rowId = ids[i];
		if(getColValue("route_name", rowId, gridName2) != "" && getColValue("route_cidade", rowId, gridName2) != ""){
			$("#"+gridName2).jqGrid("setCell", rowId, "route_time", hour + ":" + min);
			min = parseInt(min) + parseInt(intVal);
			if(min >= 60){
				hour = parseInt(hour) + 1;
				min = parseInt(min) - 60;
			}
			if(min < 10){min = "0" + parseInt(min);}
			if(hour > 24){ hour = "1"; }
			if(hour < 10){hour = "0" + parseInt(hour);}
		}
	}
}

var popWin;
function shuttleBusPopup(){
	var url = "", width = "1093", height = "600";
	url = ctxPath+"/hncis/shuttleBus/xsb08.gas";
	win = newPopWin("about:blank", width, height, "win_shuttle");
		
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_shuttle"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}