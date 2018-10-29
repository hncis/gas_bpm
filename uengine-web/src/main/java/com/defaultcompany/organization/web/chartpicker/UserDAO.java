package com.defaultcompany.organization.web.chartpicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;
import org.uengine.util.UEngineUtil;

public class UserDAO {

	protected JdbcTemplate jdbcTemplate;

	public UserDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers(String key, String column) {

		StringBuilder sql = new StringBuilder();
		String dBMSProductName = null;
		try {
			dBMSProductName = DAOFactory.getInstance().getDBMSProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String w = "partname".equals(column) ? "b." : "a.";

		if ("MySQL".equals(dBMSProductName)) {
			sql.append(" SELECT a.*, b.partname FROM emptable a, parttable b WHERE a.partcode = b.partcode AND ")
			.append(w + column).append(" LIKE CONCAT('%', ?, '%') AND a.isdeleted = '0' ");
		} else if ("Oracle".equals(dBMSProductName)) {
			sql.append(" SELECT a.*, b.partname FROM emptable a LEFT JOIN parttable b ON a.partcode = b.partcode WHERE ")
			.append(w + column).append(" LIKE '%'||?||'%' AND a.isdeleted = '0' ");
		} else if ("MSsql".equals(dBMSProductName)) {
			sql.append(" SELECT a.*, b.partname FROM emptable a LEFT JOIN parttable b ON a.partcode = b.partcode WHERE ")
			.append(w + column).append(" LIKE '%' + ? + '%' AND a.isdeleted = '0' ");
		} else {
			sql.append(	" SELECT a.*, b.partname FROM emptable as a LEFT JOIN parttable as b ON a.partcode = b.partcode WHERE ")
			.append(w + column).append(" LIKE '%'||?||'%' AND a.isdeleted = '0' ");
		}

		return this.jdbcTemplate.query(sql.toString(), new Object[] { key },
				new UserRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersOfDepartment(String partCode) {
		StringBuilder sql = new StringBuilder();
		String dBMSProductName = null;
		try {
			dBMSProductName = DAOFactory.getInstance().getDBMSProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("MySQL".equals(dBMSProductName)) {
			sql.append("SELECT a.*, b.partname FROM emptable a, parttable b WHERE a.partcode = b.partcode AND a.partcode = ? AND a.isdeleted = '0'");
		} else if ("Oracle".equals(dBMSProductName)) {
			sql.append("SELECT a.*, b.partname FROM emptable a LEFT JOIN parttable b ON a.partcode = b.partcode WHERE a.partcode = ? AND a.isdeleted = '0'");
		} else {
			sql.append("SELECT a.*, b.partname FROM emptable as a LEFT JOIN parttable as b ON a.partcode = b.partcode WHERE a.partcode = ? AND a.isdeleted = '0'");
		}

		return this.jdbcTemplate.query(sql.toString(),
				new Object[] { partCode }, new UserRowMapper());
	}

	class UserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setCode(nullToVoid(rs.getString("empcode")));
			user.setName(nullToVoid(rs.getString("empname")));
			user.setJikname(nullToVoid(rs.getString("jikname")));
			user.setEmail(nullToVoid(rs.getString("email")));
			user.setPartcode(nullToVoid(rs.getString("partcode")));
			user.setPartname(nullToVoid(rs.getString("partname")));
			user.setGlobalcom(nullToVoid(rs.getString("globalcom")));

			return user;
		}
		
		private String nullToVoid(String val) {
		    if (UEngineUtil.isNotEmpty(val)) {
		        val = val.trim();
		    } else {
		        val = "";
		    }
		    
		    return val;
		}
	}
}
