/*
 * Created on 2004-05-31
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner;

import javax.swing.*;
import org.uengine.util.*;
import org.uengine.kernel.GlobalContext;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AntToolDialog extends JDialog{

	/**
	 * 
	 * @uml.property name="textArea"
	 * @uml.associationEnd 
	 * @uml.property name="textArea" multiplicity="(1 1)"
	 */
	JTextArea textArea;

	String command;

		/**
		 * 
		 * @uml.property name="command"
		 */
		public String getCommand() {
			return command;
		}

		/**
		 * 
		 * @uml.property name="command"
		 */
		public void setCommand(String string) {
			command = string;
		}

	
	public AntToolDialog(){
		super(ProcessDesigner.getInstance());
		setTitle("Ant"/*, false*/);
		textArea = new JTextArea();
		
		getContentPane().add(new JScrollPane(textArea));
	}
	
	public void run(){
		setTitle("Ant - " + getCommand());
		setSize(450, 400);
		setLocationRelativeTo(getOwner());
		show();
		String antTool = System.getProperty(GlobalContext.PROPERTY_ANT_PATH, "ant");

		final Object finalThis = this;		
		SystemLauncher launcher = new SystemLauncher(){
			public void complete(){
			}
			
			public void write(String line){
				textArea.append(line + "\n");				
			}
		};
		
		//review: this command will work only in Windows
		launcher.run(
			"cmd /c" + antTool +
			" -buildfile " + System.getProperty(GlobalContext.PROPERTY_UENGINE_HOME_DIR, "c:/uengine") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "build.xml" +
			" -Djboss.home=" + System.getProperty(GlobalContext.PROPERTY_JBOSS_HOME_DIR, ".") +
			" -Duengine.home=" + System.getProperty(GlobalContext.PROPERTY_UENGINE_HOME_DIR, ".") +
			" " + getCommand()
		);		
	}
	
}
