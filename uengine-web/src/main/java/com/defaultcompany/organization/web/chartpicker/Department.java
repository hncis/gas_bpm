package com.defaultcompany.organization.web.chartpicker;

import org.uengine.util.UEngineUtil;

public class Department {
	private String code;
	private String name;
	private String parent;
	private String globalcom;
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
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getGlobalcom() {
		return globalcom;
	}
	
	public void setGlobalcom(String globalcom) {
		this.globalcom = globalcom;
	}

	public String toJson() {
		StringBuffer sb = new StringBuffer();

		sb.append("{\"type\" : \"department\", ");
		sb.append("\"code\" : \"").append(code).append("\", ");
		name = (UEngineUtil.encodeUTF8(name)).replace('+', ' ');
		sb.append("\"name\" : \"").append(name).append("\", ");
		sb.append("\"parent\" : \"").append(parent).append("\", ");
		sb.append("\"globalcom\" : \"").append(globalcom).append("\", ");
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
				+ "\"data\": \"" + name + "\","
				+ "\"state\": \"closed\","
				+ "\"attr\": {\"rel\" : \"department\", "
				+ "		\"code\" : \"" + code + "\", "
				+ "		\"name\" : \"" + name + "\", "
				+ "		\"parent\" : \"" + parent + "\", "
				+ "		\"globalcom\" : \"" + globalcom + "\", "
				+ "		\"description\" : \"" + description + "\""
				+ "		}"
				+ "}"
		);
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<dept>").append("\r\n");
		sb.append("  <code>").append(code).append("</code>").append("\r\n");
		sb.append("  <name>").append(UEngineUtil.encodeUTF8(name)).append("</name>").append("\r\n");
		sb.append("  <parent>").append(parent).append("</parent>").append("\r\n");
		sb.append("  <globalcom>").append(globalcom).append("</globalcom>").append("\r\n");
		sb.append("  <description>").append(UEngineUtil.encodeUTF8(description)).append("</description>").append("\r\n");
		sb.append("</dept>");

		return sb.toString();
	}

}
