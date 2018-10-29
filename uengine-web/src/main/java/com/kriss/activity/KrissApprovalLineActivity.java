/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package com.kriss.activity; 

import java.util.Iterator;
import java.util.Map;

import org.uengine.kernel.Activity;
import org.uengine.kernel.FlagActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.ValidationContext;
import org.uengine.util.ActivityForLoop;

/**
 * <pre>
 * com.kriss.activity 
 * KrissApprovalLineActivity.java
 * 
 * </pre>
 * @date : 2016. 8. 4. 오후 5:26:04
 * @version : 
 * @author : mkbok_Enki
 */
public class KrissApprovalLineActivity extends SequenceActivity {
	
	boolean isAutoCompleteWhenPreiviosCompletedActivityByRole;
		public boolean isAutoCompleteWhenPreiviosCompletedActivityByRole() {
			return isAutoCompleteWhenPreiviosCompletedActivityByRole;
		}
		public void setAutoCompleteWhenPreiviosCompletedActivityByRole(
				boolean isAutoCompleteWhenPreiviosCompletedActivityByRole) {
			this.isAutoCompleteWhenPreiviosCompletedActivityByRole = isAutoCompleteWhenPreiviosCompletedActivityByRole;
		}

	ProcessVariable preConfirmPv;
		public ProcessVariable getPreConfirmPv() {
			return preConfirmPv;
		}
		public void setPreConfirmPv(ProcessVariable preConfirmPv) {
			this.preConfirmPv = preConfirmPv;
		}

	String approvalLineStatusCode;
		public String getApprovalLineStatusCode() {
			return approvalLineStatusCode;
		}
		public void setApprovalLineStatusCode(String approvalLineStatusCode) {
			this.approvalLineStatusCode = approvalLineStatusCode;
		}
	
	Role drafter;
		public Role getDrafter() {
			return drafter;
		}
		public void setDrafter(Role drafter) {
			this.drafter = drafter;
		}
	
	public KrissApprovalLineActivity() {
		KrissApprovalActivity draftActivity = new KrissApprovalActivity();
		draftActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovalactivity.draft.message", "draft"));
		addChildActivity(draftActivity);
		//결재선 위치
		KrissApprovalSequenceActivity approvalSequenceActivity = new KrissApprovalSequenceActivity();
		FlagActivity approvalFlagActivity = new FlagActivity();
		approvalFlagActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovalflagactivity.message", "approval line position"));
		approvalSequenceActivity.addChildActivity(approvalFlagActivity);
		addChildActivity(approvalSequenceActivity);
	}
	
	KrissApprovalActivity draftActivity;
		public KrissApprovalActivity getDraftActivity() {
			KrissApprovalActivity draftActivity = null;
			for(Iterator<Activity> i = getChildActivities().iterator(); i.hasNext(); ){
				Activity act = i.next();
				if(act instanceof KrissApprovalActivity){
					draftActivity = (KrissApprovalActivity) act;
					break;
				}
			}
			
			this.draftActivity = draftActivity; 
			
			return draftActivity;
		}
	
	KrissApprovalSequenceActivity approvalSequenceActivity;
		public KrissApprovalSequenceActivity getApprovalSequenceActivity() {
			if(approvalSequenceActivity!=null)
				return approvalSequenceActivity;
			
			ActivityForLoop findingLoop = new ActivityForLoop(){
				public void logic(Activity activity){
					if(activity instanceof KrissApprovalSequenceActivity){
						stop(activity);
					}
				}
			};
			
			findingLoop.run(this);		
			this.approvalSequenceActivity = (KrissApprovalSequenceActivity) findingLoop.getReturnValue(); 
			
			return approvalSequenceActivity;
		}
		@Override
		public ValidationContext validate(Map options) {
			// TODO Auto-generated method stub
			ValidationContext valCtx = super.validate(options);
			
			if(getDrafter()==null)
				valCtx.add(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovallineactivity.fields.drafter.displayname", "drafter") + " Role is not specified");
			
			if(getApprovalLineStatusCode()==null)
				valCtx.add(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovallineactivity.fields.approvallinestatuscode.displayname", "approvalline status code") + " is not specified");
			
			if(getPreConfirmPv()==null)
				valCtx.addWarning(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovallineactivity.fields.preconfirmpv.displayname", "preConfirm Process Variable") + " is not specified");
			
			return valCtx;
		}
		
		

}
