package com.defaultcompany.organization.web.chartpicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.uengine.persistence.dao.DAOFactory;
import org.uengine.util.UEngineUtil;

public class DepartmentDAO extends DefaultDAO {

	public DepartmentDAO(DataSource dataSource) {
		super(dataSource);
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDeparments(String comCode) {
//		String sql = "SELECT * FROM parttable WHERE globalcom = ? AND isdeleted = '0'";
		String sql =  " SELECT PT.* ,  (COUNT(ET.PARTCODE) + COUNT(PT2.PARTCODE)) CNT"
					 +" FROM PARTTABLE  PT"
					 +" 	LEFT OUTER JOIN EMPTABLE ET ON PT.PARTCODE = ET.PARTCODE"
					 +" 		AND ET.ISDELETED = '0'"
					 +" 	LEFT OUTER JOIN PARTTABLE PT2 ON PT.PARTCODE = PT2.PARENT_PARTCODE"
					 +" 		AND PT2.ISDELETED = '0'"
					 +" WHERE  PT.ISDELETED = '0'"
					 +" AND PT.GLOBALCOM = ?"
					 +" GROUP BY PT.GLOBALCOM, PT.PARTCODE, PT.PARTNAME, PT.ISDELETED, PT.DESCRIPTION, PT.PARENT_PARTCODE";

		return this.jdbcTemplate.query(sql, new Object[] { comCode },
				new DepartmentRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDeparment(String partCode) {
//		String sql = "SELECT * FROM parttable WHERE partcode=?";
		String sql =  " SELECT PT.* ,  (COUNT(ET.PARTCODE) + COUNT(PT2.PARTCODE)) CNT"
			 +" FROM PARTTABLE  PT"
			 +" 	LEFT OUTER JOIN EMPTABLE ET ON PT.PARTCODE = ET.PARTCODE"
			 +" 		AND ET.ISDELETED = '0'"
			 +" 	LEFT OUTER JOIN PARTTABLE PT2 ON PT.PARTCODE = PT2.PARENT_PARTCODE"
			 +" 		AND PT2.ISDELETED = '0'"
			 +" WHERE  PT.ISDELETED = '0'"
			 +" AND    PT.PARTCODE 	= ?"
			 +" GROUP BY PT.GLOBALCOM, PT.PARTCODE, PT.PARTNAME, PT.ISDELETED, PT.DESCRIPTION, PT.PARENT_PARTCODE";

		return this.jdbcTemplate.query(sql, new Object[] { partCode },
				new DepartmentRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Department> getChildrenOfDeparment(String parentCode) {
//		String sql = "SELECT * FROM parttable WHERE parent_partcode = ? AND isdeleted = '0'";
		String sql =  " SELECT PT.* ,  (COUNT(ET.PARTCODE) + COUNT(PT2.PARTCODE)) CNT"
			 +" FROM PARTTABLE  PT"
			 +" 	LEFT OUTER JOIN EMPTABLE ET ON PT.PARTCODE = ET.PARTCODE"
			 +" 		AND ET.ISDELETED = '0'"
			 +" 	LEFT OUTER JOIN PARTTABLE PT2 ON PT.PARTCODE = PT2.PARENT_PARTCODE"
			 +" 		AND PT2.ISDELETED = '0'"
			 +" WHERE  PT.ISDELETED    = '0'"
			 +" AND PT.PARENT_PARTCODE = ?"
			 +" GROUP BY PT.GLOBALCOM, PT.PARTCODE, PT.PARTNAME, PT.ISDELETED, PT.DESCRIPTION, PT.PARENT_PARTCODE";

		return this.jdbcTemplate.query(sql, new Object[] { parentCode },
				new DepartmentRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Department> findDepartment(String column, String key,
			String globalCom) {

		StringBuilder sql = new StringBuilder();
		String dBMSProductName = null;
		try {
			dBMSProductName = DAOFactory.getInstance().getDBMSProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		sql.append(" SELECT PT.* ,  (COUNT(ET.PARTCODE) + COUNT(PT2.PARTCODE)) CNT FROM PARTTABLE ");
		sql.append(" 	LEFT OUTER JOIN EMPTABLE ET ON PT.PARTCODE = ET.PARTCODE ");
		sql.append(" 		AND ET.ISDELETED = '0' ");
		sql.append(" 	LEFT OUTER JOIN PARTTABLE PT2 ON PT.PARTCODE = PT2.PARENT_PARTCODE ");
		sql.append(" 		AND PT2.ISDELETED = '0' ");
		
		sql.append(" WHERE PT." + column);
		if ("MySQL".equals(dBMSProductName)) {
			sql.append(" LIKE CONCAT('%', ?, '%') ");
		} else {
			sql.append(" LIKE '%'||?||'%' ");
		}

		Object[] parameter = null;
		if (UEngineUtil.isNotEmpty(globalCom)) {
			sql.append(" AND PT.globalcom = ? ");
			parameter = new Object[] { key, globalCom };
		} else {
			parameter = new Object[] { key };
		}

		sql.append(" AND PT.ISDELETED = '0' ");
		sql.append(" GROUP BY PT.GLOBALCOM, PT.PARTCODE, PT.PARTNAME, PT.ISDELETED, PT.DESCRIPTION, PT.PARENT_PARTCODE ");

		return this.jdbcTemplate.query(sql.toString(), parameter,
				new DepartmentRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Department> getChildrenOfCompany(String comCode) {
//		String sql = "SELECT * FROM parttable WHERE parent_partcode is null AND globalcom = ? AND isdeleted = '0'";
		String sql =  " SELECT PT.* ,  (COUNT(ET.PARTCODE) + COUNT(PT2.PARTCODE)) CNT"
			 +" FROM PARTTABLE  PT"
			 +" 	LEFT OUTER JOIN EMPTABLE ET ON PT.PARTCODE = ET.PARTCODE"
			 +" 		AND ET.ISDELETED = '0'"
			 +" 	LEFT OUTER JOIN PARTTABLE PT2 ON PT.PARTCODE = PT2.PARENT_PARTCODE"
			 +" 		AND PT2.ISDELETED = '0'"
			 +" WHERE  PT.ISDELETED = '0'"
			 +" AND PT.GLOBALCOM = ?"
			 +" AND PT.PARENT_PARTCODE IS NULL"
			 +" GROUP BY PT.GLOBALCOM, PT.PARTCODE, PT.PARTNAME, PT.ISDELETED, PT.DESCRIPTION, PT.PARENT_PARTCODE";
		
		return this.jdbcTemplate.query(sql, new Object[] { comCode },
				new DepartmentRowMapper());
	}

	class DepartmentRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			Department department = new Department();

			department.setCode(rs.getString("partcode"));
			department.setName(rs.getString("partname"));
			department.setParent(rs.getString("parent_partcode"));
			department.setGlobalcom(rs.getString("globalcom"));
			department.setDescription(rs.getString("description"));
			department.setCnt(rs.getString("cnt"));

			return department;
		}

	}
	
}
