package com.hncis.common.exception;

import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.util.StringUtil;

public class ExceptionHandler {
	private static Log logger;
    static { logger = LogFactory.getLog(ExceptionHandler.class); }
    
    /**
     * logger에 설정된 log write를 한다.
     * @param pe 글로벌총무시스템 exception
     */
    private static void writeLog(IHncisException pe)
    {
        if(logger.isErrorEnabled())
        {
            StringBuffer strBuff = new StringBuffer();
            strBuff.append(pe.getCode())
                   .append(" : ")
                   .append(pe.getMessage());
            
            logger.error(strBuff.toString(), (Throwable)pe);
        }
    }
    
    /**
     * Exception를 hncisException에 맞게 handle한다.
     * @param t
     * @return Throwable
     */
    public static Throwable handleException(Throwable t)
    {
    	String SQL_ERROR_CODE_PREFIX = "";
    	if("oracle".equals(StringUtil.getDbType())){
    		SQL_ERROR_CODE_PREFIX = "ORA-";
    	}else if("mysql".equals(StringUtil.getDbType())){
    		SQL_ERROR_CODE_PREFIX = "MYSQL-";
    	}else if("mssql".equals(StringUtil.getDbType())){
    		SQL_ERROR_CODE_PREFIX = "MSSQL-";
    	}
    	
        IHncisException hncisException = null;
        if(t instanceof IHncisException)
        	hncisException = (IHncisException)t;
        else
        {
            SQLException sqlException = extractSQLException(t);
            if(sqlException != null)
            {
                DecimalFormat formatter = new DecimalFormat("00000");
                String sqlErrorCode = SQL_ERROR_CODE_PREFIX + formatter.format(sqlException.getErrorCode());
                hncisException = new HncisException(sqlErrorCode,sqlException.getMessage(),t);
            }
            else
            	hncisException = new HncisException(t);
            //hncisException = new HmbException(t);//sql Exception code와 message를 사용자에게 보여이려면 이곳의 주석을 달어라.
        }
        if(!hncisException.isWriteLog())
        {
            writeLog(hncisException);
            hncisException.setWriteLog(true);
        }
        return (Throwable)hncisException;
    }
    
    /**
     * 최하단의 sqlException를 찾는다. 
     * @param t
     * @return SQLException
     */
    public static SQLException extractSQLException(Throwable t)
    {
        SQLException sqlException = null;
        
        if(t != null)
        {
            if( t instanceof SQLException)
            {
                sqlException = (SQLException)t;
                if(sqlException.getCause() != null)
                    sqlException = extractSQLException(t.getCause());
                else
                {
                    if(sqlException.getNextException() != null)
                        sqlException = extractSQLException((Throwable)sqlException.getNextException());
                }
            }
            else
            {
                if(t.getCause() != null)
                    sqlException = extractSQLException(t.getCause());
            }
        }
       
        return sqlException;
    }
}
