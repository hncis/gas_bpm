package org.uengine.telnet.beans;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("TelnetCommand")
public class TelnetCommand implements Serializable {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	@XStreamAsAttribute
	private String waitFor;
	
	@XStreamAsAttribute
	private String command;
	
	@XStreamAsAttribute
	private long timeout;

	public String getWaitFor() {
		return waitFor;
	}

	public void setWaitFor(String waitFor) {
		this.waitFor = waitFor;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	

}
