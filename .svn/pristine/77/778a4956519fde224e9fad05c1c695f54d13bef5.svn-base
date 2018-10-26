package com.hncis.batch.job.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import com.hncis.batch.job.dao.JobDao;
import com.hncis.batch.job.manager.JobManager;
import com.hncis.batch.job.vo.Job;
import com.hncis.batch.job.vo.JobDetail;
import com.hncis.batch.job.vo.JobDetailStep;
import com.hncis.batch.job.vo.JobParams;
import com.hncis.batch.job.vo.ScheduleJob;
import com.hncis.batch.job.vo.ScheduleJobInfo;
import com.hncis.batch.job.vo.ScheduleJobInfoParams;
import com.hncis.batch.job.vo.ScheduleJobParams;
import com.hncis.batch.job.vo.Tree;
import com.hncis.batch.job.vo.dto.ScheduleDto;
import com.hncis.system.vo.Hncis_Job_Schedule;

@Service("jobManagerImpl")
public class JobManagerImpl implements JobManager {
	
	/** The job dao 변수 */
	@Autowired
	private JobDao jobDao;
	
	/**
	 * batch job 목록을 가져옴.
	 * @return List<Tree>
	 */
	public List<Tree> getJobList(){
		return jobDao.selectJobList();
	}
	
	/**
	 * 작업 상세 목록 갯수 가져옴.
	 * @param job
	 * @return int
	 */
	public int getJobDetailListCount(Job job){
		return jobDao.selectJobDetailListCount(job);
	}
	
	/**
	 * 작업 상세 목록 가져옴.
	 * @param job
	 * @return List<JobDetail>
	 */
	public List<JobDetail> getJobDetailList(Job job){
		return jobDao.selectJobDetailList(job);
	}
	
	/**
	 * 작업 상세스텝 목록 가져옴.
	 * @param jobDetailStep
	 * @return List<JobDetailStep>
	 */
	public List<JobDetailStep> getJobDetailStepList(JobDetailStep jobDetailStep){
		return jobDao.selectJobDetailStepList(jobDetailStep);
	}
	
	/**
	 * 작업상세스텝 상세 조회
	 * @param jobDetailStep
	 * @return JobDetailStep
	 */
	public JobDetailStep getJobDetailStep(JobDetailStep jobDetailStep){
		return jobDao.selectJobDetailStep(jobDetailStep);
	}
	
	/**
	 * 작업 파라메터 목록 가져옴.
	 * @param job
	 * @return List<JobParams>
	 */
	public List<JobParams> getJobParamsList(Job job){
		return jobDao.selectJobParamsList(job);
	}
	
	/**
	 * job clear를 함 (FAILED 경우 clear 작업)
	 * @param job
	 */
	public void modifyJobClear(Job job){
		//배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정)
		jobDao.updateClearJob(job);
		//배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정)
		jobDao.updateClearJobContext(job);
		//배치 기존 스텝 context 상태변경 
		jobDao.updateClearJobStep(job);
		//배치 기존 스텝 context 상태변경
		jobDao.updateClearJobStepContext(job);
	}
	
	/**
	 * 스케줄 목록 조회
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> getScheduleList(){
		return jobDao.selectScheduleList();
	}
	
	/**
	 * 스케줄 상세 조회
	 * @param scheduleJob
	 * @return ScheduleJob
	 */
	public ScheduleJob getSchedule(ScheduleJob scheduleJob){
		return jobDao.selectSchedule(scheduleJob);
	}
	
	/**
	 * 스케줄 파라메터 목록 조회
	 * @param scheduleJob
	 * @return List<ScheduleJobParams>
	 */
	public List<ScheduleJobParams> getScheduleParamsList(ScheduleJob scheduleJob){
		return jobDao.selectScheduleParamsList(scheduleJob);
	}
	
	/**
	 * 종속배치 목록 가져옴.
	 * @param scheduleJob
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> getDependencyScheduleList(ScheduleJob scheduleJob){
		return jobDao.selectDependencyScheduleList(scheduleJob);
	}
	
	/**
	 * 스케줄 등록
	 * @param scheduleJob
	 * @param scheduleJobParamsList
	 */
	public void addSchedule(ScheduleJob scheduleJob, ScheduleJobParams[] scheduleJobParamsList){
		scheduleJob.setId(Integer.toString(jobDao.selectScheduleNextId()));
		//스케줄 등록
		jobDao.insertSchedule(scheduleJob);
		
		//스케줄 파라메터 등록
		for(int i=0; i<scheduleJobParamsList.length; i++){
			scheduleJobParamsList[i].setScheduleId(scheduleJob.getId());
			jobDao.insertScheduleParams(scheduleJobParamsList[i]);
		}
	}
	
	/**
	 * 스케줄 Job - 수정
	 * @param scheduleJob
	 * @param scheduleJobParamsList
	 */
	public void modifySchedule(ScheduleJob scheduleJob, ScheduleJobParams[] scheduleJobParamsList){
		//스케줄 파라메터 전체삭제
		jobDao.deleteScheduleParams(scheduleJob);
		//스케줄 수정
		jobDao.updateSchedule(scheduleJob);
		
		//스케줄 파라메터 등록
		for(int i=0; i<scheduleJobParamsList.length; i++){
			scheduleJobParamsList[i].setScheduleId(scheduleJob.getId());
			jobDao.insertScheduleParams(scheduleJobParamsList[i]);
		}
	}
	
	/**
	 * 스케줄 삭제
	 * @param scheduleJob
	 */
	public void removeSchedule(ScheduleJob scheduleJob){
		//스케줄 파라메터 전체삭제
		jobDao.deleteScheduleParams(scheduleJob);
		//스케줄 삭제
		jobDao.deleteSchedule(scheduleJob);
	}
	
	/**
	 * 스케줄 Job 기본정보 목록 조회
	 * @param scheduleJobInfo
	 * @return List<ScheduleJobInfo>
	 */
	public List<ScheduleJobInfo> getJobInfoList(ScheduleJobInfo scheduleJobInfo){
		return jobDao.selectJobInfoList(scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 기본정보 파라메터 목록 조회
	 * @param scheduleJobInfoParams
	 * @return List<ScheduleJobInfoParams>
	 */
	public List<ScheduleJobInfoParams> getJobInfoParamsList(ScheduleJobInfoParams scheduleJobInfoParams){
		return jobDao.selectJobInfoParamsList(scheduleJobInfoParams);
	}
	
	/**
	 * 스케줄 Job 기본정보를 사용중인 스케줄의 갯수 조회
	 * @param scheduleJobInfo
	 * @return int
	 */
	public int getJobInfoUseCount(ScheduleJobInfo scheduleJobInfo){
		return jobDao.selectJobInfoUseCount(scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 정보 등록
	 * @param scheduleJobInfo
	 * @param scheduleJobInfoParamsList
	 */
	public void addJobInfo(ScheduleJobInfo scheduleJobInfo, ScheduleJobInfoParams[] scheduleJobInfoParamsList){
		//스케줄 Job 기본정보 등록
		jobDao.insertJobInfo(scheduleJobInfo);
		
		//스케줄 Job parameter 등록
		for(int i=0; i<scheduleJobInfoParamsList.length; i++){
			scheduleJobInfoParamsList[i].setJobId(scheduleJobInfo.getId());
			jobDao.insertJobInfoParams(scheduleJobInfoParamsList[i]);
		}
	}
	
	/**
	 * 스케줄 Job 기본정보 수정
	 * @param scheduleJobInfo
	 * @param scheduleJobInfoParamsList
	 */
	public void modifyJobInfo(ScheduleJobInfo scheduleJobInfo, ScheduleJobInfoParams[] scheduleJobInfoParamsList){
		//스케줄 Job 기본정보 파라메터 전체삭제
		jobDao.deleteJobInfoParams(scheduleJobInfo);
		//스케줄 Job 기본정보 수정
		jobDao.updateJobInfo(scheduleJobInfo);
		
		//스케줄 Job 기본정보 파라메터 등록
		for(int i=0; i<scheduleJobInfoParamsList.length; i++){
			scheduleJobInfoParamsList[i].setJobId(scheduleJobInfo.getId());
			jobDao.insertJobInfoParams(scheduleJobInfoParamsList[i]);
		}
	}
	
	/**
	 * 스케줄 Job 기본정보 삭제
	 * @param scheduleJobInfo
	 */
	public void removeJobInfo(ScheduleJobInfo scheduleJobInfo){
		//스케줄 Job 기본정보 파라메터 전체삭제
		jobDao.deleteJobInfoParams(scheduleJobInfo);
		//스케줄 Job 기본정보 삭제
		jobDao.deleteJobInfo(scheduleJobInfo);
	}
	
	/**
	 * 일 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> getQuartzScheduleDailyList(ScheduleDto scheduleDto){
		return jobDao.selectQuartzScheduleDailyList(scheduleDto);
	}
	
	/**
	 * 월 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> getQuartzScheduleMonthlyList(ScheduleDto scheduleDto){
		return jobDao.selectQuartzScheduleMonthlyList(scheduleDto);
	}
	
	/**
	 * 스케줄 파라메터 수정
	 * @param scheduleJobParams
	 */
	public void modifyScheduleParams(ScheduleJobParams scheduleJobParams){
		jobDao.updateScheduleParams(scheduleJobParams);
	}
	
	/**
	 * 잡스케줄 type_id update
	 * @param hncis_Job_Schedule
	 * @throws Exception 
	 */
	public void updateJobSchedule(Hncis_Job_Schedule hncis_Job_Schedule) throws Exception{
		jobDao.updateJobSchedule(hncis_Job_Schedule);
	}
	
	/**
	 * 잡스케줄 use_start_date update
	 * @param ScheduleJob
	 * @throws Exception 
	 */
	public void updateJobScheduleForMonth(ScheduleJob scheduleJob) throws Exception{
		jobDao.updateJobScheduleForMonth(scheduleJob);
	}
	
	/**
	 * 처리건수 update
	 * @param Job
	 * @throws Exception 
	 */
	public void updateJobCnt(Job job) throws Exception{
		jobDao.updateJobCnt(job);
	}
	
	/**
	 * 처리건수 select
	 * @param job
	 * @return int
	 */
	public int getJobCnt(Job job)  throws Exception{ 
		return jobDao.getJobCnt(job);
	}

}
