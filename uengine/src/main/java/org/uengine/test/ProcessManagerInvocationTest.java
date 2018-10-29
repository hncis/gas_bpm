/*
 * Created on 2004. 12. 27.
 */
package org.uengine.test;

import junit.framework.*;
import org.uengine.processmanager.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;


import javax.naming.InitialContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Jinyoung Jang
 */
abstract public class ProcessManagerInvocationTest extends TestCase{
	
	final static StringBuffer cleanup = new StringBuffer();
	int countOfThreads = 0;
	long startedTime;
	Hashtable methods = new Hashtable();
	String instanceId;
	
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
		
		Method[] methodArr = pm.getClass().getMethods();
		for(int i=0; i<methodArr.length; i++){			
			String methodName = methodArr[i].getName();
			
			if(!methods.containsKey(methodName)){
				methods.put(methodName, new Vector());
			}
			
			Vector methodVector = (Vector)methods.get(methodName);
			methodVector.add(methodArr[i]);
		}
		
		startedTime = System.currentTimeMillis();
	}
	
	public void testMain() throws Exception{
		doInvocations();
		pm.removeProcessInstance(instanceId);
	}
	
	abstract public void doInvocations() throws Exception;
	
	public void pmCall(String methodName, String[] argumentXMLs) throws Exception{
		System.out.println("invoking: " + methodName);
		
		Vector methodVector = (Vector)methods.get(methodName);

		Object [] params = new Object[argumentXMLs.length];
		for(int i=0; i<argumentXMLs.length; i++){
			if(argumentXMLs[i].equals("<instanceId>")){
				params[i] = instanceId;
			}else if(argumentXMLs[i]==null){
				params[i] = null;
			}else{
				ByteArrayInputStream bis = new ByteArrayInputStream(argumentXMLs[i].getBytes("UTF-8"));
				params[i] = GlobalContext.deserialize(bis, String.class);
			}
		}
		
		Exception re = null;
		for(int i=0; i<methodVector.size(); i++){
			Method m = (Method)methodVector.elementAt(i);
			try{				
				Object result = m.invoke(pm, params);
				
				if(methodName.startsWith("initializeProcess"))
					instanceId = (String)result;
				
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				GlobalContext.serialize(result, bao, String.class);
				
				System.out.println("return value :" + bao.toString(GlobalContext.DATABASE_ENCODING));
				
				re = null;
				return;
			}catch(Exception e){
				re = e;
			}
		}

		if(re!=null)
			throw re;
	}
	
	public void waitUntil(long howLong) throws InterruptedException{
		long now = System.currentTimeMillis();
		
		long howLongWait = howLong - (now - startedTime);
		
		if(howLongWait > 0)
			Thread.sleep(howLongWait);
	}

	public static void main(String[] args) throws Exception{
//		ProcessManagerInvocationTest pt = new ProcessManagerInvocationTest();
//		pt.setUp();
		
//		System.out.println("---cleanup---");
//		System.out.println(pt.cleanup.toString());
	}
	
}

