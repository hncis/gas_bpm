package com.hncis.controller.roomsMeals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.roomsMeals.manager.RoomsMealsManager;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm03Dto;
import com.hncis.roomsMeals.vo.BgabGascrm04Dto;

@Controller
public class RoomsMealsController  extends AbstractController{

	@Autowired
    @Qualifier("roomsMealsManagerImpl")
	private RoomsMealsManager roomsMealsManager;


	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doSearchRmToMultiComboPlant.do")
	public ModelAndView doSearchRmToMultiComboPlant(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="room_plant" , required=true) String room_plant,
			@RequestParam(value="mode" , required=true) String mode,
			@RequestParam(value="auth_type" , required=true) String auth_type,
			@RequestParam(value="admin_id" , required=true) String admin_id,
			@RequestParam(value="corp_cd", required=true) String corp_cd) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<BgabGascrm03Dto> codeList = null;

		BgabGascrm03Dto code = new BgabGascrm03Dto();
		code.setRoom_plant(room_plant);
		code.setAuth_type(auth_type);
		code.setAdmin_id(admin_id);
		code.setCorp_cd(corp_cd);

		codeList = roomsMealsManager.selectRmToMultiComboPlant(code);
		jsonArr = new JSONBaseArray();

		if(mode.equals("S")){
			json = new JSONBaseVO();
			json.put("name", HncisMessageSource.getMessage("select"));
			json.put("value", "");
			jsonArr.add(json);
		}

		for(BgabGascrm03Dto targetBean : codeList){

			json = new JSONBaseVO();
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getRoom_code()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getRoom_place()));

			jsonArr.add(json);
		}
		jso.put("ROOM_PLACE", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doSearchRmToMultiComboMeal.do")
	public ModelAndView doSearchRmToMultiComboMeal(HttpServletRequest req, HttpServletResponse res) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<BgabGascrm04Dto> codeList = null;
		
		BgabGascrm04Dto vo = new BgabGascrm04Dto();
		vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		codeList = roomsMealsManager.selectRmToMultiComboMeal(vo);
		jsonArr = new JSONBaseArray();

		//json = new JSONBaseVO();
		//json.put("name", HncisMessageSource.getMessage("select"));
		//json.put("value", "");
		//jsonArr.add(json);

		for(BgabGascrm04Dto targetBean : codeList){

			json = new JSONBaseVO();
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getMeal_code()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getMeal_name()));
			json.put("price", StringUtil.isNullToStrTrm(targetBean.getMeal_price()));
			jsonArr.add(json);
		}
		jso.put("ROOM_MEAL", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * businessVehicles request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSearchRmToRoomAuthInfo.do")
	public ModelAndView doSearchRmToRoomAuthInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascrm03Dto dto = (BgabGascrm03Dto) getJsonToBean(paramJson, BgabGascrm03Dto.class);

		BgabGascrm03Dto rsReqDto = roomsMealsManager.selectRmToRoomAuthInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrm03Dto();
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** Rooms & Meals request page                           **/
	/*************************************************************/
	/**
	 * Rooms & Meals request save
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSaveRmToRequest.do")
	public ModelAndView doSaveRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		CommonMessage message = new CommonMessage();

		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		BgabGascrm01Dto reqDto= roomsMealsManager.saveRmToRequest(req, dto);

		if(!reqDto.isErrYn()){
			message.setCode(reqDto.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode1("Y");
		}
		else{
			message.setMessage(reqDto.getErrMsg());
			message.setCode1("N");
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Rooms & Meals request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doDeleteRmToRequest.do")
	public ModelAndView doDeleteRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		message = roomsMealsManager.deleteRmToRequest(req, dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToReqList.do")
	public ModelAndView doSearchGridRmToReqList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{


		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

 		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);


 		CommonList list = new CommonList();

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectInfoRmToReqList(dto, req));


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Rooms & Meals request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSearchInfoRmToRequest.do")
	public ModelAndView doSearchInfoRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		BgabGascrm01Dto rsReqDto = new BgabGascrm01Dto();
		rsReqDto = roomsMealsManager.selectInfoRmToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("RM");
			commonApproval.setCorp_cd(dto.getCorp_cd());
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			/*
			if(!StringUtil.isNullToString(rsReqDto.getPgs_st_cd()).equals("0") && StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){
				rsReqDto.setCode(StringUtil.isNullToString(businessTravelManager.getSelectApprovalInfoToRequest(btReqDto)));
			}else{
				rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
			*/
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


	/**
	 * Rooms & Meals request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doApproveRmToRequest.do")
	public ModelAndView doApprovePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		message = roomsMealsManager.setApproval(dto, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * Rooms & Meals request approve cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doApproveCancelRmToRequest.do")
	public ModelAndView doApproveCancelPcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascrm01Dto regDto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		CommonMessage message = roomsMealsManager.setApprovalCancel(regDto,req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Rooms & Meals request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doConfirmRmToRequest.do")
	public ModelAndView doConfirmRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		CommonMessage message = roomsMealsManager.updateRmToRequestForConfirm(req, dto);

//		String fromEeno   = SessionInfo.getSess_name(req);
//		String fromStepNm = SessionInfo.getSess_step_name(req);
//		String toEeno     = dto.getEeno();
//		String mode       = "confirm";
//		String title      = HncisMessageSource.getMessage("mt_rm");
//		String corp_cd    = dto.getCorp_cd();
//
//		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
//		bgabGascz002Dto.setXusr_empno(toEeno);
//		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
//
//		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);
//
//		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Rooms & Meals request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doRejectRmToRequest.do")
	public ModelAndView doRejectRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		CommonMessage message = new CommonMessage();

		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateInfoRmToReject(dto, req);

		//컨펌취소 메일 발송
//		String fromEeno   = SessionInfo.getSess_name(req);
//		String fromStepNm = SessionInfo.getSess_step_name(req);
//		String toEeno     = dto.getEeno();
//		String rtnText    = dto.getSnb_rson_sbc();
//		String title      = HncisMessageSource.getMessage("mt_rm");
//		String corp_cd    = dto.getCorp_cd();
//
//		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
//		bgabGascz002Dto.setXusr_empno(toEeno);
//		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
//		
//		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);
//
//		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

		message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Rooms & Meals request confirm cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doConfirmCancelRmToRequest.do")
	public ModelAndView doConfirmCancelRmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		CommonMessage message = roomsMealsManager.updateRmToRequestForConfirmCancel(req, dto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}



	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToListForMon.do")
	public ModelAndView doSearchGridRmToListForMon(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{


		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

 		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);


 		CommonList list = new CommonList();

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectInfoRmToListForMon(dto, req));


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToListDaily.do")
	public ModelAndView doSearchGridRmToListDaily(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = roomsMealsManager.selectCountRmToListDaily(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectListRmToListDaily(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToListDailyForDone.do")
	public ModelAndView doUpdateRmToListDailyForDone(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		BgabGascrm01Dto reqDto = roomsMealsManager.updateRmToListDailyForDone(req, dtoList);

		CommonMessage message = new CommonMessage();

		if(!reqDto.isErrYn()){
			message.setCode(reqDto.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("DONE.0000"));
			message.setCode1("Y");
		}
		else{
			message.setMessage(reqDto.getErrMsg());
			message.setCode1("N");
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToListDailyForCancel.do")
	public ModelAndView doUpdateRmToListDailyFoCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToListDailyForCancel(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CANCEL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search Room code management. - Code Mgmt. 화면에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToRoomsManagement.do")
	public ModelAndView doSearchGridRmToRoomsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascrm03Dto dto = (BgabGascrm03Dto) getJsonToBean(paramJson, BgabGascrm03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = roomsMealsManager.selectCountRmToRoomsManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectListRmToRoomsManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do save Rooms code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doSaveRmToRoomsManagement.do")
	public ModelAndView doSaveRmToRoomsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascrm03Dto> dtoIList = (List<BgabGascrm03Dto>)getJsonToList(paramsI, BgabGascrm03Dto.class);
		List<BgabGascrm03Dto> dtoUList = (List<BgabGascrm03Dto>)getJsonToList(paramsU, BgabGascrm03Dto.class);

		roomsMealsManager.saveRmToRoomsManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete Rooms code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doDeleteRmToRoomsManagement.do")
	public ModelAndView doDeleteRmToRoomsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm03Dto> dtoList = (List<BgabGascrm03Dto>)getJsonToList(paramJson, BgabGascrm03Dto.class);

		roomsMealsManager.deleteRmToRoomsManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * Do search meal code management. - Code Mgmt. 화면에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToMealsManagement.do")
	public ModelAndView doSearchGridRmToMealsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascrm04Dto dto = (BgabGascrm04Dto) getJsonToBean(paramJson, BgabGascrm04Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = roomsMealsManager.selectCountRmToMealsManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectListRmToMealsManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do save meal code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doSaveRmToMealsManagement.do")
	public ModelAndView doSaveRmToMealsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascrm04Dto> dtoIList = (List<BgabGascrm04Dto>)getJsonToList(paramsI, BgabGascrm04Dto.class);
		List<BgabGascrm04Dto> dtoUList = (List<BgabGascrm04Dto>)getJsonToList(paramsU, BgabGascrm04Dto.class);

		roomsMealsManager.saveRmToMealsManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete meal code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doDeleteRmToMealsManagement.do")
	public ModelAndView doDeleteRmToMealsManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm04Dto> dtoList = (List<BgabGascrm04Dto>)getJsonToList(paramJson, BgabGascrm04Dto.class);

		roomsMealsManager.deleteRmToMealsManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToMealsMgmtList.do")
	public ModelAndView doSearchGridRmToMealsMgmtList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = roomsMealsManager.selectCountRmToMealsMgmtList(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectListRmToMealsMgmtList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do update meal mgmt - Submit.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToMealsMgmtSubmit.do")
	public ModelAndView doUpdateRmToMealsMgmtSubmit(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToMealsMgmtStatus(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SUBMIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do update meal mgmt - ongoing.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToMealsMgmtOngoing.do")
	public ModelAndView doUpdateRmToMealsMgmtOngoing(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToMealsMgmtStatus(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("ONGOING.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do update meal mgmt - done.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToMealsMgmtDone.do")
	public ModelAndView doUpdateRmToMealsMgmtDone(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToMealsMgmtStatus(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DONE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do update meal mgmt - cancel.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToMealsMgmtCancel.do")
	public ModelAndView doUpdateRmToMealsMgmtCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToMealsMgmtStatus(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CANCEL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/roomsMeals/doSearchGridRmToConfirmList.do")
	public ModelAndView doSearchGridRmToConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = roomsMealsManager.selectCountRmToConfirmList(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(roomsMealsManager.selectListRmToConfirmList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToConfirmList.do")
	public ModelAndView doUpdateRmToConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToConfirmList(req, dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/roomsMeals/doUpdateRmToConfirmCancelList.do")
	public ModelAndView doUpdateRmToConfirmCancelList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascrm01Dto> dtoList = (List<BgabGascrm01Dto>)getJsonToList(paramJson, BgabGascrm01Dto.class);

		roomsMealsManager.updateRmToConfirmCancelList(req, dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * Rooms & Meals Approve search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/roomsMeals/doSearchInfoRmToRequestForAprv.do")
	public ModelAndView doSearchInfoRmToRequestForAprv(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascrm01Dto dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		BgabGascrm01Dto rsReqDto = new BgabGascrm01Dto();
		rsReqDto = roomsMealsManager.selectInfoRmToRequestForAprv(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("RM");
			commonApproval.setCorp_cd(dto.getCorp_cd());
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

	/**
	 * meals list excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/roomsMeals/doExcelToMealsMgmtList.excel")
	public ModelAndView doExcelToMealsList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrm01Dto keyParamDto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = roomsMealsManager.selectCountRmToMealsMgmtList(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(count);
		list.setRows(roomsMealsManager.selectListRmToMealsMgmtList(keyParamDto));

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
	
	/**
	 * business Card accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/roomsMeals/doExcelRoomsList.excel")
	public ModelAndView doExcelRoomsList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascrm01Dto bgabGascrm01Dto = (BgabGascrm01Dto) getJsonToBean(paramJson, BgabGascrm01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = roomsMealsManager.selectCountRmToListDaily(bgabGascrm01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascrm01Dto.setStartRow(1);
 		bgabGascrm01Dto.setEndRow(count);
 		//검색
 		list.setRows(roomsMealsManager.selectListRmToListDaily(bgabGascrm01Dto));

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

}
