package com.hncis.controller.taxi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.taxi.manager.TaxiManager;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.taxi.vo.BgabGasctx04;

@Controller
public class TaxiController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
	@Qualifier("taxiManagerImpl")
	private TaxiManager taxiManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * Do search uf to transport combo. - 공통 콤보 데이터 조회
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
	@RequestMapping(value="/hncis/taxi/doSearchTxToTransportCombo.do")
	public ModelAndView doSearchTxToTransportCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="gubn" , required=true) String gubn,
			@RequestParam(value="locale" , required=true) String locale,
			@RequestParam(value="corp_cd" , required=true) String corp_cd) throws Exception{

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGasctx03> codeList = null;

			BgabGasctx03 code = new BgabGasctx03();
			code.setLocale(locale);
			code.setCorp_cd(corp_cd);
			jsonArr = new JSONBaseArray();

			codeList = taxiManager.getComboListTxToTransport(code);

			for(BgabGasctx03 targetBean : codeList){
				json = new JSONBaseVO();
				json.put("key", StringUtil.isNullToStrTrm(targetBean.getCb_key()));
				json.put("code", StringUtil.isNullToStrTrm(targetBean.getCb_code()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getCb_value()));

				jsonArr.add(json);
			}

			jso.put("TRANSPORT", jsonArr);

			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
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
	@RequestMapping(value="/hncis/taxi/doSearchTxToMultiCombo.do")
	public ModelAndView doSearchTxToMultiCombo(HttpServletRequest req, HttpServletResponse res,
//			@RequestParam(value="cb_key" , required=true) String cb_key,
			@RequestParam(value="gubn" , required=true) String gubn,
			@RequestParam(value="locale" , required=true) String locale) throws Exception{

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGasctx03> codeList = null;

			BgabGasctx03 code = new BgabGasctx03();
			code.setLocale(locale);
//			code.setCb_key(cb_key);

			jsonArr = new JSONBaseArray();

			if("f".equals(gubn)){
				codeList = taxiManager.getComboListTxFromPlace(code);

				for(BgabGasctx03 targetBean : codeList){
					json = new JSONBaseVO();
					json.put("key", StringUtil.isNullToStrTrm(targetBean.getCb_key()));
					json.put("code", StringUtil.isNullToStrTrm(targetBean.getCb_code()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getCb_value()));

					jsonArr.add(json);
				}

				jso.put("FROM_PLACE", jsonArr);
			}

			if("t".equals(gubn)){
				codeList = taxiManager.getComboListTxToPlace(code);

				for(BgabGasctx03 targetBean : codeList){
					json = new JSONBaseVO();
					json.put("key", StringUtil.isNullToStrTrm(targetBean.getCb_key()));
					json.put("code", StringUtil.isNullToStrTrm(targetBean.getCb_code()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getCb_value()));

					jsonArr.add(json);
				}

				jso.put("TO_PLACE"  , jsonArr);
			}

			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value="/hncis/taxi/doSearchTxToTaxiAmount.do")
	public ModelAndView doSearchTxToTaxiAmount(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		try{
			BgabGasctx03 dto = (BgabGasctx03) getJsonToBean(paramJson, BgabGasctx03.class);

			BgabGasctx03 rsReqDto = taxiManager.getSelectInfoTxToTaxiAmount(dto);

			if(rsReqDto == null){
				rsReqDto = new BgabGasctx03();
				rsReqDto.setSvca_amt("0");
			}

			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
			modelAndView.addObject("uiType", "ajax");
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}

	/**
	 * taxi request apply
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/taxi/doInsertTXToRequest.do")
	public ModelAndView doInsertTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo,
			@RequestParam(value="paramJsonList", required=true) String paramJsonList)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGasctx01 cgabGasctx01 = (BgabGasctx01)getJsonToBean(bsicInfo, BgabGasctx01.class);
		List<BgabGasctx04> bgabGasctx04 = (List<BgabGasctx04>) getJsonToList(paramJsonList, BgabGasctx04.class);

		BgabGasctx02 cgabGasctx02 = new BgabGasctx02();
		cgabGasctx02.setKey_eeno(cgabGasctx01.getEeno());
		cgabGasctx02.setKey_mode("C");

		//String doc_no = StringUtil.getDocNo();
		//cgabGasctx01.setDoc_no(doc_no);

		String doc_no = cgabGasctx01.getDoc_no();

		Integer rs1 = (Integer)taxiManager.insertInfoTXToRequest_1(cgabGasctx01);
		if(rs1 > 0){
			for(BgabGasctx04 tx04 : bgabGasctx04){
				tx04.setDoc_no(doc_no);
			}
			Integer rs2 = (Integer)taxiManager.insertInfoTXToRequest_2(cgabGasctx01, bgabGasctx04);
			if(rs2 > 0){
				message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
				message.setCode(cgabGasctx01.getDoc_no());

			}else{
				message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			}
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}


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
	 * taxi request modify
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doModifyTXToRequest.do")
	public ModelAndView doModifyTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo,
			@RequestParam(value="paramJsonList", required=true) String paramJsonList)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGasctx01 cgabGasctx01 = (BgabGasctx01)getJsonToBean(bsicInfo, BgabGasctx01.class);
		List<BgabGasctx04> bgabGasctx04 = (List<BgabGasctx04>) getJsonToList(paramJsonList, BgabGasctx04.class);

		taxiManager.updateInfoTXToRequest(cgabGasctx01, bgabGasctx04);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setCode(cgabGasctx01.getDoc_no());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * taxi request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/taxi/doDeleteTXToRequest.do")
	public ModelAndView doDeleteTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);
		
		taxiManager.deleteInfoTXToRequest(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

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
	 * taxi schedule delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doDeleteTaxiScheduleToRequest.do")
	public ModelAndView doDeleteTaxiScheduleToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGasctx04 dto = (BgabGasctx04) getJsonToBean(paramJson, BgabGasctx04.class);
		
		taxiManager.deleteTxToRequestList(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * taxi request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doApproveTXToRequest.do")
	public ModelAndView doApproveTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGasctx02 dto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		CommonMessage appMessage = null;
		//if(dto.getAuth_type().equals("6")){
		//	appMessage = taxiManager.setApprovalForSpecialAuth(dto, req);
		//}else{
			appMessage = taxiManager.setApproval(dto, appInfo, message, req);
		//}

		//send email
//		BgabGasctx04 s_param = new BgabGasctx04();
//		s_param.setDoc_no(dto.getDoc_no());
//		List<BgabGasctx04> rsList = taxiManager.getSelectListTxToRequest(s_param);
//
//		// 택시 메일 주소 리스트
//		CommonCode code = new CommonCode();
//		code.setCodknd("X3033");
//		code.setLocale(dto.getLocale());
//		List<CommonCode> taxiList = commonManager.getCodeList(code);
//
//		for(BgabGasctx04 rsVo : rsList){
////			String fromEenm   = SessionInfo.getSess_name(req);
////			String fromStepNm = SessionInfo.getSess_step_name(req);
////			String toEeno	  = "suzi.yun@hyundai-brasil.com";
////			String mode       = "request";
////			String title      = "Taxi";
////			Submit.sendEmailTaxiForRequest(fromEenm, fromStepNm, toEeno, mode, title, rsVo);
//
//			for(int i=0; i<taxiList.size(); i++){
//				String fromEenm   = SessionInfo.getSess_name(req);
//				String fromStepNm = SessionInfo.getSess_step_name(req);
//				String toEeno	  = taxiList.get(i).getName(); // taxi 회사 메일
//				String mode       = "request";
//				String title      = "Taxi";
//				Submit.sendEmailTaxiForRequest(fromEenm, fromStepNm, toEeno, mode, title, rsVo);
//			}
//		}
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(appMessage).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * taxi request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doApproveCancelTXToRequest.do")
	public ModelAndView doApproveCancelTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonApproval rtnApproval = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		message = taxiManager.setApprovalCancel(keyParamDto, appInfo, message, req);

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
	 * taxi request confirm1
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doConfirmTXToRequest.do")
	public ModelAndView doConfirm1TXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();
		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		taxiManager.updateInfoTXToApprove(keyParamDto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getKey_eeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("trpt_cost");
		String corp_cd    = keyParamDto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

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
	 * taxi request confirm1
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doRejectTXToRequest.do")
	public ModelAndView doRejectTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		CommonMessage message = taxiManager.updateInfoTxToReject(keyParamDto, req);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getEeno();
		String rtnText    = keyParamDto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("trpt_cost");
		String corp_cd    = keyParamDto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

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
	 * taxi request confirm cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doConfirmCancelTXToRequest.do")
	public ModelAndView doConfirmCancelTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		taxiManager.updateInfoTXToApprove(keyParamDto);

//		String fromEeno   = SessionInfo.getSess_name(req);
//		String fromStepNm = SessionInfo.getSess_step_name(req);
//		String toEeno     = keyParamDto.getKey_eeno();
//		String rtnText    = keyParamDto.getSnb_rson_sbc();
//		String title      = "Taxi";
//
//		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
//		bgabGascz002Dto.setXusr_empno(toEeno);
//		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
//
//		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);
//
//		Submit.confirmCancelEmail(fromEeno, fromStepNm, mailAdr, title, rtnText);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

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

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * Taxi request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/taxi/doSearchToRequest.do")
	public ModelAndView doSearchToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		BgabGasctx01 rsReqDto = new BgabGasctx01();
		rsReqDto = taxiManager.getSelectInfoTXToRequest(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rsReqDto.getIf_id());
				commonApproval.setSystem_code("TX");
				commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

				rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}

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
	@RequestMapping(value="/hncis/taxi/doSearchListTxToRequest.do")
	public ModelAndView doSearchListTxToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGasctx04 dto = (BgabGasctx04) getJsonToBean(paramJson, BgabGasctx04.class);

 		CommonList list = new CommonList();
 		//검색
 		list.setRows(taxiManager.getSelectListTxToRequest(dto));

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
	@RequestMapping(value="/hncis/taxi/doSearchListTxToRequestApprove.do")
	public ModelAndView doSearchListTxToRequestApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGasctx04 dto = (BgabGasctx04) getJsonToBean(paramJson, BgabGasctx04.class);

 		CommonList list = new CommonList();
 		//검색
 		list.setRows(taxiManager.getSelectListTxToRequestApprove(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * taxi submit search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doSearchToSubmit.do")
	public ModelAndView doSearchToSubmit(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		BgabGasctx01 rsReqDto = new BgabGasctx01();
		rsReqDto = taxiManager.getSelectInfoTXToSubmit(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(keyParamDto.getIf_id());
			commonApproval.setSystem_code("TX");
			commonApproval.setCorp_cd(keyParamDto.getCorp_cd());
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
	 * taxi List search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doSearchTXToList.do")
	public ModelAndView doSearchBTToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGasctx01 btDtlDto = (BgabGasctx01) getJsonToBean(paramJson, BgabGasctx01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = taxiManager.getSelectCountTXToList(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(taxiManager.getSelectTXToList(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * taxi accept search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doSearchGridToAccept.do")
	public ModelAndView doSearchGridToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasctx02 keyParamDto = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = taxiManager.getSelectCountTXToAccept(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(taxiManager.getSelectListTXToAccept(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/*************************************************************/
	/** Taxi Place managerment page                  	            **/
	/*************************************************************/
	/**
	 * Taxi Place managerment list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/taxi/doSearchGridTxToPlaceManagement.do")
	public ModelAndView doSearchGridTxToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGasctx03 dto = (BgabGasctx03) getJsonToBean(paramJson, BgabGasctx03.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = taxiManager.getSelectCountTxToPlaceManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(taxiManager.getSelectListTxToPlaceManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 *Taxi Place managerment list insert.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/taxi/doInsertTxToPlaceManagement.do")
	public ModelAndView doInsertTxToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGasctx03> dto = (List<BgabGasctx03>) getJsonToList(paramJson, BgabGasctx03.class);

		int cnt = taxiManager.insertTxToPlaceManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *Taxi Place managerment list delete.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/taxi/doDeleteTxToPlaceManagement.do")
	public ModelAndView doDeleteTxToPlaceManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGasctx03> dto = (List<BgabGasctx03>) getJsonToList(paramJson, BgabGasctx03.class);

		int cnt = taxiManager.deleteTxToPlaceManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/taxi/doSearchToTaxiUserInfo.do")
	public ModelAndView doSearchToTaxiUserInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		BgabGascz002Dto userDto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		BgabGascz002Dto userDetailInfo = taxiManager.getSelectTaxiUserInfo(userDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(userDetailInfo != null){
			userDetailInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userDetailInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Taxi file save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/taxi/doSaveTxToFile.do")
	public void doSaveTxToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			taxiManager.saveTxToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * Taxi file search
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/taxi/doSearchTxToFile.do")
	public ModelAndView doSearchTxToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(taxiManager.getSelectTxToFile(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Taxi file dowm
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/taxi/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = taxiManager.getSelectTxToFileInfo(dto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "taxi");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	/**
	 * Taxi file delete
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/taxi/doDeleteTxToFile.do")
	public ModelAndView doDeleteTxToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		taxiManager.deleteTxToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/taxi/doExcelTaxiList.excel")
	public ModelAndView doExcelTaxiList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGasctx02 bgabGasctx02 = (BgabGasctx02) getJsonToBean(paramJson, BgabGasctx02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = taxiManager.getSelectCountTXToAccept(bgabGasctx02);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGasctx02.setStartRow(1);
 		bgabGasctx02.setEndRow(count);
 		//검색
 		list.setRows(taxiManager.getSelectListTXToAccept(bgabGasctx02));

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
