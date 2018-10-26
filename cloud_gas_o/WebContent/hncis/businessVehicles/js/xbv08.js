var lastsel;
var fnMerge;

function fnSetDocumentReady(){
	initMenus();
	$("#key_from_ymd").datepicker({ dateFormat: "dd/mm/yy" });
	$("#key_to_ymd").datepicker({ dateFormat: "dd/mm/yy" });
	
	$(".inputOnlyNumber").numeric();
	
	getWorkAuth();
}

function getWorkAuth(){
	var paramData = {};
	doCommonAjax("/hncis/generalService/doSearchByXgs03Manager.do", paramData, "workAuthCallBack(jsonData.sendResult);");
}

function workAuthCallBack(rs){
	sess_auth = $("#work_auth").val();
	if(sess_auth != 5 && sess_mstu != "M" && rs.code != "Y"){
		$("#key_eeno").val(sess_empno);
//		$("#key_eenm").val(sess_name);
		
//		readOnlyStyle("key_eeno", 1);
		readOnlyStyle("key_eenm", 1);
	}else{
		readOnlyStyle("key_eeno", 2);
		readOnlyStyle("key_eenm", 2);
	}
	
	setInsaInfo();
	setComboInfo();
}

function setInsaInfo(){
	if($("#key_eeno").val() != ""){
		var keyData = { 
				xusr_empno : $("#key_eeno").val(),
				corp_cd		: sess_corp_cd
		};
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}else{
		$("#key_eenm").val("");
	}
}

function insaCallBack(result){
	setBottomMsg(result.message, false);
	$("#key_eenm").val(result.xusr_name);
}

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var paramValue  = "key_pgs_st_cd:XGS01:Y;key_regn_cd:X0004:A;";
	getCommonCode(paramValue, "N", "setCondition();", url);
}

function setCondition(){
	$("#key_regn_cd").val(sess_plac_work);
	
	if($("#hid_cond").val() != ""){
		var hidCond = $("#hid_cond").val().split("|");
		if(hidCond[0] != "") $("#key_eeno").val(hidCond[0]);
		if(hidCond[1] != "") $("#key_eenm").val(hidCond[1]);
		if(hidCond[2] != "") $("#key_from_ymd").val(hidCond[2]);
		if(hidCond[3] != "") $("#key_to_ymd").val(hidCond[3]);
		if(hidCond[4] != "") $("#key_pgs_st_cd").val(hidCond[4]);
		if(hidCond[5] != "") $("#key_regn_cd").val(hidCond[5]);
		if(hidCond[6] != "") $("#hid_page").val(hidCond[6]);
	}
	init();
}

var gridParam;
var gridName = "htmlTable";
var datarow = {doc_no:"", seq_no:"", eeno:"", ptt_ymd:"", pgs_st_nm:"", pgs_st_cd:"", eenm:"", pos_nm:"", dept_nm:"", region_nm:"", prod_nm:"", seqc_nm1:"", seqc_nm2:"", seqc_nm3:"", seqc_nm4:"", qty:"", hid_rcnt:"", hid_rnum:"", hid_pgs_cnt:"", hid_pgs_rnum:"", comment:"", pop_img:"",expt_ymd:"", expt_time:"",mileage:"", car_no:"", car_vehicle:"", car_type:"", car_en_nm:""};
function init(){
	var cn = ["", "", "", "Apply Date", "Status", "", "Name", "Position", "Dept.", "Region", "Produto", "Location", "Service Type", "Specification", "Plate No.", "Vehicle", "Engine", "Type", "Mileage", "Description", "Qty", "Expected<br>Date", "Expected<br>Time","", "", "", "", "File Attach", "Comment"];
	var cm = [{name:"doc_no", index:"doc_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"seq_no", index:"seq_no", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"eeno", index:"eeno", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
			  {name:"ptt_ymd", index:"ptt_ymd", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false,
	        	  cellattr:function(rowId, tv, rowObject, cm, rdata){
	        		  if(rowObject.hid_rnum == ""){
	        			  return "";
	        		  }
	        		  if(rowObject.hid_rnum == "1"){
	        			  return 'rowspan='+rowObject.hid_rcnt;
	        		  }else{
	        			  return 'style="display:none"';
	        		  }
	        	  }
			  },
	          {name:"pgs_st_nm", index:"pgs_st_nm", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false,
	        	  cellattr:function(rowId, tv, rowObject, cm, rdata){
	        		  if(rowObject.hid_pgs_rnum == ""){
	        			  return "";
	        		  }
	        		  if(rowObject.hid_pgs_rnum == "1"){
	        			  return 'rowspan='+rowObject.hid_pgs_cnt;
	        		  }else{
	        			  return 'style="display:none"';
	        		  }
	        	  }
	          },
	          {name:"pgs_st_cd", index:"pgs_st_cd", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"eenm", index:"eenm", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false,
				  cellattr:function(rowId, tv, rowObject, cm, rdata){
					  if(rowObject.hid_rnum == ""){
	        			  return "";
	        		  }
	        		  if(rowObject.hid_rnum == "1"){
	        			  return 'rowspan='+rowObject.hid_rcnt;
	        		  }else{
	        			  return 'style="display:none"';
	        		  }
	        	  }
	          },
	          {name:"pos_nm", index:"pos_nm", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false,
	        	  cellattr:function(rowId, tv, rowObject, cm, rdata){
	        		  if(rowObject.hid_rnum == ""){
	        			  return "";
	        		  }
	        		  if(rowObject.hid_rnum == "1"){
	        			  return 'rowspan='+rowObject.hid_rcnt;
	        		  }else{
	        			  return 'style="display:none"';
	        		  }
	        	  } 
	          },
	          {name:"dept_nm", index:"dept_nm", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false,
	        	  cellattr:function(rowId, tv, rowObject, cm, rdata){
	        		  if(rowObject.hid_rnum == ""){
	        			  return "";
	        		  }
	        		  if(rowObject.hid_rnum == "1"){
	        			  return 'rowspan='+rowObject.hid_rcnt;
	        		  }else{
	        			  return 'style="display:none"';
	        		  }
	        	  } 
	          },
	          {name:"region_nm", index:"region_nm", sortable:false, formatter:"string", width:100, align:"center", editable:false, frozen:false},
	          {name:"prod_nm", index:"prod_nm", sortable:false, formatter:"string", width:150, align:"center", editable:false, frozen:false},
              {name:"seqc_nm1", index:"seqc_nm1", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
	          {name:"seqc_nm2", index:"seqc_nm2", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
	          {name:"seqc_nm3", index:"seqc_nm3", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
	          {name:"car_no", index:"car_no", sortable:false, formatter:"string", width:80, align:"center", editable:false, frozen:false},
			  {name:"car_vehicle", index:"car_vehicle", sortable:false, formatter:"string", width:120, align:"center", editable:false, frozen:false},
			  {name:"car_en_nm", index:"car_en_nm", sortable:false, formatter:"string", width:60, align:"center", editable:false, frozen:false},
			  {name:"car_type", index:"car_type", sortable:false, formatter:"string", width:60, align:"center", editable:false, frozen:false},
			  {name:"mileage", index:"mileage", sortable:false, formatter:"string", width:80, align:"center", editable:false, frozen:false},
	          {name:"seqc_nm4", index:"seqc_nm4", sortable:false, formatter:"string", width:150, align:"left", editable:false, frozen:false},
              {name:"qty", index:"qty", sortable:false, formatter:"string", width:60, align:"right", editable:true, frozen:false, hidden:true},
              {name:"expt_ymd", index:"expt_ymd", sortable:false, formatter:"string", width:80, align:"center", editable:false, frozen:false},
              {name:"expt_time", index:"expt_time", sortable:false,	formatter:"string",	width:60, align:"center", editable:false, frozen : false},
	          {name:"hid_rcnt", index:"hid_rcnt", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"hid_rnum", index:"hid_rnum", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"hid_pgs_cnt", index:"hid_pgs_cnt", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"hid_pgs_rnum", index:"hid_pgs_rnum", sortable:false, formatter:"string", width:0, align:"center", editable:false, frozen:false, hidden:true},
	          {name:"pop_img",    index:"pop_img", sortable:false,	formatter:"string",	width:60,  align:"center",	editable:false,	frozen : false},
	          {name:"comment", index:"comment", sortable:false, formatter:"string", width:300, align:"left", editable:false, frozen:false}
			  ];
	
	gridParam = {
		viewEdit : [{
			gridName     : gridName,
			url          : "/hncis/generalService/doSearchByXgs02.do",
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			paramJson    : fnParamJson(),
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			page		 : $("#hid_page").val(),
			selectCellFc : "setChangeImg();",
			completeFc   : "addGridRow(15, 'htmlTable', 'datarow');setChangeImg();",
			pager		 : "htmlPager",
			dblClickRowFc : "celldbClickAction(rowId,iRow,iCol,e);"
		}]
	};
	commonJqGridInit(gridParam);
}

function retrieve(btnFlag){
	switch(btnFlag){
	   case "search" :
		   	doSearch();
			break;
	}
}

function fnParamJson(){
	var paramVo = {
		eeno 		: $("#key_eeno").val(),
		eenm 		: $("#key_eenm").val(),
		from_ymd 	: dateConversionKr(trimChar($("#key_from_ymd").val(), "/")),
		to_ymd 		: dateConversionKr(trimChar($("#key_to_ymd").val(), "/")),
		pgs_st_cd 	: $("#key_pgs_st_cd").val(),
		regn_cd 	: $("#key_regn_cd").val(),
		gs_type 	: 'BV',
		corp_cd		: sess_corp_cd
	};
	return paramVo;
}

function doSearch(){
	doCommonSearch("/hncis/generalService/doSearchByXgs02.do", util.jsonToString(fnParamJson()), "loadCallBack();", gridName, "N");
}

function loadCallBack(){
	addGridRow();
	setChangeImg();
	//setMerge();
}

function setMerge(){
    groupTable($("#"+gridName+" tr:has(td)"), 2, 7);
    $("#"+gridName+" .deleted").remove();
}

function celldbClickAction(rowId,iRow,iCol,e){
	if(getColValue("doc_no",rowId, gridName) != ""){
        var form = $("<form/>");
        form.attr("method" , "post");
        form.attr("id"     , "hideForm");
        form.attr("action" , "xbv07.gas");
        var inp1 = $("<input type='hidden' id='hid_doc_no' name='hid_doc_no'/>").val(getColValue("doc_no",rowId, gridName));
        var inp2 = $("<input type='hidden' id='hid_eeno' name='hid_eeno'/>").val(getColValue("eeno",rowId, gridName));
        var inp3 = $("<input type='hidden' id='hid_view_nm' name='hid_view_nm'/>").val($("#menu_id").val());
        var cond = "";
        cond += $("#key_eeno").val();
        cond += "|" + $("#key_eenm").val();
        cond += "|" + $("#key_from_ymd").val();
        cond += "|" + $("#key_to_ymd").val();
        cond += "|" + $("#key_pgs_st_cd").val();
        cond += "|" + $("#key_regn_cd").val();
        cond += "|" + $("#page_htmlPager").val();
        
        var inp4 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val(cond);
        var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
        form.append(inp1, inp2, inp3, inp4, token);
        $("body").append(form);
        form.submit();
        form.remove();
	}
}

function setChangeImg(){
	var gridId = jQuery('#htmlTable').jqGrid('getDataIDs');
	for(var i = 1 ; i <= gridId.length ; i++){
		if(getColValue("doc_no", i, gridName) != ""){
			var imgSrc = "<img src='../../images/hncis_bttn/open-n.gif' onClick='doFileAttachOpen(\""+i+"\");'/>";
			jQuery('#htmlTable').jqGrid("setRowData", i, {pop_img:imgSrc}); 
		}
	}
}

var win;
function doFileAttachOpen(seq){
	if(win != null){ win.close(); }
	var url = "xbv07_file.gas", width = "460", height = "453";
	
	$("#file_doc_no").val(getColValue("doc_no", seq, gridName)+getColValue("seq_no", seq, gridName));
	$("#hid_use_yn").val("N");
	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}