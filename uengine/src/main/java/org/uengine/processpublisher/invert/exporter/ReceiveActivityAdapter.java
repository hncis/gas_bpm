package org.uengine.processpublisher.invert.exporter;

import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ReceiveActivity;
import org.uengine.kernel.Role;
import org.uengine.kernel.ServiceDefinition;
import org.uengine.kernel.WebServiceActivity;

import org.uengine.processpublisher.Adapter;
import java.util.*;
	 
/**
 * @author Jinyoung Jang
 */
    
public class ReceiveActivityAdapter implements Adapter{

	public Object convert(Object src, java.util.Hashtable keyedContext) throws Exception{
		ReceiveActivity srcAct = (ReceiveActivity)src;
		WebServiceActivity destAct = new WebServiceActivity();
		Role role = (Role)keyedContext.get("role");		
		Role myRole = (Role)keyedContext.get(ProcessDefinitionAdapter.MY_ROLE);		
		ServiceDefinition serviceDefinition = (ServiceDefinition)keyedContext.get(ProcessDefinitionAdapter.MY_SVC_DEF);		
		if(srcAct.getFromRole()==null || !srcAct.getFromRole().equals(role)) return null;

		try{
			String msgName = srcAct.getMessageDefinition().getName();
			destAct.setPortType(myRole.getName());
			destAct.setService(serviceDefinition);
			destAct.setOperationName(msgName);
			ParameterContext[] paramCtxs = srcAct.getParameters();			
			destAct.setParameters(paramCtxs);
			
			Vector variableDefinitions = (Vector)keyedContext.get(ProcessDefinitionAdapter.VAR_DEFS);
			for(int i=0; i<paramCtxs.length; i++){
				variableDefinitions.add(paramCtxs[i].getVariable());
			}
		}catch(Exception e){
		}		

		destAct.setName("invoke for " + srcAct.getName());
		destAct.setRole(myRole);
		
		return destAct;
	}

}