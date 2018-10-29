package org.uengine.web.main.service; 

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.GlobalContext;


/**
 * <pre>
 * org.uengine.web.main.service 
 * MainServiceImpl.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:40:53
 * @version : 
 * @author : mkbok_Enki
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : getUserPortrait
	 * @date : 2016. 6. 3.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 6. 3.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.web.main.service.MainService#getUserPortrait(org.uengine.web.login.vo.LoginVO)
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */ 	
	@Override
	public String getUserPortrait(String userId, HttpServletRequest request) throws Exception {
		
		String servletPath = request.getSession().getServletContext().getRealPath("/");
		String portraitPath = servletPath + GlobalContext.PORTRAIT_PATH;
		String portraitFullPath = null;
		
		File portraitDir = new File(portraitPath);
		if ( portraitDir.exists() && portraitDir.isDirectory() ) {
			Iterator<String> portraitExtentions = Arrays.asList(GlobalContext.PORTRAIT_EXTENTIONS).iterator();
			while(portraitExtentions.hasNext()) {
				String extention = portraitExtentions.next();
				String fullPath = portraitPath + "/" + userId + "." + extention;
				File f = new File(fullPath);
				if ( f.exists() && f.isFile() ) {
					portraitFullPath = GlobalContext.PORTRAIT_PATH + "/" + userId + "." + extention;
					break;
				}
			}
			
		} 
		
		portraitFullPath = (portraitFullPath == null)?GlobalContext.PORTRAIT_PATH + "/" + GlobalContext.DEFAULT_PORTRAIT_FILE_NAME:portraitFullPath;
		
		return portraitFullPath;
	}


}
