package com.hncis.controller.generalService;

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

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.controller.AbstractController;
import com.hncis.generalService.manager.GeneralServiceManager;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs02;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.generalService.vo.BgabGascgsDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class generalServiceController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
    @Qualifier("generalServiceManagerImpl")
	private GeneralServiceManager generalServiceManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/getGeneralServiceCombo.do")
	public ModelAndView getGeneralServiceCombo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codknd) throws HncisException, SessionException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;
		List<CommonCode> codeList = null;
		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");
			if( codePair.length > 1){
				CommonCode code = new CommonCode();
				jsonArr = new  JSONBaseArray();
				code.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				code.setCodknd(codePair[1]);
				code.setComboType(codePair[3]);
				if( codePair.length > 4){
					code.setCode(codePair[4]);
				}
				codeList = generalServiceManager.getGeneralServiceCombo(code);
				if(codePair.length > 4){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", HncisMessageSource.getMessage("select"));
						}else{
							json.put("name", HncisMessageSource.getMessage("total"));
						}
						json.put("value", "");

						jsonArr.add(json);
					}
				}

				for(CommonCode targetBean : codeList){
					json = new JSONBaseVO();
					json.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
					json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * requset - search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByRequestInfo.do")
	public ModelAndView doSearchByRequestInfo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascgs01 gsParamVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);

		BgabGascgs01 rsReqDto = new BgabGascgs01();
		rsReqDto = generalServiceManager.doSearchByRequestInfo(gsParamVo);
		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/generalService/doSearchByRequestList.do")
	public ModelAndView doSearchByRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascgs03 gsParamVo = (BgabGascgs03) getJsonToBean(paramJson, BgabGascgs03.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsParamVo.setSess_eeno("");
		}else{
//			BgabGascgs04 gs04Vo = new BgabGascgs04();
//			gs04Vo.setEeno(SessionInfo.getSess_empno(req));
//			String isManger = generalServiceManager.getSelectByXgsIsManager(gs04Vo);
//			if("Y".equals(isManger)){
//				gsParamVo.setSess_eeno(SessionInfo.getSess_empno(req));
//			}else{
				gsParamVo.setSess_eeno("");
//			}
		}

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.doSearchByRequestList(gsParamVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * requset - insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByRequest.do")
	public ModelAndView doInsertByRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsV", required=false) String paramsV,
		@RequestParam(value="paramsI", required=false) String paramsI,
		@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException, SessionException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascgs01 gsSaveVo = (BgabGascgs01) getJsonToBean(paramsV, BgabGascgs01.class);
		List<BgabGascgs03> gsSaveList = (List<BgabGascgs03>) getJsonToList(paramsI, BgabGascgs03.class);
		List<BgabGascgs03> gsModifyList = (List<BgabGascgs03>) getJsonToList(paramsU, BgabGascgs03.class);

		String docNo = "";
		if("I".equals(gsSaveVo.getBasic_mode())){
			docNo = StringUtil.getDocNo();
//			if("".equals(gsSaveVo.getDoc_no())){
				gsSaveVo.setDoc_no(docNo);
//			}else{
//				docNo = gsSaveVo.getDoc_no();
//			}
			gsSaveVo.setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.setUpdr_eeno(SessionInfo.getSess_empno(req));
		}else{
			docNo = gsSaveVo.getDoc_no();
		}

		for(int i=0; i<gsSaveList.size(); i++){
			gsSaveList.get(i).setDoc_no(docNo);
			gsSaveList.get(i).setPgs_st_cd("0");
			gsSaveList.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		for(int i=0; i<gsModifyList.size(); i++){
			gsModifyList.get(i).setDoc_no(docNo);
			gsModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		generalServiceManager.doInsertByRequest(gsSaveVo, gsSaveList, gsModifyList);

		CommonMessage message = new CommonMessage();
		if("I".equals(gsSaveVo.getBasic_mode())){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("MODIFY.0000"));
		}
		message.setCode(docNo);

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
	 * requset - delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByRequest.do")
	public ModelAndView doDeleteByRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramV", required=true) String paramV,
		@RequestParam(value="paramD", required=true) String paramD) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		BgabGascgs01 gsDelVo = (BgabGascgs01) getJsonToBean(paramV, BgabGascgs01.class);
		List<BgabGascgs03> BgabGascgs03 = (List<BgabGascgs03>) getJsonToList(paramD, BgabGascgs03.class);

		generalServiceManager.doDeleteByRequest(gsDelVo);
		for(BgabGascgs03 delVo : BgabGascgs03){
			generalServiceManager.doDeleteByRequestList(delVo);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	@RequestMapping(value="/hncis/generalService/doDeleteByRequestList.do")
	public ModelAndView doDeleteByRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascgs03 gsDelVo = (BgabGascgs03) getJsonToBean(paramJson, BgabGascgs03.class);

		generalServiceManager.doDeleteByRequestList(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	/**
	 * requset - request
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByRequest.do")
	public ModelAndView doUpdateByRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascgs03> gsReqList = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		for(BgabGascgs03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			generalServiceManager.doUpdateByRequest(gsVo03);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));

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
	 * requset - request cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByRequestCancel.do")
	public ModelAndView doUpdateByRequestCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		

		List<BgabGascgs03> gsReqList = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		for(BgabGascgs03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			generalServiceManager.doUpdateByRequestCancel(gsVo03);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));

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
	 * requset - confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByConfirm.do")
	public ModelAndView doUpdateByConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascgs03> gsReqList = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		String corpCd = SessionInfo.getSess_corp_cd(req);

		for(BgabGascgs03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			generalServiceManager.doUpdateByConfirm(gsVo03);

			//send email
			List<BgabGascgs03> rsList = generalServiceManager.doSearchByRequestList(gsVo03);
			for(BgabGascgs03 rsVo : rsList){
				BgabGascz002Dto userDto = new BgabGascz002Dto();
				userDto.setXusr_empno(rsVo.getIpe_eeno());
				userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
				userDto.setCorp_cd(corpCd);
				BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);

				String fromEenm   = SessionInfo.getSess_name(req);
				String fromStepNm = SessionInfo.getSess_step_name(req);
				String toEeno     = userDetailInfo.getXusr_mail_adr();
				String mode       = "confirm";
				String title      = gsVo03.getTitle();
				String comment    = rsVo.getComment();
				Submit.confirmEmail(fromEenm, fromStepNm, toEeno, title);
//				Submit.sendEmailGeneralServiceForConfirm(fromEenm, fromStepNm, toEeno, mode, title, comment);
			}
		}


		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));

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
	 * requset - confirm cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByConfirmCancel.do")
	public ModelAndView doUpdateByConfirmCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		List<BgabGascgs03> gsReqList = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		for(BgabGascgs03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			generalServiceManager.doUpdateByConfirmCancel(gsVo03);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0002"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByXgs02.do")
	public ModelAndView doSearchByXgs02(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs01 gsReqVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
		}else{
//			BgabGascgs04 gs04Vo = new BgabGascgs04();
//			gs04Vo.setEeno(SessionInfo.getSess_empno(req));
//			String isManger = generalServiceManager.getSelectByXgsIsManager(gs04Vo);
//			if("Y".equals(isManger)){
//				gsReqVo.setSess_eeno(SessionInfo.getSess_empno(req));
//			}else{
				gsReqVo.setSess_eeno("");
//			}
		}

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = generalServiceManager.getSelectByXgs02ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(generalServiceManager.getSelectByXgs02List(gsReqVo));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	/**
	 * list - insert (Not use)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByXgs07Request.do")
	public ModelAndView doInsertByXgs07Request(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsV", required=false) String paramsV,
		@RequestParam(value="paramsI", required=false) String paramsI,
		@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException, SessionException{

		List<BgabGascgs03> gsSaveList = (List<BgabGascgs03>) getJsonToList(paramsI, BgabGascgs03.class);
		List<BgabGascgs03> gsModifyList = (List<BgabGascgs03>) getJsonToList(paramsU, BgabGascgs03.class);

		for(BgabGascgs03 gs03Vo : gsSaveList){
			String docNo = StringUtil.getDocNo();
			gs03Vo.setDoc_no(docNo);
			gs03Vo.setPgs_st_cd("0");
			gs03Vo.setIpe_eeno(SessionInfo.getSess_empno(req));
			gs03Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));

			BgabGascgs01 gs01Vo = new BgabGascgs01();
			gs01Vo.setDoc_no(docNo);
			gs01Vo.setEeno(SessionInfo.getSess_empno(req));
			gs01Vo.setDept_cd(SessionInfo.getSess_dept_code(req));
			gs01Vo.setPtt_ymd(CurrentDateTime.getDate());

			generalServiceManager.doInsertByXgs07Basic(gs01Vo);
			generalServiceManager.doInsertByXgs07List(gs03Vo);
		}

		for(BgabGascgs03 gsMdvo : gsModifyList){
			gsMdvo.setUpdr_eeno(SessionInfo.getSess_empno(req));
			generalServiceManager.doUpdateByXgs07List(gsMdvo);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * list - request (Not use)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByXgs07Request.do")
	public ModelAndView doUpdateByXgs07Request(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		

		List<BgabGascgs03> gsListVo = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		for(BgabGascgs03 gsReqVo : gsListVo){
			generalServiceManager.doUpdateByRequest(gsReqVo);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));

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
	 * list - request cancel (Not use)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doUpdateByXgs07RequestCancel.do")
	public ModelAndView doUpdateByXgs07RequestCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascgs03> gsListVo = (List<BgabGascgs03>) getJsonToList(paramJson, BgabGascgs03.class);

		for(BgabGascgs03 gsReqVo : gsListVo){
			generalServiceManager.doUpdateByRequestCancel(gsReqVo);
		}

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));

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
	 * confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByXgs03.do")
	public ModelAndView doSearchByXgs03(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascgs01 gsReqVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);

//		String master = SessionInfo.getSess_mstu_gubb(req);
//		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
//		}else{
//			gsReqVo.setSess_eeno(SessionInfo.getSess_empno(req));
//		}

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = generalServiceManager.getSelectByXgs03ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(generalServiceManager.getSelectByXgs03List(gsReqVo));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * generalService manager yn
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByXgs03Manager.do")
	public ModelAndView doSearchByXgs03Manager(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException, SessionException{


		BgabGascgs04 gs04Vo = new BgabGascgs04();
		gs04Vo.setEeno(SessionInfo.getSess_empno(req));
		gs04Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		String isManger = generalServiceManager.getSelectByXgsIsManager(gs04Vo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		message.setCode(isManger);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * total list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByXgs04.do")
	public ModelAndView doSearchByXgs04(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgsDto gsReqVo = (BgabGascgsDto) getJsonToBean(paramJson, BgabGascgsDto.class);

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSelectByXgs04List(gsReqVo));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * production mgmt - production
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByGeneralService1.do")
	public ModelAndView doSearchByGeneralService1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs02 gsReqDto = (BgabGascgs02) getJsonToBean(paramJson, BgabGascgs02.class);
		gsReqDto.setCatg_1("");
		gsReqDto.setCatg_2("PD");

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSearchByGeneralService(gsReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * production mgmt - category 1
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByGeneralService2.do")
	public ModelAndView doSearchByGeneralService2(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs02 gsReqDto = (BgabGascgs02) getJsonToBean(paramJson, BgabGascgs02.class);
		gsReqDto.setCatg_2("S1");

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSearchByGeneralService2(gsReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * production mgmt - category 2
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByGeneralService3.do")
	public ModelAndView doSearchByGeneralService3(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs02 gsReqDto = (BgabGascgs02) getJsonToBean(paramJson, BgabGascgs02.class);
		gsReqDto.setCatg_2("S2");

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSearchByGeneralService3(gsReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * production mgmt - category 2
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByGeneralService3FileName.do")
	public ModelAndView doSearchByGeneralService3FileName(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs02 gsReqDto = (BgabGascgs02) getJsonToBean(paramJson, BgabGascgs02.class);
		gsReqDto.setCatg_2("S2");

		BgabGascgs02 rtnVo = generalServiceManager.selectByGeneralService3FileName(gsReqDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnVo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 3
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByGeneralService4.do")
	public ModelAndView doSearchByGeneralService4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs02 gsReqDto = (BgabGascgs02) getJsonToBean(paramJson, BgabGascgs02.class);
		gsReqDto.setCatg_2("S3");

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSearchByGeneralService4(gsReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * production mgmt - production
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByGeneralService1.do")
	public ModelAndView doInsertByGeneralService1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String gridGubun,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascgs02> gsSaveVo = (List<BgabGascgs02>) getJsonToList(saveInfo, BgabGascgs02.class);
		List<BgabGascgs02> gsModifyVo = (List<BgabGascgs02>) getJsonToList(modifyInfo, BgabGascgs02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("PD");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("PD");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = generalServiceManager.doInsertByGeneralService(gsSaveVo, gsModifyVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 1
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByGeneralService2.do")
	public ModelAndView doInsertByGeneralService2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascgs02> gsSaveVo = (List<BgabGascgs02>) getJsonToList(saveInfo, BgabGascgs02.class);
		List<BgabGascgs02> gsModifyVo = (List<BgabGascgs02>) getJsonToList(modifyInfo, BgabGascgs02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S1");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S1");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = generalServiceManager.doInsertByGeneralService(gsSaveVo, gsModifyVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 2
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByGeneralService3.do")
	public ModelAndView doInsertByGeneralService3(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascgs02> gsSaveVo = (List<BgabGascgs02>) getJsonToList(saveInfo, BgabGascgs02.class);
		List<BgabGascgs02> gsModifyVo = (List<BgabGascgs02>) getJsonToList(modifyInfo, BgabGascgs02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S2");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S2");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = generalServiceManager.doInsertByGeneralService(gsSaveVo, gsModifyVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 3
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByGeneralService4.do")
	public ModelAndView doInsertByGeneralService4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=false) String saveInfo,
			@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascgs02> gsSaveVo = (List<BgabGascgs02>) getJsonToList(saveInfo, BgabGascgs02.class);
		List<BgabGascgs02> gsModifyVo = (List<BgabGascgs02>) getJsonToList(modifyInfo, BgabGascgs02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S3");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S3");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = generalServiceManager.doInsertByGeneralService(gsSaveVo, gsModifyVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - production
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByGeneralService1.do")
	public ModelAndView doDeleteByGeneralService1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascgs02> gsDelVo = (List<BgabGascgs02>) getJsonToList(paramJson, BgabGascgs02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("PD");
		}

		generalServiceManager.doDeleteByGeneralService(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 1
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByGeneralService2.do")
	public ModelAndView doDeleteByGeneralService2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascgs02> gsDelVo = (List<BgabGascgs02>) getJsonToList(paramJson, BgabGascgs02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S1");
		}

		generalServiceManager.doDeleteByGeneralService(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 2
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByGeneralService3.do")
	public ModelAndView doDeleteByGeneralService3(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascgs02> gsDelVo = (List<BgabGascgs02>) getJsonToList(paramJson, BgabGascgs02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S2");
		}

		generalServiceManager.doDeleteByGeneralService(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * production mgmt - category 3
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByGeneralService4.do")
	public ModelAndView doDeleteByGeneralService4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascgs02> gsDelVo = (List<BgabGascgs02>) getJsonToList(paramJson, BgabGascgs02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S3");
		}

		generalServiceManager.doDeleteByGeneralService(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * manager mgmt
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/generalService/doSearchByXgs06.do")
	public ModelAndView doSearchByXgs06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs04 gsReqVo = (BgabGascgs04) getJsonToBean(paramJson, BgabGascgs04.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = generalServiceManager.getSelectByXgs06ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(generalServiceManager.getSelectByXgs06List(gsReqVo));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * manager mgmt Insert/Update
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doInsertByXgs06.do")
	public ModelAndView doInsertByXgs06(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String gridGubun,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascgs04> gsSaveVo = (List<BgabGascgs04>) getJsonToList(saveInfo, BgabGascgs04.class);
		List<BgabGascgs04> gsModifyVo = (List<BgabGascgs04>) getJsonToList(modifyInfo, BgabGascgs04.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		generalServiceManager.doInsertByXgs06(gsSaveVo, gsModifyVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * manager mgmt Delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteByXgs06.do")
	public ModelAndView doDeleteByXgs06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascgs04> gsDelVo = (List<BgabGascgs04>) getJsonToList(paramJson, BgabGascgs04.class);

		generalServiceManager.doDeleteByXgs06(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * confirm excel
	 * @param req
	 * @param res
	 * @param fileName
	 * @param header
	 * @param headerName
	 * @param fomatter
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/generalService/doSelectByXgs03ListExcel.excel")
	public ModelAndView doSelectByXgs03ListExcel(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascgs01 gsReqVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);
//		String master = SessionInfo.getSess_mstu_gubb(req);
//		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
//		}else{
//			gsReqVo.setSess_eeno(SessionInfo.getSess_empno(req));
//		}
		gsReqVo.setStartRow(0);

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSelectByXgs03ExcelList(gsReqVo));
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
	 * total list excel
	 * @param req
	 * @param res
	 * @param fileName
	 * @param header
	 * @param headerName
	 * @param fomatter
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/generalService/doSelectByXgs04ListExcel.excel")
	public ModelAndView doSelectByXgs04ListExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascgsDto gsReqVo = (BgabGascgsDto) getJsonToBean(paramJson, BgabGascgsDto.class);

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSelectByXgs04List(gsReqVo));

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

	@RequestMapping(value="/hncis/generalService/doSearchPoCreate.do")
	public ModelAndView doSearchPoCreate(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		RfcPoCreateVo poParamVo = (RfcPoCreateVo) getJsonToBean(paramJson, RfcPoCreateVo.class);

		RfcPoCreateVo rsReqDto = new RfcPoCreateVo();
		rsReqDto = generalServiceManager.doSearchPoCreate(poParamVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/generalService/doSearchPoDelete.do")
	public ModelAndView doSearchPoDelete(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		RfcPoCreateVo poParamVo = (RfcPoCreateVo) getJsonToBean(paramJson, RfcPoCreateVo.class);

		RfcPoCreateVo rsReqDto = new RfcPoCreateVo();
		rsReqDto = generalServiceManager.doSearchPoDelete(poParamVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	/**
	 * manager mgmt Delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doRejectByXgs03.do")
	public ModelAndView doRejectByXgs03(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams", required=false) String uParams) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascgs03> gsDelVo = (List<BgabGascgs03>) getJsonToList(uParams, BgabGascgs03.class);

		generalServiceManager.updateByXgs03Reject(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));

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
	 * GeneralService Service file save
	 */
	@RequestMapping(value="/hncis/generalService/doSaveGeneralServiceToImgFile.do")
	public void doSaveGeneralServiceToImgFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			generalServiceManager.saveGeneralServiceToImgFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * GeneralService Service file save
	 */
	@RequestMapping(value="/hncis/generalService/doSearchGeneralServiceToFile.do")
	public ModelAndView doSearchGeneralServiceToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.getSelectGeneralServiceToFile(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * GeneralService Service file save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doGeneralServiceFileDown.do")
	public ModelAndView doGeneralServiceFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = generalServiceManager.getSelectGeneralServiceToFileInfo(dto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "generalService");

		return new ModelAndView("imgDownload", "fileData", mpfileData);
	}

	/**
	 * GeneralService Service file save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/generalService/doDeleteGeneralServiceToFile.do")
	public ModelAndView doDeleteGeneralServiceToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		generalServiceManager.deleteGeneralServiceToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
}