package com.hncis.controller.uniform;

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
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.uniform.manager.UniformManager;
import com.hncis.uniform.vo.BgabGascaf01Dto;
import com.hncis.uniform.vo.BgabGascaf02Dto;
import com.hncis.uniform.vo.BgabGascaf03Dto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UniformController extends AbstractController{

	/** The uniform manager. - uniform 비지니스 로직 class*/
	@Autowired
	@Qualifier("uniformManagerImpl")
	private UniformManager uniformManager;

	/** The common manager. - 공통 로직 class*/
	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * unfirm 공통 MultiComboBox search. - Type of Clothes, Color, Size MultiCombo
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doSearchUniformMultiComboList.do")
	public ModelAndView doSearchUniformMultiComboList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException, SessionException{

		JSONBaseVO    jso     = new JSONBaseVO();
		JSONBaseArray jsonArr = null;
		JSONBaseVO    json    = null;

		String[] comboArr = codknd.split(":");
		String copr_cd	  = SessionInfo.getSess_corp_cd(req);

		List<BgabGascaf02Dto> typeList = uniformManager.selectTypeofclothesComboList(copr_cd);
		List<BgabGascaf02Dto> clrList  = uniformManager.selectColorComboList(copr_cd);
		List<BgabGascaf02Dto> msmList  = uniformManager.selectSizeComboList(copr_cd);

		jsonArr = new JSONBaseArray();

		if(!comboArr[0].equals("Z")){
			json = new JSONBaseVO();
			if(comboArr[0].equals("S")){
				json.put("name", HncisMessageSource.getMessage("select"));
			}else{
				json.put("name", HncisMessageSource.getMessage("total"));
			}
			json.put("value", "");

			jsonArr.add(json);
		}

		for(BgabGascaf02Dto type : typeList){
			json = new JSONBaseVO();
			json.put("key"  , type.getUnif_sex_cd());
			json.put("value", type.getUnif_cd());
			json.put("name" , type.getUnif_nm());

			jsonArr.add(json);
		}
		jso.put("CLOTHES", jsonArr);

		jsonArr = new JSONBaseArray();

		if(!comboArr[1].equals("Z")){
			json = new JSONBaseVO();
			if(comboArr[1].equals("S")){
				json.put("name", HncisMessageSource.getMessage("select"));
			}else{
				json.put("name", HncisMessageSource.getMessage("total"));
			}
			json.put("value", "");

			jsonArr.add(json);
		}

		for(BgabGascaf02Dto clr : clrList){
			json = new JSONBaseVO();
			json.put("key"  , clr.getUnif_prn_cd());
			json.put("value", clr.getUnif_cd());
			json.put("name" , clr.getUnif_nm());

			jsonArr.add(json);
		}
		jso.put("COLOR", jsonArr);

		jsonArr = new JSONBaseArray();

		if(!comboArr[2].equals("Z")){
			json = new JSONBaseVO();
			if(comboArr[2].equals("S")){
				json.put("name", HncisMessageSource.getMessage("select"));
			}else{
				json.put("name", HncisMessageSource.getMessage("total"));
			}
			json.put("value", "");

			jsonArr.add(json);
		}

		for(BgabGascaf02Dto msm : msmList){
			json = new JSONBaseVO();
			json.put("key"  , msm.getUnif_prn_cd());
			json.put("value", msm.getUnif_cd());
			json.put("name" , msm.getUnif_nm());

			jsonArr.add(json);
		}
		jso.put("SIZE", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, jso);
		System.out.println(JSONObject.fromObject(jso).toString());

		return modelAndView;
	}




	/**
	 * uniform request search. - 화면 Request에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridListToRequest.do")
	public ModelAndView doSearchGridListToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		//조회조건 설정
		BgabGascaf01Dto dto = (BgabGascaf01Dto) getJsonToBean(paramJson, BgabGascaf01Dto.class);
		dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToRequest(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToRequest(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform Request request. - 화면 Request에서 request 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doRequestListToRequest.do")
	public ModelAndView doRequestListToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> list = (List<BgabGascaf01Dto>) getJsonToList(iParams, BgabGascaf01Dto.class);

		CommonMessage message = new CommonMessage();
		//저장
		message = uniformManager.insertListToRequest(list);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform List search. - 화면 List에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridListToList.do")
	public ModelAndView doSearchGridListToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf01Dto dto = (BgabGascaf01Dto) getJsonToBean(paramJson, BgabGascaf01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToList(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform List cancel. - 화면 List에서 cancel 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doCancelRequestToList.do")
	public ModelAndView doCancelRequestToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> dList = (List<BgabGascaf01Dto>) getJsonToList(dParams, BgabGascaf01Dto.class);

		//수정
		uniformManager.deleteRequestToList(dList);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("APPLY.0001"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform Confirm search. - 화면 Confirm에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridListToConfirm.do")
	public ModelAndView doSearchGridListToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf01Dto dto = (BgabGascaf01Dto) getJsonToBean(paramJson, BgabGascaf01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToConfirm(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToConfirm(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform Confirm search. - 화면 Confirm에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doExcelListToConfirm.excel")
	public ModelAndView doExcelListToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf01Dto dto = (BgabGascaf01Dto) getJsonToBean(paramJson, BgabGascaf01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
 		if(StringUtil.isNullToString(pageSize).equals("")){ pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = uniformManager.selectCountToConfirm(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(count);
		list.setRows(uniformManager.selectListToConfirm(dto));

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
	 * uniform Confirm confirm. - 화면 Confirm에서 confirm 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doUpdateConfirmToConfirm.do")
	public ModelAndView doUpdateConfirmToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> uList = (List<BgabGascaf01Dto>) getJsonToList(uParams, BgabGascaf01Dto.class);

		//수정
		CommonMessage message = uniformManager.updateUniformConfirmToConfirm(uList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform Confirm confirmCancel. - 화면 Confirm에서 confirm cancel 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doUpdateConfirmCancelToConfirm.do")
	public ModelAndView doUpdateConfirmCancelToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> uList = (List<BgabGascaf01Dto>) getJsonToList(uParams, BgabGascaf01Dto.class);

		//수정
		CommonMessage message = uniformManager.updateUniformConfirmCancelToConfirm(uList);

		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * unfirm ItemInfo menu search. - 화면 ItemInfo에서 Type of Clothes 자동조회시 실행되는 메서드
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchTypeofclothesListToItemInfo.do")
	public ModelAndView doSearchTypeofclothesListToItemInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascaf02Dto vo = (BgabGascaf02Dto)getJsonToBean(paramJson, BgabGascaf02Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(uniformManager.selectTypeofclothesListToItemInfo(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * unfirm ItemInfo menu search. - 화면 ItemInfo에서 Type of Clothes 클릭 시 실행
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchColorListToItemInfo.do")
	public ModelAndView doSearchColorListToItemInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascaf02Dto vo = (BgabGascaf02Dto)getJsonToBean(paramJson, BgabGascaf02Dto.class);

		System.out.println(vo.getUnif_prn_cd());

		CommonList list = new CommonList();
		//검색
		list.setRows(uniformManager.selectColorListToItemInfo(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * unfirm ItemInfo menu search. - 화면 ItemInfo에서 Color 클릭 시 실행
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchSizeListToItemInfo.do")
	public ModelAndView doSearchSizeListToItemInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascaf02Dto vo = (BgabGascaf02Dto)getJsonToBean(paramJson, BgabGascaf02Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(uniformManager.selectSizeListToItemInfo(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform ItemInfo save. - 화면 ItemInfo에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams, uParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doSaveListToItemInfo.do")
	public ModelAndView doSaveListToItemInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf02Dto> iList = (List<BgabGascaf02Dto>) getJsonToList(iParams, BgabGascaf02Dto.class);
		List<BgabGascaf02Dto> uList = (List<BgabGascaf02Dto>) getJsonToList(uParams, BgabGascaf02Dto.class);

		//수정
		uniformManager.saveListToItemInfo(iList, uList);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform ItemInfo delete. - 화면 ItemInfo에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doDeleteListToItemInfo.do")
	public ModelAndView doDeleteListToItemInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf02Dto> dList = (List<BgabGascaf02Dto>) getJsonToList(dParams, BgabGascaf02Dto.class);

		//수정
		uniformManager.deleteListToItemInfo(dList);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * uniform Stock Management search. - 화면 Stock Management에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridStockListToStockManagement.do")
	public ModelAndView doSearchGridStockListToStockManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToStockManagement(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToStockManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform Stock Management search. - 화면 Stock Management에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doExcelStockListToStockManagement.excel")
	public ModelAndView doExcelStockListToStockManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="fileName", required=false) String fileName,
			@RequestParam(value="header", required=false) String header,
			@RequestParam(value="headerName", required=false) String headerName,
			@RequestParam(value="fomatter", required=false) String fomatter,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToStockManagementList(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(count);
 		//검색
		list.setRows(uniformManager.selectListToStockManagementList(dto));

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
	 * uniform Stock Management save. - 화면 Stock Management에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doInsertStockListToStockManagement.do")
	public ModelAndView doInsertStockListToStockManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		//저장
		CommonMessage message = uniformManager.insertListToStockManagement(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform Stock Management delete. - 화면 Stock Management에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doDeleteStockListToStockManagement.do")
	public ModelAndView doDeleteStockListToStockManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf03Dto> dList = (List<BgabGascaf03Dto>) getJsonToList(dParams, BgabGascaf03Dto.class);

		//삭제
		CommonMessage message = uniformManager.deleteListToStockManagement(dList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform Stock Management delete. - 화면 Stock Management에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doRejectListToComfirmList.do")
	public ModelAndView doRejectListToComfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> dList = (List<BgabGascaf01Dto>) getJsonToList(uParams, BgabGascaf01Dto.class);

		//삭제
		CommonMessage message = uniformManager.updateUniformListToReject(dList);

		//화면의 하단 메시지 설정
		if("Y".equals(message.getResult())){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * uniform Confirm confirm. - 화면 Confirm에서 confirm 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param dParams the param json array - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/uniform/doSaveConfirmToRequest.do")
	public ModelAndView doSaveConfirmToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascaf01Dto> uList = (List<BgabGascaf01Dto>) getJsonToList(uParams, BgabGascaf01Dto.class);

		//수정
		CommonMessage message = uniformManager.updateConfirmListToRequest(uList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * uniform Stock Management search. - 화면 Stock Management에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridStockListToStockManagementList.do")
	public ModelAndView doSearchGridStockListToStockManagementList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToStockManagementList(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToStockManagementList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	/**
	 * uniform Stock Management search. - 화면 Stock Management에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridStockDetailIn.do")
	public ModelAndView doSearchGridStockDetailIn(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		CommonList list = new CommonList();
 		//검색
		list.setRows(uniformManager.selectListToStockDetailIn(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * uniform Stock Management search. - 화면 Stock Management에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doSearchGridStockDetailOut.do")
	public ModelAndView doSearchGridStockDetailOut(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = uniformManager.selectCountToStockDetailOut(dto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
		list.setRows(uniformManager.selectListToStockDetailOut(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/uniform/doSearchGridStockDetailBasic.do")
	public ModelAndView doSearchGridStockDetailBasic(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascaf03Dto dto = (BgabGascaf03Dto) getJsonToBean(paramJson, BgabGascaf03Dto.class);

		BgabGascaf03Dto rsReqDto = new BgabGascaf03Dto();
		rsReqDto = uniformManager.selectGridStockDetailBasic(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			CommonApproval commonApproval = new CommonApproval();
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
	 * uniform Stock Management save. - 화면 Stock Management에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/uniform/doInsertStockListToStockManagementList.do")
	public ModelAndView doInsertStockListToStockManagementList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=false) String paramsI,
			@RequestParam(value="paramsU", required=false) String paramsU) throws HncisException{
		//조회조건 설정
		List<BgabGascaf03Dto> gsSaveList = (List<BgabGascaf03Dto>) getJsonToList(paramsI, BgabGascaf03Dto.class);
		List<BgabGascaf03Dto> gsModifyList = (List<BgabGascaf03Dto>) getJsonToList(paramsU, BgabGascaf03Dto.class);
		//저장
		int cnt = uniformManager.insertListToStockManagementList(gsSaveList, gsModifyList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
}