package com.hncis.common.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.BgabGascz034Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.common.vo.HncisCommon;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz008Dto;
import com.hncis.system.vo.BgabGascz017Dto;
import com.hncis.system.vo.BgabGascz020Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz031Dto;
import com.hncis.system.vo.BgabGascz035Dto;
import com.hncis.common.vo.BpmInfo;

public class CommonJobDaoImplByIBatis extends CommonDao implements CommonJobDao{

	private final String SELECT_CODE_LIST						= "selectCodeList";
	private final String SELECT_CODE_LIST_NO_INCLUDE_FLAG		= "selectCodeListNoIncludeFlag";
	private final String SELECT_DATA_COMBO_LIST					= "selectDataComboList";
	private final String SELECT_XUSR_INFO						= "selectXusrInfo";
	private final String SELECT_SCRN_INFO						= "selectScrnInfo";
	private final String SELECT_WORK_AUTH_CD          		 	= "selectWorkAuthCd1";
	private final String SELECT_USERINFO           				= "selectUserInfo";
	private final String SELECT_USERINFO_LIST     				= "selectUserInfoList";
	private final String SELECT_USERINFO_DETAIL           		= "selectUserInfoDetail";
	private final String SELECT_ORG_INFO           				= "selectDeptInfo";
	private final String SELECT_NOTICE_POPUP 					= "selectNoticePopup";
	private final String INSERT_APPROVAL_INFO 					= "insertApprovalInfo";
	private final String INSERT_APPROVAL_DETAIL_INFO 			= "insertApprovalDetailInfo";
	private final String UPDATE_APPROVAL_INFO 					= "updateApprovalInfo";
	private final String UPDATE_APPROVAL_DETAIL_INFO 			= "updateApprovalDetailInfo";
	private final String SELECT_SYSTEM_STATUS_BY_APPROVAL 		= "selectSystemStatusByApproval";
	private final String UPDATE_SYSTEM_STATUS_BY_APPROVAL 		= "updateSystemStatusByApproval";
	private final String UPDATE_SYSTEM_STATUS_BY_APPROVAL_DOC	= "updateSystemStatusByApprovalDoc";
	private final String SELECT_MENU_LIST						= "selectMenuList";
	private final String SELECT_CALENDAR_HOLY_LIST				= "selectCalendarHolyList";
	private final String SELECT_MAIN_NOTICE_LIST 				= "selectMainNoticeList";
	private final String SELECT_MAIN_QNA_LIST 					= "selectMainQnaList";
	private final String SELECT_MAIN_FAQ_LIST 					= "selectMainFaqList";
	private final String SELECT_MAIN_CLAIM_LIST 				= "selectMainClaimList";
	private final String SELECT_APPROVAL_LEVEL_INFO 			= "selectApprovalLevelInfo";
	private final String SELECT_APPROVAL_MAX_LEVEL_INFO 			= "selectApprovalMaxLevelInfo";
	private final String SELECT_APPROVAL_STATUS_INFO 			= "selectApprovalStatusInfo";
	private final String SELECT_APPROVAL_STATUS_CANCEL_YN_INFO 			= "selectApprovalStatusCancelYnInfo";
	private final String SELECT_APPROVAL_INFO 					= "selectApprovalInfo";
	private final String SELECT_COUNT_TO_CODE_MANAGEMENT 		= "selectCountToCodeManagement";
	private final String SELECT_LIST_TO_CODE_MANAGEMENT 		= "selectListToCodeManagement";
	private final String SELECT_LIST_TO_CODE_MANAGEMENT_ORDER_BY_SORT = "selectListToCodeManagementOrderbySort";
	private final String INSERT_LIST_TO_CODE_MANAGEMENT 		= "insertListToCodeManagement";
	private final String INSERT_LIST_TO_CODE_MANAGEMENT_AUTO_SEQ= "insertListToCodeManagementAutoSeq";
	private final String UPDATE_LIST_TO_CODE_MANAGEMENT 		= "updateListToCodeManagement";
	private final String DELETE_LIST_TO_CODE_MANAGEMENT 		= "deleteListToCodeManagement";
	private final String SELECT_APPROVAL_USER_COUNT 			= "selectApprovalUserCount";
	private final String SELECT_APPROVAL_DEPT_COUNT 			= "selectApprovalDeptCount";
	private final String SELECT_APPROVAL_READER_COUNT 			= "selectApprovalReaderCount";
	private final String SELECT_COUNT_DEPTCODE_BY_COMMON 		= "selectCountDeptCodeByCommon";
	private final String SELECT_DEPTCODE_BY_COMMON 				= "selectDeptCodeByCommon";
	private final String SELECT_COUNT_JOB_MGMT_INFO 			= "selectCountJobMgmtInfo";
	private final String SELECT_JOB_MGMT_INFO 					= "selectJobMgmtInfo";
	private final String SELECT_INFO_TO_PICE_MAIL_ADDR 					= "selectInfoToPicEMailAddr";
	private final String SELECT_APPROVAL_USER_TOTAL_LEVEL_COUNT = "selectApprovalUserTotalLevelCount";
	private final String SELECT_APPROVAL_RPTS_INFO 				= "selectApprovalRptsInfo";
	private final String UPDATE_APPROVAL_STATUS_FOR_CONFIRM		= "updateApprovalStatusForConfirm";
	private final String SELECT_GNB_MENUES="selectGNBMenues";
	private final String INSERT_LOGIN_USER_LOG					= "insertLoginUserLog";
	private final String INSERT_EVENT_TO_REPLY					= "insertEventToReply";
	private final String SELECT_EVENT_QUIZ_LIST					= "selectEventQuizList";
	private final String INSERT_EVENT_QUIZ_ANSWER				= "insertEventQuizAnswer";
	private final String SELECT_EVENT_QUIZ_ANSWER_MAX_GROUP_SEQ	= "selectEventQuizAnswerMaxGroupSeq";

	private final String SELECT_PIC_INFO ="selectPicInfo";

	private final String SELECT_TS_RPTS_INFO ="selectTsRptsInfo";

	private final String SELECT_COMPANYCAR_RPTS_INFO ="selectCompanycarRptsInfo";

	private final String SELECT_COMMUTING_BUS_RPTS_INFO ="selectCommutingBusRptsInfo";

	private final String SELECT_UNIFORM_INDIVIDUAL_RPTS_INFO ="selectUniformIndividualRptsInfo";

	private final String SELECT_UNIFORM_NEWTASK_RPTS_INFO ="selectUniformNewtaskRptsInfo";
	private final String SELECT_APPLINE_LIST ="selectAppLineList";
	private final String SELECT_NEXT_APPROVAL_INFO ="selectNextApprovalInfo";
	private final String SELECT_SECURITY_NEXT_APPROVAL_INFO ="selectSecurityNextApprovalInfo";
	private final String SELECT_COORDI_CHK ="selectCoordiChk";

	private final String SELECT_INFO_TO_EENO_EMAIL_ADR ="selectInfoToEenoEmailAdr";
	private final String SELECT_COSTCENTER_NAME ="selectCostCenterName";
	private final String SELECT_TOP_MENU_LIST ="selectTopMenuList";
	private final String SELECT_TOP_MENU_LIST1 ="selectTopMenuList1";
	private final String SELECT_BUSINESS_TRAVEL_TOP_MENU_LIST ="selectBusinessTravelTopMenuList";
	private final String SELECT_LEFT_MENU_LIST ="selectLeftMenuList";
	private final String SELECT_LEFT_MENU_LIST1 ="selectLeftMenuList1";
	private final String SELECT_LEFT_MENU_XGS_AUTH ="selectLeftMenuXgsAuth";

	private final String SELECT_MENU_COMBO_LIST ="selectMenuComboList";

	private final String SELECT_APPROVE_LEVEL ="selectApproveLevel";
	private final String SELECT_APPROVE_STEP_LEVEL ="selectApproveStepLevel";
	private final String SELECT_ROOMS_NEXT_APPROVAL_INFO ="selectRoomsNextApprovalInfo";

	private final String SIGNUP_CORPORATION ="signupCorporation";
	private final String CHECK_DUPLICATE_CROP="checkDuplicateCrop";

	private final String UPDATE_MENU_LIST_REQUEST ="updateMenuListRequest";
	private final String UPDATE_TO_RESET_LOCALE ="updateToResetLocale";

	private final String SELECT_USER_PW_INFO ="selectUserPwInfo";
	private final String SELECT_USER_MAIL_ADDR ="selectUserMailAddr";

	private final String UPDATE_USER_PW_AUTH_KAY ="updateUserPwAuthKay";
	private final String SELECT_CHECK_AUTH_KEY_FOR_PW ="selectCheckAuthKeyForPw";

	private final String UPDATE_TO_RESET_PASSWORD ="updateToResetPassword";

	private final String SELECT_CORP_NAME_LIST_COUNT ="selectCorpNameListCount";
	private final String SELECT_CORP_NAME_LIST ="selectCorpNameList";
	private final String SELECT_XST30_INFO ="selectXst30Info";

	private final String SELECT_COUNT_USERCODE_BY_COMMON 		= "selectCountUserCodeByCommon";
	private final String SELECT_USERCODE_BY_COMMON 				= "selectUserCodeByCommon";
	private final String SELECT_USERCODE_BY_COMMON1				= "selectUserCodeByCommon1";

	private final String INSERT_BIZ_USE_YN_LIST					= "insertBizUseYnList";
	private final String UPDATE_BIZ_USE_YN_LIST					= "updateBizUseYnList";
	private final String SELECT_COUNT_ZIP_CODE						= "selectCountZipCode";
	private final String SELECT_ZIP_CODE						= "selectZipCode";
	private final String SELECT_USER_LOCALE						= "selectUserLocale";
	private final String SELECT_CORP_INFO						= "selectCorpInfo";

	private final String SELECT_BPM_LIST						= "selectBpmList";
	private final String uncheck								= "unchecked";

	@Override
	@SuppressWarnings(uncheck)
	public List<CommonCode> getCodeList(CommonCode commonCode){
		return getSqlMapClientTemplate().queryForList(SELECT_CODE_LIST, commonCode);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<CommonCode> getCodeListNotIncludeAply(CommonCode commonCode) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(SELECT_CODE_LIST_NO_INCLUDE_FLAG, commonCode);
	}

	@SuppressWarnings(uncheck)
	public List<CommonCode> getCodeListNotIncludeXcodAplyFlag(CommonCode commonCode) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(SELECT_CODE_LIST_NO_INCLUDE_FLAG, commonCode);
	}

	@Override
	@SuppressWarnings(uncheck)
	public CommonCode getCodeInfo(CommonCode commonCode){
		return (CommonCode) getSqlMapClientTemplate().queryForObject(SELECT_CODE_LIST, commonCode);
	}
	@Override
	@SuppressWarnings(uncheck)
	public CommonCode getCodeInfoNotIncludeXcodAplyFlag(CommonCode commonCode) {
		// TODO Auto-generated method stub
		return (CommonCode) getSqlMapClientTemplate().queryForObject(SELECT_CODE_LIST_NO_INCLUDE_FLAG, commonCode);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<CommonCode> getDataComboList(CommonCode commonCode){
		return getSqlMapClientTemplate().queryForList(SELECT_DATA_COMBO_LIST, commonCode);
	}

	@Override
	public BgabGascz002Dto getXusrInfo(BgabGascz002Dto bgabGascz002){
		return (BgabGascz002Dto)getSqlMapClientTemplate().queryForObject(SELECT_XUSR_INFO, bgabGascz002);
	}

	@Override
	public HncisCommon getScrnInfo(HncisCommon hncisCommon) {
		return (HncisCommon) getSqlMapClientTemplate().queryForObject(SELECT_SCRN_INFO, hncisCommon);
	}

	@Override
	public String getAuthority(HncisCommon hncisCommon) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_WORK_AUTH_CD, hncisCommon);
	}

	@Override
	public CommonUserInfo getSelectUserInfo(CommonUserInfo commonUserInfo) {
		return (CommonUserInfo) getSqlMapClientTemplate().queryForObject(SELECT_USERINFO, commonUserInfo);
	}

	@Override
	public List<CommonUserInfo> getSelectUserInfoList(CommonUserInfo commonUserInfo) {
		return getSqlMapClientTemplate().queryForList(SELECT_USERINFO_LIST, commonUserInfo);
	}

	@Override
	public BgabGascz002Dto getSelectUserInfoDetail(BgabGascz002Dto bgabGascz002){
		return (BgabGascz002Dto) getSqlMapClientTemplate().queryForObject(SELECT_USERINFO_DETAIL, bgabGascz002);
	}

	@Override
	public BgabGascz003Dto getSelectDeptInfo(BgabGascz003Dto xocxorgDto){
		return (BgabGascz003Dto)getSqlMapClientTemplate().queryForObject(SELECT_ORG_INFO, xocxorgDto);
	}

	@Override
	public BgabGasc01DtlDto getNoticePopup(HashMap<String, String> paramMap){
		return (BgabGasc01DtlDto)getSqlMapClientTemplate().queryForObject(SELECT_NOTICE_POPUP, paramMap);
	}

	@Override
	public int insertApprovalInfo(CommonApproval commonApproval){
		return insert(INSERT_APPROVAL_INFO, commonApproval);
	}

	@Override
	public int insertApprovalDetailInfo(CommonApproval commonApproval){
		return insert(INSERT_APPROVAL_DETAIL_INFO, commonApproval);
	}

	@Override
	public int updateApprovalInfo(CommonApproval commonApproval){
		return update(UPDATE_APPROVAL_INFO, commonApproval);
	}

	@Override
	public int updateApprovalDetailInfo(CommonApproval commonApproval){
		return update(UPDATE_APPROVAL_DETAIL_INFO, commonApproval);
	}

	@Override
	public String getSystemStatusByApproval(CommonApproval commonApproval) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_SYSTEM_STATUS_BY_APPROVAL, commonApproval);
	}

	@Override
	public int updateSystemStatusByApproval(CommonApproval commonApproval){
		return update(UPDATE_SYSTEM_STATUS_BY_APPROVAL, commonApproval);
	}

	@Override
	public int updateSystemStatusByApprovalDoc(CommonApproval commonApproval){
		return update(UPDATE_SYSTEM_STATUS_BY_APPROVAL_DOC, commonApproval);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getMenuList(HncisCommon hncisCommon){
		return getSqlMapClientTemplate().queryForList(SELECT_MENU_LIST, hncisCommon);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<String> selectCalendarHolyList(HashMap<String, String> paramMap){
		return getSqlMapClientTemplate().queryForList(SELECT_CALENDAR_HOLY_LIST, paramMap);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGasc01DtlDto> getSelectMainNoticeList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_MAIN_NOTICE_LIST, corp_cd);
	}

	@Override
	@SuppressWarnings({ uncheck })
	public List<BgabGasc01DtlDto> getSelectMainQnaList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_MAIN_QNA_LIST, corp_cd);
	}

	@Override
	@SuppressWarnings({ uncheck })
	public List<BgabGasc01DtlDto> getSelectMainFaqList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_MAIN_FAQ_LIST, corp_cd);
	}

	@Override
	@SuppressWarnings({ uncheck })
	public List<BgabGasc01DtlDto> getSelectMainClaimList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_MAIN_CLAIM_LIST, corp_cd);
	}

	@Override
	public CommonApproval getApprovalLevelInfo(CommonApproval commonApproval) {
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_LEVEL_INFO, commonApproval);
	}

	@Override
	public int selectApprovalMaxLevelInfo(CommonApproval commonApproval) {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_MAX_LEVEL_INFO, commonApproval);
	}

	@Override
	public CommonApproval getApprovalStatusInfo(CommonApproval commonApproval) {
//		CommonApproval rtnVo = (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_STATUS_INFO, commonApproval);
//
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("if_id", commonApproval.getIf_id());
//		paramMap.put("corp_cd", commonApproval.getCorp_cd());
//
//		HashMap<String, String> rtnMap = (HashMap<String, String>)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_STATUS_CANCEL_YN_INFO, paramMap);
//
//		if(rtnMap != null){
//			rtnVo.setCancel_yn(rtnMap.get("ResultVal").toString());
//		} else {
//			rtnVo.setCancel_yn("N");
//		}
//		return rtnVo;
//		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_STATUS_CANCEL_YN_INFO, commonApproval);

		CommonApproval rtnVo = (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_STATUS_INFO, commonApproval);

		String rtnStr = (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_STATUS_CANCEL_YN_INFO, commonApproval);
		rtnVo.setCancel_yn(rtnStr);

		return rtnVo;

	}
	@Override
	public String getSelectApprovalInfo(CommonApproval commonApproval) {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("if_id", commonApproval.getIf_id());
//		paramMap.put("system_code", commonApproval.getSystem_code());
//		paramMap.put("corp_cd", commonApproval.getCorp_cd());
//
//		HashMap<String, String> rtnMap = (HashMap<String, String>)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO, paramMap);
//
//		if(rtnMap.get("ResultVal") != null){
//			System.out.println("ResultVal:"+rtnMap.get("ResultVal").toString());
//			return rtnMap.get("ResultVal").toString();
//		}
//
//		return "";
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO, commonApproval);
	}
	@Override
	public String getSelectCountToCodeManagement(BgabGascz005Dto vo){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_CODE_MANAGEMENT, vo);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz005Dto> getSelectListToCodeManagement(BgabGascz005Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_CODE_MANAGEMENT, vo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz005Dto> getSelectListToCodeManagementOrderBySort(BgabGascz005Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_CODE_MANAGEMENT_ORDER_BY_SORT, vo);
	}

	@Override
	public int insertListToCodeManagement(List<BgabGascz005Dto> list){
		return super.insert(INSERT_LIST_TO_CODE_MANAGEMENT, list);
	}
	@Override
	public int insertListToCodeManagementAutoSeq(List<BgabGascz005Dto> list){
		return super.insert(INSERT_LIST_TO_CODE_MANAGEMENT_AUTO_SEQ, list);
	}
	@Override
	public int updateListToCodeManagement(List<BgabGascz005Dto> list){
		return super.update(UPDATE_LIST_TO_CODE_MANAGEMENT, list);
	}
	@Override
	public int deleteListToCodeManagement(List<BgabGascz005Dto> list){
		return super.delete(DELETE_LIST_TO_CODE_MANAGEMENT, list);
	}
	@Override
	public int getSelectApprovalUserCount(CommonApproval dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_USER_COUNT, dto));
	}
	@Override
	public int getSelectApprovalDeptCount(CommonApproval dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_DEPT_COUNT, dto));
	}

	@Override
	public int getSelectApprovalReaderCount(CommonApproval dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_READER_COUNT, dto));
	}

	@Override
	public int getSelectCountDeptCodeByCommon(HncisCommon dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_DEPTCODE_BY_COMMON, dto));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<HncisCommon> getSelectDeptCodeByCommon(HncisCommon dto){
		return getSqlMapClientTemplate().queryForList(SELECT_DEPTCODE_BY_COMMON, dto);
	}

	@Override
	public int getSelectCountZipCode(BgabGascz034Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_ZIP_CODE, dto));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz034Dto> getSelectZipCode(BgabGascz034Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_ZIP_CODE, dto);
	}

	@Override
	public int getSelectCountJobMgmtInfo(BgabGascz007Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_JOB_MGMT_INFO, dto));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz007Dto> getSelectJobMgmtInfo(BgabGascz007Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_MGMT_INFO, dto);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz007Dto> selectInfoToPicEMailAddr(BgabGascz007Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_INFO_TO_PICE_MAIL_ADDR, dto);
	}

	@Override
	public int getSelectApprovalUserTotalLevelCount(CommonApproval dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_USER_TOTAL_LEVEL_COUNT, dto));
	}
	@Override
	public CommonApproval getApprovalRptsInfo(CommonApproval commonApproval) {
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_RPTS_INFO, commonApproval);
	}
	@Override
	public int updateApprovalStatusForConfirm(CommonApproval comAppr) {
		return super.update(UPDATE_APPROVAL_STATUS_FOR_CONFIRM, comAppr);
	}
	@Override
	public List<BgabGascz004Dto> selectGNBMenues(){
		return getSqlMapClientTemplate().queryForList(SELECT_GNB_MENUES);
	}
	@Override
	public List<BgabGascz007Dto> getSelectPicInfo(BgabGascz007Dto dto) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(SELECT_PIC_INFO, dto);
	}

	@Override
	public CommonApproval getTsApprovalRptsInfo(String docNo) {
		// TODO Auto-generated method stub
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_TS_RPTS_INFO, docNo);
	}

	@Override
	public CommonApproval getCompanyCarApprovalRptsInfo(String docNo) {
		// TODO Auto-generated method stub
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_COMPANYCAR_RPTS_INFO, docNo);
	}

	@Override
	public CommonApproval getCommutingBusApprovalRptsInfo(String docNo) {
		// TODO Auto-generated method stub
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_COMMUTING_BUS_RPTS_INFO, docNo);
	}

	@Override
	public CommonApproval getUniformIndividualApprovalRptsInfo(String docNo) {
		// TODO Auto-generated method stub
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_INDIVIDUAL_RPTS_INFO, docNo);
	}

	@Override
	public CommonApproval getUniformNewTaskApprovalRptsInfo(String docNo) {
		// TODO Auto-generated method stub
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_NEWTASK_RPTS_INFO, docNo);
	}

	@Override
	public List<BgabGascz008Dto> getSelectAppLineList(BgabGascz008Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_APPLINE_LIST, dto);
	}

	@Override
	public CommonApproval getNextApprovalInfo(CommonApproval commonApproval) {
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_NEXT_APPROVAL_INFO, commonApproval);
	}

	@Override
	public CommonApproval getSecurityNextApprovalInfo(CommonApproval commonApproval) {
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_NEXT_APPROVAL_INFO, commonApproval);
	}

	@Override
	public String getSelectCoordiChk(CommonApproval commonApproval){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COORDI_CHK, commonApproval);
	}

	@Override
	public String getSelectInfoToEenoEmailAdr(CommonApproval commonApproval) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TO_EENO_EMAIL_ADR, commonApproval);
	}

	@Override
	public String getSelectCostCenterName(String cost_cd) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COSTCENTER_NAME, cost_cd);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getTopMenuList(HncisCommon hncisCommon){
		return getSqlMapClientTemplate().queryForList(SELECT_TOP_MENU_LIST, hncisCommon);
	}
	
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getTopMenuList1(HncisCommon hncisCommon){
		return getSqlMapClientTemplate().queryForList(SELECT_TOP_MENU_LIST1, hncisCommon);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getSelectBusinessTravelTopMenuList(){
		return getSqlMapClientTemplate().queryForList(SELECT_BUSINESS_TRAVEL_TOP_MENU_LIST);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getLeftMenuList(HncisCommon hncisCommon){
		return getSqlMapClientTemplate().queryForList(SELECT_LEFT_MENU_LIST, hncisCommon);
	}
	
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> getLeftMenuList1(HncisCommon hncisCommon){
		return getSqlMapClientTemplate().queryForList(SELECT_LEFT_MENU_LIST1, hncisCommon);
	}

	@Override
	public BgabGascz004Dto getLeftMenuXgsAuth(HncisCommon hncisCommon){
		return (BgabGascz004Dto)getSqlMapClientTemplate().queryForObject(SELECT_LEFT_MENU_XGS_AUTH, hncisCommon);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz004Dto> selectMenuComboList(){
		return getSqlMapClientTemplate().queryForList(SELECT_MENU_COMBO_LIST);
	}

	@Override
	public String selectApproveLevel(CommonApproval vo) {
		return StringUtil.isNullToString(getSqlMapClientTemplate().queryForObject(SELECT_APPROVE_LEVEL, vo));
	}

	@Override
	public String selectApproveStepLevel(CommonApproval vo) {
		if("VE".equals(vo.getSystem_code()) || "SE".equals(vo.getSystem_code())){
			vo.setSystem_code("EV");
		}else if("BN".equals(vo.getSystem_code())){
			vo.setSystem_code("BT");
		}
		return StringUtil.isNullToString(getSqlMapClientTemplate().queryForObject(SELECT_APPROVE_STEP_LEVEL, vo));
	}
	@Override
	public int insertLoginUserLog(BgabGascz002Dto dto){
		return insert(INSERT_LOGIN_USER_LOG, dto);
	}
	@Override
	public int insertEventToReply(BgabGascz020Dto dto){
		return insert(INSERT_EVENT_TO_REPLY, dto);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascz017Dto> selectEventQuizList(BgabGascz017Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_EVENT_QUIZ_LIST, dto);
	}
	@Override
	public int insertEventQuizAnswer(List<BgabGascz017Dto> list){
		return insert(INSERT_EVENT_QUIZ_ANSWER, list);
	}
	@Override
	public String selectEventQuizAnswerMaxGroupSeq(BgabGascz017Dto vo) {
		return StringUtil.isNullToString(getSqlMapClientTemplate().queryForObject(SELECT_EVENT_QUIZ_ANSWER_MAX_GROUP_SEQ, vo));
	}

	@Override
	public CommonApproval getRoomsNextApprovalInfo(CommonApproval commonApproval) {
		return (CommonApproval)getSqlMapClientTemplate().queryForObject(SELECT_ROOMS_NEXT_APPROVAL_INFO, commonApproval);
	}

	@Override
	public int signup(BgabGascz030Dto bgabGascz030Dto){
		return insert(SIGNUP_CORPORATION, bgabGascz030Dto);
	}

	@Override
	public int updateMenuListToRequest(BgabGascz030Dto bgabGascz030Dto){
		return update(UPDATE_MENU_LIST_REQUEST, bgabGascz030Dto);
	}

	@Override
	public int checkDuplicateCrop(BgabGascz030Dto bgabGascz030Dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(CHECK_DUPLICATE_CROP, bgabGascz030Dto));
	}

	@Override
	public String selectUserPwInfo(BgabGascz002Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_USER_PW_INFO, dto);
	}

	@Override
	public String selectUserMailAddr(BgabGascz002Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_USER_MAIL_ADDR, dto);
	}

	@Override
	public int updateUserPwAuthKay(BgabGascz002Dto dto) {
		return insert(UPDATE_USER_PW_AUTH_KAY, dto);
	}

	@Override
	public int getCheckAuthKeyForPw(BgabGascz002Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_CHECK_AUTH_KEY_FOR_PW, dto));
	}

	@Override
	public int updateToResetPassword(BgabGascz002Dto dto) {
		return update(UPDATE_TO_RESET_PASSWORD, dto);
	}

	@Override
	public int selectCorpNameListCount(BgabGascz030Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_CORP_NAME_LIST_COUNT, dto));
	}

	@Override
	public List<BgabGascz030Dto> selectCorpNameList(BgabGascz030Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_CORP_NAME_LIST, dto);
	}
	@Override
	@SuppressWarnings(uncheck)
	public BgabGascz030Dto selectXst30Info(BgabGascz030Dto dto){
		return (BgabGascz030Dto)getSqlMapClientTemplate().queryForObject(SELECT_XST30_INFO, dto);
	}

	@Override
	public int getSelectCountUserCodeByCommon(HncisCommon dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_USERCODE_BY_COMMON, dto));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<HncisCommon> getSelectUserCodeByCommon(HncisCommon dto){
		return getSqlMapClientTemplate().queryForList(SELECT_USERCODE_BY_COMMON, dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<HncisCommon> getSelectUserCodeByCommon1(HncisCommon dto){
		return getSqlMapClientTemplate().queryForList(SELECT_USERCODE_BY_COMMON1, dto);
	}

	@Override
	public int insertBizUseYnList(List<BgabGascz031Dto> list) {
		return update(INSERT_BIZ_USE_YN_LIST, list);
	}

	@Override
	public int updateBizUseYnList(List<BgabGascz031Dto> list) {
		return update(UPDATE_BIZ_USE_YN_LIST, list);
	}

	@Override
	public String getSelectCorpChkCount(BgabGascz030Dto dto){
		return (String)getSqlMapClientTemplate().queryForObject("selectCorpChkCount", dto);
	}

	@Override
	public String getSelectLoc(String corp_cd) {
		return (String)getSqlMapClientTemplate().queryForObject("selectLoc",corp_cd);
	}

	@Override
	public int updateToResetLocale(BgabGascz002Dto dto) {
		return update(UPDATE_TO_RESET_LOCALE, dto);
	}

	@Override
	public String getLocale(String corp_cd){
		return (String)getSqlMapClientTemplate().queryForObject("selectLoaderLocale", corp_cd);
	}
	
	@Override
	public String getUserLocale(BgabGascz002Dto dto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_USER_LOCALE, dto);
	}
	
	/**
	 * 회사 정보 조회
	 */
	@Override
	public BgabGascz035Dto getSelectCorpInfo(BgabGascz035Dto dto){
		return (BgabGascz035Dto)getSqlMapClientTemplate().queryForObject(SELECT_CORP_INFO, dto);
	}
	
	@Override
	public List<BpmInfo> getSelectBpmList(BpmInfo vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_BPM_LIST, vo);
	}
	
//	@Override
//	public CommonApproval getSelectApprovalStatusInfo(CommonApproval commonApp) {
//		return getSqlMapClientTemplate().queryForList(SELECT_APPROVAL_STATUS_INFO, commonApp);
//	}
}
