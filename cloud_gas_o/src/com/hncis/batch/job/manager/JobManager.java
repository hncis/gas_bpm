package com.hncis.batch.job.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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

/**
 * 인터페이스 Manager 
 */
@Transactional
public interface JobManager {
	
	/**
	 * batch job 목록을 가져옴.
	 * @return List<Tree>
	 */
	public List<Tree> getJobList();
	
	/**
	 * 작업 상세 목록 갯수 가져옴.
	 * @param job
	 * @return int
	 */
	public int getJobDetailListCount(Job job);
	
	/**
	 * 작업 상세 목록 가져옴.
	 * @param job
	 * @return List<JobDetail>
	 */
	public List<JobDetail> getJobDetailList(Job job);
	
	/**
	 * 작업 상세스텝 목록 가져옴.
	 * @param jobDetailStep
	 * @return List<JobDetailStep>
	 */
	public List<JobDetailStep> getJobDetailStepList(JobDetailStep jobDetailStep);
	
	/**
	 * 작업상세스텝 상세 조회
	 * @param jobDetailStep
	 * @return JobDetailStep
	 */
	public JobDetailStep getJobDetailStep(JobDetailStep jobDetailStep);
	
	/**
	 * 작업 파라메터 목록 가져옴.
	 * @param job
	 * @return List<JobParams>
	 */
	public List<JobParams> getJobParamsList(Job job);
	
	/**
	 * 종속배치 목록 가져옴.
	 * @param scheduleJob
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> getDependencyScheduleList(ScheduleJob scheduleJob);
	
	/**
	 * job clear를 함 (FAILED 경우 clear 작업)
	 * @param job
	 */
	public void modifyJobClear(Job job);
	
	/**
	 * 스케줄 목록 조회
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> getScheduleList();

	/**
	 * 스케줄 상세 조회
	 * @param scheduleJob
	 * @return ScheduleJob
	 */
	public ScheduleJob getSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 파라메터 목록 조회
	 * @param scheduleJob
	 * @return List<ScheduleJobParams>
	 */
	public List<ScheduleJobParams> getScheduleParamsList(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 등록
	 * @param scheduleJob
	 * @param scheduleJobParamsList
	 */
	public void addSchedule(ScheduleJob scheduleJob, ScheduleJobParams[] scheduleJobParamsList);
	
	/**
	 * 스케줄 Job - 수정
	 * @param scheduleJob
	 * @param scheduleJobParamsList
	 */
	public void modifySchedule(ScheduleJob scheduleJob, ScheduleJobParams[] scheduleJobParamsList);
	
	/**
	 * 스케줄 삭제
	 * @param scheduleJob
	 */
	public void removeSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 Job 기본정보 목록 조회
	 * @param scheduleJobInfo
	 * @return List<ScheduleJobInfo>
	 */
	public List<ScheduleJobInfo> getJobInfoList(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 기본정보 파라메터 목록 조회
	 * @param scheduleJobInfoParams
	 * @return List<ScheduleJobInfoParams>
	 */
	public List<ScheduleJobInfoParams> getJobInfoParamsList(ScheduleJobInfoParams scheduleJobInfoParams);
	
	/**
	 * 스케줄 Job 기본정보를 사용중인 스케줄의 갯수 조회
	 * @param scheduleJobInfo
	 * @return int
	 */
	public int getJobInfoUseCount(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 정보 등록
	 * @param scheduleJobInfo
	 * @param scheduleJobInfoParamsList
	 */
	public void addJobInfo(ScheduleJobInfo scheduleJobInfo, ScheduleJobInfoParams[] scheduleJobInfoParamsList);
	
	/**
	 * 스케줄 Job 기본정보 수정
	 * @param scheduleJobInfo
	 * @param scheduleJobInfoParamsList
	 */
	public void modifyJobInfo(ScheduleJobInfo scheduleJobInfo, ScheduleJobInfoParams[] scheduleJobInfoParamsList);
	
	/**
	 * 스케줄 Job 기본정보 삭제
	 * @param scheduleJobInfo
	 */
	public void removeJobInfo(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 일 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> getQuartzScheduleDailyList(ScheduleDto scheduleDto);
	
	/**
	 * 월 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> getQuartzScheduleMonthlyList(ScheduleDto scheduleDto);

	/**
	 * 스케줄 파라메터 수정
	 * @param scheduleJobParams
	 */
	public void modifyScheduleParams(ScheduleJobParams scheduleJobParams);
	
	/**
	 * 잡스케줄 type_id update
	 * @param scheduleJobParams
	 */
	public void updateJobSchedule(Hncis_Job_Schedule hncis_Job_Schedule) throws Exception;
	
	/**
	 * 잡스케줄 use_start_date update
	 * @param ScheduleJob
	 */
	public void updateJobScheduleForMonth(ScheduleJob scheduleJob) throws Exception;
	
	/**
	 * 처리건수 update
	 * @param Job
	 */
	public void updateJobCnt(Job job) throws Exception;
	
	/**
	 * 처리건수 select
	 * @param job
	 * @return int
	 */
	public int getJobCnt(Job job) throws Exception;
}
