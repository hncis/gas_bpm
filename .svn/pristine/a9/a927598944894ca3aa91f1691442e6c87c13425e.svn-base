package com.hncis.training.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx04;
import com.hncis.training.dao.TrainingDao;
import com.hncis.training.vo.BgabGasctr01;

public class TrainingDaoImplByIBatis extends CommonDao implements TrainingDao{

	private final String INSERT_INFO_TR_TO_REQUEST		= "insertInfoTRToReqeust";
	private final String DELETE_INFO_TR_TO_REQUEST		= "deleteInfoTRToRequest";
	private final String UPDATE_INFO_TR_TO_REQUEST		= "updateInfoTRToRequest";
	private final String SELECT_INFO_TR_TO_REQUEST  	= "selectInfoTRToRequest";
	private final String SELECT_INFO_TR_TO_REQUEST_BY_IF_ID	= "selectInfoTRToRequestByIfId";
	private final String SELECT_LIST_TR_TO_REQUEST 		= "selectListTRToRequest";
	
	private final String SELECT_COUNT_TR_TO_ACCEPT  	= "selectCountTRToAccept";
	private final String SELECT_LIST_TR_TO_ACCEPT 		= "selectListTRToAccept";
	
	private final String APPROVE_TR_TO_REQUEST	 		= "approveTRToRequest";
	private final String APPROVE_CANCEL_TR_TO_REQUEST	= "approveCancelTRToRequest";
	private final String CONFIRM_TR_TO_REQUEST	 		= "confirmTRToRequest";
	private final String CONFIRM_CANCEL_TR_TO_REQUEST	= "confirmCancelTRToRequest";
	private final String UPDATE_INFO_TR_TO_REJECT		= "updateInfoTrToReject";
	
	/**
	 * request apply
	 * @param BgabGasctr01
	 * @return
	 */
	public Object insertInfoTRToRequest(BgabGasctr01 cgabGasctr01){
		return super.insert(INSERT_INFO_TR_TO_REQUEST, cgabGasctr01);
	}
		
	/**
	 * request update
	 * @param BgabGasctr01
	 * @return
	 */
	public Object updateInfoTRToRequest(BgabGasctr01 cgabGasctr01){
		return super.insert(UPDATE_INFO_TR_TO_REQUEST, cgabGasctr01);
	}
	
	/**
	 * request delete
	 * @param BgabGasctr01
	 * @return
	 */
	public Object deleteInfoTRToRequest(BgabGasctr01 cgabGasctr01){
		return super.delete(DELETE_INFO_TR_TO_REQUEST, cgabGasctr01);
	}
	
	/**
	 * request search
	 * @param BgabGasctr01
	 * @return
	 */
	public BgabGasctr01 getSelectInfoTRToRequest(BgabGasctr01 dto){
		return (BgabGasctr01)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TR_TO_REQUEST, dto);
	}
	
	public BgabGasctr01 getSelectInfoTRToRequestByIfId(BgabGasctr01 dto){
		return (BgabGasctr01)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TR_TO_REQUEST_BY_IF_ID, dto);
	}
	
	public List<BgabGasctr01> getSelectListTRToRequest(BgabGasctr01 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TR_TO_REQUEST, dto);
	}
	
	/**
	 * accept count
	 * accept search
	 * @param BgabGasctr01
	 * @return
	 */
	public String getSelectCountTRToAccept(BgabGasctr01 dto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TR_TO_ACCEPT, dto);
	}
	
	public List<BgabGasctr01> getSelectListTRToAccept(BgabGasctr01 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TR_TO_ACCEPT, dto);
	}
	
	/**
	 * request
	 * @param BgabGasctr01
	 * @return
	 */
	public Object setApproveTRToRequest(BgabGasctr01 dto){
		return super.insert(APPROVE_TR_TO_REQUEST, dto);
	}
	
	public int setApproveCancelTRToRequest(BgabGasctr01 dto){
		return super.insert(APPROVE_CANCEL_TR_TO_REQUEST, dto);
	}
	
	/**
	 * confirm
	 * @param BgabGasctr01
	 * @return
	 */
	public Object setConfirmTRToRequest(BgabGasctr01 dto){
		return super.insert(CONFIRM_TR_TO_REQUEST, dto);
	}
	
	public Object setConfirmCancelTRToRequest(BgabGasctr01 dto){
		return super.insert(CONFIRM_CANCEL_TR_TO_REQUEST, dto);
	}
	
	public int updateInfoTrToReject(BgabGasctr01 dto){
		return update(UPDATE_INFO_TR_TO_REJECT, dto);
	}
	
}
