package com.hncis.batch.job.vo.impl;

import com.hncis.batch.job.vo.Tree;

public class TreeVo implements Tree {
	
	private String value;
	private String text;
	private String parentValue;
	private String parentText;
	private String type;
	private boolean checked;
	private boolean leaf;
	private String leafOrgType;
	private boolean isMaketAreaView;
	private String roleId;
	private String relationType;
	private String orgId;
	private String userId;
	private String[] statusTypes;
	private String isExpiratedYn;
	private String orgType;
	private String treeType;
	private String activeYn;	
	private String ord;
	private String effectDate;	
	private String expiraDate;
	
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getExpiraDate() {
		return expiraDate;
	}
	public void setExpiraDate(String expiraDate) {
		this.expiraDate = expiraDate;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getActiveYn() {
		return activeYn;
	}
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	public String getTreeType() {
		return treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	public String getIsExpiratedYn() {
		return isExpiratedYn;
	}
	public void setIsExpiratedYn(String isExpiratedYn) {
		this.isExpiratedYn = isExpiratedYn;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public boolean isMaketAreaView() {
		return isMaketAreaView;
	}
	public void setMaketAreaView(boolean isMaketAreaView) {
		this.isMaketAreaView = isMaketAreaView;
	}
	public String getLeafOrgType() {
		return leafOrgType;
	}
	public void setLeafOrgType(String leafOrgType) {
		this.leafOrgType = leafOrgType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParentValue() {
		return parentValue;
	}
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String[] getStatusTypes() {
		return statusTypes;
	}
	public void setStatusTypes(String[] statusTypes) {
		this.statusTypes = statusTypes;
	}
	public String getParentText() {
		return parentText;
	}
	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

}
