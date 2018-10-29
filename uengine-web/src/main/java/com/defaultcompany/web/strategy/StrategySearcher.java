package com.defaultcompany.web.strategy;

import java.io.Serializable;
import java.util.Hashtable;

import org.uengine.util.UEngineUtil;

public class StrategySearcher implements Serializable {

	final public static String PERIOD_WEEK= "period_week";
	final public static String PERIOD_MONTH= "period_month";
	final public static String PERIOD_YEAR= "period_year";
	final public static String PERIOD_QUARTER= "period_quarter";
	
	public StrategySearcher(){
		strategySearchList = new Hashtable<String, Item>();
	}
	
	public boolean isStrategySearch(){
		if(UEngineUtil.isNotEmpty(getPeriod())|| UEngineUtil.isNotEmpty(getPartCode())||UEngineUtil.isNotEmpty(getName()) ){
			return true;
		}
		return false;
	}
	private String name;
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}

	private String period;
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
	private String selectedPeriod;
		public String getSelectedPeriod() {
			return selectedPeriod;
		}
	
		public void setSelectedPeriod(String selectedPeriod) {
			this.selectedPeriod = selectedPeriod;
		}
		
	private String partCode;
		public String getPartCode() {
			return partCode;
		}
		public void setPartCode(String partCode) {
			this.partCode = partCode;
		}
		
	boolean includingIsNotCompleted;
		public boolean isIncludingIsNotCompleted() {
			return includingIsNotCompleted;
		}
	
		public void setIncludingIsNotCompleted(boolean includingIsNotCompleted) {
			this.includingIsNotCompleted = includingIsNotCompleted;
		}

	private Hashtable<String, Item> strategySearchList;
		public Hashtable<String, Item> getStrategySearchList() {
			if (this.strategySearchList == null) {
				this.setStrategySearchList(new Hashtable<String, Item>());
			}
			return strategySearchList;
		}
		private void setStrategySearchList(Hashtable<String, Item> strategySearchList) {
			this.strategySearchList = strategySearchList;
		}
}
