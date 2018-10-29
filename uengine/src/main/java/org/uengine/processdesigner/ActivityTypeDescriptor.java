/*
 * Created on 2004. 10. 28.
 */
package org.uengine.processdesigner;

import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */
public class ActivityTypeDescriptor {
	
	String group;
	String activityTypeClass;
	String name;

	public String getActivityTypeClass() {
		return activityTypeClass;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
/*		if (name == null && getActivityTypeClass() != null) {
			name = UEngineUtil.getClassNameOnly(getActivityTypeClass());
		}
*/
		return name;
	}

	public void setActivityTypeClass(String string) {
		activityTypeClass = string;
	}

	public void setGroup(String string) {
		group = string;
	}

	public void setName(String string) {
		name = string;
	}

}
