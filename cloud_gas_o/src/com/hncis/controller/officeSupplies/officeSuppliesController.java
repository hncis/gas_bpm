package com.hncis.controller.officeSupplies;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
//import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.controller.AbstractController;
import com.hncis.officeSupplies.manager.OfficeSuppliesManager;
import com.hncis.officeSupplies.vo.BgabGascos01;
import com.hncis.officeSupplies.vo.BgabGascos02;
import com.hncis.officeSupplies.vo.BgabGascos03;
import com.hncis.officeSupplies.vo.BgabGascos04;
import com.hncis.officeSupplies.vo.BgabGascosDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class officeSuppliesController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
    @Qualifier("officeSuppliesManagerImpl")
	private OfficeSuppliesManager officeSuppliesManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/officeSupplies/getOfficeCombo.do")
	public ModelAndView getOfficeCombo(HttpServletRequest req, HttpServletResponse res,
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
				code.setCodknd(codePair[1]);
				code.setComboType(codePair[3]);
				code.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				if( codePair.length > 4){
					code.setCode(codePair[4]);
				}
				codeList = officeSuppliesManager.getOfficeCombo(code);
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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByRequestInfo.do")
	public ModelAndView doSearchByRequestInfo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascos01 gsParamVo = (BgabGascos01) getJsonToBean(paramJson, BgabGascos01.class);

		BgabGascos01 rsReqDto = new BgabGascos01();
		rsReqDto = officeSuppliesManager.doSearchByRequestInfo(gsParamVo);
		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/officeSupplies/doSearchByRequestList.do")
	public ModelAndView doSearchByRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascos03 gsParamVo = (BgabGascos03) getJsonToBean(paramJson, BgabGascos03.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsParamVo.setSess_eeno("");
		}else{
//			BgabGascos04 gs04Vo = new BgabGascos04();
//			gs04Vo.setEeno(SessionInfo.getSess_empno(req));
//			String isManger = officeSuppliesManager.getSelectByXosIsManager(gs04Vo);
//			if("Y".equals(isManger)){
//				gsParamVo.setSess_eeno(SessionInfo.getSess_empno(req));
//			}else{
				gsParamVo.setSess_eeno("");
//			}
		}

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.doSearchByRequestList(gsParamVo));

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByRequest.do")
	public ModelAndView doInsertByRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsV", required=false) String paramsV,
		@RequestParam(value="paramsI", required=false) String paramsI,
		@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException, SessionException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascos01 gsSaveVo = (BgabGascos01) getJsonToBean(paramsV, BgabGascos01.class);
		List<BgabGascos03> gsSaveList = (List<BgabGascos03>) getJsonToList(paramsI, BgabGascos03.class);
		List<BgabGascos03> gsModifyList = (List<BgabGascos03>) getJsonToList(paramsU, BgabGascos03.class);

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

		officeSuppliesManager.doInsertByRequest(gsSaveVo, gsSaveList, gsModifyList);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByRequest.do")
	public ModelAndView doDeleteByRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramV", required=true) String paramV,
		@RequestParam(value="paramD", required=true) String paramD) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		BgabGascos01 gsDelVo = (BgabGascos01) getJsonToBean(paramV, BgabGascos01.class);
		List<BgabGascos03> BgabGascos03 = (List<BgabGascos03>) getJsonToList(paramD, BgabGascos03.class);

		officeSuppliesManager.doDeleteByRequest(gsDelVo);
		for(BgabGascos03 delVo : BgabGascos03){
			officeSuppliesManager.doDeleteByRequestList(delVo);
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

	@RequestMapping(value="/hncis/officeSupplies/doDeleteByRequestList.do")
	public ModelAndView doDeleteByRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascos03 gsDelVo = (BgabGascos03) getJsonToBean(paramJson, BgabGascos03.class);

		officeSuppliesManager.doDeleteByRequestList(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByRequest.do")
	public ModelAndView doUpdateByRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascos03> gsReqList = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		for(BgabGascos03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			officeSuppliesManager.doUpdateByRequest(gsVo03);
		}
		
		// BMP API호출
		String processCode = "P-C-003"; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
		String bizKey = gsReqList.get(0).getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01330010";	//액티비티 코드 (사무용품신청서작성) - 프로세스 정의서 참조
		String loginUserId = gsReqList.get(0).getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01330030";   //사무용품 담당자 역할코드

		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");

		BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
	

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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByRequestCancel.do")
	public ModelAndView doUpdateByRequestCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascos03> gsReqList = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		for(BgabGascos03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			officeSuppliesManager.doUpdateByRequestCancel(gsVo03);
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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByConfirm.do")
	public ModelAndView doUpdateByConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		List<BgabGascos03> gsReqList = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		String corpCd = SessionInfo.getSess_corp_cd(req);

		for(BgabGascos03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			officeSuppliesManager.doUpdateByConfirm(gsVo03);

			//send email
			List<BgabGascos03> rsList = officeSuppliesManager.doSearchByRequestList(gsVo03);
			for(BgabGascos03 rsVo : rsList){
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
//				Submit.confirmEmail(fromEenm, fromStepNm, toEeno, title);
//				Submit.sendEmailOfficeSuppliesForConfirm(fromEenm, fromStepNm, toEeno, mode, title, comment);
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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByConfirmCancel.do")
	public ModelAndView doUpdateByConfirmCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		List<BgabGascos03> gsReqList = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		for(BgabGascos03 gsVo03 : gsReqList){
			gsVo03.setUpdr_eeno(SessionInfo.getSess_empno(req));
			officeSuppliesManager.doUpdateByConfirmCancel(gsVo03);
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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByXos02.do")
	public ModelAndView doSearchByXos02(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos01 gsReqVo = (BgabGascos01) getJsonToBean(paramJson, BgabGascos01.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
		}else{
//			BgabGascos04 gs04Vo = new BgabGascos04();
//			gs04Vo.setEeno(SessionInfo.getSess_empno(req));
//			String isManger = officeSuppliesManager.getSelectByXosIsManager(gs04Vo);
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
		int count       = officeSuppliesManager.getSelectByXos02ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(officeSuppliesManager.getSelectByXos02List(gsReqVo));

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByXos07Request.do")
	public ModelAndView doInsertByXos07Request(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsV", required=false) String paramsV,
		@RequestParam(value="paramsI", required=false) String paramsI,
		@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException, SessionException{

		List<BgabGascos03> gsSaveList = (List<BgabGascos03>) getJsonToList(paramsI, BgabGascos03.class);
		List<BgabGascos03> gsModifyList = (List<BgabGascos03>) getJsonToList(paramsU, BgabGascos03.class);

		for(BgabGascos03 gs03Vo : gsSaveList){
			String docNo = StringUtil.getDocNo();
			gs03Vo.setDoc_no(docNo);
			gs03Vo.setPgs_st_cd("0");
			gs03Vo.setIpe_eeno(SessionInfo.getSess_empno(req));
			gs03Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));

			BgabGascos01 gs01Vo = new BgabGascos01();
			gs01Vo.setDoc_no(docNo);
			gs01Vo.setEeno(SessionInfo.getSess_empno(req));
			gs01Vo.setDept_cd(SessionInfo.getSess_dept_code(req));
			gs01Vo.setPtt_ymd(CurrentDateTime.getDate());

			officeSuppliesManager.doInsertByXos07Basic(gs01Vo);
			officeSuppliesManager.doInsertByXos07List(gs03Vo);
		}

		for(BgabGascos03 gsMdvo : gsModifyList){
			gsMdvo.setUpdr_eeno(SessionInfo.getSess_empno(req));
			officeSuppliesManager.doUpdateByXos07List(gsMdvo);
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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByXos07Request.do")
	public ModelAndView doUpdateByXos07Request(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascos03> gsListVo = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		for(BgabGascos03 gsReqVo : gsListVo){
			officeSuppliesManager.doUpdateByRequest(gsReqVo);
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
	@RequestMapping(value="/hncis/officeSupplies/doUpdateByXos07RequestCancel.do")
	public ModelAndView doUpdateByXos07RequestCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		List<BgabGascos03> gsListVo = (List<BgabGascos03>) getJsonToList(paramJson, BgabGascos03.class);

		for(BgabGascos03 gsReqVo : gsListVo){
			officeSuppliesManager.doUpdateByRequestCancel(gsReqVo);
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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByXos03.do")
	public ModelAndView doSearchByXos03(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascos01 gsReqVo = (BgabGascos01) getJsonToBean(paramJson, BgabGascos01.class);

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
		int count       = officeSuppliesManager.getSelectByXos03ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(officeSuppliesManager.getSelectByXos03List(gsReqVo));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * officeSupplies manager yn
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/officeSupplies/doSearchByXos03Manager.do")
	public ModelAndView doSearchByXos03Manager(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException, SessionException{


		BgabGascos04 gs04Vo = new BgabGascos04();
		gs04Vo.setEeno(SessionInfo.getSess_empno(req));
		gs04Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		String isManger = officeSuppliesManager.getSelectByXosIsManager(gs04Vo);

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByXos04.do")
	public ModelAndView doSearchByXos04(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascosDto gsReqVo = (BgabGascosDto) getJsonToBean(paramJson, BgabGascosDto.class);

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSelectByXos04List(gsReqVo));

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByOffice1.do")
	public ModelAndView doSearchByOffice1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos02 gsReqDto = (BgabGascos02) getJsonToBean(paramJson, BgabGascos02.class);
		gsReqDto.setCatg_1("");
		gsReqDto.setCatg_2("PD");

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSearchByOffice(gsReqDto));

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByOffice2.do")
	public ModelAndView doSearchByOffice2(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos02 gsReqDto = (BgabGascos02) getJsonToBean(paramJson, BgabGascos02.class);
		gsReqDto.setCatg_2("S1");

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSearchByOffice2(gsReqDto));

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByOffice3.do")
	public ModelAndView doSearchByOffice3(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos02 gsReqDto = (BgabGascos02) getJsonToBean(paramJson, BgabGascos02.class);
		gsReqDto.setCatg_2("S2");

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSearchByOffice3(gsReqDto));

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByOffice3FileName.do")
	public ModelAndView doSearchByOffice3FileName(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos02 gsReqDto = (BgabGascos02) getJsonToBean(paramJson, BgabGascos02.class);
		gsReqDto.setCatg_2("S2");

		BgabGascos02 rtnVo = officeSuppliesManager.selectByOffice3FileName(gsReqDto);

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByOffice4.do")
	public ModelAndView doSearchByOffice4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos02 gsReqDto = (BgabGascos02) getJsonToBean(paramJson, BgabGascos02.class);
		gsReqDto.setCatg_2("S3");

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSearchByOffice4(gsReqDto));

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByOffice1.do")
	public ModelAndView doInsertByOffice1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String gridGubun,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascos02> gsSaveVo = (List<BgabGascos02>) getJsonToList(saveInfo, BgabGascos02.class);
		List<BgabGascos02> gsModifyVo = (List<BgabGascos02>) getJsonToList(modifyInfo, BgabGascos02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("PD");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("PD");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = officeSuppliesManager.doInsertByOffice(gsSaveVo, gsModifyVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByOffice2.do")
	public ModelAndView doInsertByOffice2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascos02> gsSaveVo = (List<BgabGascos02>) getJsonToList(saveInfo, BgabGascos02.class);
		List<BgabGascos02> gsModifyVo = (List<BgabGascos02>) getJsonToList(modifyInfo, BgabGascos02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S1");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S1");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = officeSuppliesManager.doInsertByOffice(gsSaveVo, gsModifyVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByOffice3.do")
	public ModelAndView doInsertByOffice3(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascos02> gsSaveVo = (List<BgabGascos02>) getJsonToList(saveInfo, BgabGascos02.class);
		List<BgabGascos02> gsModifyVo = (List<BgabGascos02>) getJsonToList(modifyInfo, BgabGascos02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S2");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S2");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = officeSuppliesManager.doInsertByOffice(gsSaveVo, gsModifyVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByOffice4.do")
	public ModelAndView doInsertByOffice4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=false) String saveInfo,
			@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascos02> gsSaveVo = (List<BgabGascos02>) getJsonToList(saveInfo, BgabGascos02.class);
		List<BgabGascos02> gsModifyVo = (List<BgabGascos02>) getJsonToList(modifyInfo, BgabGascos02.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setCatg_2("S3");
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setCatg_2("S3");
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		CommonMessage message = officeSuppliesManager.doInsertByOffice(gsSaveVo, gsModifyVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByOffice1.do")
	public ModelAndView doDeleteByOffice1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascos02> gsDelVo = (List<BgabGascos02>) getJsonToList(paramJson, BgabGascos02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("PD");
		}

		officeSuppliesManager.doDeleteByOffice(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByOffice2.do")
	public ModelAndView doDeleteByOffice2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascos02> gsDelVo = (List<BgabGascos02>) getJsonToList(paramJson, BgabGascos02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S1");
		}

		officeSuppliesManager.doDeleteByOffice(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByOffice3.do")
	public ModelAndView doDeleteByOffice3(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascos02> gsDelVo = (List<BgabGascos02>) getJsonToList(paramJson, BgabGascos02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S2");
		}

		officeSuppliesManager.doDeleteByOffice(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByOffice4.do")
	public ModelAndView doDeleteByOffice4(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascos02> gsDelVo = (List<BgabGascos02>) getJsonToList(paramJson, BgabGascos02.class);
		for(int i=0; i<gsDelVo.size(); i++){
			gsDelVo.get(i).setCatg_2("S3");
		}

		officeSuppliesManager.doDeleteByOffice(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doSearchByXos06.do")
	public ModelAndView doSearchByXos06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascos04 gsReqVo = (BgabGascos04) getJsonToBean(paramJson, BgabGascos04.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = officeSuppliesManager.getSelectByXos06ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(officeSuppliesManager.getSelectByXos06List(gsReqVo));

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
	@RequestMapping(value="/hncis/officeSupplies/doInsertByXos06.do")
	public ModelAndView doInsertByXos06(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String gridGubun,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascos04> gsSaveVo = (List<BgabGascos04>) getJsonToList(saveInfo, BgabGascos04.class);
		List<BgabGascos04> gsModifyVo = (List<BgabGascos04>) getJsonToList(modifyInfo, BgabGascos04.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		officeSuppliesManager.doInsertByXos06(gsSaveVo, gsModifyVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doDeleteByXos06.do")
	public ModelAndView doDeleteByXos06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascos04> gsDelVo = (List<BgabGascos04>) getJsonToList(paramJson, BgabGascos04.class);

		officeSuppliesManager.doDeleteByXos06(gsDelVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doSelectByXos03ListExcel.excel")
	public ModelAndView doSelectByXos03ListExcel(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascos01 gsReqVo = (BgabGascos01) getJsonToBean(paramJson, BgabGascos01.class);
//		String master = SessionInfo.getSess_mstu_gubb(req);
//		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
//		}else{
//			gsReqVo.setSess_eeno(SessionInfo.getSess_empno(req));
//		}
		gsReqVo.setStartRow(0);

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSelectByXos03ExcelList(gsReqVo));
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
	@RequestMapping(value="/hncis/officeSupplies/doSelectByXos04ListExcel.excel")
	public ModelAndView doSelectByXos04ListExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascosDto gsReqVo = (BgabGascosDto) getJsonToBean(paramJson, BgabGascosDto.class);

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSelectByXos04List(gsReqVo));

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

	@RequestMapping(value="/hncis/officeSupplies/doSearchPoCreate.do")
	public ModelAndView doSearchPoCreate(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		RfcPoCreateVo poParamVo = (RfcPoCreateVo) getJsonToBean(paramJson, RfcPoCreateVo.class);

		RfcPoCreateVo rsReqDto = new RfcPoCreateVo();
		rsReqDto = officeSuppliesManager.doSearchPoCreate(poParamVo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/officeSupplies/doSearchPoDelete.do")
	public ModelAndView doSearchPoDelete(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		RfcPoCreateVo poParamVo = (RfcPoCreateVo) getJsonToBean(paramJson, RfcPoCreateVo.class);

		RfcPoCreateVo rsReqDto = new RfcPoCreateVo();
		rsReqDto = officeSuppliesManager.doSearchPoDelete(poParamVo);

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
	@RequestMapping(value="/hncis/officeSupplies/doRejectByXos03.do")
	public ModelAndView doRejectByXos03(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams", required=false) String uParams) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		List<BgabGascos03> gsDelVo = (List<BgabGascos03>) getJsonToList(uParams, BgabGascos03.class);

		officeSuppliesManager.updateByXos03Reject(gsDelVo);

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
	 * OfficeSupplies Service file save
	 */
	@RequestMapping(value="/hncis/officeSupplies/doSaveOfficeSuppliesToImgFile.do")
	public void doSaveOfficeSuppliesToImgFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			officeSuppliesManager.saveOfficeSuppliesToImgFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * OfficeSupplies Service file save
	 */
	@RequestMapping(value="/hncis/officeSupplies/doSearchOfficeSuppliesToFile.do")
	public ModelAndView doSearchOfficeSuppliesToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(officeSuppliesManager.getSelectOfficeSuppliesToFile(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * OfficeSupplies Service file save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/officeSupplies/doOfficeSuppliesFileDown.do")
	public ModelAndView doOfficeSuppliesFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = officeSuppliesManager.getSelectOfficeSuppliesToFileInfo(dto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "officeSupplies");

		return new ModelAndView("imgDownload", "fileData", mpfileData);
	}

	/**
	 * OfficeSupplies Service file save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/officeSupplies/doDeleteOfficeSuppliesToFile.do")
	public ModelAndView doDeleteOfficeSuppliesToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		officeSuppliesManager.deleteOfficeSuppliesToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
}