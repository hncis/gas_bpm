package com.hncis.common.message;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;

public class JSPMessageSource extends HncisMessageSource {

	public static String getMessage(String code, Locale locale)
    {
        String message = "";
        if(code == null || code.length() == 0)
            code = DEFAULT_MESSAGE_CODE;
        try
        {
            message = messageSource.getMessage(code,null,locale);
        }
        catch(NoSuchMessageException nsme)
        {
            nsme.printStackTrace();
            message = DEFAULT_MESSAGE_CODE;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            message = DEFAULT_MESSAGE_CODE;
        }
        finally
        {
            return message;
        }
    }
}
