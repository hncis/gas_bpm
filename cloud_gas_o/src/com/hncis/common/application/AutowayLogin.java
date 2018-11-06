package com.hncis.common.application;

import java.net.*;
import java.io.*;

import com.hncis.common.util.*;

public class AutowayLogin {
   public static void main(String[] args) throws Exception {
        boolean s = isLoginCorrect("37100044", "brian6539@");
//        boolean s = isLoginCorrect("37102488", "say789!!");
    }

    public static boolean isLoginCorrect(String s, String s1) throws Exception {
//        final String addr = "https://autoway.hyundai-brasil.com/AutowayDotNet_HMB/Common/Authentication/RequestAuthentication.aspx";
//        final String userInfor = "USERINFOR=" + Base64.encode(id+"|`|"+pwd);
//        String s = "";
//        try {
//            s = "1";
//            URL url = new URL(addr + "?" + userInfor);
//            System.out.println("Autoway Authorizing...["+url.toString()+"]");
//            
//            s += "2";
//            BufferedReader is = new BufferedReader(
//                                    new InputStreamReader(
//                                        url.openConnection().getInputStream()));
//            s += "3";
//            String line = null;
//            StringBuffer sb = new StringBuffer();
//            s += "4";
//            while((line = is.readLine()) != null) {
//                sb.append(line);
//            }
//            s += "5";
//        	System.out.println("Autoway response...["+sb.toString()+"]");
//            return "Y".equals(sb.toString());
//        } catch(Exception e) {
//        	System.out.println(s+e.getMessage());
//            throw new Exception(s+e.getMessage());
//        }
    	
    	String s2 = (new StringBuilder()).append("USERINFOR=").append(Base64.encode((new StringBuilder()).append(s).append("|`|").append(s1).toString())).toString();
        String s3 = "";
        try
        {
            s3 = "1";
            URL url = new URL((new StringBuilder()).append("http://autoway.hyundai-brasil.com/AutowayDotNet_HMB/Common/Authentication/RequestAuthentication.aspx?").append(s2).toString());
            s3 = (new StringBuilder()).append(s3).append("2").toString();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            s3 = (new StringBuilder()).append(s3).append("3").toString();
            String s4 = null;
            StringBuffer stringbuffer = new StringBuffer();
            s3 = (new StringBuilder()).append(s3).append("4").toString();
            while((s4 = bufferedreader.readLine()) != null) 
                stringbuffer.append(s4);
            s3 = (new StringBuilder()).append(s3).append("5").toString();
            return "Y".equals(stringbuffer.toString());
        }
        catch(Exception exception)
        {
            throw new Exception((new StringBuilder()).append(s3).append(exception.getMessage()).toString());
        }
    }

}
