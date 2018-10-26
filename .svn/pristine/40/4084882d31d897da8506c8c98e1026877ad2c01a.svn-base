function fnSetDocumentReady(){
	initMenus();
	$('#RM').slideDown('fast');
	init();    
}

/**
 * process init() loading
 */
var params;
var fnMerge;
var gridParam;
var gridName = "htmlTable";
var datarow  = {meal_name:"", meal_code:"", meal_price:"", use_yn:"", meal_code_old:"", sort:""};

function init(){

	//set grid parameter
	var params = {
			meal_name 	: $("#key_meal_name").val(),
			meal_code 	: $("#key_meal_code").val(),
			use_yn 		: $("#key_use_yn").val(),
			corp_cd		: sess_corp_cd
		};

	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "doSearchGridRmToMealsManagement.do",
			colNames     : ["Meal", "Code", "Price", "Use Type", "Sort", ""],
			colModel     : [
			                {name:"meal_name",		index:"meal_name"		, sortable:false,		formatter:"string",	width:"250",	align:"center",	editable:true,	frozen:false},
			            	{name:"meal_code",		index:"meal_code"		, sortable:false,		formatter:"string",	width:"180",	align:"center",	editable:true,	frozen:false, editoptions: {maxlength:"4"}},
			            	{name:"meal_price",	index:"meal_price"	, sortable:false,formatter:numFormat, formatoptions: {
			    			    decimalSeparator:",",
			    			    thousandsSeparator:".",
			    			    decimalPlaces:2,
			    			    defaultValue:""
			    			 },		width:300,	align:"right",	editable:true,	frozen : false,
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
			            	{name:"use_yn",	index:"use_yn"	, sortable:false,		edittype:"select", formatter:"select",	width:"150", align:"center",	editable:true,	
			            		editoptions:{value:"Y:Y;N:N"}
			            	},
			            	{name:"sort",		index:"sort"		, sortable:false,		formatter:"string",	width:"75",	align:"center",	editable:true,	frozen:false, editoptions: {maxlength:"4"}},
			            	{name:"meal_code_old",	index:"meal_code_old"	, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			            	],
			height       : "100%",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			pager		 : "htmlPager",
			completeFc   : "addGridRow();initAfterMenus();",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);",
			paramJson    : params
		}]
	};
	
	//common jqGrid call...
	commonJqGridInit(gridParam);
	
	//method overliding
	jQuery("#"+gridName).jqGrid("navGrid","#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	setGridColumnOptions(gridName);
}

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "save" :
			doSave();
			break;
		case "edit" :
			doModify();
			break;
		case "delete" :
			doDelete();
			break;
		case "addrow" :
			fnAddRow();
			break;
	}
}

function fnAddRow(){
	var gridRowId = $("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
}

function doSearch(msgFlag){

	var params = {
			meal_name : 	$("#key_meal_name").val(),
			meal_code : 	$("#key_meal_code").val().toUpperCase(),
			use_yn  : 	$("#key_use_yn").val(),
			corp_cd	  : sess_corp_cd
	}; 
	    
	doCommonSearch("doSearchGridRmToMealsManagement.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", gridName, msgFlag);
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
				if(getColValue("meal_name" , rowId, gridName) == ""){
					alertUI("Please enter Meal.");
					return;
				}else if(getColValue("meal_code" , rowId, gridName) == ""){
					alertUI("Please enter Code.");
					return;
				}else if(getColValue("meal_price" , rowId, gridName) == ""){
					alertUI("Please enter Price.");
					return;
				}
				
				if(getColValue("meal_code_old", rowId, gridName) != ""){
					list ={
						meal_name      	: getColValue("meal_name"  , rowId, gridName),
						meal_code   	: getColValue("meal_code"  , rowId, gridName).toUpperCase(),
						meal_price   	: getColValue("meal_price" , rowId, gridName).replace(",", "."),
						use_yn  		: getColValue("use_yn" , rowId, gridName),
						meal_code_old   : getColValue("meal_code_old", rowId, gridName).toUpperCase(),
						sort    		: getColValue("sort", rowId, gridName).toUpperCase(),
						ipe_eeno    	: sess_empno,
						updr_eeno   	: sess_empno,
						corp_cd			: sess_corp_cd
					};
					paramsU.push(list);
				}else{
					list ={
						meal_name      	: getColValue("meal_name"  , rowId, gridName),
						meal_code   	: getColValue("meal_code"  , rowId, gridName).toUpperCase(),
						meal_price   	: getColValue("meal_price" , rowId, gridName).replace(",", "."),
						use_yn  		: getColValue("use_yn" , rowId, gridName),
						meal_code_old   : getColValue("meal_code_old", rowId, gridName).toUpperCase(),
						sort    		: getColValue("sort", rowId, gridName).toUpperCase(),
						ipe_eeno    	: sess_empno,
						updr_eeno   	: sess_empno,
						corp_cd			: sess_corp_cd
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
						paramsU	: util.jsonToList(paramsU)
					};
				doCommonAjax("doSaveRmToMealsManagement.do", paramData, "saveCallBack('save', jsonData.sendResult);");
			}
		});
	});
}

function saveCallBack(id, result){
	setBottomMsg(result.message, true);
	doSearch("N");
}


function doDelete(){
	var params = [];
	var selectRow = jQuery("#"+gridName).jqGrid("getGridParam", "selarrrow");
	
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}else{
		for(var i = 0; i < selectRow.length; i++){
			var rowId = selectRow[i];
			
			if(getColValue("meal_code_old", rowId, gridName) == ""){
				alertUI("You can't delete");
				return;
			}
			
			if(rowId){
				list ={
						meal_code_old    : getColValue("meal_code_old", rowId, gridName).toUpperCase(),
						corp_cd			 : sess_corp_cd
				};
				params.push(list);
			}
		}
	}
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(params)
					};
					doCommonAjax("doDeleteRmToMealsManagement.do", paramData, "deleteCallBack('delete', jsonData.sendResult);");
			}
		});
	});
}

function deleteCallBack(id, result){
	setBottomMsg(result.message, true);
	doSearch("N");
}

/**
 * addRow $.IDs.length + 1
 * datarow colModel keyName:"", ....
 */
function addRow(){
	var gridRowId = jQuery("#"+gridName).getDataIDs().length;
	for(var i = 1; i <= 10 - gridRowId ; i++){
		jQuery("#"+gridName).jqGrid("addRowData", gridRowId+i, datarow);
	}
}

/**
 * callback
 */
function loadCallBack(){
	addGridRow();
}

