package com.hncis.common.application; 


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jeus.xml.binding.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.common.Constant;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.HncisCommon;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz013Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz033Dto;
import com.hncis.common.vo.BpmInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Component
public class CommonGasc {
    private transient static Log logger = LogFactory.getLog(CommonGasc.class.getClass());
    
    private static CommonJobDao commonJobDao;
	private static SystemDao systemDao;
	
	private static final String reqLocale = "reqLocale";
	
	private static final String str_excel = "excel";
	private static final String str_message = "messege";
	
	private static final String str_html_template_01 = "			<tr>  \n";
	private static final String str_html_template_02 = "			</tr> \n";
	private static final String str_html_template_03 = "		</table> \n";
	private static final String str_html_template_04 = "	</div> \n";
	private static final String str_html_template_05 = "</div> \n";
	private static final String str_html_template_06 = "\")'>";
	private static final String str_html_template_07 = "<span></span></a></li> \n";
	private static final String str_html_template_08 = "' \n";
	
	
	@Autowired
	public void setCommonJobDao(CommonJobDao commonJobDao){
		CommonGasc.commonJobDao = commonJobDao;
	}

	@Autowired
	public void setSystemDao(SystemDao systemDao){
		CommonGasc.systemDao = systemDao;
	}

	public static String getTitleAndButtonNew(String userId, String screenId, String button, String gubun1, String gubun2, String gubun3, HttpServletRequest req) throws HncisException, SessionException{
		return getTitleAndButtonNew(userId, screenId, button, gubun1, gubun2, gubun3, "N", req);
	}

	/**
	 * 화면 상단 타이틀 및 버튼 가져오기(결재란 포함)
	 * 작성 날짜: (2011-04-07)
	 * @return String
	 * @param userId 		java.lang.String 						사용자 ID
	 * @param screenId	 	java.lang.String 						화면 ID
	 * @param button		java.lang.String 						image1/function1@image2/function2@...
	 * @param gubun1		java.lang.String 						결재_발신(1@2@3....@13)
	 * @param gubun2		java.lang.String 						결재_접수(1@2)
	 * @param gubun3		java.lang.String
	 * @param gubun4		java.lang.String 						결재판 여부
	 * @param req	 		javax.servlet.http.HttpServletRequest 	request
	 * @throws SessionException
	 */
	public static String getTitleAndButtonNew(String userId, String screenId, String button, String gubun1, String gubun2, String gubun3, String gubun4, HttpServletRequest req) throws HncisException, SessionException{

		String eeno = SessionInfo.getSess_empno(req);
		String corpCd = SessionInfo.getSess_corp_cd(req);
		String admCorpCd = SessionInfo.getSess_adm_corp_cd(req);
		String locale = req.getSession().getAttribute(reqLocale).toString();
		HncisCommon hmcCommon = new HncisCommon();
		hmcCommon.setEeNo(eeno);
		hmcCommon.setScrn_id(screenId);
		hmcCommon.setXcod_code(screenId.substring(1, 3));
		hmcCommon.setCorp_cd(corpCd);
		hmcCommon.setLocale(locale);

		StringBuffer data       = new StringBuffer(500);

//		String arrGubun1 = gubun1 == null || gubun1.equals("") ? "" : gubun1;
//		String arrGubun2 = gubun2 == null || gubun2.equals("") ? "" : gubun2;
//		String arrGubun3 = gubun3 == null || gubun3.equals("") ? "" : gubun3;

		String arrGubun1 = "";
		String arrGubun2 = "";
		String arrGubun3 = "";

		if("Y".equals(StringUtil.isNullToString(gubun4)) && screenId.length() > 3){
			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setCorp_cd(corpCd);
			commonApproval.setSystem_code(screenId.substring(1,3));

			String approveStepLevel = commonJobDao.selectApproveStepLevel(commonApproval);

			if(!"".equals(StringUtil.isNullToString(approveStepLevel))){
				arrGubun1 = "1";
				arrGubun2 = "3";
			}
		}

		StringTokenizer st = null;
		StringTokenizer st2 = null;

		String lgrp_nm = "";
		String mgrp_nm = "";
		String screenName = "";
		String menuInfo = "";
		int sndType = 0;
		int workAuth = 0;
		int cnfm = 0;
		String cnfm_auth = "";

		String path = req.getContextPath();
		String tempButton = "";
		String buttonName = "";
		String buttonFunction = "";
		String display = "";
		String imgPath = "";
		int idx = 0;
		boolean flag = false;

		String appendStr = "";
		try {
			HncisCommon scrnInfo = commonJobDao.getScrnInfo(hmcCommon);
			if(scrnInfo != null){
				mgrp_nm    = scrnInfo.getMenu_mgrp_nm();
				screenName = scrnInfo.getScrn_nm().trim();
				menuInfo   = mgrp_nm + " > " + screenName;
				try {
					sndType = Integer.parseInt(scrnInfo.getMenu_mgrp_cd().trim());
				} catch(Exception ee) {
					sndType = -1;
					screenName = "ERROR";
				}
			}else{
				screenName = "미등록 화면";
				imgPath    = screenName;
				sndType    = -1;
			}
			hmcCommon.setSndType(sndType);
			try{
				if("admin".equals(admCorpCd)){
					workAuth = 5;
					cnfm = 5;
				}else{
					workAuth = Integer.parseInt(commonJobDao.getAuthority(hmcCommon).trim());
					cnfm = Integer.parseInt(SessionInfo.getSess_cnfm_auth(req).trim());

				}
			}catch (Exception ee) {
				workAuth = 0;
				cnfm = 1;
			}

			CommonApproval approval = new CommonApproval();
			approval.setRdcs_eeno(userId);
			approval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			int appCnt = systemDao.getSelectCountToMyApproval(approval);

			String bizId = screenId.substring(1, 3);

			BgabGascz013Dto z013dto = new BgabGascz013Dto();
			z013dto.setApp_type(bizId);
			z013dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			BgabGascz013Dto z013Rst = systemDao.selectApprToUseYn(z013dto);

			String apprYn = "N";
			int apprLev = 0;

			if(z013Rst != null){
				apprYn = StringUtil.isNullToString(z013Rst.getUse_yn(), "N");
				if(apprYn.equals("Y")){
					apprLev = Integer.parseInt(z013Rst.getApp_level())+1;
				}
			}

			String dsp = "";
			if(!apprYn.equals("Y")){
				dsp = "none";
			}

			hmcCommon.setSndType(sndType);
			screenName = screenName.replace("<BR>", "");
			appendStr += "<div class='sub_title_area'> \n";
			appendStr += "	<h3 class='sub_title fl'>";
			appendStr += screenName;
			appendStr += "</h3>\n";
			//버튼

			if(!arrGubun1.equals("") && arrGubun3.equals("")){
				appendStr += "<div class='app_box fr'> \n";
				appendStr += "	<div class='app_box_left'> \n";
				appendStr += "		<table class='tbl_fixed'> \n";
				appendStr += str_html_template_01;
				for (int i = 0; i < 5; i++){
					if (i < Integer.parseInt(arrGubun1)){
						appendStr += "		<th id='SUBMIT_TITLE' style='display:";
						appendStr += dsp;
						appendStr += "'>&nbsp;</th> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_01;
				for (int i = 0; i < 5; i++){
					if (i < Integer.parseInt(arrGubun1)){
						appendStr += "		<td id='SUBMIT_DATA' style='display:";
						appendStr += dsp;
						appendStr += "'>&nbsp;</td> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_03;
				appendStr += str_html_template_04;
			}

			if(!arrGubun2.equals("") && arrGubun3.equals("")){
				appendStr += "	<div class='app_box_right'> \n";
				appendStr += "		<table class='tbl_fixed'> \n";
				appendStr += "			<tr> \n";
				for(int i = 0; i < 5; i++){
//					if (i < Integer.parseInt(arrGubun2)){
//						appendStr += "		<th id='SUBMIT_TITLE' style='display:"+ dsp +"'>&nbsp;</th> \n");
//					}
					if (i < apprLev){
						appendStr += "		<th id='SUBMIT_TITLE' style='display:";
						appendStr += dsp;
						appendStr += "'>&nbsp;</th> \n";
					}
					else{
						appendStr += "		<th id='SUBMIT_TITLE' style='display:none'>&nbsp;</th> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_01;
				for(int i = 0; i < 5; i++){
//					if (i < Integer.parseInt(arrGubun2)){
//						appendStr += "		<td id='SUBMIT_DATA' style='display:"+ dsp +"'>&nbsp;</td> \n");
//					}
					if (i < apprLev){
						appendStr += "		<td id='SUBMIT_DATA' style='display:";
						appendStr += dsp;
						appendStr += "'>&nbsp;</td> \n";
					}
					else{
						appendStr += "		<td id='SUBMIT_DATA' style='display:none'>&nbsp;</td> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_03;
				appendStr += str_html_template_04;
				appendStr += str_html_template_05;
			}

			appendStr += str_html_template_04;
			appendStr += "	<div class='btn_area'> \n";
			appendStr += "		<ul class='btns'> \n";

			st = new StringTokenizer(button);
			while(st.hasMoreElements()){
				tempButton = st.nextToken("@");

				idx = 0;
				st2 = new StringTokenizer(tempButton);
				while(st2.hasMoreElements()){
					if (idx==0){
						buttonName = st2.nextToken("/").trim();
						//buttonName = buttonName.toLowerCase();
						idx++;
					} else {
						buttonFunction = st2.nextToken("/").trim();
					}
				}

				flag = false;
				if (buttonFunction.trim().equals("close")){
					flag = true;
				} else {
					if (buttonFunction.equals(str_excel)){
						if (workAuth >= 1){flag = true;}
					} else if (buttonFunction.startsWith("excelUpload")){
						if (workAuth >= 1){flag = true;}
					} else if (buttonFunction.equals("search")||buttonFunction.equals("day")||buttonFunction.equals("week")||buttonFunction.equals("month")){
						if (workAuth >= 1){flag = true;	}
					} else if (buttonFunction.equals("print")){
						if (workAuth >= 1){flag = true;	}
					} else if (buttonFunction.equals("back")){
						if (workAuth >= 1){flag = true;	}
//						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth == 5){
//							flag = true;
//						}
					} else if (buttonFunction.equals("cancel")){
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("insert")||buttonFunction.equals("apply")||buttonFunction.equals("write")||buttonFunction.equals("save")||buttonFunction.equals("request")||buttonFunction.equals("requestCancel")||buttonFunction.equals("copy")||buttonFunction.equals("new")||buttonFunction.equals("afterCal")){
						if (workAuth >= 2){flag = true;}
					} else if (buttonFunction.equals("update")||buttonFunction.equals("modify")||buttonFunction.equals("edit")){
						if (workAuth >= 3){flag = true;}
					} else if (buttonFunction.equals("delete")||buttonFunction.equals("applycancel")){
						if (workAuth >= 4){flag = true;}
					} else if (buttonFunction.equals("upload")||buttonFunction.equals("sendmail")||buttonFunction.equals("reply")||buttonFunction.equals("check")||buttonFunction.equals("checkCancel")){
						if (workAuth >= 4){flag = true;}
					} else if (buttonFunction.equals("approve")||buttonFunction.equals("approveCancel")||buttonFunction.equals("list")||buttonFunction.equals("mapInfo")||buttonFunction.equals("wbs")||buttonFunction.equals("io")||buttonFunction.equals("incomeCheck")){
						flag = true;
					} else if (buttonFunction.equals("report")||buttonFunction.equals("reportCancel")){
						flag = true;
					} else if (buttonFunction.equals("receive")||buttonFunction.equals("receive_cancel")||buttonFunction.equals("reject")||buttonFunction.equals("hrreportDone")||buttonFunction.equals("hrreportDoneCancel")){
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm")||buttonFunction.equals("confirmCancel")||buttonFunction.equals("issue")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm1")||buttonFunction.equals("confirmCancel1")||buttonFunction.equals("order")||buttonFunction.equals("hrreport")||buttonFunction.equals("hrreportCancel")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth == 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm2")||buttonFunction.equals("confirmCancel2")) {
						//명함 최종 결제자 특정 사번만 보이게 세팅
						if (SessionInfo.getSess_empno(req).equals("18104243")||SessionInfo.getSess_mstu_gubb(req).equals("M")){
							flag = true;
						}
					}else if (buttonFunction.equals("confirm3")||buttonFunction.equals("confirmCancel3")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					}else if (buttonFunction.equals("forceConfirm")||buttonFunction.equals("sapExport")||buttonFunction.equals("voucherExcel")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					}else if (buttonFunction.equals("write1")||buttonFunction.equals("edit1")||buttonFunction.equals("delete1")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M")){
							flag = true;
						}
					}else if (buttonFunction.equals("done")||buttonFunction.equals("cancel")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth == 5){
							flag = true;
						}
					}else {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M")){flag = true;}
						if (workAuth == 5){flag = true;}
					}
				}
				if (flag){
					display = "";
				} else {
					display = " style=\"display:none;\"";
				}

				String btnVal = buttonName;
				btnVal = buttonName.substring(0,1).toUpperCase() +  buttonName.substring(1);

				if (buttonFunction.equals(str_excel)){
					appendStr += "	<li id='";
					appendStr += buttonFunction;
					appendStr += "' ";
					appendStr += display;
					appendStr += " class='fl'><a href='javascript:retrieve(\"";
					appendStr += buttonFunction;
					appendStr += str_html_template_06;
					appendStr += btnVal;
					appendStr += str_html_template_07;
				} else {
					appendStr += "		<li id='";
					appendStr += buttonFunction;
					appendStr += "' ";
					appendStr += display;
					appendStr += "><a href='javascript:retrieve(\"";
					appendStr += buttonFunction;
					appendStr += str_html_template_06;
					appendStr += btnVal;
					appendStr += str_html_template_07;
				}

			}

			if(!"".equals(button)){
				/*if(!screenId.substring(0,3).toUpperCase().equals("XST") && !locale.equals("zh")){
					appendStr += "		<li id='q_tip' class='fr'><p id='tipBtn' name='q_tip' onclick='javascript:pageStepHelpPopup();' style='cursor:pointer;'></p></li> \n");
				}*/
				if(!screenId.substring(0,3).equalsIgnoreCase("XST")){
					appendStr += "		<li id='q_tip' class='fr'><p id='tipBtn' name='q_tip' onclick='javascript:pageStepHelpPopup();' style='cursor:pointer;'></p></li> \n";
				}
			}

			appendStr += "	</ul> \n";
			appendStr += str_html_template_05;
			appendStr += "<input type='hidden' id='work_auth' value='";
			appendStr += workAuth;
			appendStr += "'>";
			appendStr += "<input type='hidden' id='apprLev1' value='";
			appendStr += arrGubun1;
			appendStr += "'>";
			appendStr += "<input type='hidden' id='apprLev2' value='";
			appendStr += apprLev;
			appendStr += "'>";
			//appendStr += "<script>jQuery(document).ready(function(){$('#"+screenId.substring(1,3).toUpperCase()+"').slideDown('fast');})</script>");
			data.append(appendStr);
		}catch (Exception e){
			logger.error(str_message, e);
		}
		return data.toString();
	}

	/**
	 * 화면 상단 타이틀 및 버튼 가져오기(결재란 포함)
	 * 작성 날짜: (2011-04-07)
	 * @return String
	 * @param userId 		java.lang.String 						사용자 ID
	 * @param screenId	 	java.lang.String 						화면 ID
	 * @param button		java.lang.String 						image1/function1@image2/function2@...
	 * @param gubun1		java.lang.String 						결재_발신(1@2@3....@13)
	 * @param gubun2		java.lang.String 						결재_접수(1@2)
	 * @param req	 		javax.servlet.http.HttpServletRequest 	request
	 * @throws SessionException
	 */
	public static String getScnAuth(String userId, String screenId, HttpServletRequest req) throws HncisException, SessionException{

		HncisCommon hmcCommon = new HncisCommon();
//		hmcCommon.setEeNo(userId);
		hmcCommon.setEeNo(SessionInfo.getSess_empno(req));
		hmcCommon.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		hmcCommon.setScrn_id(screenId);
		hmcCommon.setXcod_code(screenId.substring(1, 3));
		hmcCommon.setLocale(req.getSession().getAttribute(reqLocale).toString());
		int sndType = 0;
		String workAuth = "0";


		try {
			HncisCommon scrnInfo = commonJobDao.getScrnInfo(hmcCommon);
			sndType = Integer.parseInt(scrnInfo.getMenu_mgrp_cd().trim());
			hmcCommon.setSndType(sndType);
			try{
				workAuth = commonJobDao.getAuthority(hmcCommon).trim();
			}catch (Exception ee) {
				workAuth = "0";
			}

		}catch (Exception e){
			logger.error(str_message, e);
		}
		return workAuth;
	}

	/**
	 * notice popup
	 */
	public static String getNoticePopup(String scrnId, String corp_cd, HttpServletRequest req) throws HncisException{
		StringBuffer content = new StringBuffer();
		BgabGasc01DtlDto boardDto = null;
		HashMap<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("scrnId", scrnId);
		paramMap.put("corp_cd", corp_cd);

		try{
			boardDto = new BgabGasc01DtlDto();
			boardDto = commonJobDao.getNoticePopup(paramMap);
			if(boardDto != null){
				if(boardDto.getBod_popyn().equals("Y")){
					String strText = "<form id='noticeForm' name='noticeForm'> \n";
					strText += "	<input type='hidden' id='indx' name='indx'> \n";
					strText += "	<input type='hidden' id='title' name='title'> \n";
					strText += "	<input type='hidden' id='content' name='content'> \n";
					strText += "	<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'> \n";
					strText += "	<script> \n";
					strText += "		var noticeWin; \n";
					strText += "		if(getCookie('POP";
					strText += boardDto.getBod_indx();
					strText += "') != 'done') { \n";
					strText += "			noticeWin = newPopWin('about:blank', '450', '350', 'notice_popup'); \n";
					strText += "			document.noticeForm.indx.value    = '";
					strText += boardDto.getBod_indx();
					strText += str_html_template_08;
					strText += "			document.noticeForm.hid_csrfToken.value    = '";
					strText += (String)req.getAttribute("csrfToken");
					strText += str_html_template_08;
					strText += "			document.noticeForm.title.value   = '";
					strText += Base64.serialize(boardDto.getBod_title());
					strText += str_html_template_08;
					strText += "			document.noticeForm.content.value = '";
					strText += Base64.serialize(boardDto.getBod_content());
					strText += str_html_template_08;
					strText += "			document.noticeForm.action = '";
					strText += req.getContextPath();
					strText += "/hncis/popup/notice_popup.gas' \n";
					strText += "			document.noticeForm.target = 'notice_popup'; \n";
					strText += "			document.noticeForm.method = 'post'; \n";
					strText += "			document.noticeForm.submit(); \n";
					strText += "		} \n";
					strText += "	</script> \n";
					strText += "</form>";
					
					content.append(strText);
				}
			}
		}catch(Exception e){
			logger.error(str_message, e);
		}

		return content.toString();
	}

	/**
	 * notice popup
	 */
	public static List<BgabGasc01DtlDto> getSelectMainNotice(String corp_cd) throws HncisException{
		List<BgabGasc01DtlDto> noticeList = null;
		try{
			noticeList = commonJobDao.getSelectMainNoticeList(corp_cd);
		}catch(Exception e){
			logger.error(str_message, e);
		}
		return noticeList;
	}

	/**
	 * qna popup
	 */
	public static List<BgabGasc01DtlDto> getSelectMainQna(String corp_cd) throws HncisException{
		List<BgabGasc01DtlDto> qnaList = null;
		try{
			qnaList = commonJobDao.getSelectMainQnaList(corp_cd);
		}catch(Exception e){
			logger.error(str_message, e);
		}
		return qnaList;
	}

	/**
	 * faq popup
	 */
	public static List<BgabGasc01DtlDto> getSelectMainFaq(String corp_cd) throws HncisException{
		List<BgabGasc01DtlDto> faqList = null;
		try{
			faqList = commonJobDao.getSelectMainFaqList(corp_cd);
		}catch(Exception e){
			logger.error(str_message, e);
		}
		return faqList;
	}

	/**
	 * claim popup
	 */
	public static List<BgabGasc01DtlDto> getSelectMainClaim(String corp_cd) throws HncisException{
		List<BgabGasc01DtlDto> claimList = null;
		try{
			claimList = commonJobDao.getSelectMainClaimList(corp_cd);
		}catch(Exception e){
			logger.error(str_message, e);
		}
		return claimList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getLeftMenu(String firstType, HttpServletRequest req) throws HncisException{
		String result[] = null;
		Vector data = null;

		List<BgabGascz004Dto> selectMenuList = new ArrayList<BgabGascz004Dto>();
		HncisCommon hmcCommon = null;
		try {
			data = new Vector();
			hmcCommon = new HncisCommon();
			hmcCommon.setEeNo(SessionInfo.getSess_empno(req));
			hmcCommon.setWorkArea("1");
			hmcCommon.setWork_auth(SessionInfo.getSess_mstu_gubb(req));
			hmcCommon.setKey(firstType);

			selectMenuList = commonJobDao.getMenuList(hmcCommon);
			for(BgabGascz004Dto targetBean : selectMenuList){
				result = new String[6];
				result[0] = StringUtil.isNullTrim(targetBean.getMenu_lgrp_cd()).toUpperCase();
				result[1] = StringUtil.isNullTrim(targetBean.getMenu_mgrp_cd());
				result[2] = StringUtil.isNullTrim(targetBean.getMenu_sgrp_cd());
				result[3] = targetBean.getScrn_id().trim().equals("") ? "" : StringUtil.isNullTrim(targetBean.getScrn_id().substring(1,3)).toUpperCase();
				result[4] = StringUtil.isNullTrim(targetBean.getScrn_nm());
				result[5] = StringUtil.isNullTrim(targetBean.getMenu_img_path_adr());
				data.addElement(result);
			}
		}catch(Exception e){
			logger.error(str_message, e);
		}

		return data;
	}

	/**
	 * MyApproval Count
	 */
	public static int getSelectMyApproval(String eeno, String corp_cd, HttpServletRequest req) throws HncisException{

		CommonApproval approval = new CommonApproval();
		approval.setRdcs_eeno(eeno);
		approval.setCorp_cd(corp_cd);
		approval.setLocale(req.getSession().getAttribute(reqLocale).toString());

		return systemDao.getSelectCountToMyApproval(approval);
	}
	/**
	 * Search Background Image
	 */
	public static String getSelectBackgroundImage(String corp_cd) throws HncisException{
		return systemDao.getSelectBackgroundImage(corp_cd);
	}

	/**
	 * Search Logo Image
	 */
	public static BgabGascz033Dto getSelectLogoImage(String corp_cd) throws HncisException{
		BgabGascz033Dto param = new BgabGascz033Dto();
		param.setCorp_cd(corp_cd);
		return systemDao.selectLogoMngrToFile(param);
	}

	public static String setSessionFromEmail(HttpServletRequest req, String eeno) throws HncisException{

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(com.hncis.common.util.Base64.decode(eeno));
		BgabGascz002Dto xocxusrInfo = commonJobDao.getXusrInfo(bgabGascz002Dto);

		String returnVal = "";

		if(xocxusrInfo != null){
			Map<String, BgabGascz002Dto> map = new HashMap<String,BgabGascz002Dto>();
			xocxusrInfo.setXusr_token_key(StringUtil.getDocNo());
			map.put(Constant.SESSION_USER_INFO , xocxusrInfo);

			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(Constant.SESSION_INTERVAL_TIME);
			session.setAttribute(Constant.SESSION_USER_INFO, map);

			returnVal = SessionInfo.getSess_token_key(req);
		}else{
			returnVal = "N";
		}

		return returnVal;
	}

	/**
	 * 화면 상단 타이틀 가져오기(Approval Info 에 결재상태 보이게함 - header_info,버튼 제외)
	 * 작성 날짜: (2011-04-07)
	 * @return String
	 * @param userId 		java.lang.String 						사용자 ID
	 * @param screenId	 	java.lang.String 						화면 ID
	 * @param gubun1		java.lang.String 						결재_발신(1@2@3....@13)
	 * @param gubun2		java.lang.String 						결재_접수(1@2)
	 * @param req	 		javax.servlet.http.HttpServletRequest 	request
	 */
	public static String getTitleAndNew(String userId, String screenId, String gubun1, String gubun2, String gubun3, HttpServletRequest req) throws HncisException{

		HncisCommon hmcCommon = new HncisCommon();
		hmcCommon.setEeNo(userId);
		hmcCommon.setScrn_id(screenId);
		hmcCommon.setXcod_code(screenId.substring(1, 3));
		hmcCommon.setLocale(req.getSession().getAttribute(reqLocale).toString());
		StringBuffer data       = new StringBuffer(256);

		String arrGubun1 = gubun1 == null || gubun1.equals("") ? "" : gubun1;
		String arrGubun2 = gubun2 == null || gubun2.equals("") ? "" : gubun2;
		String arrGubun3 = gubun3 == null || gubun3.equals("") ? "" : gubun3;

		//String lgrp_nm = "";
		String mgrp_nm = "";
		String screenName = "";
		String menuInfo = "";
		int sndType = 0;

		String imgPath = "";
		
		String appendStr = "";

		try {
			HncisCommon scrnInfo = commonJobDao.getScrnInfo(hmcCommon);
			if(scrnInfo != null){
				imgPath    = "<img src='../../images/sub_title/"+screenId.toLowerCase()+".jpg' alt='"+screenName+"'/>";
				//lgrp_nm    = scrnInfo.getMenu_lgrp_nm();
				mgrp_nm    = scrnInfo.getMenu_mgrp_nm();
				screenName = scrnInfo.getScrn_nm().trim();
				//menuInfo   = lgrp_nm + " > " + mgrp_nm + " > " + screenName;
				menuInfo   = mgrp_nm + " > " + screenName;
				try {
					sndType = Integer.parseInt(scrnInfo.getMenu_mgrp_cd().trim());
				} catch(Exception ee) {
					sndType = -1;
					screenName = "ERROR";
				}
			}else{
				screenName = "미등록 화면";
				imgPath    = screenName;
				sndType    = -1;
			}

			CommonApproval approval = new CommonApproval();
			approval.setRdcs_eeno(userId);

			hmcCommon.setSndType(sndType);
			screenName = screenName.replace("<BR>", "");

			appendStr += "	<div id='header_wrap'> \n";
			appendStr += "		<div class='sub_title' style='width:99%'> \n";
			appendStr += "			<div class='con_title'><h3>";
			appendStr += imgPath;
			appendStr += "</h3></div> \n";
			appendStr += "			<div class='con_navi'>";
			appendStr += menuInfo;
			appendStr += str_html_template_05;
			appendStr += "		</div> \n";
			appendStr += str_html_template_04;

			appendStr += "<div id='contents'> \n";

			if(!arrGubun1.equals("") && arrGubun3.equals("")){
				appendStr += "<div id='appBox' style='width:99%'> \n";
				appendStr += "	<div class='appBox_left'> \n";
				appendStr += "		<table> \n";
				appendStr += str_html_template_01;
				for (int i = 0; i < 13; i++){
					if (i < Integer.parseInt(arrGubun1)){
						appendStr += "		<th id='SUBMIT_TITLE'>&nbsp;</th> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_01;
				for (int i = 0; i < 13; i++){
					if (i < Integer.parseInt(arrGubun1)){
						appendStr += "		<td id='SUBMIT_DATA'>&nbsp;</td> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_03;
				appendStr += str_html_template_04;
			}

			if(!arrGubun2.equals("") && arrGubun3.equals("")){
				appendStr += "	<div class='appBox_right' align='right'> \n";
				appendStr += "		<table> \n";
				appendStr += "			<tr> \n";
				for(int i = 0; i < 13; i++){
					if (i < Integer.parseInt(arrGubun2)){
						appendStr += "		<th id='SUBMIT_TITLE'>&nbsp;</th> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_01;
				for(int i = 0; i < 13; i++){
					if (i < Integer.parseInt(arrGubun2)){
						appendStr += "		<td id='SUBMIT_DATA'>&nbsp;</td> \n";
					}
				}
				appendStr += str_html_template_02;
				appendStr += str_html_template_03;
				appendStr += str_html_template_04;
				appendStr += str_html_template_05;
			}
			data.append(appendStr);

		}catch (Exception e){
			logger.error(str_message, e);
		}
		return data.toString();
	}

	/**
	 * 업무권한 가져오기
	 * @param userId 		java.lang.String 			사용자 ID
	*/
	public static int getWorkAuth(String userId) throws HncisException{
		int sndType = 5;
		int workAuth = 0;
		HncisCommon hmcCommon = new HncisCommon();
		hmcCommon.setSndType(sndType);
		hmcCommon.setEeNo(userId);
		try{
			workAuth = Integer.parseInt(commonJobDao.getAuthority(hmcCommon).trim());
		}catch (Exception ee) {
			workAuth = 0;
		}
		return workAuth;
	}

	public static List<BgabGascz004Dto> getTopMenu(HttpServletRequest req) throws HncisException{
		List<BgabGascz004Dto> selectMenuList = new ArrayList<BgabGascz004Dto>();
		HncisCommon hmcCommon = null;
		try {
			String strCorpCd = SessionInfo.getSess_corp_cd(req);
			String strAdmCorpCd = SessionInfo.getSess_adm_corp_cd(req);
			hmcCommon = new HncisCommon();
			hmcCommon.setEeNo(SessionInfo.getSess_empno(req));
			hmcCommon.setCorp_cd(strCorpCd);
			hmcCommon.setAdm_corp_cd(strAdmCorpCd);
			hmcCommon.setWorkArea("1");
			hmcCommon.setWork_auth(SessionInfo.getSess_mstu_gubb(req));
			hmcCommon.setLocale(req.getSession().getAttribute(reqLocale).toString());

			BgabGascz030Dto dto2 = new BgabGascz030Dto();
			dto2.setCorp_cd(strCorpCd);
			BgabGascz030Dto rtnDto = new BgabGascz030Dto();
			if(!"".equals(strCorpCd)){
				rtnDto = commonJobDao.selectXst30Info(dto2);
			}

			String menuMgrpCd = "";

			if("Y".equals(rtnDto.getTask01())){			//휴양소
				menuMgrpCd += ",001";
//				this.createBgabGascRC06(map);
			}
			if("Y".equals(rtnDto.getTask02())){	//근무복
				menuMgrpCd += ",002";
//				this.createBgabGascAF05(map);
			}
			if("Y".equals(rtnDto.getTask03())){	//선물
				menuMgrpCd += ",003";
//				this.createBgabGascGFExcelTemp(map);
			}
			if("Y".equals(rtnDto.getTask04())){	//도서
				menuMgrpCd += ",004";
//				this.createBgabGascBKExcelTemp(map);
			}
			if("Y".equals(rtnDto.getTask05())){	//교육신청
				menuMgrpCd += ",007";
//				this.createBgabGascTR01(map);
			}
			if("Y".equals(rtnDto.getTask06())){	//명함
				menuMgrpCd += ",009";
//				this.createBgabGascBA03(map);
			}
			if("Y".equals(rtnDto.getTask07())){	//전산용품
				menuMgrpCd += ",010";
//				this.createBgabGascGS04(map);
			}
			if("Y".equals(rtnDto.getTask08())){	//사무용품
				menuMgrpCd += ",012";
//				this.createBgabGascOS04(map);
			}
			if("Y".equals(rtnDto.getTask09())){	//출장
				menuMgrpCd += ",014";
//				this.createBgabGascBT09(map);
			}
			if("Y".equals(rtnDto.getTask10())){	//픽업
				menuMgrpCd += ",016";
//				this.createBgabGascPS04(map);
			}
			if("Y".equals(rtnDto.getTask11())){	//교통비
				menuMgrpCd += ",17";
//				this.createBgabGascTX04(map);
			}
			if("Y".equals(rtnDto.getTask12())){	//차량신청 && 차량관리
				menuMgrpCd += ",018,019";
//				this.createBgabGascBV04(map);
			}
			if("Y".equals(rtnDto.getTask13())){	//주유비
				menuMgrpCd += ",020";
//				this.createBgabGascFC02(map);
			}
			if("Y".equals(rtnDto.getTask14())){	//운행일지
				menuMgrpCd += ",021";
//				this.createBgabGascVL01(map);
			}
			if("Y".equals(rtnDto.getTask15())){	//방문
				menuMgrpCd += ",008";
//				this.createBgabGascSE07(map);
			}
			if("Y".equals(rtnDto.getTask16())){	//회의실
				menuMgrpCd += ",011";
//				this.createBgabGascRM04(map);
			}
			if("Y".equals(rtnDto.getTask17())){	//증명서
				menuMgrpCd += ",013";
//				this.createBgabGascCE01(map);
			}
			if("Y".equals(rtnDto.getTask18())){	//연차
				menuMgrpCd += ",022";
//				this.createBgabGascLV03(map);
			}

			if(!"".equals(menuMgrpCd)){
				menuMgrpCd = menuMgrpCd.substring(1);
				hmcCommon.setMenu_auth_arr(menuMgrpCd.split(","));
			}

			if("admin".equals(strAdmCorpCd)){
				selectMenuList = commonJobDao.getTopMenuList1(hmcCommon);
			}else{
				selectMenuList = commonJobDao.getTopMenuList(hmcCommon);
			}
		}catch(Exception e){
			logger.error(str_message, e);
		}

		return selectMenuList;
	}

	public static List<BgabGascz004Dto> getBusinessTravelTopMenu(HttpServletRequest req) throws HncisException{
		List<BgabGascz004Dto> selectMenuList = new ArrayList<BgabGascz004Dto>();
		try {
			selectMenuList = commonJobDao.getSelectBusinessTravelTopMenuList();
		}catch(Exception e){
			logger.error(str_message, e);
		}

		return selectMenuList;
	}

	public static List<BgabGascz004Dto> getLeftMenuList(String menuId, HttpServletRequest req) throws HncisException{
		List<BgabGascz004Dto> selectMenuList = new ArrayList<BgabGascz004Dto>();
		HncisCommon hmcCommon = null;
		try {
			String strCorpCd = SessionInfo.getSess_corp_cd(req);
			String strAdmCorpCd = SessionInfo.getSess_adm_corp_cd(req);
			hmcCommon = new HncisCommon();
			hmcCommon.setEeNo(SessionInfo.getSess_empno(req));
			hmcCommon.setCorp_cd(strCorpCd);
			hmcCommon.setAdm_corp_cd(strAdmCorpCd);
			hmcCommon.setWorkArea("1");
			hmcCommon.setWork_auth(SessionInfo.getSess_mstu_gubb(req));
			hmcCommon.setKey(menuId);
			hmcCommon.setBizId(menuId.substring(0, 3));
			hmcCommon.setLocale(req.getSession().getAttribute(reqLocale).toString());

			if("XGS".equals(menuId.substring(0,3))){
				BgabGascz004Dto xgsAuth = commonJobDao.getLeftMenuXgsAuth(hmcCommon);
				if("Y".equals(xgsAuth.getAuth_cd())){
					hmcCommon.setAuthYn("Y");
				}
			}
			if("admin".equals(strAdmCorpCd)){
				selectMenuList = commonJobDao.getLeftMenuList1(hmcCommon);
			}else{
				selectMenuList = commonJobDao.getLeftMenuList(hmcCommon);
			}
		}catch(Exception e){
			logger.error(str_message, e);
		}

		return selectMenuList;
	}

	/**
	 * 화면 상단 타이틀 및 버튼 가져오기(결재란 제외 팝업 용도)
	 * 작성 날짜: (2015-12-10)
	 * @param button		java.lang.String 						image1/function1@image2/function2@...
	 * @param req	 		javax.servlet.http.HttpServletRequest 	request
	 */
	public static String getButtonOnly(String userId, String screenId, String button, HttpServletRequest req) throws HncisException{
		HncisCommon hmcCommon = new HncisCommon();
		hmcCommon.setEeNo(userId);
		hmcCommon.setScrn_id(screenId);
		hmcCommon.setXcod_code(screenId.substring(1, 3));

		int workAuth = 0;
		int cnfm = 0;
		int idx = 0;
		boolean flag = false;

		StringTokenizer st = null;
		StringTokenizer st2 = null;
		StringBuffer data       = new StringBuffer(200);
		String tempButton = "";
		String buttonName = "";
		String buttonFunction = "";
		String display = "";
		
		String appendStr = "";

		try {
			try{
				workAuth = Integer.parseInt(commonJobDao.getAuthority(hmcCommon).trim());
				cnfm = Integer.parseInt(SessionInfo.getSess_cnfm_auth(req).trim());
			}catch (Exception ee) {
				workAuth = 0;
				cnfm = 1;
			}

			appendStr += "	<div class='btn_area'> \n";
			appendStr += "		<ul class='btns'> \n";

			st = new StringTokenizer(button);
			while(st.hasMoreElements()){
				tempButton = st.nextToken("@");

				idx = 0;
				st2 = new StringTokenizer(tempButton);
				while(st2.hasMoreElements()){
					if (idx==0){
						buttonName = st2.nextToken("/").trim();
						//buttonName = buttonName.toLowerCase();
						idx++;
					} else {
						buttonFunction = st2.nextToken("/").trim();
					}
				}

				flag = false;
				if (buttonFunction.trim().equals("close")){
					flag = true;
				} else {
					if (buttonFunction.equals(str_excel)){
						if (workAuth >= 1){flag = true;}
					} else if (buttonFunction.equals("search")){
						if (workAuth >= 1){flag = true;	}
					} else if (buttonFunction.equals("print")){
						if (workAuth >= 1){flag = true;	}
					} else if (buttonFunction.equals("back")){
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth == 5){
							flag = true;
						}
					} else if (buttonFunction.equals("cancel")){
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("insert")||buttonFunction.equals("apply")||buttonFunction.equals("write")||buttonFunction.equals("save")||buttonFunction.equals("request")||buttonFunction.equals("requestCancel")){
						if (workAuth >= 2){flag = true;}
					} else if (buttonFunction.equals("update")||buttonFunction.equals("modify")||buttonFunction.equals("edit")){
						if (workAuth >= 3){flag = true;}
					} else if (buttonFunction.equals("delete")||buttonFunction.equals("applycancel")){
						if (workAuth >= 4){flag = true;}
					} else if (buttonFunction.equals("upload")||buttonFunction.equals("sendmail")||buttonFunction.equals("reply")){
						if (workAuth >= 4){flag = true;}
					} else if (buttonFunction.equals("approve")||buttonFunction.equals("approveCancel")||buttonFunction.equals("list")){
						flag = true;
					} else if (buttonFunction.equals("report")||buttonFunction.equals("reportCancel")){
						flag = true;
					} else if (buttonFunction.equals("receive")||buttonFunction.equals("receive_cancel")||buttonFunction.equals("reject")){
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm")||buttonFunction.equals("confirmCancel")||buttonFunction.equals("issue")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm1")||buttonFunction.equals("confirmCancel1")||buttonFunction.equals("order")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth == 5){
							flag = true;
						}
					} else if (buttonFunction.equals("confirm2")||buttonFunction.equals("confirmCancel2")) {
						//명함 최종 결제자 특정 사번만 보이게 세팅
						if (SessionInfo.getSess_empno(req).equals("18104243")||SessionInfo.getSess_mstu_gubb(req).equals("M")){
							flag = true;
						}
					}else if (buttonFunction.equals("confirm3")||buttonFunction.equals("confirmCancel3")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					}else if (buttonFunction.equals("forceConfirm")||buttonFunction.equals("sapExport")||buttonFunction.equals("voucherExcel")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M") || workAuth >= 5){
							flag = true;
						}
					}else if (buttonFunction.equals("write1")||buttonFunction.equals("edit1")||buttonFunction.equals("delete1")) {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M")){
							flag = true;
						}
					}else {
						if (SessionInfo.getSess_mstu_gubb(req).equals("M")){flag = true;}
						if (workAuth == 5){flag = true;}
					}
				}
				if (flag){
					display = "";
				} else {
					display = " style=\"display:none;\"";
				}

				String btnVal = buttonName;
				btnVal = buttonName.substring(0,1).toUpperCase() +  buttonName.substring(1);

				if (buttonFunction.equals(str_excel)){
					appendStr += "	<li id='";
					appendStr += buttonFunction;
					appendStr += "'><a href='javascript:retrieve(\"";
					appendStr += buttonFunction;
					appendStr += str_html_template_06;
					appendStr += btnVal;
					appendStr += str_html_template_07;
				} else {
					appendStr += "		<li id='";
					appendStr += buttonFunction;
					appendStr += "'><a href='javascript:retrieve(\"";
					appendStr += buttonFunction;
					appendStr += str_html_template_06;
					appendStr += btnVal;
					appendStr += str_html_template_07;
				}
			}

			appendStr += "	</ul> \n";
			appendStr += str_html_template_05;
			appendStr += "<input type='hidden' id='work_auth' value='";
			appendStr += workAuth;
			appendStr += "'>";
			//data.append("<script>jQuery(document).ready(function(){$('#"+screenId.substring(1,3).toUpperCase()+"').slideDown('fast');})</script>");
			data.append(appendStr);
		}catch (Exception e){
			logger.error(str_message, e);
		}
		return data.toString();
	}

	public static int getCheckAuthKeyForPw(String corp_cd, String id, String authKey) throws HncisException{
		int cnt = 0;
		try {

			BgabGascz002Dto dto = new BgabGascz002Dto();
			dto.setXusr_empno(id);
			dto.setXusr_pswd_key(authKey);
			dto.setCorp_cd(corp_cd);

			cnt = commonJobDao.getCheckAuthKeyForPw(dto);

		}catch(Exception e){
			logger.error(str_message, e);
		}

		return cnt;
	}

	public static String getApproveStepLevel(String screenId, HttpServletRequest req) throws HncisException{
		String approveStepLevel = "";
		try {

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			commonApproval.setSystem_code(screenId.substring(1,3));

			approveStepLevel = commonJobDao.selectApproveStepLevel(commonApproval);
		}catch(Exception e){
			logger.error(str_message, e);
			approveStepLevel = "";
		}

		return approveStepLevel;
	}

	public static String getSelectCorpChkCount(String corpCd) throws HncisException{
		String chkFlag = "N";
		try {
			BgabGascz030Dto dto = new BgabGascz030Dto();
			dto.setCorp_cd(corpCd);

			chkFlag = commonJobDao.getSelectCorpChkCount(dto);
		}catch(Exception e){
			logger.error(str_message, e);
			chkFlag = "N";
		}

		return chkFlag;
	}
	
	public static String getToDay(){
		String currYmd = CurrentDateTime.getDate();
		String yy = currYmd.substring(0, 4);
		String mm = currYmd.substring(4, 6);
		String dd = currYmd.substring(6, 8);

		return yy + "-" + mm + "-" + dd;		
	}
	
	public static String getSelectLoc(String corp_cd) throws HncisException{
		String locale = "";
		
		locale = (String)commonJobDao.getSelectLoc(corp_cd);
		
		return locale;
	}
	
	public static String getLocale(String corp_cd) throws HncisException{
		String locale="";
		
		locale = commonJobDao.getLocale(corp_cd);
		
		return locale;
	}

	public static String getUserLocale(String xusrEmpno, String corp_cd) throws HncisException{
		String locale="";
		
		BgabGascz002Dto dto = new BgabGascz002Dto();
		dto.setCorp_cd(corp_cd);
		dto.setXusr_empno(xusrEmpno);
		
		locale = commonJobDao.getUserLocale(dto);
		
		return locale;
	}
	
	public static List<BpmInfo> getSelectBpmList(HttpServletRequest req) throws HncisException, SessionException{

		BpmInfo vo = new BpmInfo();
		vo.setEmpno1(SessionInfo.getSess_empno(req));
		vo.setEmpno2(SessionInfo.getSess_empno(req));
		vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		List<BpmInfo> list = commonJobDao.getSelectBpmList(vo);

		return list;
	}
}
