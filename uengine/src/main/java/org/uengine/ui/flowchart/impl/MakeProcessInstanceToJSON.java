package org.uengine.ui.flowchart.impl;

import java.util.Calendar;

import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processmanager.ProcessManagerRemote;

import org.uengine.ui.flowchart.model.ProcessDefinitionInfo;

public class MakeProcessInstanceToJSON extends AbstractMakeProcessDefinitionToJSON {
	
	protected ProcessInstance instance;
	
	private ProcessManagerRemote pm;
		public void setPm(ProcessManagerRemote pm) {
			this.pm = pm;
		}	
		
	public MakeProcessInstanceToJSON(ProcessInstance instance) {
		this.instance = instance;
	}
	
	@Override
	protected ProcessDefinitionInfo prefixLogic(Activity act) {
		
		ProcessDefinitionInfo pdInfo = new ProcessDefinitionInfo();
		
		if (act instanceof HumanActivity) {
			try {
				HumanActivity humanAct = (HumanActivity) act;
				
				String[] taskId = humanAct.getTaskIds(this.instance);
				if (taskId != null) {
					pdInfo.setTaskId(humanAct.getTaskIds(this.instance)[0]);
				}
				
				String status = act.getStatus(this.instance);
				if (!Activity.STATUS_READY.equals(status)) {
					Calendar cal = null;
					cal = humanAct.getStartedTime(this.instance);
					if (cal != null) {
						pdInfo.setStartedDate(cal.getTime());
						cal = null;
					}
					
					cal = humanAct.getDueDate(this.instance);
					if (cal != null) {
						pdInfo.setDueDate(cal.getTime());
						cal = null;
					}
					
					cal = humanAct.getEndTime(this.instance);
					if (cal != null) {
						pdInfo.setFinishedDate(cal.getTime());
						cal = null;
					}
				}
				
				String endpoint = null;
				Role[] roles = this.pm.getProcessDefinitionRemoteWithInstanceId(this.instance.getInstanceId()).getRoles();
				if (roles != null) {
					for (Role role : roles) {
						if (role.getName().equals(humanAct.getRole().getName())) {
							endpoint = this.pm.getRoleMappingObject(this.instance.getInstanceId(), role.getName()).toString();
							break;
						}
					}
				}
				pdInfo.setEndpoint(endpoint);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		try {
			pdInfo.setStatus(act.getStatus(instance));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		if (act instanceof SubProcessActivity) {
			pdInfo.setSubInstId(((SubProcessActivity)act).getInstanceId());
		}
		
		return pdInfo;
	}
	
	@Override
	protected ProcessDefinitionInfo suffixLogic(Activity act, ProcessDefinitionInfo pdInfo) {
		return pdInfo;
	}

}

