package com.hncis.books.dao;

import java.util.List;

import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.books.vo.BgabGascbkExcelTempDto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;


public interface BooksDao {

	List<BgabGascbk03Dto> selectBkToLrgCombo(BgabGascbk03Dto dto);
	List<BgabGascbk04Dto> selectBkToMrgCombo(BgabGascbk04Dto dto);
	
	int selectCountBkToBookList(BgabGascbk01Dto dto);
	List<BgabGascbk01Dto> selectBkToBookList(BgabGascbk01Dto dto);
	int selectBkToBookExtrQty(BgabGascbk02Dto dto);
	int insertBkToBookRequest(BgabGascbk02Dto dto);
	int updateBkToBookReturn(BgabGascbk02Dto dto);
	int updateBkToBookReturnList(List<BgabGascbk02Dto> list);
	public int updateBkToBookRent(List<BgabGascbk02Dto> list);
	
	int selectCountBkToBookRentList(BgabGascbk02Dto dto);
	List<BgabGascbk02Dto> selectBkToBookRentList(BgabGascbk02Dto dto);
	public int deleteRentListToRequestCancel(List<BgabGascbk02Dto> dto);
	
	int insertBkToLrgList(List<BgabGascbk03Dto> iList);
	int updateBkToLrgList(List<BgabGascbk03Dto> uList);
	List<BgabGascbk03Dto> selectBkListToLrgInfo(BgabGascbk03Dto vo);
	int insertBkToMrgList(List<BgabGascbk04Dto> iList);
	int updateBkToMrgList(List<BgabGascbk04Dto> uList);
	List<BgabGascbk04Dto> selectBkListToMrgInfo(BgabGascbk04Dto vo);
	int deleteBkToLrgList(List<BgabGascbk03Dto> dList);
	int deleteBkToMrgDtlList(List<BgabGascbk03Dto> dList);
	int deleteBkToMrgList(List<BgabGascbk04Dto> dList);
	
	int selectCountBkToBookMgmtList(BgabGascbk01Dto dto);
	List<BgabGascbk01Dto> selectBkToBookMgmtList(BgabGascbk01Dto dto);
	
	int isnertBkToBookInfo(BgabGascbk01Dto dto);
	int deleteBkToBookInfo(BgabGascbk01Dto dto);
	BgabGascbk01Dto selectInfoBkToBookInfo(BgabGascbk01Dto dto);
	public int insertBkToExcelTemp(List<BgabGascbkExcelTempDto> list);
	
	public int selectBkToExcelTempMrgChk();
	public int selectBkToMrgChk();
	public List<BgabGascbkExcelTempDto> selectBkToLrgList(BgabGascbkExcelTempDto dto);
	public int selectBkToLrgChk(BgabGascbkExcelTempDto dto);
	public int insertBkLrgToExcelTemp(BgabGascbkExcelTempDto list);
	public int insertBkMrgToExcelTemp(BgabGascbkExcelTempDto list);
	public int deleteBkToExcelTemp();
	
	public int insertBkToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectBkToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectBkToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteBkToFile (List<BgabGascZ011Dto> bgabGascZ011List);

}
