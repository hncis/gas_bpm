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
import com.hncis.batch.job.vo.dto.BgabGascI001Dto;
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.system.vo.BgabGascz006Dto;

@Service("userCreate01Processor")
public class userCreate01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascI001Dto, BgabGascI001Dto>{
	
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
		jobDetailManager.deleteBgabGasci001Backup();
		jobDetailManager.insertBgabGasci001Backup();
		jobDetailManager.deleteBgabGascz002Temp();
		jobDetailManager.insertBgabGascz002Temp();
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public BgabGascI001Dto process(BgabGascI001Dto dto) throws Exception {	
		
		if(dto==null) return null;
		
		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		
		bgabGascz002Dto.setXusr_empno(dto.getUsn());
		bgabGascz002Dto.setXusr_auth_knd("U");
		bgabGascz002Dto.setXusr_name(dto.getUsr_nm());
		bgabGascz002Dto.setXusr_en_name(dto.getName_en());
		bgabGascz002Dto.setXusr_gnrl_area("1");
		//bgabGascz002Dto.setXusr_plac_work("1");
		bgabGascz002Dto.setXusr_plac_work(dto.getWrkplc_cd());
		bgabGascz002Dto.setXusr_bsns_dept(dto.getUp_dprtmt_cd());
		bgabGascz002Dto.setXusr_dept_code(dto.getDprtmt_cd());
		bgabGascz002Dto.setXusr_dept_name(dto.getDprtmt_nm());
		bgabGascz002Dto.setXusr_step_code(dto.getTitle_cd());
		bgabGascz002Dto.setXusr_step_name(dto.getTitle_nm());
		bgabGascz002Dto.setXusr_dept_knd("1");
		bgabGascz002Dto.setXusr_cnfm_auth(dto.getCnfm_auth());
		bgabGascz002Dto.setXusr_all_auth("");
		bgabGascz002Dto.setXusr_work_auth("44444444444444444444444444444444444444444444444444");
		bgabGascz002Dto.setXusr_aply_flag("");
		bgabGascz002Dto.setXusr_aply_date("");
		bgabGascz002Dto.setXusr_pswd("");
		bgabGascz002Dto.setXusr_pswd_date("");
		bgabGascz002Dto.setXusr_bsns_dept2("");
		bgabGascz002Dto.setXusr_bsns_dept3("");
		bgabGascz002Dto.setXusr_dept_code2("");
		bgabGascz002Dto.setXusr_dept_code3("");
		//retire flag
		//As-is get getWork_yn()
		//bgabGascz002Dto.setXusr_retr_flag(dto.getWork_yn());
		
		//To-be's retire flag is following.
		if("3".equals(StringUtil.isNullToString(dto.getWork_yn()))) {
			bgabGascz002Dto.setXusr_retr_flag("N");
		}else{
			bgabGascz002Dto.setXusr_retr_flag("Y");
		}
		
		bgabGascz002Dto.setXusr_tel_no(dto.getMp_num());
		bgabGascz002Dto.setXusr_entr_empno("BATCH");
		bgabGascz002Dto.setXusr_updt_empno("BATCH");
		//bgabGascz002Dto.setXusr_cmpny_date(dto.getJoin_cmpny_date()); //added column
		//bgabGascz002Dto.setXusr_sex_cd(dto.getGender_cd()); // added column
		
		bgabGascz002Dto.setXusr_cost_cd(dto.getCc_cd());
		bgabGascz002Dto.setXusr_cost_nm(dto.getCc_nm());
		bgabGascz002Dto.setXusr_mail_adr(dto.getEmail());
		
		bgabGascz002Dto.setXusr_street(dto.getStreet());
		bgabGascz002Dto.setXusr_house(dto.getHouse());
		bgabGascz002Dto.setXusr_aptmt(dto.getAptmt());
		bgabGascz002Dto.setXusr_city(dto.getCity());
		bgabGascz002Dto.setXusr_district(dto.getDistrict());
		bgabGascz002Dto.setXusr_postal_code(dto.getPostal_code());
		bgabGascz002Dto.setXusr_work_phone_no(dto.getWork_phone_no());
		bgabGascz002Dto.setXusr_benefit_plan_cd(dto.getBenefit_plan_cd());
		bgabGascz002Dto.setXusr_work_schedule_cd(dto.getWork_schedule_cd());
		bgabGascz002Dto.setXusr_benefit_plan_nm(dto.getBenefit_plan_nm());
		bgabGascz002Dto.setXusr_work_schedule_nm(dto.getWork_schedule_nm());
		
		BgabGascz006Dto bgabGascz006Dto = new BgabGascz006Dto();
		bgabGascz006Dto.setEmpno(dto.getUsn());
		
		int gacCnt = jobDetailManager.selectCountXgascInfo(bgabGascz006Dto);
		
		if(gacCnt > 0){
			bgabGascz002Dto.setIs_xgac("Y");
		}
		
		jobDetailManager.mergeBgabGascz002(bgabGascz002Dto);
		uptCnt++;
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		
		if(stepExec.getStatus() == stepExec.getStatus().COMPLETED){
			jobDetailManager.deleteBgabGasci001();
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
