package com.defaultcompany.web.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;


public class DashboardProcessListDAO {
	
	protected JdbcTemplate jdbcTemplate;
	
	public DashboardProcessListDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("deprecation")
	private String makeProcessListQuery(String endpoint, String status, int listLength) {
		
		String typeOfDBMS = null;
		try {
			typeOfDBMS = DAOFactory.getInstance().getDBMSProductName().toUpperCase();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		
		if ("ORACLE".equals(typeOfDBMS)) {
			sb.append(" select * from (	");
		}
		
		sb.append(" select ");
		
		
		
		if ("HSQL".equals(typeOfDBMS) ) {
			sb.append("	top ").append(listLength);
		}
		
		if( !"MSSQL".equals(typeOfDBMS))
			sb.append(" DISTINCT(inst.instid), ");
		
		if ( "MSSQL".equals(typeOfDBMS)) {
			sb.append(" DISTINCT ");
			sb.append("	top ").append(listLength);
			sb.append(" inst.instid, ");
		}
		
		
		
		sb.append("  inst.NAME, inst.DEFNAME, inst.STATUS, inst.STARTEDDATE ");
		sb.append(" from bpm_procinst inst INNER JOIN bpm_rolemapping role on role.rootinstid=inst.instid ");
		sb.append(" and role.endpoint='" + endpoint + "' ");
		sb.append(" and inst.status ='" + status + "' ");
		sb.append(" and inst.isdeleted=0 order by inst.starteddate desc ");
		if ("ORACLE".equals(typeOfDBMS)) {
			sb.append(" ) where rownum >= 1 AND rownum <= ").append(listLength);
		}
		if ("MYSQL".equals(typeOfDBMS) || "Postgres".equals(typeOfDBMS)) {
			sb.append("	limit ").append(listLength);
		}

		return sb.toString();
	}
	
	private class ProcessListMapper implements RowMapper {

		int contentMinLength;
		
		public ProcessListMapper(int contentMinLength) {
			this.contentMinLength = contentMinLength;
		}
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DashboardProcessList dashboardWorklist = new DashboardProcessList();
			dashboardWorklist.setInstId(rs.getInt("instId"));
			
			String name = rs.getString("name");
			String defname = rs.getString("defname");
			
			if (contentMinLength != 0) {
				if (name.length() > contentMinLength) {
					name = name.substring(0, contentMinLength) + "...";
				}
				if (defname.length() > contentMinLength) {
					defname = defname.substring(0, contentMinLength) + "...";
				}
			}
			
			dashboardWorklist.setInstName(name);
			dashboardWorklist.setDefname(defname);
			dashboardWorklist.setStatus(rs.getString("status"));
			dashboardWorklist.setStartDate(rs.getTimestamp("STARTEDDATE"));
			
			return dashboardWorklist;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DashboardProcessList> getCompletedProcessList(String endpoint, int contentMinLength, int listLength) {
		String query = makeProcessListQuery(endpoint, "Completed", listLength);
		return this.jdbcTemplate.query(query, new ProcessListMapper(contentMinLength));
	}
	
	@SuppressWarnings("unchecked")
	public List<DashboardProcessList> getRunningProcessList(String endpoint, int contentMinLength, int listLength) {
		String query = makeProcessListQuery(endpoint, "Running", listLength);
		return this.jdbcTemplate.query(query, new ProcessListMapper(contentMinLength));
	}
	
}
