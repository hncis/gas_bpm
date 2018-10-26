var params;
var userKeyArr = ['ee_nm','ops_cd', 'ops_nm', 'poa_nm', 'cost_cd'];
var userColArr = ['xusr_name','xusr_dept_code', 'xusr_dept_name', 'xusr_step_name', 'xusr_cost_cd'];
var comboVal;
var comboVal_nat;
var congryGroupVal;
var natFlag = true;

jQuery(document).ready(function(){
	initMenus('1850');
	
	$(".inputOnlyNumber").numeric();
	
	if($("#hid_doc_no").val() != ""){
		$("#doc_no").val($("#hid_doc_no").val());
		$("#eeno").val($("#hid_eeno").val());
	}
	
	getCommonCode("budg_no:XBT;budg_wbs:XBT;", "N", "", "/hncis/system/getPurchaseOrderCombo.do");
	getCommonCode("budg_type:X3012;", "N", "setInsaInfo();");
});

var url = "", gridName1 = "htmlTable1", gridName2 = "htmlTable2", gridName3 = "htmlTable3", gridName4 = "htmlTable4", gridName5 = "htmlTable5", gridName6 = "htmlTable6";
var datarow = {doc_no:"", seq:"", pgs_st_cd:"", eeno:"", ee_nm:"", ops_cd:"", ops_nm:"", poa_nm:"", visa_yn:"", cost_cd:"", flht_utz_amt:"", bztp_lodg_exp:"", rent_amt:"", vs_ptt_exp:"",etc_amt:"", pgs_st_nm:"", cost_info:"",pass_country:"",pass_number:"",birth_ymd:"",issue_ymd:"",expire_ymd:""};
var schedule_datarow = {doc_no:"", adsc_ymd_old:"", strt_tim_old:"", fnh_tim_old:"", seq:"", adsc_ymd:"", strt_tim:"", fnh_tim:"", dest_dtl_sbc:"", rem_sbc:""};
var flight_datarow = {flt_doc_no:"",flt_seq:"", flt_flt_ymd:"", flt_air_lines:"", flt_flight:"", flt_flt_from:"", flt_flt_to:"", flt_departure_time:"", flt_arrive_time:"", flt_seat:"", flt_etc_amt:""};
var hotel_datarow  = {htl_doc_no:"",htl_seq:"", htl_check_in_ymd:"", htl_check_out_ymd:"", htl_hotel_name:"", htl_hotel_addr:"", htl_hotel_phone:"", htl_pay_form:"", htl_confirmation:"", htl_meal_yn:"", htl_etc_amt:""};
var rent_datarow   = {rent_doc_no:"",rent_seq:"", rent_check_in_ymd:"", rent_check_out_ymd:"", rent_car_company:"", rent_car_type:"", rent_car_phone:"", rent_pay_form:"", rent_confirmation:"", rent_air_yn:"", rent_etc_amt:""};
var vt_datarow = {vt_doc_no:"", vt_seq:"", vt_pgs_st_cd:"", vt_eeno:"", vt_ee_nm:"", vt_ops_cd:"", vt_ops_nm:"", vt_poa_nm:"", vt_visa_yn:"", vt_cost_cd:"", vt_flht_utz_amt:"", vt_bztp_lodg_exp:"", vt_rent_amt:"", vt_vs_ptt_exp:"",vt_etc_amt:"", vt_pgs_st_nm:"", vt_cost_info:"",vt_pass_country:"",vt_pass_number:"",vt_birth_ymd:"",vt_issue_ymd:"",vt_expire_ymd:""};

function setComboInfo(){
	setMultiComboInfo();
}

function setComboInfoCallBack(jsonData){
	comboVal = jsonData;
	comboVal_nat = jsonData;
	initAfterMenus();
	changeAbrd($("#dom_abrd_scn_cd").val(),"Y");
	getCommonCode("htl_pay_form:X3021;rent_air_yn:X3030;", "Y", "init();");
}

function setMultiComboInfo(){
	var params = {
		codknd		: 'nat_g_scn_cd_temp:X0017:S;dest_nat_cd_temp:visit:S;'
	};

	doCommonAjax("/getCommonMultiCombo.do", params,"setComboInfoCallBack(jsonData.sendResult);");
}

function init(){
	
	var url = "/doSearchToEmpty.do";
	
	var tmpVisaShow = false;
	var tmpVisaShow01 = false;
	
	if($("#dom_abrd_scn_cd").val() == "PT003"){
		tmpVisaShow = true;
		tmpVisaShow01 = true;
	}else if($("#dom_abrd_scn_cd").val() == "PT001"){
		tmpVisaShow01 = true;
	}
	
	$("#"+gridName1).GridUnload();
	cn = ["Doc No.", "SEQ", "PGS_ST_CD", "ID No.", "Name", "DepartmentCode", "Department", "Position", "Visa", "Cost<BR>Center", "Air Ticket", "Hotel", "Rent a car", "Visa", "BTR<BR>FORECAST", "STATUS", "BTR", "Passport Country", "Passport number", "Birth Date", "Issue Date", "Expire Date"];
	cm = [
			{name:"doc_no",		index:"doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"seq",		index:"seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"eeno",		index:"eeno"		, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
				editoptions:{dataEvents:[{type:'keyup', 
						fn:function(e){
							if(!isNumeric($(e.target).val())){
								$(e.target).val(selectNum($(e.target).val()));
							}
							
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
							if(getColValue("doc_no", rowId, gridName1) != ""){
								return;
							}
							searchToUserInfo(rowId, 'eeno');
						}
					},
					{type:'keydown', 
						fn:function(e){
							var row = $(e.target).closest('tr.jqgrow');
							var rowId = row.attr('id');
							if(getColValue("doc_no", rowId, gridName1) != ""){
								return;
							}
						}
					}]
		        },
		        cellattr:function(rowId, tv, rowObject, cm, rdata){		        	
		        }
			},  
			{name:"ee_nm",		index:"ee_nm"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:false,	frozen : false},
			{name:"ops_cd",		index:"ops_cd"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"ops_nm",		index:"ops_nm"		, sortable:false,	formatter:"string",		width:110,	align:"left",	editable:false,	frozen : false},
			{name:"poa_nm",		index:"poa_nm"		, sortable:false,	formatter:"string",		width:105,	align:"left",	editable:false,	frozen : false},
			{name:'visa_yn',	index:'visa_yn'		, sortable:false,	formatter:"select",		width:35, 	align:"center",	editable:false, 	edittype:'select', hidden:tmpVisaShow01,
				editoptions:{
					value:"Y:Y;N:N",
					dataInit: function(elem) {
						$(elem).width(35);
					}
				}
			},
			{name:"cost_cd",	index:"cost_cd"		, sortable:false,	formatter:"string",		width:50,	align:"center",	editable:false,	frozen : false,
				editoptions: {maxlength:"5"}},
			{name:"flht_utz_amt",	index:"flht_utz_amt"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"bztp_lodg_exp",	index:"bztp_lodg_exp"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"rent_amt",	index:"rent_amt"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"vs_ptt_exp",	index:"vs_ptt_exp"	, sortable:false,formatter: numFormat, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"etc_amt",	index:"etc_amt"	, sortable:false,formatter: numFormat, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:65,	align:"right",	editable:false,	frozen : false,
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
			{name:"pgs_st_nm",		index:"pgs_st_nm"	, sortable:false,	formatter:"string",		width:55,	align:"center",	editable:false,	frozen : false},
			{name:"cost_info",		index:"cost_info"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"pass_country",	index:"pass_country", sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"pass_number",	index:"pass_number"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"birth_ymd",		index:"birth_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			{name:"issue_ymd",		index:"issue_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			{name:"expire_ymd",		index:"expire_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			}
		];
		
	gridParam = {
		viewEdit : [{
			gridName     : gridName1,
			url          : url,
			colNames     : cn,
			colModel     : cm,
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(3, '"+gridName1+"', 'datarow');setTravelerList();setChangeImg();initAfterMenus();",
			selectCellFc : "setChangeImg();",
			paramJson    : ""
		}]
	};
	
	commonJqGridInit(gridParam);
	
	$("#"+gridName1).setColProp('eeno', {editoptions:{maxlength:"8", dataEvents:[{type:'keyup', 
		fn:function(e){
			if(!isNumeric($(e.target).val())){
				$(e.target).val(selectNum($(e.target).val()));
			}
			
			var row = $(e.target).closest('tr.jqgrow');
			var rowId = row.attr('id');
			if(getColValue("doc_no", rowId, gridName1) != ""){
				return;
			}
			searchToUserInfo(rowId, 'eeno');
		}
		}]
	}});
	
//	$("#"+gridName2).GridUnload();
//	schedule_cn = ["DOC_NO", "", "", "", "SEQ", "Date", "Departure", "Arrival", "Detail Description", "Remark(Flight No.)"];
//	schedule_cm = [
//			{name:"doc_no",		index:"doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
//			{name:"adsc_ymd_old",		index:"adsc_ymd_old"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
//			{name:"strt_tim_old",		index:"strt_tim_old"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
//			{name:"fnh_tim_old",		index:"fnh_tim_old"		, sortable:false,	formatter:"string",		width:100,	align:"left",	editable:true,	frozen : false, hidden : true},
//			{name:"seq",		index:"seq"			, sortable:false,	formatter:"string",		width:100,	align:"left",	editable:true,	frozen : false, hidden : true},
//			{name:"adsc_ymd",	index:"adsc_ymd"	, sortable:false,	formatter:"string",		width:100,	align:"left",	editable:true,	frozen : false,
//				editoptions: {
//		            dataInit: function(element) {
//		     		    $(element).datepicker({
//		     		    	dateFormat: 'dd/mm/yy',
//		     		    	onSelect: function(dataText, inst){
//		     		    	}
//				    	});
//		            }
//				}
//			},
//			{name:"strt_tim",		index:"strt_tim"		, sortable:false,	formatter:"string",		width:70,	align:"center",	editable:true,	frozen : false,
//				editoptions: {maxlength:"4", 
//		            dataInit: function(element) {
//		     		    $(element).keyup(function(){
//
//		     		    	if(!isNumeric(element.value)){
//		     		    		element.value = selectNum(element.value);
//		     		    	}
//
//		     		    	if(trimChar(element.value, ":").length == 4){
//		     		    		if(element.value.length > 4){
//		     		    			element.value = "";
//		     		    		}else{
//		     		    			element.value = element.value.substring(0, 2)+":"+element.value.substring(2, 4);
//		     		    		}
//		     				}else{
//		     					element.value = trimChar(element.value, ":");
//		     				}
//		     		    });
//				    	
//		            }
//		        }
//			},
//			{name:"fnh_tim",		index:"fnh_tim"		, sortable:false,	formatter:"string",		width:70,	align:"center",	editable:true,	frozen : false,
//				editoptions: {maxlength:"4", 
//		            dataInit: function(element) {
//		     		    $(element).keyup(function(){
//		     		    	if(!isNumeric(element.value)){
//		     		    		element.value = selectNum(element.value);
//		     		    	}
//
//		     		    	if(trimChar(element.value, ":").length == 4){
//		     		    		if(element.value.length > 4){
//		     		    			element.value = "";
//		     		    		}else{
//		     		    			element.value = element.value.substring(0, 2)+":"+element.value.substring(2, 4);
//		     		    		}
//		     				}else{
//		     					element.value = trimChar(element.value, ":");
//		     				}
//		     		    });
//		            }
//		        }
//			},
//			{name:"dest_dtl_sbc",	index:"dest_dtl_sbc", sortable:false,	formatter:"string",		width:280,	align:"left",	editable:true,	frozen : false},
//			{name:"rem_sbc",		index:"rem_sbc"		, sortable:false,	formatter:"string",		width:460,	align:"left",	editable:true,	frozen : false}
//		];
//		
//	schedule_gridParam = {
//		viewEdit : [{
//			gridName     : gridName2,
//			url          : url,
//			colNames     : schedule_cn,
//			colModel     : schedule_cm,
//			height       : "100%",
//			sortname     : "eeno",
//			sortorder    : "asc",
//			rownumbers   : true,
//			multiselect  : false,
//			cellEdit     : true,
//			fnMerge      : false,
//			completeFc   : "addGridRow(3, '"+gridName2+"', 'schedule_datarow');initAfterMenus();",
//			paramJson    : ""
//		}]
//	};
	
//	commonJqGridInit(schedule_gridParam);
	
	$("#"+gridName3).GridUnload();
	flight_cn = ["", "", "Date","Air Lines","Flight","From","To","Departure","Arrive","Seat","Amount(R$)"];
	flight_cm = [
			{name:"flt_doc_no"			, index:"flt_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"flt_seq"				, index:"flt_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"flt_flt_ymd"			, index:"flt_flt_ymd"		, sortable:false,	formatter:"string",		width:80,	align:"center",	editable:true,	frozen : false,
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
			{name:"flt_air_lines"		, index:"flt_air_lines"		, sortable:false,	formatter:"string",		width:205,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flight"			, index:"flt_flight"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flt_from"		, index:"flt_flt_from"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flt_to"			, index:"flt_flt_to"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false},
			{name:"flt_departure_time"	, index:"flt_departure_time", sortable:false,	formatter:"string",		width:80,	align:"left",	editable:true,	frozen : false,
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
			{name:"flt_arrive_time"		, index:"flt_arrive_time"	, sortable:false,	formatter:"string",		width:160,	align:"left",	editable:true,	frozen : false,
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
			{name:"flt_seat"			, index:"flt_seat"			, sortable:false,	formatter:"string",		width:90,	align:"left",	editable:true,	frozen : false, hidden:true},
			{name:"flt_etc_amt"			, index:"flt_etc_amt"		,formatter: numFormat, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:80,	align:"right",	editable:true,	frozen : false,
				editoptions: {maxlength:"10", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    });
		            }
		        }
			}
		];

	flight_gridParam = {
		viewEdit : [{
			gridName     : gridName3,
			url          : url,
			colNames     : flight_cn,
			colModel     : flight_cm,
			height       : "100%",
			sortname     : "seq",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
//			cellEdit     : sess_mstu == "M" ? true : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(4, '"+gridName3+"', 'flight_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};
	
	commonJqGridInit(flight_gridParam);
	
	$("#"+gridName4).GridUnload();
	hotel_cn = ["", "", "Check-in","Check-out","Hotel","Adress","Phone","Payment Form","Confirmation","Breakfast","Amount(R$)"];
	hotel_cm = [
			{name:"htl_doc_no"			, index:"htl_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"htl_seq"				, index:"htl_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"htl_check_in_ymd"	, index:"htl_check_in_ymd"	, sortable:false,	formatter:"string",		width:80,	align:"center",	editable:true,	frozen : false,
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
			{name:"htl_check_out_ymd"	, index:"htl_check_out_ymd"	, sortable:false,	formatter:"string",		width:80,	align:"center",	editable:true,	frozen : false,
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
			{name:"htl_hotel_name"		, index:"htl_hotel_name"	, sortable:false,	formatter:"string",		width:140,	align:"left",	editable:true,	frozen : false},
			{name:"htl_hotel_addr"		, index:"htl_hotel_addr"	, sortable:false,	formatter:"string",		width:250,	align:"left",	editable:true,	frozen : false},
			{name:"htl_hotel_phone"		, index:"htl_hotel_phone"	, sortable:false,	formatter:"string",		width:125,	align:"left",	editable:true,	frozen : false},
			{name:"htl_pay_form",index:"htl_pay_form", formatter:"select", width:110, align:"center", editable:false, sortable:true, edittype:"select", fixed: true,
				editoptions:{value:getComboValue('htl_pay_form'),
					dataInit: function(elem) {
						$(elem).width(110);
					}
				}
			},
			{name:"htl_confirmation"	, index:"htl_confirmation"	, sortable:false,	formatter:"string",		width:110,	align:"left",	editable:true,	frozen : false, hidden:true},
			{name:"htl_meal_yn"			, index:"htl_meal_yn"		, sortable:false,	formatter:"select",		width:100, 	align:"center",	editable:true, 	edittype:'select',
				editoptions:{value:"Y:Y;N:N"
			    	,dataInit: function(elem) {
		        	$(elem).width(70);
		    		}
				}
			},
			{name:"htl_etc_amt"			, index:"htl_etc_amt"		, formatter: numFormat, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:80,	align:"right",	editable:true,	frozen : false,
				editoptions: {maxlength:"10", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    });
		            }
		        }
			}
		];


	hotel_gridParam = {
		viewEdit : [{
			gridName     : gridName4,
			url          : url,
			colNames     : hotel_cn,
			colModel     : hotel_cm,
			height       : "100%",
			sortname     : "seq",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
//			cellEdit     : sess_mstu == "M" ? true : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(1, '"+gridName4+"', 'hotel_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};
	
	commonJqGridInit(hotel_gridParam);
	
	$("#"+gridName5).GridUnload();
	rent_cn = ["", "", "Check-in","Check-out","Company","Type","Phone","Payment Form","Confirmation","Engine","Amount(R$)"];
	rent_cm = [
			{name:"rent_doc_no"			, index:"rent_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"rent_seq"			, index:"rent_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"rent_check_in_ymd"	, index:"rent_check_in_ymd"	, sortable:false,	formatter:"string",		width:80,	align:"center",	editable:true,	frozen : false,
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
			{name:"rent_check_out_ymd"	, index:"rent_check_out_ymd", sortable:false,	formatter:"string",		width:80,	align:"center",	editable:true,	frozen : false,
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
			{name:"rent_car_company"	, index:"rent_car_company"	, sortable:false,	formatter:"string",		width:155,	align:"left",	editable:true,	frozen : false},
			{name:"rent_car_type"		, index:"rent_car_type"		, sortable:false,	formatter:"string",		width:110,	align:"left",	editable:true,	frozen : false},
			{name:"rent_car_phone"		, index:"rent_car_phone"	, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false},
			{name:"rent_pay_form"		, index:"rent_pay_form"		, sortable:false,	formatter:"string",		width:100,	align:"left",	editable:true,	frozen : false, hidden:true},
			{name:"rent_confirmation"	, index:"rent_confirmation"	, sortable:false,	formatter:"string",		width:280,	align:"left",	editable:true,	frozen : false},
			{name:"rent_air_yn"			, index:"rent_air_yn"		, sortable:false,	formatter:"select",		width:60, 	align:"center",	editable:true, 	edittype:'select',
				editoptions:{value:getComboValue('rent_air_yn'),
					dataInit: function(elem) {
						$(elem).width(60);
					}
				}
			},
			{name:"rent_etc_amt"		, index:"rent_etc_amt"		, formatter: numFormat, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:80,	align:"right",	editable:true,	frozen : false,
				editoptions: {maxlength:"10", 
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ",");
		     		    	}
		     		    });
		            }
		        }
			}
		];

	rent_gridParam = {
		viewEdit : [{
			gridName     : gridName5,
			url          : url,
			colNames     : rent_cn,
			colModel     : rent_cm,
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
//			cellEdit     : sess_mstu == "M" ? true : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(1, '"+gridName5+"', 'rent_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};
	
	commonJqGridInit(rent_gridParam);
	
	$("#"+gridName6).GridUnload();
	cn_vt = ["Doc No.", "SEQ", "PGS_ST_CD", "ID No.", "Name", "DepartmentCode", "Department", "Position", "Visa", "Cost<BR>Center", "Air Ticket", "Hotel", "Rent a car", "Visa", "BTR<BR>FORECAST", "STATUS", "BTR", "Passport Country", "Passport number", "Birth Date", "Issue Date", "Expire Date"];
	cm_vt = [
			{name:"vt_doc_no",		index:"vt_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"vt_seq",			index:"vt_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"vt_pgs_st_cd",	index:"vt_pgs_st_cd"	, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"vt_eeno",		index:"vt_eeno"			, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"vt_ee_nm",		index:"vt_ee_nm"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:false,	frozen : false},
			{name:"vt_ops_cd",		index:"vt_ops_cd"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"vt_ops_nm",		index:"vt_ops_nm"		, sortable:false,	formatter:"string",		width:110,	align:"left",	editable:false,	frozen : false},
			{name:"vt_poa_nm",		index:"vt_poa_nm"		, sortable:false,	formatter:"string",		width:105,	align:"left",	editable:false,	frozen : false},
			{name:'vt_visa_yn',		index:'vt_visa_yn'		, sortable:false,	formatter:"select",		width:35, 	align:"center",	editable:false, 	edittype:'select', hidden:tmpVisaShow01,
				editoptions:{
					value:"Y:Y;N:N",
					dataInit: function(elem) {
						$(elem).width(35);
					}
				}
			},
			{name:"vt_cost_cd",	index:"vt_cost_cd"		, sortable:false,	formatter:"string",		width:50,	align:"center",	editable:false,	frozen : false,
				editoptions: {maxlength:"5"}},
			{name:"vt_flht_utz_amt",	index:"vt_flht_utz_amt"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"vt_bztp_lodg_exp",	index:"vt_bztp_lodg_exp"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"vt_rent_amt",	index:"vt_rent_amt"	, sortable:false,formatter: numFormat, hidden:tmpVisaShow, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"vt_vs_ptt_exp",	index:"vt_vs_ptt_exp"	, sortable:false,formatter: numFormat, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false,
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
			  {name:"vt_etc_amt",	index:"vt_etc_amt"	, sortable:false,formatter: numFormat, formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:2,
				    defaultValue:""
				  },		width:65,	align:"right",	editable:false,	frozen : false,
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
			{name:"vt_pgs_st_nm",		index:"vt_pgs_st_nm"	, sortable:false,	formatter:"string",		width:55,	align:"center",	editable:false,	frozen : false},
			{name:"vt_cost_info",		index:"vt_cost_info"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"vt_pass_country",	index:"vt_pass_country", sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"vt_pass_number",		index:"vt_pass_number"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"vt_birth_ymd",		index:"vt_birth_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			{name:"vt_issue_ymd",		index:"vt_issue_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			{name:"vt_expire_ymd",		index:"vt_expire_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
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
			}
		];
		
	vt_gridParam = {
		viewEdit : [{
			gridName     : gridName6,
			url          : url,
			colNames     : cn_vt,
			colModel     : cm_vt,
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(3, '"+gridName6+"', 'vt_datarow');setVtChangeImg();",
			selectCellFc : "setVtChangeImg();",
			paramJson    : ""
		}]
	};
	
	commonJqGridInit(vt_gridParam);
	
	setGridColumnOptions();
//	setGridColumnOptions(gridName2);
	
	jQuery("#"+gridName1).jqGrid('setLabel', 'visa_yn', '<td class="tooltip" title="Check if the<br>traveler has VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="35" style="font-weight:bold">Visa</td>');
	jQuery("#"+gridName1).jqGrid('setLabel', 'cost_info', '<td class="tooltip" title="You can make BTR after<br>G.A confirms your travel." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">BTR</td>');

	if($("#dom_abrd_scn_cd").val() == "PT001" || $("#dom_abrd_scn_cd").val() == "PT002"){
		jQuery("#"+gridName1).jqGrid('setLabel', 'flht_utz_amt', '<td align="center" width="65" style="font-weight:bold">Air Ticket</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'bztp_lodg_exp', '<td class="tooltip" title="You need enough<br>budget for<br>Air, Hotel, Rent, VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">Hotel</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'rent_amt', '<td align="center" width="65" style="font-weight:bold">Rent a car</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'vs_ptt_exp', '<td align="center" width="65" style="font-weight:bold">Visa</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'etc_amt', '<td align="center" width="65" style="font-weight:bold">BTR<BR>FORECAST</td>');
	}else{
		jQuery("#"+gridName1).jqGrid('setLabel', 'flht_utz_amt', '<td align="center" width="65" style="font-weight:bold">Transportation</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'bztp_lodg_exp', '<td class="tooltip" title="You need enough<br>budget for<br>Air, Hotel, Rent, VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">Accommodation</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'rent_amt', '<td align="center" width="65" style="font-weight:bold">Meal</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'vs_ptt_exp', '<td align="center" width="65" style="font-weight:bold">Expense</td>');
		jQuery("#"+gridName1).jqGrid('setLabel', 'etc_amt', '<td align="center" width="65" style="font-weight:bold">Company Card</td>');
	}
	
	jQuery("#"+gridName6).jqGrid('setLabel', 'vt_visa_yn', '<td class="tooltip" title="Check if the<br>traveler has VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="35" style="font-weight:bold">Visa</td>');
	jQuery("#"+gridName6).jqGrid('setLabel', 'vt_cost_info', '<td class="tooltip" title="You can make BTR after<br>G.A confirms your travel." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">BTR</td>');

	if($("#dom_abrd_scn_cd").val() == "PT001" || $("#dom_abrd_scn_cd").val() == "PT002"){
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_flht_utz_amt', '<td align="center" width="65" style="font-weight:bold">Air Ticket</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_bztp_lodg_exp', '<td class="tooltip" title="You need enough<br>budget for<br>Air, Hotel, Rent, VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">Hotel</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_rent_amt', '<td align="center" width="65" style="font-weight:bold">Rent a car</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_vs_ptt_exp', '<td align="center" width="65" style="font-weight:bold">Visa</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_etc_amt', '<td align="center" width="65" style="font-weight:bold">BTR<BR>FORECAST</td>');
	}else{
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_flht_utz_amt', '<td align="center" width="65" style="font-weight:bold">Transportation</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_bztp_lodg_exp', '<td class="tooltip" title="You need enough<br>budget for<br>Air, Hotel, Rent, VISA." onmouseover="tooltipDisplay(\'tooltip\');" align="center" width="65" style="font-weight:bold">Accommodation</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_rent_amt', '<td align="center" width="65" style="font-weight:bold">Meal</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_vs_ptt_exp', '<td align="center" width="65" style="font-weight:bold">Expense</td>');
		jQuery("#"+gridName6).jqGrid('setLabel', 'vt_etc_amt', '<td align="center" width="65" style="font-weight:bold">Company Card</td>');
	}
	
	jQuery("#"+gridName1).jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, 
		  groupHeaders:[
			{startColumnName: 'flht_utz_amt', numberOfColumns: 5, titleText: 'Amount(R$)'}
		  ]
		});
	
//	jQuery("#"+gridName2).jqGrid('setGroupHeaders', {
//		  useColSpanStyle: true, 
//		  groupHeaders:[
//			{startColumnName: 'strt_tim', numberOfColumns: 2, titleText: 'Time Schedule'}
//		  ]
//		});
	

	jQuery("#"+gridName6).jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, 
		  groupHeaders:[
			{startColumnName: 'vt_flht_utz_amt', numberOfColumns: 5, titleText: 'Amount(R$)'}
		  ]
		});
	
//	$("#strt_ymd").datepicker({ dateFormat: 'dd/mm/yy' });
//	$("#fnh_ymd").datepicker({ dateFormat: 'dd/mm/yy' });
	
	if($("#hid_doc_no").val() != ""){
		$("#doc_no").val($("#hid_doc_no").val());
		$("#eeno").val($("#hid_eeno").val());
		doSearch1("Y");
	}
}

function searchToUserInfo(rowId, colNm){
	if(getColValue(colNm, rowId).length != 8 ){
		jQuery("#"+gridName1).setCell(rowId, 'ee_nm',  ' ');
		jQuery("#"+gridName1).setCell(rowId, 'ops_nm', ' ');
		jQuery("#"+gridName1).setCell(rowId, 'ops_cd', ' ');
		jQuery("#"+gridName1).setCell(rowId, 'poa_nm', ' ');
		return;
	}
	
	var keyData = {
			xusr_empno : getColValue(colNm, rowId, gridName1)
			};
	paramData = {
		paramJson      	: util.jsonToString(keyData)
	};
	
	doCommonAjax("/hncis/system/doSearchToUserManagementByUserDetail.do", paramData, "userInfoCallBack(jsonData.sendResult, "+rowId+");");

}

function userInfoCallBack(result, rowId){
	if(typeof(result.xusr_name) == "undefined"){
		jQuery("#"+gridName1).setCell(rowId, 'ee_nm',  ' ');
		jQuery("#"+gridName1).setCell(rowId, 'ops_nm', ' ');
		jQuery("#"+gridName1).setCell(rowId, 'ops_cd', ' ');
		jQuery("#"+gridName1).setCell(rowId, 'poa_nm', ' ');
	}else{
		setUserInfo(result, 'Y', rowId, gridName1);
		setChangeImg();
	}
}


function retrieve(gubun){
	switch(gubun){
		case "search" : 
			doSearch1("Y");
			break;
		case "back" :
			doBack();
			break;
		case "check" :
			doCheck();
			break;
		case "checkCancel" :
			doCheckCancel();
			break;
		case "save" :
			doSave();
			break;
	}
}

function setTravelerList(){
	if($("#hid_doc_no").val() == ""){
		var reqTravelInfo = {
			eeno : $("#eeno").val(),
			ee_nm     : $("#ee_nm").val(),
			ops_nm    : $("#ops_nm").val(),
			ops_cd    : $("#ops_cd").val(),
			poa_nm    : $("#poa_nm").val(),
			visa_yn   : "N",
			cost_cd   : $("#cost_cd").val()
		};
		
		jQuery("#"+gridName1).jqGrid("setRowData", 1, reqTravelInfo);
	}
}

function setChangeImg(){
	
	var gridRow  = jQuery("#"+gridName1);
	var ids      = gridRow.getDataIDs();

	for(var i=0;i<ids.length;i++){
		
		if(getColValue("doc_no", i+1, gridName1)!="" && ($("#pgs_st_cd").val() == "3" || $("#pgs_st_cd").val() == "Z")){
			var imgSrc = "<img src='../../images/hncis_bttn/report-n.gif' onClick='doPayReportOpen("+getColValue("eeno", i+1, gridName1)+", \""+getColValue("pgs_st_cd", i+1, gridName1)+"\");'/>";
			gridRow.jqGrid("setRowData", i+1, {cost_info:imgSrc});
		}
	}
}

function setVtChangeImg(){
	var gridRow  = jQuery("#"+gridName6);
	var ids      = gridRow.getDataIDs();

	for(var i=0;i<ids.length;i++){
		if(getColValue("vt_doc_no", i+1, gridName6)!="" && ($("#pgs_st_cd").val() == "3" || $("#pgs_st_cd").val() == "Z")){
			var imgSrc = "<img src='../../images/hncis_bttn/report-n.gif' onClick='doPayReportOpen(\""+getColValue("vt_eeno", i+1, gridName6)+"\", \""+getColValue("vt_pgs_st_cd", i+1, gridName6)+"\");'/>";
			gridRow.jqGrid("setRowData", i+1, {vt_cost_info:imgSrc});
		}
	}
}

var win;
function doPayReportOpen(eeno, pgs_st_cd){	
	if(win != null){ win.close(); }
	var url = "businessTravel_report.gas", width = "1000", height = "650";
		
	$("#hid_report_doc_no").val($("#hid_doc_no").val());
	$("#hid_report_eeno").val(eeno);
	$("#hid_report_pgs_st_cd").val(pgs_st_cd);
	
	win = newPopWin("about:blank", width, height, "win_report");
	document.reportForm.hid_csrfToken.value = $("#csrfToken").val();
	document.reportForm.action = url;
	document.reportForm.target = "win_report"; 
	document.reportForm.method = "post"; 
	document.reportForm.submit();
}

var win_standard;
function doStandardExpenseOpen(){	
	if(win_standard != null){ win_standard.close(); }
	var url = "xbt01_standard.gas", width = "1000", height = "580";
	
	win_standard = newPopWin("about:blank", width, height, "win_standard");
	document.reportForm.hid_csrfToken.value = $("#csrfToken").val();
	document.reportForm.action = url;
	document.reportForm.target = "win_standard"; 
	document.reportForm.method = "post"; 
	document.reportForm.submit();
}


var saveCode = "";
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
	}
}

function setInsaInfo(){
	if($("#eeno").val() != ""){
		$("#nSource").val($("#eeno").val());
		if(saveCode == $("#nSource").val()){ return; }
		saveCode = $("#nSource").val();
		
		var keyData = { xusr_empno : $("#eeno").val() };
		paramData = {
			paramJson : util.jsonToString(keyData)
		};
		doCommonAjax("/doSearchToUserInfo.do", paramData, "insaCallBack(jsonData.sendResult)");
	}
}

function doSearch1(msgFlag){
	var params = {
		doc_no : $("#doc_no").val()
	};
	
	var paramData = {
		paramJson : util.jsonToString(params)
	};
	
	msgFlag = typeof(msgFlag) == "undefined" ? "Y" : msgFlag;
	doCommonAjax("doSearchInfoBTToRequest.do", paramData,"loadCallBack1(jsonData.sendResult, '"+msgFlag+"');");
}

function doSearch2(){
	var params = {
		doc_no : $("#doc_no").val(),
		h_gubn : "H"
	};
	
	doCommonSearch("doSearchListBTToRequestByTraveler.do", util.jsonToString(params), "loadCallBack2();", gridName1, "N");
}

//function doSearch3(){
//	var params = {
//		doc_no : $("#doc_no").val()
//	};
//	
//	doCommonSearch("doSearchListBTToRequestBySchedule.do", util.jsonToString(params), "loadCallBack3();", gridName2, "N");
//}

function doSearch4(){
	var params = {
		doc_no : $("#doc_no").val()
	};
	
	doCommonSearch("doSearchListBTToRequestByFilghtConfirmation.do", util.jsonToString(params), "loadCallBack4();", gridName3, "N");
}

function doSearch5(){
	var params = {
		doc_no : $("#doc_no").val()
	};
	
	doCommonSearch("doSearchListBTToRequestByHotelConfirmation.do", util.jsonToString(params), "loadCallBack5();", gridName4, "N");
}

function doSearch6(){
	var params = {
		doc_no : $("#doc_no").val()
	};
	
	doCommonSearch("doSearchListBTToRequestByRentCar.do", util.jsonToString(params), "loadCallBack6();", gridName5, "N");
}

function doSearch7(){
	var params = {
		doc_no : $("#doc_no").val(),
		h_gubn : "O"
	};
	
	doCommonSearch("doSearchListBTToRequestByVirtualTraveler.do", util.jsonToString(params), "loadCallBack7();", gridName6, "N");
}

function insaCallBack(result){
	$("#eeno").val(result.xusr_empno);
	$("#ee_nm").val(result.xusr_name);
	$("#poa_nm").val(result.xusr_step_name);
	$("#ops_nm").val(result.xusr_dept_name);
	$("#ops_cd").val(result.xusr_dept_code);
	$("#keyOpsCd").val(result.xusr_dept_nm_cd);
	$("#keyOpsNm").val(result.xusr_dept_nm_dept);
	$("#cost_cd").val(result.xusr_cost_cd);
	$("#tel_no").val(result.xusr_tel_no);
		
	setTravelerList();
	setComboInfo();
	
//	if(natFlag){
//		natFlag = false;
//		changeAbrd("PT002","Y");
//	}
}

function loadCallBack1(result, msgFlag){
	loadJsonSet(result);
	if(msgFlag == "Y") setBottomMsg(result.message, false);
			
	readOnlyStyle("snb_rson_sbc", 1);
	
	changeAbrd($("#dom_abrd_scn_cd").val());
	$("#nat_g_scn_cd_temp").val(trimChar($("#nat_g_scn_cd").val()));
	
	changeCountryGroup($("#nat_g_scn_cd_temp").val());
	$("#dest_nat_cd_temp").val(trimChar($("#dest_nat_cd").val()));
		
	$("#cost_cd").val(result.cost_cd);
	$("#budg_type").val(result.budg_type);
	if(result.budg_type == "D"){
		$("#budg_no").attr("style", "width:120px;");
		$("#budg_no").val(result.budget_no);
		$("#budg_wbs").attr("style", "display:none;");
		$("#budg_ipt").attr("style", "display:none;");
	}else if(result.budg_type == "I"){
		$("#budg_no").attr("style", "width:120px;");
		$("#budg_no").val(result.budget_no);
		$("#budg_wbs").attr("style", "display:none;");
		$("#b_ipt").html("<input type='text' id='budg_ipt' style='width:120px;' maxLength='7' class='disabled' readonly>");
		$(".iptOnlyNumber").numeric();
	}else if(result.budg_type == "W"){
		$("#budg_no").attr("style", "display:none;");
		$("#budg_wbs").attr("style", "width:120px;");
		$("#budg_wbs").val(result.budget_no);
		$("#b_ipt").html("<input type='text' id='budg_ipt' style='width:120px;' maxLength='10' class='disabled' readonly>");
	}
	$("#budg_ipt").val(result.budg_text);
	
	$("#budg_type").attr("disabled", true);
	$("#budg_no").attr("disabled", true);
	$("#budg_wbs").attr("disabled", true);
	$("#budg_ipt").attr("disabled", true);
	
	doSearch2();
}

function loadCallBack2(){
	addGridRow(3, gridName1, 'datarow');
	setChangeImg();
	initAfterMenus();
	doSearch4();
//	doSearch3();
}

//function loadCallBack3(){
//	addGridRow(3, gridName2, 'schedule_datarow');
//	initAfterMenus();
//	doSearch4();
//}

function loadCallBack4(){
	addGridRow(4, gridName3, 'flight_datarow');
	initAfterMenus();
	doSearch5();
}

function loadCallBack5(){
	addGridRow(1, gridName4, 'hotel_datarow');
	initAfterMenus();
	doSearch6();
}

function loadCallBack6(){
	addGridRow(1, gridName5, 'rent_datarow');
	initAfterMenus();
	doSearch7();
}

function loadCallBack7(){
	addGridRow(3, gridName6, 'vt_datarow');
	setVtChangeImg();
	initAfterMenus();
}

function saveCallBack(result){
	setBottomMsg(result.message, true);
	$("#doc_no").val($("#hid_doc_no").val());
	doSearch1("N");
}

function deleteCallBack(result){
	mainForm.reset();
	setBottomMsg(result.message, true);
	saveCode="";
	$("#hid_doc_no").val("");
	$("#hid_eeno").val("");
	$("#temp_doc_no").val(result.code);

	$("#"+gridName1).trigger("reloadGrid");
	$("#"+gridName2).trigger("reloadGrid");
	setInsaInfo();
	setTimeout("addRow()" , 100);
}

function addRow(){
	var gridRowId = jQuery("#"+gridName1).getDataIDs().length;
	jQuery("#"+gridName1).jqGrid("addRowData", gridRowId+1, datarow);
	initAfterMenus();
}

function addScheduleRow(){
	var gridRowId = jQuery("#"+gridName2).getDataIDs().length;
	jQuery("#"+gridName2).jqGrid("addRowData", gridRowId+1, schedule_datarow);
	initAfterMenus();
}

function setDateGap(){
	var fdate = dateConversionKr(trimChar($("#strt_ymd").val(), "/"));
	var tdate = dateConversionKr(trimChar($("#fnh_ymd").val(), "/"));
	
	iGap = getDateGap(fdate, tdate);
}

function getDateGap(str1, str2){
	var iGap = 0;
	if(str1.length == 8 && str2.length == 8){
		sYear     = str1.substr(0, 4);
		sMonth    = eval(str1.substr(4, 2));
		sDay      = eval(str1.substr(6, 2));
		
		eYear     = str2.substr(0, 4);
		eMonth    = eval(str2.substr(4, 2));
		eDay      = eval(str2.substr(6, 2));
		
		dteStart  = new Date(sYear, sMonth-1, sDay);
		dteEnd    = new Date(eYear, eMonth-1, eDay);
		
		iDteStart = dteStart.getTime();
		iDteEnd   = dteEnd.getTime();
		iGap      = iDteEnd - iDteStart;
	}
	
	return (iGap/86400000);
}

function chk_auth(){
	var frm = document.mainForm;
	
	with(frm){
		if($("#hid_doc_no").val() != ""){
			readOnlyStyle("eeno", 1);
		}else{
			readOnlyStyle("eeno", 2);
		}
			
		readOnlyStyle("ee_nm", 1);
		readOnlyStyle("poa_nm", 1);
		readOnlyStyle("ops_nm", 1);
		readOnlyStyle("ptt_ymd", 1);
		readOnlyStyle("doc_no", 1);
		readOnlyStyle("pgs_st_nm", 1);
		readOnlyStyle("snb_rson_sbc", 1);
		readOnlyStyle("tel_no", 1);
	}
}

function processFlag(gubun){
	if(gubun != "save"){
		if($("#doc_no").val() == ""){
			alertUI("Please save before the next step.");
			return;
		}
	}
	return true;
}

function validation(){
	
	if($("#eeno").val() == ""){
		alertUI("Please enter your ID number.");
		$("#eeno").focus();
		return false;
	}
	if($("#dom_abrd_scn_cd").val() == ""){
		alertUI("Please select Place Type.");
		$("#dom_abrd_scn_cd").focus();
		return false;
	}
	if($("#nat_g_scn_cd_temp").val() == ""){
		alertUI("Please select Country Group.");
		$("#nat_g_scn_cd_temp").focus();
		return false;
	}
	if($("#dest_nat_cd_temp").val() == ""){
		alertUI("Please select place to visit.");
		$("#dest_nat_cd_temp").focus();
		return false;
	}
	if(trimChar($("#vsit_purp_sbc").val()) == ""){
		alertUI("Please enter the purpose.");
		$("#vsit_purp_sbc").focus();
		return false;
	}
	
	if(trimChar($("#strt_ymd").val()) == ""){
		alertUI("Please enter start date.");
		//$("#strt_ymd").focus();
		return false;
	}

	if(trimChar($("#fnh_ymd").val()) == ""){
		alertUI("Please enter end date.");
		//$("#fnh_ymd").focus();
		return false;
	}
	if($("#budg_no").val() == ""){
		alertUI("Please select Budget Code.");
		$("#budg_no").focus();
		return false;
	}
	
	return true;
}


function processValidation(gubun){
	var pgs_st_cd = $("#pgs_st_cd").val();
	var cancel_yn = $("#cancel_yn").val();
	var acpc_pgs_st_cd = $("#acpc_pgs_st_cd").val();
	var flag      = false;
	
	if(pgs_st_cd == ""){
		if(gubun == "save"){
			flag  = true;
		}else{
			alertUI("Please save before the next step");
		}
	}else if(gubun == "save" ){
//		if( sess_mstu == "M" || sess_auth == 5 ){
//			flag = true;
//		}else if($("#ipe_eeno").val() != sess_empno){
//			alert("You can't save.");
//		}else if(pgs_st_cd != "0"){
//			alert("You can't "+gubun+" in this status");
//		}
//	}else if((sess_mstu == "M" || sess_auth == 5) && gubun == "edit"){
//		flag = true;
	}else if(pgs_st_cd == "0"){
		if(gubun != "save"){
			if(gubun == "request" || gubun == "edit" || gubun == "delete" || gubun == "travelerDelete" || gubun == "scheduleDelete" || gubun == "forceConfirm" ){
				flag = true;
			}else{
				alertUI("Please request before the next step.");
			}
		}else{
			alertUI("Data is already stored.");
		}
	}else if(pgs_st_cd == "A"){
		if( sess_mstu == "M" || sess_auth == 5 ){
//			if(gubun == "delete"){
//				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
//			}else{
				flag = true;
//			}
		}else{
			if(gubun == "cancel"){
				flag = true;
			}else if(cancel_yn == "Y"){
				if(gubun == "requestCancel"){
					flag = true;
				}else{
					alertUI("You can't cancel the request in this status.");
				}
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "Z"){
		if( sess_mstu == "M" || sess_auth == 5 ){
//			if(gubun == "delete"){
//				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
//			}else{
				flag = true;
//			}
		}else{
			if(gubun == "confirm" || gubun == "reject" || gubun == "confirmCancel" || gubun == "cancel"){
				flag = true;
				if(gubun == "confirm"){
//					if((sess_mstu == "M" || sess_auth == 5) && acpc_pgs_st_cd == "0"){
						flag = true;
//					}else if((sess_mstu == "M" || sess_auth == 6) && acpc_pgs_st_cd == "Z"){
//						flag = true;
//					}else{
//						flag = false;
//						alert("You can't comfirm.");
//					}
				}
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "3"){
		if( sess_mstu == "M" || sess_auth == 5 ){
//			if(gubun == "delete"){
//				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
//			}else{
				flag = true;
//			}
		}else{
			if(gubun == "confirmCancel" || gubun == "cancel"){
				flag = true;
			}else{
				alertUI("You can't "+gubun+" in this status.");
			}
		}
	}else if(pgs_st_cd == "C"){
		if( sess_mstu == "M" || sess_auth == 5 ){
//			if(gubun == "delete"){
//				flag = confirm("You are attempting to delete a request in progress.\n\nAre you sure?");
//			}else{
				flag = true;
//			}
		}else{
			alertUI("You can't "+gubun+" in this status.");
		}
	}
	return flag;
}

function travelerDelete(){
	
	if(!processValidation("travelerDelete"))return;

	var rowId = jQuery("#"+gridName1).jqGrid("getGridParam", "selrow");
		
	if(rowId == "" || rowId == null){
		alertUI("Please select the traveler to delete.");
		return;
	}else if(getColValue("doc_no", rowId, gridName1) == ""){
		alertUI("삭제할 데이터가 없습니다.");
		return;
	}
	
	var travelerInfo = {
			doc_no    		: getColValue("doc_no",     rowId, gridName1),
			eeno    		: getColValue("eeno",     rowId, gridName1)
	};
	
	confirmUI("Do you want to Delete traveler?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						travelerInfo : util.jsonToString(travelerInfo)
					};
					doCommonAjax("doDeleteTravelerToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch2();");
			}
		});
	});
}

function scheduleDelete(){
	
	if(!processValidation("scheduleDelete"))return;

	var rowId = jQuery("#"+gridName2).jqGrid("getGridParam", "selrow");
		
	if(rowId == "" || rowId == null){
		alertUI("Please select the schedule to delete.");
		return;
	}else if(getColValue("doc_no", rowId, gridName2) == "" ){
		alertUI("삭제할 데이터가 없습니다.");
		return;
	}
	
	var scheduleInfo = {
			doc_no          : getColValue("doc_no", rowId, gridName2),
			adsc_ymd 		: dateConversionKr(trimChar(getColValue("adsc_ymd_old", rowId, gridName2), "/")),
			strt_tim    	: trimChar(getColValue("strt_tim_old", rowId, gridName2), ":"),
			fnh_tim    		: trimChar(getColValue("fnh_tim_old", rowId, gridName2), ":")
	};
	
	confirmUI("Do you want to delete schedule?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						scheduleInfo : util.jsonToString(scheduleInfo)
				};
				doCommonAjax("doDeleteScheduleToRequest.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch3();");
			}
		});
	});
}

function doPrint(){
	
	if(win != null){ win.close(); }
	
	var url = "xbt01_print.gas", width = "1", height = "1";
	
	win = newPopWin("about:blank", width, height, "win_mepp_print0");
	document.mealForm.action = url;
	document.mealForm.target = "win_mepp_print0"; 
	document.mealForm.method = "post"; 
	document.mealForm.submit();
}

function setVehicleCkDisplay(value){
	/*
	if(value == "PT001"){
		$("#vehicleckTemp").attr("disabled", "");
		if($("#vehicleck").val() == "Y"){
			$("#vehicleckTemp").attr("checked", "true");
		}
	}else{
		$("#vehicleckTemp").attr("disabled", "true");
		$("#vehicleckTemp").attr("checked", "");
	}
	*/
}

function changeAbrd(value, type){
	if(value == "PT003"){
		var comboVal2='<option role="option" value="">선택</option>';
		
		$.each(eval(comboVal),function(targetNm,optionData){

			if(targetNm == "nat_g_scn_cd_temp"){
				$.each(eval(optionData),function(index,optionData){
					comboVal2 += '<option role="option" value="' +                                                   
		            optionData.value + '">' +                                                   
					optionData.name + '</option>';
				});		
				
				$("select#nat_g_scn_cd_temp").html(comboVal2);
			}
		});
		
		$("#oversee_area").hide();
		$("#oversee_text").text("Remark");
		
	}else if(value == "PT001"){
		var comboVal2 = '';
		
		$.each(eval(comboVal),function(targetNm,optionData){

			if(targetNm == "nat_g_scn_cd_temp"){
				$.each(eval(optionData),function(index,optionData){
					if(optionData.key == "PT001"){
						comboVal2 += '<option role="option" value="' +                                                   
						optionData.value + '">' +                                                   
						optionData.name + '</option>';
					}
				});		
				
				$("select#nat_g_scn_cd_temp").html(comboVal2);
			}
		});
		
		$("#nat_g_scn_cd_temp").attr("disabled", true);
	}else{
		getNatMultiComboValue('nat_g_scn_cd_temp', value);
	}
	
	if(value == "PT001"){
		changeCountryGroup($("#nat_g_scn_cd_temp").val());
	}else{
		commonSelectClear('dest_nat_cd_temp');
	}
	
	if(type == "Y"){
		if(value == "PT001"){
			$("#budg_no").val("51021010");
		}else if(value == "PT002"){
			$("#budg_no").val("51021030");
		}else{
			$("#budg_no").val("");
		}
	}
}

function changeCountryGroup(value){
	getNatMultiComboValue('dest_nat_cd_temp', value);
}

function getNatMultiComboValue(comboName, keyName){
	var comboVal2='<option role="option" value="">선택</option>';
	
	$.each(eval(comboVal_nat),function(targetNm,optionData){

		if(targetNm == comboName){
			$.each(eval(optionData),function(index,optionData){
				if(optionData.key == keyName){
					comboVal2 += '<option role="option" value="' +                                                   
		            optionData.value + '">' +                                                   
					optionData.name + '</option>';
				}
			});		
			
			$("select#" + targetNm).html(comboVal2);
		}
    });
}


function doBudget(value){
	var url = "doBudgetInfoToRequest.do";
	
	var	budgetInfo = {
			if_id     : $("#if_id").val(),
			updr_eeno : sess_empno,
			pgs_st_cd : "C",
			ops_cd	  : $("#ops_cd").val(),
			eeno	  : $("#eeno").val(),
			poa_nm	  : $("#poa_nm").val(),
			ops_nm	  : $("#ops_nm").val()
		};
	
	
	var paramData = {
		bsicInfo : util.jsonToString(bsicInfo)
	};
	doCommonAjax(url, paramData, "budgetCallBack(jsonData.sendResult);");	
}

function budgetCallBack(result){
	alertUI(result.message);
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "businessTravel_file.gas", width = "460", height = "450";
		
	if($("#hid_doc_no").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val($("#hid_doc_no").val());
	}
	$("#file_eeno").val("00000000");
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

var win;
function doFileAttachCmpx(type){
	if(win != null){ win.close(); }
	var url = "businessTravel_file_cmpx.gas", width = "460", height = "450";
		
	if($("#hid_doc_no").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val($("#hid_doc_no").val());
	}
	$("#file_eeno").val("00000000");
	
	$("#file_doc_no").val(type+$("#file_doc_no").val());
	
	win = newPopWin("about:blank", width, height, "win_file");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

function doBack(){
	var form = $("<form/>");
    form.attr("method" , "post");
    form.attr("id"     , "submitForm").attr("name", "submitForm");
    form.attr("action" , "businessTravelList.gas");
    var inp1 = $("<input type='hidden' id='hid_cond' name='hid_cond'/>").val($("#hid_cond").val());
    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
    form.append(inp1, token);
    $("body").append(form);
    form.submit();
    form.remove();
}

function doCheck(){
	var paramList = [];
	
	var bsicInfo = {
		mode : "check"
	};
	
	if($("#pgs_st_cd").val() != "Z"){
		alertUI("You can't check in this status");
		return;
	}
	
	list = {
		doc_no    		: $("#hid_doc_no").val(),
		ven_pgs_st_cd   : "Y",
		updr_eeno 		: "OUTCOMP"
	};
	paramList.push(list);
	
	confirmUI("Do you want to Ok?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo),
						uParams  : util.jsonToList(paramList)
					};
					doCommonAjax("doVendorCheckBTToConfirmList.do", paramData, "checkCallBack(jsonData.sendResult);");
			}
		});
	});
}

function doCheckCancel(){
	var paramList = [];
	
	var bsicInfo = {
		mode : "checkCancel"
	};
	
	if($("#pgs_st_cd").val() != "C"){
		alertUI("You can't check cancel in this status");
		return;
	}
	
	list = {
		doc_no    : $("#hid_doc_no").val(),
		pgs_st_cd : "Z",
		updr_eeno : "OUTCOMP"
	};
	paramList.push(list);
	
	confirmUI("Do you want to check cancel?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						bsicInfo : util.jsonToString(bsicInfo),
						uParams  : util.jsonToList(paramList)
					};
					doCommonAjax("doCheckBTToConfirmList.do", paramData, "checkCallBack(jsonData.sendResult);");
			}
		});
	});
}

function checkCallBack(result){
	setBottomMsg(result.message, true);
//	doSearch1("N");
}

var win1;
function doConfirmation(type){
	if(win != null){ win.close(); }
	var url = "businessTravel_confirm_file.gas", width = "460", height = "453";
	
	if($("#hid_doc_no").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val($("#hid_doc_no").val());
	}
	
	$("#file_eeno").val("00000000");
	$("#req_eeno").val($("#eeno").val());
	$("#file_doc_no").val(type+$("#file_doc_no").val());
	
	win1 = newPopWin("about:blank", width, height, "win_file1");
	document.fileForm.hid_csrfToken.value = $("#csrfToken").val();
	document.fileForm.action = url;
	document.fileForm.target = "win_file1"; 
	document.fileForm.method = "post"; 
	document.fileForm.submit();
}

function doSave(){
	var bsicInfo = {
		doc_no    	: $("#hid_doc_no").val(),
		prct_sbc	: overLineHtml(changeToUni($("#prct_sbc").val())),
		updr_eeno 	: "OUTCOMP"
	};
	
	confirmUI("저장 하시겠습니까?");
	$("#pop_yes").click(function(){
		$.unblockUI({
			onUnblock: function(){
				var paramData = {
						paramJson : util.jsonToString(bsicInfo)
					};
					doCommonAjax("doVendorCheckBTToSaveDetail.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);");
			}
		});
	});
}