var gridName1 = "htmlTable";
var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;
var datarow = {eeno:"",eenm:"",ops_nm:"",stap_ymd:"",sta_hhmm:"",stap_cd:"",stap_nm:"",stap_adr:"",arvp_cd:"",arvp_nm:"",arvp_adr:"",rem_sbc:"",flht_no:"",svca_amt:"",doc_no:"",seq:""};

function fnSetDocumentReady(){
	$("#keyFromDate").datepicker({ dateFormat: "yy-mm-dd" });
	$("#keyToDate").datepicker({ dateFormat: "yy-mm-dd" });
	
	getCommonCode("key_place_knd:XPS03:A;car_type_cd:XPS01:S;", "N", "getFirmCombo('firm_cd:A')");  
}

function getFirmCombo(codeStr){
	var keyData = {
		codknd : codeStr
	};

	doCommonAjax("doSearchPsToCombo.do", keyData, "comboResult(jsonData,'"+codeStr+"');");
}

function comboResult(jsonData,codknd){
	$.each(eval(jsonData.sendResult),function(index,optionData){
		$("#"+index).loadSelect(eval(optionData));
	});
	init();
}
 
function init(){
	
	cn = ['일시', '출발시간', '장소', '상세정보', '장소', '상세정보', '이름', '항공번호', 'Amount', 'Doc No', 'Seq', '', '']; 
	cm = [
      		{name:"stap_ymd", index:"stap_ymd", sortable:false, formatter:"string",	width:65, readonly:"true", align:"center", editable:false,	frozen : false},
			{name:"sta_hhmm", index:"sta_hhmm", sortable:false,	formatter:"string",	width:60, align:"center", editable:false, frozen : false},
			{name:'stap_nm',index:'stap_nm',sortable:false,width:90,align:'center',editable:false},
			{name:'stap_adr',index:'stap_adr', formatter: "string",width:138,align:'left',editable:false,sortable:false},
			{name:'arvp_nm',index:'arvp_nm',sortable:false,width:90,align:'center',editable:false},
			{name:'arvp_adr',index:'arvp_adr', formatter: "string",width:137,align:'left',editable:false,sortable:false},   
			{name:'rem_sbc',index:'rem_sbc', formatter: "string",width:155,align:'left',editable:false,sortable:false},
			{name:'flht_no',index:'flht_no', formatter: "string",width:115,align:'left',editable:false,sortable:false},
			{name:"svca_amt",	index:"svca_amt"	, sortable:false,formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:"",
			    suffix:" R$"
			  },		width:60,	align:"right",	editable:false,	frozen : false, hidden:true},
			{name:'doc_no',index:'doc_no', formatter: "string",width:60,align:'left',editable:false,sortable:false,hidden:true},
			{name:'seq',index:'seq', formatter: "string",width:60,align:'left',editable:false,sortable:false,hidden:true},
			{name:'stap_cd',index:'stap_cd',sortable:false,width:90,align:'center',editable:false, hidden:true},
			{name:'arvp_cd',index:'arvp_cd',sortable:false,width:90,align:'center',editable:false, hidden:true}
	        ];    
	
	var params = {
	};
	gridParam = {
		viewEdit : [{
			gridName     : "htmlTable",
			url          : "", 
			colNames     : cn,
			colModel     : cm,
			rowNum		 : 10,
			width        : "940",
			height       : "100%",
			sortname     : "stap_ymd",
			sortorder    : "desc",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager",
			paramJson    : params
		}]
	};
	
	commonJqGridInit(gridParam);
	
	addGridRow();
	initAfterMenus();
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
          {startColumnName: 'stap_nm', numberOfColumns: 2, titleText: '출발'},
          {startColumnName: 'arvp_nm', numberOfColumns: 2, titleText: '도착'}
		]
	});
	
	jQuery("#htmlTable").jqGrid("setFrozenColumns");
	jQuery("#htmlTable").jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
}

function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
	    case "search" :
		    doSearch();
			break;
	    case "copy" :
		    doCopy();
		    break;
	}
}

function doSearch(msgFlag){
	params = {
		from_ymd    : selectNum($("#keyFromDate").val()),
		to_ymd      : selectNum($("#keyToDate").val()),
		eeno        : $("#key_eeno").val(),
		dept_cd     : $("#key_ops_cd").val(),
		firm_cd     : $("#firm_cd").val(),
		stap_cd     : $("#key_place_knd").val(),
		car_type_cd : $("#car_type_cd").val()
	}; 
	
	doCommonSearch("doSearchListPsToPickupSchedule.do", util.jsonToString(params), "addGridRow();initAfterMenus();", "htmlTable", msgFlag);
} 

function cearInsa(){
	if($("#key_eeno").val() == ""){
		$("#key_eeno").val("");
		$("#key_eeno_nm").val("");
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
/**
 * callback
 */
function insaCallBack(result){
	$("#key_eeno").val(result.xusr_empno);
	$("#key_eeno_nm").val(result.xusr_name);
}

function deptFind(){
	var param = "?dcd=key_ops_cd&dcdNm=key_ops_nm&csrfToken="+$("#csrfToken").val();
	newPopWin(ctxPath+"/hncis/popup/deptPopup.gas"+param, "440", "510", "pop_dept");
}

function deptSearch(){
	if($("#key_ops_cd").val() != ""){
		if($("#key_ops_cd").val().length > 8) return;
		if($("#key_ops_cd").val().length == 8){
			
			var keyData = { xorg_orga_c : $("#key_ops_cd").val() };
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToDeptInfo.do", paramData, "deptCallBack(jsonData.sendResult)");
		}
	}
}

function deptCallBack(result){
	$("#key_ops_nm").val(result.xorg_orga_e);
}

function doCopy(){
	var selectRow = jQuery("#htmlTable").jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
	}
	
	var data, sendRows = [];
	
	for(var i=0; i<selectRow.length; i++){
		if(selectRow[i]){
			if(getColValue("seq", selectRow[i]) == ""){
				alertUI((i+1) + " Line : There is no data.");
				return;
			}
			data = {
				stap_ymd : getColValue("stap_ymd", selectRow[i]),
				sta_hhmm : getColValue("sta_hhmm", selectRow[i]),
				stap_adr : getColValue("stap_adr", selectRow[i]),
				arvp_adr : getColValue("arvp_adr", selectRow[i]),
				rem_sbc  : getColValue("rem_sbc", selectRow[i]),
				flht_no  : getColValue("flht_no", selectRow[i]),
//				svca_amt : getColValue("svca_amt", selectRow[i]),
				doc_no   : getColValue("doc_no", selectRow[i]),
				stap_cd  : getColValue("stap_cd", selectRow[i]),
				arvp_cd  : getColValue("arvp_cd", selectRow[i]),
				add_flag : "A"
			};
			
			sendRows.push(data);
		}
	}
	
	opener.fnCopyData(sendRows);
}