package org.uengine.kernel.designer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.shaper.ClassicButtonShaper;
import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.processdesigner.ActivityLabel;
import org.uengine.processdesigner.ArrowReceiver;
import org.uengine.processdesigner.KeyListenerTransferrer;
import org.uengine.processdesigner.MouseListenerTransferrer;

/**
 * @author Jinyoung Jang
 */

public class DefaultActivityDesigner extends AbstractActivityDesigner implements ArrowReceiver{

	JCommandButton nameButton;
	//JPanel activityPanel;

	public void setActivity(Activity value){
		super.setActivity(value);
		if(value.getDescription()!=null)
			setToolTipText(value.getDescription().getText());
					
		try{
			URL btnIconResourceUrl = getClass().getClassLoader().getResource(
					ActivityLabel.getPNGIconPath(value.getClass()));
			
			ResizableIcon btnSVGIcon = null;
			
			if(btnIconResourceUrl != null) {
				btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(24, 24));
			}
				
			if(btnSVGIcon == null){	//if there's no SVG icon for requested activity type, try to use the GIF image icon instead.
				btnIconResourceUrl = getClass().getClassLoader().getResource(
						ActivityLabel.getGIFIconPath(value.getClass()));
				if(btnIconResourceUrl!=null)
					btnSVGIcon = ImageWrapperResizableIcon.getIcon(btnIconResourceUrl, new Dimension(24, 24));
			}
			

			if(btnSVGIcon==null){
				btnIconResourceUrl = getClass().getClassLoader().getResource(
						ActivityLabel.getPNGIconPath(DefaultActivity.class));

				btnSVGIcon = ImageWrapperResizableIcon.getIcon(
						btnIconResourceUrl, new Dimension(24, 24));				
			}
			
//			System.out.println("btnIconResourceUrl:"+btnIconResourceUrl);

			if(btnSVGIcon != null) {
				nameButton = new JCommandButton(value.getName().getText(), btnSVGIcon);
				nameButton.putClientProperty(
						SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.FALSE);
				nameButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
				//nameButton.putClientProperty(SubstanceLookAndFeel.GRADIENT_PAINTER_PROPERTY,
				//		  new org.jvnet.substance.painter.SpecularWaveGradientPainter());
//				nameButton.setState(org.jvnet.flamingo.common.ElementState.CUSTOM, true); 
				//nameButton.setSize()

//					nameButton.putClientProperty(
//							SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY, 
//					  		new ClassicButtonShaper()); 
//					nameButton.putClientProperty(SubstanceLookAndFeel.BUTTON_NO_MIN_SIZE_PROPERTY, 
//							  Boolean.TRUE);			
				
				nameButton.setHorizontalAlignment(AbstractButton.RIGHT);
//				nameButton.setHorizontalTextPosition(AbstractButton.RIGHT);
				
				add("Center", nameButton);
				add("North", dotDotDot);
				dotDotDot.setVisible(collapsed);
				
				nameButton.addKeyListener(new KeyListenerTransferrer(this));
				nameButton.addMouseListener(new MouseListenerTransferrer(this));
			}
			

		}catch(Exception e){
		}
	}

	ComplexActivityDesigner activityParent;
		public ComplexActivityDesigner getActivityParent() {
			return activityParent;
		}
		public void setActivityParent(ComplexActivityDesigner value) {
			activityParent = value;
		}

	public DefaultActivityDesigner(){
		super();
		this.setLayout(new BorderLayout());
		//activityPanel = new JPanel();
		//activityPanel.setLayout(new BorderLayout());
		
		//this.add(activityPanel);
	}
	
	public void setText(String msg){
		if(nameButton != null){
			nameButton.setText(msg);
			nameButton.revalidate();
			nameButton.repaint();
		}
	}

	public void setBackground(Color color){
		//transfer the bg color to the button
		
		if(nameButton!=null)
			nameButton.setBackground(color);
		
		super.setBackground(color);
	}
	public void setCursor(Cursor cur){
		//transfer the cursor style to the button
		
		if(nameButton!=null)
			nameButton.setCursor(cur);
		
		super.setCursor(cur);
	}

	public void setStatus(String status) {
		Activity activitiy = getActivity();
		if(activity!=null && !activity.isDynamicChangeAllowed()){
			nameButton.setEnabled(false);
			return;
		}
		
		if(!status.equals(Activity.STATUS_READY)){
			nameButton.setEnabled(false);
		}
	}

	public JCommandButton getNameButton() {
		return nameButton;
	}
	
	protected Component getSymbolicComponent() {		
		return getNameButton();
	}
	
	public int getArrowReceivePointIn() {
		return getHeight()/2;
	}
	
	public int getArrowReceivePointOut() {
		return getHeight()/2;
	}
	public boolean receiveArrow() {
		return true;
	}
	@Override
	protected void toggle() {
		nameButton.setVisible(!collapsed);
		dotDotDot.setVisible(collapsed);
		
		revalidate();
	}
	
	JLabel dotDotDot = new JLabel("...");

}

