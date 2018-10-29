package org.uengine.formmanager.trans;

import org.apache.log4j.Logger;

/**
 * Form write, view JSP 에 들어갈 헤더
 *
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: Header.java,v 1.3 2007/12/05 02:31:36 curonide Exp $
 */
public class Header {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(Header.class);
    
    public static String getHeader() {
        
        StringBuffer sb = new StringBuffer();
        sb.append("<%@ page pageEncoding=\"UTF-8\" %>");
        sb.append("<%response.setContentType(\"text/html; charset=UTF-8\");%>");        
        sb.append("\r\n");
        sb.append("<%@include file=\"../../../common/commons.jsp\"%>");
        sb.append("\r\n");

        sb.append("\r\n");
        return sb.toString();       
    }
    
    
}
