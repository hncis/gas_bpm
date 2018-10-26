package com.hncis.system.vo;

import com.hncis.common.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchInfo. - BatchInfo value object
 */
public class BatchInfo {
	
	/** The job_id. - batch job 아이디*/
	private String job_id;
	
	/** The job_name. - batch job 이름*/
	private String job_name;
	
	/** The type_id. - batch 타입 id(type_id = '1' -'한번', type_id = '2' -'매일', type_id = '3' , type_id = '4' '매배치시' , type_id = '5' - '종속배치')*/
	private String type_id;
	
	/** The description. - batch 설명*/
	private String description;
	
	/** The use_start_date. 시작시간*/
	private String use_start_date;
	
	/** The use_end_date. 종료시간*/
	private String use_end_date;
	
	/** The val. 배치 시작 시간*/
	private String val;
	
	/** The use_yn. 배치 사용여부*/
	private String use_yn;
	
	/** The dependency_schedule_id. batch 타입 id */
	private String dependency_schedule_id;
	
	/** The restart_yn. 재시작여부*/
	private String restart_yn;
	
	/** The restart_count. 재시작횟수*/
	private int restart_count;
	
	/** The restart_time. 재시작 시간 */
	private String restart_time;
	
	/** The create_date. 생성일자*/
	private String create_date;
	
	/** The update_date. 수정일자*/
	private String update_date;
	
	/** The seq. 순번*/
	private String seq;
	
	/** The parameter_id. 파라미터 아이디 */
	private String parameter_id;
	
	/** The parameter_name. 파라미터 명*/
	private String parameter_name;
	
	/** The schedule_id. 스케쥴 아이디*/
	private String schedule_id;
	
	/** The auto_increment_yn. 자동 증감 여부*/
	private String auto_increment_yn;
	
	/**
	 * Gets the parameter_id.
	 *
	 * @return the parameter_id
	 */
	public String getParameter_id() {
		return parameter_id;
	}
	
	/**
	 * Sets the parameter_id.
	 *
	 * @param parameterId the new parameter_id
	 */
	public void setParameter_id(String parameterId) {
		parameter_id = parameterId;
	}
	
	/**
	 * Gets the parameter_name.
	 *
	 * @return the parameter_name
	 */
	public String getParameter_name() {
		return parameter_name;
	}
	
	/**
	 * Sets the parameter_name.
	 *
	 * @param parameterName the new parameter_name
	 */
	public void setParameter_name(String parameterName) {
		parameter_name = parameterName;
	}
	
	/**
	 * Gets the schedule_id.
	 *
	 * @return the schedule_id
	 */
	public String getSchedule_id() {
		return schedule_id;
	}
	
	/**
	 * Sets the schedule_id.
	 *
	 * @param scheduleId the new schedule_id
	 */
	public void setSchedule_id(String scheduleId) {
		schedule_id = scheduleId;
	}
	
	/**
	 * Gets the auto_increment_yn.
	 *
	 * @return the auto_increment_yn
	 */
	public String getAuto_increment_yn() {
		return auto_increment_yn;
	}
	
	/**
	 * Sets the auto_increment_yn.
	 *
	 * @param autoIncrementYn the new auto_increment_yn
	 */
	public void setAuto_increment_yn(String autoIncrementYn) {
		auto_increment_yn = autoIncrementYn;
	}
	
	/**
	 * Gets the job_id.
	 *
	 * @return the job_id
	 */
	public String getJob_id() {
		return job_id;
	}
	
	/**
	 * Sets the job_id.
	 *
	 * @param jobId the new job_id
	 */
	public void setJob_id(String jobId) {
		job_id = jobId;
	}
	
	/**
	 * Gets the job_name.
	 *
	 * @return the job_name
	 */
	public String getJob_name() {
		return job_name;
	}
	
	/**
	 * Sets the job_name.
	 *
	 * @param jobName the new job_name
	 */
	public void setJob_name(String jobName) {
		job_name = jobName;
	}
	
	/**
	 * Gets the type_id.
	 *
	 * @return the type_id
	 */
	public String getType_id() {
		return type_id;
	}
	
	/**
	 * Sets the type_id.
	 *
	 * @param typeId the new type_id
	 */
	public void setType_id(String typeId) {
		type_id = typeId;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return StringUtil.uniDecode(description);
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the use_start_date.
	 *
	 * @return the use_start_date
	 */
	public String getUse_start_date() {
		return use_start_date;
	}
	
	/**
	 * Sets the use_start_date.
	 *
	 * @param useStartDate the new use_start_date
	 */
	public void setUse_start_date(String useStartDate) {
		use_start_date = useStartDate;
	}
	
	/**
	 * Gets the use_end_date.
	 *
	 * @return the use_end_date
	 */
	public String getUse_end_date() {
		return use_end_date;
	}
	
	/**
	 * Sets the use_end_date.
	 *
	 * @param useEndDate the new use_end_date
	 */
	public void setUse_end_date(String useEndDate) {
		use_end_date = useEndDate;
	}
	
	/**
	 * Gets the val.
	 *
	 * @return the val
	 */
	public String getVal() {
		return val;
	}
	
	/**
	 * Sets the val.
	 *
	 * @param val the new val
	 */
	public void setVal(String val) {
		this.val = val;
	}
	
	/**
	 * Gets the use_yn.
	 *
	 * @return the use_yn
	 */
	public String getUse_yn() {
		return use_yn;
	}
	
	/**
	 * Sets the use_yn.
	 *
	 * @param useYn the new use_yn
	 */
	public void setUse_yn(String useYn) {
		use_yn = useYn;
	}
	
	/**
	 * Gets the dependency_schedule_id.
	 *
	 * @return the dependency_schedule_id
	 */
	public String getDependency_schedule_id() {
		return dependency_schedule_id;
	}
	
	/**
	 * Sets the dependency_schedule_id.
	 *
	 * @param dependencyScheduleId the new dependency_schedule_id
	 */
	public void setDependency_schedule_id(String dependencyScheduleId) {
		dependency_schedule_id = dependencyScheduleId;
	}
	
	/**
	 * Gets the restart_yn.
	 *
	 * @return the restart_yn
	 */
	public String getRestart_yn() {
		return restart_yn;
	}
	
	/**
	 * Sets the restart_yn.
	 *
	 * @param restartYn the new restart_yn
	 */
	public void setRestart_yn(String restartYn) {
		restart_yn = restartYn;
	}
	
	/**
	 * Gets the restart_count.
	 *
	 * @return the restart_count
	 */
	public int getRestart_count() {
		return restart_count;
	}
	
	/**
	 * Sets the restart_count.
	 *
	 * @param restartCount the new restart_count
	 */
	public void setRestart_count(int restartCount) {
		restart_count = restartCount;
	}
	
	/**
	 * Gets the restart_time.
	 *
	 * @return the restart_time
	 */
	public String getRestart_time() {
		return restart_time;
	}
	
	/**
	 * Sets the restart_time.
	 *
	 * @param restartTime the new restart_time
	 */
	public void setRestart_time(String restartTime) {
		restart_time = restartTime;
	}
	
	/**
	 * Gets the create_date.
	 *
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}
	
	/**
	 * Sets the create_date.
	 *
	 * @param createDate the new create_date
	 */
	public void setCreate_date(String createDate) {
		create_date = createDate;
	}
	
	/**
	 * Gets the update_date.
	 *
	 * @return the update_date
	 */
	public String getUpdate_date() {
		return update_date;
	}
	
	/**
	 * Sets the update_date.
	 *
	 * @param updateDate the new update_date
	 */
	public void setUpdate_date(String updateDate) {
		update_date = updateDate;
	}
	
	/**
	 * Gets the seq.
	 *
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	
	/**
	 * Sets the seq.
	 *
	 * @param seq the new seq
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
}
