package com.hncis.restCenter.dao;

import java.util.List;

import com.hncis.restCenter.vo.BgabGascrc01Dto;
import com.hncis.restCenter.vo.BgabGascrc02Dto;
import com.hncis.restCenter.vo.BgabGascrc03Dto;
import com.hncis.restCenter.vo.BgabGascrc04Dto;
import com.hncis.restCenter.vo.BgabGascrc05Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;


public interface RestCenterDao {

	List<BgabGascrc01Dto> selectRcToRestCenterCombo(BgabGascrc01Dto vo);
	List<BgabGascrc03Dto> selectRcToRoomCombo(BgabGascrc03Dto vo);
	
	int insertRcToRestCenterList(List<BgabGascrc01Dto> iList);
	int updateRcToRestCenterList(List<BgabGascrc01Dto> uList);
	List<BgabGascrc01Dto> selectRcListToRestCenter(BgabGascrc01Dto vo);
	int insertRcToCalList(List<BgabGascrc02Dto> iList);
	int updateRcToCalList(List<BgabGascrc02Dto> uList);
	List<BgabGascrc02Dto> selectRcListToCal(BgabGascrc02Dto vo);
	int insertRcToRoomList(List<BgabGascrc03Dto> iList);
	int updateRcToRoomList(List<BgabGascrc03Dto> uList);
	List<BgabGascrc03Dto> selectRcListToRoom(BgabGascrc03Dto vo);
	int insertRcToRateList(List<BgabGascrc04Dto> iList);
	int updateRcToRateList(List<BgabGascrc04Dto> uList);
	List<BgabGascrc04Dto> selectRcListToRate(BgabGascrc04Dto vo);
	int deleteRcToRestCenterList(List<BgabGascrc01Dto> dList);
	int deleteRcToCalList(List<BgabGascrc02Dto> dList);
	int deleteRcToRoomList(List<BgabGascrc03Dto> dList);
	int deleteRcToRateList(List<BgabGascrc04Dto> dList);
	int selectCountRcToAmtCheck(BgabGascrc06Dto dto);
	
	BgabGascrc05Dto selectRcToRequestCountInfo(BgabGascrc05Dto dto);
	int updateToRequestCountInfo(BgabGascrc05Dto dto);
	
	BgabGascrc06Dto selectRcToRemainDaysInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToUseAmt(BgabGascrc06Dto dto);
	int isnertRcToRequestInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToRequestInfo(BgabGascrc06Dto dto);
	BgabGascrc06Dto selectRcToRequestInfoByIfId(BgabGascrc06Dto dto);
	
	int selectCountRcToReqList(BgabGascrc06Dto dto);
	List<BgabGascrc06Dto> selectRcToReqList(BgabGascrc06Dto dto);
	List<BgabGascrc06Dto> selectRcToHistoryList(BgabGascrc06Dto dto);
	int deleteRcToRequest(BgabGascrc06Dto dto);
	int updateRcToRequestForApprove(BgabGascrc06Dto dto);
	int updateRcToRequestForApproveCancel(BgabGascrc06Dto dto);
	int updateRcToRequestForConfirm(BgabGascrc06Dto dto);
	int updateRcToRequestForReject(BgabGascrc06Dto dto);
	

}
