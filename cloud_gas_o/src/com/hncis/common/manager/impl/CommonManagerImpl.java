package com.hncis.common.manager.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hncis.books.dao.BooksDao;
import com.hncis.books.vo.BgabGascbkExcelTempDto;
import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.Base64;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.ReadExcelPoi;
import com.hncis.common.util.SHA256Util;
import com.hncis.common.util.SendMail;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.VoUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.BgabGascz034Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.common.vo.HncisCommon;
import com.hncis.gift.dao.GiftDao;
import com.hncis.gift.vo.BgabGascgfExcelTempDto;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz008Dto;
import com.hncis.system.vo.BgabGascz017Dto;
import com.hncis.system.vo.BgabGascz019Dto;
import com.hncis.system.vo.BgabGascz020Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz031Dto;
import com.hncis.system.vo.BgabGascz035Dto;

@Service("commonManagerImpl")
public class CommonManagerImpl  implements CommonManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String str1 = "b_ptt_ymd";
    private static final String str2 = "b_menu_type";
    private static final String str3 = "b_menu_nm";
    private static final String str4 = "k_ptt_ymd";
    private static final String str5 = "k_menu_type";
    private static final String str6 = "k_menu_knd";
    private static final String str7 = "k_menu_ko_nm";
    private static final String str8 = "k_menu_en_nm";
    private static final String str9 = "k_menu_po_nm";
    private static final String str10 = "xusr_empno";
    private static final String str11 = "xorg_orga_c";
    private static final String str12 = "lrg_cd";
    private static final String str13 = "lrg_nm";
    private static final String str14 = "lrg_sort";
    private static final String str15 = "mrg_cd";
    private static final String str16 = "mrg_nm";
    private static final String str17 = "mrg_sort";
    private static final String str18 = "false";
    private static final String str19 = "EXCEL.0001";
    private static final String str20 = "EXCEL.0004";
    private static final String str21 = "admin";

	@Autowired
	public BooksDao booksDao;

	@Autowired
	public GiftDao giftDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Autowired
	public SystemDao systemDao;

	@Override
	public List<CommonCode> getCodeList(CommonCode commonCode){
		return commonJobDao.getCodeList(commonCode);
	}

	@Override
	public List<CommonCode> getCodeListNotIncludeAply(CommonCode commonCode) {
		// TODO Auto-generated method stub
		return commonJobDao.getCodeListNotIncludeAply(commonCode);
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONBaseArray getDataCombo(CommonCode code){

 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = new JSONBaseArray();

		List<CommonCode> codeList = commonJobDao.getDataComboList(code);

		if(StringUtil.isNullToString(code.getComboType()).equals("S")){
			json = new JSONBaseVO();
			json.put("name", HncisMessageSource.getMessage("select"));
			json.put("value", "");

			jsonArr.add(json);
		}
		else if(StringUtil.isNullToString(code.getComboType()).equals("A")){
			json = new JSONBaseVO();
			json.put("name", HncisMessageSource.getMessage("total"));
			json.put("value", "");

			jsonArr.add(json);
		}

		for(CommonCode targetBean : codeList){
			json = new JSONBaseVO();

			json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

			jsonArr.add(json);
		}

		return jsonArr;
	}


	@Override
	public BgabGascz002Dto getXusrInfo(BgabGascz002Dto bgabGascz002Dto){
		logger.info("getXusrInfo Debug01");
		return commonJobDao.getXusrInfo(bgabGascz002Dto);
	}

	@Override
	public void setSessionInfo(HttpServletRequest req, BgabGascz002Dto bgabGascz002Dto){

		Map<String, BgabGascz002Dto> map = new HashMap<String,BgabGascz002Dto>();
		bgabGascz002Dto.setXusr_token_key(StringUtil.getDocNo());
		map.put(Constant.SESSION_USER_INFO , bgabGascz002Dto);
		
		Locale userLocale = new Locale(StringUtil.isNullToString(bgabGascz002Dto.getLocale(), "ko"));
		

		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(Constant.SESSION_INTERVAL_TIME);
		session.setAttribute(Constant.SESSION_USER_KEY, map);
		session.setAttribute("reqLocale", userLocale);
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, userLocale);
		
		logger.info(session.getId());
		logger.info(session.getAttribute(Constant.SESSION_USER_KEY));

		/*
		SessionUserInfo result = new SessionUserInfo();

		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(120*60);

		result.setSess_empno(bgabGascz002Dto.getXusr_empno());					//작업자 사번
		result.setSess_hname(bgabGascz002Dto.getXusr_name());  					//작업자 성명
		result.setSess_cntl_gubb(bgabGascz002Dto.getXusr_dept_knd());			//통제부서/신청부서(1,2)
		result.setSess_dept_code(bgabGascz002Dto.getXusr_dept_code());			//작업자 소속
		result.setSess_dept_hname(bgabGascz002Dto.getXusr_dept_name());			//작업자 소속명
		result.setSess_mstu_gubb(bgabGascz002Dto.getXusr_auth_knd());			//MASTER/USER구분 (M,U)
		result.setSess_plac_gasc(bgabGascz002Dto.getXusr_plac_work());			//작업자 근무지(총무) <--- 결재권 정보 1:일반사용자 2:결재자
		result.setSess_cnfm_auth(bgabGascz002Dto.getXusr_cnfm_auth());			//결재자레벨
		result.setSess_step_code(bgabGascz002Dto.getXusr_step_code());			//직위코드
		result.setSess_step_name(bgabGascz002Dto.getXusr_step_name());			//직위명

		session.setAttribute("SessionUserInfo", result);
		*/

	}

	@Override
	public HncisCommon getScrnInfo(HncisCommon hncisCommon){
		return commonJobDao.getScrnInfo(hncisCommon);
	}

	@Override
	public CommonUserInfo getSelectUserInfo(CommonUserInfo commonUserInfo){
		return commonJobDao.getSelectUserInfo(commonUserInfo);
	}

	@Override
	public List<CommonUserInfo> getSelectUserInfoList(CommonUserInfo commonUserInfo){
		return commonJobDao.getSelectUserInfoList(commonUserInfo);
	}



	@Override
	public BgabGascz002Dto getSelectUserInfoDetail(BgabGascz002Dto bgabGascz002Dto){
		return commonJobDao.getSelectUserInfoDetail(bgabGascz002Dto);
	}

	@Override
	public BgabGascz003Dto getSelectDeptInfo(BgabGascz003Dto xocxorgDto){
		return commonJobDao.getSelectDeptInfo(xocxorgDto);
	}

	/**
	 * 달력
	 * @throws SessionException 
	 */
	@Override
	public String[] getCalendar(String ymd, String oduRegnCd, HttpServletRequest req, HttpServletResponse res) throws SessionException{
		List<String> resultList = null;
		HashMap<String,String> paramMap = new HashMap<String, String>();
		String returnValue = "";
		String[] rtn = new String[2];

		paramMap.put("odu_regn_cd", oduRegnCd);
		paramMap.put("ymd", ymd);
		String strCorpCd = SessionInfo.getSess_corp_cd(req);
		paramMap.put("corp_cd", strCorpCd);
		resultList = commonJobDao.selectCalendarHolyList(paramMap);

		for(String result : resultList){
			returnValue += result+",";
		}
		if(resultList != null){
			rtn[0] = returnValue;
			rtn[1] = "Y";
		}else{
			rtn[0] = "";
			rtn[1] = "N";
		}

		return rtn;
	}
	@Override
	public String getSelectApprovalInfo(CommonApproval apprVo){
		return commonJobDao.getSelectApprovalInfo(apprVo);
	}
	@Override
	public int getSelectCountToCodeManagement(BgabGascz005Dto dto){
		return Integer.parseInt(commonJobDao.getSelectCountToCodeManagement(dto));
	}
	@Override
	public List<BgabGascz005Dto> getSelectListToCodeManagement(BgabGascz005Dto dto){
		return commonJobDao.getSelectListToCodeManagement(dto);
	}

	@Override
	public List<BgabGascz005Dto> getSelectListToCodeManagementOrderBySort(BgabGascz005Dto dto){
		return commonJobDao.getSelectListToCodeManagementOrderBySort(dto);
	}

	@Override
	public int insertListToCodeManagement(List<BgabGascz005Dto> list){
		return commonJobDao.insertListToCodeManagement(list);
	}

	@Override
	public int insertListToCodeManagementAutoSeq(List<BgabGascz005Dto> list){
		return commonJobDao.insertListToCodeManagementAutoSeq(list);
	}

	@Override
	public int updateListToCodeManagement(List<BgabGascz005Dto> list){
		return commonJobDao.updateListToCodeManagement(list);
	}
	@Override
	public int deleteListToCodeManagement(List<BgabGascz005Dto> list){
		return commonJobDao.deleteListToCodeManagement(list);
	}

	@Override
	public int getSelectCountDeptCodeByCommon(HncisCommon dto){
		return commonJobDao.getSelectCountDeptCodeByCommon(dto);
	}
	@Override
	public List<HncisCommon> getSelectDeptCodeByCommon(HncisCommon dto){
		return commonJobDao.getSelectDeptCodeByCommon(dto);
	}

	@Override
	public int getSelectCountZipCode(BgabGascz034Dto dto){
		return commonJobDao.getSelectCountZipCode(dto);
	}
	@Override
	public List<BgabGascz034Dto> getSelectZipCode(BgabGascz034Dto dto){
		return commonJobDao.getSelectZipCode(dto);
	}

	@Override
	public int getSelectCountJobMgmtInfo(BgabGascz007Dto dto){
		return commonJobDao.getSelectCountJobMgmtInfo(dto);
	}
	@Override
	public List<BgabGascz007Dto> getSelectJobMgmtInfo(BgabGascz007Dto dto){
		return commonJobDao.getSelectJobMgmtInfo(dto);
	}
	@Override
	public List<BgabGascz004Dto> selectGNBMenues() {
		return commonJobDao.selectGNBMenues();
	}

	@Override
	public List<BgabGascz004Dto> selectMenuComboList(){
		return commonJobDao.selectMenuComboList();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveExcelUpload(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto) throws SessionException{
		String msg        = "";
		String resultUrl  = "pop_excelUpload.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		String strCorpCd = SessionInfo.getSess_corp_cd(req);

		CommonMessage message = new CommonMessage();

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "";
			paramVal[2] = "temp";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(result[0])));

					ReadExcelPoi readExcel = new ReadExcelPoi();

					if("xls".equals(ext) || "xlsx".equals(ext)){
						String[] excelKeyArr = null;
						if("XST19A".equals(bgabGascZ011Dto.getUploadType())){
							excelKeyArr = new String[3];
							excelKeyArr[0] = str1;
							excelKeyArr[1] = str2;
							excelKeyArr[2] = str3;
						}else if("XST19B".equals(bgabGascZ011Dto.getUploadType())){
							excelKeyArr = new String[6];
							excelKeyArr[0] = str4;
							excelKeyArr[1] = str5;
							excelKeyArr[2] = str6;
							excelKeyArr[3] = str7;
							excelKeyArr[4] = str8;
							excelKeyArr[5] = str9;
						}else if("XST01A".equals(bgabGascZ011Dto.getUploadType())){
							excelKeyArr = new String[17];
							excelKeyArr[0] = str10;
							excelKeyArr[1] = "xusr_auth_knd";
							excelKeyArr[2] = "xusr_name";
							excelKeyArr[3] = "xusr_en_name";
							excelKeyArr[4] = "xusr_dept_code";
							excelKeyArr[5] = "xusr_dept_name";
							excelKeyArr[6] = "xusr_step_code";
							excelKeyArr[7] = "xusr_step_name";
							excelKeyArr[8] = "xusr_mail_adr";
							excelKeyArr[9] = "xusr_aply_date";
							excelKeyArr[10] = "xusr_retr_flag";
							excelKeyArr[11] = "xusr_tel_no";
							excelKeyArr[12] = "xusr_work_phone_no";
							excelKeyArr[13] = "xusr_postal_code";
							excelKeyArr[14] = "xusr_addr";
							excelKeyArr[15] = "xusr_sex";
							excelKeyArr[16] = "xusr_loc";
						}else if("XST01B".equals(bgabGascZ011Dto.getUploadType())){
							excelKeyArr = new String[9];
							excelKeyArr[0]  = str11;
							excelKeyArr[1]  = "xorg_orga_e";
							excelKeyArr[2]  = "xorg_orga_csner";
							excelKeyArr[3]  = "xorg_clos_d";
							excelKeyArr[4]  = "xorg_rsps_i";
							excelKeyArr[5]  = "xorg_rsps_m";
							excelKeyArr[6]  = "xorg_rsps_crank";
							excelKeyArr[7]  = "xorg_rsps_mrank";
							excelKeyArr[8] = "xorg_dept_lv";
						}else if("XBK01".equals(bgabGascZ011Dto.getUploadType()) || "XGF01".equals(bgabGascZ011Dto.getUploadType())){
							excelKeyArr = new String[6];
							excelKeyArr[0]  = str12;
							excelKeyArr[1]  = str13;
							excelKeyArr[2]  = str14;
							excelKeyArr[3]  = str15;
							excelKeyArr[4]  = str16;
							excelKeyArr[5]  = str17;
						}

						readExcel.setInputFile(result[2] + "/" + result[0]);
						List<Map<String, String>> excelData = new ArrayList<Map<String, String>>();

						if("xls".equals(ext)){
							excelData = readExcel.getExcelXlsData(excelKeyArr);
						}else{
							excelData = readExcel.getExcelXlsxData(excelKeyArr);
						}

						File delFile = new File(result[2] + "/" + result[0]);

						if(delFile.exists()){
							delFile.delete();
						}

						if(excelData.size() > 0){
							int errCnt = 0;
							if("XST19A".equals(bgabGascZ011Dto.getUploadType())){
								List<BgabGascz019Dto> iXst19AList = new ArrayList<BgabGascz019Dto>();
								BgabGascz019Dto       iXst19AData = new BgabGascz019Dto();
								for(Map<String, String> excel : excelData){
									iXst19AData = new BgabGascz019Dto();
									if(StringUtil.isEmpty(excel.get(str1))){
										errCnt++;
										break;
									}else{
										if(excel.get(str1).length() == 8){
											iXst19AData.setB_ptt_ymd(excel.get(str1).substring(4)+""+excel.get(str1).substring(2,4)+""+excel.get(str1).substring(0,2));
										}else{
											errCnt++;
											break;
										}
									}
									if(StringUtil.isEmpty(excel.get(str2))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str2))){
											iXst19AData.setB_menu_type(excel.get(str2));
										}
									}
									if(StringUtil.isEmpty(excel.get(str3))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str3))){
											iXst19AData.setB_menu_nm(excel.get(str3));
										}
									}
									iXst19AList.add(iXst19AData);
								}

//								if(errCnt == 0){
									systemDao.doInsertByBrasilanMenu(iXst19AList);
//								}else{
//									msg = HmbMessageSource.getMessage("EXCEL.0002");
//								}
							}else if("XST19B".equals(bgabGascZ011Dto.getUploadType())){
								List<BgabGascz019Dto> iXst19BList = new ArrayList<BgabGascz019Dto>();
								BgabGascz019Dto       iXst19BData = new BgabGascz019Dto();
								for(Map<String, String> excel : excelData){
									iXst19BData = new BgabGascz019Dto();
									if(StringUtil.isEmpty(excel.get(str4))){
										errCnt++;
										break;
									}else{
										if(excel.get(str4).length() == 8){
											iXst19BData.setK_ptt_ymd(excel.get(str4).substring(4)+""+excel.get(str4).substring(2,4)+""+excel.get(str4).substring(0,2));
										}else{
											errCnt++;
											break;
										}
									}
									if(StringUtil.isEmpty(excel.get(str5))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str5))){
											iXst19BData.setK_menu_type(excel.get(str5));
										}
									}
									if(StringUtil.isEmpty(excel.get(str6))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str6))){
											iXst19BData.setK_menu_knd   (excel.get(str6));
										}
									}
									if(StringUtil.isEmpty(excel.get(str7))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str7))){
											iXst19BData.setK_menu_ko_nm   (excel.get(str7));
											logger.info("iXst19BData.setK_menu_ko_nm : " + iXst19BData.getK_menu_ko_nm() );
										}
									}
									if(StringUtil.isEmpty(excel.get(str8))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str8))){
											iXst19BData.setK_menu_en_nm   (excel.get(str8));
										}
									}
									if(StringUtil.isEmpty(excel.get(str9))){
										errCnt++;
										break;
									}else{
										if(!str18.equals(excel.get(str9))){
											iXst19BData.setK_menu_po_nm   (excel.get(str9));
										}
									}
									iXst19BList.add(iXst19BData);
								}
//								if(errCnt == 0){
									systemDao.doInsertByKoreanMenu(iXst19BList);
//								}else{
//									msg = HmbMessageSource.getMessage("EXCEL.0002");
//								}
							}else if("XST01A".equals(bgabGascZ011Dto.getUploadType())){
								List<BgabGascz002Dto> iXst01AList = new ArrayList<BgabGascz002Dto>();
								String empNo = SessionInfo.getSess_empno(req);
								String corpCd = SessionInfo.getSess_corp_cd(req);

								int dataCnt = 0;
								for(Map<String, String> excel : excelData){

									if(!StringUtil.isNullToString(excel.get(str10)).equals("") && !StringUtil.isNullToString(excel.get(str10)).equals(str18)){
										if(!StringUtil.isNullToString(excel.get(str10)).equals("")     	  && !StringUtil.isNullToString(excel.get(str10)).equals(str18)    	 &&
										   !StringUtil.isNullToString(excel.get("xusr_auth_knd")).equals("")  	  && !StringUtil.isNullToString(excel.get("xusr_auth_knd")).equals(str18) 	 &&
										   !StringUtil.isNullToString(excel.get("xusr_name")).equals("")      	  && !StringUtil.isNullToString(excel.get("xusr_name")).equals(str18) 		 &&
										   !StringUtil.isNullToString(excel.get("xusr_en_name")).equals("")   	  && !StringUtil.isNullToString(excel.get("xusr_en_name")).equals(str18)  	 &&
										   !StringUtil.isNullToString(excel.get("xusr_dept_code")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_dept_code")).equals(str18)	 &&
										   !StringUtil.isNullToString(excel.get("xusr_dept_name")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_dept_name")).equals(str18) 	 &&
										   !StringUtil.isNullToString(excel.get("xusr_step_code")).equals("")     && !StringUtil.isNullToString(excel.get("xusr_step_code")).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xusr_step_name")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_step_name")).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xusr_mail_adr")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_mail_adr")).equals(str18)      &&
										   !StringUtil.isNullToString(excel.get("xusr_aply_date")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_aply_date")).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xusr_retr_flag")).equals("") 	  && !StringUtil.isNullToString(excel.get("xusr_retr_flag")).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xusr_tel_no")).equals("") 	  	  && !StringUtil.isNullToString(excel.get("xusr_tel_no")).equals(str18)     	 &&
										   !StringUtil.isNullToString(excel.get("xusr_work_phone_no")).equals("") && !StringUtil.isNullToString(excel.get("xusr_work_phone_no")).equals(str18) &&
										   !StringUtil.isNullToString(excel.get("xusr_postal_code")).equals("")   && !StringUtil.isNullToString(excel.get("xusr_postal_code")).equals(str18)   &&
										   !StringUtil.isNullToString(excel.get("xusr_addr")).equals("") 	  	  && !StringUtil.isNullToString(excel.get("xusr_addr")).equals(str18)    		 &&
										   !StringUtil.isNullToString(excel.get("xusr_sex")).equals("") 	  	  && !StringUtil.isNullToString(excel.get("xusr_sex")).equals(str18)			 &&
										   !StringUtil.isNullToString(excel.get("xusr_loc")).equals("") 	  	  	  && !StringUtil.isNullToString(excel.get("xusr_loc")).equals(str18))
										{

											BgabGascz002Dto iXst01AData = new BgabGascz002Dto();
											VoUtil.copyMapToVo(excel, iXst01AData);
											iXst01AData.setXusr_entr_empno(empNo);
											iXst01AData.setXusr_updt_empno(empNo);
											iXst01AData.setXusr_work_auth("44444444444444444444444444444444444444444444444444");
											iXst01AData.setCorp_cd(corpCd);
											//iXst01AData.setXusr_pswd(xusrPswd)
											iXst01AData.setXusr_pswd(SHA256Util.getEncrypt(excel.get(str10), excel.get(str10)));

											if("관리자".equals(iXst01AData.getXusr_auth_knd())
											 ||str21.equals(iXst01AData.getXusr_auth_knd().toUpperCase())){
												iXst01AData.setXusr_auth_knd("M");
											}else{
												iXst01AData.setXusr_auth_knd("U");
											}

											if("남성".equals(iXst01AData.getXusr_sex())
											 ||"MALE".equals(iXst01AData.getXusr_sex().toUpperCase())){
												iXst01AData.setXusr_sex("1");
											}else{
												iXst01AData.setXusr_sex("2");
											}

											String loc = SessionInfo.getSess_locale(req);
											if("en".equals(loc)){
												iXst01AData.setLocale("en");
											}else if("zh".equals(loc)){
												iXst01AData.setLocale("zh");
											}else{
												iXst01AData.setLocale("ko");
											}

											iXst01AList.add(iXst01AData);

										}else{
											dataCnt++;
										}
									}
								}

								if(dataCnt > 0){
									message.setResult("E3"); //필수 데이터 누락
								}else if(iXst01AList.size() == 0){
									message.setResult("E1"); //데이터 누락
								}else{

									BgabGascz002Dto delDto = new BgabGascz002Dto();
									delDto.setCorp_cd(corpCd);

									//List<BgabGascz002Dto> usrList = systemDao.selectUserInfoList(delDto);

									//부서 테이블 Excel Upload 전에 Temp 테이블에 Backup
									//for(BgabGascz002Dto usrData : usrList){
										//systemDao.deleteUserInfoTempByExcelUpload(delDto);
									//	systemDao.insertUserInfoToUserInfoTemp(usrData);
									//}

									//Temp 테이블 저장 백업
									//systemDao.insertUserInfoToUserInfoTemp(delDto);

									//systemDao.deleteUserInfoTempByExcelUpload(new BgabGascz002Dto());
									//systemDao.insertUserInfoToUserInfoTemp(new BgabGascz002Dto());

									systemDao.deleteUserInfoByExcelUpload(delDto);
									systemDao.insertUserInfoByExcelUpload(iXst01AList);

								}

							}else if("XST01B".equals(bgabGascZ011Dto.getUploadType())){

								String corpCd = SessionInfo.getSess_corp_cd(req);

								List<BgabGascz003Dto> iXst01BList = new ArrayList<BgabGascz003Dto>();

								int dataCnt = 0;

								for(Map<String, String> excel : excelData){
									if(!StringUtil.isNullToString(excel.get(str11)).equals("") 	   && !StringUtil.isNullToString(excel.get(str11)).equals(str18))
									{
										if(!StringUtil.isNullToString(excel.get(str11)).equals("") 	   && !StringUtil.isNullToString(excel.get(str11)).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xorg_orga_e")).equals("")     && !StringUtil.isNullToString(excel.get("xorg_orga_e")).equals(str18)     &&
										   //!StringUtil.isNullToString(excel.get("xorg_orga_csner")).equals("") && !StringUtil.isNullToString(excel.get("xorg_orga_csner")).equals(str18) &&
										   !StringUtil.isNullToString(excel.get("xorg_clos_d")).equals("")     && !StringUtil.isNullToString(excel.get("xorg_clos_d")).equals(str18) 	   &&
										   !StringUtil.isNullToString(excel.get("xorg_rsps_i")).equals("")     && !StringUtil.isNullToString(excel.get("xorg_rsps_i")).equals(str18) 	   &&
										   !StringUtil.isNullToString(excel.get("xorg_rsps_m")).equals("")     && !StringUtil.isNullToString(excel.get("xorg_rsps_m")).equals(str18)     &&
										   !StringUtil.isNullToString(excel.get("xorg_rsps_crank")).equals("") && !StringUtil.isNullToString(excel.get("xorg_rsps_crank")).equals(str18) &&
										   !StringUtil.isNullToString(excel.get("xorg_rsps_mrank")).equals("") && !StringUtil.isNullToString(excel.get("xorg_rsps_mrank")).equals(str18) &&
										   !StringUtil.isNullToString(excel.get("xorg_dept_lv")).equals("")    && !StringUtil.isNullToString(excel.get("xorg_dept_lv")).equals(str18))
										{

											BgabGascz003Dto iXst01BData = new BgabGascz003Dto();
											VoUtil.copyMapToVo(excel, iXst01BData);
											iXst01BData.setCorp_cd(corpCd);

											iXst01BList.add(iXst01BData);

										}else{
											dataCnt++;
										}
									}
								}

								if(dataCnt > 0){
									message.setResult("E3"); //필수 데이터 누락
								}else if(iXst01BList.size() == 0){
									message.setResult("E1"); //데이터 누락
								}else{

									BgabGascz003Dto delDto = new BgabGascz003Dto();
									delDto.setCorp_cd(corpCd);

									//List<BgabGascz003Dto> orgList = systemDao.selectDeptInfoList(delDto);

									//부서 테이블 Excel Upload 전에 Temp 테이블에 Backup
									//for(BgabGascz003Dto orgData : orgList){
									//	systemDao.deleteDeptInfoTempByExcelUpload(delDto);
									//	systemDao.insertDeptInfoToDeptInfoTemp(orgData);
									//}

									//systemDao.deleteDeptInfoTempByExcelUpload(new BgabGascz003Dto());
									//Temp 테이블 저장 백업
									//systemDao.insertDeptInfoToDeptInfoTemp(delDto);

									systemDao.deleteDeptInfoByExcelUpload(delDto);
									systemDao.insertDeptInfoByExcelUpload(iXst01BList);


									//부서정보 저장 후 결재레벨 생성
									List<BgabGascz003Dto> list = systemDao.selectDeptInfoList(delDto);
									List<BgabGascz008Dto> z008List = new ArrayList<BgabGascz008Dto>();

									for(BgabGascz003Dto z003Dto : list){
										BgabGascz008Dto bgabGascz008Dto = new BgabGascz008Dto();

										bgabGascz008Dto.setOrga_c(z003Dto.getXorg_orga_c());
										bgabGascz008Dto.setOrga_e(z003Dto.getXorg_orga_e());
										bgabGascz008Dto.setEmpno(z003Dto.getXorg_rsps_i());
										bgabGascz008Dto.setEmpno_org(z003Dto.getXorg_rsps_i());
										bgabGascz008Dto.setName(z003Dto.getXorg_rsps_m());
										bgabGascz008Dto.setLevl_c(z003Dto.getXorg_dept_lv());
										bgabGascz008Dto.setRank_c(z003Dto.getXorg_rsps_crank());
										bgabGascz008Dto.setRank_e(z003Dto.getXorg_rsps_mrank());
										bgabGascz008Dto.setOrga_csner(z003Dto.getXorg_orga_csner());
										bgabGascz008Dto.setCorp_cd(corpCd);

										z008List.add(bgabGascz008Dto);
									}

									systemDao.deleteAprvInfo(delDto);
									systemDao.insertAprvInfo(z008List);
								}

							}else if("XBK01".equals(bgabGascZ011Dto.getUploadType())){
								List<BgabGascbkExcelTempDto> iXbk03BList = new ArrayList<BgabGascbkExcelTempDto>();
								BgabGascbkExcelTempDto       iXbk03BData = null;
								int dataCnt = 0;

								for(Map<String, String> excel : excelData){
									if( !"".equals(StringUtil.isNullToString(excel.get(str12)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str13)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str14))) &&
										!"".equals(StringUtil.isNullToString(excel.get(str15)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str16)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str17))) ){
										iXbk03BData = new BgabGascbkExcelTempDto();
										iXbk03BData.setLrg_cd(StringUtil.isNullToString(excel.get(str12)));
										iXbk03BData.setLrg_nm(StringUtil.isNullToString(excel.get(str13)));
										iXbk03BData.setLrg_sort(StringUtil.isNullToString(excel.get(str14)));
										iXbk03BData.setMrg_cd(StringUtil.isNullToString(excel.get(str15)));
										iXbk03BData.setMrg_nm(StringUtil.isNullToString(excel.get(str16)));
										iXbk03BData.setMrg_sort(StringUtil.isNullToString(excel.get(str17)));
										iXbk03BList.add(iXbk03BData);
									}else{
										dataCnt++;
									}
								}

								if(dataCnt > 0){
									msg = HncisMessageSource.getMessage("EXCEL.0003");
								}else if(iXbk03BList.size() == 0){
									msg = HncisMessageSource.getMessage(str19);
								}else{
									booksDao.insertBkToExcelTemp(iXbk03BList);

									if(booksDao.selectBkToExcelTempMrgChk() > 0){
										msg = HncisMessageSource.getMessage(str20);
									}else if(booksDao.selectBkToMrgChk() > 0){
										msg = HncisMessageSource.getMessage(str20);
									}else{
										BgabGascbkExcelTempDto mrgTmp = new BgabGascbkExcelTempDto();
										mrgTmp.setEeno(bgabGascZ011Dto.getEeno());

										List<BgabGascbkExcelTempDto> bkLrgList = booksDao.selectBkToLrgList(mrgTmp);

										for(BgabGascbkExcelTempDto bkLrgData : bkLrgList){
											if(booksDao.selectBkToLrgChk(bkLrgData) == 0){
												booksDao.insertBkLrgToExcelTemp(bkLrgData);
											}
										}

										booksDao.insertBkMrgToExcelTemp(mrgTmp);
									}

									booksDao.deleteBkToExcelTemp();
								}
							}else if("XGF01".equals(bgabGascZ011Dto.getUploadType())){
								List<BgabGascgfExcelTempDto> iXgf03BList = new ArrayList<BgabGascgfExcelTempDto>();
								BgabGascgfExcelTempDto       iXgf03BData = null;
								int dataCnt = 0;

								for(Map<String, String> excel : excelData){
									if( !"".equals(StringUtil.isNullToString(excel.get(str12)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str13)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str14))) &&
										!"".equals(StringUtil.isNullToString(excel.get(str15)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str16)))   &&
										!"".equals(StringUtil.isNullToString(excel.get(str17))) ){
										iXgf03BData = new BgabGascgfExcelTempDto();
										iXgf03BData.setLrg_cd(StringUtil.isNullToString(excel.get(str12)));
										iXgf03BData.setLrg_nm(StringUtil.isNullToString(excel.get(str13)));
										iXgf03BData.setLrg_sort(StringUtil.isNullToString(excel.get(str14)));
										iXgf03BData.setMrg_cd(StringUtil.isNullToString(excel.get(str15)));
										iXgf03BData.setMrg_nm(StringUtil.isNullToString(excel.get(str16)));
										iXgf03BData.setMrg_sort(StringUtil.isNullToString(excel.get(str17)));
										iXgf03BData.setCorp_cd(strCorpCd);
										iXgf03BList.add(iXgf03BData);
									}else{
										dataCnt++;
									}
								}

								BgabGascgfExcelTempDto chkDto = new BgabGascgfExcelTempDto();
								chkDto.setCorp_cd(strCorpCd);

								if(dataCnt > 0){
									msg = HncisMessageSource.getMessage("EXCEL.0003");
								}else if(iXgf03BList.size() == 0){
									msg = HncisMessageSource.getMessage(str19);
								}else{
									giftDao.insertGfToExcelTemp(iXgf03BList);


									if(giftDao.selectGfToExcelTempMrgChk(chkDto) > 0){
										msg = HncisMessageSource.getMessage(str20);
									}else if(giftDao.selectGfToMrgChk(chkDto) > 0){
										msg = HncisMessageSource.getMessage(str20);
									}else{
										BgabGascgfExcelTempDto mrgTmp = new BgabGascgfExcelTempDto();
										mrgTmp.setEeno(bgabGascZ011Dto.getEeno());
										mrgTmp.setCorp_cd(strCorpCd);

										List<BgabGascgfExcelTempDto> bkLrgList = giftDao.selectGfToLrgList(mrgTmp);

										for(BgabGascgfExcelTempDto bkLrgData : bkLrgList){
											bkLrgData.setCorp_cd(strCorpCd);
											if(giftDao.selectGfToLrgChk(bkLrgData) == 0){
												giftDao.insertGfLrgToExcelTemp(bkLrgData);
											}
										}

										giftDao.insertGfMrgToExcelTemp(mrgTmp);
									}

									giftDao.deleteGfToExcelTemp(chkDto);
								}
							}
//							if(errCnt == 0){
								msg = result[4];
//							}
						}else{
							msg = HncisMessageSource.getMessage(str19);
						}
					}else{
						msg = HncisMessageSource.getMessage("FILE.0001");
					}
				}

			}else{
				msg = HncisMessageSource.getMessage("FILE.0001");
			}

			try{
				if(StringUtil.isNullToString(message.getResult()).equals("E3")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					msg = HncisMessageSource.getMessage("EXCEL.0003");
				}else if(StringUtil.isNullToString(message.getResult()).equals("E1")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					msg = HncisMessageSource.getMessage(str19);
				}

				String dispatcherYN = "Y";
				req.setAttribute("dispatcherYN", dispatcherYN);
				req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
				req.setAttribute("u_type", bgabGascZ011Dto.getUploadType());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				logger.error(e);
			}
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			msg = HncisMessageSource.getMessage("FILE.0001");
			logger.error(e);

			try{
				if(StringUtil.isNullToString(message.getResult()).equals("E3")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					msg = HncisMessageSource.getMessage("EXCEL.0003");
				}else if(StringUtil.isNullToString(message.getResult()).equals("E1")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					msg = HncisMessageSource.getMessage(str19);
				}

				String dispatcherYN = "Y";
				req.setAttribute("dispatcherYN", dispatcherYN);
				req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
				req.setAttribute("u_type", bgabGascZ011Dto.getUploadType());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception ex){
				logger.error(ex);
			}
		}finally{
		}
	}

	@Override
	public int insertLoginUserLog(BgabGascz002Dto dto){
		return commonJobDao.insertLoginUserLog(dto);
	}

	@Override
	public int insertEventToReply(BgabGascz020Dto dto){
		return commonJobDao.insertEventToReply(dto);
	}

	@Override
	public List<BgabGascz017Dto> selectEventQuizList(BgabGascz017Dto dto){
		return commonJobDao.selectEventQuizList(dto);
	}
	@Override
	public int insertEventQuizAnswer(List<BgabGascz017Dto> list){
		String maxAnswerGroupSeq = commonJobDao.selectEventQuizAnswerMaxGroupSeq(list.get(0));
		for(BgabGascz017Dto vo : list){
			vo.setAnswer_seq(maxAnswerGroupSeq);
		}
		return commonJobDao.insertEventQuizAnswer(list);
	}

	@Override
	public CommonMessage insertFileMgmt(HttpServletRequest req, HttpServletResponse res, String foldNm) {
		 CommonMessage message = new CommonMessage();
	        String[] paramVal = new String[2];
	        paramVal[0]       = "uploadFile";
	        paramVal[1]       = foldNm;
	        String resultStr  = FileUtil.uploadFileEdt(req, paramVal);
	        String fileId     = StringUtil.getDocNo();
	        message.setResult(fileId);

	        if(!"".equals(resultStr) && !"E01".equals(resultStr) && !"E02".equals(resultStr)){
	            String[] fileList = resultStr.split(";");
	            for(int i=0; i<fileList.length; i++){
	                //FileMgmtVo fileVo = new FileMgmtVo();
	                String[] retInfo = fileList[i].split(":");
	                /**
	                 * [0]:결과
	                 * [1]:원본파일명
	                 * [2]:변경파일명
	                 * [3]:파일사이즈
	                 */
	                if("S".equals(retInfo[0])){
//	                    fileVo.setFile_id(fileId);
//	                    fileVo.setOrg_file_name(retInfo[1]);
//	                    fileVo.setMfy_file_name(retInfo[2]);
//	                    fileVo.setFile_size(retInfo[3]);
//	                    fileVo.setFile_fold(foldNm);
//	                    fileVo.setInp_eeno("1234567");
//	                    fileVo.setUpdr_eeno("1234567");
//	                    fileMgmtDao.insertFileMgmt(fileVo);
	                    message.setCode1(retInfo[2]);
	                }else{
	                    message.setResult("");
	                    message.setMessage(fileList[i]);
	                }
	            }
	        }else{
	            message.setResult("");
	            if("E01".equals(resultStr)){
	                message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0048"));
	            }else if("E01".equals(resultStr)){
	                message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0049"));
	            }
	            else{
	                message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0050"));
	            }

	        }

	        return message;
	}

	@Override
	public int signup(BgabGascz030Dto bgabGascz030Dto){

		int cnt = commonJobDao.signup(bgabGascz030Dto);

		List<BgabGascz031Dto> list = new ArrayList<BgabGascz031Dto>();

		BgabGascz031Dto dto = null;

		String corpCd = bgabGascz030Dto.getCorp_cd();
		String ipeEeno = str21;
		String updrEeno = str21;

		//휴양소
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask01());
		dto.setTask_gubun("001");
		list.add(dto);

		//근무복
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask02());
		dto.setTask_gubun("002");
		list.add(dto);

		//선물
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask03());
		dto.setTask_gubun("003");
		list.add(dto);

		//도서
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask04());
		dto.setTask_gubun("004");
		list.add(dto);

		//교육신청
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask05());
		dto.setTask_gubun("007");
		list.add(dto);

		//명함
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask06());
		dto.setTask_gubun("009");
		list.add(dto);

		//전산용품
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask07());
		dto.setTask_gubun("010");
		list.add(dto);

		//사무용품
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask08());
		dto.setTask_gubun("012");
		list.add(dto);

		//출장
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask09());
		dto.setTask_gubun("014");
		list.add(dto);

		//해외출장
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask09());
		dto.setTask_gubun("015");
		list.add(dto);

		//픽업
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask10());
		dto.setTask_gubun("016");
		list.add(dto);

		//교통비
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask11());
		dto.setTask_gubun("017");
		list.add(dto);

		//차량신청 && 차량관리
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask12());
		dto.setTask_gubun("018");
		list.add(dto);

		//차량신청 && 차량관리
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask12());
		dto.setTask_gubun("019");
		list.add(dto);

		//주유비
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask13());
		dto.setTask_gubun("020");
		list.add(dto);

		//운행일지
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask14());
		dto.setTask_gubun("021");
		list.add(dto);

		//방문
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask15());
		dto.setTask_gubun("008");
		list.add(dto);

		//회의실
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask16());
		dto.setTask_gubun("011");
		list.add(dto);

		//증명서
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask17());
		dto.setTask_gubun("013");
		list.add(dto);

		//연차
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask18());
		dto.setTask_gubun("022");
		list.add(dto);
		
		//통근버스
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask19());
		dto.setTask_gubun("023");
		list.add(dto);

		//경조사
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask20());
		dto.setTask_gubun("024");
		list.add(dto);

		//물품관리
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask30());
		dto.setTask_gubun("030");
		list.add(dto);
		
		//협력업체
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask31());
		dto.setTask_gubun("031");
		list.add(dto);

		int cnt1 = commonJobDao.insertBizUseYnList(list);

		return cnt;
	}

	@Override
	public int updateMenuListToRequest(BgabGascz030Dto bgabGascz030Dto){

		int cnt = commonJobDao.updateMenuListToRequest(bgabGascz030Dto);

		List<BgabGascz031Dto> list = new ArrayList<BgabGascz031Dto>();

		BgabGascz031Dto dto = null;

		String corpCd = bgabGascz030Dto.getCorp_cd();
		String ipeEeno = str21;
		String updrEeno = str21;
		String tmpFlag = "N";

		if("4".equals(StringUtil.isNullToString(bgabGascz030Dto.getPgs_st_cd()))){
			tmpFlag = "Y";
		}

		//휴양소
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask01());
		dto.setTask_gubun("001");
		list.add(dto);

		//근무복
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask02());
		dto.setTask_gubun("002");
		list.add(dto);

		//선물
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask03());
		dto.setTask_gubun("003");
		list.add(dto);

		//도서
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask04());
		dto.setTask_gubun("004");
		list.add(dto);

		//교육신청
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask05());
		dto.setTask_gubun("007");
		list.add(dto);

		//명함
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask06());
		dto.setTask_gubun("009");
		list.add(dto);

		//전산용품
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask07());
		dto.setTask_gubun("010");
		list.add(dto);

		//사무용품
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask08());
		dto.setTask_gubun("012");
		list.add(dto);

		//출장
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask09());
		dto.setTask_gubun("014");
		list.add(dto);

		//픽업
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask10());
		dto.setTask_gubun("016");
		list.add(dto);

		//교통비
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask11());
		dto.setTask_gubun("017");
		list.add(dto);

		//차량신청 && 차량관리
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask12());
		dto.setTask_gubun("018");
		list.add(dto);

		//차량신청 && 차량관리
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask12());
		dto.setTask_gubun("019");
		list.add(dto);

		//주유비
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask13());
		dto.setTask_gubun("020");
		list.add(dto);

		//운행일지
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask14());
		dto.setTask_gubun("021");
		list.add(dto);

		//방문
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask15());
		dto.setTask_gubun("008");
		list.add(dto);

		//회의실
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask16());
		dto.setTask_gubun("011");
		list.add(dto);

		//증명서
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask17());
		dto.setTask_gubun("013");
		list.add(dto);

		//연차
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask18());
		dto.setTask_gubun("022");
		list.add(dto);

		//통근버스
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask19());
		dto.setTask_gubun("023");
		list.add(dto);

		//경조사
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask20());
		dto.setTask_gubun("024");
		list.add(dto);
		
		//물품지원
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask20());
		dto.setTask_gubun("030");
		list.add(dto);
		
		//협력업체
		dto = new BgabGascz031Dto();
		dto.setCorp_cd(corpCd);
		dto.setIpe_eeno(ipeEeno);
		dto.setUpdr_eeno(updrEeno);
		dto.setAppr_use_yn(bgabGascz030Dto.getTask20());
		dto.setTask_gubun("031");
		list.add(dto);
		
		for(BgabGascz031Dto tmpDto : list){
			tmpDto.setTmp_flag(tmpFlag);
		}

		commonJobDao.updateBizUseYnList(list);

		return cnt;
	}

	@Override
	public int checkDuplicateCrop(BgabGascz030Dto bgabGascz030Dto){
		return commonJobDao.checkDuplicateCrop(bgabGascz030Dto);
	}

	@Override
	public String selectUserPwInfo(BgabGascz002Dto dto) {
		return commonJobDao.selectUserPwInfo(dto);
	}

	public String selectUserMailAddr(BgabGascz002Dto dto) {
		return commonJobDao.selectUserMailAddr(dto);
	}

	@SuppressWarnings("finally")
	@Override
	public String selectUserMailAddr(BgabGascz002Dto dto, HttpServletRequest req) {

		String msg = "";

		try {
			String eMail = commonJobDao.selectUserMailAddr(dto);

			String uuid = UUID.randomUUID().toString().replace("-", "");

			String id = Base64.encode(dto.getXusr_empno());
			String corpCd = "";
			if(dto.getCorp_cd() == null || dto.getCorp_cd().equals("")){
				corpCd = "hncis";
			}
			else{
				corpCd = Base64.encode(dto.getCorp_cd());
			}

			String uuid1 = Base64.encode(uuid);

			String url = "";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http")
			.setHost(req.getServerName())
			.setPort(req.getServerPort())
			.setPath(req.getContextPath() + "/resetPw.gas")
			.setParameter("corp_cd", corpCd)
			.setParameter("id", id)
			.setParameter("auth_key", uuid1);

			url = builder.build().toString();
			logger.info(url);

			String subject = HncisMessageSource.getMessage("MAIL.0000");

			String text  = "*"+HncisMessageSource.getMessage("MAIL.0000")+"<br />";
				   text+= HncisMessageSource.getMessage("MAIL.0001")+"<br /><br />";
				   text+= "<a href='"+url+"'>"+HncisMessageSource.getMessage("pw_reset")+"</a><br /><br />";
			String from = "hncis@hncis.co.kr";

			logger.info(text);

			SendMail oMail = new SendMail();

			boolean success = oMail.sendMail(eMail, from, subject, text, 1, false);
			//boolean success = true;

			if(success){
				dto.setXusr_pswd_key(uuid);
				int cnt = commonJobDao.updateUserPwAuthKay(dto);

			}

			msg = HncisMessageSource.getMessage("MAIL.0002");
			return msg;

		} catch (Exception e) {
			msg = HncisMessageSource.getMessage("MAIL.0003");
			logger.error(e);
			return msg;
		}
		finally {
		}
	}

	@Override
	public CommonMessage updateToResetPassword(BgabGascz002Dto dto) {
		CommonMessage message = new CommonMessage();

		try{
			int cnt = commonJobDao.updateToResetPassword(dto);
			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0051"));

		}catch(Exception e){
			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0052"));
			logger.error(e);
		}



		return message;
	}

	@Override
	public int selectCorpNameListCount(BgabGascz030Dto dto) {
		return commonJobDao.selectCorpNameListCount(dto);
	}

	@Override
	public List<BgabGascz030Dto> selectCorpNameList(BgabGascz030Dto dto) {
		return commonJobDao.selectCorpNameList(dto);
	}

	@Override
	public int getSelectCountUserCodeByCommon(HncisCommon paramDto) {
		return commonJobDao.getSelectCountUserCodeByCommon(paramDto);
	}

	@Override
	public List<HncisCommon> getSelectUserCodeByCommon(HncisCommon paramDto) {
		return commonJobDao.getSelectUserCodeByCommon(paramDto);
	}

	@Override
	public List<HncisCommon> getSelectUserCodeByCommon1(HncisCommon paramDto) {
		return commonJobDao.getSelectUserCodeByCommon1(paramDto);
	}

	@Override
	public CommonMessage updateToResetLocale(BgabGascz002Dto dto) {
		CommonMessage message = new CommonMessage();

		try{
			int cnt = commonJobDao.updateToResetLocale(dto);
			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0068"));

		}catch(Exception e){
			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0069"));
			logger.error(e);
		}

		return message;
	}
	
	/**
	 * 회사 정보 조회
	 */
	@Override
	public BgabGascz035Dto getSelectCorpInfo(BgabGascz035Dto dto) {
		return commonJobDao.getSelectCorpInfo(dto);
	}
}
