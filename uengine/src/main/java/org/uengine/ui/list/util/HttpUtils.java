package org.uengine.ui.list.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;
import org.uengine.ui.list.datamodel.Constants;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.taglibs.input.ForEach;
import org.uengine.ui.taglibs.input.InputConstants;
import org.uengine.util.UEngineUtil;

/**
 * 
 * <pre>
 *    
 *    HttpServletRequest�� HttpSession�� ���Ͽ� /ƿ��Ƽ�� �޼ҵ�(Static)�� f���Ѵ�.
 *   
 * </pre>
 * 
 * @author
 * @since 2005. 3. 25.
 * @version 1.0
 */
public class HttpUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HttpUtils.class);

	/**
	 * Request �Ķ���� �� name�� �ش��ϴ� value�� ����.
	 * 
	 * <pre>
	 *   
	 *    ���� : name�� ���εǴ� value ���ڿ�; �����Ѵ�. 
	 *           ���� name�� ���εǴ� value ���ڿ��� ��; ���� ���(&quot;&quot;); �����Ѵ�.
	 *    
	 * </pre>
	 * 
	 * @param name
	 *            �˻��� value�� ���εǴ� Ű name
	 * @return name�� ���εǴ� value, ��; ���� ��� ����
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		if (request == null || name == null || name.equals(""))
			return "";

		String retStr = request.getParameter(name);

		if (retStr == null)
			return "";

		return retStr;
	}

	/**
	 * Request��ü�� �ش� key, value�� ��´�.
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setAttribute(HttpServletRequest request, String key,
			Object value) {
		if (request == null || key == null || value == null) {
			return;
		}
		request.setAttribute(key, value);
	}

	/**
	 * �ش� Key�� �ش��ϴ� Value�� String8�� ����
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getAttribute(HttpServletRequest request, String key) {
		String retStr = (String) request.getAttribute(key);
		if (retStr == null)
			retStr = "";
		return retStr;
	}

	/**
	 * Request �Ķ���� �� name�� �ش��ϴ� value�� ����.
	 * 
	 * <pre>
	 *   
	 *    ���� : name�� ���εǴ� value ���ڿ�; �����Ѵ�. 
	 *           ���� name�� ���εǴ� value ���ڿ��� ��; ���� 3��° ���ڸ� �����Ѵ�.
	 *    
	 * </pre>
	 * 
	 * @param name
	 *            �˻��� value�� ���εǴ� Ű name
	 * @return name�� ���εǴ� value, ��; ���� ����° ���� ����
	 */
	public static String getParameter(HttpServletRequest request, String name,
			String replace) {
		if (request == null || name == null || name.equals("")) {
			return replace;
		}

		String retStr = request.getParameter(name);

		if (retStr == null)
			return replace;

		return retStr;
	}

	/**
	 * Request���� Attribute(�Ӽ�) ���� ���� name�� �ش��ϴ� ��ü�� ��; ���� retObj�� �����Ѵ�.
	 */
	public static Object getAttribute(HttpServletRequest request, String name,
			Object retObj) {
		if (request == null || name == null || name.equals(""))
			return retObj;

		Object obj = request.getAttribute(name);

		if (obj == null) {
			return retObj;
		}
		return obj;
	}

	/**
	 * ���� HttpServletRequest�� ���Ե� Session name��; ���ڿ� �迭�� ���� ��; ���� null ����
	 */
	@Deprecated
	public static String[] getSessionAttributeNames(HttpServletRequest request) {
		
		
		if (request == null) {
			return null;
		}
		
		return getSessionAttributeNames(request.getSession(false));
		/*
		HttpSession session = request.getSession(false); // ���� ȹ��

		// session �� null �� ��� null ����
		if (session == null)
			return null;

		ArrayList alist = new ArrayList();
		String[] retArray = null;

		for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();)
			alist.add(e.nextElement());

		if (alist.size() == 0)
			return null; // Sesson�� ���Ե� attribute�� ��� ���

		// ArrayList -> String[]
		retArray = new String[alist.size()];
		for (int i = 0; i < alist.size(); i++)
			retArray[i] = (String) alist.get(i);

		Arrays.sort(retArray);
		return retArray;
		*/
	}
	/**
	 * ���� HttpSession�� ���Ե� Session name��; ���ڿ� �迭�� ���� ��; ���� null ����
	 */
	@Deprecated
	public static String[] getSessionAttributeNames(HttpSession session) {
		// session �� null �� ��� null ����
		if (session == null)
			return null;
		
		return getSessionAttributeNames(UEngineUtil.sessionToHashMap(session));
		/*
		if (session == null)
			return null;

		ArrayList alist = new ArrayList();
		String[] retArray = null;

		for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();)
			alist.add(e.nextElement());

		if (alist.size() == 0)
			return null; // Sesson�� ���Ե� attribute�� ��� ���

		// ArrayList -> String[]
		retArray = new String[alist.size()];
		for (int i = 0; i < alist.size(); i++)
			retArray[i] = (String) alist.get(i);

		Arrays.sort(retArray);
		return retArray;
		*/
	}
	
	public static String[] getSessionAttributeNames(HashMap map) {
		
		//HttpSession session = request.getSession(false); // ���� ȹ��

		// session �� null �� ��� null ����
		if (map == null)
			return null;

		ArrayList alist = new ArrayList();
		String[] retArray = null;

		Set<String> keys = map.keySet();
		for (String key : keys) {
			alist.add(key);
		}
		//for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();)
		//	alist.add(e.nextElement());

		if (alist.size() == 0)
			return null; // Sesson�� ���Ե� attribute�� ��� ���

		// ArrayList -> String[]
		retArray = new String[alist.size()];
		for (int i = 0; i < alist.size(); i++)
			retArray[i] = (String) alist.get(i);

		Arrays.sort(retArray);
		return retArray;
	}

	

	/**
	 * ���ǿ��� Attribute(�Ӽ�) ���� name �� ��; ���� 3��° ������ retObj�� �����Ѵ�.
	 */
	@Deprecated
	public static Object getSessionAttribute(HttpServletRequest request,
			String name, Object retObj) {
		if (request == null || name == null || name.equals("")) {
			return retObj;
		}

		return getSessionAttribute(UEngineUtil.sessionToHashMap(request.getSession(false)), name, retObj);
		/*
		HttpSession session = request.getSession(false); // ���� �� ���� ȹ�游..

		if (session == null) {
			return retObj;
		}

		Object obj = session.getAttribute(name);

		if (obj == null) {
			return retObj;
		}

		return obj;
		*/
	}
	/**
	 * ���ǿ��� Attribute(�Ӽ�) ���� name �� ��; ���� 3��° ������ retObj�� �����Ѵ�.
	 */
	@Deprecated
	public static Object getSessionAttribute(HttpSession session, String name,
			Object retObj) {
		if (session == null || name == null || name.equals("")) {
			return retObj;
		}

		return getSessionAttribute(UEngineUtil.sessionToHashMap(session), name, retObj);
		/*
		Object obj = session.getAttribute(name);

		if (obj == null) {
			return retObj;
		}

		return obj;
		*/
	}
	
	public static Object getSessionAttribute(HashMap map, String name, Object retObj) {
		if (map == null) {
			return retObj;
		}

		Object obj = map.get(name);

		if (obj == null) {
			return retObj;
		}

		return obj;
	}

	/**
	 * ���ǿ��� Attribute(�Ӽ�) ����
	 */
	@Deprecated
	public static void setSessionAttribute(HttpServletRequest request,
			String name, Object value) {
		if (request == null || name == null || name.equals("")) {
			return;
		}
		
		HttpSession session = request.getSession(false); // ���� �� ���� ȹ�游..

		if (session == null) {
			return;
		}

		session.setAttribute(name, value);
	}
	
	public static void setSessionAttribute(HashMap map,	String name, Object value) {
		if (map == null) {
			return;
		}

		map.put(name, value);
	}

	

	/**
	 * HttpServletRequest ���� Header �̸�; String �迭�� ���� Header�� ��; ��� null ����
	 */
	public static String[] getHeaderNames(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		ArrayList alist = new ArrayList();
		String[] retArray = null;

		for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();)
			alist.add(e.nextElement());

		if (alist.size() == 0)
			return null; // Sesson�� ���Ե� attribute�� ��� ���

		// ArrayList -> String[]
		retArray = new String[alist.size()];
		for (int i = 0; i < alist.size(); i++)
			retArray[i] = (String) alist.get(i);

		Arrays.sort(retArray);
		return retArray;
	}

	/**
	 * HttpServletRequest�� �̿��Ͽ� ���� ID ��� �1�.
	 * 
	 * @param request
	 * @return String ���� ID; ���� �� null ����
	 */
	@Deprecated
	public static String getSessionId(HttpServletRequest request) {
		if (request == null)
			return null;

		HttpSession session = request.getSession(false); // ���� �� �ɼ� ���� ���� ȹ��
		if (session == null) {
			return null;
		}

		return session.getId();
	}
	/**
	 * HttpSession ; �̿��Ͽ� ���� ID ��� �1�.
	 * 
	 * @param session
	 * @return String ���� ID; ���� �� null ����
	 */
	@Deprecated
	public static String getSessoinId(HttpSession session) {
		if (session == null) {
			return null;
		}

		return session.getId();
	}
	
	public static String getSessionId(HashMap map) {
		if (map == null) {
			return null;
		}

		return (String)map.get("appKey");
	}

	

	/**
	 * Request���� DATAMAP ��ȯ.
	 * 
	 * @param request HttpServletRequest.
	 * @param parameterDecoding �Ķ���� ���ڵ� ����.
	 * @return DATAMAP.
	 */
	public static DataMap getDataMap(HttpServletRequest request,
			boolean parameterDecoding) {
		if (request == null) {
			return null;
		}
		DataMap dm = (DataMap) request.getAttribute(Constants.DATAMAP);
		if (dm == null) {
			dm = new DataMap();
			dm.putAll(getParameterHashtable(request, parameterDecoding));
			setDataMap(request, dm);
		}
		return dm;
	}

	/**
	 * Request�� DATAMAP ��d.
	 * 
	 * @param request HttpServletRequest.
	 * @param dm DATAMAP.
	 */
	public static void setDataMap(HttpServletRequest request,
			DataMap dm) {
		if (request == null) {
			return;
		}
		request.setAttribute(Constants.DATAMAP, dm);
	}
	
	public static Hashtable getParameterHashtable(HttpServletRequest req,
			boolean parameterDecoding) {
		return getParameterHashtable(req, parameterDecoding, true, null, null);
	}
	
	/**
	 * Request���� Hashtable ��d �� ��ȯ.
	 * @param req HttpServletRequest.
	 * @param parameterDecoding �Ķ���� ���ڵ� ����.
	 * @return Parameter Hashtable.
	 */
	public static Hashtable getParameterHashtable(HttpServletRequest req,
			boolean parameterDecoding, boolean isUpperCaseKey, String encFrom, String encTo) {
		// collect input parameter and populate the hash table.
		Enumeration enumeration = req.getParameterNames();
		
		if( encFrom == null ){
			encFrom = "ISO-8859-1";
		}
		if( encTo == null ){
			encTo = "utf-8";
		}

		Hashtable ht = new Hashtable();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String[] values = req.getParameterValues(name);

			if (parameterDecoding && !Constants.USE_FILTER_ENCODING) {
				try {
					name = StringUtils.encode(name, encFrom, encTo);
				} catch (UnsupportedEncodingException e) {
					logger.error("getParameterHashtable(HttpServletRequest, boolean) - REQUEST GET PARAMETER PARSING ERROR:" + e.toString(), e); //$NON-NLS-1$
				}
			}

			if (values != null) {
				// String[] values = (String[])value1;
				if (parameterDecoding && !Constants.USE_FILTER_ENCODING) {
					for (int i = 0; i < values.length; i++) {
						try {
							values[i] = StringUtils.encode(values[i],
									encFrom, encTo);
						} catch (UnsupportedEncodingException e) {
							logger.error("getParameterHashtable(HttpServletRequest, boolean) - REQUEST GET PARAMETER PARSING ERROR:" + e.toString(), e); //$NON-NLS-1$
						}
					}
				}
				// isUpperCaseKey Check
				if(isUpperCaseKey){
					name = name.toUpperCase();
				}
				if (values.length > 1) {
					ht.put(name, values);
					// for (int i=0; i<values.length; i++)
					// System.out.println("---list---- " + values[i]);
				} else {
					ht.put(name, values[0]); // handle input
					// values as String[]
					// always
				}
			}
		}
		return ht;
	}

	public static boolean isMultipart(HttpServletRequest req) {
		String type = req.getContentType();

		if (type == null
				|| !type.toLowerCase().startsWith("multipart/form-data")) {
			return false;
		}
		return true;
	}

	public static String[] getParameter(String beanId, String name, String defaultValue, int viewMode, PageContext pageContext) throws JspException {
		
		 Object[] values= getParameterAsObject( beanId,  name,  defaultValue,  viewMode,  pageContext);
		 String[] strValues = new String[values.length];
		 for(int i=0; i<values.length; i++){
			 strValues[i] = quote(""+values[i]);
		 }
		 return strValues;
	}

	public static Object[] getParameterAsObject(String beanId, String name, String defaultValue, int viewMode, PageContext pageContext) throws JspException {
		name = name.toLowerCase();
		String beanValue;
		try {
			beanValue = (beanId != null ? UEngineUtil.beanPropertyValue(
					pageContext.findAttribute(beanId), name) : null);
		} catch (Exception e1) {
			new JspException(e1);
		}
		
		Object obj = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);

		Map mappedResult = (Map)pageContext.getAttribute("mappingResult",	PageContext.REQUEST_SCOPE);
		if (mappedResult!=null && mappedResult.containsKey(name)) { 
			obj = mappedResult.get(name);
		}

		if ( obj == null ) obj = pageContext.getRequest().getParameter(name);
				
		// �μ���.
		if ( viewMode == InputConstants.PRINT ) {
			//System.out.println("################ MAP=[" + pageContext.getRequest().getParameterMap() + "]");
			//System.out.println("################ NAME=[" + name + "]");
			//obj = GWUtil.toEncode(pageContext.getRequest().getParameter(name));
			try {
				//obj = GWUtil.toEncode( hanwha.commons.util.HttpUtil.getParameter( ( HttpServletRequest)pageContext.getRequest(), name) );
				obj = ( pageContext.getRequest().getParameter( new String(name.getBytes("UTF-8"), "8859_1") ) );
				//System.out.println("obj2=" + obj);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		Object[] multiValue = null;
		if (obj != null){
			if(!obj.getClass().isArray()) {
				multiValue = new Object[]{obj};
			}else{
				multiValue = (Object[]) obj;
			}
		}

		if (multiValue != null) {
/*			for(int i=0; i<multiValue.length; i++)
				multiValue[i] = multiValue[i];
*/		} else {
			if (defaultValue != null) beanValue = quote(defaultValue);
			else beanValue = "";
			
			multiValue = new Object[]{beanValue};
		}
		
		if(multiValue == null)
			multiValue = new Object[]{null};

		ForEach forEach = (ForEach) (pageContext.getAttribute("_forEach"));
		//find array values
		if (multiValue.length == 1 /*&& multiValue[0]!=null*/ && mappedResult != null) {
			int i=1; 
			Vector valueList = new Vector();
			
			if(UEngineUtil.isNotEmpty(""+multiValue[0]))
				valueList.add(multiValue[0]);
			else
				i=0;

			//bug fix by yookjy (checkbox addrow)
			if (forEach != null) {
				String variableName = forEach.getVariablename().toLowerCase();
				String postFix = null;
				if (mappedResult.get(variableName) != null && !UEngineUtil.isNotEmpty(""+multiValue[0])) {
					valueList.add(multiValue[0]);
				}
				for(int j=0; j<mappedResult.size(); j++) {
					postFix = "[" + (j + 1) + "]";;
					if (mappedResult.containsKey(variableName + postFix)) {
						valueList.add(mappedResult.get(name + postFix));
					}
				}
			} else {
				String valueKey = name + "[]";
				if (mappedResult.containsKey(valueKey)) {
					for (int k=i ; k < 200 ; k++) {
						valueKey = name + "["+k+"]";
						if(mappedResult.containsKey(valueKey)){
							valueList.add(mappedResult.get(valueKey));
						}
					}	
				}
			}
			
			if(valueList.size() > 0){
				multiValue = new Object[valueList.size()];
				valueList.toArray(multiValue);
			}
		}
		
		//narrowing for 'ForEach' scope 
		if (forEach != null) {
			int index = forEach.getCurrentIndex();

			//case of indexed by rolemapping
			if (multiValue[0] instanceof RoleMapping) {
				RoleMapping roleMapping = (RoleMapping)multiValue[0];
				if(roleMapping.size() <= index) 
					index = roleMapping.size()-1;
				
				roleMapping.setCursor(index);
				
				multiValue = new Object[]{roleMapping.getCurrentRoleMapping()};
			} else if (multiValue[0] instanceof ProcessVariableValue) {
				ProcessVariableValue processVariableValue = (ProcessVariableValue)multiValue[0];
				if(processVariableValue.size() <= index) 
					index = processVariableValue.size()-1;
				
				processVariableValue.setCursor(index);
				
				multiValue = new Object[] {processVariableValue.getValue()};
				
			} else { //case of indexed by array
				if(multiValue.length <= index) index = multiValue.length -1;
					multiValue = new Object[]{multiValue[index]};
			}
		}

		return multiValue;
	}


	public static String quote(String x) {
	    if (x == null)
	        return null;
	    else {
	        // deal with ampersands first so we can ignore the ones we add later
	        if ( x.length() == 0 ) return x;
	        x = UEngineUtil.replace(x, "&", "&amp;");
	        x = UEngineUtil.replace(x, "\"", "&quot;");
	        x = UEngineUtil.replace(x, "<", "&lt;");
	        x = UEngineUtil.replace(x, ">", "&gt;");
	        return x;
	    }
	}
	
	public static String toHTML(String s) {
		if (!UEngineUtil.isNotEmpty(s)) {
			s = "&nbsp;";
		} else {
			s = s
				.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;")
				.replaceAll("\n", "<br />")
				.replaceAll("\t", " &nbsp; &nbsp;")
			;
		}
		
		return s;
	}


}
