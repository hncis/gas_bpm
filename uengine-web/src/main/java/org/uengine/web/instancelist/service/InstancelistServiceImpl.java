package org.uengine.web.instancelist.service; 

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.GlobalContext;
import org.uengine.web.instancelist.dao.InstancelistDAO;
import org.uengine.web.processmanager.vo.ProcessInstanceVO;
import org.uengine.web.service.vo.DefaultVO;


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
@Service("instancelistService")
public class InstancelistServiceImpl implements InstancelistService {

	@Resource(name="instancelistDAO")
	private InstancelistDAO instancelistDAO;
	
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public List<ProcessInstanceVO> getKrissRunningInstanceList(DefaultVO vo)
			throws Exception {
		return instancelistDAO.selectKrissRunningInstanceList(vo);
	}

	@Override
	public int getCountKrissRunningInstanceList(DefaultVO vo) throws Exception {
		return instancelistDAO.selectCountKrissRunningInstanceList(vo);
	}
	
	@Override
	public List<ProcessInstanceVO> getKrissCompletedInstanceList(DefaultVO vo)
			throws Exception {
		return instancelistDAO.selectKrissCompletedInstanceList(vo);
	}
	
	@Override
	public int getCountKrissCompletedInstanceList(DefaultVO vo) throws Exception {
		return instancelistDAO.selectCountKrissCompletedInstanceList(vo);
	}

	@Override
	public List<ProcessInstanceVO> getKrissDelegatedCompletedInstanceList(
			DefaultVO vo) throws Exception {
		return instancelistDAO.selectKrissDelegatedCompletedInstanceList(vo);
	}

	@Override
	public int getCountKrissDelegatedCompletedInstanceList(DefaultVO vo)
			throws Exception {
		return instancelistDAO.selectCountKrissDelegatedCompletedInstanceList(vo);
	}



}
