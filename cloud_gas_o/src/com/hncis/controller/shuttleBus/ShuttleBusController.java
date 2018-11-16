package com.hncis.controller.shuttleBus;

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

import com.hncis.businessTravel.manager.BusinessTravelManager;
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
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.shuttleBus.manager.ShuttleBusManager;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.shuttleBus.vo.BgabGascsb02;
import com.hncis.shuttleBus.vo.BgabGascsb03;
import com.hncis.shuttleBus.vo.BgabGascsb04;

@Controller
public class ShuttleBusController extends AbstractController{

	@Autowired
	@Qualifier("shuttleBusManagerImpl")
	private ShuttleBusManager shuttleBusManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@Autowired
	@Qualifier("businessTravelManagerImpl")
	private BusinessTravelManager businessTravelManager;

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * Shuttle Bus New Save
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSaveShuttleBusRequst.do")
	public ModelAndView doSaveShuttleBusRequst(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);

		ModelAndView modelAndView = null;

		BgabGascsb01 vo = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		String doc_no = StringUtil.getDocNo();
		if("".equals(vo.getDoc_no())){
			vo.setDoc_no(doc_no);
		}
		
		shuttleBusManager.saveShuttleBusRequst(vo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setCode(vo.getDoc_no());
		
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
	 * Shuttle Bus New Delete
	 */
	@RequestMapping(value="/hncis/shuttleBus/doDeleteShuttleBusRequstNew.do")
	public ModelAndView doDeleteShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascsb01 paramList = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		shuttleBusManager.deleteShuttleBusRequstNew(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus New Request
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/shuttleBus/doRequestShuttleBusRequstNew.do")
	public ModelAndView doRequestShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException, SessionException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGascsb01 keyParamDto = (BgabGascsb01) getJsonToBean(paramJson, BgabGascsb01.class);

		message = shuttleBusManager.requestShuttleBusRequstNew(keyParamDto);

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
	 * Shuttle Bus New Request cancel
	 */
	@RequestMapping(value="/hncis/shuttleBus/doRequestShuttleBusNewRequstCancel.do")
	public ModelAndView doRequestShuttleBusNewRequstCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGascsb01 keyParamDto = (BgabGascsb01) getJsonToBean(paramJson, BgabGascsb01.class);

		message = shuttleBusManager.requestShuttleBusRequstNew(keyParamDto);

		message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		
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
	 * Shuttle Bus New Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusRequstNew.do")
	public ModelAndView doSearchShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);
		BgabGascsb01 info = shuttleBusManager.getSelectShuttleBusNewInfo(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle search list
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/shuttleBus/doRequestShuttleBusList.do")
	public ModelAndView doRequestShuttleBusList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
			BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

			if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
			if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

			int currentPage = Integer.parseInt(pageNumber);
			int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
			int endRow      = currentPage * Integer.parseInt(pageSize);
			int count       = shuttleBusManager.getSelectShuttleBusListCount(param);

			CommonList list = new CommonList();
			list.setPage(pageNumber);
			list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
			list.setRecords(Integer.toString(count));

			param.setStartRow(startRow);
			param.setEndRow(endRow);
			list.setRows(shuttleBusManager.getSelectShuttleBusList(param));

			modelAndView.setViewName(DATA_JSON_PAGE);
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}

	/**
	 * Shuttle Bus Route Management Transporte list
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/shuttleBus/doSearchTransporteFretadoList.do")
	public ModelAndView doSearchTransporteFretadoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
			CommonList list = new CommonList();
			BgabGascsb02 param = (BgabGascsb02)getJsonToBean(paramJson, BgabGascsb02.class);

			list.setRows(shuttleBusManager.getSelectTransporteFretadoList(param));

			System.out.println("============= list.getRows().size() : "+list.getRows().size());

			modelAndView.setViewName(DATA_JSON_PAGE);
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}

	/**
	 * Shuttle Bus Route Management ponto list
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/shuttleBus/doSearchPonteExistenteList.do")
	public ModelAndView doSearchPonteExistenteList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
			CommonList list = new CommonList();
			BgabGascsb02 param = (BgabGascsb02)getJsonToBean(paramJson, BgabGascsb02.class);

			list.setRows(shuttleBusManager.getSelectPonteExistenteList(param));

			modelAndView.setViewName(DATA_JSON_PAGE);
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}

	/**
	 * Shuttle Bus Route Management Transporte Save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/doSaveTransporteFretadoList.do")
	public ModelAndView doSaveTransporteFretadoList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException{
		ModelAndView modelAndView = null;

		List<BgabGascsb02> paramList = (List<BgabGascsb02>)getJsonToList(paramJson, BgabGascsb02.class);

		shuttleBusManager.insertTransporteFretadoList(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus Route Management Transporte Delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/doDeleteTransporteFretadoList.do")
	public ModelAndView doDeleteTransporteFretadoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException{
		ModelAndView modelAndView = null;

		List<BgabGascsb02> paramList = (List<BgabGascsb02>)getJsonToList(paramJson, BgabGascsb02.class);

		shuttleBusManager.deleteTransporteFretadoList(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus Route Management Ponto Existente Save
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/doSavePontoExistenteList.do")
	public ModelAndView doSavePontoExistenteList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException{
		ModelAndView modelAndView = null;

		List<BgabGascsb02> paramList = (List<BgabGascsb02>)getJsonToList(paramJson, BgabGascsb02.class);

		shuttleBusManager.insertPontoExistenteList(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus Route Management Ponto Existente Delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/doDeletePontoExistenteList.do")
	public ModelAndView doDeletePontoExistenteList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException{
		ModelAndView modelAndView = null;

		List<BgabGascsb02> paramList = (List<BgabGascsb02>)getJsonToList(paramJson, BgabGascsb02.class);

		shuttleBusManager.deletePontoExistenteList(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus New Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusRequestCheck.do")
	public ModelAndView doSearchShuttleBusRequestCheck(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);
		BgabGascsb01 info = shuttleBusManager.getSelectShuttleBusRequestCheck(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus Before View Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusBeforeView.do")
	public ModelAndView doSearchShuttleBusBeforeView(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);
		BgabGascsb03 info = shuttleBusManager.getSelectShuttleBusBeforeView(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus confirm
	 */
	@RequestMapping(value="/hncis/shuttleBus/doConfirmShuttleBus.do")
	public ModelAndView doConfirmShuttleBus(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascsb01 keyParamDto = (BgabGascsb01) getJsonToBean(paramJson, BgabGascsb01.class);

		shuttleBusManager.updateShuttleBusRequstNewRequest(keyParamDto);
		shuttleBusManager.updateShuttleBusRequstConfirmFinal(keyParamDto);

//		String fromEeno   = SessionInfo.getSess_name(req);
//		String fromStepNm = SessionInfo.getSess_step_name(req);
//		String toEeno     = keyParamDto.getKey_eeno();
//		String mode       = "confirm";
//		String title      = "Security";
//
//		Submit.sendEmailConfirm(fromEeno, fromStepNm, toEeno, mode, title);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus confirm cancel
	 */
	@RequestMapping(value="/hncis/shuttleBus/doConfirmCancelShuttleBus.do")
	public ModelAndView doConfirmCancelShuttleBus(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascsb01 keyParamDto = (BgabGascsb01) getJsonToBean(paramJson, BgabGascsb01.class);

		shuttleBusManager.updateShuttleBusRequstNewRequest(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

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
	@RequestMapping(value="/hncis/shuttleBus/doShuttleBusFileDown.do")
	public ModelAndView doShuttleBusFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascsb01 btReqDto = (BgabGascsb01) getJsonToBean(fileInfo, BgabGascsb01.class);
		BgabGascsb01 info = shuttleBusManager.getSelectShuttleBusNewInfo(btReqDto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   info.getFil_nm());
		mpfileData.put("fileName",   info.getOrg_fil_nm());
		mpfileData.put("folderName",   "shuttleBus");

		return new ModelAndView("download", "fileData", mpfileData);
	}



	/**
	 * picture and film Area combo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/getSearchShuttleBusLineCombo.do")
	public ModelAndView getSearchShuttleBusLineCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

		JSONBaseVO    jso     = new JSONBaseVO();
		JSONBaseArray jsonArr = null;
		JSONBaseVO    json    = null;

		String []codePair =  codknd.split(":");

		BgabGascsb02 dto = new BgabGascsb02();
		dto.setType(codePair[1]);
		dto.setF_levl(codePair[2]);

		List<BgabGascsb02> typeList = shuttleBusManager.getSelectShuttleBusLineCombo(dto);

		jsonArr = new JSONBaseArray();

		for(BgabGascsb02 type : typeList){
			json = new JSONBaseVO();
			json.put("key"  , "");
			json.put("value", type.getType());
			json.put("name" , type.getRoute_name());

			jsonArr.add(json);
		}

		if(typeList.size() == 0){
			json = new JSONBaseVO();
			json.put("key"  , "");
			json.put("value", "");
			json.put("name" , HncisMessageSource.getMessage("total"));

			jsonArr.add(json);
		}

		jso.put(codePair[0], jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso);
		System.out.println(JSONObject.fromObject(jso).toString());

		return modelAndView;
	}


	/**
	 * Shuttle Bus Route Management ponto list
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/shuttleBus/doSearchPonteExistentePopupList.do")
	public ModelAndView doSearchPonteExistentePopupList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
			BgabGascsb02 param = (BgabGascsb02)getJsonToBean(paramJson, BgabGascsb02.class);

			if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

			int currentPage = Integer.parseInt(pageNumber);
			int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
			int endRow      = currentPage * Integer.parseInt(pageSize);
			int count       = shuttleBusManager.getSelectPonteExistentePopupListCount(param);

			CommonList list = new CommonList();
			list.setPage(pageNumber);
			list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
			list.setRecords(Integer.toString(count));

			param.setStartRow(startRow);
			param.setEndRow(endRow);
			list.setRows(shuttleBusManager.getSelectPonteExistentePopupList(param));

			modelAndView.setViewName(DATA_JSON_PAGE);
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}

	/**
	 * Shuttle Bus Before View Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusSapInformationView.do")
	public ModelAndView doSearchShuttleBusSapInformationView(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascsb04 param = (BgabGascsb04)getJsonToBean(paramJson, BgabGascsb04.class);
		BgabGascsb04 info = shuttleBusManager.getSelectShuttleBusSapInformationView(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * doSearchWorkShiftComboList.do
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/doSearchWorkShiftComboList.do")
	public ModelAndView doSearchWorkShiftComboList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		CommonCode code = null;
		List<CommonCode> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				codeList = shuttleBusManager.getSelectWorkShiftComboList(code);

				if(codePair.length > 2){
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
					json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}
		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Shuttle Bus New Delete
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/shuttleBus/doRejectShuttleBusRequstNew.do")
	public ModelAndView doRejectShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		shuttleBusManager.updateInfoSbToReject(param, req);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus New Delete
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusDocNo.do")
	public ModelAndView doSearchShuttleBusDocNo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		BgabGascsb01 result = shuttleBusManager.getSelectShuttleBusChangeToDocNo(param);

		if(result == null){
			result = new BgabGascsb01();
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(result).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * Shuttle Bus New Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/getSearchShuttleBusLineTime.do")
	public ModelAndView getSearchShuttleBusLineTime(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		BgabGascsb02 param = (BgabGascsb02)getJsonToBean(paramJson, BgabGascsb02.class);
		BgabGascsb02 info = shuttleBusManager.getSelectShuttleBusLineTime(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Shuttle Bus New Delete
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSaveShuttleBusRequstInclusion.do")
	public ModelAndView doSaveShuttleBusRequstInclusion(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascsb01 paramList = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		String doc_no = shuttleBusManager.insertShuttleBusRequstInclusion(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setCode(doc_no);

		modelAndView = new ModelAndView();
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
	@RequestMapping(value="/hncis/shuttleBus/doSaveShuttleBusToFile.do")
	public void doSaveShuttleBusToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			shuttleBusManager.saveShuttleBusToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusToFile.do")
	public ModelAndView doSearchShuttleBusToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(shuttleBusManager.getSelectShuttleBusToFile(dto));

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
	@RequestMapping(value="/hncis/shuttleBus/doShuttleBusFileDownPopup.do")
	public ModelAndView doShuttleBusFileDownPopup(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = shuttleBusManager.getSelectShuttleBusToFileInfo(dto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "shuttleBus");

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
	@RequestMapping(value="/hncis/shuttleBus/doDeleteShuttleBusToFile.do")
	public ModelAndView doDeleteShuttleBusToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		shuttleBusManager.deleteShuttleBusToFile(dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

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
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusSendMail.do")
	public ModelAndView doSearchShuttleBusSendMail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="fileList", required=true) String fileList) throws HncisException, SessionException{

		BgabGascZ011Dto gsReqVo = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		List<BgabGascZ011Dto> rsList = (List<BgabGascZ011Dto>) getJsonToList(fileList, BgabGascZ011Dto.class);
//		List<BgabGascZ011Dto> rsList = businessTravelManager.doSearchBTToSendMail(gsReqVo);

		BgabGascsb01 dto = new BgabGascsb01();
		dto.setEeno(gsReqVo.getEeno());
		dto.setDoc_no(gsReqVo.getDoc_no());
		BgabGascsb01 info = shuttleBusManager.getSelectShuttleBusNewInfo(dto);

		// bus 메일 주소 리스트
		CommonCode code = new CommonCode();
		code.setCodknd("X3035");
		List<CommonCode> hrList = commonManager.getCodeList(code);

		for(int i=0; i<hrList.size(); i++){
			String fromEenm   = SessionInfo.getSess_name(req);
			String fromStepNm = SessionInfo.getSess_step_name(req);
			String toEeno     = hrList.get(i).getName(); //"suzi.yun@hyundai-brasil.com"; // BUS 회사 메일
			String mode       = "SendMail";
			String title      = "Shuttle Bus";
			Submit.sendEmailShuttleBusForHRmail(fromEenm, fromStepNm, toEeno, mode, title, rsList, info);
		}
//		String fromEenm   = SessionInfo.getSess_name(req);
//		String fromStepNm = SessionInfo.getSess_step_name(req);
//		String toEeno     = "tobeplain08@naver.com"; //userDetailInfo.getXusr_mail_adr();
//		String mode       = "SendMail";
//		String title      = "ShuttleBus";
//		Submit.sendEmailShuttleBusForHRmail(fromEenm, fromStepNm, toEeno, mode, title, rsList, info);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("MAIL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/shuttleBus/doShuttleBusExcelToList.excel")
	public ModelAndView doShuttleBusExcelToList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = shuttleBusManager.getSelectShuttleBusListCount(param);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(1);
		param.setEndRow(count);
		list.setRows(shuttleBusManager.getSelectShuttleBusList(param));

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
	
	
	// 노선 조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/shuttleBus/getShuttleBusCombo.do")
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
				
				if("R".equals(codePair[1])){
					codeList = shuttleBusManager.getShuttleBusCombo1(code);
				}else{
					codeList = shuttleBusManager.getShuttleBusCombo2(code);
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
	 * Shuttle Bus New Search
	 */
	@RequestMapping(value="/hncis/shuttleBus/doSearchShuttleBusBeforeRequst.do")
	public ModelAndView doSearchShuttleBusBeforeRequst(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascsb01 param = (BgabGascsb01)getJsonToBean(paramJson, BgabGascsb01.class);
		BgabGascsb01 info = shuttleBusManager.doSearchShuttleBusBeforeRequst(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(info != null){
			info.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(info).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			message.setCode("N");
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/shuttleBus/doRequestShuttleBusNewRequstCheck.do")
	public ModelAndView doRequestShuttleBusNewRequstCheck(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, IOException, SessionException{

		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGascsb01 keyParamDto = (BgabGascsb01) getJsonToBean(paramJson, BgabGascsb01.class);

		message = shuttleBusManager.requestShuttleBusRequstNew(keyParamDto);

		message.setMessage(HncisMessageSource.getMessage("CEHCK.0001"));
		
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

}
