package com.hncis.common.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BgabGascz002Dto. -사용자 정보 value object
 */
public class BgabGascz002Dto extends Common implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1830327130658426182L;

	/** The xusr_empno. 사번*/
	private String xusr_empno        ;

	/** The xusr_auth_knd. 권한*/
	private String xusr_auth_knd     ;

	/** The xusr_name. 성명*/
	private String xusr_name         ;

	/** The xusr_en_name. 영어성명*/
	private String xusr_en_name      ;

	/** The xusr_gnrl_area. 지역*/
	private String xusr_gnrl_area    ;

	/** The xusr_plac_work. 지역*/
	private String xusr_plac_work    ;

	/** The xusr_plac_work_nm. 이역명 */
	private String xusr_plac_work_nm ;

	/** The xusr_bsns_dept. 상위부서*/
	private String xusr_bsns_dept    ;

	/** The xusr_dept_code. 부서코드*/
	private String xusr_dept_code    ;

	/** The xusr_dept_name. 부서이름*/
	private String xusr_dept_name    ;

	/** The xusr_step_code. 직급코드*/
	private String xusr_step_code    ;

	/** The xusr_step_name. 직급명*/
	private String xusr_step_name    ;

	/** The xusr_dept_knd. 부서종류*/
	private String xusr_dept_knd     ;

	/** The xusr_cnfm_auth. 부서장여부*/
	private String xusr_cnfm_auth    ;

	/** The xusr_all_auth. */
	private String xusr_all_auth     ;

	/** The xusr_work_auth. 업무권한*/
	private String xusr_work_auth    ;

	/** The xusr_aply_flag. */
	private String xusr_aply_flag    ;

	/** The xusr_aply_date. */
	private String xusr_aply_date    ;

	/** The xusr_pswd. */
	private String xusr_pswd         ;

	/** The xusr_pswd_date. */
	private String xusr_pswd_date    ;

	/** The xusr_bsns_dept2. */
	private String xusr_bsns_dept2   ;

	/** The xusr_bsns_dept3. */
	private String xusr_bsns_dept3   ;

	/** The xusr_dept_code2. */
	private String xusr_dept_code2   ;

	/** The xusr_dept_code3. */
	private String xusr_dept_code3   ;

	/** The xusr_retr_flag. 은퇴여부*/
	private String xusr_retr_flag    ;

	/** The xusr_tel_no. 전화번호 */
	private String xusr_tel_no       ;

	/** The xusr_entr_empno. 입력자사번*/
	private String xusr_entr_empno   ;

	/** The xusr_updt_empno. 수정자사번*/
	private String xusr_updt_empno   ;

	/** The xusr_entr_date. 입력일자*/
	private String xusr_entr_date    ;

	/** The xusr_updt_date. 수정일자*/
	private String xusr_updt_date    ;

	/** The xusr_bsns_dept_nm. */
	private String xusr_bsns_dept_nm;

	/** The xusr_dept_code2_nm. */
	private String xusr_dept_code2_nm;

	/** The xusr_dept_code3_nm. */
	private String xusr_dept_code3_nm;

	/** The xusr_bsns_dept2_nm. */
	private String xusr_bsns_dept2_nm;

	/** The xusr_bsns_dept3_nm. */
	private String xusr_bsns_dept3_nm;

	/** The xusr_old_auth. */
	private String xusr_old_auth    ;

	/** The xusr_new_auth. */
	private String xusr_new_auth    ;

	/** The xusr_dsm_yn. */
	private String xusr_dsm_yn 		;

	/** The xawy_hpno. */
	private String xawy_hpno		;

	/** The is_xgac. */
	private String is_xgac		;

	/** The login_type. */
	private String login_type		;

	/** The start point. */
	private Long startPoint = 0L;

	/** The end point. */
	private Long endPoint = 0L;

	/** The xusr_dept_nm_dept. */
	private String xusr_dept_nm_dept;

	/** The xusr_dept_nm_cd. */
	private String xusr_dept_nm_cd;

	/** The xusr_cmpny_date. 입사일자*/
	private String xusr_cmpny_date; //company join date - new column

	/** The xusr_sex_cd. 성별*/
	private String xusr_sex_cd; //user sex(male, female)- new column

	private String xusr_cost_cd;
	private String xusr_cost_nm;
	private String xusr_mail_adr;

	private String xusr_token_key;

	private String xusr_street;
	private String xusr_house;
	private String xusr_aptmt;
	private String xusr_city;
	private String xusr_district;
	private String xusr_postal_code;
	private String xusr_work_phone_no;
	private String xusr_benefit_plan_cd;
	private String xusr_work_schedule_cd;
	private String xusr_benefit_plan_nm;
	private String xusr_work_schedule_nm;
	private String xusr_addr;
	private String xusr_sex;
	private String xusr_pswd_key;
	private String xusr_loc;
	private String corp_cd;
	private String adm_corp_cd;
	
	public String getXusr_pswd_key() {
		return xusr_pswd_key;
	}

	public void setXusr_pswd_key(String xusr_pswd_key) {
		this.xusr_pswd_key = xusr_pswd_key;
	}

	public String getXusr_token_key() {
		return xusr_token_key;
	}

	public void setXusr_token_key(String xusr_token_key) {
		this.xusr_token_key = xusr_token_key;
	}

	public String getXusr_mail_adr() {
		return xusr_mail_adr;
	}

	public void setXusr_mail_adr(String xusr_mail_adr) {
		this.xusr_mail_adr = xusr_mail_adr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getXusr_cost_nm() {
		return xusr_cost_nm;
	}

	public void setXusr_cost_nm(String xusr_cost_nm) {
		this.xusr_cost_nm = xusr_cost_nm;
	}

	public String getXusr_cost_cd() {
		return xusr_cost_cd;
	}

	public void setXusr_cost_cd(String xusr_cost_cd) {
		this.xusr_cost_cd = xusr_cost_cd;
	}

	/**
	 * Gets the xusr_dept_nm_cd.
	 *
	 * @return the xusr_dept_nm_cd
	 */
	public String getXusr_dept_nm_cd() {
		return xusr_dept_nm_cd;
	}

	/**
	 * Sets the xusr_dept_nm_cd.
	 *
	 * @param xusrDeptNmCd the new xusr_dept_nm_cd
	 */
	public void setXusr_dept_nm_cd(String xusrDeptNmCd) {
		xusr_dept_nm_cd = xusrDeptNmCd;
	}

	/**
	 * Gets the xusr_dept_nm_dept.
	 *
	 * @return the xusr_dept_nm_dept
	 */
	public String getXusr_dept_nm_dept() {
		return xusr_dept_nm_dept;
	}

	/**
	 * Sets the xusr_dept_nm_dept.
	 *
	 * @param xusrDeptNmDept the new xusr_dept_nm_dept
	 */
	public void setXusr_dept_nm_dept(String xusrDeptNmDept) {
		xusr_dept_nm_dept = xusrDeptNmDept;
	}

	/**
	 * Gets the xusr_en_name.
	 *
	 * @return the xusr_en_name
	 */
	public String getXusr_en_name() {
		return xusr_en_name;
	}

	/**
	 * Sets the xusr_en_name.
	 *
	 * @param xusrEnName the new xusr_en_name
	 */
	public void setXusr_en_name(String xusrEnName) {
		xusr_en_name = xusrEnName;
	}

	/**
	 * Gets the xusr_dsm_yn.
	 *
	 * @return the xusr_dsm_yn
	 */
	public String getXusr_dsm_yn() {
		return xusr_dsm_yn;
	}

	/**
	 * Sets the xusr_dsm_yn.
	 *
	 * @param xusrDsmYn the new xusr_dsm_yn
	 */
	public void setXusr_dsm_yn(String xusrDsmYn) {
		xusr_dsm_yn = xusrDsmYn;
	}

	/**
	 * Gets the login_type.
	 *
	 * @return the login_type
	 */
	public String getLogin_type() {
		return login_type;
	}

	/**
	 * Sets the login_type.
	 *
	 * @param loginType the new login_type
	 */
	public void setLogin_type(String loginType) {
		login_type = loginType;
	}

	/**
	 * Gets the is_xgac.
	 *
	 * @return the is_xgac
	 */
	public String getIs_xgac() {
		return is_xgac;
	}

	/**
	 * Sets the is_xgac.
	 *
	 * @param is_xgac the new is_xgac
	 */
	public void setIs_xgac(String is_xgac) {
		this.is_xgac = is_xgac;
	}

	/**
	 * Gets the xawy_hpno.
	 *
	 * @return the xawy_hpno
	 */
	public String getXawy_hpno() {
		return xawy_hpno;
	}

	/**
	 * Sets the xawy_hpno.
	 *
	 * @param xawyHpno the new xawy_hpno
	 */
	public void setXawy_hpno(String xawyHpno) {
		xawy_hpno = xawyHpno;
	}

	/**
	 * Gets the xusr_old_auth.
	 *
	 * @return the xusr_old_auth
	 */
	public String getXusr_old_auth() {
		return xusr_old_auth;
	}

	/**
	 * Sets the xusr_old_auth.
	 *
	 * @param xusrOldAuth the new xusr_old_auth
	 */
	public void setXusr_old_auth(String xusrOldAuth) {
		xusr_old_auth = xusrOldAuth;
	}

	/**
	 * Gets the xusr_new_auth.
	 *
	 * @return the xusr_new_auth
	 */
	public String getXusr_new_auth() {
		return xusr_new_auth;
	}

	/**
	 * Sets the xusr_new_auth.
	 *
	 * @param xusrNewAuth the new xusr_new_auth
	 */
	public void setXusr_new_auth(String xusrNewAuth) {
		xusr_new_auth = xusrNewAuth;
	}

	/**
	 * Gets the xusr_dept_code3_nm.
	 *
	 * @return the xusr_dept_code3_nm
	 */
	public String getXusr_dept_code3_nm() {
		return xusr_dept_code3_nm;
	}

	/**
	 * Sets the xusr_dept_code3_nm.
	 *
	 * @param xusrDeptCode3Nm the new xusr_dept_code3_nm
	 */
	public void setXusr_dept_code3_nm(String xusrDeptCode3Nm) {
		xusr_dept_code3_nm = xusrDeptCode3Nm;
	}

	/**
	 * Gets the xusr_bsns_dept3_nm.
	 *
	 * @return the xusr_bsns_dept3_nm
	 */
	public String getXusr_bsns_dept3_nm() {
		return xusr_bsns_dept3_nm;
	}

	/**
	 * Sets the xusr_bsns_dept3_nm.
	 *
	 * @param xusrBsnsDept3Nm the new xusr_bsns_dept3_nm
	 */
	public void setXusr_bsns_dept3_nm(String xusrBsnsDept3Nm) {
		xusr_bsns_dept3_nm = xusrBsnsDept3Nm;
	}

	/**
	 * Gets the xusr_bsns_dept2_nm.
	 *
	 * @return the xusr_bsns_dept2_nm
	 */
	public String getXusr_bsns_dept2_nm() {
		return xusr_bsns_dept2_nm;
	}

	/**
	 * Sets the xusr_bsns_dept2_nm.
	 *
	 * @param xusrBsnsDept2Nm the new xusr_bsns_dept2_nm
	 */
	public void setXusr_bsns_dept2_nm(String xusrBsnsDept2Nm) {
		xusr_bsns_dept2_nm = xusrBsnsDept2Nm;
	}

	/**
	 * Gets the xusr_dept_code2_nm.
	 *
	 * @return the xusr_dept_code2_nm
	 */
	public String getXusr_dept_code2_nm() {
		return xusr_dept_code2_nm;
	}

	/**
	 * Sets the xusr_dept_code2_nm.
	 *
	 * @param xusrDeptCode2Nm the new xusr_dept_code2_nm
	 */
	public void setXusr_dept_code2_nm(String xusrDeptCode2Nm) {
		xusr_dept_code2_nm = xusrDeptCode2Nm;
	}

	/**
	 * Gets the xusr_bsns_dept_nm.
	 *
	 * @return the xusr_bsns_dept_nm
	 */
	public String getXusr_bsns_dept_nm() {
		return xusr_bsns_dept_nm;
	}

	/**
	 * Sets the xusr_bsns_dept_nm.
	 *
	 * @param xusrBsnsDeptNm the new xusr_bsns_dept_nm
	 */
	public void setXusr_bsns_dept_nm(String xusrBsnsDeptNm) {
		xusr_bsns_dept_nm = xusrBsnsDeptNm;
	}

	/**
	 * Gets the xusr_empno.
	 *
	 * @return the xusr_empno
	 */
	public String getXusr_empno() {
		return xusr_empno;
	}

	/**
	 * Sets the xusr_empno.
	 *
	 * @param xusrEmpno the new xusr_empno
	 */
	public void setXusr_empno(String xusrEmpno) {
		xusr_empno = xusrEmpno;
	}

	/**
	 * Gets the xusr_auth_knd.
	 *
	 * @return the xusr_auth_knd
	 */
	public String getXusr_auth_knd() {
		return xusr_auth_knd;
	}

	/**
	 * Sets the xusr_auth_knd.
	 *
	 * @param xusrAuthKnd the new xusr_auth_knd
	 */
	public void setXusr_auth_knd(String xusrAuthKnd) {
		xusr_auth_knd = xusrAuthKnd;
	}

	/**
	 * Gets the xusr_name.
	 *
	 * @return the xusr_name
	 */
	public String getXusr_name() {
		return xusr_name;
	}

	/**
	 * Sets the xusr_name.
	 *
	 * @param xusrName the new xusr_name
	 */
	public void setXusr_name(String xusrName) {
		xusr_name = xusrName;
	}

	/**
	 * Gets the xusr_gnrl_area.
	 *
	 * @return the xusr_gnrl_area
	 */
	public String getXusr_gnrl_area() {
		return xusr_gnrl_area;
	}

	/**
	 * Sets the xusr_gnrl_area.
	 *
	 * @param xusrGnrlArea the new xusr_gnrl_area
	 */
	public void setXusr_gnrl_area(String xusrGnrlArea) {
		xusr_gnrl_area = xusrGnrlArea;
	}

	/**
	 * Gets the xusr_plac_work.
	 *
	 * @return the xusr_plac_work
	 */
	public String getXusr_plac_work() {
		return xusr_plac_work;
	}

	/**
	 * Sets the xusr_plac_work.
	 *
	 * @param xusrPlacWork the new xusr_plac_work
	 */
	public void setXusr_plac_work(String xusrPlacWork) {
		xusr_plac_work = xusrPlacWork;
	}

	/**
	 * Gets the xusr_bsns_dept.
	 *
	 * @return the xusr_bsns_dept
	 */
	public String getXusr_bsns_dept() {
		return xusr_bsns_dept;
	}

	/**
	 * Sets the xusr_bsns_dept.
	 *
	 * @param xusrBsnsDept the new xusr_bsns_dept
	 */
	public void setXusr_bsns_dept(String xusrBsnsDept) {
		xusr_bsns_dept = xusrBsnsDept;
	}

	/**
	 * Gets the xusr_dept_code.
	 *
	 * @return the xusr_dept_code
	 */
	public String getXusr_dept_code() {
		return xusr_dept_code;
	}

	/**
	 * Sets the xusr_dept_code.
	 *
	 * @param xusrDeptCode the new xusr_dept_code
	 */
	public void setXusr_dept_code(String xusrDeptCode) {
		xusr_dept_code = xusrDeptCode;
	}

	/**
	 * Gets the xusr_dept_name.
	 *
	 * @return the xusr_dept_name
	 */
	public String getXusr_dept_name() {
		return xusr_dept_name;
	}

	/**
	 * Sets the xusr_dept_name.
	 *
	 * @param xusrDeptName the new xusr_dept_name
	 */
	public void setXusr_dept_name(String xusrDeptName) {
		xusr_dept_name = xusrDeptName;
	}

	/**
	 * Gets the xusr_step_code.
	 *
	 * @return the xusr_step_code
	 */
	public String getXusr_step_code() {
		return xusr_step_code;
	}

	/**
	 * Sets the xusr_step_code.
	 *
	 * @param xusrStepCode the new xusr_step_code
	 */
	public void setXusr_step_code(String xusrStepCode) {
		xusr_step_code = xusrStepCode;
	}

	/**
	 * Gets the xusr_step_name.
	 *
	 * @return the xusr_step_name
	 */
	public String getXusr_step_name() {
		return xusr_step_name;
	}

	/**
	 * Sets the xusr_step_name.
	 *
	 * @param xusrStepName the new xusr_step_name
	 */
	public void setXusr_step_name(String xusrStepName) {
		xusr_step_name = xusrStepName;
	}

	/**
	 * Gets the xusr_dept_knd.
	 *
	 * @return the xusr_dept_knd
	 */
	public String getXusr_dept_knd() {
		return xusr_dept_knd;
	}

	/**
	 * Sets the xusr_dept_knd.
	 *
	 * @param xusrDeptKnd the new xusr_dept_knd
	 */
	public void setXusr_dept_knd(String xusrDeptKnd) {
		xusr_dept_knd = xusrDeptKnd;
	}

	/**
	 * Gets the xusr_cnfm_auth.
	 *
	 * @return the xusr_cnfm_auth
	 */
	public String getXusr_cnfm_auth() {
		return xusr_cnfm_auth;
	}

	/**
	 * Sets the xusr_cnfm_auth.
	 *
	 * @param xusrCnfmAuth the new xusr_cnfm_auth
	 */
	public void setXusr_cnfm_auth(String xusrCnfmAuth) {
		xusr_cnfm_auth = xusrCnfmAuth;
	}

	/**
	 * Gets the xusr_all_auth.
	 *
	 * @return the xusr_all_auth
	 */
	public String getXusr_all_auth() {
		return xusr_all_auth;
	}

	/**
	 * Sets the xusr_all_auth.
	 *
	 * @param xusrAllAuth the new xusr_all_auth
	 */
	public void setXusr_all_auth(String xusrAllAuth) {
		xusr_all_auth = xusrAllAuth;
	}

	/**
	 * Gets the xusr_work_auth.
	 *
	 * @return the xusr_work_auth
	 */
	public String getXusr_work_auth() {
		return xusr_work_auth;
	}

	/**
	 * Sets the xusr_work_auth.
	 *
	 * @param xusrWorkAuth the new xusr_work_auth
	 */
	public void setXusr_work_auth(String xusrWorkAuth) {
		xusr_work_auth = xusrWorkAuth;
	}

	/**
	 * Gets the xusr_aply_flag.
	 *
	 * @return the xusr_aply_flag
	 */
	public String getXusr_aply_flag() {
		return xusr_aply_flag;
	}

	/**
	 * Sets the xusr_aply_flag.
	 *
	 * @param xusrAplyFlag the new xusr_aply_flag
	 */
	public void setXusr_aply_flag(String xusrAplyFlag) {
		xusr_aply_flag = xusrAplyFlag;
	}

	/**
	 * Gets the xusr_aply_date.
	 *
	 * @return the xusr_aply_date
	 */
	public String getXusr_aply_date() {
		return xusr_aply_date;
	}

	/**
	 * Sets the xusr_aply_date.
	 *
	 * @param xusrAplyDate the new xusr_aply_date
	 */
	public void setXusr_aply_date(String xusrAplyDate) {
		xusr_aply_date = xusrAplyDate;
	}

	/**
	 * Gets the xusr_pswd.
	 *
	 * @return the xusr_pswd
	 */
	public String getXusr_pswd() {
		return xusr_pswd;
	}

	/**
	 * Sets the xusr_pswd.
	 *
	 * @param xusrPswd the new xusr_pswd
	 */
	public void setXusr_pswd(String xusrPswd) {
		xusr_pswd = xusrPswd;
	}

	/**
	 * Gets the xusr_pswd_date.
	 *
	 * @return the xusr_pswd_date
	 */
	public String getXusr_pswd_date() {
		return xusr_pswd_date;
	}

	/**
	 * Sets the xusr_pswd_date.
	 *
	 * @param xusrPswdDate the new xusr_pswd_date
	 */
	public void setXusr_pswd_date(String xusrPswdDate) {
		xusr_pswd_date = xusrPswdDate;
	}

	/**
	 * Gets the xusr_bsns_dept2.
	 *
	 * @return the xusr_bsns_dept2
	 */
	public String getXusr_bsns_dept2() {
		return xusr_bsns_dept2;
	}

	/**
	 * Sets the xusr_bsns_dept2.
	 *
	 * @param xusrBsnsDept2 the new xusr_bsns_dept2
	 */
	public void setXusr_bsns_dept2(String xusrBsnsDept2) {
		xusr_bsns_dept2 = xusrBsnsDept2;
	}

	/**
	 * Gets the xusr_bsns_dept3.
	 *
	 * @return the xusr_bsns_dept3
	 */
	public String getXusr_bsns_dept3() {
		return xusr_bsns_dept3;
	}

	/**
	 * Sets the xusr_bsns_dept3.
	 *
	 * @param xusrBsnsDept3 the new xusr_bsns_dept3
	 */
	public void setXusr_bsns_dept3(String xusrBsnsDept3) {
		xusr_bsns_dept3 = xusrBsnsDept3;
	}

	/**
	 * Gets the xusr_dept_code2.
	 *
	 * @return the xusr_dept_code2
	 */
	public String getXusr_dept_code2() {
		return xusr_dept_code2;
	}

	/**
	 * Sets the xusr_dept_code2.
	 *
	 * @param xusrDeptCode2 the new xusr_dept_code2
	 */
	public void setXusr_dept_code2(String xusrDeptCode2) {
		xusr_dept_code2 = xusrDeptCode2;
	}

	/**
	 * Gets the xusr_dept_code3.
	 *
	 * @return the xusr_dept_code3
	 */
	public String getXusr_dept_code3() {
		return xusr_dept_code3;
	}

	/**
	 * Sets the xusr_dept_code3.
	 *
	 * @param xusrDeptCode3 the new xusr_dept_code3
	 */
	public void setXusr_dept_code3(String xusrDeptCode3) {
		xusr_dept_code3 = xusrDeptCode3;
	}

	/**
	 * Gets the xusr_retr_flag.
	 *
	 * @return the xusr_retr_flag
	 */
	public String getXusr_retr_flag() {
		return xusr_retr_flag;
	}

	/**
	 * Sets the xusr_retr_flag.
	 *
	 * @param xusrRetrFlag the new xusr_retr_flag
	 */
	public void setXusr_retr_flag(String xusrRetrFlag) {
		xusr_retr_flag = xusrRetrFlag;
	}

	/**
	 * Gets the xusr_tel_no.
	 *
	 * @return the xusr_tel_no
	 */
	public String getXusr_tel_no() {
		return xusr_tel_no;
	}

	/**
	 * Sets the xusr_tel_no.
	 *
	 * @param xusrTelNo the new xusr_tel_no
	 */
	public void setXusr_tel_no(String xusrTelNo) {
		xusr_tel_no = xusrTelNo;
	}

	/**
	 * Gets the xusr_entr_empno.
	 *
	 * @return the xusr_entr_empno
	 */
	public String getXusr_entr_empno() {
		return xusr_entr_empno;
	}

	/**
	 * Sets the xusr_entr_empno.
	 *
	 * @param xusrEntrEmpno the new xusr_entr_empno
	 */
	public void setXusr_entr_empno(String xusrEntrEmpno) {
		xusr_entr_empno = xusrEntrEmpno;
	}

	/**
	 * Gets the xusr_updt_empno.
	 *
	 * @return the xusr_updt_empno
	 */
	public String getXusr_updt_empno() {
		return xusr_updt_empno;
	}

	/**
	 * Sets the xusr_updt_empno.
	 *
	 * @param xusrUpdtEmpno the new xusr_updt_empno
	 */
	public void setXusr_updt_empno(String xusrUpdtEmpno) {
		xusr_updt_empno = xusrUpdtEmpno;
	}

	/**
	 * Gets the xusr_entr_date.
	 *
	 * @return the xusr_entr_date
	 */
	public String getXusr_entr_date() {
		return xusr_entr_date;
	}

	/**
	 * Sets the xusr_entr_date.
	 *
	 * @param xusrEntrDate the new xusr_entr_date
	 */
	public void setXusr_entr_date(String xusrEntrDate) {
		xusr_entr_date = xusrEntrDate;
	}

	/**
	 * Gets the xusr_updt_date.
	 *
	 * @return the xusr_updt_date
	 */
	public String getXusr_updt_date() {
		return xusr_updt_date;
	}

	/**
	 * Sets the xusr_updt_date.
	 *
	 * @param xusrUpdtDate the new xusr_updt_date
	 */
	public void setXusr_updt_date(String xusrUpdtDate) {
		xusr_updt_date = xusrUpdtDate;
	}

	/**
	 * Gets the start point.
	 *
	 * @return the start point
	 */
	public Long getStartPoint() {
		return startPoint;
	}

	/**
	 * Sets the start point.
	 *
	 * @param startPoint the new start point
	 */
	public void setStartPoint(Long startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Gets the end point.
	 *
	 * @return the end point
	 */
	public Long getEndPoint() {
		return endPoint;
	}

	/**
	 * Sets the end point.
	 *
	 * @param endPoint the new end point
	 */
	public void setEndPoint(Long endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Gets the xusr_plac_work_nm.
	 *
	 * @return the xusr_plac_work_nm
	 */
	public String getXusr_plac_work_nm() {
		return xusr_plac_work_nm;
	}

	/**
	 * Sets the xusr_plac_work_nm.
	 *
	 * @param xusrPlacWorkNm the new xusr_plac_work_nm
	 */
	public void setXusr_plac_work_nm(String xusrPlacWorkNm) {
		xusr_plac_work_nm = xusrPlacWorkNm;
	}

	/**
	 * Gets the xusr_cmpny_date.
	 *
	 * @return the xusr_cmpny_date
	 */
	public String getXusr_cmpny_date() {
		return xusr_cmpny_date;
	}

	/**
	 * Sets the xusr_cmpny_date.
	 *
	 * @param xusr_cmpny_date the new xusr_cmpny_date
	 */
	public void setXusr_cmpny_date(String xusr_cmpny_date) {
		this.xusr_cmpny_date = xusr_cmpny_date;
	}

	/**
	 * Gets the xusr_sex_cd.
	 *
	 * @return the xusr_sex_cd
	 */
	public String getXusr_sex_cd() {
		return xusr_sex_cd;
	}

	/**
	 * Sets the xusr_sex_cd.
	 *
	 * @param xusr_sex_cd the new xusr_sex_cd
	 */
	public void setXusr_sex_cd(String xusr_sex_cd) {
		this.xusr_sex_cd = xusr_sex_cd;
	}

	public String getXusr_street() {
		return xusr_street;
	}

	public void setXusr_street(String xusr_street) {
		this.xusr_street = xusr_street;
	}

	public String getXusr_house() {
		return xusr_house;
	}

	public void setXusr_house(String xusr_house) {
		this.xusr_house = xusr_house;
	}

	public String getXusr_aptmt() {
		return xusr_aptmt;
	}

	public void setXusr_aptmt(String xusr_aptmt) {
		this.xusr_aptmt = xusr_aptmt;
	}

	public String getXusr_city() {
		return xusr_city;
	}

	public void setXusr_city(String xusr_city) {
		this.xusr_city = xusr_city;
	}

	public String getXusr_district() {
		return xusr_district;
	}

	public void setXusr_district(String xusr_district) {
		this.xusr_district = xusr_district;
	}

	public String getXusr_postal_code() {
		return xusr_postal_code;
	}

	public void setXusr_postal_code(String xusr_postal_code) {
		this.xusr_postal_code = xusr_postal_code;
	}

	public String getXusr_work_phone_no() {
		return xusr_work_phone_no;
	}

	public void setXusr_work_phone_no(String xusr_work_phone_no) {
		this.xusr_work_phone_no = xusr_work_phone_no;
	}

	public String getXusr_benefit_plan_cd() {
		return xusr_benefit_plan_cd;
	}

	public void setXusr_benefit_plan_cd(String xusr_benefit_plan_cd) {
		this.xusr_benefit_plan_cd = xusr_benefit_plan_cd;
	}

	public String getXusr_work_schedule_cd() {
		return xusr_work_schedule_cd;
	}

	public void setXusr_work_schedule_cd(String xusr_work_schedule_cd) {
		this.xusr_work_schedule_cd = xusr_work_schedule_cd;
	}

	public String getXusr_benefit_plan_nm() {
		return xusr_benefit_plan_nm;
	}

	public void setXusr_benefit_plan_nm(String xusr_benefit_plan_nm) {
		this.xusr_benefit_plan_nm = xusr_benefit_plan_nm;
	}

	public String getXusr_work_schedule_nm() {
		return xusr_work_schedule_nm;
	}

	public void setXusr_work_schedule_nm(String xusr_work_schedule_nm) {
		this.xusr_work_schedule_nm = xusr_work_schedule_nm;
	}

	public String getXusr_addr() {
		return xusr_addr;
	}

	public void setXusr_addr(String xusr_addr) {
		this.xusr_addr = xusr_addr;
	}

	public String getXusr_sex() {
		return xusr_sex;
	}

	public void setXusr_sex(String xusr_sex) {
		this.xusr_sex = xusr_sex;
	}

	public String getCorp_cd() {
		return corp_cd;
	}

	public void setCorp_cd(String corp_cd) {
		this.corp_cd = corp_cd;
	}

	public String getXusr_loc() {
		return xusr_loc;
	}

	public void setXusr_loc(String xusr_loc) {
		this.xusr_loc = xusr_loc;
	}

	public String getAdm_corp_cd() {
		return adm_corp_cd;
	}

	public void setAdm_corp_cd(String adm_corp_cd) {
		this.adm_corp_cd = adm_corp_cd;
	}
}
