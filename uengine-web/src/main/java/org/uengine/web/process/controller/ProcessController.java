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
package org.uengine.web.process.controller; 

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.kernel.GlobalContext;
import org.uengine.web.chart.vo.FlowChartVO;
import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.login.service.LoginService;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.process.service.ProcessService;
import org.uengine.web.process.vo.ProcessVO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.service.vo.ServiceVO;
import org.uengine.web.worklist.service.WorklistService;
import org.uengine.web.worklist.vo.MyWorkVO;

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
@SessionAttributes
public class ProcessController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "processService")
	private ProcessService processService;

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "worklistService")
	private WorklistService worklistService;
	
	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;
	

	
	@RequestMapping(value="/process/", method = RequestMethod.GET)
	public ModelAndView processMain() throws Exception{
        ModelAndView mv = new ModelAndView("/process/index", "command", new FlowChartVO());
        
        return mv;
    }

	@RequestMapping(value="/process/viewProcessDefinitionFlowChart.do", method = RequestMethod.GET)
	public ModelAndView viewProcessDefinitionFlowChart(@ModelAttribute FlowChartVO chart) throws Exception{
		ModelAndView mv = new ModelAndView("/process/viewProcessDefinitionFlowChart", "command", chart);
		
		return mv;
	}
	
	@RequestMapping(value = "/process/processtree/list/{comCode}", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeVO> getProcessTreeBycomCode(@PathVariable String comCode) throws Exception {
		return processService.getProcessTreeBycomCode(comCode);
	}
	
	@RequestMapping(value = "/process/list/krissprocess/{userId}/{pageNo}", method = RequestMethod.GET)
	public ModelAndView viewKrissProcessList(@PathVariable String userId, @PathVariable int pageNo, HttpServletRequest request) throws Exception {
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";

		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));

		loginVO = loginService.selectUserByUserVO(loginVO);


		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setPageNo(pageNo);
		
		ModelAndView mv = new ModelAndView("/process/krissProcessList", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value = "/process/kriss/list/process/{pageNo}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProcessDefinitionVO> getKrissWorklist(LoginVO sessionVO, @PathVariable int pageNo, @PathVariable int pageSize) throws Exception {
		int startRow = (pageNo-1)*pageSize+1;
		int endRow = pageNo*pageSize;
		ProcessDefinitionVO defVO = new ProcessDefinitionVO();
		defVO.setUserId(sessionVO.getUserId());
		defVO.setStartRow(startRow);
		defVO.setEndRow(endRow);
		
		return processService.getKrissProcessList(defVO);
	}
	
	@RequestMapping(value = "/process/kriss/count/process", method = RequestMethod.GET)
	@ResponseBody
	public int getKrissProcessListCount(LoginVO sessionVO) throws Exception {
		
		return processService.getCountKrissProcessList(sessionVO.getUserId());
	}
	
	@RequestMapping(value = "/process/executeProcess.do", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView processExecute(HttpServletRequest request) throws Exception {
		String defName = request.getParameter("defName");
		String defId = request.getParameter("defId");
		String defVerId = request.getParameter("defVerId");
		String userId = request.getParameter("userId");
		ProcessVO processVO = new ProcessVO();
		processVO.setDefName(defName);
		processVO.setDefId(defId);
		processVO.setDefVerId(defVerId);
		processVO.setUserId(userId);
		processVO = processService.initExecuteProcess(processVO, request);
		System.out.println("test1.processVO.getInstanceId(): " + processVO.getInstanceId());
		MyWorkVO myWorkVO = processService.selectInstanceIdMyWorkVO(processVO.getInstanceId());
		System.out.println("test2");
		ModelAndView mv = new ModelAndView("/process/executeWorkItem", "command", myWorkVO);
		return mv;
	}


}
