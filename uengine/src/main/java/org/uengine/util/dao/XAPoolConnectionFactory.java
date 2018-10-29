/*
 * Created on 2004. 12. 15.
 */
package org.uengine.util.dao;

import javax.sql.*;

import java.sql.*;
import javax.naming.*;

import org.enhydra.jdbc.standard.StandardXAConnection;
import org.uengine.kernel.GlobalContext;

/**
 * @author Jinyoung Jang
 */
public class XAPoolConnectionFactory implements ClosableConnectionFactory{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	public static Class USE_CLASS = null;
	
	XAConnection xaconn;
	
	protected XAPoolConnectionFactory(){}

	public Connection getConnection() throws Exception{
		
		xaconn = xapool.DataBaseLayer.getInstance().getPool().getXAConnection();
		
		return xaconn.getConnection(); 
	}

	public void close() throws Exception {
		xapool.DataBaseLayer.getInstance().getPool().closeConnection(xaconn);
	}
	
}
