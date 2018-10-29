package org.uengine.processpublisher.graph.exporter;

import java.util.Hashtable;

import org.uengine.kernel.Activity;
import org.uengine.processpublisher.Adapter;
import org.uengine.processpublisher.graph.GraphActivity;

public class ActivityAdapter implements Adapter {

	public Object convert(Object src, Hashtable keyedContext) throws Exception {
		GraphActivity graphActivity = new GraphActivity((Activity)src);
	
		return graphActivity;
	}
}
