package org.uengine.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
import java.util.Map;

import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ReceiveActivity;

public class SQLSampleActivity extends ReceiveActivity{

	//Activity �Ӽ��� ���� /////////////////////////////
	//----------------------------------------------------------
	// �� �Ӽ��� �ڹٺ��� ��Ÿ�Ϸ� setter/getter�� ������ֵ��� �մϴ�. 
    // �̸� ���� �����̳ʿ� ����Ͱ� ���÷����� ���� �ڵ������� ������ �� �ֽ��ϴ�.
	
	TextContext sqlStmt;	
		public TextContext getSqlStmt(){
			return sqlStmt;
		}			
		public void setSqlStmt(TextContext value){
			sqlStmt = value;
		}	
	
	String connectionString;	
		public String getConnectionString(){
			return connectionString;
		}
		public void setConnectionString(String value){
			connectionString = value;
		}
	
	String userId;	
		public String getUserId(){
			return userId;
		}
		public void setUserId(String value){
			userId = value;
		}
	
	String password;	
		public String getPassword(){
			return password;
		}
		public void setPassword(String value){
			password = value;
		}
		
	String driverClass;	
		public String getDriverClass(){
			return driverClass;
		}
		public void setDriverClass(String value){
			driverClass = value;
		}

	public SQLSampleActivity(){}

	public ProcessInstance createInstance(ProcessInstance instanceInCreating) throws Exception{		
		super.createInstance(instanceInCreating);
		
		//Make a space to store the instance property for result log
		instanceInCreating.set(getTracingTag(), "resultLog", "");
		
		return instanceInCreating;
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception{
		Class.forName(getDriverClass());
		Connection con = DriverManager.getConnection(getConnectionString(), getUserId(), getPassword());		
		PreparedStatement prepStmt = con.prepareStatement(getSqlStmt().parse(this, instance));
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()){
			ByteArrayOutputStream resultLog = null;
			
			try{
				ResultSetMetaData rmeta=rs.getMetaData();
				for(int i=1; i<=rmeta.getColumnCount(); i++){
					String fieldName = new String(rmeta.getColumnName(i));			
					instance.set("", fieldName, (Serializable)rs.getObject(fieldName));
				}			
			}catch(Exception e){
				e.printStackTrace(new PrintStream(resultLog));
			}
			
			if(resultLog!=null)
				instance.set(getTracingTag(), "resultLog", resultLog.toString());
		}	
		
		prepStmt.close();
			
		fireComplete(instance);
	}
	
	@Override
	public Map getActivityDetails(ProcessInstance inst, String locale)
			throws Exception {
		Hashtable details = (Hashtable)super.getActivityDetails(inst, locale);
		details.put("actual executed SQL","context");
		
		return details;
	}
	
}
