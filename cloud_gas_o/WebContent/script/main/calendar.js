var gcGray    = "#b4b4b4";
var gcToggle  = "#f2dada";
var gcBG      = "#ffffff";

var gdCurDate = new Date();
var giYear    = gdCurDate.getFullYear();
var giMonth   = gdCurDate.getMonth()+1;
var giDay     = gdCurDate.getDate();

var currYear  = gdCurDate.getFullYear();
var currMonth = gdCurDate.getMonth()+1;
var currDay   = gdCurDate.getDate();

var holiday = new Array();

/**
 * 조회  
 */
function retrieve(iYear, iMonth){
	var f = document.testForm;
	var month = iMonth+"";
	if(month.length == 1){
		month = "0" + month;
	}
	
	var paramData = {
		gubun       : "holySearch",
		ymd         : iYear + month,
		oduRegnCd   : "1"
	};
	
	doCommonAjax("/calendar.do", paramData, "loadCallBack(jsonData.sendResult, '"+iYear+"', '"+iMonth+"');");
}

function loadCallBack(result, iYear, iMonth){
	$("#returnValue").val(result.code);
	$("#flag").val(result.code1);
	if($("#returnValue").val() != ""){
		holiday = $("#returnValue").val().split(",");
		for(var i=0; i<holiday.length; i++){
			holiday[i] = holiday[i];
		}
	}
	
	if(result.code == ""){
		holiday = "";
	}
	fUpdateCal(iYear, iMonth);
}

function onstart(){
	if($("#ymd").val() != ""){
		giYear  = $("#year").val();
		giMonth = $("#month").val();
	}
	fSetYearMon(giYear, giMonth);
	if($("#flag").val() != "Y"){
		retrieve(giYear, giMonth);
	}
}

function fSetDate(iYear, iMonth, iDay){
	iMonth = gn_lpad(iMonth, 0, 2);
	iDay = gn_lpad(iDay, 0, 2);
	//window.returnValue = iYear+"-"+iMonth+"-"+iDay;
	//window.close();
}

function gn_lpad(Rstring, pad_str, base_len){
	with (document.testForm) { 
		var Rstring = Rstring.toString();
		var str_len = Rstring.length;
		
		for(var i=str_len ; i<= base_len; i++){
		
			if (str_len != base_len) {
				Rstring = pad_str + Rstring;
				str_len = Rstring.length;
			}
		}
		return Rstring;
	}
}

function fSetSelected(aCell){
	var iOffset = 0;
	var iYear = parseInt(testForm.tbSelYear.value);
	var iMonth = parseInt(testForm.tbSelMonth.value);
	try{
		aCell.bgColor = gcBG; 
		with (aCell.children["cellText"]){
			var iDay = parseInt(innerText);
			
			if(color==gcGray || color==gcGray.toLowerCase()){
				if(iDay > 22) iOffset = -1;
				else if(iDay < 15) iOffset = 1;
				//iOffset = (Victor<10)?-1:1;
				iMonth += iOffset;
			}
			if(iMonth<1){
				iYear--;
				iMonth = 12;
			}else if (iMonth>12){
				iYear++;
				iMonth = 1;
			}
		}
	}catch(e){
	}
	//fSetDate(iYear, iMonth, iDay);
}

function fBuildCal(iYear, iMonth){
	with (document.testForm) { 
		var aMonth=new Array(); 
		for(i=1;i<7;i++) 
			aMonth[i]=new Array(i); 
		
		var dCalDate=new Date(iYear, iMonth-1, 1); 
		var iDayOfFirst=dCalDate.getDay(); 
		var iDaysInMonth=new Date(iYear, iMonth, 0).getDate(); 
		var iOffsetLast=new Date(iYear, iMonth-1, 0).getDate()-iDayOfFirst+1; 
		var iDate = 1; 
		var iNext = 1; 
		
		if(iDayOfFirst <0){
			iDayOfFirst = 7+iDayOfFirst;
		}
		for (d = 0; d < 7; d++) 
			aMonth[1][d] = (d<iDayOfFirst)?-(iOffsetLast+d):iDate++; 
		for (w = 2; w < 7; w++) 
			for (d = 0; d < 7; d++) 
				aMonth[w][d] = (iDate<=iDaysInMonth)?iDate++:-(iNext++); 
		return aMonth; 
	}
}

// calendar Drawing (not use)
function fDrawCal(iYear, iMonth, iCellWidth, iDateTextSize){
	var styleTD = " bgcolor='"+gcBG+"' width='"+iCellWidth+"' bordercolor='"+gcBG+"' valign='middle' align='center'  style='font: "+iDateTextSize+" Arial;";
	var html = "";
	for (w = 1; w < 7; w++) {
		html += "<tr> "; 
			for (d = 0; d < 7; d++) { 
				html += " <td id='calCell' "+styleTD+" cursor:hand;' onMouseOver='this.bgColor=gcToggle' onMouseOut='this.bgColor=gcBG' onclick='fSetSelected(this)'> "; 
				html += " <font id='cellText'> </b> ";
				html += " </td> ";
			}
			html += " </tr> ";
	}
}

// date setting
function fUpdateCal(iYear, iMonth){
	with(document.testForm){
		myMonth = fBuildCal(iYear, iMonth); 
		var i = 0; 
		for (w = 0; w < 6; w++) {
			for (d = 0; d < 7; d++) {
				with (cellText[(7*w)+d]) {
					className = "";
					Victor = i++;
					with(calCell[(7*w)+d]){
						title = "";
					}
					if (myMonth[w+1][d]<0) { 
						color = gcGray; 
						innerText = -myMonth[w+1][d]; 
					}else{
						
						color = ((d==0)||(d==6))?"red":"black";
						innerText = myMonth[w+1][d];
						// 법정공휴일인 경우 빨강색으로 변경 
						for(var i=0; i<holiday.length; i++) {
							var temp = holiday[i].split("/");
							var h_month = temp[0];
							var h_day = temp[1];
							var h_title = temp[2];          
   
							if(h_month == tbSelMonth.value && Number(h_day) == Number(innerText)) {    
								color = "red"; 
								with(calCell[(7*w)+d]){
									if( h_title != '' ){
										title = h_title;
									}
								}
							}
						} 
						if(currYear == tbSelYear.value && currMonth == tbSelMonth.value && currDay == innerText){
							with(calCell[(7*w)+d]){
								className = "tableline";
							}
						}
					}
				}
			}
		}
	}
}

// 년,월 select  setting
function fSetYearMon(iYear, iMon){
	with(document.testForm){
		tbSelMonth.options[iMon-1].selected = true;
		for(i = 0; i < tbSelYear.length; i++)
			if (tbSelYear.options[i].value == iYear)
				tbSelYear.options[i].selected = true;
		fUpdateCal(iYear, iMon);
	}
}

function fPrevMonth(){
	with(document.testForm){
		var iMon = tbSelMonth.value; 
		var iYear = tbSelYear.value; 
		
		if (--iMon<1) { 
			iMon = 12; 
			iYear--; 
		} 
		tbSelMonth.value = iMon;
		tbSelYear.value = iYear;
		retrieve(iYear, iMon);
	}
}

function fNextMonth(){
	with(document.testForm){
		var iMon = tbSelMonth.value; 
		var iYear = tbSelYear.value; 
		
		if (++iMon>12) { 
			iMon = 1; 
			iYear++; 
		}
		tbSelMonth.value = iMon;
		tbSelYear.value = iYear;
		retrieve(iYear, iMon);
	}
}