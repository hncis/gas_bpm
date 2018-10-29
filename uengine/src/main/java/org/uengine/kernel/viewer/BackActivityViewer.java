package org.uengine.kernel.viewer;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uengine.contexts.ActivitySelectionContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.FlagActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.UEngineUtil;

public class BackActivityViewer extends DefaultActivityViewer{
	
	static final String BACK_ACTIVITY_ABSSTART_TRCTAGS = "back_activity_absstart_tracingtags";
	static final String BACK_ACTIVITY_ABSBACK_TRCTAGS = "back_activity_absback_tracingtags";
	
	public static void addBackActivityAbsoluteStartTracingTag(Map options, String absTracingTag){
		List loopActivityAbsTTs = getBackActivityAbsoluteStartTracingTags(options);
		loopActivityAbsTTs.add(absTracingTag);
	}
	
	public static List getBackActivityAbsoluteStartTracingTags(Map options){
		if(!options.containsKey(BACK_ACTIVITY_ABSSTART_TRCTAGS)){
			options.put(BACK_ACTIVITY_ABSSTART_TRCTAGS, new ArrayList());
		}
		
		List loopActivityAbsTTs = (List)options.get(BACK_ACTIVITY_ABSSTART_TRCTAGS);
		return loopActivityAbsTTs;
	}
	
	public static void addBackActivityAbsoluteBackTracingTag(Map options, String absTracingTag){
		List loopActivityAbsTTs = getBackActivityAbsoluteBackTracingTags(options);
		loopActivityAbsTTs.add(absTracingTag);
	}
	
	public static List getBackActivityAbsoluteBackTracingTags(Map options){
		if(!options.containsKey(BACK_ACTIVITY_ABSBACK_TRCTAGS)){
			options.put(BACK_ACTIVITY_ABSBACK_TRCTAGS, new ArrayList());
		}
		
		List loopActivityAbsTTs = (List)options.get(BACK_ACTIVITY_ABSBACK_TRCTAGS);
		return loopActivityAbsTTs;
	}
	
	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		
		boolean isVertical = options.containsKey("vertical");

		BackActivity backAct = (BackActivity)activity;
		String backActivitySequence = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, activity.getTracingTag());

		int targetSource = backAct.getTargetSource();
		ActivitySelectionContext asc = null;
		
		switch (targetSource) {
		case BackActivity.Flag:
			try {
				asc = FlagActivity.getFlagLocationForFlowChart(instance, backAct.getFlag());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			asc  = backAct.getTargetActivity();
			break;
		}
		
		if (asc != null) {
		    String TargetActivityTracingTag = asc.getTracingTag();
		    
		    addBackActivityAbsoluteStartTracingTag(options, TargetActivityTracingTag);
		    addBackActivityAbsoluteBackTracingTag(options, backActivitySequence);
		    
		}
		
		return super.render(activity, instance, options);
	}	
}
