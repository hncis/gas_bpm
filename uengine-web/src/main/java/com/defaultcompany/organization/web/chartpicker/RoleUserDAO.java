package com.defaultcompany.organization.web.chartpicker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;

public class RoleUserDAO extends DefaultDAO {

	public RoleUserDAO(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoleUser> getRoleUsers(String roleCode) {

		StringBuilder sql = new StringBuilder();
		String dBMSProductName = null;
		try {
			dBMSProductName = DAOFactory.getInstance().getDBMSProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("MySQL".equals(dBMSProductName)) {
			sql.append("SELECT e.*, r.rolecode, p.partname FROM emptable e, roleusertable r, parttable p WHERE e.partcode = p.partcode AND e.empcode = r.empcode AND ");
		} else if ("Oracle".equals(dBMSProductName)) {
			sql.append(" SELECT e.*, r.rolecode, p.partname FROM emptable e LEFT JOIN parttable p ON e.partcode = p.partcode LEFT JOIN roleusertable r ON e.empcode = r.empcode WHERE ");
		} else {
			sql.append(" SELECT e.*, r.rolecode, p.partname FROM emptable as e LEFT JOIN parttable as p ON e.partcode = p.partcode LEFT JOIN roleusertable as r ON e.empcode = r.empcode WHERE ");
		}
		sql.append("e.isdeleted = '0' AND r.rolecode = ?");

		return this.jdbcTemplate.query(sql.toString(),
				new Object[] { roleCode }, new UserRowMapper());
	}

	class UserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleUser user = new RoleUser();
			user.setCode(rs.getString("rolecode") + ":"
					+ rs.getString("empcode"));
			user.setUsercode(rs.getString("empcode"));
			user.setName(rs.getString("empname"));
			user.setJikname(rs.getString("jikname"));
			user.setEmail(rs.getString("email"));
			user.setPartcode(rs.getString("partcode"));
			user.setPartname(rs.getString("partname"));
			user.setGlobalcom(rs.getString("globalcom"));

			return user;
		}
	}

	 
	@SuppressWarnings("unchecked")
	public int[] deleteRoleUsers(final String rolecode, final String[] empcodeList) {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ROLEUSERTABLE WHERE ROLECODE = ? AND   EMPCODE  = ? ");
		return this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			 
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, rolecode);
				ps.setString(2, empcodeList[i]);
			}
		 
			@Override
			public int getBatchSize() {
				return empcodeList.length;
			}
		  });

		//return this.jdbcTemplate.update(sql.toString(), new Object[] { rolecode, empcode });
//		this.jdbcTemplate.query(sql.toString(), new Object[] { rolecode, empcode }, new UserRowMapper()); 
	}
}
