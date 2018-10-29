package com.defaultcompany.web.acl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;


public class AuthorityDAO {
	
	protected JdbcTemplate jdbcTemplate;
	
	public AuthorityDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int insertAuthority(int defId, String empCode, String partCode, String roleCode, String permission) {
		
		StringBuilder sql = new StringBuilder();
//		sql.append(" INSERT INTO bpm_acltable (acltableid, defid, partcode, empcode, rolecode, permission) ");
//		sql.append(" VALUES ((select NEXT VALUE FOR SEQ_BPM_ACLTABLE from DUAL), ?, ?, ?, ?, ?)" );
		
		try{
			sql.append(" INSERT INTO bpm_acltable (acltableid, defid, partcode, empcode, rolecode, permission) ");
			sql.append(" VALUES (");
			sql.append(DAOFactory.getInstance(null).getSequenceSql("BPM_ACLTABLE"));
			sql.append(", ?, ?, ?, ?, ?)" );
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.jdbcTemplate.update(sql.toString(), new Object[] { defId, partCode, empCode, roleCode, permission });
	}
	
	public int deleteAuthority(int aclTableId) {
		
		String sql = "DELETE FROM bpm_acltable WHERE acltableid=?";
		
		return this.jdbcTemplate.update(sql, new Object[] { aclTableId});
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Authority> getAuthoritys(int defId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.*, c.comname, p.partname, e.empname, r.descr ");
		sql.append(" FROM bpm_acltable as a ");
		sql.append(" LEFT JOIN comtable as c ON a.comcode = c.comcode ");
		sql.append(" LEFT JOIN parttable as p ON a.partcode = p.partcode ");
		sql.append(" LEFT JOIN emptable as e ON a.empcode = e.empcode ");
		sql.append(" LEFT JOIN roletable as r ON a.rolecode = r.rolecode ");
		sql.append(" WHERE a.defid = ? ");
		
		return this.jdbcTemplate.query(sql.toString(), new Object[] { defId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Authority authority = new Authority();
				
				authority.setAclid(rs.getString("acltableid"));
				authority.setComcode(rs.getString("comcode"));
				authority.setComname(rs.getString("comname"));
				authority.setPartcode(rs.getString("partcode"));
				authority.setPartname(rs.getString("partname"));
				authority.setEmpcode(rs.getString("empcode"));
				authority.setEmpname(rs.getString("empname"));
				authority.setRolecode(rs.getString("rolecode"));
				authority.setRolename(rs.getString("descr"));
				authority.setDefaultuser(rs.getString("defaultuser"));
				authority.setPermission(rs.getString("permission"));
				
				return authority;
			}
		});

	}
}
