package org.uengine.web.process.vo;

import java.util.Map;

public class TreeVO {

	private String id;
	private String prodVerId;
	private String defVerId;
	private String parent;
	private String text;
	private String icon;
	private Map<String, Boolean> state;
	private Map<String, String> li_attr;
	private Map<String, String> a_attr;
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Map<String, Boolean> getState() {
		return state;
	}
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}
	public Map<String, String> getLi_attr() {
		return li_attr;
	}
	public void setLi_attr(Map<String, String> li_attr) {
		this.li_attr = li_attr;
	}
	public Map<String, String> getA_attr() {
		return a_attr;
	}
	public void setA_attr(Map<String, String> a_attr) {
		this.a_attr = a_attr;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProdVerId() {
		return prodVerId;
	}
	public void setProdVerId(String prodVerId) {
		this.prodVerId = prodVerId;
	}
	public String getDefVerId() {
		return defVerId;
	}
	public void setDefVerId(String defVerId) {
		this.defVerId = defVerId;
	}
}
