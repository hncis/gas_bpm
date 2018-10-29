package com.defaultcompany.organization.web.chartpicker;

import org.uengine.admin.servlet.UenginePropertyServlet;
import org.uengine.util.UEngineUtil;

public class Company {
	private String code;
	private String name;
	private String description;
	private String cnt;

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\"type\" : \"company\", ");
		sb.append("\"code\" : \"").append(code).append("\", ");
		name = (UEngineUtil.encodeUTF8(name)).replace('+', ' ');
		sb.append("\"name\" : \"").append(name).append("\", ");
		sb.append("\"cnt\" : \"").append(cnt).append("\", ");
		if(UEngineUtil.isNotEmpty(description)){
			description = (UEngineUtil.encodeUTF8(description)).replace('+', ' ');
			sb.append("\"description\" : \"").append(description).append("\" ");
		}else{
			sb.append("\"description\" : \"").append("\" ");
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	public String toJsonForJstree() {
		return ("{"
				+ "		\"data\": \"" + name + "\","
				+ "		\"state\" : \"closed\","
				+ "		\"attr\": {"
				+ "			\"rel\" : \"company\", "
				+ "			\"code\" : \"" + code + "\", "
				+ "			\"name\" : \"" + name + "\", "
				+ "			\"description\" : \"" + description + "\" "
//				+ "			\"children\" : [] "
				+ "		}"
				+ "}"
		);
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<dept>").append("\r\n");
		sb.append("  <code>").append(code).append("</code>").append("\r\n");
		sb.append("  <name>").append(UEngineUtil.encodeUTF8(name)).append("</name>").append("\r\n");
		sb.append("  <description>").append(UEngineUtil.encodeUTF8(description)).append("</description>").append("\r\n");
		sb.append("</dept>");

		return sb.toString();
	}

}
