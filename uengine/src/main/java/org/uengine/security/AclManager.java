package org.uengine.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uengine.kernel.GlobalContext;
import org.uengine.persistence.dao.DAOFactory;
import org.uengine.persistence.dao.KeyGeneratorDAO;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectionFactory;

public class AclManager {

	public static final char PERMISSION_NONE = 'N';
	public static final char PERMISSION_VIEW = 'V';
	public static final char PERMISSION_INITIATE = 'I';
	public static final char PERMISSION_EDIT = 'E';
	public static final char PERMISSION_MANAGEMENT = 'M';

	public static final char PERMISSION_DEFAULT_MEMBERS = 'M';
	public static final char PERMISSION_DEFAULT_ANONYMOUS = 'A';

	public static final String ACL_FIELD_COM = "comcode";
	public static final String ACL_FIELD_PART = "partcode";
	public static final String ACL_FIELD_EMP = "empcode";
	public static final String ACL_FIELD_ROLE = "rolecode";
	public static final String ACL_FIELD_DEFAULT = "defaultuser";

	protected AclManager() {
	}

	private static String name = null;

	public static String getName() {
		return name;
	}

	public static void setName(String s) {
		name = s;
	}

	protected TransactionContext transactionContext;

	protected boolean isReleaseContext = false;

	public TransactionContext getTransactionContext() {
		return transactionContext;
	}

	public void setTransactionContext(TransactionContext transactionContext) {
		this.transactionContext = transactionContext;
	}

	public static Class USE_CLASS = null;
	
	public static AclManager getInstance() {
		return getInstance(null);
	}

	public static AclManager getInstance(TransactionContext tc) {
		if (USE_CLASS == null) {
			try {
				USE_CLASS = (Class<?>) Class.forName(GlobalContext.getPropertyString("aclmanager.class"));
			} catch (Exception e) {
				USE_CLASS = NoneAclManager.class;
			}
		}

		try {
			AclManager aclmanager = (AclManager) USE_CLASS.newInstance();

			if (tc == null) {
				tc = new SimpleTransactionContext();
				aclmanager.isReleaseContext = true;
			}
			aclmanager.setTransactionContext(tc);
			return aclmanager;

		} catch (Exception e) {
			return null;
		}
	}

	protected int getKeyNumber(ConnectionFactory dcf) throws Exception {
		// get sequence
		KeyGeneratorDAO kg = DAOFactory.getInstance(dcf).createKeyGenerator("ACLTABLE", null);
		kg.select();
		kg.next();
		return kg.getKeyNumber().intValue();
	}

	public void setPermission(int defId, String userType, String userCodes, String[] permissions) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" INSERT INTO bpm_acltable (defid, ");
			sql.append(userType);
			sql.append(", permission, acltableid) VALUES (?, ?, ?, ?)");

			String[] userCodeList = userCodes.split(";");
			stmt = conn.prepareStatement(sql.toString());

			for (String userCode : userCodeList) {
				if (UEngineUtil.isNotEmpty(userCode)) {
					for (int i = 0; i < permissions.length; i++) {
						stmt.setInt(1, defId);
						stmt.setString(2, userCode);
						stmt.setString(3, permissions[i]);
						stmt.setInt(4, getKeyNumber(dcf));

						stmt.execute();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			
			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}
	}

	public void setDefaultPermission(int defId, String[] permissionA, String[] permissionM) {
		Connection conn = null;
		PreparedStatement stmt = null;
		// DAOFactory df = null;
		StringBuffer sql = new StringBuffer();

		ConnectionFactory dcf = getTransactionContext();

		try {
			conn = dcf.getConnection();

			sql.append(" DELETE FROM bpm_acltable WHERE defid = ? AND defaultuser IS NOT NULL ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setInt(1, defId);
			stmt.execute();

			
			sql = new StringBuffer();
			sql.append(" INSERT INTO bpm_acltable (defid, defaultuser, permission, acltableid) VALUES (?, ?, ?, ?)");
			if (permissionA != null) {
				for (String s : permissionA) {
					stmt = conn.prepareStatement(sql.toString());
					stmt.setInt(1, defId);
					stmt.setString(2, PERMISSION_DEFAULT_ANONYMOUS + "");
					stmt.setString(3, s);
					stmt.setInt(4, getKeyNumber(dcf));

					stmt.execute();
				}
			}
			if (permissionM != null) {
				for (String s : permissionM) {
					stmt = conn.prepareStatement(sql.toString());
					stmt.setInt(1, defId);
					stmt.setString(2, PERMISSION_DEFAULT_MEMBERS + "");
					stmt.setString(3, s);
					stmt.setInt(4, getKeyNumber(dcf));

					stmt.execute();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}
	}

	public void removePermission(int iAclId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = " DELETE FROM bpm_acltable WHERE acltableid=? ";

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, iAclId);

			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}
	}

	public void removePermissionForDefinistion(int iDefId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = " DELETE FROM bpm_acltable WHERE defid = ? ";

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, iDefId);

			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}
	}

	public HashMap<Character, ArrayList<Character>> getDefaultPermission(int defId) {
		HashMap<Character, ArrayList<Character>> permission = new HashMap<Character, ArrayList<Character>>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			sql.append(" SELECT defaultuser, permission FROM bpm_acltable WHERE defid = ? AND defaultuser IS NOT NULL ");
			stmt = conn.prepareStatement(sql.toString());
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

			// parent check
			if (listA.size() == 0 && listM.size() == 0) {
				Map parentMap = getParentDefaultPermission(conn, defId);

				if (parentMap != null) {
					String defaultUser = (String) parentMap.get("DEFAULT");

					if (PERMISSION_DEFAULT_ANONYMOUS == defaultUser.charAt(0)) {
						listA = (ArrayList<Character>) parentMap.get("LIST");
					} else {
						listM = (ArrayList<Character>) parentMap.get("LIST");
					}
				}
			}

			permission.put(PERMISSION_DEFAULT_ANONYMOUS, listA);
			permission.put(PERMISSION_DEFAULT_MEMBERS, listM);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	private Map getParentDefaultPermission(Connection conn, int defId) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		Map resultMap = null;
		ArrayList<Character> resultList = new ArrayList<Character>();

		try {
			sql.append(" SELECT parentfolder FROM bpm_procdef WHERE defid = ? AND isdeleted = 0");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, defId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String parentFolder = rs.getString("parentfolder");

				int parentPermission = 0;
				String defaultUser = "";

				sql.setLength(0);
				sql.append(" SELECT  DEFAULTUSER                         ").append("        ,CASE WHEN PERMISSION = 'M' THEN '5' ").append("              WHEN PERMISSION = 'E' THEN '4' ").append("              WHEN PERMISSION = 'I' THEN '3' ")
						.append("              WHEN PERMISSION = 'V' THEN '2' ").append("              WHEN PERMISSION = 'N' THEN '1' ").append("         END AS PER                          ").append("   FROM  BPM_ACLTABLE                        ")
						.append("  WHERE  DEFID = ?                           ").append("    AND  DEFAULTUSER IS NOT NULL             ");

				pstmt.close();
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, Integer.parseInt(parentFolder));

				rs.close();
				rs = pstmt.executeQuery();

				if (rs.next()) {
					parentPermission = rs.getInt("PER");
					defaultUser = rs.getString("DEFAULTUSER");
				}

				List<Map<String, String>> parentFolderList = findParentFolder(conn, "", parentFolder);

				if (parentFolderList != null) {
					for (Map<String, String> parent : parentFolderList) {
						pstmt.setInt(1, Integer.parseInt(parent.get("defid")));

						rs.close();
						rs = pstmt.executeQuery();

						if (rs.next()) {
							if (parentPermission == 0 || parentPermission > rs.getInt("PER")) {
								parentPermission = rs.getInt("PER");
								defaultUser = rs.getString("DEFAULTUSER");
							}
						}
					}
				}

				if (parentPermission > 0) {
					resultList.add(getPermisionChar(parentPermission));

					resultMap = new HashMap();
					resultMap.put("DEFAULT", defaultUser);
					resultMap.put("LIST", resultList);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
		}

		return resultMap;
	}

	private char getPermisionChar(int permission) {
		char per;
		switch (permission) {
		case 5:
			per = 'M';
			break;
		case 4:
			per = 'E';
			break;
		case 3:
			per = 'I';
			break;
		case 2:
			per = 'V';
			break;
		case 1:
			per = 'N';
			break;
		default:
			per = 'N';
			break;
		}

		return per;
	}

	/*
	 * SELECT  MAX(A.RATING) AS PERMISSION
 		FROM (  SELECT  MAX(CASE WHEN ACL.PERMISSION = 'M' THEN '5'
			  WHEN ACL.PERMISSION = 'E' THEN '4'
			  WHEN ACL.PERMISSION = 'I' THEN '3'
			  WHEN ACL.PERMISSION = 'V' THEN '2'
			  WHEN ACL.PERMISSION = 'N' THEN '1'
			  ELSE '1' END) AS RATING,
			  CASE WHEN ACL.COMCODE IS NOT NULL THEN 'C'
			  WHEN ACL.ROLECODE IS NOT NULL THEN 'R'
			  WHEN ACL.PARTCODE IS NOT NULL THEN 'P'
			  WHEN ACL.EMPCODE IS NOT NULL  THEN 'E'
			  END AS KIND
	  FROM   BPM_ACLTABLE ACL
	  INNER JOIN BPM_PROCDEF DEF ON ACL.DEFID=DEF.DEFID AND DEF.ISDELETED=0
	  WHERE
	  ACL.EMPCODE = ?
	  OR  ACL.COMCODE IN (SELECT GLOBALCOM FROM EMPTABLE WHERE EMPCODE = ?)
	  OR  ACL.PARTCODE IN (SELECT PARTCODE FROM EMPTABLE WHERE EMPCODE = ?)
	  OR  ACL.ROLECODE IN (SELECT ROLECODE FROM ROLEUSERTABLE WHERE EMPCODE = ?)
	  GROUP  BY CASE WHEN ACL.COMCODE is not null THEN 'C'
	  WHEN ACL.ROLECODE is not null THEN 'R'
	  WHEN ACL.PARTCODE is not null THEN 'P'
	  WHEN ACL.EMPCODE is not null  THEN 'E' END
	  ) AS A
	 */
	public String getPermission(String empCode) {
		String permission = null;
		StringBuffer sql = new StringBuffer();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();

			sql.append("SELECT  MAX(A.RATING) AS PERMISSION   ").append("  FROM (                           ").append("        SELECT  MAX(CASE WHEN ACL.PERMISSION = 'M' THEN '5' ")
					.append("                         WHEN ACL.PERMISSION = 'E' THEN '4' ").append("                         WHEN ACL.PERMISSION = 'I' THEN '3' ").append("                         WHEN ACL.PERMISSION = 'V' THEN '2' ")
					.append("                         WHEN ACL.PERMISSION = 'N' THEN '1' ").append("                         ELSE '1'      ").append("                    END) AS RATING     ")
					.append("               ,CASE WHEN ACL.COMCODE IS NOT NULL THEN 'C'  ").append("                     WHEN ACL.ROLECODE IS NOT NULL THEN 'R' ").append("                     WHEN ACL.PARTCODE IS NOT NULL THEN 'P' ")
					.append("                     WHEN ACL.EMPCODE IS NOT NULL  THEN 'E' ").append("                END AS KIND            ").append("         FROM   BPM_ACLTABLE ACL   ")
					.append("              INNER JOIN BPM_PROCDEF DEF ON ACL.DEFID=DEF.DEFID AND DEF.ISDELETED=0   ").append("         WHERE                         ").append("                ACL.EMPCODE = ?        ")
					.append("            OR  ACL.COMCODE IN (SELECT GLOBALCOM FROM EMPTABLE WHERE EMPCODE = ?)     ").append("            OR  ACL.PARTCODE IN (SELECT PARTCODE FROM EMPTABLE WHERE EMPCODE = ?)     ")
					.append("            OR  ACL.ROLECODE IN (SELECT ROLECODE FROM ROLEUSERTABLE WHERE EMPCODE = ?) ").append("         GROUP  BY CASE WHEN ACL.COMCODE is not null THEN 'C'  ")
					.append("                        WHEN ACL.ROLECODE is not null THEN 'R'  ").append("                        WHEN ACL.PARTCODE is not null THEN 'P'  ").append("                        WHEN ACL.EMPCODE is not null  THEN 'E'  ")
					.append("                   END ").append("       ) AS A ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, empCode);
			pstmt.setString(3, empCode);
			pstmt.setString(4, empCode);
			rs = pstmt.executeQuery();

			if (rs.next())
				permission = rs.getString("PERMISSION");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	/*
	 * <pre> permission return </pre>
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.uengine.security.AclManager#getPermission(int, java.lang.String)
	 */
	public HashMap<Character, Boolean> getPermission(int defId, String empCode) {
		HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();

			sql.append("SELECT  ISADMIN     ").append("  FROM  EMPTABLE    ").append(" WHERE  EMPCODE = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, empCode);
			rs = stmt.executeQuery();

			rs.next();

			// manager
			if (rs.getString("isadmin").equals("1")) {
				permission.put(PERMISSION_VIEW, Boolean.TRUE);
				permission.put(PERMISSION_INITIATE, Boolean.TRUE);
				permission.put(PERMISSION_EDIT, Boolean.TRUE);
				permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
			} else {
				rs.close();
				stmt.close();
				sql = new StringBuffer();

				// level(company, department, employee...)
				sql.append(" SELECT  MAX(A.rating) AS per ").append("  FROM ( ").append("        SELECT  MAX(CASE WHEN permission = 'M' THEN '5'  ").append("                         WHEN permission = 'E' THEN '4'  ")
						.append("                         WHEN permission = 'I' THEN '3'  ").append("                         WHEN permission = 'V' THEN '2'  ").append("                         WHEN permission = 'N' THEN '1' ")
						.append("                    END) AS rating ").append("               ,CASE WHEN comcode is not null THEN 'C'  ").append("                     WHEN rolecode is not null THEN 'R'  ")
						.append("                     WHEN partcode is not null THEN 'P'  ").append("                     WHEN empcode is not null  THEN 'E'  ").append("                END AS kind  ").append("          FROM  bpm_acltable  ")
						.append("         WHERE  defid = ? AND ( ").append("                empcode = ? ").append("            OR  comcode IN (SELECT globalcom FROM emptable WHERE empcode = ?)  ")
						.append("            OR  partcode IN (SELECT partcode FROM emptable WHERE empcode = ?)  ").append("            OR  rolecode IN (SELECT rolecode FROM roleusertable WHERE empcode = ?)  ").append("               )  ")
						.append("         GROUP  BY CASE WHEN comcode is not null THEN 'C'  ").append("                        WHEN rolecode is not null THEN 'R'  ").append("                        WHEN partcode is not null THEN 'P'  ")
						.append("                        WHEN empcode is not null  THEN 'E'  ").append("                   END ").append("       ) AS A ");

				stmt = conn.prepareStatement(sql.toString());
				stmt.setInt(1, defId);
				stmt.setString(2, empCode);
				stmt.setString(3, empCode);
				stmt.setString(4, empCode);
				stmt.setString(5, empCode);

				rs = stmt.executeQuery();

				// System.out.println(sql.toString());

				if (rs.next()) {
					// high level setting
					int per = rs.getInt("per");

					if (per == 5) { // M
						permission.put(PERMISSION_VIEW, Boolean.TRUE);
						permission.put(PERMISSION_INITIATE, Boolean.TRUE);
						permission.put(PERMISSION_EDIT, Boolean.TRUE);
						permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
					} else if (per == 4) { // E
						permission.put(PERMISSION_VIEW, Boolean.TRUE);
						permission.put(PERMISSION_INITIATE, Boolean.TRUE);
						permission.put(PERMISSION_EDIT, Boolean.TRUE);
					} else if (per == 3) { // I
						permission.put(PERMISSION_VIEW, Boolean.TRUE);
						permission.put(PERMISSION_INITIATE, Boolean.TRUE);
					} else if (per == 2) { // V
						permission.put(PERMISSION_VIEW, Boolean.TRUE);
					} else if (per == 0) { // none
						// ArrayList<Character> listM = (ArrayList<Character>)
						// getDefaultPermission(defId).get(PERMISSION_DEFAULT_MEMBERS);
						//
						// if (listM != null ) {
						// for (char c : listM) {
						// permission.put(c, true);
						// }
						// }
					}
				} else { // none
					// default level setting
					// ArrayList<Character> listM = (ArrayList<Character>)
					// getDefaultPermission(defId).get(PERMISSION_DEFAULT_MEMBERS);
					//
					// if (listM != null ) {
					// for (char c : listM) {
					// permission.put(c, true);
					// }
					// }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	public boolean hasPermissionEdit(String empCode) {
		boolean b = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			sql.append(" SELECT permission FROM bpm_acltable ").append(" WHERE ").append(" 	empcode = ? ").append(" 	OR defaultuser = '").append(PERMISSION_DEFAULT_MEMBERS).append("' ")
					.append(" 	OR comcode IN (select globalcom from emptable where empcode = ?) ").append(" 	OR partcode IN (select partcode from emptable where empcode = ?) ")
					.append(" 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) ").append("; ");

			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, empCode);
			stmt.setString(2, empCode);
			stmt.setString(3, empCode);
			stmt.setString(4, empCode);

			rs = stmt.executeQuery();

			while (rs.next()) {
				char c = rs.getString("permission").charAt(0);

				if (c == PERMISSION_EDIT) {
					b = true;
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return b;
	}

	public boolean isPermission(int defId, String empCode, String permission) {
		boolean b = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			sql = " SELECT permission FROM bpm_acltable " + " WHERE " + " 	empcode = ? and (permission = ? or permission = " + PERMISSION_MANAGEMENT + " )" + " 	OR defaultuser = '" + PERMISSION_DEFAULT_MEMBERS + "' "
					+ " 	OR comcode IN (select globalcom from emptable where empcode = ?) " + " 	OR partcode IN (select partcode from emptable where empcode = ?) " + " 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) "
					+ " GROUP BY permission ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, empCode);
			stmt.setString(1, permission);
			stmt.setString(2, empCode);
			stmt.setString(3, empCode);
			stmt.setString(4, empCode);

			rs = stmt.executeQuery();

			if (rs.next()) {
				b = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return b;
	}

	public char getTopPermission(String empCode) {
		char permission = PERMISSION_NONE;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();
			sql = " SELECT permission FROM bpm_acltable " + " WHERE " + " 	empcode = ? " + " 	OR defaultuser = '" + PERMISSION_DEFAULT_MEMBERS + "' " + " 	OR comcode IN (select globalcom from emptable where empcode = ?) "
					+ " 	OR partcode IN (select partcode from emptable where empcode = ?) " + " 	OR rolecode IN (select rolecode from roleusertable where empcode = ?) ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, empCode);
			stmt.setString(2, empCode);
			stmt.setString(3, empCode);
			stmt.setString(4, empCode);

			rs = stmt.executeQuery();

			while (rs.next()) {
				char c = rs.getString("permission").charAt(0);

				if (permission == PERMISSION_NONE) {
					permission = c;
				} else if (permission == PERMISSION_VIEW) {
					if (PERMISSION_NONE != c) {
						permission = c;
					}
				} else if (permission == PERMISSION_INITIATE) {
					if (PERMISSION_NONE != c && PERMISSION_VIEW != c) {
						permission = c;
					}
				} else if (permission == PERMISSION_EDIT) {
					if (PERMISSION_NONE != c && PERMISSION_VIEW != c && PERMISSION_INITIATE != c) {
						permission = c;
					}
				} else if (permission == PERMISSION_MANAGEMENT) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	public String getGroupSqlString(String userId) throws Exception {
		return null;
	}

	public String getUserSqlString(String userId) throws Exception {
		return null;
	}

	public void setInherite(String defId) throws Exception {

	}

	public HashMap<Character, Boolean> getPermission(int defId, String tracingTag, String empCode) {
		return null;
	}

	public void setPermission(int defId, String tracingTag, String userType, String userCode, String[] permissions) {

	}

	/*
	 * <pre> find low process - high level return </pre>
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.uengine.security.AclManager#getPermission(int, java.lang.String,
	 * boolean)
	 */
	public HashMap<Character, Boolean> getPermission(int defId, String empCode, boolean dummy) {
		HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();

			sql.append("SELECT  ISADMIN     ").append("       ,GLOBALCOM   ").append("  FROM  EMPTABLE    ").append(" WHERE  EMPCODE = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, empCode);
			rs = stmt.executeQuery();

			rs.next();

			// manager
			if (rs.getString("isadmin").equals("1")) {
				permission.put(PERMISSION_VIEW, Boolean.TRUE);
				permission.put(PERMISSION_INITIATE, Boolean.TRUE);
				permission.put(PERMISSION_EDIT, Boolean.TRUE);
				permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
			} else {
				rs.close();
				stmt.close();

				// Level Search
				int permissionInt = getChildLevel(conn, defId, empCode);

				// Level Setting
				if (permissionInt == 5) { // M
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
					permission.put(PERMISSION_EDIT, Boolean.TRUE);
					permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
				} else if (permissionInt == 4) { // E
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
					permission.put(PERMISSION_EDIT, Boolean.TRUE);
				} else if (permissionInt == 3) { // I
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
				} else if (permissionInt == 2) { // V
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
				} else { // Default auth setting
					// ArrayList<Character> listM = (ArrayList<Character>)
					// getDefaultPermission(defId).get(PERMISSION_DEFAULT_MEMBERS);
					//
					// if (listM != null ) {
					// for (char c : listM) {
					// permission.put(c, Boolean.TRUE);
					// }
					// }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	/**
	 * <pre>
	 *  Child Process Level Search
	 * </pre>
	 * 
	 * @param conn
	 * @param defId
	 * @param empCode
	 * @return
	 */
	public int getChildLevel(Connection conn, int defId, String empCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		sql.append(" SELECT  CASE WHEN A.PERMISSION = 'M' THEN '5'                                        ").append("              WHEN A.PERMISSION = 'E' THEN '4'                                        ")
				.append("              WHEN A.PERMISSION = 'I' THEN '3'                                        ").append("              WHEN A.PERMISSION = 'V' THEN '2'                                        ")
				.append("              WHEN A.PERMISSION = 'N' THEN '1'                                        ").append("         END AS RATING                                                                ")
				.append("        ,D.OBJTYPE                                                                    ").append("        ,D.DEFID                                                                      ")
				.append("   FROM  BPM_PROCDEF D                                                                ").append("         INNER JOIN BPM_ACLTABLE A                                                    ")
				.append("             ON (D.DEFID = A.DEFID)                                                   ").append("  WHERE (D.DEFID = ? OR D.PARENTFOLDER = ?)                                           ")
				.append("    AND (                                                                             ").append("             A.EMPCODE = ?                                                            ")
				.append("         OR  A.COMCODE IN (SELECT GLOBALCOM FROM EMPTABLE WHERE EMPCODE = ?)          ").append("         OR  A.PARTCODE IN (SELECT PARTCODE FROM EMPTABLE WHERE EMPCODE = ?)          ")
				.append("         OR  A.ROLECODE IN (SELECT ROLECODE FROM ROLEUSERTABLE WHERE EMPCODE = ?)     ").append("        )                                                                             ");

		List<String> folderList = new ArrayList<String>();
		int level = 0;

		try {
			stmt = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			stmt.setInt(parameterIndex++, defId);
			stmt.setInt(parameterIndex++, defId);
			stmt.setString(parameterIndex++, empCode);
			stmt.setString(parameterIndex++, empCode);
			stmt.setString(parameterIndex++, empCode);
			stmt.setString(parameterIndex++, empCode);
			rs = stmt.executeQuery();

			// High Level Search
			while (rs.next()) {
				if (level == 0) {
					level = rs.getInt("RATING");
				} else if (level < rs.getInt("RATING")) {
					level = rs.getInt("RATING");
				}

				// E 레벨 이상을 못찾을 경우를 대비하여 폴더 Definition 격납
				if (rs.getString("OBJTYPE").equals("folder")) {
					folderList.add(rs.getString("DEFID"));
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}
		}

		// E 이하의 레벨이라면 하위 Definition을 조사하여 최고 레벨을 구한다.
		if (level < 4 && folderList != null && folderList.size() > 0) {
			for (String folder : folderList) {
				getChildLevel(conn, Integer.parseInt(folder), empCode);
			}
		}

		return level;
	}

	/**
	 * <pre>
	 * 	Permission low level return
	 * </pre>
	 * 
	 * @param defId
	 * @param empCode
	 * @param comCode
	 * @param parentfolder
	 * @return
	 */
	public HashMap<Character, Boolean> getPermission(int defId, String empCode, String comCode, String parentfolder) {
		HashMap<Character, Boolean> permission = new HashMap<Character, Boolean>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			ConnectionFactory dcf = getTransactionContext();

			conn = dcf.getConnection();

			sql.append("SELECT  ISADMIN     ").append("       ,GLOBALCOM   ").append("  FROM  EMPTABLE    ").append(" WHERE  EMPCODE = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, empCode);
			rs = stmt.executeQuery();

			rs.next();

			// manager
			if (rs.getString("isadmin").equals("1")) {
				permission.put(PERMISSION_VIEW, Boolean.TRUE);
				permission.put(PERMISSION_INITIATE, Boolean.TRUE);
				permission.put(PERMISSION_EDIT, Boolean.TRUE);
				permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
			} else {
				rs.close();
				stmt.close();
				sql = new StringBuffer();

				List<Map<String, String>> parentList = findParentFolder(conn, comCode, parentfolder);

				int permissionInt = 0;
				int parentPermission = 0;
				if (parentList != null) {
					for (Map<String, String> parent : parentList) {
						permissionInt = getPermissionSearch(conn, Integer.parseInt(parent.get("defid")), empCode);
						if (parentPermission == 0 || parentPermission > permissionInt) {
							parentPermission = permissionInt;
						}
					}
				}

				permissionInt = getPermissionSearch(conn, defId, empCode);

				if (parentList != null && parentPermission < permissionInt) {
					permissionInt = parentPermission;
				}

				if (permissionInt == 5) { // M
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
					permission.put(PERMISSION_EDIT, Boolean.TRUE);
					permission.put(PERMISSION_MANAGEMENT, Boolean.TRUE);
				} else if (permissionInt == 4) { // E
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
					permission.put(PERMISSION_EDIT, Boolean.TRUE);
				} else if (permissionInt == 3) { // I
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
					permission.put(PERMISSION_INITIATE, Boolean.TRUE);
				} else if (permissionInt == 2) { // V
					permission.put(PERMISSION_VIEW, Boolean.TRUE);
				} else { // none
					// ArrayList<Character> listM = (ArrayList<Character>)
					// getDefaultPermission(defId).get(PERMISSION_DEFAULT_MEMBERS);
					//
					// if (listM != null ) {
					// for (char c : listM) {
					// permission.put(c, Boolean.TRUE);
					// }
					// }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}

			if (isReleaseContext) {
				try {
					transactionContext.releaseResources();
				} catch (Exception e) {
				}
			}
		}

		return permission;
	}

	/**
	 * <pre>
	 * 	Permission search
	 * </pre>
	 * 
	 * @param conn
	 * @param defId
	 * @param empCode
	 * @return
	 * @throws Exception
	 */
	private int getPermissionSearch(Connection conn, int defId, String empCode) throws Exception {
		int intPer = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		try {
			sql.append(" SELECT  MAX(A.rating) AS per ").append("  FROM ( ").append("        SELECT  MAX(CASE WHEN permission = 'M' THEN '5'  ").append("                         WHEN permission = 'E' THEN '4'  ")
					.append("                         WHEN permission = 'I' THEN '3'  ").append("                         WHEN permission = 'V' THEN '2'  ").append("                         WHEN permission = 'N' THEN '1' ")
					.append("                    END) AS rating ").append("               ,CASE WHEN comcode is not null THEN 'C'  ").append("                     WHEN rolecode is not null THEN 'R'  ")
					.append("                     WHEN partcode is not null THEN 'P'  ").append("                     WHEN empcode is not null  THEN 'E'  ").append("                END AS kind  ").append("          FROM  bpm_acltable  ")
					.append("         WHERE  defid = ? ").append("           AND ( ").append("                empcode = ? ").append("            OR  comcode IN (SELECT globalcom FROM emptable WHERE empcode = ?)  ")
					.append("            OR  partcode IN (SELECT partcode FROM emptable WHERE empcode = ?)  ").append("            OR  rolecode IN (SELECT rolecode FROM roleusertable WHERE empcode = ?)  ").append("               )  ")
					.append("         GROUP  BY CASE WHEN comcode is not null THEN 'C'  ").append("                        WHEN rolecode is not null THEN 'R'  ").append("                        WHEN partcode is not null THEN 'P'  ")
					.append("                        WHEN empcode is not null  THEN 'E'  ").append("                   END ").append("       ) AS A ");

			stmt = conn.prepareStatement(sql.toString());
			stmt.setInt(1, defId);
			stmt.setString(2, empCode);
			stmt.setString(3, empCode);
			stmt.setString(4, empCode);
			stmt.setString(5, empCode);

			rs = stmt.executeQuery();

			if (rs.next()) {
				intPer = rs.getInt("per");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}
		}

		return intPer;
	}

	/**
	 * <pre>
	 *  find parent folder search
	 * </pre>
	 * 
	 * @param conn
	 * @param comCode
	 * @param parentefolder
	 *            high level definition id
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, String>> findParentFolder(Connection conn, final String comCode, final String parentefolder) throws Exception {
		StringBuffer sql = new StringBuffer();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		sql.append("SELECT  defid             ").append("       ,parentfolder      ").append("       ,name              ").append("       ,objtype           ").append("       ,prodver           ").append("  FROM  bpm_procdef       ")
				.append(" WHERE  isdeleted = 0     ").append("   AND  objtype = ?       ");

		if (UEngineUtil.isNotEmpty(comCode)) {
			sql.append("   AND  comcode = ?       ");
		}

		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();

		try {
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, "folder");
			if (UEngineUtil.isNotEmpty(comCode)) {
				stmt.setString(2, comCode);
			}
			rs = stmt.executeQuery();

			Map<String, String> rsMap = new HashMap<String, String>();
			while (rs.next()) {
				rsMap.put("defId", rs.getString("defid"));
				rsMap.put("parentfolder", rs.getString("parentfolder"));

				rsList.add(rsMap);
			}

			if (rsList.size() > 0 && UEngineUtil.isNotEmpty(parentefolder)) {
				FindParent.find(rsList, parentefolder);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
				}
		}

		return FindParent.getResultItemList();
	}

	/**
	 * <pre>
	 * 	parent process search
	 * </pre>
	 * 
	 * @author
	 * 
	 */
	static class FindParent {

		@SuppressWarnings("unused")
		private static void init() {
			_RESULT_ITEM_LIST = new ArrayList<Map<String, String>>();
			_LEVEL = 1;
		}

		private static int _LEVEL = 1;
		private static List<Map<String, String>> _RESULT_ITEM_LIST = null;

		public static List<Map<String, String>> getResultItemList() {
			return _RESULT_ITEM_LIST;
		}

		public static void find(List<Map<String, String>> folderItem, String defId) {
			String parent = "";
			Map<String, String> item = null;

			for (int i = 0; i < folderItem.size(); i++) {
				item = folderItem.get(i);
				if (item.get("defId").equals(defId)) {
					parent = (String) item.get("parentfolder");

					if (getResultItemList() == null || getResultItemList().size() <= 0) {
						_RESULT_ITEM_LIST = new ArrayList<Map<String, String>>();
						item.put("LEVEL", String.valueOf(_LEVEL++));
						_RESULT_ITEM_LIST.add(item);
					}
					break;
				}
			}

			for (int i = 0; i < folderItem.size(); i++) {
				item = folderItem.get(i);
				if (item.get("defId").equals(parent)) {
					item.put("LEVEL", String.valueOf(_LEVEL++));
					_RESULT_ITEM_LIST.add(item);

					if (item.get("parentfolder") != null && !item.get("parentfolder").equals("")) {
						find(folderItem, item.get("defId"));
					}
					break;
				}
			}
		}
	}

}