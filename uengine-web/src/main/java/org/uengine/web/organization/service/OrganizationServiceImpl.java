package org.uengine.web.organization.service; 

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.RoleMapping;
import org.uengine.web.organization.dao.OrganizationDAO;
import org.uengine.web.organization.vo.OrganizationVO;
import org.uengine.web.organization.vo.RoleVO;
import org.uengine.web.organization.vo.UserVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="organizationDAO")
	private OrganizationDAO organizationDAO;

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectOrganizationChart
	 * @date : 2016. 6. 20.
	 * @author : next3
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 6. 20.		next3				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.web.organization.service.OrganizationService#selectOrganizationChart()
	 * @return
	 * @throws Exception
	 */ 	
	@Override
	public List<OrganizationVO> selectOrganizationTreeByComCode(String comCode) throws Exception {
		return organizationDAO.selectOrganizationTreeByComCode(comCode);
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectUserListByPartCode
	 * @date : 2016. 6. 22.
	 * @author : next3
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 6. 22.		next3				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.web.organization.service.OrganizationService#selectUserListByPartCode(java.lang.String)
	 * @param partCode
	 * @return
	 * @throws Exception
	 */ 	
	@Override
	public List<UserVO> selectUserListByPartCode(String partCode) throws Exception {
		return organizationDAO.selectUserListByPartCode(partCode);
	}

	@Override
	public Document createOrgChartXmlDocument(String comCode) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
       	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
       	
       	//root element
       	Document doc = docBuilder.newDocument();
       	Element rootElement = doc.createElement("Organizations");
       	
       	//part tree
       	//rootpart="-1"
       	setOrganizationElementList(doc, rootElement, comCode, "-1", organizationDAO.getPartListByComCode(comCode));
       	
       	doc.appendChild(rootElement);
		return doc;
	}
	
	@Override
	public Document createGroupChartXmlDocument(String comCode) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		//root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Groups");
		
		//setGroupElementList(doc, rootElement, comCode, "-1", organizationDAO.getPartListByComCode(comCode));
		for(Iterator<RoleMapping> i = organizationDAO.getPartListByComCode(comCode).iterator(); i.hasNext();){
			RoleMapping rm = i.next();
			Element partElement = doc.createElement("part");
			String name = rm.getGroupName();
			String value = rm.getGroupId();
			partElement.setAttribute("name", name);
			partElement.setAttribute("value", value);
			rootElement.appendChild(partElement);
		}
		
		doc.appendChild(rootElement);
		return doc;
	}
	
	@Override
	public Document createRoleChartXmlDocument(String comCode) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		//root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Roles");
		
		for(Iterator<RoleVO> i=organizationDAO.getRoleListByComCode(comCode).iterator(); i.hasNext();){
			RoleVO role = i.next();
			Element roleElement = doc.createElement("part");
			String value = role.getRoleCode();
       		String name = role.getDescr();
       		roleElement.setAttribute("value", value);
       		roleElement.setAttribute("name", name);
       		rootElement.appendChild(roleElement);
		}
		
		doc.appendChild(rootElement);
		return doc;
	}
	
	public void setOrganizationElementList(Document doc, Element parentElement, String comCode, String parent, List<RoleMapping> partList){
		
		for(Iterator<RoleMapping> i=partList.iterator(); i.hasNext();){
			RoleMapping rm = i.next();
			if( rm.getParentGroupId().equals(parent) ) {
				//partElement
				Element partElement = doc.createElement("part");
				String value = rm.getGroupId();
	       		String name = rm.getGroupName();
	       		partElement.setAttribute("value", value);
	       		partElement.setAttribute("name", name);
	       		//subparts
	       		setOrganizationElementList(doc, partElement, comCode, value, partList);
	       		
	       		parentElement.appendChild(partElement);
			}
		}
		
		//resources
		for(Iterator<RoleMapping> iResources = organizationDAO.getWholeUserByPartCode(parent).iterator(); iResources.hasNext();){
			RoleMapping resource = iResources.next();
			Element resourceElement = doc.createElement("resource");
			String value = resource.getEndpoint();
			String name = resource.getResourceName();
			resourceElement.setAttribute("value", value);
			resourceElement.setAttribute("name", name);
			resourceElement.setAttribute("partcode", parent);
			parentElement.appendChild(resourceElement);
		}
	}

	public void setGroupElementList(Document doc, Element parentElement, String comCode, String parent, List<RoleMapping> partList){
		
		for(Iterator<RoleMapping> i=partList.iterator(); i.hasNext();){
			RoleMapping rm = i.next();
			if( rm.getParentGroupId().equals(parent) ) {
				//partElement
				Element partElement = doc.createElement("part");
				String value = rm.getGroupId();
				String name = rm.getGroupName();
				partElement.setAttribute("value", value);
				partElement.setAttribute("name", name);
				//subparts
				setGroupElementList(doc, partElement, comCode, value, partList);
				
				parentElement.appendChild(partElement);
			}
		}
		
	}
}
