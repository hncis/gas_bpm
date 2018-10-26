package com.hncis.system.vo;

import java.io.Serializable;

import com.hncis.common.vo.Common;

public class BgabGascz031Dto extends Common implements Serializable{
	private static final long serialVersionUID = -6379609815880903421L;

	private String corp_cd     = "";
	private String corp_nm     = "";
	private String task_gubun  = "";
	private String task_name  = "";
	private String appr_use_yn      = "";
	private String inp_ymd     = "";
	private String ipe_eeno    = "";
	private String mdfy_ymd    = "";
	private String updr_eeno   = "";
	private String temp_appr_use_yn = "";
	private String tmp_flag = "";

	@Override
	public String getCorp_cd() {
		return corp_cd;
	}
	@Override
	public void setCorp_cd(String corp_cd) {
		this.corp_cd = corp_cd;
	}
	public String getCorp_nm() {
		return corp_nm;
	}
	public void setCorp_nm(String corp_nm) {
		this.corp_nm = corp_nm;
	}
	public String getTask_gubun() {
		return task_gubun;
	}
	public void setTask_gubun(String task_gubun) {
		this.task_gubun = task_gubun;
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
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getAppr_use_yn() {
		return appr_use_yn;
	}
	public void setAppr_use_yn(String appr_use_yn) {
		this.appr_use_yn = appr_use_yn;
	}
	public String getTemp_appr_use_yn() {
		return temp_appr_use_yn;
	}
	public void setTemp_appr_use_yn(String temp_appr_use_yn) {
		this.temp_appr_use_yn = temp_appr_use_yn;
	}
	public String getTmp_flag() {
		return tmp_flag;
	}
	public void setTmp_flag(String tmp_flag) {
		this.tmp_flag = tmp_flag;
	}

}
