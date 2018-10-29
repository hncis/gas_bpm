package com.defaultcompany.web.acl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.uengine.util.UEngineUtil;

public class Authority {

	private String aclid;
	private String comcode;
	private String comname;
	private String partcode;
	private String partname;
	private String empcode;
	private String empname;
	private String rolecode;
	private String rolename;
	private String defaultuser;
	private String permission;

	public String getAclid() {
		return aclid;
	}

	public void setAclid(String aclid) {
		this.aclid = aclid;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
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

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDefaultuser() {
		return defaultuser;
	}

	public void setDefaultuser(String defaultuser) {
		this.defaultuser = defaultuser;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	private void encode() {
		if (UEngineUtil.isNotEmpty(empname)) {
			try {
				empname = URLEncoder.encode(empname, "UTF-8");
			} catch (UnsupportedEncodingException e) { }
		}
		if (UEngineUtil.isNotEmpty(partname)) {
			try {
				partname = URLEncoder.encode(partname, "UTF-8");
			} catch (UnsupportedEncodingException e) { }
		}
		if (UEngineUtil.isNotEmpty(comname)) {
			try {
				comname = URLEncoder.encode(comname, "UTF-8");
			} catch (UnsupportedEncodingException e) { }
		}
		if (UEngineUtil.isNotEmpty(rolename)) {
			try {
				rolename = URLEncoder.encode(rolename, "UTF-8");
			} catch (UnsupportedEncodingException e) { }
		}
		
	}
	
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		encode();
		
		sb.append("{");
		sb.append("type : 'authority', ");
		sb.append("aclid : '" + aclid + "', ");
		sb.append("comcode : '" + comcode + "', ");
		sb.append("comname : '" + comname + "', ");
		sb.append("partcode : '" + partcode + "', ");
		sb.append("partname : '" + partname + "', ");
		sb.append("empcode : '" + empcode + "', ");
		sb.append("empname : '" + empname + "', ");
		sb.append("rolecode : '" + rolecode + "', ");
		sb.append("rolename : '" + rolename + "', ");
		sb.append("defaultuser : '" + defaultuser + "', ");
		sb.append("permission : '" + permission + "', ");
		sb.append("}");
		
		return sb.toString();
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();
		encode();
		
		sb.append("<authority>").append("\r\n");
		sb.append("  <aclid>").append(aclid).append("</aclid>").append("\r\n");
		sb.append("  <comcode>").append(comcode).append("</comcode>").append("\r\n");
		sb.append("  <comname>").append(comname).append("</comname>").append("\r\n");
		sb.append("  <partcode>").append(partcode).append("</partcode>").append("\r\n");
		sb.append("  <partname>").append(partname).append("</partname>").append("\r\n");
		sb.append("  <empcode>").append(empcode).append("</empcode>").append("\r\n");
		sb.append("  <empname>").append(empname).append("</empname>").append("\r\n");
		sb.append("  <rolecode>").append(rolecode).append("</rolecode>").append("\r\n");
		sb.append("  <rolename>").append(rolename).append("</rolename>").append("\r\n");
		sb.append("  <defaultuser>").append(defaultuser).append("</defaultuser>").append("\r\n");
		sb.append("  <permission>").append(permission).append("</permission>").append("\r\n");
		sb.append("</authority>");
		
		return sb.toString();
	}
}
