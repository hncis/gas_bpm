var saveCode;
var comboVal;
var comboVal1;
var comboVal2;
var comboVal3;
var comboVal4;
var lastsel;
var fnMerge;

function fnSetDocumentReady(){
	initMenus('1850');
	$("#key_from_ymd").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_ymd").datepicker({ dateFormat: "dd/mm/yy" });

	$(".inputOnlyNumber").numeric();

	if(sess_auth != 5 && sess_mstu != "M"){
		$("#key_eeno").val(sess_empno);
		$("#key_eenm").val(sess_name);

		readOnlyStyle("key_eeno", 1);
		readOnlyStyle("key_eenm", 1);
	}else{
		readOnlyStyle("key_eeno", 2);
		readOnlyStyle("key_eenm", 1);
	}

	setComboInfo();
}

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var paramValue  = "key_pgs_st_cd:X0010:Y;";
	getCommonCode(paramValue, "N", "ProdutoMultiCombo1();", url);
}

function ProdutoMultiCombo1(){
	getCommonCode("seqc_1::Z:S1;", "Y", "doComboCallback1(jsonData.sendResult);", "/hncis/officeSupplies/getCatgCombo.do");
}

function doComboCallback1(result){
	comboVal1 = result;
	ProdutoMultiCombo2();
}

function ProdutoMultiCombo2(){
	getCommonCode("seqc_2::Z:S2;", "Y", "doComboCallback2(jsonData.sendResult);", "/hncis/officeSupplies/getCatgCombo.do");
}

function doComboCallback2(result){
	comboVal2 = result;
	ProdutoMultiCombo3();
}

function ProdutoMultiCombo3(){
	getCommonCode("seqc_3::Z:S3;", "Y", "doComboCallback3(jsonData.sendResult);", "/hncis/officeSupplies/getCatgCombo.do");
}

function doComboCallback3(result){
	comboVal3 = result;
	getCommonCode("prod_cd::Z:PD;", "Y", "init();", "/hncis/officeSupplies/getCatgCombo.do");
}

var gridParam;
var gridName = "htmlTable";
var datarow = {doc_no:"", seq_no:"", prod_cd:"", seqc_1:"", seqc_2:"", seqc_3:"", seqc_4:"", qty:"", pgs_st_cd:"", pgs_st_nm:""};
function init(){
	var cn = ["", "", "Status", "Produto", "Sequencia1", "Sequencia2", "Sequencia3", "Sequencia4", "Qty", ""];
	var cm = [{name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"seq_no", index:"seq_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
	          {name:"prod_cd",index:"prod_cd", formatter:"select", width:150, align:"center", editable:true, sortable:true, edittype:"select", fixed: true,
	        	  	//Discipline
					editoptions:{value:getComboValue('prod_cd'),
						dataInit: function(elem) {
							$(elem).width(160);
						},
						dataEvents:[{type:'change',
								fn:function(e){
									var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridName+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	//jQuery("#"+gridName).setSelection(rowId, true);
			                        	jQuery("#" + gridName).jqGrid("setSelection", rowId, true);
			                        }
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
								}
				        	},
				        	{type:'focus',
								fn:function(e){
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
				        		}
				        	}
			        	]
					}
				},
	          {name:"seqc_1", index:"seqc_1", sortable:false, formatter:"select", width:150, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti1('seqc_1'),
						dataInit: function(elem) {
							$(elem).width(150);
						},
						dataEvents:[
						    {type:"focus",
						    	fn:function(e){
						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
	          },
	          {name:"seqc_2", index:"seqc_2", sortable:false, formatter:"select", width:150, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti2('seqc_2'),
						dataInit: function(elem) {
							$(elem).width(150);
						},
						dataEvents:[
						    {type:"focus",
						    	fn:function(e){
						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
			  },
	          {name:"seqc_3", index:"seqc_3", sortable:false, formatter:"select", width:150, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti3('seqc_3'),
						dataInit: function(elem) {
							$(elem).width(150);
						},
						dataEvents:[
						    {type:"focus",
						    	fn:function(e){
						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
			  },
	          {name:"seqc_4", index:"seqc_4", sortable:false, formatter:"string", width:150, align:"left", editable:true, frozen:false},
              {name:"qty", index:"qty", sortable:false, formatter:"string", width:75, align:"right", editable:true, frozen:false},
              {name:"pgs_st_cd", index:"pgs_st_cd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true}
			 ];

	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchByXos02.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			paramJson    : fnParamJson(),
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			shrinkToFit  : false,
			beforeEditFc : "beforeEditRow(id,name,val,iRow,iCol);",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');",
			selectCellFc : ""
		}]
	};
	commonJqGridInit(gridParam, "N");
}


function beforeEditRow(id,name,val,iRow,iCol){
	multiComboController(gridName, iRow, 0, "prod_cd:seqc_1", "comboVal1", "seqc_1", iRow);
	multiComboController(gridName, iRow, 0, "prod_cd:seqc_2", "comboVal2", "seqc_2", iRow);
	multiComboController(gridName, iRow, 0, "prod_cd:seqc_3", "comboVal3", "seqc_3", iRow);
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


function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
		   	doSearch();
			break;
	   case "save" :
		   	doSave();
			break;
	   case "request" :
		    doRequest();
			break;
	   case "requestCancel" :
		    doRequestCancel();
			break;
	}
}

function fnParamJson(){
	var paramVo = {
		eeno : $("#key_eeno").val(),
		from_ymd : dateConversionKr(trimChar($("#key_from_ymd").val(), "/")),
		to_ymd : dateConversionKr(trimChar($("#key_to_ymd").val(), "/")),
		pgs_st_cd : $("#key_pgs_st_cd").val(),
		corp_cd	  : sess_corp_cd
	};
	return paramVo;
}

function doSearch(){
	doCommonSearch("doSearchByXos02.do", util.jsonToString(fnParamJson()), "loadCallBack();", gridName, "N");
}

function loadCallBack(){
	addGridRow();
}

function doSave(){
	var paramsI = [];
	var paramsU = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("prod_cd", rowId, gridName) == ""){
					alertUI("Please select protuto.");
					return;
				}
				if(getColValue("seq_no", rowId, gridName) != ""){
					if(getColValue("pgs_st_cd", rowId, gridName) != "0"){
						alertUI("You can't save in this status");
						return;
					}else if(getColValue("pgs_st_cd", rowId, gridName) != "Z"){
						alertUI("You can't save in this status");
						return;
					}
					list = {
						doc_no  : getColValue("doc_no", rowId, gridName),
						seq_no  : getColValue("seq_no", rowId, gridName),
						prod_cd : getColValue("prod_cd", rowId, gridName),
						seqc_1  : getColValue("seqc_1", rowId, gridName),
						seqc_2  : getColValue("seqc_2", rowId, gridName),
						seqc_3  : getColValue("seqc_3", rowId, gridName),
						seqc_4  : getColValue("seqc_4", rowId, gridName),
						qty		: getColValue("qty", rowId, gridName),
						corp_cd	  : sess_corp_cd
					};
					paramsU.push(list);
				}else{
					list = {
						prod_cd : getColValue("prod_cd", rowId, gridName),
						seqc_1  : getColValue("seqc_1", rowId, gridName),
						seqc_2  : getColValue("seqc_2", rowId, gridName),
						seqc_3  : getColValue("seqc_3", rowId, gridName),
						seqc_4  : getColValue("seqc_4", rowId, gridName),
						qty		: getColValue("qty", rowId, gridName),
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
				doCommonAjax("doInsertByXos07Request.do", paramData, "insertCallBack(jsonData.sendResult);");
			}
		});
	});
}

function insertCallBack(result){
	setBottomMsg(result.message, true);
	doSearch();
}

function doRequest(){
	var paramVo = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("pgs_st_cd", rowId, gridName) != "0"){
					alertUI("You can't request in this status");
					return;
				}
				if(getColValue("doc_no", rowId, gridName) != ""){
					list = {
						doc_no : getColValue("doc_no", rowId, gridName),
						corp_cd	  : sess_corp_cd
					};
					paramVo.push(list);
				}
			}
		}
	}

	confirmUI("신청 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(paramVo)
				};
				doCommonAjax("doUpdateByXos07Request.do", paramData, "doSearch();");
			}
		});
	});
}

function doRequestCancel(){
	var paramVo = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				if(getColValue("pgs_st_cd", rowId, gridName) == "0"){
					alertUI("You can't request cancel in this status");
					return;
				}
				if(getColValue("doc_no", rowId, gridName) != ""){
					list = {
						doc_no : getColValue("doc_no", rowId, gridName),
						corp_cd	  : sess_corp_cd
					};
					paramVo.push(list);
				}
			}
		}
	}
	
	confirmUI("신청취소 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(paramVo),
				};
				doCommonAjax("doUpdateByXos07RequestCancel.do", paramData, "doSearch();");
			}
		});
	});
}

function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	jQuery("#"+gridName).jqGrid("addRowData", gridRowId+1, datarow);
}


function listDelete(){
	var rowId = jQuery("#"+gridName).jqGrid("getGridParam", "selrow");
	if(rowId == "" || rowId == null){
		alertUI("삭제할 항목을 선택하세요.");
		return;
	}else if(getColValue("doc_no", rowId, gridName) == ""){
		alertUI("삭제할 데이터가 없습니다.");
		return;
	}else if(getColValue("pgs_st_cd", rowId, gridName) != "0"){
		if(getColValue("pgs_st_cd", rowId, gridName) == "Z"){
			alertUI("You can't delete the stand by confirm in this status.");
		}else if(getColValue("pgs_st_cd", rowId, gridName) == "3"){
			alertUI("You can't delete the confirm in this status.");
		}
		return;
	}

	var paramVo = {
		doc_no : getColValue("doc_no", rowId, gridName),
		seq_no : getColValue("seq_no", rowId, gridName),
		message : "xgs07",
		corp_cd	  : sess_corp_cd
	};
	
	confirmUI("Do you want to delete production?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(paramVo),
				};
				doCommonAjax("doDeleteByRequestList.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch();");
			}
		});
	});
}

function getComboValueMulti1(comboName){
	var returnVal;
	if(typeof(comboVal1) == 'undefined'){
		returnVal = ":";
	}else{
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
		}else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
}

function getComboValueMulti2(comboName){
	var returnVal;
	if(typeof(comboVal2) == 'undefined'){
		returnVal = ":";
	}else{
		var i = 0;
		$.each(eval(comboVal2),function(targetNm,optionData){
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
		}else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
}

function getComboValueMulti3(comboName){
	var returnVal;
	if(typeof(comboVal3) == 'undefined'){
		returnVal = ":";
	}else{
		var i = 0;
		$.each(eval(comboVal3),function(targetNm,optionData){
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
		}else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
}

function setMiltiComboList(rowId,colNm){
	if(typeof(comboVal1) == 'undefined'){return;}
	if(colNm == 'seqc_1'){
		var colValue = getColValue('prod_cd',rowId);
		if(colValue == ''){
			$("select#" + rowId +"_"+ colNm).html('<option role="option" value=""></option>');
		}else{
			comboValTemp="";
			$.each(eval(comboVal1),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm=='seqc_1'&&colValue == optionData.key){
	    				comboValTemp += '<option role="option" value="' +
			            optionData.value + '">' +
						optionData.name + '</option>';
	        		}
				});
			});
			$("select#" + rowId +"_"+ colNm).html(comboValTemp);
		}
	}else if(colNm == 'seqc_2'){
		var colValue = getColValue('prod_cd',rowId);
		if(colValue == ''){
			$("select#" + rowId +"_"+ colNm).html('<option role="option" value=""></option>');
		}else{
			comboValTemp="";
			$.each(eval(comboVal2),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm=='seqc_2'&&colValue == optionData.key){
	    				comboValTemp += '<option role="option" value="' +
			            optionData.value + '">' +
						optionData.name + '</option>';
	        		}
				});
			});
			$("select#" + rowId +"_"+ colNm).html(comboValTemp);
		}
	}else if(colNm == 'seqc_3'){
		var colValue = getColValue('prod_cd',rowId);
		if(colValue == ''){
			$("select#" + rowId +"_"+ colNm).html('<option role="option" value=""></option>');
		}else{
			comboValTemp="";
			$.each(eval(comboVal3),function(targetNm,optionData){
				$.each(eval(optionData),function(index,optionData){
	    			if(targetNm=='seqc_3'&&colValue == optionData.key){
	    				comboValTemp += '<option role="option" value="' +
			            optionData.value + '">' +
						optionData.name + '</option>';
	        		}
				});
			});
			$("select#" + rowId +"_"+ colNm).html(comboValTemp);

		}
	}
};

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
