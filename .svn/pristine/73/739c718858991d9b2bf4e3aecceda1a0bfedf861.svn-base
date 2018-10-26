package com.hncis.books.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;

@Transactional
public interface BooksManager {

	public List<BgabGascbk03Dto> selectBkToLrgCombo(BgabGascbk03Dto code);
	public List<BgabGascbk04Dto> selectBkToMrgCombo(BgabGascbk04Dto code);
	
	public int selectCountBkToBookList(BgabGascbk01Dto dto);
	public List<BgabGascbk01Dto> selectBkToBookList(BgabGascbk01Dto dto);
	public CommonMessage updateBkToBookRequest(BgabGascbk02Dto dto);
	public CommonMessage updateBkToBookReturn(BgabGascbk02Dto dto);
	public CommonMessage updateBkToBookReturnList(List<BgabGascbk02Dto> list);
	public int updateBkToBookRent(List<BgabGascbk02Dto> list);
	
	public int selectCountBkToBookRentList(BgabGascbk02Dto dto);
	public List<BgabGascbk02Dto> selectBkToBookRentList(BgabGascbk02Dto dto);
	public int deleteRentListToRequestCancel(List<BgabGascbk02Dto> dto);
	
	public void saveBkToLrgList(List<BgabGascbk03Dto> iList, List<BgabGascbk03Dto> uList);
	public List<BgabGascbk03Dto> selectBkListToLrgInfo(BgabGascbk03Dto vo);
	public void saveBkToMrgList(List<BgabGascbk04Dto> iList, List<BgabGascbk04Dto> uList);
	public List<BgabGascbk04Dto> selectBkListToMrgInfo(BgabGascbk04Dto vo);
	public void deleteBkToLrgList(List<BgabGascbk03Dto> dList);
	public void deleteBkToMrgList(List<BgabGascbk04Dto> dList);
	
	public int selectCountBkToBookMgmtList(BgabGascbk01Dto dto);
	public List<BgabGascbk01Dto> selectBkToBookMgmtList(BgabGascbk01Dto dto);
	
	public CommonMessage isnertBkToBookInfo(BgabGascbk01Dto dto);
	public CommonMessage deleteBkToBookInfo(BgabGascbk01Dto dto);
	public BgabGascbk01Dto selectInfoBkToBookInfo(BgabGascbk01Dto dto);
	

	public void saveBkToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectBkToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectBkToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteBkToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
	
}
