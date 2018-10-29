package org.uengine.workflow.springws;

import java.util.HashMap;
import java.util.List;

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
import org.uengine.workflow.springws.util.ElementToObject;

public class SetProcessVariableEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {

	private WorkflowService workflowService;

	private XPath instanceIdExpression;

	public SetProcessVariableEndpoint(WorkflowService workflowService) {

		this.workflowService = workflowService;

		try {
			instanceIdExpression = XPath.newInstance("//wf:instanceId");
			instanceIdExpression.addNamespace(WORKFLOW_NAMESPACE);

		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element setProcessVariableRequest) throws Exception {

		String instanceId = instanceIdExpression.valueOf(setProcessVariableRequest);

		List elementProcessVariableList = setProcessVariableRequest.getChildren("processVariable", WORKFLOW_NAMESPACE);
		HashMap processVariableMap = ElementToObject.makeProcessVariableMap(elementProcessVariableList);

		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

		InitialContext context = new InitialContext();
		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		try {
			pm = processManagerFactory.getProcessManager();

			if (tx != null)
				tx.begin();

			instanceId = workflowService.setProcessVariable(instanceId, processVariableMap, pm);

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

		Element response = new Element(SET_PROCESS_VARIABLE_RESPONSE, WORKFLOW_NAMESPACE).setText(instanceId);

		return response;

	}
}
