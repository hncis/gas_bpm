package com.hncis.officeSupplies.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.officeSupplies.dao.OfficeSuppliesDao;
import com.hncis.officeSupplies.vo.BgabGascos01;
import com.hncis.officeSupplies.vo.BgabGascos02;
import com.hncis.officeSupplies.vo.BgabGascos03;
import com.hncis.officeSupplies.vo.BgabGascos04;
import com.hncis.officeSupplies.vo.BgabGascosDto;

public class OfficeSuppliesDaoImplByIBatis extends CommonDao implements OfficeSuppliesDao {
	private static final String uncheck = "unchecked";

	@Override
	@SuppressWarnings(uncheck)
	public List<CommonCode> getOfficeCombo(CommonCode code) {
		return getSqlMapClientTemplate().queryForList("selectOfficeCombo", code);
	}

	/**
	 * request
	 */
	@Override
	public BgabGascos01 doSearchByRequestInfo(BgabGascos01 gsParamVo){
		return (BgabGascos01)getSqlMapClientTemplate().queryForObject("searchByXosRequestInfo", gsParamVo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos03> doSearchByRequestList(BgabGascos03 gsParamVo){
		return getSqlMapClientTemplate().queryForList("searchByXosRequestList", gsParamVo);
	}

	@Override
	public int doInsertByRequest(BgabGascos01 gsSaveVo){
		return super.insert("insertByXosRequest", gsSaveVo);
	}

	@Override
	public int doInsertByList(List<BgabGascos03> gsSaveList){
		return super.insert("insertByXosList", gsSaveList);
	}

	@Override
	public int doUpdateByList(List<BgabGascos03> gsUpdateList){
		return super.update("updateByXosList", gsUpdateList);
	}

	@Override
	public int doDeleteByRequest(BgabGascos01 gsDelVo){
		return super.delete("deleteByXosRequest", gsDelVo);
	}

	@Override
	public int doDeleteByRequestList(BgabGascos03 gsDelList){
		return super.delete("deleteByXosRequestList", gsDelList);
	}

	@Override
	public int doSelectByRequestListCnt(BgabGascos03 gsDelVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXosRequestListCnt", gsDelVo));
	}

	@Override
	public int doUpdateByRequest(BgabGascos03 gsReqVo){
		return super.update("updateByXosRequest", gsReqVo);
	}

	@Override
	public int doUpdateByRequestCancel(BgabGascos03 gsReqVo){
		return super.update("updateByXosRequestCancel", gsReqVo);
	}

	@Override
	public int doUpdateByConfirm(BgabGascos03 gsReqVo){
		return super.update("updateByXosConfirm", gsReqVo);
	}

	@Override
	public int doUpdateByConfirmCancel(BgabGascos03 gsReqVo){
		return super.update("updateByXosConfirmCancel", gsReqVo);
	}
	
	@Override /* 1102 예외 추가*/
	public int doSearchByXosIsAllConfirm(BgabGascos03 gsParamVo){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectByXosIsAllConfirm", gsParamVo);
	}

	/**
	 * list
	 */
	@Override
	public int getSelectByXos02ListCount(BgabGascos01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXos02ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos03> getSelectByXos02List(BgabGascos01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXos02List", gsReqVo);
	}

	@Override
	public int doInsertByXos07Basic(BgabGascos01 gsSvvo){
		return super.insert("insertByXosRequest", gsSvvo);
	}

	@Override
	public int doInsertByXos07List(BgabGascos03 gsSvvo){
		return super.insert("insertByXosList", gsSvvo);
	}

	@Override
	public int doUpdateByXos07List(BgabGascos03 gsMdvo){
		return super.update("updateByXosList", gsMdvo);
	}

	/**
	 * confirm
	 */
	@Override
	public int getSelectByXos03ListCount(BgabGascos01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXos03ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos03> getSelectByXos03List(BgabGascos01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXos03List", gsReqVo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos03> getSelectByXos03ExcelList(BgabGascos01 gsReqVo){
		//return getSqlMapClientTemplate().queryForList("selectByXos03ExcelList", gsReqVo);
		return getSqlMapClientTemplate().queryForList("selectByXos03List", gsReqVo);
	}

	/**
	 * total list
	 */
	@Override
	public int getSelectByXos04ListCount(BgabGascosDto gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXos04ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascosDto> getSelectByXos04List(BgabGascosDto gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXos04List", gsReqVo);
	}

	/**
	 * production management
	 */
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos02> getSearchByOffice(BgabGascos02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByOffice", gsReqVo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos02> getSearchByOffice2(BgabGascos02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByOffice2", gsReqVo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos02> getSearchByOffice3(BgabGascos02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByOffice3", gsReqVo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos02> getSearchByOffice4(BgabGascos02 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByOffice4", gsReqVo);
	}

	@Override
	public int getSelectByOfficeCheck(BgabGascos02 gsSaveVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByOfficeCheck", gsSaveVo));
	}

	@Override
	public int doInsertByOffice(List<BgabGascos02> gsSaveVo){
		return super.insert("insertByOffice", gsSaveVo);
	}

	@Override
	public int doUpdateByOffice(List<BgabGascos02> gsModifyVo){
		return super.update("updateByOffice", gsModifyVo);
	}

	@Override
	public int doDeleteByOffice(List<BgabGascos02> gsDelVo){
		return super.delete("deleteByOffice", gsDelVo);
	}

	/**
	 * manager management
	 */
	@Override
	public int getSelectByXos06ListCount(BgabGascos04 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXos06ListCount", gsReqVo));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascos04> getSelectByXos06List(BgabGascos04 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXos06List", gsReqVo);
	}

	@Override
	public int doInsertByXos06(List<BgabGascos04> gsSaveVo){
		return super.insert("insertByXos06", gsSaveVo);
	}

	@Override
	public int doUpdateByXos06(List<BgabGascos04> gsModifyVo){
		return super.update("updateByXos06", gsModifyVo);
	}

	@Override
	public int doDeleteByXos06(List<BgabGascos04> gsDelVo){
		return super.delete("deleteByXos06", gsDelVo);
	}
	@Override
	public String getSelectByXosIsManager(BgabGascos04 gs04Vo){
		return (String)getSqlMapClientTemplate().queryForObject("selectByXosIsManager", gs04Vo);
	}

	@Override
	public int updateByXos03Reject(List<BgabGascos03> gsModifyVo){
		return super.update("updateByXos03Reject", gsModifyVo);
	}

	@Override
	public int insertOfficeSuppliesToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert("insertOfficeSuppliesToFile", bgabGascZ011Dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascZ011Dto> getSelectOfficeSuppliesToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList("selectOfficeSuppliesToFile", bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectOfficeSuppliesToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject("selectOfficeSuppliesToFile", bgabGascZ011Dto);
	}

	@Override
	public int deleteOfficeSuppliesToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete("deleteOfficeSuppliesToFile", bgabGascZ011List);
	}

	@Override
	public BgabGascos02 selectByOffice3FileName(BgabGascos02 gsReqVo){
		return (BgabGascos02)getSqlMapClientTemplate().queryForObject("selectByOffice3FileName", gsReqVo);
	}
}
