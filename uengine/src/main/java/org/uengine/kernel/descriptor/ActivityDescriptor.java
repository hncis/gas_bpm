package org.uengine.kernel.descriptor;

import org.uengine.processdesigner.*;
import org.uengine.ui.XMLValueInput;
import org.uengine.util.UEngineUtil;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.metaworks.*;
import org.metaworks.Type;
import org.metaworks.inputter.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ActivityDescriptor extends Type{

	//review: too many static fields may occur waste of memory
	public static Vector fieldOrder = new Vector();
	static{
		fieldOrder.add("Role");
		fieldOrder.add("Name");
	}
	
	public static Properties allowOnlyFieldList = new Properties();
	static{
		if("PMT".equals(System.getProperty("designerMode"))){
			allowOnlyFieldList.setProperty("Name", "");
			allowOnlyFieldList.setProperty("Role", "");
			allowOnlyFieldList.setProperty("StatusCode", "");
			allowOnlyFieldList.setProperty("TracingTag", "");
		}
	}
	
	public static Properties exceptionFieldList = new Properties();
	static{
		exceptionFieldList.setProperty("ChildActivities","");
		exceptionFieldList.setProperty("Roles","");
		exceptionFieldList.setProperty("ProcessVariableDescriptors","");
		exceptionFieldList.setProperty("ProcessVariables","");
		exceptionFieldList.setProperty("ExtendedAttributes","");
		exceptionFieldList.setProperty("Parent",""); //important
//		exceptionFieldList.setProperty("QueuingEnabled","");
//		exceptionFieldList.setProperty("DynamicChangeAllowed","");
//		exceptionFieldList.setProperty("RetryDelay","");
//		exceptionFieldList.setProperty("Cost","");
//		exceptionFieldList.setProperty("RetryLimit","");
//		exceptionFieldList.setProperty("ReceiverId","");
//		exceptionFieldList.setProperty("Adlink","");
//		exceptionFieldList.setProperty("Wincolor","");
//		exceptionFieldList.setProperty("Soundfile","");
//		exceptionFieldList.setProperty("LinkParameterMap","");
	}

	public void initialize(ProcessDesigner value, Activity activity) {
		processDesigner = value;
		
		//Disabled
		setAttributeIgnoresError("TracingTag", 	"disabled", true);
		
		//Hidden
		//setAttributeIgnoresError("Workload", 	"hidden", new Boolean(true));
		//setAttributeIgnoresError("Instruction", 	"hidden", new Boolean(true));
		//setAttributeIgnoresError("Input", 	"hidden", new Boolean(true));
		//setAttributeIgnoresError("Tool", 	"hidden", new Boolean(true));
		//Group
		String labelForBasicProperties = GlobalContext.getLocalizedMessage("propertygroupname.basic", "Basic Properties");

		setAttributeIgnoresError("TracingTag", 	"group", 	labelForBasicProperties);
		setAttributeIgnoresError("Name", 		"group", 	labelForBasicProperties);
		setAttributeIgnoresError("Description", "group", 	labelForBasicProperties);

		String labelForMonitoringProperties = GlobalContext.getLocalizedMessage("propertygroupname.monitoring", "Monitoring Options");

		setAttributeIgnoresError("Hidden", 		"group", 	labelForMonitoringProperties);
		setAttributeIgnoresError("Keyword", 	"group", 	labelForMonitoringProperties);

		String labelForQueuingOption = GlobalContext.getLocalizedMessage("propertygroupname.queuing", "Queuing Options");
		
		setAttributeIgnoresError("RetryDelay", 		"group", 	labelForQueuingOption);
		setAttributeIgnoresError("QueuingEnabled", 	"group", 	labelForQueuingOption);
		setAttributeIgnoresError("RetryLimit", 		"group", 	labelForQueuingOption);
		setAttributeIgnoresError("FaultTolerant", 	"group", 	labelForQueuingOption);
		setAttributeIgnoresError("RetryDelay", 	"collapseGroup", 	true);

		String labelForDynamicChange = GlobalContext.getLocalizedMessage("propertygroupname.dynamicchange", "Dynamic Change");
		setAttributeIgnoresError("DynamicChangeAllowed", "group", labelForDynamicChange);
		setAttributeIgnoresError("DynamicChangeAllowed", "collapseGroup", true);

		
		/*FieldDescriptor fd = getFieldDescriptor("TracingTag");
		fd.setAttribute("disabled", new Boolean(true));
		fd.setAttribute("group", "�⺻d��");

		fd = getFieldDescriptor("Name");
		fd.setAttribute("group", "�⺻d��");*/
								 
		setFieldDisplayNames("activitytypes.org.uengine.kernel.activity");	
		
		
		removeFieldDescriptor("ActivityIcon");
		
//		FieldDescriptor fd = getFieldDescriptor("ActivityIcon");
//		if(fd !=null){
//			fd.setDisplayName("Select Icons");
//			fd.setInputter(new XMLValueInput("/liferayintegration/iconsListXML.jsp"));
//		}
		
		if(activity!=null)
		try{
			Method m = activity.getClass().getMethod("metaworksCallback_changeMetadata", new Class[]{Type.class});
			m.invoke(null, new Object[]{this});
		}catch(java.lang.NoSuchMethodException nsme){
		}catch(Exception e){
			e.printStackTrace();
		}

	}
		
	protected void setAttributeIgnoresError(String fieldName, String attributeName, Object value){
		
		if(fieldName.endsWith("*")){
			String prefix = fieldName.substring(0, fieldName.length()-1);
			
			FieldDescriptor[] fds = getFieldDescriptors();
			for(int i=0; i<fds.length; i++){
				if(fds[i].getName().startsWith(prefix)) fds[i].setAttribute(attributeName, value);
			}			
		}
		
		FieldDescriptor fd = getFieldDescriptor(fieldName);
		if(fd!=null)
			fd.setAttribute(attributeName, value);
	}

	ProcessDesigner processDesigner;

		public ProcessDesigner getProcessDesigner() {
			return processDesigner;
		}
		
		public void setProcessDesigner(ProcessDesigner designer) {
			processDesigner = designer;
		}
		
		protected void setFieldDisplayNames(String prefix){			
			/*String myPkgName = getClass().getPackage().getName();
			String myClsName = UEngineUtil.getClassNameOnly(getClass()).toLowerCase();
			String prefix = 
				"activitytypes." 
				+ myPkgName.substring(0, myPkgName.length() - "descriptor".length())
				+ myClsName.substring(0, myClsName.length() - "Descriptor".length());
				*/
					
			FieldDescriptor[] fds = getFieldDescriptors();
			if(fds!=null)
			for(int i=0; i<fds.length; i++){
				String displayName = GlobalContext.getLocalizedMessage(prefix + ".fields." + fds[i].getName().toLowerCase() + ".displayname");
				if(displayName!=null)
					fds[i].setDisplayName(displayName);                        	
			}
		}
		protected void setFieldDisplayNames(Class activityCls){
			try{
				setName((String)ProcessDesigner.getInstance().getActivityTypeNameMap().get(activityCls));
			}catch(Exception e){				
			}
			
			setFieldDisplayNames("activitytypes." + activityCls.getName().toLowerCase());
		}

		public ActivityDescriptor() throws Exception{
			super();
		}
		
		protected ActivityDescriptor(Class actCls) throws Exception{
			super(actCls.getName());
			setActivityClass(actCls);
		}
		
		public void setActivityClass(Class actCls) throws Exception{
			setName(UEngineUtil.getClassNameOnly(actCls));
			
            Method [] methods = actCls.getMethods();
            
     		Vector fdVt = new Vector();       
     		Hashtable fdHt = new Hashtable();
            for(int i=0; i<methods.length; i++){

                if(     methods[i].getParameterTypes().length==0
                		&& (
                			methods[i].getName().startsWith("get") 
                			|| 
                			(methods[i].getName().startsWith("is") && methods[i].getReturnType()==boolean.class))
                   
                ){
                    String fieldName = methods[i].getName();
                    
                    if(methods[i].getName().startsWith("is")){
						fieldName = fieldName.substring(2, fieldName.length());
                    }else{
						fieldName = fieldName.substring(3, fieldName.length());
                    }

                    Class retType = methods[i].getReturnType();
                    
                    try{
                        if("PMT".equals(System.getProperty("designerMode"))&& allowOnlyFieldList != null && !(allowOnlyFieldList.containsKey(fieldName))) continue;
                        
                        if(!exceptionFieldList.containsKey(fieldName) && actCls.getMethod("set" + fieldName, new Class[]{retType}) != null){
                        	FieldDescriptor fd = new FieldDescriptor( fieldName, fieldName);
                        	fd.setType(retType);
                        	
                        	try{
                            	Inputter inputter = ObjectType.getDefaultInputter(retType);
                            	fd.setInputter(inputter);
                            }catch(Exception e){	                                        	
                    		}
						
							fdVt.add(fd);
							fdHt.put(fd.getName(), fd);
                        }
                        
                        
                    }catch(Exception e){}
                }
            }               
            
            for(Enumeration enumeration = fieldOrder.elements(); enumeration.hasMoreElements();){
            	String fieldName = (String)enumeration.nextElement();
            	if(!fdHt.containsKey(fieldName)) continue;
            	
            	Object fd = fdHt.get(fieldName);
            	fdVt.remove(fd);
				fdVt.insertElementAt(fd, 0);
            }
            
			for(Enumeration enumeration = fdVt.elements(); enumeration.hasMoreElements();){
				setFieldDescriptor((FieldDescriptor)enumeration.nextElement());
			}
			
        }
        
        public void save(Instance rec){
        }

        public void update(Instance rec){
                save(rec);
        }
        
////// private methods /////////////////

/*        protected static Inputter getDefaultInputter(Class type) throws Exception{
        	boolean isArray = type.isArray();  
        	String inputterName = type.getName();
			if(inputterName.startsWith("[L")){
				inputterName = inputterName.substring(2, inputterName.length()-1);
			}
        	String originalTypeName = inputterName;
			inputterName = inputterName.replace('.', '_');
			
			if(isArray)
				try{
					Class inputterCls = Class.forName("org.uengine.processdesigner.inputters." + inputterName + "ArrayInput");
					return (Inputter)inputterCls.newInstance();
				}catch(Exception e){
				}
			
System.out.println("trying finding inputter name is '" + inputterName + "Input'" );
			try{
				Inputter inputter = null;
				
	        	Class inputterCls = Class.forName("org.uengine.processdesigner.inputters." + inputterName + "Input");
	        	inputter = (Inputter)inputterCls.newInstance();
	        	System.out.println("	-- found! ");
	        	return inputter;
			}catch(ClassNotFoundException cnfe){
				if(type == String.class)
					return new TextInput();
				else
				if(isArray)
					return new ArrayObjectInput(Class.forName(originalTypeName));
				else
					return new ObjectInput(type);
			}
       	
        	
        }*/
    	
}