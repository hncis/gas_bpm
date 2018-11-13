package org.uengine.web.organization.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.kernel.RoleMapping;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.organization.vo.OrganizationVO;
import org.uengine.web.organization.vo.RoleVO;
import org.uengine.web.organization.vo.UserVO;


/**
 * <pre>
 * org.uengine.web.organization.dao 
 * OrganizationDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 1:20:07
 * @version : 
 * @author : next3
 */
/**
 * <pre>
 * org.uengine.web.organization.dao 
 * OrganizationDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 1:20:16
 * @version : 
 * @author : next3
 */
@Repository("organizationDAO")
public class OrganizationDAO extends AbstractDAO {

	public List<OrganizationVO> selectOrganizationTreeByComCode(String comCode) throws Exception {
		return (List<OrganizationVO>) selectList("organization.selectOrganizationTreeByComCode", comCode);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserVO> selectUserListByPartCode(String partCode) throws Exception {
		return (List<UserVO>) selectList("organization.selectUserListByPartCode", partCode);
	}
	
	public List<RoleMapping> getWholeUserByPartCode(String partCode) {
		return (List<RoleMapping>) selectList("organization.selectWholeUserByPartcode", partCode);
	}
	
	public List<RoleMapping> getPartListByComCode(String comCode) {
		return (List<RoleMapping>) selectList("organization.selectPartListByComCode", comCode);
	}

	public List<RoleVO> getRoleListByComCode(String comCode) {
		return (List<RoleVO>) selectList("organization.selectRoleListByComCode", comCode);
	}

}
