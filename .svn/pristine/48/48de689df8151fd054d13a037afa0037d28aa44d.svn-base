var fnMerge;
var comboVal1;
var gridParam;
var gridName1 = "htmlTable";
var datarow = {type_cd:"", eeno:"", eenm:"", dept_cd:"", dept_nm:"", gb:"", old_type:"", old_eeno:"", old_dept_cd:""};

function fnSetDocumentReady(){
	initMenus();

	sess_auth = $("#work_auth").val();

	getCommonCode("key_type:X3005:A;", "N", "initCombo()");		//목적
}

function initCombo(){
	getCommonCode('type:X3005:Z;', 'Y', 'init();');
}

function init(){
	var cn = ["유형", "사번", "이름", "부서코드", "부서", "", "", "", ""];
	var cm =
		[
		 {name:"type_cd",	index:"type_cd"		, sortable:false,		formatter: "select",	width:160,	align:'center',	editable:true,	edittype:'select',
			 editoptions:{value:getComboValue('type'), dataInit: function(elem) {$(elem).width(100);}
			 },
			 editrules:{required:true}
		 },
		 {name:"eeno",			index:"eeno"		, sortable:false,		formatter:"string",		width:160,	align:"center",	editable:true,	frozen : false},
		 {name:"eenm",			index:"eenm"		, sortable:false,		formatter:"string",		width:250,	align:"center",	editable:false,	frozen : false},
		 {name:"dept_cd",		index:"dept_cd"		, sortable:false,		formatter:"string",		width:160,	align:"center",	editable:false,	frozen : false},
		 {name:"dept_nm",		index:"dept_nm"		, sortable:false,		formatter:"string",		width:250,	align:"center",	editable:false,	frozen : false},
		 {name:"gb",			index:"type"		, sortable:false,		formatter:"string",		width:150,	align:"center",	editable:true,	frozen : false,	hidden: true},
		 {name:"old_type",		index:"old_type"	, sortable:false,		formatter:"string",		width:150,	align:"center",	editable:true,	frozen : false,	hidden: true},
		 {name:"old_eeno",		index:"old_eeno"	, sortable:false,		formatter:"string",		width:130,	align:"center",	editable:true,	frozen : false,	hidden: true},
		 {name:"old_dept_cd",	index:"old_dept_cd"	, sortable:false,		formatter:"string",		width:130,	align:"center",	editable:true,	frozen : false,	hidden: true}
	];

	var params = {
//		type			: $("#key_type").val()
	};

	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : "doSearchSecurityManagerMgmtList.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "",
			sortorder    : "",
			rownumbers   : false,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			paramJson    : params,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();"
		}]
	};

	commonJqGridInit(gridParam);
	jQuery("#"+gridName1).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});

	$("#" + gridName1).setColProp('eeno', {editoptions:{dataEvents:[{type:"keyup",
    	fn:function(e){
    		var row = $(e.target).closest("tr.jqgrow");
    		var rowId = row.attr("id");
    		var cbs = $("#jqg_" + gridName1 + "_" + rowId);
    		if(!cbs.is(":checked")){
    			//$("#" + gridName).setSelection(rowId, true);
    			jQuery("#" + gridName1).jqGrid("setSelection", rowId, true);
    		}
    		gridInsaInfo(rowId);
    	}
		}]
	}});
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "save" :
		case "delete" :
			doCudAction(gubn);
			break;
		case "addrow" :
			fnAddRow();
			break;
	}
}

function doSearch(msgFlag){
	var params = {
		type	: $("#key_type").val(),
		corp_cd	: sess_corp_cd
	};

	doCommonSearch("doSearchSecurityManagerMgmtList.do", util.jsonToString(params), "loadCallBack();", gridName1, msgFlag);
}

function loadCallBack(){
	addGridRow();
}
/*
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
	}else{
		$("#key_ops_nm").val("");
	}
}

function deptCallBack(result){
	$("#key_ops_nm").val(result.xorg_orga_e);
}

function setInsaInfo(){
	if($("#work_auth").val() !=5 && sess_mstu != "M"){
		$("#key_eeno").val(sess_empno);
		var keyData = { xusr_empno : sess_empno };
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
	else{
		init();
	}

}
function insaCallBack(result){
	setBottomMsg(result.message, false);
	$("#key_ops_cd").val(result.xusr_dept_nm_cd);
	$("#key_ops_nm").val(result.xusr_dept_nm_dept);
	init();
}*/

function doCudAction(gubun) {
	var url = "";
	var callBack = "actionCallBack(jsonData.sendResult);";
	var params = [];
	var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');

	if(gubun == "save"){
		url = "doInsertSecurityManagerMgmt.do";
	}else if(gubun == "delete"){
		if(sess_mstu != "M" && sess_auth != 5 && parent.$("#eeno").val() != sess_empno){
			alertUI("삭제할 수 없습니다.");
			return;
		}
		url = "doDeleteSecurityManagerMgmt.do";
	}

	for(var i=0; i<selectRow.length; i++){
		rowId = selectRow[i];
		if(rowId){
			if(gubun == "delete"){
				if(getColValue("old_eeno", rowId, gridName1) != ""){
					list = {
						type   		: getColValue("old_type", rowId),
						eeno  		: getColValue("old_eeno", rowId),
						dept_cd		: getColValue("old_dept_cd", rowId),
						corp_cd		: sess_corp_cd
					};
					params.push(list);
				}
			}else{
				if(getColValue("type", rowId) == ""){
					alertUI(rowId + " line : Please enter type!");
					return;
				}
				if(getColValue("eeno", rowId) == "" || getColValue("eeno", rowId, gridName1).length < 8){
					alertUI(rowId + " line : Please enter User id!");
					return;
				}
				list = {
					type		: getColValue("type_cd", rowId),
					eeno     	: getColValue("eeno", rowId),
					dept_cd		: getColValue("dept_cd", rowId),
					gb			: getColValue("gb", rowId),
					old_type	: getColValue("old_type", rowId),
					old_eeno	: getColValue("old_eeno", rowId),
					old_dept_cd	: getColValue("old_dept_cd", rowId),
					ipe_eeno    : sess_empno,
					updr_eeno   : sess_empno,
					corp_cd		: sess_corp_cd
				};
				params.push(list);
			}
		}
	}

	if(params.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}

	var confirmStr = '';
	if(gubun == "save"){
		confirmStr = '저장 하시겠습니까?';
	}else if(gubun == "delete"){
		confirmStr = '삭제 하시겠습니까?';
	}

	confirmUI(confirmStr);
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

	doSearch('N');
}


function gridInsaInfo(rowId){
	if(getColValue("eeno", rowId, gridName1) != ""){
		if(getColValue("eeno", rowId, gridName1).length == 8){
			var keyData = { 
					xusr_empno : getColValue("eeno", rowId, gridName1),
					corp_cd		: sess_corp_cd
			};
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToUserInfo.do", paramData, "gridInsaCallBack(jsonData.sendResult, '"+rowId+"')");
		}else{
			$("#"+gridName1).jqGrid("setCell", rowId, "eenm", " ");
			$("#"+gridName1).jqGrid("setCell", rowId, "dept_cd", " ");
			$("#"+gridName1).jqGrid("setCell", rowId, "dept_nm", " ");
		}
	}
}

function gridInsaCallBack(result, rowId){
	$("#"+gridName1).jqGrid("setCell", rowId, "eenm", result.xusr_name);
	$("#"+gridName1).jqGrid("setCell", rowId, "dept_cd", result.xusr_dept_code);
	$("#"+gridName1).jqGrid("setCell", rowId, "dept_nm", result.xusr_dept_name);
}

function fnAddRow(){
	var gridRowId = $("#"+gridName1).getDataIDs().length;
	jQuery("#"+gridName1).jqGrid("addRowData", gridRowId+1, datarow);
}