var win; 
var mode;

$(function(){
	//예약확인
	$("#btn_confirm").live("click", function(){
		//유효성체크
		if(!validation()) return;

		var gbWidth = "784";
		var gbHeight = "500";
		var popTop = (($(window).height() - Number(gbHeight)) /2)-100 + 'px';
		var popLeft = (($(window).width() - Number(gbWidth)) /2) + 'px';
		var css = {
			top: popTop, left: popLeft, width: gbWidth, height: gbHeight, textAlign: "left",
			cursor: 'hand', border: 'none', backgroundColor:'#fff'
		};
		confirm_conference();
		$.blockUI({
		    message: $("#resv_confirm_pop"),
		    css: css
		});
	});

	//예약저장
	$("#btn_save").live("click", function(){
		clearInterval(intervalTime);
		updateConferenceRoom("I");
	});

	//예약 작성 화면으로 되돌림.
	$("#btn_prev").live("click", function(){
		var gbWidth = "784";
		var gbHeight = "500";
		var popTop = (($(window).height() - Number(gbHeight)) /2)-100 + 'px';
		var popLeft = (($(window).width() - Number(gbWidth)) /2) + 'px';
		var css = {
			top: popTop, left: popLeft, width: gbWidth, height: gbHeight, textAlign: "left",
			cursor: 'hand', border: 'none', backgroundColor:'#fff'
		};
		$.blockUI({
		    message: $("#resv_pop"),
		    css: css
		});
		setTimeout('$(".confirm_pop").remove();', 500);
		clearInterval(intervalTime);
		timer_script("600");
	});
	
	//예약입력 삭제
	$("#btn_del").live("click", function(){
		if(confirm("취소시 현재 예약건은 삭제 됩니다.\n예약을 취소 하시겠습니까?")){
			clearInterval(intervalTime);
			if($("#menuId").val() == "XSM02"){
				//색칠했던 배경을 원래대로 되돌린다.
				cellBgColor("A");
			}
			$.unblockUI();
			doResvDelete();
			setClear();
			setTimeout('$(".resv_pop").remove();', 500);
			try{
				ifm.cal_close();
			}catch(e){}
		}
	});

	//예약변경 삭제
	$("#btn_delete").live("click", function(){
		if(confirm("취소시 현재 예약건은 삭제 됩니다..\n예약을 취소 하시겠습니까?")){
			$.unblockUI();
			doResvDelete();
			setClear();
			setTimeout('$(".resv_pop").remove();', 500);
		}
	});
	
	//상세조회 일경우
	$("#btn_modify").live("click", function(){
		updateConferenceRoom("S");
	});
	
	//닫기
	$("#btn_close").live("click", function(){
		$.unblockUI();
		if($("#menuId").val() == "XSM02"){
			//색칠했던 배경을 원래대로 되돌린다.
			cellBgColor("B");
		}
		setClear();
		setTimeout('$(".resv_pop,.confirm_pop,.blockUI").remove();', 500);
		try{
			ifm.cal_close();
		}catch(e){}
	});

	$(".numeric").numeric();
	$(".numeric").css("ime-mode","disabled");

	//위치보기 컨틀롤
	$(".viewmap").live("click", function() {
		$(".inlyrx").show();
		return false;
	});
	$(".inlyrx .lyrClose").live("click", function() {
		$(".inlyrx").hide();
		return false;
	});
	
	//반복요일 전체
	$(".all_day").live("click", function(){
		var chk = $(this).is(":checked");
		if(chk){
			$("input:checkbox[name='cnf_spe_day']").attr("checked", true);
		}else{
			$("input:checkbox[name='cnf_spe_day']").attr("checked", false);
		}
	});
	
	//회의주관자
	$("#btn_insa1").live("click", function(){
		var cw = 440;   // 오픈할 윈도우 창 width
		var ch = 510;   // 오픈할 윈도우 창 height
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var winOpt = "top="+ht+",left="+wd +',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width='+cw+',height='+ch;
		win = window.open("", "pop_insa", winOpt);

		var form = $("<form/>").attr("id", "frmInsa");
		form.attr("method", "POST");
		form.attr("target", "pop_insa");
		form.attr("action", ctxPath+"/hncis/popup/userPopup.gas");
		var str1 = $("<input type='hidden' id='popGubun' name='popGubun'/>");
		var str2 = $("<input type='hidden' id='csrfToken' name='csrfToken'/>");
		str1.val("XSM");
		str2.val($("#csrfToken").val());
		form.append(str1, str2);
		$("body").append(form);
		form.submit();
		form.remove();
	});

	//참석자
	$(".btn_insa_multi").live("click", function(){
		var cw = 900;   // 오픈할 윈도우 창 width
		var ch = 510;   // 오픈할 윈도우 창 height
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var winOpt = "top="+ht+",left="+wd +',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width='+cw+',height='+ch;
		win = window.open("", "insa_multi", winOpt);
		
		var inpId = $(this).attr("id");
		var form = $("<form/>").attr("id", "frmInsa");
		form.attr("method", "POST");
		form.attr("target", "insa_multi");
		form.attr("action", ctxPath+"/hncis/popup/userMultiPopup.gas");
		var str1 = $("<input type='hidden' id='popGubun' name='popGubun'/>");
		var str2 = $("<input type='hidden' id='popEeno' name='popEeno'/>");
		var str3 = $("<input type='hidden' id='csrfToken' name='csrfToken'/>");
		str3.val($("#csrfToken").val());
		
		if(inpId == "btn_insa2"){
			//참석자
			str1.val("A");
			str2.val($("#cnf_attde_eeno").val());
		}else if(inpId == "btn_insa3"){
			//공람자
			str1.val("B");
			str2.val($("#rpt_oth_eeno").val());
		}
		form.append(str1, str2, str3);
		$("body").append(form);
		form.submit();
		form.remove();
	});
	
	//상용구 선택
	$("#tmp_title").live("click", function(){
		var cw = 440;   // 오픈할 윈도우 창 width
		var ch = 440;   // 오픈할 윈도우 창 height
		var wd = (eval(screen.width) - cw) /2;
		var ht = (eval(screen.height) - ch) /2;
		var winOpt = "top="+ht+",left="+wd +',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width='+cw+',height='+ch;
		win = window.open("", "pop_ttl", winOpt);

		var form = $("<form/>").attr("id", "frmTtl");
		form.attr("target", "pop_ttl");
		form.attr("action", ctxPath+"/hncis/smartRooms/xsm_title.gas");
		var str1 = $("<input type='hidden' id='csrfToken' name='csrfToken'/>");
		str1.val($("#csrfToken").val());
		form.append(str1);
		$("body").append(form);
		form.submit();
		form.remove();
	});
	
	//시작시간 변경
	$("#cnf_from_time").live("change", function(){
		var tmpYmd = selectNum($("#p_from_ymd").val());
		var tmpTime = selectNum($("#p_from_time").val());
		var fromYmd = selectNum($("#cnf_from_ymd").val());
		var fromTime = selectNum($("#cnf_from_time").val());
	    var scd = new Date();
	    var diffTime = ""+numPad(scd.getHours())+numPad(scd.getMinutes());
	    if(Number(getCurrentToDate()+diffTime) > Number(fromYmd+fromTime)){
	    	alert("과거 시간은 선택할 수 없습니다.");
	    	if(mode != ""){
		    	$("#cnf_from_ymd").val(setDateFormat(tmpYmd));
		    	$("#cnf_from_time").val(tmpTime);
	    	}
	    	return;
	    }else{
	    	if(sess_mstu == "M" || $("#etc_user").val() == "Y"){
	    		return;
	    	}else{
				var startDate = selectNum($("#cnf_from_ymd").val())+selectNum($("#cnf_from_time").val());
				var edDate = selectNum($("#cnf_to_ymd").val())+selectNum($("#cnf_to_time").val());
				var rsMin = timeRange(startDate, edDate, "M");
				var koTimeNm = timeRange(startDate, edDate, "HM");
				var resMaxMin = $("#res_max_min").val();
				var resMaxMinName = $("#res_max_min_name").val();
				if(resMaxMin != "000"){
					if(rsMin < 0){
						alert("시작시간이 종료시간보다 큽니다.");
				    	$("#cnf_from_ymd").val(setDateFormat(tmpYmd));
				    	$("#cnf_from_time").val(tmpTime);
						return;
					}else if(rsMin > resMaxMin){
						alert("입력된 시간이("+koTimeNm+") \n설정된 예약 가능한 시간("+resMaxMinName+")보다 큽니다.");
				    	$("#cnf_from_ymd").val(setDateFormat(tmpYmd));
				    	$("#cnf_from_time").val(tmpTime);
						return;
					}
				}
	    	}
	    }
	});
	
	//종료시간 변경
	$("#cnf_to_time").live("change", function(){
		var tmpYmd = selectNum($("#p_to_ymd").val());
		var tmpTime = selectNum($("#p_to_time").val());
		var toYmd = selectNum($("#cnf_to_ymd").val());
		var toTime = selectNum($("#cnf_to_time").val());
		var scd = new Date();
		var diffTime = ""+numPad(scd.getHours())+numPad(scd.getMinutes());
		if(Number(getCurrentToDate()+diffTime) > Number(toYmd+toTime)){
			alert("과거 시간은 선택할 수 없습니다.");
			if(mode != ""){
				$("#cnf_to_ymd").val(setDateFormat(tmpYmd));
				$("#cnf_to_time").val(tmpTime);
			}
			return;
		}else{
	    	if(sess_mstu == "M" || $("#etc_user").val() == "Y"){
	    		return;
	    	}else{
				var startDate = selectNum($("#cnf_from_ymd").val())+selectNum($("#cnf_from_time").val());
				var edDate = selectNum($("#cnf_to_ymd").val())+selectNum($("#cnf_to_time").val());
				var rsMin = timeRange(startDate, edDate, "M");
				var koTimeNm = timeRange(startDate, edDate, "HM");
				var resMaxMin = $("#res_max_min").val();
				var resMaxMinName = $("#res_max_min_name").val();
				console.log(rsMin + " , " + resMaxMin);
				if(resMaxMin != "000"){
					if(rsMin > resMaxMin){
						alert("입력된 시간이("+koTimeNm+") \n설정된 예약 가능한 시간("+resMaxMinName+")보다 큽니다.");
						$("#cnf_to_ymd").val(setDateFormat(tmpYmd));
						$("#cnf_to_time").val(tmpTime);
						return;
					}
				}
	    	}
		}
	});
});

/**
 * 입력된 시작일시, 종료일시를 계산해서 분으로 환산 
 * 입력 방법
 * D : 일수, H : 시간, M : 분, S : 초
 * timeRange("200001010100", "200012312359", "M");
 */
function timeRange(stDate, edDate, rsType){
	//시작일자
	var y1 = parseInt(stDate.substring(0,4), 10);
	var m1 = parseInt(stDate.substring(4,6), 10)-1;
	var d1 = parseInt(stDate.substring(6,8), 10);
	var st1 = parseInt(stDate.substring(8,10), 10);
	var st2 = parseInt(stDate.substring(10,12), 10);
	//종료일자
	var y2 = parseInt(edDate.substring(0,4), 10);
	var m2 = parseInt(edDate.substring(4,6), 10)-1;
	var d2 = parseInt(edDate.substring(6,8), 10);
	var et1 = parseInt(edDate.substring(8,10), 10);
	var et2 = parseInt(edDate.substring(10,12), 10)+1;
	var startDate = new Date(y1, m1, d1, st1, st2, "00");
	var endDate = new Date(y2, m2, d2, et1, et2, "59");
	var dateGap = endDate.getTime() - startDate.getTime();
	var timeGap = new Date(0,0,0,0,0,0,endDate-startDate);
	
	if(rsType == "D"){
		return Math.floor(dateGap/(1000*60*60*24));
	}else if(rsType == "H"){
		return Math.floor(dateGap/(1000*60*60));
	}else if(rsType == "M"){
		return Math.floor(dateGap/(1000*60));
	}else if(rsType == "S"){
		return Math.floor(dateGap/1000);
	}else if(rsType == "UTC"){
		return timeGap;
	}else if(rsType == "HM"){
		if(timeGap.getMinutes() == 0){
			return timeGap.getHours() + "시간";
		}else{
			return timeGap.getHours() + "시간" + timeGap.getMinutes() + "분";
		}
	}
}

//예약입력 생성
function create_conference(result){
	mode = "I";
	var rs = result.info1;
	var form = $("<form id='frmMain' name='frmMain' method='POST'/>");
	var html = "";
	html += "<div class='popLyr'>";
	html += "<div class='lyrhead'>";
	html += "<div class='i'><div class='r'>";
	html += "<div style='float:left;'><h3>예약 입력</h3></div>";
	html += "<div style='float:right;padding-right:20px;'><h4 id='t_left_time'>00:00:00</h4></div>";
	html += "<div style='float:right;'><h4> ※10분 내 예약 미입력 시 자동 취소됩니다.</h4></div>";
	html += "</div></div>";
	html += "</div>";
	html += "<div class='lyrinner'>";
	html += "<table class='tblT1'>";
	html += "<colgroup>";
	html += "<col width='105px'><col width='280px'><col width='90px'><col width='*'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의 제목<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	html += "<span class='mr5'><input type='text' id='cnf_title' name='cnf_title' style='width:90%' class=''/></span>";
	html += "<span class='mr5'><img id='tmp_title' src='../../images/bttn/ico_search.png' align='middle' alt='상용구'></span>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th><span id='btn_insa1' class='btn_t1'><span>회의주관자</span></span><span class='fontRed'>*</span></th>";
	html += "<td>";
	html += "<span class='mr3'><input type='text' id='cnf_eeno' name='cnf_eeno' style='width:60px' class='disabled' readonly/></span>";
	html += "<span class='mr3'><input type='text' id='cnf_eenm' name='cnf_eenm' style='width:60px' class='disabled' readonly/></span>";
	html += "<span class='mr3'>";
	html += "<input type='hidden' id='cnf_dept_code' name='cnf_dept_code' style='width:120px' class='disabled' readonly/>";
	html += "<input type='text' id='cnf_dept_name' name='cnf_dept_name' style='width:120px' class='disabled' readonly/>";
	html += "</span>";
	html += "</td>";
	html += "<th>회의알림</th>";
	html += "<td>";
	html += "<div>";
	html += "<span style='display:inline-block;width:145px;'>";
	html += "<span class='mr3'><label for='chk_email'><input type='checkbox' id='chk_email' name='chk_alr_email' value='Y'> 이메일</label></span>";
	html += "</span>";
	html += "</div>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>예약일시<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	var resMaxMin = rs.res_max_min;
	var resMaxMinName = rs.res_max_min_nm;
	html += "<input type='hidden' id='res_max_min' name='res_max_min' value='"+ resMaxMin +"'/>";
	html += "<input type='hidden' id='res_max_min_name' name='res_max_min_name' value='"+ resMaxMinName +"'/>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<span class='mr5'><input type='text' id='cnf_from_ymd' name='cnf_from_ymd' style='width:80px;margin-right:5px;' /></span>";
		html += "<span class='mr5'><select id='cnf_from_time' name='cnf_from_time' style='width:80px'></select></span>";
		html += "<span class='mr5'>부터</span>";
		html += "<span class='mr5'><input type='text' id='cnf_to_ymd' name='cnf_to_ymd' style='width:80px;margin-right:5px;' /></span>";
		html += "<span class='mr5'><select id='cnf_to_time' name='cnf_to_time' style='width:80px'></select></span>";
		html += "<span>까지</span>";
	}else{
		html += "<span class='mr5'><input type='text' id='cnf_from_ymd' name='cnf_from_ymd' style='width:80px' class='disabled' readonly/></span>";
		html += "<span class='mr5'><select id='cnf_from_time' name='cnf_from_time' style='width:80px;'></select></span>";
		html += "<span class='mr5'>부터</span>";
		html += "<span class='mr5'><input type='text' id='cnf_to_ymd' name='cnf_to_ymd' class='disabled' style='width:80px' class='disabled' readonly/></span>";
		html += "<span class='mr5'><select id='cnf_to_time' name='cnf_to_time' style='width:80px;'></select></span>";
		html += "<span class='mr5'>까지</span>";
		if(resMaxMin!= "0"){
			html += "<span class='mr5 fontBlue'><b>예약가능시간 : " + resMaxMinName + "</b></span>";
		}
	}
	html += "</td>";
	html += "</tr>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<tr>";
		html += "<th>반복요일설정</th>";
		html += "<td colspan='3'>";
		html += "<span class='mr5'><label for='cnf_spe_mon'><input type='checkbox' id='cnf_spe_mon' name='cnf_spe_day' value='2'> 월</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_tue'><input type='checkbox' id='cnf_spe_tue' name='cnf_spe_day' value='3'> 화</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_wed'><input type='checkbox' id='cnf_spe_wed' name='cnf_spe_day' value='4'> 수</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_thu'><input type='checkbox' id='cnf_spe_thu' name='cnf_spe_day' value='5'> 목</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_fri'><input type='checkbox' id='cnf_spe_fri' name='cnf_spe_day' value='6'> 금</label></span>";
		html += "<span class='mr5'><label for='all_day'><input type='checkbox' id='all_day' class='all_day'/> 전체</label></span>";
		html += "</td>";
		html += "</tr>";
	}
	html += "<th>";
	html += "<span id='btn_insa2' class='btn_t1 btn_insa_multi'><span>참석자</span></span><br>";
	html += "<span id='attde_cnt' name='attde_cnt' class='ml3'></span>";
	html += "</th>";
	html += "<td>";
	html += "<input type='hidden' id='cnf_attde_eeno' name='cnf_attde_eeno' readonly/>";
	html += "<textarea id='cnf_attde_name' name='cnf_attde_name' style='width:95%;height:50px;' readonly></textarea>";
	html += "</td>";
	html += "<th>";
	html += "<span>사외참석자</span><br>";
	html += "<span id='cnf_out_cnt' name='cnf_out_cnt' class='ml3'></span>";
	html += "</th>";
	html += "<td>";
	html += "<textarea id='cnf_out_attde' name='cnf_out_attde' style='width:95%;height:50px;'></textarea>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의내용<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	html += "<textarea id='cnf_agenda' name='cnf_agenda' style='width:98%;height:100px'></textarea>";
	html += "</td>";
	html += "</tr>";
	html += "</table>";

	//회의실상세 조회 시작
	html += "<div id='confInfo' class='mt3'>";
	html += "<table class='tblT1 tbl_fixed'>";
	html += "<colgroup>";
	html += "<col width='90px'>";
	html += "<col width='300px'>";
	html += "<col width='70px'>";
	html += "<col width='70px'>";
	html += "<col width='60px'>";
	html += "<col width='60px'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의실</th>";
	html += "<td colspan='5'>"+rs.regn_nm+"/"+rs.bd_nm+"/"+rs.corm_fl_cd.replace("-", "B")+"층"+"/"+rs.corm_nm+"</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>위치설명</th>";
	html += "<td><div class='txt_wd'>"+rs.corm_loc_sbc+"</div></td>";
	/*
	html += "<div class='rpos'><a href='#' class='viewmap btn_t1 mr5'><span>보기</span></a>";
	html += "<div class='inlyrx'>";
	html += "<div class='inbox'>";
	html += "<div class='mapDv'>";
	
	var fileId = rs.file_id_2;
	var fileFullNm = rs.file_img_2;
	var fileOrgNm = rs.org_file_2;
	if(fileOrgNm == ""){
		html += "<img src='../../images/common/no_img.gif' align='absmiddle' width='404px' height='256px' style='border:1px solid #e2e2e2'>";
	}else{
		if(checkExt(fileOrgNm)){
			html += "<a href='"+fileFullNm+"' target='_blank'><img src='"+fileFullNm+"' width='404px' height='256px' title='이미지를 클릭하시면 크게 볼수 있습니다.'></a>";
		}else{
			html += "<img src='../../images/common/no_img.gif' align='absmiddle' width='170px' height='150px' style='border:1px solid #e2e2e2'>";
			html += "<input type='hidden' id='file_img_2' name='file_img_2' value='"+fileOrgNm+"'/>";
			html += "<span id='file_nm' class='ml13'><a href='javascript:saveFile(\""+fileId+"\", \"1\")'>"+fileOrgNm+"</a></span>";
		}
	}
	html += "</div>";
	html += "<a href='#' class='lyrClose'><img src='../../images/common/btn_sclose.gif' alt=''></a>";
	html += "</div>";
	html += "</div>";
	 */
	html += "<th>수용인원</th>";
	html += "<td colspan='3'>"+rs.blns_cpsn+"명</td>";
	html += "</tr>";
	html += "<th>장비</th>";
	html += "<td colspan='5'>"+rs.item_sbc+"</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>비고</th>";
	html += "<td colspan='5'>"+rs.remark+"</td>";
	html += "</tr>";
	html += "</table>";
	
	//승인필요 회의실이지만 마스터,장기예약담당은 바로 예약가능하도록 한다.
	var confYn = rs.conf_yn;
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		confYn = "N"; 
	}
	
	html += "<input type='hidden' id='pgs_st_cd' name='pgs_st_cd' value='0'/>";
	html += "<input type='hidden' id='conf_yn' name='conf_yn' value='"+confYn+"'/>";
	html += "</div>";
	//회의실상세 조회 끝

	html += "<div class='btn_area align_c'>";
	html += "<a href='#' id='btn_confirm' class='white_btn22'><span>확인</span></a>";
	html += "<a href='#' id='btn_del' class='white_btn22'><span>취소</span></a>";
	html += "</div>";
	html += "</div>";
	html += "</div>";
	
	form.append(html);
	var resvPop = $("<div/>").attr("id", "resv_pop").attr("style","display:none");
	resvPop.addClass("resv_pop");
	resvPop.append(form);
	$("body").append(resvPop);

	$(function(){
		if($("#etc_user").val() == "N" && sess_mstu == "U"){
			$("#cnf_from_ymd").attr("disabled", true);
			$("#cnf_from_time").attr("disabled", false);
			$("#cnf_to_ymd").attr("disabled", true);
			$("#cnf_to_time").attr("disabled", false);
		}else{
			popCalendar();
		}
	});
	
	timer_script('600');
}

//본인의 예약상세화면 생성
function detail_conference(result){
	mode = "S";
	var rs1 = result.info1;
	var rs2 = result.info2;
	
	//팝업 시작
	var form = $("<form id='frmMain' name='frmMain' method='POST'/>");
	var html = "";
	html += "<div class='popLyr'>";
	html += "<div class='lyrhead'>";
	html += "<div class='i'><div class='r'>";
	html += "<h3 class='dtl_title'>예약 확인 및 수정</h3>";
	html += "</div></div>";
	html += "</div>";
	html += "<div class='lyrinner'>";
	html += "<table class='tblT1'>";
	html += "<colgroup>";
	html += "<col width='105px'><col width='280px'><col width='90px'><col width='*'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의 제목<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	html += "<span class='mr5'><input type='text' id='cnf_title' name='cnf_title' style='width:90%'/></span>";
	html += "<span class='mr5'><img id='tmp_title' src='../../images/bttn/ico_search.png' align='middle' alt='상용구'></span>";
	html += "</td>";
	html += "<tr>";
	html += "<th><span id='btn_insa1' class='btn_t1'><span>회의주관자</span></span><span class='fontRed'>*</span></th>";
	html += "<td>";
	html += "<span style='padding-right:3px'><input type='text' id='cnf_eeno' name='cnf_eeno' style='width:60px' class='disabled' readonly/></span>";
	html += "<span style='padding-right:3px'><input type='text' id='cnf_eenm' name='cnf_eenm' style='width:60px' class='disabled' readonly/></span>";
	html += "<span style='padding-right:3px'>";
	html += "<input type='hidden' id='cnf_dept_code' name='cnf_dept_code' style='width:120px' class='disabled' readonly/>";
	html += "<input type='text' id='cnf_dept_name' name='cnf_dept_name' style='width:120px' class='disabled' readonly/>";
	html += "</span>";
	html += "</td>";
	html += "<th>회의알림</th>";
	html += "<td>";
	html += "<div>";
	html += "<span style='display:inline-block;width:145px;'>";
	html += "<span class='mr3'>";
	html += "<label for='chk_email'><input type='checkbox' id='chk_email' name='chk_alr_email' value='Y'> 이메일</label>";
	html += "</span>";
	html += "</span>";
	html += "</div>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>예약일시<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	var resMaxMin = rs1.res_max_min;
	var resMaxMinName = rs1.res_max_min_nm;
	html += "<input type='hidden' id='res_max_min' name='res_max_min' value='"+ resMaxMin +"'/>";
	html += "<input type='hidden' id='res_max_min_name' name='res_max_min_name' value='"+ resMaxMinName +"'/>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<span class='mr5'><input type='text' id='cnf_from_ymd' name='cnf_from_ymd' style='width:80px;margin-right:5px;' /></span>";
		html += "<span class='mr5'><select id='cnf_from_time' name='cnf_from_time' style='width:80px'></select></span>";
		html += "<span class='mr5'>부터</span>";
		html += "<span class='mr5'><input type='text' id='cnf_to_ymd' name='cnf_to_ymd' style='width:80px;margin-right:5px;' /></span>";
		html += "<span class='mr5'><select id='cnf_to_time' name='cnf_to_time' style='width:80px'></select></span>";
		html += "<span>까지</span>";
	}else{
		html += "<span class='mr5'><input type='text' id='cnf_from_ymd' name='cnf_from_ymd' class='disabled' style='width:80px'/></span>";
		html += "<span class='mr5'><select id='cnf_from_time' name='cnf_from_time' style='width:80px'></select></span>";
		html += "<span class='mr5'>부터</span>";
		html += "<span class='mr5'><input type='text' id='cnf_to_ymd' name='cnf_to_ymd' class='disabled' style='width:80px'/></span>";
		html += "<span class='mr5'><select id='cnf_to_time' name='cnf_to_time' style='width:80px'></select></span>";
		html += "<span class='mr5'>까지</span>";
		if(resMaxMin != "000"){
			html += "<span class='mr5 fontBlue'><b>예약가능시간 : " + resMaxMinName + "</b></span>";
		}
	}
	html += "</td>";
	html += "</tr>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<tr>";
		html += "<th>반복요일설정</th>";
		html += "<td colspan='3'>";
		html += "<span class='mr5'><label for='cnf_spe_mon'><input type='checkbox' id='cnf_spe_mon' name='cnf_spe_day' value='2'> 월</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_tue'><input type='checkbox' id='cnf_spe_tue' name='cnf_spe_day' value='3'> 화</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_wed'><input type='checkbox' id='cnf_spe_wed' name='cnf_spe_day' value='4'> 수</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_thu'><input type='checkbox' id='cnf_spe_thu' name='cnf_spe_day' value='5'> 목</label></span>";
		html += "<span class='mr5'><label for='cnf_spe_fri'><input type='checkbox' id='cnf_spe_fri' name='cnf_spe_day' value='6'> 금</label></span>";
		html += "<span class='mr5'><label for='all_day'><input type='checkbox' id='all_day' class='all_day'/> 전체</label></span>";
		html += "</td>";
		html += "</tr>";
	}
	html += "<th>";
	html += "<span id='btn_insa2' class='btn_t1 btn_insa_multi'><span>참석자</span></span><br>";
	html += "<span id='attde_cnt' name='attde_cnt' class='ml3'></span>";
	html += "</th>";
	html += "<td>";
	html += "<input type='hidden' id='cnf_attde_eeno' name='cnf_attde_eeno' readonly/>";
	html += "<textarea id='cnf_attde_name' name='cnf_attde_name' style='width:95%;height:50px;' class='disabled' readonly></textarea>";
	html += "</td>";
	html += "<th>";
	html += "<span>사외참석자</span><br>";
	html += "<span id='cnf_out_cnt' name='cnf_out_cnt' class='ml3'></span>";
	html += "</th>";
	html += "<td>";
	html += "<textarea id='cnf_out_attde' name='cnf_out_attde' style='width:95%;height:50px;'></textarea>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의내용<span class='fontRed'>*</span></th>";
	html += "<td colspan='3'>";
	html += "<textarea id='cnf_agenda' name='cnf_agenda' style='width:98%;height:100px;'></textarea>";
	html += "</td>";
	html += "</tr>";
	html += "</table>";
	
	//회의실상세 조회 시작
	html += "<div id='confInfo' class='mt3'>";
	html += "<table class='tblT1 tbl_fixed'>";
	html += "<colgroup>";
	html += "<col width='90px'>";
	html += "<col width='300px'>";
	html += "<col width='70px'>";
	html += "<col width='70px'>";
	html += "<col width='60px'>";
	html += "<col width='60px'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의실</th>";
	html += "<td colspan='5'>"+rs1.regn_nm+"/"+rs1.bd_nm+"/"+rs1.corm_fl_cd.replace("-", "B")+"층"+"/"+rs1.corm_nm+"</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>위치설명</th>";
	html += "<td><div class='txt_wd'>"+rs1.corm_loc_sbc+"</div></td>";
	html += "<th>수용인원</th>";
	html += "<td colspan='3'>"+rs1.blns_cpsn+"명</td>";
	html += "</tr>";
	html += "<th>장비</th>";
	html += "<td colspan='5'>"+rs1.item_sbc+"</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>비고</th>";
	html += "<td colspan='5'>"+rs1.remark+"</td>";
	html += "</tr>";
	html += "</table>";
	
	//승인필요 회의실이지만 마스터,장기예약담당은 바로 예약가능하도록 한다.
	var confYn = rs1.conf_yn;
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		confYn = "N"; 
	}
	
	html += "<input type='hidden' id='pgs_st_cd' name='pgs_st_cd'/>";
	html += "<input type='hidden' id='conf_yn' name='conf_yn' value='"+confYn+"'/>";
	html += "</div>";
	//회의실상세 조회 끝
	
	var procYn = "";
	html += "<div class='btn_area align_c'>";
	var scd = new Date();
    var diffTime = ""+numPad(scd.getHours())+numPad(scd.getMinutes());
    if(Number(getCurrentToDate()+diffTime) > Number(rs2.cnf_to_ymd+rs2.cnf_to_time)){
    	procYn = "N";
    	html += "";
    }else{
    	procYn = "Y";
    	html += "<a href='#' id='btn_modify' class='white_btn22'><span>변경</span></a>";
    	html += "<a href='#' id='btn_delete' class='white_btn22'><span>예약취소</span></a>";
    }
	html += "<a href='#' id='btn_close' class='white_btn22'><span>닫기</span></a>";
	html += "</div>";
	html += "</div>";
	html += "</div>";
	
	form.append(html);
	var resvPop = $("<div/>").attr("id", "resv_pop").attr("style","display:none");
	resvPop.addClass("resv_pop");
	resvPop.append(form);
	$("body").append(resvPop);
	
	if(procYn == "Y"){
		$('.dtl_title').text('예약 확인 및 수정');
		if($("#etc_user").val() == "N" && sess_mstu == "U"){
			$("#cnf_from_ymd").attr("disabled", true);
			$("#cnf_from_time").attr("disabled", false);
			$("#cnf_to_ymd").attr("disabled", true);
			$("#cnf_to_time").attr("disabled", false);
		}else{
			popCalendar();
		}
	}else{
		$('.dtl_title').text('예약 확인');
		var inputIds = "#cnf_eeno,#cnf_eenm,#cnf_dept_name,#cnf_title,#cnf_out_attde,#cnf_from_ymd,#cnf_to_ymd";
		var txtareaIds = "#cnf_attde_name,#cnf_out_attde,#cnf_agenda";
		var etcIds = "#cnf_from_time,#cnf_to_time";
		var btnIds = "#btn_insa1,#btn_insa2,#tmp_title";
		$("input:checkbox[name='cnf_spe_day']").attr("disabled", true);
		$("input:checkbox[name='chk_alr_email']").attr("disabled", true);
		$("input:checkbox[id='all_day']").attr("disabled", true);
		$(inputIds + "," + txtareaIds).attr("readonly", true);
		$(etcIds + "," + btnIds).attr("disabled", true);
		$(inputIds).css({"background-color":"#fbfaf6"});
		$(txtareaIds).css({"background-color":"#fbfaf6"});
	}
	
	//시간콤보생성
	var tmpFromTimeCombo = $("#KEY_FROM_TIME").html();
	var tmpToTimeCombo = $("#KEY_TO_TIME").html();
	$("#cnf_from_time").html(tmpFromTimeCombo);
	$("#cnf_to_time").html(tmpToTimeCombo);

	var cnfAlrEmail = rs2.cnf_alr_email;
	if(cnfAlrEmail != ""){
		for(var i=0; i<cnfAlrEmail.length; i++){
			$("input:checkbox[name='chk_alr_email']").each(function(){
				if(this.value == cnfAlrEmail[i]){
					this.checked = true;
				}
			});
		}
	}
	
	//반복요일 체크
	var speDayArr = rs2.cnf_spe_day;
	if(speDayArr != ""){
		for(var i=0; i<speDayArr.length; i++){
			$("input:checkbox[name='cnf_spe_day']").each(function(){
				if(this.value == speDayArr[i]){
					this.checked = true;
				}
			});
		}
	}
	
	$("#cnf_title").val(rs2.cnf_title);
	$("#cnf_eeno").val(rs2.cnf_eeno);
	$("#cnf_eenm").val(rs2.cnf_eenm);
	$("#cnf_dept_code").val(rs2.cnf_dept_code);
	$("#cnf_dept_name").val(rs2.cnf_dept_name);
	$("#cnf_from_ymd").val(setDateFormat(rs2.cnf_from_ymd));
	$("#cnf_from_time").val(rs2.cnf_from_time);
	$("#cnf_to_ymd").val(setDateFormat(rs2.cnf_to_ymd));
	$("#cnf_to_time").val(rs2.cnf_to_time);
	$("#p_from_ymd").val(rs2.cnf_from_ymd);
	$("#p_from_time").val(rs2.cnf_from_time);
	$("#p_to_ymd").val(rs2.cnf_to_ymd);
	$("#p_to_time").val(rs2.cnf_to_time);
	$("#pgs_st_cd").val(rs2.pgs_st_cd);
	$("#cnf_attde_eeno").val(rs2.cnf_attde_eeno);
	if(rs2.cnf_attde_eeno != ""){
		$("#attde_cnt").text(rs2.cnf_attde_eeno.split(",").length+"명");
	}else{
		$("#attde_cnt").text("");
	}
	$("#cnf_attde_name").val(rs2.cnf_attde_name.replace(/,/g, '\n'));
	$("#cnf_out_attde").val(rs2.cnf_out_attde.replace(/＠/g, '\n'));
	//$("#cnf_out_cnt").val(rs2.cnf_out_cnt);
	$("#cnf_out_cnt").text("");
	$("#cnf_agenda").val(rs2.cnf_agenda.replace(/＠/g, '\n'));
}

//타인의 예약상세화면 생성
function other_detail_conference(result){
	mode = "S";
	var rs1 = result.info1;
	var rs2 = result.info2;
	
	var form = $("<form id='frmMain' name='frmMain' method='POST'/>");
	var html = "";
	html += "<div class='popLyr'>";
	html += "<div class='lyrhead'>";
	html += "<div class='i'><div class='r'>";
	html += "<h3>예약 확인</h3>";
	html += "</div></div>";
	html += "</div>";
	html += "<div class='lyrinner'>";
	html += "<table class='tblT1'>";
	html += "<colgroup>";
	html += "<col width='100px'><col width='280px'><col width='90px'><col width='*'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의 제목</th>";
	html += "<td colspan='3'><span id='td_cnf_title'></span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의주관자</th>";
	html += "<td>";
	html += "<span class='mr3' id='td_cnf_eeno'></span>";
	html += "<span class='mr3' id='td_cnf_eenm'></span>";
	html += "<span class='mr3' id='td_cnf_dept_name'></span>";
	html += "</td>";
	html += "<th>회의알림</th>";
	html += "<td><span id='td_alr_text' style='display: inline-block;width:287px;'>&nbsp;</span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>예약일시</th>";
	html += "<td colspan='3'>";
	html += "<span id='td_cnf_from_ymd'></span>&nbsp;";
	html += "<span id='td_cnf_from_time'></span>&nbsp;";
	html += "<span>부터&nbsp;</span> ";
	html += "<span id='td_cnf_to_ymd'></span>&nbsp;";
	html += "<span id='td_cnf_to_time'></span>&nbsp;";
	html += "<span>까지</span> ";
	html += "</td>";
	html += "</tr>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<tr>";
		html += "<th>반복요일설정</th>";
		html += "<td colspan='3'><span id='td_cnf_spe_day'></span></td>";
		html += "</tr>";
	}
	html += "<th>참석자<br><span id='td_attde_cnt' style='margin-left:3px'></span></th>";
	html += "<td>";
	html += "<span id='td_cnf_attde_name' style='display: inline-block;width:98%;height:75px;overflow-x:hidden;overflow-y:scroll'></span>";
	html += "</td>";
	html += "<th>참조자<br><span id='td_refer_cnt' style='margin-left:3px'></span></th>";
	html += "<td>";
	html += "<span id='td_cnf_refer_name' style='display: inline-block;width:98%;height:75px;overflow-x:hidden;overflow-y:scroll'></span>";
	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>사외참석자</th>";
	html += "<td colspan='3'><span id='td_cnf_out_attde'></span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의내용</th>";
	html += "<td colspan='3'>";
	html += "<div id='td_cnf_agenda' style='width:98%;height:50px;overflow-x:hidden;overflow-y:scroll'></div>";
	html += "</td>";
	html += "</tr>";
	html += "</table>";

	//회의실상세 조회 시작
	html += "<div id='confInfo' class='mt3'>";
	html += "<table class='tblT1'>";
	html += "<colgroup>";
	html += "<col width='92px'>";
	html += "<col width='200px'>";
	html += "<col width='60px'>";
	html += "<col width='70px'>";
	html += "<col width='60px'>";
	html += "<col width='240px'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>장소</th>";
	html += "<td><span>"+rs1.odu_regn_nm+"/"+rs1.odu_sebu_nm+"/"+rs1.bd_cl_nm+"</span></td>";
	html += "<th>층수</th>";
	html += "<td><span>"+rs1.bd_fl_cd.replace("-", "B")+"층</span></td>";
	html += "<th>위치</th>";
	html += "<td>";
	//위치보기
	html += "<div class='txt_wd'>"+rs1.corm_loc_sbc+"</div>";
	html += "<div class='rpos'><a href='#' class='viewmap btn_t1 mr5'><span>보기</span></a>";
	html += "<div class='inlyrx'>";
	html += "<div class='inbox'>";
	html += "<div class='mapDv'>";
	
	var fileId = rs1.file_id_2;
	var fileFullNm = rs1.file_img_2;
	var fileOrgNm = rs1.org_file_2;
	if(fileOrgNm == ""){
		html += "<img src='../../images/common/no_img.gif' align='absmiddle' width='404px' height='256px' style='border:1px solid #e2e2e2'>";
	}else{
		if(checkExt(fileOrgNm)){
			html += "<a href='"+fileFullNm+"' target='_blank'><img src='"+fileFullNm+"' width='404px' height='256px' title='이미지를 클릭하시면 크게 볼수 있습니다.'></a>";
		}else{
			html += "<img src='../../images/common/no_img.gif' align='absmiddle' width='170px' height='150px' style='border:1px solid #e2e2e2'>";
			html += "<input type='hidden' id='file_img_2' name='file_img_2' value='"+fileOrgNm+"'/>";
			html += "<span id='file_nm' class='ml13'><a href='javascript:saveFile(\""+fileId+"\", \"1\")'>"+fileOrgNm+"</a></span>";
		}
	}
	html += "</div>";
	html += "<a href='#' class='lyrClose'><img src='../../images/common/btn_sclose.gif' alt=''></a>";
	html += "</div>";
	html += "</div>";

	html += "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의실</th>";
	html += "<td><span>"+rs1.corm_nm+"</span></td>";
	html += "<th>수용인원</th>";
	html += "<td colspan='3'><span>"+rs1.blns_cpsn+"명</span></td>";
	html += "</tr>";
	html += "<th>장비</th>";
	html += "<td colspan='5'><span>"+rs1.item_sbc+"</span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>비고</th>";
	html += "<td colspan='5'><span>"+rs1.remark1+"</span></td>";
	html += "</tr>";
	html += "</table>";
	var confYn = rs1.conf_yn;
	if(sess_mstu == "M" && $("#etc_user").val() == "Y"){
		confYn = "N"; 
	}
	html += "<input type='hidden' id='conf_yn' name='conf_yn' value='"+confYn+"'/>";
	html += "</div>";
	//회의실상세 조회 끝

	html += "<div class='btn_area align_c'>";
	html += "<a href='#' id='btn_close' class='white_btn22'><span>닫기</span></a>";
	html += "</div>";
	html += "</div>";
	html += "</div>";

	form.append(html);
	var resvPop = $("<div/>").attr("id", "resv_pop").attr("style","display:none");
	resvPop.addClass("resv_pop");
	resvPop.append(form);
	$("body").append(resvPop);

	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		var specialDayNameArr = [];
		$("input:checkbox[name='cnf_spe_day']").each(function(){
			if(this.checked){
				var dayNm = "";
				if(this.value == "2") dayNm = '월 ';
				else if(this.value == "3") dayNm = '화 ';
				else if(this.value == "4") dayNm = '수 ';
				else if(this.value == "5") dayNm = '목 ';
				else if(this.value == "6") dayNm = '금 ';
				specialDayNameArr.push(dayNm);
			}
		});

		$("#td_cnf_spe_day").text(specialDayNameArr);
	}

	var alrmText = "";
	$("input:checkbox[name='chk_alr_email']").each(function(){
		if(this.checked){
			alrmText += "EMAIL";
		}
	});
	
	$("#td_alr_text").html(alrmText);
	$("#td_cnf_title").text(rs2.cnf_title);
	$("#td_cnf_eenm").text(rs2.cnf_eenm);
	$("#td_cnf_dept_name").text(rs2.dept_nm);
	$("#td_cnf_from_ymd").text(setDateFormat(rs2.cnf_from_ymd));
	$("#td_cnf_from_time").text(setTimeFormat(rs2.cnf_from_time));
	$("#td_cnf_to_ymd").text(setDateFormat(rs2.cnf_to_ymd));
	
	var resvToTime = changeToTime(rs2.cnf_to_time);
	$("#td_cnf_to_time").text(setTimeFormat(resvToTime));
	if(rs2.cnf_attde_eeno != ""){
		$("#td_cnf_attde_name").text(rs2.cnf_attde_name.replace(/,/g, '\n'));
		$("#td_attde_cnt").text(rs2.cnf_attde_eeno.split(",").length+"명");
	}else{
		$("#td_cnf_attde_name").text("");
		$("#td_attde_cnt").text("");
	}
	if(rs2.cnf_refer_eeno != ""){
		$("#td_cnf_refer_name").text(rs2.cnf_refer_name.replace(/,/g, '\n'));
		$("#td_refer_cnt").text(rs2.cnf_refer_eeno.split(",").length+"명");
	}else{
		$("#td_cnf_refer_name").text("");
		$("#td_refer_cnt").text("");
	}
	$("#td_cnf_out_attde").text(rs2.cnf_out_attde);
	$("#td_cnf_agenda").html(rs2.cnf_agenda.replace(/＠/g, '<br>'));
}

//예약확인
function confirm_conference(){
	var form = $("<form id='frmMain' name='frmMain' method='POST'/>");
	var html = "";
	html += "<div class='popLyr'>";
	html += "<div class='lyrhead'>";
	html += "<div class='i'><div class='r'>";
	html += "<h3>예약 입력 확인</h3>";
	html += "</div></div>";
	html += "</div>";
	html += "<div class='lyrinner'>";
	html += "<table class='tblT1'>";
	html += "<colgroup>";
	html += "<col width='100px'><col width='280px'><col width='90px'><col width='*'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<th>회의 제목</th>";
	html += "<td colspan='3'><span id='td_cnf_title'></span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의주관자</th>";
	html += "<td>";
	html += "<span class='mr3' id='td_cnf_eeno'></span>";
	html += "<span class='mr3' id='td_cnf_eenm'></span>";
	html += "<span class='mr3' id='td_cnf_dept_name'></span>";
	html += "</td>";
	html += "<th>회의알림</th>";
	html += "<td><span id='td_alr_text' style='display: inline-block;width:287px;'>&nbsp;</span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>예약일시</th>";
	html += "<td colspan='3'>";
	html += "<span id='td_cnf_from_ymd'></span>&nbsp;";
	html += "<span id='td_cnf_from_time'></span>&nbsp;";
	html += "<span>부터&nbsp;</span> ";
	html += "<span id='td_cnf_to_ymd'></span>&nbsp;";
	html += "<span id='td_cnf_to_time'></span>&nbsp;";
	html += "<span>까지</span> ";
	html += "</td>";
	html += "</tr>";
	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		html += "<tr>";
		html += "<th>반복요일설정</th>";
		html += "<td colspan='3'><span id='td_cnf_spe_day'></span></td>";
		html += "</tr>";
	}
	html += "<th>참석자<br><span id='td_attde_cnt' style='margin-left:3px'></span></th>";
	html += "<td>";
	html += "<span id='td_cnf_attde_name' style='display: inline-block;width:98%;height:75px;overflow-x:hidden;overflow-y:scroll'></span>";
	html += "</td>";
	html += "<th>사외참석자<br><span id='td_cnf_out_cnt' style='margin-left:3px'></span></th>";
	html += "<td colspan='3'><span id='td_cnf_out_attde'></span></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<th>회의내용</th>";
	html += "<td colspan='3'>";
	html += "<div id='td_cnf_agenda' style='width:98%;height:50px;overflow-x:hidden;overflow-y:scroll'></div>";
	html += "</td>";
	html += "</tr>";
	html += "</table>";

	//준수사항 시작
	html += "<table class='tblT1 mt5'>";
	html += "<colgroup>";
	html += "<col width='*'>";
	html += "</colgroup>";
	html += "<tr>";
	html += "<td>";
	html += "<b>준수사항</b><br>";
	html += "<hr>";
	html += "<div>1. 예약취소시 반드시 시스템으로 취소처리 바랍니다.</div>";
	html += "<div>2. 사용전 장비 이상있을 시, 임의조작하지 마시고 담당자에게 연락바랍니다.</div>";
	html += "<div class='fontRed'>3. 장시간 예약 후 일부시간만 사용 시 타팀 회의실 이용을 위해 예약내역 삭제 바랍니다.</div>";
	html += "<div>4. 회의실 사용 후 반드시 장비 및 자리 정리정돈 바랍니다.</div>";
	html += "<div>5. 사외참석자가 있는 경우 출입보안시스템으로 예약하셔야 합니다.</div>";
	html += "</td>";
	html += "</tr>";
	html += "</table>";
	//준수사항 끝

	html += "<div class='btn_area align_c'>";
	html += "<a href='#' id='btn_save' class='white_btn22'><span>저장</span></a>";
	html += "<a href='#' id='btn_prev' class='white_btn22'><span>이전</span></a>";
	html += "<a href='#' id='btn_del' class='white_btn22'><span>취소</span></a>";
	html += "</div>";
	html += "</div>";
	html += "</div>";

	form.append(html);
	var confirmPop = $("<div/>").attr("id", "resv_confirm_pop").attr("style","display:none");
	confirmPop.addClass("resv_confirm_pop");
	confirmPop.append(form);
	$("body").append(confirmPop);

	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		var specialDayNameArr = [];
		$("input:checkbox[name='cnf_spe_day']").each(function(){
			if(this.checked){
				var dayNm = "";
				if(this.value == "2") dayNm = '월 ';
				else if(this.value == "3") dayNm = '화 ';
				else if(this.value == "4") dayNm = '수 ';
				else if(this.value == "5") dayNm = '목 ';
				else if(this.value == "6") dayNm = '금 ';
				specialDayNameArr.push(dayNm);
			}
		});

		$("#td_cnf_spe_day").text(specialDayNameArr);
	}

	var alrmText = "";
	$("input:checkbox[name='chk_alr_email']").each(function(){
		if(this.checked){
			alrmText += "EMAIL";
		}
	});
	
	$("#td_alr_text").html(alrmText);
	$("#td_cnf_title").text($("#cnf_title").val());
	$("#td_cnf_eenm").text($("#cnf_eenm").val());
	$("#td_cnf_dept_name").text($("#cnf_dept_name").val());
	$("#td_cnf_from_ymd").text(setDateFormat($("#cnf_from_ymd").val()));
	$("#td_cnf_from_time").text(setTimeFormat($("#cnf_from_time").val()));
	$("#td_cnf_to_ymd").text(setDateFormat($("#cnf_to_ymd").val()));
	
    var revToTime = changeToTime($("#cnf_to_time").val());
	$("#td_cnf_to_time").text(setTimeFormat(revToTime));
	$("#td_cnf_attde_name").html($("#cnf_attde_name").val().replace(/\n/g, '<br>'));
	$("#td_attde_cnt").text($("#attde_cnt").text());
	$("#td_cnf_out_attde").text($("#cnf_out_attde").val().replace(/\n/g, '<br>'));
	$("#td_cnf_out_cnt").text($("#cnf_out_cnt").text());
	$("#td_cnf_agenda").html($("#cnf_agenda").val().replace(/\n/g, '<br>'));
}

var personGubn = "";
function cormReservationView(flag){
	//flag => 1.본인(마스터, 담당자 포함), 2.타인
	if(sess_mstu == "M"){
		personGubn = "1";
	}else{
		personGubn = flag;
	}
	//수정 및 상세
	conferenceInfo("S");
}

function cormReservation(){
	//작성
	conferenceInfo("I");
}

function conferenceInfo(type){
	if(sess_mstu == "U"){
		if($("#etc_user").val() == "N"){
			if(type == "I" || type == "S"){
				var date1 = $("#getToDay").val();
				var date2 = selectNum($("#p_from_ymd").val());
				var date3 = selectNum($("#p_to_ymd").val());
				
				if(checkPeriodIsOneMonth(date2, date3)){
					if (checkOneMonth(date1, date2)) { 
					}else{
						alert("예약은 한달 이내만 할 수 있습니다.");
						return;
					}
				}else{
					alert("예약은 한달 이내만 할 수 있습니다.");
					return;
				}
			}
		}
	}
	
	//회의실정보 조회 -> 선점예약 -> 예약화면 생성
    var paramData = {
        regn_cd : $("#p_regn_cd").val(),
        bd_cd : $("#p_bd_cd").val(),
        corm_cd : $("#p_corm_cd").val()
    };
    var form_data = {
    	viewType : type,
    	keyNo : $("#hid_key_no").val(),
    	docNo : $("#hid_doc_no").val(),
        paramJson : util.jsonToString(paramData)
    };
    var params = {
        url : "doSearchConferenceInfo.do",
        data : form_data,
        callback : "conferenceInfoCallBack(data.sendResult, '"+type+"')"
    };
    Con.ajax(params);
}

function conferenceInfoCallBack(result, type){
	$(".resv_pop").remove();
	
	var gbWidth = "784";
	var gbHeight = "500";
	var popTop = (($(window).height() - Number(gbHeight)) /2)-100 + 'px';
	var popLeft = (($(window).width() - Number(gbWidth)) /2) + 'px';
	var css = {
		top: popTop, left: popLeft, width: gbWidth, height: gbHeight, textAlign: "left",
		cursor: 'hand', border: 'none', backgroundColor:'#fff'
	};
	
	//신규
	if(type == "I"){
		create_conference(result);
		$.blockUI({
		    message: $("#resv_pop"),
		    css: css
		});
		setTimeout('setResvField();', 300);
	}else if(type == "S"){
		//본인 예약 내용
		if(personGubn == "1"){
			detail_conference(result);
			$.blockUI({
				message: $("#resv_pop"),
				css: css
			});
		}else if(personGubn == "2"){
			//타인 예약 내용
			other_detail_conference(result);
			$.blockUI({
				message: $("#resv_pop"),
				css: css
			});
		}
	}
}

function setResvField(){
	//콤보생성
	var tmpFromTimeCombo = $("#KEY_FROM_TIME").html();
	var tmpToTimeCombo = $("#KEY_TO_TIME").html();
	$("#cnf_from_time").html(tmpFromTimeCombo);
	$("#cnf_to_time").html(tmpToTimeCombo);

	$("#cnf_eeno").val(sess_empno);
	$("#cnf_eenm").val(sess_name);
	$("#cnf_dept_code").val(sess_dept_code);
	$("#cnf_dept_name").val(sess_dept_name);

	$("#cnf_from_ymd").val(setDateFormat($("#p_from_ymd").val()));
	$("#cnf_to_ymd").val(setDateFormat($("#p_to_ymd").val()));
	$("#cnf_from_time").val($("#p_from_time").val());
	$("#cnf_to_time").val($("#p_to_time").val());
	
	//예약선점
	doBeforeInsert();
}

function doBeforeInsert(){
	//예약선점 후 테이블 생성(doc no를 셋팅한다.);
    var paramData = {
        regn_cd : $("#p_regn_cd").val(),
        bd_cd : $("#p_bd_cd").val(),
        corm_fl_cd : $("#p_corm_fl_cd").val(),
        corm_cd : $("#p_corm_cd").val(),
        cnf_eeno : $("#cnf_eeno").val(),
    	cnf_from_ymd : selectNum($("#cnf_from_ymd").val()),
    	cnf_to_ymd : selectNum($("#cnf_to_ymd").val()),
    	cnf_from_time : selectNum($("#cnf_from_time").val()),
    	cnf_to_time : selectNum($("#cnf_to_time").val())
    };
    var form_data = {
        paramJson : util.jsonToString(paramData)
    };
    var params = {
        url : "doInsertConferenceRoom.do",
        data : form_data,
        callback : "doBeforeInsertCallBack(data.sendResult)"
    };
    Con.ajax(params);
}

function doBeforeInsertCallBack(rs){
	if(rs.result == "Y"){
		$("#hid_doc_no").val(rs.doc_no);
	}else{
		alert(rs.message);
		$.unblockUI();
		if($("#menuId").val() == "XSM02"){
			cellBgColor("A");
		}
		mode = "";
		setClear();
		setTimeout('$(".resv_pop").remove();', 500);
		clearInterval(intervalTime);
	}
}

//색칠했던 배경을 원래대로 되돌린다.
function cellBgColor(flag){
	if(flag == "A"){
		$("#"+firId+"_"+firIdx).css("background", "");
		for(var i = 1; i <= firToLastNo; i++){
			$("#"+lstId+"_"+(firIdx+i)).css("background", "");
		}
	}else{
		$("#"+firId+"_"+firIdx).css("background", "");
	}
}

function validation(){
	var rs = true;
	if($.trim($("#cnf_title").val()) == ""){
		alert("회의 제목을 입력 하세요.");
		$("#cnf_title").focus();
		rs = false;
	}else if($("#cnf_eeno").val() == ""){
		alert("회의 주관자를 선택하세요.");
		rs = false;
	}
	else if($.trim($("#cnf_agenda").val()) == ""){
		alert("회의 내용을 입력 하세요.");
		$("#cnf_agenda").focus();
		rs = false;
	}else{
		var scd = new Date();
		var diffTime = ""+numPad(scd.getHours())+numPad(scd.getMinutes());
		var a = Number(getCurrentToDate()+diffTime);
		var b = Number(selectNum($("#cnf_from_ymd").val())+selectNum($("#cnf_from_time").val()));
		var c = Number(selectNum($("#cnf_to_ymd").val())+selectNum($("#cnf_to_time").val()));
		if(a > b){
			alert("지난 일정은 예약 할 수 없습니다.");
			rs = false;
		}else if(b > c){
			alert("종료시간이 잘못 선택 되었습니다.");
			rs = false;    	
		}
	}
	
	return rs;
}

function updateConferenceRoom(flag){
	//필드검사
	if(!validation()) return;
	var alrEmailArr = [];
	var specialDayArr = [];
	$("input:checkbox[name='chk_alr_email']").each(function(){
		if(this.checked){
			alrEmailArr.push(this.value);
		}
	});

	if($("#etc_user").val() == "Y" || sess_mstu == "M"){
		$("input:checkbox[name='cnf_spe_day']").each(function(){
			if(this.checked){
				specialDayArr.push(this.value);
			}
		});
	}
	
	var targetMsg = "";
	var confirmMsg = "";
	if(alrEmailArr == "Y"){ targetMsg += "이메일"; }
	if(targetMsg != ""){
		if($.trim($("#cnf_attde_eeno").val()) == ""){
			alert("회의알림이 설정되었으나, 참석자를 지정하지 않았습니다.");
			return;
		}
		confirmMsg += "회의 참석자에게 " + targetMsg + "로 알림이 발송됩니다.\n";
	}
	
	if(flag == "I"){
		confirmMsg += "저장 하시겠습니까?";
	}else{
		if(sess_mstu == "U"){
			if($("#etc_user").val() == "N"){
				var date1 = $("#getToDay").val();
				var date2 = selectNum($("#cnf_from_ymd").val());
				var date3 = selectNum($("#cnf_to_ymd").val());
				
				if($("#KEY_REGN_CD").val() == "8"){
					var diffCnt = getDiffDay(date1, date2);
					if(diffCnt > 7){
						alert("예약은 1주 이내만 할 수 있습니다.");
						return;
					}
				}else{
					if(checkPeriodIsOneMonth(date2, date3)){
						if (checkOneMonth(date1, date2)) { 
						}else{
							alert("예약은 한달 이내만 할 수 있습니다.");
							return;
						}
					}else{
						alert("예약은 한달 이내만 할 수 있습니다.");
						return;
					}
				}
			}
    	}
		confirmMsg += "변경 하시겠습니까?";
	}
	if(!confirm(confirmMsg)){
		return;
	}
	
    var paramData = {
    	doc_no : $("#hid_doc_no").val(),
        regn_cd : $("#p_regn_cd").val(),
        bd_cd : $("#p_bd_cd").val(),
        corm_fl_cd : $("#p_corm_fl_cd").val(),
        corm_cd : $("#p_corm_cd").val(),
    	cnf_title : $.trim($("#cnf_title").val()),
    	cnf_eeno : $("#cnf_eeno").val(),
    	cnf_dept_code : $("#cnf_dept_code").val(),
		cnf_alr_email : alrEmailArr,
    	cnf_from_ymd : selectNum($("#cnf_from_ymd").val()),
    	cnf_to_ymd : selectNum($("#cnf_to_ymd").val()),
    	cnf_from_time : $.trim($("#cnf_from_time").val()),
    	cnf_to_time : $.trim($("#cnf_to_time").val()),
    	cnf_spe_day : specialDayArr,
    	cnf_attde_eeno : $.trim($("#cnf_attde_eeno").val()),
    	cnf_attde_name : $.trim($("#cnf_attde_name").val().replace(/\n/g, ',')),
    	cnf_attde_cnt : selectNum($("#attde_cnt").text()),
    	cnf_out_attde : $.trim($("#cnf_out_attde").val()),
    	cnf_out_cnt : selectNum($("#cnf_out_cnt").text() == "" ? "0" : $("#cnf_out_cnt").text()),
    	cnf_agenda : $("#cnf_agenda").val().replace(/\n/g, '＠'),
    	//cnf_file_id : $("#cnf_file_id").val(),
    	pgs_st_cd : $("#pgs_st_cd").val(),
    	isYn : $("#conf_yn").val(),
    	mode : flag
    };
    var form_data = {
    	keyNo : $("#hid_key_no").val(),
        paramJson : util.jsonToString(paramData)
    };
    var params = {
        url : "doUpdateConferenceRoom.do",
        data : form_data,
        callback : "doUpdateCallBack(data.sendResult, '"+flag+"')"
    };
    Con.ajax(params);
}

function doUpdateCallBack(rs, flag){
	alert(rs.message);
	if(rs.code1 == "Y"){
		$.unblockUI();
		if(flag == "I"){
			setTimeout('$(".resv_pop").remove();', 500);
			setTimeout('$(".confirm_pop").remove();', 550);
			clearInterval(intervalTime);
		}else{
			setTimeout('$(".resv_pop").remove();', 500);
		}
		mode = "";
		setClear();
		doSearch();
	}
}

//부서팝업에서 결과값이 받아서 처리 함
function setInsaInfo(rs, job){
	if(typeof(job) == "undefined"){
		if(sess_mstu == "M"){
			$("#cnf_eeno").val(rs.xusr_empno);
			$("#cnf_eenm").val(rs.xusr_name);
			$("#cnf_dept_code").val(rs.xusr_dept_code);
			$("#cnf_dept_name").val(rs.xusr_dept_name);
		}else{
			if(sess_dept_code != rs.xusr_dept_code){
				alert("회의주관자와 입력자의 부서가 다릅니다.");
				return;
			}else{
				$("#cnf_eeno").val(rs.xusr_empno);
				$("#cnf_eenm").val(rs.xusr_name);
				$("#cnf_dept_code").val(rs.xusr_dept_code);
				$("#cnf_dept_name").val(rs.xusr_dept_name);
			}
		}
	}else if(job == "A"){
		$("#cnf_attde_eeno").val(rs.empnos);
		$("#cnf_attde_name").val(rs.names);
		if(rs.cnt > 0){
			$("#attde_cnt").text(rs.cnt+"명");
		}else{
			$("#attde_cnt").text("");
		}
	}else if(job == "B"){
		$("#rpt_oth_eeno").val(rs.empnos);
		$("#rpt_oth_name").val(rs.names);
		if(rs.cnt > 0){
			$("#oth_cnt").text(rs.cnt+"명");
		}else{
			$("#oth_cnt").text("");
		}
	}
}

//예약 삭제
function doResvDelete(){
    var paramData = {
        doc_no : $("#hid_doc_no").val()
    };
    var form_data = {
    	keyNo : $("#hid_key_no").val(),
        paramJson : util.jsonToString(paramData)
    };
    var params = {
        url : "doDeleteConferenceRoom.do",
        data : form_data,
        callback : "doResvDeleteCallBack(data.sendResult)"
    };
    Con.ajax(params);
}

function doResvDeleteCallBack(rs){
	mode = "";
	doSearch();
}

function errCallBack(){
	if($("#menuId").val() == "XSM02"){
		cellBgColor("A");
	}else if($("#menuId").val() == "XSM03"){
		setTimeout('$("#conf_report_pop").remove();doSearch();', 500);
	}
	setClear();
}

//타이머
var intervalTime = "";
function timer_script(t){
	var timeLeft = typeof(t) == "unfdefind" ? "180" : t;
	var updateLeftTime = function(){
		timeLeft = (timeLeft <= 0) ? 0 : --timeLeft;
		var hours = numPad(Math.floor(timeLeft/3600));
		var minutes = numPad(Math.floor((timeLeft - 3600*hours)/60));
		var seconds = numPad(timeLeft%60);
		$("#t_left_time").html(hours+":"+minutes+":"+seconds);
		if(timeLeft == 0){
			//로직처리
			if($("#menuId").val() == "XSM02"){
				//색칠했던 배경을 원래대로 되돌린다.
				cellBgColor("A");
			}
			/*
			try{
				ifm.cal_close();
			}catch(e){}
			*/
			$.unblockUI({
				onUnBlock : function(){
					doResvDelete();
					setClear();
					setTimeout('$(".resv_pop").remove();', 500);
					alert("입력 시간이 초과되어 예약이 취소 되었습니다.");
					clearInterval(intervalTime);
					return;
				}
			});
		}
	};
	updateLeftTime();
	intervalTime = setInterval(updateLeftTime, 1000);
}

function onUnload(){
	if(mode == "I"){
		mode = "";
		alert("예약중 다른 메뉴를 클릭 하였습니다.\n현재 예약건은 삭제 됩니다.");
		parent.parent.pageOutAfterCall($("#hid_doc_no").val());
	}
}

function popCalendar(){
	$("#cnf_from_ymd").datepicker({
		dateFormat: "yy/mm/dd",
		showOn: "button",
		buttonImage: "../../images/sub/ico_datepicker.gif",
		buttonImageOnly: true,
		onSelect:function(){
			$("#cnf_from_ymd").blur();
		}
	});
	$("#cnf_to_ymd").datepicker({
		dateFormat: "yy/mm/dd",
		showOn: "button",
		buttonImage: "../../images/sub/ico_datepicker.gif",
		buttonImageOnly: true,
		onSelect:function(){
			$("#cnf_to_ymd").blur();
		}
	});
	$('#ui-datepicker-div').addClass('blockMsg');
}

function setCnfTitle(rs){
	$("#cnf_title").val(rs.cnf_title);
}