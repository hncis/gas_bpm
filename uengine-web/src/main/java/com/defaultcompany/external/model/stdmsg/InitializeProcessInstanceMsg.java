package com.defaultcompany.external.model.stdmsg;

public class InitializeProcessInstanceMsg extends BaseStdMsg {

	private static final long serialVersionUID = 1L;
	
	private String instanceId;
	private String endpoint;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
