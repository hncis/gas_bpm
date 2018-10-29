package org.uengine.ui.tree.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.processmanager.TransactionContext;
import org.uengine.security.AclManager;
import org.uengine.ui.tree.model.Form;
import org.uengine.ui.tree.model.Item;

public class ProcessDefinitionListDAO {
	
	protected JdbcTemplate jdbcTemplate = null;
	private TransactionContext transactionContext;
	
	public ProcessDefinitionListDAO(SimpleTransactionContext tc) {
		this.transactionContext = tc;
		this.jdbcTemplate = new JdbcTemplate(tc.getDataSource());
	}
	
	// Only Folder
	@SuppressWarnings("unchecked")
	public List<Item> findAllFolder(final String endpoint) {
		
		String sql = new StringBuffer(
		"SELECT defid, parentfolder, name, objtype, prodver FROM bpm_procdef ").append(
		"WHERE (isdeleted='0') AND (objtype = 'folder') ").toString();
		
		final AclManager aclManager = AclManager.getInstance(transactionContext);
		
		return this.jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				
				Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint);
				
				if (permission.containsKey(AclManager.PERMISSION_VIEW) ||
						permission.containsKey(AclManager.PERMISSION_INITIATE) ) {
					String parentFolder = rs.getString("parentfolder");
					String name = rs.getString("name");
					String objtype = rs.getString("objtype");
					
					item = new Item();
					item.setId(String.valueOf(defid));
					item.setName(name);
					item.setParent(parentFolder);
					item.setObj(objtype);
				}
				return item;
			}
		});
	}
	
	// ForParticipant
	
	@SuppressWarnings("unchecked")
	public List<Item> findAllProcessDefinitionsForParticipant(final String endpoint) {
		
		String sql = new StringBuffer(
		"SELECT defid, parentfolder, name, objtype, prodver, prodverid FROM bpm_procdef ").append(
		"WHERE (isdeleted='0') AND ((objtype = 'process' AND prodver <> 0) OR (objtype = 'folder') )").toString();
		
		final AclManager aclManager = AclManager.getInstance(transactionContext);
		
		return this.jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				
				Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint);
				
				if (permission.containsKey(AclManager.PERMISSION_VIEW) ||
						permission.containsKey(AclManager.PERMISSION_INITIATE) ) {
					String parentFolder = rs.getString("parentfolder");
					String name = rs.getString("name");
					String objtype = rs.getString("objtype");
					
					item = new Item();
					item.setId(String.valueOf(defid));
					item.setName(name);
					item.setParent(parentFolder);
					item.setObj(objtype);
					
					String prodVerId = String.valueOf(rs.getInt("prodVerId"));
					item.setProdVerId(prodVerId);
				}
				return item;
			}
		});
	}
	
	// Process Definition
	
	@SuppressWarnings("unchecked")
	public List<Item> findAllProcessDefinitions(final String endpoint) {
		
		String sql = "SELECT defid, parentfolder, name, objtype, prodver FROM bpm_procdef WHERE isdeleted='0'";
		
		return this.jdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				String name = rs.getString("name");
				String objtype = rs.getString("objtype");
				String parentFolder = rs.getString("parentfolder");
				
				item = new Item();
				item.setId(String.valueOf(defid));
				item.setName(name);
				item.setParent(parentFolder);
				item.setObj(objtype);
				
				return item;
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findAllAuthorityProcessDefinitions(final String endpoint, final String comCode, final char cPermission) {
		
		String sql = "SELECT defid, parentfolder, name, objtype, prodver FROM bpm_procdef WHERE isdeleted='0' AND comcode = '" + comCode + "'";
		
		final AclManager aclManager = AclManager.getInstance(transactionContext);
		
		return this.jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint, true);
				
				if (permission.containsKey(AclManager.PERMISSION_MANAGEMENT) || permission.containsKey(cPermission)) {
					String name = rs.getString("name");
					String objtype = rs.getString("objtype");
					String parentFolder = rs.getString("parentfolder");
					
					item = new Item();
					item.setId(String.valueOf(defid));
					item.setName(name);
					item.setParent(parentFolder);
					item.setObj(objtype);

					Character[] permissions = null;
					if (permission.size() > 0)
						permissions = (Character[]) permission.keySet().toArray(new Character[permission.size()]);
					item.setAuthority(permissions);
				}
				return item;
			}
		});
	}
	
	/**
	 * <pre>
	 *  Definition List type = folder Serch
	 * </pre>
	 * 
	 * @param endpoint
	 * @param comCode
	 * @param cPermission
	 * @return  
	 */
	@SuppressWarnings("unchecked")
	public List<Item> findFolderAuthorityProcessDefinitions(final String endpoint, final String comCode, final char cPermission) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT  a.defid             ").append("\n")
		   .append("       ,a.parentfolder      ").append("\n")
		   .append("       ,a.name              ").append("\n")
		   .append("       ,(select count(*) from bpm_procdef b where b.parentfolder = a.defid and b.objtype in ('folder', 'process')) as cnt").append("\n")
		   .append("       ,a.objtype           ").append("\n")
		   .append("       ,a.prodver           ").append("\n")
		   .append("  FROM  bpm_procdef a       ").append("\n")
		   .append(" WHERE  a.isdeleted = 0     ").append("\n")
		   .append("   AND  a.objtype = 'folder'").append("\n")
		   .append("   AND  a.comcode = '").append(comCode).append("'").append("\n");
		
		
		final AclManager aclManager = AclManager.getInstance(transactionContext);
		
		return this.jdbcTemplate.query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				//Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint);
				Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint, comCode, rs.getString("parentfolder"));
				
				int cnt = rs.getInt("cnt");
				
				if ((permission.containsKey(AclManager.PERMISSION_MANAGEMENT) || permission.containsKey(cPermission)) && cnt != 0) {
					String name = rs.getString("name");
					String objtype = rs.getString("objtype");
					String parentFolder = rs.getString("parentfolder");
					
					item = new Item();
					item.setId(String.valueOf(defid));
					item.setName(name);
					item.setParent(parentFolder);
					item.setObj(objtype);

					Character[] permissions = null;
					if (permission.size() > 0)
						permissions = (Character[]) permission.keySet().toArray(new Character[permission.size()]);
					item.setAuthority(permissions);
				}
				return item;
			}
		});
	}
	
	/**
     * <pre>
     *  Definition List type in ('folder', 'process') Serch
     * </pre>
     * 
     * @param endpoint
     * @param comCode
     * @param cPermission
     * @parma objtype Object Type ex) 'folder' or 'folder', 'process'
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Item> findAuthorityProcessDefinitions(final String endpoint, final String comCode, final char cPermission, final String[] objTypeList) {
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT  defid             ").append("\n")
           .append("       ,parentfolder      ").append("\n")
           .append("       ,name              ").append("\n")
           .append("       ,objtype           ").append("\n")
           .append("       ,prodver           ").append("\n")
           .append("  FROM  bpm_procdef       ").append("\n")
           .append(" WHERE  isdeleted = 0     ").append("\n")
           .append("   AND  objtype in (");
        
        for (String objType : objTypeList) {
            sql.append("'").append(objType).append("',");
        }
        
        sql.setLength(sql.length()-1);
        
        sql.append(")")
           .append("   AND  comcode = '").append(comCode).append("'").append("\n");
        
        
        final AclManager aclManager = AclManager.getInstance(transactionContext);
        
        return this.jdbcTemplate.query(sql.toString(), new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = null;
                
                int defid = rs.getInt("defid");
                Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint, true);
                
                if (permission.containsKey(AclManager.PERMISSION_MANAGEMENT) || permission.containsKey(cPermission)) {
                    String name = rs.getString("name");
                    String objtype = rs.getString("objtype");
                    String parentFolder = rs.getString("parentfolder");
                    
                    item = new Item();
                    item.setId(String.valueOf(defid));
                    item.setName(name);
                    item.setParent(parentFolder);
                    item.setObj(objtype);

                    Character[] permissions = null;
                    if (permission.size() > 0)
                        permissions = (Character[]) permission.keySet().toArray(new Character[permission.size()]);
                    item.setAuthority(permissions);
                }
                return item;
            }
        });
    }
	
	// Form Definition
	
	public int findFormProductionVersionId(int defId) {
		String sql = "SELECT prodverid FROM bpm_procdef WHERE defid=?";
		return this.jdbcTemplate.queryForInt(sql, new Object[] { defId });
	}
	
	@SuppressWarnings("unchecked")
	public List<Form> findAllFormVersions(int defId) {
		String sql = "SELECT defverid, ver FROM bpm_procdefver WHERE defid=? AND isdeleted=0";
		return this.jdbcTemplate.query(sql, new Object[] { defId },
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Form form = new Form();
						form.setDefVerId(rs.getInt("defVerId"));
						form.setVer(rs.getString("ver"));
						return form;
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findAllFormListForParticipant(final String endpoint) {
		String sql = new StringBuffer(
				"SELECT defid, name, objtype, parentFolder FROM bpm_procdef").append(
				" WHERE (isdeleted='0') AND (objtype = 'form' AND prodver <> 0) OR (objtype = 'folder')").toString();
		
		final AclManager aclManager = AclManager.getInstance(transactionContext);
		
		return this.jdbcTemplate.query(sql,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Item item = null;
						
						int defid = rs.getInt("defid");
						
						Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint);
						
						if (permission.containsKey(AclManager.PERMISSION_INITIATE) ||
								permission.containsKey(AclManager.PERMISSION_VIEW) ||
								permission.containsKey(AclManager.PERMISSION_EDIT)) {
							String name = rs.getString("name");
							String objtype = rs.getString("objtype");
							String parentFolder = rs.getString("parentfolder");
							
							item = new Item();
							item.setId(String.valueOf(defid));
							item.setName(name);
							item.setParent(parentFolder);
							item.setObj(objtype);
						}
						
						return item;
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findParentFolder(final String comCode, final String defid) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT  defid             ").append("\n")
		   .append("       ,parentfolder      ").append("\n")
		   .append("       ,name              ").append("\n")
		   .append("       ,objtype           ").append("\n")
		   .append("       ,prodver           ").append("\n")
		   .append("  FROM  bpm_procdef       ").append("\n")
		   .append(" WHERE  isdeleted = 0     ").append("\n")
		   .append("   AND  objtype = 'folder'").append("\n")
		   .append("   AND  comcode = '").append(comCode).append("'").append("\n");
		
		final List<Item> itemList = this.jdbcTemplate.query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = null;
				
				int defid = rs.getInt("defid");
				String name = rs.getString("name");
				String objtype = rs.getString("objtype");
				String parentFolder = rs.getString("parentfolder");
				
				item = new Item();
				item.setId(String.valueOf(defid));
				item.setName(name);
				item.setParent(parentFolder);
				item.setObj(objtype);

				return item;
			}
		});
		
		FindParent.find(itemList, defid);
		
		return FindParent.getResultItemList();
	}
	
	static class FindParent {
		private static List<Item> _RESULT_ITEM_LIST;
			public static List<Item> getResultItemList() {
				return _RESULT_ITEM_LIST;
			}

		public static void find(List<Item> folderItem, String id) {
			String parent = "";
			Item item = null;
			
			for(int i=0; i < folderItem.size(); i++) {
				item = folderItem.get(i);
				if (item.getId().equals(id)) {
					parent = (String)item.getParent();
					break;
				}
			}
			
			for(int i=0; i < folderItem.size(); i++) {
				item = folderItem.get(i);
				if (item.getId().equals(parent)) {
					_RESULT_ITEM_LIST.add(item);
					
					if (item.getParent() != null && !item.getParent().equals("")) {
						find(folderItem, item.getId());
					}
					break;
				}
			}
		}
	}

	
	
	/**
     * <pre>
     *  Definition List Serch
     * </pre>
     * 
     * @param endpoint
     * @param comCode
     * @param cPermission
     * @parma parentDefId 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Item> selectProcessDefinitions(final String endpoint, final String comCode, final String parentDefId, final char cPermission) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  DEF.defid                 ").append("\n")
           .append("       ,DEF.parentfolder          ").append("\n")
           .append("       ,DEF.name                  ").append("\n")
           .append("       ,DEF.objtype               ").append("\n")
           .append("       ,(SELECT COUNT(1)          ").append("\n")
           .append("          FROM BPM_PROCDEF CD     ").append("\n")
           .append("         WHERE CD.PARENTFOLDER = DEF.DEFID ").append("\n")
           .append("           AND CD.ISDELETED = 0   ").append("\n")
           .append("            AND DEF.ISFOLDER = 1) AS CNT ").append("\n")
           .append("  FROM  bpm_procdef DEF           ").append("\n")
           .append(" WHERE  DEF.isdeleted = 0         ").append("\n")
           .append("   AND  DEF.comcode = ?           ").append("\n")
           .append("   AND  DEF.parentfolder = ?      ").append("\n");
        
        final AclManager aclManager = AclManager.getInstance(transactionContext);
        
            return this.jdbcTemplate.query(sql.toString(), new Object[]{comCode, new Long(parentDefId)}, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = null;
                
                int defid = rs.getInt("defid");
                Map<Character, Boolean> permission = aclManager.getPermission(defid, endpoint, true);
                
                if (permission.containsKey(AclManager.PERMISSION_MANAGEMENT) || permission.containsKey(cPermission)) {
                    String name = rs.getString("name");
                    String objtype = rs.getString("objtype");
                    String parentFolder = rs.getString("parentfolder");
                    int cnt = rs.getInt("cnt");
                    
                    item = new Item();
                    item.setId(String.valueOf(defid));
                    item.setName(name);
                    item.setParent(parentFolder);
                    item.setObj(objtype);
                    item.setChildCnt(String.valueOf(cnt));

                    Character[] permissions = null;
                    if (permission.size() > 0)
                        permissions = (Character[]) permission.keySet().toArray(new Character[permission.size()]);
                    item.setAuthority(permissions);
                }
                return item;
            }
        });
    }
    
    /**
     * <pre>
     *  Definition List Serch
     * </pre>
     * 
     * @param endpoint
     * @param comCode
     * @param cPermission
     * @parma parentDefId 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Item> selectAllProcessDefinitions(final String parentDefId) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT  DEF.defid                 ").append("\n")
        .append("       ,DEF.parentfolder          ").append("\n")
        .append("       ,DEF.name                  ").append("\n")
        .append("       ,DEF.objtype               ").append("\n")
        .append("       ,(SELECT COUNT(1)          ").append("\n")
        .append("          FROM BPM_PROCDEF CD     ").append("\n")
        .append("         WHERE CD.PARENTFOLDER = DEF.DEFID ").append("\n")
        .append("           AND CD.ISDELETED = 0   ").append("\n")
        .append("            AND DEF.ISFOLDER = 1) AS CNT ").append("\n")
        .append("  FROM  bpm_procdef DEF           ").append("\n")
        .append(" WHERE  DEF.isdeleted = 0         ").append("\n")
        .append("   AND  DEF.parentfolder = ?      ").append("\n");
    	
    	return this.jdbcTemplate.query(sql.toString(), new Object[]{new Long(parentDefId)}, new RowMapper() {
    		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    			Item item = null;
    			
    			int defid = rs.getInt("defid");
				String name = rs.getString("name");
				String objtype = rs.getString("objtype");
				String parentFolder = rs.getString("parentfolder");
				int cnt = rs.getInt("cnt");
				
				item = new Item();
				item.setId(String.valueOf(defid));
				item.setName(name);
				item.setParent(parentFolder);
				item.setObj(objtype);
				item.setChildCnt(String.valueOf(cnt));
    				
    			return item;
    		}
    	});
    }
	
}
