/*
 * Created on 2005. 2. 14.
 */
package org.uengine.ui;

import org.metaworks.inputter.Picker;
import org.uengine.kernel.GlobalContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.util.ClientProxy;
import org.uengine.util.UEngineUtil;


// Basic GUI components
import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;

import javax.swing.SwingUtilities;

// GUI support classes
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


// For creating a TreeModel
import javax.swing.tree.*;


/**
 * @author Jinyoung Jang
 */
public class XMLValuePicker extends JDialog implements Picker{
	JTree tree;
	Object value;
	boolean cancelled = false;

	public Object getValue() {
		try{
//			if(UEngineUtil.isNotEmpty(""+value)) return value;
			return ((AdapterNode)tree.getLastSelectedPathComponent()).getValue();
		}catch(Exception e){
			return value;
		}
	}
	
	public String getDisplayValue(){
		try{
			return ((AdapterNode)tree.getLastSelectedPathComponent()).toString();
		}catch(Exception e){
			return "<null>";
		}		
	}

	public void setValue(Object val) {
System.out.println("xml value picker. setValue (" + val + ")");
		value = val;
		if(val == null && tree !=null)
			tree.clearSelection();
	}

	public XMLValuePicker(String url) {
		this(url, false);
	}
	
	public XMLValuePicker(final String url, final boolean partialLoading) {
		super(ProcessDesigner.getInstance());
		System.out.println("XMLValuePicker");
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);

		getContentPane().setLayout(new BorderLayout());

		final JPanel treePanel = new JPanel(new BorderLayout());
		//treePanel.setPreferredSize(new Dimension(180, 200));
		final JButton selBtn = new JButton("Select");
		selBtn.setEnabled(false);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				try{
					TreeModel treeModel = new DomToTreeModelAdapter(url, partialLoading);
					tree = new JTree();
					tree.setModel(treeModel);
					//tree.setPreferredSize(new Dimension(180, 400));
					treePanel.removeAll();
					treePanel.add("Center", tree);
					//pack();
					treePanel.revalidate();
					selBtn.setEnabled(true);
					
				}catch(RuntimeException e){
					e.printStackTrace();
					
					treePanel.removeAll();
					treePanel.add("Center", new JTextArea(e.getMessage()));
					treePanel.revalidate();
					
					//dispose();
				}
				
			}
		});
		
		treePanel.add("Center", new JLabel("Loading..."));
				   
		JScrollPane treeView = new JScrollPane(treePanel);
		treeView.setPreferredSize(new Dimension(300, 200));

		getContentPane().add("Center", treeView );

		selBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setValue(getValue());
				cancelled = false;
				dispose();
			}
		});

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				cancelled = true;
				dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout());{
			buttonPanel.add(selBtn);
			buttonPanel.add(cancelBtn);		
		}
		
		buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add("South", buttonPanel);

		pack();
		setModal(true);

		addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
			}
			public void focusLost(FocusEvent e) {
				dispose();
			}
		});

	} 
	
	public Component getComponent() {
		return this;
	}

	public boolean showPicker() {
		pack();
		show();
		
		return !cancelled;
	}

}
