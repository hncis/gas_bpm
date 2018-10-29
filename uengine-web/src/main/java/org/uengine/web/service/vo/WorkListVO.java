package org.uengine.web.service.vo;

import java.io.Serializable;
import java.util.List;

public class WorkListVO implements Serializable {

	String totalCount;
	
	List<WorkVO> workList;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<WorkVO> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkVO> workList) {
		this.workList = workList;
	}
	

}
