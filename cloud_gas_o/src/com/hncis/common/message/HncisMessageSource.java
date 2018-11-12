package com.hncis.common.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HmbMessageSource
 * springframework의 MessageSource wrapper class
 */
@Component("hmbMessageSource")
public class HncisMessageSource implements MessageSourceAware{
    private transient static Log logger = LogFactory.getLog(HncisMessageSource.class.getClass());
	protected static MessageSource messageSource;
    public static final String DEFAULT_MESSAGE_CODE = "ERROR.0000";
    
    /**
     * messageSource setter
     * @param messageSource
     */
    @Override
	public void setMessageSource(MessageSource messageSource)
    {
        HncisMessageSource.messageSource = messageSource;
    }
    
    /**
     * error code를 찾아 message를 얻는다.
     * @param code message
     * @return 코드에 해당하는 message
     */
    public static String getMessage(String code)
    {
        return getMessage(code,null,null);
    }

    public static String getMessage(String code, Locale locale)
    {
        return getMessage(code,null,locale);
    }

    
    /**
     * error code를 찾아 message에 messageParameter를 setting 하여 얻어준다.
     * @param code
     * @param messageParams
     * @return message
     */
    @SuppressWarnings("finally")
    public static String getMessage(String code,Object[] messageParams)
    {
        String message = "";
        if(code == null || code.length() == 0)
            {code = DEFAULT_MESSAGE_CODE;}
        try
        {
//            message = messageSource.getMessage(code,messageParams,LocaleContextHolder.getLocale());
            message = messageSource.getMessage(code,messageParams, null);
        }
        catch(NoSuchMessageException nsme)
        {
            nsme.printStackTrace();
            message = DEFAULT_MESSAGE_CODE;
        }
        catch(Exception e)
        {
			logger.error("messege", e);
            message = DEFAULT_MESSAGE_CODE;
        }
        finally
        {
            return message;
        }
    }
    
    /**
     * error code를 찾아 message에 messageParameter를 setting 하여 얻어준다.
     * @param code
     * @param messageParams
     * @return message
     */
    @SuppressWarnings("finally")
    public static String getMessage(String code,Object[] messageParams, Locale locale)
    {
        String message = "";
        if(locale == null){
        	locale = LocaleContextHolder.getLocale();
        }
        if(code == null || code.length() == 0)
            {code = DEFAULT_MESSAGE_CODE;}
        try
        {
            message = messageSource.getMessage(code,messageParams, locale);
        }
        catch(NoSuchMessageException nsme)
        {
            nsme.printStackTrace();
            message = DEFAULT_MESSAGE_CODE;
        }
        catch(Exception e)
        {
			logger.error("messege", e);
            message = DEFAULT_MESSAGE_CODE;
        }
        finally
        {
            return message;
        }
    }
}
