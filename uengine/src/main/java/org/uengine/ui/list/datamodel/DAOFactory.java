package org.uengine.ui.list.datamodel;

import java.sql.*;

import org.uengine.ui.list.exception.UEngineException;

/**
 * DAO Factory.
 * @author alcolins
 */
public interface DAOFactory
{

	public void close();

	public void setDataSource()
		throws UEngineException;

	public Connection getConnection();

	public void setConnection( Connection con );

	public void commit();

	public void rollback();

	public void setAutoCommit( boolean autoCommit );

	public BasicDAO getDAO( String str )
		throws UEngineException;
}
