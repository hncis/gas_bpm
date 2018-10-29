package kr.go.nyj.components.activity;

import org.uengine.kernel.Activity;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.UEngineUtil;

public class EventHumanActivity extends HumanActivity {
	
	SubProcessActivity eventSubProcessActivity;
	
	public SubProcessActivity getEventSubProcessActivity() {
		return eventSubProcessActivity;
	}

	public void setEventSubProcessActivity(SubProcessActivity eventSubProcessActivity) {
		this.eventSubProcessActivity = eventSubProcessActivity;
	}
	
	@Override
	protected void afterComplete(ProcessInstance instance) throws Exception {
		super.afterComplete(instance);
		
		Activity parent = getParentActivity();
		
		while(!(parent instanceof SubProcessAttatchEventHandler)) {
			parent = parent.getParentActivity();
		}
		
		SubProcessAttatchEventHandler eventHandler = (SubProcessAttatchEventHandler) parent;
		
		if (eventHandler.getEventHumanActivity() == null) {
			ProcessDefinition clonedPd = (ProcessDefinition) instance.getProcessDefinition().clone();
			//자신을 복제해서 AllActivity 에 붙인다.
			EventHumanActivity clonedThis = (EventHumanActivity) clonedPd.getActivity(getTracingTag()).clone();
			clonedThis.setTracingTag(null);
			SubProcessAttatchEventHandler clonedEventHandler = (SubProcessAttatchEventHandler) clonedPd.getActivity(eventHandler.getTracingTag());
			String dynamicTT = (instance.getProperty("", "dynamicTT")==null)?"10000":(String)instance.getProperty("", "dynamicTT");
			String attatchTT = Integer.toString(Integer.parseInt(dynamicTT)+1);
			clonedThis.setTracingTag(attatchTT);
			instance.setProperty("", "dynamicTT", attatchTT);
			clonedEventHandler.getEventAllActivity().addChildActivity(clonedThis);
			clonedEventHandler.setEventHumanActivity(clonedThis);
			instance.getProcessTransactionContext().getProcessManager().changeProcessDefinition(instance.getInstanceId(), clonedPd);
			clonedThis.executeActivity(instance);
		}
	}

	public void attatchSubProcess(ProcessInstance instance) throws Exception {
		String defVerId = eventSubProcessActivity.getDefinitionVersionId(instance);
		//defVerId = defVerId.split("@")[1];
		ProcessManagerRemote pmr = instance.getProcessTransactionContext().getProcessManager();
		String newInstanceId = pmr.initializeProcess(defVerId);
		ProcessInstance newInstance = pmr.getProcessInstance(newInstanceId);
		
		//Variable Setting
		ParameterContext[] varParams = eventSubProcessActivity.getVariableBindings();
		
		for (int i = 0; i < varParams.length; i++) {
			String argument = varParams[i].getArgument().getText();
			
			if (UEngineUtil.isNotEmpty(argument)) {
				ProcessVariable processVariable = varParams[i].getVariable();
				ProcessVariableValue pvv = instance.getMultiple("", processVariable.getName());
				ProcessVariableValue newPvv = new ProcessVariableValue();
				pvv.beforeFirst();
				newPvv.setName(argument);
				do {
					if (newPvv.size() > 0) {
						newPvv.moveToAdd();
					}
					newPvv.setValue(pvv.getValue());
				} while (pvv.next());
				
				newInstance.set("", newPvv);
			}
		}
		
		//Role Setting
		RoleParameterContext[] roleParams = eventSubProcessActivity.getRoleBindings();
		
		for (int i = 0; i < roleParams.length; i++) {
			String argument = roleParams[i].getArgument();
			
			if (UEngineUtil.isNotEmpty(argument)) {
				Role originRole = roleParams[i].getRole();
				RoleMapping rm = originRole.getMapping(instance);
				RoleMapping newRm = RoleMapping.create();
				rm.beforeFirst();
				newRm.setName(argument);
				do {
					if (newRm.getEndpoint() != null) {
						newRm.moveToAdd();
					}
					newRm.setEndpoint(rm.getEndpoint());
					newRm.fill(instance);
				} while (rm.next());
				
				newInstance.putRoleMapping(argument, newRm);
			}
		}
		pmr.executeProcess(newInstanceId);
		
		eventSubProcessActivity.attachSubProcess(instance, newInstance.getInstanceId(), newInstance.getInstanceId());
		((EJBProcessInstance)newInstance).getProcessInstanceDAO().set("issubprocess", 1);
		((EJBProcessInstance)newInstance).getProcessInstanceDAO().set("rootinstid", instance.getRootProcessInstanceId());
		((EJBProcessInstance)newInstance).getProcessInstanceDAO().set("maininstid", instance.getInstanceId());
		((EJBProcessInstance)newInstance).getProcessInstanceDAO().setMainActTrcTag(eventSubProcessActivity.getTracingTag());
		
		if (!eventSubProcessActivity.getStatus(instance).equals(Activity.STATUS_RUNNING) || !eventSubProcessActivity.getStatus(newInstance).equals(Activity.STATUS_TIMEOUT)) {
			eventSubProcessActivity.setStatus(instance, Activity.STATUS_RUNNING);
		}
	}
}
