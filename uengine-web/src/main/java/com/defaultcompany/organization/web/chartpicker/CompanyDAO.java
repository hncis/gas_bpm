package com.defaultcompany.organization.web.chartpicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

public class CompanyDAO extends DefaultDAO {

	public CompanyDAO(DataSource dataSource) {
		super(dataSource);
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompany() {
//		String sql = "SELECT comcode, comname, description FROM comtable WHERE isdeleted = '0'";
		String sql =  " SELECT CT.COMCODE, CT.COMNAME, CT.DESCRIPTION , COUNT(PT.GLOBALCOM ) CNT"
					 +" FROM COMTABLE  CT"
					 +" 	LEFT OUTER JOIN PARTTABLE PT ON PT.GLOBALCOM = CT.COMCODE"
					 +" 		AND PT.ISDELETED = '0'"
					 +" WHERE ISDELETED = '0'"
					 +" GROUP BY CT.COMCODE, CT.COMNAME, CT.DESCRIPTION"; 

		return this.jdbcTemplate.query(sql, new Object[] {},
				new CompanyRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompany(String comCode) {
//		String sql = "SELECT comcode, comname, description FROM comtable WHERE comcode = ?";
		String sql =  " SELECT CT.COMCODE, CT.COMNAME, CT.DESCRIPTION , COUNT(PT.GLOBALCOM ) CNT"
			 		 +" FROM COMTABLE  CT"
			 		 +" 	LEFT OUTER JOIN PARTTABLE PT ON PT.GLOBALCOM = CT.COMCODE"
			 		 +" 		AND PT.ISDELETED = '0'"
			 		 +" WHERE COMCODE = ?"
			 		 +" GROUP BY CT.COMCODE, CT.COMNAME, CT.DESCRIPTION"; 

		return this.jdbcTemplate.query(sql, new Object[] { comCode },
				new CompanyRowMapper());
	}

	class CompanyRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			Company company = new Company();

			company.setCode(rs.getString("comcode"));
			company.setName(rs.getString("comname"));
			company.setDescription(rs.getString("description"));
			company.setCnt(rs.getString("cnt"));

			return company;
		}

	}

}
