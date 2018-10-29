/*
 * Created on 2004-04-12
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.kernel.viewer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessDefinitionFactory;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ScopeActivity;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.util.UEngineUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubProcessActivityViewer extends DefaultActivityViewer {
	final static String PARENT_TRACINGTAG = "parentTracingTag";
	public final static String subProcessDrillingDown_By_PopingUp = "subProcessDrillingDown_By_PopingUp";

/*	public String getAnchor(Activity activity, ProcessInstance instance, Map options){
		SubProcessActivity subProcessActivity = (SubProcessActivity)activity;
		
		try{
			String spid = subProcessActivity.getSubprocessInstanceId(instance);
			if(spid!=null && spid.trim().length()>0 && !spid.equals("null"))		
				
			return "javascript:showSubProcess(\"" + spid + "\")";
		}catch(Exception e){			
		}
		
		return super.getAnchor(activity, instance, options);
	}*/

	public StringBuilder render(
		Activity activity,
		ProcessInstance instance,
		Map options) {
		
		//pushParentTracingTag(options, activity.getTracingTag());
		SubProcessActivity subProcessActivity = (SubProcessActivity)activity;
		
		if(options.containsKey("replacingSubProcessTracingTag")){
			String replacingSubProcessTracingTag = (String) options.get("replacingSubProcessTracingTag");
			
			if(replacingSubProcessTracingTag.equals(activity.getTracingTag()))
			{
				try
				{
					List<String> subProcessInstanceIds;
					subProcessInstanceIds = subProcessActivity.getSubprocessIds(instance);
					List<String> subProcessInstanceLabels = subProcessActivity.getSubprocessLabels(instance);
					
					String replacingSubProcessKey = (String) options.get("replacingSubProcessKey");
					
					int subProcessIndex = subProcessInstanceLabels.indexOf(replacingSubProcessKey);
                    /*
                    int replacingKeySize = replacingSubProcessKey.length();
                    if(subProcessIndex < 0){
                        for(int i = 0 ; i < subProcessInstanceLabels.size() ; i++){
                            String extKey = (String)subProcessInstanceLabels.get(i);
                            System.out.println("before subString extKey["+extKey+"]");
                            if(extKey != null && extKey.length() > replacingKeySize){
                                extKey = extKey.substring(0, replacingKeySize);
                            }
                            System.out.println("after subString extKey["+extKey+"]");
                            if(extKey.equals(replacingSubProcessKey)){
                                subProcessIndex = i;
                                break;
                            }
                        }
                    }
                    */
                    if(subProcessIndex > -1){
						String replacingSubProcessInstanceId = (String)subProcessInstanceIds.get(subProcessIndex);
						ProcessInstance replacingSubProcessInstance = instance.getProcessTransactionContext().getProcessManager().getProcessInstance(replacingSubProcessInstanceId);
						SequenceActivityViewer defViewer = new SequenceActivityViewer();
						
						return defViewer.render(replacingSubProcessInstance.getProcessDefinition(), replacingSubProcessInstance, options);
					}else{
						System.out.println("Although you've requested a sub process replacing option, there's no matching instance for the requested key["+ replacingSubProcessKey +"].");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		/**
		 * showing subprocess as no level
		 */
		if(subProcessActivity.isViewAlsoInMainProcess() && instance!=null){
			try {
				ProcessInstance replacingSubProcessInstance = (ProcessInstance) subProcessActivity.getSubProcesses(instance).get(0);
				SequenceActivityViewer defViewer = new SequenceActivityViewer();
				return defViewer.render(replacingSubProcessInstance.getProcessDefinition(), replacingSubProcessInstance, options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * try showing sub process' outlines
		 */ 
		if(options.containsKey("showSubProcessOutline") && instance!=null){
			try{

				int segmentIndex = activity.getTracingTag().indexOf("_");
				if(segmentIndex == -1){

					String spDefId = subProcessActivity.getDefinitionIdOnly();
					ProcessManagerBean processManager = instance.getProcessTransactionContext().getProcessManager();				
					String spVersionId = processManager.getProcessDefinitionProductionVersion(spDefId);
				
					ProcessDefinition spDef = ProcessDefinitionFactory.getInstance(instance.getProcessTransactionContext()).getDefinition(spVersionId);
					
					List<Activity> scopeActivities = new ArrayList<Activity>();
					List<Activity> firstLevelChild = spDef.getChildActivities();
					
					SequenceActivity sequence = null;
	
					List<ProcessInstance> subProcesses = subProcessActivity.getSubProcesses(instance);
					ProcessInstance proxyInstance = instance.createSnapshot();
					
					int j = 0;
					for(int i=0; i<firstLevelChild.size(); i++){
						if(firstLevelChild.get(i) instanceof ScopeActivity){
							if(sequence==null){
								sequence = new SequenceActivity();
								sequence.setTracingTag(activity.getTracingTag());
							}
							
							ScopeActivity scopeChild = (ScopeActivity)firstLevelChild.get(i); 
							scopeActivities.add(scopeChild);
							
							SubProcessActivity segment_subProcessActivity = new SubProcessActivity();
							segment_subProcessActivity.setName(scopeChild.getName());
							segment_subProcessActivity.setDefinitionId(subProcessActivity.getDefinitionId());
							segment_subProcessActivity.setTracingTag(activity.getTracingTag() + "_" + j);
							segment_subProcessActivity.setStatus(instance, Activity.STATUS_READY);
							
							boolean bAllisCompleted = (subProcesses.size() > 0);
							for(int k=0; k<subProcesses.size(); k++){
								ProcessInstance theProcessInstance = (ProcessInstance) subProcesses.get(k);
								String status = theProcessInstance.getStatus(scopeChild.getTracingTag());
								
								if(!Activity.STATUS_COMPLETED.equals(status)){
									if(!Activity.STATUS_READY.equals(status)){
										segment_subProcessActivity.setStatus(proxyInstance, Activity.STATUS_RUNNING);
										bAllisCompleted = false;
										break;
									}else
										bAllisCompleted = false;
								}
							}
							
							if(bAllisCompleted)
								segment_subProcessActivity.setStatus(proxyInstance, Activity.STATUS_COMPLETED);
							
							sequence.addChildActivity(segment_subProcessActivity);
							
							j++;
						}
					}
					
					if (sequence!=null) {
						SequenceActivityViewer seqViewer = new SequenceActivityViewer();
						proxyInstance.setProcessDefinition(subProcessActivity.getProcessDefinition());
						
						// if activity name is empty set activity name.
						sequence.setName(activity.getName());
						
						return seqViewer.render(sequence, proxyInstance, options);
					}
				}
				
			} catch(Exception e) { }
		}

//		StringBuffer sb =super.render(activity, instance, options);
		//popParentTracingTag(options);
		
		return super.render(activity, instance, options);
	}
	
	public static List getParentTracingTags(Map options){
		List parentTracingTags = null;
		if(options.containsKey(PARENT_TRACINGTAG) && ((List) options.get(PARENT_TRACINGTAG)).size() != 0){
			parentTracingTags = (List) options.get(PARENT_TRACINGTAG);
		}
		
		return parentTracingTags;
	}
	
	public static String getAbsoluteParentTracingTag(Map options, ProcessInstance instance, String tracingTag){
		List parentTracingTags = getParentTracingTags(options);
		StringBuffer absTracingTag = new StringBuffer();
		String sep = "";
		
		if (parentTracingTags != null) {
			for(int i=0; i<parentTracingTags.size(); i++){
				absTracingTag.append(sep + parentTracingTags.get(i));
				sep = "_";
			}
		}
		
		String absTT = absTracingTag  + (tracingTag!=null ? sep + tracingTag : "");
		
		if (instance != null && instance.getExecutionScopeContext() != null) {
			ProcessDefinition definition;
			try {
				definition = instance.getProcessDefinition();
				Activity theActivity = definition.getActivity(tracingTag);
				Activity rootActivityInTheScope = instance.getExecutionScopeContext().getRootActivityInTheScope();
				if(rootActivityInTheScope == theActivity || rootActivityInTheScope.isAncestorOf(theActivity)){
					absTT = absTT + "__" + instance.getExecutionScopeContext().getExecutionScope();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (UEngineUtil.isNotEmpty(absTT)) {
				absTT += "_";
			}
			if (instance instanceof EJBProcessInstance) {
				absTT += "instance" + instance.getInstanceId();
			} else {
				absTT += "definition" + instance.getProcessDefinition().getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return absTT;
	}
	
	public static void pushParentTracingTag(Map options, String tracingTag){
		if(!options.containsKey(PARENT_TRACINGTAG)){
			options.put(PARENT_TRACINGTAG, new ArrayList());
		}
		
		List parentTracingTags = (List) options.get(PARENT_TRACINGTAG);
			
		parentTracingTags.add(tracingTag);
	}
	
	public String popParentTracingTag(Map options){
		if(!options.containsKey(PARENT_TRACINGTAG)){
			return null;
		}
		
		List parentTracingTags = (List)options.get(PARENT_TRACINGTAG);
			
		int lastIdx = parentTracingTags.size()-1;
		
		if(lastIdx < 0) return null;
		
		String lastOne = (String)parentTracingTags.get(lastIdx);
		
		parentTracingTags.remove(lastIdx);
		return lastOne;
	}
	
	public static String getAbsoluteTracingTag(Map options, Activity activity){
		List parentTracingTags = getParentTracingTags(options);
		
		StringBuilder absTT = new StringBuilder();
		for(int i=0; i<parentTracingTags.size(); i++){
			if (absTT.length() > 0) absTT.append("@");
			absTT.append((String)parentTracingTags.get(i));
		}
		
		return absTT.append("@").append(activity.getTracingTag()).toString();
	}

	protected String getDetails(
		Activity activity,
		ProcessInstance instance,
		Map options) {
			
		SubProcessActivity subProcessActivity = (SubProcessActivity)activity;
		
		Map options_copy = new HashMap();
		options_copy.putAll(options);
		options_copy.remove("highlight");
		options = options_copy;
		
		StringBuffer spFlowChartHTML = new StringBuffer();
		
		if (options.containsKey(subProcessDrillingDown_By_PopingUp)) {
			spFlowChartHTML.append("<div id='subprocessActivity" + getAbsoluteParentTracingTag(options, instance, activity.getTracingTag())+"'></div>");
			
		} else {
			return super.getDetails(activity, instance, options);
		}
		
		return spFlowChartHTML!=null ? spFlowChartHTML.toString(): "";
	}

	public String getOnClick(Activity activity, ProcessInstance instance, Map options) {
		StringBuffer onClick = new StringBuffer(super.getOnClick(activity, instance, options));
		ViewerOptions viewerOptions = new ViewerOptions();
		
	    try {
			if (!options.containsKey("enableUserEvent_drillInto_org.uengine.kernel.SubProcessActivity")) {
				if (options.containsKey(viewerOptions.VIEW_SUBPROCESS)) {
					SubProcessActivity subProcessActivity = (SubProcessActivity) activity;
					String defVerId = "";
				    String instanceId = "";
				    
					if (instance.isRunning("") || instance.isCompleted("")) {
						//instnaceId = instance.getInstanceId();
						List<String> vSubProcessIds = subProcessActivity.getSubprocessIds(instance);
						
						StringBuilder sbSubProcessIds = new StringBuilder();

						for (String subProcessId : vSubProcessIds) {
							if (sbSubProcessIds.length() > 0) sbSubProcessIds.append(";");
							sbSubProcessIds.append(subProcessId);
						}
						
						instanceId = sbSubProcessIds.toString();
					}
					
					defVerId = subProcessActivity.getDefinitionVersionId(null, instance);
					
				    onClick.append(
				    		"; viewSubProcess('"
					    		+ instanceId + "', '"
					    		+ defVerId + "', '"
					    		+ activity.getName() + "', '"
					    		+ DefaultActivityViewer.getActivityName(activity, instance, options)
				    		+ "', this);"
				    );
					
				}
			}
		}
	    catch (RemoteException e)
	    {
			e.printStackTrace();
		}
	    catch (Exception e)
	    {
			e.printStackTrace();
		}
		
		return onClick.toString();
	}
	
	public StringBuffer getActivityPropertyString(Activity activity, ProcessInstance instance, Map options) throws Exception{
		SubProcessActivity subProcessActivity = (SubProcessActivity)activity;
		
		StringBuffer sbActivityPropertyString = super.getActivityPropertyString(activity, instance, options);

		if(!Activity.STATUS_READY.equals(instance.getStatus(""))){
			List<String> vSubProcessIds = subProcessActivity.getSubprocessIds(instance);
			StringBuffer sbSubProcessIds = new StringBuffer();
			for(int i=0; i<vSubProcessIds.size(); i++){
				if( i > 0 ) sbSubProcessIds.append(";");
				sbSubProcessIds.append((String)vSubProcessIds.get(i));
			}
			sbActivityPropertyString.append("subInstanceId=").append( sbSubProcessIds.toString() ).append(",");
		}else{
			sbActivityPropertyString.append("subDefinitionId=").append( subProcessActivity.getDefinitionIdOnly() ).append(",");
		}
		return sbActivityPropertyString;
	}

}
