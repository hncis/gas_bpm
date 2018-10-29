package org.uengine.web.worklist.service; 

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.GlobalContext;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.worklist.dao.WorklistDAO;
import org.uengine.web.worklist.vo.MyWorkVO;


/**
 * <pre>
 * org.uengine.web.main.service 
 * MainServiceImpl.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:40:53
 * @version : 
 * @author : mkbok_Enki
 */

@Service("worklistService")
public class WorklistServiceImpl implements WorklistService {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name="worklistDAO")
	private WorklistDAO worklistDAO;

	@Override
	public List<MyWorkVO> getKrissWorklist(MyWorkVO vo) throws Exception {
		return worklistDAO.selectKrissWorkList(vo);
	}

	@Override
	public int getCountKrissWorklist(MyWorkVO workVO) throws Exception {
		return worklistDAO.selectCountKrissWorklist(workVO);
	}

	@Override
	public List<ProcessDefinitionVO> getKrissFolderList() throws Exception {
		return worklistDAO.selectKrissFolderList();
	}

	@Override
	public List<MyWorkVO> getKrissWorklistGroup(MyWorkVO workVO)
			throws Exception {
		return worklistDAO.selectKrissWorklistGroup(workVO);
	}


}
