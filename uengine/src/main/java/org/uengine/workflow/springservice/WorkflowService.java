package org.uengine.workflow.springservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerRemote;

public interface WorkflowService {
	
	String startProcess(String type, String alias, String initiator, HashMap processVariableMap, ProcessManagerRemote pm);

	String completeWorkitem(String endpoint, String instanceId, String taskId, String tracingTag, HashMap processVariableMap, ProcessManagerRemote pm);
	
	String setProcessVariable(String instanceId, HashMap processVariableMap, ProcessManagerRemote pm);
	
	String workItemAccept(String instanceId, String tracingTag, String endpoint, ProcessManagerRemote pm);

	List getWorkList(String endpoint, String filter, int pageCount, int currentPage, ProcessManagerRemote pm);
	
	List nextTask(String requestInstanceId);
	
	boolean putRoleMapping(String instanceId, RoleMapping roleMapping, ProcessManagerRemote pm);
	
	HashMap getProcessVariable(String instanceId, ProcessManagerRemote pm);
	
	// don't use methods
	String initializeProcess(String in0, String in1);
	String getRoleMapping(String in0, String in1);
	
}