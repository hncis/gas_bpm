package com.defaultcompany.web.dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.util.dao.DefaultConnectionFactory;



public class DashboardCountService extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String endpoint = arg0.getParameter("endpoint");
		
		DataSource dataSource = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		DashboardCountDAO dashboardCountDAO = new DashboardCountDAO(dataSource);
		DashboardCount dashboardCount = dashboardCountDAO.getAllDashboardCount(endpoint);
		
		arg1.setContentType("application/json; charset=UTF-8"); 
		arg1.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = arg1.getWriter();
		out.write(dashboardCount.toJSON());
		out.flush();
		out.close();
	}
	
}
