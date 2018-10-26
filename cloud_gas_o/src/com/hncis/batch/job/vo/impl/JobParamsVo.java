package com.hncis.batch.job.vo.impl;

import java.sql.Timestamp;

import com.hncis.batch.job.vo.JobParams;

public class JobParamsVo implements JobParams{
	
	private int jobInstanceId;
	private String typeCd;
	private String keyName;
	private String stringVal;
	private Timestamp dateVal;
	private long longVal;
	private double doubleVal;
	
	public int getJobInstanceId() {
		return jobInstanceId;
	}
	public void setJobInstanceId(int jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getStringVal() {
		return stringVal;
	}
	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	public Timestamp getDateVal() {
		return dateVal;
	}
	public void setDateVal(Timestamp dateVal) {
		this.dateVal = dateVal;
	}
	public long getLongVal() {
		return longVal;
	}
	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}
	public double getDoubleVal() {
		return doubleVal;
	}
	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

}
