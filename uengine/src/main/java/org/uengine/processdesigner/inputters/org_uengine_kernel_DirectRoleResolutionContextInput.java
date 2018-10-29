package org.uengine.processdesigner.inputters;

import java.awt.Component;

import org.metaworks.inputter.InputterAdapter;
import org.uengine.kernel.DirectRoleResolutionContext;
import org.uengine.ui.XMLValuePicker;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_DirectRoleResolutionContextInput extends InputterAdapter{

	String url = "/organization/get/xml/organization/chart";
	XMLValuePicker xmlValuePicker;
	 

	public org_uengine_kernel_DirectRoleResolutionContextInput(){
		super();
	}

	public org_uengine_kernel_DirectRoleResolutionContextInput(String comCode){
		super();
		this.url = url+"/"+comCode;
	}

	public Object getValue() {
		Object value = xmlValuePicker.getValue();
		String resourceName = xmlValuePicker.getDisplayValue();
		
		DirectRoleResolutionContext drrc = new DirectRoleResolutionContext();
		drrc.setResourceName(resourceName);
		drrc.setEndpoint(""+value);
		
		return drrc;
	}

	public void setValue(Object obj) {
		if(!(obj instanceof DirectRoleResolutionContext)){
			return;
		}
	}

	public Component getNewComponent() {
		xmlValuePicker = new XMLValuePicker(url){
			public void setValue(Object val){
				super.setValue(val);
				onValueChanged();
			}
		};
		
		return xmlValuePicker.getContentPane();		
	}

}