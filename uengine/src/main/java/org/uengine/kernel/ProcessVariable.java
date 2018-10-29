package org.uengine.kernel;

import javax.xml.namespace.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import org.apache.bsf.BSFEngine;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.metaworks.inputter.*;
import org.metaworks.validator.*;
import org.metaworks.*;
import org.uengine.contexts.DatabaseSynchronizationOption;
import org.uengine.contexts.TextContext;
import org.uengine.processdesigner.DataTypeInput;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.ScriptInput;

/**
 * @author Jinyoung Jang
 */

public class ProcessVariable implements java.io.Serializable, NeedArrangementToSerialize, Cloneable{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	//this method will be invoked by metaworks framework and only in design time
	public static void metaworksCallback_changeMetadata(Type type){
		String advancedOptions = GlobalContext.getLocalizedMessage("role.advancedoptions.label", "Advanced Options");
		
		FieldDescriptor fd;

		//table.removeFieldDescriptor("OpenRoles");
		type.removeFieldDescriptor("XmlBindingClassName");
		type.removeFieldDescriptor("QName");

		fd = type.getFieldDescriptor("Inputter");
		fd.setAttribute("group", advancedOptions);
		fd.setAttribute("hiddenInTable", "yes");
		fd.setValidators(new Validator[]{
			new Validator(){

				public String validate(Object data, Instance instance) {
					Inputter inputter = (Inputter)data;
					Class type = (Class)instance.getFieldValue("Type");

					if( inputter.isEligibleType(type)) return null;

					return "This inputter is not eligible for " + type;
				}
				
			}
		});

		fd = type.getFieldDescriptor("AskWhenInit");		
		fd.setAttribute("group", advancedOptions);
		fd.setAttribute("hiddenInTable", "yes");
		fd.setAttribute("collapseGroup", true);
		
//		fd.setAttribute("hidden", new Boolean(true));
////		fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.askwheninit.displayname"));
//				
//		fd = table.getFieldDescriptor("XmlBindingClassName");		
//		fd.setAttribute("group", "XML");
////		fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.xmlbindingclassname.displayname"));
//		
//		fd = table.getFieldDescriptor("QName");		
//		fd.setAttribute("group", "XML");
////		fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.qname.displayname"));
//
//		fd = table.getFieldDescriptor("Inputter");		
//		fd.setAttribute("hidden", new Boolean(true));
//		fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.inputter.displayname"));

		fd = type.getFieldDescriptor("DefaultValue");	
			fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.defaultvalue.displayname"));
			final ObjectInput defaultValueInputter = new ObjectInput();		
			fd = type.getFieldDescriptor("DefaultValue");
			fd.setInputter(defaultValueInputter);
			fd.setAttribute("hiddenInTable", "yes");
				
		fd = type.getFieldDescriptor("Name");
			//fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.name.displayname"));
			fd.setValidators(new Validator[]{new NotNullValid()}); //it's mandatory

		fd = type.getFieldDescriptor("DisplayName");		
			fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.displayname.displayname", "Display Name"));

//		fd = table.getFieldDescriptor("ValidationScript");
//			final ScriptingInputterForProcessVariable validationScriptInputter = new ScriptingInputterForProcessVariable(ProcessDesigner.getInstance());
//			fd.setInputter(validationScriptInputter);

		fd = type.getFieldDescriptor("Type");{		
			fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.type.displayname", "Type"));
			
			final Inputter variableTypeInput = fd.getInputter();
			
			fd.setInputter(variableTypeInput);			
			fd.setValidators(new Validator[]{new NotNullValid()}); //it's mandatory
			
			variableTypeInput.addActionListener(new ActionListener(){
	
				public void actionPerformed(ActionEvent e) {
					Class varType = (Class)variableTypeInput.getValue();
					if(varType!=null){					
						defaultValueInputter.setType(varType);
//						validationScriptInputter.setType(varType);
					}
				}
				
			});
		}

		fd = type.getFieldDescriptor("OpenRole");		
		fd.setDisplayName(GlobalContext.getLocalizedMessage("processvariable.openrole.displayname", "Open to these roles"));
		fd.setAttribute("hiddenInTable", "yes");
		fd.setAttribute("group", advancedOptions);
		
		type.setName(GlobalContext.getLocalizedMessage("processvariable.displayname", "Process Variable"));
		

		//---- database synchronized field --------------
		fd = type.getFieldDescriptor("DatabaseSynchronizationOption");		
			fd.setAttribute("dependancy", new EnablingDependancy("DatabaseSynchronized"){
	
				public boolean enableIf(Object dependencyFieldValue) {
					return ((Boolean)dependencyFieldValue).booleanValue();
				}
				
			});
		
			fd.setAttribute("group", "Database Synchronization");
			fd.setAttribute("hiddenInTable", "yes");
		
		fd = type.getFieldDescriptor("DatabaseSynchronized");		
			fd.setAttribute("group", "Database Synchronization");
			fd.setAttribute("collapseGroup", true);
			fd.setAttribute("hiddenInTable", "yes");
			
	}

	String name;
		public String getName() {
			return name;
		}
		public void setName(String value) {
			name = value;
		}

	TextContext displayName = TextContext.createInstance();	
		public TextContext getDisplayName(){
			if(displayName.getText()==null){
				TextContext result = TextContext.createInstance();
				result.setText(getName());
				return result;
			}
			
			return displayName;
		}	
		public void setDisplayName(TextContext value){
			displayName = value;
		}
	
	Class type;  	
		public Class getType(){
			if(type==null){
				if(getXmlBindingClassName()!=null){
					try{
						type = GlobalContext.getComponentClass(getXmlBindingClassName());
					}catch(Exception e){
						System.out.println("Warning: Binding class not found at design time.");
					}
				}
				//The other case need to be coded: dynamic compilation of XML binding classes based on the QName
			}
			
			return type;
		}
		public void setType(Class type){
			this.type = type;
		}

	Role openRole;
		public Role getOpenRole() {
			return openRole;
		}
		public void setOpenRole(Role openRole) {
			this.openRole = openRole;
		}
	
	String xmlBindingClsName;
		public String getXmlBindingClassName(){
			if(getQName()!=null && xmlBindingClsName==null)
				xmlBindingClsName = org.uengine.util.UEngineUtil.QName2ClassName(getQName());
								
			return xmlBindingClsName;
		}
		public void setXmlBindingClassName(String value){
			xmlBindingClsName = value;
		}

	QName qname;
		public QName getQName(){
			return qname;
		}
		public void setQName(QName value){
			qname = value;
		}

/*	Serializable defaultValue;
		public Serializable getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(Serializable object) {
			defaultValue = object;
		}*/

	InputterAdapter inputter;
		public InputterAdapter getInputter() {
			return inputter;
		}
		public void setInputter(InputterAdapter inputter) {
			this.inputter = inputter;
		}

	
/*	Validator[] validators;
		public Validator[] getValidators() {
			return validators;
		}
		public void setValidators(Validator[] validators) {
			this.validators = validators;
		}*/	
	
/*	String bindingClassName;
	
		public String getBindingClassName(){
			if(getQName()!=null && bindingClassName==null)
				bindingClassName = getQName().getLocalPart();
				
			return bindingClassName;
		}
			
		public void setBindingClassName(String value){
			bindingClassName = value;
		}
*/
	
	boolean askWhenInit = false;
		public boolean isAskWhenInit() {
			return askWhenInit;
		}
		public void setAskWhenInit(boolean b) {
			askWhenInit = b;
		}

	Object defaultValue = null;
		public Object getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(Object object) {
			defaultValue = object;
		}
		
	boolean isVolatile;
		public boolean isVolatile() {
			return isVolatile;
		}
		public void setVolatile(boolean isVolatile) {
			this.isVolatile = isVolatile;
		}
	
	String valueString;
		public String getValueString() {
			return valueString;
		}
		public void setValueString(String valueString) {
			this.valueString = valueString;
		}
	
	Serializable value;
		public Serializable getValue() {
			return value;
		}
		public void setValue(Serializable value) {
			this.value = value;
		}
	
	private String displayNameString;
		public String getDisplayNameString() {
			return getDisplayName().getText();
		}
		public void setDisplayNameString(String displayNameString) {
			this.displayNameString = displayNameString;
		}
	
	public boolean equals(Object obj){
		if(obj !=null && getName()!=null)
			return getName().equals(((ProcessVariable)obj).getName());
			
		return false;
	}
	
	public ProcessVariable(Object[] settings){
		org.uengine.util.UEngineUtil.initializeProperties(this, settings);
	}
	public ProcessVariable(){}

	//review: The return object of this method is only for scripting users to indicate certain process variable
	public static ProcessVariable forName(String varName){	
		//review: fly-weight pattern needed
		ProcessVariable pv = new ProcessVariable();
		pv.setName(varName);
		
		return pv;
	}

	public void set(ProcessInstance instance, String scope, String key, Serializable value) throws Exception{
		if(isDatabaseSynchronized()){
			if(getDatabaseSynchronizationOption().set(instance, scope, value));
				return;
		}
		
		if(getType()!=null && (value==null || !value.getClass().isAssignableFrom(getType()))){		
			if(value instanceof String){
				String strValue = (String)value;
				if(getType()==Integer.class){
					try{
System.out.println("ProcessVariable:: converting from String to Integer");
						value = new Integer(Integer.parseInt(strValue));
					}catch(Exception e){
					}
				}//review: there are more cases can be converted from string.
			}
		}
				
		instance.set(scope, getName(), value);
	}
	
	public Serializable get(ProcessInstance instance, String scope, String key) throws Exception{		
		if(isDatabaseSynchronized()){
			Serializable value = getDatabaseSynchronizationOption().get(instance, scope);
			
			if(value instanceof ProcessVariableValue){
				return ((ProcessVariableValue)value).getValue();
			}else{
				return value;
			}
		}
		
		if(instance==null ) return (Serializable)getDefaultValue();
		
		Serializable theValue = instance.get(scope, getName());

		return theValue;
		
	}
	
	public ProcessVariableValue getMultiple(ProcessInstance instance, String scope, String key) throws Exception{		
		if(isDatabaseSynchronized()){
			
			Serializable value = getDatabaseSynchronizationOption().get(instance, scope);
			ProcessVariableValue pvv;
			
			if(value instanceof ProcessVariableValue){
				pvv = (ProcessVariableValue)value;
			}else{
				pvv = new ProcessVariableValue();
				pvv.setValue(value);
			}
			
			pvv.setName(getName());
			return pvv;
		}

		return instance.getMultiple(scope, getName());
	}
	
	public ProcessVariableValue getMultiple(ProcessInstance instance, String scope) throws Exception{
		return getMultiple(instance, scope, null);
	}

	public Serializable get(ProcessInstance instance, String scope) throws Exception{
		return get(instance, scope, null);
	}
	
	public void set(ProcessInstance instance, String scope, Serializable value) throws Exception{
		set(instance, scope, null, value);
	}

	
	public String toString() {
		String dispName = getDisplayName().toString(); 
		
		if(dispName!=null)
			return dispName;
		
		return super.toString();
	}
	
	public void afterDeserialization() {
		setName(getName());
	}

	public void beforeSerialization() {}
	
	public static Object evaluate(Object val, ProcessInstance instance) throws Exception{
		if(val instanceof ProcessVariable){
			val = ((ProcessVariable)val).get(instance, "", null);
		}		
		return val;
	}
	
	boolean isDatabaseSynchronized;
		public boolean isDatabaseSynchronized() {
			return isDatabaseSynchronized;
		}
		public void setDatabaseSynchronized(boolean isDatabaseSynchronized) {
			this.isDatabaseSynchronized = isDatabaseSynchronized;
		}
			
	DatabaseSynchronizationOption databaseSynchronizationOption;
		public DatabaseSynchronizationOption getDatabaseSynchronizationOption() {
			return databaseSynchronizationOption;
		}
		public void setDatabaseSynchronizationOption(
				DatabaseSynchronizationOption databaseSynchronizationOption) {
			this.databaseSynchronizationOption = databaseSynchronizationOption;
		}
		
//	String validationScript;
//		public String getValidationScript() {
//			return validationScript;
//		}
//		public void setValidationScript(String validationScript) {
//			this.validationScript = validationScript;
//		}
		
//	public String validateValue(Object value, ProcessInstance instance, ProcessDefinition definition){
//		BSFManager manager = new BSFManager();
//		manager.setClassLoader(this.getClass().getClassLoader());
//	
//		try {
//			manager.declareBean("instance", instance, ProcessInstance.class);
//			manager.declareBean("definition", definition, ProcessDefinition.class);
//			manager.declareBean("value", value, Object.class);
//			
//			BSFEngine engine = manager.loadScriptingEngine("javascript");
//				
////			String result = (String)engine.eval("my_class.my_generated_method",0,0,"function getVal(){\n"+ getValidationScript() + "}\ngetVal();");
////			return result;
//		} catch (BSFException e) {
//			e.printStackTrace();
//			
//			return null;
//		}
//	}
		
	public boolean shouldAccessValueInSpecializedWay(){
		return isDatabaseSynchronized();
	}
		
	public Object clone(){
		try{
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream ow = new ObjectOutputStream(bao);
			ow.writeObject(this);
			ByteArrayInputStream bio = new ByteArrayInputStream(bao.toByteArray());			
			ObjectInputStream oi = new ObjectInputStream(bio);
			
			ProcessVariable clonedOne =  (ProcessVariable) oi.readObject();
			clonedOne.setOpenRole(null);

			return clonedOne;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}


}

class ScriptingInputterForProcessVariable extends ScriptInput{
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	ProcessDesigner pd;
	ProcessInstance instance;
	Class type;
	Object value;
	
	public void setType(Class type){
		this.type = type;
	}
			
	public ScriptingInputterForProcessVariable(ProcessDesigner pd){
		super(pd);
		this.pd = pd;
	}
	
	protected org.apache.bsf.BSFManager createBSFManager() throws Exception{
		ProcessDefinition definition =(ProcessDefinition)pd.getProcessDefinitionDesigner().getActivity();
		instance = ProcessInstance.create(definition, "test instance", null);
				
		org.apache.bsf.BSFManager manager = super.createBSFManager();
		manager.declareBean("instance", instance, ProcessInstance.class);
		manager.declareBean("definition", new ScriptActivity(), Activity.class);
		manager.declareBean("value", value, Object.class);
				
		return manager;
	}	
			
	public void testScript() {
		
		Type dialogTable = new Type(
			"Please enter a test value:",
			new FieldDescriptor[]{
				new FieldDescriptor("TestValue", "Test Value")
			}
		);
		
		FieldDescriptor testValueFd = dialogTable.getFieldDescriptor("TestValue");
		testValueFd.setType(type);
		
		InputDialog inputDialog = new InputDialog(dialogTable);
		inputDialog.show();
				
		value = inputDialog.getInputForm().getInstance().getFieldValueObject("TestValue");
		
		super.testScript();
//		ValidationContext vc = instance.getValidationContext();
//		if(vc.size()>0)
//			reportError(vc.toString());
	}

}