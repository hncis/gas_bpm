package org.uengine.kernel.viewer;

/**
 * @author Jinyoung Jang
 */

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessInstance;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public class AtomicActivityViewer extends ComplexActivityViewer{

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		
		StringBuilder sb = new StringBuilder();
		
		String statusColor="#000000";
		if(instance!=null){
			String status = "unresolved";
			try{
				status = instance.getStatus(activity.getTracingTag());
			}catch(Exception e){
				e.printStackTrace();
			}

			statusColor = DefaultActivityViewer.getStatusColor(status);
			if(status.equals(Activity.STATUS_READY))
				statusColor = "#000000";			
		}
				
//		sb.append("<table border=1 cellpadding=0 cellspacing=4><!--td width=2 bgcolor=gray></td--><td>");
		sb.append("<table border=0 cellpadding=0 cellspacing=3><td style=\"border:1px dotted ").append(statusColor).append(";\">");
		
//		sb.append("<td>");
		sb.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		
		for (Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext(); ) {
			sb.append("<td><img src=images/arrow.gif></td><td>");
			Activity child = iter.next();
			
			ActivityViewer viewer = DefaultActivityViewer.createViewer(child);
			StringBuilder childHTML = viewer.render(child, instance, options);
			
			sb.append(childHTML);
			sb.append("</td>");
		}
		
		//sb.append(activity.getName());
		sb.append("<td><img src=images/arrow.gif></td></tr></table>");
//		sb.append("</td><!--td width=2 bgcolor=gray></td--></table>");
		sb.append("</td></table>");
		
//		insertHeader(activity, instance, sb, options);
		
		return sb;
	}	
	public String getHeader(Activity activity, ProcessInstance instance, Map options){return "";}		

	public String getLabel(
		Activity activity,
		ProcessInstance instance,
		Map options) {
		return "";
	}

}