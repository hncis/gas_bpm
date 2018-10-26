package com.hncis.gift.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.gift.dao.GiftDao;
import com.hncis.gift.vo.BgabGascgf01Dto;
import com.hncis.gift.vo.BgabGascgf02Dto;
import com.hncis.gift.vo.BgabGascgf03Dto;
import com.hncis.gift.vo.BgabGascgf04Dto;
import com.hncis.gift.vo.BgabGascgf05Dto;
import com.hncis.gift.vo.BgabGascgfExcelTempDto;

public class GiftDaoImplByIBatis extends CommonDao implements GiftDao{

	private final String SELECT_GF_TO_LRG_COMBO        	 	 = "selectGfToLrgCombo";
	private final String SELECT_GF_TO_MRG_COMBO         	 = "selectGfToMrgCombo";

	private final String SELECT_GF_TO_GIFT_LIST         	 = "selectGfToGiftList";
	private final String SELECT_GF_TO_GIFT_COUNT         	 = "selectGfToGiftCount";

	private final String SELECT_GF_TO_GIFT_EXTR_QTY    	 	 = "selectGfToGiftExtrQty";
	private final String INSERT_GF_TO_GIFT_REQUEST    	 	 = "insertGfToGiftRequest";
	private final String UPDATE_GF_TO_GIFT_REQUEST_QTY		 = "updateGfToGiftRequestQty";
	private final String DELETE_GF_TO_REQUEST_CANCEL		 = "deleteGfToRequestCancel";
	private final String UPDATE_GF_TO_GIFT_REQUEST_CANCEL_QTY	= "updateGfToGiftRequestCancelQty";
	private final String UPDATE_GF_TO_STATUS	= "updateGfToStatus";

	private final String APPROVE_CANCEL_GF_TO_REQUEST	= "approveCancelGFToRequest";
	private final String CONFIRM_GF_TO_REQUEST			= "confirmGFToRequest";
	private final String REJECT_GF_TO_REQUEST			= "rejectGFToRequest";

	private final String SELECT_COUNT_GF_TO_REQ_LIST    	 = "selectCountGfToReqList";
	private final String SELECT_GF_TO_REQ_LIST    	 	 	 = "selectGfToReqList";

	private final String INSERT_GF_TO_LRG_LIST               = "insertGfToLrgList";
	private final String UPDATE_GF_TO_LRG_LIST               = "updateGfToLrgList";
	private final String SELECT_GF_LIST_TO_LRG_INFO          = "selectGfListToLrgInfo";
	private final String INSERT_GF_TO_MRG_LIST               = "insertGfToMrgList";
	private final String UPDATE_GF_TO_MRG_LIST               = "updateGfToMrgList";
	private final String SELECT_GF_LIST_TO_MRG_INFO          = "selectGfListToMrgInfo";
	private final String DELETE_GF_TO_LRG_LIST               = "deleteGfToLrgList";
	private final String DELETE_GF_TO_MRG_DTL_LIST           = "deleteGfToMrgDtlList";
	private final String DELETE_GF_TO_MRG_LIST               = "deleteGfToMrgList";

	private final String SELECT_COUNT_GF_TO_GIFT_MGMT_LIST   = "selectCountGfToGiftMgmtList";
	private final String SELECT_GF_TO_GIFT_MGMT_LIST         = "selectGfToGiftMgmtList";

	private final String INSERT_GF_TO_GIFT_INFO         	 = "insertGfToGiftInfo";
	private final String DELETE_GF_TO_GIFT_INFO         	 = "deleteGfToGiftInfo";
	private final String SELECT_INFO_GF_TO_GIFT_INFO    	 = "selectInfoGfToGiftInfo";
	private final String SELECT_INFO_GF_TO_GIFT_INFO_BY_IF_ID	= "selectInfoGfToGiftInfoByIfId";
	private final String SELECT_INFO_GF_TO_GIFT_REQUST_INFO    	 = "selectInfoGfToGiftRequstInfo";

	private final String INSERT_GF_TO_FILE					 = "insertGfToFile";
	private final String SELECT_GF_TO_FILE					 = "selectGfToFile";
	private final String DELETE_GF_TO_FILE					 = "deleteGfToFile";

	private final String INSERT_GF_TO_EXCEL_TEMP				 = "insertGfToExcelTemp";
	private final String SELECT_GF_TO_EXCEL_TEMP_MRG_CHK		 = "selectGfToExcelTempMrgChk";
	private final String SELECT_GF_TO_MRG_CHK					 = "selectGfToMrgChk";
	private final String SELECT_GF_TO_LRG_LIST					 = "selectGfToLrgList";
	private final String SELECT_GF_TO_LRG_CHK					 = "selectGfToLrgChk";
	private final String INSERT_GF_LRG_TO_EXCEL_TEMP			 = "insertGfLrgToExcelTemp";
	private final String INSERT_GF_MRG_TO_EXCEL_TEMP			 = "insertGfMrgToExcelTemp";
	private final String DELETE_GF_TO_EXCEL_TEMP				 = "deleteGfToExcelTemp";

	private final String SELECT_XGF08_INFO				 = "selectXgf08Info";
	private final String INSERT_XGF08_INFO				 = "insertXgf08Info";
	private final String UPDATE_XGF08_INFO				 = "updateXgf08Info";

	private final String GET_CHK_REQUEST_YMD				 = "getChkRequestYmd";
	private final String GET_CHK_REQUEST_POSSIBLE				 = "getChkRequestPossible";

	@Override
	public List<BgabGascgf03Dto> selectGfToLrgCombo(BgabGascgf03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_LRG_COMBO, dto);
	}
	@Override
	public List<BgabGascgf04Dto> selectGfToMrgCombo(BgabGascgf04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_MRG_COMBO, dto);
	}

	@Override
	public List<BgabGascgf01Dto> selectGfToGiftList(BgabGascgf01Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_GIFT_LIST, vo);
	}

	@Override
	public int selectGfToGiftCount(BgabGascgf01Dto vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_GIFT_COUNT, vo));
	}

	@Override
	public int selectGfToGiftExtrQty(BgabGascgf02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_GIFT_EXTR_QTY, dto));
	}
	@Override
	public int insertGfToGiftRequest(BgabGascgf02Dto dto) {
		return insert(INSERT_GF_TO_GIFT_REQUEST, dto);
	}
	@Override
	public int approveCancelGFToRequest(BgabGascgf02Dto dto) {
		return update(APPROVE_CANCEL_GF_TO_REQUEST, dto);
	}
	@Override
	public int confirmGFToRequest(BgabGascgf02Dto dto) {
		return update(CONFIRM_GF_TO_REQUEST, dto);
	}
	@Override
	public int rejectGFToRequest(BgabGascgf02Dto dto) {
		return update(REJECT_GF_TO_REQUEST, dto);
	}
	@Override
	public int updateGfToGiftRequestQty(BgabGascgf02Dto dto) {
		return update(UPDATE_GF_TO_GIFT_REQUEST_QTY, dto);
	}

	@Override
	public int deleteGfToRequestCancel(BgabGascgf02Dto dto) {
		return delete(DELETE_GF_TO_REQUEST_CANCEL, dto);
	}
	@Override
	public int updateGfToGiftRequestCancelQty(List<BgabGascgf02Dto> dto) {
		return update(UPDATE_GF_TO_GIFT_REQUEST_CANCEL_QTY, dto);
	}
	@Override
	public int updateGfToStatus(List<BgabGascgf02Dto> dto) {
		return update(UPDATE_GF_TO_STATUS, dto);
	}

	@Override
	public int selectCountGfToReqList(BgabGascgf02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_GF_TO_REQ_LIST, dto));
	}
	@Override
	public List<BgabGascgf02Dto> selectGfToReqList(BgabGascgf02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_REQ_LIST, dto);
	}

	@Override
	public int insertGfToLrgList(List<BgabGascgf03Dto> iList) {
		return insert(INSERT_GF_TO_LRG_LIST, iList);
	}

	@Override
	public int updateGfToLrgList(List<BgabGascgf03Dto> uList) {
		return update(UPDATE_GF_TO_LRG_LIST, uList);
	}

	@Override
	public List<BgabGascgf03Dto> selectGfListToLrgInfo(BgabGascgf03Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_LIST_TO_LRG_INFO, vo);
	}
	@Override
	public int insertGfToMrgList(List<BgabGascgf04Dto> iList) {
		return insert(INSERT_GF_TO_MRG_LIST, iList);
	}

	@Override
	public int updateGfToMrgList(List<BgabGascgf04Dto> uList) {
		return update(UPDATE_GF_TO_MRG_LIST, uList);
	}

	@Override
	public List<BgabGascgf04Dto> selectGfListToMrgInfo(BgabGascgf04Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_LIST_TO_MRG_INFO, vo);
	}

	@Override
	public int deleteGfToLrgList(List<BgabGascgf03Dto> dList) {
		return update(DELETE_GF_TO_LRG_LIST, dList);
	}

	@Override
	public int deleteGfToMrgDtlList(List<BgabGascgf03Dto> dList) {
		return update(DELETE_GF_TO_MRG_DTL_LIST, dList);
	}

	@Override
	public int deleteGfToMrgList(List<BgabGascgf04Dto> dList) {
		return update(DELETE_GF_TO_MRG_LIST, dList);
	}

	@Override
	public int selectCountGfToGiftMgmtList(BgabGascgf01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_GF_TO_GIFT_MGMT_LIST, dto));
	}

	@Override
	public List<BgabGascgf01Dto> selectGfToGiftMgmtList(BgabGascgf01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_GIFT_MGMT_LIST, dto);
	}

	@Override
	public int isnertGfToGiftInfo(BgabGascgf01Dto dto) {
		return insert(INSERT_GF_TO_GIFT_INFO, dto);
	}
	@Override
	public int deleteGfToGiftInfo(BgabGascgf01Dto dto) {
		return delete(DELETE_GF_TO_GIFT_INFO, dto);
	}

	@Override
	public BgabGascgf01Dto selectInfoGfToGiftInfo(BgabGascgf01Dto dto) {
		return (BgabGascgf01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_GF_TO_GIFT_INFO, dto);
	}

	@Override
	public BgabGascgf01Dto selectInfoGfToGiftInfoByIfId(BgabGascgf01Dto dto) {
		return (BgabGascgf01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_GF_TO_GIFT_INFO_BY_IF_ID, dto);
	}

	@Override
	public BgabGascgf02Dto selectInfoGfToGiftRequstInfo(BgabGascgf02Dto dto) {
		return (BgabGascgf02Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_GF_TO_GIFT_REQUST_INFO, dto);
	}

	@Override
	public int insertGfToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_GF_TO_FILE, bgabGascZ011Dto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectGfToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_FILE, bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectGfToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_FILE, bgabGascZ011Dto);
	}

	@Override
	public int deleteGfToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_GF_TO_FILE, bgabGascZ011List);
	}


	@Override
	public int insertGfToExcelTemp(List<BgabGascgfExcelTempDto> list) {
		return insert(INSERT_GF_TO_EXCEL_TEMP, list);
	}

	@Override
	public int selectGfToExcelTempMrgChk(BgabGascgfExcelTempDto chkDto) {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_EXCEL_TEMP_MRG_CHK,chkDto), 0);
	}
	@Override
	public int selectGfToMrgChk(BgabGascgfExcelTempDto chkDto) {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_MRG_CHK,chkDto), 0);
	}
	@Override
	public List<BgabGascgfExcelTempDto> selectGfToLrgList(BgabGascgfExcelTempDto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GF_TO_LRG_LIST, dto);
	}
	@Override
	public int selectGfToLrgChk(BgabGascgfExcelTempDto dto) {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GF_TO_LRG_CHK, dto), 0);
	}
	@Override
	public int insertGfLrgToExcelTemp(BgabGascgfExcelTempDto list) {
		return insert(INSERT_GF_LRG_TO_EXCEL_TEMP, list);
	}
	@Override
	public int insertGfMrgToExcelTemp(BgabGascgfExcelTempDto list) {
		return insert(INSERT_GF_MRG_TO_EXCEL_TEMP, list);
	}
	@Override
	public int deleteGfToExcelTemp(BgabGascgfExcelTempDto chkDto) {
		return delete(DELETE_GF_TO_EXCEL_TEMP,chkDto);
	}

	@Override
	public BgabGascgf05Dto selectXgf08Info(BgabGascgf05Dto dto) {
		return (BgabGascgf05Dto)getSqlMapClientTemplate().queryForObject(SELECT_XGF08_INFO, dto);
	}
	@Override
	public int insertXgf08Info(BgabGascgf05Dto dto) {
		return insert(INSERT_XGF08_INFO, dto);
	}
	@Override
	public int updateXgf08Info(BgabGascgf05Dto dto) {
		return update(UPDATE_XGF08_INFO, dto);
	}
	@Override
	public int getChkRequestYmd(BgabGascgf02Dto dto) {
		return (Integer)getSqlMapClientTemplate().queryForObject(GET_CHK_REQUEST_YMD, dto);
	}
	@Override
	public int getChkRequestPossible(BgabGascgf02Dto dto) {
		return (Integer)getSqlMapClientTemplate().queryForObject(GET_CHK_REQUEST_POSSIBLE, dto);
	}

}
