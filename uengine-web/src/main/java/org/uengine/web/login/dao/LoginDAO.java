package org.uengine.web.login.dao;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.login.vo.LoginVO;


/**
 * <pre>
 * org.uengine.web.login.dao 
 * LoginDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:38:38
 * @version : 
 * @author : mkbok_Enki
 */
@Repository("loginDAO")
public class LoginDAO extends AbstractDAO {

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
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
	 */ 	
	public LoginVO selectUserByUserVO(LoginVO loginVO) throws Exception {
		return (LoginVO) selectOne("login.selectUserByUserVO", loginVO);
	}

	public LoginVO selectUserSSOByUserVO(LoginVO loginVO) throws Exception {
		System.out.println("loginVO: " + loginVO);
		return (LoginVO) selectOne("login.selectUserSSOByUserVO", loginVO);
	}


}
