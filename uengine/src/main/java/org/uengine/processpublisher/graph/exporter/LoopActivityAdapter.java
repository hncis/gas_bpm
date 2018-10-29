package org.uengine.processpublisher.graph.exporter;

import java.util.Hashtable;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.SequenceActivity;
import org.uengine.processpublisher.Adapter;
import org.uengine.processpublisher.graph.GraphActivity;

public class LoopActivityAdapter extends ComplexActivityAdapter{

	public Object convert(Object src, Hashtable keyedContext) throws Exception {

		GraphActivity convertedFromSequence = (GraphActivity) super.convert(src, keyedContext);
		
		GraphActivity tracing = convertedFromSequence;
		GraphActivity startTracing =  tracing;
	
		Hashtable graphList= new Hashtable();
		Vector<GraphActivity> endList = new Vector<GraphActivity>();
		tracing.getEndNodeList(tracing, graphList,endList);
		
		for (GraphActivity tracingTemp : endList)
		{
			tracingTemp.addNext(startTracing);
		}
		
		return convertedFromSequence;
	}

}
