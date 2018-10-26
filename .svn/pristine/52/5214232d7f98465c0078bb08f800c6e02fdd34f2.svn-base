package com.hncis.batch.job.dao;

import java.util.List;

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
 * 인터페이스 Dao
 */
public interface JobDao {
	
	/**
	 * batch job 목록을 가져옴.
	 * @return List<Tree>
	 */
	public List<Tree> selectJobList();
	
	/**
	 * 작업 상세 목록 갯수 가져옴.
	 * @param job
	 * @return int
	 */
	public int selectJobDetailListCount(Job job);
	
	/**
	 * 작업 상세 목록 가져옴.
	 * @param job
	 * @return List<JobDetail>
	 */
	public List<JobDetail> selectJobDetailList(Job job);
	
	/**
	 * 작업 상세스텝 목록 가져옴.
	 * @param jobDetailStep
	 * @return List<JobDetailStep>
	 */
	public List<JobDetailStep> selectJobDetailStepList(JobDetailStep jobDetailStep);
	
	/**
	 * 작업상세스텝 상세
	 * @param jobDetailStep
	 * @return JobDetailStep
	 */
	public JobDetailStep selectJobDetailStep(JobDetailStep jobDetailStep);
	
	/**
	 * 작업 파라메터 목록 가져옴.
	 * @param job
	 * @return List<JobParams>
	 */
	public List<JobParams> selectJobParamsList(Job job);
	
	// ############################################################################
	// # s:��ġ �����۾� ���º��� (��ġ�� �������� �ٽ� ���������� ����)
	// ############################################################################
	
	/**
	 * 배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정)
	 * @param job
	 * @return List<JobParams>
	 */
	public void updateClearJob(Job job);
	
	/**
	 * 배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정)
	 * @param job
	 * @return List<JobParams>
	 */
	public void updateClearJobContext(Job job);
	
	/**
	 * 배치 기존 스텝 상태변경 
	 * @param job
	 * @return List<JobParams>
	 */
	public void updateClearJobStep(Job job);
	
	/**
	 * 배치 기존 스텝 context 상태변경 
	 * @param job
	 * @return List<JobParams>
	 */
	public void updateClearJobStepContext(Job job);
	// ############################################################################
	// # e:��ġ �����۾� ���º��� (��ġ�� �������� �ٽ� ���������� ����)
	// ############################################################################
	
	/**
	 * 스케줄 목록 조회
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> selectScheduleList();
	
	/**
	 * 스케줄 상세 조회
	 * @param scheduleJob
	 * @return ScheduleJob
	 */
	public ScheduleJob selectSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 파라메터 목록 조회
	 * @param scheduleJob
	 * @return List<ScheduleJobParams>
	 */
	public List<ScheduleJobParams> selectScheduleParamsList(ScheduleJob scheduleJob);
	
	/**
	 * 종속배치 목록 가져옴.
	 * @param scheduleJob
	 * @return List<ScheduleJob>
	 */
	public List<ScheduleJob> selectDependencyScheduleList(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 Job 기본정보를 사용중인 스케줄의 갯수 조회
	 * @param scheduleJobInfo
	 * @return int
	 */
	public int selectJobInfoUseCount(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 다음 순번 조회
	 * @return int
	 */
	public int selectScheduleNextId();
	
	/**
	 * 스케줄 등록
	 * @param scheduleJob
	 */
	public void insertSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 수정
	 * @param scheduleJob
	 */
	public void updateSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 삭제
	 * @param scheduleJob
	 */
	public void deleteSchedule(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 파라메터 등록
	 * @param scheduleJobParams
	 */
	public void insertScheduleParams(ScheduleJobParams scheduleJobParams);
	
	/**
	 * 스케줄 파라메터 전체삭제
	 * @param scheduleJob
	 */
	public void deleteScheduleParams(ScheduleJob scheduleJob);
	
	/**
	 * 스케줄 파라메터 수정
	 * @param scheduleJobParams
	 */
	public void updateScheduleParams(ScheduleJobParams scheduleJobParams);
	
	/**
	 * 스케줄 Job 기본정보 목록 조회
	 * @param scheduleJobInfo
	 * @return List<ScheduleJobInfo>
	 */
	public List<ScheduleJobInfo> selectJobInfoList(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 기본정보 파라메터 목록 조회
	 * @param scheduleJobInfoParams
	 * @return List<ScheduleJobInfoParams>
	 */
	public List<ScheduleJobInfoParams> selectJobInfoParamsList(ScheduleJobInfoParams scheduleJobInfoParams);
	
	/**
	 * 스케줄 Job 기본정보 등록
	 * @param scheduleJobInfo
	 */
	public void insertJobInfo(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 기본정보 수정
	 * @param scheduleJobInfo
	 */
	public void updateJobInfo(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 기본정보 삭제
	 * @param scheduleJobInfo
	 */
	public void deleteJobInfo(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 스케줄 Job 기본정보 파라메터 등록
	 * @param scheduleJobInfoParams
	 */
	public void insertJobInfoParams(ScheduleJobInfoParams scheduleJobInfoParams);
	
	/**
	 * 스케줄 Job 기본정보 파라메터 전체삭제
	 * @param scheduleJobInfo
	 */
	public void deleteJobInfoParams(ScheduleJobInfo scheduleJobInfo);
	
	/**
	 * 일 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> selectQuartzScheduleDailyList(ScheduleDto scheduleDto);
	
	/**
	 * 월 배치 목록 조회
	 * @param scheduleDto
	 * @return List<ScheduleDto>
	 */
	public List<ScheduleDto> selectQuartzScheduleMonthlyList(ScheduleDto scheduleDto);
	
	/**
	 * 잡스케줄 type_id update
	 * @param hncis_Job_Schedule
	 * @return int
	 */
	public int updateJobSchedule(Hncis_Job_Schedule hncis_Job_Schedule) throws Exception;
	
	/**
	 * 잡스케줄 use_start_date update
	 * @param ScheduleJob
	 * @return int
	 */
	public int updateJobScheduleForMonth(ScheduleJob scheduleJob) throws Exception;
	
	/**
	 * 처리건수 update
	 * @param Job
	 * @return int
	 */
	public int updateJobCnt(Job job) throws Exception;
	
	/**
	 * 처리건수 select
	 * @param job
	 * @return int
	 */
	public int getJobCnt(Job job) throws Exception;
	
}
