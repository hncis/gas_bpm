package org.uengine.kernel.descriptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.processdesigner.*;
import org.metaworks.inputter.*;
import org.metaworks.*;

/**
 * @author Jinyoung Jang
 */

public class ProcessDefinitionDescriptor extends ActivityDescriptor implements PropertyChangeListener{

	public ProcessDefinitionDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		String [] disableProps ={ 
			"ReservedXmlTypes",
			"MessageDefinitions",
			"ServiceDefinitions",
			"ActivityFilters",
//			"RetryLimit",
//			"RetryDelay",
			"TracingTag",
			"UEngineVersion",
			"BelongingDefinitionId",
			"EventHandlers",
			"ActivitySequence",
			"CurrentLocale",
			"LeaveEventListenersEvenIfOutOfScope",
			//"Hidden",
		};
		
		FieldDescriptor fd;
		
		for(int i=0; i<disableProps.length; i++){
			//fd = getFieldDescriptor(disableProps[i]);
			//fd.setAttribute("hidden", "true");
			setAttributeIgnoresError(disableProps[i], 	"hidden", true);
		}
		
//		fd = getFieldDescriptor("Description");
//		fd.setInputter(new TextAreaInput());	

		//fd = getFieldDescriptor("Id");
		//fd.setAttribute("disabled", "true");

		//fd = getFieldDescriptor("ModifiedDate");
		//fd.setAttribute("disabled", "true");

		//fd = getFieldDescriptor("Version");
		//fd.setAttribute("disabled", "true");
		
		//Disabled
		setAttributeIgnoresError("Id", 	"disabled", true);
		setAttributeIgnoresError("ModifiedDate", 	"disabled", true);
		setAttributeIgnoresError("Version", 	"disabled", true);
	
/*		fd = getFieldDescriptor("ProcessType");
		fd.setInputter(new SelectInput(
			new Object[]{"Workflow", "EAI", "Pageflow"},
			new Object[]{
				new Integer(ProcessDefinition.PROCESS_TYPE_WORKFLOW),
				new Integer(ProcessDefinition.PROCESS_TYPE_INTEGRATION),
				new Integer(ProcessDefinition.PROCESS_TYPE_PAGEFLOW)
			}
		));*/
		
		activity.addProperyChangeListener(this);
			
		setFieldDisplayNames(ProcessDefinition.class);
		
		String labelForWebservicesProperties = GlobalContext.getLocalizedMessage("propertygroupname.webservices", "Web Services Options");
		setAttributeIgnoresError("WsdlLocation", "group", labelForWebservicesProperties);
		setAttributeIgnoresError("Endpoint", "group", labelForWebservicesProperties);
		setAttributeIgnoresError("Endpoint", "collapseGroup", true);
		
		String labelForPerformanceProperties = GlobalContext.getLocalizedMessage("propertygroupname.performance", "Performance");
		setAttributeIgnoresError("Duration", "group", labelForPerformanceProperties);
		setAttributeIgnoresError("Cost", "group", labelForPerformanceProperties);
		
		String labelForWorklistProperties = GlobalContext.getLocalizedMessage("propertygroupname.worklist", "WorkList Options");
		setAttributeIgnoresError("InstanceListFields", "group", labelForWorklistProperties);
		setAttributeIgnoresError("WorkListFields", "group", labelForWorklistProperties);
		setAttributeIgnoresError("WorkListFields", "collapseGroup", true);
		
		String labelForMonitoringProperties = GlobalContext.getLocalizedMessage("propertygroupname.monitoring", "Monitoring Options");
		setAttributeIgnoresError("Collapsed", "group", 	labelForMonitoringProperties);
		setAttributeIgnoresError("Collapsed", "collapseGroup", true);
		
		String labelForSimulationProperties = GlobalContext.getLocalizedMessage("propertygroupname.simulation", "Simulation Properties");
		setAttributeIgnoresError("Simulation*", "group", 	labelForSimulationProperties);

		String labelForDynamicChangeProperties = GlobalContext.getLocalizedMessage("propertygroupname.dynamic", "Dynamic Change");
		setAttributeIgnoresError("Adhoc", "group", labelForDynamicChangeProperties);

	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		FieldDescriptor fd = getFieldDescriptor("Name");
		if(evt.getPropertyName().equalsIgnoreCase("name")){
			fd.getInputter().setValue(evt.getNewValue());
		}
	}	

}