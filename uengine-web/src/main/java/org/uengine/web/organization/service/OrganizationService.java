package org.uengine.web.organization.service;

import java.util.List;

import org.uengine.web.organization.vo.OrganizationVO;
import org.uengine.web.organization.vo.UserVO;
import org.w3c.dom.Document;

public interface OrganizationService {

	public List<OrganizationVO> selectOrganizationTreeByComCode(String comCode)
			throws Exception;

	public List<UserVO> selectUserListByPartCode(String partCode)
			throws Exception;

	public Document createOrgChartXmlDocument(String comCode) throws Exception;

	public Document createGroupChartXmlDocument(String comCode)
			throws Exception;

	public Document createRoleChartXmlDocument(String comCode) throws Exception;

}
