package com.defaultcompany.external.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProcessVariable {
	
	private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	private List<String> values;
		public void addValue(String val) {
			this.getValues().add(val);
		}
		public List<String> getValues() {
			if (values == null) {
				this.setValues(new ArrayList<String>());
			}
			return values;
		}
		private void setValues(List<String> values) {
			this.values = values;
		}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
