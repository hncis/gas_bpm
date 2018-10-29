/*
 * Created on 2004. 12. 19.
 */
package org.uengine.kernel.viewer;

import java.util.Map;

import org.uengine.components.activityfilters.TimeDeterminationFilter;
import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */
public class HumanActivityViewer extends DefaultActivityViewer{
	
	public StringBuilder render(
		Activity activity,
		ProcessInstance instance,
		Map options) {
		
		StringBuilder sb =super.render(activity, instance, options);		
		HumanActivity humanActivity = (HumanActivity)activity;
		
		
		if(options.containsKey("show mapping")){
			String worker = null;                
							
			if(instance!=null){
				try{
					worker = humanActivity.getRole().getMapping(instance, activity.getTracingTag()).getName();
					if(options.containsKey("show photo")){
						//show photo of the user	
					}
				}catch(Exception e){
				}
			}
			
			if(worker==null)
				worker = humanActivity.getRole().toString(); 
				
			sb.insert(0, "<i><font size=-3 color=gray>&lt;" + humanActivity.getRole() + "&gt;</font></i>");
		}
		
		return sb;
	}

	public String getIconImageURL(Activity activity, ProcessInstance instance, Map options) {

		if(options!=null && options.containsKey("showUserPortrait") && instance!=null){
			options.put("prefferedActivityIconHeight", "25");
			HumanActivity humanActivity = (HumanActivity)activity;
			try {
				RoleMapping roleMapping = humanActivity.getRole().getMapping(instance);
				if(roleMapping!=null){
					if(roleMapping.getUserPortrait()==null) roleMapping.fill(instance);
					if(roleMapping.getUserPortrait()!=null)
						return roleMapping.getUserPortrait();
				}
			} catch (Exception e) {
			}
		}
		
		return super.getIconImageURL(activity, instance, options);
	}

	public StringBuffer getActivityPropertyString(Activity activity, ProcessInstance instance, Map options) throws Exception {
		return super.getActivityPropertyString(activity, instance, options);
	}
}
