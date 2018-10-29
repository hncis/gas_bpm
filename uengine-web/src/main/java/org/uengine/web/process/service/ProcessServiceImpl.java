package org.uengine.web.process.service; 

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityInstanceContext;
import org.uengine.kernel.ActivityReference;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.UEngineException;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.util.UEngineUtil;
import org.uengine.web.process.dao.ProcessDAO;
import org.uengine.web.process.vo.ProcessVO;
import org.uengine.web.process.vo.TreeVO;
import org.uengine.web.processmanager.vo.ProcessDefinitionVO;
import org.uengine.web.service.service.BpmServiceImpl;
import org.uengine.web.service.vo.ServiceVO;
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

 
@Service("processService")
public class ProcessServiceImpl implements ProcessService {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name="processDAO")
	private ProcessDAO processDAO;
	
	@Override
	public List<TreeVO> getProcessTreeBycomCode(String comCode)
			throws Exception {
		return processDAO.selectProcessTreeBycomCode(comCode);
	}

	@Override
	public List<ProcessDefinitionVO> getKrissProcessList(ProcessDefinitionVO vo)
			throws Exception {
		return processDAO.selectKrissProcessList(vo);
	}

	@Override
	public int getCountKrissProcessList(String userId) throws Exception {
		return processDAO.selectCountKrissProcessList(userId);
	}

	@Override
	public ProcessVO initExecuteProcess(ProcessVO processVO, HttpServletRequest request)
			throws UEngineException {
		
		BpmServiceImpl bpmService = new BpmServiceImpl();
		ProcessManagerBean pm = new ProcessManagerBean();
		
		String processDefinition = null;
		ProcessInstance instance = null;
		
		String procDefId = processVO.getDefId();
		String userId = processVO.getUserId();
		if(UEngineUtil.isNotEmpty(procDefId)){
			try {
				processDefinition = pm.getProcessDefinitionProductionVersion(procDefId);
			} catch (RemoteException e) {
			System.err.println(e);
			}
		}else{
			System.err.println("processDefinition is null");
		}
		ProcessDefinition pd = null;
		ActivityReference initiatorHumanActivityReference;
		String currentTracTag = null;
		String currentTaskId = null;
		
		try {
			pd = pm.getProcessDefinition(processDefinition);
			initiatorHumanActivityReference = pm.getInitiatorHumanActivityReference(processDefinition);
			
			if(initiatorHumanActivityReference!=null && pd.isInitiateByFirstWorkitem()){
				String initiatorHumanActivityTracingTag = initiatorHumanActivityReference.getAbsoluteTracingTag();
				HumanActivity initiatorHumanActivity = (HumanActivity)initiatorHumanActivityReference.getActivity();
				String tool = initiatorHumanActivity.getTool();
				
				String instId="";
				boolean isCompleteed=false;
					Map genericContext = new HashMap();
					genericContext.put("request", request);
					RoleMapping loggedRm = null;
					if (UEngineUtil.isNotEmpty(userId)) {
						loggedRm = RoleMapping.create();
						loggedRm.beforeFirst();
						loggedRm.setEndpoint(userId);	
					}
					genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRm);
					pm.setGenericContext(genericContext);
					instId = pm.initializeProcess(processDefinition);
					pm.executeProcess(instId);
					ActivityInstanceContext  aic = pm.getProcessInstance(instId).getCurrentRunningActivity();
					if(aic !=null){
						Activity act = aic.getActivity();
						currentTracTag = act.getTracingTag();
						tool = ((HumanActivity)act).getTool();
						currentTaskId = ((HumanActivity)act).getTaskIds(pm.getProcessInstance(instId))[0];
					}else{
						isCompleteed=true;
					}
					if(instance == null){
						instance = pm.getProcessInstance(instId);
					}
					processVO.setInstanceId(instId);
					processVO.setCurrentActivities(bpmService.getCurrentActivities(instance));
					pm.applyChanges();
					return processVO;
				
				}
		}catch (UEngineException e) {
				try {
					if (e.getErrorCode() == UEngineException.ERR_CODE_SYSTEM_ERROR) {
						System.err.println(e);
					}
					pm.cancelChanges();
				} catch (RemoteException e1) {
					e1.printStackTrace();
					System.err.println(e1);
				}
				throw e;
		}catch (RemoteException e) {
			e.printStackTrace();
			System.err.println(e);
			throw new UEngineException(
					UEngineException.ERR_CODE_SYSTEM_ERROR,
					bpmService.getErrorMessagefByErrorCode(UEngineException.ERR_CODE_SYSTEM_ERROR, pm));
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			throw new UEngineException(
					UEngineException.ERR_CODE_SYSTEM_ERROR,
					bpmService.getErrorMessagefByErrorCode(UEngineException.ERR_CODE_SYSTEM_ERROR, pm));
		} finally {
			try {
				pm.remove();
			} catch (RemoteException e) {
				System.err.println(e);
				e.printStackTrace();
			} catch (RemoveException e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MyWorkVO selectInstanceIdMyWorkVO(String instId) throws Exception {
		return processDAO.selectInstanceIdMyWorkVO(instId);
	}

}
