package org.uengine.bpmutil.vo;

import java.io.Serializable;

public class CurrentActivityVO implements Serializable {

	String activityCode; //2017-01-10 chonk
	String activityStatus; //2017-01-10 chonk

	//2017-01-10 chonk
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	//2017-01-10 chonk
	public String getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
}
