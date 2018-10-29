package org.uengine.admin;

/**
 * @author Jinyoung jang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.apache.bsf.*;
import java.io.*;
import org.metaworks.*;

public class ScriptConsole extends JPanel implements ActionListener{

	/**
	 * 
	 * @uml.property name="scriptEditor"
	 * @uml.associationEnd 
	 * @uml.property name="scriptEditor" multiplicity="(1 1)"
	 */
	JTextArea scriptEditor;

	/**
	 * 
	 * @uml.property name="resultTextArea"
	 * @uml.associationEnd 
	 * @uml.property name="resultTextArea" multiplicity="(1 1)"
	 */
	JEditorPane resultTextArea;

	/**
	 * 
	 * @uml.property name="runButton"
	 * @uml.associationEnd 
	 * @uml.property name="runButton" multiplicity="(1 1)"
	 */
	JButton runButton;
	
	String currentFilePath;

	/**
	 * 
	 * @uml.property name="scriptSelector"
	 * @uml.associationEnd 
	 * @uml.property name="scriptSelector" multiplicity="(1 1)"
	 */
	JPanel scriptSelector;

	/**
	 * 
	 * @uml.property name="placeHolder"
	 * @uml.associationEnd 
	 * @uml.property name="placeHolder" multiplicity="(1 1)"
	 */
	DefaultApplication placeHolder;

	
	public ScriptConsole(){
		scriptEditor = new JTextArea();
		resultTextArea = new JEditorPane();
		resultTextArea.setContentType("text/html");
		runButton = new JButton("Run!");
		runButton.addActionListener(this);

		JPanel scriptPanel = new JPanel(new BorderLayout());
			scriptPanel.add("Center", new JScrollPane(scriptEditor));
			scriptSelector = new JPanel(new GridLayout(0,1));
				File f = new File("." + System.getProperty("file.separator") + "scripts");
				final File[] files = f.listFiles();
				if(files!=null)
				for(int i=0; i<files.length; i++){
					if(!files[i].getName().endsWith(".bak"))
						addScriptFile(files[i]);
				}				
				JButton saveButton = new JButton("save");
				saveButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						save();
					}
				});
				JButton saveAsButton = new JButton("save current as..");
				saveAsButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						saveCurrentAs();
					}
				});				
				scriptSelector.add(saveButton);
				scriptSelector.add(saveAsButton);
			
			scriptPanel.add("West", new JScrollPane(scriptSelector));
			
				placeHolder = new DefaultApplication(
					new Type(
						"place holder",
						new FieldDescriptor[]{
							new FieldDescriptor("name"),
							new FieldDescriptor("value"),
						}
					)
				);							
				JPanel placeHolderPanel = placeHolder.createPanel();
		
//			scriptPanel.add("East", placeHolderPanel);

		JPanel runPanel = new JPanel(new BorderLayout());
		runPanel.add("Center", new JScrollPane(resultTextArea));
		runPanel.add("East", runButton);

		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scriptPanel, runPanel);
		splitPane.setDividerLocation(400);
		add("Center", splitPane);
	}	
	
	public void addScriptFile(File f){
		JButton scriptSelectingButton = new JButton(f.getName());
		final String fName = f.getAbsolutePath();
		scriptSelectingButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				openScript(fName);
			}
		});						
		scriptSelector.add(scriptSelectingButton, 0);
		scriptSelector.repaint();
	}
	
	public void openScript(String fName){
		save();
		currentFilePath = fName;
		scriptEditor.setText(getScript(fName));			
	}
	
	public String getScript(String fName){
		try{
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(fName));
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int offset = 0, readSize=0;
			while((readSize = is.read(buffer, 0, buffer.length))>0){
				bao.write(buffer, 0, readSize);
				offset+=readSize;
			}
			
			is.close();

			return bao.toString().trim();
		}catch(Exception e){
			e.printStackTrace();
			
			return "";
		}
	}
	
	public boolean save(){
		try{
			if(currentFilePath==null) return false;
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(currentFilePath));
			bos.write(scriptEditor.getText().getBytes());
			bos.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void saveCurrentAs(){
		final JDialog d = new JDialog();//null, "Enter filename to save current as..."/*, true*/);
		d.setTitle("Enter filename to save current as...");
		final JTextField tf = new JTextField();
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				currentFilePath = "scripts" + System.getProperty("file.separator") + tf.getText();					
				d.dispose();				
				if(save()) addScriptFile(new File(currentFilePath));
			}
		});
		d.getContentPane().setLayout(new BorderLayout());
		d.getContentPane().add("Center", tf);
		d.pack();
		
		Point loc = getLocationOnScreen();
		loc.move(100,100);
		d.setLocation(loc);
		d.show();
	}
	
	protected StringBuffer parse(String expression){
		StringBuffer generating = new StringBuffer();
		
		if(expression==null) return generating;
		
		int pos=0, endpos=0, oldpos=0;
		String key;
		String starter = "<%=", ending="%>";	
		
		while((pos = expression.indexOf(starter, oldpos)) > -1){
			pos += starter.length();
			endpos = expression.indexOf(ending, pos);
			if(endpos > pos){
				generating.append(expression.substring(oldpos, pos - starter.length()));
				key = expression.substring(pos, endpos);

				if(key!=null)
					generating.append(getScript("scripts" + System.getProperty("file.separator") + key));
			}
			oldpos = endpos + ending.length();
		}
		generating.append(expression.substring(oldpos));
		
		return generating;
	}
	
	public void actionPerformed(ActionEvent ae){
		save();
		String script = "<%=header%>\n" + scriptEditor.getText();
		
		while(script.indexOf("<%=")>-1)
			script = parse(script).toString();
		
		Instance[] placeHolders = placeHolder.getInstances();
		for(int i=0; i<placeHolders.length; i++){
			script = script.replaceAll((String)placeHolders[i].getFieldValue("name"), (String)placeHolders[i].getFieldValue("value"));
		}
		
		StringBuffer generating = new StringBuffer("");

				
		try{
			BSFManager manager = new BSFManager();
			manager.setClassLoader(this.getClass().getClassLoader());
		
//			manager.declareBean("foo", new Integer(1), Integer.class);
//			manager.declareBean("bar", new Integer(44), Integer.class);
			
			BSFEngine engine = manager.loadScriptingEngine("javascript");
				
			//engine.exec("my_class.my_generated_method",0,0,"function getVal(){return 5;}");
			
			Object result = engine.eval("my_class.my_generated_method",0, 0, "function getVal(){\n"+ script + "}\ngetVal();");

			resultTextArea.setText("Eval: " + result);
			
			/*if(result instanceof org.mozilla.javascript.NativeArray){
				int length = ((org.mozilla.javascript.NativeArray)result).jsGet_length();
				
				if(length==0) result = null;
				else{
					Class itemCls = ((org.mozilla.javascript.NativeArray)result).get();
				
				}
				
			}*/
			
		}catch(Exception e){								
			resultTextArea.setText("------- errors --------\n" + e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args){
		ScriptConsole scp = new ScriptConsole();
		
		JFrame sc = new JFrame("uEngine admin script console");		

		sc.getContentPane().add(scp);
		
		sc.pack();
		sc.setSize(600,500);
		sc.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		
		sc.show();
	}

}
