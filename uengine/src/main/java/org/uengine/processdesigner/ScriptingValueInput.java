package org.uengine.processdesigner;

import org.uengine.kernel.Activity;

import org.uengine.processdesigner.*;
import org.metaworks.inputter.AbstractComponentInputter;
import org.apache.bsf.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ScriptingValueInput extends AbstractComponentInputter{

	//review: Hard-coded. Revision is seriously required.
	protected static Hashtable scripts = new Hashtable();

	JTextArea contents;

	JButton valueViewer;

	Object value;
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
			valueViewer.setText("Eval: " + value);
		}
		
	String initScript;
		private String getInitScript() {
			return initScript;
		}
		private void setInitScript(String value) {
			initScript = value;
		}
	
	public ScriptingValueInput(ProcessDesigner pd, String initScript){
		super();
		setInitScript(initScript);
	}
	
	public Component getNewComponent(){		
		JPanel p = new JPanel(new BorderLayout());

		contents = new JTextArea( 4, 20);
		contents.setText(getInitScript());
		p.add("Center", new JScrollPane(contents));
		
		valueViewer = new JButton("Eval:");
		valueViewer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				testScript();
			}
		});
		p.add("South", valueViewer);
		
		p.setPreferredSize(new Dimension(380,250));

		return p;
	}
	
	protected void testScript(){
		String script = getScript();
		
		try{
			BSFManager manager = createBSFManager();
			BSFEngine engine = manager.loadScriptingEngine("javascript");
			
			Object result = engine.eval("my_class.my_generated_method",0, 0, "function getVal(){\n"+ script + "}\ngetVal();");

			valueViewer.setText("Eval: " + result);
			
			/*if(result instanceof org.mozilla.javascript.NativeArray){
				int length = ((org.mozilla.javascript.NativeArray)result).jsGet_length();
				
				if(length==0) result = null;
				else{
					Class itemCls = ((org.mozilla.javascript.NativeArray)result).get();
				
				}
			}*/
			
			value = result;
			setValue(result); 
			scripts.put(getValue(), script);
		}catch(Exception e){					
			contents.setText(script + "\n------- errors --------\n" + e.getMessage());
			e.printStackTrace();
		}					
	}
	
	protected BSFManager createBSFManager() throws Exception{
		BSFManager manager = new BSFManager();
		manager.setClassLoader(this.getClass().getClassLoader());
						
		return manager;		
	}
	
	//This is a Call-back method by ActivityRecord.setFieldValue. This part is temporarily hard-coded
	protected static void saveExtendedValue(Object val, Activity act, String keyStr){
		if(!ScriptingValueInput.scripts.containsKey(val)) return;
		
		String script = (String)ScriptingValueInput.scripts.get(val);
		act.setExtendedAttribute("scriptsForProperties_" + keyStr, script);
	}	
	
	public void setScript(Activity activity, String k){
		Hashtable attrs = activity.getExtendedAttributes();
//System.out.println("ScriptingValueInput::setScript . attrs = " + attrs);
		if(attrs!=null && attrs.containsKey("scriptsForProperties_"+k)){
			setScript((String)attrs.get("scriptsForProperties_"+k));
		}
	}
	
	public void setScript(String script){
		contents.setText(script);
	}
	
	public String getScript(){
		String script = contents.getText();
		int errPos = contents.getText().indexOf("\n------- errors --------\n");
		if(errPos>-1) script = script.substring(0, errPos);
		
		return script;
	}
}
	
	
	
