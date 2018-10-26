package com.hncis.businessVehicles.vo;

import com.hncis.common.vo.Common;

public class BgabGascbv04Dto  extends Common{
	
	private String doc_no			= "";
	private String eeno				= "";
	private int	seq					= 0;
	private String affr_scn_cd		= "";
	private String fil_nm			= "";
	private String ogc_fil_nm		= "";
	private int fil_mgn_qty			= 0;
	private String ipe_eeno			= "";
	private String inp_ymd			= "";
	private String pgs_st_cd		= "";
	
	public String getPgs_st_cd() {
		return pgs_st_cd;
	}
	public void setPgs_st_cd(String pgs_st_cd) {
		this.pgs_st_cd = pgs_st_cd;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public String getEeno() {
		return eeno;
	}
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getAffr_scn_cd() {
		return affr_scn_cd;
	}
	public void setAffr_scn_cd(String affr_scn_cd) {
		this.affr_scn_cd = affr_scn_cd;
	}
	public String getFil_nm() {
		return fil_nm;
	}
	public void setFil_nm(String fil_nm) {
		this.fil_nm = fil_nm;
	}
	public String getOgc_fil_nm() {
		return ogc_fil_nm;
	}
	public void setOgc_fil_nm(String ogc_fil_nm) {
		this.ogc_fil_nm = ogc_fil_nm;
	}
	public int getFil_mgn_qty() {
		return fil_mgn_qty;
	}
	public void setFil_mgn_qty(int fil_mgn_qty) {
		this.fil_mgn_qty = fil_mgn_qty;
	}
	public String getIpe_eeno() {
		return ipe_eeno;
	}
	public void setIpe_eeno(String ipe_eeno) {
		this.ipe_eeno = ipe_eeno;
	}
	public String getInp_ymd() {
		return inp_ymd;
	}
	public void setInp_ymd(String inp_ymd) {
		this.inp_ymd = inp_ymd;
	}
}
