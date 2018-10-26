var saveCode;
var comboVal;
var comboVal1;
var comboVal2;
var comboVal3;
var comboVal4;
var lastsel;
var fnMerge;
var newFlag = "Y";

function fnSetDocumentReady(){
	initMenus('1850');
	$('#GS').slideDown('fast');
	if($("#hid_doc_no").val() != ""){
		$("#doc_no").val($("#hid_doc_no").val());
		$("#eeno").val($("#hid_eeno").val());
	}
	$(".inputOnlyNumber").numeric();
	setInsaInfo();
	sess_auth = $("#work_auth").val();
//	chk_auth();
}

function setInsaInfo(){
	if($("#eeno").val() != ""){
		$("#nSource").val($("#eeno").val());
		if(saveCode == $("#nSource").val()){ return; }
		saveCode = $("#nSource").val();
		
		var keyData = { 
				xusr_empno : $("#eeno").val(),
				corp_cd		: sess_corp_cd
		};
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
}

function insaCallBack(result){
	$("#eeno").val(result.xusr_empno);
	$("#ee_nm").val(result.xusr_name);
	$("#poa_nm").val(result.xusr_step_name);
	$("#ops_nm").val(result.xusr_dept_name);
	$("#ops_cd").val(result.xusr_dept_code);
	$("#keyOpsCd").val(result.xusr_dept_nm_cd);
	$("#keyOpsNm").val(result.xusr_dept_nm_dept);
	$("#tel_no").val(result.xusr_tel_no);
	$("#plac_work").val(result.xusr_plac_work);
	$("#plac_work_nm").val(result.xusr_plac_work_nm);
	
	if(newFlag == "Y"){
		chk_auth();
	}else{
		init();
	}//	ProdutoMultiCombo1();
//	getCommonCode("prod_cd::Z:PD;", "Y", "ProdutoMultiCombo1();", "/hncis/generalService/getCatgCombo.do");
}

function cearInsa(){
	if($("#eeno").val() == ""){
		saveCode = "";
		$("#nSource").val("");
		$("#eeno").val("");
		$("#ee_nm").val("");
		$("#ops_nm").val("");
		$("#ops_cd").val("");
		$("#poa_nm").val("");
		$("#tel_no").val("");
		$("#keyOpsCd").val("");
		$("#keyOpsNm").val("");
		$("#plac_work").val("");
		$("#plac_work_nm").val("");
	}
}

function chk_auth(){
	var frm = document.mainForm;
	with(frm){
		if(Number(sess_auth) > 4 || sess_mstu == "M"){
			if($("#hid_doc_no").val() != ""){
				readOnlyStyle("eeno", 1);
			}else{
				readOnlyStyle("eeno", 2);
			}
		}else{
			readOnlyStyle("eeno", 1);
		}
		readOnlyStyle("ee_nm", 1);
		readOnlyStyle("poa_nm", 1);
		readOnlyStyle("ops_nm", 1);
		readOnlyStyle("ptt_ymd", 1);
		readOnlyStyle("doc_no", 1);
		readOnlyStyle("tel_no", 1);
	}
	
	ProdutoMultiCombo1();
}

function ProdutoMultiCombo1(){
	getCommonCode("seqc_1:"+$("#plac_work").val()+":Z:S1:BV;", "Y", "doComboCallback1(jsonData.sendResult);", "/hncis/generalService/getCatgCombo.do");
}

function doComboCallback1(result){
	comboVal1 = result;
	ProdutoMultiCombo2();
}

function ProdutoMultiCombo2(){
	getCommonCode("seqc_2:"+$("#plac_work").val()+":Z:S2:BV;", "Y", "doComboCallback2(jsonData.sendResult);", "/hncis/generalService/getCatgCombo.do");
}

function doComboCallback2(result){
	comboVal2 = result;
	ProdutoMultiCombo3();
}

function ProdutoMultiCombo3(){
	getCommonCode("seqc_3:"+$("#plac_work").val()+":Z:S3:BV;", "Y", "doComboCallback3(jsonData.sendResult);", "/hncis/generalService/getCatgCombo.do");
}

function doComboCallback3(result){
	comboVal3 = result;
	getCommonCode("prod_cd:"+$("#plac_work").val()+":Z:PD:BV;", "Y", "init();", "/hncis/generalService/getCatgCombo.do");
}

var gridParam;
var gridName = "htmlTable";
var datarow = {doc_no:"", seq_no:"", prod_cd:"", seqc_1:"", seqc_2:"", seqc_3:"", seqc_4:"", qty:"", pgs_st_cd:"", pgs_st_nm:"", hid_seqc_1:"", hid_seqc_2:"", hid_seqc_3:"", pop_img:"", comment:"",expt_ymd:"", expt_time:"",mileage:"", car_no:"", car_vehicle:"", car_type:"", car_en_nm:""};
function init(){
	$("#"+gridName).GridUnload();
	var cn = ["", "", "Produto", "Location", "Service Type", "Specification", "Plate No.", "Vehicle", "Engine", "Type", "Mileage", "Description", "Qty", "Expected<br>Date", "Expected<br>Time", "", "Status", "File attach", "", "", "", "Comment"];
	var cm = [{name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"seq_no", index:"seq_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden: true},
	          {name:"prod_cd",index:"prod_cd", formatter:"select", width:155, align:"center", editable:true, sortable:true, edittype:"select", fixed: true,
	        	  	//Discipline
					editoptions:{value:getComboValue('prod_cd'),
						dataInit: function(elem) {
							$(elem).width(145);
						},
						dataEvents:[{type:'change', 
								fn:function(e){
									var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridName+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery("#"+gridName).jqGrid("setSelection", rowId, true);
			                        }
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
								}
				        	},
				        	{type:'focus', 
								fn:function(e){
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
				        		}
				        	}
			        	]
					}
				},
	          {name:"seqc_1", index:"seqc_1", sortable:false, formatter:"select", width:155, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti1('seqc_1'),
						dataInit: function(elem) {
							$(elem).width(145);
						},
						dataEvents:[{type:'change', 
								fn:function(e){
									var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridName+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery("#"+gridName).jqGrid("setSelection", rowId, true);
			                        }
			                        $("#htmlTable").setCell(rowId,'hid_seqc_1',getColValue("seqc_1",rowId, gridName));
								}
				        	},
						    {type:"focus",
						    	fn:function(e){
//						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
	          },
	          {name:"seqc_2", index:"seqc_2", sortable:false, formatter:"select", width:155, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti2('seqc_2'),
						dataInit: function(elem) {
							$(elem).width(145);
						},
						dataEvents:[{type:'change', 
								fn:function(e){
									var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridName+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery("#"+gridName).jqGrid("setSelection", rowId, true);
			                        }
			                        $("#htmlTable").setCell(rowId,'hid_seqc_2',getColValue("seqc_2",rowId, gridName));
								}
				        	},
						    {type:"focus",
						    	fn:function(e){
//						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
			  },
	          {name:"seqc_3", index:"seqc_3", sortable:false, formatter:"select", width:155, align:"center", editable:true, edittype:'select',
					editoptions:{value:getComboValueMulti3('seqc_3'),
						dataInit: function(elem) {
							$(elem).width(145);
						},
						dataEvents:[{type:'change', 
								fn:function(e){
									var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridName+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery("#"+gridName).jqGrid("setSelection", rowId, true);
			                        }
			                        $("#htmlTable").setCell(rowId,'hid_seqc_3',getColValue("seqc_3",rowId, gridName));
								}
				        	},
						    {type:"focus",
						    	fn:function(e){
//						    		multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_1", "comboVal1", "seqc_1");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_2", "comboVal2", "seqc_2");
//									multiComboController(gridName, $(e.target).closest('tr.jqgrow'), 0, "prod_cd:seqc_3", "comboVal3", "seqc_3");
						    	}
						    }
						]
					}
			  },
			  {name:"car_no", index:"car_no", sortable:false, formatter:"string", width:80, align:"center", editable:true, frozen:false},
			  {name:"car_vehicle", index:"car_vehicle", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
			  {name:"car_en_nm", index:"car_en_nm", sortable:false, formatter:"string", width:60, align:"center", editable:false, frozen:false},
			  {name:"car_type", index:"car_type", sortable:false, formatter:"string", width:60, align:"center", editable:false, frozen:false},
			  {name:"mileage", index:"mileage", sortable:false, formatter:"string", width:80, align:"center", editable:true, frozen:false},
	          {name:"seqc_4", index:"seqc_4", sortable:false, formatter:"string", width:150, align:"left", editable:true, frozen:false},
              {name:"qty", index:"qty", sortable:false, formatter:"string", width:60, align:"right", editable:true, frozen:false, hidden:true},
              {name:"expt_ymd", index:"expt_ymd", sortable:false, formatter:"string", width:80, align:"center", editable:true, frozen:false,
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
            {name:"expt_time", index:"expt_time", sortable:false,	formatter:"string",	width:60, align:"center", editable:true, frozen : false,
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
	          {name:"pgs_st_cd", index:"pgs_st_cd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:115, align:"center", editable:false, frozen:false},
	          {name:"pop_img",    index:"pop_img", sortable:false,	formatter:"string",	width:60,  align:"center",	editable:false,	frozen : false},
	          {name:"hid_seqc_1", index:"hid_seqc_1", sortable:false, formatter:"string", width:55, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"hid_seqc_2", index:"hid_seqc_2", sortable:false, formatter:"string", width:55, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"hid_seqc_3", index:"hid_seqc_3", sortable:false, formatter:"string", width:55, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"comment", index:"comment", sortable:false, formatter:"string", width:300, align:"center", editable:false, frozen:false}
	];
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "/doSearchToEmpty.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			shrinkToFit  : false,
//			beforeEditFc : "beforeEditRow(id,name,val,iRow,iCol);",
			selectCellFc : "selectCellFcRow(rowid, iCol);setChangeImg();",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');setChangeImg();"
		}]
	};
	commonJqGridInit(gridParam, "N");
	
	if($("#hid_doc_no").val() != ""){
		newFlag = "N";
		doSearch1("Y");
	}
	
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
}

function selectCellFcRow(rowid, iCol){
	multiComboController(gridName, rowid, 0, "prod_cd:seqc_1", "comboVal1", "seqc_1", rowid);
	multiComboController(gridName, rowid, 0, "prod_cd:seqc_2", "comboVal2", "seqc_2", rowid);
	multiComboController(gridName, rowid, 0, "prod_cd:seqc_3", "comboVal3", "seqc_3", rowid);
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
//			var orgValue = getColValue(arrCol[multiCnt+1],rowId, gridName);
			var orgValue = getColValue('hid_'+arrCol[multiCnt+1],rowId, gridName);
			$("select#" + rowId +"_"+ arrCol[multiCnt+1]).html(comboValTemp);
			jQuery(orgSelect).val(orgValue);
			$("#"+gridName).setCell(rowId,'hid_'+arrCol[multiCnt+1],getColValue(arrCol[multiCnt+1],rowId, gridName));
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
	   case "save" :
		   	doSave();
			break;
	   case "delete" :
		    doDelete();
			break;
	   case "request" :
		    doRequest(); 
			break;
	   case "requestCancel" :
		    doRequestCancel();
			break;
	   case "back" :
		    doBack();
		    break;
	   case "new" :
		    doNew();
	}
}

function doSave(){
	var gubnNm = "", callBack = "", basic_mode = "";
	if($("#doc_no").val() == ""){
		basic_mode = "I";
		gubnNm = "save";
		callBack = "insertCallBack(jsonData.sendResult);";
	}else{
		basic_mode = "U";
		gubnNm = "modify";
		callBack = "modifyCallBack(jsonData.sendResult);";
	}
	
	var paramVo = {
		basic_mode : basic_mode,
		doc_no : $("#temp_doc_no").val(),
		eeno : $("#eeno").val(),
		dept_cd : $("#ops_cd").val(),
		gs_type : 'BV',
		corp_cd	: sess_corp_cd
	};
	
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
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(pgsStCd == "Z" || pgsStCd == "3" || pgsStCd == "2"){
					alertUI("You can't "+gubnNm+" in this status");
					return;
				}
				if(getColValue("prod_cd", rowId, gridName) == ""){
					alertUI("Please select protuto.");
					return;
				}
				if(getColValue("seq_no", rowId, gridName) != ""){
					list = {
						doc_no  : $("#temp_doc_no").val(),
						seq_no  : getColValue("seq_no", rowId, gridName),
						region_cd : $("#plac_work").val(),
						prod_cd : getColValue("prod_cd", rowId, gridName),
						seqc_1  : getColValue("hid_seqc_1", rowId, gridName),
						seqc_2  : getColValue("hid_seqc_2", rowId, gridName),
						seqc_3  : getColValue("hid_seqc_3", rowId, gridName),
						seqc_4  : getColValue("seqc_4", rowId, gridName),
						qty		: getColValue("qty", rowId, gridName),
						car_no	: getColValue("car_no", rowId, gridName),
						mileage	: getColValue("mileage", rowId, gridName),
						expt_ymd : dateConversionKr(trimChar(getColValue("expt_ymd", rowId, gridName), "/")),
						expt_time : selectNum(getColValue("expt_time", rowId, gridName)),
						corp_cd		: sess_corp_cd
					};
					paramsU.push(list);
				}else{
					list = {
						doc_no  : $("#temp_doc_no").val(),
						region_cd : $("#plac_work").val(),
						prod_cd : getColValue("prod_cd", rowId, gridName),
						seqc_1  : getColValue("hid_seqc_1", rowId, gridName),
						seqc_2  : getColValue("hid_seqc_2", rowId, gridName),
						seqc_3  : getColValue("hid_seqc_3", rowId, gridName),
						seqc_4  : getColValue("seqc_4", rowId, gridName),
						qty		: getColValue("qty", rowId, gridName),
						car_no	: getColValue("car_no", rowId, gridName),
						mileage	: getColValue("mileage", rowId, gridName),
						expt_ymd : dateConversionKr(trimChar(getColValue("expt_ymd", rowId, gridName), "/")),
						expt_time : selectNum(getColValue("expt_time", rowId, gridName)),
						corp_cd		: sess_corp_cd
					};
					paramsI.push(list);
				}
			}
		}
	}
	
	confirmUI("Do you want to "+gubnNm+"?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramsV : util.jsonToString(paramVo),
						paramsI : util.jsonToList(paramsI),
						paramsU : util.jsonToList(paramsU)
					};
					doCommonAjax("/hncis/generalService/doInsertByRequest.do", paramData, callBack);
			}
		});
	});
}

function insertCallBack(result){
	setBottomMsg(result.message, true);
	$("#doc_no").val(result.code);
	doSearch1("Y");
}

function modifyCallBack(result){
	setBottomMsg(result.message, true);
	doSearch2();
}

function doSearch1(flag){
	var paramVo = {
		doc_no : $("#doc_no").val(),
		gs_type : 'BV',
		corp_cd	: sess_corp_cd
	};
	
	var paramData = {
		paramJson : util.jsonToString(paramVo),
	};
	
	doCommonAjax("/hncis/generalService/doSearchByRequestInfo.do", paramData, "searchCallBack1(jsonData.sendResult, '"+flag+"');");
}

function searchCallBack1(result, flag){
	setBottomMsg(result.message, false);
	
	$("#eeno").val(result.eeno);
	$("#ee_nm").val(result.eenm);
	$("#poa_nm").val(result.pos_nm);
	$("#ops_cd").val(result.dept_cd);
	$("#ops_nm").val(result.dept_nm);
	$("#ptt_ymd").val(result.ptt_ymd);
	
	if(flag == "Y"){
		doSearch2();
	}
}

function doSearch2(){
	var params = {
		doc_no : $("#doc_no").val(),
		gs_type : 'BV',
		corp_cd	: sess_corp_cd
	};
	
	doCommonSearch("doSearchByRequestList.do", util.jsonToString(params), "searchCallBack2();");
}

function searchCallBack2(){
	setBottomMsg("Succes search complete", false);
	addGridRow(15, gridName, datarow);
	setChangeImg();
}

function doDelete(){
	if($("#doc_no").val() == ""){
		alertUI("삭제할 데이터가 없습니다.");
		return;
	}
	
	var paramList = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(pgsStCd == "Z" || pgsStCd == "3" || pgsStCd == "2"){
					alertUI("삭제할 수 없는 상태입니다.");
					return;
				}
				list = {
					doc_no  : getColValue("doc_no", rowId, gridName),
					corp_cd	: sess_corp_cd
				};
				paramList.push(list);
			}
		}
	}
	
	var paramVo = {
		doc_no : $("#doc_no").val(),
		corp_cd	: sess_corp_cd
	};
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramV : util.jsonToString(paramVo),
						paramD : util.jsonToList(paramList)
					};
					doCommonAjax("/hncis/generalService/doDeleteByRequest.do", paramData, "deleteCallBack(jsonData.sendResult);");
			}
		});
	});
}

function deleteCallBack(result){
	mainForm.reset();
	setBottomMsg(result.message, true);
	$("#eeno").val(sess_empno);
	saveCode = "";
	$("#hid_doc_no").val("");
	$("#hid_eeno").val("");
//	$("#temp_doc_no").val(result.code);

	$("#"+gridName).trigger("reloadGrid");
	setInsaInfo();
	setTimeout("addRow()" , 100);
}

function doRequest(){
	if($("#doc_no").val() == ""){
		alertUI("신청할 데이터가 없습니다.");
		return;
	}
	
	var paramList = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(pgsStCd != "0"){
					alertUI("You can't request in this status");
					return;
				}
				list = {
					doc_no  : getColValue("doc_no", rowId, gridName),
					seq_no  : getColValue("seq_no", rowId, gridName),
					corp_cd	: sess_corp_cd
				};
				paramList.push(list);
			}
		}
	}
	
	confirmUI("신청 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(paramList)
					};
					doCommonAjax("/hncis/generalService/doUpdateByRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch2();");
			}
		});
	});
}

function doRequestCancel(){
	if($("#doc_no").val() == ""){
		alertUI("신청취소할 데이터가 없습니다.");
		return;
	}
	
	var paramList = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			if(rowId){
				var pgsStCd = getColValue("pgs_st_cd", rowId, gridName);
				if(pgsStCd != "Z"){
					alertUI("You can't request cancel in this status");
					return;
				}
				list = {
					doc_no  : getColValue("doc_no", rowId, gridName),
					seq_no  : getColValue("seq_no", rowId, gridName),
					corp_cd	: sess_corp_cd
				};
				paramList.push(list);
			}
		}
	}
	
	confirmUI("신청취소 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(paramList)
					};
					doCommonAjax("/hncis/generalService/doUpdateByRequestCancel.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch2();");
			}
		});
	});
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
		alertUI("삭제할 수 없는 상태입니다.");
		return;
	}
	
	var paramVo = {
		doc_no : getColValue("doc_no", rowId, gridName),
		seq_no : getColValue("seq_no", rowId, gridName),
		corp_cd	: sess_corp_cd
	};
	
	confirmUI("Do you want to delete production?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(paramVo),
					};
					doCommonAjax("/hncis/generalService/doDeleteByRequestList.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch2();");
			}
		});
	});
}

function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	jQuery("#"+gridName).jqGrid("addRowData", gridRowId+1, datarow);
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

function getComboValueMulti1(comboName){
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

function getComboValueMulti2(comboName){
	var returnVal;
	if(typeof(comboVal2) == 'undefined'){
		returnVal = ":";
	}
	else{
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
		}
		else{
			returnVal = returnVal.replace("undefined", "");
		}
	}
	return returnVal;
}

function getComboValueMulti3(comboName){
	var returnVal;
	if(typeof(comboVal3) == 'undefined'){
		returnVal = ":";
	}
	else{
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
		}
		else{
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

function setChangeImg(){
	var gridId = jQuery('#htmlTable').jqGrid('getDataIDs');
	for(var i = 1 ; i <= gridId.length ; i++){
		var imgSrc = "<img src='../../images/hncis_bttn/open-n.gif' onClick='doFileAttachOpen(\""+i+"\");'/>";
		jQuery('#htmlTable').jqGrid("setRowData", i, {pop_img:imgSrc}); 
	}
}

var win;
function doFileAttachOpen(seq){
	if(win != null){ win.close(); }
	var url = "xbv07_file.gas", width = "460", height = "453";
	
	if($("#hid_doc_no").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val()+seq);
	}else{
		$("#file_doc_no").val($("#hid_doc_no").val()+seq);
	}

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

	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

function doNew(){
	$("#hid_doc_no").val("");
	$("#hid_eeno").val("");
	$("#ptt_ymd").val("");
	$("#doc_no").val("");
	$("#tel_no").val("");
	$("#plac_work").val("");
	$("#plac_work_nm").val("");
	$("#temp_doc_no").val(getTmpDocNo());
	$("#eeno").val(sess_empno);
	saveCode = "";
	setInsaInfo();
}

var win_standard;
function doStandardExpenseOpen(){	
	if(win_standard != null){ win_standard.close(); }
	var url = "xbv07_standard.gas", width = "1030", height = "623";
	
	win_standard = newPopWin("about:blank", width, height, "win_standard");
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_standard"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function gridCarInfo(rowId){
	if(getColValue("car_no", rowId, gridName) != ""){
		if(getColValue("car_no", rowId, gridName).length > 7) return;
		if(getColValue("car_no", rowId, gridName).length == 7){

			var keyData = { 
					car_no : getColValue("car_no", rowId, gridName),
					corp_cd		: sess_corp_cd
			};
			paramData = {
				paramJson : util.jsonToString(keyData)
			};
			doCommonAjax("/hncis/trafficTicket/doSearchToCarInfo.do", paramData, "gridCarCallBack(jsonData.sendResult, '"+rowId+"')");
		}
	}else{
		$("#"+gridName).jqGrid("setCell", rowId, "car_vehicle", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "car_type", " ");
	}
}

function gridCarCallBack(result, rowId){
	setBottomMsg(result.message, false);
	if(result.vehl_nm == ""){
		$("#"+gridName).jqGrid("setCell", rowId, "car_vehicle", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "car_type", " ");
		$("#"+gridName).jqGrid("setCell", rowId, "car_en_nm", " ");
	}else{
		$("#"+gridName).jqGrid("setCell", rowId, "car_vehicle", result.vehl_nm);
		$("#"+gridName).jqGrid("setCell", rowId, "car_type", result.car_cd_nm);
		$("#"+gridName).jqGrid("setCell", rowId, "car_en_nm", result.car_en_nm);
	}
	setRowEditMode(gridName, rowId);
}