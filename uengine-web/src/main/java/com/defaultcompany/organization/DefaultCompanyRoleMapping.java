package com.defaultcompany.organization;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.UEngineException;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.GenericDAO;
import org.uengine.util.dao.IDAO;


public class DefaultCompanyRoleMapping extends RoleMapping{
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	final static String EXT_PROP_KEY_NateOnMessengerId = "EXT_PROP_KEY_NATEON_ID";

	public void fill(TransactionContext tc) throws Exception {
		if(GlobalContext.isDesignTime()) return;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("	E.PASSWORD, ");
		sql.append("	E.ISADMIN, ");
		sql.append(" 	E.EMPNAME, ");
		sql.append("	E.NATEON, ");
		sql.append("	E.MSN, ");
		sql.append("	E.GLOBALCOM, ");
		sql.append("	E.JIKNAME, ");
		sql.append("	E.EMAIL, ");
		sql.append("	E.PARTCODE, ");
		sql.append("	E.LOCALE, ");
		sql.append("	P.PARTNAME ");
		sql.append(" FROM EMPTABLE E ");
		sql.append(" LEFT JOIN PARTTABLE P ");
		sql.append(" ON E.PARTCODE   = P.PARTCODE ");
		sql.append(" WHERE E.EMPCODE = ?EMPCODE ");

		IDAO user = null;
		boolean isNewTc = false;
		if (tc == null){
			tc = new SimpleTransactionContext();
			isNewTc = true;
		}
		try {
			if (!isNewTc)
				user = ConnectiveDAO.createDAOImpl(
						tc, 
						sql.toString(),
						IDAO.class
				);
			else
				user = GenericDAO.createDAOImpl(
						tc, 
						sql.toString(),
						IDAO.class
				);
			
			user.set("EMPCODE", getEndpoint());
			user.select();
			
			if (user.next()){
				String	PASSWORD 	= user.getString("PASSWORD")==null?"RIP":user.getString("PASSWORD");
				String	EMPNAME  	= user.getString("EMPNAME")==null?"":user.getString("EMPNAME");
				String	JIKNAME  	= user.getString("JIKNAME")==null?"":user.getString("JIKNAME");
				String	EMAIL    	= user.getString("EMAIL")==null?"":user.getString("EMAIL");
				String	PARTCODE 	= user.getString("PARTCODE")==null?"":user.getString("PARTCODE");
				String	PARTNAME 	= user.getString("PARTNAME")==null?"":user.getString("PARTNAME");
				String	NATEON 		= user.getString("NATEON")==null?"":user.getString("NATEON");
				String	MSN			= user.getString("MSN")==null?"":user.getString("MSN");
				String	LOCALE 		= user.getString("LOCALE")==null?"":user.getString("LOCALE");
				String	GLOBALCOM 	= user.getString("GLOBALCOM")==null?"":user.getString("GLOBALCOM");
				
				setResourceName(EMPNAME);
				setEmailAddress(EMAIL);
				setTitle(JIKNAME);
				setGroupId(PARTCODE);
				setGroupName(PARTNAME);
				setInstanceMessengerId(MSN);
				setExtendedProperty("password", PASSWORD);
				setExtendedProperty(EXT_PROP_KEY_NateOnMessengerId, NATEON);
				setLocale(LOCALE);
				setCompanyId(GLOBALCOM);
				
				setUserPortrait(GlobalContext.WEB_CONTEXT_ROOT+GlobalContext.PORTRAIT_PATH+getEndpoint()+".gif");
			}else
				 new UEngineException("There's no such user ["+ getEndpoint() + "]");
		} finally {
			if (isNewTc) {
				tc.releaseResources();
			}
		}
		
		
	}
	
	public static void main(String args[]) throws Exception{
		RoleMapping rm = new DefaultCompanyRoleMapping();
		rm.fill(null);
	}
}