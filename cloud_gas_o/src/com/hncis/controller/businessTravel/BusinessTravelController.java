package com.hncis.controller.businessTravel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.businessTravel.manager.BusinessTravelManager;
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
import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonSap;
import com.hncis.controller.AbstractController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class BusinessTravelController extends AbstractController{

	@Autowired
	@Qualifier("businessTravelManagerImpl")
	private BusinessTravelManager businessTravelManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * business Travel combo list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doComboListToRequest.do")
	public ModelAndView doComboListToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		String codeKnd[];
		String originalCodeKnd [];
		CommonCode code = new CommonCode();

		String comboVal				 = "";
		String comboName			 = "";
		String comboTable			 = "";
		String comboWhereCondition	 = "";
		String comboGroupBy			 = "";
		String comboorderBy			 = "";

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = null;

		String []codeKnds = codknd.split(";");

		for (int i =0; i < codeKnds.length; i++){
			codeKnd = codeKnds[i].split(":");
			originalCodeKnd = codeKnds[i].split(":");

			comboTable           = "BGAB_GASCZ005" + SessionInfo.getSess_corp_cd(req);
			comboGroupBy         = "";
			comboVal             = "XCOD_CODE";
			comboName            = "XCOD_HNAME";
			comboWhereCondition  = "XCOD_KND = '"+codeKnd[1]+"'"
									+" and locale = '"+req.getSession().getAttribute("reqLocale").toString()+"'";

			code.setValue(comboVal);
			code.setName(comboName);
			code.setTableName(comboTable);
			code.setWhereCondition(comboWhereCondition);
			code.setGroupBy(comboGroupBy);
			code.setOrderBy(comboorderBy);
			code.setComboType(codeKnd[2].toUpperCase());

			jsonArr = commonManager.getDataCombo(code);
			jso.put(originalCodeKnd[0], jsonArr);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * business Travel request insert
	 * @param bsicInfo
	 * @param travelerInfoI
	 * @param scheduleInfoI
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doInsertBTToRequest.do")
	public ModelAndView doInsertBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo,
		@RequestParam(value="travelerInfoI", required=true) String travelerInfo,
		@RequestParam(value="scheduleInfoI", required=true) String scheduleInfo) throws HncisException{

		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(bsicInfo, BgabGascbt01.class);
		List<BgabGascbt02> bt002Dto = (List<BgabGascbt02>) getJsonToList(travelerInfo, BgabGascbt02.class);
		List<BgabGascbt03> bt003Dto = (List<BgabGascbt03>) getJsonToList(scheduleInfo, BgabGascbt03.class);

		businessTravelManager.insertBTToRequest(btReqDto, bt002Dto, bt003Dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request update
	 * @param bsicInfo
	 * @param travelerInfoI
	 * @param travelerInfoU
	 * @param scheduleInfoI
	 * @param scheduleInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSaveBTToRequest.do")
	public ModelAndView doSaveBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo,
		@RequestParam(value="travelerInfoI", required=true) String travelerInfoI,
		@RequestParam(value="travelerInfoU", required=true) String travelerInfoU,
//		@RequestParam(value="scheduleInfoI", required=true) String scheduleInfoI,
//		@RequestParam(value="scheduleInfoU", required=true) String scheduleInfoU,
		@RequestParam(value="flightInfoI"  , required=true) String flightInfoI,
		@RequestParam(value="flightInfoU"  , required=true) String flightInfoU,
		@RequestParam(value="hotelInfoI"   , required=true) String hotelInfoI,
		@RequestParam(value="hotelInfoU"   , required=true) String hotelInfoU,
		@RequestParam(value="rentInfoI"    , required=true) String rentInfoI,
		@RequestParam(value="rentInfoU"    , required=true) String rentInfoU,
		@RequestParam(value="travelerVtInfoI", required=true) String travelerVtInfoI,
		@RequestParam(value="travelerVtInfoU", required=true) String travelerVtInfoU) throws HncisException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(bsicInfo, BgabGascbt01.class);
		List<BgabGascbt02> bt002DtoI = (List<BgabGascbt02>) getJsonToList(travelerInfoI, BgabGascbt02.class);
		List<BgabGascbt02> bt002DtoU = (List<BgabGascbt02>) getJsonToList(travelerInfoU, BgabGascbt02.class);
//		List<BgabGascbt03> bt003DtoI = (List<BgabGascbt03>) getJsonToList(scheduleInfoI, BgabGascbt03.class);
//		List<BgabGascbt03> bt003DtoU = (List<BgabGascbt03>) getJsonToList(scheduleInfoU, BgabGascbt03.class);
		List<BgabGascbt07> bt007DtoI = (List<BgabGascbt07>) getJsonToList(flightInfoI  , BgabGascbt07.class);
		List<BgabGascbt07> bt007DtoU = (List<BgabGascbt07>) getJsonToList(flightInfoU  , BgabGascbt07.class);
		List<BgabGascbt08> bt008DtoI = (List<BgabGascbt08>) getJsonToList(hotelInfoI   , BgabGascbt08.class);
		List<BgabGascbt08> bt008DtoU = (List<BgabGascbt08>) getJsonToList(hotelInfoU   , BgabGascbt08.class);
		List<BgabGascbt09> bt009DtoI = (List<BgabGascbt09>) getJsonToList(rentInfoI    , BgabGascbt09.class);
		List<BgabGascbt09> bt009DtoU = (List<BgabGascbt09>) getJsonToList(rentInfoU    , BgabGascbt09.class);
		List<BgabGascbt02> bt010DtoI = (List<BgabGascbt02>) getJsonToList(travelerVtInfoI, BgabGascbt02.class);
		List<BgabGascbt02> bt011DtoU = (List<BgabGascbt02>) getJsonToList(travelerVtInfoU, BgabGascbt02.class);

		businessTravelManager.saveBTToRequest(
				btReqDto
				, bt002DtoI, bt002DtoU
//				, bt003DtoI, bt003DtoU
				, bt007DtoI, bt007DtoU
				, bt008DtoI, bt008DtoU
				, bt009DtoI, bt009DtoU
				, bt010DtoI, bt011DtoU
		);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doDeleteBTToRequest.do")
	public ModelAndView doDeleteBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(bsicInfo, BgabGascbt01.class);
		businessTravelManager.deleteBTToRequest(btReqDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		message.setCode(StringUtil.getDocNo());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doDeleteTravelerToRequest.do")
	public ModelAndView doDeleteTravelerToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="travelerInfo", required=true) String travelerInfo) throws HncisException{

		BgabGascbt02 bgabGascbt02 = (BgabGascbt02) getJsonToBean(travelerInfo, BgabGascbt02.class);
		businessTravelManager.deleteTravelerToRequest(bgabGascbt02);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doDeleteScheduleToRequest.do")
	public ModelAndView doDeleteScheduleToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="scheduleInfo", required=true) String scheduleInfo) throws HncisException{

		BgabGascbt03 bgabGascbt03 = (BgabGascbt03) getJsonToBean(scheduleInfo, BgabGascbt03.class);
		businessTravelManager.deleteScheduleToRequest(bgabGascbt03);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Travel basic info search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchInfoBTToRequest.do")
	public ModelAndView doSearchInfoBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		BgabGascbt01 rsReqDto = new BgabGascbt01();
		rsReqDto = businessTravelManager.getSelectBTToRequestByBasicInfo(btReqDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("BT");
			commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Travel traveler info search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestByTraveler.do")
	public ModelAndView doSearchListBTToRequestByTraveler(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt02 btReqDto = (BgabGascbt02) getJsonToBean(paramJson, BgabGascbt02.class);

		CommonList list = new CommonList();

		list.setRows(businessTravelManager.getSelectBTToRequestByTraveler(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestByVirtualTraveler.do")
	public ModelAndView doSearchListBTToRequestByVirtualTraveler(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt02 btReqDto = (BgabGascbt02) getJsonToBean(paramJson, BgabGascbt02.class);

		CommonList list = new CommonList();

		list.setRows(businessTravelManager.getSelectBTToRequestByVirtualTraveler(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel Flight Confirmation search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestByFilghtConfirmation.do")
	public ModelAndView doSearchListBTToRequestByFilghtConfirmation(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt07 btReqDto = (BgabGascbt07) getJsonToBean(paramJson, BgabGascbt07.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.selectFlightConfirmListBtToRequest(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel Hotel Confirmation search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestByHotelConfirmation.do")
	public ModelAndView doSearchListBTToRequestByHotelConfirmation(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt08 btReqDto = (BgabGascbt08) getJsonToBean(paramJson, BgabGascbt08.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.selectHotelConfirmListBtToRequest(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel Rent Car search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestByRentCar.do")
	public ModelAndView doSearchListBTToRequestByRentCar(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt09 btReqDto = (BgabGascbt09) getJsonToBean(paramJson, BgabGascbt09.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.selectRentCarListBtToRequest(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel request Flight Confirmation delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteFlightConfirmationToRequest.do")
	public ModelAndView doDeleteFlightConfirmationToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="dParams", required=true) String dParams) throws HncisException{

		BgabGascbt07 dList = (BgabGascbt07) getJsonToBean(dParams, BgabGascbt07.class);
		businessTravelManager.deleteFlightConfirmBtToRequest(dList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request Hotel Confirmation delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteHotelConfirmationToRequest.do")
	public ModelAndView doDeleteHotelConfirmationToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="dParams", required=true) String dParams) throws HncisException{

		BgabGascbt08 dList = (BgabGascbt08) getJsonToBean(dParams, BgabGascbt08.class);
		businessTravelManager.deleteHotelConfirmBtToRequest(dList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request delete
	 * @param bsicInfo
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doDeleteRentCarToRequest.do")
	public ModelAndView doDeleteRentCarToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="rentInfo", required=true) String rentInfo) throws HncisException{

		BgabGascbt09 bgabGascbt09 = (BgabGascbt09) getJsonToBean(rentInfo, BgabGascbt09.class);
		businessTravelManager.deleteRentCarBtToRequest(bgabGascbt09);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Travel schedule info search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToRequestBySchedule.do")
	public ModelAndView doSearchListBTToRequestBySchedule(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt03 btReqDto = (BgabGascbt03) getJsonToBean(paramJson, BgabGascbt03.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBTToRequestBySchedule(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/*************************************************************/
	/** List of business traveler                                */
	/*************************************************************/
	/**
	 * business Travel List search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToList.do")
	public ModelAndView doSearchBTToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt01 btDtlDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.getSelectCountBTToList(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.getSelectBTToList(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Card accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/businessTravel/doExcelToList.excel")
	public ModelAndView doExcelToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascbt01 btDtlDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.selectBtToExcelList(btDtlDto));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("jobName",   "BT");
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToMultiComboByReport.do")
	public ModelAndView doSearchBTToMultiComboByReport(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<CommonCode> codeList = null;

		CommonCode code = new CommonCode();
		code.setCodknd(codknd);

		codeList = businessTravelManager.getSelectBTToComboListByReport(code);
		jsonArr = new JSONBaseArray();
		for(CommonCode targetBean : codeList){

			json = new JSONBaseVO();
			json.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

			jsonArr.add(json);
		}
		jso.put("REPORT", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Travel report info search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToReport.do")
	public ModelAndView doSearchBTToReport(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt04 btReqDto = (BgabGascbt04) getJsonToBean(paramJson, BgabGascbt04.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBTToReport(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchBTToReportCard.do")
	public ModelAndView doSearchBTToReportCard(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt04 btReqDto = (BgabGascbt04) getJsonToBean(paramJson, BgabGascbt04.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBTToReportCard(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchBTToReportVendor.do")
	public ModelAndView doSearchBTToReportVendor(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt04 btReqDto = (BgabGascbt04) getJsonToBean(paramJson, BgabGascbt04.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBTToReportVendor(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSaveBTToReport.do")
	public ModelAndView doSaveBTToReport(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="schedulerInfo", required=true) String schedulerInfo,
		@RequestParam(value="reportInfoI", required=true) String reportInfoI,
		@RequestParam(value="reportInfoU", required=true) String reportInfoU) throws HncisException{

		BgabGascbt02 btSchedulerDto = (BgabGascbt02) getJsonToBean(schedulerInfo, BgabGascbt02.class);
		List<BgabGascbt04> bt004DtoI = (List<BgabGascbt04>) getJsonToList(reportInfoI, BgabGascbt04.class);
		List<BgabGascbt04> bt004DtoU = (List<BgabGascbt04>) getJsonToList(reportInfoU, BgabGascbt04.class);

		businessTravelManager.saveBTToReport(btSchedulerDto, bt004DtoI, bt004DtoU);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteBTToReport.do")
	public ModelAndView doDeleteBTToReport(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="reportInfoD", required=true) String reportInfoD) throws HncisException{

		List<BgabGascbt04> bt004DtoD = (List<BgabGascbt04>) getJsonToList(reportInfoD, BgabGascbt04.class);
		System.out.println("size="+bt004DtoD.size());
		businessTravelManager.deleteBTToReport(bt004DtoD);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSaveBTToFile.do")
	public void doSaveBTToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			businessTravelManager.saveBtToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSaveBTToOutCompFile.do")
	public void doSaveBTToOutCompFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			businessTravelManager.saveBtToOutCompFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSaveBTToOutCompFileHotel.do")
	public void doSaveBTToOutCompFileHotel(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			businessTravelManager.saveBtToOutCompFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToFile.do")
	public ModelAndView doSearchBTToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBTToFile(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = businessTravelManager.getSelectBTToFileInfo(btReqDto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "businessTravel");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteBTToFile.do")
	public ModelAndView doDeleteBTToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> bt006Dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		businessTravelManager.deleteBTToFile(bt006Dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request approve
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException, Exception
	 */
	@RequestMapping(value="/hncis/businessTravel/doApproveBTToRequest.do")
	public ModelAndView doApproveBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String paramJson) throws HncisException, Exception{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);

		CommonMessage appMessage = businessTravelManager.setApproval(btReqDto, req);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(appMessage).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);
		
		return modelAndView;
	}

	/**
	 * business Travel request approve cancel
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doApproveCancelBTToRequest.do")
	public ModelAndView doApproveCancelBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String paramJson) throws HncisException, SessionException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);
		btReqDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		CommonMessage message = businessTravelManager.setApprovalCancel(btReqDto, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request confirm/confirm cancel
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doConfirmBTToRequest.do")
	public ModelAndView doConfirmBTToRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		businessTravelManager.confirmBTToRequest(req, btReqDto);

		CommonMessage message = new CommonMessage();
		if(StringUtil.isNullToString(btReqDto.getMode()).equals("confirmCancel")){
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("2STCONFIRM.0000"));
		}

		message.setCode("3");

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request confirm/confirm cancel
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doConfirm3BTToRequest.do")
	public ModelAndView doConfirm3BTToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);
		CommonMessage message = new CommonMessage();

		businessTravelManager.confirmBTToRequest(req, btReqDto);
		message.setMessage(HncisMessageSource.getMessage("1STCONFIRM.0000"));
		message.setCode("C");

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request confirm/confirm cancel
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doAfterCalBTToRequest.do")
	public ModelAndView doAfterCalBTToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		BgabGascbt01 btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);
		CommonMessage message = new CommonMessage();

		businessTravelManager.confirmBTToRequest(req, btReqDto);
		message.setMessage(HncisMessageSource.getMessage("AFTERCAL.0000"));
		message.setCode("D");

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * business Travel request confirm/confirm cancel
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doCheckBTToConfirmList.do")
	public ModelAndView doCheckBTToConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson,
			@RequestParam(value="uParams" , required=true) String uParams) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt01       btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);
		List<BgabGascbt01> uList    = (List<BgabGascbt01>) getJsonToList(uParams, BgabGascbt01.class);

		businessTravelManager.checkBTToConfirmList(uList);

		CommonMessage message = new CommonMessage();
		if(StringUtil.isNullToString(btReqDto.getMode()).equals("checkCancel")){
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		}

		message.setCode("3");

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel request reject
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doRejectBTToRequest.do")
	public ModelAndView doRejectBTToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		CommonMessage appMessage;

		BgabGascbt01 keyParamDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		appMessage = businessTravelManager.updateInfoBtToReject(keyParamDto, req);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(appMessage.getMessage());
			message.setErrorCode(appMessage.getErrorCode());
		}

		message.setCode(keyParamDto.getPgs_st_cd());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 *  business Travel Expense Excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/businessTravel/doExpenseExcelToList.excel")
	public ModelAndView doExpenseExcelToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="headerLength", required=false) String headerLength,
		@RequestParam(value="reportCard", required=false) String reportCard,
		@RequestParam(value="reportCash", required=false) String reportCash) throws HncisException{

		List<BgabGascbt04> reportCardList = (List<BgabGascbt04>) getJsonToList(reportCard, BgabGascbt04.class);
		List<BgabGascbt04> reportCashList = (List<BgabGascbt04>) getJsonToList(reportCash, BgabGascbt04.class);

		List<CommonSap> excelList = businessTravelManager.getExpenseExcelList(reportCardList, reportCashList);
		JSONArray gridData = JSONArray.fromObject(excelList);

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   	fileName);
		mpExcelData.put("headerLength", headerLength);
		mpExcelData.put("gridData",   	gridData);

		return new ModelAndView("GridSapExcelView", "excelData", mpExcelData);
	}

	/**
	 *  business Travel Expense Excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/businessTravel/doExpenseExcelToSap.excel")
	public ModelAndView doExpenseExcelToSap(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="headerLength", required=false) String headerLength,
		@RequestParam(value="travelerInfo", required=false) String travelerInfo) throws HncisException{

		List<BgabGascbt04> travelerList = (List<BgabGascbt04>) getJsonToList(travelerInfo, BgabGascbt04.class);
		List<CommonSap> excelList = businessTravelManager.getExpenseExcelToSap(travelerList);
		JSONArray gridData = JSONArray.fromObject(excelList);

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   	fileName);
		mpExcelData.put("headerLength", headerLength);
		mpExcelData.put("gridData",   	gridData);

		return new ModelAndView("GridSapExcelView", "excelData", mpExcelData);
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doExpenseVendorToList.do")
	public ModelAndView doExpenseVendorToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="reportVendor", required=true) String reportVendor) throws HncisException{

		List<BgabGascbt04> vendorList = (List<BgabGascbt04>) getJsonToList(reportVendor, BgabGascbt04.class);
		int uCnt = businessTravelManager.getExpenseVendorList(vendorList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("VENDOR.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doExpenseDataCancelToList.do")
	public ModelAndView doExpenseDataCancelToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="reportCancel", required=true) String reportCancel) throws HncisException{

		List<BgabGascbt04> cancelList = (List<BgabGascbt04>) getJsonToList(reportCancel, BgabGascbt04.class);
		int uCnt = businessTravelManager.getExpenseCancelList(cancelList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CANCEL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchCountryManagement.do")
	public ModelAndView doSearchCountryManagement(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz005Dto btDtlDto = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.getSelectCountCountryManagement(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.getSelectCountryManagement(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSaveCountryManagement.do")
	public ModelAndView doSaveCountryManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascz005Dto> dtoIList = (List<BgabGascz005Dto>)getJsonToList(paramsI, BgabGascz005Dto.class);
		List<BgabGascz005Dto> dtoUList = (List<BgabGascz005Dto>)getJsonToList(paramsU, BgabGascz005Dto.class);

		businessTravelManager.saveListToCountryManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteCountryManagement.do")
	public ModelAndView doDeleteCountryManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz005Dto> dtoDList = (List<BgabGascz005Dto>)getJsonToList(paramJson, BgabGascz005Dto.class);
		businessTravelManager.deleteListToCountryManagement(dtoDList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchListBTToSapMngrByTaveler.do")
	public ModelAndView doSearchListBTToSapMngrByTaveler(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		ModelAndView modelAndView = null;
		BgabGascbtDto btDtlDto = (BgabGascbtDto) getJsonToBean(paramJson, BgabGascbtDto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.getSelectCountBTToSapMngrByTaveler(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.getSelectBTToSapMngrByTaveler(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchExpenseManagement.do")
	public ModelAndView doSearchExpenseManagement(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt06 btDtlDto = (BgabGascbt06) getJsonToBean(paramJson, BgabGascbt06.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.getSelectCountExpenseManagement(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.getSelectExpenseManagement(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSaveExpenseManagement.do")
	public ModelAndView doSaveExpenseManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascbt06> dtoIList = (List<BgabGascbt06>)getJsonToList(paramsI, BgabGascbt06.class);
		List<BgabGascbt06> dtoUList = (List<BgabGascbt06>)getJsonToList(paramsU, BgabGascbt06.class);

		businessTravelManager.saveListToExpenseManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doDeleteExpenseManagement.do")
	public ModelAndView doDeleteExpenseManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascbt06> dtoDList = (List<BgabGascbt06>)getJsonToList(paramJson, BgabGascbt06.class);
		businessTravelManager.deleteListToExpenseManagement(dtoDList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doSubmitBTToReport.do")
	public ModelAndView doSubmitBTToReport(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="expenseInfo", required=true) String expenseInfo) throws HncisException{

		BgabGascbt02 bgabGascbt02 = (BgabGascbt02) getJsonToBean(expenseInfo, BgabGascbt02.class);
		businessTravelManager.submitBTToReport(bgabGascbt02);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SUBMIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doConfirmBTToReportBySap.do")
	public ModelAndView doConfirmBTToReportBySap(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="travelerInfo", required=true) String travelerInfo) throws HncisException{
		CommonMessage rstMessage;
		List<BgabGascbt02> travelerList = (List<BgabGascbt02>) getJsonToList(travelerInfo, BgabGascbt02.class);
		rstMessage = businessTravelManager.confirmToExpenseManagement(travelerList, req);

		CommonMessage message = new CommonMessage();
		if(StringUtil.isNullToString(travelerList.get(0).getMode()).equals("confirmCancel")){
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		}else{
			if("".equals(StringUtil.isNullToString(rstMessage.getMessage()))){
				message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			}else{
				message.setMessage(rstMessage.getMessage());
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doExpensePrint.do")
	public ModelAndView doExpensePrint(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="expenseInfo", required=true) String expenseInfo) throws HncisException{

		BgabGascbt02 bgabGascbt02 = (BgabGascbt02) getJsonToBean(expenseInfo, BgabGascbt02.class);
		Boolean returnVal = businessTravelManager.setExpenseTemplatMake(bgabGascbt02);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascbt02.getDoc_no()+bgabGascbt02.getEeno()+".xls");
		mpfileData.put("fileName",   "expense.xls");
		mpfileData.put("folderName",   "temp");
		mpfileData.put("fileDelete",   "Y");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchBudgetView.do")
	public ModelAndView doSearchBudgetView(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt02 btDtlDto = (BgabGascbt02) getJsonToBean(paramJson, BgabGascbt02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.getSelectCountBudgetView(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.getSelectBudgetView(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Voucher list excel 출력
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis//businessTravel/doExcelToVoucherList.excel")
	public ModelAndView doExcelToListByVehicle(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascbtVoucherExcelDto> travelerList = (List<BgabGascbtVoucherExcelDto>) getJsonToList(paramJson, BgabGascbtVoucherExcelDto.class);

		CommonList list = new CommonList();

//		if(travelerList.size() > 0){
//			businessTravelManager.updateListBtToVoucherList(travelerList);
//		}

		list.setRows(businessTravelManager.getSelectListBtToVoucherList(travelerList));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);

	}

	/*************************************************************/
	/** List of business traveler Customer                       */
	/*************************************************************/
	/**
	 * business Travel List search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToCustomerList.do")
	public ModelAndView doSearchBTToCustomerList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascbt01 btDtlDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessTravelManager.selectCountBtCustomerToList(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessTravelManager.selectListBtCustomerToList(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Card accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/businessTravel/doExcelCustomerToList.excel")
	public ModelAndView doExcelCustomerToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascbt01 btDtlDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = businessTravelManager.selectCountBtCustomerToList(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(count);
		list.setRows(businessTravelManager.selectListBtCustomerToList(btDtlDto));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("jobName",   "BT");
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doCompletePOCreate.do")
	public ModelAndView doCompletePOCreate(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		CommonMessage appMessage;
		List<BgabGascbt04> paramList = (List<BgabGascbt04>) getJsonToList(paramJson, BgabGascbt04.class);

		appMessage = businessTravelManager.updateCompletePOCreate(paramList);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("COMPLETE.0000"));
		}else{
			message.setMessage(appMessage.getMessage());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doCompletePODelete.do")
	public ModelAndView doCompletePODelete(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		CommonMessage appMessage;
		List<BgabGascbt04> paramList = (List<BgabGascbt04>) getJsonToList(paramJson, BgabGascbt04.class);

		appMessage = businessTravelManager.updateCompletePODelete(paramList);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("COMPLETE.0001"));
		}else{
			message.setMessage(appMessage.getMessage());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessTravel/doCompleteReInvoiceCreate.do")
	public ModelAndView doCompleteReInvoiceCreate(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		CommonMessage appMessage;
		List<BgabGascbt04> paramList = (List<BgabGascbt04>) getJsonToList(paramJson, BgabGascbt04.class);

		appMessage = businessTravelManager.updateCompleteReInvoiceCreate(paramList);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("COMPLETE.0000"));
		}else{
			message.setMessage(appMessage.getMessage());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * send mail
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToSendMail.do")
	public ModelAndView doSearchBTToSendMail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascZ011Dto gsReqVo = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		List<BgabGascZ011Dto> rsList = businessTravelManager.doSearchBTToSendMail(gsReqVo);
		BgabGascz002Dto userDto = new BgabGascz002Dto();
		userDto.setXusr_empno(gsReqVo.getEeno());
		userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
		BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);
		String fromEenm   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = userDetailInfo.getXusr_mail_adr();
		String mode       = "confirm";
		String title      = "Business Travel";
		Submit.sendEmailBusinessTripForConfirm(fromEenm, fromStepNm, toEeno, mode, title, rsList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("MAIL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * send mail
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doSearchBTToSendMailCom.do")
	public ModelAndView doSearchBTToSendMailCom(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascZ011Dto gsReqVo = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		List<BgabGascZ011Dto> rsList = businessTravelManager.doSearchBTToSendMail(gsReqVo);
		BgabGascz002Dto userDto = new BgabGascz002Dto();
		userDto.setXusr_empno(gsReqVo.getEeno());
		userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
		BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);
		String fromEenm   = "OUTCOMP";
		String fromStepNm = "OUTCOMP";
		String toEeno     = userDetailInfo.getXusr_mail_adr();
		String mode       = "confirm";
		String title      = "Business Travel";
		Submit.sendEmailBusinessTripForConfirm(fromEenm, fromStepNm, toEeno, mode, title, rsList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("MAIL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Travel Vendor Check
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doVendorCheckBTToConfirmList.do")
	public ModelAndView doVendorCheckBTToConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson,
			@RequestParam(value="uParams" , required=true) String uParams) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt01       btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);
		List<BgabGascbt01> uList    = (List<BgabGascbt01>) getJsonToList(uParams, BgabGascbt01.class);

		businessTravelManager.vendorCheckBTToConfirmList(uList);

		CommonMessage message = new CommonMessage();
		if(StringUtil.isNullToString(btReqDto.getMode()).equals("checkCancel")){
			message.setMessage(HncisMessageSource.getMessage("CHECKCANCEL.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("CHECK.0002"));
		}

		message.setCode("3");

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * business Travel Vendor Check
	 * @param paramJson
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessTravel/doVendorCheckBTToSaveDetail.do")
	public ModelAndView doVendorCheckBTToSaveDetail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt01       btReqDto = (BgabGascbt01) getJsonToBean(paramJson, BgabGascbt01.class);

		businessTravelManager.saveVendorCheckBTToSaveDetail(btReqDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessTravel/doSearchBudgetViewNew.do")
	public ModelAndView doSearchBudgetViewNew(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbt02 btDtlDto = (BgabGascbt02) getJsonToBean(paramJson, BgabGascbt02.class);

		CommonList list = new CommonList();
		list.setRows(businessTravelManager.getSelectBudgetViewNew(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
}