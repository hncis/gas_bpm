package org.uengine.workflow.springws;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.xpath.XPath;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.uengine.kernel.GlobalContext;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.workflow.springservice.WorkflowService;

public class WorkItemAcceptEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {

	private WorkflowService workflowService;

	private XPath instanceIdExpression;
	private XPath tracingTagExpression;
	private XPath endpointExpression;

	public WorkItemAcceptEndpoint(WorkflowService workflowService) {
		this.workflowService = workflowService;

		try {
			instanceIdExpression = XPath.newInstance("//wf:instanceId");
			instanceIdExpression.addNamespace(WORKFLOW_NAMESPACE);

			tracingTagExpression = XPath.newInstance("//wf:tracingTag");
			tracingTagExpression.addNamespace(WORKFLOW_NAMESPACE);

			endpointExpression = XPath.newInstance("//wf:endpoint");
			endpointExpression.addNamespace(WORKFLOW_NAMESPACE);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element workItemAcceptRequest) throws Exception {

		String instanceId = instanceIdExpression.valueOf(workItemAcceptRequest);
		String tracingTag = tracingTagExpression.valueOf(workItemAcceptRequest);
		String endpoint = endpointExpression.valueOf(workItemAcceptRequest);

		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

		InitialContext context = new InitialContext();
		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		String responseEndPoint = null;

		try {
			pm = processManagerFactory.getProcessManager();

			if (tx != null)
				tx.begin();

			responseEndPoint = workflowService.workItemAccept(instanceId, tracingTag, endpoint, pm);
			pm.applyChanges();

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

		Element response = new Element(WORKITEM_ACCEPT_RESPONSE, WORKFLOW_NAMESPACE).setText(responseEndPoint);

		return response;
	}
}
