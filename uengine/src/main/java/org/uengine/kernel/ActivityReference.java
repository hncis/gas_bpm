package org.uengine.kernel;

import java.io.Serializable;

public class ActivityReference implements Serializable{

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	private Activity activity;	
	private String absoluteTracingTag;
	private String instanceId;
	private String defVerId;
	
	public ActivityReference(){
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getAbsoluteTracingTag() {
		return absoluteTracingTag;
	}

	public void setAbsoluteTracingTag(String absoluteTracingTag) {
		this.absoluteTracingTag = absoluteTracingTag;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getDefVerId() {
		return defVerId;
	}

	public void setDefVerId(String defVerId) {
		this.defVerId = defVerId;
	}

}
