package org.uengine.kernel;

import java.io.Serializable;

public interface BeanPropertyResolver extends Serializable{

	public void setBeanProperty(String key, Object value);
	public Object getBeanProperty(String key);
}
