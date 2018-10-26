package com.hncis.postOffice.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.postOffice.vo.BgabGascpo01Dto;

public interface PostOfficeDao {

	public int insertPoToRequest(BgabGascpo01Dto dto);
	public BgabGascpo01Dto selectInfoPoToRequest(BgabGascpo01Dto dto);
	public int updatePoToRequestForRequest(BgabGascpo01Dto dto);
	public int updatePoToRequestForRequestCancel(BgabGascpo01Dto dto);
	public int updatePoToRequestForConfirm(BgabGascpo01Dto dto);
	public int updatePoToRequestForConfirmCancel(BgabGascpo01Dto dto);
	public int deletePoToRequest(BgabGascpo01Dto dto);
	
	public int selectCountPoToList(BgabGascpo01Dto dto);
	public List<BgabGascpo01Dto> selectListPoToList(BgabGascpo01Dto dto);
	
	public int selectCountPoToConfirm(BgabGascpo01Dto dto);
	public List<BgabGascpo01Dto> selectListPoToConfirm(BgabGascpo01Dto dto);
	
	public int insertPoToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectPoToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectPoToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deletePoToFile (List<BgabGascZ011Dto> bgabGascZ011List);
	
	public int rejectPoToRequest (BgabGascpo01Dto dto);
}
