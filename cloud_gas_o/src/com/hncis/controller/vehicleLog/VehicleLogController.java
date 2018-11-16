package com.hncis.controller.vehicleLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.generalService.manager.GeneralServiceManager;
import com.hncis.vehicleLog.manager.VehicleLogManager;
import com.hncis.vehicleLog.vo.BgabGascvl01Dto;

@Controller
public class VehicleLogController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
    @Qualifier("vehicleLogManagerImpl")
	private VehicleLogManager vehicleLogManager;

	@Autowired
    @Qualifier("generalServiceManagerImpl")
	private GeneralServiceManager generalServiceManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;


	/**
	 * vehicleLog request insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/vehicleLog/doSaveXvl01Info.do")
	public ModelAndView doSaveXvl01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = vehicleLogManager.saveXvl01Info(dto);

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

	@RequestMapping(value="/hncis/vehicleLog/doSelectXvl01Info.do")
	public ModelAndView doSelectXvl01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		BgabGascvl01Dto rtnDto = vehicleLogManager.selectXvl01Info(dto);

		if(rtnDto != null){
			if(!StringUtil.isNullToString(rtnDto.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rtnDto.getIf_id());
				commonApproval.setSystem_code("VL");
				commonApproval.setCorp_cd(dto.getCorp_cd());

				rtnDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/vehicleLog/doSelectXvl01InfoByIfId.do")
	public ModelAndView doSelectXvl01InfoByIfId(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		BgabGascvl01Dto rtnDto = vehicleLogManager.selectXvl01InfoByIfId(dto);

		if(rtnDto != null){
			if(!StringUtil.isNullToString(rtnDto.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rtnDto.getIf_id());
				commonApproval.setSystem_code("VL");
				commonApproval.setCorp_cd(dto.getCorp_cd());

				rtnDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/vehicleLog/doDeleteXvl01Info.do")
	public ModelAndView doDeleteXvl01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = vehicleLogManager.deleteXvl01Info(dto);

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

	@RequestMapping(value="/hncis/vehicleLog/doSearchXvl01InfoList.do")
	public ModelAndView doSearchXvl01InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = vehicleLogManager.selectXvl01InfoListCount(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(vehicleLogManager.selectXvl01InfoList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/vehicleLog/doSearchXvl02InfoList.do")
	public ModelAndView doSearchXvl02InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		//검색
		int count = vehicleLogManager.selectXvl02InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		//검색
		list.setRows(vehicleLogManager.selectXvl02InfoList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/vehicleLog/doSearchXvl03InfoList.do")
	public ModelAndView doSearchXvl03InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		//검색
		int count = vehicleLogManager.selectXvl03InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		//검색
		list.setRows(vehicleLogManager.selectXvl03InfoList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * training request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/vehicleLog/doApproveVLToRequest.do")
	public ModelAndView doApproveVLToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		message = vehicleLogManager.setApproveVLToRequest(dto, appInfo, message, req);

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
	 * training request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/vehicleLog/doApproveCancelVLToRequest.do")
	public ModelAndView doApproveCancelVLToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		CommonApproval appInfo = new CommonApproval();
		dto.setCorp_cd(dto.getCorp_cd());

		message = vehicleLogManager.setApproveCancelVLToRequest(dto, appInfo, message, req);

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
	 * training confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/vehicleLog/doConfirmVLToRequest.do")
	public ModelAndView doConfirmVLToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGascvl01Dto dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		vehicleLogManager.confirmTRToRequest(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("run_hist");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		message.setCode(dto.getPgs_st_cd());

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
	 * business Travel request reject
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/vehicleLog/doRejectVLToRequest.do")
	public ModelAndView doRejectVLToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascvl01Dto keyParamDto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		CommonMessage message = vehicleLogManager.updateInfoTrToReject(keyParamDto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getEeno();
		String rtnText    = keyParamDto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("run_hist");
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
	 * business Card accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/vehicleLog/doExcelVehicleLogList.excel")
	public ModelAndView doExcelVehicleLogList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascvl01Dto bgabGascvl01Dto = (BgabGascvl01Dto) getJsonToBean(paramJson, BgabGascvl01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = vehicleLogManager.selectXvl02InfoListCount(bgabGascvl01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascvl01Dto.setStartRow(1);
 		bgabGascvl01Dto.setEndRow(count);
 		//검색
 		list.setRows(vehicleLogManager.selectXvl02InfoList(bgabGascvl01Dto));

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