package org.uengine.web.service.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FlowchartVO extends DefaultVO {

	private String pdVersionId;
	private String instanceId;
	private String chart;
	/**
	 * @return the pdVersionId
	 */
	public String getPdVersionId() {
		return pdVersionId;
	}
	/**
	 * @param pdVersionId the pdVersionId to set
	 */
	public void setPdVersionId(String pdVersionId) {
		this.pdVersionId = pdVersionId;
	}
	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * @return the chart
	 */
	public String getChart() {
		return chart;
	}
	/**
	 * @param chart the chart to set
	 */
	public void setChart(String chart) {
		this.chart = chart;
	}
	
}
