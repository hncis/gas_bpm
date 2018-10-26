package com.hncis.pickupService.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonSap;
import com.hncis.pickupService.vo.BgabGascps01Dto;
import com.hncis.pickupService.vo.BgabGascps02Dto;
import com.hncis.pickupService.vo.BgabGascps03Dto;
import com.hncis.pickupService.vo.BgabGascps04Dto;
import com.hncis.pickupService.vo.BgabGascps05Dto;
import com.hncis.pickupService.vo.PickupServiceDto;

@Transactional
public interface PickupServiceManager {

	List<PickupServiceDto> getComboListFromPlace(PickupServiceDto code);
	List<PickupServiceDto> getComboListToPlace(PickupServiceDto code);

	BgabGascps01Dto insertPsToRequest(BgabGascps01Dto dto, List<BgabGascps02Dto> list);
	BgabGascps01Dto getSelectInfoPsToRequest(BgabGascps01Dto dto);
	List<BgabGascps02Dto> getSelectListPsToRequest(BgabGascps02Dto dto);
	int deletePsToRequest(BgabGascps01Dto bgabGascps01Dto);
	CommonMessage setApproval(BgabGascps01Dto dto, HttpServletRequest req) throws Exception;
	CommonMessage setApprovalCancel(BgabGascps01Dto dto, HttpServletRequest req);
	int updateInfoPsToConfirm(BgabGascps01Dto dto, List<BgabGascps02Dto> list, HttpServletRequest req, List<BgabGascps04Dto> diList) throws SessionException;
	int updateInfoPsToConfirmCancel(BgabGascps01Dto dto, HttpServletRequest req) throws SessionException;
	CommonMessage updateInfoPsToReject(BgabGascps01Dto dto, HttpServletRequest req) throws SessionException;
	String getSelectApprovalInfoByPs(BgabGascps01Dto rsReqDto);
	int deleteScheduleToRequest(List<BgabGascps02Dto> dto);
	BgabGascps03Dto getSelectInfoPsToPickupAmount(BgabGascps03Dto dto);

	int getSelectCountPsToList(BgabGascps01Dto bgabGascps01Dto);
	List<BgabGascps01Dto> getSelectListPsToList(BgabGascps01Dto bgabGascps01Dto);

	int getSelectCountPsToConfirm(BgabGascps01Dto bgabGascps01Dto);
	List<BgabGascps01Dto> getSelectListPsToConfirm(BgabGascps01Dto bgabGascps01Dto);
	List<CommonSap> getExpenseExcelList(List<BgabGascps01Dto> reportDataList);
	int updatePsToCancelSapData(List<BgabGascps01Dto> list);
	int getCheckPsToSrlNo(BgabGascps01Dto dto);
	Boolean setExpenseTemplatMake(BgabGascps01Dto dto, List<BgabGascps01Dto> list, HttpServletRequest req) throws SessionException;

	int getSelectCountPsToAgencyManagement(BgabGascps05Dto dto);
	List<BgabGascps05Dto> getSelectListPsToAgencyManagement(BgabGascps05Dto dto);
	int insertPsToAgencyManagement(List<BgabGascps05Dto> dto);
	int deletePsToAgencyManagement(List<BgabGascps05Dto> dto);

	int getSelectCountPsToPlaceManagement(BgabGascps03Dto dto);
	List<BgabGascps03Dto> getSelectListPsToPlaceManagement(BgabGascps03Dto dto);
	int insertPsToPlaceManagement(List<BgabGascps03Dto> dto);
	int deletePsToPlaceManagement(List<BgabGascps03Dto> dto);

	BgabGascps01Dto getSelectInfoPsToRequestForApprove(BgabGascps01Dto dto);
	List<BgabGascps02Dto> getSelectListPsToRequestForApprove(BgabGascps02Dto dto);

	void savePsToFile(HttpServletRequest req, HttpServletResponse res,BgabGascZ011Dto bgabGascZ011Dto);
	List<BgabGascZ011Dto> getSelectPsToFile(BgabGascZ011Dto btReqDto);
	BgabGascZ011Dto getSelectPsToFileInfo(BgabGascZ011Dto btReqDto);
	int deletePsToFile(List<BgabGascZ011Dto> BgabGascZ011Dto);

	public int selectCountPsToPickupSchedule(BgabGascps02Dto dto);
	public List<BgabGascps02Dto> selectListPsToPickupSchedule(BgabGascps02Dto dto);

	public List<BgabGascps04Dto> selectPsDriverInfoToRequestList(BgabGascps04Dto dto);
//	public int savePsDriverInfoToRequestList (List<BgabGascps04Dto> iList, List<BgabGascps04Dto> uList);
	public int deletePsDriverInfoToRequestList (BgabGascps04Dto dto);
	public CommonMessage updateCompletePickUpPo(BgabGascps04Dto dto);
}
