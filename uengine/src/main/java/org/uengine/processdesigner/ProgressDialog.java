/*
 * Created on 2004-06-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.uengine.processdesigner;

import org.uengine.kernel.UEngineException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author Jinyoung Jang
 */
public class ProgressDialog extends JDialog{
	
	String successMsg;
	
	public ProgressDialog(String msg, JFrame owner){
		super(owner, msg, false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("East", new DesignerLabel(DesignerLabel.PROGRESS));//
		getContentPane().add("Center", new JLabel(msg, JLabel.CENTER));
		pack();
		Dimension size = getSize();
		
		setSize(size.width>250 ? size.width:250, size.height>100 ? size.height:100);	
		setLocationRelativeTo(owner);			
	}
	public ProgressDialog(String msg){
		this(msg, (String)null);
	}
	
	public ProgressDialog(String msg, String successMsg){
		this(msg, ProcessDesigner.getInstance());
	}
	
	public void show(){
		super.show();
		final ProgressDialog finalThis = this;

		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				try{
					finalThis.run();
					success();
				}catch(UEngineException e){
					fail(e);
				}catch(Exception e){
					e.printStackTrace();
					fail(new UEngineException("Exception: " + e.getMessage(), e));
				}
				
			}
		});
		
//		new Thread(){
//			public void run(){
//				try{
//					finalThis.run();
//					success();
//				}catch(UEngineException e){
//					fail(e);
//				}catch(Exception e){
//					e.printStackTrace();
//					fail(new UEngineException("Exception: " + e.getMessage(), e));
//				}
//			}
//		}.start();
	}
	
	public void run() throws Exception{}
	
	public void fail(UEngineException ue){
		failCause = ue;
		
		if(ue.getCause()!=null) ue.getCause().printStackTrace();
		//ue.printStackTrace();
		
		getContentPane().removeAll();
		getContentPane().add("East", new DesignerLabel(DesignerLabel.FAIL));
		getContentPane().add("Center", new JScrollPane(new JTextArea(ue.getMessage())));
		
		JButton closeBtn = new JButton("OK");
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		getContentPane().add("South", closeBtn);
		validate();
	}
	
	public void success(){
		getContentPane().removeAll();
		getContentPane().add("East", new DesignerLabel(DesignerLabel.SUCCESS));
		getContentPane().add("Center", new JLabel(successMsg != null ? successMsg : "Successfully done.", JLabel.CENTER));
		
		JButton closeBtn = new JButton("OK");
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		getContentPane().add("South", closeBtn);
		validate();
	}
	
	UEngineException failCause;
		public UEngineException getFailCause() {
			return failCause;
		}
	
}
