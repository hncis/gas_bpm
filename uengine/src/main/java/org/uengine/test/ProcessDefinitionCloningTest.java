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
public class ProcessDefinitionCloningTest extends TestCase{
	
	ProcessManagerRemote pm;
	ProcessDefinition definition;
	InitialContext jndiContext;
	
	protected void setUp() throws Exception {
		definition = createProcessDefinition();
	}
	
	public void testMain() throws Exception{
		ProcessDefinition clone1 = (ProcessDefinition)definition.clone();
		System.out.println(clone1);
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

