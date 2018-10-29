package org.uengine.contexts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.janino.Java;
import org.codehaus.janino.Java.ClassDeclaration;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.SimpleCompiler;
import org.codehaus.janino.util.Traverser;
import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.ui.XMLValueInput;
import org.uengine.util.UEngineUtil;

public class ComplexType implements Serializable{
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
		
		fd = type.getFieldDescriptor("TypeId");
		XMLValueInput inputter = new XMLValueInput("/processmanager/processDefinitionListXML.jsp?omitVersion=false&objectType=class")/* {
			public void onValueChanged() { 
				changeBindingArguments((String) getValue());
			}
		}*/;

		fd.setInputter(inputter);
	}

	String typeId;
	Object value;
	Class typeClass;
	ClassLoader classLoader;
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public Class getTypeClass() throws Exception{
		return getTypeClass(null);
	}

	public Class getTypeClass(ProcessManagerRemote pm) throws Exception{
		if(typeClass!=null) return typeClass;
		
		String clsTypeId = ProcessDefinition.splitDefinitionAndVersionId(getTypeId())[1];

		InputStream is = null;
		String javaSource = null;
		
		if(GlobalContext.isDesignTime()){
			is = ProcessDesigner.getClientProxy().showObjectDefinitionWithDefinitionId(clsTypeId);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			UEngineUtil.copyStream(is, bao);
			javaSource = bao.toString();
		}else{
			javaSource = pm.getResource(clsTypeId);
		}
		
        Java.CompilationUnit cu;
        
        cu = new Parser(new Scanner("", new ByteArrayInputStream(javaSource.getBytes()))).parseCompilationUnit();
        final ArrayList clsNames = new ArrayList();

        // Traverse it and count declarations.
        new Traverser(){
			public void traverseClassDeclaration(ClassDeclaration arg0) {
				clsNames.add(arg0.getClassName());
				super.traverseClassDeclaration(arg0);
			}
        }.traverseCompilationUnit(cu);

        String clsName = (String)clsNames.get(0);
        
        SimpleCompiler compiler = new SimpleCompiler();
		compiler.setParentClassLoader(GlobalContext.class.getClassLoader());
		compiler.cook(new ByteArrayInputStream(javaSource.getBytes()));
		//compiler.getClassLoader().
		
		classLoader = compiler.getClassLoader();
		typeClass = classLoader.loadClass(clsName);
		
		return typeClass;
	}

}
