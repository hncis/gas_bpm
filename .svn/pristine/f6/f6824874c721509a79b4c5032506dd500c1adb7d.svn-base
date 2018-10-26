var tmpBoardType = "1";

function fnSetDocumentReady(){
	$(".sec_tab_con").hide(); 
	$("ul.sec_tab li p").hide();
	$("ul.sec_tab li:first p").show();
	$(".sec_tab_con:first").show();
	$("#loc").val($("#lo").val());
	
	$("ul.sec_tab li").click(function() { 
		$("ul.sec_tab li").removeClass("on");
		$(this).addClass("on");
		$("ul.sec_tab li p").hide();
		$(this).children("p").show();
		$(".sec_tab_con").hide(); 
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).show(); 
	});
	
	var images = $(".assort_ban");
	/*var t = 3000;*/
	var e = 400;

	function next() {
		
		images.stop(false, true).animate({
			left : "-221px"
			/*left : "-276px"*/
		}, e, function() {
			$(this).children(":first").insertAfter( // last �ㅼ뿉 first �쎌엯
			$(this).children(":last"));

			$(this).css({
				left : "0"
			});
		});
	}

	function prev() {
		images.children(":last").insertBefore(images.children(":first")); // first �댁쟾��last �쎌엯*/
		images.css({
			left : "-221px"
			/*left : "-276px"*/
		});
		images.stop(true, false).animate({
			left : "0"
		}, e, function() {
			$(this).css({
				left : "0"
			});
		});
	}

	$(".ban_prv").click(function() {
		prev();
	});
	
	$(".ban_next").click(function() {
		next();
	});
//	onstart();
//	setInterval("getDateCal()", 1000);
//	setComboInfo();
//	initWhether();
	initMainMenus('805');
	
	// �щ젰 �꾩옱 �좎쭨 蹂댁뿬二쇨린
	var d = new Date();
	$("#tbSelYear").val(d.getFullYear());
	$("#tbSelMonth").val(d.getMonth()+1);
	retrieve($("#tbSelYear").val(),$("#tbSelMonth").val()); 
	
//	if(getCookie("event_pop") == ""){
//		eventPop();
//	}
}

var plusTime = 0;
var days = ["SUN","MON","TUE","WED","THU","FRI","SAT"];
var weather_code, weather_temp, weather_day1, weather_high1, weather_day2, weather_high2;

function setComboInfo(){
	var url        = "/getCommonCombo.do";
	var pgs_st_cd  = "nationCo:X1012:Z;cityCo:X1013:Z;";
	var paramValue = pgs_st_cd;
	getCommonCode(paramValue, "N", "comboCallBack();", url);
}

function comboCallBack(){
	plusTime = $("#cityCo").val();
}

function nationCoChange(objValue){	
	setComboNationInfo(objValue);
}

function setComboNationInfo(kndValue){
	var url        = "/getCommonCombo.do";
	var pgs_st_cd  = "cityCo:"+kndValue+":Z;";
	var paramValue = pgs_st_cd;
	getCommonCode(paramValue, "N", "naCoCallBack();", url);
}

function naCoCallBack(){
	plusTime = $("#cityCo").val();

}

function initWhether(){
	//var url="http://www.google.co.kr/ig/api?weather="+$("#city").val()+"&ie=utf-8&oe=utf-8&hl=en";
	var url="http://weather.yahooapis.com/forecastrss?p="+$("#city").val()+"&u=c";
	
	try{
		if(window.ActiveXObject){
			var req = new ActiveXObject("Microsoft.XMLHTTP");
		}else{
			var req = new XMLHttpRequest();
		}
				
		req.open("GET", url, true);
		req.onreadystatechange = function(res){
			if(req.readyState == 0){return;};
			if(req.readyState == 4){
				if(req.status == 200){
					var xml = new rssXmlParser(req.responseXML);
				    view_weather();
				}else if(req.status == 404){
					alertUI("URL does not exist..");
				}
			}else{}
		};
		req.send(null);
	}catch(e){
		//alert(e);
	}
}

function view_weather(){
		
	try{
		var today = new Date();
		
		var array_day = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
		var dayInfo = array_day[today.getDay()];
		var weather_high_temp = weather_high1;
		
		if(dayInfo == weather_day1){
			weather_high_temp = weather_high1;
		}else{
			weather_high_temp = weather_high2;
		}
		/* weather setting */
		document.getElementById("weather_img").src = "http://l.yimg.com/a/i/us/we/52/"+weather_code+".gif";
		document.getElementById("now_whether").innerText = weather_temp;
		document.getElementById("highst_whether").innerText = weather_high_temp;
	}catch(e){
	}
}

function rssXmlParser(xml){
	try{
		 //node
		 var getNode =function (obj, tag){ 
		  return obj.getElementsByTagName(tag)[0]; 
		 }
	 
		 //node value 
		 var getValue = function (obj){ 
		  try{ 
		   return obj.firstChild.nodeValue; 
		  } catch(e){ 
		   return null; 
		  } 
		 }
		 //node item 
		 var getItem = function (obj){
		  var item = new Object();
		  var objLength = obj.childNodes.length;
		  	  
		  for(var j =0;j <objLength ; j++){
		   var eleItem = obj.childNodes[j];
		   item[eleItem.nodeName] =getValue(eleItem);
		  }
		  return item;
		 }
	
		 var xmlParseData = new Object();
		 var item_list = new Array();
		 var channel = getNode(xml, "channel");
		 var channelLength = channel.childNodes.length;
		 	 
		 for(var i =0;i <channelLength ; i++){
		  
		  var sNode = channel.childNodes[i];
		  
		  if(sNode.childNodes.length >1){
		   var pData = getItem(sNode);
		   
		   if(sNode.nodeName== "item"){
			   weather_code = sNode.childNodes[5].getAttribute("code");
			   weather_temp = sNode.childNodes[5].getAttribute("temp");
			   weather_day1 = sNode.childNodes[7].getAttribute("day");
			   weather_high1 = sNode.childNodes[7].getAttribute("high");
			   weather_day2 = sNode.childNodes[8].getAttribute("day");
			   weather_high2 = sNode.childNodes[8].getAttribute("high");
		   }
		   
		   if(sNode.nodeName== "image"){
		    xmlParseData.image = pData;
		   }else{
		    item_list.push(pData);
		   }
		  }else{
		   xmlParseData[sNode.nodeName] =getValue(sNode);
		  }
		  
		 }
		 xmlParseData.item = item_list;
	
		 return xmlParseData;
	}catch(e){
	}

}

function weatherChange(){
	initWhether();
}

function openBoard(type){
	var url = "";
	
	switch(type){
	case "qna" :
		url = ctxPath+"/hncis/board/xbd04.gas";
		break;
	case "faq" :
		url = ctxPath+"/hncis/board/xbd07.gas";
		break;
	}
	
	noticePopup(url);
}

var win;
function readBoard(idx){
	if(typeof(idx) == 'undefined'){
		switch(tmpBoardType){
		case "1" :
			url = ctxPath+"/hncis/board/xbd01.gas";	//NOTICE
			break;
		case "2" :
			url = ctxPath+"/hncis/board/xbd04.gas";	//QNA
			break;
		case "3" :
			url = ctxPath+"/hncis/board/xbd07.gas";	//FAQ
			break;
		case "4" :
			url = ctxPath+"/hncis/board/xbd10.gas";
			break;
		}
		noticePopup(url);
		
	}else{
		$("#hid_bod_indx").val(idx);
		
		switch(tmpBoardType){
		case "1" :
			url = ctxPath+"/hncis/board/xbd03.gas";
			break;
		case "2" :
			url = ctxPath+"/hncis/board/xbd06.gas";
			break;
		case "3" :
			url = ctxPath+"/hncis/board/xbd09.gas";
			break;
		case "4" :
			url = ctxPath+"/hncis/board/xbd12.gas";
			break;
		}
		
		var bsicInfo = { key_bod_indx : idx };
		var paramData = { bsicInfo : util.jsonToString(bsicInfo) };
		doCommonAjax("/hncis/board/doReadBDToBoard.do", paramData, "noticePopup('"+url+"');");
	}
}

var win;
function noticePopup(url){
	if(win != null){ win.close(); }
	
	var width = "1093", height = "623";
	
	win = newPopWin("about:blank", width, height, "win_notice");
	
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_notice"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function getDateCal(){
	var dte = new Date(dteGetTime + (1000*60*60*plusTime));
	
	var mm = dte.getMonth()+1;
	var dd = dte.getDate();
	var day = days[dte.getDay()];
	var hh = dte.getHours();
	var mi = dte.getMinutes();
	var ss = dte.getSeconds();
	
	if(dd < 10){ dd = "0"+dd; }
	if(hh < 10){ hh = "0"+hh; }
	if(mi < 10){ mi = "0"+mi; }
	if(ss < 10){ ss = "0"+ss; }
	
	if(Number(hh) >= 12){
		hh = "PM "+(Number(hh)-12);
	}else{
		hh = "AM "+hh;
	}
	
	//var newTime = mm+"."+dd+"("+day+") "+hh+":"+mi+":"+ss;
	var newTime = hh+":"+mi+":"+ss;
	document.getElementById("curr_time").innerHTML = newTime;

	dteGetTime = dteGetTime + (1*1000);
}

function jobMgmtPop(){
	if(win != null){ win.close(); }
	
	var url = "", width = "910", height = "600"; 
	//url = ctxPath+"/hncis/popup/jobPopup.gas";	
	url = ctxPath+"/hncis/popup/userSearchPopup.gas";
	
	win = newPopWin("about:blank", width, height, "win_job","yes");
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_job"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function shuttleBusPop(){
	if(win != null){ win.close(); }
	
	var url = "", width = "1093", height = "600";
	url = ctxPath+"/hncis/shuttleBus/xsb08.gas";
	win = newPopWin("about:blank", width, height, "win_shuttle");
		
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_shuttle"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

//�앸떒��
function mealMenuPop(){
	if(win != null){ win.close(); }
	
	var url = "", width = "1093", height = "645";
	url = ctxPath+"/hncis/board/xbd13.gas";
	win = newPopWin("about:blank", width, height, "win_meal");
	
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_meal"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function eventPop(){
	if(win != null){ win.close(); }
	
	var url = "", width = "686", height = "635";
	url = ctxPath+"/hncis/popup/event_pop.gas";
	win = newPopWin("about:blank", width, height, "win_event");
	
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_event";
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function fnChangeTabMainBoard(boardType){
	tmpBoardType = boardType;
	
	$(".sec_tab>li").removeClass("on");
	$(".sec_tab>sec_tab_con").hide();
	$("#not_tab0"+boardType+"_head").addClass("on");
	$("#not_tab0"+boardType).show();
}

function fnOzTest(){
	if(win != null){ win.close(); }
	
	var url = "", width = "500", height = "600";
	url = ctxPath+"/hncis/popup/commonOzPrint.gas";
	win = newPopWin("about:blank", width, height, "win_oz");
	
	var tmpStr = "Year=2016|Month=1|Company=FORCS";
	
	$("#OZR_FILE").val("Sample_Calendar.ozr");
	$("#hid_cond").val(tmpStr);
	
	document.hideForm.hid_csrfToken.value = $("#csrfToken").val();
	document.hideForm.action = url;
	document.hideForm.target = "win_oz"; 
	document.hideForm.method = "post"; 
	document.hideForm.submit();
}

function goDashBoard(){
	var hideForm = document.hideForm;
	hideForm.hid_csrfToken.value = $("#csrfToken").val();
	hideForm.action = ctxPath+"/hncis/dashBoard/xdb01.gas";
	document.hideForm.target = ""; 
	hideForm.submit();
}

var win;
function qnaPop(idx){
	url = ctxPath+"/hncis/board/xbd04.gas";
	noticePopup(url);
	var bsicInfo = { key_bod_indx : 2 };
	var paramData = { bsicInfo : util.jsonToString(bsicInfo) };
	doCommonAjax("/hncis/board/doReadBDToBoard.do", paramData, "noticePopup('"+url+"');");
}


var win;
function faqPop(idx){
	url = ctxPath+"/hncis/board/xbd07.gas";
	noticePopup(url);
	var bsicInfo = { key_bod_indx : 3 };
	var paramData = { bsicInfo : util.jsonToString(bsicInfo) };
	doCommonAjax("/hncis/board/doReadBDToBoard.do", paramData, "noticePopup('"+url+"');");
}

function fn_changeLoc(){
	var params =
	{
		locale 	   : $("#loc").val(),
		xusr_empno : sess_empno,
		corp_cd	   : sess_corp_cd
	};

	paramData = {
		paramJson : util.jsonToString(params)
	};

	doCommonAjax("/changeLocale.do", paramData, "fn_changeLocCallBack(jsonData.sendResult)");
	
}

function fn_changeLocCallBack(rtn){
	location.reload();
}

function download_shortCut(fileName){
	var fileInfo = {
		ogc_fil_nm   	: fileName,
		fil_nm			: fileName,
		corp_cd			: sess_corp_cd
	};
	
	var frm = document.fileForm;
	frm.fileInfo.value = util.jsonToString(fileInfo);
	frm.action = "doShortCutFileDown.do";
	frm.submit();
}