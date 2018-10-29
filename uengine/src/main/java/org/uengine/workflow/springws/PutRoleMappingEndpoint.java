package org.uengine.workflow.springws;

import java.util.ArrayList;
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
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.workflow.springservice.WorkflowService;
import org.uengine.workflow.springws.util.ElementToObject;
import org.uengine.workflow.springws.util.ObjectToElement;

public class PutRoleMappingEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {
	
	private WorkflowService workflowService;
	
	private XPath instanceIdExpression;
	private XPath roleNameExpression;

	public PutRoleMappingEndpoint(WorkflowService workflowService) {
		this.workflowService = workflowService;
		
		try {
			instanceIdExpression = XPath.newInstance("//wf:instanceId");
			instanceIdExpression.addNamespace(WORKFLOW_NAMESPACE);
			
			roleNameExpression = XPath.newInstance("//wf:roleName");
			roleNameExpression.addNamespace(WORKFLOW_NAMESPACE);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element putRoleMappingRequest) throws Exception {

		String instanceId = instanceIdExpression.valueOf(putRoleMappingRequest);
		String roleName = roleNameExpression.valueOf(putRoleMappingRequest);
		List elementEndpointList = putRoleMappingRequest.getChildren("endpoints", WORKFLOW_NAMESPACE);
		ArrayList endpoints = ElementToObject.makeEndpointList(elementEndpointList);
		
		RoleMapping roleMapping = RoleMapping.create();

		for (int i = 0; i < endpoints.size(); i++) {
			String endpoint = (String) endpoints.get(i);
			
			roleMapping.setEndpoint(endpoint);
			
			if ( i != endpoints.size() - 1)
				roleMapping.moveToAdd();
		}

		roleMapping.setName(roleName);
		
		
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

		InitialContext context = new InitialContext();
		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		boolean isSuccess = false;
		
		try {
			pm = processManagerFactory.getProcessManager();

			if (tx != null)
				tx.begin();

			isSuccess = workflowService.putRoleMapping(instanceId, roleMapping, pm);
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
		
		Element response = new Element(PUT_ROLEMAPPING_REQUEST, WORKFLOW_NAMESPACE).setText(String.valueOf(isSuccess));

		return response;
	}
}
