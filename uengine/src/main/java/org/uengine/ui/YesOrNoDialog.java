/*
 * Created on 2005. 4. 28.
 */
package org.uengine.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jinyoung Jang
 */
public class YesOrNoDialog extends JDialog{
	
	boolean answer = false;
	public static final int CANCEL = 0;
	public static final int YES = 1;
	public static final int NO = 2;
	
	
	public YesOrNoDialog(JFrame owner, String msg){
		super(owner, true);
		
		setTitle(msg);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", new JLabel(msg, JLabel.CENTER));
		
		JPanel decision = new JPanel(new FlowLayout());
		
		JButton yesBtn = new JButton("Yes");
		yesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer = true;
				result = YES;
				dispose();
			}
		});

		JButton noBtn = new JButton("No");
		noBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer = false;
				result = NO;
				dispose();
			}
		});
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer = false;
				result = CANCEL;
				dispose();
			}
		});
		
		
		decision.add(yesBtn);
		decision.add(noBtn);
		decision.add(cancelBtn);
		
		getContentPane().add("South", decision);
		setSize(250,110);
		setLocationRelativeTo(owner);
	}

	public boolean getAnswer() {
		return answer;
	}
	
	int result = CANCEL;
	public int getResult(){
		return result;
	}
	
	

}
