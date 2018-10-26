package com.hncis.batch.job.vo;

public interface ScheduleJob {
	
	// ������ ���
	public String getId();
	public void setId(String id);
	
	// Job ��Ī
	public String getJobId();
	public void setJobId(String jobId);
	
	// Ÿ��
	public String getTypeId();
	public void setTypeId(String typeId);
	
	// ����
	public String getDescription();
	public void setDescription(String description);
	
	// ��� ������
	public String getUseStartDate();
	public void setUseStartDate(String useStartDate);
	
	// ��� ������
	public String getUseEndDate();
	public void setUseEndDate(String useEndDate);
	
	// ��
	public String getVal();
	public void setVal(String val);
	
	// ��뿩��
	public String getUseYn();
	public void setUseYn(String useYn);
	
	// Ÿ�Ը�
	public String getTypename();
	public void setTypename(String typename);
	
	// ���ӽ����� ID
	public String getDependencyScheduleId();
	public void setDependencyScheduleId(String dependencyScheduleId);
	
	// ��õ�����
	public String getRestartYn();
	public void setRestartYn(String restartYn);
	
	// ��õ�Ƚ��
	public int getRestartCount();
	public void setRestartCount(int restartCount);
	
	// ��õ�����
	public int getRestartTime();
	public void setRestartTime(int restartTime);

}
