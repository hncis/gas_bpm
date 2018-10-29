package org.uengine.ui.jsp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.UEngineException;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.ui.list.util.StringUtils;

/**
 * <pre>
 *  �� �� �� : ComJSPBase.java 
 *  �� �� : WorkItemHandlerJSPBase SuperClass 
 *  ���� �ۼ��� : 2007/03/28 
 *  Comments : 
 *  ��d���� :
 * </pre>
 * 
 * @version : 1.0
 * @author : �ŵ���
 */
public class ComJSPBase {

	/**
	 * Constructor of this class.
	 */
	public ComJSPBase() {
	}

	/**
	 * ���� �������� ������ Path�� ��ȯ�Ѵ�.
	 * 
	 * @param request
	 * @return url
	 */
	public String getPath(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url;
	}

	/**
	 * ���� �������� JSPID�� ��ȯ�Ѵ�.
	 * 
	 * @param request
	 * @return JSP ID
	 */
	public String getJspID(HttpServletRequest request) {
		String strpath = getPath(request);
		int headerIndex = strpath.indexOf(".jsp");
		String header = strpath.substring(0, headerIndex);
		int lastIndex = header.lastIndexOf("/");
		String retjsp = header.substring(lastIndex + 1);
		return retjsp;
	}

	/**
	 * Request��ü�� Key, value��; ��d�Ѵ�.
	 * 
	 * @param key
	 * @param value
	 * @param request
	 * @exception IllegalStateException
	 */
	public void setVar(String key, Object value, HttpServletRequest request)
			throws IllegalStateException {
		String JSPID = getJspID(request);
		request.setAttribute(JSPID + "_" + key, value);
	}

	/**
	 * Request��ü�� ���õǾ��ִ� ��; ��ȯ�Ѵ�.
	 * 
	 * @param key
	 * @param request
	 * @return Object
	 * @exception IllegalStateException
	 */
	public Object getVar(String key, HttpServletRequest request)
			throws IllegalStateException {
		String JSPID = getJspID(request);
		return (Object) (request.getAttribute(JSPID + "_" + key));
	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ���� ��d�Ѵ�.
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setProcVar(String key, Serializable value) throws Exception {
		if (piRemote != null && !StringUtils.isEmpty(key) && value != null) {
			piRemote.set("", key, value);
		}
	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ����; ��ȯ�Ѵ�.
	 * 
	 * @param key
	 * @return Serializable
	 * @throws Exception
	 */
	public Serializable getProcVar(String key) throws Exception {
		if (StringUtils.isEmpty(key))
			return null;
		Serializable retObj = null;
		if (pdRemote != null && piRemote != null) {
			ProcessVariable variable = pdRemote.getProcessVariable(key);

			if (variable == null)
				throw new UEngineException(
						"Undeclared process variable reference : " + key);

			ProcessVariableValue theValue = pdRemote.getProcessVariable(key)
					.getMultiple(piRemote, "");

			theValue.beforeFirst();
			if (theValue.size() == 1)
				retObj = theValue.getValue();
			else
				retObj = theValue;
		}

		return retObj;
	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ���� ��d�Ѵ�.
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setProcStrVar(String key, String value) throws Exception {
		setProcVar(key, value);
	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ����; ��ȯ�Ѵ�.
	 * 
	 * @param key
	 * @return value
	 * @throws Exception
	 */
	public String getProcStrVar(String key) throws Exception {
		Serializable obj = getProcVar(key);
		String str = null;
		if( obj instanceof Byte ){
			str = ((Byte)obj).toString(); 
		}else{
			str = String.valueOf(obj);
		}
		if( str != null && "null".equals(str) ){
			str = null;
		}
		return str;
	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ��; ��d�Ѵ�.
	 * 
	 * @param roleName
	 * @param endpoint
	 * @throws Exception
	 */
	public void setRoleMapping(String roleName, String endpoint)
			throws Exception {
//		if( !StringUtils.isEmpty(endpoint) ){
//			RoleMapping roleMapNew = getRoleMapping(roleName);
//			roleMapNew.remove();
//			//endpoint = null;
//		}
		RoleMapping roleMapNew = null;
		if (piRemote != null && !StringUtils.isEmpty(roleName)) {
			if( StringUtils.isEmpty(endpoint) ){
				RoleMapping roleMapOld = getRoleMapping(roleName);
				if( roleMapOld != null ){
					piRemote.putRoleMapping(roleName, roleMapNew);
//					RoleMapping roleMapOld2 = getRoleMapping(roleName);
//					if( roleMapOld2 == null ){
//						System.out.println("SUCCESS");
//					}
				}
			}else{
				roleMapNew = RoleMapping.create();
				roleMapNew.setName(roleName);
				roleMapNew.setEndpoint(endpoint);
				roleMapNew.fill(piRemote);
				
//				piRemote.set("", "_roleMapping_of_" + roleName, roleMapNew);
//				piRemote.putRoleMapping(roleMapNew);
				piRemote.putRoleMapping(roleName, roleMapNew);
			}
		}
		//piRemote.putRoleMapping(roleName, roleMapNew);
		//piRemote.set("", "_roleMapping_of_" + roleName, roleMapNew);
	}
	
//	public void setRoleMapping(String roleName, String endpoint)
//			throws RemoteException {
//		try {
//			if (pm != null && !StringUtils.isEmpty(instanceId) 
//					&& !StringUtils.isEmpty(roleName)
//					&& !StringUtils.isEmpty(endpoint)) {
//				pm.putRoleMapping(instanceId, roleName, endpoint);
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}

	/**
	 * �wμ����ν��Ͻ� ��ü�� ��; ��ȯ�Ѵ�.
	 * 
	 * @param roleName
	 * @return RoleMapping
	 * @throws Exception
	 */
	public RoleMapping getRoleMapping(String roleName) throws Exception {
		if (StringUtils.isEmpty(roleName))
			return null;
		RoleMapping retRm = null;
		if (piRemote != null) {
			retRm = piRemote.getRoleMapping(roleName);
		}
		return retRm;
	}

	/**
	 * SQL Connection ��ü�� ��ȯ�Ѵ�.
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getProcessConnection() throws Exception {
		Connection con = null;
		if (pm != null) {
			con = ((ProcessManagerBean) pm).getTransactionContext()
					.getConnection();
		}
		return con;
	}
	
	/**
	 * �wμ����ν��Ͻ� ��ü�� ��ȯ�Ѵ�.
	 * 
	 * @return ProcessInstance
	 * @throws RemoteException
	 */
	public ProcessInstance getProcInst() throws RemoteException {
		return piRemote;
	}

	// //////////////////headerMethods.jsp START/////////////////////////
	public String decode(String source) throws Exception {
		if (source == null)
			return null;

		return new String(source.getBytes("8859_1"), "UTF-8");
	}

	public Object notNull(Object value) throws Exception {
		if (value != null)
			return value;
		else
			return "-";
	}

	public String replaceAmp(String src) {
		if (src != null)
			return src.replaceAll("&", "&amp;");
		else
			return null;
	}

	// //////////////////headerMethods.jsp END//////////////////////////////////

	// //////////////////initialize.jsp START//////////////////////////////////
	public ProcessManagerRemote pm;
	
	public void setProcessManager(ProcessManagerRemote pm) {
		this.pm = pm;
	}

	public String instanceId;
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String processDefinition;
	
	public void setProcessDefinition(String processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String tracingTag;
	
	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}

	// added by bhhan 2007-06-17
	public String beforeTracingTag;
	/**
	 * @return Returns the beforeTracingTag.
	 */
	public String getBeforeTracingTag() {
		return beforeTracingTag;
	}

	/**
	 * @param beforeTracingTag The beforeTracingTag to set.
	 */
	public void setBeforeTracingTag(String beforeTracingTag) {
		this.beforeTracingTag = beforeTracingTag;
	}
	
	public boolean initiate;

	public boolean isViewMode;

	public boolean isMine;

	public ProcessInstance piRemote;

	public ProcessDefinition pdRemote;

	public String taskId;

	public String dispatchingOption;

	boolean isRacing;
	
	boolean isEventHandler;
	
	// added by bhhan 2007-05-31
	public boolean isFlowControl;
	
	// added by bhhan 2007-06-16
	public boolean isChangeStaus;
	// //////////////////initialize.jsp END//////////////////////////////////

	// //////////////////getLoggedUser.jsp START/////////////////////////////
	public String loggedUserId = "";

	public String loggedUserFullName = "";

	public boolean loggedUserIsAdmin = false;

	public String loggedUserJikName = "";

	public String loggedUserEmail = "";

	public String loggedUserPartCode = "";

	public String loggedUserMsnId = "";

	public String loggedUserEMailAddress = "";

	public String loggedUserLocale = "ko";

	public RoleMapping loggedRoleMapping = null;


	// //////////////////getLoggedUser.jsp END//////////////////////////////////

}
