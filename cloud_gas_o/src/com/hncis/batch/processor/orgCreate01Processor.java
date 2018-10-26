package com.hncis.batch.processor;

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
import com.hncis.batch.job.vo.dto.BgabGascI002Dto;
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz003Dto;

@Service("orgCreate01Processor")
public class orgCreate01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascI002Dto, BgabGascI002Dto>{
	
	private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());

	private StepExecution stepExecution;
	
	@Autowired @Qualifier("jobExecutionContext")
    private JobExecutionContext jobExecutionContext;
	
	@Autowired	@Qualifier("jobDetailManagerImpl") 
	private JobDetailManager jobDetailManager;
	
	@Autowired	@Qualifier("jobManagerImpl") 
	private JobManager jobManager;
	
	String trig_type = "";

	int iptCnt = 0;						
	int uptCnt = 0;						
	
	public void beforeStep(StepExecution stepExec) {
		this.stepExecution = stepExec;
		
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();        
		jobDetailManager.deleteBgabGasci002Backup();
		jobDetailManager.insertBgabGasci002Backup();
		jobDetailManager.deleteBgabGascz003Temp();
		jobDetailManager.insertBgabGascz003Temp();
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public BgabGascI002Dto process(BgabGascI002Dto dto) throws Exception {	
		
		if(dto==null) return null;
		
		BgabGascz003Dto bgabGascz003Dto = new BgabGascz003Dto();
		
		bgabGascz003Dto.setXorg_orga_c(dto.getDept_id());
		bgabGascz003Dto.setXorg_orga_e(dto.getDept_nm());
		bgabGascz003Dto.setXorg_orga_csner(dto.getUpr_dept_id());
		bgabGascz003Dto.setXorg_clos_d("99999999");
		bgabGascz003Dto.setXorg_plac_c(dto.getWrkplc_cd());
		bgabGascz003Dto.setXorg_rsps_i(dto.getMngr_usn());
		bgabGascz003Dto.setXorg_rsps_m(dto.getMngr_nm());		
		bgabGascz003Dto.setXorg_rsps_crank(dto.getMngr_job_titl_cd());//Position code
		bgabGascz003Dto.setXorg_rsps_mrank(dto.getMngr_job_titl_nm());//Position code
		bgabGascz003Dto.setXorg_company("HMB");
		bgabGascz003Dto.setXorg_dept_lv(dto.getDept_lv());
		bgabGascz003Dto.setXorg_dept_lv_nm(dto.getDept_lv_nm());
		bgabGascz003Dto.setXorg_clos_d(dto.getClos_d());
		
		jobDetailManager.mergeBgabGascz003(bgabGascz003Dto);
		
		uptCnt++;
		
		System.out.println("org update count : " + uptCnt);
		logger.info("org update count : " + uptCnt);
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		
		if(stepExec.getStatus() == stepExec.getStatus().COMPLETED){
			jobDetailManager.deleteBgabGasci002();
		}

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
