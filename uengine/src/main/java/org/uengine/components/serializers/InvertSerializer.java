package org.uengine.components.serializers;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Serializer;
import org.uengine.processpublisher.invert.exporter.*;
import org.uengine.processpublisher.*;
import java.io.*;
import org.xmlsoap.schemas.ws.n2003.n03.business_process.*;
import java.util.Hashtable;


/**
 * @author Jinyoung Jang
 */

public class InvertSerializer implements Serializer{

	public boolean isSerializable(Class cls){
		return (ProcessDefinition.class == cls);
	}
	
	public void serialize(Object sourceObj, OutputStream os, Hashtable extendedContext) throws Exception{
		Adapter apt = new ProcessDefinitionAdapter();
		ProcessDefinition proc = (ProcessDefinition)apt.convert(sourceObj, extendedContext);
		GlobalContext.serialize(proc, os, String.class);
		//GlobalContext.serialize(proc, new PrintStream(System.out), String.class);
	}
	
	public Object deserialize(InputStream is, Hashtable extendedContext) throws Exception{
		return null;
	}
}