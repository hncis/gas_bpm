package org.uengine.kernel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.uengine.contexts.TextContext;
import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processmanager.ProcessTransactionContext;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.exporter.ProcessDefinitionAdapter;
import org.uengine.util.UEngineUtil;


/**
 * This class plays the most important role in the uEngine
 * ,which is composed of key attributes and methods of general behaviors, properties and callback messages of Activity Types.
 * To understand uEngine's process model, you may need to understand 'Composite Pattern' (which is one of most well-known design pattern also used in java.io.File)
 * first. That is, this Activity class and ComplexActivity has a self-contained relationship for easy-activity-type extension and 
 * easy-to-hold the block-based (structured) process model.
 * 
 * @author Jinyoung Jang
 * @see org.uengine.kernel.ComplexActivity
 */

public abstract class Activity implements Validatable, java.io.Serializable, Cloneable {
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	final public static String ACTIVITY_DONE=		"activity done";
	final public static String ACTIVITY_SKIPPED=	"activity skip";
	final public static String ACTIVITY_COMPENSATED="activity compensate";
	final public static String ACTIVITY_RESUMED=	"activity resume";
	final public static String ACTIVITY_RESET=		"activity reset";
	final public static String ACTIVITY_CHANGED=	"activity changed";
	final public static String ACTIVITY_STOPPED=	"activity stop";
	final public static String ACTIVITY_SUSPENDED=	"activity suspended";
	final public static String ACTIVITY_RETRYING=	"activity retrying";
	
	final public static String CHILD_DONE=			"child done";
	final public static String CHILD_COMPENSATED=	"child compensate";
	final public static String PREPIX_MESSAGE=		"message from external";
	final public static String ACTIVITY_FAULT=		"activity fault";
	final public static String CHILD_FAULT=			"child fault";
	final public static String CHILD_SKIPPED=		"child skip";
	final public static String CHILD_RESUMED=		"child resume";
	final public static String CHILD_STOPPED=		"child stop";
	
	final public static String STATUS_READY=		"Ready";
	final public static String STATUS_COMPLETED=	"Completed";
	final public static String STATUS_FAULT=		"Failed";
	final public static String STATUS_RETRYING=		"Retrying";
	final public static String STATUS_RUNNING=		"Running";
	final public static String STATUS_SUSPENDED=	"Suspended";
	final public static String STATUS_SKIPPED=		"Skipped";
	final public static String STATUS_STOPPED=		"Stopped";
	final public static String STATUS_TIMEOUT=		"Timeout";
	final public static String STATUS_CANCELLED=	"Cancelled";
	final public static String STATUS_ALLCOMMIT=	"AllCommit";
	final public static String STATUS_CONFIRMED=	"Confirmed";
	
	final public static String VAR_START_TIME=		"_start_time";
	final public static String VAR_END_TIME=		"_end_time";
	final public static String VAR_BIZ_STATUS=		"_var_biz_status";
	
	final public static String QUEUINGMECH_JMS=		"JMS";
	final public static String QUEUINGMECH_SYNC=	"synchronous";

	final public static String PVKEY_STATUS = "_status";
	final public static String PVKEY_RETRY_CNT = "_retryCnt";

	/**
	 * points parent activity (should be kind of ComplexActivity of this activity)
	 */
		
	Activity parentActivity = null;
		public Activity getParentActivity() {
			return parentActivity;
		}
		protected void setParentActivity(Activity parentAct) {
			this.parentActivity = parentAct;
		}
		public void clearParentActivity() {
			this.parentActivity = null;
		}
		
	

	TextContext name;
		public TextContext getName() {
			return name;
		}
		public void setName(TextContext name) {
			TextContext oldName = this.name;
			this.name = name;
			
			firePropertyChangeEvent(new PropertyChangeEvent(this, "name", oldName, name));
		}
		public void setName(String name) {
			if(getName()==null){
				TextContext textCtx = TextContext.createInstance(getProcessDefinition());
				setName(textCtx);		
			}
			
			getName().setText(name);
		}
		
	TextContext description = TextContext.createInstance();
		public TextContext getDescription() {
			return description;
		}
		public void setDescription(TextContext string) {
			description = string;
		}

		/**
		 * tracingTag is a identifier for a certain activity within a process definition.
		 */
	String tracingTag;
		public String getTracingTag() {
			return tracingTag;
		}
		public void setTracingTag(String tag) {
			tracingTag = tag;
		}
		
/*	String iconURL;
		public String getIconURL() {
			return iconURL;
		}
		public void setIconURL(String iconURL) {
			this.iconURL = iconURL;
		}
*/
	/**
	 * for ABC (Activity-Based Costing)
	 */
	int cost;
		public int getCost() {
			return cost;
		}
		public void setCost(int i) {
			cost = i;
		}

	Hashtable<String, Object> extendedAttributes;
		public Hashtable<String, Object> getExtendedAttributes(){
			return extendedAttributes;
		}
		public void setExtendedAttributes(Hashtable<String, Object> value){
			extendedAttributes = value;
		}
		public void setExtendedAttribute(String k, Object value){
			if(getExtendedAttributes()==null){
				setExtendedAttributes(new Hashtable<String, Object>());
			}			
			
			if(value == null)
				getExtendedAttributes().remove(k);
			else
			getExtendedAttributes().put(k, value);
			
			firePropertyChangeEvent(new PropertyChangeEvent(this, "extendedAttribute", value, value));
		}

	/**
	 * retry limits for activity execution
	 */
	int retryLimit;
		public int getRetryLimit() {
			return retryLimit;
		}
		public void setRetryLimit(int i) {
			retryLimit = i;
		}

	int retryDelay;
		public int getRetryDelay() {
			return retryDelay;
		}
		public void setRetryDelay(int l) {
			retryDelay = l;
		}
	
	boolean isHidden = false;
		public boolean isHidden() {
			return isHidden;
		}
		public void setHidden(boolean b) {
			isHidden = b;
		}

	transient ActivityDesigner designer;
		public ActivityDesigner getDesigner(){
			return designer; 
		}

	transient List<PropertyChangeListener> propertyChangeListeners = new ArrayList<PropertyChangeListener>();
		public void addProperyChangeListener(PropertyChangeListener pcl){
			if(propertyChangeListeners==null)
				propertyChangeListeners = new ArrayList<PropertyChangeListener>();
			
			if(!propertyChangeListeners.contains(pcl))
				propertyChangeListeners.add(pcl); 
		}
		public void removeProperyChangeListener(PropertyChangeListener pcl){
			propertyChangeListeners.remove(pcl); 
		}
		public void clearProperyChangeListeners(){
			if(propertyChangeListeners==null)
				propertyChangeListeners = new ArrayList<PropertyChangeListener>();				

			propertyChangeListeners.clear(); 
		}
		public void firePropertyChangeEvent(PropertyChangeEvent pce){
			if(propertyChangeListeners==null)
				propertyChangeListeners = new ArrayList<PropertyChangeListener>();				

			for(Iterator<PropertyChangeListener> iter = propertyChangeListeners.iterator(); iter.hasNext();){
				PropertyChangeListener pcl = iter.next();				
				pcl.propertyChange(pce);				
			}
		}

	boolean isDynamicChangeAllowed = true;
		public boolean isDynamicChangeAllowed() {
			return isDynamicChangeAllowed;
		}
		public void setDynamicChangeAllowed(boolean b) {
			isDynamicChangeAllowed = b;
		}
		
	boolean isQueuingEnabled = false;
		public boolean isQueuingEnabled() {
			return isQueuingEnabled;
		}
		public void setQueuingEnabled(boolean b) {
			isQueuingEnabled = b;
		}

	String activityIcon;
		public String getActivityIcon(){
			return activityIcon;
		}
		public void setActivityIcon(String activityIconURL){
			activityIcon = activityIconURL;
		}
		
	String statusCode;
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		
	protected boolean isFaultTolerant = false;
		public boolean isFaultTolerant() {
			return isFaultTolerant;
		}
		public void setFaultTolerant(boolean isErrorTolerant) {
			this.isFaultTolerant = isErrorTolerant;
		}
		
	public Calendar getStartedTime(ProcessInstance instance){
		try{
			Calendar theTime = (Calendar)instance.getProperty(getTracingTag(), VAR_START_TIME);

			return theTime;
		}catch(Exception e){
			return null;
		}
	}
	public void setStartedTime(ProcessInstance instance, Calendar theTime) throws Exception{
		instance.setProperty(getTracingTag(), VAR_START_TIME, theTime);
	}
	
	public String getBusinessStatus(ProcessInstance instance) throws Exception{
		try{
			//if(true) return "test";
			String theValue = (String)instance.getProperty(getTracingTag(), VAR_BIZ_STATUS);

			return theValue;
		}catch(Exception e){
			return null;
		}
	}
	public void setBusinessStatus(ProcessInstance instance, String value) throws Exception{
		instance.setProperty(getTracingTag(), VAR_BIZ_STATUS, value);
	}
	
	public Calendar getEndTime(ProcessInstance instance){
		try{
			Calendar theTime = (Calendar)instance.getProperty(getTracingTag(), VAR_END_TIME);
		
			return theTime;
		}catch(Exception e){
			return null;
		}
	}
		
	public void setEndTime(ProcessInstance instance, Calendar theTime) throws Exception{
		instance.setProperty(getTracingTag(), VAR_END_TIME, theTime);
	}
	
	/**
	 * there sould be an empty constructor for reflection
	 */
	public Activity(){
		setRetryLimit(0);
		setRetryDelay(60);
	}

	public Activity(String activityName){	// for manual-coding
		this();
		
		setName(activityName);
	}
	
	/**
	 * These methods would be called by the the moment of a process instance is intialized. 
	 * You may override this method for initialize some instance data when the process instance is initialized.
	 * The usual invocation sequence is createInstance --> beforeExecute -->? executeActivity -->? afterExecute --> fireComplete() --> onEvent (if done or failed) -->? afterComplete()��
	 * 
	 * @param instanceInCreating ProcessInstance passed from uEngine to initialize the instance. 
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance createInstance(ProcessInstance instanceInCreating) throws Exception{
		return instanceInCreating;
	}

	public ProcessInstance createInstance(String instanceId, Map options) throws Exception{
		return null;
	}
	
	public ProcessInstance createInstance() throws Exception{
		return createInstance((String)null, null);
	}
	
	/**
	 * returns the root activity for this (child) activity. Basically the return value should be an instance of 'ProcessDefinition' class.
	 * @return
	 */
	public Activity getRootActivity(){
		Activity temp=this;
		while(temp.getParentActivity()!=null) temp = temp.getParentActivity();
		
		return temp;
	}
	
	/**
	 * returns the process definition of this (child) activity.
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(){
		Activity root = getRootActivity();
		if(root instanceof ProcessDefinition)
			return (ProcessDefinition)root;
		else
			return null;
	}
	
	/**
	 * This callback method would be called just before the executeActivity() method.
	 * The usual invocation sequence is createInstance --> beforeExecute --> executeActivity --> afterExecute --> fireComplete() --> onEvent (if done or failed) -->? afterComplete()
	 * 
	 * @param instance
	 * @throws Exception
	 */
	protected void beforeExecute(ProcessInstance instance) throws Exception{
		if(instance.isRunning(getTracingTag()))
			instance.addDebugInfo(new UEngineException("Activity.java:: The process is trying to execute the activity [" + getName() + "] more than once."));
		
		setStartedTime(instance, GlobalContext.getNow(instance.getProcessTransactionContext()));

		ActivityFilter[] activityFilters = getProcessDefinition().getActivityFilters();
		if(activityFilters!=null)
		for(int i=0; i<activityFilters.length; i++)
			activityFilters[i].beforeExecute(this, instance);
	}
	
	/**
	 * This callback method would be called just after the fireCompleted() method.
	 * The usual invocation sequence is createInstance ?--> beforeExecute -->? executeActivity -->? afterExecute --> fireComplete() --> onEvent (if done or failed) -->? afterComplete()
	 * 
	 * @param instance
	 * @throws Exception
	 */
	protected void afterComplete(ProcessInstance instance) throws Exception{
		setEndTime(instance, GlobalContext.getNow(instance.getProcessTransactionContext()));
		
		ActivityFilter[] activityFilters = getProcessDefinition().getActivityFilters();
		if(activityFilters!=null)
		for(int i=0; i<activityFilters.length; i++)
			activityFilters[i].afterComplete(this, instance);
		
		//add completed activity to transaction history.			
		instance.getProcessTransactionContext().addExecutedActivityInstanceContext(new ActivityInstanceContext(this, instance));
		//
	}
	
	/**
	 * This callback method would be called just after the executeActivity() method.
	 * The usual invocation sequence is createInstance ?--> beforeExecute -->? executeActivity -->? afterExecute --> fireComplete() --> onEvent (if done or failed) -->? afterComplete()��
	 * 
	 * @param instance
	 * @throws Exception
	 */
	protected void afterExecute(ProcessInstance instance) throws Exception{
		//add executed(queued) activity to transaction history.
		instance.getProcessTransactionContext().addExecutedActivityInstanceContext(new ActivityInstanceContext(this, instance));
		//

		ActivityFilter[] activityFilters = getProcessDefinition().getActivityFilters();
		if(activityFilters!=null)
		for(int i=0; i<activityFilters.length; i++)
			activityFilters[i].afterExecute(this, instance);
		
		List<String> messageListeners = instance.getMessageListeners("event");
		for(int i=0; i<messageListeners.size(); i++){
			MessageListener messageListener = (MessageListener)getProcessDefinition().getActivity((String) messageListeners.get(i));
			if(messageListener instanceof ScopeActivity){
				ScopeActivity scopeActivity = (ScopeActivity)messageListener;
				
				if(scopeActivity.isAncestorOf(this)){
					EventMessagePayload emp = new EventMessagePayload();
					emp.setTriggerTracingTag(getTracingTag());
					
					scopeActivity.fireEventHandlers(instance, EventHandler.TRIGGERING_BY_AFTER_CHILD_COMPLETED, emp);
					
				}
			}
		}
		
/*		 try{
				
			if(GlobalContext.logLevelIsDebug){
				PrintStream ps;
				ps = new PrintStream(
					new FileOutputStream(FormActivity.FILE_SYSTEM_DIR + "/" + UEngineUtil.getCalendarDir() + "/" + instance.getInstanceId() + "." + getTracingTag() + ".log.xml")
				);
				ps.print(instance.getProcessTransactionContext().getDebugInfo());
				
			}
		 }catch(Exception e){
			(new UEngineException("failed to create execution log:"+ e.getMessage(), e)).printStackTrace(); 
		 }*/

	}
	
	/**
	 * Core logic for the activity should be implemented here.
	 * The usual invocation sequence is createInstance --> beforeExecute --> executeActivity --> afterExecute --> fireComplete() --> onEvent (if done or failed) --> afterComplete()
	 * @param instance
	 * @throws Exception
	 */
	abstract public void executeActivity(ProcessInstance instance) throws Exception;
	
	/**
	 * we recommend rather use method 'fireComplete()' or 'fireFault()' instead of this method.
	 * 
	 */
	protected void onEvent(String command, ProcessInstance instance, Object payload) throws Exception{
		
		if(instance.fireActivityEventInterceptor(this, command, instance, payload)) return;
		
		//review: performance: need to use 'Hashtable' to locate the command or directly invocation from fire... methods.
		if(command.equals(ACTIVITY_DONE)){
			Activity theActivityHasBeenDone = ((Activity)payload);
			
			if(Activity.STATUS_COMPLETED.equals(theActivityHasBeenDone.getStatus(instance))){
				if(!(theActivityHasBeenDone instanceof SubProcessActivity)){
					//SubProcessActivity spa = (SubProcessActivity)theActivityHasBeenDone;
					
					throw new UEngineException("Activity [" + theActivityHasBeenDone + "] tries to notify completion event twice. Check whether the activity calls 'fireComplete(instance)' more than once.");
				}
			}
			
			setStatus(instance, STATUS_COMPLETED);
			//review: Ensure subclasses are not overrided this method.
			afterComplete(instance);
			
			if(!isFaultTolerant() && getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_DONE, instance, this);
		}else		
		if(command.equals(ACTIVITY_FAULT)){
			FaultContext fc = (FaultContext)payload;
			instance.setFault(getTracingTag(), fc);
			
			if(getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_FAULT, instance, fc);
		}else
			if(command.equals(ACTIVITY_COMPENSATED)){
			if(getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_COMPENSATED, instance, payload);			
		}else
		if(command.equals(ACTIVITY_SKIPPED)){
			instance.setStatus(getTracingTag(), STATUS_SKIPPED);

			if(getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_SKIPPED, instance, payload);			
		}else
		if(command.equals(ACTIVITY_RESUMED)){
			if(getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_RESUMED, instance, payload);			
		}else
		if(command.equals(ACTIVITY_STOPPED)){
			instance.setStatus(getTracingTag(), Activity.STATUS_STOPPED);

			if(getParentActivity()!=null)
				getParentActivity().onEvent(CHILD_STOPPED, instance, payload);			
		}else
		if(command.equals(ACTIVITY_CHANGED)){
			onChanged(instance);
		}
		
		fireEventToActivityFilters(instance, command, payload);
	}
	

	
	/**
	 * This callback method whould be called when this activity is dynamically changed (added)
	 * 
	 * @param instance
	 * @throws Exception
	 */
	protected void onChanged(ProcessInstance instance) throws Exception{
	}

/*	public void fireEvent(String event, ProcessInstance instance, Object payload) throws Exception{
		onEvent(event, instance, payload);
	}*/

	/**
	 * fires a completion for this activity. You should invoke this method in the end of executeActivity() method if the activity is a synchronous job. 
	 * In the other hand, if you implement a asynchronous job of activity, you calls this fireComplete() after done (or after receiving the result).
	 */
	public void fireComplete(ProcessInstance instance) throws Exception{
		onEvent(ACTIVITY_DONE, instance, this);
	}
	
	public void fireSkipped(ProcessInstance instance) throws Exception{
		onEvent(ACTIVITY_SKIPPED, instance, this);
	}
	
	public void fireCompensate(ProcessInstance instance) throws Exception{
		onEvent(ACTIVITY_COMPENSATED, instance, this);
	}
	
	public void fireFault(ProcessInstance instance, FaultContext faultContext) throws Exception{
		onEvent(ACTIVITY_FAULT, instance, faultContext);
	}

	public void fireFault(ProcessInstance instance, Exception e) throws Exception{
		
		UEngineException fault;
		if(e instanceof UEngineException)			
			fault = (UEngineException)e;
		else if(e instanceof Throwable){
			fault = new UEngineException(((Throwable)e).getMessage(), (Throwable)e);		
		}else{
			fault = new UEngineException(""+ e);
		}
		
		FaultContext fc = new FaultContext();
		fc.setCauseActivity(this);
		fc.setFault(fault);

		fireFault(instance, fc);
		
	}

	public void fireResume(ProcessInstance instance) throws Exception{
		onEvent(ACTIVITY_RESUMED, instance, this);
	}
	
	public void fireChanged(ProcessInstance instance) throws Exception{
		onEvent(ACTIVITY_CHANGED, instance, this);
	}
	
	public void stop(ProcessInstance instance) throws Exception{
		stop(instance, Activity.STATUS_STOPPED);
	}
	
	public void stop(ProcessInstance instance,String status) throws Exception{
		onEvent(ACTIVITY_STOPPED, instance, this);
		instance.setStatus(getTracingTag(), status);
	}
	
	public void suspend(ProcessInstance instance) throws Exception{
		reset(instance);
		instance.setStatus(getTracingTag(), STATUS_SUSPENDED);		
	}
	
	public void resume(ProcessInstance instance) throws Exception{
//		instance.setStatus(getTracingTag(), STATUS_RUNNING);
		//need to check the tasks that have not completed yet or is still running..
//		executeActivity(instance); //may occur some flow control error\
		fireResume(instance);
	}
	
	public void reset(ProcessInstance instance) throws Exception{
		instance.setStatus(getTracingTag(), STATUS_READY);
	}
	
	public void skip(ProcessInstance instance) throws Exception{
		fireSkipped(instance);
	}
	
	public void compensate(ProcessInstance instance) throws Exception{
		reset(instance);
		//instance.setStatus(getTracingTag(), STATUS_READY);
		fireCompensate(instance);
	}

	public void compensateOneStep(ProcessInstance instance) throws Exception{
		//reset(instance);
		suspend(instance);
	}
	
	/**
	 */
	public void change(ProcessInstance instance) throws Exception{
		fireChanged(instance);
	}
	
	/**
	 * request a swing component for design support for this activity type.
	 * @return
	 */
	public ActivityDesigner createDesigner(){
	
		Class activityCls = getClass();
		//String clsName = UEngineUtil.getComponentClassName(activityCls, "designer");					
		designer = null;
		
		try{
			//Class designerCls = Class.forName(clsName);
			designer = (ActivityDesigner)UEngineUtil.getComponentByEscalation(activityCls, "designer");
			designer.setActivity(this);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return designer;
	}
	
	public Map<String, Serializable> getActivityDetails(ProcessInstance inst, String locale) throws Exception{
		Map<String, Serializable> details = new TreeMap<String, Serializable>();

		//details.put("name", ""+getName());
		//details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.activity.details.cost", "cost"), ""+getCost());

		if(inst!=null){
//			try{
//				details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.activity.details.elapsedtime", "elapsed time"), getElapsedTime(inst));
//			}catch(Exception e){
//				System.out.println("Activity.java : failed to get elapsedtime.");
//			}
			
			if(inst.getFault(getTracingTag())!=null){
				UEngineException fault = inst.getFault(getTracingTag());
				if(fault!=null)
					details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.activity.details.fault", locale, "fault"), fault);
				
	/*			ByteArrayOutputStream bos = new ByteArrayOutputStream();
				fault.printStackTrace(new PrintStream(bos));
				
				details.put("fault.details", bos.toString());*/
				
				if(fault.getDetails()!=null)
					details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.activity.details.faultdetails", locale, "fault.details"), fault.getDetails());
			}
			
			try{
				Calendar calStartedDate = getStartedTime(inst); 
				String strStartedDate = "-";
				if ( calStartedDate != null )
				{
					java.text.DateFormat df = new java.text.SimpleDateFormat(
							GlobalContext.getPropertyString("monitoring.activitydetails.dateformatter",  "yyyy-MM-dd"));
					strStartedDate = df.format( calStartedDate.getTime() );
				}
				details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.humanactivity.details.starteddate", locale, "started date"), strStartedDate);
			}catch(Exception e){}
			
			try{
				Calendar calEndDate = getEndTime(inst); 
				String strEndDate = "-";
				if ( calEndDate != null )
				{
					java.text.DateFormat df = new java.text.SimpleDateFormat(
							GlobalContext.getPropertyString("monitoring.activitydetails.dateformatter", "yyyy-MM-dd"));
					strEndDate = df.format( calEndDate.getTime() );
				}
				details.put(GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.humanactivity.details.enddate", locale, "end date"), strEndDate);
			}catch(Exception e){}
		}
		
		return details;
	}
	
	public ValidationContext validate(Map options){
		ValidationContext vc = new ValidationContext();
	
		try{					
			ComplexActivity.USE_JMS = false;
			ComplexActivity.USE_THREAD = false;
					
			ProcessInstance instance 
				= new DefaultProcessInstance(getProcessDefinition(), "test instance", null);

			Method[] methods = getClass().getMethods();			
			for(int i=0; i<methods.length; i++){						
				try{
					Method method = methods[i];
					if(
						method.getName().startsWith("get") 
						&& method.getParameterTypes().length==0 
						&& (method.getReturnType()==String.class || TextContext.class.isAssignableFrom(method.getReturnType()))
					){
						
						String setterName = "s" + method.getName().substring(1);
						
						boolean setterMethodExists = false;
						try{
							getClass().getMethod(setterName, new Class[]{String.class});
							setterMethodExists = true;
						}catch(Exception e){
						}
						
						if(!setterMethodExists)
							try{
								getClass().getMethod(setterName, new Class[]{TextContext.class});
								setterMethodExists = true;
							}catch(Exception e){
							}
						
						if(setterMethodExists)
								{
							//System.out.println("validating: in " + getName() + ", property = " + method.getName());
							Object value = method.invoke(this, new Object[]{});
							if(value!=null){
								if(value instanceof String)
									evaluateContent(instance, (String)value, vc);
								else
									evaluateContent(instance, ((TextContext)value).getText(), vc);
							}
						}
					}
				}catch(Exception e){						
				}
			}				
			
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		return vc;
	}
	
	public void usabilityCheck(Map<Object, Boolean> checkingValues){
       try{ 
			ComplexActivity.USE_JMS = false;
			ComplexActivity.USE_THREAD = false;
        	Method[] methods = getClass().getMethods(); 
        		for(int i=0; i<methods.length; i++){ 
        			try{
        				Method method = methods[i];
        				if(	method.getName().startsWith("get")){
							Object value = method.invoke(this, new Object[]{});
							if(checkingValues.containsKey(value)){
								checkingValues.put(value, true);
							}
        				}
					}catch(Exception e){ 
					}
	       		} 
	    }catch(Exception e){
	     	//e.printStackTrace();
	    }
	}
	
	protected String getActivityLabel(){
		return "[At " + getName() + " Activity ("+getTracingTag()+")]";
	}
	
	public static boolean isSkippable(String status){
		return !(status.equals(Activity.STATUS_SKIPPED) || status.equals(Activity.STATUS_READY) || status.equals(Activity.STATUS_CANCELLED) || status.equals(Activity.STATUS_COMPLETED));
	}
	public static boolean isStoppable(String status){
		return !(status.equals(Activity.STATUS_READY) || status.equals(Activity.STATUS_CANCELLED) || status.equals(Activity.STATUS_COMPLETED) || status.equals(Activity.STATUS_TIMEOUT)|| status.equals(Activity.STATUS_FAULT));
	}
	public static boolean isCompensatable(String status){
		return !(status.equals(Activity.STATUS_SKIPPED) || status.equals(Activity.STATUS_READY) || status.equals(Activity.STATUS_CANCELLED) || status.equals(Activity.STATUS_COMPLETED));
	}
	public static boolean isResumable(String status){
		return (status.equals(Activity.STATUS_SUSPENDED) || status.equals(Activity.STATUS_FAULT));
	}
	public static boolean isSuspendable(String status){
		return (status.equals(Activity.STATUS_RUNNING) || status.equals(Activity.STATUS_TIMEOUT));
	}
	public static boolean isCompletable(String status){
		return (status.equals(Activity.STATUS_RUNNING) || status.equals(Activity.STATUS_TIMEOUT));
	}
	//

	public String getStatus(ProcessInstance instance) throws Exception{
		String status = null;
		
		if( instance != null ){
			try{
				status = (String)instance.getProperty(getTracingTag(), Activity.PVKEY_STATUS);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(status==null)
			status = STATUS_READY;

		return status;
	}

	public void setStatus(ProcessInstance instance, String status) throws Exception{
		instance.setStatus(getTracingTag(), status); //ban to illegal invocation with invalid tracing tag
		
		firePropertyChangeEventToActivityFilters(instance, Activity.PVKEY_STATUS, status);
	}

	public int getRetryCount(ProcessInstance instance) throws Exception{
		Integer cnt = null;
		
		if( instance != null ){
			try{
				cnt = (Integer)instance.getProperty(getTracingTag(), Activity.PVKEY_RETRY_CNT);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(cnt==null)
			return 0;

		return cnt.intValue();
	}

	public void setRetryCount(ProcessInstance instance, int retryCnt) throws Exception{
		instance.setProperty(getTracingTag(), PVKEY_RETRY_CNT, new Integer(retryCnt)); //ban to illegal invocation with invalid tracing tag
	}

	public String getElapsedTime(ProcessInstance instance) throws Exception{
		
		long elapsedTime = getElapsedTimeAsLong(instance);
		
		if(elapsedTime==-1)
			return "-";
		
		String hour = "" + elapsedTime / 3600000L;
		String min = "" + (elapsedTime % 3600000L) / 60000L;
				
		return hour + " hr " + min + " min";
	}
	
	public long getElapsedTimeAsLong(ProcessInstance instance) throws Exception{
		Calendar startedTime = getStartedTime(instance);
		Calendar endTime = getEndTime(instance);
		
		if(startedTime==null)
			return -1;
		
		if(endTime==null)
			endTime = GlobalContext.getNow(instance.getProcessTransactionContext());
			
		return endTime.getTimeInMillis() - startedTime.getTimeInMillis();
	}
	
	public Activity findParentActivity(Class type){
		Activity tracing = this;
		do{
			if(type.isAssignableFrom(tracing.getClass())){
				return tracing;
			}
				
			tracing = tracing.getParentActivity();
		}while(tracing != null);
		
		return null;
	}

	public Object clone(){

		try{

			if(GlobalContext.wasIsJeus){
			
				String strInAct = GlobalContext.serialize(this, String.class);
				Activity clonedActivity = (Activity) GlobalContext.deserialize(strInAct);
				return clonedActivity;
			}else{
			
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				ObjectOutputStream ow = new ObjectOutputStream(bao);
				ow.writeObject(this);
				ByteArrayInputStream bio = new ByteArrayInputStream(bao.toByteArray());			
				ObjectInputStream oi = new ObjectInputStream(bio);
				
				return oi.readObject();
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public Vector<Activity> getPreviousActivities(){
		ComplexActivity parent = ((ComplexActivity)getParentActivity());
		if(parent==null)
			return null;
			
		return parent.getPreviousActivitiesOf(this);
	}

	public StringBuffer evaluateContent(ProcessInstance instance, String expression, ValidationContext validationContext){
		
		//if(instance==null) return new StringBuffer(expression);
		
//System.out.println("EMailActivity:: parseContent:1");
		StringBuffer generating = new StringBuffer("");

		if(expression==null) return generating;
//System.out.println("EMailActivity:: parseContent:2");
		
		int pos=0, endpos=0, oldpos=0;
		String key;
		String starter = "<%", ending="%>";	
		
		while((pos = expression.indexOf(starter, oldpos)) > -1){
			pos += starter.length();
			endpos = expression.indexOf(ending, pos);
//System.out.println("oldpos="+oldpos +"; pos = "+pos);
			if(endpos > pos){
				generating.append(expression.substring(oldpos, pos - starter.length()));
				key = expression.substring(pos, endpos);
				if(key.startsWith("=")) key = key.substring(1, key.length());
				if(key.startsWith("*")) key = key.substring(1, key.length());
				if(key.startsWith("+")) key = key.substring(1, key.length());
				
				key = key.trim();
				
	System.out.println("Activity:: evaluateContent: key="+key);
				Object val = Activity.getSpecialKeyValues(this, instance, key, validationContext);
//	System.out.println("EMailActivity:: parseContent: val:"+val);	
				if(val!=null)
					generating.append("" + val);
			}
			oldpos = endpos + ending.length();
		}
//System.out.println(generating.toString());			
		generating.append(expression.substring(oldpos));
		//end
		
		return generating;
	}

	public StringBuffer evaluateContent(ProcessInstance instance, String expression){
		return evaluateContent(instance, expression, null);
	}

	static HashMap<String, Object> getterMethodsHT = new HashMap<String, Object>();
	private static Method getGetterMethod(Class targetCls, String propertyName){
		String prefix = targetCls.getName().toLowerCase() + ".";
		propertyName = propertyName.toLowerCase(); 
		
		if(!getterMethodsHT.containsKey(targetCls.getName())){
			Method[] methods = targetCls.getMethods();
			for(int i=0; i<methods.length; i++){
				if(methods[i].getName().startsWith("get") && methods[i].getParameterTypes().length==0)
					getterMethodsHT.put(prefix + methods[i].getName().toLowerCase(), methods[i]);
			}
			getterMethodsHT.put(targetCls.getName(), "exists");
		}
		
		if(getterMethodsHT.containsKey(prefix + "get" + propertyName))
			return (Method)getterMethodsHT.get(prefix + "get" + propertyName);
		
		return null;
		
	}
	
	public static Object getSpecialKeyValues(Activity activity, ProcessInstance instance, String k, ValidationContext vc){
		//k = k.toLowerCase();
		if(k.toLowerCase().startsWith("definition.")){
			try{
				String suffix = k.substring("definition.".length(), k.length());
				suffix = suffix.trim();
				
				Method getter = getGetterMethod(activity.getProcessDefinition().getClass(), suffix);
				Object value;
				if(getter != null){
					value = getter.invoke(activity.getProcessDefinition(), new Object[]{});
					
					return value;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else
		if(k.toLowerCase().startsWith("activity.")){
			try{
				String suffix = k.substring("activity.".length(), k.length());
				suffix = suffix.trim();

				Method getter = getGetterMethod(activity.getClass(), suffix);
				Object value;
				if(getter != null){
					value = getter.invoke(activity, new Object[]{});
					
					return value;
				}
			}catch(Exception e){
				e.printStackTrace();	
			}
		}

		if(instance==null) return null;
		
		if(k.toLowerCase().startsWith("instance.")){ // <%=instance.name%>
			try{
				String suffix = k.substring("instance.".length(), k.length());
				suffix = suffix.trim();

				Method getter = getGetterMethod(instance.getClass(), suffix);
				Object value;
				if(getter != null){
					value = getter.invoke(instance, new Object[]{});
					
					return value;
				}
			}catch(Exception e){
				e.printStackTrace();	
			}
		}else
		if(k.toLowerCase().startsWith("roles.")){
			try{
				String[] elements = k.replace('.','@').split("@");
				String roleName = null;
				String suffixName = null;
				if(elements.length > 1){
					roleName = elements[1].trim();
				}
				if(elements.length > 2){
					suffixName = elements[2];
				}
				
				Role theRole = (roleName!=null ? instance.getProcessDefinition().getRole(roleName) : null);
				
				if(theRole!=null){
					if(!GlobalContext.isDesignTime()){
						RoleMapping theMapping = theRole.getMapping(instance);
						
						if(theMapping!=null && suffixName!=null){
							Method getter = getGetterMethod(theMapping.getClass(), suffixName);
							Object value;
							if(getter != null){
								value = getter.invoke(theMapping, new Object[]{});
								
								return value;
							}
						}else{
							return theMapping;
						}
					}
				}else{
					vc.add("Undeclared role name [" + roleName + "].");
				}
				
			}catch(Exception e){
				e.printStackTrace();	
			}
		}else{
			try{
				if ( instance == null ) return null;
				Object value = instance.get(k);
				if(value!=null){
					return value;
				}else{
					if(vc!=null)
						vc.addAll(instance.getValidationContext());
					
					return null;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(vc!=null)
			vc.addWarning("the expression '" + k + "' cannot be resolved.");
			
		return null;
	}
	
	protected void gatherPropagatedActivities(ProcessInstance instance, List<Activity> list) throws Exception{
		ComplexActivity parent = ((ComplexActivity)getParentActivity());
		if(parent==null)
			return;
			
		parent.gatherPropagatedActivitiesOf(instance, this, list);
	}
	
	public List<Activity> getPropagatedActivities(ProcessInstance instance) throws Exception{
		List<Activity> actList = new ArrayList<Activity>();		
		gatherPropagatedActivities(instance, actList);
		
		return actList;
	}	

	public List compensateToThis(ProcessInstance instance) throws Exception{
		return compensateToThis(instance, true);
	}
		
	public List compensateToThis(ProcessInstance instance, boolean compensateAndResume) throws Exception{
		List superCompensatingList = null;
		if(instance.isSubProcess()){
			String instanceStatus = instance.getStatus();
			
			if(instanceStatus.equals(Activity.STATUS_COMPLETED) || instanceStatus.equals(Activity.STATUS_SKIPPED) || instanceStatus.equals(Activity.STATUS_FAULT)){
				String returningTracingTag = (String)instance.getMainActivityTracingTag();
				String returningInstanceId = (String)instance.getMainProcessInstanceId();
				
				Hashtable<String, ProcessTransactionContext> options = new Hashtable<String, ProcessTransactionContext>();
				options.put("ptc", instance.getProcessTransactionContext());
				
				ProcessInstance returningInstance = ProcessInstance.create().getInstance(returningInstanceId, options);
				ProcessDefinition returningDefinition = returningInstance.getProcessDefinition();
				SubProcessActivity returningActivity = (SubProcessActivity)returningDefinition.getActivity(returningTracingTag);

//				if(returningInstance.getStatus().equals(Activity.STATUS_COMPLETED) || returningInstance.getStatus().equals(Activity.STATUS_SKIPPED))
//					returningDefinition.compensateOneStep(returningInstance);
				
				superCompensatingList = returningActivity.compensateToThis(returningInstance, false);
				
				returningActivity.setStatus(returningInstance, Activity.STATUS_RUNNING);
				
//				returningDefinition.compensateOneStep(returningInstance);
//				getProcessDefinition().compensateOneStep(instance);
			}
		}
		
		List actList = getPropagatedActivities(instance);
		
		Activity theLastPropagatedActivity = null;
		if(actList.size() > 0){
			theLastPropagatedActivity = (Activity)actList.get(actList.size() - 1);
			if(Activity.STATUS_COMPLETED.equals(instance.getStatus()) && instance.isSubProcess() && Activity.STATUS_COMPLETED.equals(theLastPropagatedActivity.getStatus(instance))){
				theLastPropagatedActivity.setStatus(instance, Activity.STATUS_SUSPENDED);
			}
		}else{
			theLastPropagatedActivity = this;
		}

		ComplexActivity theRootOfLastPropagatedActivity = (ComplexActivity)theLastPropagatedActivity.getParentActivity();
		while(theRootOfLastPropagatedActivity.getParentActivity() != null 
				&& STATUS_COMPLETED.equals(theRootOfLastPropagatedActivity.getParentActivity().getStatus(instance))){
			theRootOfLastPropagatedActivity = (ComplexActivity)theRootOfLastPropagatedActivity.getParentActivity();
		}
		
		if(STATUS_COMPLETED.equals(theRootOfLastPropagatedActivity.getStatus(instance))){
			if(theRootOfLastPropagatedActivity.getParentActivity()!=null)
				((ComplexActivity)theRootOfLastPropagatedActivity.getParentActivity()).compensateChild(instance, theRootOfLastPropagatedActivity);
			else
				theRootOfLastPropagatedActivity.compensateOneStep(instance);
		}
		
		for(int i=actList.size()-1; i>-1; i--){
			Activity actToBeCompensated = (Activity)actList.get(i);
			String status = actToBeCompensated.getStatus(instance);
			if(status.equals(Activity.STATUS_SKIPPED)){
				actToBeCompensated.reset(instance);
			}else if(Activity.isCompensatable(status)){	
				actToBeCompensated.compensate(instance);
			}/*else{
				throw new UEngineException("couldn't compensate!");
			}*/
		}
		
		instance.addActivityEventInterceptor(new ActivityEventInterceptor(){

			public boolean interceptEvent(Activity activity, String command,
					ProcessInstance instance, Object payload) throws Exception {

				if(activity == Activity.this && ACTIVITY_COMPENSATED.equals(command)){					
					
					instance.removeActivityEventInterceptor(this);
					
					return true;
				}
				
				return false;
			}
			
		});
		
		//is it right that the target activity should be compensated as well? we decided it is right.
		compensate(instance);
				
		if(compensateAndResume){
			resume(instance);
		}

//		}else{	
//			if(actList.size() == 0){
//				ComplexActivity parent = (ComplexActivity)getParentActivity();
//				while(parent!=null){
//					parent.setStatus(instance, Activity.STATUS_RUNNING);
//					parent = (ComplexActivity)parent.getParentActivity();
//				}
//				
//				setStatus(instance, Activity.STATUS_RUNNING);
//			}
//		}
		
		if(superCompensatingList!=null)
			actList.addAll(0, superCompensatingList);
		
		fireEventToActivityFilters(instance, "returned", this);
		
		return actList;
		//Integer.parseInt("xxxx");
	}
	
	public void firePropertyChangeEventToActivityFilters(ProcessInstance instance, String propertyName, Object changedValue) throws Exception{
		if(getProcessDefinition() == null) return;
		
		ActivityFilter[] activityFilters = getProcessDefinition().getActivityFilters();
		if(activityFilters!=null)
		for(int i=0; i<activityFilters.length; i++)
			activityFilters[i].onPropertyChange(this, instance, propertyName, changedValue);
	}
	
	protected void fireEventToActivityFilters(ProcessInstance instance, String eventName, Object payload) throws Exception{
		if(getProcessDefinition() == null) return;
		
		ActivityFilter[] activityFilters = getProcessDefinition().getActivityFilters();
		if(activityFilters!=null)
		for(int i=0; i<activityFilters.length; i++)
			if(activityFilters[i] instanceof SensitiveActivityFilter)
				((SensitiveActivityFilter)activityFilters[i]).onEvent(this, instance, eventName, payload);
		
		
		int triggeringMethodMapped = getTriggeringMethodFromEventName(eventName);
		
		List<String> messageListeners = instance.getMessageListeners("event");
		for(int i=0; i<messageListeners.size(); i++){
			MessageListener messageListener = (MessageListener)getProcessDefinition().getActivity((String) messageListeners.get(i));
			if(messageListener instanceof ScopeActivity){
				ScopeActivity scopeActivity = (ScopeActivity)messageListener;
				
				if(scopeActivity.isAncestorOf(this)){
					EventMessagePayload emp = new EventMessagePayload();
					emp.setTriggerTracingTag(getTracingTag());
					
					scopeActivity.fireEventHandlers(instance, triggeringMethodMapped, emp);
					
				}
			}
		}
	}
	
	static HashMap<String, Integer> triggerMethodMappingWithEventName; static{
		triggerMethodMappingWithEventName = new HashMap<String, Integer>();
		triggerMethodMappingWithEventName.put("saveWorkitem", Integer.valueOf(EventHandler.TRIGGERING_BY_AFTER_CHILD_SAVED));
		triggerMethodMappingWithEventName.put("saveAnyway", Integer.valueOf(EventHandler.TRIGGERING_BY_AFTER_CHILD_SAVED_OR_COMPLETED));
	}
	private int getTriggeringMethodFromEventName(String eventName){
		try{
			return triggerMethodMappingWithEventName.get(eventName).intValue();
		}catch(Exception e){
			return -1;
		}
	}
	
	public boolean registerToProcessDefinition(boolean autoTagging, boolean checkCollision) {
		return getProcessDefinition().registerActivity(this, autoTagging, checkCollision);
	}
	
	public String toString() {		
		return getName() + "(" + getTracingTag() + ")";
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof Activity)) return false;
		
		Activity comparatee = (Activity)obj;

		try{
			return getProcessDefinition().getId().equals(comparatee.getProcessDefinition().getId()) && getTracingTag().equals(comparatee.getTracingTag());
		}catch(Exception e){
			return super.equals(obj);
		}
	}
	
	public boolean isSuccessorOf(Activity complexActivity){
		Activity temp=this;
		while(temp.getParentActivity()!=null){
			temp = temp.getParentActivity();
			if(temp == complexActivity) return true;
		}
		
		return false;
	}
	
	public boolean isAncestorOf(Activity activity){
		return activity.isSuccessorOf(this);
	}

	public GraphActivity toGraph(Hashtable graph2BlockMap){
		try {
			return (GraphActivity) ProcessDefinitionAdapter.getAdapter(getClass()).convert(this, graph2BlockMap);
		} catch (Exception e) {
			throw new RuntimeException("Error when to convert into graph", e);
		}
	}
	
	
	public Transferable createTransferrable(){

		return new Transferable(){
			public DataFlavor[] getTransferDataFlavors() {
				return null;
			}
	
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return false;
			}
	
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				return Activity.this;
			}
		};

	}
	

	public void executeTest(ProcessInstance instance, ProcessInstance testInstance) throws Exception{
		executeActivity(instance);
	}

}
