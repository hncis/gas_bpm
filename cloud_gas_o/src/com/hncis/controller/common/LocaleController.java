package com.hncis.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;

import net.sf.json.JSONObject;

@Controller
public class LocaleController extends AbstractController{
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
    @Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	//@Autowired
	//private MessageSource messageSource;

	@RequestMapping(value = "/changeLocale.do")
    public ModelAndView changeLocale(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException, SessionException{
		
		BgabGascz002Dto bgabGascz002Dto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		CommonMessage message = new CommonMessage();
		message = commonManager.updateToResetLocale(bgabGascz002Dto);
		
		Locale locale = new Locale(StringUtil.isNullToString(bgabGascz002Dto.getLocale(), "ko"));
		
		String tempTokenKey = SessionInfo.getSess_token_key(request);
		
		SessionInfo.setSessionChange(request, tempTokenKey);
		
		HttpSession session = request.getSession();
		session.setAttribute("reqLocale", locale);
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		message.setCode(locale.toString());
		
        // step. 해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
//        String redirectURL = "redirect:" + request.getHeader("referer");
        ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return modelAndView;
    }
}