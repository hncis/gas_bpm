package com.hncis.businessTravel.vo;

import com.hncis.common.vo.Common;

public class CgabGascbtMealDto  extends Common{
	private String meal_doc_no      = "";
	private String meal_eeno        = "";
	private String meal_eenm        = "";
	private String meal_aply_eeno   = "";
	private String meal_ad_req_yn   = "";
	
	private String bztp_strt_ymd    = "";
	private String bztp_strt_ymd_temp    = "";
	private String bztp_strt_tras   = "";
	private String bztp_strt_ctms   = "";
	private String strt_trans_port  = "";
	private String bztp_fnh_ymd     = "";
	private String bztp_fnh_ymd_temp     = "";
	private String bztp_fnh_tras    = "";
	private String bztp_fnh_ctms    = "";
	private String fnh_trans_port   = "";
	
	private String strt_bor_cr_ymd  = "";
	private String strt_bor_cr_ctms = "";
	private String fnh_bor_cr_ymd   = "";
	private String fnh_bor_ch_ctms  = "";
	
	private String bztp_nm			= "";
	private int    amount			= 0;
	
	/**
	 * t1 domestic, t2 abroad, t3 abroad, t4 abroad, t5 domestic
	 */
	private String th1_day           = "";
	private String th2_day           = "";
	private String th3_day           = "";
	private String th4_day           = "";
	private String th5_day           = "";
	private String th1_hours         = "";
	private String th2_hours         = "";
	private String th3_hours         = "";
	private String th4_hours         = "";
	private String th5_hours         = "";
	private String th1_meal_bf       = "";
	private String th2_meal_bf       = "";
	private String th3_meal_bf       = "";
	private String th4_meal_bf       = "";
	private String th5_meal_bf       = "";
	private String th1_meal_exp_czk  = "";
	private String th1_meal_exp_eur  = "";
	private String th2_meal_exp_czk  = "";
	private String th2_meal_exp_eur  = "";
	private String th3_meal_exp_czk  = "";
	private String th3_meal_exp_eur  = "";
	private String th4_meal_exp_czk  = "";
	private String th4_meal_exp_eur  = "";
	private String th5_meal_exp_czk  = "";
	private String th5_meal_exp_eur  = "";
	private String th1_pocket_eur    = "";
	private String th2_pocket_eur    = "";
	private String th3_pocket_eur    = "";
	private String th4_pocket_eur    = "";
	private String th5_pocket_eur    = "";
	private String th1_trans_czk     = "";
	private String th1_trans_eur     = "";
	private String th1_trans_oth     = "";
	private String th2_trans_czk     = "";
	private String th2_trans_eur     = "";
	private String th2_trans_oth     = "";
	private String th3_trans_czk     = "";
	private String th3_trans_eur     = "";
	private String th3_trans_oth     = "";
	private String th4_trans_czk     = "";
	private String th4_trans_eur     = "";
	private String th4_trans_oth     = "";
	private String th5_trans_czk     = "";
	private String th5_trans_eur     = "";
	private String th5_trans_oth     = "";
	private String th1_accom_czk     = "";
	private String th1_accom_eur     = "";
	private String th1_accom_oth     = "";
	private String th2_accom_czk     = "";
	private String th2_accom_eur     = "";
	private String th2_accom_oth     = "";
	private String th3_accom_czk     = "";
	private String th3_accom_eur     = "";
	private String th3_accom_oth     = "";
	private String th4_accom_czk     = "";
	private String th4_accom_eur     = "";
	private String th4_accom_oth     = "";
	private String th5_accom_czk     = "";
	private String th5_accom_eur     = "";
	private String th5_accom_oth     = "";
	private String th1_total_czk     = "";
	private String th2_total_czk     = "";
	private String th3_total_czk     = "";
	private String th4_total_czk     = "";
	private String th5_total_czk     = "";
	private String th1_total_eur     = "";
	private String th2_total_eur     = "";
	private String th3_total_eur     = "";
	private String th4_total_eur     = "";
	private String th5_total_eur     = "";
	private String th1_total_dps     = "";
	private String th2_total_dps     = "";
	private String th3_total_dps     = "";
	private String th4_total_dps     = "";
	private String th5_total_dps     = "";
	private String th1_rate_bf   	 = "";
	private String th2_rate_bf   	 = "";
	private String th3_rate_bf   	 = "";
	private String th4_rate_bf   	 = "";
	private String th5_rate_bf   	 = "";
	private String th1_meal_ex_eur   = "";
	private String th2_meal_ex_eur   = "";
	private String th3_meal_ex_eur   = "";
	private String th4_meal_ex_eur   = "";
	private String th5_meal_ex_eur   = "";
	private String th1_meal_ex_czk   = "";
	private String th2_meal_ex_czk   = "";
	private String th3_meal_ex_czk   = "";
	private String th4_meal_ex_czk   = "";
	private String th5_meal_ex_czk   = "";
	private String deposit   		 = "";
	
	private String ipe_eeno          = "";
	private String inp_ymd           = "";
	private String updr_eeno         = "";
	private String updr_ymd          = "";
	private String sett_rem          = "";
	private String bztp_strt_regn          = "";
	private String apply_step_code      		= "";

	
	public String getApply_step_code() {
		return apply_step_code;
	}
	public void setApply_step_code(String applyStepCode) {
		apply_step_code = applyStepCode;
	}
	public String getBztp_strt_ymd_temp() {
		return bztp_strt_ymd_temp;
	}
	public void setBztp_strt_ymd_temp(String bztpStrtYmdTemp) {
		bztp_strt_ymd_temp = bztpStrtYmdTemp;
	}
	public String getBztp_fnh_ymd_temp() {
		return bztp_fnh_ymd_temp;
	}
	public void setBztp_fnh_ymd_temp(String bztpFnhYmdTemp) {
		bztp_fnh_ymd_temp = bztpFnhYmdTemp;
	}
	public String getBztp_strt_regn() {
		return bztp_strt_regn;
	}
	public void setBztp_strt_regn(String bztpStrtRegn) {
		bztp_strt_regn = bztpStrtRegn;
	}
	public String getSett_rem() {
		return sett_rem;
	}
	public void setSett_rem(String settRem) {
		sett_rem = settRem;
	}
	public String getMeal_eenm() {
		return meal_eenm;
	}
	public void setMeal_eenm(String mealEenm) {
		meal_eenm = mealEenm;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getTh1_meal_ex_eur() {
		return th1_meal_ex_eur;
	}
	public void setTh1_meal_ex_eur(String th1MealExEur) {
		th1_meal_ex_eur = th1MealExEur;
	}
	public String getTh2_meal_ex_eur() {
		return th2_meal_ex_eur;
	}
	public void setTh2_meal_ex_eur(String th2MealExEur) {
		th2_meal_ex_eur = th2MealExEur;
	}
	public String getTh3_meal_ex_eur() {
		return th3_meal_ex_eur;
	}
	public void setTh3_meal_ex_eur(String th3MealExEur) {
		th3_meal_ex_eur = th3MealExEur;
	}
	public String getTh4_meal_ex_eur() {
		return th4_meal_ex_eur;
	}
	public void setTh4_meal_ex_eur(String th4MealExEur) {
		th4_meal_ex_eur = th4MealExEur;
	}
	public String getTh5_meal_ex_eur() {
		return th5_meal_ex_eur;
	}
	public void setTh5_meal_ex_eur(String th5MealExEur) {
		th5_meal_ex_eur = th5MealExEur;
	}
	public String getTh1_meal_ex_czk() {
		return th1_meal_ex_czk;
	}
	public void setTh1_meal_ex_czk(String th1MealExCzk) {
		th1_meal_ex_czk = th1MealExCzk;
	}
	public String getTh2_meal_ex_czk() {
		return th2_meal_ex_czk;
	}
	public void setTh2_meal_ex_czk(String th2MealExCzk) {
		th2_meal_ex_czk = th2MealExCzk;
	}
	public String getTh3_meal_ex_czk() {
		return th3_meal_ex_czk;
	}
	public void setTh3_meal_ex_czk(String th3MealExCzk) {
		th3_meal_ex_czk = th3MealExCzk;
	}
	public String getTh4_meal_ex_czk() {
		return th4_meal_ex_czk;
	}
	public void setTh4_meal_ex_czk(String th4MealExCzk) {
		th4_meal_ex_czk = th4MealExCzk;
	}
	public String getTh5_meal_ex_czk() {
		return th5_meal_ex_czk;
	}
	public void setTh5_meal_ex_czk(String th5MealExCzk) {
		th5_meal_ex_czk = th5MealExCzk;
	}
	public String getTh1_rate_bf() {
		return th1_rate_bf;
	}
	public void setTh1_rate_bf(String th1RateBf) {
		th1_rate_bf = th1RateBf;
	}
	public String getTh2_rate_bf() {
		return th2_rate_bf;
	}
	public void setTh2_rate_bf(String th2RateBf) {
		th2_rate_bf = th2RateBf;
	}
	public String getTh3_rate_bf() {
		return th3_rate_bf;
	}
	public void setTh3_rate_bf(String th3RateBf) {
		th3_rate_bf = th3RateBf;
	}
	public String getTh4_rate_bf() {
		return th4_rate_bf;
	}
	public void setTh4_rate_bf(String th4RateBf) {
		th4_rate_bf = th4RateBf;
	}
	public String getTh5_rate_bf() {
		return th5_rate_bf;
	}
	public void setTh5_rate_bf(String th5RateBf) {
		th5_rate_bf = th5RateBf;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getBztp_nm() {
		return bztp_nm;
	}
	public void setBztp_nm(String bztpNm) {
		bztp_nm = bztpNm;
	}
	public String getMeal_doc_no() {
		return meal_doc_no;
	}
	public void setMeal_doc_no(String meal_doc_no) {
		this.meal_doc_no = meal_doc_no;
	}
	public String getMeal_eeno() {
		return meal_eeno;
	}
	public void setMeal_eeno(String meal_eeno) {
		this.meal_eeno = meal_eeno;
	}
	public String getMeal_aply_eeno() {
		return meal_aply_eeno;
	}
	public void setMeal_aply_eeno(String meal_aply_eeno) {
		this.meal_aply_eeno = meal_aply_eeno;
	}
	public String getMeal_ad_req_yn() {
		return meal_ad_req_yn;
	}
	public void setMeal_ad_req_yn(String meal_ad_req_yn) {
		this.meal_ad_req_yn = meal_ad_req_yn;
	}
	public String getBztp_strt_ymd() {
		return bztp_strt_ymd;
	}
	public void setBztp_strt_ymd(String bztp_strt_ymd) {
		this.bztp_strt_ymd = bztp_strt_ymd;
	}
	public String getBztp_strt_tras() {
		return bztp_strt_tras;
	}
	public void setBztp_strt_tras(String bztp_strt_tras) {
		this.bztp_strt_tras = bztp_strt_tras;
	}
	public String getBztp_strt_ctms() {
		return bztp_strt_ctms;
	}
	public void setBztp_strt_ctms(String bztp_strt_ctms) {
		this.bztp_strt_ctms = bztp_strt_ctms;
	}
	public String getStrt_trans_port() {
		return strt_trans_port;
	}
	public void setStrt_trans_port(String strt_trans_port) {
		this.strt_trans_port = strt_trans_port;
	}
	public String getBztp_fnh_ymd() {
		return bztp_fnh_ymd;
	}
	public void setBztp_fnh_ymd(String bztp_fnh_ymd) {
		this.bztp_fnh_ymd = bztp_fnh_ymd;
	}
	public String getBztp_fnh_tras() {
		return bztp_fnh_tras;
	}
	public void setBztp_fnh_tras(String bztp_fnh_tras) {
		this.bztp_fnh_tras = bztp_fnh_tras;
	}
	public String getBztp_fnh_ctms() {
		return bztp_fnh_ctms;
	}
	public void setBztp_fnh_ctms(String bztp_fnh_ctms) {
		this.bztp_fnh_ctms = bztp_fnh_ctms;
	}
	public String getFnh_trans_port() {
		return fnh_trans_port;
	}
	public void setFnh_trans_port(String fnh_trans_port) {
		this.fnh_trans_port = fnh_trans_port;
	}
	public String getStrt_bor_cr_ymd() {
		return strt_bor_cr_ymd;
	}
	public void setStrt_bor_cr_ymd(String strt_bor_cr_ymd) {
		this.strt_bor_cr_ymd = strt_bor_cr_ymd;
	}
	public String getStrt_bor_cr_ctms() {
		return strt_bor_cr_ctms;
	}
	public void setStrt_bor_cr_ctms(String strt_bor_cr_ctms) {
		this.strt_bor_cr_ctms = strt_bor_cr_ctms;
	}
	public String getFnh_bor_cr_ymd() {
		return fnh_bor_cr_ymd;
	}
	public void setFnh_bor_cr_ymd(String fnh_bor_cr_ymd) {
		this.fnh_bor_cr_ymd = fnh_bor_cr_ymd;
	}
	public String getFnh_bor_ch_ctms() {
		return fnh_bor_ch_ctms;
	}
	public void setFnh_bor_ch_ctms(String fnh_bor_ch_ctms) {
		this.fnh_bor_ch_ctms = fnh_bor_ch_ctms;
	}
	public String getTh1_day() {
		return th1_day;
	}
	public void setTh1_day(String th1_day) {
		this.th1_day = th1_day;
	}
	public String getTh2_day() {
		return th2_day;
	}
	public void setTh2_day(String th2_day) {
		this.th2_day = th2_day;
	}
	public String getTh3_day() {
		return th3_day;
	}
	public void setTh3_day(String th3_day) {
		this.th3_day = th3_day;
	}
	public String getTh4_day() {
		return th4_day;
	}
	public void setTh4_day(String th4_day) {
		this.th4_day = th4_day;
	}
	public String getTh5_day() {
		return th5_day;
	}
	public void setTh5_day(String th5_day) {
		this.th5_day = th5_day;
	}
	public String getTh1_hours() {
		return th1_hours;
	}
	public void setTh1_hours(String th1_hours) {
		this.th1_hours = th1_hours;
	}
	public String getTh2_hours() {
		return th2_hours;
	}
	public void setTh2_hours(String th2_hours) {
		this.th2_hours = th2_hours;
	}
	public String getTh3_hours() {
		return th3_hours;
	}
	public void setTh3_hours(String th3_hours) {
		this.th3_hours = th3_hours;
	}
	public String getTh4_hours() {
		return th4_hours;
	}
	public void setTh4_hours(String th4_hours) {
		this.th4_hours = th4_hours;
	}
	public String getTh5_hours() {
		return th5_hours;
	}
	public void setTh5_hours(String th5_hours) {
		this.th5_hours = th5_hours;
	}
	public String getTh1_meal_bf() {
		return th1_meal_bf;
	}
	public void setTh1_meal_bf(String th1_meal_bf) {
		this.th1_meal_bf = th1_meal_bf;
	}
	public String getTh2_meal_bf() {
		return th2_meal_bf;
	}
	public void setTh2_meal_bf(String th2_meal_bf) {
		this.th2_meal_bf = th2_meal_bf;
	}
	public String getTh3_meal_bf() {
		return th3_meal_bf;
	}
	public void setTh3_meal_bf(String th3_meal_bf) {
		this.th3_meal_bf = th3_meal_bf;
	}
	public String getTh4_meal_bf() {
		return th4_meal_bf;
	}
	public void setTh4_meal_bf(String th4_meal_bf) {
		this.th4_meal_bf = th4_meal_bf;
	}
	public String getTh5_meal_bf() {
		return th5_meal_bf;
	}
	public void setTh5_meal_bf(String th5_meal_bf) {
		this.th5_meal_bf = th5_meal_bf;
	}
	public String getTh1_meal_exp_czk() {
		return th1_meal_exp_czk;
	}
	public void setTh1_meal_exp_czk(String th1_meal_exp_czk) {
		this.th1_meal_exp_czk = th1_meal_exp_czk;
	}
	public String getTh1_meal_exp_eur() {
		return th1_meal_exp_eur;
	}
	public void setTh1_meal_exp_eur(String th1_meal_exp_eur) {
		this.th1_meal_exp_eur = th1_meal_exp_eur;
	}
	public String getTh2_meal_exp_czk() {
		return th2_meal_exp_czk;
	}
	public void setTh2_meal_exp_czk(String th2_meal_exp_czk) {
		this.th2_meal_exp_czk = th2_meal_exp_czk;
	}
	public String getTh2_meal_exp_eur() {
		return th2_meal_exp_eur;
	}
	public void setTh2_meal_exp_eur(String th2_meal_exp_eur) {
		this.th2_meal_exp_eur = th2_meal_exp_eur;
	}
	public String getTh3_meal_exp_czk() {
		return th3_meal_exp_czk;
	}
	public void setTh3_meal_exp_czk(String th3_meal_exp_czk) {
		this.th3_meal_exp_czk = th3_meal_exp_czk;
	}
	public String getTh3_meal_exp_eur() {
		return th3_meal_exp_eur;
	}
	public void setTh3_meal_exp_eur(String th3_meal_exp_eur) {
		this.th3_meal_exp_eur = th3_meal_exp_eur;
	}
	public String getTh4_meal_exp_czk() {
		return th4_meal_exp_czk;
	}
	public void setTh4_meal_exp_czk(String th4_meal_exp_czk) {
		this.th4_meal_exp_czk = th4_meal_exp_czk;
	}
	public String getTh4_meal_exp_eur() {
		return th4_meal_exp_eur;
	}
	public void setTh4_meal_exp_eur(String th4_meal_exp_eur) {
		this.th4_meal_exp_eur = th4_meal_exp_eur;
	}
	public String getTh5_meal_exp_czk() {
		return th5_meal_exp_czk;
	}
	public void setTh5_meal_exp_czk(String th5_meal_exp_czk) {
		this.th5_meal_exp_czk = th5_meal_exp_czk;
	}
	public String getTh5_meal_exp_eur() {
		return th5_meal_exp_eur;
	}
	public void setTh5_meal_exp_eur(String th5_meal_exp_eur) {
		this.th5_meal_exp_eur = th5_meal_exp_eur;
	}
	public String getTh1_pocket_eur() {
		return th1_pocket_eur;
	}
	public void setTh1_pocket_eur(String th1_pocket_eur) {
		this.th1_pocket_eur = th1_pocket_eur;
	}
	public String getTh2_pocket_eur() {
		return th2_pocket_eur;
	}
	public void setTh2_pocket_eur(String th2_pocket_eur) {
		this.th2_pocket_eur = th2_pocket_eur;
	}
	public String getTh3_pocket_eur() {
		return th3_pocket_eur;
	}
	public void setTh3_pocket_eur(String th3_pocket_eur) {
		this.th3_pocket_eur = th3_pocket_eur;
	}
	public String getTh4_pocket_eur() {
		return th4_pocket_eur;
	}
	public void setTh4_pocket_eur(String th4_pocket_eur) {
		this.th4_pocket_eur = th4_pocket_eur;
	}
	public String getTh5_pocket_eur() {
		return th5_pocket_eur;
	}
	public void setTh5_pocket_eur(String th5_pocket_eur) {
		this.th5_pocket_eur = th5_pocket_eur;
	}
	public String getTh1_trans_czk() {
		return th1_trans_czk;
	}
	public void setTh1_trans_czk(String th1_trans_czk) {
		this.th1_trans_czk = th1_trans_czk;
	}
	public String getTh1_trans_eur() {
		return th1_trans_eur;
	}
	public void setTh1_trans_eur(String th1_trans_eur) {
		this.th1_trans_eur = th1_trans_eur;
	}
	public String getTh1_trans_oth() {
		return th1_trans_oth;
	}
	public void setTh1_trans_oth(String th1_trans_oth) {
		this.th1_trans_oth = th1_trans_oth;
	}
	public String getTh2_trans_czk() {
		return th2_trans_czk;
	}
	public void setTh2_trans_czk(String th2_trans_czk) {
		this.th2_trans_czk = th2_trans_czk;
	}
	public String getTh2_trans_eur() {
		return th2_trans_eur;
	}
	public void setTh2_trans_eur(String th2_trans_eur) {
		this.th2_trans_eur = th2_trans_eur;
	}
	public String getTh2_trans_oth() {
		return th2_trans_oth;
	}
	public void setTh2_trans_oth(String th2_trans_oth) {
		this.th2_trans_oth = th2_trans_oth;
	}
	public String getTh3_trans_czk() {
		return th3_trans_czk;
	}
	public void setTh3_trans_czk(String th3_trans_czk) {
		this.th3_trans_czk = th3_trans_czk;
	}
	public String getTh3_trans_eur() {
		return th3_trans_eur;
	}
	public void setTh3_trans_eur(String th3_trans_eur) {
		this.th3_trans_eur = th3_trans_eur;
	}
	public String getTh3_trans_oth() {
		return th3_trans_oth;
	}
	public void setTh3_trans_oth(String th3_trans_oth) {
		this.th3_trans_oth = th3_trans_oth;
	}
	public String getTh4_trans_czk() {
		return th4_trans_czk;
	}
	public void setTh4_trans_czk(String th4_trans_czk) {
		this.th4_trans_czk = th4_trans_czk;
	}
	public String getTh4_trans_eur() {
		return th4_trans_eur;
	}
	public void setTh4_trans_eur(String th4_trans_eur) {
		this.th4_trans_eur = th4_trans_eur;
	}
	public String getTh4_trans_oth() {
		return th4_trans_oth;
	}
	public void setTh4_trans_oth(String th4_trans_oth) {
		this.th4_trans_oth = th4_trans_oth;
	}
	public String getTh5_trans_czk() {
		return th5_trans_czk;
	}
	public void setTh5_trans_czk(String th5_trans_czk) {
		this.th5_trans_czk = th5_trans_czk;
	}
	public String getTh5_trans_eur() {
		return th5_trans_eur;
	}
	public void setTh5_trans_eur(String th5_trans_eur) {
		this.th5_trans_eur = th5_trans_eur;
	}
	public String getTh5_trans_oth() {
		return th5_trans_oth;
	}
	public void setTh5_trans_oth(String th5_trans_oth) {
		this.th5_trans_oth = th5_trans_oth;
	}
	public String getTh1_accom_czk() {
		return th1_accom_czk;
	}
	public void setTh1_accom_czk(String th1_accom_czk) {
		this.th1_accom_czk = th1_accom_czk;
	}
	public String getTh1_accom_eur() {
		return th1_accom_eur;
	}
	public void setTh1_accom_eur(String th1_accom_eur) {
		this.th1_accom_eur = th1_accom_eur;
	}
	public String getTh1_accom_oth() {
		return th1_accom_oth;
	}
	public void setTh1_accom_oth(String th1_accom_oth) {
		this.th1_accom_oth = th1_accom_oth;
	}
	public String getTh2_accom_czk() {
		return th2_accom_czk;
	}
	public void setTh2_accom_czk(String th2_accom_czk) {
		this.th2_accom_czk = th2_accom_czk;
	}
	public String getTh2_accom_eur() {
		return th2_accom_eur;
	}
	public void setTh2_accom_eur(String th2_accom_eur) {
		this.th2_accom_eur = th2_accom_eur;
	}
	public String getTh2_accom_oth() {
		return th2_accom_oth;
	}
	public void setTh2_accom_oth(String th2_accom_oth) {
		this.th2_accom_oth = th2_accom_oth;
	}
	public String getTh3_accom_czk() {
		return th3_accom_czk;
	}
	public void setTh3_accom_czk(String th3_accom_czk) {
		this.th3_accom_czk = th3_accom_czk;
	}
	public String getTh3_accom_eur() {
		return th3_accom_eur;
	}
	public void setTh3_accom_eur(String th3_accom_eur) {
		this.th3_accom_eur = th3_accom_eur;
	}
	public String getTh3_accom_oth() {
		return th3_accom_oth;
	}
	public void setTh3_accom_oth(String th3_accom_oth) {
		this.th3_accom_oth = th3_accom_oth;
	}
	public String getTh4_accom_czk() {
		return th4_accom_czk;
	}
	public void setTh4_accom_czk(String th4_accom_czk) {
		this.th4_accom_czk = th4_accom_czk;
	}
	public String getTh4_accom_eur() {
		return th4_accom_eur;
	}
	public void setTh4_accom_eur(String th4_accom_eur) {
		this.th4_accom_eur = th4_accom_eur;
	}
	public String getTh4_accom_oth() {
		return th4_accom_oth;
	}
	public void setTh4_accom_oth(String th4_accom_oth) {
		this.th4_accom_oth = th4_accom_oth;
	}
	public String getTh5_accom_czk() {
		return th5_accom_czk;
	}
	public void setTh5_accom_czk(String th5_accom_czk) {
		this.th5_accom_czk = th5_accom_czk;
	}
	public String getTh5_accom_eur() {
		return th5_accom_eur;
	}
	public void setTh5_accom_eur(String th5_accom_eur) {
		this.th5_accom_eur = th5_accom_eur;
	}
	public String getTh5_accom_oth() {
		return th5_accom_oth;
	}
	public void setTh5_accom_oth(String th5_accom_oth) {
		this.th5_accom_oth = th5_accom_oth;
	}
	public String getTh1_total_czk() {
		return th1_total_czk;
	}
	public void setTh1_total_czk(String th1_total_czk) {
		this.th1_total_czk = th1_total_czk;
	}
	public String getTh2_total_czk() {
		return th2_total_czk;
	}
	public void setTh2_total_czk(String th2_total_czk) {
		this.th2_total_czk = th2_total_czk;
	}
	public String getTh3_total_czk() {
		return th3_total_czk;
	}
	public void setTh3_total_czk(String th3_total_czk) {
		this.th3_total_czk = th3_total_czk;
	}
	public String getTh4_total_czk() {
		return th4_total_czk;
	}
	public void setTh4_total_czk(String th4_total_czk) {
		this.th4_total_czk = th4_total_czk;
	}
	public String getTh5_total_czk() {
		return th5_total_czk;
	}
	public void setTh5_total_czk(String th5_total_czk) {
		this.th5_total_czk = th5_total_czk;
	}
	public String getTh1_total_eur() {
		return th1_total_eur;
	}
	public void setTh1_total_eur(String th1_total_eur) {
		this.th1_total_eur = th1_total_eur;
	}
	public String getTh2_total_eur() {
		return th2_total_eur;
	}
	public void setTh2_total_eur(String th2_total_eur) {
		this.th2_total_eur = th2_total_eur;
	}
	public String getTh3_total_eur() {
		return th3_total_eur;
	}
	public void setTh3_total_eur(String th3_total_eur) {
		this.th3_total_eur = th3_total_eur;
	}
	public String getTh4_total_eur() {
		return th4_total_eur;
	}
	public void setTh4_total_eur(String th4_total_eur) {
		this.th4_total_eur = th4_total_eur;
	}
	public String getTh5_total_eur() {
		return th5_total_eur;
	}
	public void setTh5_total_eur(String th5_total_eur) {
		this.th5_total_eur = th5_total_eur;
	}
	public String getTh1_total_dps() {
		return th1_total_dps;
	}
	public void setTh1_total_dps(String th1_total_dps) {
		this.th1_total_dps = th1_total_dps;
	}
	public String getTh2_total_dps() {
		return th2_total_dps;
	}
	public void setTh2_total_dps(String th2_total_dps) {
		this.th2_total_dps = th2_total_dps;
	}
	public String getTh3_total_dps() {
		return th3_total_dps;
	}
	public void setTh3_total_dps(String th3_total_dps) {
		this.th3_total_dps = th3_total_dps;
	}
	public String getTh4_total_dps() {
		return th4_total_dps;
	}
	public void setTh4_total_dps(String th4_total_dps) {
		this.th4_total_dps = th4_total_dps;
	}
	public String getTh5_total_dps() {
		return th5_total_dps;
	}
	public void setTh5_total_dps(String th5_total_dps) {
		this.th5_total_dps = th5_total_dps;
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
	public String getUpdr_ymd() {
		return updr_ymd;
	}
	public void setUpdr_ymd(String updr_ymd) {
		this.updr_ymd = updr_ymd;
	}
	
}