/********************Date SCRIPT********************************/
/*		실제 뿌리는 스크립트.
	setFrmYear(document.hideForm, document.hideForm.COMK_HENTR_DATE_F_YEAR, 2004);
	setFrmMonth(document.hideForm, document.hideForm.COMK_HENTR_DATE_F_MONTH, 1);
	setFrmDate(document.hideForm, document.hideForm.COMK_HENTR_DATE_F_YEAR, document.hideForm	.COMK_HENTR_DATE_F_MONTH,document.hideForm.COMK_HENTR_DATE_F_DAY, 1);


		년, 월이 변할때 일 셀렉트 박스를 셋팅(28, 29, 30, 31일)
	changeFrmDate(document.hideForm, document.hideForm.COMK_HENTR_DATE_F_YEAR, document.hideForm)
*/
	// 데이트 객채에서 년월일 가져감
	function getYear(dte){ return dte.getFullYear(); }
	function getMonth(dte){ return dte.getMonth()+1; }
	function getDate(dte){ return dte.getDate(); }

	// 폼안의 연도 셀렉트 박스에 연도 셋팅
	function setFrmYear(f, y, def){
		var i, iLoopCnt, start_year, today_year;
		iLoopCnt = 0;
		start_year = 1998; // 시작년 - 아주 중요
		today_year = getYear(new Date());
		//today_year = 2013;

		for (i=start_year; i<=today_year+3; i++){
			y.options[iLoopCnt]= new Option(i, i);
			if ((def>0)?(i==def):(i==today_year)){
				y.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 폼안의 연도 셀렉트 박스에 월 셋팅
	function setFrmMonth(f, m, def)	{
		var i, today_month;
		iLoopCnt = 0;
		today_month = getMonth(new Date());
		for (i=1; i<=12; i++){
			if(i<10)	m.options[iLoopCnt]= new Option(i, "0"+i);
			else	m.options[iLoopCnt]= new Option(i, i);

			if ((def>0)?(i==def):(i==today_month)){
				m.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 폼안의 연도 셀렉트 박스에 연도 셋팅
	function setYearCombo(y, def, type){
		var i, start_year, today_year;
		start_year = 2010; // 시작년 - 아주 중요
		today_year = getYear(new Date());

		if(type == "ALL" || type == "SELECT"){
			y.append(new Option(type, ""));
		}

		for (i = start_year; i <= today_year + 3; i++){
			y.append(new Option(i, i));
		}

		y.val(def);
	}

	// 폼안의 연도 셀렉트 박스에 월 셋팅
	function setMonthCombo(m, def, type)	{
		if(type == "ALL" || type == "SELECT"){
			m.append(new Option(type, ""));
		}

		for (var i = 1; i <= 12; i++){
			if(i<10){
				m.append(new Option(i, "0"+i));
			}else{
				m.append(new Option(i, i));
			}
		}

		if(def < 10){
			def = "0" + def;
		}

		m.val(def);
	}

	// 폼안의 연도 셀렉트 박스에 날짜 셋팅
	function setFrmDateSub(f, d, def){
		var i, today_month;
		iLoopCnt = 0;
		today_month = getMonth(new Date());
		for (i=1; i<=31; i++){
			if(i<10)	d.options[iLoopCnt]= new Option(i, "0"+i);
			else	d.options[iLoopCnt]= new Option(i, i);

			if ((def>0)?(i==def):(i==today_month)){
				d.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 폼안의 연도 셀렉트 박스에 날짜 셋팅
	function setFrmDate(f, y, m, d, def){
		var i, iLoopCnt, today_year, today_month;
		iLoopCnt = 0;
		today_year = y.value;
		today_month = m.value;
		today_date = getDate(new Date());
		end_date = getDate(new Date(new Date(today_year, today_month, 1)-86400000));
		d.length = 0;
		for (i=1; i <= end_date; i++){
			if(i<10)	d.options[iLoopCnt] = new Option(i, "0"+i);
			else	d.options[iLoopCnt] = new Option(i, i);
			if ((def>0)?(i==def):(i==today_date)){
				d.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 폼안의 시간 셀렉트 박스에 월 셋팅
	function setFrmHour(f, m, def){
		var i, today_month;
		iLoopCnt = 0;
		today_month = getMonth(new Date());
		for (i=1; i<=24; i++){
			if(i<10)	m.options[iLoopCnt]= new Option(i, "0"+i);
			else	m.options[iLoopCnt]= new Option(i, i);

			if ((def>0)?(i==def):(i==today_month)){
				m.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 사용자가 년월중을 바꿀 경우 날짜 다시 쎄팅
	function changeFrmDate(f, y, m, d){
		var user_y  = y.value;
		var user_m = m.value;
		var user_d   = d.value;
		var daysInMonth = getDate(new Date(new Date(user_y, user_m, 1)-86400000));
		for(var i=d.length-1; i>=0; i--){
			d.options[i]=null;
		}

		for(var j=0; j<daysInMonth; j++){
			var k=j+1;
			if(k<10)	d.options[j] = new Option(k, "0"+k);
			else		d.options[j]=new Option(k, k);
		}

		if(user_d <= daysInMonth){
			d.options[user_d-1].selected = true;
		}else{
			d.options[daysInMonth-1].selected = true;
		}
	}

	// 날짜 체크 리턴 1:OK 0:NO
	function DateStrChk(varStr){
		var year, month, day;
		if(varStr.length==0)		return 1;
		if(varStr.length==10){
			year = varStr.substring(0, 4);
			month = varStr.substring(5, 7);
			day = varStr.substring(8, 10);
		}else if(varStr.length==8){
			year = varStr.substring(0, 4);
			month = varStr.substring(4, 6);
			day = varStr.substring(6, 8);
		}else return 0;
		if(NumStrChk(year) && NumStrChk(month) && NumStrChk(day)){
			var varDate = new Date(year, month-1, day);
			if(month==getMonth(varDate) && day==getDate(varDate))		return 1;
			else return 0;
		}else return 0;
	}

	// 영문자 체크 리턴 1:OK 0:NO
	function EngStrChk(valStr,howSpace){
		var strSize = valStr.length;

		for(i=0;i<strSize;i++){
			str1 = valStr.substr(i,1);
			if(howSpace == "Y" || howSpace == "y" || howSpace == true){
				if((str1 >= "A" && str1 <= "Z") || (str1 >= "a" && str1 <= "z")|| (str1 >= "0" && str1 <= "9")){
				}else{
					if(str1 != " "){	return 0;	}
				}
			}else{
				if((str1 >= "A" && str1 <= "Z") || (str1 >= "a" && str1 <= "z")|| (str1 >= "0" && str1 <= "9")){
				}else{
					return 0;
				}
			}
		}
		return 1;
	}

	// 특수문자 체크 리턴 1:OK 0:NO
	function SpcStrChk(valStr){
		var strSize = valStr.length;
		for(i=0;i<strSize;i++){
			str1 = valStr.substr(i,1);
			if(str1 == "`" || str1 == "~" || str1 == "!" ||
			   str1 == "@" || str1 == "#" || str1 == "$" ||
			   str1 == "%" || str1 == "^" || str1 == "&" ||
			   str1 == "*" || str1 == "(" || str1 == ")" ||
			   str1 == "+" || str1 == "=" || str1 == "|" ||
			   str1 == "\"" || str1 == "/" || str1 == "\\" || str1 == ";" || str1 == ":"){
				return 0;
			}
		}
		return 1;
	}

	// 숫자 체크 리턴 1:OK 0:NO
	function NumStrChk(valStr){
		var strSize = valStr.length;
		for(i=0;i<strSize;i++){
			str1 = valStr.substr(i,1);
			if(str1 < "0" || str1 > "9"){
				return 0;	// No
			}
		}
		return 1;	// OK
	}

	/* 입력창 글자 길이 제한 검사(숫자, 영문자, 한글 허용)
	inputFrm	: input 엘리멘트 (예: "formName.inputName")
	msgName		: 메세지 항목 ( 예: "사원번호", "주민등록번호",...)
	tLen		: 제한할 길이
	*/
	function chkLength(inputFrm,msgName,tLen){
		var strsrc = inputFrm.value;
		if(strsrc.length > tLen){
			alertUI(msgName+"의 길이는 "+tLen+" 글자(한국 기준)를 초과할 수 없습니다.");
			inputFrm.value = strsrc.substring(0,tLen);
			return;
		}
		if(SpcStrChk(strsrc)==0){
			alertUI(msgName+"의 내용에 특수문자를 사용할 수 없습니다.");
			inputFrm.value = "";
			return;
		}
	}

	/* 입력창 글자 길이 제한 검사(숫자, 영문자만 허용)
	inputFrm	: input 엘리멘트 (예: "formName.inputName")
	msgName		: 메세지 항목 ( 예: "사원번호", "주민등록번호",...)
	tLen		: 제한할 길이
	*/
	function chkLength2(inputFrm,msgName,tLen){
		var strsrc = inputFrm.value;
		if(strsrc.length > tLen){
			alertUI(msgName+"의 길이는 "+tLen+" 글자(한국 기준)를 초과할 수 없습니다.");
			inputFrm.value = strsrc.substring(0,tLen);
			return;
		}
		if(EngStrChk(strsrc,"N")==0){
			alertUI(msgName+"는 영문자 또는 숫자만 사용할 수 있습니다.");
			inputFrm.value = "";
			return;
		}
	}

	function error(elem,text) {
			if(errfound) return;
			window.alertUI(text);
			elem.select();
			elem.focus();
			errfound=true;
	}

	function popUpWindow(winURL,winIrum,wl,hl,tools,loc,dir,state,menu,scrol,resize){
		/*
		인수 -  winURL		: GET 방식 전송 데이터 포함한 완전한 URL
				winIrum		: 새 Window 창 Name 값
				wl			: 새 창 가로크기
				hl			: 새 창 세로크기
				tools		: toolbars("yes"/"no")
				loc			: Location("yes"/"no")
				dir			: directory툴바("yes"/"no")
				state		: status("yes"/"no")
				menu		: menubar("yes"/"no")
				scrol		: scrollbars("yes"/"no")
				resize		: resizable("yes"/"no")
		*/
		var cw = wl;   // 오픈할 윈도우 창 width
		var ch = hl;   // 오픈할 윈도우 창 height
		var winwidth = (eval(screen.width) - cw) /2;
		var winheight = (eval(screen.height) - ch) /2;
		var winOpt = 'top='+winheight+',left='+ winwidth +',toolbar='+tools+',location='+loc+',directories='+dir+'status='+state+',menubar='+menu+',scrollbars='+scrol+',resizable='+resize+',width='+cw+',height='+ch;
		window.open(winURL,winIrum,winOpt);
	}

	function popUpWindow2(winURL,winIrum,wl,hl,tools,loc,dir,state,menu,scrol,resize, left, right){
		/*
		인수 -  winURL		: GET 방식 전송 데이터 포함한 완전한 URL
				winIrum		: 새 Window 창 Name 값
				wl			: 새 창 가로크기
				hl			: 새 창 세로크기
				tools		: toolbars("yes"/"no")
				loc			: Location("yes"/"no")
				dir			: directory툴바("yes"/"no")
				state		: status("yes"/"no")
				menu		: menubar("yes"/"no")
				scrol		: scrollbars("yes"/"no")
				resize		: resizable("yes"/"no")
		*/
		var cw = wl;   // 오픈할 윈도우 창 width
		var ch = hl;   // 오픈할 윈도우 창 height

		var winOpt = 'top='+left+',left='+ right +',toolbar='+tools+',location='+loc+',directories='+dir+'status='+state+',menubar='+menu+',scrollbars='+scrol+',resizable='+resize+',width='+cw+',height='+ch;
		window.open(winURL,winIrum,winOpt);
	}

	function popUpWindow3(winURL, winName){
		if(typeof(winName) == "undefined" || winName == ""){
			winName = "HELP";
		}
		var cw = "800";
		var ch = "800";
		var winwidth = (eval(screen.width) - cw) /2;
		var winheight = (eval(screen.height) - ch) /2;
		var winOpt = 'top='+winheight+',left='+ winwidth +',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width='+cw+',height='+ch;
		return window.open(winURL, winName, winOpt);
	}

	// 폼안의 연도 셀렉트 박스에 연도 셋팅
	// 시작년도와 끝 년도를 인자값으로 받는다
	function setFrmYear2(f, y, def, start, end){
		var i = 0;
		var iLoopCnt = 0;
		def = (def == 0)? getYear(new Date()): def;
		for (i=start; i<=end; i++){
			y.options[iLoopCnt]= new Option(i, i);
			if (i == def){
				y.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	// 폼안의 연도 셀렉트 박스에 연도 셋팅
	// 시작년도와 이후 몇년까지 할것인지 인자값으로 받는다.
	function setFrmYear3(f, y, def, start_year, addNth){
		var i, iLoopCnt, start_year, today_year;
		iLoopCnt = 0;
		today_year = getYear(new Date());

		for (i=start_year; i<=today_year+addNth; i++){
			y.options[iLoopCnt]= new Option(i, i);
			if ((def>0)?(i==def):(i==today_year)){
				y.options[iLoopCnt].selected = true;
			}
			iLoopCnt++;
		}
	}

	function openPopWindow(theURL,winName,features,cookie_key_val){
		var c = document.cookie + ';';
		if( c.indexOf(cookie_key_val) < 0 )
			window.open(theURL,winName,features);
	}

	// IE 버젼에 따른 현재 창 종료 방식 세팅
	function selfClose(){
		if(navigator.appVersion.indexOf("MSIE 7.0") >= 0 || navigator.appVersion.indexOf("MSIE 8.0") >= 0){
			window.open('about:blank','_self').close();
		}else{
			window.close();
		}
	}

	// IE에서 숫자만 입력 가능
	function onlyNum(){
		var code = window.event.keyCode;
		if ((code > 34 && code < 41) || (code > 47 && code < 58) || (code > 95 && code < 106) || code == 8 || code == 9 || code == 13 || code == 46)
		{
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	}

	// IE에서 숫자만 입력 가능
	function onlyNum2(){
		var code = window.event.keyCode;
		if (!(code < 45 || code > 57 || code == 45))
		{
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	}

	// IE에서 숫자만 입력 가능
	function onlyNum3(){
		var code = window.event.keyCode;
		if (!(code < 48 || code > 57 || code == 45))
		{
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	}
	// IE에서 숫자만 입력 가능
	function onlyNum4(){
		var code = window.event.keyCode;
		if ((code > 47 && code < 58) || code == 45)
		{
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	}

	// IE에서 특수문자 제외
	function onlyChar(){
		var code = window.event.keyCode;
		if (!(code > 185 && code < 193) && !(code > 218 && code <223) && (code != 106) && (code != 107) && !(code > 108 && code < 112) && code != 13)
		{
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	}

	function makeform(url)
	{
	  var f = document.createElement("form");
	  f.setAttribute("method", "post");
	  f.setAttribute("action", url);
	  document.body.appendChild(f);

	  return f;
	}

	function addData(name, value){
	  var i = document.createElement("input");
	  i.setAttribute("type", "hidden");
	  i.setAttribute("name", name);
	  i.setAttribute("value", value);
	  return i;
	}

	function newPopWin(openUrl, cw, ch, winname, sb){
		// sk/hncis/technicalSupport/xts10_car_pop.gas?row_id=1&menuid=XTS06
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var sb = typeof(sb) == "undefined" ? "no" : sb;
		var winOpt = "width="+cw+",height="+ch+",left="+wd+",top="+ht+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars="+sb+",resizable=no";

		var f ="";
		var sUrl = "";
		var arrParameters = "";

		if(openUrl.indexOf("?") > -1){
			var arrUrl = openUrl.split("?");
			sUrl = arrUrl[0];
			f = makeform(sUrl);

			arrParameters = removeTag(arrUrl[1]).split("&");
			for(var i = 0 ; i < arrParameters.length ; i++){
				var arrNameAndValue = arrParameters[i].split("=");
				f.appendChild(addData(arrNameAndValue[0], arrNameAndValue[1]));
			}

		}else{
			f = makeform(openUrl);
		}

		window.open("", winname, winOpt);
		f.setAttribute("target", winname);
		f.submit();
		document.body.removeChild(f);

	}

	function newPopWin2(openUrl, cw, ch, winname, sb){
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var sb = typeof(sb) == "undefined" ? "no" : sb;
		var winOpt = "width="+cw+",height="+ch+",left="+wd+",top="+ht+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars="+sb+",resizable=no";

		window.open(openUrl, winname, winOpt);
	}

	function newPopWin33(openUrl, cw, ch, winname, sb){
		// sk/hncis/technicalSupport/xts10_car_pop.gas?row_id=1&menuid=XTS06
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var sb = typeof(sb) == "undefined" ? "no" : sb;
		var winOpt = "width="+cw+",height="+ch+",left="+wd+",top="+ht+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars="+sb+",resizable=no";

		var f ="";
		var sUrl = "";
		var arrParameters = "";

		if(openUrl.indexOf("?") > -1){
			var arrUrl = openUrl.split("?");
			sUrl = arrUrl[0];
			f = makeform(sUrl);

			arrParameters = arrUrl[1].split("&");
			for(var i = 0 ; i < arrParameters.length ; i++){
				var arrNameAndValue = arrParameters[i].split("=");
				f.appendChild(addData(arrNameAndValue[0], arrNameAndValue[1]));
			}

		}else{
			f = makeform(openUrl);
		}

		window.open("", winname, winOpt);
		f.setAttribute("target", winname);
		f.submit();
		document.body.removeChild(f);

	}

	//textarea의 첫번째 줄의 값만 가져오기
	function getFirstRow(loopCnt, obj){
		for(var i=0; i<loopCnt; i++){
			if(obj[i].value != ""){
				var objVal = (obj[i].value).split('\n')[0];
				obj[i].value = objVal;
			}
		}
	}

	//버튼이미지 변경하는 함수(mouseOut)
	function na_restore_img_src(name){
		var img = eval('document.all.'+name);
		if (name == '') return;
		if (img && img.altsrc){
			img.src    = img.altsrc;
			img.altsrc = null;
		}
	}

	//버튼이미지 변경하는 함수(mouseOver)
	function na_change_img_src(name, nsdoc, rpath, preload){
		var img = eval((navigator.appName.indexOf('Netscape', 0) != -1) ? nsdoc+'.'+name : 'document.all.'+name);
		if(name == '')return;
		if(img){
			img.altsrc = img.src;
			img.src    = rpath;
		}
	}

	//특수문자를 제외하고 숫자만 리턴
	function selectNum(sValue, exceptValue){

		if (sValue == "") return "";
		if (!exceptValue) exceptValue = "";

		sValue = sValue;
		var sResult = "";
		var sNum = "0123456789";
		sNum += exceptValue;

		var sChar=".,/:-) ";
		sChar = sChar.replace(exceptValue, "");

		for(var i=0;i<sValue.length;i++){
			if (-1 != sNum.indexOf(sValue.charAt(i))){
				sResult = sResult + sValue.charAt(i);
			}else if (-1 != sChar.indexOf(sValue.charAt(i))){

			}
		}

		return sResult;
	}
	//날짜타입 일/월/년 타입을  년월일 타입으로 변경
	function dateConversionKr(sValue){

		if (sValue < 8 ) return sValue;

		var sResult = "";

		if(sValue.indexOf("/") != -1){
			sResult = sValue.substring(6,10)+"/"+sValue.substring(3,5)+"/"+sValue.substring(0,2);
		}
		else{
			sResult = sValue.substring(4,8)+sValue.substring(2,4)+sValue.substring(0,2);
		}
		return sResult;
	}

	// 오늘 날짜 구하는 함수
	function getCurrentToDate(type){
		var toDay = new Date();
		var year  = toDay.getFullYear();
		var month = toDay.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
		var day   = toDay.getDate();
		var yyyymmdd = "";

		if (("" + month).length == 1) { month = "0" + month; }
		if (("" + day).length   == 1) { day   = "0" + day;   }

		if(typeof(type) == "undefined" || type == ""){
			yyyymmdd = year +""+ month +""+ day;
		}else if(type == "SK"){
			yyyymmdd = day +"-"+ month +"-"+ year;
		}else{
			yyyymmdd = year +"-"+ month +"-"+ day;
		}

		return yyyymmdd;
	}
	// 오늘 날짜 구하는 함수
	function getCurrentToDateEn(type){
		var toDay = new Date();
		var year  = toDay.getFullYear();
		var month = toDay.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
		var day   = toDay.getDate();
		var yyyymmdd = "";

		if (("" + month).length == 1) { month = "0" + month; }
		if (("" + day).length   == 1) { day   = "0" + day;   }

		if(typeof(type) == "undefined" || type == ""){
			yyyymmdd = year +""+ month +""+ day;
		}else{
			yyyymmdd = year +"-"+ month +"-"+ day;
		}

		return yyyymmdd;
	}

	function getCurrentToDateAddDayEn(type,addDay){
		var toDay = new Date();
		var year  = toDay.getFullYear();
		var month = toDay.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
		var day   = toDay.getDate()+addDay;
		var yyyymmdd = "";

		if (("" + month).length == 1) { month = "0" + month; }
		if (("" + day).length   == 1) { day   = "0" + day;   }

		if(typeof(type) == "undefined" || type == ""){
			yyyymmdd = year +""+ month +""+ day;
		}else{
			yyyymmdd = year +"-"+ month +"-"+ day;
		}

		return yyyymmdd;
	}

	// 현재 년도 반환.
	function getCurrentToYear(){
		var yyyymmdd = getCurrentToDate();
		var yyyy  = yyyymmdd.substring(0, 4);
		return yyyy;
	}

	// 현재 월 반환
	function getCurrentToMonth(){
		var yyyymmdd = getCurrentToDate();
		var mm  = yyyymmdd.substring(4, 6);
		return mm;
	}

	//현재 일 반환
	function getCurrentToDay(){
		var yyyymmdd = getCurrentToDate();
		var dd  = yyyymmdd.substring(6, 8);
		return dd;
	}


	//현재 이름을 넘겨서 키값을 타이핑한 값이 maxlength랑 같으면 다음 칸으로 이동
	function tab_next(frm, Obj){
		//tab-key, Back Space, Arrow(상하좌우)
		if (window.event.keyCode == 229) return;
		if (window.event.keyCode == 9 || window.event.keyCode == 8 || window.event.keyCode == 16 || window.event.keyCode == 35 || window.event.keyCode == 36 || window.event.keyCode == 37 || window.event.keyCode == 38 || window.event.keyCode == 39 || window.event.keyCode == 40){return;}
		var index=Obj.tabIndex+1;
		if(Obj.value.length == Obj.maxLength || Obj.type == "select-one"){
			var listLength = frm.elements.length;
			for(var i = 0; i < listLength; i++) {
				if(frm.elements[i].tabIndex == index) {
					if (frm.elements[i].type == "text" || frm.elements[i].type == "password"){
						//if (frm.elements[i].readOnly == false && frm.elements[i].disabled == false){
						if(frm.elements[i].disabled == false){
							frm.elements[i].focus();
							frm.elements[i].select();
						}
					}else if (frm.elements[i].type == "select-one"){
						if (frm.elements[i].disabled == false){
							frm.elements[i].focus();
						}
					}

					return;
				}
			}
		}
	}

	// 엔터키 눌렀을때 조회
	function enterKey(){
		if(window.event.keyCode == 13){
			retrieve('search');
		}
	}

	// 문자열 str에서 문자 ch를 모두 제거하는 함수, ch의 default값은 ' '
	function trimChar(str, ch){
		var i = 0;
		var dst = "";

		if (ch==null || ch.length!=1) ch=' ';
		for (i=0;i<str.length;i++){
			if(str.charAt(i)!=ch) dst+=str.charAt(i);
		}

		return dst;
	}

	// 날짜에 '/' 넣고 제거하는 함수
	function dateFormat(obj, gubun){
		var strDate = obj.value;
		var strLength = strDate.length;

		if(gubun == 'in'){
			strDate = trimChar(strDate, '/');
		}else{
			if(strLength == 8){
				strDate = strDate.substring(0, 4) + '/' + strDate.substring(4, 6) + '/' + strDate.substring(6, 8);
			}else if(strLength == 6){
				strDate = strDate.substring(0, 4) + '/' + strDate.substring(4, 6);
			}
		}

		obj.value = strDate;
		if(gubun == 'in'){ obj.select(); }
	}

	// 시간에 ':' 넣고 제거하는 함수
	function timeFormat(obj, gubun){
		var strTime = obj.value;
		var strTimeTemp = strTime;
		var strLength = strTime.length;

		if(gubun == 'in'){
			strTime = trimChar(strTime, ':');
		}else{
			strTime = strTime.substring(0, 2) + ':' + strTime.substring(2, 4);
			if(strLength == 3){
				strTime = '0'+strTimeTemp.substring(0, 1) + ':' + strTimeTemp.substring(1, 3);
			}else if(strLength < 4){
				strTime = trimChar(strTime, ":");
			}else if(strLength > 4){
				//strTime = trimChar(strTime, ":");
				strTime = strTimeTemp.substring(0, strLength-2) + ':' + strTimeTemp.substring(strLength-2, strLength);
			}
		}

		obj.value = strTime;
		if (gubun == 'in') { obj.select(); }
	}

	// 숫자에 ',' 넣고 제거하는 함수
	function numberFormat(obj, gubun){
		var sign = obj.value.substring(0,1);
		if(sign == "-"){
			sign = obj.value.substring(0,1);
			obj.value = obj.value.substring(1);
		}else{
			sign = "";
		}

		var strNumber = obj.value;
		var strLength = strNumber.length;
		var loopCount = 0;
		var rtnString = "";
		if(gubun == 'in'){
			rtnString = trimChar(strNumber, ',');
		}else{
			strNumber = trimChar(strNumber, ',');
			strLength = strNumber.length;
			for(var i=strLength;i>0;i--){
				if(loopCount != 0 && loopCount%3 == 0){
					rtnString = ',' + rtnString;
				}
				rtnString = strNumber.substring(i-1, i) + rtnString;
				loopCount++;
			}
		}

		obj.value = sign + rtnString;
		if(gubun == 'in'){ obj.select(); }
	}

	// 숫자에 ',' 넣고 제거하는 함수
	function doubleFormat(obj, no){
		if(Number(obj.value) == 0){
			obj.value = 0;
		}else{
			obj.value = Number(obj.value).toFixed(no);
		}
	}

	function focusFormat(obj){
		if(Number(obj.value) == 0){
			obj.value = "";
		}
	}

	// 두 Time이 며칠 차이나는지 구함
	// time1이 time2보다 크면(미래면) minus(-)
	function getDayInterval(time1, time2){
		time1 = trimChar(time1,"/");
		time2 = trimChar(time2,"/");
		var date1 = toTimeObject(time1);
		var date2 = toTimeObject(time2);
		var day   = 1000 * 3600 * 24; //24시간

		return parseInt((date2 - date1) / day, 10);
	}

	// 두 Time이 며칠 차이나는지 구함
	// time1이 time2보다 크면(미래면) minus(-)
	function getTimeInterval(time1, time2){
		time1 = trimChar(time1,"/");
		time2 = trimChar(time2,"/");
		var date1 = toTimeObject(time1);
		var date2 = toTimeObject(time2);
		var day   = 1000 * 3600; //24시간

		//alertUI(date2 - date1);

		return ((date2 - date1) / day).toFixed(1);
	}

	// Time 스트링을 자바스크립트 Date 객체로 변환
	// parameter time: Time 형식의 String
	// 1월=0, 12월=11
	function toTimeObject(time){
		var year  = time.substr(0,4);
		var month = time.substr(4,2)-1;
		var day   = time.substr(6,2);
		var hour  = time.substr(8,2);
		var min   = time.substr(10,2);

		return new Date(year,month,day,hour,min);
	}

	function fnSubmitInfoSettings(pgsStCdVal, ifIdVal, approveStr, snbObjId, approveLevel){

		if(typeof(document.all.SUBMIT_TITLE) != 'undefined'){
			if(pgsStCdVal != "0" && ifIdVal != ""){
				for (var i=0; i<document.all.SUBMIT_TITLE.length ;i++ ){
					document.all.SUBMIT_TITLE[i].style.display = "";
					document.all.SUBMIT_DATA[i].style.display = "";
					document.all.SUBMIT_TITLE[i].innerText = ' ';
					document.all.SUBMIT_DATA[i].innerText = ' ';
				}
				displaySubmit(document ,approveStr, 1);
			}else if(pgsStCdVal != "0" && ifIdVal == "" && approveLevel == ""){
				for (var i=0; i<document.all.SUBMIT_TITLE.length ;i++ ){
					document.all.SUBMIT_TITLE[i].style.display = "none";
					document.all.SUBMIT_DATA[i].style.display = "none";
					document.all.SUBMIT_TITLE[i].innerText = ' ';
					document.all.SUBMIT_DATA[i].innerText = ' ';
				}
			}else{
				for (var i=0; i<document.all.SUBMIT_TITLE.length ;i++ ){
					document.all.SUBMIT_TITLE[i].innerText = ' ';
					document.all.SUBMIT_DATA[i].innerText = ' ';
				}
			}
		}
		if($("#"+snbObjId).length != 0){
//			if(pgsStCdVal == "Z" || pgsStCdVal == "3" || pgsStCdVal == "A"){
			if(pgsStCdVal == "Z"){
				if(Number(sess_auth) > 4 || sess_mstu == "M"){
					readOnlyStyle(snbObjId, 2);
				}else{
					readOnlyStyle(snbObjId, 1);
				}
			}else{
				readOnlyStyle(snbObjId, 1);
			}
		}
	}

	// 결재내용 display
	function displaySubmit(doc, argStr, appcNo){

		if(argStr == null || argStr == ""){
			return;
		}

		var mainArray = new Array();
		mainArray = argStr.split('#');

		var totLevel = mainArray[0];
		var nowLevel = mainArray[1];

		if(typeof($("#cancel_yn").val()) !="undefined"){
			$("#cancel_yn").val(mainArray[2]);
		}

		for (var i=0; i<doc.all.SUBMIT_TITLE.length ;i++ ){
			doc.all.SUBMIT_TITLE[i].innerText = ' ';
			doc.all.SUBMIT_DATA[i].innerText = ' ';
		}

		var varArray1 = new Array();
		var varArray2 = new Array();
		var data = '';
		var removeCount = 0;

		if(Number(totLevel) > 0){
			var appLen = doc.all.SUBMIT_TITLE.length - appcNo - 1;
			removeCount = appLen - Number(totLevel);

			for (var i=0; i<removeCount ;i++ ){
				doc.all.SUBMIT_TITLE[i+appcNo].style.display = "none";
				doc.all.SUBMIT_DATA[i+appcNo].style.display = "none";
			}
		}
		varArray1 = mainArray[3].split('|');

		for (var i=0; i<varArray1.length ;i++ ){
			doc.all.SUBMIT_TITLE[i].innerText = " ";
			doc.all.SUBMIT_DATA[i].innerText = " ";
		}

		var tempStr = mainArray[3];
		tempStr = trimChar(tempStr,"@");
		tempStr = trimChar(tempStr,"|");
		if (trimChar(tempStr)=="") return;
		var k = 0;

		for (var i=0; i<varArray1.length ;i++ ){
			if (i < doc.all.SUBMIT_TITLE.length){
				varArray2 = varArray1[i].split('@');
				data = '';
				if(i < (varArray1.length - appcNo)){
					doc.all.SUBMIT_DATA[i+appcNo+removeCount].innerText = '';
					for (var j=0; j<varArray2.length ;j++ ){
						if (j==0){
							if(varArray2[0] != ""){
								doc.all.SUBMIT_TITLE[i+appcNo+removeCount].innerText = varArray2[0];
							}
						}else{
							data += varArray2[j];
							if (j < 3){
								data += '\n';
							}else{
								doc.all.SUBMIT_DATA[i+appcNo+removeCount].innerText += data;
							}
						}
					}
				}else{
					doc.all.SUBMIT_DATA[k].innerText = '';
					for (var j=0; j<varArray2.length ;j++ ){
						if (j==0){
							if(varArray2[0] != ""){
								doc.all.SUBMIT_TITLE[k].innerText = varArray2[0];
							}
						}else{
							data += varArray2[j];
							if (j < 3){
								data += '\n';
							}else{
								doc.all.SUBMIT_DATA[k].innerText += data;
							}
						}
					}
					k++;
				}
			}
		}

		/*
		if(doc.all.SUBMIT_DATA[doc.all.SUBMIT_TITLE.length-appcNo-removeCount-1].innerText == doc.all.SUBMIT_DATA[doc.all.SUBMIT_TITLE.length-appcNo-removeCount].innerText){
			doc.all.SUBMIT_TITLE[doc.all.SUBMIT_TITLE.length-appcNo-removeCount-1].style.display = "none";
			doc.all.SUBMIT_DATA[doc.all.SUBMIT_TITLE.length-appcNo-removeCount-1].style.display = "none";
		}
		*/
		if(doc.all.SUBMIT_DATA[appcNo+removeCount].innerText == doc.all.SUBMIT_DATA[appcNo+removeCount+1].innerText){
			doc.all.SUBMIT_TITLE[appcNo+removeCount].style.display = "none";
			doc.all.SUBMIT_DATA[appcNo+removeCount].style.display = "none";
		}
	}

	function displaySubmitClear(doc){
		var $leftObj = $(".app_box_left");
		var $rightObj = $(".app_box_right");

		var $leftTitle = $leftObj.find("#SUBMIT_TITLE");
		var $leftData = $leftObj.find("#SUBMIT_DATA");

		var $rightTitle = $rightObj.find("#SUBMIT_TITLE");
		var $rightData = $rightObj.find("#SUBMIT_DATA");

		var apprLev1 = parseInt($("#apprLev1").val(),10);
		var apprLev2 = parseInt($("#apprLev2").val(),10);

		for (var i=0; i<$leftTitle.length ;i++ ){
			if(i<apprLev1){
				$leftTitle.eq(i).show();
				$leftTitle.eq(i).html('');
				$leftData.eq(i).show();
				$leftData.eq(i).html('');
			} else {
				$leftTitle.eq(i).hide();
				$leftTitle.eq(i).html('');
				$leftData.eq(i).hide();
				$leftData.eq(i).html('');
			}
		}

		for (var i=0; i<$rightTitle.length ;i++ ){
			if(i<apprLev2){
				$rightTitle.eq(i).show();
				$rightTitle.eq(i).html('');
				$rightData.eq(i).show();
				$rightData.eq(i).html('');
			} else {
				$rightTitle.eq(i).hide();
				$rightTitle.eq(i).html('');
				$rightData.eq(i).hide();
				$rightData.eq(i).html('');
			}
		}

//		for (var i=0; i<doc.all.SUBMIT_TITLE.length ;i++ ){
//			doc.all.SUBMIT_TITLE[i].style.display = "";
//			doc.all.SUBMIT_DATA[i].style.display = "";
//			doc.all.SUBMIT_TITLE[i].innerText = ' ';
//			doc.all.SUBMIT_DATA[i].innerText = ' ';
//		}
	}

	/**
	 * 조회 메세지는 여부 체크
	 * 입력이나 ,수정 삭제 후 조회시 doQuery('printOff')로 사용
	 * doQuery()사용시엔 메세지 출력
	 */
	function getPrintYn(args){
		var flag = true;

		if(typeof(args) !="undefined" && args.length > 0){
			for(var i =0;i <args.length; i++ ){
				if(args[i] == "printOff"){
					flag = false;
				}
			}
		}

		return flag;
	}

	/***************************************************************************************
	** 메인화면내의 iframe을 submit하기 위한 Div생성                                           **
	** 메인화면내의 필요한 객체를 iframe의 div객체로 옮긴다                                      **
	** iframe내에 div객체를 미리 선언한다(id=DivSubmit);                                      **
	****************************************************************************************/
	function DivForSubmit(argForm,listForm) {
		var s_html;
		/*--------------------------------------------------------------------------------*/
		/* Form안의 모든 개체들 인식 위한 Form개체 변수 초기화.                                     */
		/*--------------------------------------------------------------------------------*/
		lO_Object = new Enumerator(argForm);

		/*--------------------------------------------------------------------------------*/
		/* Form안의 모든 개체들을 읽어 HTML을 만듦.                                              */
		/*--------------------------------------------------------------------------------*/
		for(; !lO_Object.atEnd(); lO_Object.moveNext()){
			lO_ObjectItem = lO_Object.item();
			/*----------------------------------------------------------------------------*/
			/* Radio Button일 경우.                                                         */
			/* (각 컨트롤의 이름이 같으므로 Value가 있을 때만 Submit).                              */
			/*----------------------------------------------------------------------------*/
			if (lO_ObjectItem.type == "radio"){
				if (lO_ObjectItem.checked){
					s_html += "<input type='hidden' name='" + lO_ObjectItem.name+ "' value='" + lO_ObjectItem.value + "'>\n";
				}
			}
			/*----------------------------------------------------------------------------*/
			/* 기타.                                                                       */
			/*----------------------------------------------------------------------------*/
			else if (lO_ObjectItem.type == "checkbox"){
				if (lO_ObjectItem.checked){
					s_html += "<input type='hidden' name='" + lO_ObjectItem.name+ "' value='Y'>\n";
				}else{
					s_html += "<input type='hidden' name='" + lO_ObjectItem.name+ "' value='N'>\n";
				}
			}else{
				s_html += "<input type='hidden' name='" + lO_ObjectItem.name+ "' value='" + lO_ObjectItem.value + "'>\n";
			}
		}

		/*--------------------------------------------------------------------------------*/
		/* Submit할 Form 태그를 만듦.                                                        */
		/*--------------------------------------------------------------------------------*/
		eval(listForm + ".DivSubmit").innerHTML = s_html;
		return true;
	}

	// 하단 메시지 표시
	function setBottomMsg(msg, mode){
		try{
			$("#msgInput").val(msg);
			if(typeof(mode) != undefined && mode){
				alertUI(msg);
			}
		}catch(e){
		}
	}


	// 숫자형 전화번호를 받아와 형식에 맞춰줌
	function CheckTel(obj){
		var sVal = obj.value;
		var sTel1,sTel2,sTel3;
		var sTemp;
		var nCnt;

		var sValue = selectNum(sVal);

		if (sValue == "") {obj.value=sValue;return;}
		if (sValue.length == 4) {obj.value=sValue;return;}
		if (sValue.length < 7 || sValue.length > 12) {obj.value=sValue;return;}

		sTel3 = sValue.substring(sValue.length-4,sValue.length);
		sTemp = sValue.substring(0, sValue.length-4);

		if (sTemp.substring(0,1) != "0"){
			if (sValue.length > 8 ) {obj.value=sValue;return;}
			obj.value = "02-" + sTemp + "-" + sTel3;
			return;
		}

		switch (sTemp.substring(0,4)){
			case "0502":
			case "0505":
			case "0506":
				nCnt=4;
				break;
			default :
				switch (sTemp.substring(0,2)){
					case "02":
						nCnt=2;
						break;
					default:
						switch (sTemp.substring(0,3)){
							case "011":
							case "013":
							case "016":
							case "017":
							case "018":
							case "019":
							case "010":
							case "080":

							case "031":
							case "032":
							case "033":
							case "041":
							case "042":
							case "043":
							case "051":
							case "052":
							case "053":
							case "054":
							case "055":
							case "061":
							case "062":
							case "063":
							case "064":
							case "070":
								nCnt=3;
								break;
							default:
								sTemp.value=sValue;
								return;;
						}
				}
		}

		sTel1 = sTemp.substring(0,nCnt);
		sTel2 = sTemp.substring(nCnt, sTemp.length);

		if( sTel2.length > 4) return "";
		obj.value = sTel1 + "-" + sTel2 + "-" + sTel3;
	}

	// 전화번호체크 정규식 표현(평생번호 포함);
	function formatphoneNumber(num){
		if(typeof(num) == "undefined") return num;

		var delim = "-";
		var pt = /^(01\d{1}|02|0505|0506|0502|0\d{1,2})-?(\d{3,4})-?(\d{4})$/g;
		if(!pt.test(num)){
			alertUI("올바른 휴대폰 번호가 아닙니다.");
			return "";
		}else{
			return num.replace(/^\s+|\s+$/g, "").replace(pt, "$1" + delim + "$2" + delim + "$3");
		}
	}

	function formatEmailCheck(spod_email){
		var reg_email=/^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;

		if (spod_email.search(reg_email) == -1){
			alertUI("올바른 이메일 주소가 아닙니다. ");
			return false;
		}
		return true;
	}

	// 3 자리마다 콤마처리
	function numberComma(_number){
		var num = Number(_number);
		if(num==0) return 0;
		var reg = /(^[+-]?\d+)(\d{3})/;
		var n = (num + '');
		while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
		return n;


		return num;
	}

	// 프로그래스바 on
	function frmWait_open() {
		zu_openRunning(true,"blockPage","");
	}

	// 프로그래스바 off
	function frmWait_close() {
		zu_openRunning(false,"blockPage","");
	}

	/***************************************************************************************
	** window open 하는 함수                     **
	***************************************************************************************/
	function openWin( ss_winName, _width, _height, _scrollbar ){
		if (_height==null) return null;

		_width =(screen.width-10<_width)?   screen.width  -10 : _width;
		_height=(screen.height-10<_height)? screen.height -57 : _height;

		var left   = (screen.width - _width -10)/2;
		var top    = (screen.height- _height-57)/2;;

		left = (left<0)? 0 : left;
		top  = (top <0)? 0 : top;

		var option = "width ="+_width +","
					+"height="+_height+","
					+"left="+left+","
					+"top="+top+","
					+"toolbar=no,"
					+"location=no,"
					+"directories=no,"
					+"status=yes,"
					+"menubar=no,"
					+"scrollbars=" + _scrollbar + ","
					+"resizable=no,";

		var win = window.open("", ss_winName, option);

		win.focus();

		return win;
	}

	// 해당월과 윤년에 따른 일자수를 구한다.
	function getDaysInYm(month,year)  {
		var days;
		if (month=="01" || month=="03" || month=="05" || month=="07" || month=="08" || month=="10" || month=="12")  days="31";
		else if (month=="04" || month=="06" || month=="09" || month=="11") days=30;
		else if (month=="02"){
			if (leapYears (Number(year))==1)  days=29;
			else days=28;
		}

		return (days);
	}

	// 윤년인 년도를 구한다.
	function leapYears (Year) {
		if (((Year % 4)==0) && ((Year % 100)!=0) || ((Year % 400)==0)) return (1);
		else return (0);
	}

	/**
	* 입력 받은 두 날짜의 차이를 리턴한다.
	* 시작 time1 형식 yyyyMMdd
	* 종료 time2 형식 yyyyMMdd
	* return 두 날짜의 차이 일수
	*/
	function getDayInterval2(time1, time2){
		var sTimeArr = time1;
		var eTimeArr = time2;
		if(sTimeArr.length != 8 || eTimeArr.length != 8) { return 0; }

		var strt_day  = new Date(sTimeArr.substring(0,4),Number(sTimeArr.substring(4,6))-1,sTimeArr.substring(6,8));
		var end_day = new Date(eTimeArr.substring(0,4),Number(eTimeArr.substring(4,6))-1,eTimeArr.substring(6,8));
		var day_calc = Math.ceil((end_day.getTime() - strt_day.getTime()) / (24*60*60*1000));

		return day_calc;
	}

	/**
	 * 재외국인 번호 체크
	 * @param fgnno : 외국인번호
	 * @return
	 */
	function check_fgnno(fgnno) {
		var sum=0;
		var odd=0;
		buf = new Array(13);
		for(i=0; i<13; i++) { buf[i]=parseInt(fgnno.charAt(i)); }
		odd = buf[7]*10 + buf[8];
		if(odd%2 != 0) { return false; }
		if( (buf[11]!=6) && (buf[11]!=7) && (buf[11]!=8) && (buf[11]!=9) ) {
			return false;
		}
		multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
		for(i=0, sum=0; i<12; i++) { sum += (buf[i] *= multipliers[i]); }
		sum = 11 - (sum%11);
		if(sum >= 10) { sum -= 10; }
		sum += 2;
		if(sum >= 10) { sum -= 10; }
		if(sum != buf[12]) { return false; }
		return true;
	}

	/**
	 * 주민번호체크
	 * @param juminno : 주민번호
	 * @return
	 */
	function check_juminno(juminno, alertFlag) {
		if(juminno=="" || juminno==null || juminno.length!=13) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호를 적어주세요.");
			}
			return false;
		}
		var jumin1 = juminno.substr(0,6);
		var jumin2 = juminno.substr(6,7);
		var yy         = jumin1.substr(0,2);        // 년도
		var mm = jumin1.substr(2,2);        // 월
		var dd = jumin1.substr(4,2);        // 일
		var genda = jumin2.substr(0,1);        // 성별
		var msg, ss, cc;

		// 숫자가 아닌 것을 입력한 경우
		if (!isNumeric(jumin1)) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 앞자리를 숫자로 입력하세요.");
			}
			return false;
		}
		// 길이가 6이 아닌 경우
		if (jumin1.length != 6) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 앞자리를 다시 입력하세요.");
			}
			return false;
		}
		// 첫번째 자료에서 연월일(YYMMDD) 형식 중 기본 구성 검사
		if (yy < "00" || yy > "99" ||
				mm < "01" || mm > "12" ||
				dd < "01" || dd > "31") {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 앞자리를 다시 입력하세요.");
			}
			return false;
		}
		// 숫자가 아닌 것을 입력한 경우
		if (!isNumeric(jumin2)) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 뒷자리를 숫자로 입력하세요.");
			}
			return false;
		}
		// 길이가 7이 아닌 경우
		if (jumin2.length != 7) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 뒷자리를 다시 입력하세요.");
			}
			return false;
		}
		// 성별부분이 1 ~ 4 가 아닌 경우
		if (genda < "1" || genda > "4") {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 뒷자리를 다시 입력하세요.");
			}
			return false;
		}
		// 연도 계산 - 1 또는 2: 1900년대, 3 또는 4: 2000년대
		cc = (genda == "1" || genda == "2") ? "19" : "20";
		// 첫번째 자료에서 연월일(YYMMDD) 형식 중 날짜 형식 검사
		if (isYYYYMMDD(parseInt(cc+yy), parseInt(mm), parseInt(dd)) == false) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("주민등록번호 앞자리를 다시 입력하세요.");
			}
			return false;
		}
		// Check Digit 검사
		if (!isSSN(jumin1, jumin2)) {
			if(alertFlag || typeof(alterFlag) == undefined){
				alertUI("입력한 주민등록번호를 검토한 후, 다시 입력하세요.");
			}
			return false;
		}
		return true;
	}

	/**
	 * 사업자등록번호 체크
	 * @param vencod : 사업자등록번호
	 * @return
	 */
	function check_busino(vencod) {
		var sum = 0;
		var getlist =new Array(10);
		var chkvalue =new Array("1","3","7","1","3","7","1","3","5");
		vencod = replaceAll(vencod, "-");
		for(var i=0; i<10; i++) { getlist[i] = vencod.substring(i, i+1); }
		for(var i=0; i<9; i++) { sum += getlist[i]*chkvalue[i]; }
		sum = sum + parseInt((getlist[8]*5)/10);
		sidliy = sum % 10;
		sidchk = 0;
		if(sidliy != 0) { sidchk = 10 - sidliy; }
		else { sidchk = 0; }
		if(sidchk != getlist[9]) { return false; }
		return true;
	}

	function isYYYYMMDD(y, m, d) {
		switch (m) {
		case 2:        // 2월의 경우
			if (d > 29) return false;
			if (d == 29) {
				// 2월 29의 경우 당해가 윤년인지를 확인
				if ((y % 4 != 0) || (y % 100 == 0) && (y % 400 != 0))
					return false;
			}
			break;
		case 4:        // 작은 달의 경우
		case 6:
		case 9:
		case 11:
			if (d == 31) return false;
		}
		// 큰 달의 경우
		return true;
	}

	function isNumeric(s) {
		for (i=0; i<s.length; i++) {
			c = s.substr(i, 1);
			if (c < "0" || c > "9") return false;
		}
		return true;
	}

	function isNumeric2(s) {
		for (i=0; i<s.length; i++) {
			c = s.substr(i, 1);
			if (c != "-" || c < "0" || c > "9"){
				return false;
			}
		}
		return true;
	}

	function isLeapYear(y) {
		if (y < 100)
			y = y + 1900;
		if ( (y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0) ) {
			return true;
		} else {
			return false;
		}
	}

	function getNumberOfDate(yy, mm) {
		month = new Array(29,31,28,31,30,31,30,31,31,30,31,30,31);
		if (mm == 2 && isLeapYear(yy)) mm = 0;
		return month[mm];
	}

	function isSSN(s1, s2) {
		n = 2;
		sum = 0;
		for (i=0; i<s1.length; i++)
			sum += parseInt(s1.substr(i, 1)) * n++;
		for (i=0; i<s2.length-1; i++) {
			sum += parseInt(s2.substr(i, 1)) * n++;
			if (n == 10) n = 2;
		}
		c = 11 - sum % 11;
		if (c == 11) c = 1;
		if (c == 10) c = 0;
		if (c != parseInt(s2.substr(6, 1))) return false;
		else return true;
	}

	// 스크립트 태그 제거
	function removeTag(str){
		var rtStr = str.replace(/</g,"&lt;").replace(/>/g,"&gt;");
		return rtStr;
	}

	function replaceAll(str, gbn) {
		var reVal = str.split(gbn).join("");
		return reVal;
	}

	function replaceStr(str){
		return str.replace(/"/g, "\'");
	}

	// 넘겨받은 연도,월,일 을 yyyymmdd 형식으로 반환.
	function toYYYYMMDD(yyyy, mm, dd){
		if(yyyy.length == 1){ yyyy = "000" + yyyy;
		}else if(yyyy.length == 2){ yyyy = "00" + yyyy;
		}else if(yyyy.length == 3){ yyyy = "0" + yyyy; }

		if(mm.length == 1){ mm = "0" + mm; }
		if(dd.length == 1){ dd = "0" + dd; }

		return yyyy+mm+dd;
	}

	// 이전월또는 다음월의 날짜 구하기.
	function funcGetYearMonth(def_date, PrevNext){
		var dVal = def_date;
		var sYear = def_date.substring(0,4);
		var sMonth = def_date.substring(4, 6);
		var sDay = def_date.substring(6,8);
		var iYear = parseInt(sYear);
		var iMonth = parseInt(sMonth);

		if(PrevNext == 'Next') iMonth = iMonth + 1;
		else     iMonth = iMonth - 1;

		if(iMonth < 1){
			iYear--;
			iMonth = 12;
		}else if(iMonth > 12){
			iYear++;
			iMonth = 1;
		}

		sYear = '' + iYear;
		if(iMonth < 10) sMonth = '0' + iMonth;
		else   sMonth = ''  + iMonth;

		var rtnValue = sYear + sMonth + sDay;

		return rtnValue;
	}

	// 콤보 세팅
	(function($) {
	    $.fn.emptySelect = function(index) {
	      return this.each(function(){
	        if (this.tagName=='SELECT') this.options.length = index;
	      });
	    }

	    $.fn.loadSelect = function(optionsDataArray, selectCode) {
	      return this.emptySelect().each(function(){
	        if (this.tagName=='SELECT') {
	          var selectElement = this;
	          $.each(optionsDataArray,function(index,optionData){
	            var option = new Option(optionData.name,
	                                    optionData.value,true, optionData.value==selectCode);
	            if ($.browser.msie) {
	              selectElement.add(option);
	            }
	            else {
	              selectElement.add(option,null);
	            }
	          });
	        }
	      });
	    }
	  })(jQuery);

	/**
	 *	target에 대한 값을 세팅
	 *  {"test1":"111","test2":"222","test3":"333","combo1":"4"} 의 형식
	 */
	function loadJsonSet(jsonDataArray){

    	$.each(jsonDataArray,function(index,jsonData){
        	$('#'+index).val(jsonData);
		});
    }

	/**
	 *	target에 대한 값을 세팅
	 *  {"test1":"111","test2":"222","test3":"333","combo1":"4"} 의 형식
	 */
	function loadJsonSet02(jsonDataArray){

    	$.each(jsonDataArray,function(index,jsonData){
    		if( $('#'+index).length != 0) {
    			//alertUI( $('#'+index).attr("id") );
    			$('#'+index).val(jsonData);
    		}
		});
    }

	/**
	 *	공통 콤보 조회
	 */
	function getCommonCode(codkndStr, gridYn, completeFc, url){

		url = typeof(url) == "undefined" ? ctxPath+"/getCommonCombo.do" : url.substring(0,1) == "/" ? ctxPath+url : url;

        $.ajax( {
			type :'POST'
			,asyn :true
			,url :url
			,data:{
				codknd : codkndStr
			}
			,dataType :"json"
			,beforeSend : function(xhr){
			}
			,success : function(jsonData) {
				if(jsonData.sendResult.errorCode != null){
					alertUI(jsonData.sendResult.message);
				}else{
					if(typeof(gridYn) == "undefined" || gridYn == "N"){
						$.each(eval(jsonData.sendResult),function(index,optionData){
							$("#"+index).loadSelect(eval(optionData));
						});
					}else{
						comboVal = jsonData.sendResult;
					}
					eval(completeFc);
				}


			}
			,error : function(xhr, textStatus) {
//				alertUI("전송중 오류가 발생했습니다.");
				alertUI("An error has occurred during transmission.");

			}
			,complete : function(xhr, textStatus) {
			}
		});
    }


	/**
	 *	공통 콤보 조회(2)
	 */
	function getCommonCodeNotIncludeAply(codkndStr, gridYn, completeFc, url){

		url = typeof(url) == "undefined" ? ctxPath+"/getCommonComboNotIncludeAply.do" : url.substring(0,1) == "/" ? ctxPath+url : url;

        $.ajax( {
			type :'POST'
			,asyn :true
			,url :url
			,data:{
				codknd : codkndStr
			}
			,dataType :"json"
			,beforeSend : function(xhr){
			}
			,success : function(jsonData) {
				if(jsonData.sendResult.errorCode != null){
					alertUI(jsonData.sendResult.message);
				}else{
					if(typeof(gridYn) == "undefined" || gridYn == "N"){
						$.each(eval(jsonData.sendResult),function(index,optionData){
							$("#"+index).loadSelect(eval(optionData));
						});
					}else{
						comboVal = jsonData.sendResult;
					}
					eval(completeFc);
				}


			}
			,error : function(xhr, textStatus) {
//				alertUI("전송중 오류가 발생했습니다.");
				alertUI("An error has occurred during transmission.");
			}
			,complete : function(xhr, textStatus) {
			}
		});
    }

	function makeTitleToCombo(){
		var codeKnds = codeKnd.split(";");
		var comboKey  = "";
		var comboKind = "";
		var comboAttr ;
		var comboTextAddValue = "";
		for(var cnt in codeKnds){
			comboAttr = codeKnds[cnt].split(":");
			comboKey = comboAttr[0];
			if(comboAttr.length > 2 ){
				comboKind = comboAttr[2];
				if(comboAttr.length > 3 ){
					comboTextAddValue = comboAttr[3];
				}else{
					comboTextAddValue = "";
				}
			}else{
				comboKind = "";
			}
			ajaxGrid.evalJSONComboForKey(comboKey,comboKind,comboTextAddValue);
		}
	}

	/**
	 *	콤보 value return;
	 */
	function getComboValue(comboName){
		var returnVal = "";
		var i = 0;

		$.each(eval(comboVal),function(targetNm,optionData){

			if(targetNm == comboName){
				$.each(eval(optionData),function(index,optionData){
					if(returnVal == ""){
						returnVal = returnVal + htmlDecode(optionData.value) + ":" + htmlDecode(optionData.name);
					}else{
						returnVal = returnVal + ";" + htmlDecode(optionData.value) + ":" + htmlDecode(optionData.name);
					}
					i++;
				});
			}
	      });
		return returnVal;
	}

	/**
	 * 공통 그리드 SEARCH 함수
	 * @param url
	 * @param search parameter
	 * @param complete 함수명
	 * @returns null
	 */
	function doCommonSearch(url, paramData, completeFc, gridName, msgFlag){

		gridName = typeof(gridName) == "undefined" ? "#htmlTable" : "#"+gridName;

		url = url.substring(0,1) == "/" ? ctxPath+url : url;

		jQuery(gridName).jqGrid(
				'setGridParam',
				{
					url:url,
					page:1,
					postData:{
						paramJson : paramData
					},
					loadComplete: function(rs){
						switch (gridName.substring(gridName.length-1, gridName.length)){
							case "1":
								previRow1 = "";
								break;
							case "2":
								previRow2 = "";
								break;
							case "3":
								previRow3 = "";
								break;
							case "4":
								previRow4 = "";
								break;
							case "5":
								previRow5 = "";
								break;
							default :
								previRow = "";
						}

						var flag = typeof(msgFlag) == "undefined" ? "Y" : msgFlag;
						if(flag == "Y"){
							if(rs.records > 0 || rs.rows != ""){
								setBottomMsg(getMessage("SEARCH.0000"));//setBottomMsg("정상적으로 조회 되었습니다.");
							}else{
								setBottomMsg(getMessage("SEARCH.0002"));//setBottomMsg("조회된 데이터가 없습니다.");
							}
						}
						eval(completeFc);
					},
					loadError : function(xhr, st, err){
						setBottomMsg(err, true);
					}
				}
			).trigger("reloadGrid");

	}

	/**
	 * 공통 AJAX 호출 함수
	 * @param url
	 * @param search parameter
	 * @param success 함수명
	 * @returns null
	 */
	function doCommonAjax(url, paramData, successFc){

		url = url.substring(0,1) == "/" ? ctxPath+url : url;

		$.ajax( {
			type :'POST'
			,asyn :true
			,url : url
			,data:paramData
			,dataType :"json"
			,beforeSend : function(xhr){
			}
			,success : function(jsonData) {
				//alertUI("jsonData.sendResult.errorCode="+jsonData.sendResult.errorCode);
				if(jsonData.sendResult.errorCode == null || jsonData.sendResult.errorCode == ""){
					eval(successFc);
				}else{
					fnEndLoading();
					setBottomMsg(jsonData.sendResult.message, true);
				}
			}
			,error : function(xhr, textStatus) {
				fnEndLoading();
				setBottomMsg(textStatus, true);
			}
			,complete : function(xhr, textStatus) {
			}
		});
	}

	/**
	 * 공통 jqGrid init 함수
	 * @param url
	 * @param 컬럼명
	 * @param 컬럼속성
	 * @returns null
	 */
	var previRow = "";
	var previRow1 = "";
	var previRow2 = "";
	var previRow3 = "";
	var previRow4 = "";
	var previRow5 = "";
	var previSRow = "";
	var previSRow1 = "";
	var previSRow2 = "";
	var previSRow3 = "";
	var previSRow4 = "";
	var previSRow5 = "";
	var cell_id = "";
	var cell_nm = "";

	var prevModify = false;
	var cellSelectFlag = true;

	function commonJqGridInit(gridParam, optionType){
		var targetParam = gridParam.viewEdit[0];
		var gridName = typeof(targetParam.gridName) == "undefined" ? "#htmlTable" : "#"+targetParam.gridName;
		var url = typeof(targetParam.url) == "undefined" ? "" : targetParam.url.substring(0,1) == "/" ? ctxPath+targetParam.url : targetParam.url;
		optionType = typeof(optionType) == "undefined" ? "Y" : optionType;
		/* ### Ready event ### */
		jQuery(gridName).jqGrid({
			caption: "",
			url: url,
			editurl: ctxPath+"/doSaveToEmpty.do",
			datatype: "json",
			jsonReader : {
				page: "page",
				total: "total",
				root: "rows",
				records: "records",
				//records: function(obj){return obj.length;},
				//records:"max",
				repeatitems: false,
				id: "id"
			},
			mtype: "POST",
			pager: typeof(targetParam.pager) == "undefined" ? "" : jQuery("#"+targetParam.pager), //jQuery("#htmlPager"),
			loadtext: "Loading Data...",
			emptyrecords: "Data does not exist.",
			rowNum: typeof(targetParam.rowNum) == "undefined" ? 15 : targetParam.rowNum,
			rowList: typeof(targetParam.rowList) == "undefined" ? [10,15,30] : targetParam.rowList,
			rownumWidth: 20,
			width: typeof(targetParam.width) == "undefined" ? 1030 : targetParam.width,
			scrollrows : true,
			height: typeof(targetParam.height) == "undefined" ? "100%" : targetParam.height,
			colNames: typeof(targetParam.colNames) == "undefined" ? "" : targetParam.colNames,
			colModel : typeof(targetParam.colModel) == "undefined" ? "" : targetParam.colModel,
			sortname: typeof(targetParam.sortname) == "undefined" ? "" : targetParam.sortname,
			sortorder: typeof(targetParam.sortorder) == "undefined" ? "" : targetParam.sortorder,
			rownumbers: typeof(targetParam.rownumbers) == "undefined" ? true : targetParam.rownumbers,
			multiselect: typeof(targetParam.multiselect) == "undefined" ? true : targetParam.multiselect,
			cellEdit: typeof(targetParam.cellEdit) == "undefined" ? true : targetParam.cellEdit,
			cellsubmit: false,
			gridview: true,
			scroll: typeof(targetParam.scroll) == "undefined" ? false : targetParam.scroll,
			forceFit : typeof(targetParam.forceFit) == "undefined" ? false : targetParam.forceFit,
			shrinkToFit: typeof(targetParam.shrinkToFit) == "undefined" ? false : targetParam.shrinkToFit,
			viewrecords: true,
			footerrow : typeof(targetParam.footerrow) == "undefined" ? false : targetParam.footerrow,
			userDataOnFooter : typeof(targetParam.userDataOnFooter) == "undefined" ? false : targetParam.userDataOnFooter,
			altRows : typeof(targetParam.altRows) == "undefined" ? false : targetParam.altRows,
			treeGrid: typeof(targetParam.treeGrid) == "undefined" ? false : targetParam.treeGrid,
			treeGridModel: typeof(targetParam.treeGridModel) == "undefined" ? "" : targetParam.treeGridModel,
			treeIcons: typeof(targetParam.treeIcons) == "undefined" ? "" : targetParam.treeIcons,
			tree_root_level: typeof(targetParam.tree_root_level) == "undefined" ? "" : targetParam.tree_root_level,
			ExpandColumn: typeof(targetParam.ExpandColumn) == "undefined" ? "" : targetParam.ExpandColumn,
			subGrid: typeof(targetParam.subGrid) == "undefined" ? false : targetParam.subGrid,
			subGridRowExpanded: function(grid_id, row_id){
				typeof(targetParam.subGridRowExpandedFc) == "undefined" ? "" : eval(targetParam.subGridRowExpandedFc);
			},
			subGridOptions: typeof(targetParam.subGridOptions) == "undefined" ? false : targetParam.subGridOptions,
			postData:{
				paramJson : (typeof(targetParam.paramJson) == "undefined" || targetParam.paramJson == "") ? "{}" : util.jsonToString(targetParam.paramJson)
			},
			page : (typeof(targetParam.page) == "undefined" || targetParam.page == "") ? "1" : targetParam.page,
			formatCell: function (rowid, cellname, value, iRow, iCol) {
			},
			beforeSelectRow: function(rowid, e) {
            	switch (gridName.substring(gridName.length-1, gridName.length)){
					case "1":
						previRow_temp = previRow1;
						break;
					case "2":
						previRow_temp = previRow2;
						break;
					case "3":
						previRow_temp = previRow3;
						break;
					case "4":
						previRow_temp = previRow4;
						break;
					case "5":
						previRow_temp = previRow5;
						break;
					default :
						previRow_temp = previRow;
				}
				if(previRow_temp == ""){
					setRowEditModeByPrev(gridName, rowid, e);
					return true;
				}else if(rowid == previRow_temp){
					var cols = jQuery(gridName).jqGrid('getGridParam', 'colModel');
					var iCol =  $.jgrid.getCellIndex($(e.target).closest("td")[0]);
                    if(cols[iCol].name == "cb"){
                    	return true;
                    }else{
                    	//setRowEditModeByPrev(gridName, rowid, e);
                    	return false;
                    }
				}else{
					setRowEditModeByPrev(gridName, rowid, e);
					return true;
				}
			},
			beforeEditCell: function(rowid,name,val,iRow,iCol){
				var flag = typeof(targetParam.beforeEditCellYn) == "undefined" ? true : targetParam.beforeEditCellYn;
				switch (gridName.substring(gridName.length-1, gridName.length)){
					case "1":
						previRow_temp = previRow1;
						break;
					case "2":
						previRow_temp = previRow2;
						break;
					case "3":
						previRow_temp = previRow3;
						break;
					case "4":
						previRow_temp = previRow4;
						break;
					case "5":
						previRow_temp = previRow5;
						break;
					default :
						previRow_temp = previRow;
				}
				if(rowid == previRow_temp){
					cellSelectFlag = false;
					return;
				}else{
					cellSelectFlag = true;
				}
				if(flag){
					var previRow_temp = 0;
					jQuery(gridName).jqGrid('editRow',rowid);
					typeof(targetParam.beforeEditFc) == "undefined" ? "" : eval(targetParam.beforeEditFc);
					switch (gridName.substring(gridName.length-1, gridName.length)){
						case "1":
							previRow_temp = previRow1;
							previRow1 = rowid;
							break;
						case "2":
							previRow_temp = previRow2;
							previRow2 = rowid;
							break;
						case "3":
							previRow_temp = previRow3;
							previRow3 = rowid;
							break;
						case "4":
							previRow_temp = previRow4;
							previRow4 = rowid;
							break;
						case "5":
							previRow_temp = previRow5;
							previRow5 = rowid;
							break;
						default :
							previRow_temp = previRow;
							previRow = rowid;
					}
					var cols = jQuery(gridName).jqGrid('getGridParam', 'colModel');
				    var saveValueArray = new Array();
					for (var col in cols){
						if(cols[col].edittype == "checkbox"){
							pushVal = $(gridName).getCell(previRow_temp, cols[col].index);
						}else{
							var tempColVal = getColValue(cols[col].index,previRow_temp,gridName.replace("#", ""));
							//if(tempColVal == "") tempColVal = " ";
							//if(tempColVal == "") tempColVal = null;
							if(tempColVal == ""){
								if( (String(cols[col].formatter)).indexOf("numFormat") != -1 ){
									tempColVal = " ";
								}else{
									tempColVal = null;
								}
							}
							pushVal = tempColVal;
						}
						saveValueArray.push(pushVal);
					 }
				    //jQuery(gridName).jqGrid('saveRow',previRow_temp,  saveparameters);
				    jQuery(gridName).jqGrid('saveRow',previRow_temp);
					for (var col in cols){
						//alert("saveValueArray[col]="+saveValueArray[col]);
						var tempColVal = saveValueArray[col];
						//if(tempColVal == "null") tempColVal = "";
						jQuery(gridName).setCell(previRow_temp, cols[col].index, tempColVal);
					 }
				}
			},
			afterEditCell: function(rowid,name,val,iRow,iCol){
				cell_nm = name;
				cell_id = iRow + "_" + name;
				typeof(targetParam.afterEditFc) == "undefined" ? "" : eval(targetParam.afterEditFc);
			},
			beforeSaveCell: function(id,name,val,iRow,iCol){
				//jQuery(gridName).jqGrid("restoreRow", iRow);

				typeof(targetParam.beforeSaveFc) == "undefined" ? "" : eval(targetParam.beforeSaveFc);
			},
			afterSaveCell:function(rowid, cellname, value, iRow, iCol){
			},
			gridSelectAll : function(divID){
			},
			onSelectAll:function(rowid, isStatus){
				var gridRow = jQuery(gridName);
				var ids = gridRow.getDataIDs();
				if(isStatus){
					var cm = gridRow.jqGrid("getGridParam", "colModel");
					var rn = 2, cb = 1, cols = 0;

					for(var n = 1; n <= ids.length ; n++){
						if(n == 1){
							for(var k = 0; k < cm.length; k++){
								var colName = cm[k].name;
								if(colName == "rn" || colName == "cb"){
									cols++;
								}else if(gridRow.jqGrid("getColProp", cm[k].name).hidden == true){
									cols++;
								}else{
									if(getColValue(cm[k].name, n, gridName.replace("#", "")) != ""){
										break;
									}
								}

							}
						}

						if(getColValue(cm[cols].name, n, gridName.replace("#", "")) == ""){
							gridRow.jqGrid("setSelection", n, false);
						}else{
							//alert(getColValue(cm[cols].name, n, gridName.replace("#", "")));
						}
					}
					$("div.ui-jqgrid-bdiv").scrollTop(0);
				}
				typeof(targetParam.selectAllFc) == "undefined" ? "" : eval(targetParam.selectAllFc);
			},
			onSelectRow:function(rowid){
			},
			ondblClickRow: function(rowId,iRow,iCol,e) {
				typeof(targetParam.dblClickRowFc) == "undefined" ? "" : eval(targetParam.dblClickRowFc);
			},
			onCellSelect: function(rowid, iCol, cellcontent) {
				if(!cellSelectFlag) return;
				var previRow_temp = 0;
				switch (gridName.substring(gridName.length-1, gridName.length)){
				case "1":
					previRow_temp = previSRow1;
					break;
				case "2":
					previRow_temp = previSRow2;
					break;
				case "3":
					previRow_temp = previSRow3;
					break;
				case "4":
					previRow_temp = previSRow4;
					break;
				case "5":
					previRow_temp = previSRow5;
					break;
				default :
					previRow_temp = previSRow;
			}

				jQuery(gridName).setRowData (rowid,false,{background:'#B2EBF4'});
				if(previRow_temp != "" && previRow_temp != rowid){
					/*
					if(previRow_temp%2 == 0){
						jQuery(gridName).setRowData (previRow_temp,false,{background:'#f0f0f0'});
					}else{
						jQuery(gridName).setRowData (previRow_temp,false,{background:'#f8f8f8'});
					}
					*/
					jQuery(gridName).setRowData (previRow_temp,false,{background:'#f8f8f8'});
				}
				previRow_temp = rowid;

				switch (gridName.substring(gridName.length-1, gridName.length)){
					case "1":
						previSRow1 = rowid;
						break;
					case "2":
						previSRow2 = rowid;
						break;
					case "3":
						previSRow3 = rowid;
						break;
					case "4":
						previSRow4 = rowid;
						break;
					case "5":
						previSRow5 = rowid;
						break;
					default :
						previSRow = rowid;
				}
				
				$("#" + cell_id).attr("id", rowid + "_" + cell_nm);
				
				typeof(targetParam.selectCellFc) == "undefined" ? "" : eval(targetParam.selectCellFc);
			},
			loadComplete: function(rs){
				$('#'+gridName+'_tr.jqgrow:odd').addClass("myAltRowClass");
				if(rs.records > 0 || rs.rows != ""){
					setBottomMsg(getMessage("SEARCH.0000"));//setBottomMsg("정상적으로 조회 되었습니다.");
				}else{
					setBottomMsg(getMessage("SEARCH.0002"));//setBottomMsg("조회된 데이터가 없습니다.");
				}
				$("option[value=1000000]").text('All');

				$('#htmlPager').css('width', "100%");
				$('#htmlPager').css('font-weight', "bold");
				$('#htmlPager').css('color', "#003c65");

				typeof(targetParam.completeFc) == "undefined" ? "" : eval(targetParam.completeFc);
				try{
					var fnMerge = typeof(targetParam.fnMerge) == "undefiend" ? false : targetParam.fnMerge;
					if(fnMerge){ eval("setMerge()"); }
				}catch(e){
					var msg  = "[Name] : " + e.name    +"\n";
						msg += "[Value] : " + e.value   +"\n";
						msg += "[Massage] : " + e.message +"\n";
						alertUI(msg);
				}
			},
            gridComplete: typeof(targetParam.gridComplete) == "undefined" ? "" : targetParam.gridComplete,
			loadError : function(xhr, st, err){
			}
		});
		setGridColumnOptions(gridName, optionType);
	}

	/**
	 * 컬럼에서 Value값 추출
	 * @param url
	 * @param 컬럼명
	 * @param 컬럼속성
	 * @returns null
	 */
	function getColValue(colNm,rowId,gridName){

		var colVal = jQuery("#"+rowId+"_"+colNm).val();

		if(typeof(colVal) == "undefined"){
			colVal = jQuery(typeof(gridName) == "undefined" ? "#htmlTable" : "#"+gridName).getCell(rowId,colNm);
		}

		return $.trim(colVal);
	}

	function addGridRow(cntVal, gridName, gridData){

		if(typeof(gridName) == "undefined"){
			gridName = "htmlTable";
			datarowTemp = datarow;
		}else{
			datarowTemp = eval(gridData);
		}

		if(cntVal != null){
			var totRecord = cntVal;
	    	var finalRowNum = parseInt(jQuery('#'+gridName).jqGrid('getGridParam','records'))+1;

	    	for (i=finalRowNum; i<totRecord+1; i++){
	        	jQuery("#"+gridName).jqGrid("addRowData", i, datarowTemp);
	    	}
		}else{
			var totRecord = parseInt(jQuery('#'+gridName).jqGrid('getGridParam','rowNum'));
	    	var finalRowNum = parseInt(jQuery('#'+gridName).jqGrid('getGridParam','records'))+1;
	    	if(totRecord > 160) totRecord = 30; //150 건 이상인경우 발생으로 변경, 175건 이상이 스크립트 속도 문제 발생 - 13.09.09 오장환

	    	for (i=finalRowNum; i<totRecord+1; i++){
	        	jQuery("#"+gridName).jqGrid("addRowData", i, datarowTemp);
	    	}
		}
		$('#'+gridName+' tr.jqgrow:odd').addClass("myAltRowClass");
    }

    /**
	 * excel download
	 * @param grid
	 * @param path
	 * @param params
	 */
	function gridToExcel(grid, path, params){
		postToUrl(path, params, "post");
	}

	function postToUrl(path, params, method){
		method = method || "post";
		var form = document.createElement("form");
			form.setAttribute("method", method);
			form.setAttribute("action", path);
		for(var i=0; i<params.length; i++) {
			var hiddenField = document.createElement("input");
				hiddenField.setAttribute("type", "hidden");
				hiddenField.setAttribute("name",  params[i].name);
				hiddenField.setAttribute("value", params[i].value);
			form.appendChild(hiddenField);
		}

		document.body.appendChild(form);
		form.submit();
	}

	function setGridColumnOptions(gridName, optiontype){

		var gridName = typeof(gridName) == "undefined" ? "#htmlTable" : gridName;
		var gridNameTemp = gridName.replace("#", "");
		var rows = jQuery(gridName).getRowData();
		var cols = jQuery(gridName).jqGrid('getGridParam', 'colModel');

		for (var col in cols){
			if(typeof(cols[col].editoptions) == 'undefined'){
				if(cols[col].edittype != "checkbox" && cols[col].edittype != "select"){
					jQuery(gridName).jqGrid('setColProp', cols[col].index, {editoptions:{
				        dataEvents:[{type:'keydown',
						    fn:function(e){
						    	if(e.keyCode == 9 || e.keyCode == 16|| e.keyCode == 18) return;
						    	var row = $(e.target).closest('tr.jqgrow');
								var rowId = row.attr('id');
		                        var cbs = jQuery("#jqg_"+gridNameTemp+"_"+rowId);
		                        if(!cbs.is(":checked")){
		                        	jQuery(gridName).jqGrid("setSelection", rowId, true);
		                        }
				    		}
				    	}]
					}});
				}else{
					if(optiontype != "N"){
						jQuery(gridName).jqGrid('setColProp', cols[col].index, {editoptions:{
					        dataEvents:[{type:'change',
							    fn:function(e){
							    	var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridNameTemp+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery(gridName).jqGrid("setSelection", rowId, true);
			                        }
					    		}
					    	}]
						}});
					}
				}

		     }else{
		    	 if(cols[col].edittype != "checkbox" && cols[col].edittype != "select"){
		    		 cols[col].editoptions.dataEvents = [{type:'keydown',
						    fn:function(e){
						    	if(e.keyCode == 9 || e.keyCode == 16|| e.keyCode == 18) return;
						    	var row = $(e.target).closest('tr.jqgrow');
								var rowId = row.attr('id');
		                        var cbs = jQuery("#jqg_"+gridNameTemp+"_"+rowId);
		                        if(!cbs.is(":checked")){
		                        	//jQuery(gridName).setSelection(rowId, true);
		                        	jQuery(gridName).jqGrid("setSelection", rowId, true);
		                        }
			    		}
			    	}];
		    		 jQuery(gridName).jqGrid('setColProp', cols[col].index, cols[col].editoptions);
		    	 }else{
		    		 if(optiontype != "N"){
		    			 cols[col].editoptions.dataEvents = [{type:'change',
							    fn:function(e){
							    	var row = $(e.target).closest('tr.jqgrow');
									var rowId = row.attr('id');
			                        var cbs = jQuery("#jqg_"+gridNameTemp+"_"+rowId);
			                        if(!cbs.is(":checked")){
			                        	jQuery(gridName).jqGrid("setSelection", rowId, true);
			                        }
				    		}
				    	}];
			    		 jQuery(gridName).jqGrid('setColProp', cols[col].index, cols[col].editoptions);
		    		 }
		    	 }
		     }
		 }
	}

	function setGridBorderNone(gridName){
		$("#"+gridName).find("td").attr("style", "border:1px solid #f8f8f8;");
	}

	function getLpad(strVal, totalLen, strReplace){
		var strAdd = "";
		var diffLen = totalLen - String(strVal).length;

		for(var i=0; i<diffLen; ++i){
			strAdd += strReplace;
		}

		return strAdd + strVal;
	}
	/**
	 * 유저정보 그리드 세팅
	 * var userKeyArr = ['name','dept'....];  - 그리드,폼
	 * var userColArr = ['xusr_name','xusr_dept'...]; - 데이터
	 * doCommonAjax("/doSearchToUserInfo.do", paramData, "setUserInfo(jsonData.sendResult,'Y',"+rowId+");"); -그리드
	 * doCommonAjax("/doSearchToUserInfo.do", paramData, "setUserInfo(jsonData.sendResult,'N');"); -폼
	 */
	function setUserInfo(jsonDataArray,gridYn,rowId, gridName){
		var setGridYn = typeof(gridYn) == "undefined" ? "N" : gridYn;

		if(setGridYn == 'Y'){
			$.each(userColArr,function(idx, colNm){
				$.each(jsonDataArray,function(index,jsonData){
					if(index == colNm){
						jQuery(typeof(gridName) == "undefined" ? "#htmlTable" : "#"+gridName).setCell(rowId,userKeyArr[idx],jsonData);
					}
				});
			});
		}
		else{
			$.each(userColArr,function(idx, colNm){
				$.each(jsonDataArray,function(index,jsonData){
					if(index == colNm){
						$('#'+userKeyArr[idx]).val(jsonData);
					}
				});
			});
		}
	}

	function overLineHtml(d){
		var i = /\n/g;
		var v = stripHtml(d);
		if (v)
			return v.replace(i, '\\n');
		else
			return v;
	}

	function stripHtml(d) {
		d += "";
		var i = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
		if (d)
			return (d = d.replace(i, "")) && d !== "&nbsp;"&& d !== "&#160;" ? d.replace(/\"/g,"'") : "";
		else
			return d;
	}

	function htmlDecode(d) {
		return !d ? d : String(d).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g,'"').replace(/&amp;/g, "&");
	}

	function htmlEncode(d) {
		return !d ? d : String(d).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g,"&lt;").replace(/>/g, "&gt;");
	}

	function getCookie( name ){
		var nameOfCookie = name + "=";      var x = 0;
		while ( x <= document.cookie.length ){
			var y = (x+nameOfCookie.length);
			if ( document.cookie.substring( x, y ) == nameOfCookie ){
				if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
					endOfCookie = document.cookie.length;
				return unescape( document.cookie.substring( y, endOfCookie ) );
			}
			x = document.cookie.indexOf( " ", x ) + 1;
			if ( x == 0 ) break;
		}
		return "";
	}

	function logOut(){
//		var msg  = "로그아웃 하시겠습니까? ";
		var msg  = getMessage("COMM.0000");

		confirmUI(msg);
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
					};
					doCommonAjax("/logOut.do", paramData, "top.location.href = '"+ctxPath+"/index.htm'");
				}
			});
		});
	}

	function addDay(ymd, v_day){

		 var yyyy = ymd.substr(0,4);
		 var mm = eval(ymd.substr(4,2) + "- 1") ;
		 var dd = ymd.substr(6,2);
		 var dt3 = new Date(yyyy, mm, eval(dd + '+' + v_day));

		 yyyy = dt3.getYear();
		 mm = (dt3.getMonth()+1)<10? "0" + (dt3.getMonth()+1) : (dt3.getMonth()+1) ;
		 dd = dt3.getDate()<10 ? "0" + dt3.getDate() : dt3.getDate();

		 return  "" + yyyy + "" + mm + "" + dd ;

	}
	function changeToUni(strVal){

		var strText = strVal.replace(/</g, "").replace(/>/g, "");

		var strValue1 = "%";
		var strValue2 = "\\";
		var strValue3 = "%20";

		var strTemp = "";
		for(var k=0;k<strText.length;k++){
			var strCh = strText.substring(k,k+1);
			strTemp+=changeToUniDt(strCh);
		}

		 while(1){
			 if( strTemp.indexOf(strValue3) != -1 )
		            strTemp = strTemp.replace(strValue3, ' ');
		        else
		            break;
		 }
	    while(1){
	        if( strTemp.indexOf(strValue1) != -1 )
	            strTemp = strTemp.replace(strValue1, strValue2);
	        else
	            break;
	    }
	    return strTemp;
	}
	function changeToUniDt(strVal){
		var strTemp = escape(strVal);
		if(strTemp.length ==3){
			strTemp = strTemp.substring(0,1)+"u00"+strTemp.substring(1);
		}
		return strTemp;
	}

	function timeFormatGrid(rowId,colNm,gridNm,gubun){
		var gridName = jQuery(typeof(gridNm) == "undefined" ? "#htmlTable" : "#"+gridNm);
		var strTime = selectNum(getColValue(colNm,rowId,gridName));

		var strTimeTemp = strTime;
		var strLength = strTime.length;

		if(gubun == 'in'){
			strTime = trimChar(strTime, ':');
		}else{
			strTime = strTime.substring(0, 2) + ':' + strTime.substring(2, 4);
			if(strLength == 3){
				strTime = '0'+strTimeTemp.substring(0, 1) + ':' + strTimeTemp.substring(1, 3);
			}else if(strLength > 4){
				trTime = '0'+strTimeTemp.substring(0, 1) + ':' + strTimeTemp.substring(1, 3);
			}
		}

		return strTime;
	}

	function readOnlyStyle(objNm, type){
		if(type == "1"){
			$("#"+objNm).attr("class", "disabled");
			$("#"+objNm).attr("readonly", true);

			$("#"+objNm).keydown(function(event){
				if(event.keyCode == 8){
					return false;
				}
			});
		}else{
			$("#"+objNm).attr("class", "");
			$("#"+objNm).attr("readonly", false);
		}
	}

	jQuery(document).keydown(function(e){
		if(e.target.nodeName != "INPUT" && e.target.nodeName != "SELECT" && e.target.nodeName != "TEXTAREA"){
			if(e.keyCode == 8){
				return false;
			}
		}
	});

	function tooltipDisplay(classNm){

		$('.'+classNm).poshytip({
			className: 'tip-darkgray',
			bgImageFrameSize: 11,
			offsetX: -25
		});

		var flickrFeedsCache = {};
		$('#demo-async-flickr > a').poshytip({
			className: 'tip-darkgray',
			bgImageFrameSize: 11,
			alignY: 'bottom',
			content: function(updateCallback) {
				var rel = $(this).attr('rel');
				if (flickrFeedsCache[rel] && flickrFeedsCache[rel].container)
					return flickrFeedsCache[rel].container;
				if (!flickrFeedsCache[rel]) {
					flickrFeedsCache[rel] = { container: null };
					var tagsComma = rel.substring(4).replace('-', ',');
					$.getJSON('http://api.flickr.com/services/feeds/photos_public.gne?tags=' + tagsComma + '&tagmode=all&format=json&jsoncallback=?',
						function(data) {
							var container = $('<div/>').addClass('flickr-thumbs');
							$.each(data.items, function(i, item) {
								$('<a/>')
									.attr('href', item.link)
									.append($('<img/>').attr('src', item.media.m))
									.appendTo(container)
									.data('tip', '<strong>' + (item.title || '(no title)') + '</strong><br />by: ' + item.author.match(/\((.*)\)/)[1]);
								if (i == 4)
									return false;
							});
							// add tips for the images inside the main tip
							container.find('a').poshytip({
								content: function(){return $(this).data('tip');},
								className: 'tip-yellowsimple',
								showTimeout: 100,
								alignTo: 'target',
								alignX: 'center',
								alignY: 'bottom',
								offsetY: 5,
								allowTipHover: false,
								hideAniDuration: 0
							});
							// call the updateCallback function to update the content in the main tooltip
							// and also store the content in the cache
							updateCallback(flickrFeedsCache[rel].container = container);
						}
					);
				}
				return 'Loading images...';
			}
		});
	}

	function getMultiComboValue(comboName, keyName){

		var comboVal2='<option role="option" value="">선택</option>';

		$.each(eval(comboVal),function(targetNm,optionData){

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

	function commonSelectClear(comboName){
		$("select#" + comboName).html('<option role="option" value="">선택</option>');
	}

	function fixNumberScale(number, scale){
		var ex = Math.pow(10, scale);
		return Math.round(number * ex) / ex;
	}

	var app_win;
	function myApprovalPopup(){
		if(app_win != null){ app_win.close(); }

		var url = ctxPath+"/hncis/system/xst09.gas";
		var width = "1000";
		var height = "590";

		app_win = newPopWin("about:blank", width, height, "POP_MY_APPR");

		document.approvalForm.hid_csrfToken.value = $("#csrfToken").val();
		document.approvalForm.action = url;
		document.approvalForm.target = "POP_MY_APPR";
		document.approvalForm.method = "post";
		document.approvalForm.submit();

//		var width = "1000";
//		var height = "590";
//		app_win = newPopWin("about:blank", width, height, "POP_MY_APPR");
//
//		var form = $("<form/>");
//        form.attr("method" , "post");
//        form.attr("id"     , "approvalForm");
//        form.attr("name"   , "approvalForm");
//        form.attr("action" , ctxPath+"/hncis/system/xst09.gas");
//        form.attr("target" , "POP_MY_APPR");
//        var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
//        form.append(token);
//        $("body").append(form);
//        form.submit();
//        form.remove();
	}

	function goSystem(){
//		var hideForm = document.hideForm;
//		hideForm.hid_csrfToken.value = $("#csrfToken").val();
//		hideForm.action = ctxPath+"/hncis/system/xst01.gas";
//		document.hideForm.target = "";
//		hideForm.submit();

		var form = $("<form/>");
        form.attr("method" , "post");
        form.attr("id"     , "systemForm");
        form.attr("name"   , "systemForm");
        form.attr("action" , ctxPath+"/hncis/system/xst01.gas");
        form.attr("target" , "");
        var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
        form.append(token);
        $("body").append(form);
        form.submit();
        form.remove();
	}

	function goMenuSetting(){
		var form = $("<form/>");
        form.attr("method" , "post");
        form.attr("id"     , "systemForm");
        form.attr("name"   , "systemForm");
        form.attr("action" , ctxPath+"/menuSetting.gas");
        form.attr("target" , "");
        var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
        form.append(token);
        $("body").append(form);
        form.submit();
        form.remove();
	}

	function setRowEditMode(gridName, rowId){
		var cols = jQuery("#"+gridName).jqGrid('getGridParam', 'colModel');
		var saveValueArray = new Array();
		for (var col in cols){
			if(cols[col].edittype == "checkbox"){
				pushVal = $("#"+gridName).getCell(rowId, cols[col].index);
			}else{
				var tempColVal = getColValue(cols[col].index,rowId,gridName.replace("#", ""));
				//if(tempColVal == "") tempColVal = " ";
				pushVal = tempColVal;
			}
			saveValueArray.push(pushVal);
		 }
	    jQuery("#"+gridName).jqGrid('saveRow',rowId);
		for (var col in cols){
			var tempColVal = saveValueArray[col];
			jQuery("#"+gridName).setCell(rowId, cols[col].index, tempColVal);
		 }
		jQuery("#"+gridName).jqGrid('editRow',rowId);
	}

	function setRowEditModeByPrev(gridName, rowid, e){
		var cols = jQuery(gridName).jqGrid('getGridParam', 'colModel');
		var iCol =  $.jgrid.getCellIndex($(e.target).closest("td")[0]);
        if(!cols[iCol].editable){
        	var previRow_temp = 0;
			jQuery(gridName).jqGrid('editRow',rowid);
			switch (gridName.substring(gridName.length-1, gridName.length)){
				case "1":
					previRow_temp = previRow1;
					previRow1 = rowid;
					break;
				case "2":
					previRow_temp = previRow2;
					previRow2 = rowid;
					break;
				case "3":
					previRow_temp = previRow3;
					previRow3 = rowid;
					break;
				case "4":
					previRow_temp = previRow4;
					previRow4 = rowid;
					break;
				case "5":
					previRow_temp = previRow5;
					previRow5 = rowid;
					break;
				default :
					previRow_temp = previRow;
					previRow = rowid;
			}
			var cols = jQuery(gridName).jqGrid('getGridParam', 'colModel');
		    var saveValueArray = new Array();
			for (var col in cols){
				if(cols[col].edittype == "checkbox"){
					pushVal = $(gridName).getCell(previRow_temp, cols[col].index);
				}else{
					var tempColVal = getColValue(cols[col].index,previRow_temp,gridName.replace("#", ""));
					if(tempColVal == "") tempColVal = " ";
					pushVal = tempColVal;
				}
				saveValueArray.push(pushVal);
			}

		    jQuery(gridName).jqGrid('saveRow',previRow_temp);
		    
		    var colMaxSize = cols.length;
			for (var col in cols){
				var tempColVal = saveValueArray[col];
				if(Number(col)+1 < colMaxSize){
					if(cols[col].name == "auth" || cols[Number(col)+1].name == "auth" ){
					}else{
						jQuery(gridName).setCell(previRow_temp, cols[col].index, tempColVal);
					}
				}
			 }
        }
	}

	function fnTimeCheck(val){
		if(selectNum(val).length != 4){
			return false;
		}

		var tmpTime = selectNum(val);

		tmpTimeHH = parseInt(tmpTime.substring(0,2), 10);
		tmpTimeMM = parseInt(tmpTime.substring(2)  , 10);

		if(!(tmpTimeHH >= 00 && tmpTimeHH <= 23)){
			return false;
		}

		if(!(tmpTimeMM >= 00 && tmpTimeMM <= 59)){
			return false;
		}
	}

	function bluring(){
		if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG") document.body.focus();
	}
	document.onfocusin=bluring;

	$(document).keydown(function(e){
        if($(e.target).attr("readonly") || $(e.target).attr("disabled")){
        	if(e.keyCode === 8){
        		return false;
        	}
        }
    });

    window.history.forward(0);


    var pageSize    = 10;
    var pageNumSize = 10;

    function fnGetPageStartNum(nPage){
        return (nPage == 0 ? 1 : nPage) * pageSize - (pageSize-1);
    }

    function fnGetPageEndNum(nPage){
        return (nPage == 0 ? 1 : nPage) * pageSize;
    }

    /**
     * 공통 TABLE SEARCH 함수
     * @param url
     * @param search    : parameter
     * @param success   : 함수명
     * @param tableName : table id
     * @param trTag     : trTag
     * @param nPage     : 현재페이지(페이징 사용시만)
     * @returns null
     */
    function doCommonSearchTable(url, paramData, successFc, tableName, trTag, nPage){

        url = url.substring(0,1) == "/" ? ctxPath+url : url;

        $.ajax( {
            type :'POST'
            ,asyn :true
            ,url : url
            ,data:paramData
            ,dataType :"json"
            ,beforeSend : function(xhr){
            }
            ,success : function(jsonData) {
//                if( typeof(nPage) != 'undefined' &&
//                    $("#"+tableName+"_pageNavigator").length > 0){
//                    $("#"+tableName+"_pageNavigator").html(fnGetPageNavigator(nPage, jsonData.total));
//                }
//
//                $("#"+tableName+" tbody").empty();
//                if( typeof(tableName) != 'undefined'){
//                    fnSetTableRowDatas(tableName, trTag, jsonData.rows1);
//                }
                eval(successFc);
            }
            ,error : function(xhr, textStatus) {
            	alertUI("[ERROR]시스템 에러 입니다. 관리자에게 문의 하십시오.");
            }
            ,complete : function(xhr, textStatus) {
            }
        });
    }

    function doCommonSearchTableForPop(url, paramData, successFc, tableName, trTag, nPage){

        url = url.substring(0,1) == "/" ? ctxPath+url : url;

        $.ajax( {
            type :'POST'
            ,asyn :true
            ,url : url
            ,data:paramData
            ,dataType :"json"
            ,beforeSend : function(xhr){
            }
            ,success : function(jsonData) {
                if( typeof(nPage) != 'undefined' &&
                    $("#"+tableName+"_pageNavigator").length > 0){
                    $("#"+tableName+"_pageNavigator").html(fnGetPageNavigatorForPop(nPage, jsonData.total));
                }

                $("#"+tableName+" tbody").empty();
                if( typeof(tableName) != 'undefined'){
                    fnSetTableRowDatas(tableName, trTag, jsonData.rows1);
                }
                eval(successFc);
            }
            ,error : function(xhr, textStatus) {
            }
            ,complete : function(xhr, textStatus) {
            }
        });
    }

    /**
     * Table Tag에 데이터를 셋팅(다른화면에서 넘어오는 Parameter 처리도 담당하기 위해서 분리)
     * @param tableName : Table Tag ID
     * @param trTag     : 화면로딩시 js변수에 담겨있는 Default Tr
     * @param rows      : 데이터(Json Array)
     * @param successFc : 데이터 Setting 완료 후 호출될 CallBack(없을경우 안넣어도 됨)
     * return void
     */
    function fnSetTableRowDatas(tableName, trTag, rows, successFc){
        var tdnm =  $("#"+tableName+">tbody");

        $.each(rows, function(iRow, datas){
            $("#"+tableName+">tbody").append(trTag.replace(/_0/g,"_"+(iRow+1)));
            $.each(datas, function(idx, opt){
                if($("#"+idx+"_"+(iRow+1),tdnm).length > 0){
                    switch($("#"+idx+"_"+(iRow+1)).prop("tagName").toLowerCase()){
                    case "font" :
                    case "td" :
                    case "a" :
                    case "span" :
                    case "p" :
                        $("#"+idx+"_"+(iRow+1)).html(opt);
                        break;
                    case "textarea" :
                    case "select" :
                         $("#"+idx+"_"+(iRow+1)).on("change keypress", function() {
                             //select타입 변경시 첫번재 td의 체크박스 체크
                             $(this).parent().parent().find('td:eq(0) input[type=checkbox]').prop("checked",true);
                         });

                        $("#"+idx+"_"+(iRow+1)).val(opt);
                        break;
                    case "img" :
                        if(opt == ""){
//                            $("#"+idx+"_"+(iRow+1),tdnm).attr("src", ctxPath+"/images/common/no_image.png");
                            $("#"+idx+"_"+(iRow+1),tdnm).hide();
                        }else{
                            if(idx.indexOf('file') > -1){
                                $("#"+idx+"_"+(iRow+1),tdnm).attr("src", ctxPath+"/images/btn/btn_filedown_tbl.png");
                            } else {
                                $("#"+idx+"_"+(iRow+1),tdnm).attr("src", ctxPath+"/upload"+opt);
                            }
                        }
                        break;
                   case "input" :
                        switch($("#"+idx+"_"+(iRow+1),tdnm).prop("type").toLowerCase()){
                        case "checkbox" :
                            $("#"+idx+"_"+(iRow+1),tdnm).prop("checked", opt == "Y" ? true : false);
                            break;
                        default :
                            $("#"+idx+"_"+(iRow+1),tdnm).val(opt);
                        }
                        break;
                    default :
                        $("#"+idx+"_"+(iRow+1)).val(opt);
                    }
                }
            });

            $("#"+tableName+" tbody tr").show();
        });

        if(typeof(successFc) != 'undefined'){
            eval(successFc);
        }
    }

    /**
     * PageNavigator Script Ver.
     * @param now_page  : 현재 페이지
     * @param total_cnt : 총 카운트
     * @return pageNavigator : Html 형태
     */
    function fnGetPageNavigator(nowPage, totalCnt){
        var pageNavigator = "";
        if(totalCnt == 0){
            pageNavigator = "<span><font style='font-size: 16px;'>"+getMessage("SEARCH.0002")+"</font></span>";//조회된 데이터가 없습니다.
        }else{
            var total_page_cnt = Math.floor(totalCnt / pageSize) + (totalCnt % pageSize == 0 ? 0 : 1);                      // 전체페이지 계산
            var page_size_num = Math.floor(nowPage / pageNumSize) + (nowPage % pageNumSize == 0 ? 0 : 1);
            var start_page_num = page_size_num * pageNumSize - pageNumSize + 1;                                             // 시작페이지 번호 계산
            var end_page_num = start_page_num + pageNumSize - 1;                                                            // 마지막페이지 번호 계산
            end_page_num = total_page_cnt < end_page_num ? total_page_cnt : end_page_num;                                   // 마지막페이지가 전체페이지보다 클 경우

            var prev_start_page_num = start_page_num - pageNumSize;                                                         // 이전페이지 번호
            prev_start_page_num = prev_start_page_num < 1 ? 1 : prev_start_page_num;
            var next_end_page_num = start_page_num + pageNumSize;                                                           // 다음페이지 번호

            //pageNavigator += "<ul>";

            if (start_page_num > 1) {                                                                                       // 현재 페이지가 첫 페이지가 아닐 경우
//              pageNavigator += "<li style=\"cursor:pointer;\" onclick=\"fnPageMove('1');\"><img src=\""+ctxPath+"/images/common/page/prev_end.gif\"/></li>";                  // 첫 페이지
//              pageNavigator += "<li style=\"cursor:pointer;\" onclick=\"fnPageMove('" + (start_page_num - pageNumSize) + "');\"><img src=\""+ctxPath+"/images/common/page/prev.gif\"/></li>";                     // 이전 페이지
                pageNavigator += "<a href=\"javascript:fnPageMove('1');\" class=\"btn_first\"><span>처음</span></a>";             // 첫 페이지
                pageNavigator += "<a href=\"javascript:fnPageMove('" + (start_page_num - pageNumSize) + "');\" class=\"btn_prev\"><span>이전</span></a>";             // 이전 페이지
            }
            for (var i = start_page_num; i <= end_page_num; i++) {
                if (nowPage == i) {
//                  pageNavigator += "<li class=\"current\">"+i+"</li>";
                    pageNavigator += "<a href=\"javascript:;\" class=\"on\">" + i + "</a>";     // 현재 페이지
                } else {
//                  pageNavigator += "<li style=\"cursor:pointer;\" onclick=\"fnPageMove('" + i + "');\">"+i+"</li>";
                    pageNavigator += "<a href=\"javascript:fnPageMove('" + i + "');\">" + i + "</a>";           // 다른 페이지
                }
            }

            if (total_page_cnt > end_page_num) {                                                                            // 마지막 페이지가 전체 페이지보다 작을 경우
                pageNavigator += "<a href=\"javascript:fnPageMove('" + next_end_page_num + "');\" class=\"btn_next\"><span>다음</span></a>";              // 다음페이지
                pageNavigator += "<a href=\"javascript:fnPageMove('" + total_page_cnt + "');\" class=\"btn_last\"><span>마지막</span></a>";                // 마지막 페이지
//              pageNavigator += "<li style=\"cursor:pointer;\" onclick=\"fnPageMove('" + (start_page_num + pageNumSize) + "');\"><img src=\""+ctxPath+"/images/common/page/next.gif\"/></li>";                     // 다음페이지
//              pageNavigator += "<li style=\"cursor:pointer;\" onclick=\"fnPageMove('" + total_page_cnt + "');\"><img src=\""+ctxPath+"/images/common/page/next_end.gif\"/></li>";                 // 마지막 페이지
            }

            //pageNavigator += "</ul>";
        }

        return pageNavigator;
    }

    function fnGetPageNavigatorForPop(nowPage, totalCnt){
        var pageNavigator = "";
        if(totalCnt == 0){
            pageNavigator = getMessage("SEARCH.0002");//"조회된 데이터가 없습니다.";
        }else{
            var total_page_cnt = Math.floor(totalCnt / pageSize) + (totalCnt % pageSize == 0 ? 0 : 1);                      // 전체페이지 계산
            var page_size_num = Math.floor(nowPage / pageNumSize) + (nowPage % pageNumSize == 0 ? 0 : 1);
            var start_page_num = page_size_num * pageNumSize - pageNumSize + 1;                                             // 시작페이지 번호 계산
            var end_page_num = start_page_num + pageNumSize - 1;                                                            // 마지막페이지 번호 계산
            end_page_num = total_page_cnt < end_page_num ? total_page_cnt : end_page_num;                                   // 마지막페이지가 전체페이지보다 클 경우

            var prev_start_page_num = start_page_num - pageNumSize;                                                         // 이전페이지 번호
            prev_start_page_num = prev_start_page_num < 1 ? 1 : prev_start_page_num;
            var next_end_page_num = start_page_num + pageNumSize;                                                           // 다음페이지 번호

            if (start_page_num > 1) {                                                                                       // 현재 페이지가 첫 페이지가 아닐 경우
                pageNavigator += "<a href=\"javascript:fnPageMovePop('1');\" class=\"btn_first\"><span>처음</span></a>";             // 첫 페이지
                pageNavigator += "<a href=\"javascript:fnPageMovePop('" + (start_page_num - pageNumSize) + "');\" class=\"btn_prev\"><span>이전</span></a>";             // 이전 페이지
            }
            for (var i = start_page_num; i <= end_page_num; i++) {
                if (nowPage == i) {
                    pageNavigator += "<a href=\"javascript:;\" class=\"on\">" + i + "</a>";     // 현재 페이지
                } else {
                    pageNavigator += "<a href=\"javascript:fnPageMovePop('" + i + "');\">" + i + "</a>";           // 다른 페이지
                }
            }

            if (total_page_cnt > end_page_num) {                                                                            // 마지막 페이지가 전체 페이지보다 작을 경우
                pageNavigator += "<a href=\"javascript:fnPageMovePop('" + next_end_page_num + "');\" class=\"btn_next\"><span>다음</span></a>";              // 다음페이지
                pageNavigator += "<a href=\"javascript:fnPageMovePop('" + total_page_cnt + "');\" class=\"btn_last\"><span>마지막</span></a>";                // 마지막 페이지
            }
        }

        return pageNavigator;
    }

    /**
     * 한국식으로 입력 해서 영어식으로 표현하기
     * @param sDate 20160101
     * @param nDays -1,0,+1
     * @returns {String}
     */
    function date_add(sDate, nDays) {
        var yy = parseInt(sDate.substr(0, 4), 10);
        var mm = parseInt(sDate.substr(4, 2), 10);
        var dd = parseInt(sDate.substr(6), 10);

        d = new Date(yy, mm - 1, dd + nDays);

        yy = d.getFullYear();
        mm = d.getMonth() + 1; mm = (mm < 10) ? '0' + mm : mm;
        dd = d.getDate(); dd = (dd < 10) ? '0' + dd : dd;

        return '' + dd + '/' +  mm  + '/' + yy;
    }

    function numFormat( cellvalue, options, rowObject ){
        return String(cellvalue).replace(".",",");
    }

    function numUnformat( cellvalue, options, rowObject ){
        return cellvalue.replace(",",".");
    }

    function numFormat2( cellvalue, options, rowObject ){
    	var str = $.trim(String(cellvalue)).replace(",",".");
	    if(str.length > 0){
	    	var chk = str.indexOf(".");
	    	if(chk > 0){
	    		var arrStr = str.split(".");
	    		if(arrStr[1].length == 1){
	    			str = str + "0";
	    		}
	    	}else{
	    		str = str + ".00";
	    	}
    	}
        return str.replace(".",",");
    }


    /*
     *
     * 같은 값이 있는 열을 병합함
     *
     * 사용법 : $('#테이블 ID').rowspan(0);
     *
     */
    $.fn.rowspan = function(colIdx, isStats) {
    	return this.each(function(){
    		var that;
    		$('tr', this).each(function(row) {
    			$('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

    				if ($(this).html() == $(that).html()
    					&& (!isStats || isStats && $(this).prev().html() == $(that).prev().html())) {
    					rowspan = $(that).attr("rowspan") || 1;
    					rowspan = Number(rowspan)+1;

    					$(that).attr("rowspan",rowspan);

    					// do your action for the colspan cell here
    					$(this).hide();

    					//$(this).remove();
    					// do your action for the old cell here
    				} else {
    					that = this;
    				}

    				// set the that if not already set
    				that = (that == null) ? this : that;
    			});
    		});
    	});
    };

    /*
     *
     * 같은 값이 있는 행을 병합함
     *
     * 사용법 : $('#테이블 ID').colspan (0);
     *
     */
    $.fn.colspan = function(rowIdx) {
    	return this.each(function(){

    		var that;
    		$('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
    			$(this).find('th').filter(':visible').each(function(col) {
    				if ($(this).html() == $(that).html()) {
    					colspan = $(that).attr("colSpan") || 1;
    					colspan = Number(colspan)+1;

    					$(that).attr("colSpan",colspan);
    					$(this).hide(); // .remove();
    				} else {
    					that = this;
    				}

    				// set the that if not already set
    				that = (that == null) ? this : that;
    			});
    		});
    	});
    };

    function fnStartLoading(){
		$.blockUI({
			message: $('#loading_progress_img'),
			css: {
				top:  ($(window).height() - 130) /2 + 'px',
				left: ($(window).width() - 362) /2 + 'px',
				width: '362px'
			}
		});
	}

	function fnEndLoading(){
		$.unblockUI();
	}

	function getTmpDocNo(){
		var d = new Date();
		var mm = d.getMonth()+1;
		var dd = d.getDate();
		var hh = d.getHours();
		var mi = d.getMinutes();
		var ss = d.getSeconds();
		if(mm < 10){ mm = "0" + mm; }
		if(dd < 10){ dd = "0" + dd; }
		if(hh < 10){ hh = "0" + hh; }
		if(mi < 10){ mi = "0" + mi; }
		if(ss < 10){ ss = "0" + ss; }
		var tmpDocNo = d.getFullYear() +""+ mm +""+ dd +""+ hh +""+ mi +""+ ss +""+ parseInt(Math.random() * 10);

		return tmpDocNo;
	}

	//에디터 초기 세팅
	function initEditForm(initId, edtYn){
	    var config = {
	            txHost: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
	            txPath: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
	            txService: 'sample', /* 수정필요없음. */
	            txProject: 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
	            initializedId: "_"+initId, /* 대부분의 경우에 빈문자열 */
	            wrapper: "tx_trex_container_"+initId, /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
	            form: "tx_editor_div_"+initId, /* 등록하기 위한 Form 이름 */
	            txIconPath: ctxPath+"/daumeditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
	            txDecoPath: ctxPath+"/daumeditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
	            canvas: {
	                styles: {
	                    //fontFamily: "현대하모니 M", /* 기본 글자체 */
	                    //backgroundColor: "#fff", /*기본 배경색 */
	                    //lineHeight: "1.5", /*기본 줄간격 */
	                    //padding: "8px", /* 위지윅 영역의 여백 */
	                    fontSize: "12pt" /* 기본 글자크기 */
	                },
	                showGuideArea: false
	            },
	            events: {
	                preventUnload: false
	            },
	            sidebar: {
	                attachbox: {
	                    show: false
	                }
	            }
	        };

	    return config;
	}

	function editViewHtmlSt(){
	    var aa = "";
	    aa+='<!DOCTYPE html>';
	    aa+='<html lang="ko">';
	    aa+='<head>';
	    aa+='<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>';
	    aa+='<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">';
	    aa+='<title></title>';
	    aa+='<script id="txScriptForEval"><\/script>';
	    aa+='<style id="txStyleForSetRule"></style>';
	    aa+='<style type="text/css">';
	    aa+='p { margin:0; padding:0; }';
	    aa+='</style>';
	    aa+='</head>';
	    aa+='<body class="tx-content-container" style="padding: 0px; background-color: rgb(255, 255, 255);" contentEditable="false">';
//	    aa+='<body class="tx-content-container" style="padding-top: 8px; padding-right: 8px; padding-bottom: 8px; padding-left: 8px; background-color: rgb(255, 255, 255);" contentEditable="false">';

	    return aa;
	}

	function editViewHtmlEnd(){
	    var aa = "";
	    aa+='</body>';
	    aa+='</html>';
	    return aa;
	}

	/**
	 * 시간 형식 체크
	 * @param obj : 시간이 입력된 input obj
	 * @return
	 */
	function check_time(obj){
		var strTime = obj.value;

		strTime = trimChar(strTime, ':');
		strTime.replace(/:/g, '');

		var strLength = strTime.length;

		if(strLength==4){
			strTime = strTime.substring(0, 2) + ':' + strTime.substring(2, 4);
			var pt = /^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$/;
			if(!pt.test(strTime)){
				alertUI("정확한 시간이 아닙니다.");
				obj.value="";
				obj.focus();
			}
		}
	}

	/**
	 * Notes     : 기준일자에서 가감 년도를 구하는 함수.
	 * Parameter : 기준일자, 가감년도
	 * Return    : 일자
	 * Use       : getAddYearhDate(stdDate, 1);
	 */
	function getAddYearhDate(stdDate, addYear){
	    return getAddDate(stdDate, addYear, 0, 0);
	}

	/**
	 * Notes     : 기준일자에서 가감 월를 구하는 함수.
	 * Parameter : 기준일자, 가감월
	 * Return    : 일자
	 * Use       : getAddMonthDate(stdDate, 1);
	 */
	function getAddMonthDate(stdDate, addMonth){
	    return getAddDate(stdDate, 0, addMonth, 0);
	}

	/**
	 * Notes     : 기준일자에서 가감 일자를 구하는 함수.
	 * Parameter : 기준일자, 가감일
	 * Return    : 일자
	 * Use       : getAddDayDate(stdDate, 1);
	 */
	function getAddDayDate(stdDate, addDay){
	    return getAddDate(stdDate, 0, 0, addDay);
	}

	/**
	 * Notes     : 기준일자에서 가감 일자를 구하는 함수.
	 * Parameter : 기준일자, 가감년도, 가감월, 가감일
	 * Return    : 일자
	 * Use       : getAddDate(thisDay, 0, 1, 0);
	 */
	function getAddDate(stdDate, addYear, addMonth, addDay){
	    var addDate = "";
	    if(stdDate.length == 8){
	        var year  = parseInt(stdDate.substring(0, 4), 10) + addYear;
	        var month = parseInt(stdDate.substring(4, 6), 10) + addMonth;
	        var day   = parseInt(stdDate.substring(6)   , 10) + addDay;

	        var dateObj = new Date(year, (month-1), day);

	        var strYear  = dateObj.getFullYear().toString();
	        var strMonth = (dateObj.getMonth()+1).toString();
	        var strDate  = dateObj.getDate().toString();

	        addDate  = (strYear.length  < 4 ? ("19" + strYear) : strYear) +
	                   (strMonth.length < 2 ? ("0" + strMonth) : strMonth) +
	                   (strDate.length  < 2 ? ("0" + strDate) : strDate);
	    }
	    return addDate;
	}

	/**
	 * Notes     : 입력 받은 두 날짜의 차이를 리턴한다.
	 * Parameter : 시작일자, 종료일자
	 * Return    : 두 날짜의 차이 일수
	 * Use       : getDiffDay('20150801', '20150805');
	 */
	function getDiffDay(startYmd, endYmd){
		var sTimeArr = startYmd;
		var eTimeArr = endYmd;
		if(sTimeArr.length != 8 || eTimeArr.length != 8) { return 0; }

		var strt_day  = new Date(sTimeArr.substring(0,4),Number(sTimeArr.substring(4,6))-1,sTimeArr.substring(6,8));
		var end_day = new Date(eTimeArr.substring(0,4),Number(eTimeArr.substring(4,6))-1,eTimeArr.substring(6,8));
		var day_calc = Math.ceil((end_day.getTime() - strt_day.getTime()) / (24*60*60*1000));
		return day_calc;
	}

	/**
	 * set day
	 */
	function setDay(day){
//		var week = new Array("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일");
		var week = new Array(getMessage("COMM.0003"), getMessage("COMM.0004"), getMessage("COMM.0005"), getMessage("COMM.0006"), getMessage("COMM.0007"), getMessage("COMM.0008"), getMessage("COMM.0009"));
		var y = parseInt(day.substring(0,4),10);
		var m = parseInt(day.substring(4,6),10)-1;
		var d = parseInt(day.substring(6,8),10);
		var today1 = new Date(y,m,d).getDay();
		var weekLabel = week[today1];
		return weekLabel;
	}

	/**
	 * date format
	 */
	function setDateFormat(sDate, delim){
		var lDate = selectNum(sDate);
		var yy = lDate.substring(0,4);
		var mm = parseInt(lDate.substring(4,6),10);
		var dd = parseInt(lDate.substring(6,8),10);

		mm = (mm<10)?'0'+mm:mm;
		dd = (dd<10)?'0'+dd:dd;
		var tp = typeof(delim) == "undefined" ? "/" : delim;
		if(lDate.length == 6){
			return ""+yy+tp+mm;
		}else if(lDate.length == 8){
			if(tp == "YMD"){
//				return ""+yy+"년 "+mm+"월 "+dd+"일";
				return ""+yy+getMessage("COMM.0010")+" "+mm+getMessage("COMM.0011")+" "+dd+getMessage("COMM.0012");
			}else{
				return ""+yy+tp+mm+tp+dd;
			}
		}
	}

	/**
	 * time format
	 */
	function setTimeFormat(str){
		var t = "";
		if(str.length == 4){
			t += str.substring(0,2);
			t += ":";
			t += str.substring(2,4);
			return t;
		}else{
			return str;
		}
	}

	function padZeros(n, digits){
		var zero = "";
		n = n.toString();

		if(n.length < digits){
			for(var i = 0; i < digits - n.lenght; i++){
				zero += '0';
			}
		}
		return zero+n;
	}

	function numPad(str){
		if(str < 10) return '0'+str;
		else return str;
	};

	function setCookie(name, value, days){
		var todayDate = new Date();
		var expires = "";
		if(days){
			todayDate.setDate(todayDate.getDate()+days);
			expires = ";expires=" + todayDate.toGMTString();
		}
		document.cookie = name + "=" + value + expires + ";path=/";
	}

	function getCookie(name){
		var i,x,y,ARRcookies = document.cookie.split(";");
		for(i = 0; i < ARRcookies.length; i++){
			x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
			y = ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
			x = x.replace(/^\s+|\s+$/g, "");
			if(x == name){
				return unescape(y);
			}
		}
	}

	//프로그래스 바 오픈
	function block_open(){
		var gbWidth = "500";
		var gbHeight = "100";
		var popTop = ($(window).height() - Number(gbHeight)) /2 + 'px';
		var popLeft = ($(window).width() - Number(gbWidth)) /2 + 'px';
		 $.blockUI({
		     message: "<img src='../../images/common/progress_bar2.gif' style='vertical-align: middle;'/> 잠시만 기다려 주세요.",
		   	 css : {
				top: popTop,
				left: popLeft,
				width: gbWidth,
				height: 'auto',
		   		 fontSize : '16px',
		   		 color : '#000',
		   		 fontWeight : 'bold'
		   	 }
		 });
	}

	function block_close(){
		$.unblockUI();
	}

	//알림메시지
	function alertUI(msg, mode, fn){
		var gbWidth = "300";
		var gbHeight = "150";
		var popTop = ($(window).height() - Number(gbHeight)) /2 + 'px';
		var popLeft = ($(window).width() - Number(gbWidth)) /2 + 'px';
		var css = {
			top: popTop,
			left: popLeft,
			width: gbWidth,
			height: gbHeight,
			cursor: 'default',
			border: 'none',
			color: '#fff',
			backgroundColor:'#fff'
		};
		$("#alert_msg").html(msg);
		$.blockUI({
		    message: $("#alert_pop"),
		    css: css
		});

		$("#msgInput").val(msg);
		$("#pop_cls").click(function(){
			if(typeof(fn) == "undefined"){
				$.unblockUI();
			}else{
				$.unblockUI({
					onUnblock: function(){
						eval(fn);
					}
				});
			}
		});
	}

	function confirmUI(txt){
		var gbWidth = "300";
		var gbHeight = "150";
		var popTop = ($(window).height() - Number(gbHeight)) /2 + 'px';
		var popLeft = ($(window).width() - Number(gbWidth)) /2 + 'px';
		var css = {
			top: popTop,
			left: popLeft,
			width: gbWidth,
			height: gbHeight,
			cursor: 'default',
			border: 'none',
			color: '#fff',
			backgroundColor:'#fff'
		};
		$("#confirm_msg").html(txt);
		$.blockUI({
			message: $("#confirm_pop"),
			css: css
		});

		$("#pop_no").click(function(){
			$.unblockUI();
		});
	}

	//알림메시지
	function toolTipUI(src, mode, fn){
//		var img = document.getElementById("tmpImg");
//		var gbWidth = img.naturalWidth;
//		var gbHeight = img.naturalHeight;
		var gbWidth = "914";
		var gbHeight = "500";
		var popTop = ($(window).height() - Number(gbHeight)) /2 + 'px';
		var popLeft = ($(window).width() - Number(gbWidth)) /2 + 'px';
		var css = {
			top: popTop,
			left: popLeft,
			width: gbWidth,
			height: gbHeight,
			cursor: 'default',
			border: 'none',
			color: '#fff',
			backgroundColor:'#fff'
		};

		$("#t_tip").html("<iframe src='../../hncis/popup/"+src+"' style='width:"+gbWidth+"px; height:"+gbHeight+"px' frameborder='0'>");

		$.blockUI({
		    message: $("#tooltip_pop"),
		    css: css
		});

		$("#pop_tip_cls").click(function(){
			if(typeof(fn) == "undefined"){
				$.unblockUI();
			}else{
				$.unblockUI({
					onUnblock: function(){
						eval(fn);
					}
				});
			}
		});
	}

	function tooTipUIClose(){
		$("#pop_tip_cls").click(function(){
			$.unblockUI({
				onUnblock: function(){
					eval(fn);
				}
			});
		});
	}

	function fileExtCheck(){
		var ext = $("#fileTemp").val().slice($("#fileTemp").val().indexOf(".") + 1).toLowerCase();
		if($("#fileTemp").val() == ""){
			return true;
		}else{
			if(!(ext == "hwp" || ext == "doc" || ext == "docx" || ext == "xls" || ext == "xlsx" ||
					 ext == "pdf" || ext == "ppt" || ext == "pptx" || ext == "gif" ||
					 ext == "png" || ext == "bmp" || ext == "jpg" || ext == "jpeg" || ext == "txt")){
				return false;
			}else{
				return true;
			}
		}
	}

	function fileExtCheckImg(){
		var ext = $("#fileTemp").val().slice($("#fileTemp").val().indexOf(".") + 1).toLowerCase();
		if($("#fileTemp").val() == ""){
			return true;
		}else{
			if(!(ext == "gif" || ext == "png" || ext == "bmp" || ext == "jpg" || ext == "jpeg")){
				return false;
			}else{
				return true;
			}
		}
	}

	/**************************
	toggle
***************************/
$(document).ready(function(){
//    $(".para02 .nec").hide();

//    applyReqTotCnt = $(".req-data").length;

    $(document).on("keyup" , ".req-data", function(){
    	fnChkApplyReqsObjects();
    });

    $(document).on("blur" , ".req-data", function(){
    	fnChkApplyReqsObjects();
    });

    $(document).on("change", ".req-data", function(){
    	fnChkApplyReqsObjects();
    });

    $(document).on("keyup" , ".req-data2", function(){
    	fnChkApplyReqsObjects2();
    });

    $(document).on("blur" , ".req-data2", function(){
    	fnChkApplyReqsObjects2();
    });

    $(document).on("change", ".req-data2", function(){
    	fnChkApplyReqsObjects2();
    });
});

function fnChkApplyReqsObjects(){
	var rSpanCnt = 0;
	$(".req-data").each(function(idx, opt){
			if(this.value != ""){
				fnChkApplyReqsCnt("IN", opt);
			}else{
				fnChkApplyReqsCnt("OUT", opt);
			}
    });
}

function fnChkApplyReqsCnt(gubun, id){
	if(gubun == "IN"){
		$(id).closest("td").prev().removeClass("req-color").addClass("req-color-after");
	}else{
		$(id).closest("td").prev().removeClass("req-color-after").addClass("req-color");
	}
}

function fnChkApplyReqsObjects2(){
	var rSpanCnt = 0;
	$(".req-data2").each(function(idx, opt){
		if(this.value != ""){
			fnChkApplyReqsCnt2("IN", opt);
		}else{
			fnChkApplyReqsCnt2("OUT", opt);
		}
	});
}

function fnChkApplyReqsCnt2(gubun, id){
	var index = $(id).closest("td").index();
 	if(gubun == "IN"){
		$(id).closest("tr").prev().find("th:eq("+index+")").removeClass("req-color").addClass("req-color-after");
	}else{
		$(id).closest("tr").prev().find("th:eq("+index+")").removeClass("req-color-after").addClass("req-color");
	}
}



function createBtn(type){
	var btn = "";
	if(type == "H"){
		$(".btns").append(
			"<li id='q_tip' class='fr'><p id='tipBtn' name='q_tip' onclick='javascript:pageStepHelpPopup();' style='cursor:pointer;'></p></li>"
		);
	}else if(type == "BK"){
		$(".btns:eq(0)").append(
			"<li id='q_tip' class='fr'><p id='tipBtn' name='q_tip' onclick='javascript:pageStepHelpPopup();' style='cursor:pointer;'></p></li>"
		);
	}

	return btn;
}

function setDatepicker(a1, a2){
	$("#"+a1).datepicker({
		dateFormat: "yy-mm-dd"
		,onSelect: function (dateText, inst) {
			if($("#"+a2).val() < $("#"+a1).val()){
				$("#"+a2).val($("#"+a1).val());
			}
		}
	});
	$("#"+a2).datepicker({
		dateFormat: "yy-mm-dd"
		,beforeShowDay: function(date){
			var ymd = getAddDayDate($("#"+a1).val().replace(/-/gi,""), -1)
			var d = new Date(ymd.substr(0,4) + "-" + ymd.substr(4,2) + "-" + ymd.substr(6,2));
		    if (date < d)
		        return [false];
		    return [true];
		}
	});
}

/* a1 : content id, a2: parent iframe id*/
function resizeIframePop(a1, a2){
	var doc = document.getElementById(a1);
	var hegiht = doc.offsetHeight;
	if(hegiht < 450) hegiht = 470; 
	parent.document.getElementById(a2).height = hegiht + "px";
}

function getMessage(key){
	return $.i18n.prop(key);
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