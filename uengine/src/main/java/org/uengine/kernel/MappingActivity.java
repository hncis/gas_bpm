package org.uengine.kernel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.metaworks.Type;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.contexts.MappingContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.mapper.Transformer;
import org.uengine.util.UEngineUtil;

public class MappingActivity extends DefaultActivity {

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	public final static String FILE_SYSTEM_DIR = GlobalContext.getPropertyString("filesystem.path", ProcessDefinitionFactory.DEFINITION_ROOT);

	public MappingActivity(){
		setName("mapping");
	}
	
	MappingContext mappingContext;
		public MappingContext getMappingContext() {
			return mappingContext;
		}
		public void setMappingContext(MappingContext mappingContext) {
			this.mappingContext = mappingContext;
		}
		
	public void executeActivity(ProcessInstance instance) throws Exception {
		MappingContext mc= getMappingContext();
		if(mc !=null){
			ParameterContext[] params = mc.getMappingElements();
			for (int i = 0; i < params.length; i++) {
				ParameterContext param = params[i];
				
				String srcVariableName = null;
				String targetFieldName = param.getArgument().getText();
				Object value = null;
				
				if(param.getVariable() == null && param.getTransformerMapping() != null){
					value = param.getTransformerMapping().getTransformer().letTransform(instance, param.getTransformerMapping().getLinkedArgumentName());
				}else{
					srcVariableName = param.getVariable().getName();				
					value = instance.getBeanProperty(srcVariableName); // varA			
				}			
				
				instance.setBeanProperty(targetFieldName, (Serializable)value); //[instance].instanceId
	
			}	
		}
		fireComplete(instance);
	}
}
