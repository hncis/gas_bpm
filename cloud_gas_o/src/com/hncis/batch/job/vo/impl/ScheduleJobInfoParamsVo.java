package com.hncis.batch.job.vo.impl;

import com.hncis.batch.job.vo.ScheduleJobInfoParams;

public class ScheduleJobInfoParamsVo implements ScheduleJobInfoParams {
	
	private String jobId;				// Job ��Ī
	private String id;					// Key ��Ī
	private String val;					// �Ķ���� ��
	private String type;				// Ÿ��
	private String description;			// ����
	private String useYn;				// ��뿩��
	private String autoIncrementYn;		// �ڵ�����
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getAutoIncrementYn() {
		return autoIncrementYn;
	}
	public void setAutoIncrementYn(String autoIncrementYn) {
		this.autoIncrementYn = autoIncrementYn;
	}
	
}
