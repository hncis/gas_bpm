package com.hncis.batch.quartz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hncis.batch.job.manager.JobManager;
import com.hncis.batch.job.vo.ScheduleJob;
import com.hncis.batch.job.vo.ScheduleJobParams;
import com.hncis.batch.job.vo.dto.ScheduleDto;
import com.hncis.batch.job.vo.impl.ScheduleJobParamsVo;
import com.hncis.batch.job.vo.impl.ScheduleJobVo;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.system.vo.Hncis_Job_Schedule;

/**
 * Quartz �� ��ġ
 * @author administrator
 *
 */
public class JobLauncherDaily extends QuartzJobBean {
	
	private transient Log logger = LogFactory.getLog(this.getClass());
	
	private AutoJobLauncher autoJobLauncher;
	private JobManager jobManager;
	
	public void setAutoJobLauncher(AutoJobLauncher autoJobLauncher) {
		this.autoJobLauncher = autoJobLauncher;
	}
	
	public void setJobManager(JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
	protected synchronized void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("============================== JobLauncherDaily (" + Calendar.getInstance().getTime() + ") ==============================");
		
		System.out.println("##################################### BATCH START("+Calendar.getInstance().getTime()+") #####################################");
		
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setNowDate(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
		scheduleDto.setVal(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
		
		//search schedulelist in table
		ScheduleDto[] scheduleDtoList = (ScheduleDto[])jobManager.getQuartzScheduleDailyList(scheduleDto).toArray(new ScheduleDto[0]);
		
		for(int i=0; i<scheduleDtoList.length; i++){
			//�ѹ�¥���� ��� TYPE_ID ��󺹱�
			// user, org, appline is type 2 in develop server.
			if(scheduleDtoList[i].getTypeId().equals("1")){
				Hncis_Job_Schedule hncis_Job_Schedule = new Hncis_Job_Schedule();
				hncis_Job_Schedule.setId(scheduleDtoList[i].getId());
				hncis_Job_Schedule.setType_id(scheduleDtoList[i].getDependencyScheduleId());
				
				try {
					System.out.println("####update job schedule - start ####");
					jobManager.updateJobSchedule(hncis_Job_Schedule);
					System.out.println("####update job schedule - end ####");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}						
		}
		
		for(int i=0; i<scheduleDtoList.length; i++){					
			scheduleRun(scheduleDtoList[i].getId());				
		}
		
	}
	
	public void scheduleRun(String scheduleId){

		ScheduleJob scheduleJob = new ScheduleJobVo();
		scheduleJob.setId(scheduleId);
		
		scheduleJob = jobManager.getSchedule(scheduleJob);		
		ScheduleJobParams[] scheduleJobParamsList = (ScheduleJobParams[])jobManager.getScheduleParamsList(scheduleJob).toArray(new ScheduleJobParamsVo[0]);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		String paramsKey, paramsValue;
		boolean runFlag;
	
		for(int j=0; j<scheduleJobParamsList.length; j++){
			paramsKey = scheduleJobParamsList[j].getId();
			paramsValue = scheduleJobParamsList[j].getVal();
			
			if("date".equals(scheduleJobParamsList[j].getType())){
				try{
					Integer.parseInt(paramsValue);
					parameterMap.put(paramsKey, paramsValue);
				}catch(Exception e){
					parameterMap.put(paramsKey, new SimpleDateFormat(paramsValue).format(Calendar.getInstance().getTime()));
				}
			}else{
				parameterMap.put(paramsKey, paramsValue);
			}
		}
		
		System.out.println("##################################### JOB : "+scheduleJob.getJobId()+" #####################################");
		
		// Job run
		runFlag = autoJobLauncher.run(scheduleJob.getJobId(), parameterMap, scheduleJob.getRestartYn(), scheduleJob.getRestartCount(), scheduleJob.getRestartTime());
		
		System.out.println("scheduleRun runFlag="+runFlag);
	
		//if(runFlag == true){
		if(true){
			// ���ӽ����� ó��
			/*
			if(Integer.parseInt(scheduleJob.getDependencyScheduleId()) > 0 && !scheduleJob.getId().equals(scheduleJob.getDependencyScheduleId())){
				scheduleRun(scheduleJob.getDependencyScheduleId());
			}
			*/		
			
			// ������ �ѹ�¥�� ����
			if(StringUtil.isNullToString(scheduleJob.getTypeId()).equals("1")){
				//jobManager.removeSchedule(scheduleJob);
			}else if(StringUtil.isNullToString(scheduleJob.getTypeId()).equals("3")){	// ������ �Ѵ�¥�� �Ķ���� ����				
				try {
					scheduleJob.setUseStartDate(CurrentDateTime.getSTDT(CurrentDateTime.getYear()+CurrentDateTime.getMonth(), 1)+CurrentDateTime.getDay());
					jobManager.updateJobScheduleForMonth(scheduleJob);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{			
				// �Ķ���� �ڵ����� ����
				for(int j=0; j<scheduleJobParamsList.length; j++){
					if("Y".equals(scheduleJobParamsList[j].getAutoIncrementYn()) && "date".equals(scheduleJobParamsList[j].getType())){
						try{
							Integer.parseInt(scheduleJobParamsList[j].getVal());
							DateFormat dt = null;
	
							if(scheduleJobParamsList[j].getVal().length() == 6){
								dt = new SimpleDateFormat("yyyyMM");
							}else if(scheduleJobParamsList[j].getVal().length() == 8){
								dt = new SimpleDateFormat("yyyyMMdd");
							}
	
							if(dt != null){
								try {
									Date date = dt.parse(String.valueOf(Integer.parseInt(scheduleJobParamsList[j].getVal()) + 1));
									Calendar cal = Calendar.getInstance();
	
									cal.setTime(date);
									scheduleJobParamsList[j].setVal(dt.format(cal.getTime()));
	
									jobManager.modifyScheduleParams(scheduleJobParamsList[j]);
								} catch (ParseException e) {
									logger.error("JobLauncherDaily.scheduleRun() ParseException : " + e);
								}
							}
						}catch(Exception e){
							logger.error("JobLauncherDaily.scheduleRun() Exception : " + e);
						}
					}
				}
			}
		}
	}

}
