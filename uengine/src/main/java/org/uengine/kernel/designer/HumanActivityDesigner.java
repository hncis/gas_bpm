/*
 * Created on 2004. 12. 18.
 */
package org.uengine.kernel.designer;

import java.awt.*;

import javax.swing.*;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleResolutionContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.util.UEngineUtil;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.IOException;

/**
 * @author Jinyoung Jang
 * @author Sungsoo Park
 */
public class HumanActivityDesigner extends DefaultActivityDesigner{
	//JLabel roleLabel;
	String roleLabel;
	
	public HumanActivityDesigner(){
		super();
		
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if(roleLabel!=null){
//			Dimension existingSize = getSize();
//			existingSize.height += 13;
//			setSize(existingSize);
			
			Font font = new Font(GlobalContext.getLocalizedMessage("pd.font"), 2, 10);
	
			java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/role.gif");
			g.drawImage(new ImageIcon(imgURL).getImage(), 1, getSize().height - 13,13,13, this);
			
			g.setFont(font);
			g.drawString(roleLabel, 15, getSize().height - 2);
		}
	}
	
	public void refreshActivity() {
		super.refreshActivity();
		
		HumanActivity activity = (HumanActivity)getActivity();
		Role role = activity.getRole();
		
		if(role!=null){
			roleLabel=role.toString();
		}else
			roleLabel=null;
	}
	
	/**
	 * Generate role name
	 */
	public String getNewRoleName(Role[] roles) {
		int max = 0;
		for (int i=0; i<roles.length; i++) {
			System.out.println(roles[i].getName());
			if ( roles[i].getName().startsWith("New") ) {
				int ver = Integer.parseInt(roles[i].getName().substring(roles[i].getName().length()-1));
				if ( ver > max ) max = ver;
			}
		}
		return "New Role " + (++max);
	}
	
	/**
	 * Drop event from OrgTree source
	 * @see org.uengine.processdesigner.role.OrgTree
	 * @see org.uengine.processdesigner.role.OrgTreeNode
	 */
	public void drop(DropTargetDropEvent e) {
		super.drop(e);
		try {
			DataFlavor listFlavor = DataFlavor.javaFileListFlavor;
			Transferable tr = e.getTransferable();
			
//			System.out.println(tr);
			java.util.List list = (java.util.List)tr.getTransferData(listFlavor);
			
			Object objectGot = list.get(0);
			
			if(objectGot instanceof RoleResolutionContext){
				RoleResolutionContext context = (RoleResolutionContext)objectGot;
				System.out.println(context.toString());
				
				HumanActivity humanActivity = (HumanActivity)getActivity();
				Role role;
				if(humanActivity.getRole()!=null){
					role = humanActivity.getRole();
				} else {
					ProcessDefinition editingDefinition = humanActivity.getProcessDefinition();
					role = new Role();
					role.setName(getNewRoleName(editingDefinition.getRoles()));
					editingDefinition.addRole(role);
					ProcessDesigner.getInstance().getProcessDefinitionDesigner().refreshActivity();
				}
				
				if(UEngineUtil.isNotEmpty(context.getDisplayName())){
					String roleDisplayName = role.getDisplayName().getText();
					roleDisplayName = roleDisplayName.split(":")[0];
					role.setDisplayName(roleDisplayName + ":" + context.getDisplayName());
				}
					
				role.setRoleResolutionContext(context);
				role.setAskWhenInit(false);
				
				humanActivity.setRole(role);
				
				refreshActivity();
				
				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);	
				e.dropComplete(true);

				
			}else if(objectGot instanceof Role){
				Role role = (Role)objectGot;
				
				HumanActivity humanActivity = (HumanActivity)getActivity();
				humanActivity.setRole(role);
				
				refreshActivity();
				
				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);	
				e.dropComplete(true);

			}
			
		} catch(UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
