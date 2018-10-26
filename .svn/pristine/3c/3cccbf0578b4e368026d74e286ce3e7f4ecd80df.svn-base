package com.hncis.restCenter.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.restCenter.vo.BgabGascrc01Dto;
import com.hncis.restCenter.vo.BgabGascrc02Dto;
import com.hncis.restCenter.vo.BgabGascrc03Dto;
import com.hncis.restCenter.vo.BgabGascrc04Dto;
import com.hncis.restCenter.vo.BgabGascrc05Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;


public interface RestCenterManager {

	List<BgabGascrc01Dto> selectRcToRestCenterCombo(BgabGascrc01Dto vo);
	List<BgabGascrc03Dto> selectRcToRoomCombo(BgabGascrc03Dto vo);
	
	void saveRcToRestCenterList(List<BgabGascrc01Dto> iList, List<BgabGascrc01Dto> uList);
	List<BgabGascrc01Dto> selectRcListToRestCenter(BgabGascrc01Dto vo);
	void saveRcToCalList(List<BgabGascrc02Dto> iList, List<BgabGascrc02Dto> uList);
	List<BgabGascrc02Dto> selectRcListToCal(BgabGascrc02Dto vo);
	void saveRcToRoomList(List<BgabGascrc03Dto> iList, List<BgabGascrc03Dto> uList);
	List<BgabGascrc03Dto> selectRcListToRoom(BgabGascrc03Dto vo);
	void saveRcToRateList(List<BgabGascrc04Dto> iList, List<BgabGascrc04Dto> uList);
	List<BgabGascrc04Dto> selectRcListToRate(BgabGascrc04Dto vo);
	void deleteRcToRestCenterList(List<BgabGascrc01Dto> dList);
	void deleteRcToCalList(List<BgabGascrc02Dto> dList);
	void deleteRcToRoomList(List<BgabGascrc03Dto> dList);
	void deleteRcToRateList(List<BgabGascrc04Dto> dList);
	
	BgabGascrc05Dto selectRcToRequestCountInfo(BgabGascrc05Dto dto);
	void updateToRequestCountInfo(BgabGascrc05Dto dto);
	
	BgabGascrc06Dto selectRcToRemainDaysInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToUseAmt(BgabGascrc06Dto dto);
	CommonMessage isnertRcToRequestInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToRequestInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToRequestInfoByIfId(BgabGascrc06Dto dto);
	
	int selectCountRcToReqList(BgabGascrc06Dto dto);
	List<BgabGascrc06Dto> selectRcToReqList(BgabGascrc06Dto dto);
	List<BgabGascrc06Dto> selectRcToHistoryList(BgabGascrc06Dto vo);
	CommonMessage deleteRcToRequest(BgabGascrc06Dto dto);
	CommonMessage updateRcToRequestForApprove(BgabGascrc06Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	CommonMessage updateRcToRequestForApproveCancel(BgabGascrc06Dto dto);
	CommonMessage setApprovalCancel(BgabGascrc06Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;
	CommonMessage updateRcToRequestForConfirm(BgabGascrc06Dto dto);
	CommonMessage updateRcToRequestForReject(BgabGascrc06Dto dto);

}
