package org.uengine.ui.flowchart.impl;

import org.uengine.kernel.Activity;
import org.uengine.ui.flowchart.model.ProcessDefinitionInfo;

public class MakeProcessDefinitionToJSON extends AbstractMakeProcessDefinitionToJSON {

	@Override
	protected ProcessDefinitionInfo prefixLogic(Activity act) {
		return new ProcessDefinitionInfo();
	}

	@Override
	protected ProcessDefinitionInfo suffixLogic(Activity act, ProcessDefinitionInfo pdInfo) {
		return pdInfo;
	}

}
