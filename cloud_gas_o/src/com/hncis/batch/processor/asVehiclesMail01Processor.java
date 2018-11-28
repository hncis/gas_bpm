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
import com.hncis.batch.job.vo.dto.BgabGascI001Dto;
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.util.StringUtil;
//import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;

@Service("asVehiclesMail01Processor")
public class asVehiclesMail01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascbv01Dto, BgabGascbv01Dto>{
	
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
	
	List <BgabGascbv01Dto> mailList = new ArrayList<BgabGascbv01Dto>();
	
	
	public void beforeStep(StepExecution stepExec) {
		System.out.println("============================메일배치 프로세스전");
		this.stepExecution = stepExec;
		
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();        
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public BgabGascbv01Dto process(BgabGascbv01Dto dto) throws Exception {	
		
		
		System.out.println("============================메일배치 프로세스");
		if(dto==null) return null;
		
		BgabGascbv01Dto bgabGascbv01Dto = new BgabGascbv01Dto();
		
		bgabGascbv01Dto.setCar_no(dto.getCar_no());
		bgabGascbv01Dto.setCrgr_eeno(dto.getCrgr_eeno());
		bgabGascbv01Dto.setFxt_ins_infm_nos(dto.getFxt_ins_infm_nos());
		bgabGascbv01Dto.setAs_type(dto.getAs_type());
		bgabGascbv01Dto.setChss_no(dto.getChss_no());
		bgabGascbv01Dto.setEeno_email(dto.getEeno_email());
		
		mailList.add(bgabGascbv01Dto);
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		System.out.println("============================메일배치 프로세스후");
//		Submit.sendEmailForAsVehicle(mailList);
		
		
		
		for(int i=0;i<mailList.size();i++){
			if(mailList.get(i).getAs_type().equals("M")){
				uptCnt +=jobDetailManager.updateAsVehicleInfo(mailList.get(i));
			}
		}
		
		
		Job job = new JobVo();
		job.setJobInstanceId(stepExec.getJobExecution().getJobId());
		job.setIptCnt(mailList.size()-uptCnt);
		job.setUptCnt(uptCnt);
		
		System.out.println("===============IptCnt:"+job.getIptCnt());
		System.out.println("===============UptCnt:"+job.getUptCnt());
		
		try {
			jobManager.updateJobCnt(job);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mailList.clear();
			
		}
		
		return super.afterStep(stepExec);
	}

}
