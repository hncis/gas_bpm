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
import com.defaultcompany.external.model.stdmsg.ApprovalDraftActivityCompleteMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class ApprovalDraftActivityCompleteEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public ApprovalDraftActivityCompleteEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);
		Element activityCompleteElement = requestElement.getChild("activityComplete", WORKFLOW_NAMESPACE);

		ApprovalDraftActivityCompleteMsg adacMsg = new ApprovalDraftActivityCompleteMsg();
		
		adacMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		adacMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		adacMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		adacMsg.setEndpoint(activityCompleteElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		adacMsg.setInstanceId(activityCompleteElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		adacMsg.setTaskId(activityCompleteElement.getChildText("taskId", WORKFLOW_NAMESPACE));
		adacMsg.setTracingTag(activityCompleteElement.getChildText("tracingTag", WORKFLOW_NAMESPACE));
		
		adacMsg.setApprovalKey(commonElement.getChildText("approvalKey", WORKFLOW_NAMESPACE));
		adacMsg.setComment(commonElement.getChildText("comment", WORKFLOW_NAMESPACE));
		adacMsg.setMainParam(commonElement.getChildText("mainParam", WORKFLOW_NAMESPACE));
		adacMsg.setSubParam(commonElement.getChildText("subParam", WORKFLOW_NAMESPACE));
		
		System.out.println(adacMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.approvalDraftActivityComplete(adacMsg);
			pm.applyChanges();
			
			if (StringUtils.hasText(adacMsg.getInstanceId()) && StringUtils.hasText(adacMsg.getEndpoint())) {
				taskInfoList = pms.getTaskInfo(adacMsg.getInstanceId(), adacMsg.getEndpoint());
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

		return MakeReturnMsg.toElement(status, taskInfoList, APPROVAL_DRAFT_ACTIVITY_COMPLETE_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
