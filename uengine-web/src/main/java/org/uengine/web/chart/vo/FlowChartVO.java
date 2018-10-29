package org.uengine.web.chart.vo;

import org.uengine.kernel.viewer.ViewerOptions;
import org.uengine.web.service.vo.DefaultVO;

public class FlowChartVO extends DefaultVO{
	private String defName;
	private String instanceId;		
	private String defId;	
	private String defVerId;
	private String parentPdver;		
	private String viewType=ViewerOptions.SWIMLANE;			
	private String viewOption=ViewerOptions.HORIZONTAL;		
	private String lastInstanceId;		
	private String initiate;			
	private String viewInstanceId;
	private String chartString;
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
	public String getLastInstanceId() {
		return lastInstanceId;
	}
	public void setLastInstanceId(String lastInstanceId) {
		this.lastInstanceId = lastInstanceId;
	}
	public String getInitiate() {
		return initiate;
	}
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}
	public String getViewInstanceId() {
		return viewInstanceId;
	}
	public void setViewInstanceId(String viewInstanceId) {
		this.viewInstanceId = viewInstanceId;
	}
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public String getChartString() {
		return chartString;
	}
	public void setChartString(String chartString) {
		this.chartString = chartString;
	}		
	
}
