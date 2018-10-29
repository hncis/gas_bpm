package org.uengine.kernel.viewer;

import java.util.List;
import java.util.Map;

import org.uengine.kernel.Activity;
import org.uengine.kernel.EventHandler;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ScopeActivity;

/**
 * @author Jinyoung Jang
 */

public class ScopeActivityViewer extends SequenceActivityViewer {

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options) {
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);

		ScopeActivity scopeActivity = (ScopeActivity)activity;
		
		
		if(scopeActivity.isCollapsed()&&!options.containsKey("dontCollapseScopes")){
			
			ActivityViewer collapsedViewer = new DefaultActivityViewer(){

				public String getIconImageURL(Activity activity, ProcessInstance instance, Map options) {
					String imagePathRoot = getImagePathRoot(options);
					
					return imagePathRoot + "images/SubProcessActivity.gif";
				}


				public String getOnClick(Activity activity, ProcessInstance instance, Map options) {
					String onClick = super.getOnClick(activity, instance, options);
					//sb.insert(0, "<div id='canvasForLoopLines111' style=\"position:absolute;z-index:200;display:'NONE'\"><table cellpadding=0 cellspacing=0><td height=50></td></table><table cellpadding=20 bgcolor=gray><td bgcolor=white>");
					//sb.append("</td></table></div>");
					
					try{
						if(instance.isRunning("")){
							String instanceId = instance.getInstanceId()+";";
							List<Activity> cActList = instance.getCurrentRunningActivities();
							
							onClick =  onClick +";openSubProcess(\"scope\",\""+ ((Activity)cActList.get(0)).getTracingTag() +"\",\""+ activity.getTracingTag() +"\", null, \"" + instanceId +"\", null, null)";
								
							return onClick;		
						}
						String defVerId = activity.getProcessDefinition().getId();

						onClick = onClick + ";openSubProcess(\"scope\",null,\""+ activity.getTracingTag() +"\", "+defVerId+", null, null, null)";

					}catch(Exception e){
						e.printStackTrace();
					}

					return onClick;
				}
				
				protected String getDetails(
						Activity activity,
						ProcessInstance instance,
						Map options) {
							
					//return ("<div id='"+getActivityDivName(instance, activity, options)+"'></div>");
					return "<div id='subprocessActivity" + activity.getTracingTag() +"'></div>";
				

				}
				
				private String getActivityDivName(ProcessInstance instance, Activity activity, Map options){
					return "scopeActivity" + SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, activity.getTracingTag());
				}
				
			};

			boolean scopeCanBeHightlightedAsWell = false;
			try{
				if(options.containsKey("highlight")){
					String highlightedTracingTag = (String)options.get("highlight");
					Activity theHightlightedActivity = activity.getProcessDefinition().getActivity(highlightedTracingTag);
					scopeCanBeHightlightedAsWell = (activity.isAncestorOf(theHightlightedActivity));
				}
			}catch(Exception e){
			}

			if(scopeCanBeHightlightedAsWell){
				options.put("highlight", activity.getTracingTag());
			}
			
			
			return (collapsedViewer.render(activity, instance, options));

		}
		
		
		StringBuilder sb = super.render(activity, instance, options);
     	String locale = (String) options.get("locale");
		boolean isVertical = options.containsKey("vertical");
		boolean definitionView = false;
		try {
			definitionView = (instance==null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) {
		}

		if(definitionView && options.containsKey("ShowAllComplexActivities")){
			
			ScopeActivity scopeAct = (ScopeActivity)activity;
			
			if(isVertical){
				sb.insert(0,"<table border=0 cellpadding=0 cellspacing=0><tr><td>");
				sb.append("</td><td>");
			}
			
//			sb.append("<center>");
			if(isVertical)    sb.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
			
			if(scopeAct.getEventHandlers()!=null)
			for(int i=0; i<scopeAct.getEventHandlers().length; i++){
				EventHandler eh = scopeAct.getEventHandlers()[i]; 
				if(isVertical) sb.append("<td width=50>");
				sb.append("<img src=" + imagePathRoot + "images/event.gif border=0 align=absmiddle><font size=-3>" + eh.getDisplayName().getText(locale) + "</font><br>");
				sb.append(DefaultActivityViewer.createViewer(eh.getHandlerActivity()).render(eh.getHandlerActivity(), instance, options));
				if(isVertical) sb.append("</td>");
			}
	
			if(isVertical) sb.append("</tr></table> </td></tr></table>");
			
			sb.insert(0, "<div style='border:1px dotted #dddddd'>");
			sb.append("</div>");
		}
		
			
		return sb;
	}
	
}
