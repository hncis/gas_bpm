package org.uengine.kernel.descriptor;


import java.io.InputStream;
import java.util.HashMap;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.Inputter;
import org.metaworks.inputter.RadioInput;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.kernel.SubProcessParameterContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.ui.XMLValueInput;
import org.uengine.util.ClientProxy;


/**
 * 
 * @author Jinyoung Jang
 * @author  <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version    $Id: SubProcessActivityDescriptor.java,v 1.12 2010/05/14 08:41:05 curonide Exp $
 */
public class SubProcessActivityDescriptor extends ActivityDescriptor{
	
	Inputter bindingVariablesInput;
	Inputter bindingRolesInput;
	Inputter definitionIdInput;

	public SubProcessActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
			
		FieldDescriptor fd;

		fd = getFieldDescriptor("VariableBindings");
	/*	org_uengine_kernel_ProcessVariableParameterContextArrayInput pvArrInput =  
			(org_uengine_kernel_ProcessVariableParameterContextArrayInput)fd.getInputter();*/
			
		bindingVariablesInput = fd.getInputter();

		fd = getFieldDescriptor("RoleBindings");
		bindingRolesInput = fd.getInputter();			
		
		fd = getFieldDescriptor("DefinitionId");
		XMLValueInput inputter = new XMLValueInput("/processmanager/get/list/xml/definition/process/") {
			public void onValueChanged() {
				changeBindingArguments((String)getValue());
			}
		};
		
		fd.setInputter(inputter);
		definitionIdInput = inputter;
		
		fd = getFieldDescriptor("VersionSelectOption");
		RadioInput versionSelectionOptionInput = new RadioInput(
				new String[]{
						"Use the CURRENT production version",
						"Use the production version AT THE INITIATED TIME",
						"Use the production version AT THE DESIGNED TIME", 
						"Use the version JUST SELECTED",	 
				},
				new Object[]{
						new Integer(ProcessDefinition.VERSIONSELECTOPTION_CURRENT_PROD_VER),
						new Integer(ProcessDefinition.VERSIONSELECTOPTION_PROD_VER_AT_INITIATED_TIME),
						new Integer(ProcessDefinition.VERSIONSELECTOPTION_PROD_VER_AT_DESIGNED_TIME), 
						new Integer(ProcessDefinition.VERSIONSELECTOPTION_JUST_SELECTED),							
				}
		);
		
		versionSelectionOptionInput.setAlignHorizontally(false);
		fd.setInputter(versionSelectionOptionInput);
		
		String labelForMonitoringProperties = GlobalContext.getLocalizedMessage("propertygroupname.monitoring", "Monitoring Options");
		setAttributeIgnoresError("ViewAlsoInMainProcess", 		"group", 	labelForMonitoringProperties);

		String multipleInstances= GlobalContext.getLocalizedMessage("propertygroupname.multipleinstances", "Multiple Instances");
		setAttributeIgnoresError("MultipleInstanceLabel", 		"group", 	multipleInstances);
		setAttributeIgnoresError("ForEachVariable", 		"group", 	multipleInstances);
		setAttributeIgnoresError("ForEachRole", 		"group", 	multipleInstances);
		
		
		setFieldDisplayNames(SubProcessActivity.class);		
	}
	
	protected void changeBindingArguments(String pvId){
		if(pvId==null) return;
		
		try{
			ProcessDefinition pd = null;
			if(pvId.indexOf("@") > -1){
				String [] defIdAndVersionId = pvId.split("@");
				pd = loadDesign(defIdAndVersionId[1], false);  //load with version id;			
			}else{
				pd = loadDesign(pvId, true);  //load with definition id;	
				definitionIdInput.setValue(null); //clear picker's remained selection
				definitionIdInput.setValue(pvId + "@" + pd.getId()); //reload version
				((XMLValueInput)definitionIdInput).setDisplayValue(pd.getName() + " Version_" + pd.getVersion());
				
				//return;
			}
System.out.println("load binding information...");		
			
			RoleParameterContext[] oldRPCs = (RoleParameterContext[])bindingRolesInput.getValue();
			HashMap oldRPCHM = new HashMap();
			if(oldRPCs!=null)
			for(int i=0; i<oldRPCs.length; i++)
				oldRPCHM.put(oldRPCs[i].getArgument(), oldRPCs[i]);
				
			Role[] roles = pd.getRoles();
			RoleParameterContext[] rolePCs = new RoleParameterContext[roles.length];
			
			for(int i=0; i<roles.length; i++){
				rolePCs[i] = new RoleParameterContext();
				rolePCs[i].setArgument(roles[i].getName());
				//rolePCs[i].setRole(roles[i]);
				//rolePCs[i].setDirection(rolePCs[i].getDirection());
				
				if(oldRPCs!=null && oldRPCHM.containsKey(rolePCs[i].getArgument())){
					RoleParameterContext theRPC = (RoleParameterContext)oldRPCHM.get(rolePCs[i].getArgument());
					rolePCs[i].setRole(theRPC.getRole());
					rolePCs[i].setDirection(theRPC.getDirection());					
				}
			}
			bindingRolesInput.setValue(rolePCs);
			
			//
			ParameterContext[] oldpvPCs = (ParameterContext[])bindingVariablesInput.getValue();
			
			HashMap oldpvPCsM = new HashMap();
			if(oldpvPCs!=null){
				for(int i=0; i<oldpvPCs.length; i++)
					oldpvPCsM.put(oldpvPCs[i].getArgument(), oldpvPCs[i]);
			}
			
			ProcessVariable[] processVariables = pd.getProcessVariables();
			ParameterContext[] pvPCs = new ParameterContext[processVariables.length];
			
			for(int i=0; i<processVariables.length; i++){
				pvPCs[i] = new SubProcessParameterContext();
				pvPCs[i].getArgument().setText(processVariables[i].getName());
				
				
				if(oldpvPCs!=null && oldpvPCsM.containsKey(pvPCs[i].getArgument())){
					ParameterContext thepvPCs = (ParameterContext)oldpvPCsM.get(pvPCs[i].getArgument());
					pvPCs[i].setVariable(thepvPCs.getVariable());
					pvPCs[i].setDirection(thepvPCs.getDirection());
					
					if(thepvPCs instanceof SubProcessParameterContext) 
						((SubProcessParameterContext)pvPCs[i]).setSplit(((SubProcessParameterContext)thepvPCs).isSplit());
				}
			}
			bindingVariablesInput.setValue(pvPCs);			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	protected ProcessDefinition loadDesign(String id, boolean withDefinitionId) throws Exception{	

System.out.println("loadDesign(String "+id+", boolean "+withDefinitionId+")");		
		ClientProxy proxy = ProcessDesigner.getClientProxy();
		InputStream is;

		if(withDefinitionId)
			is = proxy.showProcessDefinitionWithDefinitionId(id);
		else
			is = proxy.showProcessDefinitionWithVersionId(id);
		
		//		InputStream is = new URL(UEngineUtil.getWebServerBaseURL() + ProcessDesigner.URL_showProcessDefinitionJSPWithDefinitionId + definitionId).openStream();	
		ProcessDefinition pd = (ProcessDefinition)GlobalContext.deserialize(is, String.class);
		
		return pd;
	}
	
	
}