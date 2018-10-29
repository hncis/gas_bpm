package org.uengine.web.worklist.service; 

import java.util.List;

import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.worklist.vo.MyWorkVO;




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
public interface WorklistService {
	
	public List<MyWorkVO> getKrissWorklist(MyWorkVO workVO) throws Exception;
	
	public int getCountKrissWorklist(MyWorkVO workVO) throws Exception;
	
	public List<ProcessDefinitionVO> getKrissFolderList() throws Exception;

	public List<MyWorkVO> getKrissWorklistGroup(MyWorkVO workVO) throws Exception;
}
