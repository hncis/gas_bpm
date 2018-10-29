/*
 * Created on 2004. 12. 19.
 */
package org.uengine.components.activityfilters;

import java.io.Serializable;
import java.util.Iterator;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.TransactionListener;
import org.uengine.processdesigner.SimulatorProcessInstance;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;



/**
 * @author Jinyoung Jang
 */
public class InstanceDataAppendingActivityFilter implements ActivityFilter, Serializable{

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public void afterExecute(Activity activity, final ProcessInstance instance)
		throws Exception {
		
		if(instance instanceof SimulatorProcessInstance ||activity.STATUS_SKIPPED.equals(activity.getStatus(instance))) return;
		
		if(activity instanceof HumanActivity && instance.getProcessDefinition().getInitiatorHumanActivityReference(instance).getActivity().equals(activity)){
			((EJBProcessInstance) instance).getProcessInstanceDAO().set("inittaskid", ((HumanActivity)activity).getTaskIds(instance)[0]);
		}

		if(activity instanceof HumanActivity){
			try{
				RoleMapping rm = ((HumanActivity)activity).getRole().getMapping(instance);
				rm.fill(instance);
				if(rm == null) return;
				if(
						instance.isNew() 
						&& instance.getProcessDefinition().getInitiatorHumanActivityReference(instance).getActivity().equals(activity)
				){	
					((EJBProcessInstance) instance).getProcessInstanceDAO().set("initEp", rm.getEndpoint());
					((EJBProcessInstance) instance).getProcessInstanceDAO().set("initRSNM", rm.getResourceName());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if ( instance.isNew() && instance.isSubProcess() && !instance.getInstanceId().equals(instance.getRootProcessInstanceId())) {
			String initEp = (String) ((EJBProcessInstance)instance.getRootProcessInstance()).getProcessInstanceDAO().get("initEp");
			String initRSNM = (String) ((EJBProcessInstance)instance.getRootProcessInstance()).getProcessInstanceDAO().get("initRSNM");
			((EJBProcessInstance) instance).getProcessInstanceDAO().set("initEp", initEp);
			((EJBProcessInstance) instance).getProcessInstanceDAO().set("initRSNM", initRSNM);
		}
	}

	public void afterComplete(Activity activity, ProcessInstance instance) throws Exception {
		if (activity instanceof HumanActivity)
			((HumanActivity)activity).firePropertyChangeEventToActivityFilters(instance, "saveEndpoint", new ResultPayload());
	}
	
	public void beforeExecute(Activity activity, ProcessInstance instance)
		throws Exception {
	}

	public void onDeploy(ProcessDefinition definition) throws Exception {
	}

	public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue) throws Exception {
		if(instance instanceof SimulatorProcessInstance ||activity.STATUS_SKIPPED.equals(activity.getStatus(instance))) return;
		
		final ProcessInstance thisInst = instance;
		

		if(activity instanceof HumanActivity && "saveEndpoint".equals(propertyName)){
			TransactionListener tl = new TransactionListener() {
				
				public void beforeRollback(TransactionContext tx) throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				public void beforeCommit(TransactionContext tx) throws Exception {
					StringBuffer endpoint = new StringBuffer();
					StringBuffer resourceName = new StringBuffer();
					StringBuffer currStatusCodes =  new StringBuffer();
					StringBuffer currStatusNames =  new StringBuffer();
					Iterator<HumanActivity> i = thisInst.getAllRunningOrCompletedHumanActivities(thisInst.getProcessDefinition()).iterator();
					while ( i.hasNext() ) {
						HumanActivity humAct = i.next();
						if ( humAct.getStatus(thisInst).equals(Activity.STATUS_RUNNING) || humAct.getStatus(thisInst).equals(Activity.STATUS_TIMEOUT) ) {
							if (currStatusCodes.length() > 0) currStatusCodes.append(";");
							currStatusCodes.append(humAct.getExtValue1());

							if (currStatusNames.length() > 0) currStatusNames.append(";");
							currStatusNames.append(humAct.getName().getText());

							RoleMapping rm = humAct.getRole().getMapping(thisInst);
							do {
								if (endpoint.length() > 0) endpoint.append(";");
								endpoint.append(rm.getEndpoint());
								
								if (resourceName.length() > 0) resourceName.append(";");
								resourceName.append(rm.getResourceName());
							} while (rm.next());
							String sql1 = "update bpm_procinst set currep=?currep where instid=?instid";
							String sql2 = "update bpm_procinst set currrsnm=?currrsnm where instid=?instid";
							String sql3 = "update bpm_procinst set currstatuscodes=?currstatuscodes where instid=?instid";
							String sql4 = "update bpm_procinst set currstatusnames=?currstatusnames where instid=?instid";
							
							IDAO idao = ConnectiveDAO.createDAOImpl(thisInst.getProcessTransactionContext(), sql1, IDAO.class);
							idao.set("currep", endpoint.toString());
							idao.set("instid", thisInst.getInstanceId());
							idao.update();
							
							idao = ConnectiveDAO.createDAOImpl(thisInst.getProcessTransactionContext(), sql2, IDAO.class);
							idao.set("currrsnm", resourceName.toString());
							idao.set("instid", thisInst.getInstanceId());
							idao.update();
							
							idao = ConnectiveDAO.createDAOImpl(thisInst.getProcessTransactionContext(), sql3, IDAO.class);
							idao.set("currstatuscodes", currStatusCodes.toString());
							idao.set("instid", thisInst.getInstanceId());
							idao.update();
							
							idao = ConnectiveDAO.createDAOImpl(thisInst.getProcessTransactionContext(), sql4, IDAO.class);
							idao.set("currstatusnames", currStatusNames.toString());
							idao.set("instid", thisInst.getInstanceId());
							idao.update();
						}
					}
					
				}
				
				public void afterRollback(TransactionContext tx) throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				public void afterCommit(TransactionContext tx) throws Exception {
					// TODO Auto-generated method stub
					
				}
			};
			
			instance.getProcessTransactionContext().addTransactionListener(tl);
		}
	}
}
