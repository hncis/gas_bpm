package com.hncis.officeSupplies.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.officeSupplies.vo.BgabGascos01;
import com.hncis.officeSupplies.vo.BgabGascos02;
import com.hncis.officeSupplies.vo.BgabGascos03;
import com.hncis.officeSupplies.vo.BgabGascos04;
import com.hncis.officeSupplies.vo.BgabGascosDto;

public interface OfficeSuppliesDao {

	public List<CommonCode> getOfficeCombo(CommonCode code);

	/**
	 * request
	 */
	public BgabGascos01 doSearchByRequestInfo(BgabGascos01 gsParamVo);
	public List<BgabGascos03> doSearchByRequestList(BgabGascos03 gsParamVo);
	public int doInsertByRequest(BgabGascos01 gsSaveVo);
	public int doInsertByList(List<BgabGascos03> gsSaveList);
	public int doUpdateByList(List<BgabGascos03> gsUpdateList);
	public int doDeleteByRequest(BgabGascos01 gsDelVo);
	public int doDeleteByRequestList(BgabGascos03 gsDelList);
	public int doSelectByRequestListCnt(BgabGascos03 gsDelVo);
	public int doUpdateByRequest(BgabGascos03 gsReqVo);
	public int doUpdateByRequestCancel(BgabGascos03 gsReqVo);
	public int doUpdateByConfirm(BgabGascos03 gsReqVo);
	public int doUpdateByConfirmCancel(BgabGascos03 gsReqVo);

	/**
	 * list
	 */
	public int getSelectByXos02ListCount(BgabGascos01 gsReqVo);
	public List<BgabGascos03> getSelectByXos02List(BgabGascos01 gsReqVo);
	public int doInsertByXos07Basic(BgabGascos01 gsSvvo);
	public int doInsertByXos07List(BgabGascos03 gsSvvo);
	public int doUpdateByXos07List(BgabGascos03 gsMdvo);

	/**
	 * confrim
	 */
	public int getSelectByXos03ListCount(BgabGascos01 gsReqVo);
	public List<BgabGascos03> getSelectByXos03List(BgabGascos01 gsReqVo);
	public List<BgabGascos03> getSelectByXos03ExcelList(BgabGascos01 gsReqVo);

	/**
	 * total list
	 */
	public int getSelectByXos04ListCount(BgabGascosDto gsReqVo);
	public List<BgabGascosDto> getSelectByXos04List(BgabGascosDto gsReqVo);

	/**
	 * production management
	 */
	public List<BgabGascos02> getSearchByOffice(BgabGascos02 gsReqVo);
	public List<BgabGascos02> getSearchByOffice2(BgabGascos02 gsReqVo);
	public List<BgabGascos02> getSearchByOffice3(BgabGascos02 gsReqVo);
	public List<BgabGascos02> getSearchByOffice4(BgabGascos02 gsReqVo);
	public int getSelectByOfficeCheck(BgabGascos02 gsSaveVo);
	public int doInsertByOffice(List<BgabGascos02> gsSaveVo);
	public int doUpdateByOffice(List<BgabGascos02> gsModifyVo);
	public int doDeleteByOffice(List<BgabGascos02> gsDelVo);

	/**
	 * manager managerment
	 */
	public int getSelectByXos06ListCount(BgabGascos04 gsReqVo);
	public List<BgabGascos04> getSelectByXos06List(BgabGascos04 gsReqVo);
	public int doInsertByXos06(List<BgabGascos04> gsSaveVo);
	public int doUpdateByXos06(List<BgabGascos04> gsModifyVo);
	public int doDeleteByXos06(List<BgabGascos04> gsDelVo);
	public String getSelectByXosIsManager(BgabGascos04 gs04Vo);

	public int updateByXos03Reject(List<BgabGascos03> gsModifyVo);

	public int insertOfficeSuppliesToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectOfficeSuppliesToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectOfficeSuppliesToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteOfficeSuppliesToFile (List<BgabGascZ011Dto> bgabGascZ011List);

	BgabGascos02 selectByOffice3FileName(BgabGascos02 gsReqVo);
}
