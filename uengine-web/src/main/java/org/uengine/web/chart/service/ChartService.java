package org.uengine.web.chart.service; 

import org.uengine.web.chart.vo.FlowChartVO;




/**
 * <pre>
 * org.uengine.web.main.service 
 * MainService.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 4:46:03
 * @version : 
 * @author : mkbok_Enki
 */
public interface ChartService {

	String getFlowChartByDefVerId(FlowChartVO vo) throws Exception;

	void getFlowChartByInstanceId(FlowChartVO flowchartVO) throws Exception;
	
}
