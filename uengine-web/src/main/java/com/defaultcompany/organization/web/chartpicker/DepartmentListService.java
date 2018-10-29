package com.defaultcompany.organization.web.chartpicker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;



public class DepartmentListService extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String comCode = req.getParameter("comCode");
		String parentCode = req.getParameter("parentCode");

		DataSource dataSource = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		DepartmentDAO departmentDAO = new DepartmentDAO(dataSource);
		Collection<Department> departments = null;
		
		if (UEngineUtil.isNotEmpty(parentCode)) {
			departments = departmentDAO.getChildrenOfDeparment(parentCode);
		} else if(UEngineUtil.isNotEmpty(comCode)) {
			departments = departmentDAO.getDeparments(comCode);
		}
		
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<deptlist>").append("\r\n");
		if (departments != null) {
			for (Department department : departments) {
				sbXml.append(department.toXML()).append("\r\n");
			}
		}
		sbXml.append("</deptlist>");
		
		res.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		res.setContentType("text/xml");
		res.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = res.getWriter();
		out.write(sbXml.toString());
		out.flush();
		out.close();
	}
	
}
