package com.hncis.gift.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.gift.vo.BgabGascgf01Dto;
import com.hncis.gift.vo.BgabGascgf02Dto;
import com.hncis.gift.vo.BgabGascgf03Dto;
import com.hncis.gift.vo.BgabGascgf04Dto;
import com.hncis.gift.vo.BgabGascgf05Dto;


public interface GiftManager {

	public List<BgabGascgf03Dto> selectGfToLrgCombo(BgabGascgf03Dto code);
	public List<BgabGascgf04Dto> selectGfToMrgCombo(BgabGascgf04Dto code);


	public List<BgabGascgf01Dto> selectGfToGiftList(BgabGascgf01Dto vo);
	public int selectGfToGiftCount(BgabGascgf01Dto vo);

	public CommonMessage insertGfToGiftRequest(BgabGascgf02Dto dto);
	
	public CommonMessage approveGfToGiftRequest(BgabGascgf02Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public CommonMessage approveCancelGfToGiftRequest(BgabGascgf02Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public CommonMessage confirmGFToRequest(BgabGascgf02Dto dto);
	public CommonMessage rejectGFToRequest(BgabGascgf02Dto dto);
	
//	public CommonMessage updateGfToGiftRequest(BgabGascgf02Dto dto);
	public int deleteGfToRequestCancel(BgabGascgf02Dto dto);
	public int updateGfToStatus(BgabGascgf02Dto dto, List<BgabGascgf02Dto> uList);

	public int selectCountGfToReqList(BgabGascgf02Dto dto);
	public List<BgabGascgf02Dto> selectGfToReqList(BgabGascgf02Dto dto);

	public void saveGfToLrgList(List<BgabGascgf03Dto> iList, List<BgabGascgf03Dto> uList);
	public List<BgabGascgf03Dto> selectGfListToLrgInfo(BgabGascgf03Dto vo);
	public void saveGfToMrgList(List<BgabGascgf04Dto> iList, List<BgabGascgf04Dto> uList);
	public List<BgabGascgf04Dto> selectGfListToMrgInfo(BgabGascgf04Dto vo);
	public void deleteGfToLrgList(List<BgabGascgf03Dto> dList);
	public void deleteGfToMrgList(List<BgabGascgf04Dto> dList);

	public int selectCountGfToGiftMgmtList(BgabGascgf01Dto dto);
	public List<BgabGascgf01Dto> selectGfToGiftMgmtList(BgabGascgf01Dto dto);

	public CommonMessage isnertGfToGiftInfo(BgabGascgf01Dto dto);
	public CommonMessage deleteGfToGiftInfo(BgabGascgf01Dto dto);
	public BgabGascgf01Dto selectInfoGfToGiftInfo(BgabGascgf01Dto dto);
	public BgabGascgf01Dto selectInfoGfToGiftInfoByIfId(BgabGascgf01Dto dto);
	public BgabGascgf02Dto selectInfoGfToGiftRequstInfo(BgabGascgf02Dto dto);

	public void saveGfToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectGfToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectGfToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteGfToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
	public BgabGascgf05Dto selectXgf08Info(BgabGascgf05Dto dto);
	public CommonMessage saveXgf08Info(BgabGascgf05Dto dto);



}
