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
package org.uengine.web.instancelist.controller; 

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;
import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.instancelist.service.InstancelistService;
import org.uengine.web.login.service.LoginService;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.main.service.MainService;
import org.uengine.web.processmanager.vo.ProcessInstanceVO;
import org.uengine.web.service.vo.DefaultVO;
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
public class InstancelistController {

	private Logger log = Logger.getLogger(this.getClass());
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource(name = "instancelistService")
	private InstancelistService instancelistService;
	
	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "worklistService")
	private WorklistService worklistService;
	
	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value="/instancelist/")
	public ModelAndView portalMain(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/instancelist/index", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/completedlist/list/kriss/{userId}/{pageNo}")
	public ModelAndView veiwCompletedInstanceList(@PathVariable String userId, @PathVariable int pageNo, HttpServletRequest request) throws Exception{
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";
		
		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));
		
		loginVO = loginService.selectUserByUserVO(loginVO);
		
		
		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setPageNo(pageNo);
		Calendar c = Calendar.getInstance();
		workVO.setStrToDate(sdf.format(new Date(c.getTimeInMillis())));
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.DATE, 1);
		workVO.setStrFromDate(sdf.format(new Date(c.getTimeInMillis())));
		
		ModelAndView mv = new ModelAndView("/instancelist/krissCompletedInstancelist", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value = "/instancelist/list/kriss/completed/instance/{pageNo}/{pageSize}", method = RequestMethod.POST)
	@ResponseBody
	public List<ProcessInstanceVO> getKrissCompletedInstanceList(@RequestBody String body, LoginVO sessionVO, @PathVariable int pageNo, @PathVariable int pageSize) throws Exception {
		List<Map<String,String>> requestList = mapper.readValue(body, List.class);
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		
		String jsonString = mapper.writeValueAsString(requestMap);
		System.out.println(jsonString);
		DefaultVO vo = mapper.readValue(jsonString, DefaultVO.class);
		
		int startRow = (pageNo-1)*pageSize+1;
		int endRow = pageNo*pageSize;
		vo.setUserId(sessionVO.getUserId());
		vo.setStartRow(startRow);
		vo.setEndRow(endRow);
		
		return instancelistService.getKrissCompletedInstanceList(vo);
	}

	@RequestMapping(value = "/instancelist/count/kriss/completed/instance", method = RequestMethod.POST)
	@ResponseBody
	public int getKrissCompletedInstanceListCount(@RequestBody String body, LoginVO sessionVO) throws Exception {
		List<Map<String,String>> requestList = mapper.readValue(body, List.class);
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		
		String jsonString = mapper.writeValueAsString(requestMap);
		DefaultVO vo = mapper.readValue(jsonString, DefaultVO.class);
		
		vo.setUserId(sessionVO.getUserId());
		
		return instancelistService.getCountKrissCompletedInstanceList(vo);
	}

	@RequestMapping(value="/delegated/completedlist/list/kriss/{userId}/{pageNo}")
	public ModelAndView veiwDelegatedCompletedInstanceList(@PathVariable String userId, @PathVariable int pageNo, HttpServletRequest request) throws Exception{
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";
		
		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));
		
		loginVO = loginService.selectUserByUserVO(loginVO);
		
		
		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setPageNo(pageNo);
		Calendar c = Calendar.getInstance();
		workVO.setStrToDate(sdf.format(new Date(c.getTimeInMillis())));
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.DATE, 1);
		workVO.setStrFromDate(sdf.format(new Date(c.getTimeInMillis())));
		
		ModelAndView mv = new ModelAndView("/instancelist/krissDelegatedCompletedInstancelist", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value = "/delegated/instancelist/list/kriss/completed/instance/{pageNo}/{pageSize}", method = RequestMethod.POST)
	@ResponseBody
	public List<ProcessInstanceVO> getKrissDelegatedCompletedInstanceList(@RequestBody String body, LoginVO sessionVO, @PathVariable int pageNo, @PathVariable int pageSize) throws Exception {
		List<Map<String,String>> requestList = mapper.readValue(body, List.class);
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		
		String jsonString = mapper.writeValueAsString(requestMap);
		System.out.println(jsonString);
		DefaultVO vo = mapper.readValue(jsonString, DefaultVO.class);
		
		int startRow = (pageNo-1)*pageSize+1;
		int endRow = pageNo*pageSize;
		vo.setUserId(sessionVO.getUserId());
		vo.setStartRow(startRow);
		vo.setEndRow(endRow);
		
		return instancelistService.getKrissDelegatedCompletedInstanceList(vo);
	}
	
	@RequestMapping(value = "/delegated/instancelist/count/kriss/completed/instance", method = RequestMethod.POST)
	@ResponseBody
	public int getKrissDelegatedCompletedInstanceListCount(@RequestBody String body, LoginVO sessionVO) throws Exception {
		List<Map<String,String>> requestList = mapper.readValue(body, List.class);
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		
		String jsonString = mapper.writeValueAsString(requestMap);
		DefaultVO vo = mapper.readValue(jsonString, DefaultVO.class);
		
		vo.setUserId(sessionVO.getUserId());
		
		return instancelistService.getCountKrissDelegatedCompletedInstanceList(vo);
	}
	
	@RequestMapping(value="/instancelist/list/kriss/{userId}/{pageNo}")
	public ModelAndView veiwRunningInstanceList(@PathVariable String userId, @PathVariable int pageNo, HttpServletRequest request) throws Exception{
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";

		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));

		loginVO = loginService.selectUserByUserVO(loginVO);


		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setPageNo(pageNo);
		
		ModelAndView mv = new ModelAndView("/instancelist/krissRunningInstancelist", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value = "/instancelist/list/kriss/running/instance/{pageNo}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProcessInstanceVO> getKrissWorklist(LoginVO sessionVO, @PathVariable int pageNo, @PathVariable int pageSize) throws Exception {
		int startRow = (pageNo-1)*pageSize+1;
		int endRow = pageNo*pageSize;
		DefaultVO vo = new MyWorkVO();
		vo.setUserId(sessionVO.getUserId());
		vo.setStartRow(startRow);
		vo.setEndRow(endRow);
		
		return instancelistService.getKrissRunningInstanceList(vo);
	}

	@RequestMapping(value = "/instancelist/count/kriss/running/instance", method = RequestMethod.GET)
	@ResponseBody
	public int getKrissWorklistCount(LoginVO sessionVO) throws Exception {
		DefaultVO vo = new MyWorkVO();
		vo.setUserId(sessionVO.getUserId());
		
		return instancelistService.getCountKrissRunningInstanceList(vo);
	}
	
	@RequestMapping(value="/instancelist/viewWorkItem.do")
	public ModelAndView viewWorkItem(@ModelAttribute MyWorkVO myWork) throws Exception{
		ModelAndView mv = new ModelAndView("/instancelist/viewWorkItem", "command", myWork);
		
		return mv;
	}
	
	@RequestMapping(value="/instancelist/viewProcessVariable.do", method = RequestMethod.POST)
	public ModelAndView viewProcessVariable(@ModelAttribute MyWorkVO myWork) throws Exception{
		ModelAndView mv = new ModelAndView("/common/viewProcessVariable", "command", myWork);
		return mv;
	}
	
	@RequestMapping(value="/instancelist/viewProcessRole.do", method = RequestMethod.POST)
	public ModelAndView viewProcessRole(@ModelAttribute MyWorkVO myWork, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/common/viewProcessRole", "command", myWork);
		String portraitPath = mainService.getUserPortrait(null, request);
        //System.out.println(portraitPath);
        
        mv.addObject("portraitPath", portraitPath);
		
		return mv;
	}

}
