package org.uengine.kernel;

public class ActivityBeanResolver implements BeanPropertyResolver{

	ProcessInstance instance;
	public ActivityBeanResolver(ProcessInstance instance){
		this.instance = instance;
	}
	
	public Object getBeanProperty(String key)  {
		
		Activity act=null;
		
		try {
			act = instance.getProcessDefinition().getActivity(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return act;
	}

	public void setBeanProperty(String key, Object value) {
		
	}

}
