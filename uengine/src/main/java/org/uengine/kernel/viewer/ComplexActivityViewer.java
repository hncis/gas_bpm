package org.uengine.kernel.viewer;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.ApprovalLineActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.Command;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ComplexActivityViewer implements ActivityViewer{
	static final int separatorWidth = 2; 
	static final boolean allBoxing = true;
	static final int unfocusedOpacity = 80;
	static final String unfocusedGrayColor = "959595";

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);
		boolean definitionView = false;
		try {
			definitionView = (instance==null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) {
		}

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
		
		boolean isVertical = options.containsKey("vertical");
		
		if(allBoxing){
			String style = (options.containsKey("ShowAllComplexActivities") || activity instanceof LoopActivity || activity instanceof ApprovalLineActivity ? "border:1px dotted " + statusColor +"; padding: 5px;" : "");
			sb.append("<div style='"+style+"'>");
		} else {
			sb.append(
					"<table border='0' cellpadding='0' cellspacing='3'><tr><td width='"+separatorWidth+"' bgcolor='" + statusColor + "'><img src='' width='"+separatorWidth+"' height=0></td>"
			);
			if(isVertical){
				sb.append("<td>");
			} else {
				sb.append("<td align='center'>");
			}
		}
		

		sb.append("<table border='0' cellpadding='0' cellspacing='0'>");
		
		if(!isVertical){
			sb.append("<tr>");
		}
		
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		
		boolean firstArrow = true;
		
		for(Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext();){
			Activity child = iter.next();
			ActivityUtil activityUtil = new ActivityUtil();
			
			if (!activityUtil.isVisible(child, options)) {
				
				if (!firstArrow || options.containsKey("ShowAllComplexActivities")) {
					boolean isGrayArrow = false;
					if (!definitionView)
					try {
						isGrayArrow = Activity.STATUS_READY.equals(child.getStatus(instance));
					} catch(Exception e) {}
					
					if (isVertical) sb.append("<tr>");
					sb.append(
							"<td align='center'><img src='" + imagePathRoot + "images/arrow" + (isVertical ? "_vertical" : "") + ".gif' " 
//							+ (isGrayArrow ? "style='filter:alpha(opacity="+unfocusedOpacity+")'":"")
							+ "></td>"
					);
					if (isVertical) sb.append("</tr>");
				}
				
				firstArrow = false;
				
				ActivityViewer viewer = DefaultActivityViewer.createViewer(child);
				StringBuilder childHTML = viewer.render(child, instance, options);
				
				if (isVertical) sb.append("<tr>");

				if (options.containsKey("valign")){
					String valign = (String)options.get("valign");
					options.remove("valign");

					sb.append("<td valign='" + valign + "' id='" + DefaultActivityViewer.getActivityName(child, instance, options) + "'>");
				} else {
					sb.append("<td align='center' id='" + DefaultActivityViewer.getActivityName(child, instance, options) + "'>");
				}
				
				sb.append(childHTML);
				
				sb.append("</td>");
				if(isVertical) sb.append("</tr>");
			}
		}
		
		//sb.append(activity.getName());
		if(options.containsKey("ShowAllComplexActivities")){
			boolean isGrayArrow = false;
			if(!definitionView)
				try{
					isGrayArrow = !Activity.STATUS_COMPLETED.equals(activity.getStatus(instance));
				}catch(Exception e){}
				
			if(isVertical) sb.append("<tr>");
			sb.append(
					"<td align='center'><img src='" + imagePathRoot +"images/arrow" + (isVertical ? "_vertical":"") + ".gif' "
					+ (isGrayArrow ? " style='filter:alpha(opacity="+unfocusedOpacity+")'":"")
					+ "></td>"
			);
			if (isVertical) sb.append("</tr>");
		}

		if (!isVertical) {
			sb.append("</tr>");
		}

		sb.append("</table>");
//		sb.append("</td><!--td width=2 bgcolor=gray></td--></table>");
		
		//if(!isVertical){
			if(allBoxing){
				sb.append("</div>");
			}else{
				if(isVertical){
					sb.append("</td></tr><tr><td>");
				} else {
					sb.append("</td><td width='2' bgcolor='"+statusColor+"'><img src='' width='" + separatorWidth + "' height='0'>");
				}
				
				sb.append("</td></tr></table>");
			}
		//}
		
		insertHeader(activity, instance, sb, options);
	
		return sb;
	}
	
	public String getHeader(Activity activity, ProcessInstance instance, Map options){
		if(options.containsKey("ShowAllComplexActivities")){
			boolean isVertical = options.containsKey("vertical");
			String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);
			return "";//"<img src="+imagePathRoot+"images/SequenceActivity"+ (isVertical ? "_vertical":"") +".gif>";
		}
		
		return "";
	}
	
	public void insertHeader(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		StringBuilder headerHtml = new StringBuilder();
		boolean isVertical = options.containsKey("vertical");

		headerHtml.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
		headerHtml.append("<td align=" + (isVertical ? "center":"") + ">");

		if(options.containsKey("flowControl") && instance!=null){
			if(appendFlowController(activity, instance, headerHtml, options)) {
				headerHtml.append("<br />");
			}
		}

		String label = getLabel(activity, instance, options);
		headerHtml.append(
				getHeader(activity, instance, options)
//				+ "<br>" + (label != null ? label : "")
				+ "</td>"
				+ (isVertical ? "</tr><tr>":"")
				+ "<td align='center'>"
		);
		
		sb.insert(0, headerHtml);

		sb.append("</td></tr></table>");
	}
	
	public String getLabel(Activity activity, ProcessInstance instance, Map options){
		String locale = (String)options.get("locale");
		
//		return activity.getName().getText(locale);
		return "";
	}
	
	protected boolean appendFlowController(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		return DefaultActivityViewer.appendFlowController(activity, instance, sb, options);
	}
}