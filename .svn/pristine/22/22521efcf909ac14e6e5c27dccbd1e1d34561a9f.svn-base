function fnSetDocumentReady(){
	initMenus();
	$('#PS').slideDown('fast');
	
	getCommonCode("key_place_knd:XPS03:A;", "N", "getCommonCode('cb_stap_cd:XPS03;cb_arvp_cd:XPS03;', 'Y', 'init();');");  
}

/**
 * process init() loading
 */
var params;
var fnMerge;
var gridParam;
var datarow  = {seq:"", car_type_cd:"", stap_cd:"", arvp_cd:"", svca_amt:"", use_yn:"", rem_sbc:""}; 
function init(){
     
	//set grid parameter
	var params = {
	}; 
	gridParam = {
		viewEdit : [{
			gridName     : "htmlTable", 
			url          : "",
			colNames     : ["", "", "From", "To", "Amount", "Use Y/N", "Remark"],
			colModel     : [{name:"seq",			index:"seq"				, sortable:false,		formatter:"string",	width:"0",		align:"center",	editable:false,	frozen:false, hidden : true},
			                {name:"car_type_cd",index:"car_type_cd"	, sortable:false,		formatter:"string",	width:"110",		align:"center",	editable:false,	frozen:false, hidden : true}, 
			            	{name:'stap_cd',index:'stap_cd',edittype:'select',formatter: "select",editable:true,width:150,align:'left',sortable:false,
								editoptions:{value:getComboValue('cb_stap_cd'),dataInit: function(elem) {$(elem).width(150);}}  
							},
							{name:'arvp_cd',index:'arvp_cd',edittype:'select',formatter: "select",editable:true,width:150,align:'left',sortable:false,
								editoptions:{value:getComboValue('cb_arvp_cd'),dataInit: function(elem) {$(elem).width(150);}}  
							},
			            	//{name:"svca_amt", index:"svca_amt", sortable:false,	formatter:"string",	width:75, align:"right", editable:true, frozen : false}, 
			            	{name:"svca_amt",	index:"svca_amt"	, sortable:false,formatter: numFormat, formatoptions: {
			    			    decimalSeparator:",",
			    			    thousandsSeparator:".",
			    			    decimalPlaces:2,
			    			    defaultValue:"",
			    			    suffix:" R$"
			    			  },editoptions: {maxlength:"10", 
			    		            dataInit: function(element) {
			    		     		    $(element).keyup(function(){
			    		     		    	if(!isNumeric(element.value)){
			    		     		    		element.value = selectNum(element.value, ",");
			    		     		    	}
			    		     		    });
			    		            }
			    		        },		width:95,	align:"right",	editable:true,	frozen : false},
			            	{name:'use_yn',index:'use_yn',edittype:'select',formatter: "select",editable:true,width:100,align:'center',sortable:false,
								editoptions:{value:"Y:Y;N:N",dataInit: function(elem) {$(elem).width(100);}}  
							},
			            	{name:"rem_sbc",			index:"rem_sbc"		, sortable:false,		formatter:"string",	width:"460",  	align:"center",	editable:true,	frozen:false}
			            	], 
			height       : "100%",
			rownumbers   : true,
			multiselect  : true,
			cellEdit     : true,
			fnMerge      : false,
			pager 		 : "htmlPager",
			completeFc   : "addGridRow(20);initAfterMenus();", 
			paramJson    : params
		}]
	};
	    
	//common jqGrid call...
	commonJqGridInit(gridParam); 
	
	
	doSearch();
} 

function retrieve(gubn){
	switch(gubn){
		case "search" :
			doSearch();
			break;
		case "save" :
			doInsert();
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
    
function doSearch(msgFlag){
	
	params = {
		stap_cd : $('#key_place_knd').val(),
		corp_cd	: sess_corp_cd
	};
	doCommonSearch("doSearchGridTxToPlaceManagement.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", "htmlTable", msgFlag);
}
/**
 * callback
 */
function loadCallBack(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow();
}


function doInsert(){
	var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
	var tempStr = [];
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	for(i=0; i<selectRow.length; i++){
		rowId = selectRow[i];
		if(rowId)	{
			if(getColValue("stap_cd",rowId) == ''){
				alertUI(rowId + " Line : Please enter From");
				return;
			}
			if(getColValue("arvp_cd",rowId) == ''){
				alertUI(rowId + " Line : Please enter To");
				return;
			}
			if(getColValue("svca_amt",rowId) == ''){
				alertUI(rowId + " Line : Please enter Amount");
				return;
			}
			if(getColValue("use_yn",rowId) == ''){
				alertUI(rowId + " Line : Please enter Use Y/N"); 
				return;
			}
			data =
			{
					seq					: getColValue("seq",rowId),
					car_type_cd			: $('#key_code_knd').val(),
					stap_cd				: getColValue("stap_cd",rowId),
					arvp_cd				: getColValue("arvp_cd",rowId),
					svca_amt			: getColValue("svca_amt",rowId).replace(",", "."),
					use_yn				: getColValue("use_yn",rowId),
					rem_sbc				: getColValue("rem_sbc",rowId),
					ipe_eeno			: sess_empno,
					updr_eeno			: sess_empno,
					corp_cd				: sess_corp_cd
					
			};
			tempStr.push(data);

		} else { alertUI("데이터를 선택하세요.");}
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(tempStr)
					};
					doCommonAjax("doInsertTxToPlaceManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}	

function doDelete(){
	var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
	var tempStr = [];
	if(selectRow.length == 0){
		alertUI("데이터를 선택하세요.");
		return;
	}
	
	for(i=0; i<selectRow.length; i++){
		rowId = selectRow[i];
		if(rowId)	{
			if(getColValue("seq",rowId) == ""){
				alertUI(rowId + " line : There is no data.");
				return;
			}
			data =
			{
					seq					: getColValue("seq",rowId),
					corp_cd		: sess_corp_cd
			};
			tempStr.push(data);;

		} else { alertUI("데이터를 선택하세요.");}
	}
	
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToList(tempStr)
					};
					doCommonAjax("doDeleteTxToPlaceManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}

function fnAddRow(){
	var gridRowId = $("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
}