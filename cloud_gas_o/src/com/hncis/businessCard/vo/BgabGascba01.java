package com.hncis.businessCard.vo;

import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.Common;

public class BgabGascba01 extends Common{
	private String eeno         = "";
	private String req_date     = "";
	private String ops_cd       = "";
	private String req_empno    = "";
	private String tmlr_eeno    = "";
	private String mngr_dept    = "";
	private String mngr_empno   = "";
	private String rapr_date1   = "";
	private String rapr_date2   = "";
	private String mapr_date    = "";
	private String pgs_st_cd    = "";
	private String pgs_st_nm    = "";
	private String bc_knd       = "";
	private String bc_type      = "";
	private String bc_prt       = "";
	private String bcr_reasn    = "";
	private int qty             = 0;
	private double price        = 0;
	private double amt          = 0;
	private String acpt_date    = "";
	private String ord_date     = "";
	private String store_date   = "";
	private String if_id        = "";
	private String rq_imtr_sbc  = "";
	private String snb_rson_sbc = "";
	
	private String ee_nm        = "";
	private String ee_kr_nm     = "";
	private String ee_en_nm     = "";
	
	private String adr_knd      = "";
	private String bc_adr_knd   = "";
	private String odu_regn_cd   = "";
	private String zip          = "";
	private String pbz_adr      = "";
	private String pbz_kr_adr   = "";
	private String pbz_en_adr   = "";
	private String eml_adr      = "";
	private String dvsn_c       = "";
	private String dvsn_name    = "";
	private String dvsn_kname   = "";
	private String dvsn_ename   = "";
	private String ops_nm       = "";
	private String ops_kname    = "";
	private String ops_en_nm    = "";
	private String olv_cd       = "";
	private String olv_nm       = "";
	private String olv_knm      = "";
	private String olv_enm      = "";
	private String ofrm_tn      = "";
	private String numberer_tn  = "";
	private String fax_tn       = "";
	private String user_hp_no   = "";
	private String issue_ymd    = "";
	private String rpts_eeno     = "";
	private String rpts_ymd      = "";
	private String rdcs_eeno     = "";
	private String rdcs_ymd      = "";
	private String acpc_eeno     = "";
	private String acpc_ymd      = "";
	
	private String xins_empl_n2  = "";
	private String xins_name_m   = "";
	private String xins_orga_cagrg = "";
	private String xins_orga_eagrg = "";
	private String now_level    = "";
	
	private String inp_ymd      = "";
	private String ipe_eeno     = "";
	private String updr_ymd     = "";
	private String updr_eeno    = "";
	private String code    = "";
	
	private String xusr_empno = "";
	private String xusr_en_name = "";
	private String xusr_dept_code = "";
	private String xusr_dept_name = "";
	private String xusr_step_code = "";
	private String xusr_step_name = "";
	private String wireless_id = "";
	
	private String doc_no = "";
	private String po_no = "";
	private String cost_cd = "";
	private String bcr_name = "";
	
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String docNo) {
		doc_no = docNo;
	}
	public String getXusr_empno() {
		return xusr_empno;
	}
	public void setXusr_empno(String xusrEmpno) {
		xusr_empno = xusrEmpno;
	}
	public String getXusr_en_name() {
		return xusr_en_name;
	}
	public void setXusr_en_name(String xusrEnName) {
		xusr_en_name = xusrEnName;
	}
	public String getXusr_dept_code() {
		return xusr_dept_code;
	}
	public void setXusr_dept_code(String xusrDeptCode) {
		xusr_dept_code = xusrDeptCode;
	}
	public String getXusr_dept_name() {
		return xusr_dept_name;
	}
	public void setXusr_dept_name(String xusrDeptName) {
		xusr_dept_name = xusrDeptName;
	}
	public String getXusr_step_code() {
		return xusr_step_code;
	}
	public void setXusr_step_code(String xusrStepCode) {
		xusr_step_code = xusrStepCode;
	}
	public String getXusr_step_name() {
		return xusr_step_name;
	}
	public void setXusr_step_name(String xusrStepName) {
		xusr_step_name = xusrStepName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEeno() {
		return eeno;
	}
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	public String getOps_cd() {
		return ops_cd;
	}
	public void setOps_cd(String ops_cd) {
		this.ops_cd = ops_cd;
	}
	public String getReq_empno() {
		return req_empno;
	}
	public void setReq_empno(String req_empno) {
		this.req_empno = req_empno;
	}
	public String getTmlr_eeno() {
		return tmlr_eeno;
	}
	public void setTmlr_eeno(String tmlr_eeno) {
		this.tmlr_eeno = tmlr_eeno;
	}
	public String getMngr_dept() {
		return mngr_dept;
	}
	public void setMngr_dept(String mngr_dept) {
		this.mngr_dept = mngr_dept;
	}
	public String getMngr_empno() {
		return mngr_empno;
	}
	public void setMngr_empno(String mngr_empno) {
		this.mngr_empno = mngr_empno;
	}
	public String getRapr_date1() {
		return rapr_date1;
	}
	public void setRapr_date1(String rapr_date1) {
		this.rapr_date1 = rapr_date1;
	}
	public String getRapr_date2() {
		return rapr_date2;
	}
	public void setRapr_date2(String rapr_date2) {
		this.rapr_date2 = rapr_date2;
	}
	public String getMapr_date() {
		return mapr_date;
	}
	public void setMapr_date(String mapr_date) {
		this.mapr_date = mapr_date;
	}
	public String getPgs_st_cd() {
		return pgs_st_cd;
	}
	public void setPgs_st_cd(String pgs_st_cd) {
		this.pgs_st_cd = pgs_st_cd;
	}
	public String getPgs_st_nm() {
		return pgs_st_nm;
	}
	public void setPgs_st_nm(String pgs_st_nm) {
		this.pgs_st_nm = pgs_st_nm;
	}
	public String getBc_knd() {
		return bc_knd;
	}
	public void setBc_knd(String bc_knd) {
		this.bc_knd = bc_knd;
	}
	public String getBc_type() {
		return bc_type;
	}
	public void setBc_type(String bc_type) {
		this.bc_type = bc_type;
	}
	public String getBc_prt() {
		return bc_prt;
	}
	public void setBc_prt(String bc_prt) {
		this.bc_prt = bc_prt;
	}
	public String getBcr_reasn() {
		return bcr_reasn;
	}
	public void setBcr_reasn(String bcr_reasn) {
		this.bcr_reasn = bcr_reasn;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getAcpt_date() {
		return acpt_date;
	}
	public void setAcpt_date(String acpt_date) {
		this.acpt_date = acpt_date;
	}
	public String getOrd_date() {
		return ord_date;
	}
	public void setOrd_date(String ord_date) {
		this.ord_date = ord_date;
	}
	public String getStore_date() {
		return store_date;
	}
	public void setStore_date(String store_date ) {
		this.store_date = store_date;
	}
	public String getIf_id() {
		return if_id;
	}
	public void setIf_id(String if_id) {
		this.if_id = if_id;
	}
	public String getRq_imtr_sbc() {
		return StringUtil.uniDecode(rq_imtr_sbc);
	}
	public void setRq_imtr_sbc(String rq_imtr_sbc) {
		this.rq_imtr_sbc = rq_imtr_sbc;
	}
	public String getSnb_rson_sbc() {
		return snb_rson_sbc;
	}
	public void setSnb_rson_sbc(String snb_rson_sbc) {
		this.snb_rson_sbc = snb_rson_sbc;
	}
	public String getEe_nm() {
		return StringUtil.uniDecode(ee_nm);
	}
	public void setEe_nm(String ee_nm) {
		this.ee_nm = ee_nm;
	}
	public String getEe_kr_nm() {
		return StringUtil.uniDecode(ee_kr_nm);
	}
	public void setEe_kr_nm(String ee_kr_nm) {
		this.ee_kr_nm = ee_kr_nm;
	}
	public String getEe_en_nm() {
		return StringUtil.uniDecode(ee_en_nm);
	}
	public void setEe_en_nm(String ee_en_nm) {
		this.ee_en_nm = ee_en_nm;
	}
	public String getAdr_knd() {
		return adr_knd;
	}
	public void setAdr_knd(String adr_knd) {
		this.adr_knd = adr_knd;
	}
	public String getBc_adr_knd() {
		return bc_adr_knd;
	}
	public void setBc_adr_knd(String bc_adr_knd) {
		this.bc_adr_knd = bc_adr_knd;
	}
	public String getOdu_regn_cd() {
		return odu_regn_cd;
	}
	public void setOdu_regn_cd(String odu_reg_cd) {
		this.odu_regn_cd = odu_reg_cd;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPbz_adr() {
		return pbz_adr;
	}
	public void setPbz_adr(String pbz_adr) {
		this.pbz_adr = pbz_adr;
	}
	public String getPbz_kr_adr() {
		return pbz_kr_adr;
	}
	public void setPbz_kr_adr(String pbz_kr_adr) {
		this.pbz_kr_adr = pbz_kr_adr;
	}
	public String getPbz_en_adr() {
		return StringUtil.uniDecode(pbz_en_adr);
	}
	public void setPbz_en_adr(String pbz_en_adr) {
		this.pbz_en_adr = pbz_en_adr;
	}
	public String getEml_adr() {
		return eml_adr;
	}
	public void setEml_adr(String eml_adr) {
		this.eml_adr = eml_adr;
	}
	public String getDvsn_c() {
		return dvsn_c;
	}
	public void setDvsn_c(String dvsn_c) {
		this.dvsn_c = dvsn_c;
	}
	public String getDvsn_name() {
		return dvsn_name;
	}
	public void setDvsn_name(String dvsn_name) {
		this.dvsn_name = dvsn_name;
	}
	public String getDvsn_kname() {
		return dvsn_kname;
	}
	public void setDvsn_kname(String dvsn_kname) {
		this.dvsn_kname = dvsn_kname;
	}
	public String getDvsn_ename() {
		return dvsn_ename;
	}
	public void setDvsn_ename(String dvsn_ename) {
		this.dvsn_ename = dvsn_ename;
	}
	public String getOps_nm() {
		return ops_nm;
	}
	public void setOps_nm(String ops_nm) {
		this.ops_nm = ops_nm;
	}
	public String getOps_kname() {
		return ops_kname;
	}
	public void setOps_kname(String ops_kname) {
		this.ops_kname = ops_kname;
	}
	public String getOps_en_nm() {
		return ops_en_nm;
	}
	public void setOps_en_nm(String ops_en_nm) {
		this.ops_en_nm = ops_en_nm;
	}
	public String getOlv_cd() {
		return olv_cd;
	}
	public void setOlv_cd(String olv_cd) {
		this.olv_cd = olv_cd;
	}
	public String getOlv_nm() {
		return olv_nm;
	}
	public void setOlv_nm(String olv_nm) {
		this.olv_nm = olv_nm;
	}
	public String getOlv_knm() {
		return olv_knm;
	}
	public void setOlv_knm(String olv_knm) {
		this.olv_knm = olv_knm;
	}
	public String getOlv_enm() {
		return olv_enm;
	}
	public void setOlv_enm(String olv_enm) {
		this.olv_enm = olv_enm;
	}
	public String getOfrm_tn() {
		return ofrm_tn;
	}
	public void setOfrm_tn(String ofrm_tn) {
		this.ofrm_tn = ofrm_tn;
	}
	public String getNumberer_tn() {
		return numberer_tn;
	}
	public void setNumberer_tn(String numberer_tn) {
		this.numberer_tn = numberer_tn;
	}
	public String getFax_tn() {
		return fax_tn;
	}
	public void setFax_tn(String fax_tn) {
		this.fax_tn = fax_tn;
	}
	public String getUser_hp_no() {
		return user_hp_no;
	}
	public void setUser_hp_no(String user_hp_no) {
		this.user_hp_no = user_hp_no;
	}
	public String getIssue_ymd() {
		return issue_ymd;
	}
	public void setIssue_ymd(String issue_ymd) {
		this.issue_ymd = issue_ymd;
	}
	public String getRpts_eeno() {
		return rpts_eeno;
	}
	public void setRpts_eeno(String rpts_eeno) {
		this.rpts_eeno = rpts_eeno;
	}
	public String getRpts_ymd() {
		return rpts_ymd;
	}
	public void setRpts_ymd(String rpts_ymd) {
		this.rpts_ymd = rpts_ymd;
	}
	public String getRdcs_eeno() {
		return rdcs_eeno;
	}
	public void setRdcs_eeno(String rdcs_eeno) {
		this.rdcs_eeno = rdcs_eeno;
	}
	public String getRdcs_ymd() {
		return rdcs_ymd;
	}
	public void setRdcs_ymd(String rdcs_ymd) {
		this.rdcs_ymd = rdcs_ymd;
	}
	public String getAcpc_eeno() {
		return acpc_eeno;
	}
	public void setAcpc_eeno(String acpc_eeno) {
		this.acpc_eeno = acpc_eeno;
	}
	public String getAcpc_ymd() {
		return acpc_ymd;
	}
	public void setAcpc_ymd(String acpc_ymd) {
		this.acpc_ymd = acpc_ymd;
	}
	public String getXins_empl_n2() {
		return xins_empl_n2;
	}
	public void setXins_empl_n2(String xins_empl_n2) {
		this.xins_empl_n2 = xins_empl_n2;
	}
	public String getXins_name_m() {
		return xins_name_m;
	}
	public void setXins_name_m(String xins_name_m) {
		this.xins_name_m = xins_name_m;
	}
	public String getXins_orga_cagrg() {
		return xins_orga_cagrg;
	}
	public void setXins_orga_cagrg(String xins_orga_cagrg) {
		this.xins_orga_cagrg = xins_orga_cagrg;
	}
	public String getXins_orga_eagrg() {
		return xins_orga_eagrg;
	}
	public void setXins_orga_eagrg(String xins_orga_eagrg) {
		this.xins_orga_eagrg = xins_orga_eagrg;
	}
	public String getNow_level() {
		return now_level;
	}
	public void setNow_level(String now_level) {
		this.now_level = now_level;
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
	public String getUpdr_ymd() {
		return updr_ymd;
	}
	public void setUpdr_ymd(String updr_ymd) {
		this.updr_ymd = updr_ymd;
	}
	public String getUpdr_eeno() {
		return updr_eeno;
	}
	public void setUpdr_eeno(String updr_eeno) {
		this.updr_eeno = updr_eeno;
	}
	public String getWireless_id() {
		return wireless_id;
	}
	public void setWireless_id(String wireless_id) {
		this.wireless_id = wireless_id;
	}
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getCost_cd() {
		return cost_cd;
	}
	public void setCost_cd(String cost_cd) {
		this.cost_cd = cost_cd;
	}
	public String getBcr_name() {
		return bcr_name;
	}
	public void setBcr_name(String bcr_name) {
		this.bcr_name = bcr_name;
	}
}
