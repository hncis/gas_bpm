/*
 * Created on 2004. 12. 18.
 */
package org.uengine.kernel.designer;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleParameterContext;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processdesigner.ActivityDesigner;
import org.uengine.processdesigner.ArrowLinkingInfo;
import org.uengine.processdesigner.ArrowTargetSource;
import org.uengine.processdesigner.LoadedDefinition;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.ProgressDialog;
import org.uengine.processdesigner.SaveDialogInfo;
import org.uengine.util.UEngineUtil;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Jinyoung Jang
 * @author Sungsoo Park
 */
public class SubProcessActivityDesigner extends DefaultActivityDesigner implements ArrowTargetSource{
	
	boolean collapsed = true;
	ProcessDefinition subProcDef;
	ProcessDefinitionDesigner subProcessDesignerComponent;

	public SubProcessActivityDesigner(){
		super();
		setBorder(new EmptyBorder(10,10,10,10));
		
/*		final JPopupMenu popup = new JPopupMenu();
		JMenuItem edit = new JMenuItem("Drill into");
		edit.setName("edit");
		
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SubProcessActivity spAct = (SubProcessActivity)getActivity();
				if(spAct.getDefinitionId()!=null){
					ProcessDesigner.getInstance().addDefinition(
							new LoadedDefinition(spAct.getDefinitionId(), ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity().getName().getText(), 0));
					ProcessDesigner.getInstance().loadDesignWithDefinitionId(spAct.getDefinitionId());
				}
				
			}
			
		});
		
		popup.add(edit);*/
		
		addCollapseMouseListener();
	}
	
	protected void addCollapseMouseListener(){
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
/*				if(e.getButton()==MouseEvent.BUTTON3){
					popup.show((SubProcessActivityDesigner)e.getComponent(), e.getX(), e.getY());
				}
*/				
				if(e.getY() > getHeight()-20){
					if(collapsed){
						unCollapse();
					}else{
						collapse();
					}
				}
			}
			
		});
	}
	
	protected void unCollapse(){
		SubProcessActivity subProcAct = (SubProcessActivity) getActivity();

//		if(subProcDef==null){
			if(subProcAct.getDefinitionId()!=null){
				InputStream is;
				try {
					is = ProcessDesigner.getInstance().getClientProxy().showProcessDefinitionWithDefinitionId(ProcessDefinition.splitDefinitionAndVersionId(subProcAct.getDefinitionId())[0]);
					subProcDef = (ProcessDefinition) GlobalContext.deserialize(is, ProcessDefinition.class);
				} catch (Exception e) {
	
					e.printStackTrace();
					add("Center", new JLabel("Couldn't read the sub-process definition: " + e.getMessage()));
					revalidate();
					collapsed = false;
	
					return;
				}
	
			}else{
			}
//		}
		
		if(subProcDef==null){
			subProcDef = new ProcessDefinition();
			subProcDef.setName(subProcAct.getName());
			
			ProcessDefinition mainProcDef = subProcAct.getProcessDefinition();
			
			Role[] subProcRoles = new Role[mainProcDef.getRoles().length];
			for(int i=0; i<mainProcDef.getRoles().length; i++){
				subProcRoles[i] = (Role) mainProcDef.getRoles()[i].clone();
			}
			subProcDef.setRoles(subProcRoles);
			/*
			ProcessVariable[] subProcVariables = new ProcessVariable[mainProcDef.getProcessVariables().length];
			for(int i=0; i<mainProcDef.getProcessVariables().length; i++){
				ProcessVariable mainProcVariable = mainProcDef.getProcessVariables()[i];
				subProcVariables[i] = (ProcessVariable) mainProcVariable.clone();
			}
			subProcDef.setProcessVariables(subProcVariables);
*/		}
		
		remove(getNameButton());
		revalidate();
		
		ProcessDefinitionDesigner subProcessDesigner = (ProcessDefinitionDesigner) subProcDef.createDesigner();
		
		subProcessDesignerComponent = (ProcessDefinitionDesigner) subProcessDesigner.getComponent();
		
		subProcessDesigner.getTrashBoxPanel().setVisible(false);
		subProcessDesigner.getLocaleLabel().setVisible(false);
		
		add("Center", subProcessDesignerComponent);
		
		revalidate();
		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();
		
		collapsed = false;
	}
	
	protected void collapse(){
		
		try{
			SubProcessActivity subProcessAct = (SubProcessActivity)getActivity();
			
			boolean isNewDefinition = (subProcessAct.getDefinitionId()==null && subProcDef.getBelongingDefinitionId()==null);
			
			if(isNewDefinition){
				if(ProcessDesigner.getInstance().saveDesignToServer(false, subProcDef)){
					if(subProcDef.getBelongingDefinitionId()!=null)
						subProcessAct.setDefinitionId("["+subProcDef.getAlias()+"]");
				}
			}else{
				ProcessDefinition def = subProcDef; 

				String definitionName = def.getName().getText();
				String alias = def.getAlias();
				int version=def.getVersion();

				SaveDialogInfo saveInfo = new SaveDialogInfo();
				saveInfo.setName(definitionName);
				saveInfo.setAlias(alias);
				saveInfo.setVersion(version+1);
				saveInfo.setAuthor(ProcessDesigner.getInstance().getRevisionInfo());

				ProcessDesigner.getInstance().saveDesignToServer(subProcDef, saveInfo);
			}
				
		}catch(Exception e){
			
		}
		
		
		removeAll();
		add("Center", getNameButton());
		subProcessDesignerComponent = null;
		
		collapsed = true;
		
		revalidate();
		ProcessDesigner.getInstance().getProcessDefinitionDesigner().revalidate();

	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(150, 150, 150));
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(150, 150, 150));
		Stroke stroke = new BasicStroke(1.5f);
		stroke = new BasicStroke(1.3f,0,0,4.0f,null,0.0f);
		g2d.setStroke(stroke);
		
		//outer border
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8,8);

		g2d.setColor(Color.BLACK);

		//collapse button
		GeneralPath path = new GeneralPath();		

		if(collapsed){
			path.moveTo(getWidth()/2, getHeight()-8);
			path.lineTo(getWidth()/2, getHeight());
		}
		
		path.moveTo(getWidth()/2-4, getHeight()-4);
		path.lineTo(getWidth()/2+4, getHeight()-4);
	
		g2d.draw(path);
		
		g2d.drawRoundRect(getWidth()/2-4, getHeight()-8, 8, 8, 2,2);

		
		g2d.dispose();

	}

	public java.util.List getArrowLinkingInfo(Object forWhat) {
		
		java.util.List arrowLinkList = new ArrayList();
		
		SubProcessActivity subProcessAct = (SubProcessActivity)getActivity();
		
		if(forWhat instanceof ProcessVariable && subProcessAct.getVariableBindings()!=null && subProcessAct.getVariableBindings().length > 0){
			ProcessVariable wannaBeLinked = (ProcessVariable)forWhat;
			
			ParameterContext[] variableBindings = subProcessAct.getVariableBindings();
			for(int i=0; i<variableBindings.length; i++){
				if(wannaBeLinked==variableBindings[i].getVariable()){
				
					ArrowLinkingInfo arrowLinkingInfo = new ArrowLinkingInfo();
					
					Point linkPoint;
					
					if(collapsed){
						linkPoint = new Point(i * getWidth()/variableBindings.length, getHeight());
					}else{
						ProcessVariable boundSubProcessVariable = subProcDef.getProcessVariable(variableBindings[i].getArgument().getText());
						JLabel labelForBoundSubProcessVariable = subProcessDesignerComponent.processVariableInformationPanel.getJLabel(boundSubProcessVariable);
						
						if(labelForBoundSubProcessVariable==null) continue;
						
						linkPoint = UEngineUtil.getRelativeLocation(this, labelForBoundSubProcessVariable);
					}
						
					
					arrowLinkingInfo.setLinkPoint(linkPoint);
					
					if(variableBindings[i].getDirection()!=null && variableBindings[i].getDirection().equals(ParameterContext.DIRECTION_IN))
						arrowLinkingInfo.setTarget(true);
					else
					if(variableBindings[i].getDirection()!=null && variableBindings[i].getDirection().equals(ParameterContext.DIRECTION_OUT)){
						arrowLinkingInfo.setSource(true);
					}else{
						arrowLinkingInfo.setTarget(true);
						arrowLinkingInfo.setSource(true);
					}

					arrowLinkList.add(arrowLinkingInfo);
				}
			}
			
		}

		
		if(forWhat instanceof Role && subProcessAct.getRoleBindings()!=null && subProcessAct.getRoleBindings().length > 0){
			Role wannaBeLinked = (Role)forWhat;
			
			RoleParameterContext[] roleBindings = subProcessAct.getRoleBindings();
			for(int i=0; i<roleBindings.length; i++){
				if(wannaBeLinked==roleBindings[i].getRole()){
				
					ArrowLinkingInfo arrowLinkingInfo = new ArrowLinkingInfo();
					
					Point linkPoint;
					
					if(collapsed){
						linkPoint = new Point(i * getWidth()/roleBindings.length, getHeight());
					}else{
						Role boundSubProcessRole = subProcDef.getRole(roleBindings[i].getArgument());
						JLabel labelForBoundSubProcessRole = subProcessDesignerComponent.roleInformationPanel.getJLabel(boundSubProcessRole);
						
						if(labelForBoundSubProcessRole==null) continue;
						
						linkPoint = UEngineUtil.getRelativeLocation(this, labelForBoundSubProcessRole);
					}
						
					
					arrowLinkingInfo.setLinkPoint(linkPoint);
					
					if(roleBindings[i].getDirection()!=null && roleBindings[i].getDirection().equals(ParameterContext.DIRECTION_IN))
						arrowLinkingInfo.setTarget(true);
					else
					if(roleBindings[i].getDirection()!=null && roleBindings[i].getDirection().equals(ParameterContext.DIRECTION_OUT)){
						arrowLinkingInfo.setSource(true);
					}else{
						arrowLinkingInfo.setTarget(true);
						arrowLinkingInfo.setSource(true);
					}

					arrowLinkList.add(arrowLinkingInfo);
				}
			}
			
		}
		
		if(arrowLinkList!=null && arrowLinkList.size()>0)
			return arrowLinkList;

		return null;
	}
	
	

	
	
}
