package org.uengine.processdesigner;

import javax.swing.*;

import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.designer.AbstractActivityDesigner;
import org.uengine.kernel.designer.ComplexActivityDesigner;

import java.awt.Component;
import java.util.Hashtable;

/**
 * @author Jinyoung Jang
 */

public class ActivityLabel extends DesignerLabel{

	static Hashtable imageIcons = new Hashtable();

	public ActivityLabel(Class activityType){
		super();
		
		ImageIcon icon = getImageIcon(activityType);
		if(icon!=null)
			setIcon(icon);
	}
		
	public static String getSVGIconPath(Class activityType, String bandName) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
				GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
				.replace('.','/') 
				+ "/images/svg/" ;
			resourcePath = resourcePath + bandName + ".svg";
			
			return resourcePath;
		}
		
		String resourcePath = 
			activityType.getPackage().getName()
			.replace('.','/') 
			+ "/images/svg/" ;		
		resourcePath = resourcePath + bandName + ".svg";
		
		return resourcePath;
	}
	
	public static String getPNGIconPath(Class activityType, String bandName) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
					GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
					.replace('.','/') 
					+ "/images/png/" ;
			resourcePath = resourcePath + bandName + ".png";
			
			return resourcePath;
		}
		
		String resourcePath = 
				activityType.getPackage().getName()
				.replace('.','/') 
				+ "/images/png/" ;		
		resourcePath = resourcePath + bandName + ".png";
		
		return resourcePath;
	}
	
	public static String getSVGIconPath(Class activityType) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
				GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
				.replace('.','/') 
				+ "/images/svg/" ;
			String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
			resourcePath = resourcePath + clsName + ".svg";
			
			return resourcePath;
		}
		
		String resourcePath = 
			activityType.getPackage().getName()
			.replace('.','/') 
			+ "/images/svg/" ;
		
		String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
		resourcePath = resourcePath + clsName + ".svg";
		
		return resourcePath;
	}
	
	public static String getPNGIconPath(Class activityType) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
					GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
					.replace('.','/') 
					+ "/images/png/" ;
			String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
			resourcePath = resourcePath + clsName + ".png";
			
			return resourcePath;
		}
		
		String resourcePath = 
				activityType.getPackage().getName()
				.replace('.','/') 
				+ "/images/png/" ;
		
		String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
		resourcePath = resourcePath + clsName + ".png";
		
		return resourcePath;
	}

	public static String getGIFIconPath(Class activityType) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
					GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
					.replace('.','/') 
					+ "/images/" ;
			String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
			resourcePath = resourcePath + clsName + ".gif";
			
			return resourcePath;
		}
		
		String resourcePath = 
				activityType.getPackage().getName()
				.replace('.','/') 
				+ "/images/" ;
		
		String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
		resourcePath = resourcePath + clsName + ".gif";
		
		return resourcePath;
	}
	
	public static String getActivitySVGIconPath(Class activityType) {
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
				GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
				.replace('.','/') 
				+ "/images/" ;
			String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
			resourcePath = resourcePath + clsName + ".svg";
			
			return resourcePath;
		}
		
		String resourcePath = 
			activityType.getPackage().getName()
			.replace('.','/') 
			+ "/images/svg/" ;
		
		String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
		resourcePath = resourcePath + clsName + ".svg";
		
		return resourcePath;
	}
	
	public static ImageIcon getImageIcon(Class activityType){
		
		if(GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE!=null){
			String resourcePath = 
				GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE
				.replace('.','/') 
				+ "/images/" ;
			String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);

			if(AbstractActivityDesigner.isVertical && ComplexActivity.class.isAssignableFrom(activityType)) {
				resourcePath = resourcePath + clsName + "_vertical" ;
			} else {
				resourcePath = resourcePath + clsName;
			}

			System.out.println("lhv = " + resourcePath);
			try{
				ImageIcon icon = getImageIcon(resourcePath);
				if(icon!=null) return icon;
			}catch(Exception e){}
		}
		
		String resourcePath = 
			activityType.getPackage().getName()
			.replace('.','/') 
			+ "/images/" ;
		
		String clsName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
		
		if(AbstractActivityDesigner.isVertical && ComplexActivity.class.isAssignableFrom(activityType)) {
			resourcePath = resourcePath + clsName + "_vertical";
		} else {
			resourcePath = resourcePath + clsName;
		}

		System.out.println("lhv = " + resourcePath + activityType.getClass().getName());
		return getImageIcon(resourcePath);
	}
	
}