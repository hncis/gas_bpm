package org.uengine.scheduler;

import java.io.Serializable;
import java.util.Calendar;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.SelectInput;

public class ManualScheCronExp implements Serializable {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public ManualScheCronExp() {
		
	}
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.setName("Manual Scheduler CronExpression");
		
		type.setFieldOrder(new String[] { 
				"Minute", "Hour", "DayOfMonth", "Month", "DayOfWeek", "Year", "ManualInput"
		});
		
		FieldDescriptor fd;
		
		fd = type.getFieldDescriptor("Minute");
			String[] minutes = new String[61];
			for (int i = 0; i < 60; i++) {
				minutes[i] = String.valueOf(i);
			}
			minutes[60] = "*";
		fd.setInputter(new SelectInput(minutes));
		
		fd = type.getFieldDescriptor("Hour");
			String[] hours = new String[25];
			for (int i = 0; i < 24; i++) {
				hours[i] = String.valueOf(i);
			}
			hours[24] = "*";
		fd.setInputter(new SelectInput(hours));
		
		fd = type.getFieldDescriptor("DayOfMonth");
			String[] dayOfMonth = new String[34];
			int count = 0 ;
			for (int i = 1; i < 32; i++) {
				dayOfMonth[count++] = String.valueOf(i);
			}
			dayOfMonth[31] = "L";
			dayOfMonth[32] = "*";
			dayOfMonth[33] = "?";
		fd.setInputter(new SelectInput(dayOfMonth));
		
		fd = type.getFieldDescriptor("Month");
		fd.setInputter(new SelectInput(new String[] {
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "*"
		}));
		
		fd = type.getFieldDescriptor("DayOfWeek");
		fd.setInputter(new SelectInput(new String[] {
				"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN", "MON-FRI", "*", "?"
		}));
		
		Calendar c = Calendar.getInstance();
		fd = type.getFieldDescriptor("Year");
			String[] year = new String[51];
			int yearCount = 0;
			year[yearCount++] = "*";
			for (int i = c.get(Calendar.YEAR); i < c.get(Calendar.YEAR) + 50; i++) {
				year[yearCount++] = String.valueOf(i);
			}
		fd.setInputter(new SelectInput(year));
		
	}
	
	public String getCronExpression() {		
		StringBuilder sb = new StringBuilder("0 ");
		sb.append(this.getMinute()).append(" ");
		sb.append(this.getHour()).append(" ");
		sb.append(this.getDayOfMonth()).append(" ");		
		sb.append(this.getMonth()).append(" ");
		sb.append(this.getDayOfWeek()).append(" ");
		sb.append(this.getYear());

		return sb.toString();
	}
	
	String minute;
		public String getMinute() {
			return minute;
		}
		public void setMinute(String minute) {
			this.minute = minute;
		}

	String hour;
		public String getHour() {
			return hour;
		}
		public void setHour(String hour) {
			this.hour = hour;
		}

	String dayOfMonth;
		public String getDayOfMonth() {
			return dayOfMonth;
		}
		public void setDayOfMonth(String dayOfMonth) {
			this.dayOfMonth = dayOfMonth;
		}
	
	String month;
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		
	String dayOfWeek;
		public String getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(String dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}

	String year;
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}

	String manualInput;
		public String getManualInput() {
			return manualInput;
		}
		public void setManualInput(String manualInput) {
			this.manualInput = manualInput;
		}
		
}
