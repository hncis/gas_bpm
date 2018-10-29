package com.defaultcompany.web.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.bind.ServletRequestUtils;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class DashboardWorkListService extends HttpServlet {
	
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String endpoint = arg0.getParameter("endpoint");
		String status = arg0.getParameter("status");
		int contentMinLength = ServletRequestUtils.getIntParameter(arg0, "contentMinLength", 0);
		int listLength = ServletRequestUtils.getIntParameter(arg0, "listLength", 0);

		DataSource dataSource = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		DashboardWorkListDAO dashboardWorkListDAO = new DashboardWorkListDAO(dataSource);
		List<DashboardWorkList> workList = null;
		if ("Open".equals(status)) {
			workList = dashboardWorkListDAO.getOpenWorkList(endpoint, contentMinLength, listLength);
		}

		arg1.setContentType("application/json; charset=UTF-8"); 
		arg1.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = arg1.getWriter();
		
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

		String strJSON = xstream.toXML(workList);

		out.write(strJSON);
		out.flush();
		out.close();
	}

}
