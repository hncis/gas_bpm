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
package org.uengine.web.common.controller; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.web.common.service.CommonService;

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
public class CommonController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	/**
	 * <pre>
	 * 1. 개요 : 스마트워크 내비게이션 "지식관리 > 업무매뉴얼" 메뉴에서 해당 프로세스에 대한 FlowChart 화면 제공을 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 해당 메소드가 호출될 경우 "/common/showFlowChartAfterLogin" URL로 요청 처리
	 * </pre>
	 * 
	 * @Method Name : showFlowChartAfterLogin
	 * @date : 2017. 1. 02.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 1. 02. chonk_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param request
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value="/common/showFlowChartAfterLogin.do")
	public ModelAndView showFlowChartAfterLogin(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/common/showFlowChartAfterLogin");
		return mv;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 스마트워크 내비게이션 "업무 매니저" 메뉴에서 uEngine Console 화면 제공을 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 해당 메소드가 호출될 경우 "/common/goMainAfterLogin" URL로 요청 처리
	 * </pre>
	 * 
	 * @Method Name : goMainAfterLogin
	 * @date : 2017. 1. 02.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 1. 02. chonk_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param request
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value="/common/goMainAfterLogin.do")
	public ModelAndView goMainAfterLogin(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/common/goMainAfterLogin");
		return mv;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 스마트워크 내비게이션 "내 할일 > 내 업무함 및 협업함" 메뉴에서 해당 접수번호에 대해 진행 상태 FlowChart 화면 제공을 아래 처리내용과 같이 처리
	 * 2. 처리내용 : 해당 메소드가 호출될 경우 "/common/workListShowFlowChartAfterLogin" URL로 요청 처리
	 * </pre>
	 * 
	 * @Method Name : workListShowFlowChartAfterLogin
	 * @date : 2017. 3. 07.
	 * @author : chonk_Enki
	 * @history :
	 *          ----------------------------------------------------------------
	 *          ------- 변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2017. 3. 07. chonk_Enki
	 *          최초 작성
	 *          ----------------------------------------------------------------
	 *          -------
	 * 
	 * @param request
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value="/common/workListShowFlowChartAfterLogin.do")
	public ModelAndView workListShowFlowChartAfterLogin(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/common/workListShowFlowChartAfterLogin");
		return mv;
	}
}
