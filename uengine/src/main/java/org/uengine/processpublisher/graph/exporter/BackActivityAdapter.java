package org.uengine.processpublisher.graph.exporter;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.uengine.contexts.ActivitySelectionContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.FlagActivity;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.util.UEngineUtil;

public class BackActivityAdapter extends ActivityAdapter{
	
	public GraphActivity getTargetGraphActivity(Hashtable keyedContext) {
		GraphActivity tmpGa = null;
		
		Collection tmpList = keyedContext.values();
		
		for (Iterator iterator = tmpList.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			
			if (obj instanceof GraphActivity) {
				GraphActivity tmp = (GraphActivity) obj;
				
				if (tmp.getReferenceActivity() instanceof FlagActivity) {
					tmpGa = tmp;
					break;
				}
			}
		}
		
		return tmpGa;
	}

	public Object convert(Object src, Hashtable keyedContext) throws Exception {
		GraphActivity graphActivity = (GraphActivity) super.convert(src, keyedContext);
		
		BackActivity bAct = (BackActivity) graphActivity.getReferenceActivity();
		ActivitySelectionContext asc = bAct.getTargetActivity();
		String tt = asc.getTracingTag();
		
		if (UEngineUtil.isNotEmpty(tt) && keyedContext.get(tt) != null) {
			graphActivity.addNext((GraphActivity) keyedContext.get(tt));
		} else {
			GraphActivity tmpGa = getTargetGraphActivity(keyedContext);
			if (tmpGa != null) graphActivity.addNext(tmpGa);
		}
		
		return graphActivity;
	}
}
