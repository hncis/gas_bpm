package org.uengine.web.chart.service; 

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.viewer.ViewerOptions;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.web.chart.vo.FlowChartVO;


/**
 * <pre>
 * org.uengine.web.main.service 
 * MainServiceImpl.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:40:53
 * @version : 
 * @author : mkbok_Enki
 */
@Service("chartService")
public class ChartServiceImpl implements ChartService {

	private Logger log = Logger.getLogger(this.getClass());
	private ProcessManagerBean processManagerBean;

	@Override
	public String getFlowChartByDefVerId(FlowChartVO vo) throws Exception {
		String chart;
		ViewerOptions options = new ViewerOptions();
		options.setViewType(vo.getViewType(), vo.getViewOption());
		options.put(ViewerOptions.IMAGE_PATH_ROOT, "../resources/images/flowchart/");
		options.put(ViewerOptions.NOWRAP, Boolean.TRUE);
		options.put(ViewerOptions.VIEW_FLOWCONTROL, new Boolean(false));
		options.put(ViewerOptions.DECORATED, new Boolean(true));
		options.put(ViewerOptions.VIEWOPTION_SHOW_HIDDEN_ACTIVITY, new Boolean(false));
		options.put("ShowAllComplexActivities", new Boolean(false));
		options.put("align", "center");
		options.put("locale", GlobalContext.getDefaultLocale());
		options.put(
				"enableUserEvent_viewFormDefinition_org.uengine.kernel.FormActivity",
				"View Form Definition");
		options.put(
				"enableUserEvent_drillInto_org.uengine.kernel.SubProcessActivity",
				"Drill Into");
		processManagerBean = new ProcessManagerBean();
		try {
			chart =processManagerBean
					.viewProcessDefinitionFlowChart(vo.getDefVerId(), options);
			processManagerBean.applyChanges();
		} catch (Exception e) {
			processManagerBean.cancelChanges();
			e.printStackTrace();
			throw e;
		} finally {
			processManagerBean.remove();
		}
		return chart;
	}
	
	@Override
	public void getFlowChartByInstanceId(FlowChartVO flowchartVO) throws Exception {
		ViewerOptions options = new ViewerOptions();
		options.setViewType(flowchartVO.getViewType(), flowchartVO.getViewOption());
		options.put("imagePathRoot", GlobalContext.WEB_CONTEXT_ROOT+"/resources/images/flowchart/");
		options.put("flowControl", new Boolean(false));
		options.put("decorated", new Boolean(true));
		options.put("show hidden activity", new Boolean(false));
		options.put("ShowAllComplexActivities", new Boolean(true));
		options.put("align", "center");
		options.put("locale", GlobalContext.getDefaultLocale());
		options.put(
				"enableUserEvent_viewFormDefinition_org.uengine.kernel.FormActivity",
				"View Form Definition");
		options.put(
				"enableUserEvent_drillInto_org.uengine.kernel.SubProcessActivity",
				"Drill Into");
		processManagerBean = new ProcessManagerBean();
		try {
			flowchartVO.setChartString(processManagerBean
					.viewProcessInstanceFlowChart(flowchartVO.getInstanceId(), options));
			processManagerBean.applyChanges();
		} catch (Exception e) {
			processManagerBean.cancelChanges();
			e.printStackTrace();
			throw e;
		} finally {
			processManagerBean.remove();
		}
	}



}
