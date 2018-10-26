var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;
         

var datarow = {dept_nm:"",eeno:"",eeno_nm:"",vstr_nm:"",vstr_id:"",vstr_cmpy_nm:"",strt_ymd:"",strt_time:"",fnh_ymd:"",fmeal_yn:"",ntbk_eon_yn:"",pgs_st_cd:"",doc_no:"",rem_flag:""}; 

var excelCn = ['Requester Dept','Requester ID','Requester Name','Visitor Name','Visitor ID','Visitor Company','Start date','Start time','End date','Meal','Laptop','Status','Remark' ]; 

 var excelCm = [ 'dept_nm','eeno','eeno_nm','vstr_nm','vstr_id','vstr_cmpy_nm','strt_ymd','strt_time','fnh_ymd','fmeal_yn_ex','ntbk_eon_yn_ex','pgs_st_cd_d','rem_flag'];

var excelFm = ['string','string','string','string','string','string','string','string','string','string','string','string','string' ];

jQuery(document).ready(function(){ 
	   
	getCommonCode("key_pgs_st_cd:X0007:A;", "N", ""); 
	getCommonCode("cb_vsit_purp_cd:XEM01;cb_pgs_st_cd:X0007;", "Y", "init()");    
	
});

function init(){
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){ 
		
		$("#key_ops_cd").val(sess_dept_code);
		$("#key_ops_nm").val(sess_dept_name);
		
		$("#key_ops_cd").attr("readonly",true);
		
	} 
	
	$("#keyFromDate").datepicker({ dateFormat: "dd/mm/yy" });
	$("#keyToDate").datepicker({ dateFormat: "dd/mm/yy" }); 

	$("#key_pgs_st_cd").val("Z");
	
	var params = { 
	};
	 
	cn =[ 'Dept','ID','Name','Visitor Name','ID','Company','Start date','Start time','End date','Meal','Eletronix<br>Device','Status','Remark','Doc No'],

	cm = [
			{name:'dept_nm',index:'dept_nm', formatter:'string', width:115,align:'center',editable:false,sortable:false}, 
			{name:'eeno',index:'eeno', formatter:'string',width:70,align:'center',editable:false,sortable:false, hidden:true},
			{name:'eeno_nm',index:'eeno_nm', formatter:'string',width:115,align:'left',editable:false,sortable:false},
			{name:'vstr_nm',index:'vstr_nm', formatter:'string',width:115,align:'left',editable:false,sortable:false},
			{name:'vstr_id',index:'vstr_id', formatter:'string',width:70,align:'left',editable:false,sortable:false},
			{name:'vstr_cmpy_nm',index:'vstr_cmpy_nm', formatter:'string',width:115,align:'left',editable:false,sortable:false},
			{name:'strt_ymd', index:'strt_ymd', formatter:"string", width:63, align:'center',editable:false, frozen : false,sortable:false,
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
	        {name:'strt_time',index:'strt_time', formatter: "string",width:60,align:'left',editable:true,sortable:false},
	        {name:'fnh_ymd', index:'fnh_ymd', formatter:"string", width:63, align:'center',editable:false, frozen : false,sortable:false,
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
			{name:'ntbk_eon_yn',index:'ntbk_eon_yn',formatter:"checkbox", formatoptions:{disabled:true}, width:50, align:'center', edittype:'checkbox', editable:true, sortable:false, 
				editoptions:{value:'1:0'}
			},
	        {name:'pgs_st_cd',index:'pgs_st_cd',edittype:'select',formatter: "select",editable:false,width:60,align:'center',sortable:false,
				editoptions:{value:getComboValue('cb_pgs_st_cd'),dataInit: function(elem) {$(elem).width(60);}}
			},  
			{name:'rem_flag',index:'rem_flag', formatter: "string",width:64,align:'left',editable:true,sortable:false},
			{name:'doc_no',index:'doc_no', formatter:'string', width:70,align:'left' ,hidden:true,editable:false,sortable:false}
		],   
		  
		gridParam = {
			viewEdit : [{
				gridName     : "htmlTable",
				url          : "",
				colNames     : cn,
				colModel     : cm,
				height       : "100%",
				sortname     : "dept_nm",
				sortorder    : "desc",
				rowNum		 : 15,
				rownumbers   : true,
				multiselect  : false,
				cellEdit     : true,
				fnMerge      : false,
				pager		 : "htmlPager",
				paramJson    : params
			}]
		};
	commonJqGridInit(gridParam);  
	

	jQuery("#htmlTable").jqGrid('navGrid',"#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
		              {startColumnName: 'dept_nm', numberOfColumns: 3, titleText: 'Requester'},
						{startColumnName: 'vstr_nm', numberOfColumns: 3, titleText: 'Visitor'},
						{startColumnName: 'strt_ymd', numberOfColumns: 3, titleText: 'Period'}
		              ]
	});
	
	addGridRow();
	initAfterMenus();
	doSearch(); 
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
			from_ymd		: dateConversionKr(selectNum($('#keyFromDate').val())),
			to_ymd 			: dateConversionKr(selectNum($('#keyToDate').val())),
			eeno_nm 		: $('#key_eeno_nm').val(),
			dept_nm 		: $('#key_dept_nm').val(),
			vstr_nm 		: $('#key_vstr_nm').val(),
			vstr_id 		: $('#key_vstr_id').val(),
			vstr_cmpy_nm 	: $('#key_vstr_cmpy_nm').val(),
			pgs_st_cd 		: $('#key_pgs_st_cd').val()
	};
	
	alertUI(util.jsonToString(params));
	
	doCommonSearch("doSearchGridEmToListForWorker.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", "htmlTable", msgFlag);
}

/**
 * callback
 */
function loadCallBack(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow();
	
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
	setTimeout("doSearch()","60000");
} 


