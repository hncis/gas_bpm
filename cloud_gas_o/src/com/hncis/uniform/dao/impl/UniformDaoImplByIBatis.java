package com.hncis.uniform.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.util.StringUtil;
import com.hncis.uniform.dao.UniformDao;
import com.hncis.uniform.vo.BgabGascaf01Dto;
import com.hncis.uniform.vo.BgabGascaf02Dto;
import com.hncis.uniform.vo.BgabGascaf03Dto;

public class UniformDaoImplByIBatis extends CommonDao implements UniformDao{
	private static final String uncheck = "unchecked";
	private final String SELECT_UNIFORM_TYPE_OF_CLOTHES_COMBO_LIST      = "selectUniformTypeofclothesComboList";
	private final String SELECT_UNIFORM_COLOR_COMBO_LIST              = "selectUniformColorComboList";
	private final String SELECT_UNIFORM_SIZE_COMBO_LIST               = "selectUniformSizeComboList";
	private final String SELECT_UNIFORM_CYCLE_TO_REQUEST              = "selectUniformCycleToRequest";
	private final String SELECT_UNIFORM_COUNT_TO_REQUEST              = "selectUniformCountToRequest";
	private final String SELECT_UNIFORM_LIST_TO_REQUEST               = "selectUniformListToRequest";
	private final String SELECT_UNIF_CLR_REMAIN_QTY_INFO             	= "selectUnifClrRemainQtyInfo";
	private final String SELECT_UNIFORM_STOCK_CHECK_TO_REQUEST         = "selectUniformStockCheckToRequest";
	private final String INSERT_UNIFORM_LIST_TO_REQUEST               = "insertUniformListToRequest";
	private final String SELECT_UNIFORM_COUNT_TO_LIST                 = "selectUniformCountToList";
	private final String SELECT_UNIFORM_LIST_TO_LIST                  = "selectUniformListToList";
	private final String DELETE_UNIFORM_REQUEST_TO_LIST               = "deleteUniformRequestToList";
	private final String SELECT_UNIFORM_COUNT_TO_CONFIRM              = "selectUniformCountToConfirm";
	private final String SELECT_UNIFORM_LIST_TO_CONFIRM               = "selectUniformListToConfirm";
	private final String UPDATE_UNIFORM_STATUS_TO_CONFIRM             = "updateUniformStatusToConfirm";
	private final String SELECT_UNIFORM_TYPE_OF_CLOTHES_LIST_TO_ITEM_INFO = "selectUniformTypeofclothesListToItemInfo";
	private final String SELECT_UNIFORM_COLOR_LIST_TO_ITEM_INFO         = "selectUniformColorListToItemInfo";
	private final String SELECT_UNIFORM_SIZE_LIST_TO_ITEM_INFO          = "selectUniformSizeListToItemInfo";
	private final String SELECT_UNIFORM_MAX_SEQ_TO_ITEM_INFO            = "selectUniformMaxSeqToItemInfo";
	private final String INSERT_UNIFORM_LIST_TO_ITEM_INFO               = "insertUniformListToItemInfo";
	private final String UPDATE_UNIFORM_LIST_TO_ITEM_INFO               = "updateUniformListToItemInfo";
	private final String DELETE_UNIFORM_LIST_TO_ITEM_INFO               = "deleteUniformListToItemInfo";
	private final String SELECT_UNIFORM_COUNT_TO_STOCK_MANAGEMENT       = "selectUniformCountToStockManagement";
	private final String SELECT_UNIFORM_LIST_TO_STOCK_MANAGEMENT        = "selectUniformListToStockManagement";
	private final String SELECT_UNIFORM_MAX_SEQ_TO_STOCK_MANAGEMENT     = "selectUniformMaxSeqToStockManagement";
	private final String SELECT_UNIFORM_TOTAL_COUNT_TO_STOCK_MANAGEMENT = "selectUniformTotalCountToStockManagement";
	private final String INSERT_UNIFORM_LIST_TO_STOCK_MANAGEMENT        = "insertUniformListToStockManagement";
	private final String DELETE_UNIFORM_LIST_TO_STOCK_MANAGEMENT        = "deleteUniformListToStockManagement";
	private final String UPDATE_UNIFORM_LIST_TO_REJECT                  = "updateUniformListToReject";
	private final String UPDATE_UNIFORM_CONFIRM_LIST_TO_REQUEST         = "updateUniformConfirmListToRequest";
	private final String SELECT_UNIFORM_REMAIN_QTY                      = "selectUniformRemainQty";
	private final String SELECT_UNIFORM_CLR_QTY                      	= "selectUniformClrQty";
	private final String SELECT_UNIFORM_QUANTITY_CHK_UNIFORM_NM         = "selectUniformQuantityChkUniformNm";
	private final String DELETE_UNIFORM_ITEM_TO_DOWN_CODE               = "deleteUniformItemToDownCode";
	private final String DELETE_UNIFORM_ITEM_TO_DOWN_CODE_SIZE          = "deleteUniformItemToDownCodeSize";
	private final String SELECT_UNIFORM_COUNT_TO_STOCK_MANAGEMENT_LIST  = "selectUniformCountToStockManagementList";
	private final String SELECT_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST   = "selectUniformListToStockManagementList";

	private final String SELECT_LIST_TO_STOCK_DETAIL_IN 				= "selectListToStockDetailIn";
	private final String SELECT_COUNT_TO_STOCK_DETAIL_OUT 				= "selectCountToStockDetailOut";
	private final String SELECT_LIST_TO_STOCK_DETAIL_OUT 				= "selectListToStockDetailOut";
	private final String SELECT_LIST_TO_STOCK_DETAIL_BASIC 				= "selectGridStockDetailBasic";
	private final String UPDATE_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST        = "updateUniformListToStockManagementList";
	private final String INSERT_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST        = "insertUniformListToStockManagementList";


	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectTypeofclothesComboList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_TYPE_OF_CLOTHES_COMBO_LIST, corp_cd);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectColorComboList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_COLOR_COMBO_LIST, corp_cd);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectSizeComboList(String corp_cd){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_SIZE_COMBO_LIST, corp_cd);
	}

	@Override
	public String selectUniformCycleToRequest(BgabGascaf01Dto vo){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_CYCLE_TO_REQUEST, vo);
	}
	@Override
	public int selectCountToRequest(BgabGascaf01Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_COUNT_TO_REQUEST, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf01Dto> selectListToRequest(BgabGascaf01Dto vo){
//		UNIF_CLR_REMAIN_QTY_INFO
		List<BgabGascaf01Dto> rtnList = getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_LIST_TO_REQUEST, vo);

//		for (BgabGascaf01Dto bgabGascaf01Dto : rtnList) {
//			HashMap<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("eeno", bgabGascaf01Dto.getEeno());
//			paramMap.put("unif_type_cd", bgabGascaf01Dto.getUnif_type_cd());
//			paramMap.put("unif_clr_cd", bgabGascaf01Dto.getUnif_clr_cd());
//			paramMap.put("corp_cd", vo.getCorp_cd());
//
//			HashMap<String, String> rtnMap = (HashMap<String, String>)getSqlMapClientTemplate().queryForObject(SELECT_UNIF_CLR_REMAIN_QTY_INFO, paramMap);
//
//			if(rtnMap != null){
//				bgabGascaf01Dto.setRemain_qty(rtnMap.get("ResultVal").toString());
//			} else {
//				bgabGascaf01Dto.setRemain_qty("0");
//			}
			
//			BgabGascaf01Dto vo1 = new BgabGascaf01Dto();
//			vo1.setEeno(bgabGascaf01Dto.getEeno());
//			vo1.setUnif_type_cd(bgabGascaf01Dto.getUnif_type_cd());
//			vo1.setUnif_clr_cd(bgabGascaf01Dto.getUnif_clr_cd());
//			vo1.setCorp_cd(vo.getCorp_cd());
//			
//			String rtnVal = (String)getSqlMapClientTemplate().queryForObject(SELECT_UNIF_CLR_REMAIN_QTY_INFO, vo1);
//			bgabGascaf01Dto.setRemain_qty(rtnVal);
//		}

		return rtnList;
	}
	@Override
	public int selectStockCheckToRequest(BgabGascaf01Dto vo){
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_STOCK_CHECK_TO_REQUEST, vo), 0);
	}
	@Override
	public int insertListToRequest(List<BgabGascaf01Dto> vo){
		return insert(INSERT_UNIFORM_LIST_TO_REQUEST, vo);
	}

	@Override
	public int selectCountToList(BgabGascaf01Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_COUNT_TO_LIST, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf01Dto> selectListToList(BgabGascaf01Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_LIST_TO_LIST, vo);
	}
	@Override
	public int deleteRequestToList(List<BgabGascaf01Dto> vo){
		return delete(DELETE_UNIFORM_REQUEST_TO_LIST, vo);
	}
	@Override
	public int selectCountToConfirm(BgabGascaf01Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_COUNT_TO_CONFIRM, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf01Dto> selectListToConfirm(BgabGascaf01Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_LIST_TO_CONFIRM, vo);
	}
	@Override
	public int updateUniformStatusToConfirm(BgabGascaf01Dto vo){
		return update(UPDATE_UNIFORM_STATUS_TO_CONFIRM, vo);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectTypeofclothesListToItemInfo(BgabGascaf02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_TYPE_OF_CLOTHES_LIST_TO_ITEM_INFO, vo);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectColorListToItemInfo(BgabGascaf02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_COLOR_LIST_TO_ITEM_INFO, vo);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf02Dto> selectSizeListToItemInfo(BgabGascaf02Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_SIZE_LIST_TO_ITEM_INFO, vo);
	}
	@Override
	public int selectMaxSeqToItemInfo(BgabGascaf02Dto vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_MAX_SEQ_TO_ITEM_INFO, vo));
	}
	@Override
	public int insertListToItemInfo(List<BgabGascaf02Dto> list){
		return insert(INSERT_UNIFORM_LIST_TO_ITEM_INFO, list);
	}
	@Override
	public int updateListToItemInfo(List<BgabGascaf02Dto> list){
		return update(UPDATE_UNIFORM_LIST_TO_ITEM_INFO, list);
	}
	@Override
	public int deleteListToItemInfo(List<BgabGascaf02Dto> list){
		return delete(DELETE_UNIFORM_LIST_TO_ITEM_INFO, list);
	}

	@Override
	public int selectCountToStockManagement(BgabGascaf03Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_COUNT_TO_STOCK_MANAGEMENT, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf03Dto> selectListToStockManagement(BgabGascaf03Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_LIST_TO_STOCK_MANAGEMENT, vo);
	}
	@Override
	public String selectMaxSeqToStockManagement(BgabGascaf03Dto vo){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_MAX_SEQ_TO_STOCK_MANAGEMENT, vo);
	}
	@Override
	public int selectUniformTotalCountToStockManagement(BgabGascaf03Dto vo){
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_TOTAL_COUNT_TO_STOCK_MANAGEMENT, vo), 0);
	}
	@Override
	public int insertListToStockManagement(BgabGascaf03Dto vo){
		return insert(INSERT_UNIFORM_LIST_TO_STOCK_MANAGEMENT, vo);
	}
	@Override
	public int deleteListToStockManagement(BgabGascaf03Dto vo){
		return delete(DELETE_UNIFORM_LIST_TO_STOCK_MANAGEMENT, vo);
	}
	@Override
	public int updateUniformListToReject(BgabGascaf01Dto vo){
		return update(UPDATE_UNIFORM_LIST_TO_REJECT, vo);
	}
	@Override
	public int updateUniformConfirmListToRequest(BgabGascaf01Dto vo){
		return update(UPDATE_UNIFORM_CONFIRM_LIST_TO_REQUEST, vo);
	}
	@Override
	public String selectUniformRemainQty(BgabGascaf01Dto vo){
		return StringUtil.isNullToString(getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_REMAIN_QTY, vo));
	}
	@Override
	public String selectUniformClrQty(BgabGascaf01Dto vo){
		return StringUtil.isNullToString(getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_CLR_QTY, vo));
	}
	@Override
	public BgabGascaf01Dto selectUniformQuantityChkUniformNm(BgabGascaf01Dto vo){
		return (BgabGascaf01Dto)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_QUANTITY_CHK_UNIFORM_NM, vo);
	}
	@Override
	public int deleteUniformItemToDownCode(BgabGascaf02Dto vo){
		return update(DELETE_UNIFORM_ITEM_TO_DOWN_CODE, vo);
	}
	@Override
	public int deleteUniformItemToDownCodeSize(BgabGascaf02Dto vo){
		return update(DELETE_UNIFORM_ITEM_TO_DOWN_CODE_SIZE, vo);
	}
	@Override
	public int selectCountToStockManagementList(BgabGascaf03Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_UNIFORM_COUNT_TO_STOCK_MANAGEMENT_LIST, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf03Dto> selectListToStockManagementList(BgabGascaf03Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST, vo);
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf03Dto> selectListToStockDetailIn(BgabGascaf03Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_STOCK_DETAIL_IN, vo);
	}
	@Override
	public int selectCountToStockDetailOut(BgabGascaf03Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_STOCK_DETAIL_OUT, vo));
	}
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascaf03Dto> selectListToStockDetailOut(BgabGascaf03Dto vo){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_STOCK_DETAIL_OUT, vo);
	}
	@Override
	public BgabGascaf03Dto selectGridStockDetailBasic(BgabGascaf03Dto vo){
		return (BgabGascaf03Dto)getSqlMapClientTemplate().queryForObject(SELECT_LIST_TO_STOCK_DETAIL_BASIC, vo);
	}
	@Override
	public int updateListToStockManagementList(List<BgabGascaf03Dto> vo){
		return update(UPDATE_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST, vo);
	}
	@Override
	public int insertListToStockManagementList(List<BgabGascaf03Dto> vo){
		return insert(INSERT_UNIFORM_LIST_TO_STOCK_MANAGEMENT_LIST, vo);
	}

}
