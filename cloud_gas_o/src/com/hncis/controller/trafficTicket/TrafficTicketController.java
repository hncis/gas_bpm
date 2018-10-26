package com.hncis.controller.trafficTicket;

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
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.trafficTicket.manager.TrafficTicketManager;
import com.hncis.trafficTicket.vo.BgabGascTm01;
import com.hncis.trafficTicket.vo.BgabGascTm02;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TrafficTicketController extends AbstractController{

	@Autowired
    @Qualifier("trafficTicketManagerImpl")
	private TrafficTicketManager trafficTicketManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * management list
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm01.do")
	public ModelAndView doSearchByXtm01(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascTm01 gsReqVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = trafficTicketManager.getSelectByXtm01ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(trafficTicketManager.getSelectByXtm01List(gsReqVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 *  management list - insert/update
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/trafficTicket/doInsertByXtm01.do")
	public ModelAndView doInsertByXgs07Request(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsV", required=false) String paramsV,
		@RequestParam(value="paramsI", required=false) String paramsI,
		@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException, SessionException{

		List<BgabGascTm01> gsSaveList = (List<BgabGascTm01>) getJsonToList(paramsI, BgabGascTm01.class);
		List<BgabGascTm01> gsModifyList = (List<BgabGascTm01>) getJsonToList(paramsU, BgabGascTm01.class);

		for(BgabGascTm01 tm01Svvo : gsSaveList){
			String docNo = StringUtil.getDocNo();
			tm01Svvo.setDoc_no(docNo);
			tm01Svvo.setPgs_st_cd("0");
			tm01Svvo.setPtt_ymd(CurrentDateTime.getDate());
			tm01Svvo.setIpe_eeno(SessionInfo.getSess_empno(req));
			tm01Svvo.setUpdr_eeno(SessionInfo.getSess_empno(req));

			trafficTicketManager.doInsertByXtm01List(tm01Svvo);
		}

		for(BgabGascTm01 tm01MdVo : gsModifyList){
			tm01MdVo.setUpdr_eeno(SessionInfo.getSess_empno(req));
			trafficTicketManager.doUpdateByXtm01List(tm01MdVo);
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
	 *  management list - delete/emailsend/done/donecancel/payment/hrrepot/hrreprotcancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/trafficTicket/doActionByXtm01.do")
	public ModelAndView doActionByXtm01(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramBtn", required=true) String paramBtn,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		List<BgabGascTm01> tmVoList = (List<BgabGascTm01>) getJsonToList(paramJson, BgabGascTm01.class);

		CommonMessage appMessage = new CommonMessage();
		String pgsStCd = "";
		String msgCd = "";
		if("delete".equals(paramBtn)){
			msgCd = "DELETE.0000";
		}else if("emailSend".equals(paramBtn)){
			pgsStCd = "1";
			msgCd = "EMAIL.0000";
		}else if("done".equals(paramBtn)){
			pgsStCd = "3";
			msgCd = "DONE.0000";
		}else if("doneCancel".equals(paramBtn)){
			pgsStCd = "2";
			msgCd = "DONE.0003";
		}else if("payment".equals(paramBtn)){
			pgsStCd = "4";
			msgCd = "PAY.0000";
		}else if("hrreport".equals(paramBtn)){
			pgsStCd = "5";
			msgCd = "HRRPT.0000";
		}else if("hrreportCancel".equals(paramBtn)){
			pgsStCd = "4";
			msgCd = "HRRPT.0003";
		}else if("hrreportDone".equals(paramBtn)){
			pgsStCd = "6";
			msgCd = "HRRPT.0005";
		}else if("hrreportDoneCancel".equals(paramBtn)){
			pgsStCd = "5";
			msgCd = "HRRPT.0007";
		}else if("apply".equals(paramBtn)){
			pgsStCd = "2";
			msgCd = "ACCEPT.0000";
		}

		for(BgabGascTm01 tm01vo : tmVoList){
			if("delete".equals(paramBtn)){
				trafficTicketManager.doDeleteByXtm01List(tm01vo);
			}else{
				if(!"emailSend".equals(paramBtn)){
					tm01vo.setPgs_st_cd(pgsStCd);
					tm01vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
					appMessage = trafficTicketManager.doActionByXtm01List(tm01vo);
				}

				if("emailSend".equals(paramBtn)){
					if("0".equals(tm01vo.getPgs_st_cd())){
						tm01vo.setPgs_st_cd(pgsStCd);
						tm01vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
						appMessage = trafficTicketManager.doActionByXtm01List(tm01vo);
					}
					BgabGascz002Dto userDto = new BgabGascz002Dto();
					userDto.setXusr_empno(tm01vo.getEeno());
					userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
					BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);

					String fromEenm   = SessionInfo.getSess_name(req);
					String fromStepNm = SessionInfo.getSess_step_name(req);
					String toEeno     = userDetailInfo.getXusr_mail_adr();
					String mode       = "Email";
					String title      = "Traffic Ticket";

					Submit.sendEmailTrafficTicketForEmailSend(fromEenm, fromStepNm, toEeno, mode, title);
				}
//				if("payment".equals(paramBtn)){
//					BgabGascz002Dto userDto = new BgabGascz002Dto();
//					userDto.setXusr_empno(tm01vo.getEeno());
//					BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);
//
//					String fromEenm   = SessionInfo.getSess_name(req);
//					String fromStepNm = SessionInfo.getSess_step_name(req);
//					String toEeno     = userDetailInfo.getXusr_mail_adr();
//					String mode       = "payment";
//					String title      = "Traffic Ticket";
//
//					Submit.sendEmailTrafficTicketForPayment(fromEenm, fromStepNm, toEeno, mode, title);
//				}
			}
		}

		CommonMessage message = new CommonMessage();
		System.out.println("appMessage.getMessage() : " + appMessage.getMessage());
		if("".equals(StringUtil.isNullToString(appMessage.getMessage()))){
			message.setMessage(HncisMessageSource.getMessage(msgCd));
		}else{
			message.setMessage(appMessage.getMessage());
			message.setErrorCode(appMessage.getErrorCode());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	/**
	 * list
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm02.do")
	public ModelAndView doSearchByXtm02(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();
		try{
		BgabGascTm01 gsReqVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = trafficTicketManager.getSelectByXtm02ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(trafficTicketManager.getSelectByXtm02List(gsReqVo));

//		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return modelAndView;
	}

	/**
	 * list
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm02ExcelList.excel")
	public ModelAndView doSearchByXtm02ExcelList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsReqVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);
		gsReqVo.setStartRow(0);

		CommonList list = new CommonList();
		list.setRows(trafficTicketManager.getSelectByXtm02ExcelList(gsReqVo));
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
	 * car info
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchToCarInfo.do")
	public ModelAndView doSearchToCarInfo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsParamVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		BgabGascTm01 rsReqDto = new BgabGascTm01();
		rsReqDto = trafficTicketManager.doSearchToCarInfo(gsParamVo);

		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		}else{
			rsReqDto = new BgabGascTm01();
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0002"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	/**
	 * view info
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm03ViewInfo.do")
	public ModelAndView doSearchByXtm03ViewInfo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsParamVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		BgabGascTm01 rsReqDto = new BgabGascTm01();
		rsReqDto = trafficTicketManager.getSearchByXtm03ViewInfo(gsParamVo);
		if(rsReqDto == null){
			rsReqDto = new BgabGascTm01();
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
		}else{
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}

	/**
	 * view info - accept
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doUpdateByXtm03Accept.do")
	public ModelAndView doUpdateByXtm03Accept(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsParamVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		trafficTicketManager.doUpdateByXtm03Accept(gsParamVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("ACCEPT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	/**
	 * HR Report list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm04.do")
	public ModelAndView doSearchByXtm04(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsReqVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = trafficTicketManager.getSelectByXtm04ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(trafficTicketManager.getSelectByXtm04List(gsReqVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * view info - done
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doUpdateByXtm04Done.do")
	public ModelAndView doUpdateByXtm04Done(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsParamVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		trafficTicketManager.doUpdateByXtm04ListDone(gsParamVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DONE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	/**
	 * view info - done cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doUpdateByXtm04DoneCancel.do")
	public ModelAndView doUpdateByXtm04DoneCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm01 gsParamVo = (BgabGascTm01) getJsonToBean(paramJson, BgabGascTm01.class);

		trafficTicketManager.doUpdateByXtm04ListDoneCancel(gsParamVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DONE.0001"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}





	/**
	 * Code Management list
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchByXtm05.do")
	public ModelAndView doSearchByXtm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm02 gsReqVo = (BgabGascTm02) getJsonToBean(paramJson, BgabGascTm02.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = trafficTicketManager.getSelectByXtm05ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(endRow);
		list.setRows(trafficTicketManager.getSelectByXtm05List(gsReqVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Code Management - Save
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/trafficTicket/doSaveByXtm05.do")
	public ModelAndView doSaveByXtm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="iParams", required=true) String iParams,
		@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		List<BgabGascTm02> iData = (List<BgabGascTm02>) getJsonToList(iParams, BgabGascTm02.class);
		List<BgabGascTm02> uData = (List<BgabGascTm02>) getJsonToList(uParams, BgabGascTm02.class);

		trafficTicketManager.saveByXtm05List(iData, uData);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	/**
	 * Code Management - Delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/trafficTicket/doDeleteByXtm05.do")
	public ModelAndView doDeleteByXtm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="dParams", required=true) String dParams) throws HncisException{

		List<BgabGascTm02> dData = (List<BgabGascTm02>) getJsonToList(dParams, BgabGascTm02.class);

		trafficTicketManager.deleteByXtm05List(dData);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hmc/trafficTicket/getCommonTrafficTicketDescCombo.do")
	public ModelAndView getCommonTrafficTicketDescCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		System.out.println("getCommonMultiCombo 입장");

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codePair = null;

		List<BgabGascTm02> codeList = null;

		codePair = codknd.split(":");

		if( codePair.length > 0)
		{
			jsonArr = new JSONBaseArray();

			codeList = trafficTicketManager.getSelectByDescrCommboList();

			for(BgabGascTm02 targetBean : codeList){

				json = new JSONBaseVO();
				json.put("key" , StringUtil.isNullToStrTrm(targetBean.getDesc_code()));
				json.put("pint", StringUtil.isNullToStrTrm(targetBean.getTic_pint()));
				json.put("amt" , StringUtil.isNullToStrTrm(targetBean.getTic_amt()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
		}

		System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * car info
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/trafficTicket/doSearchToTransitoInfo.do")
	public ModelAndView doSearchToTransitoInfo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascTm02 gsParamVo = (BgabGascTm02) getJsonToBean(paramJson, BgabGascTm02.class);

		BgabGascTm02 rsReqDto = new BgabGascTm02();
		rsReqDto = trafficTicketManager.getSelectToTransitoInfo(gsParamVo);

		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		}else{
			rsReqDto = new BgabGascTm02();
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0002"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());

		return modelAndView;
	}
}