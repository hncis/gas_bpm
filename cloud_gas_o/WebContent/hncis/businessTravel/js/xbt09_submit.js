var params;
var userKeyArr = ['ee_nm','ops_cd', 'ops_nm', 'poa_nm'];
var userColArr = ['xusr_name','xusr_dept_code', 'xusr_dept_name', 'xusr_step_name'];
var comboVal;
var comboVal_nat;
var congryGroupVal;

function fnSetDocumentReady(){
	$(".sub_title").hide();

	setComboInfo();
}

var url = "", gridName1 = "travelers", gridName2 = "schedule", gridName3 = "flightConfirm", gridName4 = "hotelConfirm", gridName5 = "rentCar", gridName6 = "travelers_vt";
var datarow = {doc_no:"", seq:"", pgs_st_cd:"", eeno:"", ee_nm:"", ops_cd:"", ops_nm:"", poa_nm:"", visa_yn:"", cost_cd:"", flht_utz_amt:"", bztp_lodg_exp:"", rent_amt:"", vs_ptt_exp:"",etc_amt:"", pgs_st_nm:"", cost_info:"",pass_country:"",pass_number:"",birth_ymd:"",issue_ymd:"",expire_ymd:""};
var flight_datarow = {flt_doc_no:"",flt_seq:"", flt_flt_ymd:"", flt_air_lines:"", flt_flight:"", flt_flt_from:"", flt_flt_to:"", flt_departure_time:"", flt_arrive_time:"", flt_seat:"", flt_etc_amt:""};
var hotel_datarow  = {htl_doc_no:"",htl_seq:"", htl_check_in_ymd:"", htl_check_out_ymd:"", htl_hotel_name:"", htl_hotel_addr:"", htl_hotel_phone:"", htl_pay_form:"", htl_confirmation:"", htl_meal_yn:"", htl_etc_amt:""};
var rent_datarow   = {rent_doc_no:"",rent_seq:"", rent_check_in_ymd:"", rent_check_out_ymd:"", rent_car_company:"", rent_car_type:"", rent_car_phone:"", rent_pay_form:"", rent_confirmation:"", rent_air_yn:"", rent_etc_amt:""};

function setComboInfo(){
	var url        = "doComboListToRequest.do";
	var paramValue    = "dom_abrd_scn_cd:place_type:S;";
	getCommonCode(paramValue, "N", "setMultiComboInfo();", url);
}

function setComboInfoCallBack(jsonData){
	comboVal = jsonData;
	comboVal_nat = jsonData;

	readOnlyStyle("eeno", 1);
	readOnlyStyle("ee_nm", 1);
	readOnlyStyle("poa_nm", 1);
	readOnlyStyle("ops_nm", 1);
	readOnlyStyle("ptt_ymd", 1);
	readOnlyStyle("doc_no", 1);
	readOnlyStyle("pgs_st_nm", 1);
	readOnlyStyle("dest_dtl_sbc", 1);
	readOnlyStyle("vsit_purp_sbc", 1);
	readOnlyStyle("strt_ymd", 1);
	readOnlyStyle("fnh_ymd", 1);
	readOnlyStyle("tel_no", 1);

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

	$("#"+gridName1).GridUnload();
	cn = ["Doc No.", "SEQ", "PGS_ST_CD", "사번", "이름", "DepartmentCode", "부서", "직급", "비자", "Cost<BR>Center", "항공티켓", "숙박", "렌트카", "비자","기타", "진행상태", "후정산", "여권", "여권번호", "출생일", "발행일", "만료일"];
	cm = [
			{name:"doc_no",		index:"doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"seq",		index:"seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"pgs_st_cd",	index:"pgs_st_cd"	, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"eeno",		index:"eeno"		, sortable:false,	formatter:"string",		width:80,	align:"left",	editable:false,	frozen : false
			},
			{name:"ee_nm",		index:"ee_nm"		, sortable:false,	formatter:"string",		width:142,	align:"left",	editable:false,	frozen : false},
			{name:"ops_cd",		index:"ops_cd"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"ops_nm",		index:"ops_nm"		, sortable:false,	formatter:"string",		width:160,	align:"left",	editable:false,	frozen : false},
			{name:"poa_nm",		index:"poa_nm"		, sortable:false,	formatter:"string",		width:150,	align:"left",	editable:false,	frozen : false},
			{name:'visa_yn',	index:'visa_yn'		, sortable:false,	formatter:"select",		width:55, 	align:"center",	editable:false, 	edittype:'select',
				editoptions:{value:"Y:Y;N:N"
		    	,dataInit: function(elem) {
	        	$(elem).width(65);
	    		}}
			},
			{name:"cost_cd",	index:"cost_cd"		, sortable:false,	formatter:"string",		width:80,	align:"center",	editable:false,	frozen : false, hidden : true, editoptions:{maxlength:"8"}},
			{name:"flht_utz_amt",	index:"flht_utz_amt"	, sortable:false,formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:0,
			    defaultValue:""
			  },		width:75,	align:"right",	editable:false,	frozen : false
			  },
			  {name:"bztp_lodg_exp",	index:"bztp_lodg_exp"	, sortable:false,formatter: 'currency', formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:0,
				    defaultValue:""
				  },		width:75,	align:"right",	editable:false,	frozen : false
				  },
			  {name:"rent_amt",	index:"rent_amt"	, sortable:false,formatter: 'currency', formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:0,
				    defaultValue:""
				  },		width:75,	align:"right",	editable:false,	frozen : false
				  },
			  {name:"vs_ptt_exp",	index:"vs_ptt_exp"	, sortable:false,formatter: 'currency', formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:0,
				    defaultValue:""
				  },		width:55,	align:"right",	editable:false,	frozen : false
				  },
			  {name:"etc_amt",	index:"etc_amt"	, sortable:false,formatter: 'currency', formatoptions: {
				    decimalSeparator:",",
				    thousandsSeparator:".",
				    decimalPlaces:0,
				    defaultValue:""
				  },		width:65,	align:"right",	editable:false,	frozen : false
				  },
			{name:"pgs_st_nm",	index:"pgs_st_nm"	, sortable:false,	formatter:"string",		width:65,	align:"center",	editable:false,	frozen : false},

			{name:"cost_info",	index:"cost_info"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"pass_country",	index:"pass_country", sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"pass_number",	index:"pass_number"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false},
			{name:"birth_ymd",		index:"birth_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false,
				editoptions: {
					readonly : true,
		            dataInit: function(element) {
		     		    $(element).datepicker({
		     		    	dateFormat: 'yy-mm-dd',
		     		    	onSelect: function(dataText, inst){
		     		    	}
				    	});
		            }
				}
			},
			{name:"issue_ymd",		index:"issue_ymd"	, sortable:false,	formatter:"string",		width:60,	align:"center",	editable:false,	frozen : false, hidden:true,
				editoptions: {
					readonly : true,
		            dataInit: function(element) {
		     		    $(element).datepicker({
		     		    	dateFormat: 'yy-mm-dd',
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
		     		    	dateFormat: 'yy-mm-dd',
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
			width        : "1122",
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : true,
			fnMerge      : false,
			completeFc   : "addGridRow(3, '"+gridName1+"', 'datarow');setTravelerList();",
			paramJson    : ""
		}]
	};

	commonJqGridInit(gridParam);

	$("#"+gridName3).GridUnload();
	flight_cn = ["", "", "일시","항공노선","항공번호","출발지","도착지","출발시간","도착시간","Seat","금액"];
	flight_cm = [
			{name:"flt_doc_no"			, index:"flt_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"flt_seq"				, index:"flt_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"flt_flt_ymd"			, index:"flt_flt_ymd"		, sortable:false,	formatter:"string",		width:90,	align:"center",	editable:true,	frozen : false,
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
			{name:"flt_air_lines"		, index:"flt_air_lines"		, sortable:false,	formatter:"string",		width:210,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flight"			, index:"flt_flight"		, sortable:false,	formatter:"string",		width:130,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flt_from"		, index:"flt_flt_from"		, sortable:false,	formatter:"string",		width:130,	align:"left",	editable:true,	frozen : false},
			{name:"flt_flt_to"			, index:"flt_flt_to"		, sortable:false,	formatter:"string",		width:130,	align:"left",	editable:true,	frozen : false},
			{name:"flt_departure_time"	, index:"flt_departure_time", sortable:false,	formatter:"string",		width:100,	align:"left",	editable:true,	frozen : false,
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
			{name:"flt_arrive_time"		, index:"flt_arrive_time"	, sortable:false,	formatter:"string",		width:185,	align:"left",	editable:true,	frozen : false},
			{name:"flt_seat"			, index:"flt_seat"			, sortable:false,	formatter:"string",		width:112,	align:"left",	editable:true,	frozen : false, hidden:true},
			{name:"flt_etc_amt"			, index:"flt_etc_amt"		,formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:80,	align:"right",	editable:true,	frozen : false,
				editoptions: {maxlength:"10",
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ".");
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
			width        : "1122",
			height       : "100%",
			sortname     : "seq",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(4, '"+gridName3+"', 'flight_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};

	commonJqGridInit(flight_gridParam);

	$("#"+gridName4).GridUnload();
	hotel_cn = ["", "", "체크인","체크아웃","숙박","주소","연락처","지불양식","확인","아침식사","금액"];
	hotel_cm = [
			{name:"htl_doc_no"			, index:"htl_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"htl_seq"				, index:"htl_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:false,	frozen : false, hidden : true},
			{name:"htl_check_in_ymd"	, index:"htl_check_in_ymd"	, sortable:false,	formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false,
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
			{name:"htl_check_out_ymd"	, index:"htl_check_out_ymd"	, sortable:false,	formatter:"string",		width:100,	align:"center",	editable:false,	frozen : false,
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
			{name:"htl_hotel_name"		, index:"htl_hotel_name"	, sortable:false,	formatter:"string",		width:150,	align:"left",	editable:false,	frozen : false},
			{name:"htl_hotel_addr"		, index:"htl_hotel_addr"	, sortable:false,	formatter:"string",		width:270,	align:"left",	editable:false,	frozen : false},
			{name:"htl_hotel_phone"		, index:"htl_hotel_phone"	, sortable:false,	formatter:"string",		width:135,	align:"left",	editable:false,	frozen : false},
			{name:"htl_pay_form",index:"htl_pay_form", formatter:"select", width:120, align:"center", editable:false, sortable:true, edittype:"select", fixed: true,
				editoptions:{value:getComboValue('htl_pay_form'),
					dataInit: function(elem) {
						$(elem).width(120);
					}
				}
			},
			{name:"htl_confirmation"	, index:"htl_confirmation"	, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:false,	frozen : false, hidden:true},
			{name:"htl_meal_yn"			, index:"htl_meal_yn"		, sortable:false,	formatter:"select",		width:100, 	align:"center",	editable:false, 	edittype:'select',
				editoptions:{value:"Y:Y;N:N"
			    	,dataInit: function(elem) {
		        	$(elem).width(60);
		    		}
				}
			},
			{name:"htl_etc_amt"			, index:"htl_etc_amt"		, formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:80,	align:"right",	editable:false,	frozen : false,
				editoptions: {maxlength:"10",
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ".");
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
			width        : "1122",
			height       : "100%",
			sortname     : "seq",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(1, '"+gridName4+"', 'hotel_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};

	commonJqGridInit(hotel_gridParam);

	$("#"+gridName5).GridUnload();
	rent_cn = ["", "", "체크인","체크아웃","회사","구분","연락처","지불양식","비고","차종","금액"];
	rent_cm = [
			{name:"rent_doc_no"			, index:"rent_doc_no"		, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"rent_seq"			, index:"rent_seq"			, sortable:false,	formatter:"string",		width:0,	align:"left",	editable:true,	frozen : false, hidden : true},
			{name:"rent_check_in_ymd"	, index:"rent_check_in_ymd"	, sortable:false,	formatter:"string",		width:100,	align:"center",	editable:true,	frozen : false,
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
			{name:"rent_check_out_ymd"	, index:"rent_check_out_ymd", sortable:false,	formatter:"string",		width:100,	align:"center",	editable:true,	frozen : false,
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
			{name:"rent_car_company"	, index:"rent_car_company"	, sortable:false,	formatter:"string",		width:165,	align:"left",	editable:true,	frozen : false},
			{name:"rent_car_type"		, index:"rent_car_type"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false},
			{name:"rent_car_phone"		, index:"rent_car_phone"	, sortable:false,	formatter:"string",		width:140,	align:"left",	editable:true,	frozen : false},
			{name:"rent_pay_form"		, index:"rent_pay_form"		, sortable:false,	formatter:"string",		width:120,	align:"left",	editable:true,	frozen : false, hidden:true},
			{name:"rent_confirmation"	, index:"rent_confirmation"	, sortable:false,	formatter:"string",		width:250,	align:"left",	editable:true,	frozen : false},
			{name:"rent_air_yn"			, index:"rent_air_yn"		, sortable:false,	formatter:"select",		width:80, 	align:"center",	editable:true, 	edittype:'select',
				editoptions:{value:getComboValue('rent_air_yn'),
					dataInit: function(elem) {
						$(elem).width(60);
					}
				}
			},
			{name:"rent_etc_amt"		, index:"rent_etc_amt"		, formatter: 'currency', formatoptions: {
			    decimalSeparator:",",
			    thousandsSeparator:".",
			    decimalPlaces:2,
			    defaultValue:""
			  },		width:100,	align:"right",	editable:true,	frozen : false,
				editoptions: {maxlength:"10",
		            dataInit: function(element) {
		     		    $(element).keyup(function(){
		     		    	if(!isNumeric(element.value)){
		     		    		element.value = selectNum(element.value, ".");
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
			width        : "1122",
			height       : "100%",
			sortname     : "eeno",
			sortorder    : "asc",
			rownumbers   : true,
			multiselect  : false,
			cellEdit     : false,
			fnMerge      : false,
			completeFc   : "addGridRow(1, '"+gridName5+"', 'rent_datarow');initAfterMenus();",
			paramJson    : ""
		}]
	};

	commonJqGridInit(rent_gridParam);

	setGridColumnOptions();

	jQuery("#"+gridName1).jqGrid('setGroupHeaders', {
		  useColSpanStyle: true,
		  groupHeaders:[
			{startColumnName: 'flht_utz_amt', numberOfColumns: 5, titleText: '금액'}
		  ]
		});

	if($("#if_id").val() == "") return;
	else doSearch1();
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
			cost_cd   : "",
			corp_cd	  : sess_corp_cd
		};

		jQuery("#"+gridName1).jqGrid("setRowData", 1, reqTravelInfo);
	}
}

function setChangeImg(){

	var gridRow  = jQuery("#"+gridName1);
	var ids      = gridRow.getDataIDs();

	for(var i=0;i<ids.length;i++){

		if(getColValue("doc_no", i+1, gridName1)!=""){
			var imgSrc = "<img src='../../images/hncis_bttn/open-n.gif' onClick='doPayReportOpen("+getColValue("eeno", i+1, gridName1)+");'/>";
			gridRow.jqGrid("setRowData", i+1, {cost_info:imgSrc});
		}
	}
}

var win;
function doPayReportOpen(eeno){
	if(win != null){ win.close(); }
	var url = "xbt01_report_app.gas", width = "1000", height = "650";

	$("#hid_report_doc_no").val($("#doc_no").val());
	$("#hid_report_eeno").val(eeno);

	win = newPopWin("about:blank", width, height, "win_report");
	document.reportForm.action = url;
	document.reportForm.target = "win_report";
	document.reportForm.method = "post";
	document.reportForm.submit();
}

function doSearch1(msgFlag){
	var params = {
		if_id  : $("#if_id").val(),
		locale : sess_locale
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
		h_gubn : "H",
		corp_cd	: sess_corp_cd
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
		doc_no : $("#doc_no").val(),
		corp_cd	: sess_corp_cd
	};

	doCommonSearch("doSearchListBTToRequestByFilghtConfirmation.do", util.jsonToString(params), "loadCallBack4();", gridName3, "N");
}

function doSearch5(){
	var params = {
		doc_no : $("#doc_no").val(),
		corp_cd	: sess_corp_cd
	};

	doCommonSearch("doSearchListBTToRequestByHotelConfirmation.do", util.jsonToString(params), "loadCallBack5();", gridName4, "N");
}

function doSearch6(){
	var params = {
		doc_no : $("#doc_no").val(),
		corp_cd	: sess_corp_cd
	};

	doCommonSearch("doSearchListBTToRequestByRentCar.do", util.jsonToString(params), "loadCallBack6();", gridName5, "N");
}

function doSearch7(){
	var params = {
		doc_no : $("#doc_no").val(),
		h_gubn : "O",
		corp_cd	: sess_corp_cd
	};

	doCommonSearch("doSearchListBTToRequestByVirtualTraveler.do", util.jsonToString(params), "loadCallBack7();", gridName6, "N");
}

function loadCallBack1(result, msgFlag){
	loadJsonSet(result);
	if(msgFlag == "Y") setBottomMsg(result.message, false);

	changeAbrd($("#dom_abrd_scn_cd").val());
	$("#nat_g_scn_cd_temp").val(trimChar($("#nat_g_scn_cd").val()));

	changeCountryGroup($("#nat_g_scn_cd_temp").val());
	$("#dest_nat_cd_temp").val(trimChar($("#dest_nat_cd").val()));

//	if($("#pgs_st_cd").val() != "0" && $("#if_id").val() != ""){
		displaySubmit(document ,result.code, 1);
//	}else{
//		displaySubmitClear(document);
//	}

	$("#cost_cd").val(result.cost_cd);
//	$("#budg_type").val(result.budg_type);
//	if(result.budg_type == "D"){
//		$("#budg_no").attr("style", "width:120px;");
//		$("#budg_no").val(result.budg_no);
//		$("#budg_wbs").attr("style", "display:none;");
//		$("#budg_ipt").attr("style", "display:none;");
//	}else if(result.budg_type == "I"){
//		$("#budg_no").attr("style", "width:120px;");
//		$("#budg_no").val(result.budg_no);
//		$("#budg_wbs").attr("style", "display:none;");
//		$("#b_ipt").html("<input type='text' id='budg_ipt' style='width:120px;' class='disabled' readOnly>");
//		$(".iptOnlyNumber").numeric();
//	}else if(result.budg_type == "W"){
//		$("#budg_no").attr("style", "display:none;");
//		$("#budg_wbs").attr("style", "width:120px;");
//		$("#budg_wbs").val(result.budget_no);
//		$("#b_ipt").html("<input type='text' id='budg_ipt' style='width:120px;' class='disabled' readOnly>");
//	}
//	$("#budg_ipt").val(result.budg_text);

	doSearch2();
}

function loadCallBack2(){
	addGridRow(3, gridName1, 'datarow');

	$("#cost_cd").val(getColValue("cost_cd", 1, gridName1));

	doSearch4();
}

function loadCallBack3(){
	addGridRow(3, gridName2, 'schedule_datarow');
	doSearch4();
}

function loadCallBack4(){
	addGridRow(4, gridName3, 'flight_datarow');
	doSearch5();
}

function loadCallBack5(){
	addGridRow(1, gridName4, 'hotel_datarow');
	doSearch6();
}

function loadCallBack6(){
	addGridRow(1, gridName5, 'rent_datarow');
	doSearch7();
	initAfterMenus();
}

function loadCallBack7(){
	addGridRow(3, gridName6, 'vt_datarow');
//	setVtChangeImg();
	initAfterMenus();
}

var win;
function doPrint(){

	if(win != null){ win.close(); }

	var url = "xbt01_print.gas", width = "1", height = "1";

	win = newPopWin("about:blank", width, height, "win_mepp_print0");
	document.mealForm.action = url;
	document.mealForm.target = "win_mepp_print0";
	document.mealForm.method = "post";
	document.mealForm.submit();
}

function changeAbrd(value){
	getNatMultiComboValue('nat_g_scn_cd_temp', value);
	commonSelectClear('dest_nat_cd_temp');
}

function changeCountryGroup(value){
	getNatMultiComboValue('dest_nat_cd_temp', value);
}


function getNatMultiComboValue(comboName, keyName){
	var comboVal2='<option role="option" value="">선택</option>';

	$.each(eval(comboVal_nat),function(targetNm,optionData){

		if(targetNm == comboName){
			$.each(eval(optionData),function(index,optionData){
//				if(optionData.key == keyName){
					comboVal2 += '<option role="option" value="' +
		            optionData.value + '">' +
					optionData.name + '</option>';
//				}
			});

			$("select#" + targetNm).html(comboVal2);
		}
      });
}

var win;
function doFileAttach(){
	if(win != null){ win.close(); }
	var url = "xbt01_file_app.gas", width = "460", height = "367";

	if($("#hid_doc_no").val() == ""){
		$("#file_doc_no").val($("#temp_doc_no").val());
	}else{
		$("#file_doc_no").val($("#hid_doc_no").val());
	}
	$("#file_eeno").val("00000000");
	$("#hid_doc_no").val($("#doc_no").val());
	$("#hid_eeno").val("00000000");

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
	var url = "xbt01_file_cmpx_app.gas", width = "460", height = "367";

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