package org.uengine.test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.uengine.kernel.DefaultProcessInstance;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;

import junit.framework.TestCase;

public class StandaloneProcessExecutionTest extends TestCase {

	
	public static void main(String args[]) throws Exception{
		
		ProcessInstance.USE_CLASS = DefaultProcessInstance.class;

		ProcessDefinition pd = (ProcessDefinition) GlobalContext.deserialize(new FileInputStream(args[0]), ProcessDefinition.class);
		
		ProcessInstance pi = pd.createInstance();
		
		pi.set("", "fieldName", "1");
		
		try{
			pi.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
