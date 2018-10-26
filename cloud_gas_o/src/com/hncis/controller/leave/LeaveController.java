package com.hncis.controller.leave;

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
import com.hncis.leave.manager.LeaveManager;
import com.hncis.leave.vo.BgabGasclv01Dto;
import com.hncis.leave.vo.BgabGasclv03Dto;

@Controller
public class LeaveController extends AbstractController{

	@Autowired
	@Qualifier("leaveManagerImpl")
	private LeaveManager leaveManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@RequestMapping(value="/hncis/leave/doSearchLvToRemainDaysInfo.do")
	public ModelAndView doSearchLvToRemainDaysInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		BgabGasclv01Dto rsReqDto = leaveManager.selectLvToRemainDaysInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGasclv01Dto();
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/leave/doSaveLvToRequestInfo.do")
	public ModelAndView doSaveLvToRequestInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		CommonMessage message = leaveManager.insertLvToRequestInfo(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doSearchLvToRequestInfo.do")
	public ModelAndView doSearchLvToRequestInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		BgabGasclv01Dto rsReqDto = leaveManager.selectLvToRequestInfo(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto != null){
			if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){
				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rsReqDto.getIf_id());
				commonApproval.setSystem_code("LV");
				commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		}else{
			rsReqDto = new BgabGasclv01Dto();
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doSearchLvToHistoryList.do")
	public ModelAndView doSearchLvToHistoryList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasclv01Dto vo = (BgabGasclv01Dto)getJsonToBean(paramJson, BgabGasclv01Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(leaveManager.selectLvToHistoryList(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	@RequestMapping(value="/hncis/leave/doApproveLvToRequest.do")
	public ModelAndView doApproveLvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGasclv01Dto keyParamDto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		CommonMessage appMessage = leaveManager.setApproval(keyParamDto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(appMessage).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doApproveCancelLvToRequest.do")
	public ModelAndView doApproveCancelLvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonApproval rtnApproval = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGasclv01Dto keyParamDto = (BgabGasclv01Dto) getJsonToBean(paramJson,BgabGasclv01Dto.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		message = leaveManager.setApprovalCancel(keyParamDto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doConfirmLvToRequest.do")
	public ModelAndView doConfirmLvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		message = leaveManager.updateLvToRequestForConfirm(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("xlv");
		String corp_cd    = dto.getCorp_cd();
		
		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doRejectLvToRequest.do")
	public ModelAndView doRejectLvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		message = leaveManager.updateLvToRequestForReject(dto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("xlv");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/leave/doDeleteLvToRequest.do")
	public ModelAndView doDeleteLvToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		message = leaveManager.deleteLvToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/leave/doSearchLvToReqList.do")
	public ModelAndView doSearchLvToReqList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGasclv01Dto dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = leaveManager.selectCountLvToReqList(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(leaveManager.selectLvToReqList(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}





	@RequestMapping(value="/hncis/leave/doUpdateLvToLeaveDayInfo.do")
	public ModelAndView doUpdateLvToLeaveDayInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGasclv03Dto dto = (BgabGasclv03Dto) getJsonToBean(paramJson, BgabGasclv03Dto.class);

		leaveManager.updateLvToLeaveDayInfo(dto);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/leave/doSearchLvToLeaveDayInfo.do")
	public ModelAndView doSearchLvToLeaveDayInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGasclv03Dto dto = (BgabGasclv03Dto) getJsonToBean(paramJson, BgabGasclv03Dto.class);

		BgabGasclv03Dto rsReqDto = leaveManager.selectLvToLeaveDayInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGasclv03Dto();
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
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
	@RequestMapping(value="/hncis/leave/doExcelLeaveList.excel")
	public ModelAndView doExcelLeaveList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGasclv01Dto bgabGasclv01Dto = (BgabGasclv01Dto) getJsonToBean(paramJson, BgabGasclv01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = leaveManager.selectCountLvToReqList(bgabGasclv01Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGasclv01Dto.setStartRow(1);
 		bgabGasclv01Dto.setEndRow(count);
 		//검색
 		list.setRows(leaveManager.selectLvToReqList(bgabGasclv01Dto));

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
