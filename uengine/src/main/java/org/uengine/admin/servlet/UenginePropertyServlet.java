package org.uengine.admin.servlet;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.kernel.GlobalContext;

public class UenginePropertyServlet {
	
	public void service(HttpServletRequest request,
			HttpServletResponse response)
	throws ServletException {


		try{
			response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
			response.setHeader("Pragma","no-cache"); //HTTP 1.0
			response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
			
			Properties prop = GlobalContext.getProperties();
			prop.store(response.getOutputStream(),"");
		}catch(Exception e){
			throw new ServletException(e);
		}
	}

}
