package com.defaultcompany.external.controller.webservices.support;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.defaultcompany.external.model.TaskInfo;

public class MakeReturnMsg {
	public static Element toElement(String status, List<TaskInfo> taskInfoList, String elementName, Namespace namespace) {
		Document doc = new Document(new Element(elementName, namespace));
		
		Element returnMsgElement = new Element("ReturnMsg", namespace);
		returnMsgElement.addContent(new Element("status", namespace).setText(status));
		
		Element taskInfoListElement = new Element("taskInfoList", namespace);
		if (taskInfoList != null) {
			for (TaskInfo taskInfo : taskInfoList) {
				Element element = new Element("TaskInfo", namespace);
					element.addContent(new Element("externalKey", namespace).setText(taskInfo.getExternalKey()));
					element.addContent(new Element("instanceId", namespace).setText(taskInfo.getInstanceId()));
					element.addContent(new Element("taskId", namespace).setText(taskInfo.getTaskId()));
					element.addContent(new Element("tracingTag", namespace).setText(taskInfo.getTracingTag()));
					element.addContent(new Element("handler", namespace).setText(taskInfo.getHandler()));
					element.addContent(new Element("mainParam", namespace).setText(taskInfo.getMainParam()));
					element.addContent(new Element("subParam", namespace).setText(taskInfo.getSubParam()));
				taskInfoListElement.addContent(element);
			}
		}
		
		returnMsgElement.addContent(taskInfoListElement);
		
		doc.getRootElement().addContent(returnMsgElement);
		Element returnElement = doc.getRootElement();
		
		return returnElement;
		
	}
}
