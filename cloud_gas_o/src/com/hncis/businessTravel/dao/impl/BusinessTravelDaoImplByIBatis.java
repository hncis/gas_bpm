package com.hncis.businessTravel.dao.impl;

import java.util.List;

import com.hncis.businessTravel.dao.BusinessTravelDao;
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
import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonCode;

public class BusinessTravelDaoImplByIBatis extends CommonDao implements BusinessTravelDao {
	
	private final String INSERT_BT_TO_REQUEST_BY_BASIC			= "insertBtToRequestByBasic";
	private final String INSERT_BT_TO_REQUEST_BY_TRAVELER		= "insertBtToRequestByTraveler";
	private final String INSERT_BT_TO_REQUEST_BY_SCHEDULE		= "insertBtToRequestBySchedule";
	private final String UPDATE_BT_TO_REQUEST_BY_BASIC			= "updateBtToRequestByBasic";
	private final String UPDATE_BT_TO_REQUEST_BY_TRAVELER		= "updateBtToRequestByTraveler";
	private final String UPDATE_BT_TO_REQUEST_BY_SCHEDULE		= "updateBtToRequestBySchedule";
	private final String DELETE_BT_TO_REQUEST_BY_BASIC			= "deleteBtToRequestByBasic";
	private final String DELETE_BT_TO_REQUEST_BY_TRAVELER		= "deleteBtToRequestByTraveler";
	private final String DELETE_BT_TO_REQUEST_BY_SCHEDULE		= "deleteBtToRequestBySchedule";
	private final String SELECT_BT_TO_REQUEST_BY_BASIC			= "selectBtToRequestByBasic";
	private final String SELECT_BT_TO_REQUEST_BY_TRAVELER		= "selectBtToRequestByTraveler";
	private final String SELECT_BT_TO_REQUEST_BY_VIRTUAL_TRAVELER= "selectBtToRequestByVirtualTraveler";
	private final String SELECT_BT_TO_REQUEST_BY_SCHEDULE		= "selectBtToRequestBySchedule";
	private final String SELECT_COUNT_BT_TO_LIST				= "selectCountBtToList";
	private final String SELECT_BT_TO_LIST						= "selectBtToList";
	private final String SELECT_BT_TO_COMBO_LIST_BY_REPORT		= "selectBtToComboListByReport";
	private final String SELECT_BT_TO_REPORT					= "selectBtToReport";
	private final String SELECT_BT_TO_REPORT_CARD				= "selectBtToReportCard";
	private final String SELECT_BT_TO_REPORT_VENDOR				= "selectBtToReportVendor";
	private final String SELECT_BT_TO_REPORT_BY_EXCEL			= "selectBtToReportByExcel";
	private final String INSERT_BT_TO_REPORT					= "insertBtToReport";
	private final String UPDATE_BT_TO_REPORT					= "updateBtToReport";
	private final String DELETE_BT_TO_REPORT					= "deleteBtToReport";
	private final String SELECT_MAX_SEQ_BT_TO_REPORT			= "selectMaxSeqBtToReport";
	private final String INSERT_BT_TO_FILE						= "insertBtToFile";
	private final String SELECT_BT_TO_FILE						= "selectBtToFile";
	private final String DELETE_BT_TO_FILE						= "deleteBtToFile";
	private final String APPROVE_BT_TO_REQUEST					= "approveBtToRequest";
	private final String APPROVE_CANCEL_BT_TO_REQUEST			= "approveCancelBtToRequest";
	private final String SELECT_APPROVALINFO_BT_TO_REQUEST		= "selectApprovalInfoBtToRequest";
	private final String CONFIRM_BT_TO_REQUEST					= "confirmBtToRequest";
	private final String SELECT_TRAVELER_TO_BUDGET				= "selectTravelerToBudget";
	private final String UPDATE_TRAVELER_TO_BUDGET				= "updateTravelerToBudget";
	private final String UPDATE_BT_TO_REPORT_BY_SAP_YN			= "updateBtToReportBySapYn";
	private final String SELECT_COUNT_BT_TO_REPORT_BY_SAP_YN	= "selectCountBtToReportBySapYn";
	private final String UPDATE_BT_TO_TRAVELER_BY_SAP_YN		= "updateBtToTravelerBySapYn";
	private final String SELECT_COUNT_COUNTY_MANAGEMENT			= "selectCountCountryManagement";
	private final String SELECT_COUNTY_MANAGEMENT				= "selectCountryManagement";
	private final String INSERT_LIST_TO_COUNTRY_MANAGEMENT		= "insertListToCountryManagement";
	private final String UPDATE_LIST_TO_COUNTRY_MANAGEMENT		= "updateListToCountryManagement";
	private final String DELETE_LIST_TO_COUNTRY_MANAGEMENT		= "deleteListToCountryManagement";
	private final String SELECT_COUNT_BT_TO_SAP_MNGR_BY_TRAVLER	= "selectCountBtToSapMngrByTravler";
	private final String SELECT_BT_TO_SAP_MNGR_BY_TRAVLER		= "selectBtToSapMngrByTravler";
	private final String SELECT_COUNT_EXPENSE_MANAGEMENT		= "selectCountExpenseManagement";
	private final String SELECT_EXPENSE_MANAGEMENT				= "selectExpenseManagement";
	private final String SELECT_COUNT_BUDGET_VIEW				= "selectCountBudgetView";
	private final String SELECT_BUDGET_VIEW						= "selectBudgetView";
	private final String INSERT_LIST_TO_EXPENSE_MANAGEMENT		= "insertListToExpenseManagement";
	private final String UPDATE_LIST_TO_EXPENSE_MANAGEMENT		= "updateListToExpenseManagement";
	private final String DELETE_LIST_TO_EXPENSE_MANAGEMENT		= "deleteListToExpenseManagement";
	private final String UPDATE_BT_TO_TRAVELER_BY_STATUS		= "updateBtToTravelerByStatus";	
	private final String SELECT_BT_TO_TRAVELER_EXCELINFO		= "selectBtToTravlerExcelInfo";
	private final String SELECT_BT_TO_TRAVELERS_EXCEL_LIST		= "selectBtToTravlersExcelList";
	private final String SELECT_BT_TO_EXPENSE_EXCEL_LIST		= "selectBtToExpenseExcelList";
	private final String SELECT_LIST_BT_TO_VOUCHER_LIST			= "selectListBtToVoucherList";
	private final String UPDATE_BT_TO_REPORT_BY_SAP_YN_FOR_VOUCHER		= "updateBtToTravelerBySapYnForVoucher";
	private final String UPDATE_BT_TO_TRAVELER_BY_SAP_YN_FOR_VOUCHER		= "updateBtToTravelerBySapYnForVoucher";
	
	private final String SELECT_COUNT_BT_CUSTOMER_TO_LIST		= "selectCountBtCustomerToList";
	private final String SELECT_LIST_BT_CUSTOMER_TO_LIST		= "selectListBtCustomerToList";
	
	
	private final String SELECT_FLIGHT_CONFIRM_LIST_BT_TOREQUEST	= "selectFlightConfirmListBtToRequest";
	private final String INSERT_FLIGHT_CONFIRM_BT_TO_REQUEST		= "insertFlightConfirmBtToRequest";
	private final String UPDATE_FLIGHT_CONFIRM_BT_TO_REQUEST		= "updateFlightConfirmBtToRequest";
	private final String DELETE_FLIGHT_CONFIRM_BT_TO_REQUEST		= "deleteFlightConfirmBtToRequest";
	private final String SELECT_HOTEL_CONFIRM_LIST_BT_TO_REQUEST	= "selectHotelConfirmListBtToRequest";
	private final String INSERT_HOTEL_CONFIRM_BT_TO_REQUEST			= "insertHotelConfirmBtToRequest";
	private final String UPDATE_HOTEL_CONFIRM_BT_TO_REQUEST			= "updateHotelConfirmBtToRequest";
	private final String DELETE_HOTEL_CONFIRM_BT_TO_REQUEST			= "deleteHotelConfirmBtToRequest";
	private final String SELECT_RENT_CAR_LIST_BT_TO_REQUEST			= "selectRentCarListBtToRequest";
	private final String INSERT_RENT_CAR_BT_TO_REQUEST				= "insertRentCarBtToRequest";
	private final String UPDATE_RENT_CAR_BT_TO_REQUEST				= "updateRentCarBtToRequest";
	private final String DELETE_RENT_CAR_BT_TO_REQUEST				= "deleteRentCarBtToRequest";
	
	private final String UPDATE_COMPLETE_FINAL_PO 					= "updateCompleteFinalPO";
	private final String UPDATE_TRAVELER_TEMP_PO 					= "updateTravelerTempPo";
	private final String SELECT_DUMMY_VENDOR_INFO 					= "selectDummyVendorInfo";
	private final String SELECT_TRAVEL_REJECTvSUBMIT_PO_SEARCH 		= "selectTravelRejectSubmitPoSearch";
	private final String UPDATE_INFO_BT_TO_REJECT 					= "updateInfoBtToReject";
	private final String SELECT_BT_TO_SEND_MAIL                     = "selectBTToSendMail";
	private final String VENDOR_CHECK_TO_REQUEST					= "vendorCheckToRequest";
	private final String VENDOR_SAVE_TO_REQUEST						= "vendorSaveToRequest";
	private final String SELECT_BT_TO_EXCEL_LIST					= "selectBtToExcelList";
	
	private static final String uncheck = "unchecked";
	
	public int insertBTToRequestByBasic(BgabGascbt01 bsicInfo){
		return super.insert(INSERT_BT_TO_REQUEST_BY_BASIC, bsicInfo);
	}
	
	public int insertBTToRequestByTraveler (List<BgabGascbt02> travelerList){
		return super.insert(INSERT_BT_TO_REQUEST_BY_TRAVELER, travelerList);
	}
	
	public int insertBTToRequestBySchedule (List<BgabGascbt03> scheduleList){
		return super.insert(INSERT_BT_TO_REQUEST_BY_SCHEDULE, scheduleList);
	}
	
	public int updateBTToRequestByBasic(BgabGascbt01 bsicInfo){
		return super.update(UPDATE_BT_TO_REQUEST_BY_BASIC, bsicInfo);
	}
	
	public int updateBTToRequestByTraveler (List<BgabGascbt02> travelerList){
		return super.update(UPDATE_BT_TO_REQUEST_BY_TRAVELER, travelerList);
	}
	
	public int updateBTToRequestBySchedule (List<BgabGascbt03> scheduleList){
		return super.update(UPDATE_BT_TO_REQUEST_BY_SCHEDULE, scheduleList);
	}
	
	public int deleteBTToRequestByBasic(BgabGascbt01 bsicInfo){
		return super.delete(DELETE_BT_TO_REQUEST_BY_BASIC, bsicInfo);
	}
	
	public int deleteBTToRequestByTraveler (BgabGascbt02 traveler){
		return super.delete(DELETE_BT_TO_REQUEST_BY_TRAVELER, traveler);
	}
	
	public int deleteBTToRequestBySchedule (BgabGascbt03 schedule){
		return super.delete(DELETE_BT_TO_REQUEST_BY_SCHEDULE, schedule);
	}
	
	public BgabGascbt01 getSelectBTToRequestByBasicInfo(BgabGascbt01 bsicInfo){
		return (BgabGascbt01)getSqlMapClientTemplate().queryForObject(SELECT_BT_TO_REQUEST_BY_BASIC, bsicInfo);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt02> getSelectBTToRequestByTraveler(BgabGascbt02 bgabGascbt02){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REQUEST_BY_TRAVELER, bgabGascbt02);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt02> getSelectBTToRequestByVirtualTraveler(BgabGascbt02 bgabGascbt02){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REQUEST_BY_VIRTUAL_TRAVELER, bgabGascbt02);
	}
	
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt03> getSelectBTToRequestBySchedule(BgabGascbt03 bgabGascbt03){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REQUEST_BY_SCHEDULE, bgabGascbt03);
	}
	
	public String getSelectCountBTToList(BgabGascbt01 bgabGascbt01){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BT_TO_LIST, bgabGascbt01);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt01> getSelectBTToList(BgabGascbt01 bgabGascbt01){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_LIST, bgabGascbt01);
	}
	
	@SuppressWarnings(uncheck)
	public List<CommonCode> getSelectBTToComboListByReport(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_COMBO_LIST_BY_REPORT, code);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt04> getSelectBTToReport(BgabGascbt04 bgabGascbt04){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REPORT, bgabGascbt04);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt04> getSelectBTToReportCard(BgabGascbt04 bgabGascbt04){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REPORT_CARD, bgabGascbt04);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt04> getSelectBTToReportVendor(BgabGascbt04 bgabGascbt04){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_REPORT_VENDOR, bgabGascbt04);
	}
	
	@SuppressWarnings(uncheck)
	public BgabGascbt04 getSelectBTToReportInfo(BgabGascbt04 bgabGascbt04){
		return (BgabGascbt04)getSqlMapClientTemplate().queryForObject(SELECT_BT_TO_REPORT_BY_EXCEL, bgabGascbt04);
	}
	
	public int updateBTToReportBySapYn (List<BgabGascbt04> bgabGascbt04List){
		return super.update(UPDATE_BT_TO_REPORT_BY_SAP_YN, bgabGascbt04List);
	}
	
	public int insertBTToReport (List<BgabGascbt04> bgabGascbt04List){
		return super.insert(INSERT_BT_TO_REPORT, bgabGascbt04List);
	}
	
	public int updateBTToReport (List<BgabGascbt04> bgabGascbt04List){
		return super.update(UPDATE_BT_TO_REPORT, bgabGascbt04List);
	}
	
	public int deleteBTToReport (List<BgabGascbt04> bgabGascbt04List){
		return super.delete(DELETE_BT_TO_REPORT, bgabGascbt04List);
	}
	
	public String getSelectMaxSeqBTToReport(BgabGascbt02 bgabGascbt02){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAX_SEQ_BT_TO_REPORT, bgabGascbt02);
	}
	
	public int insertBTToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_BT_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascZ011Dto> getSelectBTToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectBTToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_BT_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deleteBTToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_BT_TO_FILE, bgabGascZ011List);
	}
	
	public int approveBTToRequest (BgabGascbt01 bgabGascbt01){
		return update(APPROVE_BT_TO_REQUEST, bgabGascbt01);
	}
	public int approveCancelBtToRequest (BgabGascbt01 bgabGascbt01){
		return update(APPROVE_CANCEL_BT_TO_REQUEST, bgabGascbt01);
	}
	public String getSelectApprovalInfoToRequest(BgabGascbt01 bgabGascbt01){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVALINFO_BT_TO_REQUEST, bgabGascbt01);
	}
	
	public int confirmBTToRequest(BgabGascbt01 bgabGascbt01){
		return super.update(CONFIRM_BT_TO_REQUEST, bgabGascbt01);
	}
	
	public int checkBTToConfirmList(List<BgabGascbt01> bgabGascbt01){
		return super.update(CONFIRM_BT_TO_REQUEST, bgabGascbt01);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbtDto> getSelectTravelerToBudget(BgabGascbt01 bgabGascbt01){
		return getSqlMapClientTemplate().queryForList(SELECT_TRAVELER_TO_BUDGET, bgabGascbt01);
	}
	
	public int updateTravelerTotBudget(BgabGascbtDto bgabGascbtDto){
		return super.update(UPDATE_TRAVELER_TO_BUDGET, bgabGascbtDto);
	}
	
	public String getSelectCountBTToReportBySapYn(BgabGascbt04 bgabGascbt04){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BT_TO_REPORT_BY_SAP_YN, bgabGascbt04);
	}
	
	public int updateBTToTravelerBySapYn (BgabGascbt02 bgabGascbt02){
		return super.update(UPDATE_BT_TO_TRAVELER_BY_SAP_YN, bgabGascbt02);
	}
	
	public String getSelectCountCountryManagement(BgabGascz005Dto bgabGascz005Dto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_COUNTY_MANAGEMENT, bgabGascz005Dto);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascz005Dto> getSelectCountryManagement(BgabGascz005Dto bgabGascz005Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_COUNTY_MANAGEMENT, bgabGascz005Dto);
	}
	
	public int insertListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto){
		return super.insert(INSERT_LIST_TO_COUNTRY_MANAGEMENT, bgabGascz005Dto);
	}
	
	public int updateListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto){
		return super.update(UPDATE_LIST_TO_COUNTRY_MANAGEMENT, bgabGascz005Dto);
	}
	
	public int deleteListToCountryManagement (List<BgabGascz005Dto> bgabGascz005Dto){
		return super.delete(DELETE_LIST_TO_COUNTRY_MANAGEMENT, bgabGascz005Dto);
	}
	
	@SuppressWarnings(uncheck)
	public String getSelectCountBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BT_TO_SAP_MNGR_BY_TRAVLER, bgabGascbtDto);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbtDto> getSelectBTToSapMngrByTaveler(BgabGascbtDto bgabGascbtDto){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_SAP_MNGR_BY_TRAVLER, bgabGascbtDto);
	}
	
	@SuppressWarnings(uncheck)
	public String getSelectCountExpenseManagement(BgabGascbt06 bgabGascbt06){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt06> getSelectExpenseManagement(BgabGascbt06 bgabGascbt06){
		return getSqlMapClientTemplate().queryForList(SELECT_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	@SuppressWarnings(uncheck)
	public String getSelectCountBudgetView(BgabGascbt02 bgabGascbt02){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BUDGET_VIEW, bgabGascbt02);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt02> getSelectBudgetView(BgabGascbt02 bgabGascbt02){
		return getSqlMapClientTemplate().queryForList(SELECT_BUDGET_VIEW, bgabGascbt02);
	}
	
	public int insertListToExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.insert(INSERT_LIST_TO_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int updateListToExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.update(UPDATE_LIST_TO_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int deleteListToExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.update(DELETE_LIST_TO_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int updateBTToTravelerByStatus (BgabGascbt02 bgabGascbt02){
		return super.update(UPDATE_BT_TO_TRAVELER_BY_STATUS, bgabGascbt02);
	}
	
	public int confirmToExpenseManagement(List<BgabGascbt02> travelerList){
		return super.update(UPDATE_BT_TO_TRAVELER_BY_STATUS, travelerList);
	}
	
	public BgabGascbtDto getSelectBTToTravelerExcelInfo(BgabGascbt02 bgabGascbt02){
		return (BgabGascbtDto)getSqlMapClientTemplate().queryForObject(SELECT_BT_TO_TRAVELER_EXCELINFO, bgabGascbt02);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbtDto> getSelectBTToTravelersExcelList(BgabGascbt02 bgabGascbt02){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_TRAVELERS_EXCEL_LIST, bgabGascbt02);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt04> getSelectBTToExpenseExcelList(BgabGascbt02 bgabGascbt02){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_EXPENSE_EXCEL_LIST, bgabGascbt02);
	}

	@SuppressWarnings(uncheck)
	public List<BgabGascbtVoucherExcelDto> getSelectListBtToVoucherList(BgabGascbtVoucherExcelDto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BT_TO_VOUCHER_LIST, dto);
	}

	@Override
	public int updateBTToReportBySapYnForVoucher(List<BgabGascbtVoucherExcelDto> travelerList) {
		return super.update(UPDATE_BT_TO_REPORT_BY_SAP_YN_FOR_VOUCHER, travelerList);
	}

	@Override
	public int updateBTToTravelerBySapYnForVoucher(List<BgabGascbtVoucherExcelDto> travelerList) {
		return super.update(UPDATE_BT_TO_TRAVELER_BY_SAP_YN_FOR_VOUCHER, travelerList);
	}
	
	public String selectCountBtCustomerToList(BgabGascbt01 bgabGascbt01){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BT_CUSTOMER_TO_LIST, bgabGascbt01);
	}
	
	@SuppressWarnings(uncheck)
	public List<BgabGascbt01> selectListBtCustomerToList(BgabGascbt01 bgabGascbt01){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BT_CUSTOMER_TO_LIST, bgabGascbt01);
	}
	
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascbt07> selectFlightConfirmListBtToRequest(BgabGascbt07 bgabGascbt07){
		return getSqlMapClientTemplate().queryForList(SELECT_FLIGHT_CONFIRM_LIST_BT_TOREQUEST, bgabGascbt07);
	}
	@Override
	public int insertFlightConfirmBtToRequest(List<BgabGascbt07> iList) {
		return super.insert(INSERT_FLIGHT_CONFIRM_BT_TO_REQUEST, iList);
	}
	@Override
	public int updateFlightConfirmBtToRequest(List<BgabGascbt07> iList) {
		return super.update(UPDATE_FLIGHT_CONFIRM_BT_TO_REQUEST, iList);
	}
	@Override
	public int deleteFlightConfirmBtToRequest(BgabGascbt07 dList) {
		return super.delete(DELETE_FLIGHT_CONFIRM_BT_TO_REQUEST, dList);
	}
	
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascbt08> selectHotelConfirmListBtToRequest(BgabGascbt08 bgabGascbt08){
		return getSqlMapClientTemplate().queryForList(SELECT_HOTEL_CONFIRM_LIST_BT_TO_REQUEST, bgabGascbt08);
	}
	@Override
	public int insertHotelConfirmBtToRequest(List<BgabGascbt08> iList) {
		return super.insert(INSERT_HOTEL_CONFIRM_BT_TO_REQUEST, iList);
	}
	@Override
	public int updateHotelConfirmBtToRequest(List<BgabGascbt08> iList) {
		return super.update(UPDATE_HOTEL_CONFIRM_BT_TO_REQUEST, iList);
	}
	@Override
	public int deleteHotelConfirmBtToRequest(BgabGascbt08 dList) {
		return super.delete(DELETE_HOTEL_CONFIRM_BT_TO_REQUEST, dList);
	}
	
	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascbt09> selectRentCarListBtToRequest(BgabGascbt09 bgabGascbt09){
		return getSqlMapClientTemplate().queryForList(SELECT_RENT_CAR_LIST_BT_TO_REQUEST, bgabGascbt09);
	}
	@Override
	public int insertRentCarBtToRequest(List<BgabGascbt09> iList) {
		return super.insert(INSERT_RENT_CAR_BT_TO_REQUEST, iList);
	}
	@Override
	public int updateRentCarBtToRequest(List<BgabGascbt09> iList) {
		return super.update(UPDATE_RENT_CAR_BT_TO_REQUEST, iList);
	}
	@Override
	public int deleteRentCarBtToRequest(BgabGascbt09 dList) {
		return super.delete(DELETE_RENT_CAR_BT_TO_REQUEST, dList);
	}
	
	public int updateCompleteFinalPO(BgabGascbt04 param){
		return update(UPDATE_COMPLETE_FINAL_PO, param);
	}
	
	public int updateTravelerTempPo(BgabGascbt02 param){
		return update(UPDATE_TRAVELER_TEMP_PO, param);
	}
	public int updateBTToReportBySapYnRow (BgabGascbt04 bgabGascbt04){
		return super.update(UPDATE_BT_TO_REPORT_BY_SAP_YN, bgabGascbt04);
	}
	public BgabGascz005Dto getSelectDummyVendorInfo (BgabGascz005Dto dto){
		return (BgabGascz005Dto)getSqlMapClientTemplate().queryForObject(SELECT_DUMMY_VENDOR_INFO, dto);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascbt02>getSelectTravelRejectSubmitPoSearch(BgabGascbt02 dto){
		return getSqlMapClientTemplate().queryForList(SELECT_TRAVEL_REJECTvSUBMIT_PO_SEARCH, dto);
	}
	public int updateInfoBtToReject(BgabGascbt01 param){
		return update(UPDATE_INFO_BT_TO_REJECT, param);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascZ011Dto> doSearchBTToSendMail(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_SEND_MAIL, bgabGascZ011Dto);
	}
	public int vendorCheckBTToConfirmList(List<BgabGascbt01> bgabGascbt01){
		return update(VENDOR_CHECK_TO_REQUEST, bgabGascbt01);
	}
	public int saveVendorCheckBTToSaveDetail(BgabGascbt01 dto){
		return update(VENDOR_SAVE_TO_REQUEST, dto);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascbt01> selectBtToExcelList(BgabGascbt01 bgabGascbt04){
		return getSqlMapClientTemplate().queryForList(SELECT_BT_TO_EXCEL_LIST, bgabGascbt04);
	}
	public String getSelectConfirmToExpenseCount(BgabGascbt02 dto){
		return (String)getSqlMapClientTemplate().queryForObject("selectConfirmToExpenseCount", dto);
	}
}