package com.hncis.pickupService.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.pickupService.dao.PickupServiceDao;
import com.hncis.pickupService.vo.BgabGascps01Dto;
import com.hncis.pickupService.vo.BgabGascps02Dto;
import com.hncis.pickupService.vo.BgabGascps03Dto;
import com.hncis.pickupService.vo.BgabGascps04Dto;
import com.hncis.pickupService.vo.BgabGascps05Dto;
import com.hncis.pickupService.vo.PickupServiceDto;

public class PickupServiceDaoImplByIBatis extends CommonDao implements  PickupServiceDao{

	private final String SELECT_COMBO_LIST_FROM_PLACE 			= "selectComboListFromPlace";
	private final String SELECT_COMBO_LIST_TO_PLACE 			= "selectComboListToPlace";
	
	private final String INSERT_PS_TO_REQUEST 					= "insertPsToRequest";
	private final String INSERT_PS_TO_REQUEST_LIST 				= "insertPsToRequestList";
	private final String DELETE_PS_TO_REQUEST 					= "deletePsToRequest";
	private final String DELETE_PS_TO_REQUEST_LIST 				= "deletePsToRequestList";
	private final String SELECT_INFO_PS_TO_REQUEST 				= "selectInfoPsToRequest";
	private final String SELECT_LIST_PS_TO_REQUEST 				= "selectListPsToRequest";
	private final String UPDATE_INFO_PS_TO_CONFIRM 				= "updateInfoPsToConfirm";
	private final String UPDATE_INFO_PS_TO_CONFIRMCANCEL 		= "updateInfoPsToConfirmcancel";
	private final String UPDATE_INFO_PS_TO_REJECT 				= "updateInfoPsToReject";
	private final String UPDATE_INFO_PS_TO_APPROVAL 			= "updateInfoPsToApproval";
	private final String SELECT_APPROVAL_INFO_BY_PS 			= "selectApprovalInfoByPs";
	private final String DELETE_SCHEDULE_TO_REQUEST 			= "deleteScheduleToRequest";
	private final String UPDATE_SCHEDULE_TO_REQUEST 			= "updateScheduleToRequest";
	private final String SELECT_INFO_PS_TO_PICKUP_AMOUNT 		= "selectInfoPsToPickupAmount";
	private final String SELECT_PS_TO_DEPT_HOLD_BUDGET 			= "selectPsToDeptHoldBudget";
	
	private final String SELECT_COUNT_PS_TO_LIST 				= "selectCountPsToList";
	private final String SELECT_LIST_PS_TO_LIST 				= "selectListPsToList";
	
	private final String SELECT_COUNT_PS_TO_CONFIRM 			= "selectCountPsToConfirm";
	private final String SELECT_LIST_PS_TO_CONFIRM 				= "selectListPsToConfirm";
	private final String UPDATE_PS_TO_REPORT_BY_SAP_YN 			= "updatePsToReportBySapYn";
	private final String UPDATE_PS_TO_CANCEL_SAP_DATA 			= "updatePsToCancelSapData";
	private final String SELECT_CHECK_PS_TO_SRL_NO 				= "selectCheckPsToSrlNo";
	
	private final String SELECT_COUNT_PS_TO_AGENCY_MANAGEMENT 	= "selectCountPsToAgencyManagement";
	private final String SELECT_LIST_PS_TO_AGENCY_MANAGEMENT 	= "selectListPsToAgencyManagement";
	private final String INSERT_PS_TO_AGENCY_MANAGEMENT 		= "insertPsToAgencyManagement";
	private final String UPDATE_PS_TO_AGENCY_MANAGEMENT 		= "updatePsToAgencyManagement";
	private final String DELETE_PS_TO_AGENCY_MANAGEMENT 		= "deletePsToAgencyManagement";
	
	private final String SELECT_COUNT_PS_TO_PLACE_MANAGEMENT 	= "selectCountPsToPlaceManagement";
	private final String SELECT_LIST_PS_TO_PLACE_MANAGEMENT 	= "selectListPsToPlaceManagement";
	private final String INSERT_PS_TO_PLACE_MANAGEMENT 			= "insertPsToPlaceManagement";
	private final String UPDATE_PS_TO_PLACE_MANAGEMENT 			= "updatePsToPlaceManagement";
	private final String DELETE_PS_TO_PLACE_MANAGEMENT 			= "deletePsToPlaceManagement";
	
	private final String SELECT_INFO_PS_TO_REQUEST_FOR_APPROVE 	= "selectInfoPsToRequestForApprove";
	private final String SELECT_LIST_PS_TO_REQUEST_FOR_APPROVE 	= "selectListPsToRequestForApprove";
	
	private final String INSERT_PS_TO_FILE						= "insertPsToFile";
	private final String SELECT_PS_TO_FILE						= "selectPsToFile";
	private final String DELETE_PS_TO_FILE						= "deletePsToFile";
	
	private final String SELECT_COUNT_PS_TO_PICKUP_SCHEDULE		= "selectCountPsToPickupSchedule";
	private final String SELECT_LIST_PS_TO_PICKUP_SCHEDULE		= "selectListPsToPickupSchedule";
	
	private final String SELECT_PS_DRIVER_INFO_TO_REQUEST_LIST		= "selectPsDriverInfoToRequestList";
	private final String INSERT_PS_DRIVER_INFO_TO_REQUEST_LIST		= "insertPsDriverInfoToRequestList";
	private final String UPDATE_PS_DRIVER_INFO_TO_REQUEST_LIST		= "updatePsDriverInfoToRequestList";
	private final String DELETE_PS_DRIVER_INFO_TO_REQUEST_LIST		= "deletePsDriverInfoToRequestList";
	
	@Override
	public List<PickupServiceDto> getComboListFromPlace(PickupServiceDto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COMBO_LIST_FROM_PLACE, dto);
	}
	@Override
	public List<PickupServiceDto> getComboListToPlace(PickupServiceDto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COMBO_LIST_TO_PLACE, dto);
	}
	
	@Override
	public BgabGascps01Dto getSelectInfoPsToRequest(BgabGascps01Dto dto) {
		return (BgabGascps01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_PS_TO_REQUEST, dto);
	}
	@Override
	public List<BgabGascps02Dto> getSelectListPsToRequest(BgabGascps02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_REQUEST, dto);
	}
	@Override
	public int insertPsToRequest(BgabGascps01Dto dto) {
		return super.insert(INSERT_PS_TO_REQUEST, dto);
	}
	@Override
	public int insertPsToRequestList(List<BgabGascps02Dto> list) {
		return super.insert(INSERT_PS_TO_REQUEST_LIST, list);
	}
	@Override
	public int deletePsToRequest(BgabGascps01Dto dto) {
		return super.delete(DELETE_PS_TO_REQUEST, dto);
	}
	@Override
	public int deletePsToRequestList(BgabGascps01Dto dto) {
		return super.delete(DELETE_PS_TO_REQUEST_LIST, dto);
	}
	@Override
	public int updateInfoPsToConfirm(BgabGascps01Dto dto) {
		return super.update(UPDATE_INFO_PS_TO_CONFIRM, dto);
	}
	@Override
	public int updateInfoPsToConfirmCancel(BgabGascps01Dto dto) {
		return super.update(UPDATE_INFO_PS_TO_CONFIRMCANCEL, dto);
	}
	@Override
	public int updateInfoPsToReject(BgabGascps01Dto dto) {
		return super.update(UPDATE_INFO_PS_TO_REJECT, dto);
	}
	@Override
	public int updateInfoPsToApproval(BgabGascps01Dto dto) {
		return super.update(UPDATE_INFO_PS_TO_APPROVAL, dto);
	}
	@Override
	public String getSelectApprovalInfoByPs(BgabGascps01Dto rsReqDto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO_BY_PS, rsReqDto);
	}
	@Override
	public int deleteScheduleToRequest(List<BgabGascps02Dto> dto) {
		return super.delete(DELETE_SCHEDULE_TO_REQUEST, dto);
	}
	@Override
	public int updateScheduleToRequest(BgabGascps02Dto dto) {
		return super.delete(UPDATE_SCHEDULE_TO_REQUEST, dto);
	}
	@Override
	public BgabGascps03Dto getSelectInfoPsToPickupAmount(BgabGascps03Dto dto) {
		return (BgabGascps03Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_PS_TO_PICKUP_AMOUNT, dto);
	}
	@Override
	public String selectPsToDeptHoldBudget(BgabGascps01Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_PS_TO_DEPT_HOLD_BUDGET, dto);
	}
	/*************************************************************/
	/** list page                  	          				    **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToList(BgabGascps01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PS_TO_LIST, dto));
	}
	@Override
	public List<BgabGascps01Dto> getSelectListPsToList(BgabGascps01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_LIST, dto);
	}
	
	/*************************************************************/
	/** confirm page                  	       				    **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToConfirm(BgabGascps01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PS_TO_CONFIRM, dto));
	}
	@Override
	public List<BgabGascps01Dto> getSelectListPsToConfirm(BgabGascps01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_CONFIRM, dto);
	}
	@Override
	public int updatePsToReportBySapYn(List<BgabGascps01Dto> reportDataList) {
		return super.update(UPDATE_PS_TO_REPORT_BY_SAP_YN, reportDataList);
	}
	@Override
	public int updatePsToCancelSapData(List<BgabGascps01Dto> list) {
		return super.update(UPDATE_PS_TO_CANCEL_SAP_DATA, list);
	}
	@Override
	public int getCheckPsToSrlNo(BgabGascps01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_CHECK_PS_TO_SRL_NO, dto));
	}
	/*************************************************************/
	/** Agency managerment page                  	            **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToAgencyManagement(BgabGascps05Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PS_TO_AGENCY_MANAGEMENT, dto));
	}

	@Override
	public List<BgabGascps05Dto> getSelectListPsToAgencyManagement(BgabGascps05Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_AGENCY_MANAGEMENT, dto);
	}

	@Override
	public int insertPsToAgencyManagement(List<BgabGascps05Dto> dto) {
		int rtnCnt = 0;
		if("oracle".equals(StringUtil.getDbType())){
			rtnCnt = super.insert(INSERT_PS_TO_AGENCY_MANAGEMENT, dto);
		}else if("mysql".equals(StringUtil.getDbType())){
			for(BgabGascps05Dto vo : dto){
				if("".equals(vo.getSeq())){
					rtnCnt += super.insert(INSERT_PS_TO_AGENCY_MANAGEMENT, dto);
				}else{
					rtnCnt += super.update(UPDATE_PS_TO_AGENCY_MANAGEMENT, dto);	
				}
			}
		}else if("mssql".equals(StringUtil.getDbType())){
			
		}
		return rtnCnt;
	}

	@Override
	public int deletePsToAgencyManagement(List<BgabGascps05Dto> dto) {
		return super.delete(DELETE_PS_TO_AGENCY_MANAGEMENT, dto);
	}
	
	/*************************************************************/
	/** Pick-up Place managerment page                          **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToPlaceManagement(BgabGascps03Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PS_TO_PLACE_MANAGEMENT, dto));
	}

	@Override
	public List<BgabGascps03Dto> getSelectListPsToPlaceManagement(BgabGascps03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_PLACE_MANAGEMENT, dto);
	}

	public int insertPsToPlaceManagement(List<BgabGascps03Dto> dto) {
		int rtnCnt = 0;
		if("oracle".equals(StringUtil.getDbType())){
			rtnCnt = super.insert(INSERT_PS_TO_PLACE_MANAGEMENT, dto);
		}else if("mysql".equals(StringUtil.getDbType())){
			for(BgabGascps03Dto vo : dto){
				if("".equals(vo.getSeq())){
					rtnCnt += super.insert(INSERT_PS_TO_PLACE_MANAGEMENT, dto);
				}else{
					rtnCnt += super.update(UPDATE_PS_TO_PLACE_MANAGEMENT, dto);
				}
			}
		}else if("mssql".equals(StringUtil.getDbType())){
			
		}
		return rtnCnt;
	}

	@Override
	public int deletePsToPlaceManagement(List<BgabGascps03Dto> dto) {
		return super.delete(DELETE_PS_TO_PLACE_MANAGEMENT, dto);
	}
	
	@Override
	public BgabGascps01Dto getSelectInfoPsToRequestForApprove(BgabGascps01Dto dto) {
		return (BgabGascps01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_PS_TO_REQUEST_FOR_APPROVE, dto);
	}
	@Override
	public List<BgabGascps02Dto> getSelectListPsToRequestForApprove(BgabGascps02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_REQUEST_FOR_APPROVE, dto);
	}
	
	public int insertPsToFile(BgabGascZ011Dto dto){
		return super.insert(INSERT_PS_TO_FILE, dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectPsToFile(BgabGascZ011Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_PS_TO_FILE, dto);
	}
	
	public BgabGascZ011Dto getSelectPsToFileInfo(BgabGascZ011Dto dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_PS_TO_FILE, dto);
	}
	
	public int deletePsToFile (List<BgabGascZ011Dto> list){
		return super.delete(DELETE_PS_TO_FILE, list);
	}
	
	public int selectCountPsToPickupSchedule(BgabGascps02Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PS_TO_PICKUP_SCHEDULE, dto));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascps02Dto> selectListPsToPickupSchedule(BgabGascps02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PS_TO_PICKUP_SCHEDULE, dto);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascps04Dto> selectPsDriverInfoToRequestList(BgabGascps04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PS_DRIVER_INFO_TO_REQUEST_LIST, dto);
	}
	@Override
	public int insertPsDriverInfoToRequestList (List<BgabGascps04Dto> list){
		return super.delete(INSERT_PS_DRIVER_INFO_TO_REQUEST_LIST, list);
	}
	@Override
	public int deletePsDriverInfoToRequestList (BgabGascps04Dto dto){
		return super.delete(DELETE_PS_DRIVER_INFO_TO_REQUEST_LIST, dto);
	}
	public int updatePickupServicePoInfo(BgabGascps01Dto dto){
		return update("updatePickupServicePoInfo", dto);
	}
	public int updatePickupServiceFinalPoInfo(BgabGascps04Dto dto){
		return update("updatePickupServiceFinalPoInfo", dto);
	}
	public int approveCancelPSToRequest(BgabGascps01Dto dto){
		return update("approveCancelPSToRequest", dto);
	}
}
