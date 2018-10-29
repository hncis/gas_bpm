package org.uengine.web.processmanager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.processmanager.vo.ProcessInstanceVO;


/**
 * <pre>
 * org.uengine.web.organization.dao 
 * OrganizationDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 1:20:07
 * @version : 
 * @author : next3
 */
/**
 * <pre>
 * org.uengine.web.organization.dao 
 * OrganizationDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 1:20:16
 * @version : 
 * @author : next3
 */
@Repository("processmanagerDAO")
public class ProcessmanagerDAO extends AbstractDAO {

	public List<ProcessDefinitionVO> selectProcessDefinitionObjectListByDefId(String defId) throws Exception {
		return (List<ProcessDefinitionVO>) selectList("processmanager.selectProcessDefinitionObjectListByDefId", defId);
	}

	public List<TreeVO> getProcessTreeBycomCode(String comCode) {
		return (List<TreeVO>) selectList("processmanager.selectProcessTreeBycomCode", comCode);
	}
	
	public List<ProcessInstanceVO> getProcessInstanceListByComCode(String comCode) {
		return (List<ProcessInstanceVO>) selectList("processmanager.selectProcessInstanceListByComCode", comCode);
	}
	
	public List<ProcessInstanceVO> getProcessInstanceListByStatusAndSearchResult(Map<String, String> map) {
		return (List<ProcessInstanceVO>) selectList("processmanager.selectProcessInstanceListByStatusAndSearchResult", map);
	}

	public List<ProcessDefinitionVO> getProcessDefinitionByParentId(String parent) {
		return (List<ProcessDefinitionVO>) selectList("processmanager.selectProcessDefinitionByParentId", parent);
	}
	
	public void processVersionChange(Map<String,String> paramMap){
		update("processmanager.processVersionChange", paramMap);
	}
	
}
