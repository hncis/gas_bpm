/*
 * Created on 2004. 12. 28.
 */
package org.uengine.processdesigner;

import java.lang.reflect.Constructor;

import javax.swing.*;

import org.metaworks.inputter.Picker;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.RoleResolutionContext;
/**
 * @author Jinyoung Jang
 */
public abstract class RolePicker extends JDialog implements Picker{
	
	public static Class USE_CLASS;
	static{
		try{
			USE_CLASS = Class.forName(GlobalContext.getPropertyString("pd.rolepicker.class"));
		}catch(Exception e){
			USE_CLASS = DefaultRolePicker.class;
		}
	}

	public RolePicker() {
		super();
	}

	public RolePicker(JFrame parent) {
		super(parent);
	}
	
	public static RolePicker create(){
		return create(ProcessDesigner.getInstance());
	}

	public static RolePicker create(JFrame owner){
		try{
			Constructor constructor = USE_CLASS.getConstructor(new Class[]{JFrame.class});
			return (RolePicker)constructor.newInstance(new Object[]{owner});		
		}catch(Exception e){
			//e.printStackTrace();			
			return new DefaultRolePicker(owner);
		}
	}
	
	abstract public void setRoleResolutionContext(RoleResolutionContext roleResolutionContext);
	abstract public RoleResolutionContext getRoleResolutionContext();	

}
