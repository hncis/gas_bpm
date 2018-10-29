package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.InitializeProcessInstanceMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class InitializeProcessInstanceEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public InitializeProcessInstanceEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);

		InitializeProcessInstanceMsg ipiMsg = new InitializeProcessInstanceMsg();

		ipiMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		ipiMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		ipiMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		ipiMsg.setInstanceId(requestElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		ipiMsg.setEndpoint(requestElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		
		System.out.println(ipiMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.initializeProcessInstance(ipiMsg);
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

		return MakeReturnMsg.toElement(status, taskInfoList, INITIALIZE_PROCESS_INSTANCE_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
