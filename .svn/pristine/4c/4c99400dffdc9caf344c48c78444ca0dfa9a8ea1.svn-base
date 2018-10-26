package com.hncis.product.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.product.vo.BgabGascpd01Dto;
import com.hncis.product.vo.BgabGascpd02Dto;
import com.hncis.product.vo.BgabGascpd03Dto;
import com.hncis.product.vo.BgabGascpd04Dto;

public interface ProductDao {

	int insertPdToLrgList(List<BgabGascpd03Dto> iList);

	int updatePdToLrgList(List<BgabGascpd03Dto> uList);

	List<BgabGascpd03Dto> selectPdListToLrgInfo(BgabGascpd03Dto vo);

	int insertPdToMrgList(List<BgabGascpd04Dto> iList);

	int updatePdToMrgList(List<BgabGascpd04Dto> uList);

	List<BgabGascpd04Dto> selectPdListToMrgInfo(BgabGascpd04Dto vo);

	int deletePdToLrgList(List<BgabGascpd03Dto> dList);

	int deletePdToMrgDtlList(List<BgabGascpd03Dto> dList);

	int deletePdToMrgList(List<BgabGascpd04Dto> dList);

	List<BgabGascpd03Dto> selectPdToLrgCombo(BgabGascpd03Dto dto);

	List<BgabGascpd04Dto> selectPdToMrgCombo(BgabGascpd04Dto dto);

	int selectCountPdToProductList(BgabGascpd01Dto dto);

	List<BgabGascpd01Dto> selectPdToProductList(BgabGascpd01Dto dto);

	BgabGascpd01Dto selectInfoPdToProductInfo(BgabGascpd01Dto dto);

	int insertPdToProductInfo(List<BgabGascpd01Dto> dtoList);

	int selectCountPdToProductMgmtList(BgabGascpd01Dto dto);

	List<BgabGascpd01Dto> selectPdToProductMgmtList(BgabGascpd01Dto dto);

	List<BgabGascpd01Dto> doSearchPdListToSlrInfo(BgabGascpd01Dto dto);

	List<BgabGascpd01Dto> selectSlrListCombo(BgabGascpd01Dto vo);

	int selectPdToProductExtrQty(BgabGascpd02Dto dto);

	int insertPdToProductRequest(BgabGascpd02Dto dto);

	List<BgabGascpd02Dto> selectPdToProductRentList(BgabGascpd02Dto dto);

	int selectCountPdToProductRentList(BgabGascpd02Dto dto);

	int deleteRentListToRequestCancel(List<BgabGascpd02Dto> dtoList);

	int updatePdToProductRent(List<BgabGascpd02Dto> dtoList);

	int updatePdToProductReturnList(List<BgabGascpd02Dto> dtoList);

	int deletePdToProductInfo(BgabGascpd01Dto dto);

	int selectCountPdToSlrList(BgabGascpd01Dto dto);

	List<BgabGascZ011Dto> getSelectPdToFile(BgabGascZ011Dto bgabGascZ011Dto);

	Integer insertPdToFile(BgabGascZ011Dto bgabGascZ011Dto);

	Integer deletePdToFile(List<BgabGascZ011Dto> dto);

	BgabGascZ011Dto getSelectPdToFileInfo(BgabGascZ011Dto dto);

	int updatePdToRentYn(BgabGascpd02Dto dto);

}
