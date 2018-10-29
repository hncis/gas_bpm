package com.defaultcompany.external.model.stdmsg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdom2.Element;
import org.springframework.util.StringUtils;

public class DelegateWorkItemMsg extends BaseStdMsg {

	private static final long serialVersionUID = 1L;

	private String instanceId;
	private String tracingTag;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTracingTag() {
		return tracingTag;
	}

	public void setTracingTag(String tracingTag) {
		this.tracingTag = tracingTag;
	}

	private List<String> endpoints;
		public void addEndpoint(String endpoint) {
			this.getEndpoints().add(endpoint);
		}
		public List<String> getEndpoints() {
			if (this.endpoints == null) {
				this.setEndpoints(new ArrayList<String>());
			}
			return endpoints;
		}
		public void setEndpoints(List<String> endpoints) {
			this.endpoints = endpoints;
		}
		public void setEndpoints(String endpoints) {
			if (StringUtils.hasText(endpoints)) {
				for (String endpoint : endpoints.split(",")) {
					this.addEndpoint(endpoint);
				}
			}
		}
		public void setEndpointsByElementList(List<Element> endpointElementList) {
			if (endpointElementList != null) {
				for (Element endpointElement : endpointElementList) {
					String endpoint = endpointElement.getChildText("endpoint", WORKFLOW_NAMESPACE);
					this.addEndpoint(endpoint);
				}
			}
		}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
