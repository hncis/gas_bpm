package org.uengine.ui.jsp;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.RemoveException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.HttpJspPage;

import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.util.UEngineUtil;

public abstract class WorkItemHandlerJSPBase extends ComJSPBase implements
		Servlet, HttpJspPage {

	/**
	 * WorkItemHandlerJSPBase Constructor
	 */
	public WorkItemHandlerJSPBase() {
		super();
	}

	/**
	 * Servlet�� LifeCycle Init Method
	 * 
	 * @param servletconfig
	 * @exception ServletException
	 */
	public final void init(ServletConfig servletconfig) throws ServletException {
		_servletConfig = servletconfig;

		try {
			jspInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Servlet initialization.
	 */
	public void jspInit() {
	}

	public final void service(ServletRequest servletrequest,
			ServletResponse servletresponse) throws ServletException,
			IOException {
		HttpServletRequest request = (HttpServletRequest) servletrequest;
		HttpServletResponse response = (HttpServletResponse) servletresponse;
		
		// bhhan for transaction test works.
/////////////////////////////////////////////////////////////////
//		InitialContext context = null;
//		UserTransaction tx = null;
//		try {
//			System.out.println("=====================> Transaction ��....");
//			context = new InitialContext();
//			tx = (UserTransaction)context.lookup("java:comp/UserTransaction");
//		} catch (NamingException e1) {
//			e1.printStackTrace();
//		}
/////////////////////////////////////////////////////////////////		

		try {
			//tx.begin();
			doBefore(request);

			_jspService(request, response);

			doAfter(request);

			pm.applyChanges(); // commit.
			doSuccess(request, response);
//			bhhan 2007-06-07
			//tx.commit();
		} catch (Exception e) {
			doError(e, request, response);
			pm.cancelChanges(); // rollback.
			//bhhan 2007-06-07
//			try {
//				System.out.println("=====================> Transaction ���� rollback....");
//				tx.rollback();
//			} catch (IllegalStateException e1) {
//				e1.printStackTrace();
//			} catch (SecurityException e1) {
//				e1.printStackTrace();
//			} catch (SystemException e1) {
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if(pm!=null) pm.remove(); // close.
			} catch (RemoteException e) {
				e.printStackTrace();
				throw new ServletException(e.getMessage());
			} catch (RemoveException e) {
				e.printStackTrace();
				throw new ServletException(e.getMessage());
			}
		}
	}
	
	public void doBefore(HttpServletRequest request) throws Exception {
		setLoggedUserInfo(request);
		setInitializeProc(request);
	}
	
	public void doAfter(HttpServletRequest request) throws Exception {
	}
	
	public void doSuccess(HttpServletRequest request,
			HttpServletResponse response) {
	}
	
	public void doError(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
	}

	@Deprecated
	public void setLoggedUserInfo(HttpServletRequest request) throws Exception {
		setLoggedUserInfo(UEngineUtil.sessionToHashMap(request.getSession()));
		
		/*HttpSession session = request.getSession();
		loggedUserId = (String) session.getAttribute("loggedUserId");
		loggedUserFullName = (String) session
				.getAttribute("loggedUserFullName");
		loggedUserIsAdmin = "1".equals((String) session
				.getAttribute("loggedUserIsAdmin"));
		loggedUserJikName = (String) session.getAttribute("loggedUserJikName");
		loggedUserEmail = (String) session.getAttribute("loggedUserEmail");
		loggedUserPartCode = (String) session
				.getAttribute("loggedUserPartCode");
		loggedUserMsnId = (String) session.getAttribute("loggedUserMsnId");

		if (loggedUserId == null)
			throw new Exception("Logging in required.");

		loggedUserEMailAddress = "";
		loggedUserLocale = "ko";

		loggedRoleMapping = RoleMapping.create();
		loggedRoleMapping.setEndpoint(loggedUserId);
		loggedRoleMapping.setResourceName(loggedUserFullName);
		loggedRoleMapping.setEmailAddress(loggedUserEMailAddress);
		loggedRoleMapping.setMale(true);
		loggedRoleMapping.setBirthday(new java.util.Date());
		*/
	}
	public void setLoggedUserInfo(HashMap map) throws Exception {
		//HttpSession session = request.getSession();
		
		loggedUserId = (String) map.get("loggedUserId");
		loggedUserFullName = (String) map.get("loggedUserFullName");
		loggedUserIsAdmin = "1".equals((String) map.get("loggedUserIsAdmin"));
		loggedUserJikName = (String) map.get("loggedUserJikName");
		loggedUserEmail = (String) map.get("loggedUserEmail");
		loggedUserPartCode = (String) map.get("loggedUserPartCode");
		loggedUserMsnId = (String) map.get("loggedUserMsnId");

		if (loggedUserId == null)
			throw new Exception("Logging in required.");

		loggedUserEMailAddress = "";
		loggedUserLocale = "ko";

		loggedRoleMapping = RoleMapping.create();
		loggedRoleMapping.setEndpoint(loggedUserId);
		loggedRoleMapping.setResourceName(loggedUserFullName);
		loggedRoleMapping.setEmailAddress(loggedUserEMailAddress);
		loggedRoleMapping.setMale(true);
		loggedRoleMapping.setBirthday(new java.util.Date());
	}
	
	public void setInitializeProc(HttpServletRequest request) throws Exception {
		ProcessDefinitionRemote pdr = null;

		// useBean
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();

		pm = processManagerFactory.getProcessManager();

		instanceId = decode(request.getParameter("instanceId"));
		processDefinition = decode(request.getParameter("processDefinition"));
		tracingTag = request.getParameter("tracingTag");

		initiate = "yes".equals(request.getParameter("initiate"))
				|| "yes".equals(request.getParameter("isEventHandler"));
		
		isViewMode = "yes".equals(request.getParameter("isViewMode"));
		isMine = initiate || "yes".equals(request.getParameter("isMine"));
		taskId = request.getParameter("taskId");

		dispatchingOption = request
				.getParameter(KeyedParameter.DISPATCHINGOPTION);

		isRacing = ("" + Role.DISPATCHINGOPTION_RACING)
				.equals(dispatchingOption);

		if (!initiate)
			piRemote = pm.getProcessInstance(instanceId);

		if (piRemote != null)
			pdRemote = piRemote.getProcessDefinition();
	}

	public final void destroy() {
		jspDestroy();
	}

	public void jspDestroy() {
	}

	/**
	 * getServletConfig
	 * 
	 * @return ServletConfig
	 */
	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	public String getServletInfo() {
		return "Generated JSP Servlet:CLASS= [" + getClass().getName() + "]";
	}

	protected ServletConfig _servletConfig;

	public abstract void _jspService(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws ServletException,
			IOException;
}
