package kr.go.nyj.components.activity;

import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.util.UEngineUtil;

public class SplitCommaVariable extends DefaultActivity {
	
	ProcessVariable inputPv;
	
	public ProcessVariable getInputPv() {
		return inputPv;
	}
	
	public void setInputPv(ProcessVariable inputPv) {
		this.inputPv = inputPv;
	}
	
	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		ProcessVariableValue inPv = inputPv.getMultiple(instance, "", inputPv.getName());
		inPv.beforeFirst();
		String[] varStrings = ((String)inPv.getValue()).split(",");
		
		ProcessVariableValue outPvv = new ProcessVariableValue();
		outPvv.setName(inputPv.getName());
		outPvv.beforeFirst();

		for (String varString: varStrings) {
			if (UEngineUtil.isNotEmpty(varString)) {
				if (outPvv.getValue() != null) {
					outPvv.moveToAdd();
				}
				outPvv.setValue(varString);
			}
		}
		outPvv.beforeFirst();
		instance.set("", outPvv);
		fireComplete(instance);
	}
}
