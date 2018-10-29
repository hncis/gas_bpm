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
import com.defaultcompany.external.model.stdmsg.ActivityCompleteMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class ActivityCompleteEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public ActivityCompleteEndpoint() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		Element commonElement = requestElement.getChild("common", WORKFLOW_NAMESPACE);
		Element activityCompleteElement = requestElement.getChild("activityComplete", WORKFLOW_NAMESPACE);

		ActivityCompleteMsg acMsg = new ActivityCompleteMsg();
		acMsg.setExternalKey(commonElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		acMsg.setProcessVariablesByElementList(commonElement.getChildren("processVariable", WORKFLOW_NAMESPACE));
		acMsg.setRolesByElementList(commonElement.getChildren("role", WORKFLOW_NAMESPACE));
		
		acMsg.setEndpoint(activityCompleteElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		acMsg.setInstanceId(activityCompleteElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		acMsg.setTaskId(activityCompleteElement.getChildText("taskId", WORKFLOW_NAMESPACE));
		acMsg.setTracingTag(activityCompleteElement.getChildText("tracingTag", WORKFLOW_NAMESPACE));
		
		System.out.println(acMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManager();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			pms.activityComplete(acMsg);
			pm.applyChanges();
			
			if (StringUtils.hasText(acMsg.getInstanceId()) && StringUtils.hasText(acMsg.getEndpoint())) {
				taskInfoList = pms.getTaskInfo(acMsg.getInstanceId(), acMsg.getEndpoint());
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

		return MakeReturnMsg.toElement(status, taskInfoList, ACTIVITY_COMPLETE_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
