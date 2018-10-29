package com.defaultcompany.organization.web.chartpicker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import org.uengine.util.UEngineUtil;

public class CalendarEvent {
	private String taskid;
	private String title;
	private String description;
	private Timestamp startdate;
	private Timestamp enddate;
	private String status;

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();

		sb.append("{type : 'event', ");
		sb.append("id : ").append(taskid).append(", ");
		sb.append("title : '").append(UEngineUtil.encodeUTF8(title)).append("', ");
		sb.append("start : '").append(startdate).append("', ");
		sb.append("end : '").append(enddate).append("', ");
		sb.append("status : '").append(status).append("', ");
		sb.append("description : '").append(UEngineUtil.encodeUTF8(description)).append("' ");
		sb.append("}");
		
		return sb.toString();
	}
}
