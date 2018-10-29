package org.uengine.kernel.viewer.svg;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.viewer.ActivityViewer;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ComplexActivityViewer implements ActivityViewer{
	static final int separatorWidth = 2; 
	static final boolean allBoxing = true;

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
		if(allBoxing){
			sb.append("<table border=0 cellpadding=0 cellspacing=3><td style=\"border:1px dotted "+ statusColor +";\">");
		}else{
			sb.append("<table border=0 cellpadding=0 cellspacing=3><td width="+separatorWidth+" bgcolor=" + statusColor + "><img src='' width=2 height=0></td>");		
			sb.append("<td>");
		}		
		
		sb.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		
		for(Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext();){
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
		if(allBoxing){
			sb.append("</td></table>");
		}else{
			sb.append("</td><td width=2 bgcolor="+statusColor+"><img src='' width="+separatorWidth+" height=0></td></table>");
		}
		
		insertHeader(activity, instance, sb, options);
		
		return sb;
	}
	
	public String getHeader(){return "<img src=images/SequenceActivity.gif>";}
	
	public void insertHeader(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		StringBuilder headerHtml = new StringBuilder();

		headerHtml.append("<table border=0 cellpadding=0 cellspacing=0><td align=right>");

		if(options.containsKey("flowControl") && instance!=null){
			if(appendFlowController(activity, instance, headerHtml, options))
				headerHtml.append("<br>");
		}

		headerHtml.append(getHeader() + "<br>" + getLabel(activity, instance, options) + "</td><td>");
		
		sb.insert(0, headerHtml);
		sb.append("</td></table>");
	}
	
	public String getLabel(Activity activity, ProcessInstance instance, Map options){
		return activity.getName().getText();
	}
	
	protected boolean appendFlowController(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		return DefaultActivityViewer.appendFlowController(activity, instance, sb, options);
	}
}