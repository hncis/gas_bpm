<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

        String userid = "";
        String serverName = "";
        int serverPort = 80;
        String nextUrl = "";
        String uri = "";

        String ctxPath = request.getContextPath();
		userid = request.getHeader("AUTOWAY_HMMC");
        serverName = request.getServerName();
//        serverPort = request.getServerPort();
        uri = ctxPath + "/hmbSSOLogin.gas";
        nextUrl = "http://" + serverName + ":" + serverPort + uri;

        session.setAttribute("SSO_ID", userid); //session으로 ID 값 넘길 때 주석처리 해제할 부분
		
        response.sendRedirect(nextUrl);

%>