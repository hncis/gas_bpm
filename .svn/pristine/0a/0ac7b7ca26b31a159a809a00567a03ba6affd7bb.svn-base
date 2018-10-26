package com.hncis.leave.dao;

import java.util.List;

import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.leave.vo.BgabGasclv01Dto;
import com.hncis.leave.vo.BgabGasclv02Dto;
import com.hncis.leave.vo.BgabGasclv03Dto;

public interface LeaveDao {

	BgabGasclv01Dto selectLvToJoinYearsInfo(BgabGasclv01Dto dto);
	BgabGasclv01Dto selectLvToRemainDaysInfo(BgabGasclv01Dto dto);
	int selectLvToUseDaysInfo(BgabGasclv01Dto dto);
	int selectLvToUseDaysInfo1(BgabGasclv01Dto dto);
	int insertLvToRequestInfo(BgabGasclv01Dto dto);
	int deleteLvToRequestInfoDtl(List<BgabGasclv02Dto> dtlDto);
	int insertToRequestInfoDtl(List<BgabGasclv02Dto> dtlDto);
	int updateInfoLvToRequestCancel(BgabGasclv01Dto keyParamDto);
	int updateInfoLvToRequestCancel2(BgabGasclv01Dto keyParamDto);
	BgabGasclv01Dto selectLvToRequestInfo(BgabGasclv01Dto dto);
	List<BgabGasclv01Dto> selectLvToHistoryList(BgabGasclv01Dto dto);
	int deleteLvToRequest(BgabGasclv01Dto dto);
	int deleteLvToRequestDtl(BgabGasclv01Dto dto);
	int updateLvToRequestChrg(BgabGasclv01Dto keyParamDto);
	
	int selectCountLvToReqList(BgabGasclv01Dto dto);
	List<BgabGasclv01Dto> selectLvToReqList(BgabGasclv01Dto dto);
	
	int updateLvToLeaveDayInfo(BgabGasclv03Dto dto);
	BgabGasclv03Dto selectLvToLeaveDayInfo(BgabGasclv03Dto dto);
	int selectCountLvToLeaveDayInfo(BgabGasclv03Dto dto);
	int insertLvToLeaveDayInfo(BgabGasclv03Dto dto);
	public int updateLvToRequestForConfirm(BgabGasclv01Dto dto);
	public int updateLvToRequestForReject(BgabGasclv01Dto dto);
	
	

}
