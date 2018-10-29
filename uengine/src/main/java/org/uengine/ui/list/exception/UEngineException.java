package org.uengine.ui.list.exception;

import org.apache.log4j.Logger;

public class UEngineException
	extends Exception {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UEngineException.class);

	private String errCode = null;
	
	private Exception ex = null;

	public UEngineException(Exception ex) {
		super(ex.getMessage());
		this.ex = ex;
	}

	public UEngineException(String code) {
		super();
		this.errCode = code;
	}

	public UEngineException(Exception ex, String code) {
		super(ex.getMessage());
		this.ex = ex;
		this.errCode = code;
	}

	public UEngineException(String message, String code) {
		super(message);
		this.errCode = code;
	}

	public UEngineException(Exception ex, String message, String code) {
		super(message);
		this.ex = ex;
		this.errCode = code;
	}

	public UEngineException(int code, String msg)
	{
		super(msg);
		this.errCode = String.valueOf(code);
	}

	public UEngineException(int code, String msg, Exception ex)
	{
		super(msg);
		this.ex = ex;
		this.errCode = String.valueOf(code);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public Exception getDetailException() {
		return ex;
	}

	public void printStackTrace() {
		logger.error("printStackTrace()", ex); //$NON-NLS-1$
	}
}
