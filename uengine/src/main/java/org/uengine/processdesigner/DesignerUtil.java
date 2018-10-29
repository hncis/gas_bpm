package org.uengine.processdesigner;

import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
* @author Jinyoung Jang
*/

public class DesignerUtil{


	public static String getObjectName( Class cls){
		//String objName  = obj.getClass().getName();
	
		String clsName  = cls.getName();
		clsName = clsName.substring( clsName.lastIndexOf(".")+1);
//System.out.println( " Object name ===========  : " + clsName);
		return clsName;
	}

	public static Class findClass( Class cls, String endString){
		return findClass( "", cls, endString);
	}
	public static Class findClass( String startString, Class cls){
		return findClass( startString, cls, "");
	}
	public static Class findClass( String startString, Class cls, String endString){
		try{
			String clsName = getObjectName( cls);
				
			Class findedCls = Class.forName( startString + clsName + endString);
//System.out.println( "Finded this class : " + cls);
			
			return findedCls;
			
		}catch( Exception ex){}
		
		return null;
	}
	
	public static String getImagesPath(){
		return "./dcwf/admin/designer/images/";
	}
	
	public static String getFilesPath(){
		return "./dcwf/admin/designer/files/";
	}
	
	public static ImageIcon getImage( String imageName){
		return new ImageIcon( getImagesPath() + imageName);
	}
	
	public static Point getAbsoluteLocation( Component cmp){
		Frame f = new Frame();
		
		return DesignerUtil.getAbsoluteLocation( cmp, f.getClass());
	}
	
	public static Point getAbsoluteLocation( Component cmp, Object parent){
		Vector cmps = new Vector();
		Component parentCmp = cmp;
		Point location = new Point(0,0);
		
		cmps.add( cmp);
		while( true){

			if( parent instanceof Component){
				if(  parentCmp.getParent() == parent){
					cmps.add( parentCmp.getParent());

					break;

				}
			}
			
			if( parent instanceof Class){
				
				if( parent == Frame.class || parent == JFrame.class){
					if( parentCmp.getParent() instanceof Frame){
						cmps.add( parentCmp.getParent());

						break;
					}
				}else if( parent == Dialog.class || parent == JDialog.class){
					if( parentCmp.getParent() instanceof Dialog){
						cmps.add( parentCmp.getParent());

						break;
					}
				}
			}
				
			cmps.add( parentCmp.getParent());
			parentCmp = parentCmp.getParent();

		}
		for( int i=0; i<cmps.size(); i++){
			location.x = location.x + ((Component)cmps.get(i)).getLocation().x;
			location.y = location.y + ((Component)cmps.get(i)).getLocation().y;
		}
		return location;
	}
	
	public static Frame getMyFrame( Component cmp){
		
		Component parentCmp = cmp.getParent();
		
		while( true){
			
			if( parentCmp instanceof Frame)
				return (Frame)parentCmp;
				
			parentCmp = parentCmp.getParent();
		}
	}
	
	public static Dialog getMyDialog( Component cmp){
		
		Component parentCmp = cmp.getParent();
		
		while( true){
			
			if( parentCmp instanceof Dialog)
				return (Dialog)parentCmp;
				
			parentCmp = parentCmp.getParent();
		}
	}
}