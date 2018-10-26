package com.hncis.businessTravel.vo;

import com.hncis.common.vo.Common;

public class BgabGascbt04 extends Common{
	
	private String doc_no			= "";
	private String eeno				= "";
	private String eenm				= "";
	private int	seq					= 0;
	private String prvs_scn_cd		= "";
	private String prvs_scn_nm		= "";
	private String prvs_dtl_cd		= "";
	private String prvs_dtl_nm		= "";
	private String strt_ymd			= "";
	private String fnh_ymd			= "";
	private String stl_way_cd		= "";
	private String curr_cd			= "";
	private String curr_nm			= "";
	private double apl_xr			= 0;
	private String prvs_amt			= "";
	private String natv_curr_amt	= "";
	private String rem_sbc			= "";
	private String dom_abrd_scn_cd	= "";
	private String nat_g_scn_cd		= "";
	private String arsl_refl_yn		= "";
	private String budg_no				= "";
	private String ipe_eeno			= "";
	private String inp_ymd			= "";
	private String updr_eeno		= "";
	private String mdfy_ymd			= "";
	private String cost_cd			= "";

	private String own_doc_no			= "";
	private String own_eeno				= "";
	private int	own_seq					= 0;
	private String own_prvs_scn_cd		= "";
	private String own_prvs_dtl_cd		= "";
	private String own_strt_ymd			= "";
	private String own_fnh_ymd			= "";
	private String own_stl_way_cd		= "";
	private String own_curr_cd			= "";
	private double own_apl_xr			= 0;
	private String own_prvs_amt			= "";
	private String own_natv_curr_amt	= "";
	private String own_arsl_refl_yn		= "";
	private String own_evds_attc_fil_nm = "";
	private String own_rem_sbc			= "";
	private String own_vendor_cd		= "";
	private String own_po_no			= "";

	private String vou_doc_no			= "";
	private String vou_eeno				= "";
	private int	vou_seq					= 0;
	private String vou_prvs_scn_cd		= "";
	private String vou_prvs_dtl_cd		= "";
	private String vou_strt_ymd			= "";
	private String vou_fnh_ymd			= "";
	private String vou_stl_way_cd		= "";
	private String vou_curr_cd			= "";
	private double vou_apl_xr			= 0;
	private String vou_prvs_amt			= "";
	private String vou_natv_curr_amt	= "";
	private String vou_arsl_refl_yn		= "";
	private String vou_evds_attc_fil_nm = "";
	private String vou_rem_sbc			= "";
	private String vou_vendor_cd		= "";
	private String vou_po_no			= "";
	
	private String tot_amt 				= "";
	private String po_no				= "";
	private String vendor_cd			= "";
	private String vendor_nm			= "";
	private String budg_type 			= "";
	private int	   hid_seq				= 0;
	private String own_dtl_nm			= "";
	private String h_gubn				= "";
	private String req_eeno				= "";
	private String budg_text			= "";
	
	public String getCurr_nm() {
		return curr_nm;
	}
	public void setCurr_nm(String curr_nm) {
		this.curr_nm = curr_nm;
	}
	public String getNatv_curr_amt() {
		return natv_curr_amt;
	}
	public void setNatv_curr_amt(String natv_curr_amt) {
		this.natv_curr_amt = natv_curr_amt;
	}
	public String getPrvs_amt() {
		return prvs_amt;
	}
	public void setPrvs_amt(String prvs_amt) {
		this.prvs_amt = prvs_amt;
	}
	public String getPrvs_scn_nm() {
		return prvs_scn_nm;
	}
	public void setPrvs_scn_nm(String prvs_scn_nm) {
		this.prvs_scn_nm = prvs_scn_nm;
	}
	public String getCost_cd() {
		return cost_cd;
	}
	public void setCost_cd(String cost_cd) {
		this.cost_cd = cost_cd;
	}
	public String getBudg_no() {
		return budg_no;
	}
	public void setBudg_no(String budg_no) {
		this.budg_no = budg_no;
	}
	public String getArsl_refl_yn() {
		return arsl_refl_yn;
	}
	public void setArsl_refl_yn(String arsl_refl_yn) {
		this.arsl_refl_yn = arsl_refl_yn;
	}
	public double getApl_xr() {
		return apl_xr;
	}
	public void setApl_xr(double apl_xr) {
		this.apl_xr = apl_xr;
	}
	public String getDom_abrd_scn_cd() {
		return dom_abrd_scn_cd;
	}
	public void setDom_abrd_scn_cd(String dom_abrd_scn_cd) {
		this.dom_abrd_scn_cd = dom_abrd_scn_cd;
	}
	public String getNat_g_scn_cd() {
		return nat_g_scn_cd;
	}
	public void setNat_g_scn_cd(String nat_g_scn_cd) {
		this.nat_g_scn_cd = nat_g_scn_cd;
	}
	public String getPrvs_dtl_nm() {
		return prvs_dtl_nm;
	}
	public void setPrvs_dtl_nm(String prvs_dtl_nm) {
		this.prvs_dtl_nm = prvs_dtl_nm;
	}
	public String getEenm() {
		return eenm;
	}
	public void setEenm(String eenm) {
		this.eenm = eenm;
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
	public String getPrvs_scn_cd() {
		return prvs_scn_cd;
	}
	public void setPrvs_scn_cd(String prvs_scn_cd) {
		this.prvs_scn_cd = prvs_scn_cd;
	}
	public String getPrvs_dtl_cd() {
		return prvs_dtl_cd;
	}
	public void setPrvs_dtl_cd(String prvs_dtl_cd) {
		this.prvs_dtl_cd = prvs_dtl_cd;
	}
	public String getStrt_ymd() {
		return strt_ymd;
	}
	public void setStrt_ymd(String strt_ymd) {
		this.strt_ymd = strt_ymd;
	}
	public String getFnh_ymd() {
		return fnh_ymd;
	}
	public void setFnh_ymd(String fnh_ymd) {
		this.fnh_ymd = fnh_ymd;
	}
	public String getStl_way_cd() {
		return stl_way_cd;
	}
	public void setStl_way_cd(String stl_way_cd) {
		this.stl_way_cd = stl_way_cd;
	}
	public String getCurr_cd() {
		return curr_cd;
	}
	public void setCurr_cd(String curr_cd) {
		this.curr_cd = curr_cd;
	}
	public String getRem_sbc() {
		return rem_sbc;
	}
	public void setRem_sbc(String rem_sbc) {
		this.rem_sbc = rem_sbc;
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
	public String getUpdr_eeno() {
		return updr_eeno;
	}
	public void setUpdr_eeno(String updr_eeno) {
		this.updr_eeno = updr_eeno;
	}
	public String getMdfy_ymd() {
		return mdfy_ymd;
	}
	public void setMdfy_ymd(String mdfy_ymd) {
		this.mdfy_ymd = mdfy_ymd;
	}
	public String getOwn_doc_no() {
		return own_doc_no;
	}
	public void setOwn_doc_no(String own_doc_no) {
		this.own_doc_no = own_doc_no;
	}
	public String getOwn_eeno() {
		return own_eeno;
	}
	public void setOwn_eeno(String own_eeno) {
		this.own_eeno = own_eeno;
	}
	public int getOwn_seq() {
		return own_seq;
	}
	public void setOwn_seq(int own_seq) {
		this.own_seq = own_seq;
	}
	public String getOwn_prvs_scn_cd() {
		return own_prvs_scn_cd;
	}
	public void setOwn_prvs_scn_cd(String own_prvs_scn_cd) {
		this.own_prvs_scn_cd = own_prvs_scn_cd;
	}
	public String getOwn_prvs_dtl_cd() {
		return own_prvs_dtl_cd;
	}
	public void setOwn_prvs_dtl_cd(String own_prvs_dtl_cd) {
		this.own_prvs_dtl_cd = own_prvs_dtl_cd;
	}
	public String getOwn_strt_ymd() {
		return own_strt_ymd;
	}
	public void setOwn_strt_ymd(String own_strt_ymd) {
		this.own_strt_ymd = own_strt_ymd;
	}
	public String getOwn_fnh_ymd() {
		return own_fnh_ymd;
	}
	public void setOwn_fnh_ymd(String own_fnh_ymd) {
		this.own_fnh_ymd = own_fnh_ymd;
	}
	public String getOwn_stl_way_cd() {
		return own_stl_way_cd;
	}
	public void setOwn_stl_way_cd(String own_stl_way_cd) {
		this.own_stl_way_cd = own_stl_way_cd;
	}
	public String getOwn_curr_cd() {
		return own_curr_cd;
	}
	public void setOwn_curr_cd(String own_curr_cd) {
		this.own_curr_cd = own_curr_cd;
	}
	public double getOwn_apl_xr() {
		return own_apl_xr;
	}
	public void setOwn_apl_xr(double own_apl_xr) {
		this.own_apl_xr = own_apl_xr;
	}
	public String getOwn_prvs_amt() {
		return own_prvs_amt;
	}
	public void setOwn_prvs_amt(String own_prvs_amt) {
		this.own_prvs_amt = own_prvs_amt;
	}
	public String getOwn_natv_curr_amt() {
		return own_natv_curr_amt;
	}
	public void setOwn_natv_curr_amt(String own_natv_curr_amt) {
		this.own_natv_curr_amt = own_natv_curr_amt;
	}
	public String getOwn_arsl_refl_yn() {
		return own_arsl_refl_yn;
	}
	public void setOwn_arsl_refl_yn(String own_arsl_refl_yn) {
		this.own_arsl_refl_yn = own_arsl_refl_yn;
	}
	public String getOwn_evds_attc_fil_nm() {
		return own_evds_attc_fil_nm;
	}
	public void setOwn_evds_attc_fil_nm(String own_evds_attc_fil_nm) {
		this.own_evds_attc_fil_nm = own_evds_attc_fil_nm;
	}
	public String getOwn_rem_sbc() {
		return own_rem_sbc;
	}
	public void setOwn_rem_sbc(String own_rem_sbc) {
		this.own_rem_sbc = own_rem_sbc;
	}
	public String getVou_doc_no() {
		return vou_doc_no;
	}
	public void setVou_doc_no(String vou_doc_no) {
		this.vou_doc_no = vou_doc_no;
	}
	public String getVou_eeno() {
		return vou_eeno;
	}
	public void setVou_eeno(String vou_eeno) {
		this.vou_eeno = vou_eeno;
	}
	public int getVou_seq() {
		return vou_seq;
	}
	public void setVou_seq(int vou_seq) {
		this.vou_seq = vou_seq;
	}
	public String getVou_prvs_scn_cd() {
		return vou_prvs_scn_cd;
	}
	public void setVou_prvs_scn_cd(String vou_prvs_scn_cd) {
		this.vou_prvs_scn_cd = vou_prvs_scn_cd;
	}
	public String getVou_prvs_dtl_cd() {
		return vou_prvs_dtl_cd;
	}
	public void setVou_prvs_dtl_cd(String vou_prvs_dtl_cd) {
		this.vou_prvs_dtl_cd = vou_prvs_dtl_cd;
	}
	public String getVou_strt_ymd() {
		return vou_strt_ymd;
	}
	public void setVou_strt_ymd(String vou_strt_ymd) {
		this.vou_strt_ymd = vou_strt_ymd;
	}
	public String getVou_fnh_ymd() {
		return vou_fnh_ymd;
	}
	public void setVou_fnh_ymd(String vou_fnh_ymd) {
		this.vou_fnh_ymd = vou_fnh_ymd;
	}
	public String getVou_stl_way_cd() {
		return vou_stl_way_cd;
	}
	public void setVou_stl_way_cd(String vou_stl_way_cd) {
		this.vou_stl_way_cd = vou_stl_way_cd;
	}
	public String getVou_curr_cd() {
		return vou_curr_cd;
	}
	public void setVou_curr_cd(String vou_curr_cd) {
		this.vou_curr_cd = vou_curr_cd;
	}
	public double getVou_apl_xr() {
		return vou_apl_xr;
	}
	public void setVou_apl_xr(double vou_apl_xr) {
		this.vou_apl_xr = vou_apl_xr;
	}
	public String getVou_prvs_amt() {
		return vou_prvs_amt;
	}
	public void setVou_prvs_amt(String vou_prvs_amt) {
		this.vou_prvs_amt = vou_prvs_amt;
	}
	public String getVou_natv_curr_amt() {
		return vou_natv_curr_amt;
	}
	public void setVou_natv_curr_amt(String vou_natv_curr_amt) {
		this.vou_natv_curr_amt = vou_natv_curr_amt;
	}
	public String getVou_arsl_refl_yn() {
		return vou_arsl_refl_yn;
	}
	public void setVou_arsl_refl_yn(String vou_arsl_refl_yn) {
		this.vou_arsl_refl_yn = vou_arsl_refl_yn;
	}
	public String getVou_evds_attc_fil_nm() {
		return vou_evds_attc_fil_nm;
	}
	public void setVou_evds_attc_fil_nm(String vou_evds_attc_fil_nm) {
		this.vou_evds_attc_fil_nm = vou_evds_attc_fil_nm;
	}
	public String getVou_rem_sbc() {
		return vou_rem_sbc;
	}
	public void setVou_rem_sbc(String vou_rem_sbc) {
		this.vou_rem_sbc = vou_rem_sbc;
	}
	public String getTot_amt() {
		return tot_amt;
	}
	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt;
	}
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getVendor_cd() {
		return vendor_cd;
	}
	public void setVendor_cd(String vendor_cd) {
		this.vendor_cd = vendor_cd;
	}
	public String getVendor_nm() {
		return vendor_nm;
	}
	public void setVendor_nm(String vendor_nm) {
		this.vendor_nm = vendor_nm;
	}
	public String getBudg_type() {
		return budg_type;
	}
	public void setBudg_type(String budg_type) {
		this.budg_type = budg_type;
	}
	public String getOwn_vendor_cd() {
		return own_vendor_cd;
	}
	public void setOwn_vendor_cd(String own_vendor_cd) {
		this.own_vendor_cd = own_vendor_cd;
	}
	public String getVou_vendor_cd() {
		return vou_vendor_cd;
	}
	public void setVou_vendor_cd(String vou_vendor_cd) {
		this.vou_vendor_cd = vou_vendor_cd;
	}
	public String getOwn_po_no() {
		return own_po_no;
	}
	public void setOwn_po_no(String own_po_no) {
		this.own_po_no = own_po_no;
	}
	public String getVou_po_no() {
		return vou_po_no;
	}
	public void setVou_po_no(String vou_po_no) {
		this.vou_po_no = vou_po_no;
	}
	public int getHid_seq() {
		return hid_seq;
	}
	public void setHid_seq(int hid_seq) {
		this.hid_seq = hid_seq;
	}
	public String getOwn_dtl_nm() {
		return own_dtl_nm;
	}
	public void setOwn_dtl_nm(String own_dtl_nm) {
		this.own_dtl_nm = own_dtl_nm;
	}
	public String getH_gubn() {
		return h_gubn;
	}
	public void setH_gubn(String h_gubn) {
		this.h_gubn = h_gubn;
	}
	public String getReq_eeno() {
		return req_eeno;
	}
	public void setReq_eeno(String req_eeno) {
		this.req_eeno = req_eeno;
	}
	public String getBudg_text() {
		return budg_text;
	}
	public void setBudg_text(String budg_text) {
		this.budg_text = budg_text;
	}
}
