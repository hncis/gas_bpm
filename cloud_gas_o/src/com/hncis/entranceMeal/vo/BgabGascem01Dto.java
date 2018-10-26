package com.hncis.entranceMeal.vo;

import com.hncis.common.vo.Common;

public class BgabGascem01Dto extends Common{

	private String doc_no = "";
	private String eeno = "";
	private String ptt_ymd = "";
	private String vsit_purp_cd = "";
	private String vsit_purp_dtl_sbc = "";
	private String budg_no = "";
	private String snb_rson_sbc = "";
	private String fmeal_cset_yn = "";
	private String if_id = "";
	private String pgs_st_cd = "";
	private String pgs_st_cd_d = "";
	private String rdcs_eeno = "";
	private String rdcs_ymd = "";
	private String acpc_eeno = "";
	private String acpc_ymd = "";
	private String rpts_eeno = "";
	private String rpts_ymd = "";
	private String dcd = "";
	private String inp_ymd = "";
	private String ipe_eeno = "";
	private String mdfy_ymd = "";
	private String updr_eeno = "";
	
	private String mode;
	private boolean errYn;
	private String errMsg;
	private String code = "";
	
	private String from_ymd = "";
	private String to_ymd = "";
	private String eenm = "";
	private String dept_nm = "";
	private String dept_cd = "";
	private String meal_req_cnt = "";
	private String step_nm = "";
	private String pos_nm = "";
	private String eeno_nm = "";
	private String tel_no = "";
	
	private String vstr_seq = "";
	private String vstr_id = "";
	private String vstr_nm = "";
	private String strt_ymd = "";
	private String strt_time = "";
	private String fnh_ymd = "";
	private String ccpc_tn = "";
	private String tclg_sup_cd = "";
	private String fmeal_yn = "";
	private String ntbk_eon_yn = "";
	private String fmeal_yn_ex = "";
	private String ntbk_eon_yn_ex = "";
	private String vstr_cmpy_nm  = "";
	private String rem_flag = "";
	private String ntbk_eon_sbc = "";
	
	public String getVstr_cmpy_nm() {
		return vstr_cmpy_nm;
	}
	public void setVstr_cmpy_nm(String vstr_cmpy_nm) {
		this.vstr_cmpy_nm = vstr_cmpy_nm;
	}
	public String getFmeal_yn_ex() {
		return fmeal_yn_ex;
	}
	public void setFmeal_yn_ex(String fmeal_yn_ex) {
		this.fmeal_yn_ex = fmeal_yn_ex;
	}
	public String getNtbk_eon_yn_ex() {
		return ntbk_eon_yn_ex;
	}
	public void setNtbk_eon_yn_ex(String ntbk_eon_yn_ex) {
		this.ntbk_eon_yn_ex = ntbk_eon_yn_ex;
	}
	private String app_yn = "";
	
	public String getApp_yn() {
		return app_yn;
	}
	public void setApp_yn(String app_yn) {
		this.app_yn = app_yn;
	}
	public String getVstr_seq() {
		return vstr_seq;
	}
	public void setVstr_seq(String vstr_seq) {
		this.vstr_seq = vstr_seq;
	}
	public String getVstr_id() {
		return vstr_id;
	}
	public void setVstr_id(String vstr_id) {
		this.vstr_id = vstr_id;
	}
	public String getVstr_nm() {
		return vstr_nm;
	}
	public void setVstr_nm(String vstr_nm) {
		this.vstr_nm = vstr_nm;
	}
	public String getStrt_ymd() {
		return strt_ymd;
	}
	public void setStrt_ymd(String strt_ymd) {
		this.strt_ymd = strt_ymd;
	}
	public String getStrt_time() {
		return strt_time;
	}
	public void setStrt_time(String strt_time) {
		this.strt_time = strt_time;
	}
	public String getFnh_ymd() {
		return fnh_ymd;
	}
	public void setFnh_ymd(String fnh_ymd) {
		this.fnh_ymd = fnh_ymd;
	}
	public String getCcpc_tn() {
		return ccpc_tn;
	}
	public void setCcpc_tn(String ccpc_tn) {
		this.ccpc_tn = ccpc_tn;
	}
	public String getTclg_sup_cd() {
		return tclg_sup_cd;
	}
	public void setTclg_sup_cd(String tclg_sup_cd) {
		this.tclg_sup_cd = tclg_sup_cd;
	}
	public String getFmeal_yn() {
		return fmeal_yn;
	}
	public void setFmeal_yn(String fmeal_yn) {
		this.fmeal_yn = fmeal_yn;
	}
	public String getNtbk_eon_yn() {
		return ntbk_eon_yn;
	}
	public void setNtbk_eon_yn(String ntbk_eon_yn) {
		this.ntbk_eon_yn = ntbk_eon_yn;
	}
	public String getPos_nm() {
		return pos_nm;
	}
	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}
	public String getEeno_nm() {
		return eeno_nm;
	}
	public void setEeno_nm(String eeno_nm) {
		this.eeno_nm = eeno_nm;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFmeal_cset_yn() {
		return fmeal_cset_yn;
	}
	public void setFmeal_cset_yn(String fmeal_cset_yn) {
		this.fmeal_cset_yn = fmeal_cset_yn;
	}
	public String getPgs_st_cd_d() {
		return pgs_st_cd_d;
	}
	public void setPgs_st_cd_d(String pgs_st_cd_d) {
		this.pgs_st_cd_d = pgs_st_cd_d;
	}
	public String getStep_nm() {
		return step_nm;
	}
	public void setStep_nm(String step_nm) {
		this.step_nm = step_nm;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	public String getEenm() {
		return eenm;
	}
	public void setEenm(String eenm) {
		this.eenm = eenm;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getMeal_req_cnt() {
		return meal_req_cnt;
	}
	public void setMeal_req_cnt(String meal_req_cnt) {
		this.meal_req_cnt = meal_req_cnt;
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
	public String getVsit_purp_cd() {
		return vsit_purp_cd;
	}
	public void setVsit_purp_cd(String vsit_purp_cd) {
		this.vsit_purp_cd = vsit_purp_cd;
	}
	public String getVsit_purp_dtl_sbc() {
		return vsit_purp_dtl_sbc;
	}
	public void setVsit_purp_dtl_sbc(String vsit_purp_dtl_sbc) {
		this.vsit_purp_dtl_sbc = vsit_purp_dtl_sbc;
	}
	public String getBudg_no() {
		return budg_no;
	}
	public void setBudg_no(String budg_no) {
		this.budg_no = budg_no;
	}
	public String getSnb_rson_sbc() {
		return snb_rson_sbc;
	}
	public void setSnb_rson_sbc(String snb_rson_sbc) {
		this.snb_rson_sbc = snb_rson_sbc;
	}
	public String getIf_id() {
		return if_id;
	}
	public void setIf_id(String if_id) {
		this.if_id = if_id;
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
	public String getDcd() {
		return dcd;
	}
	public void setDcd(String dcd) {
		this.dcd = dcd;
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
	public String getRem_flag() {
		return rem_flag;
	}
	public void setRem_flag(String rem_flag) {
		this.rem_flag = rem_flag;
	}
	public String getNtbk_eon_sbc() {
		return ntbk_eon_sbc;
	}
	public void setNtbk_eon_sbc(String ntbk_eon_sbc) {
		this.ntbk_eon_sbc = ntbk_eon_sbc;
	}
}
