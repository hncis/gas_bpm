package com.hncis.generalService.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.generalService.dao.GeneralServiceDao;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs02;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.generalService.vo.BgabGascgsDto;

public class GeneralServiceDaoImplByIBatis extends CommonDao implements GeneralServiceDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<CommonCode> getGeneralServiceCombo(CommonCode code) {
		return getSqlMapClientTemplate().queryForList("selectGeneralServiceCombo", code);
	}

	/**
	 * request
	 */
	@Override
	public BgabGascgs01 doSearchByRequestInfo(BgabGascgs01 gsParamVo){
		return (BgabGascgs01)getSqlMapClientTemplate().queryForObject("searchByXgsRequestInfo", gsParamVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs03> doSearchByRequestList(BgabGascgs03 gsParamVo){
		return getSqlMapClientTemplate().queryForList("searchByXgsRequestList", gsParamVo);
	}

	@Override
	public int doInsertByRequest(BgabGascgs01 gsSaveVo){
		return super.insert("insertByXgsRequest", gsSaveVo);
	}

	@Override
	public int doInsertByList(List<BgabGascgs03> gsSaveList){
		return super.insert("insertByXgsList", gsSaveList);
	}

	@Override
	public int doUpdateByList(List<BgabGascgs03> gsUpdateList){
		return super.update("updateByXgsList", gsUpdateList);
	}

	@Override
	public int doDeleteByRequest(BgabGascgs01 gsDelVo){
		return super.delete("deleteByXgsRequest", gsDelVo);
	}

	@Override
	public int doDeleteByRequestList(BgabGascgs03 gsDelList){
		return super.delete("deleteByXgsRequestList", gsDelList);
	}

	@Override
	public int doSelectByRequestListCnt(BgabGascgs03 gsDelVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXgsRequestListCnt", gsDelVo));
	}

	@Override
	public int doUpdateByRequest(BgabGascgs03 gsReqVo){
		return super.update("updateByXgsRequest", gsReqVo);
	}

	@Override
	public int doUpdateByRequestCancel(BgabGascgs03 gsReqVo){
		return super.update("updateByXgsRequestCancel", gsReqVo);
	}

	@Override
	public int doUpdateByConfirm(BgabGascgs03 gsReqVo){
		return super.update("updateByXgsConfirm", gsReqVo);
	}

	@Override
	public int doUpdateByConfirmCancel(BgabGascgs03 gsReqVo){
		return super.update("updateByXgsConfirmCancel", gsReqVo);
	}

	/**
	 * list
	 */
	@Override
	public int getSelectByXgs02ListCount(BgabGascgs01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXgs02ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs03> getSelectByXgs02List(BgabGascgs01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXgs02List", gsReqVo);
	}

	@Override
	public int doInsertByXgs07Basic(BgabGascgs01 gsSvvo){
		return super.insert("insertByXgsRequest", gsSvvo);
	}

	@Override
	public int doInsertByXgs07List(BgabGascgs03 gsSvvo){
		return super.insert("insertByXgsList", gsSvvo);
	}

	@Override
	public int doUpdateByXgs07List(BgabGascgs03 gsMdvo){
		return super.update("updateByXgsList", gsMdvo);
	}

	/**
	 * confirm
	 */
	@Override
	public int getSelectByXgs03ListCount(BgabGascgs01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXgs03ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs03> getSelectByXgs03List(BgabGascgs01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXgs03List", gsReqVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs03> getSelectByXgs03ExcelList(BgabGascgs01 gsReqVo){
		//return getSqlMapClientTemplate().queryForList("selectByXgs03ExcelList", gsReqVo);
		return getSqlMapClientTemplate().queryForList("selectByXgs03List", gsReqVo);
	}

	/**
	 * total list
	 */
	@Override
	public int getSelectByXgs04ListCount(BgabGascgsDto gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXgs04ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgsDto> getSelectByXgs04List(BgabGascgsDto gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXgs04List", gsReqVo);
	}

	/**
	 * production management
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs02> getSearchByGeneralService(BgabGascgs02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByGeneralService", gsReqVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs02> getSearchByGeneralService2(BgabGascgs02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByGeneralService2", gsReqVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs02> getSearchByGeneralService3(BgabGascgs02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByGeneralService3", gsReqVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs02> getSearchByGeneralService4(BgabGascgs02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByGeneralService4", gsReqVo);
	}

	@Override
	public int getSelectByGeneralServiceCheck(BgabGascgs02 gsSaveVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByGeneralServiceCheck", gsSaveVo));
	}

	@Override
	public int doInsertByGeneralService(List<BgabGascgs02> gsSaveVo){
		return super.insert("insertByGeneralService", gsSaveVo);
	}

	@Override
	public int doUpdateByGeneralService(List<BgabGascgs02> gsModifyVo){
		return super.update("updateByGeneralService", gsModifyVo);
	}

	@Override
	public int doDeleteByGeneralService(List<BgabGascgs02> gsDelVo){
		return super.delete("deleteByGeneralService", gsDelVo);
	}

	/**
	 * manager management
	 */
	@Override
	public int getSelectByXgs06ListCount(BgabGascgs04 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXgs06ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascgs04> getSelectByXgs06List(BgabGascgs04 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXgs06List", gsReqVo);
	}

	@Override
	public int doInsertByXgs06(List<BgabGascgs04> gsSaveVo){
		return super.insert("insertByXgs06", gsSaveVo);
	}

	@Override
	public int doUpdateByXgs06(List<BgabGascgs04> gsModifyVo){
		return super.update("updateByXgs06", gsModifyVo);
	}

	@Override
	public int doDeleteByXgs06(List<BgabGascgs04> gsDelVo){
		return super.delete("deleteByXgs06", gsDelVo);
	}
	@Override
	public String getSelectByXgsIsManager(BgabGascgs04 gs04Vo){
		return (String)getSqlMapClientTemplate().queryForObject("selectByXgsIsManager", gs04Vo);
	}

	@Override
	public int updateByXgs03Reject(List<BgabGascgs03> gsModifyVo){
		return super.update("updateByXgs03Reject", gsModifyVo);
	}

	@Override
	public int insertGeneralServiceToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert("insertGeneralServiceToFile", bgabGascZ011Dto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectGeneralServiceToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList("selectGeneralServiceToFile", bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectGeneralServiceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject("selectGeneralServiceToFile", bgabGascZ011Dto);
	}

	@Override
	public int deleteGeneralServiceToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete("deleteGeneralServiceToFile", bgabGascZ011List);
	}

	@Override
	public BgabGascgs02 selectByGeneralService3FileName(BgabGascgs02 gsReqVo){
		return (BgabGascgs02)getSqlMapClientTemplate().queryForObject("selectByGeneralService3FileName", gsReqVo);
	}
}
