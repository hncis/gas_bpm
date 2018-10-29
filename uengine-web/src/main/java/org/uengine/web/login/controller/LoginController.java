package org.uengine.web.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;
import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.login.service.LoginService;
import org.uengine.web.login.vo.LoginVO;

import com.google.gson.Gson;

/**
 * <pre>
 * org.uengine.web.login.controller 
 * LoginController.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:38:26
 * @version : 
 * @author : mkbok_Enki
 */
@Controller
public class LoginController {

	Logger log = Logger.getLogger(this.getClass());
	private Gson gson = new Gson();

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;

	/**
	 * <pre>
	 * 1. 개요 : 로그인 화면 이동
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : loginForm
	 * @date : 2016. 5. 20.
	 * @author : mkbok_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2016. 5. 20. mkbok_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/loginForm.do")
	public ModelAndView loginForm(LoginVO sessionVO) throws Exception {
		
		ModelAndView mv = new ModelAndView("/login/loginForm");

		return mv;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 로그인 화면 이동
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : loginForm
	 * @date : 2016. 5. 20.
	 * @author : mkbok_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2016. 5. 20. mkbok_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/logout.do")
	public void logout(HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		request.getSession().invalidate();
		
	}

	/**
	 * <pre>
	 * 1. 개요 : 로그인 프로세스를 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 1. 메소드 파라미터 중 전달 받은 SSO여부 ("isSSO")에 값이 "true" 일 경우
	 * 				 	1-1. 메소드 파라미터 중 전달 받은 loginVO 안에 사용자 ID ("userId")에 값으로 일치하는 사용자 ID 조회
	 * 					1-2. 사용자 ID 정보가 있을 경우 
	 *				 		1-2-1. resultMap 에 결과상태를 "success" 로 세팅
	 *							1-2-1-1. 사용자 ID 정보가 관리자 일 경우
	 *				 				1-2-1-1-1. resultMap 에 관리자여부 ("isAdmin") 값을 "true" 로 세팅 후 요청 처리
	 *							1-2-1-2. 사용자 ID 정보가 관리자가 아닐 경우
	 *								1-2-1-2-1. resultMap 에 관리자여부 ("isAdmin") 값을 "false" 로 세팅 후 요청 처리
	 *					1-3. 사용자 ID 정보가 없을 경우 
	 *						1-3-1. resultMap 에 결과상태를 "fail" 로 세팅 후 요청 처리
	 * 				 2. 메소드 파라미터 중 전달 받은 SSO여부 ("isSSO")에 값이 "false" 일 경우
	 * 				 	2-1. 메소드 파라미터 중 전달 받은 loginVO 안에 사용자 ID ("userId")에 값과 사용자 PW ("userPassword") 로 일치하는 사용자 ID 조회
	 *  			 	2-2. 사용자 ID 정보가 있을 경우 
	 *				 		2-2-1. resultMap 에 결과상태를 "success" 로 세팅
	 *							2-2-1-1. 사용자 ID 정보가 관리자 일 경우
	 *				 				2-2-1-1-1. resultMap 에 관리자여부 ("isAdmin") 값을 "true" 로 세팅 후 요청 처리
	 *							2-2-1-2. 사용자 ID 정보가 관리자가 아닐 경우
	 *								1-2-1-2-1. resultMap 에 관리자여부 ("isAdmin") 값을 "false" 로 세팅 후 요청 처리
	 *					2-3. 사용자 ID 정보가 없을 경우 
	 *						2-3-1. resultMap 에 결과상태를 "fail" 로 세팅 후 요청 처리
	 * </pre>
	 * 
	 * @Method Name : loginProc
	 * @date : 2016. 5. 20.
	 * @author : mkbok_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 3. 07. chonk_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param loginVO
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/loginProc.do")
	public void loginProc(@ModelAttribute LoginVO loginVO, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		
		boolean isSSO = "true".equals(UEngineUtil.getString(request.getParameter("isSSO")));
		
		if (isSSO) {
			loginVO = loginService.selectUserSSOByUserVO(loginVO);
		} else {
			String strPassword = loginVO.getUserPassword();
			// password encoding
			loginVO=loginService.selectUserSSOByUserVO(loginVO);
			loginVO.setUserPassword(encoder.encoding(strPassword));
			loginVO = loginService.selectUserByUserVO(loginVO);

		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (loginVO != null) {
			HttpSession session = request.getSession(false);
			
			session.setAttribute("loggedUserId", loginVO.getUserId());
			session.setAttribute("loggedUserFullName", loginVO.getUserName());
			session.setAttribute("loggedUserIsAdmin", loginVO.getIsAdmin());
			session.setAttribute("loggedUserJikName", loginVO.getJikName());
			session.setAttribute("loggedUserEmail", loginVO.getEmail());
			session.setAttribute("loggedUserPartCode", loginVO.getPartCode());
			session.setAttribute("loggedUserPartName", loginVO.getPartName());
			session.setAttribute("loggedUserGlobalCom", loginVO.getComCode());
			session.setAttribute("loggedUserGlobalComName", loginVO.getComName());
			session.setAttribute("loggedUserLocale", loginVO.getLocale());
			resultMap.put("status", "success");
			//2017-03-07 chonk, 조회한 Id가 관리자일 경우
			if (loginVO.getIsAdmin().equals("1")) {
				resultMap.put("isAdmin", "true");
			} else {
				resultMap.put("isAdmin", "false");
			}
			resultMap.put("redirectUrl", "main/");
			request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		} else {

			resultMap.put("status", "fail");
		}
		// 한글깨짐 방지.
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(gson.toJson(resultMap));
	
	}
		
}
