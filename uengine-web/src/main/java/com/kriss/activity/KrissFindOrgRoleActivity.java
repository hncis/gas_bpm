package com.kriss.activity;

import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

public class KrissFindOrgRoleActivity extends DefaultActivity {
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	Role userRole;
		public Role getUserRole() {
			return userRole;
		}
		public void setUserRole(Role userRole) {
			this.userRole = userRole;
		}
		
	Role topRole;
		public Role getTopRole() {
			return topRole;
		}
		public void setTopRole(Role topRole) {
			this.topRole = topRole;
		}
		
		

	@Override
	public void executeActivity(ProcessInstance instance) throws Exception {
		
		//상위부서장 검색 쿼리
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                                                                   \n");
		sql.append("       (                                                                 \n");
		sql.append("           CASE                                                          \n");
		sql.append("               WHEN E.EMPCODE=P.TOP_EMPNO                                \n");
		sql.append("                   AND P_TOP.TOP_EMPNO IS NOT NULL                       \n");
		sql.append("               THEN P_TOP.TOP_EMPNO                                      \n");
		sql.append("               ELSE P.TOP_EMPNO                                          \n");
		sql.append("           END                                                           \n");
		sql.append("       ) AS top_user                                                     \n");
		sql.append("  FROM EMPTABLE E                                                        \n");
		sql.append("   LEFT JOIN PARTTABLE P                                                 \n");
		sql.append("       ON E.PARTCODE=P.PARTCODE AND P.ISDELETED='0'                      \n");
		sql.append("   LEFT JOIN PARTTABLE P_TOP                                             \n");
		sql.append("       ON P.PARENT_PARTCODE=P_TOP.PARTCODE AND P_TOP.ISDELETED='0'       \n");
		sql.append(" WHERE E.EMPCODE=?empcode                                                \n");
		
		IDAO findUserDao = ConnectiveDAO.createDAOImpl(instance.getProcessTransactionContext(), sql.toString(), IDAO.class);
		RoleMapping rm = userRole.getMapping(instance);
		rm.beforeFirst();
		findUserDao.set("empcode", rm.getEndpoint());
		findUserDao.select();
		String topUser = null;
		if(findUserDao.next()) {
			topUser = findUserDao.getString("top_user");
		}
		
		RoleMapping topUserMapping = RoleMapping.create();
		topUserMapping.setName(topRole.getName());
		topUserMapping.beforeFirst();
		topUserMapping.setEndpoint(topUser);
		topUserMapping.fill(instance);
		
		instance.putRoleMapping(topRole.getName(), topUserMapping);
		
		this.fireComplete(instance);
	}
	
	

}
