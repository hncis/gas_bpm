package com.hncis.system.vo;

import com.hncis.common.vo.Common;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchProcess.
 */
public class BatchProcess extends Common{

	/** The job_id. - batch job 아이디*/
	private String job_id		= "";
	
	/** The job_name. - batch job 이름*/
	private String job_name		= "";
	
	/** The start_time. - job 시작시간*/
	private String start_time		= "";
	
	/** The end_time. - job 종료시간*/
	private String end_time		= "";
	
	/** The interval. - job 구간*/
	private String interval		= "";
	
	/** The status. -job 상태*/
	private String status		= "";
	
	/** The search type. - 조회상태*/
	private String searchType	= "";
	
	/** The start point. - 시작점*/
	private Long startPoint = 0L;
	
	/** The end point. - 끝 점*/
	private Long endPoint = 0L;
	
	/** The input cnt. - 입력 갯수*/
	private int iptCnt = 0;
	
	/** The update cnt. - 수정 갯수*/
	private int uptCnt = 0;
	
	private String interval1		= "";
	
	public String getInterval1() {
		return interval1;
	}

	public void setInterval1(String interval1) {
		this.interval1 = interval1;
	}

	/**
	 * Gets the job_id.
	 * 배치 잡 id를 가져옴.
	 * @return the job_id
	 */
	public String getJob_id() {
		return job_id;
	}
	
	/**
	 * Sets the job_id.
	 * 배치 잡 id를 설정
	 * @param jobId the new job_id
	 */
	public void setJob_id(String jobId) {
		job_id = jobId;
	}
	
	/**
	 * Gets the job_name.
	 * 배치 잡 이름 가져옴
	 * @return the job_name
	 */
	public String getJob_name() {
		return job_name;
	}
	
	/**
	 * Sets the job_name.
	 * 배치 잡 이름 설정
	 * @param jobName the new job_name
	 */
	public void setJob_name(String jobName) {
		job_name = jobName;
	}
	
	/**
	 * Gets the start_time.
	 * 배치 잡 시작시간 가져옴
	 * @return the start_time
	 */
	public String getStart_time() {
		return start_time;
	}
	
	/**
	 * Sets the start_time.
	 * 배치 잡 시작시간 설정
	 * @param startTime the new start_time
	 */
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	
	/**
	 * Gets the end_time.
	 * 배치 잡 종료시간 가져옴
	 * @return the end_time
	 */
	public String getEnd_time() {
		return end_time;
	}
	
	/**
	 * Sets the end_time.
	 * 배치 잡 시작시간 설정
	 * @param endTime the new end_time
	 */
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	
	/**
	 * Gets the interval.
	 * 배치 잡 구간 가져옴
	 * @return the interval
	 */
	public String getInterval() {
		return interval;
	}
	
	/**
	 * Sets the interval.
	 * 배치 잡 구간 설정
	 * @param interval the new interval
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
	/**
	 * Gets the status.
	 * 배치 잡 상태 가져옴
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 * 배치 잡 상태 설정
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the search type.
	 * 조회상태 가져옴
	 * @return the search type
	 */
	public String getSearchType() {
		return searchType;
	}
	
	/**
	 * Sets the search type.
	 * 조회상태 설정
	 * @param searchType the new search type
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	/**
	 * Gets the start point.
	 * 조회상태 가져옴
	 * @return the start point
	 */
	public Long getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Sets the start point.
	 * 시작점 설정
	 * @param startPoint the new start point
	 */
	public void setStartPoint(Long startPoint) {
		this.startPoint = startPoint;
	}
	
	/**
	 * Gets the end point.
	 * 끝 점 가져옴
	 * @return the end point
	 */
	public Long getEndPoint() {
		return endPoint;
	}
	
	/**
	 * Sets the end point.
	 * 끝 점 설정
	 * @param endPoint the new end point
	 */
	public void setEndPoint(Long endPoint) {
		this.endPoint = endPoint;
	}
	
	/**
	 * Gets the input count.
	 * 입력 횟수 가져옴
	 * @return the ipt cnt
	 */
	public int getIptCnt() {
		return iptCnt;
	}
	
	/**
	 * Sets the input count.
	 * 입력 횟수 설정
	 * @param iptCnt the new ipt cnt
	 */
	public void setIptCnt(int iptCnt) {
		this.iptCnt = iptCnt;
	}
	
	/**
	 * Gets the update count.
	 * 갱신 횟수 가져옴
	 * @return the upt cnt
	 */
	public int getUptCnt() {
		return uptCnt;
	}
	
	/**
	 * Sets the update count.
	 * 갱신 횟수 설정
	 * @param uptCnt the new upt cnt
	 */
	public void setUptCnt(int uptCnt) {
		this.uptCnt = uptCnt;
	}
	
}
