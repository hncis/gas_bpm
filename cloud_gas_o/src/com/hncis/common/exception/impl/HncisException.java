package com.hncis.common.exception.impl;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.hncis.common.exception.IHncisException;
import com.hncis.common.message.HncisMessageSource;

public class HncisException extends Exception implements IHncisException {
	private static final long serialVersionUID = 8938058159882815007L;
    private boolean isWriteLog;
    private String code;
    private String message;
    private Object userObject;
    
    /**
     * 글로벌총무시스템 framework Exception constructor 
     * @param code error code
     */
    public HncisException(String code)
    {
        this(code,(Throwable)null);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor 
     * @param cause Throwable
     */
    public HncisException(Throwable cause)
    {
        this(HncisMessageSource.DEFAULT_MESSAGE_CODE,cause);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor 
     * @param code error code
     * @param cause Throwable
     */
    public HncisException(String code,Throwable cause)
    {
        super(cause);
        this.isWriteLog = false;
        this.setCode(code);
        this.message = HncisMessageSource.getMessage(this.code);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor 
     * @param code error code
     * @param message message
     */
    public HncisException(String code,String message)
    {
        this.isWriteLog = false;
        this.setCode(code);
        this.message = message != null ? message : HncisMessageSource.getMessage(this.code);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor
     * @param code : error code
     * @param messageParam : message에 들어갈 parameter
     */
    public HncisException(String code,Object[] messageParam)
    {
        this(code,messageParam,null);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor
     * @param code error code
     * @param messageParams message에 들어갈 parameter
     * @param cause Throwable
     */
    public HncisException(String code,Object[] messageParams,Throwable cause)
    {
        super(cause);
        this.isWriteLog = false;
        this.setCode(code);
        
        this.message = HncisMessageSource.getMessage(this.code, messageParams);
    }
    
    /**
     * 글로벌총무시스템 framework Exception constructor
     * @param code error code
     * @param message message에 들어갈 parameter
     * @param cause Throwable
     */
    public HncisException(String code,String message,Throwable cause)
    {
        super(cause);
        this.isWriteLog = false;
        this.code = code != null ? code : HncisMessageSource.DEFAULT_MESSAGE_CODE;
        
        this.message = message != null ? message : HncisMessageSource.getMessage(code);
    }
    
    /**
     * error code setter
     * @param codeerror code
     */
    private void setCode(String code)
    {
        this.code = code != null ? code : HncisMessageSource.DEFAULT_MESSAGE_CODE; 
    }
    
    /**
     * error code getter
     * @return error code
     */
    public String getCode()
    {
        return this.code;
    }
    
    /**
     * message getter
     * @return error message 
     */
    public String getMessage()
    {
        return this.message;
    }
    
    /**
     * logg 쓸여부 getter
     * @return true log 남김,false log 남기지 않는다.
     */
    public boolean isWriteLog()
    {
        return this.isWriteLog;
    }
    
    /**
     * log 쓸여부 setter
     * @param isWriteLog log 쓸여부 
     */
    public void setWriteLog(boolean isWriteLog)
    {
        this.isWriteLog = isWriteLog;
    }
    
    /**
     * logg 쓸여부 setter
     * @param userObject 사용자 정의 object
     */
    public void setUserObject(Object userObject)
    {
        this.userObject = userObject;
    }
    
    /**
     * logg 쓸여부 getter
     * @return user Object
     */
    public Object getUserObject()
    {
        return this.userObject;
    }
    
    /**
     * super class override
     * @param ps PrintStream
     */
    @Override
    public void printStackTrace(PrintStream ps)
    {
        if(getCause() == null){
            super.printStackTrace(ps);
        }else{
            getCause().printStackTrace(ps);
        }
    }
    
    /**
     * super class override
     * @param pw PrintWriter
     */
    @Override
    public void printStackTrace(PrintWriter pw)
    {
        if(getCause() == null){
            super.printStackTrace(pw);
        }else{
            getCause().printStackTrace(pw);
        }
    }
}
