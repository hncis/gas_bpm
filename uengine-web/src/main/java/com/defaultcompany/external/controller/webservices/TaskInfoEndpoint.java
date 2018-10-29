package com.defaultcompany.external.controller.webservices;

import java.util.List;

import javax.ejb.RemoveException;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

import com.defaultcompany.external.controller.webservices.support.MakeReturnMsg;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.TaskInfoMsg;
import com.defaultcompany.external.service.ProcessManagerService;

public class TaskInfoEndpoint extends AbstractJDomPayloadEndpoint implements WebServiceConstants {

	public TaskInfoEndpoint() {
	}

	@Override
	protected Element invokeInternal(Element requestElement) throws Exception {
		TaskInfoMsg taskInfoMsg = new TaskInfoMsg();
		taskInfoMsg.setExternalKey(requestElement.getChildText("externalKey", WORKFLOW_NAMESPACE));
		taskInfoMsg.setInstanceId(requestElement.getChildText("instanceId", WORKFLOW_NAMESPACE));
		taskInfoMsg.setEndpoint(requestElement.getChildText("endpoint", WORKFLOW_NAMESPACE));
		
		System.out.println(taskInfoMsg);
		
		String status = null;
		List<TaskInfo> taskInfoList = null;
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		try {
			pm = pmfb.getProcessManagerForReadOnly();
			
			ProcessManagerService pms = new ProcessManagerService(pm, null);
			taskInfoList = pms.getTaskInfo(taskInfoMsg);
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

		return MakeReturnMsg.toElement(status, taskInfoList, TASK_INFO_RESPONSE, WORKFLOW_NAMESPACE);
		
	}
}
