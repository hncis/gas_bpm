package org.uengine.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.UEngineException;

//import org.uengine.processdesigner.ProcessDesigner;


/**
 *
 * @author  <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version    $Id: ClientProxy.java,v 1.16 2011/07/22 07:33:14 curonide Exp $
 */
public class ClientProxy {

	private HttpClient httpClient;
	private String comCode;
	
	public HttpClient getHttpClient(){
		return httpClient;
	}
	
	public ClientProxy() throws Exception {
		String host = GlobalContext.getPropertyString("bpm_host", "localhost");
		String port = GlobalContext.getPropertyString("bpm_port", "8080");
		init(host, port);
	}
	
	public ClientProxy(String host, String port) throws Exception {
		init(host, port);
	}	
	
	private void init(String host, String port) throws Exception {
		System.out.println("init ClientProxy...");
		
		
		
		String userId = System.getProperty("bpm_userId", "209105");
		String passwd = System.getProperty("bpm_encryptedPasswd", "pwd");
				
		System.out.println("host : " + host + ", port : " + port + ", userId : " + userId);
		
		httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
		httpClient.getHostConfiguration().setHost(host, Integer.parseInt(port), "http");
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

 	}
	
	private InputStream executeMethodAsStream(HttpMethod method) throws Exception {
		InputStream is = null;
		System.out.println("ClientProxy.executeMethodAsStream");
		try {
			System.out.println("target url : "+ method.getPath());
			httpClient.executeMethod(method);
			System.out.println("ClientProxy.executeMethodAsStream2");
			is = method.getResponseBodyAsStream();
			System.out.println("ClientProxy.executeMethodAsStream3");
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		} finally {
		}
		return is;
	}
	
	
	private BufferedReader executeMethod(HttpMethod method) throws Exception {
		return new BufferedReader(
				new InputStreamReader(executeMethodAsStream(method), GlobalContext.ENCODING));
	}
	
	private String executeMethodAsString(HttpMethod method) throws Exception {
		
		System.out.println("target url   : "+ method.getPath());
		System.out.println("query string : "+ method.getQueryString());
		
		String result = null;
		try {
			httpClient.executeMethod(method);
			result = method.getResponseBodyAsString();
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	private NameValuePair[] createParameter(Map map) throws Exception {
		NameValuePair[] nvPair = new NameValuePair[map.size()];
		Iterator iter = map.keySet().iterator();
		for(int i=0; iter.hasNext(); i++) {
			String key = (String)iter.next();
			String value = (String)map.get(key);
			if ( value != null ) value = fromEncode(value);
			nvPair[i] = new NameValuePair(key, value);
		}
		return nvPair;
	}
	
	private static String fromEncode(String str) {
        if (str == null)
            return null;
        try {
			return new String(str.getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    }	
	
	public InputStream showProcessDefinitionWithDefinitionId(String id) throws Exception {
		System.out.println("showProcessDefinitionWithDefinitionId");
		System.out.println("showProcessDefinitionWithDefinitionId::"+id);
		String loaderURL = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/get/definition/xpd/"+id).replaceAll("//", "/");
		PostMethod method = 
			new PostMethod(loaderURL);
		return executeMethodAsStream(method);
	}
	
	public InputStream showProcessDefinitionWithVersionId(String id) throws Exception {
		System.out.println("showProcessDefinitionWithVersionId");
		System.out.println("showProcessDefinitionWithVersionId::"+id);
		String loaderURL = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/get/definition/xpd/version/"+id).replaceAll("//", "/");
		System.out.println(loaderURL);
		PostMethod method = 
			new PostMethod(loaderURL);
		return executeMethodAsStream(method);
	}
	
	public InputStream showProcessDefinitionWithInstanceId(String id) throws Exception {
		System.out.println("showProcessDefinitionWithInstanceId::"+id);
		String loaderURL = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/get/instance/xpd/"+id).replaceAll("//", "/");
		System.out.println(loaderURL);
		PostMethod method = 
			new PostMethod(loaderURL);
		return executeMethodAsStream(method);
	}
	
	public String saveProcessDefinition(Map formValueMap) throws Exception {
		String saverUrl = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/put/definition").replaceAll("//", "/");

		PostMethod method = 
				new PostMethod(saverUrl);
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(formValueMap);
		method.setRequestBody(body);
		method.setRequestHeader("Content-Type", "application/json;charset=utf-8");

		return executeMethodAsString(method);
	}
	
	public String changeProcessDefinition(Map map) throws Exception {
		String saverUrl = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/put/instance/definition").replaceAll("//", "/");

		PostMethod method = 
				new PostMethod(saverUrl);
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(map);
		method.setRequestBody(body);
		method.setRequestHeader("Content-Type", "application/json;charset=utf-8");

		return executeMethodAsString(method);
	}
	
	public BufferedReader getProcessDefinitionList() throws Exception {
		String saverUrl = (GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/get/list/xml/definition/"+comCode).replaceAll("//", "/");
		PostMethod method = 
			new PostMethod(saverUrl);
		BufferedReader reader = executeMethod(method);
		return reader;
	}
	
	public InputStream showFormDefinitionWithDefinitionId(String id) throws Exception {
		System.out.println("showFormDefinitionWithDefinitionId");
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/showFormDefinitionInLanguage.jsp?language=XPD&formDefId="+URLEncoder.encode(id, "UTF-8"));
		return executeMethodAsStream(method);
	}
	
	public InputStream showFormDefinitionWithVersionId(String id) throws Exception {
		System.out.println("showFormDefinitionWithVersionId");
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/showFormDefinitionWithVersionIdInLanguage.jsp?language=XPD&versionId="+id);
		return executeMethodAsStream(method);
	}

	public InputStream showObjectDefinitionWithDefinitionId(String id) throws Exception {
		System.out.println("showObjectDefinitionWithDefinitionId");
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/showObjectDefinition.jsp?objDefId="+id);
		return executeMethodAsStream(method);
	}	

	public InputStream openStream(String path) throws Exception {
		System.out.println("openStream");
		
		try{
			GetMethod method = 
				new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+path);
			return executeMethodAsStream(method);
		}catch(Exception e){
			throw new UEngineException("Fail to open url : " + getHttpClient().getHostConfiguration().getHostURL() + GlobalContext.WEB_CONTEXT_ROOT+path);
		}
	}

	public InputStream postStream(String path, Map formValueMap) throws Exception {
		System.out.println("postStream");
		try{
			PostMethod method = 
				new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+path);
			method.setRequestBody(createParameter(formValueMap));
			method.setRequestHeader("Content-Type", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE 
					+ "; charset=" + "UTF-8");
			return executeMethodAsStream(method);
		}catch(Exception e){
			throw new UEngineException("Fail to open url : " + getHttpClient().getHostConfiguration().getHostURL() + GlobalContext.WEB_CONTEXT_ROOT+path);
		}
	}

	
	public String saveFormDefinition(String defVerId, String definition) throws Exception {
		HashMap formValueMap = new HashMap();
		formValueMap.put("definition", definition);
		formValueMap.put("defVerId", defVerId);
		
		//String saverUrl = System.getProperty("saver_url",GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/saveProcessDefinition.jsp");
		
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/saveFormDefinitionWithDefinitionAndDefVerId.jsp");
		method.setRequestBody(createParameter(formValueMap));
		method.setRequestHeader("Content-Type", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE 
				+ "; charset=" + "UTF-8");

		return executeMethodAsString(method);
	}
	
	public String createFormDefinition(String name, String alias, String definition) throws Exception {
		HashMap formValueMap = new HashMap();
		formValueMap.put("name", name);
		formValueMap.put("alias", alias);
		formValueMap.put("definition", definition);
		
		//String saverUrl = System.getProperty("saver_url",GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/saveProcessDefinition.jsp");
		
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/saveFormDefinitionWithDefinitionAndDefVerId.jsp");
		method.setRequestBody(createParameter(formValueMap));
		method.setRequestHeader("Content-Type", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE 
				+ "; charset=" + "UTF-8");

		return executeMethodAsString(method);
	}
	
	public BufferedReader getProcessDefinition(){
		
		return null;
	}
	//mr.heo
	public BufferedReader getProcessDefinitionVersionList(NameValuePair[] defid) throws Exception {
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+"/admin/processmanager/processDefinitionVersionListXML.jsp");
		method.setRequestBody(defid);
		BufferedReader reader = executeMethod(method);
		return reader;
	}
	
	
	public BufferedReader getRootDept() throws Exception {
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/common/org/getGroupXML.jsp?deptId=root");
		System.out.println("[getDeptList] DEBUG1 : ");
		BufferedReader reader = executeMethod(method);
		System.out.println("[getDeptList] DEBUG2");
		return reader;
	}
	
	public BufferedReader getFormGroupList(int id) throws Exception {
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/admin/formmanager/getFormGroupXML.jsp?ownCompany="+id);
		System.out.println("[getFormGroupList] DEBUG1 : ");
		BufferedReader reader = executeMethod(method);
		System.out.println("[getFormGroupList] DEBUG2");
		return reader;
	}


	public BufferedReader getFormList(int id) throws Exception {
		GetMethod method = 
			new GetMethod(GlobalContext.WEB_CONTEXT_ROOT+"/admin/formmanager/getFormXML.jsp?id="+id);
		System.out.println("[getFormList] DEBUG1 : ");
		BufferedReader reader = executeMethod(method);
		System.out.println("[getFormList] DEBUG2");
		return reader;
	}
	
	public BufferedReader getRoleBasedRoleResolutionContext() throws Exception {
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+"/common/org/RoleBasedXML.jsp");
		BufferedReader reader = executeMethod(method);
		return reader;
	}
	
	public BufferedReader getGroupBasedRoleResolutionContext() throws Exception {
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+"/common/org/GroupBasedXML.jsp");
		//method.setRequestBody(useYN);
		BufferedReader reader = executeMethod(method);
		return reader;
	}
	
	
	public BufferedReader findByRoleID(NameValuePair[] roleid) throws Exception {
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+"/common/org/RoleBasedXML.jsp");
		method.setRequestBody(roleid);
		BufferedReader reader = executeMethod(method);
		return reader;
	}
	
	public String makeMime(Map map) throws Exception {
		PostMethod method = 
			new PostMethod(GlobalContext.WEB_CONTEXT_ROOT+"/modules/transfer/makeMime.jsp");
		method.setRequestBody(createParameter(map));
		method.setRequestHeader("Content-Type", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE 
				+ "; charset=" + "UTF-8");

		return executeMethodAsString(method);
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}	
	
	

}

/*
 * $Log: ClientProxy.java,v $
 * Revision 1.16  2011/07/22 07:33:14  curonide
 * codi engine sync
 *
 * Revision 1.15  2009/03/08 01:56:34  pongsor
 * Automatic Form Generation
 * Automatic Variable Generation
 *
 * Revision 1.14  2009/01/05 13:40:20  kmooje
 * *** empty log message ***
 *
 * Revision 1.13  2008/01/29 04:10:29  pongsor
 * *** empty log message ***
 *
 * Revision 1.12  2007/12/12 06:36:41  pongsor
 * *** empty log message ***
 *
 * Revision 1.11  2007/12/12 06:26:44  pongsor
 * *** empty log message ***
 *
 * Revision 1.10  2007/12/05 02:31:29  curonide
 * *** empty log message ***
 *
 * Revision 1.5  2007/12/04 07:34:42  bpm
 * *** empty log message ***
 *
 * Revision 1.3  2007/12/04 05:25:42  bpm
 * *** empty log message ***
 *
 * Revision 1.9  2007/10/03 16:39:37  pongsor
 * *** empty log message ***
 *
 * Revision 1.8  2007/08/27 04:21:45  pongsor
 * Transaction management
 *
 * Revision 1.7  2007/02/02 04:20:35  pongsor
 * *** empty log message ***
 *
 * Revision 1.5  2006/11/21 07:05:09  pongsor
 * *** empty log message ***
 *
 * Revision 1.4  2006/11/08 07:35:10  yongheekim
 * *** empty log message ***
 *
 * Revision 1.3  2006/09/02 06:28:42  pongsor
 * *** empty log message ***
 *
 * Revision 1.2  2006/07/07 01:53:23  pongsor
 * 1. Database Synchronized Process Variable
 * 2. Customizable Instance List Feature is added
 * 3. 'Key' field name has been renamed as 'KeyName' in the ProcessVariable table since the 'key' is a reserved word in MySQL.
 *
 * Revision 1.1  2006/06/17 07:51:43  pongsor
 * uEngine 2.0
 *
 * Revision 1.18  2006/01/20 03:56:06  mrheo
 * getchilddeptlist, getrootdept �߰�
 *
 * Revision 1.17  2006/01/06 16:23:46  phz
 * *** empty log message ***
 *
 * Revision 1.16  2006/01/06 16:02:47  phz
 * *** empty log message ***
 *
 * Revision 1.15  2006/01/04 12:30:46  ghbpark
 * *** empty log message ***
 *
 * Revision 1.14  2006/01/04 01:34:54  mrheo
 * �׷�8�� ��d
 *
 * Revision 1.13  2005/12/29 00:43:39  uengine
 * 1. Hot process definition deploy
 * 2. Event Driven Sub Process�� event Handler activity�� ���� instanceó��
 *
 * Revision 1.12  2005/12/23 11:34:44  southshine
 * v�� �����Ͱ� ū���� ����ֱ⶧���� int --> long, Integer --> BigDecimal �� ��ü�Ͽ�=
 *
 * Revision 1.11  2005/12/15 05:02:14  mrheo
 * *** empty log message ***
 *
 * Revision 1.10  2005/12/10 06:40:15  mrheo
 * *** empty log message ***
 *
 * Revision 1.9  2005/11/22 08:29:13  mrheo
 * *** empty log message ***
 *
 * Revision 1.8  2005/11/15 09:47:43  mrheo
 * *** empty log message ***
 *
 * Revision 1.7  2005/11/01 04:09:44  mahler
 * *** empty log message ***
 *
 * Revision 1.6  2005/10/14 08:23:26  ghbpark
 * *** empty log message ***
 *
 * Revision 1.5  2005/10/14 07:51:58  uengine
 * *** empty log message ***
 *
 * Revision 1.4  2005/10/10 01:17:13  ghbpark
 * FormLink �۾� 1�� �Ϸ�
 *
 * Revision 1.3  2005/09/13 10:31:44  ghbpark
 * ClientProxy -> Singleton 8�� ���� �� ���� ó�� �߰�
 *
 * Revision 1.2  2005/09/12 01:56:56  ghbpark
 * /hwbpm 8�� ����
 *
 * Revision 1.1  2005/09/06 07:08:17  ghbpark
 * EagleBPM 2.0 start
 *
 * Revision 1.1  2005/04/11 10:29:17  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/04/03 02:34:12  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2005/02/11 07:28:23  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2005/01/11 09:10:28  ghbpark
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 01:01:06  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2005/01/10 03:04:27  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2004/12/28 11:24:43  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2004/12/28 09:05:19  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2004/12/28 02:43:38  ghbpark
 * *** empty log message ***
 *
 * Revision 1.1  2004/12/28 02:09:56  ghbpark
 * *** empty log message ***
 *
 * Revision 1.3  2004/09/07 23:30:27  ghbpark
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/19 04:21:57  ghbpark
 * ��, ��å ȣ�� �κ� ��� ��d
 *
 * Revision 1.1  2004/08/16 10:31:13  ghbpark
 * *** empty log message ***
 *
 */