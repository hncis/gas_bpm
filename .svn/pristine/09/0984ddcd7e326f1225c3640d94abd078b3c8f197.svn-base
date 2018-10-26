package com.hncis.familyJob.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.familyJob.vo.BgabGascfj01Dto;
import com.hncis.familyJob.vo.BgabGascfj02Dto;


public interface FamilyJobManager {
	public List<BgabGascfj02Dto> selectGbListToFamilyJob(BgabGascfj02Dto vo);
	public int saveRcToGbListToFamilyJob(List<BgabGascfj02Dto> list);
	public int deleteRcToGbListToFamilyJob(List<BgabGascfj02Dto> list);
	public List<BgabGascfj02Dto> selectRelListToFamilyJob(BgabGascfj02Dto vo);
	public int saveRcToRelListToFamilyJob(List<BgabGascfj02Dto> list);
	public int deleteRcToRelListToFamilyJob(List<BgabGascfj02Dto> list);
	public List<BgabGascfj02Dto> selectToFamilyJobCombo(BgabGascfj02Dto vo);
	public List<BgabGascfj02Dto> selectToFamilyJobCombo2(BgabGascfj02Dto vo);
	public int saveToFamilyJob(BgabGascfj01Dto vo);
	public int deleteToFamilyJob(BgabGascfj01Dto vo);
	public BgabGascfj01Dto selectToFamilyJob(BgabGascfj01Dto vo);
	public int selectToFamilyJobListCount(BgabGascfj01Dto vo);
	public List<BgabGascfj01Dto> selectToFamilyJobList(BgabGascfj01Dto vo);
	
	public void saveFjToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectFjToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectFjToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteFjToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
	
	CommonMessage updateFjToRequestForApprove(BgabGascfj01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	CommonMessage updateFjToRequestForApproveCancel(BgabGascfj01Dto dto);
	CommonMessage setApprovalCancel(BgabGascfj01Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;
	CommonMessage updateFjToRequestForConfirm(BgabGascfj01Dto dto);
	CommonMessage updateFjToRequestForReject(BgabGascfj01Dto dto);
}
