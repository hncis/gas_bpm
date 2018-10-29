package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.ApprovalLineChangeMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class ApprovalLineChangeEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public ApprovalLineChangeEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);

		ApprovalLineChangeMsg alcMsg = new ApprovalLineChangeMsg();
		alcMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		alcMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		alcMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));

		alcMsg.setApprovalKey(requestElement.getChildText("approvalKey", WORKFLOW_NAMESPACE));
		alcMsg.setInstanceId(requestElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		
		System.out.println(alcMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.approvalLineChange(alcMsg);
			pm.applyChanges();
			
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

		return MakeReturnMsg.toElement(status, taskInfoList, APPROVAL_LINE_CHANGE_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
