package org.uengine.components.serializers;

import java.util.ArrayList;

import org.uengine.processdesigner.ActivityTypeDescriptor;

public class XPDSerializer extends XStreamSerializer{
	public static void main(String args[]) throws Exception{
		
		ArrayList list = new ArrayList();
		ActivityTypeDescriptor descr = new ActivityTypeDescriptor();
		descr.setActivityTypeClass("org.uengine.kernel");
		descr.setGroup("name");
		list.add(descr);
		list.add(descr);
		
		XPDSerializer xpdSer = new XPDSerializer();
		xpdSer.serialize(list, System.out, null);
	}
}
