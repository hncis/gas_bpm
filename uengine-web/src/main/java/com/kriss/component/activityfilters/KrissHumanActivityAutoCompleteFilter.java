/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package com.kriss.component.activityfilters; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

import com.kriss.activity.KrissHumanActivity;

/**
 * <pre>
 * com.kriss.component.activityfilters 
 *    |_ KrissHumanActivityAutoCompleteFilter.java
 * 
 * </pre>
 * @date : 2016. 8. 5. 오후 1:08:55
 * @version : 
 * @author : mkbok_Enki
 */
/**
 * <pre>
 * com.kriss.component.activityfilters 
 * KrissHumanActivityAutoCompleteFilter.java
 * 
 * </pre>
 * @date : 2016. 8. 5. 오후 1:08:55
 * @version : 
 * @author : mkbok_Enki
 */
public class KrissHumanActivityAutoCompleteFilter implements ActivityFilter, Serializable {


	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : beforeExecute
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.ActivityFilter#beforeExecute(org.uengine.kernel.Activity, org.uengine.kernel.ProcessInstance)
	 * @param activity
	 * @param instance
	 * @throws Exception
	 */
	@Override
	public void beforeExecute(Activity activity, ProcessInstance instance) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : afterExecute
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.ActivityFilter#afterExecute(org.uengine.kernel.Activity, org.uengine.kernel.ProcessInstance)
	 * @param activity
	 * @param instance
	 * @throws Exception
	 */
	@Override
	public void afterExecute(Activity activity, ProcessInstance instance) throws Exception {
		
		if ( activity instanceof KrissHumanActivity 
				&& !instance.getProcessDefinition().getInitiatorHumanActivityReference(instance).getActivity().equals(activity) 
				&& ((HumanActivity)activity).getActualMapping(instance).size() == 1 ) {
			
			if (((KrissHumanActivity)activity).isAutoCompleteByHistory()) {
				
				// 롤백된 업무중 완료된 업무 조회
				List<String> endpoints = new ArrayList<String>();
				
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT endpoint                         \n");
				sql.append("	FROM bpm_worklist                   \n");
				sql.append("WHERE instid=?instid                    \n");
				sql.append("	AND trctag=?trctag           		\n");
				sql.append("	AND enddate IS NOT NULL             \n");
				sql.append("	AND status='CANCELLED'           	\n");
				
				IDAO idao = ConnectiveDAO.createDAOImpl(instance.getProcessTransactionContext(), sql.toString(), IDAO.class);
				idao.set("instid", instance.getInstanceId());
				idao.set("trctag", activity.getTracingTag());
				idao.select();
				while(idao.next()) {
					endpoints.add(idao.getString("endpoint"));
				}
				
				Iterator<String> i = endpoints.iterator();
				RoleMapping actualMapping = ((KrissHumanActivity)activity).getActualMapping(instance);
				actualMapping.beforeFirst();
				String actualWorker = actualMapping.getEndpoint();
				while(i.hasNext()) {
					if ( i.next().equals(actualWorker) ) {
						((HumanActivity)activity).fireReceived(instance, new ResultPayload());
						instance.setProperty(activity.getTracingTag(), HumanActivity.IS_AUTO_COMPLETED, true);
						break;
					}
				}
				
			}
			
			
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : afterComplete
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.ActivityFilter#afterComplete(org.uengine.kernel.Activity, org.uengine.kernel.ProcessInstance)
	 * @param activity
	 * @param instance
	 * @throws Exception
	 */
	@Override
	public void afterComplete(Activity activity, ProcessInstance instance) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : onPropertyChange
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.ActivityFilter#onPropertyChange(org.uengine.kernel.Activity, org.uengine.kernel.ProcessInstance, java.lang.String, java.lang.Object)
	 * @param activity
	 * @param instance
	 * @param propertyName
	 * @param changedValue
	 * @throws Exception
	 */
	@Override
	public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : onDeploy
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.ActivityFilter#onDeploy(org.uengine.kernel.ProcessDefinition)
	 * @param definition
	 * @throws Exception
	 */
	@Override
	public void onDeploy(ProcessDefinition definition) throws Exception {
		// TODO Auto-generated method stub

	}

}
