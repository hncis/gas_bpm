package org.uengine.telnet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.SimpleOptionHandler;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.uengine.util.UEngineUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class TelnetNormalModule implements Runnable, TelnetNotificationHandler {

	String remoteip;
		public String getRemoteip() {
			return remoteip;
		}
		public void setRemoteip(String remoteip) {
			this.remoteip = remoteip;
		}

	int remoteport;
		public int getRemoteport() {
			return remoteport;
		}
		public void setRemoteport(int remoteport) {
			this.remoteport = remoteport;
		}
		
	long[] timeout;
		public long[] getTimeout() {
			return timeout;
		}
		public void setTimeout(long[] timeout) {
			this.timeout = timeout;
		}
		
	int sessionTimeout;
		public int getSessionTimeout() {
			return sessionTimeout;
		}
		public void setSessionTimeout(int sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
		}

	String resultString = "";
		public String getResultString() {
			return resultString;
		}
		public void setResultString(String resultString) {
			this.resultString = resultString;
		}
	
	String totalResultString = "";
		public String getTotalResultString() {
			return totalResultString;
		}
		public void setTotalResultString(String totalResultString) {
			this.totalResultString = totalResultString;
		}

	ArrayList resultStringList = new ArrayList();
		public ArrayList getResultStringList() {
			return resultStringList;
		}

	String[] waitFor;
		public String[] getWaitFor() {
			return waitFor;
		}
		public void setWaitFor(String[] waitFor) {
			this.waitFor = waitFor;
		}
	
	String[] command;
		public String[] getCommand() {
			return command;
		}
		public void setCommand(String[] command) {
			this.command = command;
		}
		
	ArrayList errorLog = new ArrayList();
		public ArrayList getErrorLog() {
			return errorLog;
		}
		
	boolean isTimeoutExit;
		public boolean isTimeoutExit() {
			return isTimeoutExit;
		}
		public void setTimeoutExit(boolean isTimeoutExit) {
			this.isTimeoutExit = isTimeoutExit;
		}
		
//	boolean isEcho;
//		public boolean isEcho() {
//			return isEcho;
//		}
//		public void setEcho(boolean isEcho) {
//			this.isEcho = isEcho;
//		}

	static TelnetClient tc;
	
	public void setHost(String remoteip, int remoteport) {
		this.remoteip = remoteip;
		this.remoteport = remoteport;
	}
	
	String serverType;
		public String getServerType() {
			return serverType;
		}
		public void setServerType(String serverType) {
			this.serverType = serverType;
		}

	public Hashtable getOptionHandler(String serverType) {
		
		String serverTypeLowerCase = null;
		
		if (UEngineUtil.isNotEmpty(serverType))
			serverTypeLowerCase = serverType.toLowerCase();
		
		Hashtable optionHandlers = new Hashtable();
		
		if ("option1".equals(serverTypeLowerCase) || !UEngineUtil.isNotEmpty(serverTypeLowerCase)) {
			optionHandlers.put("ttopt", new TerminalTypeOptionHandler("VT100", false, false, true, false));
			optionHandlers.put("echoopt", new EchoOptionHandler(true, false, true, true));
			optionHandlers.put("gaopt", new SuppressGAOptionHandler(true, true, true, true));
		} else if ("option2".equals(serverTypeLowerCase)) {
			optionHandlers.put("ttopt", new TerminalTypeOptionHandler("VT100", false, false, true, false));
			optionHandlers.put("echoopt", new EchoOptionHandler(true, false, true, false));
			optionHandlers.put("gaopt", new SuppressGAOptionHandler(true, true, true, true));
		} else if ("option3".equals(serverTypeLowerCase)) {
			optionHandlers.put("ttopt", new TerminalTypeOptionHandler("VT100"));
			optionHandlers.put("echoopt", new EchoOptionHandler());
			optionHandlers.put("gaopt", new SuppressGAOptionHandler());
		}

		return optionHandlers;
	}
	
	public void start() {

		tc = new TelnetClient();
		
		Hashtable optionHandlers = getOptionHandler(getServerType()); 

		TerminalTypeOptionHandler ttopt = (TerminalTypeOptionHandler) optionHandlers.get("ttopt");
		EchoOptionHandler echoopt = (EchoOptionHandler) optionHandlers.get("echoopt");
		SuppressGAOptionHandler gaopt = (SuppressGAOptionHandler) optionHandlers.get("gaopt");
		
		try {
			tc.setDefaultTimeout(getSessionTimeout());
			tc.addOptionHandler(ttopt);
			tc.addOptionHandler(echoopt);
			tc.addOptionHandler(gaopt);
		} catch (InvalidTelnetOptionException e) {
			System.err.println("Error registering option handlers: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OutputStream outstr = null;
		ByteArrayInputStream bais = null;

		boolean end_loop = false;
		try {
			tc.connect(getRemoteip(), getRemoteport());

			Thread readerThread = new Thread(this);
			tc.registerNotifHandler(this);

			readerThread.start();
			outstr = tc.getOutputStream();

			byte[] buff = new byte[1024];
			int ret_read = 0;
			
			int i = 0;
			int timeout = 0;
			
			do {
				
				if (i != 0 && i != 1) {
					resultStringList.add(getResultString());
				}
				setResultString("");
				
				if (i == getCommand().length) {
					break;
				}
				else {
					
					timeout = 0;

					while (true) {
						if (timeout == (getTimeout()[i] / 1000)) {
							break;
						}

						Thread.sleep(500);

						if (getResultString().indexOf(getWaitFor()[i]) != -1) {
							break;
						} else {
							timeout++;
						}
					}

					if (timeout == (getTimeout()[i] / 1000)) {
						setTimeoutExit(true);
						break;
					}

					Thread.sleep(1000);
					
					bais = new ByteArrayInputStream((getCommand()[i] + "\r\n").getBytes());
					
					try {
						
						ret_read = bais.read(buff);
//						ret_read = System.in.read(buff);

						if (ret_read > 0) {
							if ((new String(buff, 0, ret_read)).startsWith("AYT")) {
								try {
									System.out.println("Sending AYT");

									System.out.println("AYT response:" + tc.sendAYT(getTimeout()[i]));
//									System.out.println("AYT response:" + tc.sendAYT(5000));
								} catch (Exception e) {
									System.err.println("Exception waiting AYT response: " + e.getMessage());
								}
							} else if ((new String(buff, 0, ret_read)).startsWith("OPT")) {
								System.out.println("Status of options:");
								for (int ii = 0; ii < 25; ii++)
									System.out.println("Local Option " + ii + ":" + tc.getLocalOptionState(ii) + " Remote Option " + ii + ":" + tc.getRemoteOptionState(ii));
							} else if ((new String(buff, 0, ret_read)).startsWith("REGISTER")) {
								StringTokenizer st = new StringTokenizer(new String(buff));
								try {
									st.nextToken();
									int opcode = (new Integer(st.nextToken())).intValue();
									boolean initlocal = (new Boolean(st.nextToken())).booleanValue();
									boolean initremote = (new Boolean(st.nextToken())).booleanValue();
									boolean acceptlocal = (new Boolean(st.nextToken())).booleanValue();
									boolean acceptremote = (new Boolean(st.nextToken())).booleanValue();
									SimpleOptionHandler opthand = new SimpleOptionHandler(opcode, initlocal, initremote, acceptlocal, acceptremote);
									tc.addOptionHandler(opthand);
								} catch (Exception e) {
									if (e instanceof InvalidTelnetOptionException) {
										System.err.println("Error registering option: " + e.getMessage());
									} else {
										System.err.println("Invalid REGISTER command.");
										System.err.println("Use REGISTER optcode initlocal initremote acceptlocal acceptremote");
										System.err.println("(optcode is an integer.)");
										System.err.println("(initlocal, initremote, acceptlocal, acceptremote are boolean)");
									}
								}
							} else if ((new String(buff, 0, ret_read)).startsWith("UNREGISTER")) {
								StringTokenizer st = new StringTokenizer(new String(buff));
								try {
									st.nextToken();
									int opcode = (new Integer(st.nextToken())).intValue();
									tc.deleteOptionHandler(opcode);
								} catch (Exception e) {
									if (e instanceof InvalidTelnetOptionException) {
										System.err.println("Error unregistering option: " + e.getMessage());
									} else {
										System.err.println("Invalid UNREGISTER command.");
										System.err.println("Use UNREGISTER optcode");
										System.err.println("(optcode is an integer)");
									}
								}
							} else if ((new String(buff, 0, ret_read)).startsWith("SPY")) {
//								try {
//									tc.registerSpyStream(fout);
//								} catch (Exception e) {
//									System.err.println("Error registering the spy");
//								}
							} else if ((new String(buff, 0, ret_read)).startsWith("UNSPY")) {
								tc.stopSpyStream();
							} else {
								try {
									outstr.write(buff, 0, ret_read);
									outstr.flush();
								} catch (Exception e) {
									end_loop = true;
								}
							}
						}
					} catch (Exception e) {
						System.err.println("Exception while reading keyboard:" + e.getMessage());
						end_loop = true;
					}
					i++;
					bais.close();
				}
			} while ((ret_read > 0) && (end_loop == false));

			try {
				if (tc.isConnected())
					tc.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				errorLog.add(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorLog.add(e.getMessage());
		} finally {
//			if (outstr != null)
//				try {
//					outstr.close();
//				} catch (IOException e) {
//				}
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
				}
		}
	}

	public void receivedNegotiation(int negotiation_code, int option_code) {
		String command = null;
		if (negotiation_code == TelnetNotificationHandler.RECEIVED_DO) {
			command = "DO";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_DONT) {
			command = "DONT";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WILL) {
			command = "WILL";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WONT) {
			command = "WONT";
		}
		
		System.out.println("Received " + command + " for option code " + option_code);
	}

	public void run() {
		InputStream instr = tc.getInputStream();

		try {
			byte[] buff = new byte[2048];
			int ret_read = 0;

			do {
//				if(instr.available() ==0){
//					break;
//				}
				ret_read = instr.read(buff);
				if (ret_read > 0) {
//					String result = new String(buff, 0, ret_read);
					String result = new String(buff, 0, ret_read, "EUC-KR");
					
					setResultString(getResultString() + result);
					setTotalResultString(getTotalResultString() + result);
					
					System.out.print(result);
				}
			} while (ret_read >= 0);
			
		} catch (Exception e) {
			System.err.println("Exception while reading socket:" + e.getMessage());
			errorLog.add(e.getMessage());
		}

		try {
			tc.disconnect();
		} catch (Exception e) {
			System.err.println("Exception while closing telnet:" + e.getMessage());
			errorLog.add(e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		String[] waitFor = { "ogin:", "assword:", "$", "$", "assword:", "fepmon][/FEP]#", "fepmon][/FEP]#", "/EAI>","intuser] :", "$" };
		String[] command = { "d3176634", "kimsk790", "cd mr01e", "su - fepmon", "bestez01", "psg.sh", "exit", "exit", "exit", "exit" };
		long[] timeout = new long[waitFor.length];
		for (int i = 0; i < timeout.length; i++) {
			timeout[i] = 500000;
		}
		
//		String[] waitFor = { "ogin:", "assword:", "$", "intuser] :", "assword:", "/EAI>", "/EAI>", "/EAI>", "intuser] :", "$" };
//		String[] command = { "d3176634", "kimsk790", "cd ad011h", "su - eaiusr", "eaiusr", "cd /EAI", "Eai_Check_DB DT011H_EAI", "exit", "exit", "exit" };

//		String[] waitFor = { "ogin:", "assword:", "$", "] : ", "] : ", "$" };
//		String[] command = { "d3176634", "kimsk790", "cd dt901e", "ls -al", "exit", "exit" };
		
//		String[] waitFor = { "ogin:", "assword:", "allbegray@allbegray-desktop:~$", "/workspace$", "/workspace$ " };
//		String[] command = { "allbegray", "02dydred", "cd workspace", "ls -al", "exit" };
		
		TelnetNormalModule tc = new TelnetNormalModule();
//		tc.setHost("localhost", 23);
		tc.setHost("10.100.13.50", 23);
		tc.setTimeout(timeout);
		tc.setSessionTimeout(100 * 1000);
		tc.setWaitFor(waitFor);
		tc.setCommand(command);
		tc.setServerType("default");
//		tc.setEcho(true);
		tc.start();
		
//		System.out.println(tc.getResultString());
		
		for (int i=0; i<tc.getResultStringList().size(); i++) {
//			System.out.println("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ\t"+i);
//			String result = (String)tc.getResultStringList().get(i);
//			if (result.lastIndexOf("\r\n") != -1) {
//				System.out.println(result.substring(0, result.lastIndexOf("\r\n")));
//			}
		}
		
		System.out.println("exit");
		System.out.println(tc.isTimeoutExit());
		System.out.println(tc.getErrorLog().size() + "\t" + tc.getErrorLog());
	}

}
