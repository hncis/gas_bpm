package org.uengine.ui.flowchart.impl;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.Condition;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.ui.flowchart.model.ProcessDefinitionInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public abstract class AbstractMakeProcessDefinitionToJSON {
	protected abstract ProcessDefinitionInfo prefixLogic(Activity act);
	protected abstract ProcessDefinitionInfo suffixLogic(Activity act, ProcessDefinitionInfo pdInfo);
	
	private boolean onlyHumanActivity;
		public boolean isOnlyHumanActivity() {
			return onlyHumanActivity;
		}	
		public void setOnlyHumanActivity(boolean onlyHumanActivity) {
			this.onlyHumanActivity = onlyHumanActivity;
		}

	private ArrayList<ProcessDefinitionInfo> makeChildren(List<Activity> list) {
		ArrayList<ProcessDefinitionInfo> returnProcessDefinitionInfos = new ArrayList<ProcessDefinitionInfo>(); 
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Activity act = list.get(i);
				
				if (act.isHidden() == false) {
					ProcessDefinitionInfo pdInfo = prefixLogic(act);
					
					pdInfo.setName(act.getName().getText());
					pdInfo.setTracingTag(act.getTracingTag());
					if (act instanceof HumanActivity) {
						HumanActivity humanAct = (HumanActivity) act;
						pdInfo.setRoleName(humanAct.getRole().getDisplayName().getText());
						pdInfo.setDuration(String.valueOf(humanAct.getDuration()));
						pdInfo.setDescription(humanAct.getDescription().getText());
					}
					
					if (act instanceof SubProcessActivity) {
						pdInfo.setSubDefId(((SubProcessActivity)act).getDefinitionId());
					}
					
					String activityClassName = act.getClass().toString();
					pdInfo.setType(activityClassName.substring(activityClassName.lastIndexOf(".") + 1, activityClassName.length()));
					
					if (act instanceof SwitchActivity) {
						Condition[] condition = ((SwitchActivity) act).getConditions();
						String[] conditionDescription = new String[condition.length];
						for (int j = 0; j < condition.length; j++) {
							conditionDescription[j] = condition[j].getDescription().getText();
						}
						pdInfo.setCondition(conditionDescription);
					}
					
					pdInfo = suffixLogic(act, pdInfo);
					
					if(act instanceof ComplexActivity) {
						pdInfo.setChildren(makeChildren(((ComplexActivity) act).getChildActivities()));
						if (pdInfo.getChildren() != null) {
							if (pdInfo.getChildren().size() != 0) {
								returnProcessDefinitionInfos.add(pdInfo);
							}
						}
					} else {
						if (this.onlyHumanActivity == true) {
							if (act instanceof HumanActivity) {
								returnProcessDefinitionInfos.add(pdInfo);	
							}
						} else {
							returnProcessDefinitionInfos.add(pdInfo);
						}
					}
				}
			}
		}
		
		return returnProcessDefinitionInfos;
	}

	public String toJSON(ProcessDefinition pd) throws Exception {
		
		ArrayList<ProcessDefinitionInfo> activityInfos = makeChildren(pd.getChildActivities());

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

		return xstream.toXML(activityInfos);
	}
}

