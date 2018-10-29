package com.defaultcompany.web.acl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.security.AclManager;

public class DefaultPermissionService extends HttpServlet {
	
	private String getDefaultPermaissionXml(int defId) {
		AclManager acl = AclManager.getInstance();  
		StringBuffer xml = new StringBuffer();
		HashMap hm = acl.getDefaultPermission(defId);
		ArrayList<Character> listA = (ArrayList<Character>) hm.get(AclManager.PERMISSION_DEFAULT_ANONYMOUS);
		ArrayList<Character> listM = (ArrayList<Character>) hm.get(AclManager.PERMISSION_DEFAULT_MEMBERS);
		
		for (char c : listA) {
			xml.append("<anonymous>").append(c).append("</anonymous>");
		}
		
		for (char c : listM) {
			xml.append("<members>").append(c).append("</members>");
		}
		
		return xml.toString();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		int defId = Integer.parseInt(req.getParameter("defId"));

		res.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		res.setContentType("text/xml");
		res.setHeader("Cache-Control", "no-cache");
		res.getWriter().write(getDefaultPermaissionXml(defId));
	}
}
