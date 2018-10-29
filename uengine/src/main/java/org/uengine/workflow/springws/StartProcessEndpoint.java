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

public class StartProcessEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {

	private WorkflowService workflowService;

	private XPath typeExpression;
	private XPath aliasExpression;
	private XPath initiatorExpression;

	public StartProcessEndpoint(WorkflowService workflowService) {

		this.workflowService = workflowService;

		try {
			typeExpression = XPath.newInstance("//wf:type");
			typeExpression.addNamespace(WORKFLOW_NAMESPACE);
			
			aliasExpression = XPath.newInstance("//wf:alias");
			aliasExpression.addNamespace(WORKFLOW_NAMESPACE);

			initiatorExpression = XPath.newInstance("//wf:initiator");
			initiatorExpression.addNamespace(WORKFLOW_NAMESPACE);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element startProcessRequest) throws Exception {
		String type = typeExpression.valueOf(startProcessRequest);
		String alias = aliasExpression.valueOf(startProcessRequest);
		String initiator = initiatorExpression.valueOf(startProcessRequest);

		List elementProcessVariableList = startProcessRequest.getChildren("processVariable", WORKFLOW_NAMESPACE);
		HashMap processVariableMap = ElementToObject.makeProcessVariableMap(elementProcessVariableList);

		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

		InitialContext context = new InitialContext();
		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		String instanceId = null;
		List nextTaskList = null;

		try {
			pm = processManagerFactory.getProcessManager();

			if (tx != null)
				tx.begin();

			instanceId = workflowService.startProcess(type, alias, initiator, processVariableMap, pm);
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

		Document doc = new Document(new Element(START_PROCESS_RESPONSE, WORKFLOW_NAMESPACE));
		doc.getRootElement().addContent(new Element("newInstanceId", WORKFLOW_NAMESPACE).setText(instanceId));

		doc = ObjectToElement.makeNextTaskElement(doc, nextTaskList);
		Element returnElement = doc.getRootElement();

		return returnElement;

	}

}
