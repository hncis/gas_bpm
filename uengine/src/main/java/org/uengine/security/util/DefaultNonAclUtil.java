package org.uengine.security.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.uengine.util.dao.DefaultConnectionFactory;

public class DefaultNonAclUtil extends DefaultAclUtil {
	
	public DefaultNonAclUtil() {
		super.setName("DefaultNonAclUtil");
	}
	
	@Override
	public void setPermission(int defId, String userType, String userCodes, String[] permissions) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		DAOFactory df = null;
//		
//		try {
//			DefaultConnectionFactory dcf = DefaultConnectionFactory.create();
//			df = DAOFactory.getInstance();
//			
//			String strSeq = df.getSequenceSql("ACLTABLE");
//			String seqColun = "";
//			if (UEngineUtil.isNotEmpty(strSeq)) {
//				strSeq = ", " + strSeq;
//				seqColun = ", acltableid";
//			}
//			
//			conn = dcf.getConnection();
//			StringBuffer sql = new StringBuffer();
//			sql.append(" INSERT INTO bpm_acltable (defid, ");
//			sql.append(userType);
////			sql.append(", permission) VALUES (").append(df.getSequenceSql("ACLTABLE")).append(", ?, ?, ?) ");
//			sql.append(", permission").append(seqColun).append(") VALUES (?, ?, ?").append(strSeq).append(") ");
//			
//			String[] userCodeList = userCodes.split(";");
//			stmt = conn.prepareStatement(sql.toString());
//			
//			for (String userCode : userCodeList) {
//				if (UEngineUtil.isNotEmpty(userCode)) {
//					for (int i = 0; i < permissions.length; i++) {
//						stmt.setInt(1, defId);
//						stmt.setString(2, userCode);
//						stmt.setString(3, permissions[i]);
//						
//						stmt.execute();
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
	}
	
	@Override
	public void setDefaultPermission(int defId, String[] permissionA, String[] permissionM) {
//		Connection conn	= null;
//		PreparedStatement stmt = null;
//		DAOFactory df = null;
//		StringBuffer sql = new StringBuffer();
//		
//		try {
//			DefaultConnectionFactory dcf = DefaultConnectionFactory.create();
//			df = DAOFactory.getInstance();
//			
//			conn = dcf.getConnection();
//			sql.append(" DELETE FROM bpm_acltable WHERE defid = ? AND defaultuser IS NOT NULL ");
//			stmt = conn.prepareStatement(sql.toString());
//			stmt.setInt(1, defId);
//			stmt.execute();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
//		
//
//		try {
//			DefaultConnectionFactory dcf = DefaultConnectionFactory.create();
//			df = DAOFactory.getInstance();
//			
//			String strSeq = df.getSequenceSql("ACLTABLE");
//			String seqColun = "";
//			if (UEngineUtil.isNotEmpty(strSeq)) {
//				strSeq = ", " + strSeq;
//				seqColun = ", acltableid";
//			}
//			sql.setLength(0);
//			sql.append(" INSERT INTO bpm_acltable (defid, defaultuser, permission").append(seqColun).append(") VALUES (?, ?, ?").append(strSeq).append(") ");
//			
//			conn = dcf.getConnection();
//			
//			if (permissionA != null) {
//				for (String s : permissionA) {
////					KeyGeneratorDAO kg = df.createKeyGenerator("ACLTABLE", new HashMap());
////					kg.select();
////					kg.next();
//					
//					stmt = conn.prepareStatement(sql.toString());
//					stmt.setInt(1, defId);
//					stmt.setString(2, PERMISSION_DEFAULT_ANONYMOUS + "");
//					stmt.setString(3, s);
////					stmt.setInt(4, kg.getKeyNumber().intValue());
//					
//					stmt.execute();
//				}
//			}
//			
//			if (permissionM != null) {
//				for (String s : permissionM) {
////					KeyGeneratorDAO kg = df.createKeyGenerator("ACLTABLE", new HashMap());
////					kg.select();
////					kg.next();
//					
//					stmt = conn.prepareStatement(sql.toString());
//					stmt.setInt(1, defId);
//					stmt.setString(2, PERMISSION_DEFAULT_MEMBERS + "");
//					stmt.setString(3, s);
////					stmt.setInt(4, kg.getKeyNumber().intValue());
//					
//					stmt.execute();
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
	}
	
	@Override
	public void removePermission(int iAclId) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		String sql = " DELETE FROM bpm_acltable WHERE acltableid=? ";
//		
//		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, iAclId);
//			
//			stmt.execute();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
	}
	
	@Override
	public void removePermissionForDefinistion(int iDefId) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		String sql = " DELETE FROM bpm_acltable WHERE defid = ? ";
//		
//		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, iDefId);
//			
//			stmt.execute();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
	}
	
	@Override
	public HashMap<Character, ArrayList<Character>> getDefaultPermission(int defId) {
		HashMap<Character, ArrayList<Character>> permission = new HashMap<Character, ArrayList<Character>>();
		
		Connection conn	= null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DefaultConnectionFactory.create().getConnection();
			sql = " SELECT defaultuser, permission FROM bpm_acltable WHERE defid = ? AND defaultuser IS NOT NULL ";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, defId);
			
			rs = stmt.executeQuery();
			
			ArrayList<Character> listA = new ArrayList<Character>();
			ArrayList<Character> listM = new ArrayList<Character>();
			while (rs.next()) {
				if (PERMISSION_DEFAULT_ANONYMOUS == rs.getString("defaultuser").charAt(0)) {
					listA.add(rs.getString("permission").charAt(0));
				} else {
					listM.add(rs.getString("permission").charAt(0));
				}
			}
			
			permission.put(PERMISSION_DEFAULT_ANONYMOUS, listA);
			permission.put(PERMISSION_DEFAULT_MEMBERS, listM);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e2) {}
			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
			if (conn != null) try { conn.close(); } catch (Exception e2) {}
		}
		
		return permission;
	}
	
	@Override
    public String getPermission(String empCode) {
        return null;
    }
	
	@Override
	public HashMap<Character, Boolean> getPermission(int defId, String empCode) {
		
		HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();
		
		permission.put(PERMISSION_VIEW, true);
		permission.put(PERMISSION_INITIATE, true);
		permission.put(PERMISSION_EDIT, true);
		permission.put(PERMISSION_MANAGEMENT, true);
		
		return permission;
	}
	
	@Override
    public HashMap<Character, Boolean> getPermission(int defId, String tracingTag, String empCode) {
	    HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();
        
        permission.put(PERMISSION_VIEW, true);
        permission.put(PERMISSION_INITIATE, true);
        permission.put(PERMISSION_EDIT, true);
        permission.put(PERMISSION_MANAGEMENT, true);
        
        return permission;
    }
    
    @Override
    public HashMap<Character, Boolean> getPermission(int defId, String empCode, String comCode, String parentfolder) {
        HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();
        
        permission.put(PERMISSION_VIEW, true);
        permission.put(PERMISSION_INITIATE, true);
        permission.put(PERMISSION_EDIT, true);
        permission.put(PERMISSION_MANAGEMENT, true);
        
        return permission;
    }
    
    @Override
    public HashMap<Character, Boolean> getPermission(int defId, String empCode, boolean dummy) {
        HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();
        
        permission.put(PERMISSION_VIEW, true);
        permission.put(PERMISSION_INITIATE, true);
        permission.put(PERMISSION_EDIT, true);
        permission.put(PERMISSION_MANAGEMENT, true);
        
        return permission;
    }
	
	@Override
	public boolean hasPermissionEdit(String empCode) {
//		boolean b = false;
//		Connection conn	= null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		StringBuffer sql = new StringBuffer();
//		
//		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			sql.append(" SELECT permission FROM bpm_acltable ")
//				.append(" WHERE ")
//				.append(" 	empcode = ? ")
//				.append(" 	OR defaultuser = '").append(PERMISSION_DEFAULT_MEMBERS).append("' ")
//				.append(" 	OR comcode IN (select globalcom from emptable where empcode = ?) ")
//				.append(" 	OR partcode IN (select partcode from emptable where empcode = ?) ")
//				.append(" 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) ")
//			.append("; ");
//			
//			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString(1, empCode);
//			stmt.setString(2, empCode);
//			stmt.setString(3, empCode);
//			stmt.setString(4, empCode);
//			
//			rs = stmt.executeQuery();
//			
//			while (rs.next()) {
//				char c = rs.getString("permission").charAt(0);
//				
//				if (c == PERMISSION_EDIT) {
//					b = true;
//					break;
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) try { rs.close(); } catch (Exception e2) {}
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
//		
//		return b;
		
		return true;
	}
	
	@Override
	public boolean isPermission(int defId, String empCode, String permission) {
//		boolean b = false;
//		Connection conn	= null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		
//		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			sql = " SELECT permission FROM bpm_acltable "
//				+ " WHERE "
//				+ " 	empcode = ? and (permission = ? or permission = " + PERMISSION_MANAGEMENT + " )"
//				+ " 	OR defaultuser = '" + PERMISSION_DEFAULT_MEMBERS + "' "
//				+ " 	OR comcode IN (select globalcom from emptable where empcode = ?) "
//				+ " 	OR partcode IN (select partcode from emptable where empcode = ?) "
//				+ " 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) "
//				+ " GROUP BY permission "
//			;
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, empCode);
//			stmt.setString(1, permission);
//			stmt.setString(2, empCode);
//			stmt.setString(3, empCode);
//			stmt.setString(4, empCode);
//			
//			rs = stmt.executeQuery();
//			
//			if (rs.next()) {
//				b = true;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) try { rs.close(); } catch (Exception e2) {}
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
//		
//		return b;
		
		return true;
	}
	
	@Override
	public char getTopPermission(String empCode) {
//		char permission = PERMISSION_NONE;
//		
//		Connection conn	= null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		
//		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			sql = " SELECT permission FROM bpm_acltable "
//				+ " WHERE "
//				+ " 	empcode = ? "
//				+ " 	OR defaultuser = '" + PERMISSION_DEFAULT_MEMBERS + "' "
//				+ " 	OR comcode IN (select globalcom from emptable where empcode = ?) "
//				+ " 	OR partcode IN (select partcode from emptable where empcode = ?) "
//				+ " 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) "
//			;
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, empCode);
//			stmt.setString(2, empCode);
//			stmt.setString(3, empCode);
//			stmt.setString(4, empCode);
//			
//			rs = stmt.executeQuery();
//			
//			while (rs.next()) {
//				char c = rs.getString("permission").charAt(0);
//				
//				if (permission == PERMISSION_NONE) {
//					permission = c;
//				} else if (permission == PERMISSION_VIEW) {
//					if (PERMISSION_NONE != c) {
//						permission = c;
//					}
//				} else if (permission == PERMISSION_INITIATE) {
//					if (PERMISSION_NONE != c && PERMISSION_VIEW != c) {
//						permission = c;
//					}
//				} else if (permission == PERMISSION_EDIT) {
//					if (PERMISSION_NONE != c && PERMISSION_VIEW != c && PERMISSION_INITIATE != c) {
//						permission = c;
//					}
//				} else if (permission == PERMISSION_MANAGEMENT) {
//					break;
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) try { rs.close(); } catch (Exception e2) {}
//			if (stmt != null) try { stmt.close(); } catch (Exception e2) {}
//			if (conn != null) try { conn.close(); } catch (Exception e2) {}
//		}
//		
//		return permission;
		
		return PERMISSION_MANAGEMENT;
	}

	@Override
	public String getGroupSqlString(String userId) throws Exception {
		return null;
	}

	@Override
	public String getUserSqlString(String userId) throws Exception {
		return null;
	}

	@Override
	public void setInherite(String defId) throws Exception {
		
	}

	@Override
	public void setPermission(int defId, String tracingTag, String userType, String userCode, String[] permissions) {
		
	}

}
