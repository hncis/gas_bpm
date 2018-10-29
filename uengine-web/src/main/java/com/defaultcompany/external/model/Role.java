package com.defaultcompany.external.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Role {

	private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	private List<String> endpoints;
		public void addEndpoint(String endpoint) {
			this.getEndpoints().add(endpoint);
		}
		public List<String> getEndpoints() {
			if (endpoints == null) {
				this.setEndpoints(new ArrayList<String>());
			}
			return endpoints;
		}
		private void setEndpoints(List<String> endpoints) {
			this.endpoints = endpoints;
		}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
