package org.uengine.processdesigner;

import org.uengine.kernel.Activity;
import org.uengine.kernel.descriptor.*;

import org.uengine.processdesigner.inputters.*;
import org.metaworks.*;
import java.util.Hashtable;
import java.lang.reflect.Method;

/**
 * @author Jinyoung Jang
 */

public class ActivityRecord extends Instance{

	Hashtable setterMethods = new Hashtable();

	Activity activity;
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity value) {
			activity = value;

			setterMethods.clear();
			Method[] allMethods = getActivity().getClass().getMethods();
			for (int i = 0; i < allMethods.length; i++) {
				Method thisMethod = allMethods[i];
				if (thisMethod.getName().startsWith("set")
					&& thisMethod.getParameterTypes().length == 1) {
					setterMethods.put(thisMethod.getName(), thisMethod);
				}
			}
		}

////////////////
	public ActivityRecord(ActivityDescriptor table, Activity act){
		super(table);
		setActivity(act);
		
		//setTable(new ActivityTable(getActivity().getClass()));
	}

	public void setFieldValue(String keyStr, Object val){
		try{			
//System.out.println("setting field: "+keyStr+" val:"+val);
			((Method)(setterMethods).get("set"+keyStr)).invoke(getActivity(), new Object[]{val});
			
			//save the script for future reference
			ScriptingValueInput.saveExtendedValue(val, getActivity(), keyStr);
			
		}catch(Exception e){
			System.out.println("Setting '"+keyStr+"' failed. maybe type mismatch.");
		}
	}
	
	public Object getFieldValueObject(String keyStr){
//System.out.println("getting field: "+keyStr);
		try{
			return getActivity().getClass().getMethod("get"+keyStr, new Class[]{}).invoke(getActivity(), new Object[]{});
		}catch(Exception e){
			try{
				return getActivity().getClass().getMethod("is"+keyStr, new Class[]{}).invoke(getActivity(), new Object[]{});
			}catch(Exception e2){
			}
		}
		
		return null;
	}
	
}