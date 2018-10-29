package org.uengine.web.monitoring.vo;

import java.util.Date;

import org.uengine.web.service.vo.DefaultVO;

public class MonitoringVO extends DefaultVO {
	private Date startedDate;	// 프로세스시작일
	private String defName;		// 프로세스명
	private int totalCount;		// 건수
	private int delayedCount;	// 지연건수
	private Date finishedDate;	// 프로세스 완료일
	private int workingDayAVG; // 평균완료일, 평균일수
	private String partName;   // 수행부서명, 부서명
	private String path;	   // /구분/프로세스명
	private int workingDayMin; // 최소실행일수
	private int workingDayMax; // 최대실행일수
	private String empName;    // 사용자명
	private int passedCount;   // 처리건수
	private int passedDayAVG;  // 평균처리일수
	private int delayedDayAVG; // 평균지연일수
	public Date getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getDelayedCount() {
		return delayedCount;
	}
	public void setDelayedCount(int delayedCount) {
		this.delayedCount = delayedCount;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public int getWorkingDayAVG() {
		return workingDayAVG;
	}
	public void setWorkingDayAVG(int workingDayAVG) {
		this.workingDayAVG = workingDayAVG;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getWorkingDayMin() {
		return workingDayMin;
	}
	public void setWorkingDayMin(int workingDayMin) {
		this.workingDayMin = workingDayMin;
	}
	public int getWorkingDayMax() {
		return workingDayMax;
	}
	public void setWorkingDayMax(int workingDayMax) {
		this.workingDayMax = workingDayMax;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getPassedCount() {
		return passedCount;
	}
	public void setPassedCount(int passedCount) {
		this.passedCount = passedCount;
	}
	public int getPassedDayAVG() {
		return passedDayAVG;
	}
	public void setPassedDayAVG(int passedDayAVG) {
		this.passedDayAVG = passedDayAVG;
	}
	public int getDelayedDayAVG() {
		return delayedDayAVG;
	}
	public void setDelayedDayAVG(int delayedDayAVG) {
		this.delayedDayAVG = delayedDayAVG;
	}
	@Override
	public String toString() {
		return "MonitoringVO [startedDate=" + startedDate + ", defName="
				+ defName + ", totalCount=" + totalCount + ", delayedCount="
				+ delayedCount + ", finishedDate=" + finishedDate
				+ ", workingDayAVG=" + workingDayAVG + ", partName=" + partName
				+ ", path=" + path + ", workingDayMin=" + workingDayMin
				+ ", workingDayMax=" + workingDayMax + ", empName=" + empName
				+ ", passedCount=" + passedCount + ", passedDayAVG="
				+ passedDayAVG + ", delayedDayAVG=" + delayedDayAVG + "]";
	}
	
	
}
