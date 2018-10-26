package com.hncis.system.vo;

import java.io.Serializable;

import com.hncis.common.vo.Common;

public class BgabGascz020Dto extends Common implements Serializable{
	
	private static final long serialVersionUID = -1830327130658426182L;
	
	private String eeno = "";
	private String txt  = "";
	public String getEeno() {
		return eeno;
	}
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
}
