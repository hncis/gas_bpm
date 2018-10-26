package com.hncis.controller.security;

import java.io.IOException;
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
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
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
import com.hncis.security.manager.SecurityManager;
import com.hncis.security.vo.BgabGascse01;
import com.hncis.security.vo.BgabGascse02;
import com.hncis.security.vo.BgabGascse03;
import com.hncis.security.vo.BgabGascse04;
import com.hncis.security.vo.BgabGascse05;
import com.hncis.security.vo.BgabGascse06;

@Controller
public class SecurityController extends AbstractController{

	@Autowired
	@Qualifier("securityManagerImpl")
	private SecurityManager securityManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * security request vehicle entrance insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestVehicle.do")
	public ModelAndView doInsertSecurityRequestVehicle(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse01> paramList = (List<BgabGascse01>)getJsonToList(paramJson, BgabGascse01.class);

		BgabGascse01 info = securityManager.insertSecurityRequestVehicle(paramList);
        
		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		// BPM API호출
		String processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
		String bizKey = info.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01520010";	//액티비티 코드 (방문차량출입 신청서작성) - 프로세스 정의서 참조
		String loginUserId = info.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01520030";   //방문객 담당자 역할코드
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");

		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityRequestVehicle.do")
	public ModelAndView doDeleteSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse01> paramList = (List<BgabGascse01>)getJsonToList(paramJson, BgabGascse01.class);

		int rs = securityManager.deleteSecurityRequestVehicle(paramList);
		if(rs>0){
			// BPM API호출
			String processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
			String bizKey = paramList.get(0).getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01520010";	//액티비티 코드 (방문차량출입 신청서작성) - 프로세스 정의서 참조
			String loginUserId = paramList.get(0).getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		}

		CommonMessage message = new CommonMessage();
		message.setCode(paramList.get(0).getApply_date());
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance search
	 */
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestVehicle.do")
	public ModelAndView doSearchSecurityRequestVehicle(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectSecurityRequestVehicle(param));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}



	/**
	 * security request Material insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestMaterial.do")
	public ModelAndView doInsertSecurityRequestMaterial(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse02> paramList = (List<BgabGascse02>)getJsonToList(paramJson, BgabGascse02.class);

		BgabGascse02 info = securityManager.insertSecurityRequestMaterial(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Material delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityRequestMaterial.do")
	public ModelAndView doDeleteSecurityRequestMaterial(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse02> paramList = (List<BgabGascse02>)getJsonToList(paramJson, BgabGascse02.class);

		securityManager.deleteSecurityRequestMaterial(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(paramList.get(0).getApply_date());
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Material search
	 */
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestMaterial.do")
	public ModelAndView doSearchSecurityRequestMaterial(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse02 param = (BgabGascse02)getJsonToBean(paramJson, BgabGascse02.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectSecurityRequestMaterial(param));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}



	/**
	 * security request Devices insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestDevices.do")
	public ModelAndView doInsertSecurityRequestDevices(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse03> paramList = (List<BgabGascse03>)getJsonToList(paramJson, BgabGascse03.class);

		BgabGascse03 info = securityManager.insertSecurityRequestDevices(paramList);

		
		// BPM API호출
		String processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
		String bizKey = info.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입 신청서작성) - 프로세스 정의서 참조
		String loginUserId = info.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01530030";   //방문IT장비반입 담당자 역할코드
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");

		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
							
		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Devices delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityRequestDevices.do")
	public ModelAndView doDeleteSecurityRequestDevices(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse03> paramList = (List<BgabGascse03>)getJsonToList(paramJson, BgabGascse03.class);

		int rs = securityManager.deleteSecurityRequestDevices(paramList);
		if(rs > 0){
			// BPM API호출
			String processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
			String bizKey = paramList.get(0).getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입 신청서작성) - 프로세스 정의서 참조
			String loginUserId = paramList.get(0).getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		}

		CommonMessage message = new CommonMessage();
		message.setCode(paramList.get(0).getApply_date());
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Devices search
	 */
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestDevices.do")
	public ModelAndView doSearchSecurityRequestDevices(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse03 param = (BgabGascse03)getJsonToBean(paramJson, BgabGascse03.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectSecurityRequestDevices(param));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}




	/**
	 * security request Film insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestFilm.do")
	public ModelAndView doInsertSecurityRequestFilm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse04> paramList = (List<BgabGascse04>)getJsonToList(paramJson, BgabGascse04.class);

		BgabGascse04 info = securityManager.insertSecurityRequestFilm(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Film delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityRequestFilm.do")
	public ModelAndView doDeleteSecurityRequestFilm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse04> paramList = (List<BgabGascse04>)getJsonToList(paramJson, BgabGascse04.class);

		securityManager.deleteSecurityRequestFilm(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(paramList.get(0).getApply_date());
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Film search
	 */
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestFilm.do")
	public ModelAndView doSearchSecurityRequestFilm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse04 param = (BgabGascse04)getJsonToBean(paramJson, BgabGascse04.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectSecurityRequestFilm(param));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}



	/**
	 * security request vehicle entrance request
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doApproveSecurityRequest.do")
	public ModelAndView doApproveSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		String rtn = securityManager.setSecurityApproval(keyParamDto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance request cancel
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doApproveCancelSecurityRequest.do")
	public ModelAndView doApproveCancelSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		CommonApproval appInfo = new CommonApproval();
		CommonApproval rtnApproval = new CommonApproval();
		CommonMessage message = new CommonMessage();
		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		message = securityManager.setSecurityApprovalCancel(keyParamDto, appInfo, message, req);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * security request confirm
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doConfirmSecurityRequestVehicle.do")
	public ModelAndView doConfirm1BCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		securityManager.updateRequestSecurityRequestVehicle(keyParamDto);

		String param1 = keyParamDto.getType();
		if(param1.equals("1") || param1.equals("3")) {
			String processCode = "";
			String statusCode = "";
			if(param1.equals("1")) {
				// BPM API호출
				processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
				statusCode = "GASBZ01520030";	//액티비티 코드 (방문차량출입 담당자확인) - 프로세스 정의서 참조
			} else{
				// BPM API호출
				processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
				statusCode = "GASBZ01530030";	//액티비티 코드 (방문IT장비반입 담당자확인) - 프로세스 정의서 참조
			}
			
			String bizKey = keyParamDto.getDoc_no();	//신청서 번호
			String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
			String comment = null;
	
			BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		}
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getKey_eeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("vst");
		String corp_cd    = keyParamDto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String email = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, email, mode, title, corp_cd);

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
	 * security request confirm cancel
	 */
	@RequestMapping(value="/hncis/security/doConfirmCancelSecurityRequestVehicle.do")
	public ModelAndView doConfirmCancelBCToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		securityManager.updateRequestSecurityRequestVehicle(keyParamDto);

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
	 * Material request confirm
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doConfirmSecurityRequestMaterial.do")
	public ModelAndView doConfirmSecurityRequestMaterial(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascse02 keyParamDto = (BgabGascse02) getJsonToBean(paramJson, BgabGascse02.class);

		securityManager.updateRequestSecurityRequestMaterial(keyParamDto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getKey_eeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("vst");
		String corp_cd    = keyParamDto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String email = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.sendEmailConfirm(fromEeno, fromStepNm, email, mode, title, corp_cd);

		// BPM API호출
		String processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
		String bizKey = keyParamDto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01510030";	//액티비티 코드 (방문객 담당자확인) - 프로세스 정의서 참조
		String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
					
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
	 * Material request confirm cancel
	 */
	@RequestMapping(value="/hncis/security/doConfirmCancelSecurityRequestMaterial.do")
	public ModelAndView doConfirmCancelSecurityRequestMaterial(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascse02 keyParamDto = (BgabGascse02) getJsonToBean(paramJson, BgabGascse02.class);

		securityManager.updateRequestSecurityRequestMaterial(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/security/doSearchSecurtyRequestType.do")
	public ModelAndView doSearchSecurtyRequestType(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		BgabGascse01 vo = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);
		BgabGascse01 resultVo = securityManager.getSelectSecurtyRequestType(vo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(resultVo != null){
			resultVo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setIf_id(vo.getIf_id());
			commonApproval.setSystem_code("SE");
			resultVo.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));

			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(resultVo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance search
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestConfirmList.do")
	public ModelAndView doSearchSecurityRequestConfirmList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();

		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = securityManager.getSelectCountSecurityRequestConfirmList(param);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(startRow);
		param.setEndRow(endRow);
		list.setRows(securityManager.getSelectSecurityRequestConfirmList(param));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * business Card request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doSearchToSecurityRequest.do")
	public ModelAndView doSearchToSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascse01 vo = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(vo != null){
			if(!"".equals(StringUtil.isNullToString(vo.getIf_id()))){
				CommonApproval commonApproval = new CommonApproval();
				commonApproval.setIf_id(vo.getIf_id());
				commonApproval.setSystem_code("SE");
				commonApproval.setCode1(vo.getType());
				commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				vo.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
			}

			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(vo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Security excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/security/doExcelToSecurityRequest.excel")
	public ModelAndView doExcelToSecurityRequest(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = securityManager.getSelectCountSecurityRequestConfirmList(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(count);
		list.setRows(securityManager.getSelectSecurityRequestConfirmList(keyParamDto));

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
	 * security request vehicle entrance search
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/security/doSearchSecurityManagerMgmtList.do")
	public ModelAndView doSearchSecurityManagerMgmtList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
		BgabGascse05 param = (BgabGascse05)getJsonToBean(paramJson, BgabGascse05.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = securityManager.getSelectSecurityManagerMgmtListCount(param);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(startRow);
		param.setEndRow(endRow);
		list.setRows(securityManager.getSelectSecurityManagerMgmtList(param));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}

	/**
	 * security Manager Mgmt insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityManagerMgmt.do")
	public ModelAndView doInsertSecurityManagerMgmt(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		List<BgabGascse05> paramList = (List<BgabGascse05>)getJsonToList(paramJson, BgabGascse05.class);

		int cnt = securityManager.getSelectSecurityManagerMgmtCheck(paramList);
		if( cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0002"));
		}else{
			securityManager.insertSecurityManagerMgmt(paramList);

			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security Manager Mgmt delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityManagerMgmt.do")
	public ModelAndView doDeleteSecurityManagerMgmt(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse05> paramList = (List<BgabGascse05>)getJsonToList(paramJson, BgabGascse05.class);

		securityManager.deleteSecurityManagerMgmt(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * picture and film Area combo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/getSearchFilmComboArea.do")
	public ModelAndView getSearchFilmComboArea(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

		JSONBaseVO    jso     = new JSONBaseVO();
		JSONBaseArray jsonArr = null;
		JSONBaseVO    json    = null;

		List<BgabGascse05> typeList = securityManager.selectFilmComboArea();

		jsonArr = new JSONBaseArray();

		for(BgabGascse05 type : typeList){
			json = new JSONBaseVO();
			json.put("key"  , "");
			json.put("value", type.getDept_cd());
			json.put("name" , type.getDept_nm());

			jsonArr.add(json);
		}
		jso.put("area", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso);
		System.out.println(JSONObject.fromObject(jso).toString());

		return modelAndView;
	}

	/**
	 * security request Entrance insert & update
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestEntrance.do")
	public ModelAndView doInsertSecurityRequestEntrance(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse06> paramList = (List<BgabGascse06>)getJsonToList(paramJson, BgabGascse06.class);

		BgabGascse06 info = securityManager.insertSecurityRequestEntrance(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		// BPM API호출
		String processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
		String bizKey = info.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01510010";	//액티비티 코드 (방문객 신청서작성) - 프로세스 정의서 참조
		String loginUserId = info.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01510030";   //방문객 담당자 역할코드
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");

		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
	

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Material delete
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteSecurityRequestEntrance.do")
	public ModelAndView doDeleteSecurityRequestEntrance(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse06> paramList = (List<BgabGascse06>)getJsonToList(paramJson, BgabGascse06.class);

		securityManager.deleteSecurityRequestEntrance(paramList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
						
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Material search
	 */
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestEntrance.do")
	public ModelAndView doSearchSecurityRequestEntrance(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse06 param = (BgabGascse06)getJsonToBean(paramJson, BgabGascse06.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectSecurityRequestEntrance(param));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * security remark Search
	 */
	@RequestMapping(value="/hncis/security/doSaerchSecurityRemark.do")
	public ModelAndView doSaerchSecurityRemark(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);
		BgabGascse01 info = securityManager.getSelectSecurityRemark(param);

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
	 * security file search
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/security/doSearchXVToFile.do")
	public ModelAndView doSearchXVToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);

		CommonList list = new CommonList();
		list.setRows(securityManager.getSelectXVToFile(btReqDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * security file save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/security/doSaveXVToFile.do")
	public void doSaveXVToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			securityManager.saveXVToFile(req, res, bgabGascZ011Dto);
		}
	}

	/**
	 * security file delete
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doDeleteXVToFile.do")
	public ModelAndView doDeleteXVToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		List<BgabGascZ011Dto> bt006Dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);

		securityManager.deleteXVToFile(bt006Dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security file down
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/security/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto btReqDto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = securityManager.getSelectXVToFileInfo(btReqDto);

		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "security");

		return new ModelAndView("download", "fileData", mpfileData);
	}

	/**
	 * security request confirm
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/security/doRejectSecurityRequest.do")
	public ModelAndView doRejectSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		int rs = securityManager.updateInfoSecurityToReject(keyParamDto, req);
		if(rs > 0){
			String param1 = keyParamDto.getType();
			if(param1.equals("1") || param1.equals("3") || param1.equals("5")) {
				String processCode = "";
				String statusCode = "";
				if(param1.equals("5")) {
					// BPM API호출
					processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01510030";	//액티비티 코드 (방문객 당당자 확인) - 프로세스 정의서 참조
				} else if(param1.equals("1")) {
					// BPM API호출
					processCode = "P-E-002"; 	//프로세스 코드 ((방문차량출입  프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01520010";	//액티비티 코드 ((방문차량출입  당당자 확인) - 프로세스 정의서 참조
				} else {
					// BPM API호출
					processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입  프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입  당당자 확인) - 프로세스 정의서 참조
				}
				String bizKey = keyParamDto.getDoc_no();	//신청서 번호
				String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			}
		}
		

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = keyParamDto.getEeno();
		String rtnText    = keyParamDto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("vst");
		String corp_cd    = keyParamDto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		message.setCode(keyParamDto.getKey_pgs_st_cd());

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance request cancel
	 */
	@RequestMapping(value="/hncis/security/doApproveSecurityEntrance.do")
	public ModelAndView doApproveSecurityEntrance(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		securityManager.updateApproveSecurityEntrance(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Entrance copy
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestEntranceCopy.do")
	public ModelAndView doInsertSecurityRequestEntranceCopy(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse06> paramList = (List<BgabGascse06>)getJsonToList(paramJson, BgabGascse06.class);

		BgabGascse06 info = securityManager.insertSecurityRequestEntranceCopy(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Devices copy
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestDevicesCopy.do")
	public ModelAndView doInsertSecurityRequestDevicesCopy(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse03> paramList = (List<BgabGascse03>)getJsonToList(paramJson, BgabGascse03.class);

		BgabGascse03 info = securityManager.insertSecurityRequestDevicesCopy(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Material copy
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestMaterialCopy.do")
	public ModelAndView doInsertSecurityRequestMaterialCopy(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse02> paramList = (List<BgabGascse02>)getJsonToList(paramJson, BgabGascse02.class);

		BgabGascse02 info = securityManager.insertSecurityRequestMaterialCopy(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance copy
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestVehicleCopy.do")
	public ModelAndView doInsertSecurityRequestVehicleCopy(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse01> paramList = (List<BgabGascse01>)getJsonToList(paramJson, BgabGascse01.class);

		BgabGascse01 info = securityManager.insertSecurityRequestVehicleCopy(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request Film copy
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/security/doInsertSecurityRequestFilmCopy.do")
	public ModelAndView doInsertSecurityRequestFilmCopy(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascse04> paramList = (List<BgabGascse04>)getJsonToList(paramJson, BgabGascse04.class);

		BgabGascse04 info = securityManager.insertSecurityRequestFilmCopy(paramList);

		CommonMessage message = new CommonMessage();
		message.setCode(info.getApply_date());
		message.setCode1(info.getDoc_no());
		message.setCode2(info.getEeno());
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance done
	 */
	@RequestMapping(value="/hncis/security/doDoneSecurityRequest.do")
	public ModelAndView doDoneSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		securityManager.updateApproveSecurityEntrance(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DONE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * security request vehicle entrance done cancel
	 */
	@RequestMapping(value="/hncis/security/doDoneCancelSecurityRequest.do")
	public ModelAndView doDoneCancelSecurityRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String paramJson)throws HncisException{
		ModelAndView modelAndView = null;

		BgabGascse01 keyParamDto = (BgabGascse01) getJsonToBean(paramJson, BgabGascse01.class);

		securityManager.updateApproveSecurityEntrance(keyParamDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DONE.0003"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * post office confirm excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/security/doExcelSecurityRequestConfirmList.excel")
	public ModelAndView doExcelSecurityRequestConfirmList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = 0;

		if("5".equals(param.getType())){
			count = securityManager.selectCountSecurityVistiorCustomer(param);
		}else{
			count = securityManager.getSelectCountSecurityRequestList(param);
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(startRow);
		param.setEndRow(count);
		if("5".equals(param.getType())){
			list.setRows(securityManager.selectSecurityVistiorCustomer(param));
		}else{
			list.setRows(securityManager.getSelectSecurityRequestList(param));
		}

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

	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/security/doSearchSecurityVistiorCustomer.do")
	public ModelAndView doSearchSecurityVistiorCustomer(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = securityManager.selectCountSecurityVistiorCustomer(param);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(startRow);
		param.setEndRow(endRow);
		list.setRows(securityManager.selectSecurityVistiorCustomer(param));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}


	/**
	 * security request vehicle entrance search
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/hncis/security/doSearchSecurityRequestList.do")
	public ModelAndView doSearchSecurityRequestList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try{
		BgabGascse01 param = (BgabGascse01)getJsonToBean(paramJson, BgabGascse01.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = securityManager.getSelectCountSecurityRequestList(param);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		param.setStartRow(startRow);
		param.setEndRow(endRow);
		list.setRows(securityManager.getSelectSecurityRequestList(param));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return modelAndView;
		}
	}
}
