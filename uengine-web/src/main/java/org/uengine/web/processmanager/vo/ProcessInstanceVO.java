package org.uengine.web.processmanager.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.uengine.web.chart.vo.FlowChartVO;

public class ProcessInstanceVO extends FlowChartVO{
	
	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private String comCode;
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
	private String instId; 
	private String defVerId; 
	private String defId; 
	private String defName; 
	private String defPath; 
	private Date defModDate; 
	private String strDefModDate; 
	private Date startedDate; 
	private String strStartedDate; 
	private Date finishedDate; 
	private String strFinishedDate; 
	private Date dueDate;
	private String strDueDate;
	private String status; 
	private String info; 
	private String name; 
	private String isDeleted; 
	private String isAdhoc; 
	private String isArchive; 
	private String isSubprocess; 
	private String isEventhandler; 
	private String rootInstid; 
	private String mainInstid; 
	private String mainDefVerId; 
	private String mainActTrcTag; 
	private String mainExecScope; 
	private String absTrcPath; 
	private String dontReturn; 
	private Date modDate; 
	private String strModDate; 
	private String ext1; 
	private String initEp; 
	private String initRsNm; 
	private String currEp; 
	private String currRsNm; 
	private String initTrcTag; 
	private String initTaskId; 
	private String currStatusCodes; 
	private String currStatusNames;
	private int isEmergency;
	private String folderName;
	private String collectActivityName;
	private String searchResult;

	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getDefVerId() {
		return defVerId;
	}
	public void setDefVerId(String defVerId) {
		this.defVerId = defVerId;
	}
	public String getDefId() {
		return defId;
	}
	public void setDefId(String defId) {
		this.defId = defId;
	}
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public String getDefPath() {
		return defPath;
	}
	public void setDefPath(String defPath) {
		this.defPath = defPath;
	}
	public Date getDefModDate() {
		return defModDate;
	}
	public void setDefModDate(Date defModDate) {
		this.defModDate = defModDate;
	}
	public String getStrDefModDate() {
		return strDefModDate;
	}
	public void setStrDefModDate(String strDefModDate) {
		this.strDefModDate = strDefModDate;
	}
	public Date getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}
	public String getStrStartedDate() {
		return startedDate == null?null:sdf.format(startedDate);
	}
	public void setStrStartedDate(String strStartedDate) {
		this.strStartedDate = strStartedDate;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public String getStrFinishedDate() {
		return finishedDate == null?null:sdf.format(finishedDate);
	}
	public void setStrFinishedDate(String strFinishedDate) {
		this.strFinishedDate = strFinishedDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getStrDueDate() {
		return dueDate == null?null:sdf.format(dueDate);
	}
	public void setStrDueDate(String strDueDate) {
		this.strDueDate = strDueDate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getIsAdhoc() {
		return isAdhoc;
	}
	public void setIsAdhoc(String isAdhoc) {
		this.isAdhoc = isAdhoc;
	}
	public String getIsArchive() {
		return isArchive;
	}
	public void setIsArchive(String isArchive) {
		this.isArchive = isArchive;
	}
	public String getIsSubprocess() {
		return isSubprocess;
	}
	public void setIsSubprocess(String isSubprocess) {
		this.isSubprocess = isSubprocess;
	}
	public String getIsEventhandler() {
		return isEventhandler;
	}
	public void setIsEventhandler(String isEventhandler) {
		this.isEventhandler = isEventhandler;
	}
	public String getRootInstid() {
		return rootInstid;
	}
	public void setRootInstid(String rootInstid) {
		this.rootInstid = rootInstid;
	}
	public String getMainInstid() {
		return mainInstid;
	}
	public void setMainInstid(String mainInstid) {
		this.mainInstid = mainInstid;
	}
	public String getMainDefVerId() {
		return mainDefVerId;
	}
	public void setMainDefVerId(String mainDefVerId) {
		this.mainDefVerId = mainDefVerId;
	}
	public String getMainActTrcTag() {
		return mainActTrcTag;
	}
	public void setMainActTrcTag(String mainActTrcTag) {
		this.mainActTrcTag = mainActTrcTag;
	}
	public String getMainExecScope() {
		return mainExecScope;
	}
	public void setMainExecScope(String mainExecScope) {
		this.mainExecScope = mainExecScope;
	}
	public String getAbsTrcPath() {
		return absTrcPath;
	}
	public void setAbsTrcPath(String absTrcPath) {
		this.absTrcPath = absTrcPath;
	}
	public String getDontReturn() {
		return dontReturn;
	}
	public void setDontReturn(String dontReturn) {
		this.dontReturn = dontReturn;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getStrModDate() {
		return modDate == null?null:sdf.format(modDate);
	}
	public void setStrModDate(String strModDate) {
		this.strModDate = strModDate;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
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
	public String getCurrEp() {
		return currEp;
	}
	public void setCurrEp(String currEp) {
		this.currEp = currEp;
	}
	public String getCurrRsNm() {
		return currRsNm;
	}
	public void setCurrRsNm(String currRsNm) {
		this.currRsNm = currRsNm;
	}
	public String getInitTrcTag() {
		return initTrcTag;
	}
	public void setInitTrcTag(String initTrcTag) {
		this.initTrcTag = initTrcTag;
	}
	public String getInitTaskId() {
		return initTaskId;
	}
	public void setInitTaskId(String initTaskId) {
		this.initTaskId = initTaskId;
	}
	public String getCurrStatusCodes() {
		return currStatusCodes;
	}
	public void setCurrStatusCodes(String currStatusCodes) {
		this.currStatusCodes = currStatusCodes;
	}
	public String getCurrStatusNames() {
		return currStatusNames;
	}
	public void setCurrStatusNames(String currStatusNames) {
		this.currStatusNames = currStatusNames;
	}
	public int getIsEmergency() {
		return isEmergency;
	}
	public void setIsEmergency(int isEmergency) {
		this.isEmergency = isEmergency;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getCollectActivityName() {
		return collectActivityName;
	}
	public void setCollectActivityName(String collectActivityName) {
		this.collectActivityName = collectActivityName;
	}
	public String getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}
	
	
}
