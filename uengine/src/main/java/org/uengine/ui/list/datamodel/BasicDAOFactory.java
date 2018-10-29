package org.uengine.ui.list.datamodel;

import java.sql.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.*;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.util.dao.DefaultConnectionFactory;

public class BasicDAOFactory
	implements DAOFactory
{
	private static Logger logger = Logger.getLogger( BasicDAOFactory.class.
		getName() );

	protected Connection con;

	public BasicDAOFactory()
	{
		setDataSource();
	}

	public void setDataSource()
	{
		try
		{
//			ServiceLocator s = ServiceLocator.getInstance();
//			DataSource ds = s.getDataSource( dsName );
//			con = ds.getConnection();

//			// Load the JDBC driver
//			String driverName = "";
//			Class.forName(driverName);
//			// Create a connection to the database
//			String url = "";
//			String username = "";
//			String password = "";
//			con = DriverManager.getConnection(url, username, password);

//			ConnectionPool cp = ConnectionPoolMgr.getInstance();
//			con = cp.getConnection();
			
//			InitialContext ctx = null;
//			ctx = new InitialContext();
//			DataSource ds = (javax.sql.DataSource) ctx.lookup(BPMConstants.DATA_SOURCE_NAME);
//			con = ds.getConnection();
			
			con = DefaultConnectionFactory.create().getConnection();
		}
		catch ( Exception ex )
		{
			logger.error( ex, ex );
		}
	}

	public void close()
	{
		try
		{
			if ( con != null )
			{
				con.close();
			}
		}
		catch ( Exception ex )
		{
			logger.error( ex, ex );
		}
	}

	public Connection getConnection()
	{
		return con;
	}

	public void setConnection( Connection con )
	{
		close();
		this.con = con;
	}

	public void commit()
	{
		try
		{
			if ( con != null )
			{
				this.con.commit();
			}
		}
		catch ( Exception ex )
		{
			logger.error( ex, ex );
		}
	}

	public void rollback()
	{
		try
		{
			if ( con != null )
			{
				this.con.rollback();
			}
		}
		catch ( Exception ex )
		{
			logger.error( ex, ex );
		}
	}

	public void setAutoCommit( boolean autoCommit )
	{
		try
		{
			if ( con != null )
			{
				this.con.setAutoCommit(autoCommit);
			}
		}
		catch ( Exception ex )
		{
			logger.error( ex, ex );
		}
	}

	public BasicDAO getDAO( String classNm )
	{
		Object o = null;
		try
		{
			logger.debug( "[DAO]" + classNm + "\n" );
			o = Class.forName( classNm ).newInstance();
			BasicDAO basicDAO = ( BasicDAO )o;
			basicDAO.setConnection( con );
			return basicDAO;
		}
		catch ( Exception e )
		{
			logger.error( e, e );
			return null;
		}
	}

	public static DAOFactory getDAOFactory()
		throws UEngineException
	{
		return new BasicDAOFactory();
	}
}
