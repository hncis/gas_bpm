package com.hncis.common.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonMessage. - 화면의 하단 메시지나 기타 필요한 정보를 담기 위한 value object
 */
public class CommonMessage {
	
	/** The message. 메시지 */
	private String message;
	
	/** The code. 필요한 정보 담기위한 코드*/
	private String code;
	
	/** The code1. 필요한 정보 담기위한 코드1*/
	private String code1;
	
	/** The code2. 필요한 정보 담기위한 코드2*/
	private String code2;
	
	/** The code3. 필요한 정보 담기위한 코드3*/
	private String code3;
	
	private String result;
	
	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the code1.
	 *
	 * @return the code1
	 */
	public String getCode1() {
		return code1;
	}
	
	/**
	 * Sets the code1.
	 *
	 * @param code1 the new code1
	 */
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	
	/**
	 * Gets the code2.
	 *
	 * @return the code2
	 */
	public String getCode2() {
		return code2;
	}
	
	/**
	 * Sets the code2.
	 *
	 * @param code2 the new code2
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	
	/**
	 * Gets the code3.
	 *
	 * @return the code3
	 */
	public String getCode3() {
		return code3;
	}
	
	/**
	 * Sets the code3.
	 *
	 * @param code3 the new code3
	 */
	public void setCode3(String code3) {
		this.code3 = code3;
	}

}
