package org.uengine.kernel.viewer;

/**
 * @author Jinyoung Jang
 */

import org.uengine.components.activityfilters.ProbabilityInstrumentationFilter;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.Condition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.util.UEngineUtil;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public class SwitchActivityViewer extends AllActivityViewer{
	
	protected String getLabel(int i, Activity activity, ProcessInstance instance, Map options){
		try{
			String locale = (String)options.get("locale");
			
			SwitchActivity switchActivity = (SwitchActivity)activity;
			
			boolean isVertical = options.containsKey("vertical");
			boolean isSelected = false;
			if(instance!=null /*&& instance.isRunning(activity.getTracingTag())*/) {
				Activity currChild = (Activity) switchActivity.getChildActivities().get(i);
				if (!instance.getStatus().equals(Activity.STATUS_RUNNING)) {
					if (currChild instanceof ComplexActivity) {
						while (currChild instanceof ComplexActivity) {
							currChild = (Activity) ((ComplexActivity) currChild).getChildActivities().get(0);
						}
					}
				}
				isSelected = !(currChild.getStatus(instance).equals(Activity.STATUS_READY));
			}
			
			String probability = "";
			if(options.containsKey("probability")){
				Long[] occurrenceAndTotal = ProbabilityInstrumentationFilter.getOccurrenceAndTotal(switchActivity, i);
				
				if(occurrenceAndTotal!=null){
					long occurrence=occurrenceAndTotal[0].longValue();
					long total=occurrenceAndTotal[1].longValue();
					probability = " ("+(occurrence*100 / total) + " %)";
				}else
					probability = " (No data)";
			}
			
			return 
				  (isSelected ? "<font color='black'><b>":"") 
				+ ((SwitchActivity)activity).getConditions()[i].getDescription().getText(locale)
				+ probability
				+ (isSelected ? "</b></font>":"");
		}catch(Exception e){
			return "condition" + i;
		}
	}

	public String getHeader(Activity activity, ProcessInstance instance, Map options){
		//String imagePathRoot = DefaultActivityViewer.getImagePathRoot(activity, instance, options);
		
		return super.getHeader(activity, instance, options);//"<img src="+imagePathRoot +"images/SwitchActivity.gif>";
	}		
	
	@Override
	public StringBuilder render(Activity activity, ProcessInstance instance, Map options) {
		// Lee YongHong made "showRanPathOnly" option
		if ( options.containsKey("showRanPathOnly") ) {
			try {
				ComplexActivity cActivity = (ComplexActivity) activity;
				String statusOfThecActivity = cActivity.getStatus(instance);
				
				if ( statusOfThecActivity.equals(Activity.STATUS_READY)) {
					return super.render(activity, instance, options);
				} else {
					for ( Iterator<Activity> iter = cActivity.getChildActivities().iterator() ; iter.hasNext(); ) {
						Activity child = iter.next();
						String statusOfTheChild = child.getStatus(instance);
						ActivityViewer aViewer = (ActivityViewer)UEngineUtil.getComponentByEscalation(child.getClass(), "viewer");
						if ( statusOfTheChild.equals(Activity.STATUS_FAULT) ) {							
							return aViewer.render(child, instance, options);
						}
						if ( !statusOfTheChild.equals(Activity.STATUS_READY) && !statusOfTheChild.equals(Activity.STATUS_RUNNING) && !statusOfTheChild.equals(activity.STATUS_COMPLETED) ) {
							return new StringBuilder();
						} else if ( statusOfTheChild.equals(Activity.STATUS_RUNNING) || statusOfTheChild.equals(activity.STATUS_COMPLETED) ) {
							return aViewer.render(child, instance, options); 
						}
					}
				}
				
			} catch( Exception e ) {
				e.printStackTrace();
			}			
		}
		return super.render(activity, instance, options);
	}
}