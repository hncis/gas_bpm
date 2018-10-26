package com.hncis.generalService.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs02;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.generalService.vo.BgabGascgsDto;

@Transactional
public interface GeneralServiceManager {
	public List<CommonCode> getGeneralServiceCombo(CommonCode code);

	/**
	 * request
	 */
	public BgabGascgs01 doSearchByRequestInfo(BgabGascgs01 gsParamVo);
	public List<BgabGascgs03> doSearchByRequestList(BgabGascgs03 gsParamVo);
	public int doInsertByRequest(BgabGascgs01 gsSaveVo, List<BgabGascgs03> gsSaveList, List<BgabGascgs03> gsUpdateList);
	public int doDeleteByRequest(BgabGascgs01 gsDelVo);
	public int doDeleteByRequestList(BgabGascgs03 gsDelVo);
	public int doUpdateByRequest(BgabGascgs03 gsVo03);
	public int doUpdateByRequestCancel(BgabGascgs03 gsReqVo);
	public int doUpdateByConfirm(BgabGascgs03 gsReqVo);
	public int doUpdateByConfirmCancel(BgabGascgs03 gsReqVo);

	/**
	 * list
	 */
	public int getSelectByXgs02ListCount(BgabGascgs01 gsReqVo);
	public List<BgabGascgs03> getSelectByXgs02List(BgabGascgs01 gsReqVo);

	public int doInsertByXgs07Basic(BgabGascgs01 gsSvvo);
	public int doInsertByXgs07List(BgabGascgs03 gsSvvo);
	public int doUpdateByXgs07List(BgabGascgs03 gsMdvo);

	/**
	 * Confirm
	 */
	public int getSelectByXgs03ListCount(BgabGascgs01 gsReqVo);
	public List<BgabGascgs03> getSelectByXgs03List(BgabGascgs01 gsReqVo);
	public List<?> getSelectByXgs03ExcelList(BgabGascgs01 gsReqVo);

	/**
	 * total list
	 */
	public int getSelectByXgs04ListCount(BgabGascgsDto gsReqVo);
	public List<BgabGascgsDto> getSelectByXgs04List(BgabGascgsDto gsReqVo);

	/**
	 * production management
	 */
	public List<BgabGascgs02> getSearchByGeneralService(BgabGascgs02 gsReqDto);
	public List<BgabGascgs02> getSearchByGeneralService2(BgabGascgs02 gsReqDto);
	public List<BgabGascgs02> getSearchByGeneralService3(BgabGascgs02 gsReqDto);
	public List<BgabGascgs02> getSearchByGeneralService4(BgabGascgs02 gsReqDto);
	public CommonMessage doInsertByGeneralService(List<BgabGascgs02> gsSaveVo, List<BgabGascgs02> gsModifyVo);
	public int doDeleteByGeneralService(List<BgabGascgs02> gsDelVo);

	/**
	 * general service manager
	 */
	public int getSelectByXgs06ListCount(BgabGascgs04 gsReqVo);
	public List<BgabGascgs04> getSelectByXgs06List(BgabGascgs04 gsReqVo);
	public int doInsertByXgs06(List<BgabGascgs04> gsSaveVo, List<BgabGascgs04> gsModifyVo);
	public int doDeleteByXgs06(List<BgabGascgs04> gsDelVo);
	public String getSelectByXgsIsManager(BgabGascgs04 gs04Vo);

	public RfcPoCreateVo doSearchPoCreate(RfcPoCreateVo poParamVo);
	public RfcPoCreateVo doSearchPoDelete(RfcPoCreateVo poParamVo);

	public int updateByXgs03Reject(List<BgabGascgs03> gsModifyVo);

	public void saveGeneralServiceToImgFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectGeneralServiceToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectGeneralServiceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteGeneralServiceToFile(List<BgabGascZ011Dto> bgabGascZ011IList);

	BgabGascgs02 selectByGeneralService3FileName(BgabGascgs02 bgabGascos02);
}
