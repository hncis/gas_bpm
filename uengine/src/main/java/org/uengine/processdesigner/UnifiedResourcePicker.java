/*
 * Created on 2004-06-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner;

import org.apache.log4j.Logger;

import org.uengine.kernel.GlobalContext;

import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import org.uengine.ui.*;
import org.uengine.util.*;

/**
 * @author Jinyoung Jang
 */
public class UnifiedResourcePicker extends JDialog implements org.metaworks.inputter.Picker{	
	Object value;
	JTextField url;
	Vector recentList;
	final JTabbedPane tabPane;
	Hashtable tabNameIndex = new Hashtable();

	
	public UnifiedResourcePicker(){
		this(null);
	}
		
	public UnifiedResourcePicker(final String type){
		super(ProcessDesigner.getInstance(), "Unified Resource Picker", true);
		
		url = new JTextField();
		
		getContentPane().setLayout(new BorderLayout());
		tabPane = new JTabbedPane(JTabbedPane.LEFT){
			public Component add(String tabName, Component c){
				tabNameIndex.put(tabName, new Integer(getTabCount()));
				return super.add(tabName, c);
			}
		};
System.out.println("UnifiedResourcePicker::init1");		
		//file
		final JFileChooser fc = new JFileChooser(ProcessDesigner.getInstance().getCurrentWorkingPath());
		
/*		if(type!=null)
		fc.setFileFilter(new FileChooserFilter(type));
*/		
		tabPane.add("Local", fc);
		fc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try{
					url.setText(fc.getSelectedFile().toURL().toString());					
					ProcessDesigner.getInstance().setCurrentWorkingPath(fc.getSelectedFile().getPath().toString());
				}catch(Exception e){
				}
			}
		});
		System.out.println("UnifiedResourcePicker::init2");        
        //ftp
//		tabPane.add( "FTP", new ftp.ui.FtpClient().getContentPane());
		tabPane.add( "FTP", new JLabel("ftp client"));
		
		//http
		final JEditorPane http = new JEditorPane(){
			public void setPage(String page) throws IOException{
				super.setPage(page);
			}
			public void setPage(URL page) throws IOException{
				super.setPage(page);
			}
		};
		http.setContentType("text/html");
		http.setEditable(false);
/*		http.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				URL url_ = http.getPage();
				url.setText(url_.toString());			
			}			
		});*/
		url.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new Thread(){
					public void run(){
						try{	
							http.setPage(url.getText());
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
		tabPane.add( "HTTP", new JScrollPane(http));
		
		System.out.println("UnifiedResourcePicker::init3");
		//uddi
		JButton launchUDDIBrowserBtn = new JButton("Launch UDDI Browser");
		ActionListener launchAction = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				AntToolDialog ant = new AntToolDialog();
				ant.setCommand("uddibrowser");
				ant.run();
			}
		};		
		launchUDDIBrowserBtn.addActionListener(launchAction);
		tabPane.add( "UDDI", launchUDDIBrowserBtn);
				
		getContentPane().add("Center", tabPane);
		
		JPanel urlPanel = new JPanel(new BorderLayout());
		urlPanel.add("West", new JLabel("  URL: "));
		urlPanel.add("Center", url);
		JButton closeBtn;
		urlPanel.add("East", closeBtn = new JButton("Bind"));
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				value = url.getText();
				if(recentList.contains(value)){
					recentList.remove(value);
				}
				recentList.insertElementAt(value.toString(), 0);
				setConfirmed(true);
				dispose();
			}
		});
		System.out.println("UnifiedResourcePicker::init4");
		//recent
		try{
			recentList = (Vector)GlobalContext.deserialize(new FileInputStream(GlobalContext.RECENT_URLS_FILE), String.class);
		}catch(Exception e){
			recentList = new Vector();	
		}
		
		final JList recentJList;
		tabPane.add( "Recent", new JScrollPane(recentJList = new JList(recentList)));
		recentJList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = recentJList.getSelectedIndex();
					url.setText(""+recentJList.getSelectedValue());
				 }
			}
		});
		
		getContentPane().add("South", urlPanel);

		pack();
		setLocationRelativeTo(ProcessDesigner.getInstance());
		
		//server definition browser
		final JPanel processDefinitions = new JPanel(new BorderLayout());
		processDefinitions.add(new JLabel("     Loading..."));
		new Thread(){
			public void run(){
				XMLValuePicker picker = new XMLValuePicker(
					GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/processDefinitionListXML?"
				){
					public void setValue(Object val){
						super.setValue(val);
						url.setText(ProcessDesigner.getClientProxy().getHttpClient().getHost()+val);
					}
				};
				processDefinitions.removeAll();
				System.out.println("UnifiedResourcePicker");
				System.out.println("picker: " + picker);
				processDefinitions.add("Center", picker.getContentPane());
				processDefinitions.revalidate();				
			}
		}.start();
		
		tabPane.add("Server", new JScrollPane(processDefinitions));
		//
		
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value != null) {
			url.setText(value.toString());
			ActionListener[] als = url.getActionListeners();
			for (int i = 0; i < als.length; i++) {
				//review: may cause some problem
				als[i].actionPerformed(null);
			}

			this.value = value;
		}
	}

	
	public void setTab(String tabName){
		tabPane.setSelectedIndex(((Integer)tabNameIndex.get(tabName)).intValue());
	}
	
	public void dispose() {
		try{
			GlobalContext.serialize(recentList, new FileOutputStream(GlobalContext.RECENT_URLS_FILE), String.class);
		}catch(Exception e){			
		}
		
		super.dispose();
	}
	
	public class FileChooserFilter implements FileFilter{
		String extension;
		
		FileChooserFilter(String ext){
			extension = ext;
		}
		
		public boolean accept(File pathname) {
			return pathname.getName().toLowerCase().endsWith("." + extension.toLowerCase());
		}
	}
	
	
	////// properties ///////////
	
	boolean confirmed = false;
	

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean b) {
		confirmed = b;
	}

	public Component getComponent() {
		return null;
	}

	public boolean showPicker() {
		show();
		return true;
	}

}
