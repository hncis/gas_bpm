package org.uengine.bpmutil.vo;

import java.io.Serializable;

public class WorkVO implements Serializable {

	String titleLink;
	String title;
	String regDate;
	String status;
	String link;
	String statusClz;
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitleLink() {
		return titleLink;
	}
	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getStatusClz() {
		return statusClz;
	}
	public void setStatusClz(String statusClz) {
		this.statusClz = statusClz;
	}
	
	
}
