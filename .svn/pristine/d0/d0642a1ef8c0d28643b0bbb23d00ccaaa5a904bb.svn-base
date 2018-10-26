package com.hncis.restCenter.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.gift.vo.BgabGascgf05Dto;
import com.hncis.restCenter.dao.RestCenterDao;
import com.hncis.restCenter.vo.BgabGascrc01Dto;
import com.hncis.restCenter.vo.BgabGascrc02Dto;
import com.hncis.restCenter.vo.BgabGascrc03Dto;
import com.hncis.restCenter.vo.BgabGascrc04Dto;
import com.hncis.restCenter.vo.BgabGascrc05Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;

public class RestCenterDaoImplByIBatis extends CommonDao implements RestCenterDao{

	private final String SELECT_RC_TO_REST_CENTER_COMBO			 	 = "selectRcToRestCenterCombo";
	private final String SELECT_RC_TO_ROOM_COMBO			 		 = "selectRcToRoomCombo";
	
	private final String INSERT_RC_TO_REST_CENTER_LIST               = "insertRcToRestCenterList";
	private final String UPDATE_RC_TO_REST_CENTER_LIST               = "updateRcToRestCenterList";
	private final String SELECT_RC_LIST_TO_REST_CENTER           	 = "selectRcListToRestCenter";
	private final String INSERT_RC_TO_CAL_LIST               		 = "insertRcToCalList";
	private final String UPDATE_RC_TO_CAL_LIST               		 = "updateRcToCalList";
	private final String SELECT_RC_LIST_TO_CAL			           	 = "selectRcListToCal";
	private final String INSERT_RC_TO_ROOM_LIST               		 = "insertRcToRoomList";
	private final String UPDATE_RC_TO_ROOM_LIST               		 = "updateRcToRoomList";
	private final String SELECT_RC_LIST_TO_ROOM			           	 = "selectRcListToRoom";
	private final String INSERT_RC_TO_RATE_LIST               		 = "insertRcToRateList";
	private final String UPDATE_RC_TO_RATE_LIST               		 = "updateRcToRateList";
	private final String SELECT_RC_LIST_TO_RATE			           	 = "selectRcListToRate";
	private final String DELETE_RC_TO_REST_CENTER_LIST			     = "deleteRcToRestCenterList";
	private final String DELETE_RC_TO_CAL_LIST			           	 = "deleteRcToCalList";
	private final String DELETE_RC_TO_ROOM_LIST			           	 = "deleteRcToRoomList";
	private final String DELETE_RC_TO_RATE_LIST			           	 = "deleteRcToRateList";
	private final String SELECT_COUNT_RC_TO_AMT_CHECK				 = "selectCountRcToAmtCheck";
	
	private final String SELECT_RC_TO_REQUEST_COUNT_INFO			 = "selectRcToRequestCountInfo";
	private final String UPDATE_TO_REQUEST_COUNT_INFO			     = "updateToRequestCountInfo";
	
	private final String SELECT_RC_TO_REMAIN_DAYS_INFO			     = "selectRcToRemainDaysInfo";
	private final String SELECT_RC_TO_USE_AMT			    	 	 = "selectRcToUseAmt";
	
	private final String ISNERT_RC_TO_REQUEST_INFO			     	 = "isnertRcToRequestInfo";
	private final String SELECT_RC_TO_REQUEST_INFO			    	 = "selectRcToRequestInfo";
	private final String SELECT_RC_TO_REQUEST_INFO_BY_IF_ID	    	 = "selectRcToRequestInfoByIfId";
	
	private final String SELECT_COUNT_RC_TO_REQ_LIST			     = "selectCountRcToReqList";
	private final String SELECT_RC_TO_REQ_LIST			    	 	 = "selectRcToReqList";
	private final String SELECT_RC_TO_HISTORY_LIST		    	 	 = "selectRcToHistoryList";
	private final String DELETE_RC_TO_REQUEST		    	 	 	 = "deleteRcToRequest";
	private final String UPDATE_RC_TO_REQUEST_FOR_APPROVE		     = "updateRcToRequestForApprove";
	private final String UPDATE_RC_TO_REQUEST_FOR_APPROVE_CANCEL	 = "updateRcToRequestForApproveCancel";
	private final String UPDATE_RC_TO_REQUEST_FOR_CONFIRM		     = "updateRcToRequestForConfirm";
	private final String UPDATE_RC_TO_REQUEST_FOR_REJECT		     = "updateRcToRequestForReject";
	
	
	@Override
	public List<BgabGascrc01Dto> selectRcToRestCenterCombo(BgabGascrc01Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_TO_REST_CENTER_COMBO, vo);
	}
	@Override
	public List<BgabGascrc03Dto> selectRcToRoomCombo(BgabGascrc03Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_TO_ROOM_COMBO, vo);
	}
	
	
	@Override
	public int insertRcToRestCenterList(List<BgabGascrc01Dto> iList) {
		return insert(INSERT_RC_TO_REST_CENTER_LIST, iList);
	}
	@Override
	public int updateRcToRestCenterList(List<BgabGascrc01Dto> uList) {
		return update(UPDATE_RC_TO_REST_CENTER_LIST, uList);
	}
	@Override
	public List<BgabGascrc01Dto> selectRcListToRestCenter(BgabGascrc01Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_LIST_TO_REST_CENTER, vo);
	}
	
	@Override
	public int insertRcToCalList(List<BgabGascrc02Dto> iList) {
		return insert(INSERT_RC_TO_CAL_LIST, iList);
	}
	@Override
	public int updateRcToCalList(List<BgabGascrc02Dto> uList) {
		return update(UPDATE_RC_TO_CAL_LIST, uList);
	}
	@Override
	public List<BgabGascrc02Dto> selectRcListToCal(BgabGascrc02Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_LIST_TO_CAL, vo);
	}
	
	@Override
	public int insertRcToRoomList(List<BgabGascrc03Dto> iList) {
		return insert(INSERT_RC_TO_ROOM_LIST, iList);
	}
	@Override
	public int updateRcToRoomList(List<BgabGascrc03Dto> uList) {
		return update(UPDATE_RC_TO_ROOM_LIST, uList);
	}
	@Override
	public List<BgabGascrc03Dto> selectRcListToRoom(BgabGascrc03Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_LIST_TO_ROOM, vo);
	}
	
	@Override
	public int insertRcToRateList(List<BgabGascrc04Dto> iList) {
		return insert(INSERT_RC_TO_RATE_LIST, iList);
	}
	@Override
	public int updateRcToRateList(List<BgabGascrc04Dto> uList) {
		return update(UPDATE_RC_TO_RATE_LIST, uList);
	}
	@Override
	public List<BgabGascrc04Dto> selectRcListToRate(BgabGascrc04Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_LIST_TO_RATE, vo);
	}
	@Override
	public int deleteRcToRestCenterList(List<BgabGascrc01Dto> dList) {
		return delete(DELETE_RC_TO_REST_CENTER_LIST, dList);
	}
	@Override
	public int deleteRcToCalList(List<BgabGascrc02Dto> dList) {
		return delete(DELETE_RC_TO_CAL_LIST, dList);
	}
	@Override
	public int deleteRcToRoomList(List<BgabGascrc03Dto> dList) {
		return delete(DELETE_RC_TO_ROOM_LIST, dList);
	}
	@Override
	public int deleteRcToRateList(List<BgabGascrc04Dto> dList) {
		return delete(DELETE_RC_TO_RATE_LIST, dList);
	}
	@Override
	public int selectCountRcToAmtCheck(BgabGascrc06Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RC_TO_AMT_CHECK, dto));
	}
	
	
	
	@Override
	public BgabGascrc05Dto selectRcToRequestCountInfo(BgabGascrc05Dto dto) {
		return (BgabGascrc05Dto)getSqlMapClientTemplate().queryForObject(SELECT_RC_TO_REQUEST_COUNT_INFO, dto);
	}
	@Override
	public int updateToRequestCountInfo(BgabGascrc05Dto dto) {
		return update(UPDATE_TO_REQUEST_COUNT_INFO, dto);
	}
	
	
	@Override
	public BgabGascrc06Dto selectRcToRemainDaysInfo(BgabGascrc06Dto dto) {
		return (BgabGascrc06Dto)getSqlMapClientTemplate().queryForObject(SELECT_RC_TO_REMAIN_DAYS_INFO, dto);
	}
	
	@Override
	public BgabGascrc06Dto selectRcToUseAmt(BgabGascrc06Dto dto) {
		return (BgabGascrc06Dto)getSqlMapClientTemplate().queryForObject(SELECT_RC_TO_USE_AMT, dto);
	}
	
	@Override
	public int isnertRcToRequestInfo(BgabGascrc06Dto dto) {
		return update(ISNERT_RC_TO_REQUEST_INFO, dto);
	}
	@Override
	public BgabGascrc06Dto selectRcToRequestInfo(BgabGascrc06Dto dto) {
		return (BgabGascrc06Dto)getSqlMapClientTemplate().queryForObject(SELECT_RC_TO_REQUEST_INFO, dto);
	}
	@Override
	public BgabGascrc06Dto selectRcToRequestInfoByIfId(BgabGascrc06Dto dto) {
		return (BgabGascrc06Dto)getSqlMapClientTemplate().queryForObject(SELECT_RC_TO_REQUEST_INFO_BY_IF_ID, dto);
	}
	@Override
	public int selectCountRcToReqList(BgabGascrc06Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RC_TO_REQ_LIST, dto));
	}
	@Override
	public List<BgabGascrc06Dto> selectRcToReqList(BgabGascrc06Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_TO_REQ_LIST, dto);
	}
	@Override
	public List<BgabGascrc06Dto> selectRcToHistoryList(BgabGascrc06Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RC_TO_HISTORY_LIST, dto);
	}
	@Override
	public int deleteRcToRequest(BgabGascrc06Dto dto) {
		return update(DELETE_RC_TO_REQUEST, dto);
	}
	@Override
	public int updateRcToRequestForApprove(BgabGascrc06Dto dto) {
		return update(UPDATE_RC_TO_REQUEST_FOR_APPROVE, dto);
	}
	@Override
	public int updateRcToRequestForApproveCancel(BgabGascrc06Dto dto) {
		return update(UPDATE_RC_TO_REQUEST_FOR_APPROVE_CANCEL, dto);
	}
	@Override
	public int updateRcToRequestForConfirm(BgabGascrc06Dto dto) {
		return update(UPDATE_RC_TO_REQUEST_FOR_CONFIRM, dto);
	}
	@Override
	public int updateRcToRequestForReject(BgabGascrc06Dto dto) {
		return update(UPDATE_RC_TO_REQUEST_FOR_REJECT, dto);
	}

}
