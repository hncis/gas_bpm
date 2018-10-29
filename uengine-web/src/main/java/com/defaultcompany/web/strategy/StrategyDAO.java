package com.defaultcompany.web.strategy;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class StrategyDAO {
	
	protected JdbcTemplate jdbcTemplate;
	
	public StrategyDAO(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	public void update(Strategy strategy) {
//		StringBuilder sql = new StringBuilder("");
//		sql.append(" update BPM_STRTG set STRTGNM = ?, TYPE = ?, PARENTID = ?, ISDELETED = ? WHERE STRTGID = ? ");
//		
//		this.jdbcTemplate.update(sql.toString(), 
//				new Object[] {
//					strategy.getStrategyName(),
//					strategy.getType(),
//					strategy.getParentId(),
//					strategy.getIsDeleted(),
//					strategy.getStrategyId()
//				}
//		);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getInstanceIdListById(int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select * from BPM_PROCINST WHERE STRATEGYID = ? ");
		
		return this.jdbcTemplate.query(sql.toString(), new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				int instanceId = rs.getInt("INSTID"); 
				return instanceId;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Instance> getInstanceIdListById(List<Integer> ids) {
		StringBuilder sql = new StringBuilder("");
		
		StringBuilder ss = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			if (ss.length() > 0) ss.append(", ");
			ss.append(String.valueOf(ids.get(i)));
		}
		
		sql.append(" select * from BPM_PROCINST WHERE STRATEGYID in ("+ss+") ");
		
		return this.jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Instance instance = new Instance();
				instance.setId(""+ rs.getInt("INSTID") ); 
				instance.setStatus(rs.getString("STATUS")); 
				return instance;
			}
		});
	}
	
	public List<Integer> getParentIds(int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select PARENTID from BPM_STRTG_PARENTMAPPING WHERE STRTGID = ? ");
		
		List<Integer> parentIds = new ArrayList<Integer>();
		List result = this.jdbcTemplate.queryForList(sql.toString(), new Object[] { id } );
		for (int i = 0; i < result.size(); i++) {
			Map parentMap = (Map)result.get(i);
			int parentId=((Integer)parentMap.get("PARENTID")).intValue();
			parentIds.add(parentId);
		}

		return parentIds;
	}
	
	public void setParentId(int parentId ,int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" INSERT INTO BPM_STRTG_PARENTMAPPING (STRTGID, PARENTID) VALUES ( ? , ? ) ");
		
		this.jdbcTemplate.update(sql.toString(), new Object[] { id,parentId });
	}
	
	public void updateCMPLTINSTCNT(int id, int cnt) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" UPDATE BPM_STRTG SET CMPLTINSTCNT=? WHERE STRTGID=? ");
		
		this.jdbcTemplate.update(sql.toString(), new Object[] { cnt , id });
	}
	
	public void move(int parentId ,int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" DELETE FROM BPM_STRTG_PARENTMAPPING WHERE STRTGID=? ");
		
		this.jdbcTemplate.update(sql.toString(), new Object[] { id });
		
		setParentId(parentId, id);
	}
	
	public Strategy getById(int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select * from BPM_STRTG WHERE STRTGID = ? ");
		
		return (Strategy) this.jdbcTemplate.queryForObject(sql.toString(), new Object[] { id }, new StrategyRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	public List<Strategy> getList() {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select * from BPM_STRTG WHERE ISDELETED = '0' ");
		
		return this.jdbcTemplate.query(sql.toString(), new Object[] { }, new StrategyRowMapper());
	}

	public List<Strategy> getChildStrategyListById(int id) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select P.STRTGID from BPM_STRTG S,BPM_STRTG_PARENTMAPPING P WHERE P.PARENTID = ? AND P.STRTGID = S.STRTGID AND S.ISDELETED = '0' ");
		
		List result = this.jdbcTemplate.queryForList(sql.toString(), new Object[] { id } );
		List<Strategy> returnValues = new ArrayList<Strategy>();
		for (int i = 0; i < result.size(); i++) {
			Map parentMap = (Map)result.get(i);
			Strategy strategy = this.getById((Integer) parentMap.get("STRTGID"));
			returnValues.add(strategy);
		}
			
		return returnValues;
	}
	
	private class StrategyRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			Strategy strategy = new Strategy();
			strategy.setStrategyId(rs.getInt("STRTGID"));
			strategy.setDescription((rs.getString("DESCRIPTION")==null)? "":rs.getString("DESCRIPTION"));
			strategy.setStrategyName((rs.getString("STRTGNM")==null)? "":rs.getString("STRTGNM"));
			strategy.setType((rs.getString("TYPE")==null)? "":rs.getString("TYPE"));
			strategy.setIsDeleted((rs.getString("ISDELETED")==null)? "":rs.getString("ISDELETED"));
			strategy.setStatus((rs.getString("STATUS")==null)? "":rs.getString("STATUS"));
			strategy.setInstCnt(rs.getInt("INSTCNT"));
			strategy.setCmpltInstCnt(rs.getInt("CMPLTINSTCNT"));
			strategy.setStartDate(rs.getTimestamp("STARTDATE"));
			strategy.setEndDate(rs.getTimestamp("ENDDATE"));
			strategy.setComCode((rs.getString("COMCODE")==null)? "":rs.getString("COMCODE"));
			strategy.setPartCode((rs.getString("PARTCODE")==null)? "":rs.getString("PARTCODE"));
			strategy.setPartName((rs.getString("PARTNAME")==null)? "":rs.getString("PARTNAME"));			
			return strategy;
		}
	}
}
