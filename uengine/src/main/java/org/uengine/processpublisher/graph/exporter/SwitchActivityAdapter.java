package org.uengine.processpublisher.graph.exporter;

import java.util.Hashtable;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.ComplexActivity;
import org.uengine.processpublisher.Adapter;
import org.uengine.processpublisher.graph.GraphActivity;

public class SwitchActivityAdapter implements Adapter{

	public Object convert(Object src, Hashtable keyedContext) throws Exception {

		ComplexActivity srcDef = (ComplexActivity)src;
		GraphActivity graphProcess = new GraphActivity(srcDef);
		ActivityUtil activityUtil = new ActivityUtil();

		for(int i=0; i < srcDef.getChildActivities().size(); i++){
			Activity childAct = (Activity) srcDef.getChildActivities().get(i);
			
			//if(childAct instanceof Activity){
				if (!activityUtil.isVisible(childAct, keyedContext)) {
					Adapter adapter = ProcessDefinitionAdapter.getAdapter(childAct.getClass());
					graphProcess.addNext((GraphActivity)adapter.convert(childAct, keyedContext));
				}
		/*	}else{
				graphProcess.addNext(new GraphActivity(childAct));
			}*/
		}
		
		return graphProcess;
	}
}
