package com.defaultcompany.web.strategy;

import java.util.Date;

public class Strategy {
	//creation status
	final public static String STRATEGY_STATUS_APPROVED= "APPROVED";
	final public static String STRATEGY_STATUS_PROPOSED= "PROPOSED";
	final public static String STRATEGY_STATUS_DROPED= "DROPED";
	
	//performance status
	final public static String STRATEGY_STATUS_COMPLETED= "COMPLETED";
//	final public static String STRATEGY_STATUS_RUNNING= "RUNNING";
//	final public static String STRATEGY_STATUS_DELAYED= "DELAYED";
//	final public static String STRATEGY_STATUS_READY= "READY";	
//	final public static String STRATEGY_STATUS_STOP= "STOP";	
	
	private int strategyId;
	private String strategyName;
	private String description;
	private String type;
	private String status;
	private Date startDate;
	private Date endDate;
	private String isDeleted;
	private int instCnt;
	private int cmpltInstCnt;
	private String comCode;
	private boolean isRoot;
	private String partCode;
	private String partName;
	
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public int getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(int strategyId) {
		this.strategyId = strategyId;
	}
	public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getInstCnt() {
		return instCnt;
	}
	public void setInstCnt(int instCnt) {
		this.instCnt = instCnt;
	}
	public int getCmpltInstCnt() {
		return cmpltInstCnt;
	}
	public void setCmpltInstCnt(int cmpltInstCnt) {
		this.cmpltInstCnt = cmpltInstCnt;
	}
}
