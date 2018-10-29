package com.defaultcompany.external.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.uengine.processmanager.TransactionContext;

import com.defaultcompany.external.model.ApprovalLine;



public class ApprovalLineDAO {
	
	private TransactionContext tc;
	
	public ApprovalLineDAO(TransactionContext tc) {
		this.tc = tc;
	}
	
	public ApprovalLine get(String approvalKey) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("    APPR_KEY, INSTID, VERSION, LINE_STATUS ");
		sql.append(" FROM BPM_APPRREG ");
		sql.append(" WHERE APPR_KEY = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			pstmt.setString(1, approvalKey);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				ApprovalLine al = new ApprovalLine();
				al.setApprovalKey(rs.getString("APPR_KEY"));
				al.setInstanceId(rs.getInt("INSTID"));
				al.setVersion(rs.getInt("VERSION"));
				al.setLineStatus(rs.getString("LINE_STATUS"));
				return al;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) { }
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
		return null;
		
	}
	
	public void update(ApprovalLine al) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE BPM_APPRREG ");
		sql.append(" SET INSTID = ?, VERSION = ?, LINE_STATUS = ? WHERE APPR_KEY = ? ");
		
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = tc.getConnection().prepareStatement(sql.toString());
			pstmt.setInt(1, al.getInstanceId());
			pstmt.setInt(2, al.getVersion());
			pstmt.setString(3, al.getLineStatus());
			pstmt.setString(4, al.getApprovalKey());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		}
		
	}
	
}
