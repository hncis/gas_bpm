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

import com.hncis.common.application.SessionInfo;
import com.hncis.common.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CSRSslFilter.
 */
public class CSRSslFilter implements Filter {
    private transient Log logger = LogFactory.getLog(getClass());
	private static final String token = "csrfToken";

	/** The config. */
	private FilterConfig config;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;

		if(!(httpReq.getRequestURI().contains("index.gas")||
				httpReq.getRequestURI().contains("bottom.gas")||
				httpReq.getRequestURI().contains("loginAdmin.gas")||
				httpReq.getRequestURI().contains("login.gas")||
				httpReq.getRequestURI().contains("introduce.gas")||
				httpReq.getRequestURI().contains("xst09FromEmail.gas")||
				httpReq.getRequestURI().contains("entranceMealMgmt.gas")||
				httpReq.getRequestURI().contains("entranceMealDetail.gas")||
				httpReq.getRequestURI().contains("entranceMeal_entrance.gas")||
				httpReq.getRequestURI().contains("entranceMeal_material_leave.gas")||
				httpReq.getRequestURI().contains("entranceMeal_it_devices.gas")||
				httpReq.getRequestURI().contains("entranceMeal_film.gas")||
				httpReq.getRequestURI().contains("entranceMeal_vehicle_entrance.gas")||
				httpReq.getRequestURI().contains("po_create.gas")||
				httpReq.getRequestURI().contains("po_delete.gas")||
				httpReq.getRequestURI().contains("businessTravelList.gas")||
				httpReq.getRequestURI().contains("businessTravelMgmt.gas")||
				httpReq.getRequestURI().contains("businessTravel_report.gas")||
				httpReq.getRequestURI().contains("businessTravel_file.gas")||
				httpReq.getRequestURI().contains("businessTravel_file_cmpx.gas")||
				httpReq.getRequestURI().contains("businessTravel_confirm_file.gas")||
				httpReq.getRequestURI().contains("commonOzPrint.gas")||
				httpReq.getRequestURI().contains("userSearchPopup.gas")||
				httpReq.getRequestURI().contains("mealMgmt.gas")||
				httpReq.getRequestURI().contains("jobPopup.gas")||
				httpReq.getRequestURI().contains("xos_img_preview.gas")||
				httpReq.getRequestURI().contains("xgs_img_preview.gas")||
				httpReq.getRequestURI().contains("fileAttach.gas")||
				httpReq.getRequestURI().contains("xgf06_view.gas")||
				httpReq.getRequestURI().contains("xbk06_view.gas")||
				httpReq.getRequestURI().contains("pop_corpList.gas")||
				httpReq.getRequestURI().contains("pop_resetPw.gas")
				)){

			if(SessionInfo.sessionCheck(httpReq)) {
				String paramToken = StringUtil.isNullToString(httpReq.getParameter(token)).equals("")?(String) httpReq.getParameter("hid_csrfToken"):(String) httpReq.getParameter(token);
				String sessToken = SessionInfo.getSess_token_key(httpReq);
				String refererUrl = StringUtil.isNullToString(httpReq.getHeader("REFERER"));
				String serverName = httpReq.getServerName();
				String tempTokenKey = StringUtil.getDocNo();
				//httpReq.setAttribute("csrfToken", tempTokenKey);
				httpReq.setAttribute(token, sessToken);

				logger.info("###########################################");
				logger.info("CSRSslFilter doFilter ="+httpReq.getRequestURI());
				logger.info("REFERER="+httpReq.getHeader("REFERER"));
				logger.info("getServerName="+httpReq.getServerName());
				logger.info("getRequestURI="+httpReq.getRequestURI());
				logger.info("paramToken ="+paramToken);
				logger.info("sessToken ="+sessToken);
				logger.info("SessionInfo.getSess_mstu_gubb(httpReq) ="+SessionInfo.getSess_mstu_gubb(httpReq));

				if(!refererUrl.contains(serverName) && !httpReq.getRequestURI().contains("main.gas")){
					throw new ServletException("Wrong Connection.");
				}

				if(!StringUtil.isNullToString(paramToken).equals(StringUtil.isNullToString(sessToken))){
					throw new ServletException("Wrong Connection.");
				}

				if(httpReq.getRequestURI().contains("/system/") && !(httpReq.getRequestURI().contains("xst09.gas") || httpReq.getRequestURI().contains("xst19.gas") || httpReq.getRequestURI().contains("xst09_pop.gas")) && !"M".equals(SessionInfo.getSess_mstu_gubb(httpReq))){
					throw new ServletException("Wrong Connection.");
				}
				//SessionInfo.setSessionChange(httpReq, tempTokenKey);
			}
		}

		if(doUrlCheckSsl(httpReq)){
			String area = StringUtil.getSystemArea().toUpperCase();
			if (area.equals("REAL")){
				/*
				redirectBasicToSsl(httpReq, httpRes);
				return;
				*/
				chain.doFilter(httpReq, httpRes);
			} else {
				chain.doFilter(httpReq, httpRes);
			}
		}else{
			chain.doFilter(httpReq, httpRes);
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {}

	/**
	 * Do url check ssl.
	 *
	 * @param req the req
	 * @return true, if successful
	 */
	private boolean doUrlCheckSsl(HttpServletRequest req){
		boolean isSsl = false;

		if (req.getScheme().equalsIgnoreCase("http")) {
			String uri = req.getRequestURI();
			if(uri.contains("/")|| uri.contains("index.htm") || uri.contains("indexQtest.htm") || uri.contains("indexMtest.htm") || uri.contains("hmbMailLogin.gas") || uri.contains("index_M001.htm")){
				isSsl = true;
			}
		}
		return isSsl;
	}

	/**
	 * Redirect basic to ssl.
	 *
	 * @param req the req
	 * @param res the res
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void redirectBasicToSsl(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if (req.getQueryString() == null) {
			res.sendRedirect("https://" + req.getServerName()+ req.getRequestURI());
		}
		else {
			res.sendRedirect("https://" + req.getServerName()+ req.getRequestURI() + "?" + req.getQueryString());
		}
	}

}
