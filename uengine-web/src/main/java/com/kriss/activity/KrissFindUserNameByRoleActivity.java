package com.kriss.activity;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.MultipleInput;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;
import org.uengine.processdesigner.ProcessDesigner;

import com.kriss.component.KrissRoleAndVariableParameterContext;

public class KrissFindUserNameByRoleActivity extends DefaultActivity {
	
	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd = type.getFieldDescriptor("Parameters");
		fd.setDisplayName("이름으로 변환할 롤 정의");
		
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("Role");
		fd.setDisplayName("원본 롤");
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("Variable");
		fd.setDisplayName("사용자명 저장 변수");
		
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("Argument");
		fd.setAttribute("hidden", new Boolean(true));
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("Type");
		fd.setAttribute("hidden", new Boolean(true));
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("TransformerMapping");
		fd.setAttribute("hidden", new Boolean(true));
		fd = ((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().getFieldDescriptor("Direction");
		fd.setAttribute("hidden", new Boolean(true));
		
		((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("Argument");
		((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("Type");
		((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("TransformerMapping");
		((MultipleInput) type.getFieldDescriptor("Parameters").getInputter()).getTable().removeFieldDescriptor("Direction");
		
	}

	public KrissFindUserNameByRoleActivity() {
		setName("롤 이름 찾기");
	}

	KrissRoleAndVariableParameterContext[] parameters;

	public KrissRoleAndVariableParameterContext[] getParameters() {
		return parameters;
	}

	public void setParameters(KrissRoleAndVariableParameterContext[] parameters) {
		this.parameters = parameters;
	}




	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		
		for ( int i = 0; i < parameters.length; i++ ) {
			KrissRoleAndVariableParameterContext parameter = parameters[i];
			RoleMapping rm = parameter.getRole().getMapping(instance);
			instance.set("", parameter.getVariable().getName(), rm.toString());
		}

		fireComplete(instance);
	}

}
