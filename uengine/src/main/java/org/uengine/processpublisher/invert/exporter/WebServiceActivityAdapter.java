package org.uengine.processpublisher.invert.exporter;

import org.uengine.kernel.MessageDefinition;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ReceiveActivity;
import org.uengine.kernel.Role;
import org.uengine.kernel.WebServiceActivity;

import org.uengine.processpublisher.Adapter;
import java.util.*;
	 
/**
 * @author Jinyoung Jang
 */
    
public class WebServiceActivityAdapter implements Adapter{

	public Object convert(Object src, java.util.Hashtable keyedContext) throws Exception{
		WebServiceActivity srcAct = (WebServiceActivity)src;
		ReceiveActivity destAct = new ReceiveActivity();
		Role role = (Role)keyedContext.get("role");		
		Role myRole = (Role)keyedContext.get(ProcessDefinitionAdapter.MY_ROLE);		
		if(!srcAct.getRole().equals(role)) return null;

		Vector messageDefinitions = (Vector)keyedContext.get(ProcessDefinitionAdapter.MSG_DEFS);
		MessageDefinition md = null;		
		try{			
			Object[] parameters = srcAct.getParameters();
			ParameterContext[] paramCtxs = new ParameterContext[parameters.length];
			System.arraycopy(parameters, 0, paramCtxs, 0, parameters.length);
			md = new MessageDefinition();
			String opName = srcAct.getOperationName();
			if(opName.startsWith("on")){
				opName = opName.substring(2);
			}
			md.setName(opName);
			md.setParameters(paramCtxs);
			messageDefinitions.add(md);

			Vector variableDefinitions = (Vector)keyedContext.get(ProcessDefinitionAdapter.VAR_DEFS);
			for(int i=0; i<paramCtxs.length; i++){
				variableDefinitions.add(paramCtxs[i].getVariable());
			}			
		}catch(Exception e){
		}

		destAct.setName("Rcv. from " + srcAct.getName());
		destAct.setFromRole(myRole);
		destAct.setMessageDefinition(md);
		
		return destAct;
	}

}