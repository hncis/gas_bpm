package org.uengine.workflow.springws;

import org.jdom2.Namespace;

public interface WorkflowWebServiceConstants {

	// process
//	String INITIALIZE_PROCESS_REQUEST = "InitializeProcessRequest";
//	String INITIALIZE_PROCESS_RESPONSE = "InitializeProcessResponse";
	
	String START_PROCESS_REQUEST = "StartProcessRequest";
	String START_PROCESS_RESPONSE = "StartProcessResponse";

	String SET_PROCESS_VARIABLE_REQUEST = "SetProcessVariableRequest";
	String SET_PROCESS_VARIABLE_RESPONSE = "SetProcessVariableResponse";	

//	String GET_PROCESS_VARIABLE_REQUEST = "GetProcessVariableRequest";
//	String GET_PROCESS_VARIABLE_RESPONSE = "GetProcessVariableResponse";

	String PUT_ROLEMAPPING_REQUEST = "PutRoleMappingRequest";
	String PUT_ROLEMAPPING_RESPONSE = "PutRoleMappingResponse";

//	String GET_ROLEMAPPING_REQUEST = "GetRoleMappingRequest";
//	String GET_ROLEMAPPING_RESPONSE = "GetRoleMappingResponse";
	
	String WORKITEM_ACCEPT_REQUEST = "WorkItemAcceptRequest";
	String WORKITEM_ACCEPT_RESPONSE = "WorkItemAcceptResponse";
	
	String COMPLETE_WORKITEM_REQUEST = "CompleteWorkitemRequest";
	String COMPLETE_WORKITEM_RESPONSE = "CompleteWorkitemResponse";

	// worklist
	String GET_WORKLIST_REQUEST = "GetWorkListRequest";
	String GET_WORKLIST_RESPONE = "GetWorkListResponse";

	// namespace
	String NAMESPACE_WF_PREFIX = "wf";
	String NAMESPACE_WF_URI = "http://www.uengine.org/workflow-ws/schemas";
	Namespace WORKFLOW_NAMESPACE = Namespace.getNamespace(NAMESPACE_WF_PREFIX, NAMESPACE_WF_URI);

}
