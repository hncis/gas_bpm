package org.uengine.web.worklist.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.worklist.vo.MyWorkVO;

@Repository("worklistDAO")
public class WorklistDAO extends AbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<MyWorkVO> selectKrissWorkList(MyWorkVO vo) throws Exception {
		return (List<MyWorkVO>) selectList("worklist.selectKrissWorklist", vo);
	}
	
	public int selectCountKrissWorklist(MyWorkVO vo) throws Exception {
		return ((Integer) selectOne("worklist.selectCountKrissWorklist", vo)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessDefinitionVO> selectKrissFolderList() throws Exception{
		return (List<ProcessDefinitionVO>) selectList("worklist.selectKrissFolderList");
	}

	public List<MyWorkVO> selectKrissWorklistGroup(MyWorkVO workVO) {
		return (List<MyWorkVO>) selectList("worklist.selectKrissWorklistGroup", workVO);
	}
}
