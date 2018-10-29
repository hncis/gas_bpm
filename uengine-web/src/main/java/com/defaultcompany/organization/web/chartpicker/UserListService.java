package com.defaultcompany.organization.web.chartpicker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.util.dao.DefaultConnectionFactory;



public class UserListService extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String key = URLDecoder.decode(req.getParameter("key"), "UTF-8");
		String column = URLDecoder.decode(req.getParameter("column"), "UTF-8");

		DataSource dataSouce = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		UserDAO userDAO = new UserDAO(dataSouce);
		Collection<User> users = userDAO.getUsers(key, column);

		StringBuffer sbXml = new StringBuffer();
		sbXml.append("[").append("\r\n");
		if (users != null) {
			boolean isFirst = true;
			for (User user : users) {
				if (isFirst) {
					isFirst = false;
				} else {
					sbXml.append(", ");
				}
				sbXml.append(user.toJson()).append("\r\n");
			}
		}
		sbXml.append("]");

		res.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		res.setContentType("text/xml");
		res.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = res.getWriter();
		out.write(sbXml.toString());
		out.flush();
		out.close();
	}
	
}
