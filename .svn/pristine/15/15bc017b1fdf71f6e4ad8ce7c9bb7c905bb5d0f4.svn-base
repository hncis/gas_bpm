<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //String uiType = (String)request.getAttribute("uiType");
    String callBack = (String)request.getAttribute("callBack");

    //if(uiType != null && uiType.equals("ajax")) {
        //System.out.println("{\n \"sendResult\":" + request.getAttribute("data") + "\n}");
        //out.println("{\n \"sendResult\":" + request.getAttribute("data") + "\n}");
    //}else {
        //out.println(request.getAttribute("data"));    
    //}
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script>
		    var jsonData = <%=request.getAttribute("data")%>;
		    eval(parent.<%=callBack%>);
		</script>
    </head>
    <body>
    </body>
</html>    
