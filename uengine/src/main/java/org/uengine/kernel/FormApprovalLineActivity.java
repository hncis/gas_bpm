package org.uengine.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.SimulatorProcessInstance;
import org.uengine.util.ActivityForLoop;
import org.uengine.util.UEngineUtil;

/**
 * 
 * @author <a href="mailto:bigmahler@users.sourceforge.net">Jong-Uk Jeong</a>
 * @version $Id: FormApprovalLineActivity.java,v 1.12 2010/07/26 07:08:08 erim79 Exp $
 * @version $Revision: 1.12 $
 */
public class FormApprovalLineActivity extends ScopeActivity {
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	public static final int LOOPINGOPTION_AUTO = 0;
	public static final int LOOPINGOPTION_REPEATONREJECT = 1;
	public static final int LOOPINGOPTION_FINISHONREJECT = 2;

	public final static String KEY_APPR_LINE_STATUS = "KEY_APPR_LINE_STATUS";

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd = type.getFieldDescriptor("ReferencerRole");

		fd = type.getFieldDescriptor("ReceiverRole");
	
		fd = type.getFieldDescriptor("LoopingOption");
		fd.setInputter(new RadioInput(new String[]{"Auto","Loop","Finish"}, new Integer[]{FormApprovalLineActivity.LOOPINGOPTION_AUTO, FormApprovalLineActivity.LOOPINGOPTION_REPEATONREJECT, FormApprovalLineActivity.LOOPINGOPTION_FINISHONREJECT}));
		//fd.setDisplayName("");
		
		type.setName((String)ProcessDesigner.getInstance().getActivityTypeNameMap().get(FormApprovalLineActivity.class));
	}
	
	public FormApprovalLineActivity(){
		setName("");
		setLoopingOption(LOOPINGOPTION_FINISHONREJECT);
		FormApprovalActivity draftActivity = new FormApprovalActivity();
		draftActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.FormApprovalActivity.draft.message", "Draft"));
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
		if(getStatusOutPV()!=null){
			getStatusOutPV().set(instance, "", status);
		}
	}
	
	public String getApprovalLineStatus(ProcessInstance instance) throws Exception{
		return (String)instance.getProperty(getTracingTag(), KEY_APPR_LINE_STATUS);
	}
	
	public void resetApprovalLine(){
		
		List appActList = getChildActivities();
		String draftActivityTT = getDraftActivity().getTracingTag();

		for(int i=0 ; i<appActList.size();i++){
			Activity act  = (Activity)appActList.get(i);
			if(!act.getTracingTag().equals(draftActivityTT)){
				removeChildActivity(act);
			}
		}
	}
	
	public void addApprovalActivity(ProcessInstance instance, String approveType, String activityName, String approverEndpoint) throws Exception{
		
		FormApprovalActivity formApprovalActivity = (FormApprovalActivity) getDraftActivity().clone();
		ComplexActivity parent = (FormApprovalLineActivity) formApprovalActivity.getParentActivity();
		RoleMapping approver = RoleMapping.create();
		approver.setEndpoint(approverEndpoint);
		approver.fill(instance);
		
		formApprovalActivity.setName(activityName);
		formApprovalActivity.setTracingTag(null);
		formApprovalActivity.setRole(null);
		formApprovalActivity.setViewMode(true);
		formApprovalActivity.setArbitraryApprovable(false);
		formApprovalActivity.setNotificationWorkitem(false);
		
		if(FormApprovalActivity.APPROVALTYPE_ARBITRARY_APPROVAL.equals(approveType)){			
			formApprovalActivity.setArbitraryApprovable(true);
		}else if(FormApprovalActivity.APPROVALTYPE_POST_APPROVAL.equals(approveType)){
			formApprovalActivity.setNotificationWorkitem(true);
		}else if(FormApprovalActivity.APPROVALTYPE_CONSENT.equals(approveType)){
			AllActivity allAct = new AllActivity();
			parent.addChildActivity(allAct);
			parent = allAct;
		}
		
		if(formApprovalActivity.getParameters()!=null){
			for(int j=0; j<formApprovalActivity.getParameters().length; j++){
				formApprovalActivity.getParameters()[j].setDirection(ParameterContext.DIRECTION_IN);
			}					
		}
		
		formApprovalActivity.setApprover(instance, approver);
		
		addChildActivity(formApprovalActivity);
	}
	
	FormApprovalActivity draftActivity;
	public FormApprovalActivity getDraftActivity(){
		if(draftActivity!=null)
			return draftActivity;
		
		ActivityForLoop findingLoop = new ActivityForLoop(){
			public void logic(Activity activity){
				if(activity instanceof FormApprovalActivity){
					stop(activity);
				}
			}
		};
		
		findingLoop.run(this);		
		this.draftActivity = (FormApprovalActivity)findingLoop.getReturnValue(); 
		
		return draftActivity;
	}
	
	//referencer (11.23)
	protected void afterExecute(ProcessInstance instance) throws Exception {
		super.afterExecute(instance);
	
	}
	
	//receiver (11.23)
	protected void afterComplete(ProcessInstance instance) throws Exception {
		
		boolean isSimulate =  instance instanceof SimulatorProcessInstance;
		
		super.afterComplete(instance);
		if(getReceiverRole()!=null &&!isSimulate){
			RoleMapping receivers = getReceiverRole().getMapping(instance);
			if(receivers!=null){
				receivers.setDispatchingOption(Role.DISPATCHINGOPTION_RECEIVE);
				instance.putRoleMapping("receiver_" + getTracingTag(), receivers);
			}
		}
	}
	
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
					}/* else if (childActs.get(i) instanceof ComplexActivity) {
						ComplexActivity complexActivity = (ComplexActivity) childActs.get(i);
						complexActivity.onEvent(command, instance, payload);
					}*/
				}
//				RoleMapping referencers = getReferencerRole().getMapping(instance);
//				if (referencers != null) {
//					referencers.setDispatchingOption(Role.DISPATCHINGOPTION_REFERENCE);
//					instance.putRoleMapping("referencer_" + getTracingTag(),referencers);
//				}
			}
		}
	
		super.onEvent(command, instance, payload);
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

		
	int loopingOption;
		public int getLoopingOption() {
			return loopingOption;
		}
		public void setLoopingOption(int loopingOption) {
			this.loopingOption = loopingOption;
		}
	
	ProcessVariable statusOutPV;

		public ProcessVariable getStatusOutPV() {
			return statusOutPV;
		}
	
		public void setStatusOutPV(ProcessVariable statusOut) {
			this.statusOutPV = statusOut;
		}
}
