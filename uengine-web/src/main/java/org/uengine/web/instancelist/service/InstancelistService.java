package org.uengine.web.instancelist.service; 

import java.util.List;

import org.uengine.web.processmanager.vo.ProcessInstanceVO;
import org.uengine.web.service.vo.DefaultVO;




/**
 * <pre>
 * org.uengine.web.main.service 
 * MainService.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 4:46:03
 * @version : 
 * @author : mkbok_Enki
 */
public interface InstancelistService {
	
	public List<ProcessInstanceVO> getKrissRunningInstanceList(DefaultVO vo) throws Exception;
	public int getCountKrissRunningInstanceList(DefaultVO vo) throws Exception;
	public List<ProcessInstanceVO> getKrissCompletedInstanceList(DefaultVO vo) throws Exception;
	public int getCountKrissCompletedInstanceList(DefaultVO vo) throws Exception;
	public List<ProcessInstanceVO> getKrissDelegatedCompletedInstanceList(DefaultVO vo) throws Exception;
	public int getCountKrissDelegatedCompletedInstanceList(DefaultVO vo) throws Exception;
	
}
