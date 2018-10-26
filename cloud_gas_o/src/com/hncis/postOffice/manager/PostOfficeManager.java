package com.hncis.postOffice.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.postOffice.vo.BgabGascpo01Dto;

@Transactional
public interface PostOfficeManager {

	CommonMessage savePoToRequest(HttpServletRequest req, BgabGascpo01Dto dto);
	BgabGascpo01Dto selectInfoPoToRequest(BgabGascpo01Dto dto);
	CommonMessage updatePoToRequestForRequest(HttpServletRequest req, BgabGascpo01Dto dto);
	CommonMessage updatePoToRequestForRequestCancel(HttpServletRequest req, BgabGascpo01Dto dto);
	CommonMessage updatePoToRequestForConfirm(HttpServletRequest req, BgabGascpo01Dto dto);
	CommonMessage updatePoToRequestForConfirmCancel(HttpServletRequest req, BgabGascpo01Dto dto);
	CommonMessage deletePoToRequest(HttpServletRequest req, BgabGascpo01Dto dto);
	
	int selectCountPoToList(BgabGascpo01Dto dto);
	List<BgabGascpo01Dto> selectListPoToList(BgabGascpo01Dto dto);
	
	int selectCountPoToConfirm(BgabGascpo01Dto dto);
	List<BgabGascpo01Dto> selectListPoToConfirm(BgabGascpo01Dto dto);
	
	
	public void savePoToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectPoToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectPoToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deletePoToFile(List<BgabGascZ011Dto> bgabGascZ011IList);

	public int rejectPoToRequest (BgabGascpo01Dto dto);
}
