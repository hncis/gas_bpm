package com.hncis.familyJob.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.familyJob.dao.FamilyJobDao;
import com.hncis.familyJob.vo.BgabGascfj01Dto;
import com.hncis.familyJob.vo.BgabGascfj02Dto;

public class FamilyJobDaoImplByIBatis extends CommonDao implements FamilyJobDao{

	private final String SELECT_GB_LIST_TO_FAMILY_JOB			= "selectGbListToFamilyJob";
	private final String INSERT_GB_LIST_TO_FAMILY_JOB			= "insertGbListToFamilyJob";
	private final String UPDATE_GB_LIST_TO_FAMILY_JOB			= "updateGbListToFamilyJob";
	private final String DELETE_GB_LIST_TO_FAMILY_JOB_DETAIL	= "deleteGbListToFamilyJobDetail";
	private final String DELETE_GB_LIST_TO_FAMILY_JOB			= "deleteGbListToFamilyJob";
	private final String SELECT_REL_LIST_TO_FAMILY_JOB			= "selectRelListToFamilyJob";
	private final String INSERT_REL_LIST_TO_FAMILY_JOB			= "insertRelListToFamilyJob";
	private final String UPDATE_REL_LIST_TO_FAMILY_JOB			= "updateRelListToFamilyJob";
	private final String DELETE_REL_LIST_TO_FAMILY_JOB			= "deleteRelListToFamilyJob";
	private final String SELECT_TO_FAMILY_JOB_COMBO			 	= "selectToFamilyJobCombo";
	private final String SELECT_TO_FAMILY_JOB_COMBO2			= "selectToFamilyJobCombo2";
	private final String INSERT_TO_FAMILY_JOB				 	= "insertToFamilyJob";
	private final String UPDATE_TO_FAMILY_JOB			 		= "updateToFamilyJob";
	private final String DELETE_TO_FAMILY_JOB			 		= "deleteToFamilyJob";
	private final String SELECT_TO_FAMILY_JOB 					= "selectToFamilyJob";
	private final String SELECT_TO_FAMILY_JOB_LIST_COUNT 		= "selectToFamilyJobListCount";
	private final String SELECT_TO_FAMILY_JOB_LIST				= "selectToFamilyJobList";
	private final String INSERT_FJ_TO_FILE					 	= "insertFjToFile";
	private final String SELECT_FJ_TO_FILE					 	= "selectFjToFile";
	private final String DELETE_FJ_TO_FILE					 	= "deleteFjToFile";
	private final String UPDATE_FJ_TO_REQUEST_FOR_APPROVE_CANCEL	 = "updateFjToRequestForApproveCancel";
	private final String UPDATE_FJ_TO_REQUEST_FOR_CONFIRM		     = "updateFjToRequestForConfirm";
	private final String UPDATE_FJ_TO_REQUEST_FOR_REJECT		     = "updateFjToRequestForReject";
	
	@SuppressWarnings("unchecked")
	public List<BgabGascfj02Dto> selectGbListToFamilyJob(BgabGascfj02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_GB_LIST_TO_FAMILY_JOB, vo);
	}

	public int insertGbListToFamilyJob(BgabGascfj02Dto vo){
		return insert(INSERT_GB_LIST_TO_FAMILY_JOB, vo);
	}
	
	public int updateGbListToFamilyJob(BgabGascfj02Dto vo){
		return update(UPDATE_GB_LIST_TO_FAMILY_JOB, vo);
	}
	
	public int deleteGbListToFamilyJobDetail(List<BgabGascfj02Dto> list){
		return delete(DELETE_GB_LIST_TO_FAMILY_JOB_DETAIL, list);
	}
	
	public int deleteGbListToFamilyJob(List<BgabGascfj02Dto> list){
		return delete(DELETE_GB_LIST_TO_FAMILY_JOB, list);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascfj02Dto> selectRelListToFamilyJob(BgabGascfj02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_REL_LIST_TO_FAMILY_JOB, vo);
	}
	
	public int insertRelListToFamilyJob(BgabGascfj02Dto vo){
		return insert(INSERT_REL_LIST_TO_FAMILY_JOB, vo);
	}
	
	public int updateRelListToFamilyJob(BgabGascfj02Dto vo){
		return update(UPDATE_REL_LIST_TO_FAMILY_JOB, vo);
	}
	
	public int deleteRelListToFamilyJob(List<BgabGascfj02Dto> list){
		return delete(DELETE_REL_LIST_TO_FAMILY_JOB, list);
	}

	@SuppressWarnings("unchecked")
	public List<BgabGascfj02Dto> selectToFamilyJobCombo(BgabGascfj02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_TO_FAMILY_JOB_COMBO, vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascfj02Dto> selectToFamilyJobCombo2(BgabGascfj02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_TO_FAMILY_JOB_COMBO2, vo);
	}
	
	public int insertToFamilyJob(BgabGascfj01Dto vo){
		return insert(INSERT_TO_FAMILY_JOB, vo);
	}
	
	public int updateToFamilyJob(BgabGascfj01Dto vo){
		return update(UPDATE_TO_FAMILY_JOB, vo);
	}
	
	public int deleteToFamilyJob(BgabGascfj01Dto vo){
		return delete(DELETE_TO_FAMILY_JOB, vo);
	}
	
	public BgabGascfj01Dto selectToFamilyJob(BgabGascfj01Dto vo){
		return (BgabGascfj01Dto)getSqlMapClientTemplate().queryForObject(SELECT_TO_FAMILY_JOB, vo);
	}
	
	public int selectToFamilyJobListCount(BgabGascfj01Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_TO_FAMILY_JOB_LIST_COUNT, vo));
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascfj01Dto> selectToFamilyJobList(BgabGascfj01Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_TO_FAMILY_JOB_LIST, vo);
	}
	
	public int insertFjToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_FJ_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectFjToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_FJ_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectFjToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_FJ_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deleteFjToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_FJ_TO_FILE, bgabGascZ011List);
	}
	
	@Override
	public int updatefJToRequestForApproveCancel(BgabGascfj01Dto dto) {
		return update(UPDATE_FJ_TO_REQUEST_FOR_APPROVE_CANCEL, dto);
	}
	
	@Override
	public int updateFjToRequestForConfirm(BgabGascfj01Dto dto) {
		return update(UPDATE_FJ_TO_REQUEST_FOR_CONFIRM, dto);
	}
	
	@Override
	public int updateFjToRequestForReject(BgabGascfj01Dto dto) {
		return update(UPDATE_FJ_TO_REQUEST_FOR_REJECT, dto);
	}
}
