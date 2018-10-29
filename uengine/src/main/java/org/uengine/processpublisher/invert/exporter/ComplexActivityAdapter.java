package org.uengine.processpublisher.invert.exporter;
	
import java.util.Hashtable;
import java.util.Iterator;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.Role;
import org.uengine.kernel.SequenceActivity;
import org.uengine.processpublisher.Adapter;
 
/**
 * @author Jinyoung Jang
 */
   
public class ComplexActivityAdapter implements Adapter{
	
	public Object convert(Object src, Hashtable keyedContext) throws Exception{
		ComplexActivity srcAct = (ComplexActivity)src;
		ComplexActivity dstAct = createDestinationActivity();
						
		int i=0;
		for(Iterator<Activity> iter = srcAct.getChildActivities().iterator(); iter.hasNext(); ){
			Activity item = iter.next();
			Adapter adpt = ProcessDefinitionAdapter.getAdapter(item.getClass());
			if(adpt==null){
				continue;
			}
			
			Activity convertedAct = (Activity)adpt.convert(item, keyedContext);
			if(convertedAct!=null){
				dstAct.addChildActivity(convertedAct);
				i++;							
			}
		}		
		
		if(i==0) return null;
		if(i==1) return dstAct.getChildActivities().get(0);
		
		return dstAct;
	}
	
	protected ComplexActivity createDestinationActivity(){
		return new SequenceActivity();
	}
}
