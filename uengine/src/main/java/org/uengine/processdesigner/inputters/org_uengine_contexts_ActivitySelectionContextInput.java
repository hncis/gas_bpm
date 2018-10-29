package org.uengine.processdesigner.inputters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.InputterAdapter;
import org.metaworks.inputter.ObjectInput;
import org.metaworks.web.HTML;
import org.uengine.contexts.ActivitySelectionContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessTransactionContext;

public class org_uengine_contexts_ActivitySelectionContextInput extends ObjectInput{
	
	public org_uengine_contexts_ActivitySelectionContextInput(){
		super(ActivitySelectionContext.class);
	}
	
	public Object createValueFromHTTPRequest(Map req, String section, String name, Object oldValue) {
		String inputName = "_" + section + "_" + name;
		String instanceIdInputName = inputName + "_instanceId";
		String tracingTagInputName = inputName + "_tracingTag";
		
		String instanceId = ((String[])req.get(instanceIdInputName))[0];
		String tracingTag = ((String[])req.get(tracingTagInputName))[0];

		ActivitySelectionContext asc = new ActivitySelectionContext();
		asc.setInstanceId(instanceId);
		asc.setTracingTag(tracingTag);
		
		return asc;
	}

	public String getInputterHTML(String section, FieldDescriptor fd, Object defaultValue, Map options) {
		String inputName = "_" + section + "_" + fd.getName();
		String instanceIdInputName = inputName + "_instanceId";
		String tracingTagInputName = inputName + "_tracingTag";
		
		ActivitySelectionContext defaultASC = (ActivitySelectionContext)defaultValue;
		ProcessInstance instance = (ProcessInstance)options.get("instance");
	
		if(defaultASC==null)
			defaultASC = new ActivitySelectionContext();
		
		StringBuffer html = new StringBuffer();
		html
			.append("<input type=hidden name='" + instanceIdInputName + "'"+ HTML.getAttrHTML("value", defaultASC.getInstanceId()) +">")
			.append("<input type=hidden name='" + tracingTagInputName + "'"+ HTML.getAttrHTML("value", defaultASC.getTracingTag()) +">")
			.append("<input type=button name='" + inputName + "_display' value='Select Step' onclick=\"openActivityPicker('"+ inputName +"', '"+ instance.getRootProcessInstanceId() +"')\">")
		;
    	
		return html.toString();
	}
	
	public void addScriptTo(Properties scripts, String section, FieldDescriptor fd, Object defaultValue, Map options) {
		StringBuffer openActivityPicker = new StringBuffer();
		openActivityPicker
			.append("<script>\n")
			.append("function openActivityPicker(inputName, instanceId){\n")
			.append("	var activityPicker = window.open('"+ GlobalContext.WEB_CONTEXT_ROOT +"/common/activityPicker.jsp?instanceId='+instanceId,'_new','width=700,height=500,resizable=yes,scrollbars=yes');\n")
			.append("	activityPicker.onOk = onActivitySelected;\n")
			.append("	activityPicker.inputName = inputName;\n")
			.append("}\n")
			.append("</script>\n")
		;	
		
		scripts.put("openActivityPicker", openActivityPicker);

		StringBuffer onActivitySelected = new StringBuffer();
		onActivitySelected
			.append("<script>\n")
			.append("function onActivitySelected(activitySelection, inputName){\n")
			.append("	document.forms[0].all[inputName+'_instanceId'].value = activitySelection.instanceId;\n")
			.append("	document.forms[0].all[inputName+'_tracingTag'].value = activitySelection.tracingTag;\n")
			.append("	document.forms[0].all[inputName+'_display'].value = activitySelection.activityName;\n")
			.append("}\n")
			.append("</script>\n")
		;	
		
		scripts.put("onActivitySelected", onActivitySelected);	
	}

	public String getViewerHTML(String section, FieldDescriptor fd, Object defaultValue, Map options) {
		ProcessInstance instance = (ProcessInstance)options.get("instance");
		
		ActivitySelectionContext asc = (ActivitySelectionContext)defaultValue;
		
		if(asc==null || asc.getInstanceId()==null || asc.getTracingTag()==null) return "-";
		try {
			HashMap param = new HashMap();
			param.put("ptc", instance.getProcessTransactionContext());
			ProcessInstance theInstance = ProcessInstance.create().getInstance(asc.getInstanceId(), param);
			Activity theActivity = theInstance.getProcessDefinition().getActivity(asc.getTracingTag());
			
			return theActivity.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return super.getViewerHTML(section, fd, defaultValue, options);
	}

}
