package org.uengine.processpublisher.invert.exporter;

import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;

	
/**
 * @author Jinyoung Jang
 */
   
public class AllActivityAdapter extends ComplexActivityAdapter{
	
	protected ComplexActivity createDestinationActivity() {
		return new AllActivity();
	}

}
