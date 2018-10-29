package com.defaultcompany.external.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uengine.processmanager.TransactionContext;

import com.defaultcompany.external.model.Approver;


public class ApproverDAO {
	
	private TransactionContext tc;
	
	public ApproverDAO(TransactionContext tc) {
		this.tc = tc;
	}
	
	public Approver getByInstanceIdAndTracingTag(String approvalKey, int instanceId, String tracingTag) throws Exception {
		StringBuffer sql = new StringBuffer();
//		유저 정보 없는 쿼리
//		sql.append(" SELECT ");
//		sql.append("    REG.INSTID, INFO.APPR_KEY, INFO.APPR_VERSION, INFO.APPR_EMPCODE, INFO.APPR_TRUTH_EMPCODE, ");
//		sql.append("    INFO.APPR_TYPE, INFO.APPR_COMMENT, INFO.APPR_STATUS, INFO.APPR_ENDDATE, ");
//		sql.append("    INFO.TASKID, INFO.TRCTAG, INFO.APPR_ORDERBY "); 
//		sql.append(" FROM BPM_APPRREG REG LEFT JOIN BPM_APPRINFO INFO ON REG.APPR_KEY=INFO.APPR_KEY ");
//		sql.append(" WHERE REG.INSTID = ? AND INFO.TRCTAG = ? ");
//		sql.append(" AND INFO.APPR_VERSION = (select MAX(APPR_VERSION) from BPM_APPRINFO where APPR_KEY = ?) ");
		
		sql.append("SELECT "); 
		sql.append("B.* "); 
		sql.append("FROM "); 
		sql.append("( "); 
		sql.append("   SELECT "); 
		sql.append("   A.*, "); 
		sql.append("   EMP.EMPNAME AS APPR_TRUTH_EMPNAME, "); 
		sql.append("   EMP.JIKNAME AS APPR_TRUTH_JIKNAME, "); 
		sql.append("   EMP.PARTCODE AS APPR_TRUTH_PARTCODE, "); 
		sql.append("   PART.PARTNAME AS APPR_TRUTH_PARTNAME "); 
		sql.append("   FROM "); 
		sql.append("   ( "); 
		sql.append("      SELECT "); 
		sql.append("      REG.INSTID, "); 
		sql.append("      INFO.APPR_KEY, "); 
		sql.append("      INFO.APPR_VERSION, "); 
		sql.append("      INFO.APPR_TYPE, "); 
		sql.append("      INFO.APPR_COMMENT, "); 
		sql.append("      INFO.APPR_STATUS, "); 
		sql.append("      INFO.APPR_ENDDATE, "); 
		sql.append("      INFO.TASKID, "); 
		sql.append("      INFO.TRCTAG, "); 
		sql.append("      INFO.APPR_ORDERBY, "); 
		sql.append("      INFO.APPR_EMPCODE, "); 
		sql.append("      EMP.EMPNAME AS APPR_EMPNAME, "); 
		sql.append("      EMP.JIKNAME AS APPR_JIKNAME, "); 
		sql.append("      EMP.PARTCODE AS APPR_PARTCODE, "); 
		sql.append("      PART.PARTNAME AS APPR_PARTNAME, "); 
		sql.append("      INFO.APPR_TRUTH_EMPCODE "); 
		sql.append("      FROM BPM_APPRREG REG LEFT JOIN BPM_APPRINFO INFO ON REG.APPR_KEY=INFO.APPR_KEY "); 
		sql.append("      LEFT JOIN EMPTABLE EMP ON EMP.EMPCODE = INFO.APPR_EMPCODE "); 
		sql.append("      LEFT JOIN PARTTABLE PART ON PART.PARTCODE = EMP.PARTCODE "); 
		sql.append("      WHERE REG.INSTID = ? AND INFO.TRCTAG = ? "); 
		sql.append("      AND INFO.APPR_VERSION = "); 
		sql.append("      ( "); 
		sql.append("         select "); 
		sql.append("         MAX(APPR_VERSION) "); 
		sql.append("         from BPM_APPRINFO "); 
		sql.append("         where APPR_KEY = ? "); 
		sql.append("      ) "); 
		sql.append("   ) "); 
		sql.append("   A "); 
		sql.append("   LEFT JOIN EMPTABLE EMP ON EMP.EMPCODE = A.APPR_TRUTH_EMPCODE "); 
		sql.append("   LEFT JOIN PARTTABLE PART ON PART.PARTCODE = EMP.PARTCODE "); 
		sql.append(") "); 
		sql.append("B ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			pstmt.setInt(1, instanceId);
			pstmt.setString(2, tracingTag);
			pstmt.setString(3, approvalKey);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Approver approver = new Approver();
				approver.setApprovalKey(rs.getString("APPR_KEY"));
				approver.setVersion(rs.getInt("APPR_VERSION"));
				approver.setComment(rs.getString("APPR_COMMENT"));
				approver.setEndDate(rs.getTimestamp("APPR_ENDDATE"));
				approver.setType(rs.getString("APPR_TYPE"));
				approver.setStatus(rs.getString("APPR_STATUS"));
				approver.setTaskId(rs.getString("TASKID"));
				approver.setTracingTag(rs.getString("TRCTAG"));
				approver.setOrder(rs.getInt("APPR_ORDERBY"));
				
				approver.setEmpCode(rs.getString("APPR_EMPCODE"));
				approver.setTruthEmpCode(rs.getString("APPR_TRUTH_EMPCODE"));
				
				return approver;
			}
			
		} catch (Exception e) {
			System.err.println("결재선 정보 가져오기 실패");
			System.err.println("instanceId:" + instanceId + "\ttracingTag:" + tracingTag);
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) { }
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
		return null;
	}
	
	public void add(List<Approver> approverList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BPM_APPRINFO ");
		sql.append(" 		(APPR_KEY, APPR_VERSION, APPR_EMPCODE, APPR_TRUTH_EMPCODE, APPR_TYPE, APPR_COMMENT, APPR_STATUS, APPR_ENDDATE, TASKID, TRCTAG, APPR_ORDERBY) ");
		sql.append(" VALUES (?       , ?           , ?           , ?                 , ?        , ?           , ?          , ?           , ?     , ?     , ?           ) ");
		
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			for (int i = 0; i < approverList.size(); i++) {
				Approver approver = approverList.get(i);
				int parameterIndex = 1;
				pstmt.setString(parameterIndex++, approver.getApprovalKey());
				pstmt.setInt(parameterIndex++, approver.getVersion());
				pstmt.setString(parameterIndex++, approver.getEmpCode());
				pstmt.setString(parameterIndex++, approver.getTruthEmpCode());
				pstmt.setString(parameterIndex++, approver.getType());
				pstmt.setString(parameterIndex++, approver.getComment());
				pstmt.setString(parameterIndex++, approver.getStatus());
				pstmt.setTimestamp(parameterIndex++, approver.getEndDate());
				pstmt.setString(parameterIndex++, approver.getTaskId());
				pstmt.setString(parameterIndex++, approver.getTracingTag());
				pstmt.setInt(parameterIndex++, approver.getOrder());
				pstmt.addBatch();
				pstmt.clearParameters();
			}
			pstmt.executeBatch();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
	}
	
	public void update(Approver... approver) throws Exception {
		this.update(Arrays.asList(approver));
	}
	
	public void update(final List<Approver> approverList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE BPM_APPRINFO ");
		sql.append(" SET TASKID = ?, APPR_TRUTH_EMPCODE = ?, APPR_STATUS = ?, APPR_ENDDATE = ?, TRCTAG = ?, APPR_COMMENT =? ");
		sql.append(" WHERE APPR_KEY = ? AND APPR_ORDERBY = ? AND APPR_VERSION = ? ");
		
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			for (int i = 0; i < approverList.size(); i++) {
				Approver approver = approverList.get(i);
				int parameterIndex = 1;
				pstmt.setString(parameterIndex++, approver.getTaskId());
				pstmt.setString(parameterIndex++, approver.getTruthEmpCode());
				pstmt.setString(parameterIndex++, approver.getStatus());
				pstmt.setTimestamp(parameterIndex++, approver.getEndDate());
				pstmt.setString(parameterIndex++, approver.getTracingTag());
				pstmt.setString(parameterIndex++, approver.getComment());
				
				pstmt.setString(parameterIndex++, approver.getApprovalKey());
				pstmt.setInt(parameterIndex++, approver.getOrder());
				pstmt.setInt(parameterIndex++, approver.getVersion());
				
				pstmt.addBatch();
				pstmt.clearParameters();
			}
			pstmt.executeBatch();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
	}
	
	public List<Approver> getList(String approvalLineKey) throws Exception {
		StringBuffer sql = new StringBuffer();
//		유저 정보 없는 쿼리
//		sql.append(" SELECT ");
//		sql.append(" 	APPR_KEY, APPR_VERSION, APPR_EMPCODE, APPR_TRUTH_EMPCODE, APPR_TYPE, APPR_COMMENT, APPR_STATUS, APPR_ENDDATE, TASKID, TRCTAG, APPR_ORDERBY "); 
//		sql.append(" FROM BPM_APPRINFO ");
//		sql.append(" WHERE APPR_KEY = ? ");
//		sql.append(" AND APPR_VERSION = (select MAX(APPR_VERSION) from BPM_APPRINFO where APPR_KEY = ?) ");
//		sql.append(" ORDER BY APPR_ORDERBY ASC "); 
		
		sql.append("SELECT "); 
		sql.append("B.* "); 
		sql.append("FROM "); 
		sql.append("( "); 
		sql.append("   SELECT "); 
		sql.append("   A.*, "); 
		sql.append("   EMP.EMPNAME AS APPR_TRUTH_EMPNAME, "); 
		sql.append("   EMP.JIKNAME AS APPR_TRUTH_JIKNAME, "); 
		sql.append("   EMP.PARTCODE AS APPR_TRUTH_PARTCODE, "); 
		sql.append("   PART.PARTNAME AS APPR_TRUTH_PARTNAME "); 
		sql.append("   FROM "); 
		sql.append("   ( "); 
		sql.append("      SELECT "); 
		sql.append("      INFO.APPR_KEY, "); 
		sql.append("      INFO.APPR_VERSION, "); 
		sql.append("      INFO.APPR_TYPE, "); 
		sql.append("      INFO.APPR_COMMENT, "); 
		sql.append("      INFO.APPR_STATUS, "); 
		sql.append("      INFO.APPR_ENDDATE, "); 
		sql.append("      INFO.TASKID, "); 
		sql.append("      INFO.TRCTAG, "); 
		sql.append("      INFO.APPR_ORDERBY, "); 
		sql.append("      INFO.APPR_EMPCODE, "); 
		sql.append("      EMP.EMPNAME AS APPR_EMPNAME, "); 
		sql.append("      EMP.JIKNAME AS APPR_JIKNAME, "); 
		sql.append("      EMP.PARTCODE AS APPR_PARTCODE, "); 
		sql.append("      PART.PARTNAME AS APPR_PARTNAME, "); 
		sql.append("      INFO.APPR_TRUTH_EMPCODE "); 
		sql.append("      FROM BPM_APPRINFO INFO "); 
		sql.append("      LEFT JOIN EMPTABLE EMP ON EMP.EMPCODE = INFO.APPR_EMPCODE "); 
		sql.append("      LEFT JOIN PARTTABLE PART ON PART.PARTCODE = EMP.PARTCODE "); 
		sql.append("      WHERE INFO.APPR_KEY = ? "); 
		sql.append("      AND INFO.APPR_VERSION = "); 
		sql.append("      ( "); 
		sql.append("         select "); 
		sql.append("         MAX(APPR_VERSION) "); 
		sql.append("         from BPM_APPRINFO "); 
		sql.append("         where APPR_KEY = ? "); 
		sql.append("      ) "); 
		sql.append("   ) "); 
		sql.append("   A "); 
		sql.append("   LEFT JOIN EMPTABLE EMP ON EMP.EMPCODE = A.APPR_TRUTH_EMPCODE "); 
		sql.append("   LEFT JOIN PARTTABLE PART ON PART.PARTCODE = EMP.PARTCODE "); 
		sql.append(") "); 
		sql.append("B "); 
		sql.append("ORDER BY B.APPR_ORDERBY ASC ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			pstmt.setString(1, approvalLineKey);
			pstmt.setString(2, approvalLineKey);
			rs = pstmt.executeQuery();
			
			List<Approver> approverList = new ArrayList<Approver>();
			
			while (rs.next()) {
				Approver approver = new Approver();
				approver.setApprovalKey(rs.getString("APPR_KEY"));
				approver.setVersion(rs.getInt("APPR_VERSION"));
				approver.setComment(rs.getString("APPR_COMMENT"));
				approver.setEndDate(rs.getTimestamp("APPR_ENDDATE"));
				approver.setType(rs.getString("APPR_TYPE"));
				approver.setStatus(rs.getString("APPR_STATUS"));
				approver.setTaskId(rs.getString("TASKID"));
				approver.setTracingTag(rs.getString("TRCTAG"));
				approver.setOrder(rs.getInt("APPR_ORDERBY"));
				
				approver.setEmpCode(rs.getString("APPR_EMPCODE"));
				approver.setTruthEmpCode(rs.getString("APPR_TRUTH_EMPCODE"));
				
				approverList.add(approver);
			}
			
			return approverList;
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) { }
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
	}
	
}
