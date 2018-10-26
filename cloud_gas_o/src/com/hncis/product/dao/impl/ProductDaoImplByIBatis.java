package com.hncis.product.dao.impl;

import java.util.List;

import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.product.dao.ProductDao;
import com.hncis.product.vo.BgabGascpd01Dto;
import com.hncis.product.vo.BgabGascpd02Dto;
import com.hncis.product.vo.BgabGascpd03Dto;
import com.hncis.product.vo.BgabGascpd04Dto;

public class ProductDaoImplByIBatis extends CommonDao implements ProductDao{
	
	// 대분류 저장 및 조회
	private final String INSERT_PD_TO_LRG_LIST               = "insertPdToLrgList";
	private final String UPDATE_PD_TO_LRG_LIST               = "updatePdToLrgList";
	private final String SELECT_PD_LIST_TO_LRG_INFO          = "selectPdListToLrgInfo";
	
	// 중분류 저장 및 조회
	private final String INSERT_PD_TO_MRG_LIST               = "insertPdToMrgList";
	private final String UPDATE_PD_TO_MRG_LIST               = "updatePdToMrgList";
	private final String SELECT_PD_LIST_TO_MRG_INFO          = "selectPdListToMrgInfo";

	// 물품 분류 삭제
	private final String DELETE_PD_TO_LRG_LIST               = "deletePdToLrgList";
	private final String DELETE_PD_TO_MRG_DTL_LIST           = "deletePdToMrgDtlList";
	private final String DELETE_PD_TO_MRG_LIST               = "deletePdToMrgList";

	// 분류 콤보박스 조회
	private final String SELECT_PD_TO_LRG_COMBO        	 	 = "selectPdToLrgCombo";
	private final String SELECT_PD_TO_MRG_COMBO         	 = "selectPdToMrgCombo";

	// 물품 목록 조회
	private final String SELECT_COUNT_PD_TO_PRODUCT_LIST   	 = "selectCountPdToProductList";
	private final String SELECT_PD_TO_PRODUCT_LIST         	 = "selectPdToProductList";
	
	// 물품 정보 조회
	private final String SELECT_INFO_PD_TO_PRODUCT_INFO    	  = "selectInfoPdToProductInfo";

	// 물품 저장 및 조회
	private final String INSERT_PD_TO_PRODUCT_INFO         	  = "insertPdToProductInfo";

	private final String SELECT_COUNT_PD_TO_PRODUCT_MGMT_LIST   = "selectCountPdToProductMgmtList";
	private final String SELECT_PD_TO_PRODUCT_MGMT_LIST         = "selectPdToProductMgmtList";
	
	private final String SELECT_COUNT_PD_SLR_LIST = "selectCountPdToSlrList";

	// 물품 삭제
	private final String DELETE_PD_TO_PRODUCT_INFO         	  = "deletePdToProductInfo";
	
	//시리얼 번호 조회
	private final String SELECT_PD_LIST_TO_SLR_INFO = "selectPdListToSlr";
	
	// 물품 시리얼 콤보박스
	private final String SELECT_SLR_LIST_COMBO = "selectSlrListCombo";
	
	// 물품 저장
	private final String SELECT_PD_TO_PRODUCT_EXTR_QTY   	 	 = "selectPdToProductExtrQty";
	private final String INSERT_PD_TO_PRODUCT_REQUEST         	 = "insertPdToProductRequest";

	// 물품 이력 조회
	private final String SELECT_COUNT_PD_TO_PRODUCT_RENT_LIST   = "selectCountPdToProductRentList";
	private final String SELECT_PD_TO_PRODUCT_RENT_LIST         = "selectPdToProductRentList";

	// 물품 신청취소
	private final String DELETE_RENT_LIST_TO_REQUEST_CANCEL  = "deletePdRentListToRequestCancel";
	
	// 물품 대여
	private final String UPDATE_PD_TO_PRODUCT_RENT = "updatePdToProductRent";
	private final String UPDATE_PD_TO_RENT_YN = "updatePdToRentYn";
	
	// 물품 반납
	private final String UPDATE_PD_TO_PRODUCT_RETURN_LIST       = "updatePdToProductReturnList";
	
	// 물품 이미지 저장
	private final String SELECT_PD_TO_FILE 					= "selectPdToFile";
	private final String INSERT_PD_TO_FILE					 = "insertPdToFile";
	private final String DELETE_PD_TO_FILE					 = "deletePdToFile";
	
	@Override
	public int insertPdToLrgList(List<BgabGascpd03Dto> iList) {
		return insert(INSERT_PD_TO_LRG_LIST, iList);
	}

	@Override
	public int updatePdToLrgList(List<BgabGascpd03Dto> uList) {
		return update(UPDATE_PD_TO_LRG_LIST, uList);
	}

	@Override
	public List<BgabGascpd03Dto> selectPdListToLrgInfo(BgabGascpd03Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_LIST_TO_LRG_INFO, vo);
	}

	@Override
	public int insertPdToMrgList(List<BgabGascpd04Dto> iList) {
		return insert(INSERT_PD_TO_MRG_LIST, iList);
	}

	@Override
	public int updatePdToMrgList(List<BgabGascpd04Dto> uList) {
		return update(UPDATE_PD_TO_MRG_LIST,uList);
	}

	@Override
	public List<BgabGascpd04Dto> selectPdListToMrgInfo(BgabGascpd04Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_LIST_TO_MRG_INFO,vo);
	}

	@Override
	public int deletePdToLrgList(List<BgabGascpd03Dto> dList) {
		return update(DELETE_PD_TO_LRG_LIST, dList);
	}

	@Override
	public int deletePdToMrgDtlList(List<BgabGascpd03Dto> dList) {
		return update(DELETE_PD_TO_MRG_DTL_LIST, dList);
	}

	@Override
	public int deletePdToMrgList(List<BgabGascpd04Dto> dList) {
		return update(DELETE_PD_TO_MRG_LIST, dList);
	}

	@Override
	public List<BgabGascpd03Dto> selectPdToLrgCombo(BgabGascpd03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_LRG_COMBO, dto);
	}

	@Override
	public List<BgabGascpd04Dto> selectPdToMrgCombo(BgabGascpd04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_MRG_COMBO, dto);
	}

	@Override
	public int selectCountPdToProductList(BgabGascpd01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PD_TO_PRODUCT_LIST, dto));
	}

	@Override
	public List<BgabGascpd01Dto> selectPdToProductList(BgabGascpd01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_PRODUCT_LIST, dto);
	}

	@Override
	public BgabGascpd01Dto selectInfoPdToProductInfo(BgabGascpd01Dto dto) {
		return (BgabGascpd01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_PD_TO_PRODUCT_INFO, dto);
	}

	@Override
	public int insertPdToProductInfo(List<BgabGascpd01Dto> dto) {
		return insert(INSERT_PD_TO_PRODUCT_INFO, dto);
	}

	@Override
	public int selectCountPdToProductMgmtList(BgabGascpd01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PD_TO_PRODUCT_MGMT_LIST, dto));
	}

	@Override
	public List<BgabGascpd01Dto> selectPdToProductMgmtList(BgabGascpd01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_PRODUCT_MGMT_LIST, dto);
	}

	@Override
	public List<BgabGascpd01Dto> doSearchPdListToSlrInfo(BgabGascpd01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_LIST_TO_SLR_INFO, dto);
	}

	@Override
	public List<BgabGascpd01Dto> selectSlrListCombo(BgabGascpd01Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_SLR_LIST_COMBO, vo);
	}

	@Override
	public int selectPdToProductExtrQty(BgabGascpd02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_PD_TO_PRODUCT_EXTR_QTY, dto));
	}

	@Override
	public int insertPdToProductRequest(BgabGascpd02Dto dto) {
		return insert(INSERT_PD_TO_PRODUCT_REQUEST, dto);
	}

	@Override
	public List<BgabGascpd02Dto> selectPdToProductRentList(BgabGascpd02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_PRODUCT_RENT_LIST, dto);
	}

	@Override
	public int selectCountPdToProductRentList(BgabGascpd02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PD_TO_PRODUCT_RENT_LIST, dto));
	}

	@Override
	public int deleteRentListToRequestCancel(List<BgabGascpd02Dto> dtoList) {
		return delete(DELETE_RENT_LIST_TO_REQUEST_CANCEL, dtoList);
	}

	@Override
	public int updatePdToProductRent(List<BgabGascpd02Dto> dtoList) {
		return update(UPDATE_PD_TO_PRODUCT_RENT,dtoList);
	}

	@Override
	public int updatePdToRentYn(BgabGascpd02Dto dto) {
		return update(UPDATE_PD_TO_RENT_YN,dto);
	}

	@Override
	public int updatePdToProductReturnList(List<BgabGascpd02Dto> dtoList) {
		return update(UPDATE_PD_TO_PRODUCT_RETURN_LIST,dtoList);
	}

	@Override
	public int deletePdToProductInfo(BgabGascpd01Dto dto) {
		return delete(DELETE_PD_TO_PRODUCT_INFO, dto);
	}

	@Override
	public int selectCountPdToSlrList(BgabGascpd01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PD_SLR_LIST, dto));
	}

	@Override
	public List<BgabGascZ011Dto> getSelectPdToFile(BgabGascZ011Dto bgabGascZ011Dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_PD_TO_FILE, bgabGascZ011Dto);
	}

	@Override
	public Integer insertPdToFile(BgabGascZ011Dto bgabGascZ011Dto) {
		return super.insert(INSERT_PD_TO_FILE, bgabGascZ011Dto);
	}

	@Override
	public Integer deletePdToFile(List<BgabGascZ011Dto> dto) {
		return super.delete(DELETE_PD_TO_FILE, dto);
	}

	@Override
	public BgabGascZ011Dto getSelectPdToFileInfo(BgabGascZ011Dto dto) {
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_PD_TO_FILE, dto);
	}
}
