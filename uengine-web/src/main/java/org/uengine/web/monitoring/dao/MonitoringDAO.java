package org.uengine.web.monitoring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.monitoring.vo.MonitoringVO;
import org.uengine.web.organization.vo.OrganizationVO;


@Repository("monitoringDAO")
public class MonitoringDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<MonitoringVO> getNewWorkStatus(){
		return (List<MonitoringVO>)selectList("monitoring.selectNewWorkStatus");
	}
	
	@SuppressWarnings("unchecked")
	public List<MonitoringVO> getProgressStatusByTask(){
		return (List<MonitoringVO>)selectList("monitoring.selectProgressStatusByTask");
	}
	
	@SuppressWarnings("unchecked")
	public List<MonitoringVO> getProcessingDelayStatusByTask(){
		return (List<MonitoringVO>)selectList("monitoring.selectProcessingDelayStatusByTask");
	}
	@SuppressWarnings("unchecked")
	public List<MonitoringVO> getTaskCompletedAverageTime(){
		return (List<MonitoringVO>)selectList("monitoring.selectTaskCompletedAverageTime");
	}
	
	
	// taskcompletedaveragetime.jsp
}
