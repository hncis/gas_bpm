package com.hncis.controller.training;

import java.util.ArrayList;
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

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.training.manager.TrainingManager;
import com.hncis.training.vo.BgabGasctr01;

@Controller
public class TrainingController extends AbstractController{

	@Autowired
	@Qualifier("trainingManagerImpl")
	private TrainingManager trainingManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * training request apply
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/training/doInsertTRToRequest.do")
	public ModelAndView doInsertTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGasctr01 cgabGasctr01 = (BgabGasctr01)getJsonToBean(bsicInfo, BgabGasctr01.class);


		String doc_no = StringUtil.getDocNo();
		cgabGasctr01.setDoc_no(doc_no);

		Integer cnt = (Integer)trainingManager.insertInfoTRToRequest(cgabGasctr01);

		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(cgabGasctr01.getDoc_no());
			
			// BPM API호출
			String processCode = "P-B-005"; 	//프로세스 코드 (교육신청 프로세스) - 프로세스 정의서 참조
			String bizKey = cgabGasctr01.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01250010";	//액티비티 코드 (교육신청신청서작성) - 프로세스 정의서 참조
			String loginUserId = cgabGasctr01.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01250030";  //교육신청 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");

			BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
		
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * training request modify
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doModifyTRToRequest.do")
	public ModelAndView doModifyTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGasctr01 cgabGasctr01 = (BgabGasctr01)getJsonToBean(bsicInfo, BgabGasctr01.class);

		trainingManager.updateInfoTRToRequest(cgabGasctr01);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setCode(cgabGasctr01.getDoc_no());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * training request delete
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doDeleteTRToRequest.do")
	public ModelAndView doDeleteTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGasctr01 keyParamDto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		trainingManager.deleteInfoTRToRequest(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Training request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doSelectInfoTRToRequest.do")
	public ModelAndView doSelectInfoTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasctr01 keyParamDto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		BgabGasctr01 rsReqDto = new BgabGasctr01();
		rsReqDto = trainingManager.getSelectInfoTRToRequest(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);

		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rsReqDto.getIf_id());
				commonApproval.setSystem_code("TR");
				commonApproval.setCorp_cd(keyParamDto.getCorp_cd());

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
	 * Training request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doSelectInfoTRToRequestByIfId.do")
	public ModelAndView doSelectInfoTRToRequestByIfId(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasctr01 keyParamDto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		BgabGasctr01 rsReqDto = new BgabGasctr01();
		rsReqDto = trainingManager.getSelectInfoTRToRequestByIfId(keyParamDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);

		if(rsReqDto != null){
			rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(rsReqDto.getIf_id());
				commonApproval.setSystem_code("TR");
				commonApproval.setCorp_cd(keyParamDto.getCorp_cd());

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
	 * training list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doSelectListTRToRequest.do")
	public ModelAndView doSearchListTrToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = trainingManager.getSelectCountTRToAccept(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);

 		list.setRows(trainingManager.getSelectListTRToRequest(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * training list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doSelectListTRToAccept.do")
	public ModelAndView doSearchListTrToAccept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasctr01 keyParamDto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = trainingManager.getSelectCountTRToAccept(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(trainingManager.getSelectListTRToAccept(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
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
	@RequestMapping(value="/hncis/training/doApproveTRToRequest.do")
	public ModelAndView doApproveTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

//		CommonMessage message = new CommonMessage();
//		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);
//
//		trainingManager.setApproveTRToRequest(dto);
//
//		message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
//		message.setCode(dto.getPgs_st_cd());

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		message = trainingManager.setApproveTRToRequest(dto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * training request approve
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doApproveCancelTRToRequest.do")
	public ModelAndView doApproveCancelTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);
		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		dto.setCorp_cd(dto.getCorp_cd());

		message = trainingManager.setApproveCancelTRToRequest(dto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

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
	@RequestMapping(value="/hncis/training/doConfirmTRToRequest.do")
	public ModelAndView doConfirmTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		message = trainingManager.updateConfirmTRToRequest(dto);
//				trainingManager.setConfirmTRToRequest(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("edu_req");
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
	@RequestMapping(value="/hncis/training/doRejectTRToRequest.do")
	public ModelAndView doRejectTRToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasctr01 keyParamDto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		CommonMessage message = trainingManager.updateInfoTrToReject(keyParamDto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getEeno();
		String rtnText    = keyParamDto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("edu_req");
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

		return modelAndView;
	}

	/**
	 * training confirm cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/training/doConfirmCancelTRToRequest.do")
	public ModelAndView doConfirmCancelTXToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		CommonMessage message = new CommonMessage();
		BgabGasctr01 dto = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		trainingManager.setConfirmCancelTRToRequest(dto);

		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		message.setCode(dto.getPgs_st_cd());

		modelAndView = new ModelAndView();
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
	@RequestMapping(value="/hncis/training/doExcelTrainingList.excel")
	public ModelAndView doExcelTrainingList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGasctr01 bgabGasctr01 = (BgabGasctr01) getJsonToBean(paramJson, BgabGasctr01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = trainingManager.getSelectCountTRToAccept(bgabGasctr01);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGasctr01.setStartRow(1);
 		bgabGasctr01.setEndRow(count);
 		//검색
 		list.setRows(trainingManager.getSelectListTRToAccept(bgabGasctr01));

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
