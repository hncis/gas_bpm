	var params; 
	var comboVal;
	var comboVal1;
	var lastsel;
	var fnMerge;
	var gridParam;
	
	
	var datarow = {firm_cd:"",firm_nm:"",use_yn:"",ipe_eeno:"",inp_ymd:"",updr_eeno:"",mdfy_ymd:"",seq:""}; 
	var excelCn = ['Code', 'Name','Use Y/N','Enter ID','Enter Date','Update ID','Update Date'];   
	
	function fnSetDocumentReady(){
		initMenus();
		$('#PS').slideDown('fast');
		
		init();    
	}
	
	function init(){
		
		var params = { 
				firm_nm		: $('#keyName').val(),
				affr_scn_cd : 'PS',
				corp_cd		: sess_corp_cd
		};
		
		cn =['코드', '이름','사용여부 Y/N','입력 아이디','입력일자'
  		     ,'수정 아이디','수정일자','Seq'
  		     ], 
		  
		cm = [
				{name:'firm_cd',index:'firm_cd', formatter:'string', width:120,align:'left',editable:true,sortable:false},
				{name:'firm_nm',index:'firm_nm', formatter:'string',width:295,align:'left',editable:true,sortable:false},
				{name:'use_yn',index:'use_yn',width:90,align:'center',editable:true,sortable:false,
					edittype:'select', formatter: "select",editoptions:{value:"Y:Y;N:N"}},  
				{name:'ipe_eeno',index:'ipe_eeno', formatter:'string',width:110,align:'center',editable:false,sortable:false},
				{name:'inp_ymd',index:'inp_ymd', formatter:'string',width:110,align:'center',editable:false,sortable:false},
				{name:'updr_eeno',index:'updr_eeno', formatter:'string',width:110,align:'center',editable:false,sortable:false},
				{name:'mdfy_ymd',index:'mdfy_ymd', formatter:'string',width:110,align:'center',editable:false,sortable:false},
				{name:'seq',index:'seq', formatter:'string',hidden:true, width:120,align:'left',editable:true,sortable:false}
			],
		     
			gridParam = {
				viewEdit : [{
					gridName     : "htmlTable",
					url          : "doSearchGridPsToAgencyManagement.do",
					colNames     : cn,
					colModel     : cm,
					height       : "100%",
					sortname     : "firm_nm",
					sortorder    : "desc",
					rowNum		 : 15,
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
		
		setGridColumnOptions();
		
		jQuery("#htmlTable").jqGrid('navGrid',"#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
		
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
		   case "addrow" :
				fnAddRow();
				break;
		}
	}
	function doSearch(msgFlag){
		params = {
				firm_nm		: $('#keyName').val(),
				affr_scn_cd : 'PS',
				corp_cd		: sess_corp_cd
		};
		doCommonSearch("doSearchGridPsToAgencyManagement.do", util.jsonToString(params), "loadCallBack();initAfterMenus();", "htmlTable", msgFlag);
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
				if(getColValue("firm_cd",rowId) == ''){
					alertUI(rowId + " Line : 코드를 입력하세요.");
					return;
				}
				if(getColValue("firm_nm",rowId) == ''){
					alertUI(rowId + " Line : 이름을 입력하세요.");
					return;
				}
				data =
				{
						affr_scn_cd		: 'PS',
						firm_cd			: getColValue("firm_cd",rowId),
						firm_nm			: changeToUni(getColValue("firm_nm",rowId)),
						use_yn			: getColValue("use_yn",rowId),
						ipe_eeno		: sess_empno,
						updr_eeno		: sess_empno,
						seq				: getColValue("seq",rowId),
						corp_cd			: sess_corp_cd
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
						doCommonAjax("doInsertPsToAgencyManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
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
		
		for(var i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId)	{
				if(getColValue("seq",rowId) == ""){
					alertUI(rowId + " line : 데이터가 없습니다.");
					return;
				}
				data =
				{
						seq				: getColValue("seq",rowId),
						affr_scn_cd 	: 'PS',
						corp_cd			: sess_corp_cd
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
						doCommonAjax("doDeletePsToAgencyManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
				}
			});
		});
	}	
	
	/**
	 * addRow $.IDs.length + 1
	 * datarow colModel keyName:"", ....
	 */
	function addRow(){
		var gridRowId = jQuery("#htmlTable").getDataIDs().length;
		for(var i = 1; i <= 10 - gridRowId ; i++){
			jQuery("#htmlTable").jqGrid("addRowData", gridRowId+i, datarow);
		}
	}
	
	function fnAddRow(){
		var gridRowId = $("#htmlTable").getDataIDs().length;
		jQuery("#htmlTable").jqGrid("addRowData", gridRowId+1, datarow);
	}
	