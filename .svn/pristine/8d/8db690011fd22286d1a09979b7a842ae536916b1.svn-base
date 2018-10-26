function fnSetDocumentReady(){
	init();
}

var comboVal;
var lastsel;
var fnMerge;
var gridRowCnt = 10;
var gridParam1, gridParam2;
var gridName1 = "htmlTable1";
var gridName2 = "htmlTable2";
var cn1       = ["사번","이름", "직급", "부서명", "직급코드", "부서코드"];
var cn2       = ["사번","이름", "직급", "부서명", "직급코드", "부서코드"];
var datarow1  = {eeNo:"",hName:"",stepName:"",dcdName:"",stepCode:"",dcd:""};
var datarow2  = {aEeNo:"",aHname:"",aStepName:"",aDcdName:"",aStepCode:"",aDcd:""};
var cm1 =
[
	{name:"eeNo",		index:"eeNo"		, sortable:false, formatter:"string", width:60, align:"left", editable:false, frozen:false},
	{name:"hName",		index:"hName"		, sortable:false, formatter:"string", width:70, align:"left", editable:false, frozen:false},
	{name:"stepName",	index:"stepName"	, sortable:false, formatter:"string", width:70, align:"left", editable:false, frozen:false},
	{name:"dcdName",	index:"dcdName"		, sortable:false, formatter:"string", width:124, align:"left", editable:false, frozen:false},
	{name:"stepCode",	index:"stepCode"	, sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden:true},
	{name:"dcd",		index:"dcd"			, sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden:true}
];                                                                                                                         
var cm2 =                                                                                                                  
[                                                                                                                          
	{name:"aEeNo",		index:"aEeNo"		, sortable:false, formatter:"string", width:60, align:"left", editable:false, frozen:false},
	{name:"aHname",		index:"aHname"		, sortable:false, formatter:"string", width:70, align:"left", editable:false, frozen:false},
	{name:"aStepName",	index:"aStepName"	, sortable:false, formatter:"string", width:70, align:"left", editable:false, frozen:false},
	{name:"aDcdName",	index:"aDcdName"	, sortable:false, formatter:"string", width:124, align:"left", editable:false, frozen:false},
	{name:"aStepCode",	index:"aStepCode"	, sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden:true},
	{name:"aDcd",		index:"aDcd"		, sortable:false, formatter:"string", width:0, align:"left", editable:false, frozen:false, hidden:true}
];
function init(){
	gridParam1 = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "/doSearchToEmpty.do",
			colNames     : cn1,
			colModel     : cm1,
			height       : 230,
			width		 : 395,
			rowNum       : 10,
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager1",
			completeFc   : "fnGridInitCallBack('"+gridName1+"');"
		}]
	};
	
	var params, url2;
	if($("#hidEeno").val() != ""){
		url2 = "../../doSearchUserCode.do";
		params = {
			eeNo : $("#hidEeno").val()
		};
	}else{
		url2 = "/doSearchToEmpty.do";
	}
	
	gridParam2 = {
		viewEdit : [{
			gridName     : gridName2,
			url          : url2,
			colNames     : cn2,
			colModel     : cm2,
			height       : 230,
			width		 : 395,
			rowNum       : 10,
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : false,
			fnMerge      : false,
			pager		 : "htmlPager2",
			completeFc   : "fnGridInitCallBack('"+gridName2+"');",
			paramJson    : params
		}]
	};
	
	commonJqGridInit(gridParam1);
}

function fnGridInitCallBack(initGrid){
	switch(initGrid){
		case "htmlTable1" :
			addGridRow(gridRowCnt, initGrid, 'datarow1');
			commonJqGridInit(gridParam2);
			break;
		case "htmlTable2" :
			if($("#hidEeno").val() != ""){
				var gridRowId = $("#"+gridName2).getDataIDs();
				for(var i = 0 ; i < gridRowId.length ; i++){
					$("#"+gridName2).jqGrid("setSelection", i+1, true);
				}
			}
			break;
	}
}

function defineFields(){
	if($("#hidEeno").val() != ""){
		doSearch1();
	}
}

/**
 * 업무 호출
 */
function retrieve(btnFlag){
	switch(btnFlag){
		case "search" :
			 doSearch();
			 break;
		case "save" :
			 doSave();
			 break;
		case "right" :
			 moveRight();
			 break;
		case "left" :
			 moveLeft();
			 break;
		case "close":
			 selfClose();
			 break;
	}
}

function doSearch(){
	var params = {
		hName       : $("#key_user_name").val()
	};
	
	doCommonSearch("../../doSearchUserCode.do", util.jsonToString(params), "doEndQuery('select');", gridName1);
}

function doSearch1(){
	var params = {
		eeNo       : $("#hidEeno").val()
	};
	
	doCommonSearch("../../doSearchUserCode.do", util.jsonToString(params), "doEndQuery('select1');", gridName2);
}

/**
 * callback
 */
function loadCallBack(){
	addGridRow();
}

var c = 0;
function moveRight(){
	var tmpArr = [];
	var selectRow = $("#"+gridName1).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var xusrEmpno = getColValue("eeNo", rowId, gridName1);
				var xusrName = getColValue("hName", rowId, gridName1);
				var xusrStepName = getColValue("stepName", rowId, gridName1);
				var xusrDeptName = getColValue("dcdName", rowId, gridName1);
				if(xusrEmpno != ""){
					var data = {
						xusr_empno : xusrEmpno,
						xusr_name : xusrName,
						xusr_step_name : xusrStepName,
						xusr_dept_name : xusrDeptName
					};
					tmpArr.push(data);
				}
			}
		}
	}
	
	if(popGubun != "C"){
		var rtn = true;
		//회의주관자 와 참석자,참조자,공람자가 같은지 체크
		for(var k = 0; k<tmpArr.length; k++){
			var openerEeno = opener.document.getElementById("cnf_eeno").value;
			if(tmpArr[k].xusr_empno == openerEeno){
				alertUI(tmpArr[k].xusr_name + " 님은 회의주관자 입니다.");
				rtn = false;
				return;
			}
		}
		
		//인사정보 기준으로 참석자 같은지 체크
		var gridId = $('#'+gridName2).jqGrid('getDataIDs');
		for(var i = 1 ; i <= gridId.length ; i++){
			var eeno = getColValue("aEeNo", i, gridName2);
			for(var k = 0; k < tmpArr.length; k++){
				if(eeno == tmpArr[k].xusr_empno){
					alertUI(tmpArr[k].xusr_name + " 님은 이미 추가 되었습니다.");
					rtn = false;
					return;
				}
			}
		}
		
		if(rtn){
			var rowCount = $("#"+gridName1).getGridParam("reccount");
			for(var d = rowCount-1; d >= 0; d--){
				var rowId = selectRow[d];
				jQuery("#"+gridName1).delRowData(rowId);
			}
			if(c == 0){
				c = $("#"+gridName2).getGridParam("reccount");
			}
			for(var i = 0 ; i < tmpArr.length ; i++){
				var json = {
					aEeNo : tmpArr[i].xusr_empno,
					aHname : tmpArr[i].xusr_name,
					aStepName : tmpArr[i].xusr_step_name,
					aDcdName : tmpArr[i].xusr_dept_name
				}
				$("#"+gridName2).jqGrid("addRowData", c+1, json);
				$("#"+gridName2).jqGrid("setSelection", c+1, true);
				c++;
			}
			tmpArr = null;
		}
	}
}

function moveLeft(){
	var tmpArr = [];
	var selectRow = $("#"+gridName2).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var xusrEmpno = getColValue("aEeNo", rowId, gridName2);
				var xusrName = getColValue("aHname", rowId, gridName2);
				var xusrStepName = getColValue("aStepName", rowId, gridName2);
				var xusrDeptName = getColValue("aDcdName", rowId, gridName2);
				if(xusrEmpno != ""){
					var data = {
						xusr_empno : xusrEmpno,
						xusr_name : xusrName,
						xusr_step_name : xusrStepName,
						xusr_dept_name : xusrDeptName
					};
					tmpArr.push(data);
				}
			}
		}
	}
	
	var rowCount = $("#"+gridName2).getGridParam("reccount");
	for(var d = rowCount-1; d >= 0; d--){
		var rowId = selectRow[d];
		jQuery("#"+gridName2).delRowData(rowId);
	}
	
	var gridRowId = $("#"+gridName1).getDataIDs();
	for(var i = 0 ; i < tmpArr.length ; i++){
		var json = {
			eeNo : tmpArr[i].xusr_empno,
			hName : tmpArr[i].xusr_name,
			stepName : tmpArr[i].xusr_step_name,
			dcdName : tmpArr[i].xusr_dept_name
		}
		$("#"+gridName1).jqGrid("addRowData", i+1, json);
		$("#"+gridName1).jqGrid("resetSelection");
	}
}

function doSave(){
	var jsonArr = {};
	var empnoArr = "";
	var nameArr = "";
	var cnt = 0;
	var selectRow = $("#"+gridName2).jqGrid("getGridParam", "selarrrow");
	if(selectRow == 0){
		alertUI("데이터를 선택 하세요.");
		return;
	}
	var rowCount = $("#"+gridName2).getGridParam("reccount");
	for(var i = 0; i < rowCount; i++) {
		var rowId = selectRow[i];
		var xusrEmpno = getColValue("aEeNo", rowId, gridName2);
		var xusrName = getColValue("aHname", rowId, gridName2);
		var xusrStepName = getColValue("aStepName", rowId, gridName2);
		var xusrDeptName = getColValue("aDcdName", rowId, gridName2);
		
		if(i == 0){
			empnoArr += xusrEmpno;
			nameArr += xusrName +" "+ xusrStepName +" "+ xusrDeptName;
		}else{
			empnoArr += "," + xusrEmpno;
			nameArr += "\n" + xusrName +" "+ xusrStepName +" "+ xusrDeptName;							
		}
		++cnt;
	}
	
	jsonArr = {
		empnos : empnoArr,
		names : nameArr,
		cnt : cnt
	};
	opener.setInsaInfo(jsonArr, popGubun);
	self.close();
}

function doEndQuery(mode, rs){
	switch(mode){
		case "select" : 
			addGridRow(gridRowCnt, gridName1, datarow1);
		break;
		case "select1" :
			addGridRow(gridRowCnt, gridName2, datarow2);
			break;
	}
}