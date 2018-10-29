/*
 * Created on 2004. 12. 27.
 */
package org.uengine.test;

import junit.framework.*;
import org.uengine.processmanager.*;
import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;


import javax.naming.InitialContext;
import java.util.*;

/**
 * @author Jinyoung Jang
 */
public class DynamicChangeTest extends TestCase{
	
	ProcessManagerRemote pm;
	ProcessDefinition definition;
	InitialContext jndiContext;
	
	protected void setUp() throws Exception {
		Hashtable prop = new Hashtable();
		prop.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
		prop.put("java.naming.provider.url", "localhost");
		jndiContext = new InitialContext(prop);

		ProcessManagerHomeRemote pmh = (ProcessManagerHomeRemote)jndiContext.lookup("ProcessManagerHomeRemote");
		pm = pmh.create();
		definition = createProcessDefinition();
	}
	
	public void testDynamicChange() throws Exception{
			
		final String changedName = "changed name";
		final String tracingTag = "_0";
		final String pdName = "dynamicChangeProcess";
		final String instName = "testInstance";
		final long pdid;
		
/*		try{
			try{		
				pdid = pm.addProcessDefinition(pdName, 0, "", false, definition, -1, -1);
			}catch(Exception e){
			}
		
			pm.initializeProcess(pdid, instName);
			//pm.executeProcess(instName);
		
			ProcessDefinition pd = pm.getProcessDefinitionWithInstanceId(instName);
			Activity act = pd.getActivity(tracingTag);
			String oldName = act.getName();
			act.setName(changedName);
			pm.changeProcessDefinition(instName, pd);
			ProcessDefinition pd2 = pm.getProcessDefinitionWithInstanceId(instName);
		
			Activity act2 = pd2.getActivity(tracingTag);
			assertTrue(act2.getName().equals(changedName));
		}catch(Exception e){
			throw e;	
		}finally{
			try{pm.removeProcessInstance(instName);}catch(Exception e){}
			try{pm.removeProcessDefinition(pdid);}catch(Exception e){}
			try{pm.removeProcessDefinition("ADHOC_" + instName);}catch(Exception e){}
		}*/
	}
	
	public ProcessDefinition createProcessDefinition(){
		ProcessDefinition def = new ProcessDefinition();
		
		def.setRoles(new Role[]{
			new Role("referencer"),
			new Role("drafter"),
			new Role("approver")
		});
				
		ProcessVariable pv;{
			pv = new ProcessVariable();
			pv.setName("var1");
			pv.setType(Integer.class);				
			def.setProcessVariables(new ProcessVariable[]{pv});
		}
			
		Activity childActivity = new DefaultActivity("test");			
		def.setAdhoc(true);		
		def.setChildActivities(new Activity[]{childActivity});
		
		return def;
	}
	
}

