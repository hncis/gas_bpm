package org.uengine.processdesigner;

import javax.swing.*;

import org.uengine.kernel.GlobalContext;

import java.awt.Component;
import java.util.Hashtable;

/**
 * @author Jinyoung Jang
 */

public class DesignerLabel extends JLabel{

	static Hashtable imageIcons = new Hashtable();
//	public static final String START = "org/uengine/kernel/images/start";
//	public static final String END = "org/uengine/kernel/images/end";
//	public static final String LOGO = "org/uengine/kernel/images/logo";
//	public static final String SPLASHLOGO = "org/uengine/kernel/images/splashlogo";
//	public static final String ARROW = "org/uengine/kernel/images/arrow";
//	public static final String ARROW_SELECTED = "org/uengine/kernel/images/arrow_selected";
//	public static final String PROGRESS = "org/uengine/kernel/images/progress";
//	public static final String FAIL = "org/uengine/kernel/images/fail";
//	public static final String SUCCESS = "org/uengine/kernel/images/success";
//	public static final String EVENTHANDLER = "org/uengine/kernel/images/lightning";
	
	public static final String START = GlobalContext.getPropertyString("pd.start.image", "org/uengine/kernel/images/start");
	public static final String END = GlobalContext.getPropertyString("pd.end.image", "org/uengine/kernel/images/end");
	public static final String LOGO = GlobalContext.getPropertyString("pd.logo.image", "org/uengine/kernel/images/logo");
	public static final String SPLASHLOGO = GlobalContext.getPropertyString("pd.splashlogo.image", "org/uengine/kernel/images/splashlogo");
	public static final String ARROW = GlobalContext.getPropertyString("pd.arrow.image", "org/uengine/kernel/images/arrow");
	public static final String ARROW_SELECTED = GlobalContext.getPropertyString("pd.arrow_selected.image", "org/uengine/kernel/images/arrow_selected");
	public static final String VERT_ARROW = GlobalContext.getPropertyString("pd.arrow_vertical.image.", "org/uengine/kernel/images/arrow_vertical");
	public static final String VERT_ARROW_SELECTED = GlobalContext.getPropertyString("pd.arrow_selected_vertical.image", "org/uengine/kernel/images/arrow_selected_vertical");
	public static final String PROGRESS = GlobalContext.getPropertyString("pd.progress.image", "org/uengine/kernel/images/progress");
	public static final String FAIL = GlobalContext.getPropertyString("pd.fail.image", "org/uengine/kernel/images/fail");
	public static final String SUCCESS = GlobalContext.getPropertyString("pd.success.image", "org/uengine/kernel/images/success");
	public static final String EVENTHANDLER = GlobalContext.getPropertyString("pd.lightning.image", "org/uengine/kernel/images/lightning");
	public static final String KEY = GlobalContext.getPropertyString("pd.key.image", "org/uengine/kernel/images/key");	
	
	public DesignerLabel(){
		super();
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	public DesignerLabel(String iconName){
		super();
		
		ImageIcon icon = getImageIcon(iconName);
		if(icon!=null)
			setIcon(icon);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);

	}
	
	public static ImageIcon getImageIcon(String iconName){
		if(!imageIcons.containsKey(iconName)){
			try{
				String resourcePath = iconName + ".gif";	
				
				imageIcons.put(
					iconName,
					new ImageIcon(
						ActivityLabel.class.getClassLoader().getResource(resourcePath)
					)
				);
			}catch(Exception e){
			}
		}
				
		if(imageIcons.containsKey(iconName))
			return (ImageIcon)imageIcons.get(iconName);
		else 
			return null;
	}


	
}