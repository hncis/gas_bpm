package com.defaultcompany.external.activities;

import java.util.List;
import java.util.Vector;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.ScopeActivity;
import org.uengine.processdesigner.SimulatorProcessInstance;
import org.uengine.util.ActivityForLoop;

public class ExternalApprovalLineActivity extends ScopeActivity {
	
	private static final long serialVersionUID = 1L;
	
	public static final int LOOPING_OPTION_BACK_TO_DRAFT = 1;
	public static final int LOOPING_OPTION_BACK_TO_PREV = 2;
	public static final int LOOPING_OPTION_BACK_TO_FLAG = 3;
	public static final int LOOPING_OPTION_FINISH = 4;

	public final static String KEY_APPR_LINE_STATUS = "KEY_APPR_LINE_STATUS";
	public final static String KEY_APPR_KEY = "KEY_APPR_KEY";

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd = type.getFieldDescriptor("RejectOption");
		fd.setInputter(new RadioInput(
				new String[] { "back to draft", "back to prev", "back to flag", "finish" }, new Integer[] {
						ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_DRAFT,
						ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_PREV,
						ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_FLAG,
						ExternalApprovalLineActivity.LOOPING_OPTION_FINISH }));
	}
	
	public ExternalApprovalLineActivity() {
		setName("");
		setRejectOption(ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_DRAFT);
		ExternalApprovalActivity draftActivity = new ExternalApprovalActivity();
		draftActivity.setName("Draft");
		addChildActivity(draftActivity);
	}

	public void executeActivity(ProcessInstance instance) throws Exception {
		super.executeActivity(instance);
		
/*		if(getCurrentStep(instance) > 1){
			fireComplete(instance);
		}
*/	}
	
	public void setApprovalLineStatus(ProcessInstance instance, String status) throws Exception{
		instance.setProperty(getTracingTag(), KEY_APPR_LINE_STATUS, status);
		if (getStatusOutPV() != null) {
			getStatusOutPV().set(instance, "", status);
		}
	}
	
	public String getApprovalLineStatus(ProcessInstance instance) throws Exception{
		return (String)instance.getProperty(getTracingTag(), KEY_APPR_LINE_STATUS);
	}
	
	@SuppressWarnings("rawtypes")
	public void resetApprovalLine() {

		List appActList = getChildActivities();
		String draftActivityTT = getDraftActivity().getTracingTag();

		for (int i = 0; i < appActList.size(); i++) {
			Activity act = (Activity) appActList.get(i);
			if (!act.getTracingTag().equals(draftActivityTT)) {
				removeChildActivity(act);
			}
		}
	}
	
	ExternalApprovalActivity draftActivity;
	public ExternalApprovalActivity getDraftActivity() {
		if (draftActivity != null)
			return draftActivity;

		ActivityForLoop findingLoop = new ActivityForLoop() {
			public void logic(Activity activity) {
				if (activity instanceof ExternalApprovalActivity) {
					stop(activity);
				}
			}
		};

		findingLoop.run(this);
		this.draftActivity = (ExternalApprovalActivity) findingLoop.getReturnValue();

		return draftActivity;
	}
	
	//referencer (11.23)
	protected void afterExecute(ProcessInstance instance) throws Exception {
		super.afterExecute(instance);
	}
	
	//receiver (11.23)
	protected void afterComplete(ProcessInstance instance) throws Exception {

		boolean isSimulate = instance instanceof SimulatorProcessInstance;

		super.afterComplete(instance);
		if (getReceiverRole() != null && !isSimulate) {
			RoleMapping receivers = getReceiverRole().getMapping(instance);
			if (receivers != null) {
				receivers.setDispatchingOption(Role.DISPATCHINGOPTION_RECEIVE);
				instance.putRoleMapping("receiver_" + getTracingTag(), receivers);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void onEvent(String command, ProcessInstance instance, Object payload) throws Exception{
		//review: performance: need to use 'Hashtable' to locate the command or directly invocation from fire... methods.
		boolean isSimulate =  instance instanceof SimulatorProcessInstance;
		
		if (command.equals(CHILD_DONE) && getDraftActivity().equals(payload) && !isSimulate) {
			if (getReferencerRole() != null) {
				List<Activity> childActs = this.getChildActivities();
				for (int i=0; i<childActs.size(); i++) {
					if (childActs.get(i) instanceof HumanActivity) {
						HumanActivity humanAct = ((HumanActivity)childActs.get(i));
						humanAct.setReferenceRole(getReferencerRole());
					}
				}
			}
		}
	
		super.onEvent(command, instance, payload);
	}
	
	String flag;
		public String getFlag() {
			return flag;
		}	
		public void setFlag(String flag) {
			this.flag = flag;
		}
	
	Role referencerRole;
		public Role getReferencerRole() {
			return referencerRole;
		}
		public void setReferencerRole(Role referencerRole) {
			this.referencerRole = referencerRole;
		}

	Role receiverRole;
		public Role getReceiverRole() {
			return receiverRole;
		}
		public void setReceiverRole(Role receiverRole) {
			this.receiverRole = receiverRole;
		}

		
	int rejectOption;
		public int getRejectOption() {
			return rejectOption;
		}
	
		public void setRejectOption(int rejectOption) {
			this.rejectOption = rejectOption;
		}

	ProcessVariable statusOutPV;

		public ProcessVariable getStatusOutPV() {
			return statusOutPV;
		}
	
		public void setStatusOutPV(ProcessVariable statusOut) {
			this.statusOutPV = statusOut;
		}
}