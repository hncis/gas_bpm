package org.uengine.web.worklist.vo;

import java.io.Serializable;

import org.uengine.web.chart.vo.FlowChartVO;

public class MyWorkVO extends FlowChartVO {
	
	private String userId;
	private String processName;
	private String processCode;    
	private String defName;
	private String title;                                 
	private String status;                                
	private String info;                                
	private String instanceName;              
	private String startDate;                             
	private String endDate;                               
	private String dueDate;                               
	private String currEp;                              
	private String currStatusNames;     
	private String currStatusCodes;     
	private String currrsNm;                
	private String initEp;                              
	private String initRsNm;                            
	private String instanceId;
	private String rootInstId;
	private String tracingTag;                  
	private String taskId;                   
	private String folderName;
	private int isEmergency;
	private String statusCode;
	private String instanceKeyword;
	private String ext1;                          
	private String ext2;                          
	private String ext3;                          
	private String ext4;                          
	private String ext5;                          
	private String ext6;                          
	private String ext7;                          
	private String ext8;                          
	private String ext9;                          
	private String ext10;
	private int totalCount;
	private int groupCount;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCurrEp() {
		return currEp;
	}
	public void setCurrEp(String currEp) {
		this.currEp = currEp;
	}
	public String getCurrStatusNames() {
		return currStatusNames;
	}
	public void setCurrStatusNames(String currStatusNames) {
		this.currStatusNames = currStatusNames;
	}
	public String getCurrStatusCodes() {
		return currStatusCodes;
	}
	public void setCurrStatusCodes(String currStatusCodes) {
		this.currStatusCodes = currStatusCodes;
	}
	public String getCurrrsNm() {
		return currrsNm;
	}
	public void setCurrrsNm(String currrsNm) {
		this.currrsNm = currrsNm;
	}
	public String getInitEp() {
		return initEp;
	}
	public void setInitEp(String initEp) {
		this.initEp = initEp;
	}
	public String getInitRsNm() {
		return initRsNm;
	}
	public void setInitRsNm(String initRsNm) {
		this.initRsNm = initRsNm;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getRootInstId() {
		return rootInstId;
	}
	public void setRootInstId(String rootInstId) {
		this.rootInstId = rootInstId;
	}
	public String getTracingTag() {
		return tracingTag;
	}
	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	public String getExt6() {
		return ext6;
	}
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	public String getExt7() {
		return ext7;
	}
	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}
	public String getExt8() {
		return ext8;
	}
	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}
	public String getExt9() {
		return ext9;
	}
	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}
	public String getExt10() {
		return ext10;
	}
	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getIsEmergency() {
		return isEmergency;
	}
	public void setIsEmergency(int isEmergency) {
		this.isEmergency = isEmergency;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getInstanceKeyword() {
		return instanceKeyword;
	}
	public void setInstanceKeyword(String instanceKeyword) {
		this.instanceKeyword = instanceKeyword;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	@Override
	public String toString() {
		return "MyWorkVO [userId=" + userId + ", processName=" + processName
				+ ", processCode=" + processCode + ", defName=" + defName
				+ ", title=" + title + ", status=" + status + ", info=" + info
				+ ", instanceName=" + instanceName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", dueDate=" + dueDate + ", currEp="
				+ currEp + ", currStatusNames=" + currStatusNames
				+ ", currStatusCodes=" + currStatusCodes + ", currrsNm="
				+ currrsNm + ", initEp=" + initEp + ", initRsNm=" + initRsNm
				+ ", instanceId=" + instanceId + ", rootInstId=" + rootInstId
				+ ", tracingTag=" + tracingTag + ", taskId=" + taskId
				+ ", folderName=" + folderName + ", isEmergency=" + isEmergency
				+ ", statusCode=" + statusCode + ", instanceKeyword="
				+ instanceKeyword + ", ext1=" + ext1 + ", ext2=" + ext2
				+ ", ext3=" + ext3 + ", ext4=" + ext4 + ", ext5=" + ext5
				+ ", ext6=" + ext6 + ", ext7=" + ext7 + ", ext8=" + ext8
				+ ", ext9=" + ext9 + ", ext10=" + ext10 + ", totalCount="
				+ totalCount + ", groupCount=" + groupCount + "]";
	}
	
	
	
}
