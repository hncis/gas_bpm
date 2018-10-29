package com.defaultcompany.web.strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	private String id;
	private String name;
	private String type;
	private List<Integer> parent = new ArrayList<Integer>();
	private Data data = new Data();
	private List<Item> children = new ArrayList<Item>();
	private int childrenCnt;
	
	public Item(){}
	
	public Item(String id){
		setId(id);
	}
	
	public int getChildrenCnt() {
		return childrenCnt;
	}

	public void setChildrenCnt(int childrenCnt) {
		this.childrenCnt = childrenCnt;
	}

	public Object clone()  {
		// TODO Auto-generated method stub
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setType(type);
		item.setData(data);
		item.setParent(parent);
		item.setChildren(children);
		
		return item;
	}

	public List<Item> getChildren() {
		if (this.children == null) {
			this.setChildren(new ArrayList<Item>());
		}
		return children;
	}

	public void setChildren(List<Item> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Integer> getParent() {
		if (parent == null ){
			this.setParent(new ArrayList<Integer>());
		}
		return parent;
	}

	public void setParent(List<Integer> parent) {
		this.parent = parent;
	}

	public Data getData() {
		if (data == null) {
			this.setData(new Data());
		}
		return data;
	}

	private void setData(Data data) {
		this.data = data;
	}

}
