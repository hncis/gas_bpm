package com.hncis.businessTravel.dao;

import java.util.List;

import com.hncis.businessTravel.vo.BgabGascbt01;
import com.hncis.businessTravel.vo.BgabGascbt02;
import com.hncis.businessTravel.vo.BgabGascbt03;
import com.hncis.businessTravel.vo.BgabGascbt04;
import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessTravel.vo.BgabGascbt07;
import com.hncis.businessTravel.vo.BgabGascbt08;
import com.hncis.businessTravel.vo.BgabGascbt09;
import com.hncis.businessTravel.vo.BgabGascbtDto;
import com.hncis.businessTravel.vo.BgabGascbtVoucherExcelDto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonCode;

public interface BusinessTravelDao {

	public int insertBTToRequestByBasic(BgabGascbt01 bsicInfo);
	
	public int insertBTToRequestByTraveler (List<BgabGascbt02> travelerList);
	
	public int insertBTToRequestBySchedule (List<BgabGascbt03> scheduleList);
	
	public int updateBTToRequestByBasic(BgabGascbt01 bsicInfo);
	
	public int updateBTToRequestByTraveler (List<BgabGascbt02> travelerList);
	
	public int updateBTToRequestBySchedule (List<BgabGascbt03> scheduleList);
	
	public int deleteBTToRequestByBasic(BgabGascbt01 bsicInfo);
	
	public int deleteBTToRequestByTraveler (BgabGascbt02 bgabGascbt02);
	
	public int deleteBTToRequestBySchedule (BgabGascbt03 bgabGascbt03);
	
	public BgabGascbt01 getSelectBTToRequestByBasicInfo(BgabGascbt01 bsicInfo);
	
	public List<BgabGascbt02> getSelectBTToRequestByTraveler(BgabGascbt02 bgabGascbt02);
	public List<BgabGascbt02> getSelectBTToRequestByVirtualTraveler(BgabGascbt02 bgabGascbt02);
	
	public List<BgabGascbt03> getSelectBTToRequestBySchedule(BgabGascbt03 bgabGascbt03);
	
	public String getSelectCountBTToList(BgabGascbt01 bgabGascbt01);
	
	public List<BgabGascbt01> getSelectBTToList(BgabGascbt01 bgabGascbt01);
	
	public List<CommonCode> getSelectBTToComboListByReport(CommonCode code);
	
	public List<BgabGascbt04> getSelectBTToReport(BgabGascbt04 bgabGascbt04);
	
	public List<BgabGascbt04> getSelectBTToReportCard(BgabGascbt04 bgabGascbt04);

	public List<BgabGascbt04> getSelectBTToReportVendor(BgabGascbt04 bgabGascbt04);
	
	public BgabGascbt04 getSelectBTToReportInfo(BgabGascbt04 bgabGascbt04);
	
	public int updateBTToReportBySapYn (List<BgabGascbt04> bgabGascbt04List);
	
	public int insertBTToReport (List<BgabGascbt04> bgabGascbt04List);
	
	public int updateBTToReport (List<BgabGascbt04> bgabGascbt04List);
	
	public int deleteBTToReport (List<BgabGascbt04> bgabGascbt04List);
	
	public String getSelectMaxSeqBTToReport(BgabGascbt02 bgabGascbt02);
	
	public int insertBTToFile (BgabGascZ011Dto bgabGascZ011Dto);
	
	public List<BgabGascZ011Dto> getSelectBTToFile(BgabGascZ011Dto bgabGascZ011Dto);
	
	public BgabGascZ011Dto getSelectBTToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	
	public int deleteBTToFile (List<BgabGascZ011Dto> bgabGascZ011List);
	
	public int approveBTToRequest (BgabGascbt01 bgabGascbt01);
	
	public int approveCancelBtToRequest (BgabGascbt01 bgabGascbt01);
	
	public String getSelectApprovalInfoToRequest(BgabGascbt01 bgabGascbt01);
	
	public int confirmBTToRequest(BgabGascbt01 bgabGascbt01);
	
	public int checkBTToConfirmList(List<BgabGascbt01> bgabGascbt01);
	
	public List<BgabGascbtDto> getSelectTravelerToBudget(BgabGascbt01 bgabGascbt01);
	
	public int updateTravelerTotBudget(BgabGascbtDto bgabGascbtDto);
	
	public String getSelectCountBTToReportBySapYn(BgabGascbt04 bgabGascbt04);
	
	public int updateBTToTravelerBySapYn (BgabGascbt02 bgabGascbt02);
	
	public String getSelectCountCountryManagement(BgabGascz005Dto bgabGascz005Dto);
	
	public List<BgabGascz005Dto> getSelectCountryManagement(BgabGascz005Dto bgabGascz005Dto);
	
	public int insertListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto);
	
	public int updateListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto);
	
	public int deleteListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto);
	
	public String getSelectCountBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto);
	
	public List<BgabGascbtDto> getSelectBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto);
	
	public String getSelectCountExpenseManagement(BgabGascbt06 bgabGascbt06);
	
	public List<BgabGascbt06> getSelectExpenseManagement(BgabGascbt06 bgabGascbt06);
	
	public String getSelectCountBudgetView(BgabGascbt02 bgabGascbt02);
	
	public List<BgabGascbt02> getSelectBudgetView(BgabGascbt02 bgabGascbt02);
	
	public int insertListToExpenseManagement (List<BgabGascbt06> bgabGascbt06);
	
	public int updateListToExpenseManagement (List<BgabGascbt06> bgabGascbt06);
	
	public int deleteListToExpenseManagement (List<BgabGascbt06> bgabGascbt06);
	
	public int updateBTToTravelerByStatus (BgabGascbt02 bgabGascbt02);
	
	public int confirmToExpenseManagement(List<BgabGascbt02> travelerList);
	
	public BgabGascbtDto getSelectBTToTravelerExcelInfo(BgabGascbt02 bgabGascbt02);
	
	public List<BgabGascbtDto> getSelectBTToTravelersExcelList(BgabGascbt02 bgabGascbt02);
	
	public List<BgabGascbt04> getSelectBTToExpenseExcelList(BgabGascbt02 bgabGascbt02);

	public List<BgabGascbtVoucherExcelDto> getSelectListBtToVoucherList(BgabGascbtVoucherExcelDto bgabGascbtVoucherExcelDto);

	public int updateBTToReportBySapYnForVoucher(List<BgabGascbtVoucherExcelDto> travelerList);

	public int updateBTToTravelerBySapYnForVoucher(List<BgabGascbtVoucherExcelDto> travelerList);
	
	public String selectCountBtCustomerToList(BgabGascbt01 bgabGascbt01);
	
	public List<BgabGascbt01> selectListBtCustomerToList(BgabGascbt01 bgabGascbt01);
	
	public List<BgabGascbt07> selectFlightConfirmListBtToRequest(BgabGascbt07 bgabGascbt07);
	public int insertFlightConfirmBtToRequest(List<BgabGascbt07> iList);
	public int updateFlightConfirmBtToRequest(List<BgabGascbt07> iList);
	public int deleteFlightConfirmBtToRequest(BgabGascbt07 dList);
	
	public List<BgabGascbt08> selectHotelConfirmListBtToRequest(BgabGascbt08 bgabGascbt08);
	public int insertHotelConfirmBtToRequest(List<BgabGascbt08> iList);
	public int updateHotelConfirmBtToRequest(List<BgabGascbt08> iList);
	public int deleteHotelConfirmBtToRequest(BgabGascbt08 dList);
	
	public List<BgabGascbt09> selectRentCarListBtToRequest(BgabGascbt09 bgabGascbt09);
	public int insertRentCarBtToRequest(List<BgabGascbt09> iList);
	public int updateRentCarBtToRequest(List<BgabGascbt09> iList);
	public int deleteRentCarBtToRequest(BgabGascbt09 dList);
	
	public int updateCompleteFinalPO(BgabGascbt04 param);
	public int updateTravelerTempPo(BgabGascbt02 param);
	
	public int updateBTToReportBySapYnRow (BgabGascbt04 bgabGascbt04);
	public BgabGascz005Dto getSelectDummyVendorInfo (BgabGascz005Dto dto);
	
	public List<BgabGascbt02> getSelectTravelRejectSubmitPoSearch(BgabGascbt02 dto);
	
	public int updateInfoBtToReject(BgabGascbt01 param);

	public List<BgabGascZ011Dto> doSearchBTToSendMail(BgabGascZ011Dto bgabGascZ011Dto);
	
	public int vendorCheckBTToConfirmList(List<BgabGascbt01> bgabGascbt01);
	public int saveVendorCheckBTToSaveDetail(BgabGascbt01 dto);
	
	public List<BgabGascbt01> selectBtToExcelList(BgabGascbt01 bgabGascbt04);
	
	public String getSelectConfirmToExpenseCount(BgabGascbt02 dto);
}
