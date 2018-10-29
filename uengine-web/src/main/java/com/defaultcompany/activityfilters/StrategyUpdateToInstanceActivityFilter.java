package com.defaultcompany.activityfilters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.persistence.dao.DAOFactory;
import org.uengine.persistence.dao.KeyGeneratorDAO;
import org.uengine.processmanager.ProcessTransactionContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

public class StrategyUpdateToInstanceActivityFilter implements ActivityFilter, Serializable{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public void afterComplete(Activity activity, ProcessInstance instance) throws Exception {
//		if(activity instanceof ProcessDefinition){
//			ProcessTransactionContext ptc = instance.getProcessTransactionContext();
//			
//			Number strategyId = ((EJBProcessInstance) instance).getProcessInstanceDAO().getStrategyId();
//
//			if(strategyId != null){
//				IDAO strategyDAO = instance.getProcessTransactionContext().findSynchronizedDAO("bpm_strtg", "strtgId", strategyId, IDAO.class);
//				strategyDAO.select();
//				strategyDAO.next();
//				int cmpltinstCnt = strategyDAO.getInt("cmpltinstCnt");
//				strategyDAO.set("cmpltinstCnt", cmpltinstCnt + 1);   // increase completed instance count
//				
//			}
//			
//		}
	}

	public void afterExecute(Activity activity, ProcessInstance instance) throws Exception {
		if(instance.isNew() && activity instanceof ProcessDefinition){
			ProcessTransactionContext ptc = instance.getProcessTransactionContext();
			Map requestMap = ptc.getProcessManager().getGenericContext();
			if(requestMap !=null && !"addStrategy".equals(instance.getProcessDefinition().getAlias()) ){
				
				HttpServletRequest req =(HttpServletRequest)requestMap.get("request");
				
				if(req !=null){
					String strategyIdInString = (String)req.getParameter("strategyId");
		
					if(UEngineUtil.isNotEmpty(strategyIdInString)){
						Number strategyId = new Integer(strategyIdInString);
						
						((EJBProcessInstance) instance).getProcessInstanceDAO().setStrategyId(strategyId);
		
						IDAO strategyDAO = instance.getProcessTransactionContext().findSynchronizedDAO("bpm_strtg", "strtgId", strategyId, IDAO.class);
						int instanceCnt = strategyDAO.getInt("instCnt");
						strategyDAO.set("instCnt", instanceCnt + 1);   // increase instance count
						
					}
				}
			}
		}
	}

	public void beforeExecute(Activity activity, ProcessInstance instance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onDeploy(ProcessDefinition definition) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
