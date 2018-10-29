package org.uengine.kernel.descriptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.Inputter;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FormActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ParameterContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.inputters.org_uengine_contexts_MappingContextInput;
import org.uengine.util.ClientProxy;

/**
 * 
 * @author <a href="mailto:bigmahler@user.sourceforge.net">Jong-Uk Jeong</a>
 * @version $Id: FormActivityDescriptor.java,v 1.6 2011/07/22 07:33:22 curonide Exp $
 */
public class FormActivityDescriptor extends HumanActivityDescriptor {

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	Inputter bindingVariablesInput;

	Inputter bindingRolesInput;

	Inputter definitionIdInput;

	public FormActivityDescriptor() throws Exception {
		super();
	}

//	public void initialize(final ProcessDesigner pd, Activity activity) {
//		super.initialize(pd, null);
//
//		FieldDescriptor fd;
//		fd = getFieldDescriptor("VariableBindings");
//		bindingVariablesInput = fd.getInputter();
//
//		fd = getFieldDescriptor("DefinitionId");
//
//		XMLValueInput inputter = new XMLValueInput("/processmanager/processDefinitionListXML.jsp?omitVersion=false&objectType=form") {
//			
//			private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
//
//			public void onValueChanged() { 
//				changeBindingArguments((String) getValue());
//			}
//		};
//
//		fd.setInputter(inputter);
//		definitionIdInput = inputter;
//	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
			
		FieldDescriptor fd = getFieldDescriptor("MappingContext");
		
		org_uengine_contexts_MappingContextInput formMappingContextInput = new org_uengine_contexts_MappingContextInput();
		formMappingContextInput.getNewComponent();
		fd.setInputter(formMappingContextInput);
		
		removeFieldDescriptor("Parameters");
		removeFieldDescriptor("Tool");
		removeFieldDescriptor("Instruction");
		
		setFieldDisplayNames(FormActivity.class);
	}
	
	protected void changeBindingArguments(String pvId) {
		System.out.println(pvId);
		
		if (pvId == null)
			return;

		try {
			//ProcessDefinition pd = null;
			ArrayList array = null;
			if (pvId.indexOf("@") > -1) {
				String[] defIdAndVersionId = pvId.split("@");
				array = loadDesign(defIdAndVersionId[1], false); // load with

				// version id;
			} else {
				array = loadDesign(pvId, false);
				
			}
			System.out.println("load binding information...");

			
			//
			ParameterContext[] oldpvPCs = (ParameterContext[]) bindingVariablesInput.getValue();

			HashMap oldpvPCsM = new HashMap();
			if (oldpvPCs != null) {
				for (int i = 0; i < oldpvPCs.length; i++)
					oldpvPCsM.put(oldpvPCs[i].getArgument(), oldpvPCs[i]);
			}

			ParameterContext[] pvPCs = new ParameterContext[array.size()];
			
			for(int i=0;i<array.size();i++){
				pvPCs[i] = new ParameterContext();
				pvPCs[i].getArgument().setText((String)array.get(i));

				if (oldpvPCs != null && oldpvPCsM.containsKey(pvPCs[i].getArgument())) {
					ParameterContext thepvPCs = (ParameterContext) oldpvPCsM.get(pvPCs[i].getArgument());
					pvPCs[i].setVariable(thepvPCs.getVariable());
					pvPCs[i].setDirection(thepvPCs.getDirection());
				}
			}
			bindingVariablesInput.setValue(pvPCs);
            System.out.println(pvPCs);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected ArrayList loadDesign(String id, boolean withDefinitionId)
			throws Exception {

		System.out.println("loadDesign(String " + id + ", boolean "
				+ withDefinitionId + ")");
		ClientProxy proxy = ProcessDesigner.getClientProxy();
		InputStream is = proxy.showFormDefinitionWithDefinitionId(id);
		

		// InputStream is = new URL(UEngineUtil.getWebServerBaseURL() +
		// ProcessDesigner.URL_showProcessDefinitionJSPWithDefinitionId +
		// definitionId).openStream();
		ArrayList array = (ArrayList) GlobalContext.deserialize(
				is, ArrayList.class);

		return array;
	}

}