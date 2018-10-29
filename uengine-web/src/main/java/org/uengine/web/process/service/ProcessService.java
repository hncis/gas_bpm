package org.uengine.web.process.service; 

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.uengine.kernel.UEngineException;
import org.uengine.web.process.vo.ProcessVO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.service.vo.ServiceVO;
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
public interface ProcessService {

	List<TreeVO> getProcessTreeBycomCode(String comCode) throws Exception;
	List<ProcessDefinitionVO> getKrissProcessList(ProcessDefinitionVO vo) throws Exception;
	int getCountKrissProcessList(String userId) throws Exception;
	ProcessVO initExecuteProcess(ProcessVO processVO, HttpServletRequest request) throws UEngineException;
	MyWorkVO selectInstanceIdMyWorkVO(String instId) throws Exception;
}
