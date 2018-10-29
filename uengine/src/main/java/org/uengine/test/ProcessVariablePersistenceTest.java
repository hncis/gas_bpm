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

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Jinyoung Jang
 */
public class ProcessVariablePersistenceTest extends TestCase{
	
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
	
	public void testMain() throws Exception{
			
	/*	final String pdName = "testProcess";
		final String instName = "testInstance";
		
		final String varStringValue = "value";
		final Integer varIntegerValue = new Integer(123);
		final Long varLongValue = new Long(123);
		final Calendar varCalendarValue = Calendar.getInstance();
		final Date varDateValue = new Timestamp((new Date()).getTime());
		final Boolean varBooleanValue = new Boolean(false);
		final Properties varAnyValue = new Properties();{
			varAnyValue.put("prop1", "val1");
		}
		
				
		try{
			try{		
				pm.addProcessDefinition(pdName, definition, null);
			}catch(Exception e){
			}
		
			pm.initializeProcess(pdName, instName);
			
			pm.setProcessVariable(instName, "", "varString", varStringValue);
			pm.setProcessVariable(instName, "", "varInteger", varIntegerValue);
			pm.setProcessVariable(instName, "", "varLong", varLongValue);
			pm.setProcessVariable(instName, "", "varBoolean", varBooleanValue);
			pm.setProcessVariable(instName, "", "varCalendar", varCalendarValue);
			pm.setProcessVariable(instName, "", "varDate", varDateValue);
			pm.setProcessVariable(instName, "", "varAny", varAnyValue);
			
			assertTrue(pm.getProcessVariable(instName, "", "varString").equals(varStringValue));
			assertTrue(pm.getProcessVariable(instName, "", "varInteger").equals(varIntegerValue));
			assertTrue(pm.getProcessVariable(instName, "", "varLong").equals(varLongValue));
			assertTrue(pm.getProcessVariable(instName, "", "varBoolean").equals(varBooleanValue));
			assertTrue(pm.getProcessVariable(instName, "", "varCalendar").equals(varCalendarValue));
			assertTrue(pm.getProcessVariable(instName, "", "varDate").equals(varDateValue));
			assertTrue(pm.getProcessVariable(instName, "", "varAny").equals(varAnyValue));

		}catch(Exception e){
			throw e;	
		}finally{
			try{pm.removeProcessInstance(instName);}catch(Exception e){}
			try{pm.removeProcessDefinition(pdName);}catch(Exception e){}
		}*/
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1109071780716l);
		Date date = new Date();
		date.setTime(1109071780716l);
	}
	
	public ProcessDefinition createProcessDefinition(){
		ProcessDefinition def = new ProcessDefinition();
		
		def.setRoles(new Role[]{
			new Role("referencer"),
			new Role("drafter"),
			new Role("approver")
		});
				
		ProcessVariable pv1;{
			pv1 = new ProcessVariable();
			pv1.setName("varString");
			pv1.setType(String.class);				
		}
		ProcessVariable pv2;{
			pv2 = new ProcessVariable();
			pv2.setName("varInteger");
			pv2.setType(Integer.class);				
		}
		ProcessVariable pv3;{
			pv3 = new ProcessVariable();
			pv3.setName("varLong");
			pv3.setType(Long.class);				
		}
		ProcessVariable pv4;{
			pv4 = new ProcessVariable();
			pv4.setName("varBoolean");
			pv4.setType(Boolean.class);				
		}
		ProcessVariable pv5;{
			pv5 = new ProcessVariable();
			pv5.setName("varCalendar");
			pv5.setType(Calendar.class);				
		}
		ProcessVariable pv6;{
			pv6 = new ProcessVariable();
			pv6.setName("varDate");
			pv6.setType(Date.class);				
		}
		ProcessVariable pv7;{
			pv7 = new ProcessVariable();
			pv7.setName("varAny");
		}

		def.setProcessVariables(new ProcessVariable[]{pv1, pv2, pv3, pv4, pv5, pv6, pv7});

			
		Activity childActivity = new DefaultActivity("test");			
		def.setChildActivities(new Activity[]{childActivity});
		
		return def;
	}
	
}

