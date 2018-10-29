package org.uengine.web.instancelist.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.processmanager.vo.ProcessInstanceVO;
import org.uengine.web.service.vo.DefaultVO;
import org.uengine.web.worklist.vo.MyWorkVO;

@Repository("instancelistDAO")
public class InstancelistDAO extends AbstractDAO {
	public List<ProcessInstanceVO> selectKrissRunningInstanceList(DefaultVO vo) throws Exception {
		return (List<ProcessInstanceVO>) selectList("instancelist.selectKrissRunningInstanceList", vo);
	}
	public int selectCountKrissRunningInstanceList(DefaultVO vo) throws Exception {
		return (int) selectOne("instancelist.selectCountKrissRunningInstanceList", vo);
	}
	public List<ProcessInstanceVO> selectKrissCompletedInstanceList(DefaultVO vo) throws Exception {
		return (List<ProcessInstanceVO>) selectList("instancelist.selectKrissCompletedInstanceList", vo);
	}
	public int selectCountKrissCompletedInstanceList(DefaultVO vo) throws Exception {
		return (int) selectOne("instancelist.selectCountKrissCompletedInstanceList", vo);
	}
	public List<ProcessInstanceVO> selectKrissDelegatedCompletedInstanceList(DefaultVO vo) throws Exception {
		return (List<ProcessInstanceVO>) selectList("instancelist.selectKrissDelegatedCompletedInstanceList", vo);
	}
	public int selectCountKrissDelegatedCompletedInstanceList(DefaultVO vo) throws Exception {
		return (int) selectOne("instancelist.selectCountKrissDelegatedCompletedInstanceList", vo);
	}
}
