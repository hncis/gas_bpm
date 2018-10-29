package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;
import org.uengine.kernel.ServiceDefinition;
import org.uengine.kernel.WebServiceActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;
import org.metaworks.inputter.*;
import javax.swing.JTextField;

/**
 * @author Jinyoung Jang
 */

public class WebServiceActivityDescriptor extends ActivityDescriptor{
	
	static{
		fieldOrder.insertElementAt("Service", 0);
		fieldOrder.insertElementAt("PortType", 0);
		fieldOrder.insertElementAt("OperationName", 0);
	}

	public WebServiceActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd;
		
		fd = getFieldDescriptor("Output");
		fd.setInputter(new ProcessVariableInput());
		
		//service, portType, and operation are tied
		try{
			ProcessVariableArrayInput parameterInput = new ProcessVariableArrayInput(){
				public Object getValue(){
					ParameterContext[] vals = (ParameterContext[])super.getValue();
					Object[] realValues = new Object[vals.length];
					System.arraycopy(vals,0,realValues,0,vals.length);
					
					return realValues;
				}
			};
			
			SelectInput operationInputter = new SelectInput(new String[]{});
			final PortTypeInput portTypeInputter = new PortTypeInput(pd, operationInputter, parameterInput);
		
			fd = getFieldDescriptor("Service");
			fd.setInputter(new ServiceDefinitionInput((ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity(), portTypeInputter));
			final InputterAdapter serviceDefinitionInputter = (InputterAdapter)fd.getInputter();
			//JTextField tf = (JTextField)serviceDefinitionInputter.getValueComponent();
			//tf.setEditable(false);
			//fd.setAttribute("hidden", new Boolean(true));
		
			fd = getFieldDescriptor("PortType");
			fd.setInputter(portTypeInputter);
		
			fd = getFieldDescriptor("OperationName");
			fd.setInputter(operationInputter);

			fd = getFieldDescriptor("Parameters");
			fd.setInputter(parameterInput);

			fd = getFieldDescriptor("Role");		
			fd.setInputter(new RoleInput(pd, RoleInput.WEBSERVICE_ONLY){
				public void onValueChanged(){
					Role role = (Role)getValue();
					ServiceDefinition svcdef = (ServiceDefinition)role.getServiceType();
					serviceDefinitionInputter.setValue(svcdef);
					portTypeInputter.setServiceDefinition(svcdef);					
				}
			});

		}catch(Exception e){
			e.printStackTrace();
		}

		setFieldDisplayNames(WebServiceActivity.class);
	}
}