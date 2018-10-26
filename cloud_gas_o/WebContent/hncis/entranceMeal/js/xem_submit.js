	var params;	
	var fnMerge;
	var comboVal;
	
	function fnSetDocumentReady(){
		//공통 콤보 조회
		getCommonCode("vsit_purp_cd:XEM01:S;", "N", "");		//목적
		getCommonCode("cb_tclg_sup_cd:XEM02;", "Y", "init();");	// 기술지원
	}

function init(){

	cn =['Name','ID','Company','StartDate','StartTime','EndDate','Contacts','Technical\nSupport','Meal','Eletronix<br>Device','Remark','Seq','Doc No.'];
	cm = [		{name:'vstr_nm',index:'vstr_nm', formatter: "string",width:130,align:'left',editable:false,sortable:false},
				{name:'vstr_id',index:'vstr_id', formatter: "string",width:70,align:'left',editable:false,sortable:false},
				{name:'vstr_cmpy_nm',index:'vstr_cmpy_nm', formatter: "string",width:100,align:'left',editable:false,sortable:false},
				{name:'strt_ymd', index:'strt_ymd', formatter:"string", width:65, align:'left', editable:false, frozen : false,sortable:false, 
					editoptions: {readonly:true,
			            dataInit: function(element) {
			     		    $(element).datepicker({
			     		    	dateFormat: 'dd/mm/yy',
			     		    	onSelect: function(dataText, inst){
			     		    	}
					    	});
			            }
			        }
				},
				{name:'strt_time',index:'strt_time', formatter: "string",width:60,align:'left',editable:false,sortable:false},
				{name:'fnh_ymd', index:'fnh_ymd', formatter:"string", width:65, align:'left', editable:false, frozen : false,sortable:false, 
					editoptions: {readonly:true,
			            dataInit: function(element) {
			     		    $(element).datepicker({
			     		    	dateFormat: 'dd/mm/yy',
			     		    	onSelect: function(dataText, inst){
			     		    	}
					    	});
			            }
			        }
				},
				{name:'ccpc_tn',index:'ccpc_tn', formatter: "string",width:90,align:'center',editable:false,sortable:false},
				{name:'tclg_sup_cd',index:'tclg_sup_cd',edittype:'select',formatter: "select",editable:false,width:80,align:'center',sortable:false,
					editoptions:{value:getComboValue('cb_tclg_sup_cd'),dataInit: function(elem) {$(elem).width(80);}}  
				}, 
				{name:'fmeal_yn',index:'fmeal_yn',formatter:"checkbox", formatoptions:{disabled:true}, width:50, align:'center', edittype:'checkbox', editable:true, sortable:false, hidden:true, 
					editoptions:{value:'1:0'}
				},
				{name:'ntbk_eon_yn',index:'ntbk_eon_yn',formatter:"checkbox", formatoptions:{disabled:true}, width:50, align:'center', edittype:'checkbox', editable:true, sortable:false, 
					editoptions:{value:'1:0'}
				},  
				{name:'rem_sbc',index:'rem_sbc', formatter: "string",width:185,align:'center',editable:false,sortable:false},
				{name:'vstr_seq',index:'vstr_seq', formatter: "string",width:40,hidden:true,align:'center',editable:false,sortable:false},
				{name:'doc_no',index:'doc_no', formatter: "string",width:90,hidden:true,align:'center',editable:false,sortable:false}
	        ];     
	datarow = {vstr_nm:"",vstr_id:"",vstr_cmpy_nm:"",strt_ymd:"",strt_time:"",fnh_ymd:"",ccpc_tn:"",tclg_sup_cd:"",fmeal_yn:"",ntbk_eon_yn:"",rem_sbc:"",vstr_seq:"",doc_no:""}; 
	
	var params = {
	};
	gridParam = {
		viewEdit : [{
			gridName     : "htmlTable",
			url          : "", 
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "vstr_nm",
			sortorder    : "desc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			paramJson    : params,
			scroll: true,  
			completeFc	 	: ""
		}]
	};
	
	commonJqGridInit(gridParam);
	
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
		              {startColumnName: 'strt_ymd', numberOfColumns: 3, titleText: 'Period'}
		              ]
	});
	addGridRow(5);
	
	doSearch();
}

//조회
function doSearch(){
	
	var keyData = {
			if_id		: $('#M_DOC_ID').val(),
			corp_cd		: sess_corp_cd
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoEmToRequestForApprove.do", paramData, "loadCallBack(jsonData.sendResult,'N');");
}

function loadCallBack(result,msgFlag){ 
	
	loadJsonSet(result);
	 
	if($("#pgs_st_cd").val() != "0" && $("#if_id").val() != ""){
		displaySubmit(document ,result.code, 0);		
	}else{
		displaySubmitClear(document);
	}
	 
	doSerchList(result.doc_no);
	
}

function doSerchList(m_doc_no){
	
	params = {
			doc_no		: m_doc_no,
			corp_cd		: sess_corp_cd
	};
	
	doCommonSearch("doSearchListEmToRequestForApprove.do",util.jsonToString(params), "loadCallBackList();", "htmlTable", "N");
}
function loadCallBackList(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow(5);
}
//조회 후 데이터 세팅
function setForm(result){

	$('#key_eeno').val(result.eeno);
	$('#key_eeno_nm').val(result.eenm);
	$('#key_pstn').val(result.step_nm);
	$('#key_dept').val(result.dept_nm);
	$('#key_ptt_ymd').val(result.ptt_ymd);
	$('#key_doc_no').val(result.doc_no);
	$('#pgs_st_cd_d').val(result.pgs_st_cd_d);
	$('#pgs_st_cd').val(result.pgs_st_cd);
	$('#ipe_eeno').val(result.ipe_eeno);
	
	$('#if_id').val(result.if_id);
	
	$('#vsit_purp_cd').val(result.vsit_purp_cd);
	$('#vsit_purp_dtl_sbc').val(result.vsit_purp_dtl_sbc);
	$('#budg_no').val(result.budg_no);
	
}

