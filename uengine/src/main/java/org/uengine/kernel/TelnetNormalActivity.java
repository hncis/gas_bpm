package org.uengine.kernel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.validator.NotNullValid;
import org.metaworks.validator.Validator;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.telnet.TelnetCommand;
import org.uengine.telnet.TelnetConfigCommand;
import org.uengine.telnet.TelnetConfigHost;
import org.uengine.telnet.TelnetNormalModule;
import org.uengine.telnet.TelnetParameter;
import org.uengine.util.UEngineUtil;

public class TelnetNormalActivity extends HumanActivity {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	// 대우증권 기존에 만들어 놓은 프로세스들 때문에 TelnetConfigHosts 로 이름 놔둠
	// 차후 엔진 반영시에는 첫 T 는 t 로
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.removeFieldDescriptor("Parameters");
	}
	
	TelnetConfigHost[] TelnetConfigHosts;
		public TelnetConfigHost[] getTelnetConfigHosts() {
			return TelnetConfigHosts;
		}
		public void setTelnetConfigHosts(TelnetConfigHost[] telnetConfigHosts) {
			this.TelnetConfigHosts = telnetConfigHosts;
		}
		
	public TelnetNormalActivity() {
		super();
		setName("Telnet");
		setTool("telnetHandler");
		setDuration(5);
	}

	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {		
		boolean isSuccess = true;
		String tracingTag = this.getTracingTag();
//		Vector currentRunningActivities = instance.getCurrentRunningActivities();
//		for (int i = 0; i < currentRunningActivities.size(); i++) {
//			TelnetNormalActivity currentRunningTelnetNormalActivity = (TelnetNormalActivity) currentRunningActivities.get(i);
//			tracingTag = currentRunningTelnetNormalActivity.getTracingTag();
//		}

		if (isNotificationWorkitem()) {
			String TEMP_DIRECTORY = GlobalContext.getPropertyString(
					"filesystem.path",
					"." + File.separatorChar + "uengine" + File.separatorChar + "fileSystem" + File.separatorChar
				);
			TEMP_DIRECTORY = TEMP_DIRECTORY + "telnet" + File.separatorChar;
			File f = new File(TEMP_DIRECTORY);
			if (!f.exists()) {
				f.mkdirs();
			}
			BufferedWriter bw = null;
			
			if (TelnetConfigHosts != null && TelnetConfigHosts.length > 0) {
				for (int i = 0; i < TelnetConfigHosts.length; i++) {
					TelnetConfigHost telnetConfigHost = TelnetConfigHosts[i];
					
					String hostName = telnetConfigHost.getHostName();
					int port = telnetConfigHost.getPort();
					
					String eventParentDivName = "telnetHost_" + i;

					TelnetConfigCommand[] telnetConfigCommands = telnetConfigHost.getTelnetConfigCommand();
					if (telnetConfigCommands != null && telnetConfigCommands.length > 0) {
						for (int j = 0; j < telnetConfigCommands.length; j++) {
							TelnetConfigCommand telnetConfigCommand = telnetConfigCommands[j];
							
							String serverType = telnetConfigCommand.getServerType();
							int sessionTimeout = telnetConfigCommand.getSessionTimeout();
							ProcessVariable totalResult = telnetConfigCommand.getTotalResult();
							TelnetCommand[] telnetCommands = telnetConfigCommand.getTelnetCommands();

							String[] waitFors = new String[telnetCommands.length];
							String[] commands = new String[telnetCommands.length];
							long[] timeouts = new long[telnetCommands.length];
							ProcessVariable[] resultCommands = new ProcessVariable[telnetCommands.length];
							
							String eventDivName = "telnet_" + j;

							if (telnetCommands != null && telnetCommands.length > 0) {
								for (int k = 0; k < telnetCommands.length; k++) {
									TelnetCommand telnetCommand = telnetCommands[k];
									
									String waitFor = telnetCommand.getWaitFor();
									String command = evaluateContent(instance, telnetCommand.getCommand()).toString();
									long timeout = telnetCommand.getTimeout();
									ProcessVariable resultCommand = telnetCommand.getResultCommand();

									String parameter = "";
									TelnetParameter[] telnetparameters = telnetCommand.getTelnetParameter();
									if (telnetparameters != null && telnetparameters.length > 0) {
										for (int u = 0; u < telnetparameters.length; u++) {
											TelnetParameter telnetparameter = telnetparameters[u];
											parameter += evaluateContent(instance, telnetparameter.getParameter());
											if (u != telnetparameters.length - 1)
												parameter += " ";
										}
									}

									waitFors[k] = waitFor;
									if ("".equals(parameter.replace(" ", ""))) {
										commands[k] = command;
									} else {
										commands[k] = command + " " + parameter;
									}
									timeouts[k] = timeout;
									resultCommands[k] = resultCommand;

								}
								
								TelnetNormalModule telnetNormalModule = new TelnetNormalModule();
								telnetNormalModule.setHost(hostName, port);
								telnetNormalModule.setSessionTimeout(sessionTimeout);
								telnetNormalModule.setWaitFor(waitFors);
								telnetNormalModule.setCommand(commands);
								telnetNormalModule.setTimeout(timeouts);
								telnetNormalModule.setServerType(serverType);
								telnetNormalModule.start();

								String resultTotalStr = telnetNormalModule.getTotalResultString();
								ArrayList resultStringList = telnetNormalModule.getResultStringList();
								
								if (UEngineUtil.isNotEmpty(resultTotalStr)) {
									try {
										bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instance.getInstanceId() + "."
												+ tracingTag + "." + eventParentDivName + "." + eventDivName + ".totalResult.log"));
										bw.write(resultTotalStr);
										bw.close();
									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										if (bw != null) try { bw.close(); } catch (Exception e) { }
									}
								}
								for (int k = 0; k < telnetNormalModule.getResultStringList().size(); k++) {
									String result = (String) telnetNormalModule.getResultStringList().get(k);
									if (result.lastIndexOf("\r\n") != -1) {
										result = result.substring(0, result.lastIndexOf("\r\n"));
									} else if (result.lastIndexOf("\n") != -1) {
										result = result.substring(0, result.lastIndexOf("\n"));
									}
									result = result.replace(" ", "");
									
									if (UEngineUtil.isNotEmpty(result)) {
										try {
											bw = new BufferedWriter(new FileWriter(TEMP_DIRECTORY + File.separatorChar + instance.getInstanceId() + "."
													+ tracingTag + "." + eventParentDivName + "." + eventDivName + "." + k + "."
													+ URLEncoder.encode(waitFors[k], "UTF-8") + ".log"));
											bw.write(result);
											bw.close();
										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											if (bw != null) try { bw.close(); } catch (Exception e) { }
										}
									}
								}

								if (totalResult != null) {
									if (instance.getProcessDefinition().getProcessVariable(totalResult.getName()) != null) {
										instance.set("", totalResult.getName(), resultTotalStr);
									}
								}
								for (int k = 0; k < resultStringList.size(); k++) {
									if (resultCommands[k] != null) {
										if (instance.getProcessDefinition().getProcessVariable(resultCommands[k].getName()) != null) {
											String resultStr = (String) resultStringList.get(k);
											instance.set("", resultCommands[k].getName(), resultStr);
										}
									}
								}
								
								if (telnetNormalModule.isTimeoutExit() == true) {
									isSuccess = false;
									break;
								}
								
							}	// if (telnetCommands != null && telnetCommands.length > 0)
						}
					}	// if (telnetConfigCommands != null && telnetConfigCommands.length > 0)
				}
			}	// if (TelnetConfigHosts != null && TelnetConfigHosts.length > 0)
		}	// if (isNotificationWorkitem())
		
		if  (isSuccess == false) {
			this.setNotificationWorkitem(false);
		}
		
		super.executeActivity(instance);
	}
	
	@Override
	public void afterExecute(ProcessInstance instance) throws Exception {
		ProcessDefinitionFactory pdf = ProcessDefinitionFactory.getInstance(instance.getProcessTransactionContext());
		pdf.removeFromCache(instance.getProcessDefinition().getId());
		super.afterExecute(instance);
	}

	
}