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
import com.defaultcompany.external.model.stdmsg.ProcessStartMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class ProcessStartEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public ProcessStartEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);

		ProcessStartMsg psMsg = new ProcessStartMsg();

		psMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		psMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		psMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		psMsg.setInitiator(requestElement.getChildText("initiator", WORKFLOW_NAMESPACE));
		psMsg.setProcAlias(requestElement.getChildText("procAlias", WORKFLOW_NAMESPACE));
		psMsg.setInstanceName(requestElement.getChildText("instanceName", WORKFLOW_NAMESPACE));
		
		psMsg.setApprovalKey(requestElement.getChildText("approvalKey", WORKFLOW_NAMESPACE));
		psMsg.setComment(requestElement.getChildText("comment", WORKFLOW_NAMESPACE));
		psMsg.setMainParam(requestElement.getChildText("mainParam", WORKFLOW_NAMESPACE));
		psMsg.setSubParam(requestElement.getChildText("subParam", WORKFLOW_NAMESPACE));
		
		psMsg.setFirstTaskCompleted(Boolean.parseBoolean(requestElement.getChildText("firstTaskCompleted", WORKFLOW_NAMESPACE)));
		
		System.out.println(psMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			String newInstanceId = pms.processStart(psMsg);
			pm.applyChanges();
			
			if (StringUtils.hasText(newInstanceId) && StringUtils.hasText(psMsg.getInitiator())) {
				taskInfoList = pms.getTaskInfo(newInstanceId, psMsg.getInitiator());
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

		return MakeReturnMsg.toElement(status, taskInfoList, PROCESS_START_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
