package com.defaultcompany.organization.web.chartpicker;

import org.uengine.util.UEngineUtil;

public class User {

	private String code;
	private String name;
	private String jikname;
	private String email;
	private String partcode;
	private String partname;
	private String globalcom;

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

	public String getJikname() {
		return jikname;
	}

	public void setJikname(String jikname) {
		this.jikname = jikname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPartcode() {
		return partcode;
	}

	public void setPartcode(String partcode) {
		this.partcode = partcode;
	}

	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}
	
	public String getGlobalcom() {
		return globalcom;
	}
	
	public void setGlobalcom(String globalcom) {
		this.globalcom = globalcom;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("type : 'user', ");
		sb.append("code : '").append(code).append("', ");
		name = (UEngineUtil.encodeUTF8(name)).replace('+', ' ');
		sb.append("\"name\" : \"").append(name).append("\", ");
//		sb.append("name : '").append(UEngineUtil.encodeUTF8(name)).append("', ");
		sb.append("jikname : '").append(UEngineUtil.encodeUTF8(jikname)).append("', ");
		sb.append("email : '").append(email).append("', ");
		sb.append("partcode : '").append(partcode).append("', ");
		sb.append("partname : '").append(UEngineUtil.encodeUTF8(partname)).append("', ");
		sb.append("globalcom : '").append(globalcom).append("'");
		sb.append("}");
		
		return sb.toString();
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();

		sb.append("<user>").append("\r\n");
		sb.append("  <code>").append(code).append("</code>").append("\r\n");
		sb.append("  <name>").append(UEngineUtil.encodeUTF8(name)).append("</name>").append("\r\n");
		sb.append("  <jikname>").append(UEngineUtil.encodeUTF8(jikname)).append("</jikname>").append("\r\n");
		sb.append("  <email>").append(email).append("</email>").append("\r\n");
		sb.append("  <partcode>").append(partcode).append("</partcode>").append("\r\n");
		sb.append("  <partname>").append(UEngineUtil.encodeUTF8(partname)).append("</partname>").append("\r\n");
		sb.append("</user>");		

		return sb.toString();
	}

}
