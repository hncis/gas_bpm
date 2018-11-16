package com.hncis.controller.businessVehicles;

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

import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessVehicles.manager.BusinessVehiclesManager;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.businessVehicles.vo.BgabGascbv02Dto;
import com.hncis.businessVehicles.vo.BgabGascbv03Dto;
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
import com.hncis.generalService.manager.GeneralServiceManager;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.pickupService.vo.BgabGascps01Dto;

@Controller
public class BusinessVehiclesController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
    @Qualifier("businessVehiclesManagerImpl")
	private BusinessVehiclesManager businessVehiclesManager;

	@Autowired
    @Qualifier("generalServiceManagerImpl")
	private GeneralServiceManager generalServiceManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * business vehicle 에서 사용하는 selectbox 값 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param codknd the codknd
	 * @param keyType the key type
	 * @return the combo list by uf
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doSearchBvToCombo.do")
	public ModelAndView doSearchBvToCombo(HttpServletRequest req, HttpServletResponse res,
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

			if(codeKnd[0].equals("vehl_cd")||codeKnd[0].equals("key_vehl_cd")){
				comboVal = "BV_CD";
				comboName = "BV_NM";
				comboTable = "BGAB_GASCBV03" + SessionInfo.getSess_corp_cd(req);
				comboWhereCondition  = "BV_TY='A' AND USE_YN = 'Y'";
				comboGroupBy = "";
				comboorderBy = "BV_SRT";
			}
			else if(codeKnd[0].equals("car_type_cd")||codeKnd[0].equals("key_car_type_cd")){
				comboVal = "BV_CD";
				comboName = "BV_NM";
				comboTable = "BGAB_GASCBV03" + SessionInfo.getSess_corp_cd(req);
				comboWhereCondition  = "BV_TY='B' AND USE_YN = 'Y'";
				comboGroupBy = "";
				comboorderBy = "BV_SRT";
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
	 * Vehicle list for dept search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchGridBvToVehicleListForDept.do")
	public ModelAndView doSearchGridBvToVehicleListForDept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToVehicleListForDept(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToVehicleListForDept(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	/*************************************************************/
	/** businessVehicles request page                           **/
	/*************************************************************/
	/**
	 * businessVehicles request insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doInsertBvToRequest.do")
	public ModelAndView doInsertPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		CommonMessage message = new CommonMessage();

		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		BgabGascbv02Dto reqDto= businessVehiclesManager.insertBvToRequest(dto);

		if(!reqDto.isErrYn()){
			message.setCode(reqDto.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}
		else{
			message.setMessage(reqDto.getErrMsg());
		}

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
	 * businessVehicles request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchInfoBvToRequest.do")
	public ModelAndView doSearchInfoBvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		BgabGascbv02Dto rsReqDto = new BgabGascbv02Dto();
		rsReqDto = businessVehiclesManager.getSelectInfoBvToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){
				
				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rsReqDto.getIf_id());
				commonApproval.setSystem_code("BV");
				commonApproval.setCorp_cd(dto.getCorp_cd());
				
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
	 * businessVehicles request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doDeleteBvToRequest.do")
	public ModelAndView doDeletePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		CommonMessage message = new CommonMessage();

		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		int cnt= businessVehiclesManager.deleteBvToRequest(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

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
	 * businessVehicles request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doApproveBvToRequest.do")
	public ModelAndView doApprovePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		

		CommonMessage message = new CommonMessage();
		BgabGascbv02Dto regDto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		message = businessVehiclesManager.setApproval(regDto, req);

		ModelAndView modelAndView = new ModelAndView();
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
	 * businessVehicles request approve cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doApproveCancelBvToRequest.do")
	public ModelAndView doApproveCancelPcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		BgabGascbv02Dto regDto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		
		message = businessVehiclesManager.setApprovalCancel(regDto, appInfo, message, req);

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
	 * businessVehicles request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doConfirmBvToRequest.do")
	public ModelAndView doConfirmPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		

		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		int cnt = businessVehiclesManager.updateInfoBvToConfirm(dto,req);

		CommonMessage message = new CommonMessage();

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
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}

	/**
	 * businessVehicles request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doRejectBvToRequest.do")
	public ModelAndView doRejectBvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		

		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		int cnt = businessVehiclesManager.updateInfoBvToReject(dto,req);

		CommonMessage message = new CommonMessage();

		if(cnt == 1){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
		}

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
	 * businessVehicles request ConfirmCancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doConfirmCancelBvToRequest.do")
	public ModelAndView doConfirmCancelPsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{


		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		int cnt = businessVehiclesManager.updateInfoBvToConfirmCancel(dto, req);

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
	 * businessVehicles approve search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchInfoBvToRequestForApprove.do")
	public ModelAndView doSearchInfoBvToRequestForApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		BgabGascbv02Dto rsReqDto = new BgabGascbv02Dto();
		rsReqDto = businessVehiclesManager.getSelectInfoBvToRequestForApprove(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("BV");
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
	/*************************************************************/
	/** businessVehicles list page                                 **/
	/*************************************************************/
	/**
	 * pickupService list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchGridBvToList.do")
	public ModelAndView doSearchGridPsToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToList(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * businessVehicles excel 출력
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doExcelToList.excel")
	public ModelAndView doExcelToTotalListByCompanyCar(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascbv02Dto dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = businessVehiclesManager.getSelectCountBvToList(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(count);
		list.setRows(businessVehiclesManager.getSelectListBvToList(dto));

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
	/** Vehicle management page                                 **/
	/*************************************************************/

	/**
	 * Vehicles management request insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doInsertBvToVehicleManagement.do")
	public ModelAndView doInsertBvToVehicleManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		BgabGascbv01Dto reqDto= businessVehiclesManager.insertBvToVehicleManagement(dto);

		if(!reqDto.isErrYn()){
			message.setCode(reqDto.getChss_no());
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}
		else{
			message.setMessage(reqDto.getErrMsg());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * businessVehicles request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchInfoBvToVehicleManagement.do")
	public ModelAndView doSearchInfoBvToVehicleManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		BgabGascbv01Dto rsReqDto = new BgabGascbv01Dto();
		rsReqDto = businessVehiclesManager.getSelectInfoBvToVehicleManagement(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
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
	 * businessVehicles request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doDeleteBvToVehicleManagement.do")
	public ModelAndView doDeleteBvToVehicleManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		int cnt= businessVehiclesManager.deleteBvToVehicleManagement(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** Vehicle list page                                       **/
	/*************************************************************/
	/**
	 * Vehicle list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchGridBvToVehicleList.do")
	public ModelAndView doSearchGridBvToVehicleList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToVehicleList(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToVehicleList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Vehicle list excel 출력
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis//businessVehicles/doExcelToVehicleList.excel")
	public ModelAndView doExcelToListByVehicle(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascbv01Dto bgabGascem01Dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = businessVehiclesManager.getSelectCountBvToVehicleList(bgabGascem01Dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		bgabGascem01Dto.setStartRow(startRow);
		bgabGascem01Dto.setEndRow(count);
		list.setRows(businessVehiclesManager.getSelectListBvToVehicleList(bgabGascem01Dto));

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
	/** code managerment page                  	                **/
	/*************************************************************/
	/**
	 * code managerment list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchGridBvToCodeManagement.do")
	public ModelAndView doSearchGridBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascbv03Dto dto = (BgabGascbv03Dto) getJsonToBean(paramJson, BgabGascbv03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToCodeManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToCodeManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 *code managerment list insert.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doInsertBvToCodeManagement.do")
	public ModelAndView doInsertBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascbv03Dto> dto = (List<BgabGascbv03Dto>) getJsonToList(paramJson, BgabGascbv03Dto.class);

		int cnt = businessVehiclesManager.insertBvToCodeManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *code managerment list delete.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doDeleteBvToCodeManagement.do")
	public ModelAndView doDeleteBvToCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		List<BgabGascbv03Dto> dto = (List<BgabGascbv03Dto>) getJsonToList(paramJson, BgabGascbv03Dto.class);

		int cnt = businessVehiclesManager.deleteBvToCodeManagement(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/*************************************************************/
	/** Vehicle registration info list page                     **/
	/*************************************************************/
	/**
	 * Vehicle list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchGridBvToVehicleRegInfo.do")
	public ModelAndView doSearchGridBvToVehicleRegInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascbv01Dto dto = (BgabGascbv01Dto) getJsonToBean(paramJson, BgabGascbv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToVehicleRegInfo(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToVehicleRegInfo(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * businessVehicles file save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSaveBvToFile.do")
	public void doSaveBvToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		System.out.println("fileInfo="+fileInfo);

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			businessVehiclesManager.saveBvToFile(req, res, dto);
		}
	}

	/**
	 * businessVehicles file search
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchBvToFile.do")
	public ModelAndView doSearchBvToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(businessVehiclesManager.getSelectBvToFile(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * businessVehicles file down
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = businessVehiclesManager.getSelectBvToFileInfo(btReqDto);



		//File downloadFile = new File("D:/upload/businessTravel/201310281811374.docx");

		System.out.println("doFileDown");

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "businessVehicles");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	/**
	 * businessVehicles file delete
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doDeleteBvToFile.do")
	public ModelAndView doDeleteBvToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		businessVehiclesManager.deleteBvToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/businessVehicles/doSearchByRequestList.do")
	public ModelAndView doSearchByRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascgs03 gsParamVo = (BgabGascgs03) getJsonToBean(paramJson, BgabGascgs03.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsParamVo.setSess_eeno("");
		}else{
			BgabGascgs04 gs04Vo = new BgabGascgs04();
			gs04Vo.setEeno(SessionInfo.getSess_empno(req));
			String isManger = generalServiceManager.getSelectByXgsIsManager(gs04Vo);
			if("Y".equals(isManger)){
				gsParamVo.setSess_eeno(SessionInfo.getSess_empno(req));
			}else{
				gsParamVo.setSess_eeno("");
			}
		}

		CommonList list = new CommonList();
		list.setRows(generalServiceManager.doSearchByRequestList(gsParamVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchByXgs03.do")
	public ModelAndView doSearchByXgs03(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascgs01 gsReqVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
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
	@RequestMapping(value="/hncis/businessVehicles/doSelectByXbv03ListExcel.excel")
	public ModelAndView doSelectByXbv03ListExcel(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascgs01 gsReqVo = (BgabGascgs01) getJsonToBean(paramJson, BgabGascgs01.class);

		String master = SessionInfo.getSess_mstu_gubb(req);
		if("M".equals(master)){
			gsReqVo.setSess_eeno("");
		}else{
			gsReqVo.setSess_eeno(SessionInfo.getSess_empno(req));
		}

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = generalServiceManager.getSelectByXgs03ListCount(gsReqVo);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		gsReqVo.setStartRow(startRow);
		gsReqVo.setEndRow(count);

		list.setRows(generalServiceManager.getSelectByXgs03List(gsReqVo));

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
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSaveMaintenanceToFile.do")
	public void doSaveMaintenanceToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			businessVehiclesManager.saveMaintenanceToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/businessVehicles/doSearchMaintenanceToFile.do")
	public ModelAndView doSearchMaintenanceToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(businessVehiclesManager.getSelectMaintenanceToFile(dto));

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
	@RequestMapping(value="/hncis/businessVehicles/doMaintenanceFileDown.do")
	public ModelAndView doMaintenanceFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = businessVehiclesManager.getSelectMaintenanceToFileInfo(dto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "companyCar");

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
	@RequestMapping(value="/hncis/businessVehicles/doDeleteMaintenanceToFile.do")
	public ModelAndView doDeleteMaintenanceToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		businessVehiclesManager.deleteMaintenanceToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/businessVehicles/doSearchMaintenanceExpenseManagement.do")
	public ModelAndView doSearchMaintenanceExpenseManagement(HttpServletRequest req, HttpServletResponse res,
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
		int count       = businessVehiclesManager.getSelectCountMaintenanceExpenseManagement(btDtlDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		btDtlDto.setStartRow(startRow);
		btDtlDto.setEndRow(endRow);
		list.setRows(businessVehiclesManager.getSelectMaintenanceExpenseManagement(btDtlDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doSaveMaintenanceExpenseManagement.do")
	public ModelAndView doSaveMaintenanceExpenseManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascbt06> dtoIList = (List<BgabGascbt06>)getJsonToList(paramsI, BgabGascbt06.class);
		List<BgabGascbt06> dtoUList = (List<BgabGascbt06>)getJsonToList(paramsU, BgabGascbt06.class);

		businessVehiclesManager.saveListToMaintenanceExpenseManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/businessVehicles/doDeleteMaintenanceExpenseManagement.do")
	public ModelAndView doDeleteMaintenanceExpenseManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascbt06> dtoDList = (List<BgabGascbt06>)getJsonToList(paramJson, BgabGascbt06.class);
		businessVehiclesManager.deleteListToMaintenanceExpenseManagement(dtoDList);

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
	@RequestMapping(value="/hncis/businessVehicles/doExcelBusinessVehiclesList.excel")
	public ModelAndView doExcelPickUpList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascbv02Dto bgabGascbv02Dto = (BgabGascbv02Dto) getJsonToBean(paramJson, BgabGascbv02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = businessVehiclesManager.getSelectCountBvToList(bgabGascbv02Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascbv02Dto.setStartRow(1);
 		bgabGascbv02Dto.setEndRow(count);
 		//검색
 		list.setRows(businessVehiclesManager.getSelectListBvToList(bgabGascbv02Dto));

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
