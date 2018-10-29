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
package org.uengine.web.apitest.controller; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.web.apitest.service.ApiTestService;

/**
 * <pre>
 * org.uengine.web.apitest.controller 
 * ApiTestController.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:40:02
 * @version : 
 * @author : mkbok_Enki
 */
@Controller
public class ApiTestController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "apiTestService")
	private ApiTestService apiTestService;
	
	@RequestMapping(value="/apitest/")
	public ModelAndView apiTestMain() throws Exception{
        ModelAndView mv = new ModelAndView("/apitest/index");        
        return mv;
    }

	@RequestMapping(value="/apitest/serviceExecute.do")
	public ModelAndView serviceExecute(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/apitest/serviceExecute");
		return mv;
	}

	@RequestMapping(value="/apitest/flowControl.do")
	public ModelAndView flowControl(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/apitest/flowControl");
		return mv;
	}
	
}
