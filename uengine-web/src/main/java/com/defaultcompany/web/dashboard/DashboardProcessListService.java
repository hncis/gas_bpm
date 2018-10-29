package com.defaultcompany.web.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class DashboardProcessListService extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String endpoint = arg0.getParameter("endpoint");
		String status = arg0.getParameter("status");
		String _contentMinLength = arg0.getParameter("contentMinLength");
		String _listLength = arg0.getParameter("listLength");
		
		int contentMinLength = 0;
		int listLength = 0;
		if (UEngineUtil.isNotEmpty(_contentMinLength)){
			contentMinLength = Integer.parseInt(_contentMinLength);
		}
		if (UEngineUtil.isNotEmpty(_listLength)) {
			listLength = Integer.parseInt(_listLength);
		}
		
		DataSource dataSource = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		DashboardProcessListDAO dashboardProcessListDAO = new DashboardProcessListDAO(dataSource);
		List<DashboardProcessList> processList = null;
		if ("Running".equals(status)) {
			processList = dashboardProcessListDAO.getRunningProcessList(endpoint, contentMinLength, listLength);
		} else if ("Completed".equals(status)) {
			processList = dashboardProcessListDAO.getCompletedProcessList(endpoint, contentMinLength, listLength);
		}
		
		arg1.setContentType("application/json; charset=UTF-8"); 
		arg1.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = arg1.getWriter();
		
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

		String strJSON = xstream.toXML(processList);

		out.write(strJSON);
		out.flush();
		out.close();
	}

}
