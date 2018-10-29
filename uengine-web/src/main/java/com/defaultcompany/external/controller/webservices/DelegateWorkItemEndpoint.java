package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.DelegateWorkItemMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class DelegateWorkItemEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public DelegateWorkItemEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);

		DelegateWorkItemMsg dwMsg = new DelegateWorkItemMsg(); 

		dwMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		dwMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		dwMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		dwMsg.setInstanceId(requestElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		dwMsg.setTracingTag(requestElement.getChildText("tracingTag", WORKFLOW_NAMESPACE));
		dwMsg.setEndpointsByElementList(requestElement.getChildren("endpoint", WORKFLOW_NAMESPACE));
		
		System.out.println(dwMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.delegateWorkItem(dwMsg);
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

		return MakeReturnMsg.toElement(status, taskInfoList, DELEGATE_WORKITEM_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
