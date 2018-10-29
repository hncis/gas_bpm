package com.kriss.activity;

import java.io.Serializable;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.ResultPayload;
import org.uengine.util.UEngineUtil;

public class KrissEventActionHumanActivity extends KrissHumanActivity {
	
	public KrissEventHandlerActivity getEventHandlerActivity(){
		ComplexActivity parent = (ComplexActivity) getParentActivity();
		while (!(parent instanceof KrissEventHandlerActivity)){
			parent = (ComplexActivity) parent.getParentActivity();
		}
		return (KrissEventHandlerActivity) parent;
	}

	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		super.executeActivity(instance);
		ProcessVariable allProcVar = getEventHandlerActivity().getAllProcVar();
		if (allProcVar == null || allProcVar.getMultiple(instance, "").size() == 0){
			fireReceived(instance, new ResultPayload());
		}
	}

	@Override
	public void saveActivity(ProcessInstance instance) throws Exception {
		ProcessVariable allProcVar = getEventHandlerActivity().getAllProcVar();
		ProcessVariable devidingProcVar = getEventHandlerActivity().getDevidingProcVar();

		ProcessVariableValue allProcVarVal = allProcVar.getMultiple(instance, "");
		ProcessVariableValue devidingProcVarVal = devidingProcVar.getMultiple(instance, "");
		ProcessDefinition clonedPd = null;
		KrissEventScopeActivity clonedEventScopeActivity = null;
		if (allProcVarVal != null && allProcVarVal.size() > 0 && devidingProcVarVal != null && devidingProcVarVal.size() > 0){
			devidingProcVarVal.beforeFirst();
			do{
				Serializable devidingVal = devidingProcVarVal.getValue();
				allProcVarVal.beforeFirst();
				do{
					if (allProcVarVal.getValue().equals(devidingVal)){
						if ( clonedEventScopeActivity == null ){
							clonedPd = (ProcessDefinition) getProcessDefinition().clone();
							KrissEventHandlerActivity parent =  (KrissEventHandlerActivity) clonedPd.getActivity(getEventHandlerActivity().getTracingTag());
							//복제할 eventscope activity
							
							clonedEventScopeActivity = (KrissEventScopeActivity) parent.getEventScopeActivity().clone();
							parent.attatchClonedChildActivity(clonedEventScopeActivity);
							instance.getProcessTransactionContext().getProcessManager().changeProcessDefinition(instance.getInstanceId(), clonedPd);
	//						clonedEventScopeActivity.setStatus(instance, Activity.STATUS_RUNNING);
	//						clonedEventScopeActivity.getChildActivities().get(0).executeActivity(instance);
							clonedEventScopeActivity.executeActivity(instance);
						}
						String devidedProcVar = (String) instance.getProperty(clonedEventScopeActivity.getTracingTag(), "devidedProcVar");
						if (UEngineUtil.isNotEmpty(devidedProcVar)) {
							instance.setProperty(clonedEventScopeActivity.getTracingTag(), "devidedProcVar", devidedProcVar+","+devidingVal);
						} else {
							instance.setProperty(clonedEventScopeActivity.getTracingTag(), "devidedProcVar", devidingVal);
						}
						allProcVarVal.remove();
						break;
					}
				}while(allProcVarVal.next());
				allProcVarVal.beforeFirst();
			} while (devidingProcVarVal.next());
			devidingProcVarVal.beforeFirst();
			allProcVar.set(instance, "", allProcVarVal);
		}
		
		
		if (allProcVar == null || allProcVar.getMultiple(instance, "").size() == 0){
			instance.getProcessTransactionContext().getProcessManager().completeWorkitem(instance.getInstanceId(), getTracingTag(), getTaskIds(instance)[0], new ResultPayload());
		}
	}

}
