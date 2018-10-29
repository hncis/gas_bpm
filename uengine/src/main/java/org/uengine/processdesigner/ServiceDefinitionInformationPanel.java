package org.uengine.processdesigner;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ServiceDefinition;

import org.metaworks.*;
import org.metaworks.inputter.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jinyoung Jang
 */

public class ServiceDefinitionInformationPanel extends AbstractInformationPanel{
	
	public ServiceDefinitionInformationPanel(ProcessDefinition proc){
		super(proc, GlobalContext.getLocalizedMessage("pd.servicedefinitions.label"), ServiceDefinition.class);

		java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/servicedefinitions.gif");
		setBorder(new IconTitledBorder(getBorder(), GlobalContext.getLocalizedMessage("pd.servicedefinitions.label"), new ImageIcon(imgURL)));

		Type table = getApplication().getType();
		FieldDescriptor fd;

		fd = table.getFieldDescriptor("Name");
		Inputter nameInputter = fd.getInputter(); 
		
		fd = table.getFieldDescriptor("WsdlLocation");
		fd.setInputter(new PickerInput(){
			public Picker getNewPicker(){
				return new UnifiedResourcePicker();
			}
		});
		Inputter wsdlLocationInputter = fd.getInputter(); 
			
		fd = table.getFieldDescriptor("StubPackage");
		fd.setInputter(new StubGeneratorInput(nameInputter, wsdlLocationInputter)); 
		
	}

	protected Object[] getValues() {
		return getProcessDefinition().getServiceDefinitions();
	}
	
	protected void applyValues(Object[] objs) {
		ServiceDefinition[] sds = new ServiceDefinition[objs.length];
		for(int i=0; i<objs.length; i++)
			sds[i] = (ServiceDefinition)objs[i];
					
		getProcessDefinition().setServiceDefinitions(sds);	
	}
	
	protected String getLabel(Object value){
		return ((ServiceDefinition)value).getName();
	}
	
	class StubGeneratorInput extends TextInput{
		
//		String stubClsPkg;
		Inputter wsdlLocationInputter;

//		String stubClsPkg;
		Inputter nameInputter;
		Component tf;		

		public StubGeneratorInput(Inputter nameInputter, Inputter wsdlLocationInputter){
			super();
			this.wsdlLocationInputter = wsdlLocationInputter;
			this.nameInputter = nameInputter; 
		}
	
/*		public Object getValue(){
			return stubClsPkg;
		}
		
		public void setValue(Object value){
			stubClsPkg = (String)value;
			((JButton)getValueComponent()).setText(stubClsPkg + "(click to regenerate)");			
		}
*/		
		public Component getNewComponent(){
			tf = super.getNewComponent();
			
			JPanel panel = new JPanel(new BorderLayout());
			JButton btn = new JButton("Generate");
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					try{												
						ServiceDefinition tempSvcDef = (ServiceDefinition)((ObjectInstance)((DefaultApplication)getApplication()).getSelectedRecord()).getObject();
						tempSvcDef.generateStub();
						setValue(tempSvcDef.getStubPackage());					
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});			
			
			panel.add("Center", tf);
			panel.add("East", btn);
			
			return panel;
		}
		
		public Component getValueComponent(){
			return tf;
		}
	}

}