package com.hncis.roomsMeals.vo;

import com.hncis.common.vo.Common;

/**
 * @author baek
 *
 */
public class BgabGascrm01Dto  extends Common{
	
	private String doc_no           = "";
	private String room_plant       = "";
	private String room_place       = "";
	private String ptt_ymd          = "";
	private String fr_ymd           = "";
	private String to_ymd           = "";
	private String fr_time          = "";
	private String to_time          = "";
	private String tim_info_sbc     = "";
	private int room_qty         	= 0;
	private String room_meal        = "";
	private String cost_cd          = "";
	private String note             = "";
	private String inp_ymd          = "";
	private String ipe_eeno         = "";
	private String mdfy_ymd         = "";
	private String updr_eeno        = "";
	private String pgs_st_cd        = "";
	
	private String mode;
	private boolean errYn;
	private String errMsg;
	private String code = "";
	
	private String use_yn = "";
	private String col_value[];
	private String new_write_key = "";
	private String day_num = "";
	private String day_name = "";
	
	private String sply_ymd = "";
	
	
	private String tm0000_yn = "";
	private String tm0030_yn = "";
	private String tm0100_yn = "";
	private String tm0130_yn = "";
	private String tm0200_yn = "";
	private String tm0230_yn = "";
	private String tm0300_yn = "";
	private String tm0330_yn = "";
	private String tm0400_yn = "";
	private String tm0430_yn = "";
	private String tm0500_yn = "";
	private String tm0530_yn = "";
	private String tm0600_yn = "";
	private String tm0630_yn = "";
	private String tm0700_yn = "";
	private String tm0730_yn = "";
	private String tm0800_yn = "";
	private String tm0830_yn = "";
	private String tm0900_yn = "";
	private String tm0930_yn = "";
	private String tm1000_yn = "";
	private String tm1030_yn = "";
	private String tm1100_yn = "";
	private String tm1130_yn = "";
	private String tm1200_yn = "";
	private String tm1230_yn = "";
	private String tm1300_yn = "";
	private String tm1330_yn = "";
	private String tm1400_yn = "";
	private String tm1430_yn = "";
	private String tm1500_yn = "";
	private String tm1530_yn = "";
	private String tm1600_yn = "";
	private String tm1630_yn = "";
	private String tm1700_yn = "";
	private String tm1730_yn = "";
	private String tm1800_yn = "";
	private String tm1830_yn = "";
	private String tm1900_yn = "";
	private String tm1930_yn = "";
	private String tm2000_yn = "";
	private String tm2030_yn = "";
	private String tm2100_yn = "";
	private String tm2130_yn = "";
	private String tm2200_yn = "";
	private String tm2230_yn = "";
	private String tm2300_yn = "";
	private String tm2330_yn = "";
	private String tm2400_yn = "";
	
	private String tm0000 = "";
	private String tm0030 = "";
	private String tm0100 = "";
	private String tm0130 = "";
	private String tm0200 = "";
	private String tm0230 = "";
	private String tm0300 = "";
	private String tm0330 = "";
	private String tm0400 = "";
	private String tm0430 = "";
	private String tm0500 = "";
	private String tm0530 = "";
	private String tm0600 = "";
	private String tm0630 = "";
	private String tm0700 = "";
	private String tm0730 = "";
	private String tm0800 = "";
	private String tm0830 = "";
	private String tm0900 = "";
	private String tm0930 = "";
	private String tm1000 = "";
	private String tm1030 = "";
	private String tm1100 = "";
	private String tm1130 = "";
	private String tm1200 = "";
	private String tm1230 = "";
	private String tm1300 = "";
	private String tm1330 = "";
	private String tm1400 = "";
	private String tm1430 = "";
	private String tm1500 = "";
	private String tm1530 = "";
	private String tm1600 = "";
	private String tm1630 = "";
	private String tm1700 = "";
	private String tm1730 = "";
	private String tm1800 = "";
	private String tm1830 = "";
	private String tm1900 = "";
	private String tm1930 = "";
	private String tm2000 = "";
	private String tm2030 = "";
	private String tm2100 = "";
	private String tm2130 = "";
	private String tm2200 = "";
	private String tm2230 = "";
	private String tm2300 = "";
	private String tm2330 = "";
	private String tm2400 = "";
	
	private String room_code 			= "";
	private String vip_yn 				= "";
	
	private String req_eeno 			= "";
	private String req_eeno_nm 			= "";
	private String req_info 			= "";
	private String tot_amt 				= "";
	private String pgs_st_nm 			= "";
	private String req_dept_nm 			= "";
	private String req_step_nm 			= "";
	private String req_tel_no 			= "";
	private String req_dept_cd 			= "";
	
	private int btisSt;
	private int btisEd;
	
	private String fr_ymd_h           	= "";
	private String to_ymd_h           	= "";
	
	private String auth_type			= "";
	private String room_place_h 		= "";
	
	private String App_yn = "";
	
	private String if_id 			= "";
	private String rdcs_eeno 		= "";
	private String rdcs_ymd 		= "";
	private String acpc_eeno 		= "";
	private String acpc_ymd 		= "";
	private String rpts_eeno 		= "";
	private String rpts_ymd 		= "";
	private String snb_rson_sbc 	= "";
	private String pgs_st_cd2       = "";
	private String room_meal_h      = "";
	private String amount 			= "";
	private String budget_type 		= "";
	private String budget_no		= "";
	private String admin_id			= "";
	
	private String pgs_st_nm2 		= "";
	private String po_no	 		= "";
	private String eeno		 		= "";
	
	private String tot				= "";
	private String pop_img			= "";
	
	private String rsvt_day			= "";
	private String budg_text		= "";
	
	private String ptt_hhmm			= "";
	private String room_admin_flag	= "";
	private String chkFlag   		= "";
	private String loc_cd	   		= "";
	
	public String getRsvt_day() {
		return rsvt_day;
	}
	public void setRsvt_day(String rsvt_day) {
		this.rsvt_day = rsvt_day;
	}
	public String getPop_img() {
		return pop_img;
	}
	public void setPop_img(String pop_img) {
		this.pop_img = pop_img;
	}
	public String getTot() {
		return tot;
	}
	public void setTot(String tot) {
		this.tot = tot;
	}
	public String getTm0000_yn() {
		return tm0000_yn;
	}
	public void setTm0000_yn(String tm0000_yn) {
		this.tm0000_yn = tm0000_yn;
	}
	public String getTm0030_yn() {
		return tm0030_yn;
	}
	public void setTm0030_yn(String tm0030_yn) {
		this.tm0030_yn = tm0030_yn;
	}
	public String getTm0100_yn() {
		return tm0100_yn;
	}
	public void setTm0100_yn(String tm0100_yn) {
		this.tm0100_yn = tm0100_yn;
	}
	public String getTm0130_yn() {
		return tm0130_yn;
	}
	public void setTm0130_yn(String tm0130_yn) {
		this.tm0130_yn = tm0130_yn;
	}
	public String getTm0200_yn() {
		return tm0200_yn;
	}
	public void setTm0200_yn(String tm0200_yn) {
		this.tm0200_yn = tm0200_yn;
	}
	public String getTm0230_yn() {
		return tm0230_yn;
	}
	public void setTm0230_yn(String tm0230_yn) {
		this.tm0230_yn = tm0230_yn;
	}
	public String getTm0300_yn() {
		return tm0300_yn;
	}
	public void setTm0300_yn(String tm0300_yn) {
		this.tm0300_yn = tm0300_yn;
	}
	public String getTm0330_yn() {
		return tm0330_yn;
	}
	public void setTm0330_yn(String tm0330_yn) {
		this.tm0330_yn = tm0330_yn;
	}
	public String getTm0400_yn() {
		return tm0400_yn;
	}
	public void setTm0400_yn(String tm0400_yn) {
		this.tm0400_yn = tm0400_yn;
	}
	public String getTm0430_yn() {
		return tm0430_yn;
	}
	public void setTm0430_yn(String tm0430_yn) {
		this.tm0430_yn = tm0430_yn;
	}
	public String getTm0500_yn() {
		return tm0500_yn;
	}
	public void setTm0500_yn(String tm0500_yn) {
		this.tm0500_yn = tm0500_yn;
	}
	public String getTm0530_yn() {
		return tm0530_yn;
	}
	public void setTm0530_yn(String tm0530_yn) {
		this.tm0530_yn = tm0530_yn;
	}
	public String getTm0600_yn() {
		return tm0600_yn;
	}
	public void setTm0600_yn(String tm0600_yn) {
		this.tm0600_yn = tm0600_yn;
	}
	public String getTm0630_yn() {
		return tm0630_yn;
	}
	public void setTm0630_yn(String tm0630_yn) {
		this.tm0630_yn = tm0630_yn;
	}
	public String getTm2000_yn() {
		return tm2000_yn;
	}
	public void setTm2000_yn(String tm2000_yn) {
		this.tm2000_yn = tm2000_yn;
	}
	public String getTm2030_yn() {
		return tm2030_yn;
	}
	public void setTm2030_yn(String tm2030_yn) {
		this.tm2030_yn = tm2030_yn;
	}
	public String getTm2100_yn() {
		return tm2100_yn;
	}
	public void setTm2100_yn(String tm2100_yn) {
		this.tm2100_yn = tm2100_yn;
	}
	public String getTm2130_yn() {
		return tm2130_yn;
	}
	public void setTm2130_yn(String tm2130_yn) {
		this.tm2130_yn = tm2130_yn;
	}
	public String getTm2200_yn() {
		return tm2200_yn;
	}
	public void setTm2200_yn(String tm2200_yn) {
		this.tm2200_yn = tm2200_yn;
	}
	public String getTm2230_yn() {
		return tm2230_yn;
	}
	public void setTm2230_yn(String tm2230_yn) {
		this.tm2230_yn = tm2230_yn;
	}
	public String getTm2300_yn() {
		return tm2300_yn;
	}
	public void setTm2300_yn(String tm2300_yn) {
		this.tm2300_yn = tm2300_yn;
	}
	public String getTm2330_yn() {
		return tm2330_yn;
	}
	public void setTm2330_yn(String tm2330_yn) {
		this.tm2330_yn = tm2330_yn;
	}
	public String getTm2400_yn() {
		return tm2400_yn;
	}
	public void setTm2400_yn(String tm2400_yn) {
		this.tm2400_yn = tm2400_yn;
	}
	public String getTm0000() {
		return tm0000;
	}
	public void setTm0000(String tm0000) {
		this.tm0000 = tm0000;
	}
	public String getTm0030() {
		return tm0030;
	}
	public void setTm0030(String tm0030) {
		this.tm0030 = tm0030;
	}
	public String getTm0100() {
		return tm0100;
	}
	public void setTm0100(String tm0100) {
		this.tm0100 = tm0100;
	}
	public String getTm0130() {
		return tm0130;
	}
	public void setTm0130(String tm0130) {
		this.tm0130 = tm0130;
	}
	public String getTm0200() {
		return tm0200;
	}
	public void setTm0200(String tm0200) {
		this.tm0200 = tm0200;
	}
	public String getTm0230() {
		return tm0230;
	}
	public void setTm0230(String tm0230) {
		this.tm0230 = tm0230;
	}
	public String getTm0300() {
		return tm0300;
	}
	public void setTm0300(String tm0300) {
		this.tm0300 = tm0300;
	}
	public String getTm0330() {
		return tm0330;
	}
	public void setTm0330(String tm0330) {
		this.tm0330 = tm0330;
	}
	public String getTm0400() {
		return tm0400;
	}
	public void setTm0400(String tm0400) {
		this.tm0400 = tm0400;
	}
	public String getTm0430() {
		return tm0430;
	}
	public void setTm0430(String tm0430) {
		this.tm0430 = tm0430;
	}
	public String getTm0500() {
		return tm0500;
	}
	public void setTm0500(String tm0500) {
		this.tm0500 = tm0500;
	}
	public String getTm0530() {
		return tm0530;
	}
	public void setTm0530(String tm0530) {
		this.tm0530 = tm0530;
	}
	public String getTm0600() {
		return tm0600;
	}
	public void setTm0600(String tm0600) {
		this.tm0600 = tm0600;
	}
	public String getTm0630() {
		return tm0630;
	}
	public void setTm0630(String tm0630) {
		this.tm0630 = tm0630;
	}
	public String getTm2000() {
		return tm2000;
	}
	public void setTm2000(String tm2000) {
		this.tm2000 = tm2000;
	}
	public String getTm2030() {
		return tm2030;
	}
	public void setTm2030(String tm2030) {
		this.tm2030 = tm2030;
	}
	public String getTm2100() {
		return tm2100;
	}
	public void setTm2100(String tm2100) {
		this.tm2100 = tm2100;
	}
	public String getTm2130() {
		return tm2130;
	}
	public void setTm2130(String tm2130) {
		this.tm2130 = tm2130;
	}
	public String getTm2200() {
		return tm2200;
	}
	public void setTm2200(String tm2200) {
		this.tm2200 = tm2200;
	}
	public String getTm2230() {
		return tm2230;
	}
	public void setTm2230(String tm2230) {
		this.tm2230 = tm2230;
	}
	public String getTm2300() {
		return tm2300;
	}
	public void setTm2300(String tm2300) {
		this.tm2300 = tm2300;
	}
	public String getTm2330() {
		return tm2330;
	}
	public void setTm2330(String tm2330) {
		this.tm2330 = tm2330;
	}
	public String getTm2400() {
		return tm2400;
	}
	public void setTm2400(String tm2400) {
		this.tm2400 = tm2400;
	}
	public String getPgs_st_nm2() {
		return pgs_st_nm2;
	}
	public void setPgs_st_nm2(String pgs_st_nm2) {
		this.pgs_st_nm2 = pgs_st_nm2;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRoom_meal_h() {
		return room_meal_h;
	}
	public void setRoom_meal_h(String room_meal_h) {
		this.room_meal_h = room_meal_h;
	}
	public String getPgs_st_cd2() {
		return pgs_st_cd2;
	}
	public void setPgs_st_cd2(String pgs_st_cd2) {
		this.pgs_st_cd2 = pgs_st_cd2;
	}
	public String getSnb_rson_sbc() {
		return snb_rson_sbc;
	}
	public void setSnb_rson_sbc(String snb_rson_sbc) {
		this.snb_rson_sbc = snb_rson_sbc;
	}
	public String getApp_yn() {
		return App_yn;
	}
	public void setApp_yn(String app_yn) {
		App_yn = app_yn;
	}
	public String getIf_id() {
		return if_id;
	}
	public void setIf_id(String if_id) {
		this.if_id = if_id;
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
	public String getRoom_place_h() {
		return room_place_h;
	}
	public void setRoom_place_h(String room_place_h) {
		this.room_place_h = room_place_h;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	public String getFr_ymd_h() {
		return fr_ymd_h;
	}
	public void setFr_ymd_h(String fr_ymd_h) {
		this.fr_ymd_h = fr_ymd_h;
	}
	public String getTo_ymd_h() {
		return to_ymd_h;
	}
	public void setTo_ymd_h(String to_ymd_h) {
		this.to_ymd_h = to_ymd_h;
	}
	public String getReq_dept_cd() {
		return req_dept_cd;
	}
	public void setReq_dept_cd(String req_dept_cd) {
		this.req_dept_cd = req_dept_cd;
	}
	public int getBtisSt() {
		return btisSt;
	}
	public void setBtisSt(int btisSt) {
		this.btisSt = btisSt;
	}
	public int getBtisEd() {
		return btisEd;
	}
	public void setBtisEd(int btisEd) {
		this.btisEd = btisEd;
	}
	public String getReq_tel_no() {
		return req_tel_no;
	}
	public void setReq_tel_no(String req_tel_no) {
		this.req_tel_no = req_tel_no;
	}
	public String getReq_step_nm() {
		return req_step_nm;
	}
	public void setReq_step_nm(String req_step_nm) {
		this.req_step_nm = req_step_nm;
	}
	public String getReq_dept_nm() {
		return req_dept_nm;
	}
	public void setReq_dept_nm(String req_dept_nm) {
		this.req_dept_nm = req_dept_nm;
	}
	public String getReq_eeno_nm() {
		return req_eeno_nm;
	}
	public void setReq_eeno_nm(String req_eeno_nm) {
		this.req_eeno_nm = req_eeno_nm;
	}
	public String getPgs_st_nm() {
		return pgs_st_nm;
	}
	public void setPgs_st_nm(String pgs_st_nm) {
		this.pgs_st_nm = pgs_st_nm;
	}
	public String getTot_amt() {
		return tot_amt;
	}
	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt;
	}
	public String getReq_info() {
		return req_info;
	}
	public void setReq_info(String req_info) {
		this.req_info = req_info;
	}
	public String getReq_eeno() {
		return req_eeno;
	}
	public void setReq_eeno(String req_eeno) {
		this.req_eeno = req_eeno;
	}
	public String getRoom_code() {
		return room_code;
	}
	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}
	public String getVip_yn() {
		return vip_yn;
	}
	public void setVip_yn(String vip_yn) {
		this.vip_yn = vip_yn;
	}
	public String getSply_ymd() {
		return sply_ymd;
	}
	public void setSply_ymd(String sply_ymd) {
		this.sply_ymd = sply_ymd;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String[] getCol_value() {
		return col_value;
	}
	public void setCol_value(String[] col_value) {
		this.col_value = col_value;
	}
	public String getNew_write_key() {
		return new_write_key;
	}
	public void setNew_write_key(String new_write_key) {
		this.new_write_key = new_write_key;
	}
	public String getDay_num() {
		return day_num;
	}
	public void setDay_num(String day_num) {
		this.day_num = day_num;
	}
	public String getDay_name() {
		return day_name;
	}
	public void setDay_name(String day_name) {
		this.day_name = day_name;
	}
	public String getTm0700_yn() {
		return tm0700_yn;
	}
	public void setTm0700_yn(String tm0700_yn) {
		this.tm0700_yn = tm0700_yn;
	}
	public String getTm0730_yn() {
		return tm0730_yn;
	}
	public void setTm0730_yn(String tm0730_yn) {
		this.tm0730_yn = tm0730_yn;
	}
	public String getTm0800_yn() {
		return tm0800_yn;
	}
	public void setTm0800_yn(String tm0800_yn) {
		this.tm0800_yn = tm0800_yn;
	}
	public String getTm0830_yn() {
		return tm0830_yn;
	}
	public void setTm0830_yn(String tm0830_yn) {
		this.tm0830_yn = tm0830_yn;
	}
	public String getTm0900_yn() {
		return tm0900_yn;
	}
	public void setTm0900_yn(String tm0900_yn) {
		this.tm0900_yn = tm0900_yn;
	}
	public String getTm0930_yn() {
		return tm0930_yn;
	}
	public void setTm0930_yn(String tm0930_yn) {
		this.tm0930_yn = tm0930_yn;
	}
	public String getTm1000_yn() {
		return tm1000_yn;
	}
	public void setTm1000_yn(String tm1000_yn) {
		this.tm1000_yn = tm1000_yn;
	}
	public String getTm1030_yn() {
		return tm1030_yn;
	}
	public void setTm1030_yn(String tm1030_yn) {
		this.tm1030_yn = tm1030_yn;
	}
	public String getTm1100_yn() {
		return tm1100_yn;
	}
	public void setTm1100_yn(String tm1100_yn) {
		this.tm1100_yn = tm1100_yn;
	}
	public String getTm1130_yn() {
		return tm1130_yn;
	}
	public void setTm1130_yn(String tm1130_yn) {
		this.tm1130_yn = tm1130_yn;
	}
	public String getTm1200_yn() {
		return tm1200_yn;
	}
	public void setTm1200_yn(String tm1200_yn) {
		this.tm1200_yn = tm1200_yn;
	}
	public String getTm1230_yn() {
		return tm1230_yn;
	}
	public void setTm1230_yn(String tm1230_yn) {
		this.tm1230_yn = tm1230_yn;
	}
	public String getTm1300_yn() {
		return tm1300_yn;
	}
	public void setTm1300_yn(String tm1300_yn) {
		this.tm1300_yn = tm1300_yn;
	}
	public String getTm1330_yn() {
		return tm1330_yn;
	}
	public void setTm1330_yn(String tm1330_yn) {
		this.tm1330_yn = tm1330_yn;
	}
	public String getTm1400_yn() {
		return tm1400_yn;
	}
	public void setTm1400_yn(String tm1400_yn) {
		this.tm1400_yn = tm1400_yn;
	}
	public String getTm1430_yn() {
		return tm1430_yn;
	}
	public void setTm1430_yn(String tm1430_yn) {
		this.tm1430_yn = tm1430_yn;
	}
	public String getTm1500_yn() {
		return tm1500_yn;
	}
	public void setTm1500_yn(String tm1500_yn) {
		this.tm1500_yn = tm1500_yn;
	}
	public String getTm1530_yn() {
		return tm1530_yn;
	}
	public void setTm1530_yn(String tm1530_yn) {
		this.tm1530_yn = tm1530_yn;
	}
	public String getTm1600_yn() {
		return tm1600_yn;
	}
	public void setTm1600_yn(String tm1600_yn) {
		this.tm1600_yn = tm1600_yn;
	}
	public String getTm1630_yn() {
		return tm1630_yn;
	}
	public void setTm1630_yn(String tm1630_yn) {
		this.tm1630_yn = tm1630_yn;
	}
	public String getTm1700_yn() {
		return tm1700_yn;
	}
	public void setTm1700_yn(String tm1700_yn) {
		this.tm1700_yn = tm1700_yn;
	}
	public String getTm1730_yn() {
		return tm1730_yn;
	}
	public void setTm1730_yn(String tm1730_yn) {
		this.tm1730_yn = tm1730_yn;
	}
	public String getTm1800_yn() {
		return tm1800_yn;
	}
	public void setTm1800_yn(String tm1800_yn) {
		this.tm1800_yn = tm1800_yn;
	}
	public String getTm1830_yn() {
		return tm1830_yn;
	}
	public void setTm1830_yn(String tm1830_yn) {
		this.tm1830_yn = tm1830_yn;
	}
	public String getTm1900_yn() {
		return tm1900_yn;
	}
	public void setTm1900_yn(String tm1900_yn) {
		this.tm1900_yn = tm1900_yn;
	}
	public String getTm1930_yn() {
		return tm1930_yn;
	}
	public void setTm1930_yn(String tm1930_yn) {
		this.tm1930_yn = tm1930_yn;
	}
	public String getTm0700() {
		return tm0700;
	}
	public void setTm0700(String tm0700) {
		this.tm0700 = tm0700;
	}
	public String getTm0730() {
		return tm0730;
	}
	public void setTm0730(String tm0730) {
		this.tm0730 = tm0730;
	}
	public String getTm0800() {
		return tm0800;
	}
	public void setTm0800(String tm0800) {
		this.tm0800 = tm0800;
	}
	public String getTm0830() {
		return tm0830;
	}
	public void setTm0830(String tm0830) {
		this.tm0830 = tm0830;
	}
	public String getTm0900() {
		return tm0900;
	}
	public void setTm0900(String tm0900) {
		this.tm0900 = tm0900;
	}
	public String getTm0930() {
		return tm0930;
	}
	public void setTm0930(String tm0930) {
		this.tm0930 = tm0930;
	}
	public String getTm1000() {
		return tm1000;
	}
	public void setTm1000(String tm1000) {
		this.tm1000 = tm1000;
	}
	public String getTm1030() {
		return tm1030;
	}
	public void setTm1030(String tm1030) {
		this.tm1030 = tm1030;
	}
	public String getTm1100() {
		return tm1100;
	}
	public void setTm1100(String tm1100) {
		this.tm1100 = tm1100;
	}
	public String getTm1130() {
		return tm1130;
	}
	public void setTm1130(String tm1130) {
		this.tm1130 = tm1130;
	}
	public String getTm1200() {
		return tm1200;
	}
	public void setTm1200(String tm1200) {
		this.tm1200 = tm1200;
	}
	public String getTm1230() {
		return tm1230;
	}
	public void setTm1230(String tm1230) {
		this.tm1230 = tm1230;
	}
	public String getTm1300() {
		return tm1300;
	}
	public void setTm1300(String tm1300) {
		this.tm1300 = tm1300;
	}
	public String getTm1330() {
		return tm1330;
	}
	public void setTm1330(String tm1330) {
		this.tm1330 = tm1330;
	}
	public String getTm1400() {
		return tm1400;
	}
	public void setTm1400(String tm1400) {
		this.tm1400 = tm1400;
	}
	public String getTm1430() {
		return tm1430;
	}
	public void setTm1430(String tm1430) {
		this.tm1430 = tm1430;
	}
	public String getTm1500() {
		return tm1500;
	}
	public void setTm1500(String tm1500) {
		this.tm1500 = tm1500;
	}
	public String getTm1530() {
		return tm1530;
	}
	public void setTm1530(String tm1530) {
		this.tm1530 = tm1530;
	}
	public String getTm1600() {
		return tm1600;
	}
	public void setTm1600(String tm1600) {
		this.tm1600 = tm1600;
	}
	public String getTm1630() {
		return tm1630;
	}
	public void setTm1630(String tm1630) {
		this.tm1630 = tm1630;
	}
	public String getTm1700() {
		return tm1700;
	}
	public void setTm1700(String tm1700) {
		this.tm1700 = tm1700;
	}
	public String getTm1730() {
		return tm1730;
	}
	public void setTm1730(String tm1730) {
		this.tm1730 = tm1730;
	}
	public String getTm1800() {
		return tm1800;
	}
	public void setTm1800(String tm1800) {
		this.tm1800 = tm1800;
	}
	public String getTm1830() {
		return tm1830;
	}
	public void setTm1830(String tm1830) {
		this.tm1830 = tm1830;
	}
	public String getTm1900() {
		return tm1900;
	}
	public void setTm1900(String tm1900) {
		this.tm1900 = tm1900;
	}
	public String getTm1930() {
		return tm1930;
	}
	public void setTm1930(String tm1930) {
		this.tm1930 = tm1930;
	}
	public String getMdfy_ymd() {
		return mdfy_ymd;
	}
	public void setMdfy_ymd(String mdfy_ymd) {
		this.mdfy_ymd = mdfy_ymd;
	}
	public String getPgs_st_cd() {
		return pgs_st_cd;
	}
	public void setPgs_st_cd(String pgs_st_cd) {
		this.pgs_st_cd = pgs_st_cd;
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
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public String getRoom_plant() {
		return room_plant;
	}
	public void setRoom_plant(String room_plant) {
		this.room_plant = room_plant;
	}
	public String getRoom_place() {
		return room_place;
	}
	public void setRoom_place(String room_place) {
		this.room_place = room_place;
	}
	public String getPtt_ymd() {
		return ptt_ymd;
	}
	public void setPtt_ymd(String ptt_ymd) {
		this.ptt_ymd = ptt_ymd;
	}
	public String getFr_ymd() {
		return fr_ymd;
	}
	public void setFr_ymd(String fr_ymd) {
		this.fr_ymd = fr_ymd;
	}
	public String getTo_ymd() {
		return to_ymd;
	}
	public void setTo_ymd(String to_ymd) {
		this.to_ymd = to_ymd;
	}
	public String getFr_time() {
		return fr_time;
	}
	public void setFr_time(String fr_time) {
		this.fr_time = fr_time;
	}
	public String getTo_time() {
		return to_time;
	}
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	public String getTim_info_sbc() {
		return tim_info_sbc;
	}
	public void setTim_info_sbc(String tim_info_sbc) {
		this.tim_info_sbc = tim_info_sbc;
	}
	
	public int getRoom_qty() {
		return room_qty;
	}
	public void setRoom_qty(int room_qty) {
		this.room_qty = room_qty;
	}
	public String getRoom_meal() {
		return room_meal;
	}
	public void setRoom_meal(String room_meal) {
		this.room_meal = room_meal;
	}
	public String getCost_cd() {
		return cost_cd;
	}
	public void setCost_cd(String cost_cd) {
		this.cost_cd = cost_cd;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getUpdr_eeno() {
		return updr_eeno;
	}
	public void setUpdr_eeno(String updr_eeno) {
		this.updr_eeno = updr_eeno;
	}
	public String getBudget_type() {
		return budget_type;
	}
	public void setBudget_type(String budget_type) {
		this.budget_type = budget_type;
	}
	public String getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(String budget_no) {
		this.budget_no = budget_no;
	}
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getEeno() {
		return eeno;
	}
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}
	public String getBudg_text() {
		return budg_text;
	}
	public void setBudg_text(String budg_text) {
		this.budg_text = budg_text;
	}
	public String getPtt_hhmm() {
		return ptt_hhmm;
	}
	public void setPtt_hhmm(String ptt_hhmm) {
		this.ptt_hhmm = ptt_hhmm;
	}
	public String getRoom_admin_flag() {
		return room_admin_flag;
	}
	public void setRoom_admin_flag(String room_admin_flag) {
		this.room_admin_flag = room_admin_flag;
	}
	public String getChkFlag() {
		return chkFlag;
	}
	public void setChkFlag(String chkFlag) {
		this.chkFlag = chkFlag;
	}
	public String getLoc_cd() {
		return loc_cd;
	}
	public void setLoc_cd(String loc_cd) {
		this.loc_cd = loc_cd;
	}
}
