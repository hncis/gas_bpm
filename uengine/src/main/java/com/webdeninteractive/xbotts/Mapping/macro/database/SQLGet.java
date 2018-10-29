/*
-------------------------------------------------------------------
BIE is Copyright 2001-2004 Brunswick Corp.
-------------------------------------------------------------------
Please read the legal notices (docs/legal.txt) and the license
(docs/bie_license.txt) that came with this distribution before using
this software.
-------------------------------------------------------------------
*/

package com.webdeninteractive.xbotts.Mapping.macro.database;

import com.webdeninteractive.xbotts.Mapping.macro.*;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.Driver; 
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.regex.*;
import java.util.Properties;

/**
 *
 * @author jtsmith
 */


public class SQLGet extends AbstractExtensionFunction {
    
    	public SQLGet() {
		super.addLinkableParameter("str1");
        	super.addEditableParameter("driver", "com.mysql.jdbc.Driver", String.class, "The database driver to use.");
        	super.addEditableParameter("url", "jdbc:mysql://localhost/bie", String.class, "The JDBC URL to connect with.");
        	super.addEditableParameter("user", "", String.class, "The username to connect with.");
        	super.addEditableParameter("pass", "", String.class, "The password to connect with.");
        	super.addEditableParameter("query", "", String.class, "The query to execute.");
    	}
    
    	public String runQuery(String str1, String driver, String url, String user, String pass, String query)
	throws Exception {
		String output = "";
		Driver d = (Driver)Class.forName(driver).newInstance();
		Properties p = new Properties( );
		p.setProperty("user", user);
		p.setProperty("password", pass);
		//Connection conn = DriverManager.getConnection(url, user, pass);		
		Connection conn = d.connect(url, p);
		query = fixQuery(query, str1, "%1");
		//query = fixQuery(query, str2, "%2");
		Statement stmt = null; 
		ResultSet rs = null; 
		try {
			stmt = conn.createStatement(); 
    			rs = stmt.executeQuery(query);
			if (rs.next()) {
				output = rs.getObject(1).toString();
			} else {
				output = "";
			}
		} finally {
			if (rs != null) { 
        			try {
            				rs.close(); 
        			} finally { 
					// ignore 
				} 
        			rs = null; 
    			}
    			if (stmt != null) { 
        			try { 
            				stmt.close(); 
        			} finally { 
					// ignore 
				} 
        			stmt = null; 
    			} 
			if (conn != null) {
				try {
					conn.close();
        			} finally { 
					// ignore 
				}
			}
		}
        	return output;
    	}
		public String runQuery(String driver, String url, String user, String pass, String query)
	throws Exception {
			return runQuery("", driver, url, user, pass, query);
	}
        private String fixQuery(String query, String var, String patternStr) {
            	Pattern pattern = Pattern.compile(patternStr);
            	Matcher matcher = pattern.matcher(query);
            	return matcher.replaceAll(var);
        }
}

