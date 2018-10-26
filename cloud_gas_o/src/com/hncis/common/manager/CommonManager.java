package com.hncis.common.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.exception.impl.SessionException;
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
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz017Dto;
import com.hncis.system.vo.BgabGascz020Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz035Dto;

@Transactional
public interface CommonManager {

	CommonManager commonManager = null;

	/**
	 * 공통 코드 조회
	 */
	public List<CommonCode> getCodeList(CommonCode commonCode);

	/**
	 * 공통 코드 조회
	 */
	public List<CommonCode> getCodeListNotIncludeAply(CommonCode commonCode);

	/**
	 * 업무별 코드조회
	 */
	public JSONBaseArray getDataCombo(CommonCode code);

	/**
	 * 사용자 정보 조회
	 */
	public BgabGascz002Dto getXusrInfo(BgabGascz002Dto bgabGascz002Dto);

	/**
	 * 사용자 세션 생성
	 */
	public void setSessionInfo(HttpServletRequest req, BgabGascz002Dto bgabGascz002Dto);

	public HncisCommon getScrnInfo(HncisCommon hncisCommon);

	/**
	 * 사용자 정보 조회
	 */
	public CommonUserInfo getSelectUserInfo(CommonUserInfo commonUserInfo);

	/**
	 * 사용자 정보 조회
	 */
	public List<CommonUserInfo> getSelectUserInfoList(CommonUserInfo commonUserInfo);

	/**
	 * 사용자 정보 조회
	 */
	public BgabGascz002Dto getSelectUserInfoDetail(BgabGascz002Dto bgabGascz002Dto);

	/**
	 * 부서 정보 조회
	 */
	public BgabGascz003Dto getSelectDeptInfo(BgabGascz003Dto xocxorgDto);

	/**
	 * 달력
	 * @param oduRegnCd
	 * @param ymd
	 */
	public String[] getCalendar(String ymd, String oduRegnCd, HttpServletRequest req, HttpServletResponse res) throws SessionException;
	/**
	 * 결재판 정보
	 * @param ifId
	 */
	public String getSelectApprovalInfo(CommonApproval apprVo);

	/**
	 * 공통코드 조회
	 * @param bgabGascz005Dto
	 * @return
	 */
	public int getSelectCountToCodeManagement(BgabGascz005Dto bgabGascz005Dto);
	public List<BgabGascz005Dto> getSelectListToCodeManagement(BgabGascz005Dto bgabGascz005Dto);

	public List<BgabGascz005Dto> getSelectListToCodeManagementOrderBySort(BgabGascz005Dto bgabGascz005Dto);
	/**
	 * 공통코드입력
	 * @param list
	 * @return
	 */
	public int insertListToCodeManagement(List<BgabGascz005Dto> list);

	/**
	 * 공통코드입력 - code 채번 생성(1,2.3...)
	 * @param list
	 * @return
	 */
	public int insertListToCodeManagementAutoSeq(List<BgabGascz005Dto> list);
	/**
	 * 공통코드 수정
	 * @param list
	 * @return
	 */
	public int updateListToCodeManagement(List<BgabGascz005Dto> list);
	/**
	 * 공통코드 삭제
	 * @param list
	 * @return
	 */
	public int deleteListToCodeManagement(List<BgabGascz005Dto> list);

	public int getSelectCountDeptCodeByCommon(HncisCommon dto);
	public List<HncisCommon> getSelectDeptCodeByCommon(HncisCommon dto);

	public int getSelectCountZipCode(BgabGascz034Dto dto);
	public List<BgabGascz034Dto> getSelectZipCode(BgabGascz034Dto dto);

	public int getSelectCountJobMgmtInfo(BgabGascz007Dto dto);
	public List<BgabGascz007Dto> getSelectJobMgmtInfo(BgabGascz007Dto dto);

	public List<BgabGascz004Dto> selectGNBMenues();

	public List<BgabGascz004Dto> selectMenuComboList();

	public void saveExcelUpload(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto) throws SessionException;

	public int insertLoginUserLog(BgabGascz002Dto dto);

	public int insertEventToReply(BgabGascz020Dto dto);

	public List<BgabGascz017Dto> selectEventQuizList(BgabGascz017Dto dto);

	public int insertEventQuizAnswer(List<BgabGascz017Dto> list);

	public CommonMessage insertFileMgmt(HttpServletRequest req, HttpServletResponse res, String string);

	public int signup(BgabGascz030Dto bgabGascz030Dto);

	public int updateMenuListToRequest(BgabGascz030Dto bgabGascz030Dto);

	public int checkDuplicateCrop(BgabGascz030Dto bgabGascz030Dto);

	public String selectUserPwInfo(BgabGascz002Dto bgabGascz002Dto);

	public String selectUserMailAddr(BgabGascz002Dto bgabGascz002Dto);

	public String selectUserMailAddr(BgabGascz002Dto bgabGascz002Dto, HttpServletRequest req);

	public CommonMessage updateToResetPassword(BgabGascz002Dto bgabGascz002Dto);

	public int selectCorpNameListCount(BgabGascz030Dto dto);

	public List<BgabGascz030Dto> selectCorpNameList(BgabGascz030Dto dto);

	public int getSelectCountUserCodeByCommon(HncisCommon paramDto);

	public List<HncisCommon> getSelectUserCodeByCommon(HncisCommon paramDto);

	public List<HncisCommon> getSelectUserCodeByCommon1(HncisCommon paramDto);
	
	public CommonMessage updateToResetLocale(BgabGascz002Dto bgabGascz002Dto);

	public BgabGascz035Dto getSelectCorpInfo(BgabGascz035Dto dto);

}
