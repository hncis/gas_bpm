package com.defaultcompany.web.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;


public class DashboardWorkListDAO {
	
	protected JdbcTemplate jdbcTemplate;
	
	public DashboardWorkListDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List<DashboardWorkList> getOpenWorkList(String endpoint, final int contentMinLength, int listLength) {
		
		String typeOfDBMS = null;
		try {
			typeOfDBMS = DAOFactory.getInstance().getDBMSProductName().toUpperCase();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		String condition = "wl.status = 'NEW' or wl.status = 'CONFIRMED' or wl.status = 'DRAFT'";
		
		if ("ORACLE".equals(typeOfDBMS)) {
			sb.append(" select * from (	");
		}
		sb.append(" select	");
		if ("HSQL".equals(typeOfDBMS) ) {
			sb.append("	top ").append(listLength);
		}
		
		sb.append(" DISTINCT ");
		
		if ( "MSSQL".equals(typeOfDBMS)) {
			sb.append("	top ").append(listLength);
		}
		sb.append(" INST.NAME as procinstnm, INST.INFO, WL.* FROM BPM_PROCINST INST, ");
		sb.append(" BPM_WORKLIST WL INNER JOIN BPM_ROLEMAPPING ROLE ON (WL.ROLENAME=ROLE.ROLENAME OR WL.REFROLENAME=ROLE.ROLENAME) ");
		sb.append("	OR WL.ENDPOINT='" + endpoint + "' ");
		sb.append(" WHERE ("+ condition +") ");
		sb.append(" AND WL.INSTID=INST.INSTID AND WL.INSTID=ROLE.INSTID AND INST.ISDELETED=0 AND INST.STATUS<>'Stopped' AND INST.STATUS<>'Failed' ");
		sb.append(" AND ROLE.ENDPOINT='" + endpoint + "' ");
		sb.append(" order by wl.startdate desc	");
		if ("ORACLE".equals(typeOfDBMS)) {
			sb.append(" ) where rownum >= 1 AND rownum <= ").append(listLength);
		}
		if ("MYSQL".equals(typeOfDBMS)) { 
			sb.append("	limit ").append(listLength);
		}
		
		return this.jdbcTemplate.query(sb.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DashboardWorkList dashboardWorklist = new DashboardWorkList();
				dashboardWorklist.setInstId(rs.getInt("instId"));
				dashboardWorklist.setTrcTag(rs.getString("trcTag"));
				dashboardWorklist.setTaskId(rs.getInt("taskId"));
				
				String title = rs.getString("title");
				String procinstnm = rs.getString("procinstnm");
				
				if (contentMinLength != 0) {
					if (title.length() > contentMinLength) {
						title = title.substring(0, contentMinLength) + "...";
					}
					if (procinstnm.length() > contentMinLength) {
						procinstnm = procinstnm.substring(0, contentMinLength) + "...";
					}
				}
				
				dashboardWorklist.setTitle(title);
				dashboardWorklist.setProcinstnm(procinstnm);
				dashboardWorklist.setStatus(rs.getString("status"));
				dashboardWorklist.setStartDate(rs.getTimestamp("startDate"));
				
				return dashboardWorklist;
			}
		});

	}
	
}
