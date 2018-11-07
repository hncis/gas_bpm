package com.hncis.common.message;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;

public class JSPMessageSource extends HncisMessageSource {
    private transient static Log logger = LogFactory.getLog(JSPMessageSource.class.getClass());

	public static String getMessage(String code, Locale locale)
    {
        String message = "";
        if(code == null || code.length() == 0)
            code = DEFAULT_MESSAGE_CODE;
        try
        {
            message = messageSource.getMessage(code,null,locale);
            return message;
        }
        catch(NoSuchMessageException nsme)
        {
			logger.error("messege", nsme);
            message = DEFAULT_MESSAGE_CODE;
            return message;
        }
        catch(Exception e)
        {
			logger.error("messege", e);
            message = DEFAULT_MESSAGE_CODE;
            return message;
        }
        /*finally
        {
            return message;
        }*/
    }
}
