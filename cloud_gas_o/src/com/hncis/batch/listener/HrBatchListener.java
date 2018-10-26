package com.hncis.batch.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

import com.hncis.batch.factor.BatchLogClass;

@Component("hrBatchListener")
public class HrBatchListener {
	
	private transient Log logger = LogFactory.getLog(new BatchLogClass()
		.getClass());
	
	String jobName;
	long jobId;
	BatchStatus jobStatus;
	
	public HrBatchListener() {
	}
	
	public void afterJob(JobExecution jobExecution) {
	
		jobName = jobExecution.getJobInstance().getJobName();
		jobId = jobExecution.getJobId();
		jobStatus = jobExecution.getStatus();
		
		logger.debug("################ hrBatchListener afterJob call "+ jobName+"("+jobId+"),status : "+jobStatus);
		
		if("insaToHmbJobByRs".equals(jobName)){
		}else if("odJobByRs".equals(jobName)){
		}
		
		/*
		if("insaToHmcJobByRs".equals(jobName)){
		}else if("odJobByRs".equals(jobName)){
		}
		*/
	}
	
	public void beforeJob(JobExecution jobExecution) {
	
		jobName = jobExecution.getJobInstance().getJobName();
		jobId = jobExecution.getJobId();
		jobStatus = jobExecution.getStatus();
		
		logger.debug("################ hrBatchListener beforeJob call "+ jobName+"("+jobId+"),status : "+jobStatus);
		
		if("insaToHmbJobByRs".equals(jobName)){
		}else if("odJobByRs".equals(jobName)){
		}
		
		/*
		if("insaToHmcJobByRs".equals(jobName)){
		}else if("odJobByRs".equals(jobName)){
		}
		*/
	}

}
