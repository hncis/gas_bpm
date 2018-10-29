package com.defaultcompany.web.dashboard;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


public class DashboardCountDAO {

	protected JdbcTemplate jdbcTemplate;
	
	public DashboardCountDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private String makeWorkCountQuery(String endpoint, String status) {
		StringBuilder sb = new StringBuilder();

		String condition = null;

		if ("NewWork".equals(status)) {
			condition = "wl.status in('NEW', 'CONFIRMED')";
		} else if ("SavedWork".equals(status)) {
			condition = "wl.status = 'DRAFT'";
		} else if ("CompletedWork".equals(status)) {
			condition = "wl.status = 'COMPLETED'";
		}
		
		sb.append(" SELECT COUNT(*) 			");
		sb.append(" FROM ( 						");
		sb.append(" 	SELECT DISTINCT INST.NAME as procinstnm, INST.INFO, WL.* 	");
		sb.append(" 	FROM BPM_PROCINST INST, BPM_WORKLIST WL 					");
		sb.append(" 	INNER JOIN BPM_ROLEMAPPING ROLE ON (WL.ROLENAME=ROLE.ROLENAME OR WL.REFROLENAME=ROLE.ROLENAME) OR WL.ENDPOINT=ROLE.ENDPOINT");
		sb.append(" WHERE 						").append(condition);
		sb.append(" AND WL.INSTID=INST.INSTID 	");
		sb.append(" AND WL.INSTID=ROLE.INSTID 	");
		sb.append(" AND INST.ISDELETED=0		");
		sb.append(" AND ROLE.ENDPOINT='" + endpoint + "' ");
		sb.append(" AND INST.STATUS NOT IN('Stopped', 'Failed')");
		sb.append(" ) c ");

		return sb.toString();
	}

	public int getNewWorkCount(String endpoint) {
		String queryStr = makeWorkCountQuery(endpoint, "NewWork");
		return this.jdbcTemplate.queryForInt(queryStr);
	}

	public int getSavedWorkCount(String endpoint) {
		String queryStr = makeWorkCountQuery(endpoint, "SavedWork");
		return this.jdbcTemplate.queryForInt(queryStr);
	}

	public int getCompletedWorkCount(String endpoint) {
		String queryStr = makeWorkCountQuery(endpoint, "CompletedWork");
		return this.jdbcTemplate.queryForInt(queryStr);
	}

	private String makeProcessCountQuery(String endpoint, String status) {
		StringBuilder sb = new StringBuilder();

		sb.append(" select count(*) ");
		sb.append(" from bpm_procinst inst, ");
		sb.append(" ( ");
		sb.append(" select rootInstId,endpoint ");
		sb.append(" from bpm_rolemapping ");
		sb.append(" where endpoint='").append(endpoint).append("' ");
		sb.append(" group by rootInstId, endpoint ");
		sb.append(" ) roll ");
		sb.append(" where inst.instId = roll.rootInstId ");
		sb.append(" and roll.endpoint='").append(endpoint).append("' ");
		sb.append(" and inst.status ='").append(status).append("' ");
		sb.append(" and inst.isdeleted=0");

		return sb.toString();
	}

	public int getRunningProcessCount(String endpoint) {
		String queryStr = makeProcessCountQuery(endpoint, "Running");
		return this.jdbcTemplate.queryForInt(queryStr);
	}

	public int getCompletedProcessCount(String endpoint) {
		String queryStr = makeProcessCountQuery(endpoint, "Completed");
		return this.jdbcTemplate.queryForInt(queryStr);
	}

	public DashboardCount getAllDashboardCount(String endpoint) {
		DashboardCount dashboardCount = new DashboardCount();

		dashboardCount.setNewWork(getNewWorkCount(endpoint));
		dashboardCount.setSavedWork(getSavedWorkCount(endpoint));
		dashboardCount.setCompletedWork(getCompletedWorkCount(endpoint));
		dashboardCount.setRunningProcess(getRunningProcessCount(endpoint));
		dashboardCount.setCompletedProcess(getCompletedProcessCount(endpoint));

		return dashboardCount;
	}
}