package com.hncis.generalService.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs02;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.generalService.vo.BgabGascgsDto;

public interface GeneralServiceDao {

	public List<CommonCode> getGeneralServiceCombo(CommonCode code);

	/**
	 * request
	 */
	public BgabGascgs01 doSearchByRequestInfo(BgabGascgs01 gsParamVo);
	public List<BgabGascgs03> doSearchByRequestList(BgabGascgs03 gsParamVo);
	public int doInsertByRequest(BgabGascgs01 gsSaveVo);
	public int doInsertByList(List<BgabGascgs03> gsSaveList);
	public int doUpdateByList(List<BgabGascgs03> gsUpdateList);
	public int doDeleteByRequest(BgabGascgs01 gsDelVo);
	public int doDeleteByRequestList(BgabGascgs03 gsDelList);
	public int doSelectByRequestListCnt(BgabGascgs03 gsDelVo);
	public int doUpdateByRequest(BgabGascgs03 gsReqVo);
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
	 * confrim
	 */
	public int getSelectByXgs03ListCount(BgabGascgs01 gsReqVo);
	public List<BgabGascgs03> getSelectByXgs03List(BgabGascgs01 gsReqVo);
	public List<BgabGascgs03> getSelectByXgs03ExcelList(BgabGascgs01 gsReqVo);

	/**
	 * total list
	 */
	public int getSelectByXgs04ListCount(BgabGascgsDto gsReqVo);
	public List<BgabGascgsDto> getSelectByXgs04List(BgabGascgsDto gsReqVo);

	/**
	 * production management
	 */
	public List<BgabGascgs02> getSearchByGeneralService(BgabGascgs02 gsReqVo);
	public List<BgabGascgs02> getSearchByGeneralService2(BgabGascgs02 gsReqVo);
	public List<BgabGascgs02> getSearchByGeneralService3(BgabGascgs02 gsReqVo);
	public List<BgabGascgs02> getSearchByGeneralService4(BgabGascgs02 gsReqVo);
	public int getSelectByGeneralServiceCheck(BgabGascgs02 gsSaveVo);
	public int doInsertByGeneralService(List<BgabGascgs02> gsSaveVo);
	public int doUpdateByGeneralService(List<BgabGascgs02> gsModifyVo);
	public int doDeleteByGeneralService(List<BgabGascgs02> gsDelVo);

	/**
	 * manager managerment
	 */
	public int getSelectByXgs06ListCount(BgabGascgs04 gsReqVo);
	public List<BgabGascgs04> getSelectByXgs06List(BgabGascgs04 gsReqVo);
	public int doInsertByXgs06(List<BgabGascgs04> gsSaveVo);
	public int doUpdateByXgs06(List<BgabGascgs04> gsModifyVo);
	public int doDeleteByXgs06(List<BgabGascgs04> gsDelVo);
	public String getSelectByXgsIsManager(BgabGascgs04 gs04Vo);

	public int updateByXgs03Reject(List<BgabGascgs03> gsModifyVo);

	public int insertGeneralServiceToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectGeneralServiceToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectGeneralServiceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteGeneralServiceToFile (List<BgabGascZ011Dto> bgabGascZ011List);

	BgabGascgs02 selectByGeneralService3FileName(BgabGascgs02 gsReqVo);
}
