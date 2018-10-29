package org.uengine.kernel.descriptor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.uengine.kernel.Activity;
import org.uengine.kernel.DatabaseMappingActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.SQLActivity;
import org.uengine.processdesigner.*;
import org.uengine.processdesigner.inputters.*;
import org.uengine.ui.XMLValueInput;
import org.uengine.util.dao.ConnectionFactory;
import org.uengine.util.dao.DataSourceConnectionFactory;
import org.uengine.util.dao.DefaultConnectionFactory;
import org.metaworks.*;
import org.metaworks.inputter.Inputter;
import org.metaworks.inputter.RadioInput;
import org.metaworks.inputter.SelectInput;
import org.metaworks.inputter.TextAreaInput;

import com.webdeninteractive.xbotts.Mapping.compiler.Record;
import com.webdeninteractive.xbotts.Mapping.maptool.SchemaTreeModel;

/**
 * @author Jinyoung Jang
 */

public class DatabaseMappingActivityDescriptor extends ActivityDescriptor{
	Inputter connectionFactoryInputter = null;
	Inputter tableNameInputter = null;
	org_uengine_contexts_MappingContextInput dbMappingContextInput =null;
	
	static HashMap typesToClassMap = new HashMap();
	static{
		typesToClassMap.put("VARCHAR",String.class);
		typesToClassMap.put("VARCHAR2",String.class);
		typesToClassMap.put("INT",Number.class);
		typesToClassMap.put("INTEGER",Number.class);
		typesToClassMap.put("NUMBER",Number.class);
		typesToClassMap.put("DATE",Calendar.class);
		typesToClassMap.put("TIMESTAMP",Calendar.class);
	}
	
	public DatabaseMappingActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(final ProcessDesigner pd, final Activity activity){
		super.initialize(pd, activity);
		
		FieldDescriptor fd = getFieldDescriptor("MappingContext");
		
		dbMappingContextInput = new org_uengine_contexts_MappingContextInput(){
			
		
			protected SchemaTreeModel createProcessDefinitionSchemaTreeModel(boolean isSource) {
				if(!isSource)
					return super.createProcessDefinitionSchemaTreeModel(isSource);
				
				Record root = new Record("Tables");
				
				try {
					
					InputStream is = null;
					
					String tableName = null;
					ConnectionFactory connectionFactory = null;
					if(activity!=null){
						tableName = ((DatabaseMappingActivity)activity).getTableName();
						connectionFactory = ((DatabaseMappingActivity)activity).getConnectionFactory();
					}
					
					if(tableName==null){
						tableName = (String)tableNameInputter.getValue();
					}
					
					if(connectionFactory==null){
						connectionFactory = (ConnectionFactory) connectionFactoryInputter.getValue();
					}
					
					//if(connectionFactory!=null){
						String serConnectionFactory = GlobalContext.serialize(connectionFactory, ConnectionFactory.class);
						
						HashMap keyAndValue = new HashMap();
						keyAndValue.put("connectionFactory", serConnectionFactory);
						keyAndValue.put("tableName", tableName);

						is = ProcessDesigner.getClientProxy().postStream("/processmanager/browseDatabaseDirectory.jsp", keyAndValue);
					/*}else{
						is = ProcessDesigner.getClientProxy().openStream("/processmanager/browseDatabaseDirectory.jsp");
					}*/
				
					ArrayList columnList = (ArrayList) GlobalContext.deserialize(is, String.class);
					Record parent = null;
					/*for(int i=0; i<dbList.size(); i++){
						Object element = dbList.get(i);
						if(element instanceof ArrayList){//that means the field parts are started.
							if(parent!=null){
								ArrayList fields = (ArrayList)element;
								for(int j=0; j<fields.size(); j++){
									String fieldName = (String)fields.get(j);
									Record node = createRecord(fieldName, parent.getName() + "." + fieldName, isSource);
									parent.add(node);
								}
							}
						}else{
							String tableName = (String)element;
							Record node = createRecord(tableName, tableName, isSource);
							parent = node;
							root.add(node);
						}
					}*/
					
					for(int i=0; i<columnList.size(); i++){
						ColumnProperty columnProperty = (ColumnProperty) columnList.get(i);
							
						Record node = createRecord(columnProperty.getColumnName(), tableName + "." + columnProperty.getColumnName(), isSource);
						node.getExtendedProperties().put("columnProperty", columnProperty);
						
						if(typesToClassMap.containsKey(columnProperty.getType())){
							Class type = (Class) typesToClassMap.get(columnProperty.getType());
							
							node.getExtendedProperties().put("type", type);
							node.setClassType(type);
						}
						
						root.add(node);
					}
					
					return new SchemaTreeModel(root);
					
				} catch (Exception e) {
					e.printStackTrace();
					
					Record node = new Record("can't connect database repository...");
					SchemaTreeModel schema = new SchemaTreeModel(node);
					
					return schema;
				}
			}
			
		};

		fd.setInputter(dbMappingContextInput);
		
		fd = getFieldDescriptor("ConnectionFactory");
		connectionFactoryInputter = fd.getInputter();
		
		fd = getFieldDescriptor("TableName");
		
		tableNameInputter = fd.getInputter();		
		
/*		tableNameInputter = new SelectInput();
		fd.setInputter(tableNameInputter);
		
		tableNameInputter.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dbMappingContextInput.refresh();
			}
			
		});
*/		
		connectionFactoryInputter.addActionListener(
			new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					//dbMappingContextInput.refresh();
					
					if(connectionFactoryInputter!=null){
						String serConnectionFactory;
						try {
							serConnectionFactory = GlobalContext.serialize(connectionFactoryInputter.getValue(), ConnectionFactory.class);
							HashMap keyAndValue = new HashMap();
							keyAndValue.put("connectionFactory", serConnectionFactory);

							InputStream is = ProcessDesigner.getClientProxy().postStream("/processmanager/browseDatabaseDirectory_tableNames.jsp", keyAndValue);
							
							ArrayList tableList = (ArrayList) GlobalContext.deserialize(is, String.class);
							String[] tableNamesArr = new String[tableList.size()];
							
							//tableNameInputter.setValues(tableNamesArr);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
					
				}
				
			}
		);
		
		fd = getFieldDescriptor("QueryMode");
		fd.setInputter(new RadioInput(
				new String[]{
						GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.databasemappingactivity.querymode.insert.displayname", "Insert"),
						GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.databasemappingactivity.querymode.update.displayname", "Update"),
						//GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.databasemappingactivity.querymode.insertorupdate.displayname", "Insert if not exist and Update if exist"),
						GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.databasemappingactivity.querymode.delete.displayname", "Delete"),
						GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.databasemappingactivity.querymode.select.displayname", "Select"),
				}, new Object[]{
						new Integer(DatabaseMappingActivity.QUERYMODE_INSERT),
						new Integer(DatabaseMappingActivity.QUERYMODE_UPDATE),
						//new Integer(DatabaseMappingActivity.QUERYMODE_INSERT_IF_NOT_EXIST_AND_UPDATE_IF_EXIST),
						new Integer(DatabaseMappingActivity.QUERYMODE_DELETE),						
						new Integer(DatabaseMappingActivity.QUERYMODE_SELECT),						
				}
			)
		);
		
		setFieldDisplayNames(DatabaseMappingActivity.class);
	}
	
	static public ArrayList getDatabaseDirectory() throws Exception{
		return getDatabaseDirectory((ConnectionFactory)null);
	}

	static public ArrayList getDatabaseDirectory(String dsn) throws Exception{
		if("".equals(dsn)||dsn==null){
			return getDatabaseDirectory((ConnectionFactory)null); 
		}else{
			DataSourceConnectionFactory dsnCF = new DataSourceConnectionFactory();
			dsnCF.setDataSourceJndiName(dsn);
			return getDatabaseDirectory(dsnCF);
		}
	}
	
	static public ArrayList getDatabaseDirectory(ConnectionFactory cf, String fieldOnlyTableName) throws Exception{
		ArrayList dbDirList = new ArrayList();
		Connection con=null ;
		
		if(cf!=null){
			try{
				con = cf.getConnection();
			}catch(Exception e){
				return dbDirList;
			}
		}else {
			con = DefaultConnectionFactory.create().getConnection();
		}

		DatabaseMetaData dMeta = con.getMetaData();

        String[] usertables = {
                "TABLE", "GLOBAL TEMPORARY", "VIEW"
            };
        
        try{
	        // fredt@users Schema support
	        Vector schemas = new Vector();
	        Vector tables  = new Vector();
	
	        // sqlbob@users Added remarks.
	        Vector    remarks = new Vector();

//	        ResultSet result  = dMeta.getTables(null, dMeta.getUserName(), null, usertables);
	        ResultSet result  = dMeta.getTables(null, null, "%", usertables);
	
	        try {
	            while (result.next()) {
	                schemas.addElement(result.getString(2));
	                tables.addElement(result.getString(3));
	                remarks.addElement(result.getString(5));
	            }
	        } finally {
	            result.close();
	        }
	        
	        boolean foundTable = false;
	
	        for (int i = 0; i < tables.size() && !foundTable; i++) {	        	
	        	
	            String name   = (String) tables.elementAt(i);
	            String schema = (String) schemas.elementAt(i);
	            String key    = "tab-" + name + "-";

	        	if("null".equals(fieldOnlyTableName)){
	        		dbDirList.add(name);
	        		continue;
	        	}
	
	        	if(fieldOnlyTableName!=null){
	        		if(!name.equalsIgnoreCase(fieldOnlyTableName))
	        			continue;
	        			else 
	        			foundTable = true;
	        	}
	        	
	        	if(fieldOnlyTableName==null)
	        		dbDirList.add(name);
	
	            // sqlbob@users Added remarks.
	            String remark = (String) remarks.elementAt(i);
	
	            if ((schema != null) &&!schema.trim().equals("")) {
	//                tTree.addRow(key + "s", "schema: " + schema);
	            }
	
	            if ((remark != null) &&!remark.trim().equals("")) {
	 //               tTree.addRow(key + "r", " " + remark);
	            }
	
	            ResultSet col = dMeta.getColumns(null, schema, name, null);
	
	            try {
	            	ArrayList columns = null;
	            	
	            	if(fieldOnlyTableName==null)
	            		columns = new ArrayList();
	            	else
	            		columns = dbDirList;
	            	
	                while (col.next()) {
	                    String c  = col.getString(4);
	                    String k1 = key + "col-" + c + "-";
	                    
	
	                    String type = col.getString(6);
	
	                    //tTree.addRow(k1 + "t", "Type: " + type);

	                    boolean nullable = col.getInt(11)
	                                       != DatabaseMetaData.columnNoNulls;
	
	                    ColumnProperty columnProperty = new ColumnProperty();
	                    columnProperty.setColumnName(c);
	                    columnProperty.setType(type);
	                    columnProperty.setNullable(nullable);
	                    columns.add(columnProperty);

	                    //tTree.DaddRow(k1 + "n", "Nullable: " + nullable);
	                }
	                
	            	if(fieldOnlyTableName==null)
	            		dbDirList.add(columns);
	            } finally {
	                col.close();
	            }
	        }
	        
	        return dbDirList;
	        
        }finally{
        	try{con.close();}catch(Exception e){}
        }
	}
	
	static public ArrayList getColumnNames(ConnectionFactory cf, String tableName) throws Exception{
        return getDatabaseDirectory(cf, tableName);
	}
	
	static public ArrayList getDatabaseDirectory(ConnectionFactory cf) throws Exception{
		return getDatabaseDirectory(cf, null);
	}

	static public ArrayList getTableNames(ConnectionFactory cf) throws Exception{
		return getDatabaseDirectory(cf, "null");
	}


}
