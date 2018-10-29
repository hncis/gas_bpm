package org.uengine.web.process.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.worklist.vo.MyWorkVO;


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
@Repository("processDAO")
public class ProcessDAO extends AbstractDAO {

	public List<TreeVO> selectProcessTreeBycomCode(String comCode) throws Exception{
		return (List<TreeVO>) selectList("process.selectProcessTreeBycomCode", comCode);
	}
	
	public List<ProcessDefinitionVO> selectKrissProcessList(ProcessDefinitionVO vo) throws Exception{
		return (List<ProcessDefinitionVO>) selectList("process.selectKrissProcessList", vo);
	}
	
	public int selectCountKrissProcessList(String userId) throws Exception{
		return (int) selectOne("process.selectCountKrissProcessList", userId);
	}
	
	public MyWorkVO selectInstanceIdMyWorkVO(String instId) throws Exception{
		return (MyWorkVO) selectOne("process.selectInstanceIdMyWorkVO", instId);
	}
	

}
