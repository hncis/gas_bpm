package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.WorkItemAcceptMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class WorkItemAcceptEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public WorkItemAcceptEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);

		WorkItemAcceptMsg wiaMsg = new WorkItemAcceptMsg();

		wiaMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		wiaMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		wiaMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		wiaMsg.setEndpoint(requestElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		wiaMsg.setInstanceId(requestElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		wiaMsg.setTracingTag(requestElement.getChildText("tracingTag", WORKFLOW_NAMESPACE));
		
		System.out.println(wiaMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.workItemAccept(wiaMsg);
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

		return MakeReturnMsg.toElement(status, taskInfoList, WORKITEM_ACCEPT_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
