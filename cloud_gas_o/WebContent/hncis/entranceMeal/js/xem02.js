var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;
 

var datarow = {dept_nm:"",eeno:"",eeno_nm:"",vstr_nm:"",vstr_id:"",vstr_cmpy_nm:"",strt_ymd:"",strt_time:"",fnh_ymd:"",fmeal_yn:"",ntbk_eon_yn:"",ntbk_eon_sbc:"",pgs_st_cd:"",doc_no:""}; 

var excelCn = ['Requester Dept','Requester ID','Requester Name','Visitor Name','Visitor ID','Visitor Company','Start date','Start time','End date','Electronix Use','Electronix Device','Status' ]; 

var excelCm = [ 'dept_nm','eeno','eeno_nm','vstr_nm','vstr_id','vstr_cmpy_nm','strt_ymd','strt_time','fnh_ymd','ntbk_eon_yn_ex','ntbk_eon_sbc','pgs_st_cd_d'];

var excelFm = ['string','string','string','string','string','string','string','string','string','string','string','string' ];

function fnSetDocumentReady(){
	initMenus();
//	$('#EM').slideDown('fast');
	
	getCommonCode("key_pgs_st_cd:X0007:A;", "N", ""); 
	getCommonCode("cb_vsit_purp_cd:XEM01;cb_pgs_st_cd:X0007;", "Y", "init()");    
}

function init(){
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){ 
		
		$("#key_ops_cd").val(sess_dept_code);
		$("#key_ops_nm").val(sess_dept_name);
		
		$("#key_ops_cd").attr("readonly",true);;
		$("#deptBtn").attr("disabled",true);
		
	} 
	
	$("#keyFromDate").datepicker({ dateFormat: "dd/mm/yy" });
	$("#keyToDate").datepicker({ dateFormat: "dd/mm/yy" }); 
	
	var params = {
	};
	  
	cn =[ 'Dept','ID','Name','Visitor','ID','Company','Start date','Start time','End date','Meal','Use','Device','Status','Doc No'],
    
	cm = [ 
			{name:'dept_nm',index:'dept_nm', formatter:'string', width:118,align:'center',editable:false,sortable:false}, 
			{name:'eeno',index:'eeno', formatter:'string',width:60,align:'center',editable:false,sortable:false, hidden:true},
			{name:'eeno_nm',index:'eeno_nm', formatter:'string',width:118,align:'left',editable:false,sortable:false},
			{name:'vstr_nm',index:'vstr_nm', formatter:'string',width:118,align:'left',editable:false,sortable:false},
			{name:'vstr_id',index:'vstr_id', formatter:'string',width:90,align:'left',editable:false,sortable:false},
			{name:'vstr_cmpy_nm',index:'vstr_cmpy_nm', formatter:'string',width:100,align:'left',editable:false,sortable:false},
			{name:'strt_ymd', index:'strt_ymd', formatter:"string", width:75, align:'center',editable:false, frozen : false,sortable:false,
				editoptions: {
					readonly : true,
	            dataInit: function(element) { 
	     		    $(element).datepicker({
	     		    	dateFormat: 'dd/mm/yy',
	     		    	onSelect: function(dataText, inst){
	     		    	}
			    	});
	            }
	        }},
	        {name:'strt_time',index:'strt_time', formatter: "string",width:70,align:'left',editable:false,sortable:false},
	        {name:'fnh_ymd', index:'fnh_ymd', formatter:"string", width:75, align:'center',editable:false, frozen : false,sortable:false,
				editoptions: {
					readonly : true,
	            dataInit: function(element) {
	     		    $(element).datepicker({
	     		    	dateFormat: 'dd/mm/yy',
	     		    	onSelect: function(dataText, inst){
	     		    	}
			    	});
	            }
	        }},
	        {name:'fmeal_yn',index:'fmeal_yn',formatter:"checkbox", formatoptions:{disabled:true}, width:42, align:'center', edittype:'checkbox', editable:true, sortable:false, hidden:true, 
				editoptions:{value:'1:0'}
			},
			{name:'ntbk_eon_yn',index:'ntbk_eon_yn',formatter:"checkbox", formatoptions:{disabled:true}, width:51, align:'center', edittype:'checkbox', editable:true, sortable:false, 
				editoptions:{value:'1:0'}
			},
			{name:'ntbk_eon_sbc',index:'ntbk_eon_sbc',formatter:"string", formatoptions:{disabled:true}, width:70, align:'center', editable:false, sortable:false, 
				editoptions:{maxlength:"30"}
			},
	        {name:'pgs_st_cd',index:'pgs_st_cd',edittype:'select',formatter: "select",editable:false,width:65,align:'center',sortable:false,
				editoptions:{value:getComboValue('cb_pgs_st_cd'),dataInit: function(elem) {$(elem).width(65);}} 
			},  
			{name:'doc_no',index:'doc_no', formatter:'string', width:70,align:'left' ,hidden:true,editable:false,sortable:false}
		], 
	       
		gridParam = {
			viewEdit : [{
				gridName     : "htmlTable",
				url          : "/doSearchToEmpty.do",
				colNames     : cn,
				colModel     : cm,
				height       : "100%",
				sortname     : "dept_nm",
				sortorder    : "desc",
				rowNum		 : 15,
				rownumbers   : true,
				multiselect  : false,
				cellEdit     : false,
				fnMerge      : false,
				paramJson    : params,
				pager		 : "htmlPager",
				completeFc	 : "addGridRow();"
			}]
		};
	commonJqGridInit(gridParam);
	

	jQuery("#htmlTable").jqGrid('navGrid',"#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
						{startColumnName: 'dept_nm', numberOfColumns: 3, titleText: 'Requester'},
						{startColumnName: 'vstr_nm', numberOfColumns: 3, titleText: 'Visitor'},
						{startColumnName: 'strt_ymd', numberOfColumns: 3, titleText: 'Period'},
						{startColumnName: 'ntbk_eon_yn', numberOfColumns: 2, titleText: 'Electronix Device'}
		              ]
	}); 

	jQuery("#htmlTable").jqGrid("setGridParam",{
		ondblClickRow : function(rowid, iRow, iCol, e){ 
			var docNo = getColValue("doc_no", iRow, "htmlTable");
			if(docNo != ""){
				var hfrm = document.hideForm;
				$("#M_DOC_NO").val(docNo);
				$("#hid_csrfToken").val($("#csrfToken").val());
				hfrm.method = "post";
				hfrm.action = "xem01.gas";
				hfrm.submit();
			}
		}
	}).trigger('reloadGrid');
}
function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
	   case "search" :
		    doSearch();
			break;
	   case "excel" :
		    doExcel(); 
			break;

	}
}

function doSearch(msgFlag){
	
	params = {
			from_ymd	: dateConversionKr(selectNum($('#keyFromDate').val())),
			to_ymd 		: dateConversionKr(selectNum($('#keyToDate').val())),
			eeno 		: $('#key_eeno').val(),
			dept_cd 	: $('#key_ops_cd').val(),
			vstr_nm 	: $('#key_vstr_nm').val(),
			pgs_st_cd 	: $('#key_pgs_st_cd').val()
	};
	  
	doCommonSearch("doSearchGridEmToList.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", "htmlTable", msgFlag);
}

/**
 * callback
 */
function loadCallBack(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow();
	
 
	
//	$("#htmlTable").tuiTableRowSpan("4");
//	$("#htmlTable").tuiTableRowSpan("3");
//	$("#htmlTable").tuiTableRowSpan("2");
     	 
	
	var ids = jQuery("#htmlTable").getDataIDs();
	
	var deptCd = "";
	var empNo = "";
	var docNo = "";
	var colNm = "#7e9db9";
	var falg = "N";
	
	for(i=0; i<ids.length; i++){
		rowId = ids[i];
		if(rowId)	{
			if(i == 0){
				deptCd = getColValue("dept_nm", rowId);
				empNo = getColValue("eeno", rowId);
				docNo = getColValue("doc_no", rowId);
				if(getColValue("doc_no", rowId) != ''){ 
					jQuery("#htmlTable").setCell (rowId,"vstr_nm",'',{background:'#eeeeee'});
					jQuery("#htmlTable").setCell (rowId,"strt_ymd",'',{background:'#eeeeee'});
					jQuery("#htmlTable").setCell (rowId,"fnh_ymd",'',{background:'#eeeeee'});
					jQuery("#htmlTable").setCell (rowId,"fmeal_yn",'',{background:'#eeeeee'});
					jQuery("#htmlTable").setCell (rowId,"ntbk_eon_yn",'',{background:'#eeeeee'});
					jQuery("#htmlTable").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
				}
			} 
			else{
				if(deptCd==getColValue("dept_nm", rowId)){
					$("#htmlTable").setCell(rowId,'dept_nm',null);
				}
				else{
					deptCd = getColValue("dept_nm", rowId);
				}
				if(empNo==getColValue("eeno", rowId)){
					$("#htmlTable").setCell(rowId,'eeno',null);
					$("#htmlTable").setCell(rowId,'eeno_nm',null); 
				}
				else{
					empNo = getColValue("eeno", rowId);
				}
				if(getColValue("doc_no", rowId) != ''){
					if(docNo==getColValue("doc_no", rowId)){
						if(falg == "Y"){
							jQuery("#htmlTable").setCell (rowId,"vstr_nm",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"strt_ymd",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"fnh_ymd",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"fmeal_yn",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"ntbk_eon_yn",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"pgs_st_cd",'',{background:'#D5D5D5'});
						}
						else{ 
							jQuery("#htmlTable").setCell (rowId,"vstr_nm",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"strt_ymd",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"fnh_ymd",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"fmeal_yn",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"ntbk_eon_yn",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
						}
					}
					else{ 
						if(falg == "N"){
							jQuery("#htmlTable").setCell (rowId,"vstr_nm",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"strt_ymd",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"fnh_ymd",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"fmeal_yn",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"ntbk_eon_yn",'',{background:'#D5D5D5'});
							jQuery("#htmlTable").setCell (rowId,"pgs_st_cd",'',{background:'#D5D5D5'});
							docNo = getColValue("doc_no", rowId);
							falg = "Y";
						}
						else{
							jQuery("#htmlTable").setCell (rowId,"vstr_nm",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"strt_ymd",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"fnh_ymd",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"fmeal_yn",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"ntbk_eon_yn",'',{background:'#eeeeee'});
							jQuery("#htmlTable").setCell (rowId,"pgs_st_cd",'',{background:'#eeeeee'});
							docNo = getColValue("doc_no", rowId);
							falg = "N"; 
						}
					} 
				}
			}
		}
	}
} 
function doExcel(){

	keyData = {
			from_ymd	: dateConversionKr(selectNum($('#keyFromDate').val())),
			to_ymd 		: dateConversionKr(selectNum($('#keyToDate').val())),
			eeno 		: $('#key_eeno').val(),
			dept_cd 	: $('#key_ops_cd').val(),
			vstr_nm 	: $('#key_vstr_nm').val(),
			pgs_st_cd 	: $('#key_pgs_st_cd').val()
	};

	var params = [
	      		{name : 'fileName',		value : "EntranceMeal"},
	      		{name : 'header',		value : util.jsonToArray(excelCn)},
	      		{name : 'headerName',	value : util.jsonToArray(excelCm)},
	      		{name : 'fomatter',		value : util.jsonToArray(excelFm)},
	      		{name : 'paramJson',	value : util.jsonToString(keyData)},
	      		{name : 'firstUseYn',	value : "Y"}
	];
	
	gridToExcel("#htmlTable", "doExcelToList.excel", params);

}//end method

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
	$("#key_eeno_nm").val(result.xusr_name);
}

function cearInsa(){
	if($("#key_eeno").val().length != 8){
		$("#key_eeno_nm").val("");
	}
}
function deptFind(){
	var param = "?dcd=key_ops_cd&dcdNm=key_ops_nm&hid_csrfToken="+$("#csrfToken").val();
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