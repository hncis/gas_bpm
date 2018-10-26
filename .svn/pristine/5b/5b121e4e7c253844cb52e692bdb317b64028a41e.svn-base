var comboVal;
var lastsel;
var fnMerge;
var userKeyArr = ['xusr_name','xusr_dept_name'];
var userColArr = ['xusr_name','xusr_dept_name'];

function fnSetDocumentReady(){
	initMenus();
	sess_auth = $("#work_auth").val();
	readOnlyStyle("key_eenm", 1);
	$(".inputOnlyNumber").numeric();

	getCommonCode("key_produto::Y:PD;", "N", "getGridCommonCode();", "/hncis/generalService/getCatgCombo.do");
}

function getGridCommonCode(){
	getCommonCode("prod_cd::Z:PD;", "Y", "init();", "/hncis/generalService/getCatgCombo.do");
}

var gridParam;
var gridName = "htmlTable";
var datarow = {old_prod_cd:"", old_eeno:"", emp_eeno:"", prod_cd:"", eeno:"", xusr_name:"", xusr_dept_name:"", xusr_plac_work_nm:""};
function init(){
	var cn = ["old_prod_cd", "old_eeno", "tmp_eeno", "Produto", "ID", "Name", "Dept Name", "Region"];
	var cm = [{name:"old_prod_cd", index:"old_prod_cd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"old_eeno", index:"old_eeno", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"tmp_eeno", index:"tmp_eeno", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"prod_cd", index:"prod_cd", sortable:false, formatter:"select", width:160, align:"center", editable:true, edittype:'select',
				editoptions : { value : getComboValue('prod_cd'),
					dataInit : function(elem) {
						$(elem).width(150);
					}
				}
			  },
	          {name:"eeno", index:"eeno", sortable:false, formatter:"string", width:200, align:"center", editable:true, frozen:true,
				editoptions:{dataEvents:[{type:'keyup',
						fn:function(e){
							if(!isNumeric($(e.target).val())){
								$(e.target).val(selectNum($(e.target).val()));
							}
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
							searchToUserInfo(rowId, 'eeno');
						}
					},
					{type:'keydown',
						fn:function(e){
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
						}
					}]
		        }
	          },
	          {name:"xusr_name", index:"xusr_name", sortable:false, formatter:"string", width:230, align:"center", editable:false, frozen:true},
	          {name:"xusr_dept_name", index:"xusr_dept_name", sortable:false, formatter:"string", width:230, align:"center", editable:false, frozen:true},
	          {name:"xusr_plac_work_nm", index:"xusr_plac_work_nm", sortable:false, formatter:"string", width:165, align:"center", editable:false, frozen:true}
			  ];

	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchByXgs06.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			paramJson    : fnParamJson(),
			rownumbers   : false,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');",
			selectCellFc : ""
		}]
	};
	commonJqGridInit(gridParam);
	jQuery("#"+gridName).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});

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
		   doDelete();
		   break;
	}
}

function fnParamJson(){
	var paramVo = {
		eeno : $("#key_eeno").val(),
		prod_cd : $("#key_produto").val(),
		corp_cd	  : sess_corp_cd
	};
	return paramVo;
}

function doSearch(){
	doCommonSearch("doSearchByXgs06.do", util.jsonToString(fnParamJson()), "loadCallBack();", gridName, "N");
}

function loadCallBack(){
	addGridRow();
}

function doSave(){
	var paramsI = [];
	var paramsU = [];
	var selectRow = jQuery("#"+gridName).jqGrid('getGridParam','selarrrow');

	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				if($.trim(getColValue("xusr_name", rowId, gridName)) == ""){
					alertUI("Please. enter id number");
					return;
				}
				if(getColValue("prod_cd", rowId, gridName) != ""){
					if(getColValue("old_prod_cd", rowId, gridName) != ""){
						list = {
							old_prod_cd : getColValue("old_prod_cd", rowId, gridName),
							old_eeno : getColValue("old_eeno", rowId, gridName),
							prod_cd : getColValue("prod_cd", rowId, gridName),
							eeno  : getColValue("eeno", rowId, gridName),
							corp_cd	  : sess_corp_cd
						};
						paramsU.push(list);
					}else{
						list = {
							prod_cd : getColValue("prod_cd", rowId, gridName),
							eeno  : getColValue("eeno", rowId, gridName),
							corp_cd	  : sess_corp_cd
						};
						paramsI.push(list);
					}
				}
			}
		}
		confirmUI("저장 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							paramsI : util.jsonToList(paramsI),
							paramsU : util.jsonToList(paramsU)
					};
					doCommonAjax("doInsertByXgs06.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch();");
				}
			});
		});
	}
}


function doDelete(){
	var paramsD = [];
	var selectRow = jQuery("#"+gridName).jqGrid('getGridParam','selarrrow');

	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId){
				if(getColValue("old_prod_cd", rowId, gridName) == ""){
					alertUI("삭제할 수 없는 상태입니다.");
					return;
				}
				list = {
					old_prod_cd : getColValue("old_prod_cd", rowId, gridName),
					old_eeno : getColValue("old_eeno", rowId, gridName),
					corp_cd	  : sess_corp_cd
				};
				paramsD.push(list);
			}
		}
		
		confirmUI("삭제 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							paramJson : util.jsonToList(paramsD)
					};
					doCommonAjax("doDeleteByXgs06.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch();");
				}
			});
		});
	}
}

function searchToUserInfo(rowId, colNm){
	if(getColValue(colNm, rowId, gridName).length != 8 ){
		$("#"+gridName).setCell(rowId, 'xusr_name', ' ');
		$("#"+gridName).setCell(rowId, 'xusr_dept_name', ' ');
		return;
	}

	var keyData = {
		xusr_empno : getColValue(colNm, rowId, gridName),
		corp_cd	  : sess_corp_cd
	};
	var paramData = {
		paramJson : util.jsonToString(keyData)
	};

	doCommonAjax("/hncis/system/doSearchToUserManagementByUserDetail.do", paramData, "setUserInfo(jsonData.sendResult, 'Y', "+rowId+");");
}

function clearInsa(){
	if($("#key_eeno").val() == ""){
		$("#key_eeno").val("");
		$("#key_eenm").val("");
	}
}

function setInsaInfo(){
	if($("#key_eeno").val() != ""){
		var keyData = { 
				xusr_empno : $("#key_eeno").val(),
				corp_cd	  : sess_corp_cd
		};
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

function gridInsaInfo(rowId){
	if(getColValue("eeno", rowId, gridName) != ""){
		if(getColValue("eeno", rowId, gridName).length > 8) return;
		if(getColValue("eeno", rowId, gridName).length == 8){
			if(getColValue("eeno", rowId, gridName) == getColValue("tmp_eeno", rowId, gridName)){ return; }
			$("#"+gridName).jqGrid("setCell", rowId, "tmp_eeno", getColValue("eeno", rowId, gridName));

			var keyData = { 
					xusr_empno : getColValue("eeno", rowId, gridName),
					corp_cd	  : sess_corp_cd
			};
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/doSearchToUserInfo.do", paramData, "gridInsaCallBack(jsonData.sendResult, '"+rowId+"')");
		}
	}
}

function gridInsaCallBack(result, rowId){
	$("#"+gridName).jqGrid("setCell", rowId, "xusr_name", result.xusr_name);
	$("#"+gridName).jqGrid("setCell", rowId, "xusr_dept_name", result.xusr_dept_name);
}