package com.kriss.activity;

import java.util.Iterator;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.util.ActivityForLoop;

public class KrissEventHandlerActivity extends AllActivity {

	public KrissEventHandlerActivity() {
		KrissEventActionHumanActivity eventActionHumanActivity = new KrissEventActionHumanActivity();
		addChildActivity(eventActionHumanActivity);
		KrissEventScopeActivity eventScopeActivity = new KrissEventScopeActivity();
		this.eventScopeActivity = eventScopeActivity;
		//복제할 eventsope activity 는 실행될때 없애고 hidden 처리 한다.
		addChildActivity(eventScopeActivity);
	}
	
	private KrissEventScopeActivity eventScopeActivity;
		public KrissEventScopeActivity getEventScopeActivity() {
			return eventScopeActivity;
		}
		public void setEventScopeActivity(KrissEventScopeActivity eventScopeActivity) {
			this.eventScopeActivity = eventScopeActivity;
		}
	

	ProcessVariable allProcVar;
		public ProcessVariable getAllProcVar() {
			return allProcVar;
		}
		public void setAllProcVar(ProcessVariable allProcVar) {
			this.allProcVar = allProcVar;
		}
	
	ProcessVariable devidingProcVar;
		public ProcessVariable getDevidingProcVar() {
			return devidingProcVar;
		}
		public void setDevidingProcVar(ProcessVariable devidingProcVar) {
			this.devidingProcVar = devidingProcVar;
		}
	
	@Override
	public void executeActivity(ProcessInstance instance)
			throws Exception {
		
		//복제용 eventscopactivity 를 찾아서 떼어낸다.
		ActivityForLoop forLoop = new ActivityForLoop() {
			
			@Override
			public void logic(Activity activity) {
				if (activity instanceof KrissEventScopeActivity && activity.getTracingTag().equals(eventScopeActivity.getTracingTag()))
					stop(activity);
			}
		};
		
		forLoop.run(this);
		if (forLoop.getReturnValue() != null) {
			Activity eventScopeActivity = (Activity) forLoop.getReturnValue();
			ProcessDefinition clonedPd = (ProcessDefinition) getProcessDefinition().clone();
			KrissEventHandlerActivity thisClonedActivity = (KrissEventHandlerActivity)clonedPd.getActivity(getTracingTag());
			thisClonedActivity.removeChildActivity(clonedPd.getActivity(eventScopeActivity.getTracingTag()));
			instance.getProcessTransactionContext().getProcessManager().changeProcessDefinition(instance.getInstanceId(), clonedPd);
			for(Iterator<Activity> iter = thisClonedActivity.getChildActivities().iterator(); iter.hasNext(); ){
				Activity child = iter.next();
				if(!(Activity.STATUS_RUNNING.equals(child.getStatus(instance)) || Activity.STATUS_TIMEOUT.equals(child.getStatus(instance))))
					queueActivity(child, instance);//child.executeActivity(instance);
			}
		}
	}

	

}
