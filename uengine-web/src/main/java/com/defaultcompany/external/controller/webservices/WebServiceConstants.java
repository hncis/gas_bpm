package com.defaultcompany.external.controller.webservices;

import org.jdom2.Namespace;

public interface WebServiceConstants {

	String ACTIVITY_COMPLETE_REQUEST = "ActivityCompleteRequest";
	String ACTIVITY_COMPLETE_RESPONSE = "ActivityCompleteResponse";
	
	String APPROVAL_DRAFT_ACTIVITY_COMPLETE_REQUEST = "ApprovalDraftActivityCompleteRequest";
	String APPROVAL_DRAFT_ACTIVITY_COMPLETE_RESPONSE = "ApprovalDraftActivityCompleteResponse";
	
	String APPROVAL_ACTIVITY_COMPLETE_REQUEST = "ApprovalActivityCompleteRequest";
	String APPROVAL_ACTIVITY_COMPLETE_RESPONSE = "ApprovalActivityCompleteResponse";
	
	String APPROVAL_LINE_CHANGE_REQUEST = "ApprovalLineChangeRequest";
	String APPROVAL_LINE_CHANGE_RESPONSE = "ApprovalLineChangeResponse";
	
	String INITIALIZE_PROCESS_INSTANCE_REQUEST = "InitializeProcessInstanceRequest";
	String INITIALIZE_PROCESS_INSTANCE_RESPONSE = "InitializeProcessInstanceResponse";
	
	String PROCESS_START_REQUEST = "ProcessStartRequest";
	String PROCESS_START_RESPONSE = "ProcessStartResponse";
	
	String PROCESS_STOP_REQUEST = "ProcessStopRequest";
	String PROCESS_STOP_RESPONSE = "ProcessStopResponse";
	
	String WORKITEM_ACCEPT_REQUEST = "WorkItemAcceptRequest";
	String WORKITEM_ACCEPT_RESPONSE = "WorkItemAcceptResponse";
	
	String TASK_INFO_REQUEST = "TaskInfoRequest";
	String TASK_INFO_RESPONSE = "TaskInfoResponse";
	
	String DELEGATE_WORKITEM_REQUEST = "DelegateWorkItemRequest";
	String DELEGATE_WORKITEM_RESPONSE = "DelegateWorkItemResponse";

	// namespace
	String NAMESPACE_WF_PREFIX = "wf";
	String NAMESPACE_WF_URI = "http://www.uengine.org/workflow-ws/schemas";
	Namespace WORKFLOW_NAMESPACE = Namespace.getNamespace(NAMESPACE_WF_PREFIX, NAMESPACE_WF_URI);

}
