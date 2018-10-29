package com.defaultcompany.login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.uengine.security.AclManager;

import com.defaultcompany.organization.web.chartpicker.DefaultDAO;


/**
 * 로그인시 필요한 DB connection 부분 처리
 * @author  JISUN CHOI
 * @param	userId
 * @return	getUserInfo(String userId)	: 로그인 하고자할 user 정보를 가져옴
 * 			return userInfoList(ArrayList);	//emptable, comtable의 user 정보
 * 
 * 			getPermission(String userId): 로그인 완료한 user의 권한을 가져옴(상위메뉴출력목적)
 * 			return permission(String);
 * */

public class LoginDAO extends DefaultDAO{
	
	public LoginDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	public Login getUserInfo(String userId){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT E.EMPNAME, E.EMPCODE, E.PASSWORD, E.ISADMIN, E.JIKNAME, E.EMAIL, E.PARTCODE, E.GLOBALCOM, ")
		   .append("		E.ISDELETED, E.LOCALE, E.NATEON, E.MSN, E.MOBILECMP, E.MOBILENO, P.PARTNAME, C.COMNAME 	  ") 
		   .append(" FROM 	EMPTABLE E 	  ")
		   .append(" 		LEFT JOIN COMTABLE C ON E.GLOBALCOM = C.COMCODE	 ") 
		   .append(" 		LEFT JOIN PARTTABLE P ON P.PARTCODE = E.PARTCODE ") 
		   .append(" WHERE  E.EMPCODE=?	  ")
		   .append(" AND 	E.ISDELETED=0 ")
		   .append(" AND 	C.ISDELETED=0 ");
		
		return (Login) jdbcTemplate.queryForObject(sql.toString(), new Object[] {userId}, new LoginRowMapper());
	}
	
	public String getPermission(String userId){
		return AclManager.getInstance().getPermission(userId);
	}
	
	class LoginRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			Login login = new Login();

			login.setPassword	(rs.getString("PASSWORD"));
			login.setIsadmin	(rs.getString("ISADMIN"));
			login.setEmpname	(rs.getString("EMPNAME"));
			login.setJikname	(rs.getString("JIKNAME"));
			login.setEmail		(rs.getString("EMAIL"));
			login.setPartcode	(rs.getString("PARTCODE"));
			login.setPartname	(rs.getString("PARTNAME"));
			login.setLocale		(rs.getString("LOCALE"));
			login.setGlobalcom	(rs.getString("GLOBALCOM"));
			login.setComname	(rs.getString("COMNAME"));
			login.setNateon		(rs.getString("NATEON"));
			login.setMsn		(rs.getString("MSN"));

			return login;
		}
	}
	
	class PermissionRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return rs.getString("PERMISSION");
		}
	}
}
