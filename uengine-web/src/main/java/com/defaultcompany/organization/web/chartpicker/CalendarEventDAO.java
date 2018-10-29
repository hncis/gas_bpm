package com.defaultcompany.organization.web.chartpicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;


public class CalendarEventDAO extends DefaultDAO {

	public CalendarEventDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List<CalendarEvent> getEvents(Timestamp start, Timestamp end, String endpoint) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT w.taskid, w.title, i.name, w.description, w.startdate, w.enddate, w.status");
		sql.append(" FROM bpm_worklist w RIGHT JOIN bpm_procinst i ON w.instid = i.instid AND i.isdeleted <> 1 AND i.status <> 'Stopped' AND i.status <> 'Failed' ");
		sql.append(" WHERE w.endpoint = ? AND w.status <> 'CANCELLED' AND (w.startdate < ? AND (w.enddate > ? OR w.enddate is null)) ");
		
		return this.jdbcTemplate.query(
				sql.toString(),
				new Object[] {endpoint, end, start},
				new CalendarEventRowMapper()
		);
	}
	
	class CalendarEventRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			CalendarEvent calendarEvent = new CalendarEvent();
			
			calendarEvent.setTaskid(rs.getString("taskid"));
			calendarEvent.setStatus(rs.getString("status"));
			calendarEvent.setStartdate(rs.getTimestamp("startdate"));
			calendarEvent.setEnddate(rs.getTimestamp("enddate"));
			calendarEvent.setTitle(rs.getString("name") + " / " + rs.getString("title"));
			calendarEvent.setDescription(rs.getString("description"));
			
			return calendarEvent;
		}
		
	}
	
}
