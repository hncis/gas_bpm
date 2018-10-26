package com.hncis.common.application;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hncis.common.Constant;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;

public class SessionInfo {
	public SessionInfo() {
		super();
	}

	/**
	 * @desc :세션정보가 존재여부 체크
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BgabGascz002Dto getSession(HttpServletRequest req){

		HttpSession session = req.getSession(false);
		Map<String, BgabGascz002Dto> attribute = (HashMap<String, BgabGascz002Dto>)session.getAttribute(Constant.SESSION_USER_KEY);

		if(attribute != null){
			BgabGascz002Dto sessionInfo = attribute.get(Constant.SESSION_USER_INFO);
			if(sessionInfo == null){
				return null;
			}
			return sessionInfo;
		}
		return null;
	}

	public static boolean sessionCheck(HttpServletRequest req) {
		try{
			System.out.println("sessionCheck 입장");
			if (getSession(req)==null){
				return false;
			}

			//setSessionChange(req);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public static void setSessionChange(HttpServletRequest req, String tempTokenKey) throws SessionException, HncisException{

		HttpSession session = req.getSession(false);
//		Map<String, BgabGascz002Dto> attribute = (HashMap<String, BgabGascz002Dto>)session.getAttribute(Constant.SESSION_USER_KEY);
	
		System.out.println("session ID="+session.getId());

		String temp_empno 		= getSess_empno(req);
		String temp_name 		= getSess_name(req);
		String temp_dept_name 	= getSess_dept_name(req);
		String temp_dept_code 	= getSess_dept_code(req);
		String temp_cnfm_auth 	= getSess_cnfm_auth(req);
		String temp_mstu_gubb 	= getSess_mstu_gubb(req);
		String temp_work_auth 	= getSess_work_auth(req);
		String temp_step_code 	= getSess_step_code(req);
		String temp_step_name 	= getSess_step_name(req);
		String temp_dsm_yn 		= getSess_dsm_yn(req);
		String temp_plac_work	= getSess_plac_work(req);
		String temp_cost_cd		= getSess_cost_cd(req);
		String temp_tel_no	 	= getSess_tel_no(req);
		String temp_corp_cd		= getSess_corp_cd(req);
		String temp_token_key 	= getSess_token_key(req);
		String temp_locale	 	= CommonGasc.getUserLocale(temp_empno, temp_corp_cd);

		session.invalidate();

		Map<String, BgabGascz002Dto> map = new HashMap<String,BgabGascz002Dto>();
		BgabGascz002Dto changeSessionInfo = new BgabGascz002Dto();
		changeSessionInfo.setXusr_empno(temp_empno);
		changeSessionInfo.setXusr_name(temp_name);
		changeSessionInfo.setXusr_dept_name(temp_dept_name);
		changeSessionInfo.setXusr_dept_code(temp_dept_code);
		changeSessionInfo.setXusr_cnfm_auth(temp_cnfm_auth);
		changeSessionInfo.setXusr_auth_knd(temp_mstu_gubb);
		changeSessionInfo.setXusr_work_auth(temp_work_auth);
		changeSessionInfo.setXusr_step_code(temp_step_code);
		changeSessionInfo.setXusr_step_name(temp_step_name);
		changeSessionInfo.setXusr_dsm_yn(temp_dsm_yn);
		changeSessionInfo.setXusr_plac_work(temp_plac_work);
		changeSessionInfo.setXusr_cost_cd(temp_cost_cd);
		changeSessionInfo.setXusr_tel_no(temp_tel_no);
		changeSessionInfo.setCorp_cd(temp_corp_cd);
		changeSessionInfo.setLocale(temp_locale);
		//changeSessionInfo.setXusr_token_key(tempTokenKey);
		changeSessionInfo.setXusr_token_key(temp_token_key);
		map.put(Constant.SESSION_USER_INFO , changeSessionInfo);

		Locale userLocale = new Locale(StringUtil.isNullToString(temp_locale, "ko"));
		
		HttpSession newSession = req.getSession();
		newSession.setMaxInactiveInterval(Constant.SESSION_INTERVAL_TIME);
		newSession.setAttribute(Constant.SESSION_USER_KEY, map);
		newSession.setAttribute("reqLocale", new Locale(temp_locale));
		newSession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, userLocale);
		System.out.println(newSession.getId());
		System.out.println(newSession.getAttribute(Constant.SESSION_USER_KEY));

		
		
		/*
		System.out.println("session ID="+session.getId());

		String temp_empno 		= getSess_empno(req);
		String temp_name 		= getSess_name(req);
		String temp_dept_name 	= getSess_dept_name(req);
		String temp_dept_code 	= getSess_dept_code(req);
		String temp_cnfm_auth 	= getSess_cnfm_auth(req);
		String temp_mstu_gubb 	= getSess_mstu_gubb(req);
		String temp_work_auth 	= getSess_work_auth(req);
		String temp_step_code 	= getSess_step_code(req);
		String temp_step_name 	= getSess_step_name(req);
		String temp_dsm_yn 		= getSess_dsm_yn(req);
		String temp_plac_work	= getSess_plac_work(req);
		String temp_cost_cd		= getSess_cost_cd(req);
		String temp_tel_no	 	= getSess_tel_no(req);
		String temp_token_key 	= getSess_token_key(req);

		session.invalidate();

		Map<String, BgabGascz002Dto> map = new HashMap<String,BgabGascz002Dto>();
		BgabGascz002Dto changeSessionInfo = new BgabGascz002Dto();
		changeSessionInfo.setXusr_empno(temp_empno);
		changeSessionInfo.setXusr_name(temp_name);
		changeSessionInfo.setXusr_dept_name(temp_dept_name);
		changeSessionInfo.setXusr_dept_code(temp_dept_code);
		changeSessionInfo.setXusr_cnfm_auth(temp_cnfm_auth);
		changeSessionInfo.setXusr_auth_knd(temp_mstu_gubb);
		changeSessionInfo.setXusr_work_auth(temp_work_auth);
		changeSessionInfo.setXusr_step_code(temp_step_code);
		changeSessionInfo.setXusr_step_name(temp_step_name);
		changeSessionInfo.setXusr_dsm_yn(temp_dsm_yn);
		changeSessionInfo.setXusr_plac_work(temp_plac_work);
		changeSessionInfo.setXusr_cost_cd(temp_cost_cd);
		changeSessionInfo.setXusr_tel_no(temp_tel_no);
		//changeSessionInfo.setXusr_token_key(tempTokenKey);
		changeSessionInfo.setXusr_token_key(temp_token_key);
		map.put(Constant.SESSION_USER_INFO , changeSessionInfo);

		HttpSession sessionChange = req.getSession();
		sessionChange.setMaxInactiveInterval(Constant.SESSION_INTERVAL_TIME);
		sessionChange.setAttribute(Constant.SESSION_USER_KEY, map);
		*/
	}

	//작업자 사번
	public static String getSess_empno(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_empno());
	}

	//작업자 성명
	public static String getSess_name(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_name());
	}

	//작업자 소속
	public static String getSess_dept_name(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_dept_name());
	}

	//작업자 소속명
	public static String getSess_dept_code(HttpServletRequest req)  throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_dept_code());
	}

	//결재 권한값
	public static String getSess_cnfm_auth(HttpServletRequest req)  throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_cnfm_auth());
	}

	//MASTER/USER구분 (M,U)
	public static String getSess_mstu_gubb(HttpServletRequest req){
		return StringUtil.isNullToString(getSession(req).getXusr_auth_knd());
	}

	//사용자 권한값
	public static String getSess_work_auth(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_work_auth());
	}
	//스텝코드
	public static String getSess_step_code(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_step_code());
	}
	//스텝네임
	public static String getSess_step_name(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_step_name());
	}

	//업무담당자여부
	public static String getSess_dsm_yn(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_dsm_yn());
	}

	public static String getSess_plac_work(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_plac_work());
	}

	public static String getSess_token_key(HttpServletRequest req){
		return StringUtil.isNullToString(getSession(req).getXusr_token_key());
	}

	public static String getSess_cost_cd(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_cost_cd());
	}

	public static String getSess_tel_no(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getXusr_tel_no());
	}

	public static String getSess_corp_cd(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getCorp_cd());
	}
	
	public static String getSess_adm_corp_cd(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getAdm_corp_cd());
	}
	
	public static String getSess_locale(HttpServletRequest req) throws SessionException{
		BgabGascz002Dto sessionInfo = getSession(req);
		if (sessionInfo==null){
			throw new SessionException("NOTLOGIN");
		}
		return StringUtil.isNullToString(sessionInfo.getLocale());
	}
}
