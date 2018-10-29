package com.webdeninteractive.xbotts.Mapping.macro.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;

import com.webdeninteractive.xbotts.Mapping.macro.AbstractExtensionFunction;
import com.webdeninteractive.xbotts.Mapping.macro.MutableParameter;
import com.webdeninteractive.xbotts.Mapping.macro.database.ConnectionFactory;

public class SequenceIdGenerator extends AbstractExtensionFunction {
	
	public SequenceIdGenerator() {
		
	}
	
	public String getName() {
		return "getSequenceId";
	}	
	
	MutableParameter[] mparams = new MutableParameter[]{
		new MutableParameter("Sequence Name", "", Object.class, "Sequence Key Name", this)
	};
	public MutableParameter[] getMutableParameters() {
		return mparams;
	}	
	
	Object oid = null;
    
	/**
	 *
	 */
	public Object getSequenceId(String sequence)throws Exception{
		try{
			if(oid==null){
				String statement = "SELECT " + sequence + ".nextval FROM DUAL";
				
				Connection connection = ConnectionFactory.getConnection();	
				PreparedStatement stmt = null;
				ResultSet rs = null;
		
				BigDecimal id = null;

				try {
					stmt = connection.prepareStatement(statement);

					rs = stmt.executeQuery();
					if (rs.next()) {
						id = rs.getBigDecimal(1);	
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if (rs != null) rs.close();
					if ( stmt != null ) stmt.close();
					if ( connection != null ) connection.close();
					
				}

				oid = id.toString();
				
			}
			return oid;
		}catch(Exception ex){
			throw new Exception("Could not get incrementor: "+sequence, ex);
		}
	}
	


	public ImageIcon getIcon() {
		return new ImageIcon(SequenceIdGenerator.class.getClassLoader().getResource("com/webdeninteractive/images/macro.gif"));
	}
    

	public String getSource() {
		return getClass( ).getName( );
	}
    
	public boolean isStatic( ){
		return false;
	}
}
