package org.uengine.processpublisher.invert.exporter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.MessageDefinition;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.ServiceDefinition;
import org.uengine.processpublisher.Adapter;

/**
 * @author Jinyoung Jang
 */

public class ProcessDefinitionAdapter implements org.uengine.processpublisher.Adapter{

	static Hashtable adapters = new Hashtable();
	final static String DEST_PROC = "dest process";
	final static String WSDL_DEF = "wsdl definition";
	final static String MSG_DEFS = "message definitions";
	final static String VAR_DEFS = "variables";
	final static String MY_SVC_DEF = "my service definition";
	final static String MY_ROLE = "my role";
	
	public Object convert(Object src, java.util.Hashtable keyedContext) throws Exception{
		ProcessDefinition srcProc = (ProcessDefinition)src;
		ProcessDefinition dstProc = new ProcessDefinition();
		Role role = (Role)keyedContext.get("role");		
		
		ServiceDefinition mySvcDef = new ServiceDefinition();
		
		//review: ProcessDefinition need WSDL location field if once exposed as a Web service
		String srcProcName = srcProc.getName().getText().replace(' ', '_');
		mySvcDef.setName(srcProcName);
		mySvcDef.setWsdlLocation("http://localhost:8082/axis/services/" + srcProcName );
		mySvcDef.setStubPackage("org.uengine");		
		dstProc.setServiceDefinitions(new ServiceDefinition[]{mySvcDef});
	
		Role myRole = new Role();
		//review: seems occur namespace collision
		myRole.setName(srcProcName);
		myRole.setServiceType(mySvcDef);
		dstProc.setRoles(new Role[]{myRole});
		dstProc.setName(role.getName());
		
		MessageDefinition message = new MessageDefinition();
		ServiceDefinition svcDef = role.getServiceType();		
//		Definition wsdlDef = svcDef.getDefinition();
		
//		keyedContext.put(WSDL_DEF, wsdlDef);	
		keyedContext.put(DEST_PROC, dstProc);	
		keyedContext.put(MSG_DEFS, new ExclusiveList());
		keyedContext.put(VAR_DEFS, new ExclusiveList());
		keyedContext.put(MY_SVC_DEF, mySvcDef);
		keyedContext.put(MY_ROLE, myRole);
						
		for(Iterator<Activity> iter = srcProc.getChildActivities().iterator(); iter.hasNext(); ){
			Activity item = iter.next();
			Adapter adpt = getAdapter(item.getClass());
			if(adpt==null){
				continue;
			}
			
			Activity convertedAct = (Activity)adpt.convert(item, keyedContext);
			if(convertedAct!=null){
				if(convertedAct instanceof SequenceActivity){
					SequenceActivity seq = (SequenceActivity)convertedAct;
					List<Activity> childActs = seq.getChildActivities();
					for(Iterator<Activity> iterSub = childActs.iterator(); iterSub.hasNext();){
						Activity act = iterSub.next();
						dstProc.addChildActivity(act);
					}
				}else
					dstProc.addChildActivity(convertedAct);							
			}
		}		
		
		Vector messageDefinitions = (Vector)keyedContext.get(ProcessDefinitionAdapter.MSG_DEFS);
		MessageDefinition[] mds = new MessageDefinition[messageDefinitions.size()];
		messageDefinitions.toArray(mds);
		dstProc.setMessageDefinitions(mds);

		Vector variableDefinitions = (Vector)keyedContext.get(ProcessDefinitionAdapter.VAR_DEFS);
		ProcessVariable[] pvs = new ProcessVariable[variableDefinitions.size()];
		variableDefinitions.toArray(pvs);
		dstProc.setProcessVariables(pvs);
		
		return dstProc;
	}
	
	protected static Adapter getAdapter(Class activityType){
		if(adapters.containsKey(activityType))
			return (Adapter)adapters.get(activityType);
		
		Adapter adapter = null;
		do{	
			try{
				String activityTypeName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
				adapter = (Adapter)Class.forName("org.uengine.processpublisher.invert.exporter." + activityTypeName + "Adapter").newInstance();
				
				adapters.put(activityType, adapter);
			}catch(Exception e){
				activityType = activityType.getSuperclass();
			}
		}while(adapter==null && activityType!=Object.class);

		if(adapter==null)			
			System.out.println("ProcessDefinitionAdapter::getAdapter : can't find adapter for " + activityType);
			
		return adapter;
	}

	public static void main(String [] args) throws Exception{
//		ProcessDefinition def = ProcessDefinitionFactory.getDefinition(new java.io.FileInputStream(args[0]));
//		GlobalContext.serialize(def, System.out, "Invert");
	}
	
	class ExclusiveList extends Vector{
		public synchronized boolean add(Object o) {
			if(o!=null && !contains(o))
				return super.add(o);
				
			return false;
		}
	} 

}