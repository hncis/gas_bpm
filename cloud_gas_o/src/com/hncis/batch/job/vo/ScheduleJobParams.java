package com.hncis.batch.job.vo;

public interface ScheduleJobParams {
	
	// �Ķ���� Key ��Ī
	public String getId();
	public void setId(String id);
	
	// ������ ���
	public String getScheduleId();
	public void setScheduleId(String scheduleId);
	
	// �Ķ���� ��
	public String getVal();
	public void setVal(String val);
	
	// Ÿ��
	public String getType();
	public void setType(String type);
	
	// �Ķ���� �⺻ ��
	public String getDefaultVal();
	public void setDefaultVal(String defaultVal);
	
	// �ڵ����� ����
	public String getAutoIncrementYn();
	public void setAutoIncrementYn(String autoIncrementYn);
	

}
