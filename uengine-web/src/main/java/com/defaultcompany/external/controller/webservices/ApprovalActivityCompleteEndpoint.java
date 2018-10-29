package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.ApprovalActivityCompleteMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class ApprovalActivityCompleteEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public ApprovalActivityCompleteEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);
		Element activityCompleteElement = requestElement.getChild("activityComplete", WORKFLOW_NAMESPACE);

		ApprovalActivityCompleteMsg aacMsg = new ApprovalActivityCompleteMsg();
		aacMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		aacMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		aacMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		aacMsg.setEndpoint(activityCompleteElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		aacMsg.setInstanceId(activityCompleteElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		aacMsg.setTaskId(activityCompleteElement.getChildText("taskId", WORKFLOW_NAMESPACE));
		aacMsg.setTracingTag(activityCompleteElement.getChildText("tracingTag", WORKFLOW_NAMESPACE));
		
		aacMsg.setApprovalKey(requestElement.getChildText("approvalKey", WORKFLOW_NAMESPACE));
		aacMsg.setApprovalStatus(requestElement.getChildText("approvalStatus", WORKFLOW_NAMESPACE));
		aacMsg.setComment(requestElement.getChildText("comment", WORKFLOW_NAMESPACE));
		
		
		System.out.println(aacMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.approvalActivityComplete(aacMsg);
			pm.applyChanges();
			
			if (StringUtils.hasText(aacMsg.getInstanceId()) && StringUtils.hasText(aacMsg.getEndpoint())) {
				taskInfoList = pms.getTaskInfo(aacMsg.getInstanceId(), aacMsg.getEndpoint());
			}
			
			status = "S";
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if (pm != null)
				try {
					pm.cancelChanges();
				} catch (Exception e1) {
				}
			
			status = "F";
			
		} finally {
			if (pm != null)
				try {
					pm.remove();
				} catch (RemoveException e) {
				}
		}

		return MakeReturnMsg.toElement(status, taskInfoList, APPROVAL_ACTIVITY_COMPLETE_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
