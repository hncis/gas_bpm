package org.uengine.admin.servlet;

import java.io.*;
import java.net.URLEncoder;
import java.rmi.RemoteException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.uengine.kernel.GlobalContext;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.UEngineUtil;


public class ExportServlet extends HttpServlet {

	public void service(HttpServletRequest request,	HttpServletResponse response)throws ServletException {
	
		String defId = request.getParameter("processDefinition");
		ProcessManagerRemote pm = null;
		
		try{
			ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
			pm = processManagerFactory.getProcessManagerForReadOnly();
			pm.exportProcessDefinitionbyDefinitionId(defId, false);
			
			String fileName = "";
			if(!defId.equals("-1")){
				fileName = pm.getProcessDefinitionRemoteByDefinitionId(defId).getName().getText();
			}else{
				fileName = "root";
			}

			String TEMP_DIRECTORY = GlobalContext.getPropertyString(
				"server.definition.path",
				"." + File.separatorChar + "uengine" + File.separatorChar + "definition" + File.separatorChar
			);
					
			if(!TEMP_DIRECTORY.endsWith("/") && !TEMP_DIRECTORY.endsWith("\\")){
				TEMP_DIRECTORY = TEMP_DIRECTORY + "/";
			}
			TEMP_DIRECTORY = TEMP_DIRECTORY + "temp" + File.separatorChar + "download" + File.separatorChar;
			String filePath = TEMP_DIRECTORY + fileName + ".zip";
			
			BufferedInputStream  fis  = new BufferedInputStream(new FileInputStream(filePath));
			int idx = filePath.lastIndexOf(File.separatorChar);
			filePath = filePath.substring(idx+1, filePath.length());

			response.setHeader("Cache-Control","private"); //HTTP 1.1
			response.setHeader("Pragma","no-cache"); //HTTP 1.0
			response.setHeader("Content-Disposition", "attachment;filename=\"" +  URLEncoder.encode(filePath, "UTF-8").replace('+',' ')+"\";");
			response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

			UEngineUtil.copyStream(fis, response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();

		}catch(Exception e){
			try {
				pm.cancelChanges();
			} catch (RemoteException e1) {}
			e.printStackTrace();
		}finally{
			try{
				pm.remove();
			}catch(Exception ex){}
		}
	}
}

