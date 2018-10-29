package org.uengine.scheduler;

import java.io.Serializable;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;

public class AutoScheCronExp implements Serializable {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public AutoScheCronExp() {
		
	}
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.setName("Auto Scheduler CronExpression");
		type.setFieldOrder(new String[] { 
				"Minutes", "Hours", "Days", "Months", "Years"
		});
		
		FieldDescriptor fd;
		fd = type.getFieldDescriptor("Minutes");
		fd.setDisplayName("Minutes After");
		
		fd = type.getFieldDescriptor("Hours");
		fd.setDisplayName("Hours After");
		
		fd = type.getFieldDescriptor("Days");
		fd.setDisplayName("Days After");
		
		fd = type.getFieldDescriptor("Months");
		fd.setDisplayName("Months After");
		
		fd = type.getFieldDescriptor("Years");
		fd.setDisplayName("Years After");
	}
	
	String minutes;
		public String getMinutes() {
			return minutes;
		}
		public void setMinutes(String minutes) {
			this.minutes = minutes;
		}

	String hours;
		public String getHours() {
			return hours;
		}
		public void setHours(String hours) {
			this.hours = hours;
		}

	String days;
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
		}

	String months;
		public String getMonths() {
			return months;
		}
		public void setMonths(String months) {
			this.months = months;
		}

	String years;
		public String getYears() {
			return years;
		}
		public void setYears(String years) {
			this.years = years;
		}
	
}
