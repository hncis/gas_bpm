package org.uengine.components.serializers;

import org.uengine.smcp.twister.TwisterBPELToolkit;
import org.uengine.smcp.twister.engine.priv.core.definition.TwisterProcess;
import org.uengine.util.UEngineUtil;

import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Serializer;
import org.uengine.processpublisher.*;
import java.io.*;
import org.xmlsoap.schemas.ws.n2003.n03.business_process.*;
import java.util.Hashtable;
import org.uengine.util.UEngineUtil;


/**
 * @author Jinyoung Jang
 */

public class BPELSerializer implements Serializer{

	public boolean isSerializable(Class cls){
		return (ProcessDefinition.class == cls);
	}
	
	public void serialize(Object sourceObj, OutputStream os, Hashtable extendedContext) throws Exception{
//		Adapter apt = new ProcessDefinitionAdapter();
//		process proc = (process)apt.convert(sourceObj, null);
//		
//		//review: I can't find a proper method replacing the tag names
//		ByteArrayOutputStream bao = new ByteArrayOutputStream();
//		
//		proc.marshal(new PrintWriter(bao));
//		String bpel = bao.toString();		
//		bpel = bpel.replaceAll("case_", "case");	
//		bpel = bpel.replaceAll("switch_", "switch");	
//		PrintWriter pw = new PrintWriter(os);
//		pw.print(bpel);
//		pw.flush();
//		pw.close();	
	}
	
	public Object deserialize(InputStream is, Hashtable extendedContext) throws Exception{
// deserialize by xgen doesn't work so use the Twister's BPEL toolkit instead.
/*		EntityResolver er = new ChainedEntityResolver();
		Unmarshaller un = new Unmarshaller(er);
		GlobalElement doc = un.unmarshal(is, "file:///C:/Java/xml/xgen/docsoapxdk/BPELv1_1.xsd");
*/

		InputStream defIS = (InputStream)extendedContext.get("wsdl");
		
		TwisterProcess tp = (new TwisterBPELToolkit()).read(is, defIS);
				
		return null;
	}
	
	public static String toSafeName(String srcStr, String defStr){
		if(!UEngineUtil.isNotEmpty(srcStr)) return defStr;
		
		return srcStr.replace(' ', '_').replace('?', '_').replace('[', '_').replace(']','_');
	}
}