package org.uengine.web.processmanager.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.uengine.web.login.vo.LoginVO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.processmanager.vo.ProcessInstanceVO;
import org.w3c.dom.Document;

public interface ProcessmanagerService {
	
	public List<ProcessDefinitionVO> selectProcessDefinitionObjectListByDefId(String defId) throws Exception;

	public Document createJnlpXmlDocument(LoginVO sessionVO, HttpServletRequest request) throws Exception;

	public Document createJnlpXmlDocumentByInstanceId(LoginVO sessionVO, HttpServletRequest request) throws Exception;

	public String getProcessDefinitionXPDStringWithVersion(String version) throws Exception;

	public String getProcessDefinitionXPDString(String defId) throws Exception;

	public String getProcessDefinitionXPDStringWithInstanceId(String instanceId) throws Exception;

	public String saveProcessDefinition(Map<String,String> parameterMap) throws Exception;

	public List<TreeVO> getProcessTreeBycomCode(String comCode) throws Exception;
	
	public List<ProcessInstanceVO> getProcessInstanceListByComCode(List<Map<String,String>> params) throws Exception;

	public String addFolder(Map<String, String> parameterMap) throws Exception;

	public void moveFolder(String parent, String object) throws Exception;

	public void removeDefinition(String defId) throws Exception;

	public void removeProcessInstance(String instanceId) throws Exception;

	public void saveProcessInstanceDefinition(Map<String, String> paramMap) throws Exception;
	public void processVersionChange(List<Map<String,String>> params) throws Exception;
	
	public Document createDefinitionListXmlDocument() throws Exception;
	
	public Map<String,String> StringToMap(String body) throws Exception;
	public void forwordMapIntoPage(HttpServletRequest request, Map<String, String> map) throws Exception;

}
