package com.hncis.postOffice.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.postOffice.dao.PostOfficeDao;
import com.hncis.postOffice.vo.BgabGascpo01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;

public class PostOfficeDaoImplByIBatis extends CommonDao implements PostOfficeDao{

	private final String INSERT_PO_TO_REQUEST 						= "insertPoToRequest";
	private final String SELECT_INFO_PO_TO_REQUEST 					= "selectInfoPoToRequest";
	private final String UPDATE_PO_TO_REQUEST_FOR_REQUEST			= "updatePoToRequestForRequest";
	private final String UPDATE_PO_TO_REQUEST_FOR_REQUEST_CANCEL	= "updatePoToRequestForRequestCancel";
	private final String UPDATE_PO_TO_REQUEST_FOR_CONFIRM			= "updatePoToRequestForConfirm";
	private final String UPDATE_PO_TO_REQUEST_FOR_CONFIRM_CANCEL	= "updatePoToRequestForConfirmCancel";
	private final String DELETE_PO_TO_REQUEST 						= "deletePoToRequest";
	
	private final String SELECT_COUNT_PO_TO_LIST 					= "selectCountPoToList";
	private final String SELECT_LIST_PO_TO_LIST 					= "selectListPoToList";
	
	private final String SELECT_COUNT_PO_TO_CONFIRM 				= "selectCountPoToConfirm";
	private final String SELECT_LIST_PO_TO_CONFIRM 					= "selectListPoToConfirm";
	
	private final String INSERT_PO_TO_FILE							= "insertPoToFile";
	private final String SELECT_PO_TO_FILE							= "selectPoToFile";
	private final String DELETE_PO_TO_FILE							= "deletePoToFile";
	
	private final String REJECT_PO_TO_REQUEST						= "rejectPoToRequest";
	
	@Override
	public int insertPoToRequest(BgabGascpo01Dto dto) {
		return super.insert(INSERT_PO_TO_REQUEST, dto);
	}
	@Override
	public BgabGascpo01Dto selectInfoPoToRequest(BgabGascpo01Dto dto) {
		return (BgabGascpo01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_PO_TO_REQUEST, dto);
	}
	@Override
	public int updatePoToRequestForRequest(BgabGascpo01Dto dto) {
		return super.insert(UPDATE_PO_TO_REQUEST_FOR_REQUEST, dto);
	}
	@Override
	public int updatePoToRequestForRequestCancel(BgabGascpo01Dto dto) {
		return super.insert(UPDATE_PO_TO_REQUEST_FOR_REQUEST_CANCEL, dto);
	}
	@Override
	public int updatePoToRequestForConfirm(BgabGascpo01Dto dto) {
		return super.insert(UPDATE_PO_TO_REQUEST_FOR_CONFIRM, dto);
	}
	@Override
	public int updatePoToRequestForConfirmCancel(BgabGascpo01Dto dto) {
		return super.insert(UPDATE_PO_TO_REQUEST_FOR_CONFIRM_CANCEL, dto);
	}
	@Override
	public int deletePoToRequest(BgabGascpo01Dto dto) {
		return super.insert(DELETE_PO_TO_REQUEST, dto);
	}
	
	@Override
	public int selectCountPoToList(BgabGascpo01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PO_TO_LIST, dto));
	}

	@Override
	public List<BgabGascpo01Dto> selectListPoToList(BgabGascpo01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PO_TO_LIST, dto);
	}
	
	@Override
	public int selectCountPoToConfirm(BgabGascpo01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PO_TO_CONFIRM, dto));
	}

	@Override
	public List<BgabGascpo01Dto> selectListPoToConfirm(BgabGascpo01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_PO_TO_CONFIRM, dto);
	}
	
	public int insertPoToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_PO_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectPoToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_PO_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectPoToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_PO_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deletePoToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_PO_TO_FILE, bgabGascZ011List);
	}
	
	public int rejectPoToRequest (BgabGascpo01Dto dto){
		return update(REJECT_PO_TO_REQUEST, dto);
	}

}
