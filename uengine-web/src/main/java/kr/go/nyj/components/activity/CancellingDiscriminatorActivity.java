package kr.go.nyj.components.activity; 

import java.util.ArrayList;
import java.util.List;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.EndActivity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.util.ActivityForLoop;
import org.uengine.webservices.worklist.WorkList;
import org.uengine.webservices.worklist.WorkListServiceLocator;

public class CancellingDiscriminatorActivity extends DefaultActivity {

	public CancellingDiscriminatorActivity() {
		setName("Cancelling Discriminator");
	}
	
	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		Activity parent = this.getParentActivity();
		
		while (!(parent instanceof AllActivity)) {
			parent = parent.getParentActivity();
		}
		
		final AllActivity finalThis = (AllActivity) parent;
		final ProcessInstance finalInst = instance;
		final List<Activity> runningActivities = new ArrayList<Activity>();

		ActivityForLoop forLoop = new ActivityForLoop() {
			public void logic(Activity act) {
				try {
					if ((act.getStatus(finalInst).equals(Activity.STATUS_RUNNING)
							|| act.getStatus(finalInst).equals(Activity.STATUS_READY)
							|| act.getStatus(finalInst).equals(Activity.STATUS_TIMEOUT))) {
						
						runningActivities.add(act);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		forLoop.run(finalThis);

		for (int i = 0; i < runningActivities.size(); i++) {
			if (!runningActivities.get(i).getTracingTag().equals(getTracingTag())) {
				if (runningActivities.get(i) instanceof SubProcessActivity) {
					List<ProcessInstance> subInsts = ((SubProcessActivity)runningActivities.get(i)).getSubProcesses(instance);
					for (int j = 0; j < subInsts.size(); j++) {
						ProcessInstance subInst = subInsts.get(j);
						EndActivity endActivity = new EndActivity();
						endActivity.setStatus(EndActivity.STATUS_CANCELLED);
						endActivity.executeActivity(subInst);
					}
				}
				runningActivities.get(i).setStatus(instance, Activity.STATUS_SKIPPED);
				
				if (runningActivities.get(i) instanceof HumanActivity) {
					HumanActivity humAct = (HumanActivity) runningActivities.get(i);
					WorkList worklist = (new WorkListServiceLocator()).getWorkList();
					KeyedParameter[] parameters = new KeyedParameter[]{new KeyedParameter("status",Activity.STATUS_CANCELLED)};
					String[] taskIds = humAct.getTaskIds(instance);
					
					if (taskIds != null) {
						for (int j = 0; j < taskIds.length; j++) {
							String taskId = taskIds[j];
							worklist.cancelWorkItem(taskId, parameters, instance.getProcessTransactionContext());
						}
					}
				}
			}
		}
		fireComplete(instance);
		
		if (!parent.getStatus(instance).equals(Activity.STATUS_COMPLETED)) {
			parent.fireComplete(instance);
		}
	}
}
