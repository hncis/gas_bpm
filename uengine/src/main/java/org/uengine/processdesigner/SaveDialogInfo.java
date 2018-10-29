package org.uengine.processdesigner;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.validator.NotNullValid;
import org.metaworks.validator.Validator;
import org.uengine.kernel.RevisionInfo;

public class SaveDialogInfo{
	
	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;

		type.setName("Deploy process design to uEngine server");
		
		fd = type.getFieldDescriptor("Name");
		fd.setValidators(new Validator[]{new NotNullValid()});

		fd = type.getFieldDescriptor("Version");
		fd.setValidators(new Validator[]{new NotNullValid()});
		
		fd = type.getFieldDescriptor("Alias");
		fd.setValidators(new Validator[]{new NotNullValid()});
		
		type.setFieldOrder(new String[]{"Name", "Alias", "Version", "Author"});
	}
	
	String name;
	String alias;
	int version;
	boolean autoProduction = true;
	RevisionInfo author;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public RevisionInfo getAuthor() {
		return author;
	}
	public void setAuthor(RevisionInfo author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isAutoProduction() {
		return autoProduction;
	}
	public void setAutoProduction(boolean autoProduction) {
		this.autoProduction = autoProduction;
	}
}



