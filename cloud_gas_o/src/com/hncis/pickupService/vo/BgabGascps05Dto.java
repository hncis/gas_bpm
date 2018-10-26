package com.hncis.pickupService.vo;

import com.hncis.common.vo.Common;

public class BgabGascps05Dto extends Common{

	private String seq           = "";
	private String affr_scn_cd   = "";
	private String firm_cd       = "";
	private String firm_nm       = "";
	private String use_yn       = "";
	private String rem_sbc       = "";
	private String inp_ymd       = "";
	private String ipe_eeno      = "";
	private String mdfy_ymd      = "";
	private String updr_eeno     = "";

	private String mode;
	private boolean errYn;
	private String errMsg;
	private String code = "";
	
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getAffr_scn_cd() {
		return affr_scn_cd;
	}
	public void setAffr_scn_cd(String affr_scn_cd) {
		this.affr_scn_cd = affr_scn_cd;
	}
	public String getFirm_cd() {
		return firm_cd;
	}
	public void setFirm_cd(String firm_cd) {
		this.firm_cd = firm_cd;
	}
	public String getFirm_nm() {
		return firm_nm;
	}
	public void setFirm_nm(String firm_nm) {
		this.firm_nm = firm_nm;
	}
	public String getRem_sbc() {
		return rem_sbc;
	}
	public void setRem_sbc(String rem_sbc) {
		this.rem_sbc = rem_sbc;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public boolean isErrYn() {
		return errYn;
	}
	public void setErrYn(boolean errYn) {
		this.errYn = errYn;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
