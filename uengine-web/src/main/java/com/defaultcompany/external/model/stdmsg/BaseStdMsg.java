package com.defaultcompany.external.model.stdmsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdom2.Element;
import org.springframework.util.StringUtils;

import com.defaultcompany.external.controller.webservices.WebServiceConstants;
import com.defaultcompany.external.model.ProcessVariable;
import com.defaultcompany.external.model.Role;

public class BaseStdMsg implements Serializable, WebServiceConstants {
	
	private static final long serialVersionUID = 1L;
	
	private String externalKey;
		public String getExternalKey() {
			return externalKey;
		}
		public void setExternalKey(String externalKey) {
			this.externalKey = externalKey;
		}
	
	private List<Role> roles;
		public void addRole(Role role) {
			this.getRoles().add(role);
		}
		public List<Role> getRoles() {
			if (this.roles == null) {
				this.setRoles(new ArrayList<Role>());
			}
			return roles;
		}
		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}
		public void setRoles(String procRoleList) {
			if (StringUtils.hasText(procRoleList)) {
				for (String procRole : procRoleList.split(";")) {
					String[] roleItem = procRole.split("=");
					if (roleItem.length == 2) {
						String roleName = roleItem[0].trim();
						String roleEndpoints = roleItem[1].trim();
						
						if (StringUtils.hasText(roleName) && StringUtils.hasText(roleEndpoints)) {
							Role role = new Role();
							role.setName(roleName);
							for (String endpoint : roleEndpoints.split(",")) {
								role.addEndpoint(endpoint);	
							}
							this.addRole(role);
						}
					}
				}
			}
		}
		@SuppressWarnings({ "unchecked" })
		public void setRolesByElementList(List<Element> roleElementList) {
			if (roleElementList != null) {
				for (Element roleElement : roleElementList) {
					Role role = new Role();
					
					String roleName = roleElement.getChildText("roleName", WORKFLOW_NAMESPACE);
					List<Element> valueElementList = roleElement.getChildren("endpoint", WORKFLOW_NAMESPACE);
					if (valueElementList.size() == 1) {
						String endpoint = roleElement.getChildText("endpoint", WORKFLOW_NAMESPACE);
						role.addEndpoint(endpoint);
					} else {
						for (Element endpointElement : valueElementList) {
							String endpoint = endpointElement.getText();
							role.addEndpoint(endpoint);
						}
					}
					
					role.setName(roleName);
					if (role.getEndpoints().size() > 0)
						this.addRole(role);
				}
			}
		}
	
	private List<ProcessVariable> processVariables;
		public void addProcessVariable(ProcessVariable pv) {
			this.getProcessVariables().add(pv);
		}
		public List<ProcessVariable> getProcessVariables() {
			if (this.processVariables == null) {
				this.setProcessVariables(new ArrayList<ProcessVariable>());
			}
			return processVariables;
		}
		public void setProcessVariables(List<ProcessVariable> processVariables) {
			this.processVariables = processVariables;
		}
		public void setProcessVariables(String procValList) {
			if (StringUtils.hasText(procValList)) { 
				for (String procVal : procValList.split(";")) {
					String[] variableItem = procVal.trim().split("=");
					if (variableItem.length == 2) {
						String variableName = variableItem[0].trim();
						String variableValues = variableItem[1].trim();
						
						if (StringUtils.hasText(variableName) && StringUtils.hasText(variableValues)) {
							ProcessVariable pv = new ProcessVariable();
							pv.setName(variableName);
							for (String val : variableValues.split(",")) {
								pv.addValue(val);	
							}
							this.addProcessVariable(pv);
						}
					}
				}
			}
		}
		@SuppressWarnings("unchecked")
		public void setProcessVariablesByElementList(List<Element> processVariableElementList) {
			if (processVariableElementList != null) {
				for (Element processVariableElement : processVariableElementList) {
					ProcessVariable pv = new ProcessVariable();
					
					String key = processVariableElement.getChildText("key", WORKFLOW_NAMESPACE);
					List<Element> valueElementList = processVariableElement.getChildren("value", WORKFLOW_NAMESPACE);
					if (valueElementList.size() == 1) {
						String value = processVariableElement.getChildText("value", WORKFLOW_NAMESPACE);
						pv.addValue(value);
					} else {
						for (Element valueElement : valueElementList) {
							String value = valueElement.getText();
							pv.addValue(value);
						}
					}
					
					pv.setName(key);
					if (pv.getValues().size() > 0)
						this.addProcessVariable(pv);
				}
			}
		}
		
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}
