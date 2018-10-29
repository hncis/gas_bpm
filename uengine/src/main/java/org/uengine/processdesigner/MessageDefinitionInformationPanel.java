package org.uengine.processdesigner;

import org.uengine.processdesigner.inputters.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.MessageDefinition;
import org.uengine.kernel.ProcessDefinition;

import org.metaworks.*;
import org.metaworks.inputter.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jinyoung Jang
 */

public class MessageDefinitionInformationPanel extends AbstractInformationPanel{
	
	public MessageDefinitionInformationPanel(ProcessDefinition proc){
		super(proc, GlobalContext.getLocalizedMessage("pd.messagedefinitions.label"), MessageDefinition.class);

		java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/messagedefinitions.gif");
		setBorder(new IconTitledBorder(getBorder(), GlobalContext.getLocalizedMessage("pd.messagedefinitions.label"), new ImageIcon(imgURL)));

		Type table = getApplication().getType();
		FieldDescriptor fd;

		try{
			fd = table.getFieldDescriptor("Parameters");
			ProcessVariableArrayInput pvai = new ProcessVariableArrayInput();
			pvai.setProcessDefinition(proc);
			
			fd.setInputter(pvai); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected Object[] getValues() {
		return getProcessDefinition().getMessageDefinitions();
	}
	
	protected void applyValues(Object[] objs) {
		MessageDefinition[] mds = new MessageDefinition[objs.length];
		for(int i=0; i<objs.length; i++)
			mds[i] = (MessageDefinition)objs[i];
					
		getProcessDefinition().setMessageDefinitions(mds);	
	}
	
	protected String getLabel(Object value){
		return ((MessageDefinition)value).getName();
	}
	
}