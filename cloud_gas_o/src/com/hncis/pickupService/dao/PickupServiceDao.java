package com.hncis.pickupService.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.pickupService.vo.BgabGascps01Dto;
import com.hncis.pickupService.vo.BgabGascps02Dto;
import com.hncis.pickupService.vo.BgabGascps03Dto;
import com.hncis.pickupService.vo.BgabGascps04Dto;
import com.hncis.pickupService.vo.BgabGascps05Dto;
import com.hncis.pickupService.vo.PickupServiceDto;

public interface PickupServiceDao {

	public List<PickupServiceDto> getComboListFromPlace(PickupServiceDto code);
	public List<PickupServiceDto> getComboListToPlace(PickupServiceDto code);
	
	public int insertPsToRequest(BgabGascps01Dto dto);
	public int insertPsToRequestList(List<BgabGascps02Dto> list);
	public BgabGascps01Dto getSelectInfoPsToRequest(BgabGascps01Dto dto);
	public List<BgabGascps02Dto> getSelectListPsToRequest(BgabGascps02Dto dto);
	public int deletePsToRequest(BgabGascps01Dto dto);
	public int deletePsToRequestList(BgabGascps01Dto dto);
	public int updateInfoPsToApproval(BgabGascps01Dto dto);
	public int updateInfoPsToConfirm(BgabGascps01Dto dto);
	public int updateInfoPsToConfirmCancel(BgabGascps01Dto dto);
	public int updateInfoPsToReject(BgabGascps01Dto dto);
	public String getSelectApprovalInfoByPs(BgabGascps01Dto rsReqDto);
	public int deleteScheduleToRequest(List<BgabGascps02Dto> dto);
	public int updateScheduleToRequest(BgabGascps02Dto dto);
	public BgabGascps03Dto getSelectInfoPsToPickupAmount(BgabGascps03Dto dto);
	public String selectPsToDeptHoldBudget(BgabGascps01Dto dto);
	
	public int getSelectCountPsToList(BgabGascps01Dto dto);
	public List<BgabGascps01Dto> getSelectListPsToList(BgabGascps01Dto dto);
	
	public int getSelectCountPsToConfirm(BgabGascps01Dto dto);
	public List<BgabGascps01Dto> getSelectListPsToConfirm(BgabGascps01Dto dto);
	public int updatePsToReportBySapYn(List<BgabGascps01Dto> reportDataList);
	public int updatePsToCancelSapData(List<BgabGascps01Dto> list);
	public int getCheckPsToSrlNo(BgabGascps01Dto dto);
	
	int getSelectCountPsToAgencyManagement(BgabGascps05Dto dto);
	List<BgabGascps05Dto> getSelectListPsToAgencyManagement(BgabGascps05Dto dto);
	int insertPsToAgencyManagement(List<BgabGascps05Dto> dto);
	int deletePsToAgencyManagement(List<BgabGascps05Dto> dto);
	
	int getSelectCountPsToPlaceManagement(BgabGascps03Dto dto);
	List<BgabGascps03Dto> getSelectListPsToPlaceManagement(BgabGascps03Dto dto);
	int insertPsToPlaceManagement(List<BgabGascps03Dto> dto);
	int deletePsToPlaceManagement(List<BgabGascps03Dto> dto);
	
	public BgabGascps01Dto getSelectInfoPsToRequestForApprove(BgabGascps01Dto dto);
	public List<BgabGascps02Dto> getSelectListPsToRequestForApprove(BgabGascps02Dto dto);
	
	public int insertPsToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectPsToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectPsToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deletePsToFile(List<BgabGascZ011Dto> BgabGascZ011Dto);
	
	public int selectCountPsToPickupSchedule(BgabGascps02Dto dto);
	public List<BgabGascps02Dto> selectListPsToPickupSchedule(BgabGascps02Dto dto);
	
	public List<BgabGascps04Dto> selectPsDriverInfoToRequestList(BgabGascps04Dto dto);
	public int insertPsDriverInfoToRequestList (List<BgabGascps04Dto> list);
	public int deletePsDriverInfoToRequestList (BgabGascps04Dto dto);
	
	public int updatePickupServicePoInfo(BgabGascps01Dto dto);
	public int updatePickupServiceFinalPoInfo(BgabGascps04Dto dto);
	public int approveCancelPSToRequest(BgabGascps01Dto dto);
}
