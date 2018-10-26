package com.hncis.controller.familyJob;

import java.io.IOException;
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
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.familyJob.manager.FamilyJobManager;
import com.hncis.familyJob.vo.BgabGascfj01Dto;
import com.hncis.familyJob.vo.BgabGascfj02Dto;

@Controller
public class FamilyJobController extends AbstractController{

	@Autowired
    @Qualifier("familyJobManagerImpl")
	private FamilyJobManager familyJobManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@RequestMapping(value="/hncis/familyJob/doSearchGbListToFamilyJob.do")
	public ModelAndView doSearchGbListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascfj02Dto vo = (BgabGascfj02Dto)getJsonToBean(paramJson, BgabGascfj02Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(familyJobManager.selectGbListToFamilyJob(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doSaveGbListToFamilyJob.do")
	public ModelAndView doSaveGbListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		List<BgabGascfj02Dto> list = (List<BgabGascfj02Dto>) getJsonToList(paramJson, BgabGascfj02Dto.class);

		familyJobManager.saveRcToGbListToFamilyJob(list);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doDeleteGbListToFamilyJob.do")
	public ModelAndView doDeleteGbListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		List<BgabGascfj02Dto> list = (List<BgabGascfj02Dto>) getJsonToList(paramJson, BgabGascfj02Dto.class);

		familyJobManager.deleteRcToGbListToFamilyJob(list);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/familyJob/doSearchRelListToFamilyJob.do")
	public ModelAndView doSearchRelListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascfj02Dto vo = (BgabGascfj02Dto)getJsonToBean(paramJson, BgabGascfj02Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(familyJobManager.selectRelListToFamilyJob(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doSaveRelListToFamilyJob.do")
	public ModelAndView doSaveRelListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		List<BgabGascfj02Dto> list = (List<BgabGascfj02Dto>) getJsonToList(paramJson, BgabGascfj02Dto.class);

		familyJobManager.saveRcToRelListToFamilyJob(list);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doDeleteRelListToFamilyJob.do")
	public ModelAndView doDeleteRelListToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		List<BgabGascfj02Dto> list = (List<BgabGascfj02Dto>) getJsonToList(paramJson, BgabGascfj02Dto.class);

		familyJobManager.deleteRcToRelListToFamilyJob(list);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/familyJob/doSearchToFamilyJobCombo.do")
	public ModelAndView doSearchToFamilyJobCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

		BgabGascfj02Dto vo = (BgabGascfj02Dto)getJsonToBean(paramJson, BgabGascfj02Dto.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGascfj02Dto> codeList = null;

			jsonArr = new JSONBaseArray();

			
			codeList = familyJobManager.selectToFamilyJobCombo(vo);

			for(BgabGascfj02Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getF_code()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getF_gubun()));
				json.put("key", StringUtil.isNullToStrTrm(targetBean.getF_type()));
				json.put("item", StringUtil.isNullToStrTrm(targetBean.getF_item()));

				jsonArr.add(json);
			}

			jso.put("F_GUBUN", jsonArr);
			
			codeList = familyJobManager.selectToFamilyJobCombo2(vo);

			for(BgabGascfj02Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getR_seq()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getR_rel()));
				json.put("key", StringUtil.isNullToStrTrm(targetBean.getR_type()));
				json.put("pay", StringUtil.formatComma(StringUtil.isNullToString(targetBean.getR_compay(),"0")));
				

				jsonArr.add(json);
			}

			jso.put("F_REL", jsonArr);
			
			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/familyJob/doSaveToFamilyJob.do")
	public ModelAndView doSaveRcToRestCenterList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascfj01Dto vo = (BgabGascfj01Dto)getJsonToBean(paramJson, BgabGascfj01Dto.class);
		
		if("".equals(vo.getDoc_no())){
			String doc_no = StringUtil.getDocNo();
			vo.setDoc_no(doc_no);
		}

		familyJobManager.saveToFamilyJob(vo);

		CommonMessage message = new CommonMessage();
		message.setCode(vo.getDoc_no());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/familyJob/doDeleteToFamilyJob.do")
	public ModelAndView doDeleteToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascfj01Dto vo = (BgabGascfj01Dto)getJsonToBean(paramJson, BgabGascfj01Dto.class);
		
		int rs = familyJobManager.deleteToFamilyJob(vo);
		if(rs>0){
			// BPM API호출
			String processCode = "P-B-006"; 	//프로세스 코드 (경조사  프로세스) - 프로세스 정의서 참조
			String bizKey = vo.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01260010";	//액티비티 코드 (경조사신청서작성) - 프로세스 정의서 참조
			String loginUserId = vo.getUpdr_eeno();	//로그인 사용자 아이디
		
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
									
		}
		
		CommonMessage message = new CommonMessage();
		message.setCode(vo.getDoc_no());
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}

	@RequestMapping(value="/hncis/familyJob/doSearchToFamilyJob.do")
	public ModelAndView doSearchToFamilyJob(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();

		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		BgabGascfj01Dto rsReqDto = familyJobManager.selectToFamilyJob(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascfj01Dto();
		}else{
			if(rsReqDto != null){
				rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

				if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

					CommonApproval commonApproval = new CommonApproval();
					commonApproval.setIf_id(rsReqDto.getIf_id());
					commonApproval.setSystem_code("FJ");
					commonApproval.setCorp_cd(dto.getCorp_cd());

					rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
				}

				modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
			}
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/familyJob/doSearchToFamilyJobList.do")
	public ModelAndView doSearchRcToReqList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = familyJobManager.selectToFamilyJobListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(familyJobManager.selectToFamilyJobList(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/familyJob/doSaveFjToFile.do")
	public void doSaveFjToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{
		
		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			familyJobManager.saveFjToFile(req, res, bgabGascZ011Dto);
		}
	}
	
	@RequestMapping(value="/hncis/familyJob/doSearchFjToFile.do")
	public ModelAndView doSearchFjToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		
		CommonList list = new CommonList();
		list.setRows(familyJobManager.getSelectFjToFile(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = familyJobManager.getSelectFjToFileInfo(dto);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "familyJob");
		
		return new ModelAndView("download", "fileData", mpfileData);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doDeleteFjToFile.do")
	public ModelAndView doDeleteFjToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{
		
		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);
		
		familyJobManager.deleteFjToFile(dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}

	@RequestMapping(value="/hncis/familyJob/doApproveFjToRequest.do")
	public ModelAndView doApproveFjToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		message = familyJobManager.updateFjToRequestForApprove(dto, appInfo, message, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/familyJob/doApproveCancelFjToRequest.do")
	public ModelAndView doApproveCancelFjToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		CommonApproval appInfo = new CommonApproval();
		dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
			message = familyJobManager.updateFjToRequestForApproveCancel(dto);
		}else{
			message = familyJobManager.setApprovalCancel(dto, appInfo, message, req);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/familyJob/doConfirmFjToRequest.do")
	public ModelAndView doConfirmRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		message = familyJobManager.updateFjToRequestForConfirm(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("familyJob");
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
	
	@RequestMapping(value="/hncis/familyJob/doRejectFjToRequest.do")
	public ModelAndView doRejectRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		message = familyJobManager.updateFjToRequestForReject(dto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("familyJob");
		String corp_cd 	  = dto.getCorp_cd();

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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/familyJob/doExcelFamilyJobList.excel")
	public ModelAndView doExcelFamilyJobList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
 
		//조회조건 설정
		BgabGascfj01Dto dto = (BgabGascfj01Dto) getJsonToBean(paramJson, BgabGascfj01Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = familyJobManager.selectToFamilyJobListCount(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(1);
 		dto.setEndRow(count);
 		//검색
 		list.setRows(familyJobManager.selectToFamilyJobList(dto));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("jobName",   "familyJob");
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}
}
