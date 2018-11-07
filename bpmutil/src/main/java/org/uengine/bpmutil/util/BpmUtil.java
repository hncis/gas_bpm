package org.uengine.bpmutil.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.uengine.bpmutil.vo.ProcessRole;
import org.uengine.bpmutil.vo.ProcessRoleId;
import org.uengine.bpmutil.vo.ProcessVariable;
import org.uengine.bpmutil.vo.ProcessVariableValue;
import org.uengine.bpmutil.vo.ServiceVO;

import com.google.gson.Gson;

public class BpmUtil {

	/* 사용자 권한 행위 수행코드 */
	public static final String ACTION_CODE_COMELETE         		= "01";	//01: 완료
	public static final String ACTION_CODE_SAVEONLY         		= "02";	//02: 저장
	public static final String ACTION_CODE_COLLECTION       		= "03";	//03: 회수
	public static final String ACTION_CODE_REJECT           		= "04";	//04: 반려
	public static final String ACTION_CODE_DELEGATE         		= "05";	//05: 위임
	
	/* 프로세스 제어 수행코드 */
    public static final String ACTION_CODE_PROCESS_DELETE   		= "06";	//06: 프로세스 삭제
    public static final String ACTION_CODE_PROCESS_COMPLETE 		= "07";	//07: 프로세스 완료
    public static final String ACTION_CODE_PROCESS_STOP     		= "08";	//08: 프로세스 중지
    public static final String ACTION_CODE_PROCESS_CANCEL   		= "09";	//09: 프로세스 취소
    public static final String ACTION_CODE_PROCESS_RESTART  		= "10";	//10: 프로세스 재시작
    public static final String ACTION_CODE_PROCESS_CHANGE_DUEDATE	= "11";	//11: 프로세스 처리기한 일자 변경
	
	/* 관리자 권한 행위 수행코드 */
	public static final String ACTION_CODE_ACTIVITY_COMPENSATE		= "12";	//12: 되돌리기
	public static final String ACTION_CODE_ACTIVITY_SUSPEND			= "13";	//13: 일시중지
	public static final String ACTION_CODE_ACTIVITY_RESUME			= "14";	//14: 재개
	public static final String ACTION_CODE_ACTIVITY_SKIP			= "15";	//15: 건너뛰기
	static boolean isNds = false;
	private static final Map<String, String> propertiesMap = propertiesValueMap(isNds);

	private static final String LITERAL_WORKFLOW_URL = "workflow_url";
	private static final String LITERAL_POST = "POST";
	private static final String LITERAL_CONTENT_TYPE_NAME = "Content-Type";
	private static final String LITERAL_CONTENT_TYPE_VALUE = "application/json;charset=utf-8";
	private static final String LITERAL_UTF8 = "utf-8";
	private static final String LITERAL_SERVER_OUTPUT = "Output from Server .... \n";	
	
    private static final String WORKFLOW_URL = getProperties().getProperty(propertiesMap.get(LITERAL_WORKFLOW_URL), "http://105.13.8.62:7070/bpm/service/workflow/");
    private static final String WORKFLOW_INSTANCE_URL = getProperties().getProperty(propertiesMap.get("workflow_instance_url"), "http://105.13.8.62:7070/bpm/service/instance/list/");
    private static final String WORKFLOW_WORKLIST_URL = getProperties().getProperty(propertiesMap.get("workflow_worklist_url"), "http://105.13.8.62:7070/bpm/service/worklist/list/");
    private static final String WORKFLOW_COMMENT_URL = getProperties().getProperty(propertiesMap.get("workflow_comment_url"), "http://105.13.8.62:7070/bpm/service/comment/list/");
    private static final String WORKFLOW_AUTHORITY_URL = getProperties().getProperty(propertiesMap.get("workflow_authority_url"), "http://105.13.8.62:7070/bpm/service/authority/get/");
	private static Properties properties;
    
   public static Map<String, String> propertiesValueMap(boolean isNds){
	   Map<String, String> propertiesMap = new HashMap<String, String>();
	   if(isNds){
		   propertiesMap.put(LITERAL_WORKFLOW_URL, "nds.url");
		   propertiesMap.put("workflow_instance_url", "nds.instance.url");
		   propertiesMap.put("workflow_worklist_url", "nds.worklist.url");
		   propertiesMap.put("workflow_comment_url", "nds.comment.url");
		   propertiesMap.put("workflow_authority_url", "nds.authority.url");
	   }else{
		   propertiesMap.put(LITERAL_WORKFLOW_URL, "bpm.url");
		   propertiesMap.put("workflow_instance_url", "bpm.instance.url");
		   propertiesMap.put("workflow_worklist_url", "bpm.worklist.url");
		   propertiesMap.put("workflow_comment_url", "bpm.comment.url");
		   propertiesMap.put("workflow_authority_url", "bpm.authority.url");
	   }
	   System.out.println("bpm.url: " + propertiesMap.get(LITERAL_WORKFLOW_URL)); // NOPMD - The BPM API does not know the Logger of the site-specific application
	   return propertiesMap;
   }
	 
	public static URL getResourceURL(String path) throws ClassNotFoundException{
		URL url= BpmUtil.class.getClassLoader().getResource(path);
		return  url;
	}
    
    public static Properties getProperties() {
		if(properties==null){
			try {
				URL url = null;
								
				url = getResourceURL("bpm_settings.properties");

				if (url != null) {
					InputStream is = url.openStream();
					properties = new Properties();
					properties.load(is);
					is.close();
					
					if(properties.containsKey("content.reference.url")){
						String realFileURL = (String) properties.get("content.reference.url");
						is = new URL(realFileURL).openStream();
						properties.load(is);
						is.close();
					}

					System.out.println("Loading " + url); // NOPMD - The BPM API does not know the Logger of the site-specific application
				}
			}
			catch (Exception e) {
				e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
			}
		}

		return properties;
	}

    private static String sendBpm(ServiceVO serviceVO) throws IOException {
        String result = null;

        try {

            String body = new Gson().toJson(serviceVO);
            System.out.println(body); // NOPMD - The BPM API does not know the Logger of the site-specific application
            URL postUrl = new URL(WORKFLOW_URL);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
            connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
            connection.setRequestMethod(LITERAL_POST);
            connection.setRequestProperty(LITERAL_CONTENT_TYPE_NAME, LITERAL_CONTENT_TYPE_VALUE);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            result = bos.toString(LITERAL_UTF8);

            System.out.println(LITERAL_SERVER_OUTPUT); // NOPMD - The BPM API does not know the Logger of the site-specific application
            System.out.println(result); // NOPMD - The BPM API does not know the Logger of the site-specific application

            connection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (IOException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        }

        return result;
    }

    //2016-11-29 freshka
    public static String completeTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String comment) throws IOException {
        return completeTask(processCode, bizKey, statusCode, userId, roles, procVars, comment, false);
    }
    //2016-11-29 freshka
    public static String completeTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String comment, boolean isSubProcess) throws IOException {

        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_COMELETE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);

        serviceVO.setProcessRoles(setProcessRoles(roles));
        serviceVO.setProcessVariables(setProcessVariables(procVars));
        
        serviceVO.setComment(comment);
        serviceVO.setIsSubProcess(isSubProcess);

        return sendBpm(serviceVO);

    }

    //2016-11-29 freshka
    public static String saveTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars) throws IOException {
        return saveTask(processCode, bizKey, statusCode, userId, roles, procVars, false);
    }
    //2016-11-29 freshka
    public static String saveTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, boolean isSubProcess) throws IOException {

        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_SAVEONLY);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);

        serviceVO.setProcessRoles(setProcessRoles(roles));
        serviceVO.setProcessVariables(setProcessVariables(procVars));
        
        serviceVO.setIsSubProcess(isSubProcess);

        return sendBpm(serviceVO);

    }

    //2016-11-29 freshka
    public static String collectTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars) throws IOException {
        return collectTask(processCode, bizKey, statusCode, userId, roles, procVars, false);
    }
    //2016-11-29 freshka
    public static String collectTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, boolean isSubProcess) throws IOException {

        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_COLLECTION);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);

        serviceVO.setProcessRoles(setProcessRoles(roles));
        serviceVO.setProcessVariables(setProcessVariables(procVars));
        
        serviceVO.setIsSubProcess(isSubProcess);

        return sendBpm(serviceVO);

    }

    //2016-11-29 freshka
    public static String rejectTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String comment) throws IOException {
        return rejectTask(processCode, bizKey, statusCode, userId, roles, procVars, comment, false);
    }
    //2016-11-29 freshka
    public static String rejectTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String comment, boolean isSubProcess) throws IOException {

        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_REJECT);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setComment(comment);

        serviceVO.setProcessRoles(setProcessRoles(roles));
        serviceVO.setProcessVariables(setProcessVariables(procVars));
        
        serviceVO.setIsSubProcess(isSubProcess);

        return sendBpm(serviceVO);

    }

    //2016-11-29 freshka
    public static String deleagteTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String delegatorRoleCode, List<String> delegators) throws IOException {
        return deleagteTask(processCode, bizKey, statusCode, userId, roles, procVars, delegatorRoleCode, delegators, true, false);
    }
    //2016-11-29 freshka
    public static String deleagteTask(String processCode, String bizKey, String statusCode, String userId, Map<String, List<String>> roles, Map<String, List<String>> procVars, String delegatorRoleCode, List<String> delegators, boolean isSystemCall, boolean isSubProcess)
            throws IOException {

        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_DELEGATE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);

        serviceVO.setProcessRoles(setProcessRoles(roles));
        serviceVO.setProcessVariables(setProcessVariables(procVars));
        serviceVO.setDelegateRoleCode(delegatorRoleCode);
        serviceVO.setDelegateIds(setDelegators(delegators));
        serviceVO.setIsSystemCall(isSystemCall);
        serviceVO.setIsSubProcess(isSubProcess);

        return sendBpm(serviceVO);

    }

    //2016-12-27 freshka
    public static String deleteProcess(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return deleteProcess(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-27 freshka
    public static String deleteProcess(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_DELETE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-27 freshka
    public static String stopProcess(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return stopProcess(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-27 freshka
    public static String stopProcess(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_STOP);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-27 freshka
    public static String cancelProcess(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return cancelProcess(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-27 freshka
    public static String cancelProcess(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_CANCEL);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-27 freshka
    public static String restartProcess(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return restartProcess(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-27 freshka
    public static String restartProcess(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_RESTART);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2017-03-06 chonk
    public static String changeDueDateProcess(String processCode, String bizKey, String dueDate, String userId) throws IOException {
        return changeDueDateProcess(processCode, bizKey, dueDate, userId, false);
    }
    //2017-03-06 chonk
    public static String changeDueDateProcess(String processCode, String bizKey, String dueDate, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_CHANGE_DUEDATE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setDueDate(dueDate);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-27 freshka
    public static String completeProcess(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return completeProcess(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-27 freshka
    public static String completeProcess(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_PROCESS_COMPLETE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }
    

    //2016-12-26 freshka
    public static String compensateTask(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return compensateTask(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-26 freshka
    public static String compensateTask(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_ACTIVITY_COMPENSATE);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-26 freshka
    public static String suspendTask(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return suspendTask(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-26 freshka
    public static String suspendTask(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_ACTIVITY_SUSPEND);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-26 freshka
    public static String resumeTask(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return resumeTask(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-26 freshka
    public static String resumeTask(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_ACTIVITY_RESUME);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    //2016-12-26 freshka
    public static String skipTask(String processCode, String bizKey, String statusCode, String userId) throws IOException {
        return skipTask(processCode, bizKey, statusCode, userId, false);
    }
    //2016-12-26 freshka
    public static String skipTask(String processCode, String bizKey, String statusCode, String userId, boolean isSubProcess) throws IOException {
        ServiceVO serviceVO = new ServiceVO();
        serviceVO.setActionCode(ACTION_CODE_ACTIVITY_SKIP);
        serviceVO.setProcessCode(processCode);
        serviceVO.setStatusCode(statusCode);
        serviceVO.setBizKey(bizKey);
        serviceVO.setLoginUserId(userId);
        serviceVO.setIsSubProcess(isSubProcess);
        return sendBpm(serviceVO);
    }

    
    private static List<ProcessRoleId> setDelegators(List<String> delegators) {

        List<ProcessRoleId> delegateRoles = new ArrayList<ProcessRoleId>();

        if (delegators != null && delegators.size() > 0) {
            Iterator<String> i = delegators.iterator();
            while (i.hasNext()) {
                ProcessRoleId roleId = new ProcessRoleId();
                roleId.setRoleId(i.next());
                delegateRoles.add(roleId);
            }
        }

        return delegateRoles;
    }

    private static List<ProcessRole> setProcessRoles(Map<String, List<String>> roles) {
        List<ProcessRole> processRoles = new ArrayList<ProcessRole>();
        if (roles != null && roles.size() > 0) {

            Iterator<String> roleKeys = roles.keySet().iterator();
            while (roleKeys.hasNext()) {

                String roleName = roleKeys.next();
                ProcessRole processRole = new ProcessRole();
                processRole.setRoleName(roleName);

                List<ProcessRoleId> roleIds = new ArrayList<ProcessRoleId>();
                Iterator<String> roleIdStrings = roles.get(roleName).iterator();
                while (roleIdStrings.hasNext()) {
                    String roleIdString = roleIdStrings.next();

                    ProcessRoleId roleId = new ProcessRoleId();
                    roleId.setRoleId(roleIdString);
                    roleIds.add(roleId);
                }
                processRole.setRoleIds(roleIds);

                processRoles.add(processRole);

            }
        }
        return processRoles;
    }

    private static List<ProcessVariable> setProcessVariables(Map<String, List<String>> vars) {
        List<ProcessVariable> processVariables = new ArrayList<ProcessVariable>();
        if (vars != null && vars.size() > 0) {

            Iterator<String> varKeys = vars.keySet().iterator();
            while (varKeys.hasNext()) {

                String varName = varKeys.next();
                ProcessVariable processVariable = new ProcessVariable();
                processVariable.setVarName(varName);

                List<ProcessVariableValue> procVars = new ArrayList<ProcessVariableValue>();
                Iterator<String> valueStrings = vars.get(varName).iterator();
                while (valueStrings.hasNext()) {
                    String valueString = valueStrings.next();

                    ProcessVariableValue pvv = new ProcessVariableValue();
                    pvv.setVarValue(valueString);
                    procVars.add(pvv);
                }
                processVariable.setVarValues(procVars);

                processVariables.add(processVariable);

            }
        }

        return processVariables;
    }
    
    public static String getComments(String processCode, String bizKey) throws Exception{
    	String result = null;

        try {

            String body = new Gson().toJson(new ServiceVO());
            System.out.println(body); // NOPMD - The BPM API does not know the Logger of the site-specific application
            URL postUrl = new URL(WORKFLOW_COMMENT_URL+processCode+"/"+bizKey);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
            connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
            connection.setRequestMethod(LITERAL_POST);
            connection.setRequestProperty(LITERAL_CONTENT_TYPE_NAME, LITERAL_CONTENT_TYPE_VALUE);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            result = bos.toString(LITERAL_UTF8);

            System.out.println(LITERAL_SERVER_OUTPUT); // NOPMD - The BPM API does not know the Logger of the site-specific application
            System.out.println(result); // NOPMD - The BPM API does not know the Logger of the site-specific application

            connection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (IOException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        }

        return result;
    }

    public static String getInstances(String userId) throws Exception{
    	String result = null;

        try {

            String body = new Gson().toJson(new ServiceVO());
            System.out.println(body); // NOPMD - The BPM API does not know the Logger of the site-specific application
            URL postUrl = new URL(WORKFLOW_INSTANCE_URL+userId);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
            connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
            connection.setRequestMethod(LITERAL_POST);
            connection.setRequestProperty(LITERAL_CONTENT_TYPE_NAME, LITERAL_CONTENT_TYPE_VALUE);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            result = bos.toString(LITERAL_UTF8);

            System.out.println(LITERAL_SERVER_OUTPUT); // NOPMD - The BPM API does not know the Logger of the site-specific application
            System.out.println(result); // NOPMD - The BPM API does not know the Logger of the site-specific application

            connection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (IOException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        }

        return result;
    }

    public static String getWorklist(String userId) throws Exception{
    	String result = null;

        try {

            String body = new Gson().toJson(new ServiceVO());
            System.out.println(body); // NOPMD - The BPM API does not know the Logger of the site-specific application
            URL postUrl = new URL(WORKFLOW_WORKLIST_URL+userId);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
            connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
            connection.setRequestMethod(LITERAL_POST);
            connection.setRequestProperty(LITERAL_CONTENT_TYPE_NAME, LITERAL_CONTENT_TYPE_VALUE);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            result = bos.toString(LITERAL_UTF8);

            System.out.println(LITERAL_SERVER_OUTPUT); // NOPMD - The BPM API does not know the Logger of the site-specific application
            System.out.println(result); // NOPMD - The BPM API does not know the Logger of the site-specific application

            connection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        } catch (IOException e) {
            e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
            throw e;
        }

        return result;
    }

    public static String getAuthority(String processCode, String bizKey, String statusCode, String userId) throws Exception{
    	String result = null;
    	
    	try {
    		
    		String body = new Gson().toJson(new ServiceVO());
    		System.out.println(body); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		URL postUrl = new URL(WORKFLOW_AUTHORITY_URL+processCode+"/"+bizKey+"/"+statusCode+"/"+userId);
    		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
    		connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
    		connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
    		connection.setRequestMethod(LITERAL_POST);
    		connection.setRequestProperty(LITERAL_CONTENT_TYPE_NAME, LITERAL_CONTENT_TYPE_VALUE);
    		OutputStream os = connection.getOutputStream();
    		os.write(body.getBytes());
    		os.flush();
    		
    		InputStream is = connection.getInputStream();
    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		byte[] buffer = new byte[1024];
    		int len;
    		while ((len = is.read(buffer)) != -1) {
    			bos.write(buffer, 0, len);
    		}
    		
    		result = bos.toString(LITERAL_UTF8);
    		
    		System.out.println(LITERAL_SERVER_OUTPUT); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		System.out.println(result); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		
    		connection.disconnect();
    	} catch (ProtocolException e) {
    		e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		throw e;
    	} catch (MalformedURLException e) {
    		e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		throw e;
    	} catch (IOException e) {
    		e.printStackTrace(); // NOPMD - The BPM API does not know the Logger of the site-specific application
    		throw e;
    	}
    	
    	return result;
    }

    public static void main(String[] args) throws Exception {
//        String processCode = "NYJPSWN01410000";
//        String bizKey = "201739902980003505";
//        String statusCode = "SWNBZ01410030";
//        String loginUserId = "oooohjjun";
//
//        // role
//        Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
////         List<String> roleList = new ArrayList<String>();
////         roleList.add("00874");
////         roleMap.put("UtilizeDirector", roleList);
////         List<String> retreeList = new ArrayList<String>();
////         retreeList.add("test");
////         roleMap.put("Retiree", retreeList);
//
//        // processVariables
//        Map<String, List<String>> varMap = new HashMap<String, List<String>>();
//         List<String> varList = new ArrayList<String>();
//         varList.add("2");
//         varMap.put("ProgressCode", varList);
//        
//        String comment = "상신완료";
//
//        completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap,comment);
//        
////        getAuthority(processCode, bizKey, statusCode, "01106");
////        getComments(processCode, bizKey);
    	
//		  String processCode = "NYJPSWN01610000";
//		  String bizKey = "SWN201703061704541";
//		  String userId = "smarttest";
//		  //String dueDate = "2017-03-25";
//		  String dueDate = "";
//		  
//		  //2017-03-06 chonk, 프로세스 처리 기한 일자 변경 테스트
//		  changeDueDateProcess(processCode, bizKey, userId, dueDate);
    }
}
