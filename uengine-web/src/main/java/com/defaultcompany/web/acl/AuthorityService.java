package com.defaultcompany.web.acl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.uengine.kernel.GlobalContext;
import org.uengine.util.dao.DefaultConnectionFactory;


public class AuthorityService extends HttpServlet {
    private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
    
    public void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
    
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		int defId = Integer.parseInt(req.getParameter("defId"));
		String sTodo = req.getParameter("todo");
		int aclTableId;
		String empCode;
		String partCode;
		String roleCode;
		String permission;
		
		DataSource dataSource = ((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource();
		AuthorityDAO authorityDAO = new AuthorityDAO(dataSource);
		
		if ("add".equals(sTodo)) {
			empCode = req.getParameter("empCode");
			partCode = req.getParameter("partCode");
			roleCode = req.getParameter("roleCode");
			permission = req.getParameter("permission");
			
			authorityDAO.insertAuthority(defId, empCode, partCode, roleCode, permission);
		} else if ("delete".equals(sTodo)) {
			aclTableId = Integer.parseInt(req.getParameter("acltableid"));
			
			authorityDAO.deleteAuthority(aclTableId);
		}
		
		List<Authority> authoritys = authorityDAO.getAuthoritys(defId);
		
		StringBuffer sbXml = new StringBuffer();
		sbXml.append("<list>").append("\r\n");
		if (authoritys != null) {
			for (Authority authority : authoritys) {
				sbXml.append(authority.toXML()).append("\r\n");	
			}
		}
		sbXml.append("</list>");
		
		res.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		res.setContentType("text/xml");
		res.setHeader("Cache-Control", "no-cache");
		PrintWriter out = res.getWriter();
		out.write(sbXml.toString());
		out.flush();
		out.close();
		
	}
}
