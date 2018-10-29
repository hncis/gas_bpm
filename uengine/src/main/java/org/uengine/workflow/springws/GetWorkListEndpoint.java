package org.uengine.workflow.springws;

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
import org.uengine.util.UEngineUtil;
import org.uengine.workflow.model.WorkList;
import org.uengine.workflow.springservice.WorkflowService;

public class GetWorkListEndpoint extends AbstractJDomPayloadEndpoint implements WorkflowWebServiceConstants {

	private WorkflowService workflowService;

	private XPath endpointExpression;
	private XPath filterExpression;
	private XPath pageCountExpression;
	private XPath currentPageExpression;

	public GetWorkListEndpoint(WorkflowService workflowService) {

		this.workflowService = workflowService;

		try {
			endpointExpression = XPath.newInstance("//wf:endpoint");
			endpointExpression.addNamespace(WORKFLOW_NAMESPACE);

			filterExpression = XPath.newInstance("//wf:filter");
			filterExpression.addNamespace(WORKFLOW_NAMESPACE);

			pageCountExpression = XPath.newInstance("//wf:pageCount");
			pageCountExpression.addNamespace(WORKFLOW_NAMESPACE);

			currentPageExpression = XPath.newInstance("//wf:currentPage");
			currentPageExpression.addNamespace(WORKFLOW_NAMESPACE);

		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element invokeInternal(Element getWorkListRequest) throws Exception {

		String endpoint = endpointExpression.valueOf(getWorkListRequest);
		String filter = filterExpression.valueOf(getWorkListRequest);
		String _pageCount = pageCountExpression.valueOf(getWorkListRequest);
		int pageCount = Integer.parseInt(UEngineUtil.isNotEmpty(_pageCount) ? _pageCount : "10");
		String _currentPage = currentPageExpression.valueOf(getWorkListRequest);
		int currentPage = Integer.parseInt(UEngineUtil.isNotEmpty(_currentPage) ? _currentPage : "1");

		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;

//		InitialContext context = new InitialContext();
//		UserTransaction tx = (GlobalContext.useManagedTransaction ? (UserTransaction) context.lookup(GlobalContext.USERTRANSACTION_JNDI_NAME) : null);

		List workList = null;

		try {
			pm = processManagerFactory.getProcessManagerForReadOnly();

			workList = workflowService.getWorkList(endpoint, filter, pageCount, currentPage, pm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pm.remove();
			} catch (Exception e) {
			}
		}

		Document doc = new Document(new Element(GET_WORKLIST_RESPONE, WORKFLOW_NAMESPACE));

		doc.getRootElement().addContent(new Element("totalCount", WORKFLOW_NAMESPACE).setText((String) workList.get(0)));

		for (int i = 1; i < workList.size(); i++) {
			WorkList wl = (WorkList) workList.get(i);
			Element element = new Element("workList", WORKFLOW_NAMESPACE);
			element.addContent(new Element("endpoint", WORKFLOW_NAMESPACE).setText(wl.getEndpoint()));
			element.addContent(new Element("instanceId", WORKFLOW_NAMESPACE).setText(wl.getInstanceId()));
			element.addContent(new Element("rootInstanceId", WORKFLOW_NAMESPACE).setText(wl.getRootInstanceId()));
			element.addContent(new Element("taskId", WORKFLOW_NAMESPACE).setText(wl.getTaskId()));
			element.addContent(new Element("tracingTag", WORKFLOW_NAMESPACE).setText(wl.getTracingTag()));
			element.addContent(new Element("title", WORKFLOW_NAMESPACE).setText(wl.getTitle()));
			element.addContent(new Element("defName", WORKFLOW_NAMESPACE).setText(wl.getDefName()));
			element.addContent(new Element("startDate", WORKFLOW_NAMESPACE).setText(wl.getStartDate()));
			element.addContent(new Element("duplicateTaskCount", WORKFLOW_NAMESPACE).setText(wl.getDuplicateTaskCount()));

			element.addContent(new Element("info", WORKFLOW_NAMESPACE).setText(wl.getInfo()));
			doc.getRootElement().addContent(element);
		}

		Element returnElement = doc.getRootElement();

		return returnElement;
	}

}