package com.hncis.businessVehicles.vo;

import com.hncis.common.vo.Common;

public class BgabGascbv02Dto extends Common{
	
	private String doc_no             = "";
	private String eeno               = "";
	private String ptt_ymd            = "";
	private String chss_no            = "";
	private String drvr_eeno          = "";
	private String strt_ymd           = "";
	private String strt_tim           = "";
	private String fnh_ymd            = "";
	private String fnh_tim            = "";
	private String cro_purp_cd        = "";
	private String arvp_nm            = "";
	private String koil_crd_use_yn    = "";
	private String use_amt            = "";
	private String strt_trvg_dist     = "";
	private String fnh_trvg_dist      = "";
	private String rem_sbc            = "";
	private String if_id              = "";
	private String snb_rson_sbc       = "";
	private String pgs_st_cd          = "";
	private String rdcs_eeno          = "";
	private String rdcs_ymd           = "";
	private String acpc_eeno          = "";
	private String acpc_ymd           = "";
	private String rpts_eeno          = "";
	private String rpts_ymd           = "";
	private String inp_ymd            = "";
	private String ipe_eeno           = "";
	private String mdfy_ymd           = "";
	private String updr_eeno          = "";
	
	private String from_ymd 			= "";
	private String to_ymd 				= "";
	private String eeno_nm 				= "";
	private String dept_nm 				= "";
	private String dept_cd 				= "";
	private String pos_nm 				= "";
	private String pgs_st_cd_d          = "";
	private String tel_no               = "";
	
	private String car_no				= "";
	private String drvr_eeno_nm			= "";
	private String vehl_cd				= "";
	private String car_type_cd			= "";
	
	private String mode;
	private boolean errYn;
	private String errMsg;
	private String code = "";
	private String chmb_cror_agr_yn     = "";
	private String regn_cd 				= "";
	
	public String getChmb_cror_agr_yn() {
		return chmb_cror_agr_yn;
	}
	public void setChmb_cror_agr_yn(String chmb_cror_agr_yn) {
		this.chmb_cror_agr_yn = chmb_cror_agr_yn;
	}
	public String getVehl_cd() {
		return vehl_cd;
	}
	public void setVehl_cd(String vehl_cd) {
		this.vehl_cd = vehl_cd;
	}
	public String getCar_type_cd() {
		return car_type_cd;
	}
	public void setCar_type_cd(String car_type_cd) {
		this.car_type_cd = car_type_cd;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	public String getDrvr_eeno_nm() {
		return drvr_eeno_nm;
	}
	public void setDrvr_eeno_nm(String drvr_eeno_nm) {
		this.drvr_eeno_nm = drvr_eeno_nm;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
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
	public String getChss_no() {
		return chss_no;
	}
	public void setChss_no(String chss_no) {
		this.chss_no = chss_no;
	}
	public String getDrvr_eeno() {
		return drvr_eeno;
	}
	public void setDrvr_eeno(String drvr_eeno) {
		this.drvr_eeno = drvr_eeno;
	}
	public String getStrt_ymd() {
		return strt_ymd;
	}
	public void setStrt_ymd(String strt_ymd) {
		this.strt_ymd = strt_ymd;
	}
	public String getStrt_tim() {
		return strt_tim;
	}
	public void setStrt_tim(String strt_tim) {
		this.strt_tim = strt_tim;
	}
	public String getFnh_ymd() {
		return fnh_ymd;
	}
	public void setFnh_ymd(String fnh_ymd) {
		this.fnh_ymd = fnh_ymd;
	}
	public String getFnh_tim() {
		return fnh_tim;
	}
	public void setFnh_tim(String fnh_tim) {
		this.fnh_tim = fnh_tim;
	}
	public String getCro_purp_cd() {
		return cro_purp_cd;
	}
	public void setCro_purp_cd(String cro_purp_cd) {
		this.cro_purp_cd = cro_purp_cd;
	}
	public String getArvp_nm() {
		return arvp_nm;
	}
	public void setArvp_nm(String arvp_nm) {
		this.arvp_nm = arvp_nm;
	}
	public String getKoil_crd_use_yn() {
		return koil_crd_use_yn;
	}
	public void setKoil_crd_use_yn(String koil_crd_use_yn) {
		this.koil_crd_use_yn = koil_crd_use_yn;
	}
	public String getUse_amt() {
		return use_amt;
	}
	public void setUse_amt(String use_amt) {
		this.use_amt = use_amt;
	}
	public String getStrt_trvg_dist() {
		return strt_trvg_dist;
	}
	public void setStrt_trvg_dist(String strt_trvg_dist) {
		this.strt_trvg_dist = strt_trvg_dist;
	}
	public String getFnh_trvg_dist() {
		return fnh_trvg_dist;
	}
	public void setFnh_trvg_dist(String fnh_trvg_dist) {
		this.fnh_trvg_dist = fnh_trvg_dist;
	}
	public String getRem_sbc() {
		return rem_sbc;
	}
	public void setRem_sbc(String rem_sbc) {
		this.rem_sbc = rem_sbc;
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
	public String getPos_nm() {
		return pos_nm;
	}
	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}
	public String getPgs_st_cd_d() {
		return pgs_st_cd_d;
	}
	public void setPgs_st_cd_d(String pgs_st_cd_d) {
		this.pgs_st_cd_d = pgs_st_cd_d;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
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
	public String getRegn_cd() {
		return regn_cd;
	}
	public void setRegn_cd(String regn_cd) {
		this.regn_cd = regn_cd;
	}
}
