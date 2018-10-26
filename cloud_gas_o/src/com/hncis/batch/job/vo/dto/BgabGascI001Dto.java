package com.hncis.batch.job.vo.dto;

// TODO: Auto-generated Javadoc
/**
 * The Class BgabGascI001Dto. - 인터페이스 사원 Vo
 */
public class BgabGascI001Dto {
	
	/** The usn. 사번 */
	private String usn;
	
	/** The usr_nm. 사원명 */
	private String usr_nm;
	
	/** The dprtmt_cd. department 부서코드*/
	private String dprtmt_cd;
	
	/** The dprtmt_nm. department  부서명*/
	private String dprtmt_nm;
	
	/** The job_titl_cd. 직책코드*/
	private String job_titl_cd;
	
	/** The job_titl_nm. 직책명*/
	private String job_titl_nm;
	
	/** The email. 이메일*/
	private String email;
	
	/** The cmpny_cd. 회사코드*/
	private String cmpny_cd;
	
	/** The cmpny_nm. 회사명*/
	private String cmpny_nm;
	
	/** The home_phone_num. 집전화번호*/
	private String home_phone_num;
	
	/** The fax_num. 팩스번호*/
	private String fax_num;
	
	/** The mp_num. 핸드폰번호*/
	private String mp_num;
	
	/** The bp_num. 번호*/
	private String bp_num; //not usable column
	
	/** The wrkplc_cd. workplace 코드*/
	private String wrkplc_cd;
	
	/** The wrkplc_nm. workplace 명*/
	private String wrkplc_nm;
	
	/** The title_cd. 직급코드*/
	private String title_cd;
	
	/** The title_nm. 직급명*/
	private String title_nm;
	
	/** The title_lev. 직급레벨*/
	private String title_lev;
	
	/** The dpco_yn. */
	private String dpco_yn;
	
	/** The rebusi_nm. */
	private String rebusi_nm;
	
	/** The name_en. 영문명*/
	private String name_en;
	
	/** The dty_cd. 부서코드*/
	private String dty_cd;
	
	/** The daynnight_asrtmnt. -야간근무여부*/
	private String daynnight_asrtmnt;
	
	/** The pstn_cd. - 직급코드*/
	private String pstn_cd;
	
	/** The join_cmpny_date. - 입사일자*/
	private String join_cmpny_date;
	
	/** The employee_group. - employee_group. 기본값으로 Active로 되어 있음*/
	private String employee_group;
	
	/** The employee_sub_group. -employee_sub_group 기본값으로 Expatriate로 되어 있음*/
	private String employee_sub_group;
	
	/** The work_yn. 근무여부 */
	private String work_yn;
	
	/** The botm_sctr_nm. 부서명 */
	private String botm_sctr_nm;
	
	/** The botm_sctr_cd. 부서코드*/
	private String botm_sctr_cd;
	
	/** The cnfm_auth. 결재권한*/
	private String cnfm_auth;
	
	/** The up_dprtmt_cd. - 상위부서*/
	private String up_dprtmt_cd;
	
	/** The gender_cd. 성별*/
	private String gender_cd;
	
	private String cc_cd;
	private String cc_nm;
	
	private String street;
	private String house;
	private String aptmt;
	private String city;
	private String district;
	private String postal_code;
	private String work_phone_no;
	private String benefit_plan_cd;
	private String work_schedule_cd;
	private String benefit_plan_nm;
	private String work_schedule_nm;
	
	public String getCc_cd() {
		return cc_cd;
	}

	public void setCc_cd(String cc_cd) {
		this.cc_cd = cc_cd;
	}

	public String getCc_nm() {
		return cc_nm;
	}

	public void setCc_nm(String cc_nm) {
		this.cc_nm = cc_nm;
	}

	/**
	 * Gets the up_dprtmt_cd.
	 * 상위부서 가져옴
	 * @return the up_dprtmt_cd
	 */
	public String getUp_dprtmt_cd() {
		return up_dprtmt_cd;
	}
	
	/**
	 * Sets the up_dprtmt_cd.
	 * 상위부서 설정
	 * @param upDprtmtCd the new up_dprtmt_cd
	 */
	public void setUp_dprtmt_cd(String upDprtmtCd) {
		up_dprtmt_cd = upDprtmtCd;
	}
	
	/**
	 * Gets the cnfm_auth.
	 * 결재권한 가져옴
	 * @return the cnfm_auth
	 */
	public String getCnfm_auth() {
		return cnfm_auth;
	}
	
	/**
	 * Sets the cnfm_auth.
	 * 결재권한 설정
	 * @param cnfmAuth the new cnfm_auth
	 */
	public void setCnfm_auth(String cnfmAuth) {
		cnfm_auth = cnfmAuth;
	}
	
	/**
	 * Gets the usn.
	 * 사번 가져옴
	 * @return the usn
	 */
	public String getUsn() {
		return usn;
	}
	
	/**
	 * Sets the usn.
	 * 사번 설정
	 * @param usn the new usn
	 */
	public void setUsn(String usn) {
		this.usn = usn;
	}
	
	/**
	 * Gets the usr_nm.
	 * 사원명 가져옴
	 * @return the usr_nm
	 */
	public String getUsr_nm() {
		return usr_nm;
	}
	
	/**
	 * Sets the usr_nm.
	 * 사원명 설정
	 * @param usrNm the new usr_nm
	 */
	public void setUsr_nm(String usrNm) {
		usr_nm = usrNm;
	}
	
	/**
	 * Gets the dprtmt_cd.
	 * department 부서코드 가져옴
	 * @return the dprtmt_cd
	 */
	public String getDprtmt_cd() {
		return dprtmt_cd;
	}
	
	/**
	 * Sets the dprtmt_cd.
	 * department 부서코드 설정
	 * @param dprtmtCd the new dprtmt_cd
	 */
	public void setDprtmt_cd(String dprtmtCd) {
		dprtmt_cd = dprtmtCd;
	}
	
	/**
	 * Gets the dprtmt_nm.
	 * department  부서명 가져옴
	 * @return the dprtmt_nm
	 */
	public String getDprtmt_nm() {
		return dprtmt_nm;
	}
	
	/**
	 * Sets the dprtmt_nm.
	 * department  부서명 설정
	 * @param dprtmtNm the new dprtmt_nm
	 */
	public void setDprtmt_nm(String dprtmtNm) {
		dprtmt_nm = dprtmtNm;
	}
	
	/**
	 * Gets the job_titl_cd.
	 * 직책코드 가져옴
	 * @return the job_titl_cd
	 */
	public String getJob_titl_cd() {
		return job_titl_cd;
	}
	
	/**
	 * Sets the job_titl_cd.
	 * 직책코드 설정
	 * @param jobTitlCd the new job_titl_cd
	 */
	public void setJob_titl_cd(String jobTitlCd) {
		job_titl_cd = jobTitlCd;
	}
	
	/**
	 * Gets the job_titl_nm.
	 * 직책명 가져옴
	 * @return the job_titl_nm
	 */
	public String getJob_titl_nm() {
		return job_titl_nm;
	}
	
	/**
	 * Sets the job_titl_nm.
	 * 직책명 설정
	 * @param jobTitlNm the new job_titl_nm
	 */
	public void setJob_titl_nm(String jobTitlNm) {
		job_titl_nm = jobTitlNm;
	}
	
	/**
	 * Gets the email.
	 * 이메일 가져옴
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 * 이메일 설정
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the cmpny_cd.
	 * 회사코드 가져옴
	 * @return the cmpny_cd
	 */
	public String getCmpny_cd() {
		return cmpny_cd;
	}
	
	/**
	 * Sets the cmpny_cd.
	 * 회사코드 설정
	 * @param cmpnyCd the new cmpny_cd
	 */
	public void setCmpny_cd(String cmpnyCd) {
		cmpny_cd = cmpnyCd;
	}
	
	/**
	 * Gets the cmpny_nm.
	 * 회사명 가져옴
	 * @return the cmpny_nm
	 */
	public String getCmpny_nm() {
		return cmpny_nm;
	}
	
	/**
	 * Sets the cmpny_nm.
	 * 회사명 설정
	 * @param cmpnyNm the new cmpny_nm
	 */
	public void setCmpny_nm(String cmpnyNm) {
		cmpny_nm = cmpnyNm;
	}
	
	/**
	 * Gets the home_phone_num.
	 * 집전화번호 가져옴
	 * @return the home_phone_num
	 */
	public String getHome_phone_num() {
		return home_phone_num;
	}
	
	/**
	 * Sets the home_phone_num.
	 * 집전화번호 설정
	 * @param homePhoneNum the new home_phone_num
	 */
	public void setHome_phone_num(String homePhoneNum) {
		home_phone_num = homePhoneNum;
	}
	
	/**
	 * Gets the fax_num.
	 * 팩스번호 가져옴
	 * @return the fax_num
	 */
	public String getFax_num() {
		return fax_num;
	}
	
	/**
	 * Sets the fax_num.
	 * 팩스번호 설정
	 * @param faxNum the new fax_num
	 */
	public void setFax_num(String faxNum) {
		fax_num = faxNum;
	}
	
	/**
	 * Gets the mp_num.
	 * 핸드폰번호 가져옴
	 * @return the mp_num
	 */
	public String getMp_num() {
		return mp_num;
	}
	
	/**
	 * Sets the mp_num.
	 * 핸드폰번호 설정
	 * @param mpNum the new mp_num
	 */
	public void setMp_num(String mpNum) {
		mp_num = mpNum;
	}
	
	/**
	 * Gets the bp_num.
	 * 번호 가져옴
	 * @return the bp_num
	 */
	public String getBp_num() {
		return bp_num;
	}
	
	/**
	 * Sets the bp_num.
	 * 번호 설정
	 * @param bpNum the new bp_num
	 */
	public void setBp_num(String bpNum) {
		bp_num = bpNum;
	}
	
	/**
	 * Gets the wrkplc_cd.
	 * workplace 코드 가져옴
	 * @return the wrkplc_cd
	 */
	public String getWrkplc_cd() {
		return wrkplc_cd;
	}
	
	/**
	 * Sets the wrkplc_cd.
	 * workplace 코드 설정
	 * @param wrkplcCd the new wrkplc_cd
	 */
	public void setWrkplc_cd(String wrkplcCd) {
		wrkplc_cd = wrkplcCd;
	}
	
	/**
	 * Gets the wrkplc_nm.
	 * workplace 명 가져옴
	 * @return the wrkplc_nm
	 */
	public String getWrkplc_nm() {
		return wrkplc_nm;
	}
	
	/**
	 * Sets the wrkplc_nm.
	 * workplace 명 설정
	 * @param wrkplcNm the new wrkplc_nm
	 */
	public void setWrkplc_nm(String wrkplcNm) {
		wrkplc_nm = wrkplcNm;
	}
	
	/**
	 * Gets the title_cd.
	 * 직급코드 가져옴
	 * @return the title_cd
	 */
	public String getTitle_cd() {
		return title_cd;
	}
	
	/**
	 * Sets the title_cd.
	 * 직급코드 설정
	 * @param titleCd the new title_cd
	 */
	public void setTitle_cd(String titleCd) {
		title_cd = titleCd;
	}
	
	/**
	 * Gets the title_nm.
	 * 직급명 가져옴
	 * @return the title_nm
	 */
	public String getTitle_nm() {
		return title_nm;
	}
	
	/**
	 * Sets the title_nm.
	 * 직급명 설정
	 * @param titleNm the new title_nm
	 */
	public void setTitle_nm(String titleNm) {
		title_nm = titleNm;
	}
	
	/**
	 * Gets the title_lev.
	 * 직급레벨 가져옴
	 * @return the title_lev
	 */
	public String getTitle_lev() {
		return title_lev;
	}
	
	/**
	 * Sets the title_lev.
	 * 직급레벨 설정
	 * @param titleLev the new title_lev
	 */
	public void setTitle_lev(String titleLev) {
		title_lev = titleLev;
	}
	
	/**
	 * Gets the dpco_yn.
	 * 가져옴
	 * @return the dpco_yn
	 */
	public String getDpco_yn() {
		return dpco_yn;
	}
	
	/**
	 * Sets the dpco_yn.
	 * 설정
	 * @param dpcoYn the new dpco_yn
	 */
	public void setDpco_yn(String dpcoYn) {
		dpco_yn = dpcoYn;
	}
	
	/**
	 * Gets the rebusi_nm.
	 * 가져옴
	 * @return the rebusi_nm
	 */
	public String getRebusi_nm() {
		return rebusi_nm;
	}
	
	/**
	 * Sets the rebusi_nm.
	 * 설정
	 * @param rebusiNm the new rebusi_nm
	 */
	public void setRebusi_nm(String rebusiNm) {
		rebusi_nm = rebusiNm;
	}
	
	/**
	 * Gets the name_en.
	 * 영문명 가져옴
	 * @return the name_en
	 */
	public String getName_en() {
		return name_en;
	}
	
	/**
	 * Sets the name_en.
	 * 영문명 설정
	 * @param nameEn the new name_en
	 */
	public void setName_en(String nameEn) {
		name_en = nameEn;
	}
	
	/**
	 * Gets the dty_cd.
	 * 부서코드 가져옴
	 * @return the dty_cd
	 */
	public String getDty_cd() {
		return dty_cd;
	}
	
	/**
	 * Sets the dty_cd.
	 * 부서코드 설정
	 * @param dtyCd the new dty_cd
	 */
	public void setDty_cd(String dtyCd) {
		dty_cd = dtyCd;
	}
	
	/**
	 * Gets the daynnight_asrtmnt.
	 * 야간근무여부 가져옴
	 * @return the daynnight_asrtmnt
	 */
	public String getDaynnight_asrtmnt() {
		return daynnight_asrtmnt;
	}
	
	/**
	 * Sets the daynnight_asrtmnt.
	 * 야간근무여부 설정
	 * @param daynnightAsrtmnt the new daynnight_asrtmnt
	 */
	public void setDaynnight_asrtmnt(String daynnightAsrtmnt) {
		daynnight_asrtmnt = daynnightAsrtmnt;
	}
	
	/**
	 * Gets the pstn_cd.
	 * 직급코드 가져옴
	 * @return the pstn_cd
	 */
	public String getPstn_cd() {
		return pstn_cd;
	}
	
	/**
	 * Sets the pstn_cd.
	 * 직급코드 설정
	 * @param pstnCd the new pstn_cd
	 */
	public void setPstn_cd(String pstnCd) {
		pstn_cd = pstnCd;
	}
	
	/**
	 * Gets the join_cmpny_date.
	 * 입사일자 가져옴
	 * @return the join_cmpny_date
	 */
	public String getJoin_cmpny_date() {
		return join_cmpny_date;
	}
	
	/**
	 * Sets the join_cmpny_date.
	 * 입사일자 설정
	 * @param joinCmpnyDate the new join_cmpny_date
	 */
	public void setJoin_cmpny_date(String joinCmpnyDate) {
		join_cmpny_date = joinCmpnyDate;
	}
	
	/**
	 * Gets the employee_group.
	 * employee_group 가져옴
	 * @return the employee_group
	 */
	public String getEmployee_group() {
		return employee_group;
	}
	
	/**
	 * Sets the employee_group.
	 * employee_group 설정
	 * @param employeeGroup the new employee_group
	 */
	public void setEmployee_group(String employeeGroup) {
		employee_group = employeeGroup;
	}
	
	/**
	 * Gets the employee_sub_group.
	 * employee_sub_group 가져옴
	 * @return the employee_sub_group
	 */
	public String getEmployee_sub_group() {
		return employee_sub_group;
	}
	
	/**
	 * Sets the employee_sub_group.
	 * employee_sub_group 설정
	 * @param employeeSubGroup the new employee_sub_group
	 */
	public void setEmployee_sub_group(String employeeSubGroup) {
		employee_sub_group = employeeSubGroup;
	}
	
	/**
	 * Gets the work_yn.
	 * 근무여부 가져옴
	 * @return the work_yn
	 */
	public String getWork_yn() {
		return work_yn;
	}
	
	/**
	 * Sets the work_yn.
	 * 근무여부 설정
	 * @param workYn the new work_yn
	 */
	public void setWork_yn(String workYn) {
		work_yn = workYn;
	}
	
	/**
	 * Gets the botm_sctr_nm.
	 * 부서명 가져옴
	 * @return the botm_sctr_nm
	 */
	public String getBotm_sctr_nm() {
		return botm_sctr_nm;
	}
	
	/**
	 * Sets the botm_sctr_nm.
	 * 부서명 설정
	 * @param botmSctrNm the new botm_sctr_nm
	 */
	public void setBotm_sctr_nm(String botmSctrNm) {
		botm_sctr_nm = botmSctrNm;
	}
	
	/**
	 * Gets the botm_sctr_cd.
	 * 부서코드 가져옴
	 * @return the botm_sctr_cd
	 */
	public String getBotm_sctr_cd() {
		return botm_sctr_cd;
	}
	
	/**
	 * Sets the botm_sctr_cd.
	 * 부서코드 설정
	 * @param botmSctrCd the new botm_sctr_cd
	 */
	public void setBotm_sctr_cd(String botmSctrCd) {
		botm_sctr_cd = botmSctrCd;
	}
	
	/**
	 * Gets the gender_cd.
	 * 성별 가져옴
	 * @return the gender_cd
	 */
	public String getGender_cd() {
		return gender_cd;
	}
	
	/**
	 * Sets the gender_cd.
	 * 성별 설정
	 * @param gender_cd the new gender_cd
	 */
	public void setGender_cd(String gender_cd) {
		this.gender_cd = gender_cd;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getAptmt() {
		return aptmt;
	}

	public void setAptmt(String aptmt) {
		this.aptmt = aptmt;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getWork_phone_no() {
		return work_phone_no;
	}

	public void setWork_phone_no(String work_phone_no) {
		this.work_phone_no = work_phone_no;
	}

	public String getBenefit_plan_cd() {
		return benefit_plan_cd;
	}

	public void setBenefit_plan_cd(String benefit_plan_cd) {
		this.benefit_plan_cd = benefit_plan_cd;
	}

	public String getWork_schedule_cd() {
		return work_schedule_cd;
	}

	public void setWork_schedule_cd(String work_schedule_cd) {
		this.work_schedule_cd = work_schedule_cd;
	}

	public String getBenefit_plan_nm() {
		return benefit_plan_nm;
	}

	public void setBenefit_plan_nm(String benefit_plan_nm) {
		this.benefit_plan_nm = benefit_plan_nm;
	}

	public String getWork_schedule_nm() {
		return work_schedule_nm;
	}

	public void setWork_schedule_nm(String work_schedule_nm) {
		this.work_schedule_nm = work_schedule_nm;
	}

	
}
