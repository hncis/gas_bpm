package com.hncis.controller.entranceMeal;

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
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.entranceMeal.manager.EntranceMealManager;
import com.hncis.entranceMeal.vo.BgabGascem01Dto;
import com.hncis.entranceMeal.vo.BgabGascem02Dto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class EntranceMealController extends AbstractController{

	@Autowired
    @Qualifier("entranceMealManagerImpl")
	private EntranceMealManager entranceMealManager;


	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;



	/*************************************************************/
	/** entrance&meal request page                              **/
	/*************************************************************/
	/**
	 * entrance&meal request insert
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doInsertEmToRequest.do")
	public ModelAndView doInsertEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJsonList" , required=true) String paramJsonList,
			@RequestParam(value="paramJsonStr" , required=true) String paramJsonStr
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJsonStr, BgabGascem01Dto.class);
		List<BgabGascem02Dto> bgabGascem02DtoList = (List<BgabGascem02Dto>)getJsonToList(paramJsonList, BgabGascem02Dto.class);

		BgabGascem01Dto reqDto= entranceMealManager.insertEmToRequest(bgabGascem01Dto,bgabGascem02DtoList);

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

		return modelAndView;
	}

	/**
	 * entrance&meal request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchInfoEmToRequest.do")
	public ModelAndView doSearchInfoEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascem01Dto dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		BgabGascem01Dto rsReqDto = new BgabGascem01Dto();
		rsReqDto = entranceMealManager.getSelectInfoEmToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("EM");
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
	 * meal list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchListEmToRequest.do")
	public ModelAndView doSearchListEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);


 		CommonList list = new CommonList();
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToRequest(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * entrance&meal delete visitors
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doDeleteVisitorsRequest.do")
	public ModelAndView doDeleteVisitorsRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascem02Dto dto = (BgabGascem02Dto) getJsonToBean(paramJson, BgabGascem02Dto.class);

		CommonMessage message = new CommonMessage();

		int cnt = entranceMealManager.deleteVisitorsEmToRequest(dto);

		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * entrance&meal request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doDeleteEmToRequest.do")
	public ModelAndView doDeleteEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		BgabGascem01Dto reqDto= entranceMealManager.deleteEmToRequest(bgabGascem01Dto);

		if(!reqDto.isErrYn()){
			message.setCode(reqDto.getDoc_no());
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
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
	 * entrance&meal request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doApproveEmToRequest.do")
	public ModelAndView doApprovePsToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascem01Dto dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		message = entranceMealManager.setApproval(dto, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	/**
	 * entranceMeal request approve cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/entranceMeal/doApproveCancelEmToRequest.do")
	public ModelAndView doApproveCancelPcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascem01Dto regDto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);
		regDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		CommonMessage message = entranceMealManager.setApprovalCancel(regDto,req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * entrance&meal request confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doConfirmEmToRequest.do")
	public ModelAndView doConfirmEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJsonList" , required=true) String paramJsonList,
			@RequestParam(value="paramJsonStr" , required=true) String paramJsonStr
			) throws Exception{

		CommonMessage message = new CommonMessage();

		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJsonStr, BgabGascem01Dto.class);
		List<BgabGascem02Dto> bgabGascem02DtoList = (List<BgabGascem02Dto>)getJsonToList(paramJsonList, BgabGascem02Dto.class);

		int cnt = entranceMealManager.updateInfoEmToConfirm(bgabGascem01Dto,bgabGascem02DtoList);

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
	 * entrance&meal request ConfirmCancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doConfirmCancelEmToRequest.do")
	public ModelAndView doConfirmCancelEmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{


		BgabGascem01Dto dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		int cnt = entranceMealManager.updateInfoEmToConfirmCancel(dto);

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
	 * entrance&meal approve search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchInfoEmToRequestForApprove.do")
	public ModelAndView doSearchInfoEmToRequestForApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascem01Dto dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		BgabGascem01Dto rsReqDto = new BgabGascem01Dto();
		rsReqDto = entranceMealManager.getSelectInfoEmToRequestForApprove(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(rsReqDto.getIf_id());
			commonApproval.setSystem_code("EM");
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
	 * meal list approve search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchListEmToRequestForApprove.do")
	public ModelAndView doSearchListEmToRequestForApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);


 		CommonList list = new CommonList();
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToRequestForApprove(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	/*************************************************************/
	/** list page                                               **/
	/*************************************************************/
	/**
	 * meal list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchGridEmToList.do")
	public ModelAndView doSearchGridEmToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = entranceMealManager.getSelectCountEmToList(bgabGascem01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascem01Dto.setStartRow(startRow);
 		bgabGascem01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToList(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * entrance&meal excel 출력
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/entranceMeal/doExcelToList.excel")
	public ModelAndView doExcelToTotalListByCompanyCar(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = entranceMealManager.getSelectCountEmToList(bgabGascem01Dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		bgabGascem01Dto.setStartRow(startRow);
		bgabGascem01Dto.setEndRow(count);
		list.setRows(entranceMealManager.getSelectListEmToList(bgabGascem01Dto));

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

	}//end method

	/*************************************************************/
	/** meal list page                                          **/
	/*************************************************************/
	/**
	 * meal list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchGridEmToListForMeal.do")
	public ModelAndView doSearchGridEmToListForMeal(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = entranceMealManager.getSelectCountEmToListForMeal(bgabGascem01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascem01Dto.setStartRow(startRow);
 		bgabGascem01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToListForMeal(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/*************************************************************/
	/** entrance list page                                          **/
	/*************************************************************/
	/**
	 * meal list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchGridEmToListForEntrance.do")
	public ModelAndView doSearchGridEmToListForEntrance(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = entranceMealManager.getSelectCountEmToListForEntrance(bgabGascem01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascem01Dto.setStartRow(startRow);
 		bgabGascem01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToListForEntrance(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/*************************************************************/
	/** list page for worker                                    **/
	/*************************************************************/
	/**
	 * list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/entranceMeal/doSearchGridEmToListForWorker.do")
	public ModelAndView doSearchGridEmToListForWorker(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascem01Dto bgabGascem01Dto = (BgabGascem01Dto) getJsonToBean(paramJson, BgabGascem01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = entranceMealManager.getSelectCountEmToListForWorker(bgabGascem01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascem01Dto.setStartRow(startRow);
 		bgabGascem01Dto.setEndRow(endRow);
 		//검색
 		list.setRows(entranceMealManager.getSelectListEmToListForWorker(bgabGascem01Dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
}
