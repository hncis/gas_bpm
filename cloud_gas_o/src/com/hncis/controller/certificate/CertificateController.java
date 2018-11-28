package com.hncis.controller.certificate;

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

import com.hncis.certificate.manager.CertificateManager;
import com.hncis.certificate.vo.BgabGascce01;
import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
//import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;

@Controller
public class CertificateController extends AbstractController{

	@Autowired
	@Qualifier("certificateManagerImpl")
	private CertificateManager certificateManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@RequestMapping(value="/hncis/certificate/doInsertCertificateToRequest.do")
	public ModelAndView doInsertCertificateToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);

		if("".equals(dto.getHid_doc_no())){
			String doc_no = StringUtil.getDocNo();
			dto.setDoc_no(doc_no);
		}

		int cnt = certificateManager.insertCertificateToRequest(dto);
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(dto.getDoc_no());
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doDeleteCertificateToRequest.do")
	public ModelAndView doDeleteCertificateToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);

		int cnt = certificateManager.deleteCertificateToRequest(dto);
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode(dto.getDoc_no());
		}else{
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doSearchCertificateToRequest.do")
	public ModelAndView doSearchCertificateToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		try{
			BgabGascce01 dto = (BgabGascce01) getJsonToBean(paramJson, BgabGascce01.class);

			BgabGascce01 rsReqDto = certificateManager.getSelectCertificateToRequest(dto);

			if(rsReqDto == null){
				rsReqDto = new BgabGascce01();
			}else{
				if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

					CommonApproval commonApproval = new CommonApproval();
					commonApproval.setIf_id(rsReqDto.getIf_id());
					commonApproval.setSystem_code("CE");
					commonApproval.setCorp_cd(dto.getCorp_cd());

					rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
				}
			}

			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
			modelAndView.addObject("uiType", "ajax");
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doSearchGridCertificateToList.do")
	public ModelAndView doSearchGridCertificateToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascce01 keyParamDto = (BgabGascce01) getJsonToBean(paramJson, BgabGascce01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = certificateManager.getSelectGridCertificateToListCount(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(certificateManager.getSelectGridCertificateToList(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCertificateToRequest.do")
	public ModelAndView doApproveCertificateToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);
		CommonApproval appInfo = new CommonApproval();

		message = certificateManager.updateCertificateToApprove(dto, appInfo, message, req);

//		int cnt = certificateManager.updateCertificateToApprove(dto);
//		if(cnt > 0){
//			message.setMessage(HncisMessageSource.getMessage("APPROVE.0000"));
//			message.setCode(dto.getDoc_no());
//		}else{
//			message.setMessage(HncisMessageSource.getMessage("APPROVE.0001"));
//		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCancelCertificateToRequest.do")
	public ModelAndView doApproveCancelCertificateToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);
		CommonApproval appInfo = new CommonApproval();

		message = certificateManager.updateCertificateToApproveCancel(dto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCertificateToConfirm.do")
	public ModelAndView doApproveCertificateToConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("cert");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

//		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		int cnt = certificateManager.updateCertificateToConfirm(dto);
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode(dto.getDoc_no());
		}else{
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCancelCertificateToReject.do")
	public ModelAndView doApproveCancelCertificateToReject(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGascce01 dto = (BgabGascce01)getJsonToBean(bsicInfo, BgabGascce01.class);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("cert");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

//		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

		int cnt = certificateManager.updateCertificateToReject(dto);
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode(dto.getDoc_no());
		}else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCertificateToConfirmList.do")
	public ModelAndView doApproveCertificateToConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String params)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		List<BgabGascce01> list = (List<BgabGascce01>)getJsonToList(params, BgabGascce01.class);

		int cnt = 0;
		for(int i=0; i<list.size(); i++){
			cnt = certificateManager.updateCertificateToConfirm(list.get(i));
		}
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/certificate/doApproveCancelCertificateToRejectList.do")
	public ModelAndView doApproveCancelCertificateToRejectList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String params)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		List<BgabGascce01> list = (List<BgabGascce01>)getJsonToList(params, BgabGascce01.class);

		int cnt = 0;
		for(int i=0; i<list.size(); i++){
			cnt = certificateManager.updateCertificateToReject(list.get(i));
		}
		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
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
	@RequestMapping(value="/hncis/certificate/doExcelCertificateList.excel")
	public ModelAndView doExcelCertificateList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascce01 bgabGascce01 = (BgabGascce01) getJsonToBean(paramJson, BgabGascce01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = certificateManager.getSelectGridCertificateToListCount(bgabGascce01);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascce01.setStartRow(1);
 		bgabGascce01.setEndRow(count);
 		//검색
 		list.setRows(certificateManager.getSelectGridCertificateToList(bgabGascce01));

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
