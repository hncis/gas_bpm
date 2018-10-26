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
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.vo.BgabGascz008Dto;

@Service("approvalCreate01Processor")
public class approvalCreate01Processor extends StepExecutionListenerSupport implements ItemProcessor<BgabGascz003Dto, BgabGascz003Dto>{
	
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
		
		System.out.println("$$$$$$$$ approvalCreate01Processor beforeStep");
		
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();       
		jobDetailManager.deleteBgabGascz008Temp();
		jobDetailManager.insertBgabGascz008Temp();
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public int setApprovalData(BgabGascz008Dto bgabGascz008Dto, BgabGascz003Dto dto){
		
		System.out.println("setApprovalData 입장");
		BgabGascz008Dto bgabGascz008Info = jobDetailManager.getBgabGascz008Info(bgabGascz008Dto);
		
		if(bgabGascz008Info == null){
			jobDetailManager.mergeBgabGascz008(bgabGascz008Dto);
		}else{
			// 대결 O
			if(StringUtil.isNullToString(bgabGascz008Info.getSubt_flag()).equals("Y")){
				// 결재권자 변경시
//				if(!StringUtil.isNullToString(dto.getXorg_rsps_i()).equals(StringUtil.isNullToString(bgabGascz008Info.getEmpno_org()))){
//					BgabGascz008Dto bgabGascz008Dto01 = new BgabGascz008Dto();
//					bgabGascz008Dto01.setEmpno_org(bgabGascz008Info.getEmpno());
//					bgabGascz008Dto01.setEmpno(dto.getXorg_rsps_i());
//					bgabGascz008Dto01.setOrga_c(bgabGascz008Info.getOrga_c());
//					jobDetailManager.updateApprovalerChangeByBatch(bgabGascz008Dto01);
//					jobDetailManager.updateApprovalerChangeByBatch(bgabGascz008Dto01);
//					
//					jobDetailManager.mergeBgabGascz008(bgabGascz008Dto);
//					
//				// 상위부서 변경시
//				}else if(!StringUtil.isNullToString(dto.getXorg_orga_csner()).equals(StringUtil.isNullToString(bgabGascz008Info.getOrga_csner()))){ 
//					
//					jobDetailManager.updateBgabGascz008ByUpperDept(bgabGascz008Dto);
//				}
				
			}else{	// 대결 X
				// 결재권자 변경시
				System.out.println("bgabGascz008Info.getOrga_c()="+bgabGascz008Info.getOrga_c());
				System.out.println("dto.getXorg_rsps_i()="+dto.getXorg_rsps_i());
				System.out.println("bgabGascz008Info.getEmpno_org()="+bgabGascz008Info.getEmpno());
				
				if(!StringUtil.isNullToString(dto.getXorg_rsps_i()).equals(StringUtil.isNullToString(bgabGascz008Info.getEmpno()))){
					BgabGascz008Dto bgabGascz008Dto01 = new BgabGascz008Dto();
					bgabGascz008Dto01.setEmpno_org(bgabGascz008Info.getEmpno());
					bgabGascz008Dto01.setEmpno(dto.getXorg_rsps_i());
					
					//bgabGascz008Dto01.setEmpno(bgabGascz008Info.getEmpno());
					//bgabGascz008Dto01.setEmpno_org(dto.getXorg_rsps_i());
					
					bgabGascz008Dto01.setOrga_c(bgabGascz008Info.getOrga_c());
					jobDetailManager.updateApprovalerChangeByBatch(bgabGascz008Dto01);
					jobDetailManager.updateApprovalerChangeDetailByBatch(bgabGascz008Dto01);
				}
				
				jobDetailManager.mergeBgabGascz008(bgabGascz008Dto);
			}
		}
		return 1;
	}
	
	public BgabGascz003Dto process(BgabGascz003Dto dto) throws Exception {	
		
		if(dto==null) return null;
		
		System.out.println("debug01");
		
		BgabGascz008Dto bgabGascz008Dto = new BgabGascz008Dto();
		bgabGascz008Dto.setOrga_c(dto.getXorg_orga_c());
		bgabGascz008Dto.setOrga_e(dto.getXorg_orga_e());
		bgabGascz008Dto.setEmpno(dto.getXorg_rsps_i());
		bgabGascz008Dto.setEmpno_org(dto.getXorg_rsps_i());
		bgabGascz008Dto.setName(dto.getXorg_rsps_m());
		bgabGascz008Dto.setRank_c(dto.getXorg_rsps_crank());
		bgabGascz008Dto.setRank_e(dto.getXorg_rsps_mrank());
		bgabGascz008Dto.setOrga_csner(dto.getXorg_orga_csner());
		
		System.out.println("dto.getXorg_orga_c()="+dto.getXorg_orga_c());
		System.out.println("dto.getXorg_orga_csner()="+dto.getXorg_orga_csner());
		System.out.println("dto.getXorg_dept_lv()="+dto.getXorg_dept_lv());
		System.out.println("dto.getXorg_dept_lv()="+dto.getXorg_dept_lv());
		
		if(Integer.parseInt(dto.getXorg_dept_lv()) > 3){
			
			System.out.println("debug02");
			
			if(Integer.parseInt(dto.getXorg_dept_lv()) == 4){
				
				System.out.println("debug03");
				BgabGascz008Dto setCoordiInfo = new BgabGascz008Dto();
				String tempRsps_i = dto.getXorg_rsps_i();
				String tempCrd_i = StringUtil.isNullToString(dto.getXorg_crd_i());
				String tempCrd_m = StringUtil.isNullToString(dto.getXorg_crd_m());
				
				if(!tempCrd_i.equals("")){
					
					System.out.println("debug04");
					
					System.out.println("###############################################################");
					System.out.println("getXorg_crd_i()="+tempCrd_i);
					System.out.println("getXorg_crd_m()="+tempCrd_m);
					
					
					dto.setXorg_rsps_i(tempCrd_i);
					
					//bgabGascz008Dto01.setEmpno_org(bgabGascz008Info.getEmpno());
					//bgabGascz008Dto01.setEmpno(dto.getXorg_rsps_i());
					
					
					
					setCoordiInfo.setOrga_c(dto.getXorg_orga_c()+"C");
					setCoordiInfo.setOrga_e(dto.getXorg_orga_e()+" Coordi");
					//setCoordiInfo.setEmpno(coordiInfo.getEmpno());
					setCoordiInfo.setEmpno(dto.getXorg_rsps_i());
					setCoordiInfo.setEmpno_org(tempCrd_i);
					setCoordiInfo.setName(tempCrd_m);
					setCoordiInfo.setRank_c(dto.getXorg_rsps_crank());
					setCoordiInfo.setRank_e(dto.getXorg_rsps_mrank());
					setCoordiInfo.setOrga_csner(dto.getXorg_orga_csner());
					setCoordiInfo.setLevl_c("4");
					
					// dto.getXorg_rsps_i()
					
					uptCnt += setApprovalData(setCoordiInfo, dto);
					bgabGascz008Dto.setOrga_csner(dto.getXorg_orga_c()+"C");
				}
				dto.setXorg_rsps_i(tempRsps_i);
				System.out.println("debug05");
				
			}
			
			bgabGascz008Dto.setLevl_c(Integer.toString(Integer.parseInt(dto.getXorg_dept_lv())+1));
		}else{
			bgabGascz008Dto.setLevl_c(dto.getXorg_dept_lv());
		}
		
		System.out.println("debug06");
		
		uptCnt += setApprovalData(bgabGascz008Dto, dto);
		
		System.out.println("approval update count : " + uptCnt);
		
		logger.info("approval update count : " + uptCnt);
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		
		jobDetailManager.deleteBgabGascz008ByExpire();
		
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
