package org.uengine.web.service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.UEngineException;
import org.uengine.util.UEngineUtil;
import org.uengine.web.common.util.ShaEncoder;
import org.uengine.web.login.service.LoginService;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.service.service.BpmService;
import org.uengine.web.service.vo.AuthorityVO;
import org.uengine.web.service.vo.CommentVO;
import org.uengine.web.service.vo.DefaultVO;
import org.uengine.web.service.vo.FlowchartVO;
import org.uengine.web.service.vo.ServiceVO;
import org.uengine.web.service.vo.WorkListVO;
import org.uengine.web.service.vo.WorkVO;
import org.uengine.web.worklist.vo.MyWorkVO;

/**
 * <pre>
 * org.uengine.web.sample.controller 
 * SampleController.java
 * 
 * </pre>
 * 
 * @date : 2016. 6. 3. 오후 1:41:07
 * @version :
 * @author : mkbok_Enki
 */
@Controller
public class ServiceController {

	Logger log = Logger.getLogger(this.getClass());


	@Resource(name = "bpmService")
	private BpmService bpmService;
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;
	
	@Resource(name = "loginService")
	private LoginService loginService;

	@RequestMapping(value = "/common/paging.do", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView paging(@ModelAttribute DefaultVO defaultVO) {
		int pageNo = defaultVO.getPageNo();
		int totalCount = defaultVO.getTotalCount();
		int pageSize = defaultVO.getPageSize();
		int pagerSize = defaultVO.getPagerSize();
		
		int lastPage = (int) Math.ceil((double)totalCount/pageSize);
		defaultVO.setLastPage(lastPage);
		
		if ( lastPage == pageNo ){
			defaultVO.setHasLastPage(false);
		}
		if ( pageNo == 1 ) {
			defaultVO.setHasFirstPage(false);
		}

		List<Integer> pageGroup = new ArrayList<Integer>();
		
		int minPage = (int) (Math.floor((double)((pageNo-1)/pagerSize)) * pagerSize +1);
		int maxPage = minPage + pagerSize - 1;
		
		int nextMinPage = (int) (Math.floor((double)((pageNo+pagerSize-1)/pagerSize)) * pagerSize +1);
		int prevMaxPage = (int) (Math.floor((double)((pageNo-pagerSize-1)/pagerSize)) * pagerSize +1) + pagerSize - 1;
		
		defaultVO.setNextMinPage(nextMinPage);
		defaultVO.setPrevMaxPage(prevMaxPage);
		
		if ( maxPage <= pageSize ) {
			defaultVO.setHasPrevPage(false);
		}
		
		for ( int i = minPage; i <= maxPage; i++ ){
			pageGroup.add(i);
			if ( lastPage == i ){
				defaultVO.setHasNextPage(false);
				break;
			}
		}
		defaultVO.setPageGroup(pageGroup);
		
		
		
		ModelAndView mv = new ModelAndView("/common/paging", "command", defaultVO);
		
		return mv;
	}
	
	@RequestMapping(value = "/service/workflow", method = RequestMethod.POST)
	@ResponseBody
	public ServiceVO execute(@RequestBody String body, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		ServiceVO serviceVO = mapper.readValue(body, ServiceVO.class);
		try {
			serviceVO = bpmService.executeService(serviceVO, request);
			serviceVO.setStatus(ServiceVO.BPM_SERVICE_SUCCESS_CODE);
			
		} catch (UEngineException e) {
			serviceVO.setStatus(ServiceVO.BPM_SERVICE_FAIL_CODE);
			serviceVO.setErrorCode(Integer.toString(e.getErrorCode()));
			serviceVO.setMessage(e.getMessage());
		}
		
		return serviceVO;
	}

	@RequestMapping(value = "/service/gridSaveDummy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> gridSaveDummy(@RequestBody String body, HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(body);
		Map<String,String> result = new HashMap<String, String>();
		result.put("value", req.getParameter("value"));
		return(result);
		
	}

	@RequestMapping(value = "/service/authority/get/{processCode}/{bizKey}/{statusCode}/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public AuthorityVO getAuthority(@PathVariable String processCode, @PathVariable String bizKey, @PathVariable String statusCode, @PathVariable String userId, @RequestBody String body, HttpServletRequest request) throws Exception {
		return bpmService.getAuthority(processCode, bizKey, statusCode, userId);
	}
	
	@RequestMapping(value = "/service/flowchart/pd/{pdVer}", method = RequestMethod.GET)
	@ResponseBody
	public FlowchartVO getFlowchart(@PathVariable String pdVer) throws Exception {

		FlowchartVO flowchartVO = new FlowchartVO();
		flowchartVO.setPdVersionId(pdVer);
		
		try {
			bpmService.getFlowChartByPdVer(flowchartVO);
		} catch (Exception e) {
			throw e;
		}
		
		
		return flowchartVO;
	}

	@RequestMapping(value = "/service/flowchart/instance/{instanceId}", method = RequestMethod.GET)
	@ResponseBody
	public FlowchartVO getInstanceFlowChart(@PathVariable String instanceId) throws Exception {
		
		FlowchartVO flowchartVO = new FlowchartVO();
		flowchartVO.setInstanceId(instanceId);
		
		try {
			bpmService.getFlowChartByInstanceId(flowchartVO);
		} catch (Exception e) {
			throw e;
		}
		
		
		return flowchartVO;
	}

	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/worklist/asistobe/list/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public WorkListVO getWorklist(@PathVariable String userId, @RequestBody String body, HttpServletRequest request) throws Exception {
		
		int pageSize = 21;
		
		WorkListVO tmaxWorklist = new WorkListVO();
		
		if (UEngineUtil.isNotEmpty(body) && body.trim().length() > 0) {
			tmaxWorklist = mapper.readValue(body, WorkListVO.class);
		} else {
			tmaxWorklist.setTotalCount("0");
		}
		
		String minRegDate = null;
		
		if ( Integer.parseInt(tmaxWorklist.getTotalCount()) >= pageSize ) {
			minRegDate = tmaxWorklist.getWorkList().get(tmaxWorklist.getWorkList().size()-1).getRegDate();
		}
		
		WorkListVO uengineWorklist = bpmService.getWorkListByUserId(userId, pageSize, minRegDate);
		
		List<WorkVO> resultWorkList = uengineWorklist.getWorkList();
		if ( Integer.parseInt(tmaxWorklist.getTotalCount()) > 0)
			resultWorkList.addAll(tmaxWorklist.getWorkList());
		
		Collections.sort(resultWorkList, new Comparator<WorkVO>() {

			@Override
			public int compare(WorkVO o1, WorkVO o2) {
				long o2Date = Long.parseLong(o2.getRegDate().replaceAll("/", "").replaceAll(" ", "").replaceAll(":", ""));
				long o1Date = Long.parseLong(o1.getRegDate().replaceAll("/", "").replaceAll(" ", "").replaceAll(":", ""));
				return (o2Date > o1Date?1:-1);
			}
		});
		
		while ( resultWorkList.size() > pageSize ) {
			resultWorkList.remove(resultWorkList.size()-1);
		}
		
		int totalCount = Integer.parseInt(tmaxWorklist.getTotalCount()) + Integer.parseInt(uengineWorklist.getTotalCount());
		
		WorkListVO resultWorkListVO = new WorkListVO();
		resultWorkListVO.setWorkList(resultWorkList);
		resultWorkListVO.setTotalCount(Integer.toString(totalCount));
		
		
		return resultWorkListVO;
	}
	
	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/instance/asistobe/list/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public WorkListVO getInstancelist(@PathVariable String userId, @RequestBody String body, HttpServletRequest request) throws Exception {
		
		int pageSize = 21;
		
		WorkListVO tmaxWorklist = new WorkListVO();
		
		if (UEngineUtil.isNotEmpty(body) && body.trim().length() > 0) {
			tmaxWorklist = mapper.readValue(body, WorkListVO.class);
		} else {
			tmaxWorklist.setTotalCount("0");
		}

		
		String minRegDate = null;
		
		if ( Integer.parseInt(tmaxWorklist.getTotalCount()) >= pageSize ) {
			minRegDate = tmaxWorklist.getWorkList().get(tmaxWorklist.getWorkList().size()-1).getRegDate();
		}
		
		WorkListVO uengineWorklist = bpmService.getInstanceListByUserId(userId, pageSize, minRegDate);
		
		List<WorkVO> resultWorkList = uengineWorklist.getWorkList();
		if ( Integer.parseInt(tmaxWorklist.getTotalCount()) > 0)
			resultWorkList.addAll(tmaxWorklist.getWorkList());
		
		Collections.sort(resultWorkList, new Comparator<WorkVO>() {

			@Override
			public int compare(WorkVO o1, WorkVO o2) {
				long o2Date = Long.parseLong(o2.getRegDate().replaceAll("/", "").replaceAll(" ", "").replaceAll(":", ""));
				long o1Date = Long.parseLong(o1.getRegDate().replaceAll("/", "").replaceAll(" ", "").replaceAll(":", ""));
				return (o2Date > o1Date?1:-1);
			}
		});
		
		while ( resultWorkList.size() > pageSize ) {
			resultWorkList.remove(resultWorkList.size()-1);
		}
		
		int totalCount = Integer.parseInt(tmaxWorklist.getTotalCount()) + Integer.parseInt(uengineWorklist.getTotalCount());
		
		WorkListVO resultWorkListVO = new WorkListVO();
		resultWorkListVO.setWorkList(resultWorkList);
		resultWorkListVO.setTotalCount(Integer.toString(totalCount));
		
		
		return resultWorkListVO;
	}
	
	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/worklist/list/{userId}/{rowCount}/{status}", method = RequestMethod.POST)
	@ResponseBody
	public List<MyWorkVO> getMyWorklist(@PathVariable String status, @PathVariable String userId, @PathVariable String rowCount) throws Exception {
		int iRowCount = Integer.parseInt(rowCount);
		MyWorkVO myWorkVO = new MyWorkVO();
		myWorkVO.setUserId(userId);
		myWorkVO.setStatus(status);
		
		List<MyWorkVO> worklist = bpmService.getMyWorkListByUserId(myWorkVO);
		
		List<MyWorkVO> resultList = new ArrayList<MyWorkVO>();
		if ( iRowCount == 0 ) {
			resultList = worklist;
		} else {
			for(int i=0; i<worklist.size() && i < iRowCount ; i++){
				resultList.add(worklist.get(i));
			}
		}
		return resultList;
	}
	
	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/instance/list/{userId}/{rowCount}/{status}", method = RequestMethod.POST)
	@ResponseBody
	public List<MyWorkVO> getMyInstanceList(@PathVariable String status, @PathVariable String userId, @PathVariable String rowCount) throws Exception {
		int iRowCount = Integer.parseInt(rowCount);
		MyWorkVO myWorkVO = new MyWorkVO();
		myWorkVO.setStatus(status);
		myWorkVO.setUserId(userId);
		List<MyWorkVO> 
		instanceList = bpmService.getMyInstanceListByUserIdAndStatus(myWorkVO );	
		Iterator<MyWorkVO> iteraort = instanceList.iterator();
		while(iteraort.hasNext()){
			 MyWorkVO myWorkVOContent = (MyWorkVO)iteraort.next();
		}
		List<MyWorkVO> resultList = new ArrayList<MyWorkVO>();
		if ( iRowCount == 0 ) {
			resultList = instanceList;
		} else {
			for(int i=0; i<instanceList.size() && i < iRowCount ; i++){
				resultList.add(instanceList.get(i));
			}
		}
		return resultList;
	}
	
	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/worklist/count/get/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public List<MyWorkVO> getMyWorklistCount(@PathVariable String userId) throws Exception {
		return bpmService.getMyWorkListCountByUserId(userId);
	}

	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/instance/count/get/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public List<MyWorkVO> getMyInstanceCount(@PathVariable String userId) throws Exception {
		return bpmService.getMyInstanceListCountByUserId(userId);
	}
	
	/**
	 * @param userId
	 * @param body
	 * @param request
	 * @return
	 * @description 병행구동 워크리스트 
	 * @throws Exception
	 */
	@RequestMapping(value = "/service/instance/list/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public WorkListVO getMyInstancelist(@PathVariable String userId, @RequestBody String body, HttpServletRequest request) throws Exception {
		
		WorkListVO uengineWorklist = bpmService.getMyInstanceListByUserId(userId);
		
		return uengineWorklist;
	}
	
	@RequestMapping(value = "/service/comment/list/{processCode}/{bizKey}", method = RequestMethod.POST)
	@ResponseBody
	public List<CommentVO> getCommentList(@PathVariable String processCode, @PathVariable String bizKey, @RequestBody String body, HttpServletRequest request) throws Exception {
		return bpmService.getCommentList(processCode, bizKey);
	}
	
	@RequestMapping(value = "/service/get/prodver/{defId}", method = RequestMethod.POST)
	@ResponseBody
	public ProcessDefinitionVO getProdversionInfo(@PathVariable String defId) throws Exception {
		ProcessDefinition def = bpmService.getProdVersionInfoByDefId(defId);
		ProcessDefinitionVO result = new ProcessDefinitionVO();
		result.setDefVerId(def.getId());
		result.setDescription(def.getDescription().getText());
		result.setDefId(defId);
		result.setDefName(def.getName().getText());
		return result;
	}

	public static void main(String[]args) throws Exception{
		System.out.println((double)((10+10-1)/10));
		System.out.println(Math.ceil((double)1110/12));
	}
	

}
