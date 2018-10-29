package org.uengine.telnet;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;

public class TelnetConfigHost implements java.io.Serializable {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	String hostName;
	int port;
	
	TelnetConfigCommand[] telnetConfigCommand;
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.setFieldOrder( new String[] {"HostName", "Port", "TelnetConfigCommand"} );
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public TelnetConfigCommand[] getTelnetConfigCommand() {
		return telnetConfigCommand;
	}

	public void setTelnetConfigCommand(TelnetConfigCommand[] telnetConfigCommand) {
		this.telnetConfigCommand = telnetConfigCommand;
	}
	/*
	String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	*/

}
