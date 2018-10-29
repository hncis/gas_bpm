package com.defaultcompany.organization.web.chartpicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

public class RoleDAO extends DefaultDAO {

	public RoleDAO(DataSource dataSource) {
		super(dataSource);
	}

	@SuppressWarnings("unchecked")
	public List<Role> getRoles(String comCode) {
		String sql = "SELECT rolecode, descr, comcode FROM roletable WHERE comcode = ? AND isdeleted = '0'";

		return this.jdbcTemplate.query(sql, new Object[] { comCode },
				new RoleRowMapper());
	}

	class RoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			Role role = new Role();

			role.setCode(rs.getString("rolecode"));
			role.setDescr(rs.getString("descr"));
			role.setComcode(rs.getString("comcode"));

			return role;
		}

	}
}
