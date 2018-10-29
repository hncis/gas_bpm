package org.uengine.kernel;

import java.io.Serializable;

import org.metaworks.FieldDescriptor;
import org.metaworks.ObjectType;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.contexts.TextContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.processdesigner.mapper.Transformer;
import org.uengine.processdesigner.mapper.TransformerMapping;

/**
 * @author Jinyoung Jang
 */
public class ParameterContext implements Serializable{
	
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public static final String DIRECTION_IN = "in".intern();
	public static final String DIRECTION_OUT = "out".intern();
	public static final String DIRECTION_INOUT = "in-out".intern();
	
	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
				
		fd = type.getFieldDescriptor("Direction");		
		fd.setInputter(new RadioInput(
			new String[]{
				GlobalContext.getLocalizedMessage("parametercontext.direction.in.displayname", "in"),
				GlobalContext.getLocalizedMessage("parametercontext.direction.out.displayname", "out"),
				GlobalContext.getLocalizedMessage("parametercontext.direction.inout.displayname", "in-out")
			},
			new Object[]{
				DIRECTION_IN, 
				DIRECTION_OUT, 
				DIRECTION_INOUT, 
			}
		));
	}
	
	TextContext argument = TextContext.createInstance();
		public TextContext getArgument() {
			if(argument.getText()==null && getVariable()!=null){
				return getVariable().getDisplayName();
			}
			
			return argument;
		}
		public void setArgument(TextContext string) {
			argument = string;
		}

	ProcessVariable variable;
		public ProcessVariable getVariable() {
			return variable;
		}
		public void setVariable(ProcessVariable variable) {
			this.variable = variable;
		}

	Object type;
		public Object getType() {
			return type;
		}
		public void setType(Object name) {
			type = name;
		}
	
	String direction;
		public String getDirection() {
			return direction;
		}
		public void setDirection(String i) {
			direction = i;
		}
		
	TransformerMapping transformerMapping;
		public TransformerMapping getTransformerMapping() {
			return transformerMapping;
		}
		public void setTransformerMapping(TransformerMapping transformerMapping) {
			this.transformerMapping = transformerMapping;
		}	
	
	String typeClassName;
		public String getTypeClassName() {
			return variable.getClass().getName();
		}
		public void setTypeClassName(String typeClassName) {
			this.typeClassName = typeClassName;
		}
}
