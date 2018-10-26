var params; 
var comboVal;
var comboVal1;
var lastsel;
var fnMerge;
var gridParam;


var datarow = {eenm:"",eeno:"",dept_nm:"",ptt_ymd:"",meal_req_cnt:"",vsit_purp_cd:"",budg_no:"",pgs_st_cd:"",doc_no:""}; 

function fnSetDocumentReady(){
	initMenus();
	$('#EM').slideDown('fast');
	
	getCommonCode("key_pgs_st_cd:X0007:A;", "N", "");  
	getCommonCode("cb_vsit_purp_cd:XEM01;cb_pgs_st_cd:X0007;", "Y", "init()");    
}

function init(){
	
	$("#keyFromDate").datepicker({ dateFormat: "dd/mm/yy" });
	$("#keyToDate").datepicker({ dateFormat: "dd/mm/yy" }); 
	
	$("#key_pgs_st_cd").val('Z'); 
	
	var params = {
	};
	
	cn =[ 'Name','ID','Dept','Apply Date','Number of \n meal request','Purpose','Budget \n Code','Status','Doc No'],
	
	cm = [
			{name:'eenm',index:'eenm', formatter:'string', width:140,align:'left',editable:false,sortable:false}, 
			{name:'eeno',index:'eeno', formatter:'string',width:85,align:'center',editable:false,sortable:false},
			{name:'dept_nm',index:'dept_nm', formatter:'string',width:160,align:'left',editable:false,sortable:false},
			{name:'ptt_ymd', index:'ptt_ymd', formatter:"string", width:90, align:'center',editable:false, frozen : false,sortable:false,
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
	        {name:'meal_req_cnt',index:'meal_req_cnt', formatter:'string',width:150,align:'center',editable:false,sortable:false},
			{name:'vsit_purp_cd',index:'vsit_purp_cd',edittype:'select',formatter: "select",editable:false,width:210,align:'center',sortable:false,
				editoptions:{value:getComboValue('cb_vsit_purp_cd'),dataInit: function(elem) {$(elem).width(210);}}
			}, 
			{name:'budg_no',index:'budg_no', formatter:'string',width:120,align:'center',editable:false,sortable:false, hidden:true},
			{name:'pgs_st_cd',index:'pgs_st_cd',edittype:'select',formatter: "select",editable:false,width:110,align:'center',sortable:false,
				editoptions:{value:getComboValue('cb_pgs_st_cd'),dataInit: function(elem) {$(elem).width(110);}} 
			},  
			{name:'doc_no',index:'doc_no', formatter:'string', width:0,align:'left' ,hidden:true,editable:false,sortable:false}
		], 
	 
		gridParam = {
			viewEdit : [{
				gridName     : "htmlTable",
				url          : "/doSearchToEmpty.do",
				colNames     : cn,
				colModel     : cm,
				height       : "100%",
				sortname     : "eenm",
				sortorder    : "desc",
				rowNum		 : 10,
				rownumbers   : true,
				multiselect  : true,
				cellEdit     : true,
				fnMerge      : false,
				paramJson    : params,
				pager		 : "htmlPager",
				completeFc	 : "addGridRow();initAfterMenus();"
			}]
		};
	commonJqGridInit(gridParam);
	

	jQuery("#htmlTable").jqGrid('navGrid',"#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
	
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

	}
}

function doSearch(msgFlag){
	params = {
			from_ymd	: dateConversionKr(selectNum($('#keyFromDate').val())),
			to_ymd 		: dateConversionKr(selectNum($('#keyToDate').val())),
			eeno 		: $('#key_eeno').val(),
			dept_cd 	: $('#key_ops_cd').val(),
			pgs_st_cd 	: $('#key_pgs_st_cd').val()
	};
	
	doCommonSearch("doSearchGridEmToListForEntrance.do", util.jsonToString(params), "loadCallBack();", "htmlTable", msgFlag);
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