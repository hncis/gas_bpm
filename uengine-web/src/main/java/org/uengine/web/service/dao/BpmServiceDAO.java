package org.uengine.web.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.uengine.processmanager.ProcessManagerBean;
import org.uengine.processmanager.ProcessTransactionContext;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;
import org.uengine.web.common.dao.AbstractDAO;
import org.uengine.web.organization.vo.UserVO;
import org.uengine.web.worklist.vo.MyWorkVO;

@Repository("bpmServiceDAO")
public class BpmServiceDAO extends AbstractDAO {
	public List<MyWorkVO> selectMyWorkListByUserId(MyWorkVO vo) throws Exception {
		return (List<MyWorkVO>) selectList("service.selectMyWorkListByUserId", vo);
	}
	public List<MyWorkVO> selectMyInstanceListByUserId(MyWorkVO vo) throws Exception {
		return (List<MyWorkVO>) selectList("service.selectMyInstanceListByUserId", vo);
	}
	public List<MyWorkVO> selectMyWorkListCountByUserId(String userId) throws Exception {
		return (List<MyWorkVO>) selectList("service.selectMyWorkListCountByUserId", userId);
	}
	public List<MyWorkVO> selectMyInstanceListCountByUserId(String userId) throws Exception {
		return (List<MyWorkVO>) selectList("service.selectMyInstanceListCountByUserId", userId);
	}
	public UserVO selecUserVOByUserId(ProcessManagerBean processManagerBean, String userId) throws Exception {
		UserVO userVo = new UserVO();
				
		Connection conn = null;
		PreparedStatement psmt = null;
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		sql.append("SELECT EMP.EMPCODE AS USERID,				\n");
		sql.append("       EMP.EMPNAME AS USERNAME,				\n");
		sql.append("       EMP.ISADMIN AS ISADMIN,				\n");
		sql.append("       EMP.EMAIL AS EMAIL,				\n");
		sql.append("       EMP.PARTCODE AS PARTCODE,				\n");
		sql.append("       PART.PARTNAME AS PARTNAME,				\n");
		sql.append("       EMP.GLOBALCOM AS COMCODE,				\n");
		sql.append("       COM.COMNAME AS COMNAME,				\n");
		sql.append("       EMP.MOBILENO AS MOBILENO,				\n");
		sql.append("       EMP.LOCALE AS LOCALE,				\n");
		sql.append("       EMP.JIKNAME AS JIKNAME				\n");
		sql.append("FROM EMPTABLE EMP				\n");
		sql.append("  LEFT JOIN PARTTABLE PART ON EMP.PARTCODE = PART.PARTCODE				\n");
		sql.append("  LEFT JOIN COMTABLE COM ON EMP.GLOBALCOM = COM.COMCODE				\n");
		sql.append("WHERE EMP.empcode =?				\n");
		sql.append("AND   EMP.ISDELETED = 0				\n");
		try {
			conn = processManagerBean.getTransactionContext().getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				userVo.setEmpName(rs.getString("USERNAME"));
				userVo.setJikName(rs.getString("JIKNAME"));
				userVo.setPartName(rs.getString("PARTNAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // Resultset 객체 해제
			if(psmt != null) try{psmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제
			if(conn != null) try{conn.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return userVo;
	}
}
