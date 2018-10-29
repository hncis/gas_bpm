package org.uengine.workflow.springws.util;

import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.uengine.workflow.model.NextTask;
import org.uengine.workflow.springws.WorkflowWebServiceConstants;

public class ObjectToElement implements WorkflowWebServiceConstants {
	
	public static Document makeNextTaskElement(Document doc, List nextTaskList) {
		if (nextTaskList != null) {
			for (int i = 0; i < nextTaskList.size(); i++) {
				NextTask nextTask = (NextTask) nextTaskList.get(i);
				Element element = new Element("nextTask", WORKFLOW_NAMESPACE);
					element.addContent(new Element("endpoint", WORKFLOW_NAMESPACE).setText(nextTask.getEndpoint()));
					element.addContent(new Element("instanceId", WORKFLOW_NAMESPACE).setText(nextTask.getInstanceId()));
					element.addContent(new Element("rootInstanceId", WORKFLOW_NAMESPACE).setText(nextTask.getRootInstanceId()));
					element.addContent(new Element("taskId", WORKFLOW_NAMESPACE).setText(nextTask.getTaskId()));
					element.addContent(new Element("tracingTag", WORKFLOW_NAMESPACE).setText(nextTask.getTracingTag()));
					element.addContent(new Element("info", WORKFLOW_NAMESPACE).setText(nextTask.getInfo()));
				doc.getRootElement().addContent(element);
			}
		}
		return doc;
	}
}
