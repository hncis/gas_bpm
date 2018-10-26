package com.hncis.batch.job.vo;

import java.sql.Timestamp;

public interface JobParams {
	
	public int getJobInstanceId();
	public void setJobInstanceId(int jobInstanceId);
	
	public String getTypeCd();
	public void setTypeCd(String typeCd);
	
	public String getKeyName();
	public void setKeyName(String keyName);
	
	public String getStringVal();
	public void setStringVal(String stringVal);
	
	public Timestamp getDateVal();
	public void setDateVal(Timestamp dateVal);
	
	public long getLongVal();
	public void setLongVal(long longVal);
	
	public double getDoubleVal();
	public void setDoubleVal(double doubleVal);

}
