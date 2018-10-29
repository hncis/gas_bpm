package org.uengine.web.login.service; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.login.vo.LoginVO;

/**
 * <pre>
 * org.uengine.web.login.service 
 * LoginService.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:38:53
 * @version : 
 * @author : mkbok_Enki
 */
public interface LoginService {
	

	/**
	 * <pre>
	 * 1. 개요 : login check
	 * 2. 처리내용 : login check service
	 * </pre>
	 * @Method Name : checkLogin
	 * @date : 2016. 5. 26.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 26.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param loginVO
	 * @return
	 * @throws Exception 
	 */ 	
	public LoginVO selectUserByUserVO(LoginVO loginVO) throws Exception;

	public LoginVO selectUserSSOByUserVO(LoginVO loginVO) throws Exception;
	
	public void loginExecute(LoginVO loginVO, HttpServletResponse response, HttpServletRequest request, ShaEncoder encoder) throws Exception;
	
}
