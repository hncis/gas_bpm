package org.uengine.kernel;

import java.util.Vector;

public class ActivityRepository extends Vector{
	transient private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	transient ComplexActivity parent;
		
	public ActivityRepository(ComplexActivity parent){
		super();
		this.parent = parent;
	}
	
	public ActivityRepository(){
		super();
	}
		
	public void add(int index, Object obj){
		Activity child = (Activity)obj;
		
		//I'm your parent...
		//if(parent!=null)
		child.setParentActivity(parent);
		
		if(index>-1)
			super.add(index, child);
		else
			super.add(child);
			
		//child.setTracingTag(parent.getTracingTag() + "_" + size());
	}

	public boolean add(Object child){
		add(-1, child);
		
		return true;
	}
}

