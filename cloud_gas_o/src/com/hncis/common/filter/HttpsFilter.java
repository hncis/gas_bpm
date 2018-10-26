package com.hncis.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hncis.common.wrapper.HttpsRequestWrapper;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : CSRSslFilter.java
 * @Description : Tag Filter.
 * @author Seung Hun
 * @since 2012. 9. 10.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2012. 9. 10.     Seung Hun     최초 생성
 * </pre>
 */

public class HttpsFilter implements Filter {
    private FilterConfig config;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpsRequestWrapper httpsRequest = new HttpsRequestWrapper( (HttpServletRequest)request);
    	httpsRequest.setResponse( (HttpServletResponse)response);
    	chain.doFilter(httpsRequest, response);
    }
    
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }
    
    public void destroy() {}
    
}
