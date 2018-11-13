package com.hncis.common.aspect;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hncis.common.Constant;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.util.ParameterSecurity;

/**
 * LoginHandlerInterceptor
 * login check interceptor
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter
{
    private transient Log logger = LogFactory.getLog(getClass());
	private static final String notLogin = "NOTLOGIN";

    private long startTime;
    private DecimalFormat formatter = new DecimalFormat("0000");

    private List<String> exclusionList;

    private ParameterSecurity security;

    private ParameterSecurity getSecurityInstance() {
    	if(this.security == null) {
    		this.security = new ParameterSecurity();
    	}
    	return this.security;
    }

    private void writeLog(String log)
    {
        //if(logger.isInfoEnabled())
    	logger.info("log="+log);
            logger.debug(log);
    }

    /**
     * intercepter 예외 url list setter
     * @param exclusionList intercepter 예외 url
     */
    public void setExclusionList(List<String> exclusionList)
    {
        this.exclusionList = exclusionList;
    }

    /**
     * controller 호출전 login check
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler
     * @return true 시 controller 호출
     */
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object obj) throws SessionException , HncisException
    {
        StringBuffer message = new StringBuffer();
        String appendStr = "";
//        message = new StringBuffer();
        startTime = System.currentTimeMillis();

        appendStr += "[[CONTROLLER LAYER STARTED]";
        appendStr += obj.getClass().getSimpleName();
        appendStr += "( ";
        appendStr += request.getServletPath();
        appendStr += " )]";
        message.append(appendStr);

        this.writeLog(message.toString());

        request.setAttribute("originalURI", request.getRequestURI());

//        logger.info("============= request.getRequestURI() : "+request.getRequestURI());

        if(this.exclusionList.contains(request.getRequestURI())) {return true;}
        HttpSession session = request.getSession(false);

        //String sess_empno 		= SessionInfo.getSess_empno(request);			//작업자 사번
        //String sess_name   		= SessionInfo.getSess_name(request);			//작업자 성명
        //String sess_dept_code  	= SessionInfo.getSess_dept_code(request);		//작업자 소속
        //String sess_dept_name 	= SessionInfo.getSess_dept_name(request);		//작업자 소속명
        //String sess_auth 		= SessionInfo.getSess_work_auth(request);		//작업자 권한
//    	String sess_mstu_gubb 	= SessionInfo.getSess_mstu_gubb(request);
    	//String sess_step_code 	= SessionInfo.getSess_step_code(request);
    	//String sess_step_name 	= SessionInfo.getSess_step_name(request);
    	//String sess_dsm_yn 		= SessionInfo.getSess_dsm_yn(request);
    	//String sess_cnfm_auth	= SessionInfo.getSess_cnfm_auth(request);

    	/*
    	if(request.getRequestURI().contains("doWriteBDToNotice.do") || request.getRequestURI().contains("doWriteBDToNotice.do")){
        	if( !sess_mstu_gubb.equals("M") ){
        		throw new SessionException("NOTLOGIN");
        	}
        }
    	*/
    	/*
        if(request.getRequestURI().contains("xbd02.gas") ){
        	if( Integer.parseInt(sess_auth) < 5 && !sess_mstu_gubb.equals("M") ){
        		throw new SessionException("NOTLOGIN");
        	}
        }
        */

        if(session != null && session.getAttribute(Constant.SESSION_USER_KEY) != null) {
        	return true;
        } else if((session == null || session.getAttribute(Constant.SESSION_USER_KEY) == null)) {
        	removeSession(session);
    		throw new SessionException(notLogin);
//        } else if(!"M".equals(SessionInfo.getSess_mstu_gubb(request)) && !StringUtil.getMenuAuthManagerFlag(request)){

        } else	{
        	if(session != null) {
        		removeSession(session);
        	}

        	if(request.getRequestURI().contains(".xls") ||request.getRequestURI().contains("view.do") || request.getRequestURI().contains("index.do")){ throw new SessionException(notLogin);}
        	else{ throw new HncisException(notLogin);}
        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        StringBuffer message = new StringBuffer(60);
        String appendStr = "";
        long duringTime = System.currentTimeMillis() - startTime;
        
        appendStr += "[[CONTROLLER LAYER FINISHED]";
        appendStr += handler.getClass().getSimpleName();
        appendStr += ".(";
        appendStr += request.getServletPath();
        appendStr += ")] 걸린시간:";
        appendStr += formatter.format(duringTime);
        message.append(appendStr);
        this.writeLog(message.toString());
    }

    private void removeSession(HttpSession session) throws SessionException {
    	try{
    		session.removeAttribute(Constant.SESSION_USER_KEY);
    	}catch(Exception e){
    		throw new SessionException(notLogin);
    	}
    }
}