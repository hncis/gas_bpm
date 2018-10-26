<%@ page contentType="text/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="net.sf.json.JSONArray"%>
<%
	String uiType = (String)request.getAttribute("uiType");

	if(uiType != null && uiType.equals("ajax")) {
		//System.out.println("{\n \"sendResult\":" + request.getAttribute("data") + "\n}");
		out.println("{\n \"sendResult\":" + request.getAttribute("data") + "\n}");
	}else {
		out.println(request.getAttribute("data"));	
	}
%>
