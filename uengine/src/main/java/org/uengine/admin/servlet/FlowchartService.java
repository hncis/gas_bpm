package org.uengine.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.ui.flowchart.impl.MakeProcessDefinitionToJSON;
import org.uengine.ui.flowchart.impl.MakeProcessInstanceToJSON;

public class FlowchartService extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		boolean onlyHumanActivity = false;
		try {
			onlyHumanActivity = new Boolean(arg0.getParameter("onlyHumanActivity"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String instanceId = arg0.getParameter("instanceId");
		String defVerId = arg0.getParameter("defVerId");
		String defId = arg0.getParameter("defId");
		
		MakeProcessDefinitionToJSON mkProcessDefinitionToJSON = null;
		MakeProcessInstanceToJSON mkProcessInstanceToJSON = null;
		String result = null;
		
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		
		try {
			pm = processManagerFactory.getProcessManagerForReadOnly();
			
			if (instanceId != null) {
				mkProcessInstanceToJSON = new MakeProcessInstanceToJSON(pm.getProcessInstance(instanceId));
				mkProcessInstanceToJSON.setOnlyHumanActivity(onlyHumanActivity);
				mkProcessInstanceToJSON.setPm(pm);
				result = mkProcessInstanceToJSON.toJSON(pm.getProcessDefinitionWithInstanceId(instanceId));
			} else {
				mkProcessDefinitionToJSON = new MakeProcessDefinitionToJSON();
				mkProcessDefinitionToJSON.setOnlyHumanActivity(onlyHumanActivity);
				if (defVerId != null) {
					result = mkProcessDefinitionToJSON.toJSON(pm.getProcessDefinition(defVerId)); 
				} else if (defId != null) {
					String tempDefVerId = pm.getProcessDefinitionProductionVersion(defId);
					result = mkProcessDefinitionToJSON.toJSON(pm.getProcessDefinition(tempDefVerId));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pm.remove();
			} catch (Exception e) {
			}
		}
		
		arg1.setContentType("application/json; charset=UTF-8"); 
		arg1.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = arg1.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}
	
}
