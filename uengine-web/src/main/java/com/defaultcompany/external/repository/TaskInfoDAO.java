package com.defaultcompany.external.repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.TaskInfoMsg;

public class TaskInfoDAO {
	private JdbcTemplate jt;
	
	public TaskInfoDAO(DataSource ds) {
		jt = new JdbcTemplate(ds);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskInfo> getWorkItemInfoBySearchContext(TaskInfoMsg taskInfoMsg) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select "); 
		sql.append(" DISTINCT INST.NAME as procinstnm, INST.initrsnm, INST.INFO, WL.* "); 
		sql.append(" FROM BPM_PROCINST INST, BPM_WORKLIST WL "); 
		sql.append(" INNER JOIN BPM_ROLEMAPPING ROLE ON "); 
		sql.append(" ( "); 
		sql.append("   WL.ROLENAME=ROLE.ROLENAME OR WL.REFROLENAME=ROLE.ROLENAME "); 
		sql.append(" ) "); 
		sql.append(" OR WL.ENDPOINT = ? "); 
		if (StringUtils.hasText(taskInfoMsg.getExternalKey())) {
			sql.append(" where INST.INFO = ").append(taskInfoMsg.getExternalKey());
		} else if (StringUtils.hasText(taskInfoMsg.getInstanceId())) {
			sql.append(" where INST.INFO = (SELECT INFO FROM BPM_PROCINST WHERE INSTID = ").append(taskInfoMsg.getInstanceId()).append(") ");	
		}
		sql.append(" AND (wl.status = 'NEW' or wl.status = 'CONFIRMED' or wl.status = 'DRAFT') ");
		sql.append(" AND WL.INSTID=INST.INSTID "); 
		sql.append(" AND WL.INSTID=ROLE.INSTID "); 
		sql.append(" AND INST.ISDELETED=0 "); 
		sql.append(" AND INST.STATUS<>'Stopped' "); 
		sql.append(" AND INST.STATUS<>'Failed' "); 
		sql.append(" AND ROLE.ENDPOINT = ? "); 
		sql.append(" ORDER BY WL.STARTDATE DESC ");
		
		return (List<TaskInfo>) jt.query(sql.toString(), 
				new Object[] { taskInfoMsg.getEndpoint(), taskInfoMsg.getEndpoint() }, new TaskInfoRowMapper());
	}
	
	class TaskInfoRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			TaskInfo item = new TaskInfo();
			
			item.setInstanceId(rs.getString("INSTID"));
			item.setTaskId(rs.getString("TASKID"));
			item.setTracingTag(rs.getString("TRCTAG"));
			
			item.setHandler(rs.getString("TOOL"));
			item.setMainParam(rs.getString("EXT1"));
			item.setSubParam(rs.getString("EXT2"));
			
			item.setExternalKey(rs.getString("INFO"));
				
			return item;
		}
	}
	
}
