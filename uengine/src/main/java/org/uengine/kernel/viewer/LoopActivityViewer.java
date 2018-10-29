package org.uengine.kernel.viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.ApprovalLineActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.ProcessInstance;

/**
 * @author Jinyoung Jang
 */

public class LoopActivityViewer extends ComplexActivityViewer{
	
	static final String SEQ_LOOP_ACTIVITY = "seq_loop_activity";
	static final String LOOP_ACTIVITY_ABS_TRCTAGS = "loop_activity_abs_tracingtags";
	
	public static int nextLoopActivitySequence(Map options){
		
		int currSeq = 0;
			
		if(options.containsKey(SEQ_LOOP_ACTIVITY)){
			currSeq = ((Integer)options.get(SEQ_LOOP_ACTIVITY)).intValue();
		}
		
		options.put(SEQ_LOOP_ACTIVITY, new Integer(currSeq+1));
		
		return currSeq;
	}
	
	public static void addLoopActivityAbsoluteTracingTag(Map options, String absTracingTag){
		List<String> loopActivityAbsTTs = getLoopActivityAbsoluteTracingTags(options);
		loopActivityAbsTTs.add(absTracingTag);
	}
	
	public static List<String> getLoopActivityAbsoluteTracingTags(Map options){
		if(!options.containsKey(LOOP_ACTIVITY_ABS_TRCTAGS)){
			options.put(LOOP_ACTIVITY_ABS_TRCTAGS, new ArrayList());
		}
		
		List<String> loopActivityAbsTTs = (List<String>) options.get(LOOP_ACTIVITY_ABS_TRCTAGS);
		return loopActivityAbsTTs;
	}
		
	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		
		//String loopActivitySequence = ""+nextLoopActivitySequence(options);
		boolean isVertical = options.containsKey("vertical");

		String loopActivitySequence = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, activity.getTracingTag());
		addLoopActivityAbsoluteTracingTag(options, loopActivitySequence);
		
		String locale = (String)options.get("locale");
		String imagePathRoot = DefaultActivityViewer.getImagePathRoot(options);
		
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
				
		//div
		sb.append("<div id='loop" + loopActivitySequence + "'>");
		//
		
		String style = (options.containsKey("ShowAllComplexActivities") || activity instanceof LoopActivity || activity instanceof ApprovalLineActivity ? "border:1px dotted " + statusColor +";" : "");
		
/*		if(allBoxing){
			sb.append("<table border=0 cellpadding=0 cellspacing=3><td style=\""+style+"\">");
		}else{
			sb.append("<table border=0 cellpadding=0 cellspacing=3><td width="+separatorWidth+" bgcolor=" + statusColor + "><img src='' width=2 height=0></td>");		
			sb.append("<td>");
		}		
*/		
		String loopLabel = activity.getName().getText(locale);
		int loopLabelsize = loopLabel.length() * 7;
		if(loopLabelsize < 20) loopLabelsize =20;
		
//		if(((LoopActivity)activity).getLoopingCount(instance) > 0){
		if(instance!=null)
		try {
			loopLabel = loopLabel + " (" + ((LoopActivity)activity).getLoopingCount(instance) + ")";
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		
		sb.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
		
		int spanSize = (((ComplexActivity)activity).getChildActivities().size() * 2 + 3);
		
		if(isVertical){
			sb.append("<td id=leftSpacer"+loopActivitySequence+" align='center' width="+loopLabelsize+" rowspan='"+ spanSize +"'><img src='" + imagePathRoot + "images/empty.gif' width='" + loopLabelsize + "' height='0' /></td>");
		}else{
			sb.append("<td height=20 colspan="+ spanSize +"><center><table cellpadding=0 cellspacing=0 border=0><td bgcolor=white>&nbsp;<font color=gray>"+ loopLabel +"&nbsp;</td></table></td></tr><tr>");
		}
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		
		sb.append("<td align='center'>");
		sb.append("<img id='start" + loopActivitySequence + "' src="+imagePathRoot+"images/feedbackpoint.gif />");
		sb.append("</td>");
		
		if(isVertical){
			sb.append("<td align=center rowspan="+ spanSize +"><table cellpadding=0 cellspacing=0 border=0><td bgcolor=white><img src='" + imagePathRoot + "images/empty.gif' width='" + loopLabelsize + "' height='0' /><div id=rightLabel"+loopActivitySequence+" style='text-align:center;'>&nbsp;<font color=gray>"+ loopLabel +"&nbsp;</div></td></table></td>");
			sb.append("</tr><tr>");
		}

		sb.append("<td align=center><img src="+imagePathRoot+"images/arrow"+ (isVertical ? "_vertical":"") +".gif" + "></td>");
		
		if(isVertical)
			sb.append("</tr>");

		
		boolean firstArrow = true;
		for(Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			ActivityUtil activityUtil = new ActivityUtil();
			
			if(!activityUtil.isVisible(child, options)) {
				if(!firstArrow){
					if(isVertical)
						sb.append("<tr>");
					
					sb.append("<td align=center><img src="+imagePathRoot+"images/arrow"+ (isVertical ? "_vertical":"") +".gif" + "></td>");

					if(isVertical)
						sb.append("</tr>");
				}

				if(isVertical){
					sb.append("<tr>");
				}
				else{
				}

				sb.append("<td align=center>");
				sb.append("<div id='" + DefaultActivityViewer.getActivityName(child, instance, options)+"'>");
				
				ActivityViewer viewer = DefaultActivityViewer.createViewer(child);
				
				StringBuilder childHTML = viewer.render(child, instance, options);
				
/*				if(firstArrow){
					sb.append("<div id='start" + activity.getTracingTag() + "'>");
					sb.append(childHTML);
					sb.append("</div>");
				}else{
*/					sb.append(childHTML);
//				}
				sb.append("</div><td>");
				
				if(isVertical)
					sb.append("</tr>");

				firstArrow = false;
			}
		}
				
		//for Loop
		if(isVertical){
			sb.append("<tr>");
		}
		sb.append("<td align=center><img src="+imagePathRoot+"images/arrow"+ (isVertical ? "_vertical":"") +".gif></td>");
		if(isVertical){
			sb.append("</tr><tr>");
		}
		
		if(isVertical){
			sb.append("<td align=center><table cellspacing=0 cellpadding=0 border=0><td id='switch" + loopActivitySequence + "'><img src="+imagePathRoot+"images/LoopActivity.gif></td></table></td>");
		}else{
			sb.append("<td align=center><div id='switch" + loopActivitySequence + "'><img src="+imagePathRoot+"images/LoopActivity.gif></div></td>");
		}
		
		if(isVertical){
			sb.append("</tr>");
		}

		//
		
		sb.append("</tr>");
		
		if(!isVertical){
			sb.append("<tr><td height=20></td></tr>");
		}

		sb.append("</table>");

/*		if(allBoxing){
			sb.append("</td></table>");
		}else{
			sb.append("</td><td width=2 bgcolor="+statusColor+"><img src='' width="+separatorWidth+" height=0></td></table>");
		}
*/
		//div
		sb.append("</div>");
		//

		//insertHeader(activity, instance, sb, options);
				
		return sb;
	}	


}