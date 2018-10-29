/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/
/*
 * TableOIDGenerator.java
 *
 * Created on March 25, 2003, 4:30 PM
 */

package com.webdeninteractive.bie.persistent;

import java.sql.*;
import java.io.*;
import java.math.BigDecimal;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 * Utility class to generate OID's
 * 
 */
public class OIDGenerator {
    
/** Generate an object ID for the given table. */
public static synchronized int get(String table) throws IOException, SQLException {
	String statement = "SELECT " + table + ".nextval FROM DUAL";
	
	InitialContext ctx = null;
	DataSource ds = null;
	try {
		ctx = new InitialContext();
		ds = (javax.sql.DataSource) ctx.lookup("java:/uEngineDS");
	} catch (NamingException e) {
		e.printStackTrace();
	}
	Connection connection = ds.getConnection();
		
	PreparedStatement stmt = null;
		
	BigDecimal id = null;

	try {
		stmt = connection.prepareStatement(statement);

		//			System.out.println("statement ==> " + statement);
		ResultSet resultSet = stmt.executeQuery();
		resultSet.next();
		id = resultSet.getBigDecimal(1);
		stmt.close();
	} finally {
		if ( connection != null ) connection.close();
		if ( stmt != null ) stmt.close();
	}

	return id.intValue();	    
//        Connection conn = null;
//        Statement s = null;
//        ResultSet rs = null;
//        int oid = 1;
//        try {
//            conn = DataSourceFactory.getConnection();
//            s = conn.createStatement();
//            
//            boolean found = false;
//            
//            try {
//                rs = s.executeQuery("select nextValue from Incrementer where incrementerId='" + table + "'");            
//                if (rs.next()) {
//                    found=true;
//                }
//            }catch (SQLException sqle) {
//                //do nothing
//            }
//            
//            if (found) {
//                oid =  rs.getInt(1);
//                int next = oid +1;
//                s.execute("update Incrementer set nextValue=" + next + " where incrementerId='"+ table + "'");                                    
//            } else {
//                s.execute("insert into Incrementer (incrementerId,nextValue) values ('" + table + "',2)");                                    
//            }       
//            
//            //conn.commit();
//            return oid;
//        }finally {
//            try {
//                if (rs != null) rs.close();
//                if (s != null) s.close();
//                if (conn != null) conn.close();
//            }catch (Exception e2) {
//                //e2.printStackTrace();
//            
//            }
//        }                                                   
    }
}
