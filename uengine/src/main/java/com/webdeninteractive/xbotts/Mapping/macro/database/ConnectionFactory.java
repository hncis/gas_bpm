package com.webdeninteractive.xbotts.Mapping.macro.database;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.uengine.kernel.GlobalContext;

public class ConnectionFactory {

	public static Connection getConnection() throws Exception{
		/*Class.forName("oracle.jdbc.driver.OracleDriver"); 
		String url="jdbc:oracle:thin:@172.16.2.163:1521:demoora"; 
		Connection conn = DriverManager.getConnection(url, "renewal", "renewal");*/
		Connection conn = null;
		try {		
			InitialContext ctx = new InitialContext();
			DataSource ds = (javax.sql.DataSource) ctx.lookup("java:/uEngineDS");
			conn = ds.getConnection();
		} catch (Exception e) {
			
		} finally {
			GlobalContext.loadClass("oracle.jdbc.driver.OracleDriver"); 
			String url="jdbc:oracle:thin:@172.16.2.163:1521:demoora"; 
			conn = DriverManager.getConnection(url, "bpm", "bpm");
		}
		
				
		return conn; 
	}
}
