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

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;

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
public class KrissInstanceAppendingFilter implements ActivityFilter, Serializable {


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
