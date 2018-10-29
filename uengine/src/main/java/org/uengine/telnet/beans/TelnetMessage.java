package org.uengine.telnet.beans;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("TelnetMessage")
public class TelnetMessage implements Serializable {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	private String hostName;
	private int port;

	private int sessionTimeout;
//	private String isEcho;
	private String serverType;
	
	@XStreamImplicit(itemFieldName="telnetCommand")
	private List<TelnetCommand> telnetCommand;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	/*
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	*/

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

//	public String getIsEcho() {
//		return isEcho;
//	}
//
//	public void setIsEcho(String isEcho) {
//		this.isEcho = isEcho;
//	}

	public List<TelnetCommand> getTelnetCommand() {
		return telnetCommand;
	}

	public void setTelnetCommand(List<TelnetCommand> telnetCommand) {
		this.telnetCommand = telnetCommand;
	}

	private String eventParentDivName;
	private String eventDivName;
	private String endpoint;
	private String instanceId;
	private String tracingTag;
	private String processDefinition;

	public String getEventParentDivName() {
		return eventParentDivName;
	}

	public void setEventParentDivName(String eventParentDivName) {
		this.eventParentDivName = eventParentDivName;
	}

	public String getEventDivName() {
		return eventDivName;
	}

	public void setEventDivName(String eventDivName) {
		this.eventDivName = eventDivName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTracingTag() {
		return tracingTag;
	}

	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}

	public String getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(String processDefinition) {
		this.processDefinition = processDefinition;
	}
	
	
}
