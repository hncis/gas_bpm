package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ParameterContext;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;
import org.metaworks.inputter.MultipleInput;
import org.metaworks.inputter.ObjectInput;

/**
 * @author Jinyoung Jang
 */

public class HumanActivityDescriptor extends ActivityDescriptor{

	public HumanActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		((MultipleInput) getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("Type");
			
		//FieldDescriptor fd = getFieldDescriptor("Role");
		//fd.setInputter(new RoleInput(pd));
		
/*		fd = getFieldDescriptor("Output");
		fd.setAttribute("hidden", new Boolean(true));*/
//		fd.setInputter(new ProcessVariableInput(pd));
		
		//fd = getFieldDescriptor("Instruction");
		//fd.setInputter(new org.metaworks.inputter.TextAreaInput());
		
		//fd = getFieldDescriptor("Message");
		//fd.setAttribute("disabled", new Boolean(true));

/*		fd = getFieldDescriptor("Parameters");
		fd.setAttribute("hidden", new Boolean(true));
*/		
		//setAttributeIgnoresError("Tool", 	"hidden", new Boolean(true));
		
		//fd.setInputter(new WIHInput());
		
		//fd = getFieldDescriptor("MessageDefinition");
		//fd.setAttribute("hidden", new Boolean(true));
		
		//fd = getFieldDescriptor("FromRole");
		//fd.setAttribute("hidden", new Boolean(true));

/*		fd = getFieldDescriptor("Name");
		fd.setDisplayName("��Ƽ��Ƽ ��");
		fd = getFieldDescriptor("Keyword");
		fd.setDisplayName("��� ��� Ű���");
		fd = getFieldDescriptor("Workload");
		fd.setDisplayName("�۾� �ҿ� �ε�");
		fd = getFieldDescriptor("Priority");
		fd.setDisplayName("�켱��'");
		fd = getFieldDescriptor("DueDate");
		fd.setDisplayName("������");
		fd = getFieldDescriptor("Instruction");
		fd.setDisplayName("�۾� ��ó���");
		fd = getFieldDescriptor("Parameters");
		fd.setDisplayName("��� ���� ����");
		fd = getFieldDescriptor("Description");
		fd.setDisplayName("����");
		fd = getFieldDescriptor("RetryLimit");
		fd.setDisplayName("���н� ��õ� Ƚ��");
		fd = getFieldDescriptor("RetryDelay");
		fd.setDisplayName("��õ� ����(��)");
		fd = getFieldDescriptor("DynamicChangeAllowed");
		fd.setDisplayName("���� �� ���氡��");
		fd = getFieldDescriptor("Duration");
		fd.setDisplayName("�۾� �Ⱓ(��)");
		*/
		//Hidden
		//setAttributeIgnoresError("Message", 	"disabled", new Boolean(true));
		//setAttributeIgnoresError("Tool", 	"hidden", new Boolean(true));
		setAttributeIgnoresError("MessageDefinition", 	"hidden", true);
		setAttributeIgnoresError("FromRole", 	"hidden", true);
		//setAttributeIgnoresError("Workload", 	"hidden", new Boolean(true));
		//setAttributeIgnoresError("Instruction", 	"hidden", new Boolean(true));
		//setAttributeIgnoresError("Tool", 	"hidden", new Boolean(true));
		setAttributeIgnoresError("Message", 	"hidden", true);
		setAttributeIgnoresError("Input", 	"hidden", true);
		setAttributeIgnoresError("Co2Emission", 	"hidden", true);
		//Group
		
		String advancedOptions = GlobalContext.getLocalizedMessage("propertygroupname.advancedoptions", "Advanced Options");
		setAttributeIgnoresError("ReferenceRole", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Priority", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Cost", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Workload", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Duration", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Keyword", 	"group", 	advancedOptions);
		setAttributeIgnoresError("Duration", 	"collapseGroup", 	true);

		String worklistExtValue = GlobalContext.getLocalizedMessage("propertygroupname.extensionworklistdata", "Extension Worklist Data");
		setAttributeIgnoresError("ExtValue1", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue2", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue3", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue4", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue5", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue6", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue7", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue8", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue9", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue10", 	"group", 	worklistExtValue);
		setAttributeIgnoresError("ExtValue10", 	"collapseGroup", 	true);
			
		setFieldDisplayNames(HumanActivity.class);
	}

}