/*
 * Created on 2004-07-28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner.inputters;

import org.uengine.kernel.GlobalContext;


import javax.swing.*;
import org.metaworks.inputter.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * @author Jinyoung Jang
 */

public class WIHInput extends SelectInput{
	
	public WIHInput(){
		super(new String[]{});
		
		try{
			String sep = "/";//System.getProperty("file.separator");
			String wihPath = System.getProperty(GlobalContext.PROPERTY_JBOSS_HOME_DIR, ".") 
							+ sep + "server" + sep + "default" + sep + "deploy" + sep + "ext.ear"+ sep +"portal-web-complete.war"+sep+"html"+sep+"uengine-web" + sep + "wih";
						
			File wihRootDir = new File(wihPath);
			Vector itemsVct = new Vector();
		
			File[] wihDirs = wihRootDir.listFiles();
			for(int i=0; i<wihDirs.length; i++){
				if(wihDirs[i].isDirectory()){
					itemsVct.add(wihDirs[i].getName());
				}
			}
		
			String [] items = new String[itemsVct.size()];
			itemsVct.toArray(items);
		
			setSelections(items);
		}catch(Exception e){
			System.out.println("failed to list the WIHs...");
		}
	}
	
	public Component getNewComponent() {
		JComboBox comp = (JComboBox)super.getNewComponent();
		comp.setEditable(true);
		
		return comp;
	}

}
