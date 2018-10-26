package com.hncis.businessTravel.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

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
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonSap;

@Transactional
public interface BusinessTravelManager {

	/**
	 * request page insert(bsicInfo, travelerList, scheduleList)
	 * @param BgabGascbt01
	 * @param List<BgabGascbt02>
	 * @param List<BgabGascbt03>
	 * @return int
	 */
	public int insertBTToRequest(BgabGascbt01 bgabGascbt01, List<BgabGascbt02> bgabGascbt02List, List<BgabGascbt03> bgabGascbt03List);

	/**
	 * request page update(bsicInfo, travelerList, scheduleList)
	 * @param BgabGascbt01
	 * @param List<BgabGascbt02>
	 * @param List<BgabGascbt03>
	 * @return int
	 */
	public int saveBTToRequest(BgabGascbt01 bgabGascbt01
			, List<BgabGascbt02> bgabGascbt02IList, List<BgabGascbt02> bgabGascbt02UList
//			, List<BgabGascbt03> bgabGascbt03IList, List<BgabGascbt03> bgabGascbt03UList
			, List<BgabGascbt07> bgabGascbt07IList, List<BgabGascbt07> bgabGascbt07UList
			, List<BgabGascbt08> bgabGascbt08IList, List<BgabGascbt08> bgabGascbt08UList
			, List<BgabGascbt09> bgabGascbt09IList, List<BgabGascbt09> bgabGascbt09UList
			, List<BgabGascbt02> bgabGascbt02VtIList, List<BgabGascbt02> bgabGascbt02VtUList);

	/**
	 * request page delete(bsicInfo)
	 * @param BgabGascbt01
	 * @return int
	 */
	public int deleteBTToRequest(BgabGascbt01 bgabGascbt01);

	/**
	 * request page delete(schedule)
	 * @param BgabGascbt03
	 * @return int
	 */
	public int deleteTravelerToRequest(BgabGascbt02 bgabGascbt02);

	/**
	 * request page delete(schedule)
	 * @param BgabGascbt03
	 * @return int
	 */
	public int deleteScheduleToRequest(BgabGascbt03 bgabGascbt03);

	/**
	 * request page search(bsicInfo)
	 * @param BgabGascbt01
	 * @return BgabGascbt01
	 */
	public BgabGascbt01 getSelectBTToRequestByBasicInfo(BgabGascbt01 bgabGascbt01);

	/**
	 * request page search(traveler)
	 * @param bgabGascbt02
	 * @return List<BgabGascbt02>
	 */
	public List<BgabGascbt02> getSelectBTToRequestByTraveler(BgabGascbt02 bgabGascbt02);
	public List<BgabGascbt02> getSelectBTToRequestByVirtualTraveler(BgabGascbt02 bgabGascbt02);

	/**
	 * request page search(schedule)
	 * @param bgabGascbt03
	 * @return List<BgabGascbt03>
	 */
	public List<BgabGascbt03> getSelectBTToRequestBySchedule(BgabGascbt03 bgabGascbt03);

	/**
	 * List page count search
	 * @param bgabGascbt01
	 * @return int
	 */
	public int getSelectCountBTToList(BgabGascbt01 bgabGascbt01);

	/**
	 * List page search
	 * @param bgabGascbt01
	 * @return List<BgabGascbt01>
	 */
	public List<BgabGascbt01> getSelectBTToList(BgabGascbt01 bgabGascbt01);

	/**
	 * List multiCombo search
	 * @param CgabGascz006Dto
	 * @return List<CgabGascManager>
	 */
	public List<CommonCode> getSelectBTToComboListByReport(CommonCode code);

	/**
	 * report page search
	 * @param bgabGascbt04
	 * @return List<BgabGascbt04>
	 */
	public List<BgabGascbt04> getSelectBTToReport(BgabGascbt04 bgabGascbt04);
	public List<BgabGascbt04> getSelectBTToReportCard(BgabGascbt04 bgabGascbt04);
	public List<BgabGascbt04> getSelectBTToReportVendor(BgabGascbt04 bgabGascbt04);

	/**
	 * report page save
	 * @param List<BgabGascbt04>
	 * @param List<BgabGascbt04>
	 * @return int
	 */
	public int saveBTToReport(BgabGascbt02 bgabGascbt02, List<BgabGascbt04> bgabGascbt04IList, List<BgabGascbt04> bgabGascbt04UList);

	/**
	 * report page delete
	 * @param List<BgabGascbt04>
	 * @return int
	 */
	public int deleteBTToReport(List<BgabGascbt04> bgabGascbt04DList);

	/**
	 * report page save
	 * @param List<BgabGascbt04>
	 * @param List<BgabGascbt04>
	 * @return int
	 */
	public void saveBtToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);

	/**
	 * report page save
	 * @param List<BgabGascbt04>
	 * @param List<BgabGascbt04>
	 * @return int
	 */
	public void saveBtToOutCompFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public void saveBtToOutCompFileHotel(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);

	/**
	 * file page search
	 * @param BgabGascZ011Dto
	 * @return List<BgabGascZ011Dto>
	 */
	public List<BgabGascZ011Dto> getSelectBTToFile(BgabGascZ011Dto bgabGascZ011Dto);

	/**
	 * file page search
	 * @param BgabGascZ011Dto
	 * @return BgabGascZ011Dto
	 */
	public BgabGascZ011Dto getSelectBTToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);

	/**
	 * file page delete
	 * @param List<BgabGascZ011Dto>
	 * @return int
	 */
	public int deleteBTToFile(List<BgabGascZ011Dto> bgabGascZ011IList);

	/**
	 * approve
	 * @param BgabGascbt01
	 * @param req
	 * @return CommonMessage
	 * @throws SessionException
	 */
	public CommonMessage setApproval(BgabGascbt01 btReqDto, HttpServletRequest req) throws SessionException;

	/**
	 * approve
	 * @param BgabGascbt01
	 * @param req
	 * @return CommonMessage
	 */
	public CommonMessage setApprovalCancel(BgabGascbt01 btReqDto, HttpServletRequest req);

	/**
	 * approvalInfo
	 * @param BgabGascbt01
	 * @return String
	 */
	public String getSelectApprovalInfoToRequest(BgabGascbt01 bgabGascbt01);

	/**
	 * confirm/confirm cancel
	 * @param BgabGascbt01
	 * @return int
	 * @throws SessionException
	 */
	public int confirmBTToRequest(HttpServletRequest req, BgabGascbt01 bgabGascbt01) throws SessionException;

	public int checkBTToConfirmList(List<BgabGascbt01> bgabGascbt01);

	public List<CommonSap> getExpenseExcelList(List<BgabGascbt04> reportCardList, List<BgabGascbt04> reportCashList);

	public List<CommonSap> getExpenseExcelToSap(List<BgabGascbt04> travelerList);

	public int getExpenseVendorList(List<BgabGascbt04> reportVendorList);

	public CommonMessage getBudgetInfoCheck(BgabGascbt01 btReqDto) throws Exception;

	public int getExpenseCancelList(List<BgabGascbt04> reportCancelList);

	/**
	 * Country Mngr. count search
	 * @param BgabGascz005Dto
	 * @return int
	 */
	public int getSelectCountCountryManagement(BgabGascz005Dto bgabGascz005Dto);

	public List<BgabGascz005Dto> getSelectCountryManagement(BgabGascz005Dto bgabGascz005Dto);

	public int saveListToCountryManagement(List<BgabGascz005Dto> dtoI, List<BgabGascz005Dto> dtoU);

	public int deleteListToCountryManagement(List<BgabGascz005Dto> dtoD);

	public int getSelectCountBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto);

	public List<BgabGascbtDto> getSelectBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto);

	public int getSelectCountExpenseManagement(BgabGascbt06 bgabGascbt06);

	public List<BgabGascbt06> getSelectExpenseManagement(BgabGascbt06 bgabGascbt06);

	public int saveListToExpenseManagement(List<BgabGascbt06> dtoI, List<BgabGascbt06> dtoU);

	public int deleteListToExpenseManagement(List<BgabGascbt06> dtoD);

	/**
	 * report page submit
	 * @param BgabGascbt02
	 * @return int
	 */
	public int submitBTToReport(BgabGascbt02 bgabGascbt02);

	public CommonMessage confirmToExpenseManagement(List<BgabGascbt02> travelerList, HttpServletRequest req);

	public boolean setExpenseTemplatMake(BgabGascbt02 bgabGascbt02);

	public int getSelectCountBudgetView(BgabGascbt02 bgabGascbt02);

	public List<BgabGascbt02> getSelectBudgetView(BgabGascbt02 bgabGascbt02);

	public List<BgabGascbtVoucherExcelDto> getSelectListBtToVoucherList(List<BgabGascbtVoucherExcelDto> travelerList);

	public int updateListBtToVoucherList(List<BgabGascbtVoucherExcelDto> travelerList);

	public int selectCountBtCustomerToList(BgabGascbt01 bgabGascbt01);

	public List<BgabGascbt01> selectListBtCustomerToList(BgabGascbt01 bgabGascbt01);

	public List<BgabGascbt07> selectFlightConfirmListBtToRequest(BgabGascbt07 bgabGascbt07);
	public int deleteFlightConfirmBtToRequest(BgabGascbt07 dList);

	public List<BgabGascbt08> selectHotelConfirmListBtToRequest(BgabGascbt08 bgabGascbt08);
	public int deleteHotelConfirmBtToRequest(BgabGascbt08 dList);

	public List<BgabGascbt09> selectRentCarListBtToRequest(BgabGascbt09 bgabGascbt09);
	public int deleteRentCarBtToRequest(BgabGascbt09 dList);

	public CommonMessage updateCompletePOCreate(List<BgabGascbt04> param);
	public CommonMessage updateCompleteReInvoiceCreate(List<BgabGascbt04> param);
	public CommonMessage updateCompletePODelete(List<BgabGascbt04> param);

	public CommonMessage updateInfoBtToReject(BgabGascbt01 param, HttpServletRequest req) throws SessionException;

	public List<BgabGascZ011Dto> doSearchBTToSendMail(BgabGascZ011Dto gsReqVo);

	public int vendorCheckBTToConfirmList(List<BgabGascbt01> bgabGascbt01);
	public int saveVendorCheckBTToSaveDetail(BgabGascbt01 dto);

	public List<BgabGascbt01> selectBtToExcelList(BgabGascbt01 bgabGascbt04);

	public List<BgabGascbt02> getSelectBudgetViewNew(BgabGascbt02 bgabGascbt02);
}
