package org.uengine.processdesigner;

public class LoadedDefinition {
	//Type : 0 -> DefinitionID, 1 -> DefinitionVersionID, 2 -> SubProcessDefinition
	
	public LoadedDefinition(String id, String name, int type){
		this.id = id;
		this.type = type;
		setName(name);
	}
	public LoadedDefinition(){
		this(null, "", 0);
	}
	String name;
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	int type;
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	
	String id;
	public void setID(String id){
		this.id = id;
	}
	public String getID(){
		return id;
	}

}
