package com.hncis.controller.fuelCost;

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
import com.hncis.fuelCost.manager.FuelCostManager;
import com.hncis.fuelCost.vo.BgabGascfc01Dto;
import com.hncis.fuelCost.vo.BgabGascfc02Dto;

@Controller
public class FuelCostController extends AbstractController{

	@Autowired
    @Qualifier("fuelCostManagerImpl")
	private FuelCostManager fuelCostManager;

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@RequestMapping(value="/hncis/fuelCost/doSaveXfc01Info.do")
	public ModelAndView doSaveXfc01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = fuelCostManager.saveXfc01Info(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doDeleteXfc01Info.do")
	public ModelAndView doDeleteXfc01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		CommonMessage message = fuelCostManager.deleteXfc01Info(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc01Info.do")
	public ModelAndView doSearchXfc01Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		BgabGascfc01Dto rtnVo = fuelCostManager.selectXfc01Info(dto);

		if(rtnVo != null){
			if(!StringUtil.isNullToString(rtnVo.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rtnVo.getIf_id());
				commonApproval.setSystem_code("FC");
				commonApproval.setCorp_cd(dto.getCorp_cd());

				rtnVo.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnVo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc01InfoByIfId.do")
	public ModelAndView doSearchXfc01InfoByIfId(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		BgabGascfc01Dto rtnVo = fuelCostManager.selectXfc01InfoByIfId(dto);

		if(rtnVo != null){
			if(!StringUtil.isNullToString(rtnVo.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rtnVo.getIf_id());
				commonApproval.setSystem_code("FC");
				commonApproval.setCorp_cd(dto.getCorp_cd());

				rtnVo.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnVo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc01InfoList.do")
	public ModelAndView doSearchXfc01InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = fuelCostManager.selectXfc01InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(fuelCostManager.selectXfc01InfoList(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchFuelCostCal.do")
	public ModelAndView doSearchFuelCostCal(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		BgabGascfc01Dto rtnVo = fuelCostManager.selectFuelCostCal(dto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnVo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doUpdateXfcRequest.do")
	public ModelAndView doUpdateXfcRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();

		message = fuelCostManager.updateXfcRequest(dto, appInfo, message, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doUpdateXfcRequestCancel.do")
	public ModelAndView doUpdateXfcRequestCancel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonApproval appInfo = new CommonApproval();
		dto.setCorp_cd(dto.getCorp_cd());

		CommonMessage message = new CommonMessage();
		message = fuelCostManager.updateXfcRequestCancel(dto, appInfo, message, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doUpdateXfcConfirm.do")
	public ModelAndView doUpdateXfcConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = fuelCostManager.updateXfcConfirm(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("fuel_cost");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doUpdateXfcReject.do")
	public ModelAndView doUpdateXfcReject(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = fuelCostManager.updateXfcToReject(dto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("fuel_cost");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfcRequestInfoList.do")
	public ModelAndView doSearchXfcRequestInfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView  = new ModelAndView();
		
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = fuelCostManager.selectXfcRequestInfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(fuelCostManager.selectXfcRequestInfoList(dto));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc04InfoList.do")
	public ModelAndView doSearchXfc04InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc01Dto dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = fuelCostManager.selectXfc04InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(fuelCostManager.selectXfc04InfoList(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSaveXfc05Info.do")
	public ModelAndView doSaveXfc05Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascfc02Dto dto = (BgabGascfc02Dto) getJsonToBean(paramJson, BgabGascfc02Dto.class);
		dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		CommonMessage message = fuelCostManager.saveXfc05Info(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc05Info.do")
	public ModelAndView doSearchXfc05Info(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc02Dto dto = (BgabGascfc02Dto) getJsonToBean(paramJson, BgabGascfc02Dto.class);

		BgabGascfc02Dto rtnVo = fuelCostManager.selectXfc05Info(dto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rtnVo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/fuelCost/doSearchXfc05InfoList.do")
	public ModelAndView doSearchXfc05InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfc02Dto dto = (BgabGascfc02Dto) getJsonToBean(paramJson, BgabGascfc02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = fuelCostManager.selectXfc05InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(fuelCostManager.selectXfc05InfoList(dto));

		modelAndView = new ModelAndView();
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
	@RequestMapping(value="/hncis/fuelCost/doExcelFuelCostList.excel")
	public ModelAndView doExcelFuelCostList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascfc01Dto bgabGascfc01Dto = (BgabGascfc01Dto) getJsonToBean(paramJson, BgabGascfc01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = fuelCostManager.selectXfcRequestInfoListCount(bgabGascfc01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascfc01Dto.setStartRow(1);
 		bgabGascfc01Dto.setEndRow(count);
 		//검색
 		list.setRows(fuelCostManager.selectXfcRequestInfoList(bgabGascfc01Dto));

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