package org.uengine.ui.worklist.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkListFilter {

	String filterName;
		public String getFilterName() {
			return filterName;
		}
		
	String status;
		public String getStatus() {
			return status;
		}
	
		public void setStatus(String status) {
			this.status = status;
		}
	
	String definition;
		public String getDefinition() {
			return definition;
		}
	
		public void setDefinition(String definition) {
			this.definition = definition;
		}
		
	String information;
		public String getInformation() {
			return information;
		}
	
		public void setInformation(String information) {
			this.information = information;
		}

	String filterUId;
		public String getFilterUId() {
			return filterUId;
		}
	String type;
		public String getType() {
			return type;
		}
	
		public void setType(String type) {
			this.type = type;
		}
		
	String endpointType;
		public String getEndpointType() {
			return endpointType;
		}
	
		public void setEndpointType(String endpointType) {
			this.endpointType = endpointType;
		}

	public WorkListFilter(String filterName){
		this.filterName = filterName;
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
		this.filterUId=sdf.format(new Date());
	}
		
}
