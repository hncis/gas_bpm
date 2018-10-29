package com.defaultcompany.external.controller.http.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.defaultcompany.external.model.stdmsg.ActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalDraftActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalLineChangeMsg;
import com.defaultcompany.external.model.stdmsg.BaseStdMsg;
import com.defaultcompany.external.model.stdmsg.DelegateWorkItemMsg;
import com.defaultcompany.external.model.stdmsg.InitializeProcessInstanceMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStartMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStopMsg;
import com.defaultcompany.external.model.stdmsg.TaskInfoMsg;
import com.defaultcompany.external.model.stdmsg.WorkItemAcceptMsg;

public class StdMsgContext {
	
	public final static String CMD_PROCESS_START = "PROC_START";
	public final static String CMD_ACTIVITY_COMPLETE = "ACT_COMPLETE";
	public final static String CMD_PROCESS_STOP = "PROC_STOP";
	
	public final static String CMD_APPROVAL_DRAFT = "APPROVAL_DRAFT";
	public final static String CMD_APPROVAL_COMPLETE = "APPROVAL_COMPLETE";
	public final static String CMD_APPROVAL_LINE_CHANGE = "APPROVAL_LINE_CHANGE";
	
	public final static String CMD_TASK_INFO = "TASK_INFO";
	public final static String CMD_INITIALIZE_PROCESS_INSTANCE = "INITIALIZE_PROCESS_INSTANCE";
	
	public final static String CMD_WORKITEM_ACCEPT = "WORKITEM_ACCEPT";
	public final static String CMD_DELEGATE_WORKITEM = "DELEGATE_WORKITEM";
		
	public StdMsgContext() {}
	
	private void bindCommon(HttpServletRequest request, BaseStdMsg baseStdMsg) {
		baseStdMsg.setExternalKey(ServletRequestUtils.getStringParameter(request, "externalKey", null));
		baseStdMsg.setProcessVariables(ServletRequestUtils.getStringParameter(request, "procVal", null));
		baseStdMsg.setRoles(ServletRequestUtils.getStringParameter(request, "procRole", null));
	}
	
	public WorkItemAcceptMsg bindWorkItemAcceptMsg(HttpServletRequest request) {
		WorkItemAcceptMsg wiaMsg = new WorkItemAcceptMsg();
		
		this.bindCommon(request, wiaMsg);
		
		wiaMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		wiaMsg.setTracingTag(ServletRequestUtils.getStringParameter(request, "tracingTag", null));
		wiaMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		return wiaMsg;
	}
	
	public InitializeProcessInstanceMsg bindInitializeProcessInstanceMsg(HttpServletRequest request) {
		InitializeProcessInstanceMsg ipiMsg = new InitializeProcessInstanceMsg();
		
		this.bindCommon(request, ipiMsg);
		
		ipiMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		ipiMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		return ipiMsg;
	}
	
	public TaskInfoMsg bindTaskInfoMsg(HttpServletRequest request) {
		TaskInfoMsg taskInfoMsg = new TaskInfoMsg();
		
		this.bindCommon(request, taskInfoMsg);
		
		taskInfoMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		taskInfoMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		return taskInfoMsg;
	}
	
	public ApprovalActivityCompleteMsg bindApprovalActivityCompleteMsg(HttpServletRequest request) {
		ApprovalActivityCompleteMsg aacMsg = new ApprovalActivityCompleteMsg();
		
		this.bindCommon(request, aacMsg);
		
		aacMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		aacMsg.setTaskId(ServletRequestUtils.getStringParameter(request, "taskId", null));
		aacMsg.setTracingTag(ServletRequestUtils.getStringParameter(request, "tracingTag", null));
		aacMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		aacMsg.setApprovalKey(ServletRequestUtils.getStringParameter(request, "approvalKey", null));
		aacMsg.setApprovalStatus(ServletRequestUtils.getStringParameter(request, "approvalStatus", null));
		aacMsg.setComment(ServletRequestUtils.getStringParameter(request, "comment", null));
		
		return aacMsg;
	}
	
	public ApprovalDraftActivityCompleteMsg bindApprovalDraftMsg(HttpServletRequest request) {
		ApprovalDraftActivityCompleteMsg adacMsg = new ApprovalDraftActivityCompleteMsg();
		
		this.bindCommon(request, adacMsg);
	
		adacMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		adacMsg.setTaskId(ServletRequestUtils.getStringParameter(request, "taskId", null));
		adacMsg.setTracingTag(ServletRequestUtils.getStringParameter(request, "tracingTag", null));
		adacMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		adacMsg.setApprovalKey(ServletRequestUtils.getStringParameter(request, "approvalKey", null));
		adacMsg.setMainParam(ServletRequestUtils.getStringParameter(request, "mainParam", null));
		adacMsg.setSubParam(ServletRequestUtils.getStringParameter(request, "subParam", null));
		adacMsg.setComment(ServletRequestUtils.getStringParameter(request, "comment", null));
		
		return adacMsg;
	}
	
	public ProcessStartMsg bindProcessStartMsg(HttpServletRequest request) {
		ProcessStartMsg psMsg = new ProcessStartMsg();
		
		this.bindCommon(request, psMsg);
		
		psMsg.setInitiator(ServletRequestUtils.getStringParameter(request, "initiator", null));
		psMsg.setProcAlias(ServletRequestUtils.getStringParameter(request, "procAlias", null));
		
		psMsg.setApprovalKey(ServletRequestUtils.getStringParameter(request, "approvalKey", null));
		psMsg.setComment(ServletRequestUtils.getStringParameter(request, "comment", null));
		psMsg.setMainParam(ServletRequestUtils.getStringParameter(request, "mainParam", null));
		psMsg.setSubParam(ServletRequestUtils.getStringParameter(request, "subParam", null));
		
		psMsg.setInstanceName(ServletRequestUtils.getStringParameter(request, "instanceName", null));
		
		psMsg.setFirstTaskCompleted(ServletRequestUtils.getBooleanParameter(request, "firstTaskCompleted", true));
		
		return psMsg;
	}
	
	public ProcessStopMsg bindProcessStopMsg(HttpServletRequest request) {
		ProcessStopMsg psMsg = new ProcessStopMsg();
		
		this.bindCommon(request, psMsg);
		
		psMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		psMsg.setInstanceStatus(ServletRequestUtils.getStringParameter(request, "instanceStatus", null));
		
		return psMsg;
	}
	
	public ActivityCompleteMsg bindActivityCompleteMsg(HttpServletRequest request) {
		ActivityCompleteMsg acMsg = new ActivityCompleteMsg();
		
		this.bindCommon(request, acMsg);
		
		acMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		acMsg.setTaskId(ServletRequestUtils.getStringParameter(request, "taskId", null));
		acMsg.setTracingTag(ServletRequestUtils.getStringParameter(request, "tracingTag", null));
		acMsg.setEndpoint(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		return acMsg;
	}
	
	public ApprovalLineChangeMsg bindApprovalLineChangeMsg(HttpServletRequest request) {
		ApprovalLineChangeMsg alcMsg = new ApprovalLineChangeMsg();
		
		this.bindCommon(request, alcMsg);
		
		alcMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		alcMsg.setApprovalKey(ServletRequestUtils.getStringParameter(request, "approvalKey", null));
		
		return alcMsg;
	}
	
	public DelegateWorkItemMsg bindDelegateWorkItemMsg(HttpServletRequest request) {
		DelegateWorkItemMsg dwMsg = new DelegateWorkItemMsg();
		
		this.bindCommon(request, dwMsg);
		
		dwMsg.setInstanceId(ServletRequestUtils.getStringParameter(request, "instanceId", null));
		dwMsg.setTracingTag(ServletRequestUtils.getStringParameter(request, "tracingTag", null));
		dwMsg.setEndpoints(ServletRequestUtils.getStringParameter(request, "endpoint", null));
		
		return dwMsg;
	}
	
}
