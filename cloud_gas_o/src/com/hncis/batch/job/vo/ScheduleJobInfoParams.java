package com.hncis.batch.job.vo;

public interface ScheduleJobInfoParams {
	
	// Job ��Ī
	public String getJobId();
	public void setJobId(String jobId);
	
	// �Ķ���� Key ��Ī
	public String getId();
	public void setId(String id);
	
	// �Ķ���� ��
	public String getVal();
	public void setVal(String val);
	
	// Ÿ��
	public String getType();
	public void setType(String type);
	
	// ����
	public String getDescription();
	public void setDescription(String description);
	
	// ��뿩��
	public String getUseYn();
	public void setUseYn(String useYn);
	
	// �ڵ����� ����
	public String getAutoIncrementYn();
	public void setAutoIncrementYn(String autoIncrementYn);
	

}
