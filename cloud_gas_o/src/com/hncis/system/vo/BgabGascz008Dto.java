package com.hncis.system.vo;

import com.hncis.common.vo.Common;

// TODO: Auto-generated Javadoc
/**
 * The Class BgabGascz008Dto.
 */
public class BgabGascz008Dto extends Common{
	
	/** The orga_c. 부서코드*/
	private String orga_c = "";
	
	/** The orga_e. 부서이름*/
	private String orga_e = "";
	
	/** The empno. 부서장 사번*/
	private String empno = "";
	
	/** The empno_temp. 부서장 사번*/
	private String empno_temp = "";
	
	/** The name. 부서장 성명*/
	private String name = "";
	
	/** The levl_c. 레벨*/
	private String levl_c = "";
	
	/** The levl_c. 레벨*/
	private int max_levl = 0;
	
	/** The levl_c. 레벨*/
	private int lev = 0;
	
	/** The call_m. 직급*/
	private String call_m = "";
	
	/** The rank_c. 부서레벨*/
	private String rank_c = "";
	
	/** The rank_e. 부서레벨정보*/
	private String rank_e = "";
	
	/** The orga_csner. 상위부서*/
	private String orga_csner = "";
	
	/** The subt_flag. 대결지정여부*/
	private String subt_flag = "";
	
	/** The inp_ymd. 입력일자*/
	private String inp_ymd = "";
	
	/** The ipe_eeno. 입력자사번*/
	private String ipe_eeno = "";
	
	/** The mdfy_ymd. 수정일자*/
	private String mdfy_ymd = "";
	
	/** The updr_eeno. 수정자 사번*/
	private String updr_eeno = "";
	
	/** The empno_h. 부서장 사번*/
	private String empno_h = "";
	
	/** The depute_dept. 부서*/
	private String depute_dept = "";
	
	/** The depute_eeno. 사번*/
	private String depute_eeno = "";
	
	/** The subt_from. */
	private String subt_from = "";
	
	/** The subt_to. */
	private String subt_to = "";
	
	/** The empno_org. 부서장 사번*/
	private String empno_org = "";
	
	/** The orga_e_upper. 상위부서*/
	private String orga_e_upper = "";
	
	/** The name_org. 이름*/
	private String name_org = "";
	
	/** The empno_nm. 부서장 이름*/
	private String empno_nm = "";
	
	/** The dept_nm. 부서명*/
	private String dept_nm  = "";
	
	private String coordi_yn  = "";
	
	

	public String getCoordi_yn() {
		return coordi_yn;
	}

	public void setCoordi_yn(String coordi_yn) {
		this.coordi_yn = coordi_yn;
	}

	public int getMax_levl() {
		return max_levl;
	}

	public void setMax_levl(int max_levl) {
		this.max_levl = max_levl;
	}

	/**
	 * Gets the empno_nm.
	 *
	 * @return the empno_nm
	 */
	public String getEmpno_nm() {
		return empno_nm;
	}
	
	/**
	 * Sets the empno_nm.
	 *
	 * @param empnoNm the new empno_nm
	 */
	public void setEmpno_nm(String empnoNm) {
		empno_nm = empnoNm;
	}
	
	/**
	 * Gets the dept_nm.
	 *
	 * @return the dept_nm
	 */
	public String getDept_nm() {
		return dept_nm;
	}
	
	/**
	 * Sets the dept_nm.
	 *
	 * @param deptNm the new dept_nm
	 */
	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}
	
	/**
	 * Gets the empno_temp.
	 *
	 * @return the empno_temp
	 */
	public String getEmpno_temp() {
		return empno_temp;
	}
	
	/**
	 * Sets the empno_temp.
	 *
	 * @param empnoTemp the new empno_temp
	 */
	public void setEmpno_temp(String empnoTemp) {
		empno_temp = empnoTemp;
	}
	
	/**
	 * Gets the name_org.
	 *
	 * @return the name_org
	 */
	public String getName_org() {
		return name_org;
	}
	
	/**
	 * Sets the name_org.
	 *
	 * @param nameOrg the new name_org
	 */
	public void setName_org(String nameOrg) {
		name_org = nameOrg;
	}
	
	/**
	 * Gets the orga_e_upper.
	 *
	 * @return the orga_e_upper
	 */
	public String getOrga_e_upper() {
		return orga_e_upper;
	}
	
	/**
	 * Sets the orga_e_upper.
	 *
	 * @param orgaEUpper the new orga_e_upper
	 */
	public void setOrga_e_upper(String orgaEUpper) {
		orga_e_upper = orgaEUpper;
	}
	
	/**
	 * Gets the subt_from.
	 *
	 * @return the subt_from
	 */
	public String getSubt_from() {
		return subt_from;
	}
	
	/**
	 * Sets the subt_from.
	 *
	 * @param subtFrom the new subt_from
	 */
	public void setSubt_from(String subtFrom) {
		subt_from = subtFrom;
	}
	
	/**
	 * Gets the subt_to.
	 *
	 * @return the subt_to
	 */
	public String getSubt_to() {
		return subt_to;
	}
	
	/**
	 * Sets the subt_to.
	 *
	 * @param subtTo the new subt_to
	 */
	public void setSubt_to(String subtTo) {
		subt_to = subtTo;
	}
	
	/**
	 * Gets the empno_org.
	 *
	 * @return the empno_org
	 */
	public String getEmpno_org() {
		return empno_org;
	}
	
	/**
	 * Sets the empno_org.
	 *
	 * @param empnoOrg the new empno_org
	 */
	public void setEmpno_org(String empnoOrg) {
		empno_org = empnoOrg;
	}
	
	/**
	 * Gets the depute_dept.
	 *
	 * @return the depute_dept
	 */
	public String getDepute_dept() {
		return depute_dept;
	}
	
	/**
	 * Sets the depute_dept.
	 *
	 * @param deputeDept the new depute_dept
	 */
	public void setDepute_dept(String deputeDept) {
		depute_dept = deputeDept;
	}
	
	/**
	 * Gets the depute_eeno.
	 *
	 * @return the depute_eeno
	 */
	public String getDepute_eeno() {
		return depute_eeno;
	}
	
	/**
	 * Sets the depute_eeno.
	 *
	 * @param deputeEeno the new depute_eeno
	 */
	public void setDepute_eeno(String deputeEeno) {
		depute_eeno = deputeEeno;
	}
	
	/**
	 * Gets the empno_h.
	 *
	 * @return the empno_h
	 */
	public String getEmpno_h() {
		return empno_h;
	}
	
	/**
	 * Sets the empno_h.
	 *
	 * @param empnoH the new empno_h
	 */
	public void setEmpno_h(String empnoH) {
		empno_h = empnoH;
	}
	
	/**
	 * Gets the orga_c.
	 *
	 * @return the orga_c
	 */
	public String getOrga_c() {
		return orga_c;
	}
	
	/**
	 * Sets the orga_c.
	 *
	 * @param orgaC the new orga_c
	 */
	public void setOrga_c(String orgaC) {
		orga_c = orgaC;
	}
	
	/**
	 * Gets the orga_e.
	 *
	 * @return the orga_e
	 */
	public String getOrga_e() {
		return orga_e;
	}
	
	/**
	 * Sets the orga_e.
	 *
	 * @param orgaE the new orga_e
	 */
	public void setOrga_e(String orgaE) {
		orga_e = orgaE;
	}
	
	/**
	 * Gets the empno.
	 *
	 * @return the empno
	 */
	public String getEmpno() {
		return empno;
	}
	
	/**
	 * Sets the empno.
	 *
	 * @param empno the new empno
	 */
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the levl_c.
	 *
	 * @return the levl_c
	 */
	public String getLevl_c() {
		return levl_c;
	}
	
	/**
	 * Sets the levl_c.
	 *
	 * @param levlC the new levl_c
	 */
	public void setLevl_c(String levlC) {
		levl_c = levlC;
	}
	
	/**
	 * Gets the call_m.
	 *
	 * @return the call_m
	 */
	public String getCall_m() {
		return call_m;
	}
	
	/**
	 * Sets the call_m.
	 *
	 * @param callM the new call_m
	 */
	public void setCall_m(String callM) {
		call_m = callM;
	}
	
	/**
	 * Gets the rank_c.
	 *
	 * @return the rank_c
	 */
	public String getRank_c() {
		return rank_c;
	}
	
	/**
	 * Sets the rank_c.
	 *
	 * @param rankC the new rank_c
	 */
	public void setRank_c(String rankC) {
		rank_c = rankC;
	}
	
	/**
	 * Gets the rank_e.
	 *
	 * @return the rank_e
	 */
	public String getRank_e() {
		return rank_e;
	}
	
	/**
	 * Sets the rank_e.
	 *
	 * @param rankE the new rank_e
	 */
	public void setRank_e(String rankE) {
		rank_e = rankE;
	}
	
	/**
	 * Gets the orga_csner.
	 *
	 * @return the orga_csner
	 */
	public String getOrga_csner() {
		return orga_csner;
	}
	
	/**
	 * Sets the orga_csner.
	 *
	 * @param orgaCsner the new orga_csner
	 */
	public void setOrga_csner(String orgaCsner) {
		orga_csner = orgaCsner;
	}
	
	/**
	 * Gets the subt_flag.
	 *
	 * @return the subt_flag
	 */
	public String getSubt_flag() {
		return subt_flag;
	}
	
	/**
	 * Sets the subt_flag.
	 *
	 * @param subtFlag the new subt_flag
	 */
	public void setSubt_flag(String subtFlag) {
		subt_flag = subtFlag;
	}
	
	/**
	 * Gets the inp_ymd.
	 *
	 * @return the inp_ymd
	 */
	public String getInp_ymd() {
		return inp_ymd;
	}
	
	/**
	 * Sets the inp_ymd.
	 *
	 * @param inpYmd the new inp_ymd
	 */
	public void setInp_ymd(String inpYmd) {
		inp_ymd = inpYmd;
	}
	
	/**
	 * Gets the ipe_eeno.
	 *
	 * @return the ipe_eeno
	 */
	public String getIpe_eeno() {
		return ipe_eeno;
	}
	
	/**
	 * Sets the ipe_eeno.
	 *
	 * @param ipeEeno the new ipe_eeno
	 */
	public void setIpe_eeno(String ipeEeno) {
		ipe_eeno = ipeEeno;
	}
	
	/**
	 * Gets the mdfy_ymd.
	 *
	 * @return the mdfy_ymd
	 */
	public String getMdfy_ymd() {
		return mdfy_ymd;
	}
	
	/**
	 * Sets the mdfy_ymd.
	 *
	 * @param mdfyYmd the new mdfy_ymd
	 */
	public void setMdfy_ymd(String mdfyYmd) {
		mdfy_ymd = mdfyYmd;
	}
	
	/**
	 * Gets the updr_eeno.
	 *
	 * @return the updr_eeno
	 */
	public String getUpdr_eeno() {
		return updr_eeno;
	}
	
	/**
	 * Sets the updr_eeno.
	 *
	 * @param updrEeno the new updr_eeno
	 */
	public void setUpdr_eeno(String updrEeno) {
		updr_eeno = updrEeno;
	}
	
	
}
