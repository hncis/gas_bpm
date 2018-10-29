package org.uengine.telnet;

import org.metaworks.Type;
import org.uengine.kernel.ProcessVariable;

public class TelnetCommand implements java.io.Serializable {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.setFieldOrder( new String[] {
				"WaitFor", "Command", "Timeout", "ResultCommand", "RegularExpression",
				"Visible", "TelnetParameter"
		} );
	}
	
	String waitFor;
	String command;
	long timeout;
	ProcessVariable resultCommand;
	String regularExpression;
	boolean isVisible;
	TelnetParameter[] telnetParameter;

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

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

	public ProcessVariable getResultCommand() {
		return resultCommand;
	}

	public void setResultCommand(ProcessVariable resultCommand) {
		this.resultCommand = resultCommand;
	}

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	public TelnetParameter[] getTelnetParameter() {
		return telnetParameter;
	}

	public void setTelnetParameter(TelnetParameter[] telnetParameter) {
		this.telnetParameter = telnetParameter;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
}