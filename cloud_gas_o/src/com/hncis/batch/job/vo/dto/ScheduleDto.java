package com.hncis.batch.job.vo.dto;

import java.util.Date;

import com.hncis.batch.job.vo.impl.ScheduleJobParamsVo;

// TODO: Auto-generated Javadoc
/**
 * The Class ScheduleDto. - Schedule Vo
 */
public class ScheduleDto {
	
	/** The id. - 순번*/
	private String id;					
	
	/** The job id. */
	private String jobId;				
	
	/** The type id. - 스케쥴 유형*/
	private String typeId;				
	
	/** The description. - 설명*/
	private String description;			
	
	/** The use start date. - 시작시간 */
	private String useStartDate;		
	
	/** The use end date. - 종료시간*/
	private String useEndDate;			
	
	/** The val. - 변수*/
	private String val;					
	
	/** The use yn. - 사용여부 */
	private String useYn;				
	
	/** The dependency schedule id. - 의존하는 스케쥴 id */
	private String dependencyScheduleId;
	
	/** The restart yn. - 재시작 여부 */
	private String restartYn;			
	
	/** The restart count. - 재시작 횟수*/
	private int restartCount;			
	
	/** The restart time. - 재시작 시간*/
	private int restartTime;			
	
	/** The sys creation date. - 생성 시간(시스템) */
	private Date sysCreationDate;
	
	/** The sys update date. - 수정 시간(시스템) */
	private Date sysUpdateDate;
	
	/** The dl service code. - 서비스 코드 */
	private String dlServiceCode;
	
	/** The app service code. - 서비스 코드*/
	private String appServiceCode;
	
	/** The operator id. - 실행 id */
	private String operatorId;
	
	/** The schedule job params vo list. - 스케쥴 잡 파라미터 목록 */
	private ScheduleJobParamsVo[] scheduleJobParamsVoList;
	
	/** The now date. - 현재 시간 */
	private String nowDate;
	
	/**
	 * Gets the id.
	 * 순번 가져옴
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 * 순번 설정
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the job id.
	 * job id가져옴
	 * @return the job id
	 */
	public String getJobId() {
		return jobId;
	}
	
	/**
	 * Sets the job id.
	 * job id 설정
	 * @param jobId the new job id
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	/**
	 * Gets the type id.
	 * 스케쥴 유형 가져옴
	 * @return the type id
	 */
	public String getTypeId() {
		return typeId;
	}
	
	/**
	 * Sets the type id.
	 * 스케쥴 유형 설정
	 * @param typeId the new type id
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * Gets the description.
	 * 설명 가져옴
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 * 설명 설정
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the use start date.
	 * 시작시간 가져옴
	 * @return the use start date
	 */
	public String getUseStartDate() {
		return useStartDate;
	}
	
	/**
	 * Sets the use start date.
	 * 시작시간 설정
	 * @param useStartDate the new use start date
	 */
	public void setUseStartDate(String useStartDate) {
		this.useStartDate = useStartDate;
	}
	
	/**
	 * Gets the use end date.
	 * 종료시간 가져옴
	 * @return the use end date
	 */
	public String getUseEndDate() {
		return useEndDate;
	}
	
	/**
	 * Sets the use end date.
	 * 종료시간 설정
	 * @param useEndDate the new use end date
	 */
	public void setUseEndDate(String useEndDate) {
		this.useEndDate = useEndDate;
	}
	
	/**
	 * Gets the val.
	 * 변수 가져옴
	 * @return the val
	 */
	public String getVal() {
		return val;
	}
	
	/**
	 * Sets the val.
	 * 변수 설정
	 * @param val the new val
	 */
	public void setVal(String val) {
		this.val = val;
	}
	
	/**
	 * Gets the use yn.
	 * 사용여부 가져옴
	 * @return the use yn
	 */
	public String getUseYn() {
		return useYn;
	}
	
	/**
	 * Sets the use yn.
	 * 사용여부 설정
	 * @param useYn the new use yn
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	/**
	 * Gets the dependency schedule id.
	 * 의존하는 스케쥴 id 가져옴
	 * @return the dependency schedule id
	 */
	public String getDependencyScheduleId() {
		return dependencyScheduleId;
	}
	
	/**
	 * Sets the dependency schedule id.
	 * 의존하는 스케쥴 id 설정
	 * @param dependencyScheduleId the new dependency schedule id
	 */
	public void setDependencyScheduleId(String dependencyScheduleId) {
		this.dependencyScheduleId = dependencyScheduleId;
	}
	
	/**
	 * Gets the restart yn.
	 * 재시작 여부 가져옴
	 * @return the restart yn
	 */
	public String getRestartYn() {
		return restartYn;
	}
	
	/**
	 * Sets the restart yn.
	 * 재시작 여부 설정
	 * @param restartYn the new restart yn
	 */
	public void setRestartYn(String restartYn) {
		this.restartYn = restartYn;
	}
	
	/**
	 * Gets the restart count.
	 * 재시작 횟수 가져옴
	 * @return the restart count
	 */
	public int getRestartCount() {
		return restartCount;
	}
	
	/**
	 * Sets the restart count.
	 * 재시작 횟수 설정
	 * @param restartCount the new restart count
	 */
	public void setRestartCount(int restartCount) {
		this.restartCount = restartCount;
	}
	
	/**
	 * Gets the restart time.
	 * 재시작 시간 가져옴
	 * @return the restart time
	 */
	public int getRestartTime() {
		return restartTime;
	}
	
	/**
	 * Sets the restart time.
	 * 재시작 시간 설정
	 * @param restartTime the new restart time
	 */
	public void setRestartTime(int restartTime) {
		this.restartTime = restartTime;
	}
	
	/**
	 * Gets the sys creation date.
	 * 생성 시간(시스템) 가져옴
	 * @return the sys creation date
	 */
	public Date getSysCreationDate() {
		return sysCreationDate;
	}
	
	/**
	 * Sets the sys creation date.
	 * 생성 시간(시스템) 설정
	 * @param sysCreationDate the new sys creation date
	 */
	public void setSysCreationDate(Date sysCreationDate) {
		this.sysCreationDate = sysCreationDate;
	}
	
	/**
	 * Gets the sys update date.
	 * 수정 시간(시스템) 가져옴
	 * @return the sys update date
	 */
	public Date getSysUpdateDate() {
		return sysUpdateDate;
	}
	
	/**
	 * Sets the sys update date.
	 * 수정 시간(시스템) 설정
	 * @param sysUpdateDate the new sys update date
	 */
	public void setSysUpdateDate(Date sysUpdateDate) {
		this.sysUpdateDate = sysUpdateDate;
	}
	
	/**
	 * Gets the dl service code.
	 * 서비스 코드 가져옴
	 * @return the dl service code
	 */
	public String getDlServiceCode() {
		return dlServiceCode;
	}
	
	/**
	 * Sets the dl service code.
	 * 서비스 코드 설정
	 * @param dlServiceCode the new dl service code
	 */
	public void setDlServiceCode(String dlServiceCode) {
		this.dlServiceCode = dlServiceCode;
	}
	
	/**
	 * Gets the app service code.
	 * 서비스 코드 가져옴
	 * @return the app service code
	 */
	public String getAppServiceCode() {
		return appServiceCode;
	}
	
	/**
	 * Sets the app service code.
	 * 서비스 코드 설정
	 * @param appServiceCode the new app service code
	 */
	public void setAppServiceCode(String appServiceCode) {
		this.appServiceCode = appServiceCode;
	}
	
	/**
	 * Gets the operator id.
	 * 실행 id 가져옴
	 * @return the operator id
	 */
	public String getOperatorId() {
		return operatorId;
	}
	
	/**
	 * Sets the operator id.
	 * 실행 id 설정
	 * @param operatorId the new operator id
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	/**
	 * Gets the schedule job params vo list.
	 * 스케쥴 잡 파라미터 목록 가져옴
	 * @return the schedule job params vo list
	 */
	public ScheduleJobParamsVo[] getScheduleJobParamsVoList() {
		return scheduleJobParamsVoList;
	}
	
	/**
	 * Sets the schedule job params vo list.
	 * 스케쥴 잡 파라미터 목록 설정
	 * @param scheduleJobParamsVoList the new schedule job params vo list
	 */
	public void setScheduleJobParamsVoList(
			ScheduleJobParamsVo[] scheduleJobParamsVoList) {
		this.scheduleJobParamsVoList = scheduleJobParamsVoList;
	}
	
	/**
	 * Gets the now date.
	 * 현재 시간 가져옴
	 * @return the now date
	 */
	public String getNowDate() {
		return nowDate;
	}
	
	/**
	 * Sets the now date.
	 * 현재 시간 설정
	 * @param nowDate the new now date
	 */
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

}
