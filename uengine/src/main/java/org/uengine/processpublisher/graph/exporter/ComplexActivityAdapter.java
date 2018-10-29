package org.uengine.processpublisher.graph.exporter;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityUtil;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.processpublisher.Adapter;
import org.uengine.processpublisher.graph.GraphActivity;

public class ComplexActivityAdapter implements Adapter{

	public Object convert(Object src, Hashtable keyedContext) throws Exception {

		ActivityUtil activityUtil = new ActivityUtil();
		ComplexActivity srcDef = (ComplexActivity)src;
		GraphActivity previousGraphAct = null;//graphActivity;
		GraphActivity headerGA = null;

		for(int i=0; i<srcDef.getChildActivities().size(); i++){
			
			Activity childAct = (Activity) srcDef.getChildActivities().get(i);
			
			GraphActivity currGraphAct;
		    if (childAct instanceof Activity) {
				Adapter adapter = ProcessDefinitionAdapter.getAdapter(childAct.getClass());
				currGraphAct = (GraphActivity) adapter.convert(childAct, keyedContext);
			} else {
				currGraphAct = new GraphActivity(childAct);
			}
		    
		    if (previousGraphAct != null) {
			    if(previousGraphAct.getReferenceActivity() instanceof BackActivity) {
			    	previousGraphAct.addNext(currGraphAct);
			    } else {
					GraphActivity tracing = previousGraphAct;

					Hashtable graphList = new Hashtable();
					Vector<GraphActivity> endNodeList = new Vector<GraphActivity>();
				    tracing.getEndNodeList(tracing, graphList,endNodeList);
				    
					for (GraphActivity tracingTemp : endNodeList) {
						if (!activityUtil.isVisible(childAct, keyedContext)) tracingTemp.addNext(currGraphAct);
					}
			    }
			} else {
				headerGA = currGraphAct;
			}
		    
		    if (!activityUtil.isVisible(childAct, keyedContext)) {
				previousGraphAct = currGraphAct;
				String tt = currGraphAct.getReferenceActivity().getTracingTag();
				
				if (!keyedContext.containsKey(tt)) {
					keyedContext.put(tt, currGraphAct);
				}
		    }
			

		}

		return headerGA;
	}

}
