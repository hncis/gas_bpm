package org.uengine.web.organization.vo;

import java.io.Serializable;

/**
 * <pre>
 * org.uengine.web.organization.vo 
 * OrganizationVO.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오전 10:55:06
 * @version : 
 * @author : next3
 */
public class OrganizationVO implements Serializable {

	private static final long serialVersionUID = 342794486746189970L;
	private String level;
	private String partCode;
    private String partName;
    private String description;
    private boolean isLeaf;
    private int lft, rgt;
    private boolean expanded = false;
    private String objType = "folder";
	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}
	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}
	/**
	 * @return the expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * @return the lft
	 */
	public int getLft() {
		return lft;
	}
	/**
	 * @param lft the lft to set
	 */
	public void setLft(int lft) {
		this.lft = lft;
	}
	/**
	 * @return the rgt
	 */
	public int getRgt() {
		return rgt;
	}
	/**
	 * @param rgt the rgt to set
	 */
	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	/**
	 * @return the isLeaf
	 */
	public boolean isLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @return the partName
	 */
	public String getPartName() {
		return partName;
	}
	/**
	 * @param partName the partName to set
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
