package com.hncis.batch.job.vo.impl;

import com.hncis.batch.job.vo.JobDetailStep;

public class JobDetailStepVo  implements JobDetailStep{
	
	private int stepExecutionId;
	private int version;
	private String stepName;
	private int jobExecutionId;
	private String StartTime;
	private String EndTime;
	private String status;
	private int commitCount;
	private int readCount;
	private int filterCount;
	private int writeCount;
	private int readSkipCount;
	private int writeSkipCount;
	private int processSkipCount;
	private int rollbackCount;
	private String exitCode;
	private String exitMessage;
	private String lastUpdated;
	
	public int getStepExecutionId() {
		return stepExecutionId;
	}
	public void setStepExecutionId(int stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public int getJobExecutionId() {
		return jobExecutionId;
	}
	public void setJobExecutionId(int jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCommitCount() {
		return commitCount;
	}
	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getFilterCount() {
		return filterCount;
	}
	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}
	public int getWriteCount() {
		return writeCount;
	}
	public void setWriteCount(int writeCount) {
		this.writeCount = writeCount;
	}
	public int getReadSkipCount() {
		return readSkipCount;
	}
	public void setReadSkipCount(int readSkipCount) {
		this.readSkipCount = readSkipCount;
	}
	public int getWriteSkipCount() {
		return writeSkipCount;
	}
	public void setWriteSkipCount(int writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}
	public int getProcessSkipCount() {
		return processSkipCount;
	}
	public void setProcessSkipCount(int processSkipCount) {
		this.processSkipCount = processSkipCount;
	}
	public int getRollbackCount() {
		return rollbackCount;
	}
	public void setRollbackCount(int rollbackCount) {
		this.rollbackCount = rollbackCount;
	}
	public String getExitCode() {
		return exitCode;
	}
	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}
	public String getExitMessage() {
		return exitMessage;
	}
	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
