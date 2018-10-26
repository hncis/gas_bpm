package com.hncis.product.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.product.vo.BgabGascpd01Dto;
import com.hncis.product.vo.BgabGascpd02Dto;
import com.hncis.product.vo.BgabGascpd03Dto;
import com.hncis.product.vo.BgabGascpd04Dto;

@Transactional
public interface ProductManager {

	public void savePdToLrgList(List<BgabGascpd03Dto> iList, List<BgabGascpd03Dto> uList); // 물품 대분류 저장

	public List<BgabGascpd03Dto> selectPdListToLrgInfo(BgabGascpd03Dto vo); // 물품 대분류 조회

	public void savePdToMrgList(List<BgabGascpd04Dto> iList, List<BgabGascpd04Dto> uList); //물품 중분류 저장

	public List<BgabGascpd04Dto> selectPdListToMrgInfo(BgabGascpd04Dto vo); // 물품 중분류 조회

	public void deletePdToLrgList(List<BgabGascpd03Dto> dList); // 물품 대분류 삭제

	public void deletePdToMrgList(List<BgabGascpd04Dto> dList); // 물품 중분류 삭제

	public List<BgabGascpd03Dto> selectPdToLrgCombo(BgabGascpd03Dto vo); // 대분류 콤보박스 조회

	public List<BgabGascpd04Dto> selectPdToMrgCombo(BgabGascpd04Dto vo); // 중분류 콤보박스 조회

	public int selectCountPdToProductList(BgabGascpd01Dto dto); // 저장된 물픔 갯수 조회

	public List<BgabGascpd01Dto> selectPdToProductList(BgabGascpd01Dto dto); // 저장된 물품 조회

	public BgabGascpd01Dto selectInfoPdToProductInfo(BgabGascpd01Dto dto); // 물품 정보 조회

	public CommonMessage insertPdToProductInfo(List<BgabGascpd01Dto> dtoList); // 물품 저장

	public int selectCountPdToProductMgmtList(BgabGascpd01Dto dto); // 물품 관리 조회

	public List<BgabGascpd01Dto> selectPdToProductMgmtList(BgabGascpd01Dto dto); // 물품 관리 조회

	public List<BgabGascpd01Dto> doSearchPdListToSlrInfo(BgabGascpd01Dto dto); // 물품 시리얼 번호 조회

	public List<BgabGascpd01Dto> selectSlrListCombo(BgabGascpd01Dto vo);	// 물품 시리얼 콤보박스

	public CommonMessage updatePdToProductRequest(BgabGascpd02Dto dto);		// 물품 신청

	public int selectCountPdToProductRentList(BgabGascpd02Dto dto);	// 물품 이력 갯수 조회

	public List<BgabGascpd02Dto> selectPdToProductRentList(BgabGascpd02Dto dto);	// 물품 이력 조회

	public int deleteRentListToRequestCancel(List<BgabGascpd02Dto> dtoList);	// 물품 신청취소

	public CommonMessage updatePdToProductRent(List<BgabGascpd02Dto> dtoList);	// 물품 대여

	public CommonMessage updatePdToProductReturnList(List<BgabGascpd02Dto> dtoList);	// 물품 반납

	public CommonMessage deletePdToProductInfo(BgabGascpd01Dto dto); // 물품 삭제

	public List<BgabGascZ011Dto> getSelectPdToFile(BgabGascZ011Dto dto);	// 물품 이미지 파일 

	public void savePdToFile(HttpServletRequest req, HttpServletResponse res,BgabGascZ011Dto bgabGascZ011Dto);	// 물품 이미지파일 저장

	public int deletePdToFile(List<BgabGascZ011Dto> dto);	// 물품 이미지 불러오기 삭제

	public BgabGascZ011Dto getSelectPdToFileInfo(BgabGascZ011Dto dto);

}
