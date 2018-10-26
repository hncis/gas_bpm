package com.hncis.entranceMeal.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.entranceMeal.dao.EntranceMealDao;
import com.hncis.entranceMeal.vo.BgabGascem01Dto;
import com.hncis.entranceMeal.vo.BgabGascem02Dto;
import com.hncis.pickupService.vo.BgabGascps01Dto;

public class EntranceMealDaoImplByIBatis  extends CommonDao implements EntranceMealDao{
	
	//entrance&meal request page
	private final String INSERT_EM_TO_REQUEST_BASIC 				= "insertEmToRequestBasic";
	private final String INSERT_EM_TO_REQUEST_VISIT 				= "insertEmToRequestVisit";
	private final String SELECT_INFO_EM_TO_REQUEST 					= "selectInfoEmToRequest";
	private final String SELECT_LIST_EM_TO_REQUEST 					= "selectListEmToRequest";
	private final String DELETE_VISITORS_EM_TO_REQUEST 				= "deleteVisitorsEmToRequest";
	private final String DELETE_EM_TO_REQUEST 						= "deleteEmToRequest";
	private final String SELECT_COUNT_EM_TO_REQUEST_VISITORS 		= "selectCountEmToRequestVisitors";
	private final String DELETE_EM_TO_REQUEST_VISITORS 				= "deleteEmToRequestVisitors";
	private final String UPDATE_INFO_EM_TO_CONFIRM 					= "updateInfoEmToConfirm";
	private final String UPDATE_INFO_EM_TO_CONFIRMCANCEL 			= "updateInfoEmToConfirmcancel";
	private final String UPDATE_INFO_EM_TO_APPROVAL 				= "updateInfoEmToApproval";
	private final String SELECT_APPROVAL_INFO_BY_EM 				= "selectApprovalInfoByEm";
	private final String SELECT_INFO_EM_TO_REQUEST_FOR_APPROVE 		= "selectInfoEmToRequestForApprove";
	private final String SELECT_LIST_EM_TO_REQUEST_FOR_APPROVE 		= "selectListEmToRequestForApprove";
	
	//list page
	private final String SELECT_COUNT_EM_TO_LIST 					= "selectCountEmToList";
	private final String SELECT_LIST_EM_TO_LIST 					= "selectListEmToList";
	
	//meal list page
	private final String SELECT_COUNT_EM_TO_LIST_FOR_MEAL 			= "selectCountEmToListForMeal";
	private final String SELECT_LIST_EM_TO_LIST_FOR_MEAL 			= "selectListEmToListForMeal";
	
	//entrance list page
	private final String SELECT_COUNT_EM_TO_LIST_FOR_ENTRANCE 		= "selectCountEmToListForEntrance";
	private final String SELECT_LIST_EM_TO_LIST_FOR_ENTRANCE 		= "selectListEmToListForEntrance";
	
	//list page for worker
	private final String SELECT_COUNT_EM_TO_LIST_FOR_WORKER 		= "selectCountEmToListForWorker";
	private final String SELECT_LIST_EM_TO_LIST_FOR_WORKER 			= "selectListEmToListForWorker";
	
	/*************************************************************/
	/** entrance&meal request page                              **/
	/*************************************************************/
	@Override
	public int insertEmToRequestBasic(BgabGascem01Dto vo) {
		return super.insert(INSERT_EM_TO_REQUEST_BASIC, vo);
	}
	@Override
	public int insertEmToRequestVisit(BgabGascem02Dto vo) {
		return super.insert(INSERT_EM_TO_REQUEST_VISIT, vo);
	}
	@Override
	public BgabGascem01Dto getSelectInfoEmToRequest(BgabGascem01Dto dto) {
		return (BgabGascem01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_EM_TO_REQUEST, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToRequest(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_REQUEST, dto);
	}
	@Override
	public int deleteVisitorsEmToRequest(BgabGascem02Dto dto) {
		return super.delete(DELETE_VISITORS_EM_TO_REQUEST, dto);
	}
	@Override
	public int deleteEmToRequest(BgabGascem01Dto bgabGascem01Dto) {
		return super.delete(DELETE_EM_TO_REQUEST, bgabGascem01Dto);
	}
	@Override
	public int selectCountEmToRequestVisitors(BgabGascem01Dto bgabGascem01Dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EM_TO_REQUEST_VISITORS, bgabGascem01Dto));
	}
	@Override
	public int deleteEmToRequestVisitors(BgabGascem01Dto bgabGascem01Dto) {
		return super.delete(DELETE_EM_TO_REQUEST_VISITORS, bgabGascem01Dto);
	}
	@Override
	public int updateInfoEmToConfirm(BgabGascem01Dto dto) {
		return super.update(UPDATE_INFO_EM_TO_CONFIRM, dto);
	}
	@Override
	public int updateInfoEmToConfirmCancel(BgabGascem01Dto dto) {
		return super.update(UPDATE_INFO_EM_TO_CONFIRMCANCEL, dto);
	}
	@Override
	public int updateInfoEmToApproval(BgabGascem01Dto dto) {
		return super.update(UPDATE_INFO_EM_TO_APPROVAL, dto);
	}
	@Override
	public String getSelectApprovalInfoByEm(BgabGascem01Dto rsReqDto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO_BY_EM, rsReqDto);
	}
	@Override
	public BgabGascem01Dto getSelectInfoEmToRequestForApprove(BgabGascem01Dto dto) {
		return (BgabGascem01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_EM_TO_REQUEST_FOR_APPROVE, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToRequestForApprove(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_REQUEST_FOR_APPROVE, dto);
	}
	/*************************************************************/
	/** list page                                          		**/
	/*************************************************************/
	@Override
	public String getSelectCountEmToList(BgabGascem01Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EM_TO_LIST, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToList(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_LIST, dto);
	}
	/*************************************************************/
	/** meal list page                                          **/
	/*************************************************************/
	@Override
	public String getSelectCountEmToListForMeal(BgabGascem01Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EM_TO_LIST_FOR_MEAL, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToListForMeal(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_LIST_FOR_MEAL, dto);
	}
	/*************************************************************/
	/** entrance list page                                      **/
	/*************************************************************/
	@Override
	public String getSelectCountEmToListForEntrance(BgabGascem01Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EM_TO_LIST_FOR_ENTRANCE, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToListForEntrance(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_LIST_FOR_ENTRANCE, dto);
	}
	/*************************************************************/
	/** list page for worker                               		**/
	/*************************************************************/
	@Override
	public String getSelectCountEmToListForWorker(BgabGascem01Dto dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EM_TO_LIST_FOR_WORKER, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascem01Dto> getSelectListEmToListForWorker(BgabGascem01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_EM_TO_LIST_FOR_WORKER, dto);
	}
}
