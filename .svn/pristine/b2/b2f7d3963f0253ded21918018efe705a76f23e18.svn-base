package com.hncis.gift.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.gift.vo.BgabGascgf01Dto;
import com.hncis.gift.vo.BgabGascgf02Dto;
import com.hncis.gift.vo.BgabGascgf03Dto;
import com.hncis.gift.vo.BgabGascgf04Dto;
import com.hncis.gift.vo.BgabGascgf05Dto;
import com.hncis.gift.vo.BgabGascgfExcelTempDto;


public interface GiftDao {

	List<BgabGascgf03Dto> selectGfToLrgCombo(BgabGascgf03Dto dto);
	List<BgabGascgf04Dto> selectGfToMrgCombo(BgabGascgf04Dto dto);

	List<BgabGascgf01Dto> selectGfToGiftList(BgabGascgf01Dto vo);
	int selectGfToGiftCount(BgabGascgf01Dto vo);

	int selectGfToGiftExtrQty(BgabGascgf02Dto dto);
	int insertGfToGiftRequest(BgabGascgf02Dto dto);
	int updateGfToGiftRequestQty(BgabGascgf02Dto dto);
	public int approveCancelGFToRequest(BgabGascgf02Dto dto);
	public int confirmGFToRequest(BgabGascgf02Dto dto);
	public int rejectGFToRequest(BgabGascgf02Dto dto);

	public int deleteGfToRequestCancel(BgabGascgf02Dto dto);
	public int updateGfToGiftRequestCancelQty(List<BgabGascgf02Dto> dto);
	public int updateGfToStatus(List<BgabGascgf02Dto> dto);

	int selectCountGfToReqList(BgabGascgf02Dto dto);
	List<BgabGascgf02Dto> selectGfToReqList(BgabGascgf02Dto dto);


	int insertGfToLrgList(List<BgabGascgf03Dto> iList);
	int updateGfToLrgList(List<BgabGascgf03Dto> uList);
	List<BgabGascgf03Dto> selectGfListToLrgInfo(BgabGascgf03Dto vo);
	int insertGfToMrgList(List<BgabGascgf04Dto> iList);
	int updateGfToMrgList(List<BgabGascgf04Dto> uList);
	List<BgabGascgf04Dto> selectGfListToMrgInfo(BgabGascgf04Dto vo);
	int deleteGfToLrgList(List<BgabGascgf03Dto> dList);
	int deleteGfToMrgDtlList(List<BgabGascgf03Dto> dList);
	int deleteGfToMrgList(List<BgabGascgf04Dto> dList);

	int selectCountGfToGiftMgmtList(BgabGascgf01Dto dto);
	List<BgabGascgf01Dto> selectGfToGiftMgmtList(BgabGascgf01Dto dto);

	int isnertGfToGiftInfo(BgabGascgf01Dto dto);
	int deleteGfToGiftInfo(BgabGascgf01Dto dto);
	BgabGascgf01Dto selectInfoGfToGiftInfo(BgabGascgf01Dto dto);
	BgabGascgf01Dto selectInfoGfToGiftInfoByIfId(BgabGascgf01Dto dto);
	BgabGascgf02Dto selectInfoGfToGiftRequstInfo(BgabGascgf02Dto dto);

	public int insertGfToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectGfToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectGfToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteGfToFile (List<BgabGascZ011Dto> bgabGascZ011List);

	public int insertGfToExcelTemp(List<BgabGascgfExcelTempDto> list);
	public int selectGfToExcelTempMrgChk(BgabGascgfExcelTempDto chkDto);
	public int selectGfToMrgChk(BgabGascgfExcelTempDto chkDto);
	public List<BgabGascgfExcelTempDto> selectGfToLrgList(BgabGascgfExcelTempDto dto);
	public int selectGfToLrgChk(BgabGascgfExcelTempDto dto);
	public int insertGfLrgToExcelTemp(BgabGascgfExcelTempDto list);
	public int insertGfMrgToExcelTemp(BgabGascgfExcelTempDto list);
	public int deleteGfToExcelTemp(BgabGascgfExcelTempDto chkDto);
	public BgabGascgf05Dto selectXgf08Info(BgabGascgf05Dto dto);
	public int insertXgf08Info(BgabGascgf05Dto list);
	public int updateXgf08Info(BgabGascgf05Dto list);
	public int getChkRequestYmd(BgabGascgf02Dto dto);
	public int getChkRequestPossible(BgabGascgf02Dto dto);

}
