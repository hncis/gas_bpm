package com.defaultcompany.external.model.stdmsg;

public class ProcessStopMsg extends BaseStdMsg {

	private static final long serialVersionUID = 1L;
	
	public final static String INSTANCE_STATUS_STOPPED = "STOPPED";
	public final static String INSTANCE_STATUS_CANCELLED = "CANCELLED";
	public final static String INSTANCE_STATUS_FAULT = "FAULT";
	public final static String INSTANCE_STATUS_COMPLETED = "COMPLETED";
	
	private String instanceId;
	private String instanceStatus;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

}
