package com.defaultcompany.organization.web.chartpicker;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DefaultDAO {
	protected JdbcTemplate jdbcTemplate;
	
	public DefaultDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
