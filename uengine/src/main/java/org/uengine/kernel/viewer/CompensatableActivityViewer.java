package org.uengine.kernel.viewer;

/**
 * @author Jinyoung Jang
 */

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;

import java.util.Map;

public class CompensatableActivityViewer extends TryCatchActivityViewer{

	String[] labels = {"commitment", "onCompensate"};
	protected String getLabel(int i, Activity activity, ProcessInstance instance, Map options){
		if(i < labels.length)
			return labels[i];
		else
			return "";
	}
}