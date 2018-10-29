package org.uengine.web.login.service; 

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;
import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.login.dao.LoginDAO;
import org.uengine.web.login.vo.LoginVO;

import com.google.gson.Gson;


/**
 * <pre>
 * org.uengine.web.login.service 
 * LoginServiceImpl.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:39:01
 * @version : 
 * @author : mkbok_Enki
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private Logger log = Logger.getLogger(this.getClass());
	private Gson gson = new Gson();

	@Resource(name="loginDAO")
	private LoginDAO loginDAO;
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectUserByUserVO
	 * @date : 2016. 6. 3.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 6. 3.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.web.login.service.LoginService#selectUserByUserVO(org.uengine.web.login.vo.LoginVO)
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */ 	
	@Override
	public LoginVO selectUserByUserVO(LoginVO loginVO) throws Exception {		
		return loginDAO.selectUserByUserVO(loginVO);		
	}

	@Override
	public LoginVO selectUserSSOByUserVO(LoginVO loginVO) throws Exception {		
		return loginDAO.selectUserSSOByUserVO(loginVO);		
	}

	@Override
	public void loginExecute(LoginVO loginVO, HttpServletResponse response,
			HttpServletRequest request, ShaEncoder encoder) throws Exception {
	}
}
