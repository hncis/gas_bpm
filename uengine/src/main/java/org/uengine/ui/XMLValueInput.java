/*
 * Created on 2005. 2. 15.
 */
package org.uengine.ui;

import java.awt.Component;

import javax.swing.JTextField;

import org.metaworks.inputter.Picker;
import org.metaworks.inputter.PickerInput;
import org.uengine.kernel.Activity;
import org.uengine.processdesigner.ActivityHandlingInputter;

/**
 * @author Jinyoung Jang
 */

public class XMLValueInput extends PickerInput implements ActivityHandlingInputter{
	String xmlURL;
	Activity editingActivity;
	String propertyName;
	boolean partialLoading;
	
	
	public XMLValueInput(String url){
		this(url, false);
	}
	
	public XMLValueInput(String url, boolean partialLoading){
		this.xmlURL = url;
		this.partialLoading = partialLoading;
	}

	public XMLValueInput(String url, boolean partialLoading, boolean isAbsoluteCreatePicker){
		this.xmlURL = url;
		this.partialLoading = partialLoading;
		setAbsoluteCreatePicker(isAbsoluteCreatePicker);
	}
	
	public Picker getNewPicker(){
		return new XMLValuePicker(xmlURL, partialLoading);
	}
	
	public Object getValue() {
		return super.getValue();
	}

	public void setValue(Object obj) {
		super.setValue(obj);

		try{
			String dispVal = (String)editingActivity.getExtendedAttributes().get(propertyName + "_displayValue");

			JTextField tf = (JTextField)getValueComponent();
			tf.setText(dispVal);
		}catch(Exception e){
		}
		
	}

	public void setActivity(Activity activity, String propertyName) {
		editingActivity = activity;
		this.propertyName = propertyName;
	}

	public Activity getActivity() {
		return editingActivity;
	}

	protected void onSelect() {
		if(editingActivity!=null){
			String dispVal = ((XMLValuePicker)getPicker()).getDisplayValue();
			editingActivity.setExtendedAttribute(propertyName + "_displayValue", dispVal);
		}
		
		super.onSelect();
	}

	public void setDisplayValue(String dispVal){
		editingActivity.setExtendedAttribute(propertyName + "_displayValue", dispVal);
		JTextField tf = (JTextField)getValueComponent();
		tf.setText(dispVal);
	}

	public String getXmlURL() {
		return xmlURL;
	}

	public void setXmlURL(String xmlURL) {
		this.xmlURL = xmlURL;
	}

}
