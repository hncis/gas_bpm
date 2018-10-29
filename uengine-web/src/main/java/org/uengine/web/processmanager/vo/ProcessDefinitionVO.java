package org.uengine.web.processmanager.vo;

import java.io.Serializable;

import org.uengine.web.chart.vo.FlowChartVO;

/**
 * <pre>
 * org.uengine.web.organization.vo 
 * UserVO.java
 * 
 * </pre>
 * @date : 2016. 6. 22. 오후 4:11:08
 * @version : 
 * @author : next3
 */
public class ProcessDefinitionVO extends FlowChartVO {

	private static final long serialVersionUID = 342794486746189970L;
	private String level;
	private String isLeaf;
	private String objType;
	private String defId;
	private String defName;
	private String description;
	private String defVerId;
	private String version;
	private String alias;
	private String modDate;
	private String author;
	private String prodVer;
	private String authorName;
	private String programId;
	private String folderId;
	private String folderName;
	private String processCode;
	private String duration;
	private String comCode;
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
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
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
	 * @return the defId
	 */
	public String getDefId() {
		return defId;
	}
	/**
	 * @param defId the defId to set
	 */
	public void setDefId(String defId) {
		this.defId = defId;
	}
	/**
	 * @return the defName
	 */
	public String getDefName() {
		return defName;
	}
	/**
	 * @param defName the defName to set
	 */
	public void setDefName(String defName) {
		this.defName = defName;
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
	public String getDefVerId() {
		return defVerId;
	}
	public void setDefVerId(String defVerId) {
		this.defVerId = defVerId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getProdVer() {
		return prodVer;
	}
	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "ProcessDefinitionVO [level=" + level + ", isLeaf=" + isLeaf
				+ ", objType=" + objType + ", defId=" + defId + ", defName="
				+ defName + ", description=" + description + ", defVerId="
				+ defVerId + ", version=" + version + ", alias=" + alias
				+ ", modDate=" + modDate + ", author=" + author + ", prodVer="
				+ prodVer + ", authorName=" + authorName + ", programId="
				+ programId + ", folderId=" + folderId + ", folderName="
				+ folderName + ", processCode=" + processCode + ", duration="
				+ duration + ", comCode=" + comCode + "]";
	}
	
}
