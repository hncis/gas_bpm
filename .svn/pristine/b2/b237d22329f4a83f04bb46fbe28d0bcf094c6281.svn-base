package com.hncis.batch.processor;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hncis.batch.factor.BatchLogClass;
import com.hncis.batch.factor.JobExecutionContext;
import com.hncis.batch.job.manager.JobDetailManager;
import com.hncis.batch.job.manager.JobManager;
import com.hncis.batch.job.vo.Job;
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.vo.BgabGascz008Dto;

@Service("fileRemove01Processor")
public class fileRemove01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascZ011Dto, BgabGascZ011Dto>{
	
	private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());

	private StepExecution stepExecution;
	
	@Autowired @Qualifier("jobExecutionContext")
    private JobExecutionContext jobExecutionContext;
	
	@Autowired	@Qualifier("jobDetailManagerImpl") 
	private JobDetailManager jobDetailManager;
	
	@Autowired	@Qualifier("jobManagerImpl") 
	private JobManager jobManager;
	
	int iptCnt = 0;						
	int uptCnt = 0;						
	
	public void beforeStep(StepExecution stepExec) {
		this.stepExecution = stepExec;
				
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public BgabGascZ011Dto process(BgabGascZ011Dto dto) throws Exception {	
		
		if(dto==null) return null;
		
		System.out.println("Doc_no="+dto.getDoc_no());
		System.out.println("Affr_scn_cd="+dto.getAffr_scn_cd());
		System.out.println("getOgc_fil_nm="+dto.getOgc_fil_nm());
		
		String folderNm = "";
		
		if(StringUtil.isNullToString(dto.getAffr_scn_cd()).equals("BT")){
			folderNm = "businessTravel";
		}else if(StringUtil.isNullToString(dto.getAffr_scn_cd()).equals("PS")){
			folderNm = "pickupService";
		}
		
		String fileResult = FileUtil.deleteFile(dto.getCorp_cd(), folderNm, dto.getOgc_fil_nm());
		
		jobDetailManager.deleteBgabGascZ011(dto);
		uptCnt++;
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		
		Job job = new JobVo();
		job.setJobInstanceId(stepExec.getJobExecution().getJobId());
		job.setIptCnt(iptCnt);
		job.setUptCnt(uptCnt);
		
		try {
			jobManager.updateJobCnt(job);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.afterStep(stepExec);
	}

}
