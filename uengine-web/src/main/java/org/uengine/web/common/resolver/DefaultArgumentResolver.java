package org.uengine.web.common.resolver; 

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.uengine.kernel.GlobalContext;
import org.uengine.web.login.vo.LoginVO;

/**
 * <pre>
 * org.uengine.web.common.resolver 
 * DefaultArgumentResolver.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:38:15
 * @version : 
 * @author : mkbok_Enki
 */
public class DefaultArgumentResolver implements HandlerMethodArgumentResolver {


	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : resolveArgument
	 * @date : 2016. 5. 20.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 20.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer arg1, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Class<?> clazz = methodParameter.getParameterType();
        String paramName = methodParameter.getParameterName();
        if(clazz.equals(LoginVO.class) && paramName.equals("sessionVO")){            
           
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();  
            return request.getSession().getAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME);
        }
        return null;
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : supportsParameter
	 * @date : 2016. 5. 20.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 20.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 * @param arg0
	 * @return
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return true;
	}

}
