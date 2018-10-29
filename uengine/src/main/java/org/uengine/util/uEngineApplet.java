package org.uengine.util;
import java.applet.Applet;
import java.io.ByteArrayOutputStream;
//import java.io.ByteArrayInputStream;
import java.util.Hashtable;

import org.uengine.components.serializers.XStreamSerializer;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.viewer.ActivityViewer;
import org.uengine.kernel.viewer.DefaultActivityViewer;

public class uEngineApplet extends Applet {

	public void init() {
		super.init();
	}

	public String flowChartTransform(String definitionXml) throws Exception{
		//try{
			Hashtable options = new Hashtable();
			ProcessDefinition definition = (ProcessDefinition) GlobalContext.deserialize(definitionXml, ProcessDefinition.class);

			//XStreamSerializer serializer = new XStreamSerializer();
			//ByteArrayInputStream bis = new ByteArrayInputStream(definitionXml.getBytes("UTF-8"));

			//ProcessDefinition definition = (ProcessDefinition) GlobalContext.deserialize(definitionXml, null);
		
			//			ProcessInstance instance = new DefaultProcessInstance();
			//instance.setProcessTransactionContext(getTransactionContext());
			ActivityViewer processDefinitionViewer = DefaultActivityViewer.createViewer(definition);
			return processDefinitionViewer.render(definition, null, options).toString();
			//return ProcessDefinitionViewer.getInstance().render(getDefinition(processDefinition), null, options).toString();
		//}catch(Exception e){
//			ByteArrayOutputStream bao = new ByteArrayOutputStream();
//			return "반영되고있니???:"+e.getMessage();
//		}
		

	}
}
