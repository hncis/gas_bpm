
var ticks = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];

var mon_arr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

function fnSetDocumentReady(){
	initMenus();
	
	
	//공통 콤보 조회
	searchDeptComb();
}

function searchDeptComb(){
	var params = {
		};
	doCommonAjax("/hncis/dashBoard/doSearchDbToDeptCombo.do", params,"deptComboCallBack(jsonData);");
}

function deptComboCallBack(jsonData){
	$.each(eval(jsonData.sendResult),function(targetNm,optionData){
		$("#keyDeptCd").loadSelect(eval(optionData));
	});
	
	doSearch();
}

function init(){
	$.jqplot.config.enablePlugins = true;

	plot1 = $.jqplot('chart1', [mon_arr], {
		title: 'Shuttle Bus',
		seriesDefaults:{
			renderer:$.jqplot.BarRenderer,
			pointLabels: { show: true }
		},
		series: [{color:'#4674A6'}],
		axes: {
			xaxis: {
				renderer: $.jqplot.CategoryAxisRenderer,
				ticks: ticks,
				label : "Monthly"
			},
			yaxis:{tickOptions:{formatString:'%i'}} 
		},
		grid: {
			background : 'white',
			gridLineColor : '#C6C6C6',
			borderColor : '#C6C6C6'
		},
		highlighter : {
			show : true
		}
	});
	plot1.redraw({resetAxes:true});

	initAfterMenus();
	if($('#initYn').val() == 'N'){
		$('#initYn').val('Y');
	}
}

function retrieve(btnFlag){
	var f = document.frm;
	switch(btnFlag){
	   case "search" :
		    doSearch();
			break;
	   case "search1" :
		    doSearch1();
			break;
	}
}
function doSearch(){
	
	//frmWait_open1();
	document.getElementById("chart1").style.display="none";
	setTimeout("doSearch1()","500");
	
}
function doSearch1(){
	var keyData = {
			key_year		: $('#keyYmdYY').val(),
			key_category	: 'SB',
			key_dept_cd		: $('#keyDeptCd').val()
	};
	paramData = {
			paramJson      	: util.jsonToString(keyData)
	};
	doCommonAjax("/hncis/dashBoard/doSearchDbToDashBoard.do", paramData,"loadCallBack(jsonData.sendResult);");
}
/**
 * callback
 */
function loadCallBack(jsonData){
	document.getElementById("chart1").style.display="";
	//frmWait_close1();
	$.each(eval(jsonData),function(targetNm,optionData){
			$.each(eval(optionData),function(index,optionData){
				if(targetNm == 'CHART'){
					mon_arr[index] = Number(optionData);
				}
				if(targetNm == 'MSG'){
					setBottomMsg(optionData, false);
				}
			});
      });
	   
	init();
	
}

function frmWait_open1(){
	window.document.getElementById("cont_dt").style.cursor	=	"wait";
	var msgW						= 280;
	var msgH						= 48;
	var leftPos = "530px";
	var topPos = "370px";
	var _WaitImage					= document.createElement("<div id='WaitImage' style='z-index:1000;position:absolute;left:"+leftPos+";top:"+topPos+";display:inline;'></div>");
	document.getElementById("cont_dt").insertBefore(_WaitImage);
	var waitImage 				= "<img src='"+ctxPath+"/images/wait_msg.gif' width="+msgW+"px height="+msgH+"px>";
	_WaitImage.innerHTML		= waitImage;
}
function frmWait_close1(){
	window.document.getElementById("cont_dt").style.cursor	=	"default";
	var waitImage 			= document.getElementById("WaitImage");
	
	waitImage.removeNode(true);
}

  