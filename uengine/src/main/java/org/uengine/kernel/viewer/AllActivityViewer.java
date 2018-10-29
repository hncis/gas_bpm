package org.uengine.kernel.viewer;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.SwitchActivity;
import org.uengine.util.Command;
import org.uengine.util.UEngineUtil;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class AllActivityViewer extends ComplexActivityViewer{

	static final String All_ACTIVITY_ABS_TRCTAGS = "all_activity_abs_tracingtags";
	static final String INNER_ACTIVITY_ABS_TRCTAGS = "inner_activity_abs_tracingtags";
	
	public static void addAllActivityAbsoluteTracingTag(Map options, String absTracingTag){
		List<String> allActivityAbsTTs = getAllActivityAbsoluteTracingTags(options);
		allActivityAbsTTs.add(absTracingTag);
	}
	
	public static List<String> getAllActivityAbsoluteTracingTags(Map options){
		if(!options.containsKey(All_ACTIVITY_ABS_TRCTAGS)){
			options.put(All_ACTIVITY_ABS_TRCTAGS, new ArrayList());
		}
		
		List<String> allActivityAbsTTs = (List<String>) options.get(All_ACTIVITY_ABS_TRCTAGS);
		return allActivityAbsTTs;
	}

	public static void addInnerActivityAbsoluteTracingTag(Map options,String parentActivityTT, String childActivityTT){
		List<String> innerActivityAbsTTs = getInnerActivityAbsoluteTracingTags(options,parentActivityTT);
		boolean isExist=false;
		
		for(int i=0;i<innerActivityAbsTTs.size();i++){
			String tt= (String)innerActivityAbsTTs.get(i);
			
			if(tt.equals(childActivityTT))
				isExist=true;
		}
		
		if(!isExist)
			innerActivityAbsTTs.add(childActivityTT);
	}
	
	public static List getInnerActivityAbsoluteTracingTags(Map options,String parentActivityTT){
		if (!options.containsKey(INNER_ACTIVITY_ABS_TRCTAGS)) {
			HashMap innerActivityAbsTTsList=new HashMap();
			innerActivityAbsTTsList.put(parentActivityTT, new ArrayList());
			options.put(INNER_ACTIVITY_ABS_TRCTAGS,innerActivityAbsTTsList);
		} else {
			HashMap innerActivityAbsTTsList = (HashMap)options.get(INNER_ACTIVITY_ABS_TRCTAGS);
			
			if (!innerActivityAbsTTsList.containsKey(parentActivityTT)) {
				innerActivityAbsTTsList.put(parentActivityTT, new ArrayList());
			}
		}
		HashMap innerActivityAbsTTsList = (HashMap)options.get(INNER_ACTIVITY_ABS_TRCTAGS);
		return (List)innerActivityAbsTTsList.get(parentActivityTT);
	}
	
	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){

//		String loopActivitySequence = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, activity.getTracingTag());
		String loopActivitySequence = DefaultActivityViewer.getActivityName(activity, instance, options);
		addAllActivityAbsoluteTracingTag(options, loopActivitySequence);
		ViewerOptions viewerOptions = new ViewerOptions();
		
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);

		boolean isVertical = options.containsKey("vertical");
		boolean definitionView = false;
		try {
			definitionView = (instance==null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) {
		}
		
		StringBuilder sb = new StringBuilder();
			
		String statusColor="#000000";
		if(!definitionView){
			String status = "unresolved";
			try {
				status = instance.getStatus(activity.getTracingTag());
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			statusColor = DefaultActivityViewer.getStatusColor(status);
			if (status.equals(Activity.STATUS_READY))
				statusColor = unfocusedGrayColor;			
		}
		
		boolean allBoxing = false;
		
//		sb.append("<table border=1 cellpadding=0 cellspacing=3><!--td width=2 bgcolor=gray></td--><td>");
		if (allBoxing) {
			sb.append("<div style='border:1px dotted " + statusColor + ";'");
		} else {
			if (isVertical) {
				sb.append("<div class='flowchart-box-vertical'>");
			} else {
				sb.append("<div class='flowchart-box-horizontal'>");
			}
		}
		
		sb.append("<table border='0' cellpadding='0' cellspacing='0'>");
		if (isVertical) sb.append("<tr>");
		
		ComplexActivity cActivity = (ComplexActivity) activity;
		
		int i=0;
		for (Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext(); ) {
			ActivityUtil activityUtil = new ActivityUtil();
			Activity child = iter.next();		
	
			if(!activityUtil.isVisible(child, options)) {
				addInnerActivityAbsoluteTracingTag(
						options,
						DefaultActivityViewer.getActivityName(activity, instance, options),
						DefaultActivityViewer.getActivityName(child, instance, options)
				);
				
				String childLabel = getLabel(i++, activity, instance, options);

				if (isVertical) {
					sb.append("<td valign='top' align='center' height='100%'>");
				} else {
					sb.append("<tr><td colspan='2'>");
				}

				if(childLabel!=null){
					sb.append("<div class='flowchart-condition-title'>[ " + childLabel + " ]</div>");
				}
				
//				boolean isGrayArrow = false;
//				if (!definitionView)
//				try {
//					isGrayArrow = Activity.STATUS_READY.equals(child.getStatus(instance));
//				} catch(Exception e) {}

//				sb.append("</tr><tr>");
				//sb.append("<td align=center><img src="+imagePathRoot+"images/arrow"+ (isVertical ? "_vertical":"")+".gif" +  (isGrayArrow ? " style='filter:alpha(opacity="+unfocusedOpacity+")'":"") +"></td>");
				
				if (isVertical) {
					sb.append("<div style='text-align:center;' class='flowchart-activity-cover-vertical'");
				} else {
					sb.append("</td></tr><td><div class='flowchart-activity-cover-horizontal'");
				}
				sb.append(" id='" + DefaultActivityViewer.getActivityName(child, instance, options) + "'>");

				ActivityViewer viewer = DefaultActivityViewer.createViewer(child);
				StringBuilder childHTML = viewer.render(child, instance, options);

//				if (!definitionView)
//					try {
//						isGrayArrow = !Activity.STATUS_COMPLETED.equals(child.getStatus(instance));
//					} catch(Exception e) {}
				
				sb.append(childHTML + "</div>");
				
				if(isVertical){
					sb.append("<div style='width:2px;'></div></td>");
					//sb.append("<tr><td align=center><img src="+imagePathRoot+"images/arrow"+ (isVertical ? "_vertical":"")+".gif" +  (isGrayArrow ? " style='filter:alpha(opacity="+unfocusedOpacity+")'":"") +"></td></tr>");
//					sb.append("</table>");
					
				}else{
					sb.append("</td></tr>");
				}
			}
		}
		
		if(activity instanceof SwitchActivity && cActivity.getChildActivities().size()==1){
//			if(childLabel!=null)
//				sb.append("<tr><td colspan=2><font face=-2 color=gray>" + childLabel + "</font></td></tr>");
			if(isVertical) {
			   sb.append("<td>[ " + GlobalContext.getMessageForWeb("Otherwise", (String) options.get(viewerOptions.LOCALE)) + "]");
			   sb.append("<div style='text-align:center; height:100%' class='flowchart-line-vertical'></div>");
			   sb.append("<div style='text-align:center;'><img src='").append(imagePathRoot).append("images/arrow_vertical.gif' />").append("</div>");
			 //  sb.append("<tr><td height=100% >&nbsp;</tr>");
			} else {
				sb.append("<tr><td width='100%' colspan=3>[ " + GlobalContext.getMessageForWeb("Otherwise", (String) options.get(viewerOptions.LOCALE)) + "]</tr>");
				sb.append("<tr><td width='100%' colspan='2'><div class='flowchart-line-horizontal'></div></td>");
				sb.append("<td><img src=").append(imagePathRoot).append("images/arrow.gif></td></tr>");
//				sb.append("<tr><td width=100% colspan=3>&nbsp;</td></tr>");
			}

			options.put("valign", "top");
		}
		
		//sb.append(activity.getName());
		if (isVertical) sb.append("</tr>");
		sb.append("</table>");
//		sb.append("</td><!--td width=2 bgcolor=gray></td--></table>");
		if(allBoxing){
			sb.append("</div>");
		}else{
			if(isVertical){
				sb.append("</div>");
			}else{
				sb.append("</div>");
			}

//			sb.append("</td><td width=2 bgcolor="+statusColor+"><img src='' width=2 height=0></td></table>");
		}
		
		insertHeader(activity, instance, sb, options);
		
		return sb;
	}

	public String getHeader(Activity activity, ProcessInstance instance, Map options){
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);
		
		boolean definitionView = false;
		try {
			definitionView = (instance==null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) {
		}

		boolean isGrayArrow = false;
		if(!definitionView)
			try{
				isGrayArrow = Activity.STATUS_READY.equals(activity.getStatus(instance));
			}catch(Exception e){}
			
		String className = UEngineUtil.getClassNameOnly(activity.getClass());
		boolean isVertical = options.containsKey("vertical");
		
		return "<img class='' src='" + imagePathRoot + "images/" + className
				+ (isVertical && "AllActivity".equals(className) ? "_vertical" : "") + ".gif'"
				+ (isGrayArrow ? " style='filter:alpha(opacity=" + unfocusedOpacity + ")'" : "") + ">";
	}		

	protected String getLabel(int i, Activity activity, ProcessInstance instance, Map options){
		return null;
	}

}