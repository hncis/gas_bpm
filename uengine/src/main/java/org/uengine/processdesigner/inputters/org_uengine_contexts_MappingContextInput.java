package org.uengine.processdesigner.inputters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.metaworks.FieldDescriptor;
import org.metaworks.ObjectType;
import org.metaworks.inputter.InputterAdapter;
import org.uengine.contexts.ComplexType;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.contexts.MappingContext;
import org.uengine.contexts.OfficeDocumentDefinition;
import org.uengine.contexts.OfficeDocumentInstance;
import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.MappingElement;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.mapper.Transformer;
import org.uengine.processdesigner.mapper.TransformerArgument;
import org.uengine.processdesigner.mapper.TransformerDesigner;
import org.uengine.processdesigner.mapper.TransformerMapping;
import org.uengine.util.ActivityForLoop;
import org.uengine.util.RecursiveLoop;

import com.webdeninteractive.xbotts.Mapping.compiler.LinkedPair;
import com.webdeninteractive.xbotts.Mapping.compiler.Record;
import com.webdeninteractive.xbotts.Mapping.maptool.MapToolPanel;
import com.webdeninteractive.xbotts.Mapping.maptool.SchemaTreeModel;

public class org_uengine_contexts_MappingContextInput extends InputterAdapter{

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	transient MapToolPanel mapToolPanel;
	transient HashMap sourceNodes = new HashMap();
	transient HashMap targetNodes = new HashMap();
	
	ActionListener refreshAction = new ActionListener(){

		public void actionPerformed(ActionEvent e) {
			refresh();
		}
		
	};

	
	public Component getNewComponent() {

		SchemaTreeModel model = createProcessDefinitionSchemaTreeModel(true);
		SchemaTreeModel model2 = createProcessDefinitionSchemaTreeModel(false);
		
		mapToolPanel = new MapToolPanel(model, model2);
		
		
		installRefresher(mapToolPanel);
		
		JButton refresher = new JButton("refresh");
		refresher.addActionListener(refreshAction);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add("North", mapToolPanel);
		panel.add("South", refresher);
		
		return panel;

	}
	
	private void installRefresher(MapToolPanel mapToolPanel){
		JMenuItem mi = new JMenuItem("Refresh");
		mi.addActionListener(refreshAction);
		JMenuItem mi2 = new JMenuItem("Refresh");
		mi2.addActionListener(refreshAction);
		JMenuItem mi3 = new JMenuItem("Refresh");
		mi3.addActionListener(refreshAction);
		
		mapToolPanel.getSrcTree().getMenu().add(mi);
		mapToolPanel.getTrgTree().getMenu().add(mi2);
		mapToolPanel.getLinkPanel().getPopupMenu().add(mi3);
	}
	
	public void refresh(){
		Object oldValue = getValue();
		
		SchemaTreeModel model = createProcessDefinitionSchemaTreeModel(true);
		SchemaTreeModel model2 = createProcessDefinitionSchemaTreeModel(false);
		
		mapToolPanel.setSchemaTreeModels(model, model2);
		installRefresher(mapToolPanel);
		mapToolPanel.revalidate();
		
		setValue(oldValue);
	}
	
	protected Record createRecord(String name, String referenceName, boolean isSource){
		Record node = new Record(name);
		if(referenceName==null){
			referenceName = name;
		}
		
		node.setReferenceName(referenceName);
		
		if(isSource)
			sourceNodes.put(referenceName, node);
		else
			targetNodes.put(referenceName, node);
		
		return node;
	}
	
	protected SchemaTreeModel createProcessDefinitionSchemaTreeModel(boolean isSource){
		Record variablesNode = new Record("Variables");
		Record rolesNode = new Record("Roles");
		
		ProcessDefinition def = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
		ProcessVariable[] pvds = (def != null ? def.getProcessVariables() : new ProcessVariable[]{});
		Role[] roles = (def != null ? def.getRoles() : new Role[]{});
		
		//final ArrayList variables = new ArrayList();
		
		if(pvds!=null)
		for(int i=0; i<pvds.length; i++){
			//variables.add(pvds[i]);
			
			Record variableNode = createRecord(pvds[i].getDisplayName().getText(), pvds[i].getName(), isSource);
			
			variablesNode.add(variableNode);
			
			createVariableNode(pvds[i], variableNode, isSource);
		}
		
		for(int i=0; i<roles.length; i++){
			//RolePointingProcessVariable rolePV = new RolePointingProcessVariable();
			//rolePV.setRole(roles[i]);
			//variables.add(rolePV);
			
			rolesNode.add(createRecord(roles[i].getName(), "[roles]."+roles[i].getName(), isSource));
		}
		
		String[] instancePropertyNames = new String[]{
			"instanceId",
			"name",
			"locale",
			"status",
			"info",
			"dueDate",
			"mainProcessInstanceId",
			"mainActivityTracingTag",
			"rootProcessInstanceId",	
			"dummy1",	
			"dummy2",	
			"dummy3",	
			"dummy4",	
			"dummy5",	

		};
		
		//build up bean property tree
/*		HashMap beanTree = new HashMap();
		for(int i=0; i < beanNames.length; i++){
			String beanName = beanNames[i];
			
			String[] parts = beanName.replace('.','@').split("@");
			
			HashMap tree = beanTree;
			for(int depth = 0; depth < parts.length; depth++){
				String part = parts[depth];
				if(!tree.containsKey(part))
					tree.put(part, new HashMap());
				
				tree = (HashMap) tree.get(part);
			}
		}
*/		
		Record instanceNode = new Record("Instance");
		
		for(int i=0; i < instancePropertyNames.length; i++){
			instanceNode.add(createRecord(instancePropertyNames[i], "[instance]." + instancePropertyNames[i], isSource));
		}
		
		Record activitiesNode = new Record("Activities");
		createActivityNode(def, activitiesNode, isSource);
		
		Record srcNode = new Record("source");
		srcNode.add(variablesNode);
		srcNode.add(rolesNode);
		srcNode.add(instanceNode);
		srcNode.add(activitiesNode);
		
		SchemaTreeModel model = new SchemaTreeModel(srcNode);

		return model;
	}
	
	protected void createActivityNode(ProcessDefinition def,final Record activitiesNode,final boolean isSource){
		
		final String[] activityPropertyNames = new String[]{
				"startedTime",
				"endTime",
				"dueDate",
				"duration",
				"businessStatus",
				"status",
			};
		
		ActivityForLoop afl = new ActivityForLoop(){
			public void logic(Activity activity) {
				if(activity instanceof HumanActivity){
					Record activityNode = createRecord(activity.getName().getText(), "[activities]."+activity.getTracingTag(), isSource);
					
					activitiesNode.add(activityNode);
					
					for (int i = 0; i < activityPropertyNames.length; i++) {
						activityNode.add(createRecord(activityPropertyNames[i],"[activities]."+activity.getTracingTag() + "." + activityPropertyNames[i], isSource));
					}
				}
			}
		};
		afl.run(def);
	}
	
	
	protected void createVariableNode(ProcessVariable variable, Record variableNode, boolean isSource){
		
		if(variable.getType() == ComplexType.class){
			ComplexType metaValue = (ComplexType)(variable.getDefaultValue());
			try {

				Class testcls = metaValue.getTypeClass();
				
				ObjectType objType = new ObjectType(testcls);
				FieldDescriptor[] fieldDescriptors = objType.getFieldDescriptors();
				for(int j=0; j<fieldDescriptors.length; j++){
					FieldDescriptor fd = fieldDescriptors[j];
					variableNode.add(createRecord(fd.getName(),variable.getName() + "." + fd.getName(), isSource));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(variable.getType() == HtmlFormContext.class){
			HtmlFormContext metaValue = (HtmlFormContext)(variable.getDefaultValue());
			
			if(metaValue.getFormDefId()==null)
				variableNode.add(createRecord("No form template is set", null, isSource));;
			
			String formDefId = ProcessDefinition.splitDefinitionAndVersionId(metaValue.getFormDefId())[0];
			try {
				
				InputStream is = ProcessDesigner.getClientProxy().showFormDefinitionWithDefinitionId(formDefId);
				
				ArrayList formFieldList = (ArrayList) GlobalContext.deserialize(is, ArrayList.class);
				for(int k=0; k<formFieldList.size(); k++){
					Object element = formFieldList.get(k);
					if(element instanceof String){//that means the field parts are started.
						String fieldName = (String)element;
						variableNode.add(createRecord(fieldName, variable.getName() + "." + fieldName, isSource));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		if(variable.getType() == OfficeDocumentInstance.class){
			OfficeDocumentInstance metaValue = (OfficeDocumentInstance)(variable.getDefaultValue());
			
			if(metaValue.getDocumentDefId()==null)
				variableNode.add(createRecord("No form template is set", null, isSource));;
			
			String formDefId = ProcessDefinition.splitDefinitionAndVersionId(metaValue.getDocumentDefId())[0];
			try {
				
				InputStream is = ProcessDesigner.getClientProxy().showObjectDefinitionWithDefinitionId(formDefId);
				
				OfficeDocumentDefinition odd = (OfficeDocumentDefinition) GlobalContext.deserialize(is, OfficeDocumentDefinition.class);
				List officeDocFieldList = odd.getFieldList();
				for(int k=0; k<officeDocFieldList.size(); k++){
					Object element = officeDocFieldList.get(k);
					String fieldName = null;
					if(element instanceof String){//that means the field parts are started.
						fieldName = (String)element;
					}else if(element instanceof FieldDescriptor){
						fieldName = ((FieldDescriptor)element).getName();
					}
					
					if(fieldName!=null)
						variableNode.add(createRecord(fieldName, variable.getName() + "." + fieldName, isSource));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object getValue() {
		ArrayList mappingContexts = new ArrayList();
		
		 Iterator iter = mapToolPanel.getModel().getLinkedPairs();
		 while(iter.hasNext()){
			 LinkedPair linkedPair = (LinkedPair) iter.next();
			 ParameterContext paramContext = null;
			 
			 if(linkedPair.getSource() instanceof TransformerArgument && linkedPair.getTarget() instanceof Record){
				 TransformerArgument source = (TransformerArgument) linkedPair.getSource();
				 Record target = (Record) linkedPair.getTarget();
				 
				 paramContext = new MappingElement();

				 TextContext argumentText = TextContext.createInstance();
				 argumentText.setText(target.getReferenceName());
				 paramContext.setArgument(argumentText);
				 
				 ((MappingElement)paramContext).setKey(Record.KEY == target.getKeyType());
				 //paramContext.setVariable(ProcessVariable.forName(source.getReferenceName()));
				 
				 if(target.getExtendedProperties()!=null && target.getExtendedProperties().containsKey("type")){
					 paramContext.setType((Class)target.getExtendedProperties().get("type"));
				 }
				 
				 source.getTransformer().beforeSerialization();
				 
				 TransformerMapping transformerMapping = new TransformerMapping();
				 transformerMapping.setTransformer(source.getTransformer());
				 transformerMapping.setLinkedArgumentName(source.getName());
				 
				 paramContext.setTransformerMapping(transformerMapping);
				 
			 }else if (linkedPair.getSource() instanceof Record && linkedPair.getTarget() instanceof Record){
			 
				 
				 Record source = (Record) linkedPair.getSource();
				 Record target = (Record) linkedPair.getTarget();
				 
				 paramContext = new MappingElement();
				 TextContext argumentText = TextContext.createInstance();
				 argumentText.setText(target.getReferenceName());
				 paramContext.setArgument(argumentText);
				 paramContext.setVariable(ProcessVariable.forName(source.getReferenceName()));
				 ((MappingElement)paramContext).setKey(Record.KEY == target.getKeyType());
				 
				 if(target.getExtendedProperties()!=null && target.getExtendedProperties().containsKey("type")){
					 paramContext.setType((Class)target.getExtendedProperties().get("type"));
				 }
			 }
			 
			 if(paramContext !=null)
				 mappingContexts.add(paramContext);
		 }
		 
		 ParameterContext[] result = new ParameterContext[mappingContexts.size()];
		 mappingContexts.toArray(result);
		 
		 MappingContext mc = new MappingContext();
		 mc.setMappingElements(result);
		 
		 return mc;
	}

	public void setValue(Object arg0) {
		if(arg0==null) return;
		
		 MappingContext mc = (MappingContext)arg0;
		 ParameterContext[] mappingElements = mc.getMappingElements();
		
		 final HashMap existingTransformers = new HashMap();
		 
		 for(int i=0; i<mappingElements.length; i++){
			 MappingElement paramContext = (MappingElement) mappingElements[i];
			 
			 if(paramContext.getTransformerMapping()!=null){
				 TransformerMapping transformerMapping = paramContext.getTransformerMapping();
				 Transformer transformer = transformerMapping.getTransformer();
				 
				 /**
				  * ban to add same transformers multiplicitly
				  */
				 if(!existingTransformers.containsKey(transformer)){
					 //need to regenerate the instance of Designer since there's no graphical objects those are deserialized.
					 TransformerDesigner tfd = transformer.createDesigner(mapToolPanel.getLinkPanel());
					 
					 //mapToolPanel.getLinkPanel().add(tfd);
					 
					 existingTransformers.put(transformer, tfd);
					 //transformer.beforeSerialization();
				 }

				 Record target = (Record) sourceNodes.get(paramContext.getArgument().getText());
				 //the output argument of transformer must be the source which should be linked in the first order
				 TransformerArgument source = transformer.getTransferArgument(transformerMapping.getLinkedArgumentName());
				 
				 if(paramContext.isKey()){
				 	target.setKeyType(Record.KEY);
			 	 }
			 	 
				 try{
					 mapToolPanel.getModel().addLink(source, target);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
				 
				 new RecursiveLoop(){

					@Override
					public boolean logic(Object tree) {
						return false;
					}

					@Override
					public List getChildren(Object tree) {
						
						if(tree instanceof TransformerMapping){
							System.out.println();
						}
						
						// stops the recursive tracing when it meets the endpoints						
						if(!(tree instanceof Transformer)){
							return null;
						}
						
						Transformer transformer = (Transformer)tree;
						
						final ArrayList children = new ArrayList();

						if(transformer.getInputArguments()!=null)
						for(int i=0; i<transformer.getInputArguments().length; i++){
							Object argumentSource = transformer.getArgumentSourceMap().get(transformer.getInputArguments()[i]);
							TransformerArgument target = transformer.getTransferArgument(transformer.getInputArguments()[i]);
							
							if(argumentSource==null) continue;
							
							ArrayList argumentSources;
							if(argumentSource instanceof ArrayList){
								argumentSources = (ArrayList)argumentSource;
							}else{
								argumentSources = new ArrayList();
								argumentSources.add(argumentSource);
							}
							
							for(int j=0; j<argumentSources.size(); j++){
								argumentSource = argumentSources.get(j);
								
								if(argumentSource instanceof TransformerMapping){
									TransformerMapping priorTransformerMapping = (TransformerMapping) argumentSource;
									
									Transformer priorTransformer = priorTransformerMapping.getTransformer();
									argumentSource = priorTransformer;
									
									if(!existingTransformers.containsKey(priorTransformer)){
										TransformerDesigner tfd = priorTransformer.createDesigner(mapToolPanel.getLinkPanel());
										existingTransformers.put(priorTransformer, tfd);
									}
									
									TransformerArgument priorTransformerArgument = priorTransformer.getTransferArgument(priorTransformerMapping.getLinkedArgumentName());
									children.add(argumentSource);
	
	
									if(priorTransformerArgument==null)
										continue; //anyway never be occurred... means priorTransformerArgument must exist.
									
									 /**
									  * transformer argument is eager to be changed since the user interface occasionally re-rendered.
									  */
									 priorTransformerArgument = priorTransformer.getTransferArgument(priorTransformerArgument.getName());
	
									 //the output argument of transformer must be the source which should be linked in the first order
									 TransformerArgument source = priorTransformerArgument;
									 
									 
									 try{
										 mapToolPanel.getModel().addLink(source, target);
									 }catch(Exception e){
										 e.printStackTrace();
									 }
									 
									 
								}else{ // the case that the source is a node
									 Record source = (Record) targetNodes.get((String)argumentSource);
									 
									 try{
										 mapToolPanel.getModel().addLink(source, target);
									 }catch(Exception e){
										 e.printStackTrace();
									 }
									 
									children.add(argumentSource);
	
								}
							}
						}

						return children;
					}
					 
				 }.run(transformer);

			 }else{
			 
				 Record source = (Record) targetNodes.get(paramContext.getVariable().getName());
				 Record target = (Record) sourceNodes.get(paramContext.getArgument().getText());
				 if(paramContext.isKey()){
				 	target.setKeyType(Record.KEY);
			 	 }
			 	 
				 try{
					 mapToolPanel.getModel().addLink(source, target);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			 }
			 
			 mapToolPanel.revalidate();
		 }
	}
	

}
