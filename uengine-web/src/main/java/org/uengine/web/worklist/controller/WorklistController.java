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
package org.uengine.web.worklist.controller; 

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import org.uengine.web.login.service.LoginService;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.service.vo.DefaultVO;
import org.uengine.web.worklist.service.WorklistService;
import org.uengine.web.worklist.vo.MyWorkVO;

import com.fasterxml.jackson.databind.ObjectMapper;

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
public class WorklistController {

	private Logger log = Logger.getLogger(this.getClass());
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Resource(name = "worklistService")
	private WorklistService worklistService;
	
	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;
	
	@RequestMapping(value="/worklist/")
	public ModelAndView portalMain() throws Exception{
        ModelAndView mv = new ModelAndView("/worklist/index", "command", new MyWorkVO());
        return mv;
    }

	@RequestMapping(value="/worklist/krissWorklist/{userId}/{pageNo}")
	public ModelAndView viewKrissWorklist(@PathVariable String userId, @PathVariable int pageNo, HttpServletRequest request) throws Exception{
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";

		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));

		loginVO = loginService.selectUserByUserVO(loginVO);


		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setPageNo(pageNo);
		
		ModelAndView mv = new ModelAndView("/worklist/krissWorklist", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value="/worklist/krissWorklistGroup/{userId}")
	public ModelAndView viewWorklistGroup(@PathVariable String userId, HttpServletRequest request) throws Exception{
		LoginVO loginVO = new LoginVO();
		loginVO.setUserId(userId);
		String strPassword = "test";
		
		// password encoding
		loginVO.setUserPassword(encoder.encoding(strPassword));
		
		loginVO = loginService.selectUserByUserVO(loginVO);
		
		
		request.getSession().setAttribute(GlobalContext.LOGIN_SESSION_ATTR_NAME, loginVO);
		
		MyWorkVO workVO = new MyWorkVO();
		workVO.setUserId(userId);
		
		ModelAndView mv = new ModelAndView("/worklist/krissWorklistGroup", "command", workVO);
		mv.addObject("folderList", worklistService.getKrissFolderList());
		return mv;
	}
	
	@RequestMapping(value = "/worklist/kriss/list/mywork/group", method = RequestMethod.GET)
	@ResponseBody
	public List<MyWorkVO> getKrissWorklistGroup(LoginVO sessionVO) throws Exception {
		MyWorkVO workVO = new MyWorkVO();
		workVO.setUserId(sessionVO.getUserId());
		
		List<MyWorkVO> groupList = worklistService.getKrissWorklistGroup(workVO);
		int totalCount = 0;
		for(MyWorkVO work: groupList){
			totalCount += work.getGroupCount();
		}
		if ( groupList != null && groupList.size() > 0 ) {
			groupList.get(0).setTotalCount(totalCount);
		}
		
		return groupList;
	}
	
	@RequestMapping(value = "/worklist/kriss/list/mywork/{pageNo}/{pageSize}", method = RequestMethod.POST)
	@ResponseBody
	public List<MyWorkVO> getKrissWorklist(@RequestBody List<Map<String,String>> requestList, LoginVO sessionVO, @PathVariable int pageNo, @PathVariable int pageSize) throws Exception {
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		String jsonString = mapper.writeValueAsString(requestMap);
		MyWorkVO workVO = mapper.readValue(jsonString, MyWorkVO.class);
		
		int startRow = (pageNo-1)*pageSize+1;
		int endRow = pageNo*pageSize;
		workVO.setUserId(sessionVO.getUserId());
		workVO.setStartRow(startRow);
		workVO.setEndRow(endRow);
		
		return worklistService.getKrissWorklist(workVO);
	}

	@RequestMapping(value = "/worklist/kriss/count/mywork", method = RequestMethod.POST)
	@ResponseBody
	public int getKrissWorklistCount(@RequestBody List<Map<String,String>> requestList, LoginVO sessionVO) throws Exception {
		
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(requestList);
		String jsonString = mapper.writeValueAsString(requestMap);
		MyWorkVO workVO = mapper.readValue(jsonString, MyWorkVO.class);
		workVO.setUserId(sessionVO.getUserId());
		
		return worklistService.getCountKrissWorklist(workVO);
	}

	@RequestMapping(value="/worklist/viewWorkItem.do")
	public ModelAndView viewWorkItem(@ModelAttribute MyWorkVO myWork) throws Exception{
		ModelAndView mv = new ModelAndView("/worklist/viewWorkItem", "command", myWork);
		
		return mv;
	}

}
