package com.hncis.common.dao;

import java.util.HashMap;
import java.util.List;

import com.hncis.board.vo.BgabGasc01DtlDto;
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

public interface CommonJobDao {

	public List<CommonCode> getCodeList(CommonCode commonCode);

	public List<CommonCode> getCodeListNotIncludeAply(CommonCode commonCode);

	public CommonCode getCodeInfo(CommonCode commonCode);

	public CommonCode getCodeInfoNotIncludeXcodAplyFlag(CommonCode commonCode);

	public List<CommonCode> getDataComboList(CommonCode commonCode);

	public BgabGascz002Dto getXusrInfo(BgabGascz002Dto bgabGascz002Dto);

	public HncisCommon getScrnInfo(HncisCommon hncisCommon);

	public String getAuthority(HncisCommon hncisCommon);

	public CommonUserInfo getSelectUserInfo(CommonUserInfo commonUserInfo);

	public List<CommonUserInfo> getSelectUserInfoList(CommonUserInfo commonUserInfo);

	public BgabGascz002Dto getSelectUserInfoDetail(BgabGascz002Dto bgabGascz002Dto);

	public BgabGascz003Dto getSelectDeptInfo(BgabGascz003Dto xocxorgDto);

	public BgabGasc01DtlDto getNoticePopup(HashMap<String, String> paramMap);

	public int insertApprovalInfo(CommonApproval commonApproval);

	public int insertApprovalDetailInfo(CommonApproval commonApproval);

	public int updateApprovalInfo(CommonApproval commonApproval);

	public int updateApprovalDetailInfo(CommonApproval commonApproval);

	public String getSystemStatusByApproval(CommonApproval commonApproval);

	public int updateSystemStatusByApproval(CommonApproval commonApproval);

	public int updateSystemStatusByApprovalDoc(CommonApproval commonApproval);

	public List<BgabGascz004Dto> getMenuList(HncisCommon hncisCommon);

	public List<String> selectCalendarHolyList(HashMap<String, String> paramMap);

	public List<BgabGasc01DtlDto> getSelectMainNoticeList(String corp_cd);

	public List<BgabGasc01DtlDto> getSelectMainQnaList(String corp_cd);

	public List<BgabGasc01DtlDto> getSelectMainFaqList(String corp_cd);

	public List<BgabGasc01DtlDto> getSelectMainClaimList(String corp_cd);

	public CommonApproval getApprovalLevelInfo(CommonApproval commonApproval);

	public CommonApproval getApprovalStatusInfo(CommonApproval commonApproval);

	public String getSelectApprovalInfo(CommonApproval commonApproval);

	public String getSelectCountToCodeManagement(BgabGascz005Dto dto);

	public List<BgabGascz005Dto> getSelectListToCodeManagement(BgabGascz005Dto dto);

	public List<BgabGascz005Dto> getSelectListToCodeManagementOrderBySort(BgabGascz005Dto dto);

	public int insertListToCodeManagement(List<BgabGascz005Dto> list);

	public int insertListToCodeManagementAutoSeq(List<BgabGascz005Dto> list);

	public int updateListToCodeManagement(List<BgabGascz005Dto> list);

	public int deleteListToCodeManagement(List<BgabGascz005Dto> list);

	public int getSelectApprovalUserCount(CommonApproval dto);

	public int getSelectApprovalDeptCount(CommonApproval dto);

	public int getSelectApprovalReaderCount(CommonApproval dto);

	public int getSelectCountDeptCodeByCommon(HncisCommon dto);
	public List<HncisCommon> getSelectDeptCodeByCommon(HncisCommon dto);

	public int getSelectCountZipCode(BgabGascz034Dto dto);
	public List<BgabGascz034Dto> getSelectZipCode(BgabGascz034Dto dto);

	public int getSelectCountJobMgmtInfo(BgabGascz007Dto dto);
	public List<BgabGascz007Dto> getSelectJobMgmtInfo(BgabGascz007Dto dto);

	public List<BgabGascz007Dto> selectInfoToPicEMailAddr(BgabGascz007Dto dto);

	public int getSelectApprovalUserTotalLevelCount(CommonApproval dto);

	public CommonApproval getApprovalRptsInfo(CommonApproval commonApproval);

	public int updateApprovalStatusForConfirm(CommonApproval comAppr);

	public List<BgabGascz004Dto> selectGNBMenues();

	public List<BgabGascz007Dto> getSelectPicInfo(BgabGascz007Dto dto);

	public CommonApproval getTsApprovalRptsInfo(String docNo);

	public CommonApproval getCompanyCarApprovalRptsInfo(String docNo);

	public CommonApproval getCommutingBusApprovalRptsInfo(String docNo);

	public CommonApproval getUniformIndividualApprovalRptsInfo(String docNo);

	public CommonApproval getUniformNewTaskApprovalRptsInfo(String docNo);

	public List<BgabGascz008Dto> getSelectAppLineList(BgabGascz008Dto dto);

	public CommonApproval getNextApprovalInfo(CommonApproval commonApproval);

	public CommonApproval getSecurityNextApprovalInfo(CommonApproval commonApproval);

	public String getSelectCoordiChk(CommonApproval commonApproval);

	public String getSelectInfoToEenoEmailAdr(CommonApproval commonApproval);

	public String getSelectCostCenterName(String cost_cd);

	public List<BgabGascz004Dto> getTopMenuList(HncisCommon info);
	
	public List<BgabGascz004Dto> getTopMenuList1(HncisCommon info);

	public List<BgabGascz004Dto> getSelectBusinessTravelTopMenuList();
	
	public List<BgabGascz004Dto> getLeftMenuList(HncisCommon info);
	
	public List<BgabGascz004Dto> getLeftMenuList1(HncisCommon info);

	public BgabGascz004Dto getLeftMenuXgsAuth(HncisCommon info);

	public List<BgabGascz004Dto> selectMenuComboList();

	public String selectApproveLevel(CommonApproval vo);

	public String selectApproveStepLevel(CommonApproval vo);

	public int insertLoginUserLog(BgabGascz002Dto dto);

	public int insertEventToReply(BgabGascz020Dto dto);

	public List<BgabGascz017Dto> selectEventQuizList(BgabGascz017Dto dto);

	public int insertEventQuizAnswer(List<BgabGascz017Dto> list);

	public String selectEventQuizAnswerMaxGroupSeq(BgabGascz017Dto vo);

	public CommonApproval getRoomsNextApprovalInfo(CommonApproval commonApproval);

	public int signup(BgabGascz030Dto bgabGascz030Dto);

	public int updateMenuListToRequest(BgabGascz030Dto bgabGascz030Dto);

	public int checkDuplicateCrop(BgabGascz030Dto bgabGascz030Dto);

	public String selectUserPwInfo(BgabGascz002Dto dto);

	public String selectUserMailAddr(BgabGascz002Dto dto);

	public int updateUserPwAuthKay(BgabGascz002Dto dto);

	public int getCheckAuthKeyForPw(BgabGascz002Dto dto);

	public int updateToResetPassword(BgabGascz002Dto dto);

	public int selectCorpNameListCount(BgabGascz030Dto dto);

	public List<BgabGascz030Dto> selectCorpNameList(BgabGascz030Dto dto);

	BgabGascz030Dto selectXst30Info(BgabGascz030Dto dto);

	public int getSelectCountUserCodeByCommon(HncisCommon paramDto);

	public List<HncisCommon> getSelectUserCodeByCommon(HncisCommon paramDto);

	public List<HncisCommon> getSelectUserCodeByCommon1(HncisCommon paramDto);

	public int insertBizUseYnList(List<BgabGascz031Dto> list);

	public int updateBizUseYnList(List<BgabGascz031Dto> list);

	int selectApprovalMaxLevelInfo(CommonApproval commonApproval);

	public String getSelectCorpChkCount(BgabGascz030Dto dto);

	public String getSelectLoc(String corp_cd);
	
	public int updateToResetLocale(BgabGascz002Dto dto);

	public String getLocale(String corp_cd);
	
	public String getUserLocale(BgabGascz002Dto dto);

	public BgabGascz035Dto getSelectCorpInfo(BgabGascz035Dto dto);
	
	public List<BpmInfo> getSelectBpmList(BpmInfo vo);
}
