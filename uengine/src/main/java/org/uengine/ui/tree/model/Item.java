package org.uengine.ui.tree.model;

import java.io.Serializable;
import java.util.Collection;

public class Item implements Serializable, Comparable<Item> {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	private String id;
	private String name;
	private String obj;
	private String type;
	private String visible;
	private String parent;
	private String childCnt;
	
	private Character[] authority;
	private Collection<Item> children;
	
	private String prodVerId;

	public Item() {

	}
	
	public Item(String id, String name, String obj, String type, String visible, String parent, String childCnt, Character[] authority) {
		this.id = id;
		this.name = name;
		this.obj = obj;
		this.type = type;
		this.visible = visible;
		this.parent = parent;
		this.childCnt = childCnt;
		this.authority = authority;
	}

	public Item(String id, String name, String obj, String type, String visible, String parent, String childCnt, Character[] authority, Collection<Item> children) {
		this.id = id;
		this.name = name;
		this.obj = obj;
		this.type = type;
		this.visible = visible;
		this.parent = parent;
		this.childCnt = childCnt;
		this.children = children;
		this.authority = authority;
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

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Collection<Item> getChildren() {
		return children;
	}

	public void setChildren(Collection<Item> children) {
		this.children = children;
	}

	public String getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(String childCnt) {
		this.childCnt = childCnt;
	}
	
	public Character[] getAuthority() {
		return authority;
	}

	public void setAuthority(Character[] authority) {
		this.authority = authority;
	}

	public String getProdVerId() {
	    return prodVerId;
	}

	public void setProdVerId(String prodVerId) {
	    this.prodVerId = prodVerId;
	}

	public int compareTo(Item o) {
		int o1Order = 0;
		int o2Order = 0;
		
		for (ItemType itemType : ItemType.values()) {
			if (this.getObj().equals(itemType.getValue())) {
				o1Order = itemType.getOrder();
				break;
			}
		}
		
		for (ItemType itemType : ItemType.values()) {
			if (o.getObj().equals(itemType.getValue())) {
				o2Order = itemType.getOrder();
				break;
			}
		}
		
		int result = 0;
		
		if (o1Order > o2Order) {
			result = 1;
		} else if (o1Order < o2Order) {
			result = -1;
		}
		
		if (result == 0) {
			result = this.getName().compareTo(o.getName());
		}
		
		return result; 
	}
	
}
