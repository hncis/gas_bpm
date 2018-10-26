/**
 * 테이블의 td값 selector
 */
function getTdNumValue(id, rowNum, cellNum){
    return $("#"+id).find("tr").eq(rowNum).find("td").eq(cellNum).text();
}
function getTdIdValue(id, rowNum, cellId){
	return $("#"+id).find("tr").eq(rowNum).find("td#"+cellId+rowNum).text();
}

/**
 * 테이블의 td값 셋팅
 */
function setTdValue(id, rowNum, cellNum, text){
    return $("#"+id).find("tr").eq(rowNum).find("td").eq(cellNum).text(text);
}

/**
 * 레이어 팝업 
 */
function layer_open(el, gbWidth, gbHeight){
	var temp = $("#" + el);
	var bg = temp.prev().hasClass("bg");
	
	if(bg){
		$(".layer").fadeIn();
	}else{
		temp.fadeIn();
	}
	
	temp.css("width", gbWidth);
	temp.css("height", gbHeight);
	temp.css("top", "30%");
	temp.css("left", "35%");
		
//	if(temp.outerHeight() < $(document).height()) temp.css("margin-top", "-"+temp.outerHieght()/2+"px");
//	else temp.css("top", "0px");
//	if(temp.outerWidth() < $(document).width()) temp.css("margin-left", "-"+temp.outerWidth()/2+"px");
//	else temp.css("left", "0px");
		
	temp.find("a.cbtn").click(function(e){
		if(bg){
			$(".layer").fadeOut();
		}else{
			temp.fadeOut();
			temp.remove();
		}
		e.preventDefault();
	});
	
	$(".layer .bg").click(function(e){
		$(".layer").fadeOut();
		e.preventDefault();
	});
}

//html 그리드 초기화
function init_grid(bln_cnt, cal_cnt, def_cnt, defult_yn){
    var rtnHtml = "";
    if(def_cnt == "" || def_cnt <= 0 || typeof(def_cnt) == "undefined"){
        def_cnt = 20;
    }
    if(cal_cnt >= def_cnt){
        return rtnHtml;
    }
    if(typeof(defult_yn) == "undefined"){
        defult_yn = false;
    }
    if(defult_yn && cal_cnt == 0){
        cal_cnt++;
        rtnHtml += "<tr> \n";
        rtnHtml += "<td colspan='"+bln_cnt+"'>조회된 내용이 없습니다.</td> \n";
        rtnHtml += "</tr> \n";
    }
    for(var i=cal_cnt; i<def_cnt; i++){
        cal_cnt++;
        rtnHtml += "<tr>";
        for(var b=0; b<bln_cnt; b++){
            rtnHtml += "<td></td>";
        }
        rtnHtml += "</tr>";
    }

    return rtnHtml;
}

// ajax 호출
var Con = {
    ajax : function(params){
        if(typeof(params.url) == "undefined" || params.url == ""){
            alertUI("url(은)는 필수 항목입니다.");
            return;
        }
        $.ajax({
            url        : params.url
          , type       : "POST"
          , cache      : false
          , asyn       : typeof(params.asyn) == "undefined" ? false : true 
          , data       : typeof(params.data) == "undefined" ? "" : params.data
          , dataType   : typeof(params.dType) == "undefined" ? "json" : params.dType
//	          , beforeSend : function(xhr){}
//	          , complete   : function(xhr, textStatus){}
          , success    : function(data){
              (typeof(params.callback) == "undefined" || params.callback == "") ?
                  Con.sucCallBack(data) : eval(params.callback);
          }
          , error      : function(xhr, textStatus, stt){
              Con.errCallBack(xhr, textStatus, stt);
          }
      });
    },

    sucCallBack : function(data){
        //alertUI("성공 : " + data);
    },

    errCallBack : function(xhr, textStatus, stt){
    	block_close();
    	errCallBack();
        alertUI("SYSTEM 오류 입니다. IT 담당자에게 문의 하세요!");
    }
};

/**
 * 공통 AJAX 호출 함수 - Form Submit Type
 * @param url
 * @param search parameter
 * @param success 함수명
 * @returns null
 */
function doCommonAjaxToFile(url, successFc, formId){
    var form = "";
    if(typeof(formId) == "undefined"){
        form = "tx_editor_form";
    }else{
        form = formId;
    }
    var options = {
        url: url
        ,dataType: "json"
        ,type: "post"
        ,iframe:true
        ,success:function(xhr){
            if(xhr != null){
                if(xhr.errorCode != null){
                    //alertUI(xhr.message);
                }else{
                    eval(successFc);
                }
            }
        }
        ,error:function(result){
            alertUI("SYSTEM 오류 입니다. IT 담당자에게 문의 하세요!");
        }
    };
    $("#"+form).ajaxSubmit(options);
}

//페이지이동
function getMovePage(json){
    var form = $("<form/>");
    form.attr("method" , "POST");
    form.attr("id"     , "hideForm");
    form.attr("action" , json.url);
    //form.attr("target" , "_self");
    var inp1 = $("<input/>").attr("type", "hidden").attr("name", "param1").val(json.mv);
    var inp2 = $("<input/>").attr("type", "hidden").attr("name", "param2").val(json.param2);
    var token = $("<input/>").attr("type", "hidden").attr("name", "hid_csrfToken").val($("#csrfToken").val());
    form.append(inp1, inp2, token);
    $("body").append(form);
    form.submit();
    form.remove();
}

function fromToTime(){
	var rtn = new Array();
	var now = new Date();
	var hr = now.getHours();
	var mi = now.getMinutes();
	var fhr = "", thr = "";
	if(mi <= 30){
		//30분보다 작으면 From시간은 정각 To시간은 +1시간 임
		fhr = numPad(hr)+"30";
		thr = numPad(hr+1)+"29";
	}else{
		//30분보다 크면 From시간은 +1 To시간은 +2시간 임
		fhr = numPad(hr+1)+"00";
		thr = numPad(hr+1)+"59";
	}
	
	//현재시간이 23시일경우 23:00 ~ 23:59분을 셋팅함.
	if(hr == "23"){
		if(mi <= 30){
			fhr = numPad(hr)+"00";
		}else{
			fhr = numPad(hr)+"30";
		}
		thr = numPad(hr)+"59";
	}
	rtn[0] = fhr;
	rtn[1] = thr;
	return rtn;
}

function changeToTime(toTime){
	var tmpToTime = selectNum(toTime);
	if(tmpToTime.length != 4){
		return toTime;
	}
   var tmpToHour = tmpToTime.substring(0,2); 
   var tmpToMin = tmpToTime.substring(2,4); 
   if(tmpToMin == "29"){
   	revToTime = tmpToHour+""+numPad(Number(tmpToMin)+1);
   }else if(tmpToMin == "59"){
   	revToTime = numPad(Number(tmpToHour)+1)+"00";
   }else{
   	revToTime = tmpToHour+""+tmpToMin;
   }
   return revToTime;
}

function checkPeriodIsOneMonth(date1, date2) {
	if (date1 != date2) {
		var f_y = date1.substring(0,4);
		var f_m = date1.substring(4,6);
		var f_d = date1.substring(6,8);
		var e_y = date2.substring(0,4);
		var e_m = date2.substring(4,6);
		var e_d = date2.substring(6,8);
		
		if (e_y == f_y) {
			if (e_m == f_m) {
				if (! (e_d - f_d >= 0)){
					return false;
				}	
			}
			else if (e_m > f_m) {
				if (e_m - f_m == 1) {
					if(e_d - f_d >= 0) {
						return false;
					} 
				}
				else { 
					return false;
				}
			}
			else {
				return false;
			} 
			
		}
		else if (e_y > f_y) {
			if( e_y - f_y == 1) {
				if (e_m == 1 && f_m == 12 ) {
					if(e_d - f_d >= 0) {
						return false;
					} 
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	else {
		return true;
	}
}

function checkOneMonth(date1, date2) {
	var sys_year = date1.substring(0,4);
	var sys_month = date1.substring(4,6);
	var sys_day = date1.substring(6,8);
	var f_y = date2.substring(0,4);
	var f_m = date2.substring(4,6);
	var f_d = date2.substring(6,8);
	
	if (sys_year == f_y) {
		if (sys_month == f_m) {
			return true;
		}
		else if ( f_m > sys_month ) {
			if ( f_m - sys_month == 01) {
				if(sys_day - f_d < 0) {
					return false;
				} else {
					return true;
				}
			}
			else { 
				return false;
			}
		}
		else {
			return false;
		} 
		
	}
	else if (sys_year > f_y) {
		if( sys_year - f_y == 1) {
			if (sys_month == 1 && f_m == 12 ) {
				if(sys_day - f_d >= 0) {
					return false;
				} 
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	 else if (f_y > sys_year) {
		if (sys_month == 12 && f_m == 1 ) {
			if(f_d - sys_day >= 0) {
				return false;
			} else {
				return true;
			}
		}
	}
	else {
		return false;
	} 		

}