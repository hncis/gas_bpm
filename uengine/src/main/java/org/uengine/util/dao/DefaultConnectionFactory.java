/*
 * Created on 2004. 12. 15.
 */
package org.uengine.util.dao;

import javax.sql.*;

import java.sql.*;
import javax.naming.*;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.uengine.kernel.GlobalContext;

/**
 * @author Jinyoung Jang
 */
public class DefaultConnectionFactory implements ConnectionFactory {
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	public static Class USE_CLASS = null;
	
	protected DefaultConnectionFactory(){}

	public Connection getConnection() throws Exception{
		return this.getDataSource().getConnection(); 
	}
	
	public DataSource getDataSource() {
		DataSource ds = null;
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalContext.DATASOURCE_JNDI_NAME);
		} catch (Exception e) {
//			e.printStackTrace();
//			BasicDataSource bds = new BasicDataSource();
//			bds.setDriverClassName("org.hsqldb.jdbcDriver");
//			bds.setUrl("jdbc:hsqldb:hsql://localhost");
//			bds.setUsername("sa");
//			bds.setPassword("");
////			bds.setValidationQuery(GlobalContext.JDBC_VALIDATION_QUERY);
//			bds.setMaxTotal(20);
//			bds.setMaxIdle(10);
//			bds.setMaxWaitMillis(-1);
//			BasicDataSource bds = new BasicDataSource();
//			bds.setDriverClassName(GlobalContext.JDBC_DRIVERCLASSNAME);
//			bds.setUrl(GlobalContext.JDBC_URL);
//			bds.setUsername(GlobalContext.JDBC_USERNAME);
//			bds.setPassword(GlobalContext.JDBC_PASSWORD);
//			bds.setValidationQuery(GlobalContext.JDBC_VALIDATION_QUERY);
//			bds.setMaxActive(GlobalContext.JDBC_MAX_ACTIVE);
//			bds.setMaxIdle(GlobalContext.JDBC_MAX_IDLE);
//			bds.setMaxWait(GlobalContext.JDBC_MAX_WAIT);
//			ds = bds;
		}
		return ds;
	}
	
	public static DefaultConnectionFactory create() {
		if(USE_CLASS==null){
			try{
				USE_CLASS = Class.forName(GlobalContext.getPropertyString("defaultconnectionfactory.class"));
			}catch(Exception e){
				USE_CLASS = DefaultConnectionFactory.class;
			}
		}
		
		try {
			return (DefaultConnectionFactory) USE_CLASS.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		DefaultConnectionFactory connfac = DefaultConnectionFactory.create();
		Connection conn1 = connfac.getConnection();
		Connection conn2 = connfac.getConnection();
		
		System.out.println(conn1);
		System.out.println(conn2);
		
		if ( conn1 != null ) conn1.close();
		if ( conn2 != null ) conn2.close();
		
	}

}
