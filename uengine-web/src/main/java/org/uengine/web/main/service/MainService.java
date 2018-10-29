package org.uengine.web.main.service; 

import javax.servlet.http.HttpServletRequest;

import org.uengine.web.login.vo.LoginVO;



/**
 * <pre>
 * org.uengine.web.main.service 
 * MainService.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 4:46:03
 * @version : 
 * @author : mkbok_Enki
 */
public interface MainService {
	
	public String getUserPortrait(String userId, HttpServletRequest request) throws Exception;
	
}
