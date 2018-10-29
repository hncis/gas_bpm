package org.uengine.kernel.viewer.svg;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.viewer.ActivityViewer;
import org.uengine.util.UEngineUtil;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class DefaultActivityViewer implements ActivityViewer{

	static Hashtable viewerPool = new Hashtable();
	static Properties statusColors = new Properties();
	static{
		statusColors.setProperty(Activity.STATUS_READY, "#ffffff");
		statusColors.setProperty(Activity.STATUS_COMPLETED, "#aaaaaa");
		statusColors.setProperty(Activity.STATUS_RUNNING, "#20aa20");
		statusColors.setProperty(Activity.STATUS_FAULT, "#ee2020");
		statusColors.setProperty(Activity.STATUS_SKIPPED, "#2020aa");
		statusColors.setProperty(Activity.STATUS_SUSPENDED, "#20cccc");
		statusColors.setProperty(Activity.STATUS_TIMEOUT, "#aaaa20");
	}
	
	static ActivityViewer instance;
		public static ActivityViewer getInstance() {
			if (instance == null)
				instance = new DefaultActivityViewer();

			return instance;
		}

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		
		StringBuilder sb = new StringBuilder();
		
		String statusColor="";
		String status="";
		if(instance!=null){
			status = "unresolved";
			try{
				status = instance.getStatus(activity.getTracingTag());
			}catch(Exception e){
				e.printStackTrace();
			}
			statusColor = " bgcolor=" + getStatusColor(status);
		}
				
		Class activityCls = activity.getClass();
		String clsName = activityCls.getName().substring( activityCls.getName().lastIndexOf(".")+1);	
		
		sb.append("<table cellpadding=3 border=0 cellspacing=1><td" +statusColor+ ">");
		sb.append("<table border=0 cellpadding=2 cellspacing=0><td bgcolor=white><center>");
		
		if(options.containsKey("flowControl") && instance!=null){
			if(appendFlowController(activity, instance, sb, options))
				sb.append("<br>");
		}
		
		/*if(instance!=null) */sb.append("<a href='" +getAnchor(activity, instance, options) + "'>");
		
		try{
			if(options.containsKey("highlight") && options.get("highlight").equals(activity.getTracingTag()))
				sb.append("<img src=images/blinkarrow.gif border=0><br>");
		}catch(Exception e){
		}
			
		sb.append("<img src=images/"+ clsName +".gif border=0 alt='" + activity.getDescription() + "'>");
		if(instance!=null) sb.append("</a>");
		
		sb.append("<br>" + getLabel(activity, instance, options));
		
		if(instance!=null){
			sb.append("<br><font size=-2>");
			
			try{
				if(instance.isFault(activity.getTracingTag()))
					sb.append(instance.getFault(activity.getTracingTag()));
				else
					sb.append(status);
			}catch(Exception e){
				e.printStackTrace();
				
				sb.append("can't load fault");
			}
			
		}
		
		sb.append("</td></table></td></table>");
		
		return sb;
	}
	
	public String getAnchor(Activity activity, ProcessInstance instance, Map options){
		String instanceId = (instance!=null ? ("\"" + instance.getInstanceId() + "\"") : "null");

		try{		
			return "javascript:showActivityDetails(\"" + activity.getProcessDefinition().getId() + "\", " + instanceId + ", \"" + activity.getTracingTag() + "\")";
		}catch(Exception e){
			return "";
		}
	}
	
	public String getLabel(Activity activity, ProcessInstance instance, Map options){
		return activity.getName().getText();
	}
	
	public static ActivityViewer createViewer(Activity act){
		Class activityCls = act.getClass();
		//String clsName = UEngineUtil.getComponentClassName(activityCls, "viewer");
		
		ActivityViewer viewer = null;
		
		if(viewerPool.containsKey(activityCls))
			return (ActivityViewer)viewerPool.get(activityCls);
		
/*		try{		
			Class viewerCls = Class.forName(clsName);
			viewer = (ActivityViewer)viewerCls.newInstance();
		
		}catch(Exception e){
			//e.printStackTrace();
			
			viewer = DefaultActivityViewer.getInstance();
		}*/
		
		viewer = (ActivityViewer)(UEngineUtil.getComponentByEscalation(activityCls, "viewer", DefaultActivityViewer.getInstance()));
		viewerPool.put(activityCls, viewer);
		
		return viewer;
	}
	
	public static String getStatusColor(String status){		
		return statusColors.getProperty(status);
	}
	
	protected static boolean appendFlowController(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		
		String status ="unresolved";
		try{
			status = instance.getStatus(activity.getTracingTag());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		boolean bAppended = false;
			
		if(Activity.isCompensatable(status)){
			sb.append("<a href=\"javascript:compensate('"+activity.getTracingTag()+"')\"><img src=images/rewind.gif border=0></a>");
			bAppended = true;
		}

		if(Activity.isSkippable(status)){
			sb.append("<a href=\"javascript:skip('"+activity.getTracingTag()+"')\"><img src=images/forward.gif border=0></a>");
			bAppended = true;
		}

		if(Activity.isResumable(status)){
			sb.append("<a href=\"javascript:resume('"+activity.getTracingTag()+"')\"><img src=images/resume.gif border=0></a>");
			bAppended = true;
		}
		else if(Activity.isSuspendable(status)){
			sb.append("<a href=\"javascript:suspend('"+activity.getTracingTag()+"')\"><img src=images/suspend.gif border=0></a>");
			bAppended = true;
		}
		
		return bAppended;		
	}
}