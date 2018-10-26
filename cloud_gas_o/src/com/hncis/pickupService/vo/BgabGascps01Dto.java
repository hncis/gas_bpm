package com.hncis.pickupService.vo;

import com.hncis.common.vo.Common;

public class BgabGascps01Dto extends Common{

	private String doc_no           = "";
	private String eeno             = "";
	private String ptt_ymd          = "";
	private String cost_cd          = "";
	private String budg_no          = "";
	private String car_type_cd      = "";
	private String purp_sbc          = "";
	private String rem_sbc          = "";
	private String drvr_nm          = "";
	private String ccpc             = "";
	private String firm_cd          = "";
	private String grss_amt         = "";
	private String drvr_nm2         = "";
	private String ccpc2            = "";
	private String firm_cd2         = "";
	private String grss_amt2        = "";
	private String if_id            = "";
	private String snb_rson_sbc     = "";
	private String pgs_st_cd        = "";
	private String rdcs_eeno        = "";
	private String rdcs_ymd         = "";
	private String acpc_eeno        = "";
	private String acpc_ymd         = "";
	private String rpts_eeno        = "";
	private String rpts_ymd         = "";
	private String dept_cd          = "";
	private String inp_ymd          = "";
	private String ipe_eeno         = "";
	private String mdfy_ymd         = "";
	private String updr_eeno        = "";
	private String attc_fil_nm      = "";
	
	private String mode;
	private String errYn;
	private String errMsg;
	private String code = "";
	
	private String from_ymd			= "";
	private String to_ymd			= "";
	private String eeno_nm			= "";
	private String dept_nm			= "";
	private String pgs_st_cd_d      = "";
	private String pos_nm        	= "";
	private String stap_from_ymd    = "";
	private String stap_to_ymd      = "";
	private String stap_ymd      	= "";
	private String firm_nm			= "";
	private String invc_no			= "";
	private String stap_nm			= "";
	private String doc_ymd			= "";
	private String due_ymd			= "";
	private String arsl_refl_yn		= "";
	private String srl_no			= "";
	private String invc_no_h        = "";
	private String file_cnt			= "";
	
	private String po_no			= "";
	private String po_no_h			= "";
	private String bdgt_cd			= "";
	private String fst_yn			= "";
	
	private String acpc2_eeno       = "";
	private String acpc2_ymd        = "";
	private String acpc_pgs_st_cd   = "";
	private String acpc_type		= "";
	
	private String regn_cd			= "";
	private String tel_no			= "";
	
	private String cost_cd_h        = "";
	private String budg_no_h        = "";
	private String agree_flag       = "";
	private String dtl_cnt          = "";
	private String budg_type        = "";
	private String budg_type_h      = "";
	
	private String tot_amt	        = "";
	private String budg_text        = "";
	private String key_eenm			= "";
	
	private String locale 			= "";
	private String corp_cd 			= "";
	private int	seq					= 0;
	
	public String getCost_cd_h() {
		return cost_cd_h;
	}
	public void setCost_cd_h(String cost_cd_h) {
		this.cost_cd_h = cost_cd_h;
	}
	public String getBudg_no_h() {
		return budg_no_h;
	}
	public void setBudg_no_h(String budg_no_h) {
		this.budg_no_h = budg_no_h;
	}
	public String getBudg_no() {
		return budg_no;
	}
	public void setBudg_no(String budg_no) {
		this.budg_no = budg_no;
	}
	public String getRegn_cd() {
		return regn_cd;
	}
	public void setRegn_cd(String regn_cd) {
		this.regn_cd = regn_cd;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getAcpc2_eeno() {
		return acpc2_eeno;
	}
	public void setAcpc2_eeno(String acpc2_eeno) {
		this.acpc2_eeno = acpc2_eeno;
	}
	public String getAcpc2_ymd() {
		return acpc2_ymd;
	}
	public void setAcpc2_ymd(String acpc2_ymd) {
		this.acpc2_ymd = acpc2_ymd;
	}
	public String getAcpc_type() {
		return acpc_type;
	}
	public void setAcpc_type(String acpc_type) {
		this.acpc_type = acpc_type;
	}
	public String getAcpc_pgs_st_cd() {
		return acpc_pgs_st_cd;
	}
	public void setAcpc_pgs_st_cd(String acpc_pgs_st_cd) {
		this.acpc_pgs_st_cd = acpc_pgs_st_cd;
	}
	public String getFst_yn() {
		return fst_yn;
	}
	public void setFst_yn(String fst_yn) {
		this.fst_yn = fst_yn;
	}
	public String getBdgt_cd() {
		return bdgt_cd;
	}
	public void setBdgt_cd(String bdgt_cd) {
		this.bdgt_cd = bdgt_cd;
	}
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getPo_no_h() {
		return po_no_h;
	}
	public void setPo_no_h(String po_no_h) {
		this.po_no_h = po_no_h;
	}
	public String getFile_cnt() {
		return file_cnt;
	}
	public void setFile_cnt(String file_cnt) {
		this.file_cnt = file_cnt;
	}
	public String getInvc_no_h() {
		return invc_no_h;
	}
	public void setInvc_no_h(String invc_no_h) {
		this.invc_no_h = invc_no_h;
	}
	public String getSrl_no() {
		return srl_no;
	}
	public void setSrl_no(String srl_no) {
		this.srl_no = srl_no;
	}
	public String getArsl_refl_yn() {
		return arsl_refl_yn;
	}
	public void setArsl_refl_yn(String arsl_refl_yn) {
		this.arsl_refl_yn = arsl_refl_yn;
	}
	public String getDoc_ymd() {
		return doc_ymd;
	}
	public void setDoc_ymd(String doc_ymd) {
		this.doc_ymd = doc_ymd;
	}
	public String getDue_ymd() {
		return due_ymd;
	}
	public void setDue_ymd(String due_ymd) {
		this.due_ymd = due_ymd;
	}
	public String getStap_nm() {
		return stap_nm;
	}
	public void setStap_nm(String stap_nm) {
		this.stap_nm = stap_nm;
	}
	public String getInvc_no() {
		return invc_no;
	}
	public void setInvc_no(String invc_no) {
		this.invc_no = invc_no;
	}
	public String getFirm_nm() {
		return firm_nm;
	}
	public void setFirm_nm(String firm_nm) {
		this.firm_nm = firm_nm;
	}
	public String getStap_ymd() {
		return stap_ymd;
	}
	public void setStap_ymd(String stap_ymd) {
		this.stap_ymd = stap_ymd;
	}
	public String getStap_from_ymd() {
		return stap_from_ymd;
	}
	public void setStap_from_ymd(String stap_from_ymd) {
		this.stap_from_ymd = stap_from_ymd;
	}
	public String getStap_to_ymd() {
		return stap_to_ymd;
	}
	public void setStap_to_ymd(String stap_to_ymd) {
		this.stap_to_ymd = stap_to_ymd;
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
	public String getPtt_ymd() {
		return ptt_ymd;
	}
	public void setPtt_ymd(String ptt_ymd) {
		this.ptt_ymd = ptt_ymd;
	}
	public String getCost_cd() {
		return cost_cd;
	}
	public void setCost_cd(String cost_cd) {
		this.cost_cd = cost_cd;
	}
	public String getCar_type_cd() {
		return car_type_cd;
	}
	public void setCar_type_cd(String car_type_cd) {
		this.car_type_cd = car_type_cd;
	}
	public String getPurp_sbc() {
		return purp_sbc;
	}
	public void setPurp_sbc(String purp_sbc) {
		this.purp_sbc = purp_sbc;
	}
	public String getRem_sbc() {
		return rem_sbc;
	}
	public void setRem_sbc(String rem_sbc) {
		this.rem_sbc = rem_sbc;
	}
	public String getDrvr_nm() {
		return drvr_nm;
	}
	public void setDrvr_nm(String drvr_nm) {
		this.drvr_nm = drvr_nm;
	}
	public String getCcpc() {
		return ccpc;
	}
	public void setCcpc(String ccpc) {
		this.ccpc = ccpc;
	}
	public String getFirm_cd() {
		return firm_cd;
	}
	public void setFirm_cd(String firm_cd) {
		this.firm_cd = firm_cd;
	}
	public String getGrss_amt() {
		return grss_amt;
	}
	public void setGrss_amt(String grss_amt) {
		this.grss_amt = grss_amt;
	}
	public String getDrvr_nm2() {
		return drvr_nm2;
	}
	public void setDrvr_nm2(String drvr_nm2) {
		this.drvr_nm2 = drvr_nm2;
	}
	public String getCcpc2() {
		return ccpc2;
	}
	public void setCcpc2(String ccpc2) {
		this.ccpc2 = ccpc2;
	}
	public String getFirm_cd2() {
		return firm_cd2;
	}
	public void setFirm_cd2(String firm_cd2) {
		this.firm_cd2 = firm_cd2;
	}
	public String getGrss_amt2() {
		return grss_amt2;
	}
	public void setGrss_amt2(String grss_amt2) {
		this.grss_amt2 = grss_amt2;
	}
	public String getIf_id() {
		return if_id;
	}
	public void setIf_id(String if_id) {
		this.if_id = if_id;
	}
	public String getSnb_rson_sbc() {
		return snb_rson_sbc;
	}
	public void setSnb_rson_sbc(String snb_rson_sbc) {
		this.snb_rson_sbc = snb_rson_sbc;
	}
	public String getPgs_st_cd() {
		return pgs_st_cd;
	}
	public void setPgs_st_cd(String pgs_st_cd) {
		this.pgs_st_cd = pgs_st_cd;
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
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
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
	public String getAttc_fil_nm() {
		return attc_fil_nm;
	}
	public void setAttc_fil_nm(String attc_fil_nm) {
		this.attc_fil_nm = attc_fil_nm;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getErrYn() {
		return errYn;
	}
	public void setErrYn(String errYn) {
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
	public String getFrom_ymd() {
		return from_ymd;
	}
	public void setFrom_ymd(String from_ymd) {
		this.from_ymd = from_ymd;
	}
	public String getTo_ymd() {
		return to_ymd;
	}
	public void setTo_ymd(String to_ymd) {
		this.to_ymd = to_ymd;
	}
	public String getEeno_nm() {
		return eeno_nm;
	}
	public void setEeno_nm(String eeno_nm) {
		this.eeno_nm = eeno_nm;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getPgs_st_cd_d() {
		return pgs_st_cd_d;
	}
	public void setPgs_st_cd_d(String pgs_st_cd_d) {
		this.pgs_st_cd_d = pgs_st_cd_d;
	}
	public String getPos_nm() {
		return pos_nm;
	}
	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}
	public String getAgree_flag() {
		return agree_flag;
	}
	public void setAgree_flag(String agree_flag) {
		this.agree_flag = agree_flag;
	}
	public String getDtl_cnt() {
		return dtl_cnt;
	}
	public void setDtl_cnt(String dtl_cnt) {
		this.dtl_cnt = dtl_cnt;
	}
	public String getBudg_type() {
		return budg_type;
	}
	public void setBudg_type(String budg_type) {
		this.budg_type = budg_type;
	}
	public String getBudg_type_h() {
		return budg_type_h;
	}
	public void setBudg_type_h(String budg_type_h) {
		this.budg_type_h = budg_type_h;
	}
	public String getTot_amt() {
		return tot_amt;
	}
	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt;
	}
	public String getBudg_text() {
		return budg_text;
	}
	public void setBudg_text(String budg_text) {
		this.budg_text = budg_text;
	}
	public String getKey_eenm() {
		return key_eenm;
	}
	public void setKey_eenm(String key_eenm) {
		this.key_eenm = key_eenm;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getCorp_cd() {
		return corp_cd;
	}
	public void setCorp_cd(String corp_cd) {
		this.corp_cd = corp_cd;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
