package org.uengine.kernel.viewer;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.ProcessInstance;

import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class TryCatchActivityViewer extends AllActivityViewer{

	String[] labels = {"try", "catch"};
	protected String getLabel(int i, Activity activity, ProcessInstance instance, Map options){
		if(i < labels.length)
			return labels[i];
		else
			return "";
	}		

	public StringBuilder render(Activity activity, ProcessInstance instance, Map options){
		StringBuilder sb = new StringBuilder();
		
//		sb.append("<table border=1 cellpadding=0 cellspacing=3><!--td width=2 bgcolor=gray></td--><td>");
		sb.append("<table border=0 cellpadding=0 cellspacing=3><td width=2 bgcolor=black></td><td>");
		sb.append("<table border=0 cellpadding=0 cellspacing=0><tr>");
		
		ComplexActivity cActivity = (ComplexActivity)activity;
		
		int i=0;
		for(Iterator<Activity> iter = cActivity.getChildActivities().iterator(); iter.hasNext(); ){
			sb.append("<tr><td><font face=-2 color=gray>" + getLabel(i++, activity, instance, options) + "</font></td></tr>");
			sb.append("<tr><td>");
			
			Activity child = iter.next();			
			ActivityViewer viewer = DefaultActivityViewer.createViewer(child);			
			StringBuilder childHTML = viewer.render(child, instance, options);			
			sb.append(childHTML);			
			
			sb.append("</td><tr>");
		}
		
		//sb.append(activity.getName());
		sb.append("</tr></table>");
//		sb.append("</td><!--td width=2 bgcolor=gray></td--></table>");
		sb.append("</td><td width=2 bgcolor=black></td></table>");
		
		return sb;
	}

}