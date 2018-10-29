package com.defaultcompany.organization.web.chartpicker;

import org.uengine.util.UEngineUtil;

public class Role {
	private String code;
	private String descr;
	private String comcode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getComcode() {
		return comcode;
	}
	
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();

		sb.append("{type : 'role', ");
		sb.append("code : '").append(code).append("', ");
		//descr = (UEngineUtil.encodeUTF8(descr)).replace('+', ' ');
		sb.append("name : '").append(descr).append("', ");
		sb.append("globalcom : '").append(comcode).append("'");
		sb.append("}");
		
		return sb.toString();
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<role>").append("\r\n");
		sb.append("  <code>").append(code).append("</code>").append("\r\n");
		sb.append("  <name>").append(UEngineUtil.encodeUTF8(descr)).append("</name>").append("\r\n");
		sb.append("  <globalcom>").append(comcode).append("</globalcom>").append("\r\n");
		sb.append("</role>");

		return sb.toString();
	}

}
