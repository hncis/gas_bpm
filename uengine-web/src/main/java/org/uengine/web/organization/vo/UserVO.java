package org.uengine.web.organization.vo;

import java.io.Serializable;

/**
 * <pre>
 * org.uengine.web.organization.vo 
 * UserVO.java
 * 
 * </pre>
 * @date : 2016. 6. 22. 오후 4:11:08
 * @version : 
 * @author : next3
 */
public class UserVO implements Serializable {

	private static final long serialVersionUID = 342794486746189970L;
	private String empCode;
	private String empName;
    private String partName;
    private String partCode;
    private String jikName;
    private boolean accessable = false;
	/**
	 * @return the accessable
	 */
	public boolean isAccessable() {
		return accessable;
	}
	/**
	 * @param accessable the accessable to set
	 */
	public void setAccessable(boolean accessable) {
		this.accessable = accessable;
	}
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
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
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

}
