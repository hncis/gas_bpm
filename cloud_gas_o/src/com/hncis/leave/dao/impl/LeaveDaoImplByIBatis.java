package com.hncis.leave.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.leave.dao.LeaveDao;
import com.hncis.leave.vo.BgabGasclv01Dto;
import com.hncis.leave.vo.BgabGasclv02Dto;
import com.hncis.leave.vo.BgabGasclv03Dto;

public class LeaveDaoImplByIBatis extends CommonDao implements LeaveDao{

	private final String SELECT_LV_TO_JOIN_YEARS_INFO			 = "selectLvToJoinYearsInfo";
	private final String SELECT_LV_TO_REMAIN_DAYS_INFO			 = "selectLvToRemainDaysInfo";
	private final String SELECT_LV_TO_USE_DAYS_INFO				 = "selectLvToUseDaysInfo";
	private final String SELECT_LV_TO_USE_DAYS_INFO1			 = "selectLvToUseDaysInfo1";
	
	private final String INSERT_LV_TO_REQUEST_INFO			 	 = "insertLvToRequestInfo";
	private final String DELETE_LV_TO_REQUEST_INFO_DTL			 = "deleteLvToRequestInfoDtl";
	private final String ISNERT_LV_TO_REQUEST_INFO_DTL			 = "insertToRequestInfoDtl";
	private final String SELECT_LV_TO_REQUEST_INFO			 	 = "selectLvToRequestInfo";
	private final String SELECT_LV_TO_HISTORY_LIST			 	 = "selectLvToHistoryList";
	private final String UPDATE_INFO_LV_TO_REQUEST_CANCEL		 = "updateInfoLvToRequestCancel";
	private final String UPDATE_INFO_LV_TO_REQUEST_CANCEL2		 = "updateInfoLvToRequestCancel2";
	private final String DELETE_LV_TO_REQUEST		 			 = "deleteLvToRequest";
	private final String DELETE_LV_TO_REQUEST_DTL		 	 	 = "deleteLvToRequestDtl";
	private final String UPDATE_LV_TO_REQUEST_CHRG		 	 	 = "updateLvToRequestChrg";
	
	private final String SELECT_COUNT_LV_TO_REQ_LIST			 = "selectCountLvToReqList";
	private final String SELECT_LV_TO_REQ_LIST			    	 = "selectLvToReqList";
	
	
	private final String SELECT_LV_TO_LEAVE_DAY_INFO			 = "selectLvToLeaveDayInfo";
	private final String UPDATE_LV_TO_LEAVE_DAY_INFO			 = "updateLvToLeaveDayInfo";
	private final String SELECT_COUNT_LV_TO_LEAVE_DAY_INFO		 = "selectCountLvToLeaveDayInfo";
	private final String INSERT_LV_TO_LEAVE_DAY_INFO			 = "insertLvToLeaveDayInfo";
	
	
	@Override
	public BgabGasclv01Dto selectLvToJoinYearsInfo(BgabGasclv01Dto dto) {
		return (BgabGasclv01Dto)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_JOIN_YEARS_INFO, dto);
	}
	@Override
	public BgabGasclv01Dto selectLvToRemainDaysInfo(BgabGasclv01Dto dto) {
		return (BgabGasclv01Dto)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_REMAIN_DAYS_INFO, dto);
	}
	@Override
	public int selectLvToUseDaysInfo(BgabGasclv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_USE_DAYS_INFO, dto));
	}
	@Override
	public int selectLvToUseDaysInfo1(BgabGasclv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_USE_DAYS_INFO1, dto));
	}
	
	@Override
	public int insertLvToRequestInfo(BgabGasclv01Dto dto) {
		return insert(INSERT_LV_TO_REQUEST_INFO, dto);
	}
	@Override
	public int deleteLvToRequestInfoDtl(List<BgabGasclv02Dto> dtlDto) {
		return delete(DELETE_LV_TO_REQUEST_INFO_DTL, dtlDto);
	}
	@Override
	public int insertToRequestInfoDtl(List<BgabGasclv02Dto> dtlDto) {
		return insert(ISNERT_LV_TO_REQUEST_INFO_DTL, dtlDto);
	}
	
	@Override
	public int updateInfoLvToRequestCancel(BgabGasclv01Dto dtlDto) {
		return update(UPDATE_INFO_LV_TO_REQUEST_CANCEL, dtlDto);
	}
	
	@Override
	public int updateInfoLvToRequestCancel2(BgabGasclv01Dto dtlDto) {
		return update(UPDATE_INFO_LV_TO_REQUEST_CANCEL2, dtlDto);
	}
	
	@Override
	public BgabGasclv01Dto selectLvToRequestInfo(BgabGasclv01Dto dto) {
		return (BgabGasclv01Dto)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_REQUEST_INFO, dto);
	}
	@Override
	public List<BgabGasclv01Dto> selectLvToHistoryList(BgabGasclv01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LV_TO_HISTORY_LIST, dto);
	}
	@Override
	public int deleteLvToRequest(BgabGasclv01Dto dtlDto) {
		return delete(DELETE_LV_TO_REQUEST, dtlDto);
	}
	@Override
	public int deleteLvToRequestDtl(BgabGasclv01Dto dtlDto) {
		return delete(DELETE_LV_TO_REQUEST_DTL, dtlDto);
	}
	@Override
	public int updateLvToRequestChrg(BgabGasclv01Dto dtlDto) {
		return update(UPDATE_LV_TO_REQUEST_CHRG, dtlDto);
	}
	
	
	@Override
	public int selectCountLvToReqList(BgabGasclv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_LV_TO_REQ_LIST, dto));
	}
	@Override
	public List<BgabGasclv01Dto> selectLvToReqList(BgabGasclv01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LV_TO_REQ_LIST, dto);
	}
	
	@Override
	public BgabGasclv03Dto selectLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		return (BgabGasclv03Dto)getSqlMapClientTemplate().queryForObject(SELECT_LV_TO_LEAVE_DAY_INFO, dto);
	}
	@Override
	public int updateLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		return update(UPDATE_LV_TO_LEAVE_DAY_INFO, dto);
	}
	@Override
	public int selectCountLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_LV_TO_LEAVE_DAY_INFO, dto));
	}
	@Override
	public int insertLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		return insert(INSERT_LV_TO_LEAVE_DAY_INFO, dto);
	}
	@Override
	public int updateLvToRequestForConfirm(BgabGasclv01Dto dto) {
		return insert("updateLvToRequestForConfirm", dto);
	}
	@Override
	public int updateLvToRequestForReject(BgabGasclv01Dto dto) {
		return insert("updateLvToRequestForReject", dto);
	}
	
}
