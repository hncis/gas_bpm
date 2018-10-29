package org.uengine.kernel.viewer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.uengine.components.activityfilters.CostDeterminationFilter;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.ExecutionScopeContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ScopeActivity;
import org.uengine.kernel.viewer.gantt.GanttActivityViewer;
import org.uengine.kernel.viewer.swimlane.SwimlaneViewer;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;
import org.uengine.processpublisher.graph.exporter.ProcessDefinitionAdapter;

/**
 * @author Jinyoung Jang
 */

public class ProcessDefinitionViewer extends ComplexActivityViewer{
	
	static ActivityViewer instance;
		public static ActivityViewer getInstance() {
			if (instance == null){
				String processDefinitionViewerPackage = GlobalContext.getPropertyString("activityviewer.package", "org.uengine.kernel.ProcessDefinitionViewer");
				
				try{
					Class pdvc = Class.forName(processDefinitionViewerPackage + ".ProcessDefinitionViewer");
					instance = (ActivityViewer)pdvc.newInstance();
				}catch(Exception e){
					instance = new ProcessDefinitionViewer();
				}
				
			}

			return instance;
		}

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);
		
		if(!(instance instanceof EJBProcessInstance)) {
			instance.setProcessDefinition((ProcessDefinition) activity);
		}
		
		boolean isFlowChartCenter = false;
		if (options.containsKey("align")) { 
			if("center".equals((String)options.get("align"))) isFlowChartCenter = true; 
		}
		
		boolean definitionView = false;
		try {
			definitionView = (instance==null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) {}
		
		/**
		 * indexing the execution scopes and provide it as one of option values so that 
		 * the child activity viewer can reference them and render the transitions for 
		 * execution threads
		 */
		if (!definitionView) {
			ArrayList executionScopes = instance.getExecutionScopeContexts();
			
			if (executionScopes!=null) {
				HashMap executionScopesHT = new HashMap();
				for(int i=0; i<executionScopes.size(); i++){
					ExecutionScopeContext esc = (ExecutionScopeContext)executionScopes.get(i);

					List executionScopeListIndexedByTriggerActivityTracingTag;
					if(executionScopesHT.containsKey(esc.getTriggerActivityTracingTag())){
						executionScopeListIndexedByTriggerActivityTracingTag = (List)executionScopesHT.get(esc.getTriggerActivityTracingTag());
					} else {
						executionScopeListIndexedByTriggerActivityTracingTag = new ArrayList();
					
						executionScopesHT.put(
							esc.getTriggerActivityTracingTag(), 
							executionScopeListIndexedByTriggerActivityTracingTag
						);
					}
					
					executionScopeListIndexedByTriggerActivityTracingTag.add(esc);
				}
				
				options.put("executionScopeHT", executionScopesHT);
			}
		}
		
		/**
		 * viewOnlyScopeIndex would be used for seqmenting the chart view of multiple instance subprocesses.
		 */
		int viewOnlyScopeIndex = options.containsKey("viewOnlyScopeIndex") ? Integer.parseInt((String)options.get("viewOnlyScopeIndex")) : -1;
		int indexOfScopeActivity = -1;
/*		if(options.containsKey("locale")){
			((ProcessDefinition)activity).setCurrentLocale((String)options.get("locale"));
		}
*/		
		StringBuilder sb = new StringBuilder();
		String drawLinerNamespace = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, null);
		
		sb.append(" <div id='canvasForProcessDefinition" + drawLinerNamespace + "' ");
		if (isFlowChartCenter) {
			sb.append(" style='text-align:center;'");
		}
		sb.append(">");
		
		if (options.containsKey("swimlane")) {
			
			ProcessDefinitionAdapter adapter = new ProcessDefinitionAdapter();
			Hashtable keyedContext = (Hashtable) options;
			GraphActivity graph;

			try {
				graph = (GraphActivity) adapter.convert(activity, keyedContext);
				SwimLaneCoordinate coordinate = new SwimLaneCoordinate();
				graph = coordinate.traverse(graph, null, options);
				
				if (coordinate.getRoleList().size() == 0) {
					String viewOption = (String) options.get(ViewerOptions.SWIMLANE);
					options.put(viewOption, viewOption);
				} else {
					SwimlaneViewer viewer = new SwimlaneViewer();
					sb.append(viewer.render(graph, coordinate, instance, options));
					sb.append("</div>");
					
					sb.append(getScriptSourceForBegin(drawLinerNamespace));
					sb = coordinate.appendCoordinateFunctionScript(instance , sb, options);
					sb.append(getScriptSourceForEnd(drawLinerNamespace));

					return sb;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return sb;
			}
		} else if (options.containsKey("ganttchart")) {
			GanttActivityViewer Viewer = new GanttActivityViewer();
			sb.append(Viewer.render(activity, instance, options));
			
			sb.append("</div>");
			return sb;
		}
		
		if (options.containsKey("viewOnlyScopeTracingTag")) {
			String viewOnlyScopeTracingTag = (String)options.get("viewOnlyScopeTracingTag");
			activity = ((ProcessDefinition)activity).getActivity(viewOnlyScopeTracingTag);
		}
		
		if (instance != null && options.containsKey("total cost")) {
			try {
				long totalCost = CostDeterminationFilter.getTotalCost(instance);
				sb.append("<b>Total Cost: </b>" + totalCost);
			} catch(Exception e) { }
		}
		
		boolean isVertical = options.containsKey("vertical");
		
//		if (isFlowChartCenter) sb.append("<center>"); //flowchart center
		
		sb.append("<table border=0 cellpadding=0 cellspacing=0 style=\"margin:0 auto;\"><tr>");
		sb.append("<td align=center><img src="+imagePathRoot+"images/start"+(isVertical? "_vertical":"")+".gif></td>");

		if(isVertical) sb.append("</tr><tr>");
		
		sb.append("<td>");
		sb.append("<table border='0' cellpadding='0' cellspacing='1'><tr>");
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		//System.out.println("ProcessDefinitionViewer::render : number of child activities: " + cActivity.getChildActivities().size());
		
		for(Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			ActivityUtil activityUtil = new ActivityUtil();

			if (child instanceof ScopeActivity) indexOfScopeActivity++;
			if (viewOnlyScopeIndex > -1 && indexOfScopeActivity != viewOnlyScopeIndex) continue;
			
			if (!activityUtil.isVisible(child, options)) {
				boolean isGrayArrow = false;
				if (!definitionView)
					try{
						isGrayArrow = Activity.STATUS_READY.equals(child.getStatus(instance));
					}catch(Exception e){}
				
				if (isVertical) sb.append("<tr>");

				sb.append("<td align=center><img src="+imagePathRoot+"images/arrow" + (isVertical ? "_vertical":"") +".gif" +  (isGrayArrow ? " style='filter:alpha(opacity="+unfocusedOpacity+")'":"") +" /></td>");
				
				if(isVertical){
					sb.append("</tr><tr>");
				}
				
				// ****************************************************
				// Activity Box Start
				sb.append("<td align='center' id='" + DefaultActivityViewer.getActivityName(child, instance, options) + "'>");
					ActivityViewer viewer = DefaultActivityViewer.createViewer(child);
					sb.append(viewer.render(child, instance, options));
				sb.append("</td>");
				// Activity Box End
				// ****************************************************

				if (isVertical) sb.append("</tr>");
			}
		}
		
		//sb.append(activity.getName());
		sb.append("</tr></table>");
		
		boolean isGrayArrow = false;
		if (!definitionView)
			try {
				isGrayArrow = !Activity.STATUS_COMPLETED.equals(activity.getStatus(instance));
			} catch (Exception e) {}
		
		if (!isVertical) {
			sb.append("</td>");
		} else {
			sb.append("<tr>");
		}
		
	
		sb.append("<td align=center><img src="+imagePathRoot+"images/arrow" + (isVertical ? "_vertical":"") +".gif" +  (isGrayArrow ? " style='filter:alpha(opacity="+unfocusedOpacity+")'":"") +"></td>");
		
		if (isVertical) sb.append("<tr>");
	
		sb.append("<td align=center><img src="+imagePathRoot+"images/end.gif></td>");
		
		sb.append("</tr></table>");
		
//		if(isFlowChartCenter) 	sb.append("</center>"); //flowchart center
		
		sb.append("</div>"); 
		
		//write script for drawing loop activity lines
		sb.append(getScriptSourceForBegin(drawLinerNamespace));
		
		try {
			List<String> allActivityAbsTTs = AllActivityViewer.getAllActivityAbsoluteTracingTags(options);
			
			for (int i = 0; i < allActivityAbsTTs.size(); i++) {
				String allActivityAbsTT = "";
				allActivityAbsTT = ((String) allActivityAbsTTs.get(i));
				
//				if (((String) allActivityAbsTTs.get(i)).indexOf("_") > -1) {
//					String[] allActivityAbsTTSplit = ((String) allActivityAbsTTs.get(i)).split("_");
//					allActivityAbsTT = allActivityAbsTTSplit[allActivityAbsTTSplit.length - 1];
//				} else {
//					allActivityAbsTT = ((String) allActivityAbsTTs.get(i));
//				}
				List<String> innerActivityAbsTTs = AllActivityViewer.getInnerActivityAbsoluteTracingTags(options, allActivityAbsTT);
				
				for (int j = 0; j < innerActivityAbsTTs.size(); j++) {
					sb.append("\n drawAllActivityLine(paper,'" + drawLinerNamespace 
							+ "', '" + allActivityAbsTT
							+ "', '" + innerActivityAbsTTs.get(j)
							+ "', " + isVertical + ");");
				}
			}
			
			List loopActivityAbsTTs = LoopActivityViewer.getLoopActivityAbsoluteTracingTags(options);
			for (int i=0; i<loopActivityAbsTTs.size(); i++) {
				sb.append("\n drawLoopLine(paper, '" + drawLinerNamespace + "', '" + loopActivityAbsTTs.get(i)  + "');");
			}
		
			List backActivityAbsStartTTs = BackActivityViewer.getBackActivityAbsoluteStartTracingTags(options);
			List backActivityAbsBackTTs = BackActivityViewer.getBackActivityAbsoluteBackTracingTags(options);
			
			for(int i=0; i<backActivityAbsStartTTs.size(); i++){
				String sTT = (String) backActivityAbsStartTTs.get(i);
				String eTT = (String) backActivityAbsBackTTs.get(i);
				
				sb.append(
						"\n drawBackLine(paper, '"
						+ isVertical + "','"
						+ drawLinerNamespace + "','"
						+ DefaultActivityViewer.getActivityName(sTT, instance, options) + "','"
						+ DefaultActivityViewer.getActivityName(eTT, instance, options) + "');"
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sb.append(getScriptSourceForEnd(drawLinerNamespace));
		
		return sb;
	}
	
	private StringBuffer getScriptSourceForBegin(String drawLinerNamespace) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n<script type=\"text/javascript\">\n//<![CDATA[");
		sb.append("\n var drawLoopLines" + drawLinerNamespace + " =  function(paper) { ");
		
		return sb;
	}
	
	private StringBuffer getScriptSourceForEnd(String drawLinerNamespace) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n }\n setDrawArea('" + drawLinerNamespace + "', drawLoopLines" + drawLinerNamespace + ");");
		sb.append("\n //]]>\n</script>\n ");
		
		return sb;
	}
}