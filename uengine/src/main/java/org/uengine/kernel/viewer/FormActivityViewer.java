package org.uengine.kernel.viewer;

import java.util.Map;
import java.util.Vector;

import org.uengine.contexts.HtmlFormContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FormActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.SubProcessActivity;

public class FormActivityViewer extends DefaultActivityViewer {

	public StringBuffer getActivityPropertyString(Activity activity,ProcessInstance instance, Map options) throws Exception {
		FormActivity formActivity = (FormActivity) activity;
		
		StringBuffer formActivityPropertyString = super.getActivityPropertyString(activity, instance, options);
		String formDefinitionId = ((HtmlFormContext)formActivity.getVariableForHtmlFormContext().getDefaultValue()).getFormDefId();
		
		formActivityPropertyString.append("formDefinitionId=").append(formDefinitionId).append(",");
		
		String[] taskId = formActivity.getTaskIds(instance);
		if(taskId !=null)
			formActivityPropertyString.append("taskId=").append(taskId[0]).append(",");
		
		return formActivityPropertyString;
	}
}
