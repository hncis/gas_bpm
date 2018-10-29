/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package org.uengine.web.main.controller; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.main.service.MainService;

/**
 * <pre>
 * org.uengine.web.main.controller 
 * MainController.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:40:02
 * @version : 
 * @author : mkbok_Enki
 */
@Controller
public class MainController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	@RequestMapping(value="/main/")
	public ModelAndView portalMain(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/main/index");
        
        //portrait info
        String portraitPath = mainService.getUserPortrait(sessionVO.getUserId(), request);
        //System.out.println(portraitPath);
        
        mv.addObject("portraitPath", portraitPath);
        
//        mv.addObject("logoImage", "/resources/images/page/logo/"+sessionVO.getComCode()+"_logo.png" );
        
        return mv;
    }

}
