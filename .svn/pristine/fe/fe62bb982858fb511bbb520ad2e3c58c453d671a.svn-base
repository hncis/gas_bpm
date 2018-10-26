	var params;	
	var fnMerge;
	var comboVal;
	
	function fnSetDocumentReady(){
		initMenus();
		$('#EM').slideDown('fast');  
		
		//공통 콤보 조회
		getCommonCode("vsit_purp_cd:XEM01:S;", "N", "");		//목적
		getCommonCode("cb_tclg_sup_cd:XEM02;", "Y", "init();");	// 기술지원
	}

function init(){
	
	cn =['Name','ID','Company','StartDate','StartTime','EndDate','Contacts','Technical\nSupport','Meal','Use','Device','Remark','Seq','Doc No.'];
	cm = [		{name:'vstr_nm',index:'vstr_nm', formatter: "string",width:125,align:'left',editable:true,sortable:false},
				{name:'vstr_id',index:'vstr_id', formatter: "string",width:70,align:'left',editable:true,sortable:false},
				{name:'vstr_cmpy_nm',index:'vstr_cmpy_nm', formatter: "string",width:110,align:'left',editable:true,sortable:false},
				{name:'strt_ymd', index:'strt_ymd', formatter:"string", width:70, align:'left', editable:true, frozen : false,sortable:false, 
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
				{name:'strt_time',index:'strt_time', formatter: "string",width:60,align:'left',editable:true,sortable:false,
					editoptions: { maxlength : "4", dataEvents:[
					    {type:'keyup', 
							fn:function(e){
								if(!isNumeric($(e.target).val())){
									$(e.target).val(selectNum($(e.target).val()));
								}
								if($(e.target).val().length == 4){
									$(e.target).val($(e.target).val().substring(0,2) + ":"+ $(e.target).val().substring(2));
								}
//								else if($(e.target).val().length == 3){
//									$(e.target).val($(e.target).val().substring(0,1) + ":"+ $(e.target).val().substring(1));
//								}
							}
						}]
					}
				},
				{name:'fnh_ymd', index:'fnh_ymd', formatter:"string", width:70, align:'left', editable:true, frozen : false,sortable:false, 
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
				{name:'ccpc_tn',index:'ccpc_tn', formatter: "string",width:90,align:'center',editable:true,sortable:false},
				{name:'tclg_sup_cd',index:'tclg_sup_cd',edittype:'select',formatter: "select",editable:true,width:130,align:'center',sortable:false,
					editoptions:{value:getComboValue('cb_tclg_sup_cd'),dataInit: function(elem) {$(elem).width(80);}}  
				}, 
				{name:'fmeal_yn',index:'fmeal_yn',formatter:"checkbox", formatoptions:{disabled:false}, width:50, align:'center', edittype:'checkbox', editable:true, sortable:false, hidden:true, 
					editoptions:{value:'1:0'}
				},
				{name:'ntbk_eon_yn',index:'ntbk_eon_yn',formatter:"checkbox", formatoptions:{disabled:false}, width:50, align:'center', edittype:'checkbox', editable:true, sortable:false, classes:'check',
					editoptions:{value:'1:0'}
				},
				{name:'ntbk_eon_sbc',index:'ntbk_eon_sbc', formatter: "string",width:60,align:'left',editable:true,sortable:false,
					editoptions:{ maxlength:"30"
//						dataEvents:[
//					    {type:'keyup',
//					    	fn:function(e){
//					    		var row = $(e.target).closest('tr.jqgrow');
//			                    var rowId = row.attr('id');
//			                    
//			                    $("#htmlTable").setCell(rowId,'svca_amt',null);
//					    	}
//					    }
//					]
					}
				},
				{name:'rem_sbc',index:'rem_sbc', formatter: "string",width:115,align:'left',editable:true,sortable:false},
				{name:'vstr_seq',index:'vstr_seq', formatter: "string",width:40,hidden:true,align:'center',editable:false,sortable:false},
				{name:'doc_no',index:'doc_no', formatter: "string",width:90,hidden:true,align:'center',editable:false,sortable:false} 
	        ];
	datarow = {vstr_nm:"",vstr_id:"",vstr_cmpy_nm:"",strt_ymd:"",strt_time:"",fnh_ymd:"",ccpc_tn:"",tclg_sup_cd:"",fmeal_yn:"",ntbk_eon_yn:"",ntbk_eon_sbc:"",rem_sbc:"",vstr_seq:"",doc_no:""}; 
	 
	var params = {
	};
	gridParam = {
		viewEdit : [{
			gridName     : "htmlTable",
			url          : "/doSearchToEmpty.do", 
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
			completeFc	 	: "addGridRow(5);initAfterMenus();"
		}]
	};
	
	commonJqGridInit(gridParam);
	
	jQuery("#htmlTable").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
		              {startColumnName: 'strt_ymd', numberOfColumns: 3, titleText: 'Period'},
		              {startColumnName: 'ntbk_eon_yn', numberOfColumns: 2, titleText: 'Electronix Device'}
		              ]
	});
	
	jQuery(".check input").each
	
	chk_auth();
	
	if($("#M_DOC_NO").val() == ""){
		
		setInitUserInfo();
		
	}
	else{
		
		doSearch();
	
	}
}
  
function chk_auth(){ 
	var f = document.frm;
	with(f){ 
		if($("#work_auth").val() < 5 && sess_mstu != "M"){ 
			readOnlyStyle("eeno", 1);
			readOnlyStyle("snb_rson_sbc", 1);
		}else{
			readOnlyStyle("snb_rson_sbc", 2);
			if($("#M_DOC_NO").val() != ""){
				readOnlyStyle("eeno", 1);  
			}else{
				readOnlyStyle("eeno", 2);
			}
		}
	}
}

function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
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
	   case "request" :
		    doApprove();
			break;
	   case "requestCancel" :
		    doApproveCancel();
			break;
	   case "confirmCancel" :
		    doConfirmCancel();
			break;
	   case "confirm" :
		    doConfirm();
			break;
	}
}

//조회
function doSearch(msgFlag){
	
	setFormClear();
	
	var keyData = {
			doc_no		: $('#M_DOC_NO').val()
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoEmToRequest.do", paramData, "loadCallBack(jsonData.sendResult,'"+msgFlag+"');");
}

function loadCallBack(result,msgFlag){
	loadJsonSet(result);
	
	if(msgFlag != 'N'){
		setBottomMsg(result.message, false);
	}
	
	if($("#pgs_st_cd").val() != "0" && $("#if_id").val() != ""){
		displaySubmit(document ,result.code, 0);	 
	}else{
		displaySubmitClear(document);
	}
	
	doSerchList(msgFlag);
	
}

function doSerchList(msgFlag){
	
	params = {
			doc_no		: $('#M_DOC_NO').val()
	};
	doCommonSearch("doSearchListEmToRequest.do",util.jsonToString(params), "loadCallBackList();", "htmlTable", msgFlag);
}
function loadCallBackList(){
	if(fnMerge !== ""){
		eval(fnMerge);
	}
	addGridRow(5);
	initAfterMenus();
}
//조회 후 데이터 세팅
function setForm(result){

	$('#eeno').val(result.eeno);
	$('#eeno_nm').val(result.eenm);
	$('#pstn').val(result.step_nm);
	$('#dept').val(result.dept_nm);
	$('#ptt_ymd').val(result.ptt_ymd);
	$('#doc_no').val(result.doc_no);
	$('#pgs_st_cd_d').val(result.pgs_st_cd_d);
	$('#tel_no').val(result.tel_no);
	$('#pgs_st_cd').val(result.pgs_st_cd);
	$('#ipe_eeno').val(result.ipe_eeno);
	
	$('#if_id').val(result.if_id);
	
	$('#vsit_purp_cd').val(result.vsit_purp_cd);
	$('#vsit_purp_dtl_sbc').val(result.vsit_purp_dtl_sbc);
//	$('#budg_no').val(result.budg_no);
	$('#snb_rson_sbc').val(result.snb_rson_sbc);
	$('#fmeal_cset_yn').val(result.fmeal_cset_yn);  
	
}


//입력,수정
function doInsert(){
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){
		if($("#doc_no").val() != ''){
			if(sess_empno != $("#ipe_eeno").val()){
				alertUI("You can't save.");
				return;
			};
			if($("#pgs_st_cd").val() != '0'){
				alertUI("You can't save in this status.");
				return;
			}
		} 
	}

	var lstCnt = 0;
	
	var ids = jQuery("#htmlTable").getDataIDs();
	var vstParams = [];
	var tmpNtbkEonYn = "";
	
	for(i=0; i<ids.length; i++){
		rowId = ids[i];
		if(rowId)	{
			if(getColValue("vstr_nm",rowId)!=''){
				tmpNtbkEonYn = getColValue("ntbk_eon_yn",rowId) == "1" ? getColValue("ntbk_eon_sbc",rowId) : "";
				data =
				{
						doc_no 			: $("#doc_no").val(),
						vstr_seq 		: getColValue("vstr_seq",rowId),
						vstr_nm			: getColValue("vstr_nm",rowId),
						vstr_id			: getColValue("vstr_id",rowId),
						vstr_cmpy_nm    : getColValue("vstr_cmpy_nm",rowId),
						strt_ymd		: dateConversionKr(selectNum(getColValue("strt_ymd",rowId))),
						strt_time		: getColValue("strt_time",rowId),
						fnh_ymd			: dateConversionKr(selectNum(getColValue("fnh_ymd",rowId))),
						ccpc_tn			: getColValue("ccpc_tn",rowId),
						tclg_sup_cd		: getColValue("tclg_sup_cd",rowId),
						fmeal_yn		: getColValue("fmeal_yn",rowId),
						ntbk_eon_yn		: tmpNtbkEonYn,
						rem_sbc			: getColValue("rem_sbc",rowId),
						ipe_eeno		: sess_empno,
						updr_eeno		: sess_empno
				};
				vstParams.push(data);
				lstCnt++;
			}

		} else { alertUI("Please enter visitors data.");}
	}
	
	var keyData = {
			doc_no      		: $("#doc_no").val(),
			eeno      			: $("#eeno").val(),
			ptt_ymd      		: dateConversionKr(selectNum($("#ptt_ymd").val())),
			vsit_purp_cd      	: $("#vsit_purp_cd").val(),
			vsit_purp_dtl_sbc   : changeToUni($("#vsit_purp_dtl_sbc").val()),
//			budg_no      		: changeToUni($("#budg_no").val()),
			snb_rson_sbc     	: changeToUni($("#snb_rson_sbc").val()),
			pgs_st_cd			: '0',
			dept_cd				: sess_dept_code,
			ipe_eeno			: sess_empno,
			updr_eeno			: sess_empno
			
	};
	
	if(lstCnt == 0){
		alertUI("Please enter visitors data.");
		return;
	}
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData =  {
						paramJsonList 			: util.jsonToList(vstParams),
						paramJsonStr 			: util.jsonToString(keyData) 
					};
					doCommonAjax("doInsertEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);insertCallBack(jsonData.sendResult.code);");
			}
		});
	});
}
function insertCallBack(doc_no){
	$("#M_DOC_NO").val(doc_no);
	doSearch('N');
	
}
//삭제
function doDelete(){
	if($("#work_auth").val() < 5 && sess_mstu != "M"){
		if(sess_empno != $("#ipe_eeno").val()){
			alertUI("You can't delete.");
			return;
		};
	}
	if($("#pgs_st_cd").val() != '0'){
		alertUI("삭제할 수 없는 상태입니다..");
		return;
	}
	var keyData = {
			doc_no		 : $("#doc_no").val()
	};
	confirmUI("삭제 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
				};
				doCommonAjax("doDeleteEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearchFormAfterDel('N');"); 
			}
		});
	});
}
function doSearchFormAfterDel(msgFlag){
	
	setFormClear(); 
	var keyData = {
			doc_no		: $('#M_DOC_NO').val()
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("doSearchInfoEmToRequest.do", paramData, "loadCallBackFormAfterDel(jsonData.sendResult,'"+msgFlag+"');");
}
/**
 * callback
 */
function loadCallBackFormAfterDel(result,msgFlag){

	setInitUserInfo();
	
}

function doApprove(){
	if($("#ipe_eeno").val() != sess_empno){
		alertUI("You can't request.");
		return;
	}
	if($("#pgs_st_cd").val() != '0'){
		alertUI("You can't request in this status.");
		return;
	}
	
	var ids = jQuery("#htmlTable").getDataIDs();
	var vstParams = [];
	
	var appYn = 'N';
	for(i=0; i<ids.length; i++){
		rowId = ids[i];
		if(rowId)	{
			if(getColValue("fmeal_yn",rowId) == '1'|| getColValue("ntbk_eon_yn",rowId) == '1'){
				appYn = 'Y';
				break;
			}
		}
	}
	 
	var keyData = { 
			doc_no			 : $("#doc_no").val(),
			pgs_st_cd		 : 'A',
			updr_eeno 		 : sess_empno,
			app_yn			 : appYn
	};
	confirmUI("신청 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
				};
				doCommonAjax("doApproveEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}
function doApproveCancel(){
	
	//작성 중인 사람이 아닌 경우에는 cancel할 수 없음.
	if($("#ipe_eeno").val() != sess_empno){
		alertUI("You can't cancel the request.");
		return;
	}
	if($("#pgs_st_cd").val() != 'A'){
		alertUI("You can't cancel the request in this status.");
		return;
	}
	var keyData = {
			pgs_st_cd		 : '0',
			updr_eeno		 : sess_empno,
			if_id			 : $("#if_id").val()
	}; 
	confirmUI("Do you want to cancel the request?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
				};
				doCommonAjax("doApproveCancelEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}

function doConfirm(){
	if($("#pgs_st_cd").val() != 'Z'){
		alertUI("You can't confirm.");
		return;
	}

	var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
	var vstParams = [];
	
	for(i=0; i<selectRow.length; i++){
		rowId = selectRow[i];
		if(rowId)	{
			if(getColValue("vstr_nm",rowId) == ''){
				alertUI(rowId + " Line : Please enter name");
				return;
			}
			data =
			{
					doc_no 			: $("#doc_no").val(),
					vstr_seq 		: getColValue("vstr_seq",rowId),
					vstr_nm			: getColValue("vstr_nm",rowId),
					vstr_id			: getColValue("vstr_id",rowId),
					strt_ymd		: dateConversionKr(selectNum(getColValue("strt_ymd",rowId))),
					strt_time		: getColValue("strt_time",rowId),
					fnh_ymd			: dateConversionKr(selectNum(getColValue("fnh_ymd",rowId))),
					ccpc_tn			: getColValue("ccpc_tn",rowId),
					tclg_sup_cd		: getColValue("tclg_sup_cd",rowId),
					fmeal_yn		: getColValue("fmeal_yn",rowId),
					rem_sbc			: getColValue("rem_sbc",rowId),
					ipe_eeno		: sess_empno,
					updr_eeno		: sess_empno
			};
			vstParams.push(data);

		} else { alertUI("데이터를 선택하세요.");}
	}
	
	var keyData = {
			doc_no      		: $("#doc_no").val(),
			pgs_st_cd		 	: '3',
			acpc_eeno		 	: sess_empno,
			vsit_purp_cd      	: $("#vsit_purp_cd").val(),
			vsit_purp_dtl_sbc   : $("#vsit_purp_dtl_sbc").val(),
//			budg_no      		: $("#budg_no").val(),
			fmeal_cset_yn     	: $("#fmeal_cset_yn").val()
	};
	
	confirmUI("확정 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJsonList 			: util.jsonToList(vstParams),
						paramJsonStr 			: util.jsonToString(keyData) 
					};
					doCommonAjax("doConfirmEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}

function doConfirmCancel(){
	if($("#pgs_st_cd").val() != 'Z'){
		alertUI("You can't cancel the confirm in this status.");
		return;
	}
	
	if($("#snb_rson_sbc").val() == ""){
		alertUI("Please enter the reason for cancelling confirm.");
		$("#snb_rson_sbc").focus();
		return;
	}
	
	var keyData = {
			doc_no			 : $("#doc_no").val(),
			pgs_st_cd		 : '0',
			snb_rson_sbc  	 : $("#snb_rson_sbc").val(),
			updr_eeno		 : sess_empno
	};
	confirmUI("Do you want to cancel confirm?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(keyData)
				};
				doCommonAjax("doConfirmCancelEmToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}






//유저정보 세팅
function setInitUserInfo(){
	
	$("#eeno").val(sess_empno);
	$("#eeno_nm").val(sess_name);
	$("#pos_nm").val(sess_step_name);
	$("#dept_nm").val(sess_dept_name);
	$("#ptt_ymd").val(getCurrentToDateAddDayEn("/",0));
	$("#tel_no").val(sess_tel_no);
}

//방문자 로우 추가
function gridRowAdd(){
	
	var gridRowId = jQuery("#htmlTable").getDataIDs().length;
	jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
	
	initAfterMenus();
}

//방문자 정보 삭제
function gridRowDelete(){
	
	if($("#work_auth").val() < 5 && sess_mstu != "M"){
		if(sess_empno != $("#ipe_eeno").val()){
			alertUI("You can't delete.");
			return;
		};
	}
	if($("#pgs_st_cd").val() != '0'){
		alertUI("삭제할 수 없는 상태입니다..");
		return;
	}
	
	var rowId = jQuery("#htmlTable").jqGrid("getGridParam", "selrow");
	
	if(rowId == "" || rowId == null){
		alertUI("Please select delete visitor.");
		return;
	}else if(getColValue("doc_no", rowId) == "" ){
		alertUI("삭제할 데이터가 없습니다.");
		return;
	}
	
	var vstrInfo = {
			doc_no    		: getColValue("doc_no",     rowId, "htmlTable"),
			vstr_seq 		: getColValue("vstr_seq", rowId, "htmlTable")
	};
	
	confirmUI("Do you want to Delete visitor?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(vstrInfo)
				};
				doCommonAjax("doDeleteVisitorsRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
			}
		});
	});
}
// 데이터 초기화
function setFormClear(){  
	
	$("#pgs_st_cd").val("");
	$("#ipe_eeno").val("");
	$("#eeno").val("");
	$("#eeno_nm").val("");
	$("#pos_nm").val("");
	$("#dept_nm").val("");
	$("#ptt_ymd").val("");
	$("#doc_no").val("");
	$("#pgs_st_cd_d").val("");
	$("#vsit_purp_cd").val("");
	$("#vsit_purp_dtl_sbc").val("");
//	$("#budg_no").val("");
	$("#snb_rson_sbc").val("");
	$("#fmeal_cset_yn").val("");
	$("#tel_no").val("");
	
	$("#if_id").val("");
	  
	$("#htmlTable").jqGrid("clearGridData", true); 
	addGridRow(5); 
	
}

function cearInsa(){
	if($("#eeno").val() == ""){
		$("#eeno").val("");
		$("#eeno_nm").val("");
		$("#pos_nm").val("");
		$("#dept_nm").val("");
		$("#tel_no").val("");
	}
}

function setInsaInfo(){ 
	if($("#eeno").val() != ""){
		var keyData = { xusr_empno : $("#eeno").val() };
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
	$("#eeno").val(result.xusr_empno);
	$("#eeno_nm").val(result.xusr_name);
	$("#pos_nm").val(result.xusr_step_name);
	$("#dept_nm").val(result.xusr_dept_name); 
	$("#tel_no").val(result.xusr_tel_no); 
}

//function cboxFormatter(cellvalue, options, rowObject){
//	return '<input type="checkbox"' + (cellvalue ? ' checked="checked"' : '') + 'onclick="fnNtbkEonEditable(' + options.rowId + ', this.checked)"/>';
//}
//
//function fnNtbkEonEditable(rowId, chkFlag){
//	$("#htmlTable").setCell(rowId,'ntbk_eon_sbc',null);
//}