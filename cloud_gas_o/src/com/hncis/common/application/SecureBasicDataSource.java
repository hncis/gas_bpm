package com.hncis.common.application;

import org.apache.commons.dbcp.BasicDataSource;

import com.hncis.common.util.Base64;

public class SecureBasicDataSource  extends BasicDataSource {

    //사용자아이디 암호화 해제 설정
    public void setUsername(String username){
//    	System.out.println(Base64.decode(username));
        super.setUsername(Base64.decode(username));
    }
    

    //비밀번호 암호화 해제 설정
    public void setPassword(String password) {
//    	System.out.println(Base64.decode(password));
        super.setPassword(Base64.decode(password));
    }
    
}

