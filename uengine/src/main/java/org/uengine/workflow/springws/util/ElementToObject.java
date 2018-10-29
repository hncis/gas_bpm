package org.uengine.workflow.springws.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.uengine.workflow.springws.WorkflowWebServiceConstants;

public class ElementToObject implements WorkflowWebServiceConstants {
	
	public static HashMap makeProcessVariableMap(List processVariableList) {
		HashMap processVariableMap = null;
		
		if (processVariableList != null) {
			processVariableMap = new HashMap();
			for (int i = 0; i < processVariableList.size(); i++) {
				Element processVariableElement = (Element) processVariableList.get(i);

				String key = processVariableElement.getChildText("key", WORKFLOW_NAMESPACE);
				List valueList = processVariableElement.getChildren("value", WORKFLOW_NAMESPACE);

				if (valueList.size() == 1) {
					String value = processVariableElement.getChildText("value", WORKFLOW_NAMESPACE);
					processVariableMap.put(key, value);
				} else {
					List values = new ArrayList();
					for (Object object : valueList) {
						Element valueElement = (Element) object;
						String value = valueElement.getText();
						values.add(value);
					}
					processVariableMap.put(key, values);
				}
			}
		}

		return processVariableMap;
	}
	
	public static ArrayList makeEndpointList(List elementEndpointList) {
		ArrayList endpoints = null;
		
		if (elementEndpointList != null) {
			endpoints = new ArrayList();
			for (int i=0; i<elementEndpointList.size(); i++) {
				Element endpointElement = (Element) elementEndpointList.get(i);
				
				List endpointList = endpointElement.getChildren("endpoint", WORKFLOW_NAMESPACE);
				
				if (endpointList.size() == 1) {
					String endpoint = endpointElement.getChildText("endpoint", WORKFLOW_NAMESPACE);
					endpoints.add(endpoint);
				} else {
					for (Object object : endpointList) {
						Element valueElement = (Element) object;
						String value = valueElement.getText();
						endpoints.add(value);
					}
				}
			}
		}
		
		return endpoints;
	}

}
