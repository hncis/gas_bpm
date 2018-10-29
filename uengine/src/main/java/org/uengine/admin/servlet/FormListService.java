package org.uengine.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.ui.tree.dao.ProcessDefinitionListDAO;
import org.uengine.ui.tree.model.Form;
import org.uengine.ui.tree.model.FormList;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class FormListService extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		String type = arg0.getParameter("type");
		int defId = 0;
		int defVerId = 0;
		try {
			defId = Integer.parseInt(arg0.getParameter("defId"));
		} catch (NumberFormatException e) {
		}
		
		try {
			defVerId = Integer.parseInt(arg0.getParameter("defVerId"));
		} catch (NumberFormatException e) {
		}
		
		SimpleTransactionContext st = new SimpleTransactionContext();
		
		try{
			//DataSource dataSource = DefaultConnectionFactory.create().getDataSource();
			ProcessDefinitionListDAO formListDAO = new ProcessDefinitionListDAO(st);
			String result = null;
			
			if ("defId".equals(type)) {
				defVerId = formListDAO.findFormProductionVersionId(defId);
				Collection<Form> forms = formListDAO.findAllFormVersions(defId);
				
				FormList formList = new FormList();
				formList.setDefVerId(defVerId);
				formList.setForms(forms);
				
		        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
		            public HierarchicalStreamWriter createWriter(Writer writer) {
		                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
		            }
		        });
		        
		        result = xstream.toXML(formList).replace("   ", "");
			} else if ("defVerId".equals(type)) {
				result = getDefinitionVersionResource(String.valueOf(defVerId));
			}
	
			arg1.setContentType("application/json; charset=UTF-8"); 
			arg1.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = arg1.getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch(Exception e){
			throw new ServletException(e);
		} finally{
			try {
				st.releaseResources();
			} catch (Exception e) {
			}
		}
	}
	
	private String getDefinitionVersionResource(String defVerId) {
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		String content = null;
		
		try {
			pm = processManagerFactory.getProcessManagerForReadOnly();
			content = pm.getResource(defVerId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pm.remove();
			} catch (Exception e) {
			}
		}
		
		return content;
	}
	
}
