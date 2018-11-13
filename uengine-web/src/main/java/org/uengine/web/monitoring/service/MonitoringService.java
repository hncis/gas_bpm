package org.uengine.web.monitoring.service;

import java.util.List;
import java.util.Map;

import org.uengine.kernel.RoleMapping;
import org.uengine.web.monitoring.vo.MonitoringVO;

import be.ceau.chart.data.BarData;

public interface MonitoringService {

	public Object getChartData(String type) throws Exception;
	public Object getComboBoxData(List<RoleMapping> roleInfoList) throws Exception;
	public Object getProcessingStatusByTaskData (Map<String, String> map) throws Exception;
}
