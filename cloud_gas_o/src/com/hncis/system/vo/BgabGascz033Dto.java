package com.hncis.system.vo;

import java.io.Serializable;

import com.hncis.common.vo.Common;

public class BgabGascz033Dto extends Common implements Serializable{
	private static final long serialVersionUID = -6379609815880903421L;

	private String corp_cd     = "";
	private String ogc_fil_nm  = "";
	private String fil_nm      = "";
	private int fil_mgn_qty    = 0;
	private String inp_ymd     = "";
	private String ipe_eeno    = "";
	private String mdfy_ymd    = "";
	private String updr_eeno   = "";
	private String csrfToken   = "";

	@Override
	public String getCorp_cd() {
		return corp_cd;
	}
	@Override
	public void setCorp_cd(String corp_cd) {
		this.corp_cd = corp_cd;
	}
	public String getOgc_fil_nm() {
		return ogc_fil_nm;
	}
	public void setOgc_fil_nm(String ogc_fil_nm) {
		this.ogc_fil_nm = ogc_fil_nm;
	}
	public String getFil_nm() {
		return fil_nm;
	}
	public void setFil_nm(String fil_nm) {
		this.fil_nm = fil_nm;
	}
	public int getFil_mgn_qty() {
		return fil_mgn_qty;
	}
	public void setFil_mgn_qty(int fil_mgn_qty) {
		this.fil_mgn_qty = fil_mgn_qty;
	}
	public String getInp_ymd() {
		return inp_ymd;
	}
	public void setInp_ymd(String inp_ymd) {
		this.inp_ymd = inp_ymd;
	}
	public String getIpe_eeno() {
		return ipe_eeno;
	}
	public void setIpe_eeno(String ipe_eeno) {
		this.ipe_eeno = ipe_eeno;
	}
	public String getMdfy_ymd() {
		return mdfy_ymd;
	}
	public void setMdfy_ymd(String mdfy_ymd) {
		this.mdfy_ymd = mdfy_ymd;
	}
	public String getUpdr_eeno() {
		return updr_eeno;
	}
	public void setUpdr_eeno(String updr_eeno) {
		this.updr_eeno = updr_eeno;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCsrfToken() {
		return csrfToken;
	}
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}
}
