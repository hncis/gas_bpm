package org.uengine.telnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.metaworks.inputter.booleanInput;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.telnet.beans.TelnetCommand;
import org.uengine.telnet.beans.TelnetMessage;
import org.uengine.util.UEngineUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TelnetNormalServlet extends HttpServlet {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		String TEMP_DIRECTORY = GlobalContext.getPropertyString(
				"filesystem.path",
				"." + File.separatorChar + "uengine" + File.separatorChar + "fileSystem" + File.separatorChar
			);
		TEMP_DIRECTORY = TEMP_DIRECTORY + "telnet" + File.separatorChar;
		File f = new File(TEMP_DIRECTORY);
		if (!f.exists()) {
			f.mkdirs();
		}
		
		StringBuffer sb = new StringBuffer();
		String line = null;
		TelnetMessage telnetMessage = null;

		BufferedReader reader = arg0.getReader();
		while ((line = reader.readLine()) != null)
			sb.append(line);
		
		String decodeMessage = URLDecoder.decode(sb.toString(), "UTF-8");

		XStream xStream = new XStream(new DomDriver());
		xStream.alias("TelnetMessage", TelnetMessage.class);
		xStream.alias("TelnetCommand", TelnetCommand.class);
		telnetMessage = (TelnetMessage) xStream.fromXML(decodeMessage);
		
		String instanceId = telnetMessage.getInstanceId();
		String eventParentDivName = telnetMessage.getEventParentDivName();
		String eventDivName = telnetMessage.getEventDivName();
		
		if (!UEngineUtil.isNotEmpty(instanceId)) {
			ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
			ProcessManagerRemote pm = null;

			InitialContext context = null;
			UserTransaction tx = null;
			try {
				context = new InitialContext();
				tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);
			} catch (NamingException e2) {
				e2.printStackTrace();
			}

			try {
				pm = processManagerFactory.getProcessManager();

				if (tx != null)
					tx.begin();

				String processDefinition = telnetMessage.getProcessDefinition();
				String endpoint = telnetMessage.getEndpoint();
				
				RoleMapping loggedRoleMapping = RoleMapping.create();
				loggedRoleMapping.setEndpoint(endpoint);

//				ActivityReference initiatorHumanActivityReference = pm.getInitiatorHumanActivityReference(processDefinition);
//				String initiatorDefVerId = initiatorHumanActivityReference.getActivity().getProcessDefinition().getId();
				String fantomInstanceId = pm.initialize(processDefinition, null, loggedRoleMapping);
				
//				ProcessInstance piRemote = pm.getProcessInstance(fantomInstanceId);
				pm.executeProcess(fantomInstanceId);
				
				pm.applyChanges();
				
				instanceId = fantomInstanceId;

				if (tx != null && tx.getStatus() != Status.STATUS_NO_TRANSACTION)
					tx.commit();

			} catch (Exception e) {
				try {
					pm.cancelChanges();
				} catch (Exception ex) {
				}

				try {
					if (tx != null && tx.getStatus() != Status.STATUS_NO_TRANSACTION)
						tx.rollback();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (SystemException e1) {
					e1.printStackTrace();
				}

			} finally {
				try {
					pm.remove();
				} catch (Exception e) {
				}
			}
		}

		if (telnetMessage != null) {
			TelnetNormalModule tc = new TelnetNormalModule();
			tc.setHost(telnetMessage.getHostName(), telnetMessage.getPort());
			tc.setSessionTimeout(telnetMessage.getSessionTimeout());

			if (telnetMessage.getTelnetCommand() != null) {
				String[] waitFor = new String[telnetMessage.getTelnetCommand().size()];
				String[] command = new String[telnetMessage.getTelnetCommand().size()];
				long[] timeout = new long[telnetMessage.getTelnetCommand().size()+1];
				timeout[0] = 5000;
				
				String serverType = telnetMessage.getServerType();
				for (int i = 0; i < telnetMessage.getTelnetCommand().size(); i++) {
					TelnetCommand telnetCommand = telnetMessage.getTelnetCommand().get(i);
					waitFor[i] = telnetCommand.getWaitFor();
					command[i] = telnetCommand.getCommand();
					timeout[i+1] = telnetCommand.getTimeout();
				}
				
				
//				String echo = telnetMessage.getIsEcho();
//				boolean isEcho = false;
//				if ("true".equals(echo) || "".equals(echo) || echo == null) {
//					isEcho = true;
//				}
	
				tc.setWaitFor(waitFor);
				tc.setCommand(command);
				tc.setTimeout(timeout);
//				tc.setEcho(isEcho);
				tc.setServerType(serverType);
				tc.start();
	
				String resultStr = tc.getTotalResultString();
	
				
				
				
				
				PrintWriter out = arg1.getWriter();
				arg1.setContentType("text/xml");
				arg1.setHeader("Cache-Control", "no-cache");
				
//				Test 용 코드
//				out.println("<?xml version='1.0' encoding='UTF-8' ?>");
//				out.println("<TelnetResponseMessage>");
//				out.println("<error>none</error>");
//				out.println("<totalResult>xxxx"+URLEncoder.encode("한글 + 토탈 결과 !@#$%^&*()_+|-=\\`~,./".replace(" ", "%20"), "UTF-8")+"</totalResult>");
//				BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instanceId + "."
//						+ telnetMessage.getTracingTag() + "." + eventParentDivName + "." + eventDivName + ".totalResult.log"));
//				bw.write("한글 + 토탈 결과 !@#$%^&*()_+|-=\\`~,./");
//				bw.close();
//				out.println("<instanceId>" + instanceId + "</instanceId>");
//				out.println("<results>");
//				for (int i = 0; i < waitFor.length; i++) {
//					out.println("<result>yyyy" + (i + 1) +URLEncoder.encode(" abcdefghijklmnopqrstuvwxyz결과 !@#$%^&*()_+|-=\\`~,./".replace(" ", "%20"), "UTF-8")+ "</result>");
//					bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instanceId + "."
//						+ telnetMessage.getTracingTag() + "." + eventParentDivName + "." + eventDivName + "." + i + "."
//						+ URLEncoder.encode(waitFor[i], "UTF-8") + ".log"));
//					bw.write("yyyy" + (i + 1) +" abcdefghijklmnopqrstuvwxyz결과 !@#$%^&*()_+|-=\\`~,./");
//					bw.close();
//				}
//				out.println("</results>");
//				out.println("</TelnetResponseMessage>");
//				out.close();
				
				BufferedWriter bw = null;
				
				out.println("<?xml version='1.0' encoding='UTF-8' ?>");
				out.println("<TelnetResponseMessage>");
				
				if (resultStr.length() < 1) {
					out.println("<error>Login Error</error>");
					out.println("<instanceId>" + instanceId + "</instanceId>");
				} else {
					if (tc.isTimeoutExit() == true) {
						out.println("<error>TimeOut</error>");
					} else {
						if (tc.getErrorLog().size() != 0) {
							StringBuffer errorLog = new StringBuffer();
							for (int i = 0; i < tc.getErrorLog().size(); i++) {
								errorLog.append((String) tc.getErrorLog().get(i));
							}
							out.println("<error>" + errorLog.toString() + "</error>");
						} else {
							out.println("<error>none</error>");
						}
					}
					out.println("<instanceId>" + instanceId + "</instanceId>");
					out.println("<totalResult>" + URLEncoder.encode(resultStr.replace(" ", "%20"), "UTF-8") + "</totalResult>");
					
					if (UEngineUtil.isNotEmpty(resultStr)) {
						bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instanceId + "."
								+ telnetMessage.getTracingTag() + "." + eventParentDivName + "." + eventDivName + ".totalResult.log"));
						bw.write(resultStr);
						bw.close();
					}
										
					out.println("<results>");
					for (int i = 0; i < tc.getResultStringList().size(); i++) {
						String result = (String) tc.getResultStringList().get(i);
						if (result.lastIndexOf("\r\n") != -1) {
							result = result.substring(0, result.lastIndexOf("\r\n"));
						} else if (result.lastIndexOf("\n") != -1) {
							result = result.substring(0, result.lastIndexOf("\n"));
						}
						result = result.replace(" ", "");
						out.println("<result>" + URLEncoder.encode(result.replace(" ", "%20"), "UTF-8") + "</result>");
						
						if (UEngineUtil.isNotEmpty(result)) {
							bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instanceId + "."
									+ telnetMessage.getTracingTag() + "." + eventParentDivName + "." + eventDivName + "." + i + "."
									+ URLEncoder.encode(waitFor[i], "UTF-8") + ".log"));
							bw.write(result);
							bw.close();
						}
					}
					out.println("</results>");
				}
				out.println("</TelnetResponseMessage>");
				out.close();
				

			}
		}

	}

}
