package com.hncis.controller.pickupService;

import java.io.IOException;
import java.util.ArrayList;
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
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.pickupService.manager.PickupServiceManager;
import com.hncis.pickupService.vo.BgabGascps01Dto;
import com.hncis.pickupService.vo.BgabGascps02Dto;
import com.hncis.pickupService.vo.BgabGascps03Dto;
import com.hncis.pickupService.vo.BgabGascps04Dto;
import com.hncis.pickupService.vo.BgabGascps05Dto;
import com.hncis.pickupService.vo.PickupServiceDto;

@Controller
public class PickupServiceController extends AbstractController{


	@Autowired
    @Qualifier("pickupServiceManagerImpl")
	private PickupServiceManager pickupServiceManager;


	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * pick-up service 에서 사용하는 selectbox 값 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param codknd the codknd
	 * @param keyType the key type
	 * @return the combo list by uf
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doSearchPsToCombo.do")
	public ModelAndView doSearchPsToCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{

		String codeKnd[];
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = new JSONBaseArray();
		CommonCode code = new CommonCode();

		String comboVal				 = "";
		String comboName			 = "";
		String comboTable			 = "";
		String comboWhereCondition	 = "";
		String comboGroupBy			 = "";
		String comboorderBy			 = "";

		String []codeKnds = codknd.split(";");

		for (int i =0; i < codeKnds.length; i++){

			codeKnd = codeKnds[i].split(":");

			if(codeKnd[0].equals("firm_cd")){
				comboVal = "FIRM_CD";
				comboName = "FIRM_NM";
				comboTable = "BGAB_GASCZ012" + SessionInfo.getSess_corp_cd(req);
				comboWhereCondition  = " USE_YN = 'Y'";
				comboGroupBy = "";
				comboorderBy = "FIRM_CD";
			}


			code.setValue(comboVal);
			code.setName(comboName);
			code.setTableName(comboTable);
			code.setWhereCondition(comboWhereCondition);
			code.setGroupBy(comboGroupBy);
			code.setOrderBy(comboorderBy);
			code.setComboType(codeKnd.length > 1 ? codeKnd[1]:"N");
			code.setComboName(codeKnd[0]);

			jsonArr = commonManager.getDataCombo(code);

			jso.put(codeKnd[0], jsonArr);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Do search uf to multi combo. - 공통 콤보 데이터 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param unif_lgrp_cd the unif_lgrp_cd
	 * @param unif_mgrp_cd the unif_mgrp_cd
	 * @param unif_sgrp_cd the unif_sgrp_cd
	 * @param combo_type the combo_type
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doSearchPsToMultiCombo.do")
	public ModelAndView doSearchPsToMultiCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="cb_key" , required=true) String cb_key,
			@RequestParam(value="gubn" , required=true) String gubn,
			@RequestParam(value="corp_cd" , required=true) String corpCd) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<PickupServiceDto> codeList = null;

		PickupServiceDto code = new PickupServiceDto();
		code.setCb_key(cb_key);
		code.setLocale(SessionInfo.getSess_locale(req));
		code.setCorp_cd(corpCd);

		jsonArr = new JSONBaseArray();

		if("f".equals(gubn)){
			codeList = pickupServiceManager.getComboListFromPlace(code);

			for(PickupServiceDto targetBean : codeList){

				json = new JSONBaseVO();
				json.put("key", StringUtil.isNullToStrTrm(targetBean.getCb_key()));
				json.put("code", StringUtil.isNullToStrTrm(targetBean.getCb_code()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getCb_value()));

				jsonArr.add(json);
			}
			jso.put("FROM_PLACE", jsonArr);
		}

		jsonArr = new JSONBaseArray();
		if("t".equals(gubn)){
			codeList = pickupServiceManager.getComboListToPlace(code);

			for(PickupServiceDto targetBean : codeList){

				json = new JSONBaseVO();
				json.put("key", StringUtil.isNullToStrTrm(targetBean.getCb_key()));
				json.put("code", StringUtil.isNullToStrTrm(targetBean.getCb_code()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getCb_value()));

				jsonArr.add(json);
			}
			jso.put("TO_PLACE", jsonArr);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/*************************************************************/
	/** pickupService request page                              **/
	/*************************************************************/
	/**
	 * pickupService request insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doInsertPsToRequest.do")
	public ModelAndView doInsertPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJsonList" , required=true) String paramJsonList,
			@RequestParam(value="paramJsonStr" , required=true) String paramJsonStr
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJsonStr, BgabGascps01Dto.class);
		List<BgabGascps02Dto> list = (List<BgabGascps02Dto>) getJsonToList(paramJsonList, BgabGascps02Dto.class);

		BgabGascps01Dto reqDto= pickupServiceManager.insertPsToRequest(dto,list);
		if(reqDto.getErrYn().equals("N")){
			message.setCode1("Y");
			message.setCode(reqDto.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}
		else{
			message.setCode1("N");
			message.setMessage(reqDto.getErrMsg());

		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doDeletePsToRequest.do")
	public ModelAndView doDeletePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascps01Dto bgabGascps01Dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		int cnt= pickupServiceManager.deletePsToRequest(bgabGascps01Dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService schedule delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doDeleteScheduleToRequest.do")
	public ModelAndView doDeleteScheduleToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		List<BgabGascps02Dto> dto = (List<BgabGascps02Dto>) getJsonToList(paramJson, BgabGascps02Dto.class);

		int cnt= pickupServiceManager.deleteScheduleToRequest(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService driver info delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doDeleteDriverInfoToRequest.do")
	public ModelAndView doDeleteDriverInfoToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascps04Dto dto = (BgabGascps04Dto) getJsonToBean(paramJson, BgabGascps04Dto.class);

		int cnt= pickupServiceManager.deletePsDriverInfoToRequestList(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchInfoPsToRequest.do")
	public ModelAndView doSearchInfoPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		BgabGascps01Dto rsReqDto = new BgabGascps01Dto();
		rsReqDto = pickupServiceManager.getSelectInfoPsToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("PS");
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
	 * pickupService list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchListPsToRequest.do")
	public ModelAndView doSearchListEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps02Dto dto = (BgabGascps02Dto) getJsonToBean(paramJson, BgabGascps02Dto.class);

		CommonList list = new CommonList();
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToRequest(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * pickupService list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchDriverListPsToRequest.do")
	public ModelAndView doSearchDriverListPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps04Dto dto = (BgabGascps04Dto) getJsonToBean(paramJson, BgabGascps04Dto.class);


 		CommonList list = new CommonList();
 		//검색
 		list.setRows(pickupServiceManager.selectPsDriverInfoToRequestList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * pickupService request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doApprovePsToRequest.do")
	public ModelAndView doApprovePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		message = pickupServiceManager.setApproval(dto, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request approve cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/pickupService/doApproveCancelPsToRequest.do")
	public ModelAndView doApproveCancelPcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);
		dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		CommonMessage message = pickupServiceManager.setApprovalCancel(dto, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doConfirmPsToRequest.do")
	public ModelAndView doConfirmPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJsonList" , required=true) String paramJsonList,
			@RequestParam(value="paramJsonStr" , required=true) String paramJsonStr,
			@RequestParam(value="paramJsonDriver" , required=false) String paramJsonDriver
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJsonStr, BgabGascps01Dto.class);
		List<BgabGascps02Dto> list = (List<BgabGascps02Dto>) getJsonToList(paramJsonList, BgabGascps02Dto.class);
//		List<BgabGascps04Dto> diList = (List<BgabGascps04Dto>) getJsonToList(paramJsonDriver, BgabGascps04Dto.class);
		List<BgabGascps04Dto> diList = new ArrayList<BgabGascps04Dto>();

		int cnt = pickupServiceManager.updateInfoPsToConfirm(dto,list,req,diList);

		if(cnt == 1){
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request ConfirmCancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doConfirmCancelPsToRequest.do")
	public ModelAndView doConfirmCancelPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{


		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		int cnt = pickupServiceManager.updateInfoPsToConfirmCancel(dto,req);

		CommonMessage message = new CommonMessage();

		if(cnt == 1){
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0001"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * pickupService request reject
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doRejectPsToRequest.do")
	public ModelAndView doRejectPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		CommonMessage appMessage;

		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		appMessage = pickupServiceManager.updateInfoPsToReject(dto,req);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(appMessage.getMessage());
			message.setErrorCode(appMessage.getErrorCode());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/pickupService/doSearchPsToPickupAmount.do")
	public ModelAndView doSearchPsToPickupAmount(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascps03Dto dto = (BgabGascps03Dto) getJsonToBean(paramJson, BgabGascps03Dto.class);

		BgabGascps03Dto rsReqDto = new BgabGascps03Dto();
		rsReqDto = pickupServiceManager.getSelectInfoPsToPickupAmount(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/*************************************************************/
	/** pickupService list page                                 **/
	/*************************************************************/
	/**
	 * pickupService list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchGridPsToList.do")
	public ModelAndView doSearchGridPsToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps01Dto bgabGascps01Dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

//		RfcBudgetCheck rf = new RfcBudgetCheck();
//
//		RfcBudgetCheckVo rt = rf.getResult();
//
//		System.out.println("Gs_msg:"+rt.getGs_msg());
//		System.out.println("O_actual:"+rt.getO_actual());
//		System.out.println("O_balance:"+rt.getO_balance());
//		System.out.println("O_budget:"+rt.getO_budget());
//		System.out.println("O_commitment:"+rt.getO_commitment());
//		System.out.println("O_waers:"+rt.getO_waers());


 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.getSelectCountPsToList(bgabGascps01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascps01Dto.setStartRow(startRow);
 		bgabGascps01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToList(bgabGascps01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/*************************************************************/
	/** pickupService confirm page                                 **/
	/*************************************************************/
	/**
	 * pickupService list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchGridPsToConfirm.do")
	public ModelAndView doSearchGridPsToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps01Dto bgabGascps01Dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.getSelectCountPsToConfirm(bgabGascps01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascps01Dto.setStartRow(startRow);
 		bgabGascps01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToConfirm(bgabGascps01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * pickupService srlNo check
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doCheckPsToSrlNo.do")
	public ModelAndView doCheckPsToSrlNo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		BgabGascps01Dto rsReqDto = new BgabGascps01Dto();
		int cnt = pickupServiceManager.getCheckPsToSrlNo(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(cnt > 0){
			CommonMessage message = new CommonMessage();
			message.setCode("N");
			message.setMessage(HncisMessageSource.getMessage("EXPORT.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setCode("Y");
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *  pick-up service sap Excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/pickupService/doPoExcelToList.excel")
	public ModelAndView doSapExcelToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="expenseInfo", required=true) String expenseInfo,
			@RequestParam(value="expenseList", required=true) String expenseList) throws HncisException, SessionException{

		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(expenseInfo, BgabGascps01Dto.class);
		List<BgabGascps01Dto> list = (List<BgabGascps01Dto>) getJsonToList(expenseList, BgabGascps01Dto.class);
		Boolean returnVal = pickupServiceManager.setExpenseTemplatMake(dto,list,req);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   dto.getPo_no()+".xls");
		mpfileData.put("fileName",   "po_expense.xls");
		mpfileData.put("folderName",   "temp");
		mpfileData.put("fileDelete",   "Y");

		return new ModelAndView("download", "fileData", mpfileData);
	}
	/**
	 *Pick-up Place managerment list delete.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doUpdatePsToCancelSapData.do")
	public ModelAndView doUpdatePsToCancelSapData(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascps01Dto> list = (List<BgabGascps01Dto>) getJsonToList(paramJson, BgabGascps01Dto.class);

		int cnt = pickupServiceManager.updatePsToCancelSapData(list);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/*************************************************************/
	/** Agency managerment page                  	            **/
	/*************************************************************/
	/**
	 * Agency managerment list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchGridPsToAgencyManagement.do")
	public ModelAndView doSearchGridBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps05Dto dto = (BgabGascps05Dto) getJsonToBean(paramJson, BgabGascps05Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.getSelectCountPsToAgencyManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToAgencyManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 *Agency managerment list insert.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doInsertPsToAgencyManagement.do")
	public ModelAndView doInsertBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascps05Dto> dto = (List<BgabGascps05Dto>) getJsonToList(paramJson, BgabGascps05Dto.class);

		int cnt = pickupServiceManager.insertPsToAgencyManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *Agency managerment list delete.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doDeletePsToAgencyManagement.do")
	public ModelAndView doDeleteBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascps05Dto> dto = (List<BgabGascps05Dto>) getJsonToList(paramJson, BgabGascps05Dto.class);

		int cnt = pickupServiceManager.deletePsToAgencyManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** pickupService approve page                  	        **/
	/*************************************************************/
	/**
	 * pickupService approve search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchInfoPsToRequestForApprove.do")
	public ModelAndView doSearchInfoPsToRequestForApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascps01Dto dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		BgabGascps01Dto rsReqDto = new BgabGascps01Dto();
		rsReqDto = pickupServiceManager.getSelectInfoPsToRequestForApprove(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("PS");
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
	@RequestMapping(value="/hncis/pickupService/doSearchListPsToRequestForApprove.do")
	public ModelAndView doSearchListPsToRequestForApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps02Dto dto = (BgabGascps02Dto) getJsonToBean(paramJson, BgabGascps02Dto.class);


 		CommonList list = new CommonList();
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToRequestForApprove(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/*************************************************************/
	/** Pick-up Place managerment page                  	            **/
	/*************************************************************/
	/**
	 * Pick-up Place managerment list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchGridPsToPlaceManagement.do")
	public ModelAndView doSearchGridBvToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps03Dto dto = (BgabGascps03Dto) getJsonToBean(paramJson, BgabGascps03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.getSelectCountPsToPlaceManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToPlaceManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 *Pick-up Place managerment list insert.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doInsertPsToPlaceManagement.do")
	public ModelAndView doInsertBvToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascps03Dto> dto = (List<BgabGascps03Dto>) getJsonToList(paramJson, BgabGascps03Dto.class);

		int cnt = pickupServiceManager.insertPsToPlaceManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *Pick-up Place managerment list delete.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doDeletePsToPlaceManagement.do")
	public ModelAndView doDeleteBvToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascps03Dto> dto = (List<BgabGascps03Dto>) getJsonToList(paramJson, BgabGascps03Dto.class);

		int cnt = pickupServiceManager.deletePsToPlaceManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Pick-up service file save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/pickupService/doSavePsToFile.do")
	public void doSavePsToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		System.out.println("fileInfo="+fileInfo);

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			pickupServiceManager.savePsToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * Pick-up service file search
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchPsToFile.do")
	public ModelAndView doSearchPsToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		ModelAndView modelAndView = null;
		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(pickupServiceManager.getSelectPsToFile(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Pick-up service file down
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = pickupServiceManager.getSelectPsToFileInfo(btReqDto);



		//File downloadFile = new File("D:/upload/businessTravel/201310281811374.docx");

		System.out.println("doFileDown");

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "pickupService");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	/**
	 * Pick-up service file delete
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doDeletePsToFile.do")
	public ModelAndView doDeletePsToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> bgabGascZ011Dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		pickupServiceManager.deletePsToFile(bgabGascZ011Dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * pickupService schedule list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doSearchListPsToPickupSchedule.do")
	public ModelAndView doSearchListPsToPickupSchedule(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascps02Dto dto = (BgabGascps02Dto) getJsonToBean(paramJson, BgabGascps02Dto.class);
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.selectCountPsToPickupSchedule(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(pickupServiceManager.selectListPsToPickupSchedule(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * pickupService schedule list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doExcelPsToPickupSchedule.excel")
	public ModelAndView doExcelPsToPickupSchedule(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascps02Dto dto = (BgabGascps02Dto) getJsonToBean(paramJson, BgabGascps02Dto.class);
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int count = pickupServiceManager.selectCountPsToPickupSchedule(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(count);
 		//검색
 		list.setRows(pickupServiceManager.selectListPsToPickupSchedule(dto));

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

	/**
	 * pickupService schedule list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/pickupService/doExcelPsToRequestPickupSchedule.excel")
	public ModelAndView doExcelPsToRequestPickupSchedule(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{


		//조회조건 설정
		BgabGascps02Dto dto = (BgabGascps02Dto) getJsonToBean(paramJson, BgabGascps02Dto.class);

		CommonList list = new CommonList();

 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToRequest(dto));

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

	/**
	 * pick up final po create
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/pickupService/doCompletePickUpPo.do")
	public ModelAndView doCompletePickUpPo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		CommonMessage appMessage = new CommonMessage();

		BgabGascps04Dto d_info = (BgabGascps04Dto)getJsonToBean(paramJson, BgabGascps04Dto.class);

		appMessage= pickupServiceManager.updateCompletePickUpPo(d_info);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setCode1("Y");
			message.setCode(d_info.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("COMPLETE.0000"));
		}
		else{
			message.setCode1("N");
			message.setErrorCode(appMessage.getErrorCode());
			message.setMessage(appMessage.getMessage());

		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

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
	@RequestMapping(value="/hncis/pickupService/doExcelPickUpList.excel")
	public ModelAndView doExcelPickUpList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascps01Dto bgabGascps01Dto = (BgabGascps01Dto) getJsonToBean(paramJson, BgabGascps01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = pickupServiceManager.getSelectCountPsToConfirm(bgabGascps01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascps01Dto.setStartRow(1);
 		bgabGascps01Dto.setEndRow(count);
 		//검색
 		list.setRows(pickupServiceManager.getSelectListPsToConfirm(bgabGascps01Dto));

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
