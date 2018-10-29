package org.uengine.web.process.vo;

import java.io.Serializable;
import java.util.List;

import org.uengine.kernel.ProcessInstance;
import org.uengine.web.service.vo.CurrentActivityVO;

public class ProcessVO implements Serializable {
	private String defName;
	private String instanceId;
	private String defId;
	private String defVerId;
	private String parentPdver;
	private String viewType;
	private String viewOption;
	private String initiate;
	private String userId;
	private ProcessInstance processInstance;
	private List<CurrentActivityVO> currentActivities; 
	private String message;
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getDefId() {
		return defId;
	}
	public void setDefId(String defId) {
		this.defId = defId;
	}
	public String getDefVerId() {
		return defVerId;
	}
	public void setDefVerId(String defVerId) {
		this.defVerId = defVerId;
	}
	public String getParentPdver() {
		return parentPdver;
	}
	public void setParentPdver(String parentPdver) {
		this.parentPdver = parentPdver;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getViewOption() {
		return viewOption;
	}
	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}
	public String getInitiate() {
		return initiate;
	}
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
	public List<CurrentActivityVO> getCurrentActivities() {
		return currentActivities;
	}
	public void setCurrentActivities(List<CurrentActivityVO> currentActivities) {
		this.currentActivities = currentActivities;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ProcessVO [defName=" + defName + ", instanceId=" + instanceId
				+ ", defId=" + defId + ", defVerId=" + defVerId
				+ ", parentPdver=" + parentPdver + ", viewType=" + viewType
				+ ", viewOption=" + viewOption + ", initiate=" + initiate
				+ ", userId=" + userId + ", processInstance=" + processInstance
				+ ", currentActivities=" + currentActivities + ", message="
				+ message + "]";
	}
	
}
