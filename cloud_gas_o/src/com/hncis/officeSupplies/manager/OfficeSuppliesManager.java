package com.hncis.officeSupplies.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.officeSupplies.vo.BgabGascos01;
import com.hncis.officeSupplies.vo.BgabGascos02;
import com.hncis.officeSupplies.vo.BgabGascos03;
import com.hncis.officeSupplies.vo.BgabGascos04;
import com.hncis.officeSupplies.vo.BgabGascosDto;

@Transactional
public interface OfficeSuppliesManager {
	public List<CommonCode> getOfficeCombo(CommonCode code);

	/**
	 * request
	 */
	public BgabGascos01 doSearchByRequestInfo(BgabGascos01 gsParamVo);
	public List<BgabGascos03> doSearchByRequestList(BgabGascos03 gsParamVo);
	public int doInsertByRequest(BgabGascos01 gsSaveVo, List<BgabGascos03> gsSaveList, List<BgabGascos03> gsUpdateList);
	public int doDeleteByRequest(BgabGascos01 gsDelVo);
	public int doDeleteByRequestList(BgabGascos03 gsDelVo);
	public int doUpdateByRequest(BgabGascos03 gsVo03);
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
	 * Confirm
	 */
	public int getSelectByXos03ListCount(BgabGascos01 gsReqVo);
	public List<BgabGascos03> getSelectByXos03List(BgabGascos01 gsReqVo);
	public List<?> getSelectByXos03ExcelList(BgabGascos01 gsReqVo);

	/**
	 * total list
	 */
	public int getSelectByXos04ListCount(BgabGascosDto gsReqVo);
	public List<BgabGascosDto> getSelectByXos04List(BgabGascosDto gsReqVo);

	/**
	 * production management
	 */
	public List<BgabGascos02> getSearchByOffice(BgabGascos02 gsReqDto);
	public List<BgabGascos02> getSearchByOffice2(BgabGascos02 gsReqDto);
	public List<BgabGascos02> getSearchByOffice3(BgabGascos02 gsReqDto);
	public List<BgabGascos02> getSearchByOffice4(BgabGascos02 gsReqDto);
	public CommonMessage doInsertByOffice(List<BgabGascos02> gsSaveVo, List<BgabGascos02> gsModifyVo);
	public int doDeleteByOffice(List<BgabGascos02> gsDelVo);

	/**
	 * general service manager
	 */
	public int getSelectByXos06ListCount(BgabGascos04 gsReqVo);
	public List<BgabGascos04> getSelectByXos06List(BgabGascos04 gsReqVo);
	public int doInsertByXos06(List<BgabGascos04> gsSaveVo, List<BgabGascos04> gsModifyVo);
	public int doDeleteByXos06(List<BgabGascos04> gsDelVo);
	public String getSelectByXosIsManager(BgabGascos04 gs04Vo);

	public RfcPoCreateVo doSearchPoCreate(RfcPoCreateVo poParamVo);
	public RfcPoCreateVo doSearchPoDelete(RfcPoCreateVo poParamVo);

	public int updateByXos03Reject(List<BgabGascos03> gsModifyVo);

	public void saveOfficeSuppliesToImgFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectOfficeSuppliesToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectOfficeSuppliesToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteOfficeSuppliesToFile(List<BgabGascZ011Dto> bgabGascZ011IList);

	BgabGascos02 selectByOffice3FileName(BgabGascos02 bgabGascos02);
}
