package org.uengine.web.login.vo;

import java.io.Serializable;

/**
 * <pre>
 * org.uengine.web.login.vo 
 * LoginVO.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:39:51
 * @version : 
 * @author : mkbok_Enki
 */
public class LoginVO implements Serializable {

	private static final long serialVersionUID = 342794486746189970L;
	private String userId;
	private String userPassword;
    private String userName;
    private String isAdmin;
    private String email;
    private String partCode;
    private String partName;
    private String comCode;
    private String comName;
    private String mobileNo;
    private String locale;
    private String jikName;
	/**
	 * @return the jikName
	 */
	public String getJikName() {
		return jikName;
	}
	/**
	 * @param jikName the jikName to set
	 */
	public void setJikName(String jikName) {
		this.jikName = jikName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}
	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the isAdmin
	 */
	public String getIsAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @return the partName
	 */
	public String getPartName() {
		return partName;
	}
	/**
	 * @param partName the partName to set
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	/**
	 * @return the comCode
	 */
	public String getComCode() {
		return comCode;
	}
	/**
	 * @param comCode the comCode to set
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	/**
	 * @return the comName
	 */
	public String getComName() {
		return comName;
	}
	/**
	 * @param comName the comName to set
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	@Override
	public String toString() {
		return "LoginVO [userId=" + userId + ", userPassword=" + userPassword
				+ ", userName=" + userName + ", isAdmin=" + isAdmin
				+ ", email=" + email + ", partCode=" + partCode + ", partName="
				+ partName + ", comCode=" + comCode + ", comName=" + comName
				+ ", mobileNo=" + mobileNo + ", locale=" + locale
				+ ", jikName=" + jikName + "]";
	}

	
}
