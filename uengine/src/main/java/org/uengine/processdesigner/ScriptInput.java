package org.uengine.processdesigner;

import org.apache.bsf.*;
import org.uengine.processdesigner.*;
import org.metaworks.inputter.TextAreaInput;

import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;

import javax.swing.*;

/**
 * @author Jinyoung Jang
 */

public class ScriptInput extends TextAreaInput{

	/**
	 * 
	 * @uml.property name="resultTextArea"
	 * @uml.associationEnd 
	 * @uml.property name="resultTextArea" multiplicity="(0 1)"
	 */
	JTextArea resultTextArea;

 
	
	public ScriptInput(ProcessDesigner pd){
		super();
	}
	
	public Component getNewComponent(){		
		JPanel resultPanel = new JPanel(new BorderLayout());
			JButton b = new JButton("Test");
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					testScript();
				}
			});		
		resultPanel.add("East", b);
		resultPanel.add("Center", new JScrollPane(resultTextArea = new JTextArea()));
		
		JComponent c = (JComponent)super.getNewComponent();
		c.setPreferredSize(new Dimension(380,250));
		
		return new JSplitPane(JSplitPane.VERTICAL_SPLIT, c, resultPanel);
	}
	
	public void testScript(){
		String script = (String)getValue();
		
/*		int oldpos = 0;
		int pos = script.indexOf("variables.", oldpos);
		pos
		
		script.replaceAll("variables.", "");*/
		
		try{
			BSFManager manager = createBSFManager();
		
			BSFEngine engine = manager.loadScriptingEngine("javascript");
				
			Object result = engine.eval(
				"my_class.my_generated_method"
				,0
				,0,
				"function getVal(){\nimportPackage(Packages.org.uengine.kernel);\n "+ getValue()+"}\ngetVal();"
			);

			resultTextArea.setText(""+result);
			
		}catch(Exception e){
/*			e.printStackTrace();
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(bao));
*/			
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				e.printStackTrace(new java.io.PrintStream(bao));
				reportError(bao.toString());
		}					
	}
	
	public void reportError(String msg){
		resultTextArea.setText(msg);
	}
	
	protected BSFManager createBSFManager() throws Exception{
		BSFManager manager = new BSFManager();
		manager.setClassLoader(this.getClass().getClassLoader());
						
		return manager;		
	}

}
	
	
	
