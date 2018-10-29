/*
 * Created on 2004. 12. 27.
 */
package org.uengine.test;

import junit.framework.*;
import org.uengine.processmanager.*;
import org.uengine.kernel.ProcessDefinition;


import javax.naming.InitialContext;
import java.util.*;

/**
 * @author Jinyoung Jang
 */
public class PerformanceTest extends TestCase{
	
	final static String ProcessDefinitionId = "399";
	final static StringBuffer cleanup = new StringBuffer();
	int countOfThreads = 0;
	long startedTime;
	
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
	}
	
	public void testConcurrentAdhocProcessInitiation() throws Exception{
		
		startedTime = Calendar.getInstance().getTimeInMillis();
			
		for(int i=0; i<100; i++){
			final int threadId = i;
			
			new Thread(){	
				public void run(){
					try{
						initializeAdhocProcess(threadId, ProcessDefinitionId);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}.start();
			
			Thread.sleep(100);
			
//			countOfThreads++;
		}
		
	}
	
	private void initializeAdhocProcess(int threadId, String defId) throws Exception{
		String instanceId=null;
		try{
			long thatTime;
			long now = Calendar.getInstance().getTimeInMillis();
			thatTime = now;
		
			//initialize
			instanceId = pm.initializeProcess(defId);
//			pm.executeProcessByWorkitem(instanceId, null);
		
			now = Calendar.getInstance().getTimeInMillis();
			System.out.println("thread: " + threadId + ";	initialize time: " + (now-thatTime) + "	instanceId: " + instanceId);
			thatTime = now;
		
			//dynamic change
			ProcessDefinition def = pm.getProcessDefinitionWithInstanceId(instanceId);
			String changedName = "name is changed to def of " + instanceId;
			def.setName(changedName);
			pm.changeProcessDefinition(instanceId, def);
			
			ProcessDefinition def2 = pm.getProcessDefinitionWithInstanceId(instanceId);
			
			if(!def.getName().equals(def2.getName()))
				System.err.println("something wrong with dynamic change!");

			now = Calendar.getInstance().getTimeInMillis();
			System.out.println("thread: " + threadId + ";	dynamic change time: " + (now-thatTime));
			thatTime = now;
		
			//removal
			pm.removeProcessInstance(instanceId);
		
			now = Calendar.getInstance().getTimeInMillis();
			System.out.println("thread: " + threadId + ";	removal time: " + (now-thatTime));
			//thatTime = now;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(instanceId!=null)
					pm.removeProcessInstance(instanceId);
			}catch(Exception ee){
			}
			
//			countOfThreads--;
			
			if(countOfThreads==0){
				long now = Calendar.getInstance().getTimeInMillis();
				System.out.println("------------------------------------- elapsed: " + (now-startedTime));
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		PerformanceTest pt = new PerformanceTest();
		pt.setUp();
		pt.testConcurrentAdhocProcessInitiation();
		
//		System.out.println("---cleanup---");
//		System.out.println(pt.cleanup.toString());
	}
	
}

