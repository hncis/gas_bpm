package com.hncis.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.common.Constant;
import com.hncis.common.application.CommonGasc;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.message.JSPMessageSource;
import com.hncis.common.util.RSA;
import com.hncis.common.util.SHA256Util;
import com.hncis.common.util.SendMail;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz034Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.common.vo.HncisCommon;
import com.hncis.controller.AbstractController;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz017Dto;
import com.hncis.system.vo.BgabGascz020Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz035Dto;


@Controller
public class CommonController extends AbstractController{

	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	private static Log log = LogFactory.getLog(CommonController.class);

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCommonCombo.do")
	public ModelAndView getCommonCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		CommonCode code = null;
		List<CommonCode> codeList = null;

//		System.out.println(LocaleContextHolder.getLocale());
//		System.out.println(req.getSession().getAttribute("reqLocale"));
		String locale = req.getSession().getAttribute("reqLocale").toString();

		String corpCd = "";

		try{
			corpCd = SessionInfo.getSess_corp_cd(req);
		}catch(Exception e){
			e.printStackTrace();
		}

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				code.setLocale(locale);
				code.setCorp_cd(corpCd);
				codeList = commonManager.getCodeList(code);

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

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCommonComboByTask.do")
	public ModelAndView getCommonComboByTask(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		CommonCode code = null;
		List<CommonCode> codeList = null;

//		System.out.println(LocaleContextHolder.getLocale());
//		System.out.println(req.getSession().getAttribute("reqLocale"));
		String locale = req.getSession().getAttribute("reqLocale").toString();

		String corpCd = "";

		try{
			corpCd = SessionInfo.getSess_corp_cd(req);
		}catch(Exception e){
			e.printStackTrace();
		}

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				code.setLocale(locale);
				code.setCorp_cd(corpCd);
				codeList = commonManager.getCodeList(code);

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

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCommonComboNotIncludeAply.do")
	public ModelAndView getCommonComboNotIncludeAply(HttpServletRequest req, HttpServletResponse res,
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
				codeList = commonManager.getCodeListNotIncludeAply(code);

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
	}//end method

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCommonMultiCombo.do")
	public ModelAndView getCommonMultiCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		System.out.println("getCommonMultiCombo 입장");

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;
		String corpCd     = "";

		try{
			corpCd = SessionInfo.getSess_corp_cd(req);
		}catch(Exception e){
			e.printStackTrace();
		}

		CommonCode code = null;
		List<CommonCode> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				code.setLocale(req.getSession().getAttribute("reqLocale").toString());
				code.setCorp_cd(corpCd);
				codeList = commonManager.getCodeList(code);

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
		System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCommonMultiComboByTask.do")
	public ModelAndView getCommonMultiComboByTask(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		System.out.println("getCommonMultiCombo 입장");

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;
		String corpCd     = "";

		try{
			corpCd = SessionInfo.getSess_corp_cd(req);
		}catch(Exception e){
			e.printStackTrace();
		}

		CommonCode code = null;
		List<CommonCode> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				code.setLocale(req.getSession().getAttribute("reqLocale").toString());
				code.setCorp_cd(corpCd);
				codeList = commonManager.getCodeList(code);

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
		System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@SuppressWarnings("finally")
	@RequestMapping(value="/doLogin.do")
	public ModelAndView doLogin(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

 		BgabGascz002Dto bgabGascz002Dto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);

 		ModelAndView modelAndView = new ModelAndView();
 		CommonMessage message = new CommonMessage();
 		String locale = bgabGascz002Dto.getLocale();
 		System.out.println(locale);
 		
 		String code = "";
 		String checkVal = "";
	 	boolean loginCheckVal = true;

// 		String sXusrEmpnoTemp = Base64.decode(bgabGascz002Dto.getXusr_empno());
// 		String sXusrEmpno     = sXusrEmpnoTemp.replaceAll("[^0-9]","");

 		String sXusrEmpno = "";
 		String sXusrPswd  = "";
 		String sCorpCd = bgabGascz002Dto.getCorp_cd();
 		String sAdminCorpCd = "admin";
 		if("Y".equals(bgabGascz002Dto.getIs_xgac())){
 			bgabGascz002Dto.setCorp_cd(sAdminCorpCd);
 		}
 		
 		HttpSession session = req.getSession();
 		PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_Key_");
 		
 		String error = JSPMessageSource.getMessage("MSG.VAL.0064",new Locale (CommonGasc.getLocale(bgabGascz002Dto.getCorp_cd())));

 		try{
	 		if(privateKey == null){
	 			checkVal = 	error;
	 		}else{
 				//암호화처리된 사용자계정정보를 복호화 처리한다.
 				sXusrEmpno = RSA.decryptRsa(privateKey, bgabGascz002Dto.getXusr_empno());
 				sXusrPswd  = RSA.decryptRsa(privateKey, bgabGascz002Dto.getXusr_pswd());

 				//String salt = SHA256Util.generateSalt();
 				System.out.println("sXusrPswd:"+sXusrPswd);
 				System.out.println("sXusrEmpno:"+sXusrEmpno);
 	            String newPassword = SHA256Util.getEncrypt(sXusrPswd, sXusrEmpno);

 				System.out.println("sXusrPswd2:"+newPassword);

 		 		bgabGascz002Dto.setXusr_empno(sXusrEmpno);
 		 		bgabGascz002Dto.setXusr_pswd (newPassword);

 		 		if(StringUtil.isNullToString(bgabGascz002Dto.getLogin_type()).equals("Y")){

	 				String loginPw = commonManager.selectUserPwInfo(bgabGascz002Dto);

	 				System.out.println("loginPw     :"+loginPw);
	 				System.out.println("getXusr_pswd:"+bgabGascz002Dto.getXusr_pswd());

	 				if(loginPw == null || !bgabGascz002Dto.getXusr_pswd().equals(loginPw) ){
	 					loginCheckVal = false;
	 				}

					if(!loginCheckVal){
						checkVal = error;
					}

 		 		}

 		 		if(loginCheckVal){
 			 		BgabGascz002Dto xocxusrInfo = commonManager.getXusrInfo(bgabGascz002Dto);
 			 		if(xocxusrInfo != null){
 			 			if("Y".equals(bgabGascz002Dto.getIs_xgac())){
 			 				xocxusrInfo.setAdm_corp_cd(sAdminCorpCd);
 			 			}
 			 			
 			 			//xocxusrInfo.setLocale(CommonGasc.getSelectLoc(sCorpCd));
 			 			commonManager.setSessionInfo(req, xocxusrInfo);
 			 			code = SessionInfo.getSess_token_key(req);
 						checkVal = "Y";
 						commonManager.insertLoginUserLog(xocxusrInfo);

 			 		}else{
 			 			checkVal = HncisMessageSource.getMessage("MSG.VAL.0064");
 			 		}
 		 		}
	 		}
 		}catch(Exception e){
 			checkVal = HncisMessageSource.getMessage("MSG.VAL.0064");
 			e.printStackTrace();
 		}finally{
 			message.setMessage(checkVal);
 			message.setCode(code);

 			modelAndView.setViewName(DATA_JSON_PAGE);
 			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
 			modelAndView.addObject("uiType", "ajax");

 			return modelAndView;
 		}
	}

	/**
	 * business Card request information search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/doSearchToEmpty.do")
	public ModelAndView doSearchToRequestByEmpty(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		CommonList list = new CommonList();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	@RequestMapping(value="/doSearchToUserInfo.do")
	public ModelAndView doSearchToUserInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException, SessionException{

		BgabGascz002Dto userDto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		userDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
		BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(userDetailInfo != null){
			userDetailInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userDetailInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0002"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doSearchToDeptInfo.do")
	public ModelAndView doSearchToDeptInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException, SessionException{

		BgabGascz003Dto deptDto = (BgabGascz003Dto) getJsonToBean(paramJson, BgabGascz003Dto.class);
		deptDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		BgabGascz003Dto deptDetailInfo = commonManager.getSelectDeptInfo(deptDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(deptDetailInfo != null){
			deptDetailInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(deptDetailInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 *
	 * 로그아웃을 처리한다.
	 * @param request HttpServletRequest
	 * @return org.springframework.web.servlet.ModelAndView
	 */
	@RequestMapping(value="/logOut.do")
	public ModelAndView logOut(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		removeSession(request, Constant.SESSION_USER_KEY);

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		CommonMessage message = new CommonMessage();
		message.setMessage("");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		return modelAndView;
	}

	@RequestMapping(value="/calendar.do")
	public ModelAndView getCalendar(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="gubun", required = false) String gubun,
		@RequestParam(value="ymd", required = false) String ymd,
		@RequestParam(value="oduRegnCd", required = false) String oduRegnCd) throws HncisException, SessionException{

		ModelAndView modelAndView = new ModelAndView();

		String mode = gubun;
		String[] rtn = new String[2];
		if(mode.equals("holySearch")){
			rtn = commonManager.getCalendar(ymd, oduRegnCd, req, res);
		}

		CommonMessage message = new CommonMessage();
		message.setCode(rtn[0]);
		message.setCode1(rtn[1]);

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doSearchDeptCode.do")
	public ModelAndView doSearchDeptCode(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		HncisCommon paramDto = (HncisCommon) getJsonToBean(paramJson, HncisCommon.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizePopUp; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = commonManager.getSelectCountDeptCodeByCommon(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(commonManager.getSelectDeptCodeByCommon(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/doSearchZipCode.do")
	public ModelAndView doSearchZipCode(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz034Dto paramDto = (BgabGascz034Dto) getJsonToBean(paramJson, BgabGascz034Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizePopUp; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = commonManager.getSelectCountZipCode(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(commonManager.getSelectZipCode(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/doSearchJobManagement.do")
	public ModelAndView doSearchJobManagement(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascz007Dto paramDto = (BgabGascz007Dto) getJsonToBean(paramJson, BgabGascz007Dto.class);
		paramDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		//이 팝업창만 15 row로 보여주기로 함.
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = "15";}

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = commonManager.getSelectCountJobMgmtInfo(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(commonManager.getSelectJobMgmtInfo(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/doSaveToEmpty.do")
	public ModelAndView doSearchToRequestByEmpty(HttpServletRequest req, HttpServletResponse res) throws HncisException{

		CommonList list = new CommonList();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getMenuComboList.do")
	public ModelAndView getMenuComboList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = new JSONBaseArray();

		List<BgabGascz004Dto> codeList = commonManager.selectMenuComboList();

		String[] codePair = codknd.split(":");

		if(codePair.length == 2){
			json = new JSONBaseVO();
			if(!codePair[1].equals("Z")){
				if(codePair[1].equals("S")){
					json.put("name", HncisMessageSource.getMessage("select"));
				}else{
					json.put("name", HncisMessageSource.getMessage("total"));
				}
				json.put("value", "");

				jsonArr.add(json);
			}

			for(BgabGascz004Dto targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getScrn_nm()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getScrn_id()));

				jsonArr.add(json);
			}

			jso.put(codePair[0], jsonArr);
		}


		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@RequestMapping(value="/getUserInfo.do")
	public ModelAndView getUserInfo(HttpServletRequest req, HttpServletResponse res) throws HncisException, SessionException{

 		JSONBaseVO jso = new JSONBaseVO();
		jso.put("sess_empno"    , SessionInfo.getSess_empno(req));
 		jso.put("sess_name"     , SessionInfo.getSess_name(req));
 		jso.put("sess_dept_code", SessionInfo.getSess_dept_code(req));
 		jso.put("sess_dept_name", SessionInfo.getSess_dept_name(req));
 		jso.put("sess_step_code", SessionInfo.getSess_step_code(req));
 		jso.put("sess_step_name", SessionInfo.getSess_step_name(req));
 		jso.put("sess_mstu_gubb", SessionInfo.getSess_mstu_gubb(req));
 		jso.put("sess_dsm_yn"   , SessionInfo.getSess_dsm_yn(req));
 		jso.put("sess_plac_work", SessionInfo.getSess_plac_work(req));
 		jso.put("sess_cost_cd"  , SessionInfo.getSess_cost_cd(req));
 		jso.put("sess_tel_no"   , SessionInfo.getSess_tel_no(req));
 		jso.put("sess_corp_cd"   , SessionInfo.getSess_corp_cd(req));
 		jso.put("sess_locale"   , SessionInfo.getSess_locale(req));

		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@RequestMapping(value="/doInsertEventToReply.do")
	public ModelAndView doInsertEventToReply(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		BgabGascz020Dto paramDto = (BgabGascz020Dto) getJsonToBean(paramJson, BgabGascz020Dto.class);
		commonManager.insertEventToReply(paramDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setResult("Y");
		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getEventQuizList.do")
	public ModelAndView getEventQuizList(HttpServletRequest req, HttpServletResponse res) throws HncisException{

 		JSONBaseVO jso  = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = new JSONBaseArray();

		try{
			BgabGascz017Dto quizParam = new BgabGascz017Dto();
			quizParam.setQuiz_seq_arr(StringUtil.getQuizSeq());

			List<BgabGascz017Dto> codeList = commonManager.selectEventQuizList(quizParam);

			for(BgabGascz017Dto targetBean : codeList){

				json = new JSONBaseVO();
				json.put("q_seq"     , StringUtil.isNullToStrTrm(targetBean.getQ_seq()));
				json.put("quiz_txt"  , StringUtil.isNullToStrTrm(targetBean.getQuiz_txt()));
				json.put("a_seq"     , StringUtil.isNullToStrTrm(targetBean.getA_seq()));
				json.put("answer_txt", StringUtil.isNullToStrTrm(targetBean.getAnswer_txt()));

				jsonArr.add(json);
			}

			jso.put("QUIZ", jsonArr);
		}catch(Exception e){
			e.printStackTrace();
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/doInsertEventToQuizAnswer.do")
	public ModelAndView doInsertEventToQuizAnswer(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams" , required=true) String iParams) throws HncisException{

		List<BgabGascz017Dto> paramDto = (List<BgabGascz017Dto>) getJsonToList(iParams, BgabGascz017Dto.class);
		commonManager.insertEventQuizAnswer(paramDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		message.setResult("Y");
		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
	}

	@RequestMapping(value="/doSearchToUserInfoByPopup.do")
	public ModelAndView doSearchToUserInfoByPopup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		System.out.println("paramJson="+paramJson);
		CommonUserInfo userDto = (CommonUserInfo) getJsonToBean(paramJson, CommonUserInfo.class);

		System.out.println("userDto eenm="+userDto.getEenm());
		List<CommonUserInfo> userInfo = commonManager.getSelectUserInfoList(userDto);

		CommonList list = new CommonList();
		list.setRows(userInfo);

		System.out.println(JSONObject.fromObject(list).toString());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doSaveCommonEditorFile.do")
    public  ModelAndView doSaveCommonEditorFile(HttpServletRequest req, HttpServletResponse res) throws HncisException, IOException{

		ModelAndView modelAndView = new ModelAndView();
		CommonMessage message = commonManager.insertFileMgmt(req, res, "editor");


        modelAndView.setViewName(DATA_JSON_IFRAME_PAGE);
        modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
        modelAndView.addObject("callBack", "doPicSaveCallback(jsonData)");
        modelAndView.addObject("uiType", "file");

        return modelAndView;

    }

	@RequestMapping(value="/doSignup.do")
	public ModelAndView doSignup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

 		BgabGascz030Dto bgabGascz030Dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);

 		ModelAndView modelAndView = new ModelAndView();
 		CommonMessage message = new CommonMessage();

 		int result = commonManager.signup(bgabGascz030Dto);

 		if(result>0){
			Locale locale = null;
			if(bgabGascz030Dto.getLocale().equals("ko")){
				locale = new Locale("ko","KR");
			} else if(bgabGascz030Dto.getLocale().equals("zh")){
				locale = new Locale("zh","CN");
			} else if(bgabGascz030Dto.getLocale().equals("en")){
				locale = new Locale("en","US");
			} else {
				locale = new Locale("ko","KR");
			}
 			
 			String toEmail  = bgabGascz030Dto.getReq_email();
			String fromEmail     = "hncis@hncis.co.kr";
			String text       = bgabGascz030Dto.getCorp_nm()+HncisMessageSource.getMessage("MAIL.0016",locale);
			String subject      = HncisMessageSource.getMessage("MAIL.0015",locale); //가입신청이 완료되었습니다.
			SendMail oMail = new SendMail();

			boolean success = oMail.sendMail(toEmail, fromEmail, subject, text, 0, false);
 			
			if(success){
				message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0053"));
			}else{
				message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0054"));
			}
 		}else{
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0054"));
 		}

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doUpdateMenuListToRequest.do")
	public ModelAndView doUpdateMenuListToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

 		BgabGascz030Dto bgabGascz030Dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);

 		ModelAndView modelAndView = new ModelAndView();
 		CommonMessage message = new CommonMessage();

 		int result = commonManager.updateMenuListToRequest(bgabGascz030Dto);

 		if(result>0){
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0055"));
 		}else{
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0056"));
 		}

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/checkCorpcdDuplicate.do")
	public ModelAndView checkCorpcdDuplicate(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

 		BgabGascz030Dto bgabGascz030Dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);

 		ModelAndView modelAndView = new ModelAndView();
 		CommonMessage message = new CommonMessage();

 		int result = commonManager.checkDuplicateCrop(bgabGascz030Dto);
 		
 		Locale locale = null;
 		if(bgabGascz030Dto.getLocale().equals("ko")){
 			locale = new Locale("ko", "KR");
 		} else if(bgabGascz030Dto.getLocale().equals("en")){
 			locale = new Locale("en", "US");
 		} else if(bgabGascz030Dto.getLocale().equals("zh")){
 			locale = new Locale("zh", "CN");
 		} else {
 			locale = new Locale("ko", "KR");
 		}
 		
 		if(result >0){
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0057",locale));
 			message.setCode("");
 		}else{
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0058",locale));
 			message.setCode("1");
 	 	}

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doSendMailForResetPassword.do")
	public ModelAndView doSendMailForResetPassword(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

 		BgabGascz002Dto bgabGascz002Dto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);

 		ModelAndView modelAndView = new ModelAndView();
 		CommonMessage message = new CommonMessage();

 		String msg = commonManager.selectUserMailAddr(bgabGascz002Dto, req);

 		message.setMessage(msg);

 		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;

	}

	@RequestMapping(value="/doResetPassword.do")
	public ModelAndView doResetPassword(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

 		BgabGascz002Dto bgabGascz002Dto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);

 		ModelAndView modelAndView = new ModelAndView();

 		CommonMessage message = new CommonMessage();

 		HttpSession session = req.getSession();
 		PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_Key_");

 		if(privateKey == null){
 			message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0052"));
 		}else{
			//암호화처리된 사용자계정정보를 복호화 처리한다.
			String sXusrEmpno = RSA.decryptRsa(privateKey, bgabGascz002Dto.getXusr_empno());
			String sXusrPsswd = RSA.decryptRsa(privateKey, bgabGascz002Dto.getXusr_pswd());
			String sCorpCd = RSA.decryptRsa(privateKey, bgabGascz002Dto.getCorp_cd());
			bgabGascz002Dto.setXusr_empno(sXusrEmpno);
			bgabGascz002Dto.setCorp_cd(sCorpCd);

			//String salt = SHA256Util.generateSalt();

//			System.out.println("sXusrEmpno:"+sXusrEmpno);
//			System.out.println("sXusrPsswd:"+sXusrPsswd);
            String newPassword = SHA256Util.getEncrypt(sXusrPsswd, sXusrEmpno);

            bgabGascz002Dto.setXusr_pswd(newPassword);

//			System.out.println("newPassword:"+newPassword);

			message = commonManager.updateToResetPassword(bgabGascz002Dto);
 		}

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/doSearchCorpNameList.do")
	public ModelAndView doSearchCorpNameList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz030Dto dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		int count = commonManager.selectCorpNameListCount(dto);


		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(commonManager.selectCorpNameList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/doSearchUserCode.do")
	public ModelAndView doSearchUserCode(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		HncisCommon paramDto = (HncisCommon) getJsonToBean(paramJson, HncisCommon.class);
		paramDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(!"".equals(StringUtil.isNullToString(paramDto.getEeNo()))){
			String[] xusrEmpno = paramDto.getEeNo().split(",");
			paramDto.setArray_empno(xusrEmpno);
		}

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizePopUp; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = commonManager.getSelectCountUserCodeByCommon(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		if("".equals(StringUtil.isNullToString(paramDto.getEeNo()))){
			list.setRows(commonManager.getSelectUserCodeByCommon(paramDto));
		}else{
			list.setRows(commonManager.getSelectUserCodeByCommon1(paramDto));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping("/getPropertiesMessages.do")
	 public void getProperties(HttpServletResponse response) throws IOException {
	  OutputStream outputStream = response.getOutputStream();
	  String locale = LocaleContextHolder.getLocale().toString();
	  
	  if(StringUtil.isEmpty(locale)){
		  locale = "ko";
	  }
	  
	  Resource resource = new ClassPathResource("/com/hncis/common/message/messages_"+locale+".properties");
//	  Resource resource = new ClassPathResource("/com/hncis/common/message/messages_ko.properties");
	  InputStream inputStream = resource.getInputStream();

	  @SuppressWarnings("unchecked")
	  List<String> readLines = IOUtils.readLines(inputStream);
	  IOUtils.writeLines(readLines, null, outputStream);
	  IOUtils.closeQuietly(inputStream);
	  IOUtils.closeQuietly(outputStream);
	 }
	
	/**
	 * 회사 정보 조회
	 */
	@RequestMapping(value="/doSearchCorpInfo.do")
	public ModelAndView doSearchCorpInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
 		BgabGascz035Dto bgabGascz035Dto = (BgabGascz035Dto) getJsonToBean(paramJson, BgabGascz035Dto.class);

 		BgabGascz035Dto rsDto = commonManager.getSelectCorpInfo(bgabGascz035Dto);
 		
 		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		if (rsDto != null) {
			rsDto.setIsSuccess("Y");
			rsDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDto).toString());
		} else {
			rsDto = new BgabGascz035Dto();
			rsDto.setIsSuccess("N");
			rsDto.setMessage(HncisMessageSource.getMessage("SEARCH.0002"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDto).toString());
		}
		
		return modelAndView;
	}
}