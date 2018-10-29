package org.uengine.workflow.springws;

import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.xpath.XPath;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.kernel.GlobalContext;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.workflow.springservice.WorkflowService;
import org.uengine.workflow.springws.util.ElementToObject;
import org.uengine.workflow.springws.util.ObjectToElement;

public class CompleteWorkitemEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {

	private WorkflowService workflowService;

	private XPath endpointExpression;
	private XPath instanceIdExpression;
	private XPath taskIdExpression;
	private XPath tracingTagExpression;

	public CompleteWorkitemEndpoint(WorkflowService workflowService) {

		this.workflowService = workflowService;

		try {
			endpointExpression = XPath.newInstance("//wf:endpoint");
			endpointExpression.addNamespace(WORKFLOW_NAMESPACE);

			instanceIdExpression = XPath.newInstance("//wf:instanceId");
			instanceIdExpression.addNamespace(WORKFLOW_NAMESPACE);

			taskIdExpression = XPath.newInstance("//wf:taskId");
			taskIdExpression.addNamespace(WORKFLOW_NAMESPACE);

			tracingTagExpression = XPath.newInstance("//wf:tracingTag");
			tracingTagExpression.addNamespace(WORKFLOW_NAMESPACE);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element completeWorkitemRequest) throws Exception {

		String endpoint = endpointExpression.valueOf(completeWorkitemRequest);
		String instanceId = instanceIdExpression.valueOf(completeWorkitemRequest);
		String taskId = taskIdExpression.valueOf(completeWorkitemRequest);
		String tracingTag = tracingTagExpression.valueOf(completeWorkitemRequest);

		List elementProcessVariableList = completeWorkitemRequest.getChildren("processVariable", WORKFLOW_NAMESPACE);
		HashMap processVariableMap = ElementToObject.makeProcessVariableMap(elementProcessVariableList);

		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

		InitialContext context = new InitialContext();
		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		String completedTaskId = null;
		List nextTaskList = null;

		try {
			pm = processManagerFactory.getProcessManager();

			if (tx != null)
				tx.begin();

			completedTaskId = workflowService.completeWorkitem(endpoint, instanceId, taskId, tracingTag, processVariableMap, pm);
			pm.applyChanges();

			nextTaskList = workflowService.nextTask(instanceId);

			if (tx != null && tx.getStatus() != Status.STATUS_NO_TRANSACTION)
				tx.commit();

		} catch (Exception e) {
			try {
				pm.cancelChanges();
			} catch (Exception ex) {
			}

			if (tx != null && tx.getStatus() != Status.STATUS_NO_TRANSACTION)
				tx.rollback();

		} finally {
			try {
				pm.remove();
			} catch (Exception e) {
			}
		}

		Document doc = new Document(new Element(COMPLETE_WORKITEM_RESPONSE, WORKFLOW_NAMESPACE));
		doc.getRootElement().addContent(new Element("completedTaskId", WORKFLOW_NAMESPACE).setText(completedTaskId));

		doc = ObjectToElement.makeNextTaskElement(doc, nextTaskList);
		Element returnElement = doc.getRootElement();

		return returnElement;
		
	}
}
