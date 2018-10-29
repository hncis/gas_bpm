package org.uengine.contexts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processdesigner.ProcessDesigner;

/**
 * This class holds descriptive string in multi-lingual support. 
 * It contains each language-specific string in the localedTexts hashmap with key as the locale.
 * When it is requested a string by caller without certain locale information (in case of being-called by the method 'getText()'),
 * it references the process definition object to get the currently set locale setting in a static-way.
 *  
 * @author Jinyoung Jang
 */

public class TextContext implements Serializable{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	
	public static TextContext createInstance(ProcessDefinition definition){
		TextContext newTC = new TextContext();
		newTC.setProcessDefinition(definition);
		return newTC;
	}
	
	public static TextContext createInstance(){
		if(!GlobalContext.isDesignTime() || ProcessDesigner.getInstance().getProcessDefinitionDesigner()==null) return createInstance(null);
		return createInstance((ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity());
	}
	
	String text;
		public String getText() {
			if(!GlobalContext.isDesignTime()){
				String defaultLocale = GlobalContext.getDefaultLocale();
				
				if(defaultLocale!=null) return getText(defaultLocale);
			}
			
			if(getProcessDefinition()==null) return text;
			return getText(getProcessDefinition().getCurrentLocale());
		}

		public String getText(String locale) {
			String textInSelectedLocale = text;
			if(/*getProcessDefinition()!=null && */locale!=null){
				
				if(localedTexts==null)
					localedTexts = new HashMap();
					
				if(localedTexts.containsKey(locale))
					textInSelectedLocale = (String)localedTexts.get(locale);
				
			}
			
			return textInSelectedLocale;
		}
		
		public void setText(String value) {
			if(text==null || (getProcessDefinition()!=null && getProcessDefinition().getCurrentLocale()==null)){
				text = value;
				return;
			}
			
			if(getProcessDefinition()!=null){
				if(localedTexts==null)
					localedTexts = new HashMap();
				
				localedTexts.put(getProcessDefinition().getCurrentLocale(), value);
					
			}else
				text = value;
		}
		
	Map localedTexts;
		public Map getLocaledTexts() {
			return localedTexts;
		}
		public void setLocaledTexts(Map localedTexts) {
			this.localedTexts = localedTexts;
		}
	
		
	transient ProcessDefinition definition;
		public ProcessDefinition getProcessDefinition() {
			if(/*definition==null && */GlobalContext.isDesignTime() && ProcessDesigner.getInstance().getProcessDefinitionDesigner()!=null){
				definition = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
			}
			return definition;
		}
		public void setProcessDefinition(ProcessDefinition definition) {
			this.definition = definition;
		}
		
	public String parse(Activity activity, ProcessInstance instance){
		return activity.evaluateContent(instance, getText()).toString();
	}

	public String toString() {
		return getText();
	}

}