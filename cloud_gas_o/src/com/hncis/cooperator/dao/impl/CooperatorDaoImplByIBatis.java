package com.hncis.cooperator.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.cooperator.dao.CooperatorDao;
import com.hncis.cooperator.vo.BgabGascco01Dto;
import com.hncis.cooperator.vo.BgabGascco02Dto;

public class CooperatorDaoImplByIBatis extends CommonDao implements CooperatorDao{
	
	/**
	 * 등록 인덱스값 조회
	 */
	@Override
	public String selectXcoCorpIdx(BgabGascco01Dto co01Dto) {
		return (String)getSqlMapClientTemplate().queryForObject("selectXcoCorpIdx", co01Dto);
	}
	
	/**
	 * 협력업체 등록
	 */
	@Override
	public int mergeXcoToRequestByBasic(BgabGascco01Dto co01Dto) {
		return super.update("mergetXcoToRequest", co01Dto);
	}
	
	/**
	 * 협력업체 담당자 등록
	 */
	@Override
	public int mergeXcoToRequestByPic(BgabGascco02Dto co02Dto) {
		return super.update("mergeXcoToRequestByPic", co02Dto);
	}
	
	/**
	 * 협력업체 전체 삭제
	 */
	@Override
	public int deleteXcoToRequest(BgabGascco01Dto co01Dto) {
		return super.delete("deleteXcoToRequest", co01Dto);
	}
	
	/**
	 * 협력업체 담당자 삭제
	 */
	@Override
	public int deleteXcoPicToRequest(BgabGascco02Dto co02Dto) {
		return super.delete("deleteXcoPicToRequest", co02Dto);
	}
	
	/**
	 * 협력업체 담당자 삭제(그리드)
	 */
	@Override
	public int deleteXcoPicToList(List<BgabGascco02Dto> co02ListDto) {
		return super.delete("deleteXcoPicToList", co02ListDto);
	}
	
	/**
	 * 협력업체 조회
	 */
	@Override
	public BgabGascco01Dto selectInfoXcoToRequest(BgabGascco01Dto co01Dto) {
		return (BgabGascco01Dto)getSqlMapClientTemplate().queryForObject("selectInfoXcoToRequest", co01Dto);
	}
	
	/**
	 * 협력업체 담당자 조회
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascco02Dto> selectPicListXcoToRequest(BgabGascco02Dto co02Dto) {
		return getSqlMapClientTemplate().queryForList("selectPicListXcoToRequest", co02Dto);
	}
	
	/**
	 * 협력업체 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGascco02Dto> selectXcoToList(BgabGascco01Dto co01Dto) {
		return getSqlMapClientTemplate().queryForList("selectXcoToList", co01Dto);
	}
}
