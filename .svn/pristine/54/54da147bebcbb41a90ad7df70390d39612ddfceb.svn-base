/*
 * 
 */
package com.hncis.batch.job.dao.impl;

import java.util.List;

import com.hncis.batch.job.dao.JobDao;
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
import com.hncis.common.dao.CommonDao;
import com.hncis.system.vo.Hncis_Job_Schedule;

// TODO: Auto-generated Javadoc
/**
 * The Class JobDaoImplByIbatis. - 배치 작업에 대한 crud dao
 */
public class JobDaoImplByIbatis extends CommonDao implements JobDao  {
	
	/** The Constant SELECT_JOB_LIST. - sql 작업목록*/
	private final static String SELECT_JOB_LIST = "selectJobList";
	
	/** The Constant SELECT_JOB_DETAIL_LIST_COUNT. - sql 작업목록 */
	private final static String SELECT_JOB_DETAIL_LIST_COUNT = "selectJobDetailListCount";
	
	/** The Constant SELECT_JOB_DETAIL_LIST. - sql 작업상세 목록*/
	private final static String SELECT_JOB_DETAIL_LIST = "selectJobDetailList";
	
	/** The Constant SELECT_JOB_DETAIL_STEP_LIST. - sql 작업상세스텝 목록*/
	private final static String SELECT_JOB_DETAIL_STEP_LIST = "selectJobStepList";
	
	/** The Constant SELECT_JOB_DETAIL_STEP. - sql 작업상세스텝 상세*/
	private final static String SELECT_JOB_DETAIL_STEP = "selectJobStep";
	
	/** The Constant SELECT_JOB_PARAMS_LIST. - sql 작업 파라메터 목록*/
	private final static String SELECT_JOB_PARAMS_LIST = "selectJobParamsList";
	
	/** The Constant UPDATE_CLEAR_JOB. - sql 배치 기존작업 상태변경*/
	private final static String UPDATE_CLEAR_JOB = "updateClearJob";
	
	/** The Constant UPDATE_CLEAR_JOB_CONTEXT. - sql 배치 기존작업 상태변경*/
	private final static String UPDATE_CLEAR_JOB_CONTEXT = "updateClearJobContext";
	
	/** The Constant UPDATE_CLEAR_JOB_STEP. - sql 배치 기존 스텝 상태변경.*/
	private final static String UPDATE_CLEAR_JOB_STEP = "updateClearJobStep";
	
	/** The Constant UPDATE_CLEAR_JOB_STEP_CONTEXT. - sql 배치 기존 스텝 context 상태변경.*/
	private final static String UPDATE_CLEAR_JOB_STEP_CONTEXT = "updateClearJobStepContext";
	
	/** The Constant SELECT_SCHEDULE_LIST. - sql 스케줄 목록*/
	private final static String SELECT_SCHEDULE_LIST = "selectScheduleList";
	
	/** The Constant SELECT_SCHEDULE. - sql 스케줄 상세*/
	private final static String SELECT_SCHEDULE = "selectSchedule";
	
	/** The Constant SELECT_SCHEDULE_PARAMS_LIST. - sql 스케줄 파라메터 목록*/
	private final static String SELECT_SCHEDULE_PARAMS_LIST = "selectScheduleParamsList";
	
	/** The Constant SELECT_DEPENDENCY_SCHEDULE_LIST. - sql 종속배치 목록*/
	private final static String SELECT_DEPENDENCY_SCHEDULE_LIST = "selectDependencyScheduleList";
	
	/** The Constant SELECT_SCHEDULE_NEXT_ID. - sql 스케줄 다음 순번*/
	private final static String SELECT_SCHEDULE_NEXT_ID = "selectScheduleNextId";
	
	/** The Constant INSERT_SCHEDULE. - sql 스케줄 등록*/
	private final static String INSERT_SCHEDULE = "insertSchedule";
	
	/** The Constant UPDATE_SCHEDULE. - sql 스케줄 수정*/
	private final static String UPDATE_SCHEDULE = "updateSchedule";
	
	/** The Constant DELETE_SCHEDULE. - sql 스케줄 삭제*/
	private final static String DELETE_SCHEDULE = "deleteSchedule";
	
	/** The Constant INSERT_SCHEDULE_PARAMS. - sql 스케줄 파라메터 등록*/
	private final static String INSERT_SCHEDULE_PARAMS = "insertScheduleParams";
	
	/** The Constant DELETE_SCHEDULE_PARAMS. - sql 스케줄 파라메터 전체삭제*/
	private final static String DELETE_SCHEDULE_PARAMS = "deleteScheduleParams";
	
	/** The Constant UPDATE_SCHEDULE_PARAMS. - sql 스케줄 파라메터 수정*/
	private final static String UPDATE_SCHEDULE_PARAMS = "updateScheduleParams";

	/** The Constant SELECT_JOB_INFO_LIST. - sql 스케줄 Job 기본정보 목록*/
	private final static String SELECT_JOB_INFO_LIST = "selectJobInfoList";
	
	/** The Constant SELECT_JOB_INFO_PARAMS_LIST. - sql 스케줄 Job 기본정보 파라메터 목록*/
	private final static String SELECT_JOB_INFO_PARAMS_LIST = "selectJobInfoParamsList";
	
	/** The Constant SELECT_JOB_INFO_USE_COUNT. - sql 스케줄 Job 기본정보를 사용중인 스케줄의 갯수*/
	private final static String SELECT_JOB_INFO_USE_COUNT = "selectJobInfoUseCount";
	
	/** The Constant INSERT_JOB_INFO. - sql 스케줄 Job 기본정보 등록*/
	private final static String INSERT_JOB_INFO = "insertJobInfo";
	
	/** The Constant UPDATE_JOB_INFO. - sql 스케줄 Job 기본정보 수정*/
	private final static String UPDATE_JOB_INFO = "updateJobInfo";
	
	/** The Constant DELETE_JOB_INFO. - sql 스케줄 Job 기본정보 삭제*/
	private final static String DELETE_JOB_INFO = "deleteJobInfo";
	
	/** The Constant INSERT_JOB_INFO_PARAMS. - sql 스케줄 Job 기본정보 파라메터 등록*/
	private final static String INSERT_JOB_INFO_PARAMS = "insertJobInfoParams";
	
	/** The Constant DELETE_JOB_INFO_PARAMS. - sql 스케줄 Job 기본정보 파라메터 전체삭제*/
	private final static String DELETE_JOB_INFO_PARAMS = "deleteJobInfoParams";
	
	/** The Constant SELECT_QUARTZ_SCHEDULE_DAILY_LIST. - sql 일 배치 목록*/
	private final static String SELECT_QUARTZ_SCHEDULE_DAILY_LIST = "selectQuartzScheduleDailyList";
	
	/** The Constant SELECT_QUARTZ_SCHEDULE_MONTHLY_LIST. - sql 월 배치 목록*/
	private final static String SELECT_QUARTZ_SCHEDULE_MONTHLY_LIST = "selectQuartzScheduleMonthlyList";
	
	/** The Constant UPDATE_JOB_SCHEDULE. - sql 잡스케줄 type_id update*/
	private final static String UPDATE_JOB_SCHEDULE = "updateJobSchedule";
	
	/** The Constant UPDATE_JOB_SCHEDULE_FOR_MONTH. - sql 잡스케줄 use_start_date update*/
	private final static String UPDATE_JOB_SCHEDULE_FOR_MONTH = "updateJobScheduleForMonth";
	
	/** The Constant UPDATE_JOB_CNT. - sql 처리건수 update*/
	private final static String UPDATE_JOB_CNT = "updateJobCnt";
	
	/** The Constant SELECT_JOB_CNT. - sql 처리건수 select*/
	private final static String SELECT_JOB_CNT = "selectJobCnt";


	/**
	 * batch job 목록을 가져옴.
	 * @return List<Tree>
	 */
	@SuppressWarnings("unchecked")
	public List<Tree> selectJobList() {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_LIST);
	}
	
	/**
	 * 작업 상세 목록 갯수 가져옴.
	 *
	 * @param job the job
	 * @return int
	 */
	public int selectJobDetailListCount(Job job){
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_JOB_DETAIL_LIST_COUNT, job);
	}
	
	/**
	 * 작업 상세 목록 가져옴.
	 *
	 * @param job the job
	 * @return List<JobDetail>
	 */
	@SuppressWarnings("unchecked")
	public List<JobDetail> selectJobDetailList(Job job) {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_DETAIL_LIST, job);
	}

	/**
	 * 작업 상세스텝 목록 가져옴.
	 *
	 * @param jobDetailStep the job detail step
	 * @return List<JobDetailStep>
	 */
	@SuppressWarnings("unchecked")
	public List<JobDetailStep> selectJobDetailStepList(JobDetailStep jobDetailStep) {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_DETAIL_STEP_LIST, jobDetailStep);
	}
	
	/**
	 * 작업상세스텝 상세.
	 *
	 * @param jobDetailStep the job detail step
	 * @return JobDetailStep
	 */
	public JobDetailStep selectJobDetailStep(JobDetailStep jobDetailStep) {
		return (JobDetailStep)getSqlMapClientTemplate().queryForObject(SELECT_JOB_DETAIL_STEP, jobDetailStep);
	}
	
	/**
	 * 작업 파라메터 목록 가져옴.
	 *
	 * @param job the job
	 * @return List<JobParams>
	 */
	@SuppressWarnings("unchecked")
	public List<JobParams> selectJobParamsList(Job job) {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_PARAMS_LIST, job);
	}
	
	// ############################################################################
	// # s:��ġ �����۾� ���º��� (��ġ�� �������� �ٽ� ���������� ����)
	// ############################################################################
	
	/**
	 * 배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정).
	 *
	 * @param job the job
	 * @return List<JobParams>
	 */
	public void updateClearJob(Job job) {
		getSqlMapClientTemplate().update(UPDATE_CLEAR_JOB, job);
	}
	
	/**
	 * 배치 기존작업 상태변경 (배치를 수동으로 다시 돌리기위한 수정).
	 *
	 * @param job the job
	 * @return List<JobParams>
	 */
	public void updateClearJobContext(Job job) {
		getSqlMapClientTemplate().update(UPDATE_CLEAR_JOB_CONTEXT, job);
	}
	
	/**
	 * 배치 기존 스텝 상태변경.
	 *
	 * @param job the job
	 * @return List<JobParams>
	 */
	public void updateClearJobStep(Job job) {
		getSqlMapClientTemplate().update(UPDATE_CLEAR_JOB_STEP, job);
	}
	
	/**
	 * 배치 기존 스텝 context 상태변경.
	 *
	 * @param job the job
	 * @return List<JobParams>
	 */
	public void updateClearJobStepContext(Job job) {
		getSqlMapClientTemplate().update(UPDATE_CLEAR_JOB_STEP_CONTEXT, job);
	}
	// ############################################################################
	// # e:��ġ �����۾� ���º��� (��ġ�� �������� �ٽ� ���������� ����)
	// ############################################################################
	
	/**
	 * 스케줄 목록 조회.
	 *
	 * @return List<ScheduleJob>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleJob> selectScheduleList() {
		return getSqlMapClientTemplate().queryForList(SELECT_SCHEDULE_LIST);
	}
	
	/**
	 * 스케줄 상세 조회.
	 *
	 * @param scheduleJob the schedule job
	 * @return ScheduleJob
	 */
	public ScheduleJob selectSchedule(ScheduleJob scheduleJob) {
		return (ScheduleJob)getSqlMapClientTemplate().queryForObject(SELECT_SCHEDULE, scheduleJob);
	}
	
	/**
	 * 스케줄 파라메터 목록 조회.
	 *
	 * @param scheduleJob the schedule job
	 * @return List<ScheduleJobParams>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleJobParams> selectScheduleParamsList(ScheduleJob scheduleJob) {
		return getSqlMapClientTemplate().queryForList(SELECT_SCHEDULE_PARAMS_LIST, scheduleJob);
	}
	
	/**
	 * 종속배치 목록 가져옴.
	 *
	 * @param scheduleJob the schedule job
	 * @return List<ScheduleJob>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleJob> selectDependencyScheduleList(ScheduleJob scheduleJob) {
		return getSqlMapClientTemplate().queryForList(SELECT_DEPENDENCY_SCHEDULE_LIST, scheduleJob);
	}
	
	/**
	 * 스케줄 다음 순번 조회.
	 *
	 * @return int
	 */
	public int selectScheduleNextId() {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_SCHEDULE_NEXT_ID);
	}
	
	/**
	 * 스케줄 등록.
	 *
	 * @param scheduleJob the schedule job
	 */
	public void insertSchedule(ScheduleJob scheduleJob) {
		getSqlMapClientTemplate().insert(INSERT_SCHEDULE, scheduleJob);
	}
	
	/**
	 * 스케줄 수정.
	 *
	 * @param scheduleJob the schedule job
	 */
	public void updateSchedule(ScheduleJob scheduleJob) {
		getSqlMapClientTemplate().update(UPDATE_SCHEDULE, scheduleJob);
	}
	
	/**
	 * 스케줄 삭제.
	 *
	 * @param scheduleJob the schedule job
	 */
	public void deleteSchedule(ScheduleJob scheduleJob) {
		getSqlMapClientTemplate().delete(DELETE_SCHEDULE, scheduleJob);
	}
	
	/**
	 * 스케줄 파라메터 등록.
	 *
	 * @param scheduleJobParams the schedule job params
	 */
	public void insertScheduleParams(ScheduleJobParams scheduleJobParams) {
		getSqlMapClientTemplate().insert(INSERT_SCHEDULE_PARAMS, scheduleJobParams);
	}
	
	/**
	 * 스케줄 파라메터 전체삭제.
	 *
	 * @param scheduleJob the schedule job
	 */
	public void deleteScheduleParams(ScheduleJob scheduleJob) {
		getSqlMapClientTemplate().insert(DELETE_SCHEDULE_PARAMS, scheduleJob);
	}
	
	/**
	 * 스케줄 파라메터 수정.
	 *
	 * @param scheduleJobParams the schedule job params
	 */
	public void updateScheduleParams(ScheduleJobParams scheduleJobParams) {
		getSqlMapClientTemplate().update(UPDATE_SCHEDULE_PARAMS, scheduleJobParams);
	}
	
	/**
	 * 스케줄 Job 기본정보 목록 조회.
	 *
	 * @param scheduleJobInfo the schedule job info
	 * @return List<ScheduleJobInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleJobInfo> selectJobInfoList(ScheduleJobInfo scheduleJobInfo) {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_INFO_LIST, scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 기본정보 파라메터 목록 조회.
	 *
	 * @param scheduleJobInfoParams the schedule job info params
	 * @return List<ScheduleJobInfoParams>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleJobInfoParams> selectJobInfoParamsList(ScheduleJobInfoParams scheduleJobInfoParams) {
		return getSqlMapClientTemplate().queryForList(SELECT_JOB_INFO_PARAMS_LIST, scheduleJobInfoParams);
	}
	
	/**
	 * 스케줄 Job 기본정보를 사용중인 스케줄의 갯수 조회.
	 *
	 * @param scheduleJobInfo the schedule job info
	 * @return int
	 */
	public int selectJobInfoUseCount(ScheduleJobInfo scheduleJobInfo) {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_JOB_INFO_USE_COUNT, scheduleJobInfo);
	}

	/**
	 * 스케줄 Job 기본정보 등록.
	 *
	 * @param scheduleJobInfo the schedule job info
	 */
	public void insertJobInfo(ScheduleJobInfo scheduleJobInfo) {
		getSqlMapClientTemplate().insert(INSERT_JOB_INFO, scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 기본정보 수정.
	 *
	 * @param scheduleJobInfo the schedule job info
	 */
	public void updateJobInfo(ScheduleJobInfo scheduleJobInfo) {
		getSqlMapClientTemplate().update(UPDATE_JOB_INFO, scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 기본정보 삭제.
	 *
	 * @param scheduleJobInfo the schedule job info
	 */
	public void deleteJobInfo(ScheduleJobInfo scheduleJobInfo) {
		getSqlMapClientTemplate().delete(DELETE_JOB_INFO, scheduleJobInfo);
	}
	
	/**
	 * 스케줄 Job 기본정보 파라메터 등록.
	 *
	 * @param scheduleJobInfoParams the schedule job info params
	 */
	public void insertJobInfoParams(ScheduleJobInfoParams scheduleJobInfoParams) {
		getSqlMapClientTemplate().insert(INSERT_JOB_INFO_PARAMS, scheduleJobInfoParams);
	}
	
	/**
	 * 스케줄 Job 기본정보 파라메터 전체삭제.
	 *
	 * @param scheduleJobInfo the schedule job info
	 */
	public void deleteJobInfoParams(ScheduleJobInfo scheduleJobInfo) {
		getSqlMapClientTemplate().delete(DELETE_JOB_INFO_PARAMS, scheduleJobInfo);
	}
	
	/**
	 * 일 배치 목록 조회.
	 *
	 * @param scheduleDto the schedule dto
	 * @return List<ScheduleDto>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleDto> selectQuartzScheduleDailyList(ScheduleDto scheduleDto) {
		return getSqlMapClientTemplate().queryForList(SELECT_QUARTZ_SCHEDULE_DAILY_LIST, scheduleDto);
	}
	
	/**
	 * 월 배치 목록 조회.
	 *
	 * @param scheduleDto the schedule dto
	 * @return List<ScheduleDto>
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleDto> selectQuartzScheduleMonthlyList(ScheduleDto scheduleDto) {
		return getSqlMapClientTemplate().queryForList(SELECT_QUARTZ_SCHEDULE_MONTHLY_LIST, scheduleDto);
	}
	
	/**
	 * 잡스케줄 type_id update.
	 *
	 * @param hncis_Job_Schedule the hncis_ job_ schedule
	 * @return int
	 * @throws Exception the exception
	 */
	public int updateJobSchedule(Hncis_Job_Schedule hncis_Job_Schedule) throws Exception{
		return update(UPDATE_JOB_SCHEDULE, hncis_Job_Schedule);
	}
	
	/**
	 * 잡스케줄 use_start_date update.
	 *
	 * @param scheduleJob the schedule job
	 * @return int
	 * @throws Exception the exception
	 */
	public int updateJobScheduleForMonth(ScheduleJob scheduleJob) throws Exception{
		return update(UPDATE_JOB_SCHEDULE_FOR_MONTH, scheduleJob);
	}
	
	/**
	 * 처리건수 update.
	 *
	 * @param job the job
	 * @return int
	 * @throws Exception the exception
	 */
	public int updateJobCnt(Job job) throws Exception{
		return update(UPDATE_JOB_CNT, job);
	}
	
	/**
	 * 처리건수 select.
	 *
	 * @param job the job
	 * @return int
	 * @throws Exception the exception
	 */
	public int getJobCnt(Job job) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_JOB_CNT, job);
	}


}
