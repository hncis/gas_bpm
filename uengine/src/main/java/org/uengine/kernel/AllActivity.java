package org.uengine.kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.metaworks.Type;
import org.uengine.contexts.TextContext;
import org.uengine.processdesigner.ProcessDesigner;


/**
 * @author Jinyoung Jang
 */

public class AllActivity extends ComplexActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
		
	public static void metaworksCallback_changeMetadata(Type type){
		type.setName((String)ProcessDesigner.getInstance().getActivityTypeNameMap().get(AllActivity.class));
	}
	
	public AllActivity(){
		super();
		setName("All121212");
	}
	
	protected void onEvent(String command, ProcessInstance instance, Object payload) throws Exception{
				
		//dispatching leaf childs
		if(command.equals(CHILD_COMPENSATED)){
			List<Activity> childs = getChildActivities();
			boolean stillRunning = false;
			for(int i=0; i<childs.size(); i++){
				Activity theChild = (Activity)childs.get(i);
				
				String statusOfTheChild = theChild.getStatus(instance); 
				
				if(statusOfTheChild.equals(Activity.STATUS_RUNNING) || statusOfTheChild.equals(Activity.STATUS_SUSPENDED)){
					stillRunning = true;
					break;
				}
			}
			
			if(!stillRunning){
				reset(instance);
				fireCompensate(instance);
			}
		}else				
		if(command.equals(CHILD_SKIPPED)){
			List<Activity> childs = getChildActivities();
			boolean stillRunning = false;
			for(int i=0; i<childs.size(); i++){
				Activity theChild = (Activity)childs.get(i);
				
				String statusOfTheChild = theChild.getStatus(instance); 
				
				if(statusOfTheChild.equals(Activity.STATUS_RUNNING) || statusOfTheChild.equals(Activity.STATUS_SUSPENDED)){
					stillRunning = true;
					break;
				}
			}
			
			if(!stillRunning){
				fireSkipped(instance);
			}
		}else
		if(command.equals(CHILD_DONE)){
			boolean stillRunning = false;
			List<Activity> childActivities = getChildActivities();	 
			for(Iterator<Activity> iter = childActivities.iterator(); iter.hasNext(); ){
				Activity child = iter.next();
				if(!Activity.STATUS_COMPLETED.equals(child.getStatus(instance)))
					stillRunning = true;
			}
			
			if(!stillRunning){
				fireComplete(instance);
			}
		}else		
		if(command.equals(CHILD_RESUMED)){
			ComplexActivity parentActivity = (ComplexActivity)this;
			do{
				parentActivity.setStatus(instance, Activity.STATUS_RUNNING);
				parentActivity = (ComplexActivity)parentActivity.getParentActivity();
			}while(parentActivity!=null);
			
			Activity childActivity = (Activity)payload;
			int activityOrder = getChildActivities().indexOf(childActivity);
			if(activityOrder==-1) throw new UEngineException("Resuming activity is not a child of the parent activity. Some inconsistence status.");
			
			queueActivity(childActivity, instance);
		}else		
			super.onEvent(command, instance, payload);
	}

	public void executeActivity(ProcessInstance instance) throws Exception{		
		for(Iterator<Activity> iter = getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			if(!(Activity.STATUS_RUNNING.equals(child.getStatus(instance)) || Activity.STATUS_TIMEOUT.equals(child.getStatus(instance))))
				queueActivity(child, instance);//child.executeActivity(instance);
		}
	}
	
	protected void onChanged(ProcessInstance instance) throws Exception {
		super.onChanged(instance);
		
		if(!instance.isRunning(getTracingTag())) return;
		
		for(Iterator<Activity> iter = getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			
			if(child.getStatus(instance).equals(Activity.STATUS_READY)){
				queueActivity(child, instance);//child.executeActivity(instance);
			}
		}
	}
	
	public Vector<Activity> getPreviousActivitiesOf(Activity child){
		return getPreviousActivities();	
	}
	
	public Vector getLastActivities(){
		List<Activity> childs = getChildActivities();
		
		Vector<Activity> lastActs = new Vector<Activity>();
		for(int i=0; i<childs.size(); i++){
			Activity act = (Activity)childs.get(i);
			
			if(act instanceof ComplexActivity){
				List<Activity> lastActsOfLastAct = ((ComplexActivity)act).getLastActivities();			
				if(lastActsOfLastAct!=null)
					lastActs.addAll(lastActsOfLastAct); 
			}
			else{
				lastActs.add(act);
			}
		}
		
		return lastActs;
	}

	protected void gatherPropagatedActivitiesOf(ProcessInstance instance, Activity child, List list) throws Exception{
		gatherPropagatedActivities(instance, list);
	}

	public void compensateOneStep(ProcessInstance instance) throws Exception{
		//Lets each child activity reset instance
		boolean allChildReset = true;
		
		for(Iterator<Activity> iter = getChildActivities().iterator(); iter.hasNext(); ){
			Activity child = iter.next();
			
			String status = child.getStatus(instance);
			
			if(Activity.isCompensatable(status) || status.equals(Activity.STATUS_COMPLETED))
			child.compensateOneStep(instance);
			
			String statusOfChild = child.getStatus(instance);
			if(!(statusOfChild.equals(Activity.STATUS_READY))){
				allChildReset = false;
			}
		}
		
		if(allChildReset){
			super.reset(instance);
			fireCompensate(instance);
		}else{
			setStatus(instance, Activity.STATUS_RUNNING);
		}
	}
}
//
