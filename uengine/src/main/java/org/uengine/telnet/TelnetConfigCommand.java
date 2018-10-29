package org.uengine.telnet;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.kernel.ProcessVariable;

public class TelnetConfigCommand implements java.io.Serializable {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
		
		fd = type.getFieldDescriptor("ServerType");
		fd.setInputter(
				new RadioInput(
						new String[] { "option1", "option2", "option3" },
						new Object[] { new String("option1"), new String("option2"), new String("option3") }
				)
		);	
		
		type.setFieldOrder( new String[] {"Description", "ServerType", "SessionTimeout", "TotalResult", "TelnetCommands"} );
	}
	
	String description;
	String serverType;
	int sessionTimeout;
	ProcessVariable totalResult;
	TelnetCommand[] telnetCommands;
	
	// 대우증권 기존에 만들어 놓은 프로세스들 때문에 isEcho 옵션을 놔둠. 차후엔진 반영시에는 삭제
//	boolean isEcho;
//
//	public boolean isEcho() {
//		return isEcho;
//	}
//
//	public void setEcho(boolean isEcho) {
//		this.isEcho = isEcho;
//	}
	// 여기까지

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TelnetCommand[] getTelnetCommands() {
		return telnetCommands;
	}

	public void setTelnetCommands(TelnetCommand[] telnetCommands) {
		this.telnetCommands = telnetCommands;
	}

	public ProcessVariable getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(ProcessVariable totalResult) {
		this.totalResult = totalResult;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	
	

	
	



	/*
	 * String command; ProcessVariable resultPV; String regex; int
	 * channelTimeOut; String parameter;
	 * 
	 * public String getCommand() { return command; }
	 * 
	 * public void setCommand(String command) { this.command = command; }
	 * 
	 * public ProcessVariable getResultPV() { return resultPV; }
	 * 
	 * public void setResultPV(ProcessVariable resultPV) { this.resultPV =
	 * resultPV; }
	 * 
	 * public String getRegex() { return regex; }
	 * 
	 * public void setRegex(String regex) { this.regex = regex; }
	 * 
	 * public int getChannelTimeOut() { return channelTimeOut; }
	 * 
	 * public void setChannelTimeOut(int channelTimeOut) { this.channelTimeOut =
	 * channelTimeOut; }
	 * 
	 * public String getParameter() { return parameter; }
	 * 
	 * public void setParameter(String parameter) { this.parameter = parameter;
	 * }
	 */
}
