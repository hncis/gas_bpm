<%@ page contentType="text/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.hncis.common.exception.ExceptionHandler" %>
<%@ page import="com.hncis.common.exception.IHncisException" %>
<%@ page import="com.hncis.common.message.JSPMessageSource"%>
<%@ page import="java.util.Locale" %>
<%@ page import="net.sf.json.JSONObject" %>

<%
	Throwable throwable = (Throwable)request.getAttribute("exception");
	Locale locale = (Locale)session.getAttribute("reqLocale");
	
	
    String message = null;
    String errorCode = null ;
    String returnMessage = null;
    if(throwable != null)
    {
    	IHncisException pe = (IHncisException)ExceptionHandler.handleException(throwable);
        //message = "Please contact system administrator.\n\n #Error message\n"+pe.getMessage();
        //message = "Please contact GA members(Ext. 0165)";
//         message = "에러가 발생하였습니다.\n총무팀에 문의 바랍니다.";
        message = JSPMessageSource.getMessage("ERROR.0000",locale);
        
        errorCode = pe.getCode();
    }
    //System.out.println("errorMessage="+message);
    
    String stringURI =(String)request.getAttribute("originalURI");
    
    //System.out.println("error stringURI="+stringURI);
/*     
    System.out.println("#############################################");
	System.out.println("throwable="+errorCode);
	System.out.println("stringURI="+stringURI);
	System.out.println("#############################################");
   */  
    if(stringURI.contains("Grid")) 
    {
    	//out.println("Please contact system administrator.");
    	out.println(message);
    }
    else
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode",errorCode);
        jsonObject.put("message",message);
        //jsonObject.put("message","System Error");
        //보안 테스트 에서 아래와 같이 메시지를 뿌리면 db 정보 등이 나와는 등의 지적사항이 나와서 returnMessage = "error"; 로 나오게 수정하였음.
        //만약 개발에서 에러 메시지를 보고 싶을 경우 아래 주석을 풀고 returnMessage = "error"; 에 주석을 주면 됨.
        //returnMessage = "{\n \"sendResult\":"+jsonObject.toString()+"\n}";      
        returnMessage = "{\n \"sendResult\":"+jsonObject.toString()+"\n}";
        //returnMessage = "Please contact system administrator.";
        out.print(returnMessage);
    }
%>   
