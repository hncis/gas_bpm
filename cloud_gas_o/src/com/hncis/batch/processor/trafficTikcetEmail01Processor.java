package com.hncis.batch.processor;

import java.util.ArrayList;
import java.util.List;
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
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
//import com.hncis.common.util.Submit;
import com.hncis.trafficTicket.vo.BgabGascTm01;

@Service("trafficTikcetEmail01Processor")
public class trafficTikcetEmail01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascTm01, BgabGascTm01>{
	
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
	List <BgabGascTm01> mailList = new ArrayList<BgabGascTm01>();
	
	public void beforeStep(StepExecution stepExec) {
		this.stepExecution = stepExec;
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();        
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public BgabGascTm01 process(BgabGascTm01 dto) throws Exception {	
		System.out.println("============================메일배치 프로세스");
		if(dto==null) return null;
		
		BgabGascTm01 bgabGascTm01 = new BgabGascTm01();
		
		bgabGascTm01.setDoc_no(dto.getDoc_no());
		bgabGascTm01.setCar_no(dto.getCar_no());
		bgabGascTm01.setTic_no(dto.getTic_no());
		bgabGascTm01.setTic_ymd(dto.getTic_ymd());
		bgabGascTm01.setTic_pint(dto.getTic_pint());
		bgabGascTm01.setTic_amt(dto.getTic_amt());
		bgabGascTm01.setTic_desc(dto.getTic_desc());
		
		mailList.add(bgabGascTm01);
		uptCnt++;
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		System.out.println("============================메일배치 프로세스후");
//		Submit.sendEmailTrafficTicket(mailList);
		
		Job job = new JobVo();
		job.setJobInstanceId(stepExec.getJobExecution().getJobId());
		job.setIptCnt(iptCnt);
		job.setUptCnt(uptCnt);
		
		try {
			jobManager.updateJobCnt(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.afterStep(stepExec);
	}

}
