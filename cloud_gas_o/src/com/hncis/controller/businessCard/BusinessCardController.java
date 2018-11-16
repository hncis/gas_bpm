package com.hncis.controller.businessCard;

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

import com.hncis.businessCard.manager.BusinessCardManager;
import com.hncis.businessCard.vo.BgabGascba01;
import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.businessCard.vo.BgabGascba03;
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
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;

@Controller
public class BusinessCardController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
	@Qualifier("businessCardManagerImpl")
	private BusinessCardManager businessCardManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Card request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doSearchToRequest.do")
	public ModelAndView doSearchToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		BgabGascba01 rsReqDto = new BgabGascba01();
		
		rsReqDto = businessCardManager.getSelectInfoBCToRequest(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			if(!StringUtil.isNullToString(rsReqDto.getReq_date()).equals("")){
				keyParamDto.setKey_req_date(rsReqDto.getReq_date());
				rsReqDto.setCode(StringUtil.isNullToString(businessCardManager.getSelectApprovalInfo(keyParamDto)));
			}

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("BC");
			commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
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
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card request apply
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessCard/doInsertBCToRequest.do")
	public ModelAndView doInsertBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascba01 cgabGascba01 = (BgabGascba01)getJsonToBean(bsicInfo, BgabGascba01.class);

		BgabGascba02 cgabGascba02 = new BgabGascba02();
		cgabGascba02.setKey_eeno(cgabGascba01.getEeno());
		cgabGascba02.setKey_mode("C");
		cgabGascba02.setCorp_cd(cgabGascba01.getCorp_cd());

		int cnt = businessCardManager.getSelectCountBCToAccept(cgabGascba02);

		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0004"));
		}else{
			Integer rs1 = (Integer)businessCardManager.insertInfoBCToRequest_1(cgabGascba01);
			if(rs1 > 0){
				String doc_no = StringUtil.getDocNo();
				cgabGascba01.setDoc_no(doc_no);
				message.setCode(doc_no);
				Integer rs2 = (Integer)businessCardManager.insertInfoBCToRequest_2(cgabGascba01);
				if(rs2 > 0){
					message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
				}else{
					message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
				}
			}else{
				message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			}
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
	 * business Card request modify
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doModifyBCToRequest.do")
	public ModelAndView doModifyBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascba01 cgabGascba01 = (BgabGascba01)getJsonToBean(bsicInfo, BgabGascba01.class);

		businessCardManager.insertInfoBCToRequest_1(cgabGascba01);
		businessCardManager.insertInfoBCToRequest_2(cgabGascba01);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));

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
	 * business Card request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doDeleteBCToRequest.do")
	public ModelAndView doDeleteBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		businessCardManager.deleteInfoBCToRequest(keyParamDto);

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
	 * business Card request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doApproveBCToRequest.do")
	public ModelAndView doApproveBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		CommonMessage appMessage = businessCardManager.setApproval(keyParamDto, appInfo, message, req);

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
	 * business Card request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doApproveCancelBCToRequest.do")
	public ModelAndView doApproveCancelBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonApproval rtnApproval = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		rtnApproval = businessCardManager.setApprovalCancel(keyParamDto, appInfo, message, req);

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
	 * business Card request confirm1
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirm1BCToRequest.do")
	public ModelAndView doConfirm1BCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		businessCardManager.updateInfoBCToRequest(keyParamDto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getKey_eeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("business_card");
		String corp_cd 	  = keyParamDto.getCorp_cd();
		
		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String email = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, email, mode, title, corp_cd);

		CommonMessage message = new CommonMessage();
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
	 * business Card request confirm cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirmCancelBCToRequest.do")
	public ModelAndView doConfirmCancelBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		businessCardManager.updateInfoBCToRequest(keyParamDto);

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

	/**
	 * business Card request reject
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doRejectBCToRequest.do")
	public ModelAndView doRejectBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;
		CommonMessage appMessage;

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		appMessage = businessCardManager.updateInfoBcToReject(keyParamDto, req);

		CommonMessage message = new CommonMessage();
		if("".equals(StringUtil.isNullToStrTrm(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode(keyParamDto.getKey_pgs_st_cd());
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
	 * business Card request confirm2
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirm2BCToRequest.do")
	public ModelAndView doConfirm2BCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		businessCardManager.updateInfoBCToRequest(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM2.0000"));
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
	/** accept page                                              */
	/*************************************************************/
	/**
	 * business Card accept search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doSearchGridToAccept.do")
	public ModelAndView doSearchGridToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessCardManager.getSelectCountBCToAccept(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(businessCardManager.getSelectListBCToAccept(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * business Card accept delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doDeleteToAccept.do")
	public ModelAndView doDeleteToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		businessCardManager.deleteListBCToAccept(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card accept return
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doRejectToAccept.do")
	public ModelAndView doRejectToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		Integer rs = (Integer)businessCardManager.updateListBCToAcceptByReject(keyParamDto);

		if(rs > 0){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card accept confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirm1ToAccept.do")
	public ModelAndView doConfir1ToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		businessCardManager.updateListBCToAcceptByConfirm1(keyParamDto);

		for(int i=0;i<keyParamDto.size();i++){
			String fromEeno   = SessionInfo.getSess_name(req);
			String fromStepNm = SessionInfo.getSess_step_name(req);
			String toEeno     = keyParamDto.get(i).getKey_eeno();
			String mode       = "confirm";
			String title      = HncisMessageSource.getMessage("business_card");
			String corp_cd    = keyParamDto.get(i).getCorp_cd();

			BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
			bgabGascz002Dto.setXusr_empno(toEeno);
			bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			String email = commonManager.selectUserMailAddr(bgabGascz002Dto);

//			Submit.sendEmailConfirm(fromEeno, fromStepNm, toEeno, mode, title);
			Submit.sendEmailConfirm(fromEeno, fromStepNm, email, mode, title, corp_cd);
		}


		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card accept confirmCancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirmCancelToAccept.do")
	public ModelAndView doConfirmCancelToAccep(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		businessCardManager.updateListBCToAcceptByConfirmCancel(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card accept confirm2
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doConfirm2ToAccept.do")
	public ModelAndView doConfirm2ToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		businessCardManager.updateListBCToAcceptByConfirm2(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM2.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card accept issue
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/businessCard/doIssueToAccept.do")
	public ModelAndView doIssueToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		@SuppressWarnings("unchecked")
		List<BgabGascba02> keyParamDto = (List<BgabGascba02>)getJsonToList(paramJson, BgabGascba02.class);

		businessCardManager.updateListBCToAcceptByIssue(keyParamDto);

		for(int i=0 ; i < keyParamDto.size() ; i++){
			String fromEeno   = SessionInfo.getSess_name(req);
			String fromStepNm = SessionInfo.getSess_step_name(req);
			String toEeno     = keyParamDto.get(i).getKey_eeno();
			String mode       = "issue";
			String title      = HncisMessageSource.getMessage("business_card");
			String corp_cd    = keyParamDto.get(i).getCorp_cd();

			BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
			bgabGascz002Dto.setXusr_empno(toEeno);
			bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			String email = commonManager.selectUserMailAddr(bgabGascz002Dto);

			Submit.sendEmailConfirm(fromEeno, fromStepNm, toEeno, mode, title, corp_cd);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("ISSUE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card combo list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessCard/doComboListToRequest.do")
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
			codeKnd = codeKnds[i].toLowerCase().split(":");
			originalCodeKnd = codeKnds[i].split(":");

			//comboTable           = "bgab_gascba03" + SessionInfo.getSess_corp_cd(req);
			comboTable           = "bgab_gascba03";
			comboGroupBy         = "";
			comboorderBy         = "sort";
			//if(codeKnd[0].equals("bc_adr_knd") || codeKnd[0].equals("bc_adr_kr_knd") || codeKnd[0].equals("bc_adr_en_knd")){
			//}
			if(codeKnd[0].equals("bc_type") || codeKnd[0].equals("bc_knd") || codeKnd[0].equals("bcr_reasn")
					|| codeKnd[0].equals("bc_prt")){
				comboVal             = "bc_type";
				comboName            = "name";
				comboWhereCondition  = " aply_knd = 'Y' and knd = '"+codeKnd[1]+"'"; //and code = '"+codeKnd[2]+"'
			}else{
				comboVal             = "code";
				comboName            = "name";
				comboWhereCondition  = " aply_knd = 'Y' and knd = '"+codeKnd[1]+"' and bc_type = '"+codeKnd[2]+"'";
			}

			code.setValue(comboVal);
			code.setName(comboName);
			code.setTableName(comboTable);
			code.setWhereCondition(comboWhereCondition);
			code.setGroupBy(comboGroupBy);
			code.setOrderBy(comboorderBy);
			code.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			code.setComboType(codeKnd.length > 3 ? codeKnd[3].toUpperCase():"N");

			jsonArr = commonManager.getDataCombo(code);
			jso.put(originalCodeKnd[0], jsonArr);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessCard/doSearchToUserManagementByUserInfo.do")
	public ModelAndView doSearchToUserManagementByUserInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

//		CgabGascz002Dto userDto = (CgabGascz002Dto) getJsonToBean(paramJson, CgabGascz002Dto.class);
//		CgabGascz002Dto userInfo = commonManager.getSelectUserInfoDetail(userDto);

		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName(DATA_JSON_PAGE);
//		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userInfo).toString());
//		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * business Card submit search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doSearchToSubmit.do")
	public ModelAndView doSearchToSubmit(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		BgabGascba01 rsReqDto = new BgabGascba01();
		rsReqDto = businessCardManager.getSelectInfoBCToSubmit(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(keyParamDto.getIf_id());
			commonApproval.setSystem_code("BC");
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

	/*************************************************************/
	/** Card Type management page                                             */
	/*************************************************************/
	@RequestMapping(value="/hncis/businessCard/doSearchGridBcToCardTypeManagement.do")
	public ModelAndView doSearchGridBcToCardTypeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascba03 dto = (BgabGascba03) getJsonToBean(paramJson, BgabGascba03.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = businessCardManager.getSelectCountBcToCardTypeManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(businessCardManager.getSelectListBcToCardTypeManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * Card type management insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessCard/doInsertBcToCardTypeManagement.do")
	public ModelAndView doInsertBcToCardTypeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascba03> list = (List<BgabGascba03>) getJsonToList(paramJson, BgabGascba03.class);

		int cnt = businessCardManager.insertListBcToCardTypeManagement(list);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * Card type management update
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessCard/doModifyBcToCardTypeManagement")
	public ModelAndView doModifyBcToCardTypeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascba03> list = (List<BgabGascba03>) getJsonToList(paramJson, BgabGascba03.class);

		int cnt = businessCardManager.updateListBcToCardTypeManagement(list);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * Card type management delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessCard/doDeleteBcToCardTypeManagement")
	public ModelAndView doDeleteBcToCardTypeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascba03> list = (List<BgabGascba03>) getJsonToList(paramJson, BgabGascba03.class);

		int cnt = businessCardManager.deleteListBcToCardTypeManagement(list);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

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
	@RequestMapping(value="/hncis/businessCard/doExcelToAccept.excel")
	public ModelAndView doExcelToAccept(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = businessCardManager.getSelectCountBCToAccept(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(count);
		list.setRows(businessCardManager.getSelectListBCToAccept(keyParamDto));

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
	/** accept page                                              */
	/*************************************************************/
	/**
	 * business Card accept search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessCard/doSearchGridToConfirm.do")
	public ModelAndView doSearchGridToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessCardManager.getSelectCountBCToConfirm(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(businessCardManager.getSelectListBCToConfirm(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
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
	@RequestMapping(value="/hncis/businessCard/doExcelToConfirm.excel")
	public ModelAndView doExcelToConfirm(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascba02 keyParamDto = (BgabGascba02) getJsonToBean(paramJson, BgabGascba02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = businessCardManager.getSelectCountBCToConfirm(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(businessCardManager.getSelectListBCToConfirm(keyParamDto));

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
}
