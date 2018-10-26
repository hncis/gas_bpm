package com.hncis.common.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonUserInfo. - 공통 사용자 정보 value object
 */
public class CommonUserInfo extends Common{
	
	/** The eeno. 사번*/
	private String eeno        		;
	
	/** The eenm. 사용자명*/
	private String eenm        		;
	
	/** The retr flag. 사용자 은퇴 여부*/
	private String retrFlag        	;
	
	/** The step name. 사용자 직급명*/
	private String stepName        	;
	
	/** The dept cd. 사용자 부서코드*/
	private String deptCd        	;
	
	/** The dept name. 사용자 부서명*/
	private String deptName        	;
	
	/** The hpno. 사용자 전화번호*/
	private String hpno          	;
	
	/** The email. 사용자 이메일*/
	private String email          	;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the eeno.
	 *
	 * @return the eeno
	 */
	public String getEeno() {
		return eeno;
	}
	
	/**
	 * Sets the eeno.
	 *
	 * @param eeno the new eeno
	 */
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}
	
	/**
	 * Gets the eenm.
	 *
	 * @return the eenm
	 */
	public String getEenm() {
		return eenm;
	}
	
	/**
	 * Sets the eenm.
	 *
	 * @param eenm the new eenm
	 */
	public void setEenm(String eenm) {
		this.eenm = eenm;
	}
	
	/**
	 * Gets the retr flag.
	 *
	 * @return the retr flag
	 */
	public String getRetrFlag() {
		return retrFlag;
	}
	
	/**
	 * Sets the retr flag.
	 *
	 * @param retrFlag the new retr flag
	 */
	public void setRetrFlag(String retrFlag) {
		this.retrFlag = retrFlag;
	}
	
	/**
	 * Gets the step name.
	 *
	 * @return the step name
	 */
	public String getStepName() {
		return stepName;
	}
	
	/**
	 * Sets the step name.
	 *
	 * @param stepName the new step name
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	/**
	 * Gets the dept cd.
	 *
	 * @return the dept cd
	 */
	public String getDeptCd() {
		return deptCd;
	}
	
	/**
	 * Sets the dept cd.
	 *
	 * @param deptCd the new dept cd
	 */
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	
	/**
	 * Gets the dept name.
	 *
	 * @return the dept name
	 */
	public String getDeptName() {
		return deptName;
	}
	
	/**
	 * Sets the dept name.
	 *
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	/**
	 * Gets the hpno.
	 *
	 * @return the hpno
	 */
	public String getHpno() {
		return hpno;
	}
	
	/**
	 * Sets the hpno.
	 *
	 * @param hpno the new hpno
	 */
	public void setHpno(String hpno) {
		this.hpno = hpno;
	}
}
