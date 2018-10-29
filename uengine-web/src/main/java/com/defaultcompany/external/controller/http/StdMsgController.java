package com.defaultcompany.external.controller.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.http.support.StdMsgContext;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.ActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalDraftActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalLineChangeMsg;
import com.defaultcompany.external.model.stdmsg.DelegateWorkItemMsg;
import com.defaultcompany.external.model.stdmsg.InitializeProcessInstanceMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStartMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStopMsg;
import com.defaultcompany.external.model.stdmsg.ReturnMsg;
import com.defaultcompany.external.model.stdmsg.TaskInfoMsg;
import com.defaultcompany.external.model.stdmsg.WorkItemAcceptMsg;
import com.defaultcompany.external.service.ProcessManagerService;
import com.thoughtworks.xstream.XStream;

public class StdMsgController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StdMsgContext stdMsgContext = new StdMsgContext();
		String cmdBpm = ServletRequestUtils.getStringParameter(request, "cmdBpm", null);
		
		String returnEndpoint = null;
		String returnInstanceId = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, request);
			
			if (StdMsgContext.CMD_PROCESS_START.equalsIgnoreCase(cmdBpm)) {
				ProcessStartMsg psMsg = stdMsgContext.bindProcessStartMsg(request);
				System.out.println(psMsg);
				
				String newInstanceId = pms.processStart(psMsg);
				
				returnEndpoint = psMsg.getInitiator();
				returnInstanceId = newInstanceId;

			} else if (StdMsgContext.CMD_ACTIVITY_COMPLETE.equalsIgnoreCase(cmdBpm)) {
				ActivityCompleteMsg acMsg = stdMsgContext.bindActivityCompleteMsg(request);
				System.out.println(acMsg);
				
				pms.activityComplete(acMsg);
				
				returnEndpoint = acMsg.getEndpoint();
				returnInstanceId = acMsg.getInstanceId();
				
			} else if (StdMsgContext.CMD_PROCESS_STOP.equalsIgnoreCase(cmdBpm)) {
				ProcessStopMsg psMsg = stdMsgContext.bindProcessStopMsg(request);
				System.out.println(psMsg);
				
				pms.processStop(psMsg);
				
			} else if (StdMsgContext.CMD_APPROVAL_DRAFT.equalsIgnoreCase(cmdBpm)) {
				ApprovalDraftActivityCompleteMsg adacMsg = stdMsgContext.bindApprovalDraftMsg(request);
				
				System.out.println(adacMsg);
				pms.approvalDraftActivityComplete(adacMsg);
				
				returnEndpoint = adacMsg.getEndpoint();
				returnInstanceId = adacMsg.getInstanceId();
				
			} else if (StdMsgContext.CMD_APPROVAL_COMPLETE.equalsIgnoreCase(cmdBpm)) {
				ApprovalActivityCompleteMsg aacMsg = stdMsgContext.bindApprovalActivityCompleteMsg(request);
				System.out.println(aacMsg);
				
				pms.approvalActivityComplete(aacMsg);
				
				returnEndpoint = aacMsg.getEndpoint();
				returnInstanceId = aacMsg.getInstanceId();

			} else if (StdMsgContext.CMD_APPROVAL_LINE_CHANGE.equalsIgnoreCase(cmdBpm))  {
				ApprovalLineChangeMsg alcMsg = stdMsgContext.bindApprovalLineChangeMsg(request);
				System.out.println(alcMsg);
				
				pms.approvalLineChange(alcMsg);
			
			} else if (StdMsgContext.CMD_TASK_INFO.equalsIgnoreCase(cmdBpm)) {
				TaskInfoMsg tiMsg = stdMsgContext.bindTaskInfoMsg(request);
//				System.out.println(tiMsg);
				
				taskInfoList = pms.getTaskInfo(tiMsg);
				
			} else if (StdMsgContext.CMD_INITIALIZE_PROCESS_INSTANCE.equalsIgnoreCase(cmdBpm)) {
				InitializeProcessInstanceMsg ipiMsg = stdMsgContext.bindInitializeProcessInstanceMsg(request);
				System.out.println(ipiMsg);
				
				pms.initializeProcessInstance(ipiMsg);
				
			} else if (StdMsgContext.CMD_WORKITEM_ACCEPT.equalsIgnoreCase(cmdBpm)) {
				WorkItemAcceptMsg wiaMsg = stdMsgContext.bindWorkItemAcceptMsg(request);
				System.out.println(wiaMsg);
				
				pms.workItemAccept(wiaMsg);
				
			} else if (StdMsgContext.CMD_DELEGATE_WORKITEM.equalsIgnoreCase(cmdBpm)) {
				DelegateWorkItemMsg dwMsg = stdMsgContext.bindDelegateWorkItemMsg(request);
				System.out.println(dwMsg);
				
				pms.delegateWorkItem(dwMsg);
				
			}
			
			pm.applyChanges();
			
			
			if (!StdMsgContext.CMD_TASK_INFO.equalsIgnoreCase(cmdBpm) && StringUtils.hasText(returnInstanceId) && StringUtils.hasText(returnEndpoint)) {
				taskInfoList = pms.getTaskInfo(returnInstanceId, returnEndpoint);
			}
			
			String returnXmlMsg = makeReturnMsg("S", taskInfoList);
			System.out.println(returnXmlMsg);
			
			response.getWriter().print(returnXmlMsg);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if (pm != null)
				pm.cancelChanges();
			
			response.getWriter().print(makeReturnMsg("F", null));
			
		} finally {
			if (pm != null)
				try {
					pm.remove();
				} catch (RemoveException e) {
				}
				
			response.getWriter().flush();
			response.getWriter().close();
		}
		
		
	}
	
	private String makeReturnMsg(String status, List<TaskInfo> taskInfoList) {
		XStream xstream = new XStream();
		xstream.alias("ReturnMsg", ReturnMsg.class);
		xstream.alias("TaskInfo", TaskInfo.class);
		
		ReturnMsg returnMsg = new ReturnMsg();
		returnMsg.setStatus(status);
		if (taskInfoList != null) {
			returnMsg.setTaskInfoList(taskInfoList);
		} else {
			returnMsg.setTaskInfoList(new ArrayList<TaskInfo>());
		}
		
		return xstream.toXML(returnMsg);
	}

}
