package org.uengine.web.monitoring.service;

import java.util.List;

import org.uengine.web.monitoring.vo.MonitoringVO;

import be.ceau.chart.data.BarData;

public interface MonitoringService {

	public Object getChartData(String type) throws Exception;
}
