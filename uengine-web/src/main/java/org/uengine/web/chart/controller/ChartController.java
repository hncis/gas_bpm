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
package org.uengine.web.chart.controller; 

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.uengine.util.UEngineUtil;
import org.uengine.web.chart.service.ChartService;
import org.uengine.web.chart.vo.FlowChartVO;
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
public class ChartController {

	private Logger log = Logger.getLogger(this.getClass());
	private ObjectMapper mapper = new ObjectMapper();
	
	@Resource(name = "chartService")
	private ChartService chartService;
	
	@RequestMapping(value="/chart/flowchart/definition/get", method = RequestMethod.POST)
	public void getFlowChart(@ModelAttribute FlowChartVO flowchart, HttpServletResponse response) throws Exception {

		
        String chart = chartService.getFlowChartByDefVerId(flowchart);
		response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(chart);
    }

	@RequestMapping(value="/chart/viewInstanceFlowChart.do", method = RequestMethod.POST)
	public ModelAndView viewInstanceFlowChart(@ModelAttribute MyWorkVO myWork) throws Exception {
		ModelAndView mv = new ModelAndView("/chart/viewInstanceFlowChart", "command", myWork);
		return mv;
	}

	@RequestMapping(value="/chart/flowchart/instance/get", method = RequestMethod.POST)
	@ResponseBody
	public FlowChartVO getInstanceFlowChart(@RequestBody String body) throws Exception {
		FlowChartVO chart = new FlowChartVO();
		Map<String, String> requestMap = UEngineUtil.convertParameterListToMap(mapper.readValue(body, List.class));
		chart.setInstanceId(requestMap.get("instanceId"));
		chart.setViewType(requestMap.get("viewType"));
		chart.setViewOption(requestMap.get("viewOption"));
		
		chartService.getFlowChartByInstanceId(chart);
		return chart;
	}

}
