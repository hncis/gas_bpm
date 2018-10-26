package com.hncis.books.dao.impl;

import java.util.List;

import com.hncis.books.dao.BooksDao;
import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.books.vo.BgabGascbkExcelTempDto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;

public class BooksDaoImplByIBatis extends CommonDao implements BooksDao{
	
	private final String SELECT_BK_TO_LRG_COMBO        	 	 = "selectBkToLrgCombo";
	private final String SELECT_BK_TO_MRG_COMBO         	 = "selectBkToMrgCombo";
	
	private final String SELECT_COUNT_BK_TO_BOOK_LIST   	 = "selectCountBkToBookList";
	private final String SELECT_BK_TO_BOOK_LIST         	 = "selectBkToBookList";
	private final String SELECT_BK_TO_BOOK_EXTR_QTY   	 	 = "selectBkToBookExtrQty";
	private final String INSERT_BK_TO_BOOK_REQUEST         	 = "insertBkToBookRequest";
	private final String UPDATE_BK_TO_BOOK_RETURN         	 = "updateBkToBookReturn";
	private final String UPDATE_BK_TO_BOOK_RETURN_LIST       = "updateBkToBookReturnList";
	private final String UPDATE_BK_TO_BOOK_RENT              = "updateBkToBookRent";
	
	private final String SELECT_COUNT_BK_TO_BOOK_RENT_LIST   = "selectCountBkToBookRentList";
	private final String SELECT_BK_TO_BOOK_RENT_LIST         = "selectBkToBookRentList";
	private final String DELETE_RENT_LIST_TO_REQUEST_CANCEL  = "deleteRentListToRequestCancel";
	
	private final String INSERT_BK_TO_LRG_LIST               = "insertBkToLrgList";
	private final String UPDATE_BK_TO_LRG_LIST               = "updateBkToLrgList";
	private final String SELECT_BK_LIST_TO_LRG_INFO          = "selectBkListToLrgInfo";
	private final String INSERT_BK_TO_MRG_LIST               = "insertBkToMrgList";
	private final String UPDATE_BK_TO_MRG_LIST               = "updateBkToMrgList";
	private final String SELECT_BK_LIST_TO_MRG_INFO          = "selectBkListToMrgInfo";
	private final String DELETE_BK_TO_LRG_LIST               = "deleteBkToLrgList";
	private final String DELETE_BK_TO_MRG_DTL_LIST           = "deleteBkToMrgDtlList";
	private final String DELETE_BK_TO_MRG_LIST               = "deleteBkToMrgList";
	
	private final String SELECT_COUNT_BK_TO_BOOK_MGMT_LIST   = "selectCountBkToBookMgmtList";
	private final String SELECT_BK_TO_BOOK_MGMT_LIST         = "selectBkToBookMgmtList";
	
	private final String INSERT_BK_TO_BOOK_INFO         	  = "insertBkToBookInfo";
	private final String DELETE_BK_TO_BOOK_INFO         	  = "deleteBkToBookInfo";
	private final String SELECT_INFO_BK_TO_BOOK_INFO    	  = "selectInfoBkToBookInfo";
	
	private final String INSERT_BK_TO_EXCEL_TEMP    		  = "insertBkToExcelTemp";
	private final String SELECT_BK_TO_EXCEL_TEMP_MRG_CHK      = "selectBkToExcelTempMrgChk";
	private final String SELECT_BK_TO_MRG_CHK    			  = "selectBkToMrgChk";
	private final String SELECT_BK_TO_LRG_LIST    			  = "selectBkToLrgList";
	private final String SELECT_BK_TO_LRG_CHK    			  = "selectBkToLrgChk";
	private final String INSERT_BK_LRG_TO_EXCEL_TEMP    	  = "insertBkLrgToExcelTemp";
	private final String INSERT_BK_MRG_TO_EXCEL_TEMP    	  = "insertBkMrgToExcelTemp";
	private final String DELETE_BK_TO_EXCEL_TEMP    		  = "deleteBkToExcelTemp";
	
	private final String INSERT_BK_TO_FILE					 = "insertBkToFile";
	private final String SELECT_BK_TO_FILE					 = "selectBkToFile";
	private final String DELETE_BK_TO_FILE					 = "deleteBkToFile";
	
	@Override
	public List<BgabGascbk03Dto> selectBkToLrgCombo(BgabGascbk03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_LRG_COMBO, dto);
	}
	@Override
	public List<BgabGascbk04Dto> selectBkToMrgCombo(BgabGascbk04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_MRG_COMBO, dto);
	}
	@Override
	public int selectCountBkToBookList(BgabGascbk01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BK_TO_BOOK_LIST, dto));
	}
	@Override
	public List<BgabGascbk01Dto> selectBkToBookList(BgabGascbk01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_BOOK_LIST, dto);
	}
	
	@Override
	public int selectCountBkToBookRentList(BgabGascbk02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BK_TO_BOOK_RENT_LIST, dto));
	}

	@Override
	public List<BgabGascbk02Dto> selectBkToBookRentList(BgabGascbk02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_BOOK_RENT_LIST, dto);
	}
	
	@Override
	public int deleteRentListToRequestCancel(List<BgabGascbk02Dto> dto) {
		return delete(DELETE_RENT_LIST_TO_REQUEST_CANCEL, dto);
	}
	
	@Override
	public int selectBkToBookExtrQty(BgabGascbk02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_BK_TO_BOOK_EXTR_QTY, dto));
	}
	@Override
	public int insertBkToBookRequest(BgabGascbk02Dto dto) {
		return insert(INSERT_BK_TO_BOOK_REQUEST, dto);
	}
	@Override
	public int updateBkToBookReturn(BgabGascbk02Dto dto) {
		return insert(UPDATE_BK_TO_BOOK_RETURN, dto);
	}
	@Override
	public int updateBkToBookReturnList(List<BgabGascbk02Dto> list) {
		return insert(UPDATE_BK_TO_BOOK_RETURN_LIST, list);
	}
	@Override
	public int updateBkToBookRent(List<BgabGascbk02Dto> list) {
		return insert(UPDATE_BK_TO_BOOK_RENT, list);
	}
	
	@Override
	public int insertBkToLrgList(List<BgabGascbk03Dto> iList) {
		return insert(INSERT_BK_TO_LRG_LIST, iList);
	}

	@Override
	public int updateBkToLrgList(List<BgabGascbk03Dto> uList) {
		return update(UPDATE_BK_TO_LRG_LIST, uList);
	}

	@Override
	public List<BgabGascbk03Dto> selectBkListToLrgInfo(BgabGascbk03Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_LIST_TO_LRG_INFO, vo);
	}

	@Override
	public int insertBkToMrgList(List<BgabGascbk04Dto> iList) {
		return insert(INSERT_BK_TO_MRG_LIST, iList);
	}

	@Override
	public int updateBkToMrgList(List<BgabGascbk04Dto> uList) {
		return update(UPDATE_BK_TO_MRG_LIST, uList);
	}

	@Override
	public List<BgabGascbk04Dto> selectBkListToMrgInfo(BgabGascbk04Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_LIST_TO_MRG_INFO, vo);
	}

	@Override
	public int deleteBkToLrgList(List<BgabGascbk03Dto> dList) {
		return update(DELETE_BK_TO_LRG_LIST, dList);
	}

	@Override
	public int deleteBkToMrgDtlList(List<BgabGascbk03Dto> dList) {
		return update(DELETE_BK_TO_MRG_DTL_LIST, dList);
	}

	@Override
	public int deleteBkToMrgList(List<BgabGascbk04Dto> dList) {
		return update(DELETE_BK_TO_MRG_LIST, dList);
	}

	@Override
	public int selectCountBkToBookMgmtList(BgabGascbk01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BK_TO_BOOK_MGMT_LIST, dto));
	}

	@Override
	public List<BgabGascbk01Dto> selectBkToBookMgmtList(BgabGascbk01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_BOOK_MGMT_LIST, dto);
	}
	
	@Override
	public int isnertBkToBookInfo(BgabGascbk01Dto dto) {
		return insert(INSERT_BK_TO_BOOK_INFO, dto);
	}
	@Override
	public int deleteBkToBookInfo(BgabGascbk01Dto dto) {
		return delete(DELETE_BK_TO_BOOK_INFO, dto);
	}
	
	@Override
	public BgabGascbk01Dto selectInfoBkToBookInfo(BgabGascbk01Dto dto) {
		return (BgabGascbk01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_BK_TO_BOOK_INFO, dto);
	}
	
	@Override
	public int insertBkToExcelTemp(List<BgabGascbkExcelTempDto> list) {
		return insert(INSERT_BK_TO_EXCEL_TEMP, list);
	}
	
	@Override
	public int selectBkToExcelTempMrgChk() {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_BK_TO_EXCEL_TEMP_MRG_CHK), 0);
	}
	@Override
	public int selectBkToMrgChk() {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_BK_TO_MRG_CHK), 0);
	}
	@Override
	public List<BgabGascbkExcelTempDto> selectBkToLrgList(BgabGascbkExcelTempDto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_LRG_LIST, dto);
	}
	@Override
	public int selectBkToLrgChk(BgabGascbkExcelTempDto dto) {
		return StringUtil.isNullToInt((String)getSqlMapClientTemplate().queryForObject(SELECT_BK_TO_LRG_CHK, dto), 0);
	}
	@Override
	public int insertBkLrgToExcelTemp(BgabGascbkExcelTempDto list) {
		return insert(INSERT_BK_LRG_TO_EXCEL_TEMP, list);
	}
	@Override
	public int insertBkMrgToExcelTemp(BgabGascbkExcelTempDto list) {
		return insert(INSERT_BK_MRG_TO_EXCEL_TEMP, list);
	}
	@Override
	public int deleteBkToExcelTemp() {
		return delete(DELETE_BK_TO_EXCEL_TEMP, new BgabGascbkExcelTempDto());
	}
	
	public int insertBkToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_BK_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectBkToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_BK_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectBkToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_BK_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deleteBkToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_BK_TO_FILE, bgabGascZ011List);
	}

}
