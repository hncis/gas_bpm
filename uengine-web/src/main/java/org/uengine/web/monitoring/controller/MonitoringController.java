package org.uengine.web.monitoring.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;












import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.UEngineUtil;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.monitoring.service.MonitoringService;
import org.uengine.web.organization.service.OrganizationService;
import org.uengine.web.worklist.vo.MyWorkVO;

import be.ceau.chart.BarChart;
import be.ceau.chart.LineChart;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.LineData;

@Controller
public class MonitoringController {

	private Logger log = Logger.getLogger(this.getClass());
	
	
	/*
	 * type(화면아이디)에 따라서 제공하는 데이터가 달라진다.  
	 * 1) newworkstatus: 신규업무현황
	 * 2) processingdelaystatusbytask: 업무별 처리 지연현황
	 * 3) progressstatusbytask: 업무별 진행현황
	 * 4) taskcompletedaveragetime: 평균 업무완료 시간 추이
	 */
	
	public static final String MC_type1 = "newworkstatus";
	public static final String MC_type2 = "processingdelaystatusbytask";
	public static final String MC_type3 = "progressstatusbytask";
	public static final String MC_type4 = "taskcompletedaveragetime";
	
	@Resource(name = "monitoringService")
	private MonitoringService monitoringService;
	
	@Resource(name = "organizationService")
	private OrganizationService organizationService;
	
	@RequestMapping(value="/monitoring/")
	public ModelAndView portalMain(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/index", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/csboard.do")
	public ModelAndView viewCombinationStatusByTask(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/csboard", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/csboarddisplay.do")
	public ModelAndView viewCombinationStatusByTaskDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/csboard", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/newworkstatus.do")
	public ModelAndView viewNewWorkStatus(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/newworkstatus", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/chartjs/{windowName}", method = RequestMethod.POST)
	@ResponseBody
	public String viewBarData(@PathVariable String windowName, HttpServletRequest request) throws Exception{
		BarData monitoringVoBarDataList = null;
		LineData monitoringVoBarLineList = null;
		String result = null;
		if(windowName.equals(MC_type1)){
			monitoringVoBarDataList = (BarData)monitoringService.getChartData(windowName);
			result = new BarChart(monitoringVoBarDataList).toJson();
		}else if(windowName.equals(MC_type4)){
			monitoringVoBarLineList = (LineData)monitoringService.getChartData(windowName);
			result = new LineChart(monitoringVoBarLineList).toJson();
		}else{
			monitoringVoBarDataList = (BarData)monitoringService.getChartData(windowName);
			result = new BarChart(monitoringVoBarDataList).toJson();
		}
        return result;
    }
	
	@RequestMapping(value="/monitoring/toastjs/{windowName}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject viewToastData(@PathVariable String windowName, HttpServletRequest request) throws Exception{
        return (JSONObject)monitoringService.getChartData(windowName);
    }
	
	@RequestMapping(value="/monitoring/processingstatusbytask", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getProcessingStatusByTaskData(HttpServletRequest request) throws Exception{
		@SuppressWarnings("unchecked")
		Map<String, String> map = new HashedMap();

		if(UEngineUtil.isNotEmpty(request.getParameter("partCode"))){
			map.put("partCode", request.getParameter("partCode"));	
		} 
		if(UEngineUtil.isNotEmpty(request.getParameter("searchFromDate"))){
			map.put("searchFromDate", request.getParameter("searchFromDate"));	
		} 
		if(UEngineUtil.isNotEmpty(request.getParameter("searchToDate"))){
			map.put("searchToDate", request.getParameter("searchToDate"));	
		} 
        return (JSONObject)monitoringService.getProcessingStatusByTaskData(map);
    }
	
	@RequestMapping(value="/monitoring/processingstatusbyuser", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getProcessingStatusByUserData(HttpServletRequest request) throws Exception{
		System.out.println("partCode: " + request.getParameter("partCode"));
		System.out.println("searchFromDate: " + request.getParameter("searchFromDate"));
		System.out.println("searchToDate: " + request.getParameter("searchToDate"));
		@SuppressWarnings("unchecked")
		Map<String, String> map = new HashedMap();
		if(UEngineUtil.isNotEmpty(request.getParameter("partCode"))){
			map.put("partCode", request.getParameter("partCode"));	
		} 
		if(UEngineUtil.isNotEmpty(request.getParameter("searchFromDate"))){
			map.put("searchFromDate", request.getParameter("searchFromDate"));	
		} 
		if(UEngineUtil.isNotEmpty(request.getParameter("searchToDate"))){
			map.put("searchToDate", request.getParameter("searchToDate"));	
		} 
        return (JSONObject)monitoringService.getProcessingStatusByUserData(map);
    }
	
	@RequestMapping(value="/monitoring/combovaluelist/{comCode}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getComboData(@PathVariable String comCode) throws Exception{
		List<RoleMapping> roleInfoList = organizationService.getPartListByComCode(comCode);
        return (JSONObject)monitoringService.getComboBoxData(roleInfoList);
    }
	
	@RequestMapping(value="/monitoring/taskcompletedaveragetime.do")
	public ModelAndView viewTaskCompletedAverageTime(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/taskcompletedaveragetime", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingdelaystatusbytask.do")
	public ModelAndView viewProcessingDelayStatusByTask(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/processingdelaystatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/progressstatusbytask.do")
	public ModelAndView viewProgressStatusByTask(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/progressstatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingstatusbytask.do")
	public ModelAndView viewProcessingStatusByTask(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/processingstatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingstatusbyuser.do")
	public ModelAndView viewProcessingStatusByUser(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/processingstatusbyuser", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingdelaystatus.do")
	public ModelAndView viewProcessingDelayStatus(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/processingdelaystatus", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/newworkstatusdisplay.do")
	public ModelAndView viewNewWorkStatusDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/newworkstatus", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/taskcompletedaveragetimedisplay.do")
	public ModelAndView viewTaskCompletedAverageTimeDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/taskcompletedaveragetime", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingdelaystatusbytaskdisplay.do")
	public ModelAndView viewProcessingDelayStatusByTaskDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/processingdelaystatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/progressstatusbytaskdisplay.do")
	public ModelAndView viewProgressStatusByTaskDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/progressstatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingstatusbytaskdisplay.do")
	public ModelAndView viewProcessingStatusByTaskDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/processingstatusbytask", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingstatusbyuserdisplay.do")
	public ModelAndView viewProcessingStatusByUserDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/processingstatusbyuser", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/processingdelaystatusdisplay.do")
	public ModelAndView viewProcessingDelayStatusDisplay(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoringdisplay/processingdelaystatus", "command", new MyWorkVO() );
        
        return mv;
    }
	
	@RequestMapping(value="/monitoring/test.do")
	public ModelAndView viewTest(LoginVO sessionVO, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("/monitoring/test", "command", new MyWorkVO() );
        
        return mv;
    }
	
	//processingdelaystatus.jsp
}
