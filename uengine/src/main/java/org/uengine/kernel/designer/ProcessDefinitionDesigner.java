package org.uengine.kernel.designer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.metaworks.component.MetaWorksComponentCenter;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ActivityDesignerListener;
import org.uengine.processdesigner.ActivityFilterInformationPanel;
import org.uengine.processdesigner.ArrowLabel;
import org.uengine.processdesigner.ArrowLinkingInfo;
import org.uengine.processdesigner.ArrowReceiver;
import org.uengine.processdesigner.ArrowTargetSource;
import org.uengine.processdesigner.BaseLineFlowLayout;
import org.uengine.processdesigner.DesignerLabel;
import org.uengine.processdesigner.MessageDefinitionInformationPanel;
import org.uengine.processdesigner.PlaceHolder;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.ProcessVariableInformationPanel;
import org.uengine.processdesigner.ProxyPanel;
import org.uengine.processdesigner.RoleInformationPanel;
import org.uengine.processdesigner.ServiceDefinitionInformationPanel;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */

//INSTRUCTION:
// 1. when you want to add an InformationPanel find word "//Must-implements for InformationPanel" to find where you have to add codes caused by addition 

public class ProcessDefinitionDesigner extends ScopeActivityDesigner{

	//	Must-implements for InformationPanel
	protected ProcessVariableInformationPanel processVariableInformationPanel;
	protected RoleInformationPanel roleInformationPanel;
	protected ServiceDefinitionInformationPanel serviceDefinitionInformationPanel;
	protected MessageDefinitionInformationPanel messageDefinitionInformationPanel;
	protected ActivityFilterInformationPanel activityFilterInformationPanel;
	protected JPanel informationPanel;
	protected JPanel downPanel;
	protected JPanel trashBoxPanel;
	protected JComboBox localeList;
	protected JLabel localeLabel;
	
	ResizableIcon trashSVGIcon_small, trashSVGIcon_large;
	
	JLabel procTitleLabel;

	
	public ProcessDefinitionDesigner(){
		super();
//		setBackground(Color.WHITE);
	}
	
	protected void initialize(){

		setLayout(new BorderLayout(0, 0));
		JPanel mainPanel = new ProxyPanel(new BorderLayout(0, 0));	

		URL trashIconResourceUrl = getClass().getClassLoader().getResource("org/uengine/processdesigner/images/svg/user-trash.svg");
		if(trashIconResourceUrl != null) {
			trashSVGIcon_small = ImageWrapperResizableIcon.getIcon(
					trashIconResourceUrl, new Dimension(60, 60));
			trashSVGIcon_large = ImageWrapperResizableIcon.getIcon(
					trashIconResourceUrl, new Dimension(80, 80));
		}
		
		trashBoxPanel = new ProxyPanel(new BorderLayout());
		trashBoxPanel.add("East", new PlaceHolder(new Dimension(60,60), false){
	
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				if(selected){
					if(trashSVGIcon_large != null) {
						trashSVGIcon_large.paintIcon(this, g2, 20, 0);
					}
				}else{
					if(trashSVGIcon_small != null) {
						trashSVGIcon_small.paintIcon(this, g2, 0, 0);
					}
				}
			}

			@Override
			public void onDropped() {
				super.onDropped();
				
				ActivityDesignerListener.deleteSelectedActivity();
			}
		});
		
		mainPanel.add("South", trashBoxPanel);

		//add(new Separator());
		
		//JPanel upPanel = new ProxyPanel(new BorderLayout(0, 0));
		final JPanel processPanel = new ProxyPanel();	//(new FlowLayout(FlowLayout.CENTER, 0, 0));
		if(isVertical) {
			processPanel.setLayout(new BoxLayout(processPanel, BoxLayout.Y_AXIS));
		} else {
			processPanel.setLayout(new BaseLineFlowLayout(FlowLayout.CENTER, 0, 0));
		}

		processPanel.add(new DesignerLabel(DesignerLabel.START));

		centerPanel = new ProxyPanel();
		if (isVertical) {
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		} else {
			BaseLineFlowLayout fl = new BaseLineFlowLayout(FlowLayout.CENTER, 0, 0);
			centerPanel.setLayout(fl);
		}
		processPanel.add(centerPanel);
		processPanel.add(new ArrowLabel() {
			public void onDropped() {
				Vector selectedComps = ActivityDesignerListener
						.getSelectedComponents();
				if (selectedComps != null) {
					insertActivityDesigners(selectedComps, -1);
				}

				ActivityDesignerListener.bDragging = false;
				setSelected(false);
			}
		});
		
		
		class EndLabel extends DesignerLabel implements ArrowReceiver{

			public EndLabel(){
				super(DesignerLabel.END);
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
		}
		
		processPanel.add(new EndLabel());
		// upPanel.add("Center", processPanel);
		
		JPanel flowPanel = new ProxyPanel();
		flowPanel.add(processPanel);
		mainPanel.add("Center", flowPanel);
		

		
		JPanel spacer = new ProxyPanel(new BorderLayout());
		spacer.setPreferredSize(new Dimension(100,50));
		procTitleLabel = new JLabel(){
			public void setText(String text){
				super.setText("   " +text);
			}
		};
		
		final Font font = new Font(GlobalContext.getLocalizedMessage("pd.font"), 3, 20);		
		final JPanel procTitleLabelPanel = new ProxyPanel(new BorderLayout());
		procTitleLabelPanel.add("Center", procTitleLabel);
		
		//------- locale selector ---------------------
		
		final JPanel eastProcTitleLabelPanel = new ProxyPanel(new FlowLayout());
		localeLabel = new JLabel();
		
		localeList = new JComboBox(GlobalContext.getPropertyStringArray("locale.lists", new String[]{"Default Locale", "ko", "en", "ja", "zh"}));
		if(getActivity()!=null)
			localeList.setSelectedItem(((ProcessDefinition)getActivity()).getCurrentLocale());
		else
			localeList.setSelectedItem(Locale.getDefault().getLanguage());
			
		localeList.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()!=ItemEvent.SELECTED) return;
				String locale = (String)localeList.getSelectedItem();
				if(localeList.getSelectedIndex()==0) locale = null;
				((ProcessDefinition)getActivity()).setCurrentLocale(locale);
				
				setActivity(getActivity());
				
				if(ProcessDesigner.getInstance()!=null){
					ProcessDesigner.getInstance().getProcessDefinitionDesigner().openDialog();
				}
				
				eastProcTitleLabelPanel.remove(localeList);
				eastProcTitleLabelPanel.add(localeLabel);
				localeLabel.setText(locale);
				eastProcTitleLabelPanel.revalidate();
			}
		});
		
		final ImageIcon moreArrowIcon = new ImageIcon(
				MetaWorksComponentCenter.class.getClassLoader().getResource("org/metaworks/images/more-arrow.gif")
		);

		localeLabel.setText("Default");
		localeLabel.setHorizontalTextPosition(JLabel.LEFT);
		localeLabel.setIcon(moreArrowIcon);
		localeLabel.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				eastProcTitleLabelPanel.remove(localeLabel);
				eastProcTitleLabelPanel.add(localeList);
				eastProcTitleLabelPanel.revalidate();
			}
			
		});

		final String hideNonHuman = "hide non human";
		final String showNonHuman = "show non human";
		final JLabel hideNonHumanLabel = new JLabel(hideNonHuman);
		hideNonHumanLabel.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1){
					collapsed = !collapsed;
					
					hideNonHumanLabel.setText(collapsed ? showNonHuman : hideNonHuman);
					
					toggle();
				}
			}
			
		});

		
		eastProcTitleLabelPanel.add(hideNonHumanLabel);
		eastProcTitleLabelPanel.add(localeLabel);

			
		//-----------------------------------------------
		
		
		
		spacer.add("East", eastProcTitleLabelPanel);
		
		procTitleLabel.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent arg0) {
				procTitleLabelPanel.removeAll();
				final JTextField labelEditor = new JTextField();
				labelEditor.setText(procTitleLabel.getText().trim());
				labelEditor.setFont(font);
				procTitleLabelPanel.add("Center", labelEditor);
				
				final ActionListener onEditDone = new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String description = labelEditor.getText();
						
						procTitleLabel.setText(description);
						procTitleLabelPanel.removeAll();
						procTitleLabelPanel.add("Center", procTitleLabel);
						procTitleLabelPanel.revalidate();

						getActivity().setName(description);
					}
				};
				
				labelEditor.addFocusListener(new FocusListener(){
					public void focusGained(FocusEvent arg0) {
					}
					public void focusLost(FocusEvent arg0) {
						onEditDone.actionPerformed(null);						
					}
				});
				
				labelEditor.addActionListener(onEditDone);
				
				procTitleLabelPanel.revalidate();
			}
			
		});
		
		procTitleLabel.setFont(font);
		procTitleLabel.setForeground(ProcessDesigner.ACT_LABEL_BGCOLOR);
		
		//mr.heo
		upperDefinition = new JButton() {
			public Icon getIcon(){
				java.net.URL img = 
					getClass().getClassLoader().getResource(
							"org/uengine/kernel/images/up_back.gif");
				return new ImageIcon(img);
			}
			
			protected void paintBorder(Graphics g) {
				//super.paintBorder(g);
				Border border = BorderFactory.createEtchedBorder(Color.WHITE, Color.WHITE);
				border.paintBorder(this, g, this.getX(), this.getY(), this.getWidth(),this.getHeight());
				
			}
			
		};
		upperDefinition.addMouseMotionListener(new MouseMotionListener(){

			public void mouseMoved(MouseEvent e) {
				JButton button = (JButton)e.getSource();
				
				String text = ProcessDesigner.getInstance().getParentDefinitionNameForToolTip();
				if(text=="")
					button.setToolTipText("���� �����Դϴ�");
				
				else
					button.setToolTipText(text + "�� �̵�");
			}

			public void mouseDragged(MouseEvent e) {
			}
			
			
		});
		
		
		upperDefinition.setContentAreaFilled(false);
		//ProcessDesigner.getInstance().getParentDefinitionNameForToolTip();
		//upperDefinition.setToolTipText("test");
		
		upperDefinition.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//if(!ProcessDesigner.getInstance().isRootDefinition())
					ProcessDesigner.getInstance().loadParentDefinition();
				
			}
			
		});
		upperDefinition.setVisible(buttonFlag);
		spacer.add("West", upperDefinition);
		spacer.add("Center", procTitleLabelPanel);
		
		
		mainPanel.add("North", spacer);
		
		/**
		 * layer
		 *//*
		
		JLayeredPane layeredPane = new JLayeredPane();
		
		
		layeredPane.setLayout(new BorderLayout(0,0));
		layeredPane.add(mainPanel, "Center", JLayeredPane.DEFAULT_LAYER);
		layeredPane.setPreferredSize(new Dimension(800,500));
		
				
       JPanel selectionMarker = new JPanel(){
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2d = (Graphics2D) g;
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Graphics2D g2 = (Graphics2D) g;
				BasicStroke stroke = new BasicStroke(2, 1, 1, 1, new float[]{4f,4f}, 3);
				g2.setStroke(stroke);
				g2.setColor(new Color(100, 100, 100));
	
	   			g2d.drawRoundRect(0, 0, 40, 20, 8, 8);    			
			}
       };
       
       selectionMarker.setOpaque(true);
       selectionMarker.setBounds(100, 100, 140, 140);
        
		layeredPane.add(selectionMarker, "Center", JLayeredPane.PALETTE_LAYER);
		layeredPane.setBounds(0,0,800,600);

*/		add("Center", mainPanel);
		
	}
	
	JButton upperDefinition;
	boolean buttonFlag=false;
	public void SetVisiableButton(boolean buttonFlag){
		this.buttonFlag =buttonFlag;
		upperDefinition.setVisible(buttonFlag);
		
	}
	
	public void setActivity(Activity act){
		super.setActivity(act);		
		
		refreshActivity();
		
		act.addProperyChangeListener(new PropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent evt) {
				if("processVariables".equals(evt.getPropertyName())){
					processVariableInformationPanel.refresh();
				}
			}
			
		});
			
	}

/*	public void setBackground(Color col){
		//disabled
	}*/
	
	public void validateActivity(){
		//disabled
	}	


	/* (non-Javadoc)
	 * @see org.uengine.kernel.designer.AbstractActivityDesigner#refreshActivity()
	 */
	public void refreshActivity() {
		if(AbstractActivityDesigner.isDebugger()) return;
		
//		if(processVariableInformationPanel==null) return;
		
//		processVariableInformationPanel.setProcessDefinition((ProcessDefinition)getActivity());
//		roleInformationPanel.setProcessDefinition((ProcessDefinition)getActivity());			
//		serviceDefinitionInformationPanel.setProcessDefinition((ProcessDefinition)getActivity());			
//		messageDefinitionInformationPanel.setProcessDefinition((ProcessDefinition)getActivity());
//		activityFilterInformationPanel.setProcessDefinition((ProcessDefinition)getActivity());

		if(downPanel==null){
			downPanel = new ProxyPanel(new BorderLayout());
			add("South", downPanel);
		}else
			downPanel.remove(informationPanel);

			//informationPanel = new ProxyPanel(new FlowLayout());
			informationPanel = new ProxyPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
			//review: TO-DO: move to setActivity() or forward the designer instead of procdef.
			//Must-implements for InformationPanel
			processVariableInformationPanel = new ProcessVariableInformationPanel((ProcessDefinition)getActivity());
			processVariableInformationPanel.setBackground(Color.WHITE);
			processVariableInformationPanel.setProcessDefinitionDesigner(this);

			roleInformationPanel = new RoleInformationPanel((ProcessDefinition)getActivity());
			roleInformationPanel.setBackground(Color.WHITE);
			roleInformationPanel.setProcessDefinitionDesigner(this);
			
			serviceDefinitionInformationPanel = new ServiceDefinitionInformationPanel((ProcessDefinition)getActivity());
			serviceDefinitionInformationPanel.setBackground(Color.WHITE);

			messageDefinitionInformationPanel = new MessageDefinitionInformationPanel((ProcessDefinition)getActivity());
			messageDefinitionInformationPanel.setBackground(Color.WHITE);

			activityFilterInformationPanel = new ActivityFilterInformationPanel((ProcessDefinition)getActivity());
			activityFilterInformationPanel.setBackground(Color.WHITE);
			
			//Must-implements for InformationPanel
			informationPanel.add(roleInformationPanel);
			informationPanel.add(processVariableInformationPanel);
			informationPanel.add(serviceDefinitionInformationPanel);
			informationPanel.add(messageDefinitionInformationPanel);
			informationPanel.add(activityFilterInformationPanel);
			
		downPanel.add("Center", informationPanel);
		downPanel.revalidate();
		
		procTitleLabel.setText(getActivity().getName().getText());	
		
		revalidate();


	}

	public JPanel getTrashBoxPanel() {
		return trashBoxPanel;
	}

	public void paint(Graphics g) {
		super.paint(g);
//		System.out.println("pddraw2");

/*		if(ActivityDesignerListener.getSelectedComponents()!=null && ActivityDesignerListener.getSelectedComponentImage()!=null){
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Graphics2D g2 = (Graphics2D) g;
			BasicStroke stroke = new BasicStroke(2, 1, 1, 1, new float[]{4f,4f}, 3);
			g2.setStroke(stroke);
			g2.setColor(new Color(100, 100, 100));

			//g2d.drawImage(ActivityDesignerListener.getSelectedComponentImage(), 0,0, Color.WHITE, null);
			
			Point relativeLocation = getLocationOnScreen();
			Point mouseLocation = ActivityDesignerListener.draggingMousePoint;
			Point relativeMouseLocation = new Point(mouseLocation.x - relativeLocation.x, mouseLocation.y - relativeLocation.y);
			
			g2d.drawRoundRect(relativeMouseLocation.x-40, relativeMouseLocation.y-20, relativeMouseLocation.x+40, relativeMouseLocation.y+20, 8, 8);
		}
*/
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke stroke = new BasicStroke(2, 1, 1, 1, new float[]{4f,4f}, 3);
		g2.setStroke(stroke);
		g2.setColor(new Color(100, 100, 100));
		
		ProcessDefinition processDefinition = (ProcessDefinition)getActivity();
		
		
		
		Vector selectedActivityComponents = ActivityDesignerListener.getSelectedComponents();
		for(int i=0; i<selectedActivityComponents.size(); i++){
			ActivityDesigner activityDesigner = (ActivityDesigner)selectedActivityComponents.get(i);
			if(activityDesigner instanceof ArrowTargetSource){
				ArrowTargetSource arrowTargetSource = ((ArrowTargetSource)activityDesigner);
				
				ProcessVariable variables[] = processDefinition.getProcessVariables();
				if(variables !=null)
				for(int j=0; j<variables.length; j++){
					java.util.List links = arrowTargetSource.getArrowLinkingInfo(variables[j]);

					if(links!=null)
					for(int k=0; k<links.size(); k++){
						ArrowLinkingInfo ali = (ArrowLinkingInfo)links.get(k);
						Point pointToLink = ali.getLinkPoint();
						
						Point relativeLocationOfActivityDesigner = UEngineUtil.getRelativeLocation(this, activityDesigner.getComponent());
						Point relativeLinkPointToActivityDesigner = new Point(relativeLocationOfActivityDesigner.x + pointToLink.x, relativeLocationOfActivityDesigner.y + pointToLink.y);
						Point relativeLinkPointFrom = UEngineUtil.getRelativeLocation(this, processVariableInformationPanel.getJLabel(variables[j]));
						
						g2.drawLine(relativeLinkPointFrom.x, relativeLinkPointFrom.y, relativeLinkPointToActivityDesigner.x, relativeLinkPointToActivityDesigner.y);
						
						if(ali.isTarget())
							g2.drawArc(relativeLinkPointToActivityDesigner.x, relativeLinkPointToActivityDesigner.y, 2, 2, 0, 0);
						if(ali.isSource())
							g2.drawArc(relativeLinkPointFrom.x, relativeLinkPointFrom.y, 2, 2, 0, 0);
					}
				}
				
				Role roles[] = processDefinition.getRoles();
				if(roles !=null)
				for(int j=0; j<roles.length; j++){
					java.util.List links = arrowTargetSource.getArrowLinkingInfo(roles[j]);

					if(links!=null)
					for(int k=0; k<links.size(); k++){
						ArrowLinkingInfo ali = (ArrowLinkingInfo)links.get(k);
						Point pointToLink = ali.getLinkPoint();
						
						Point relativeLocationOfActivityDesigner = UEngineUtil.getRelativeLocation(this, activityDesigner.getComponent());
						Point relativeLinkPointToActivityDesigner = new Point(relativeLocationOfActivityDesigner.x + pointToLink.x, relativeLocationOfActivityDesigner.y + pointToLink.y);
						Point relativeLinkPointFrom = UEngineUtil.getRelativeLocation(this, roleInformationPanel.getJLabel(roles[j]));
						
						g2.drawLine(relativeLinkPointFrom.x, relativeLinkPointFrom.y, relativeLinkPointToActivityDesigner.x, relativeLinkPointToActivityDesigner.y);
						
						if(ali.isTarget())
							g2.drawArc(relativeLinkPointToActivityDesigner.x, relativeLinkPointToActivityDesigner.y, 2, 2, 0, 0);
						if(ali.isSource())
							g2.drawArc(relativeLinkPointFrom.x, relativeLinkPointFrom.y, 2, 2, 0, 0);
					}
				}
			}
		}
		
		//g2.dispose();
		
	}

	@Override
	public void revalidate() {
		super.revalidate();
		repaint();
	}

	public JLabel getLocaleLabel() {
		return localeLabel;
	}

}

