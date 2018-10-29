/*
 * Created on 2004. 11. 15.
 */
package org.uengine.kernel.viewer;

import java.util.Map;
import org.uengine.kernel.*;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */
public class ApprovalActivityViewer extends HumanActivityViewer{
	
	public String getLabel(
		Activity activity,
		ProcessInstance instance,
		Map options) {
			
		StringBuilder label = new StringBuilder();
		label.append(super.getLabel(activity, instance, options));
		
		ApprovalActivity thisActivity = (ApprovalActivity)activity;
		
		try{
			String signMsg = "";
			String apprStat = thisActivity.getApprovalStatus(instance);
			if( ApprovalActivity.SIGN_APPROVED.equals(apprStat)){
				signMsg = "Approved";
			}else if( ApprovalActivity.SIGN_ARBITRARY_APPROVED.equals(apprStat) ){
				signMsg = "Arbitrary Approved";
			}else if( ApprovalActivity.SIGN_REJECT.equals(apprStat) ){
				signMsg = "Rejected";
			}
			RoleMapping mapping = thisActivity.getRole().getMapping(instance, thisActivity.getTracingTag());
			
			label.append("<br><font size=-2>[").append((UEngineUtil.isNotEmpty(mapping.getResourceName()) ? mapping.getResourceName()+"/" : "")).append(signMsg).append("]</font>");
		}catch(Exception e){
		}
		
		return label.toString();
	}
}
