package org.uengine.ui.list.datamodel;

import org.apache.log4j.*;

public class DAOFactoryImpl
{

	private static Logger logger = Logger.getLogger( DAOFactoryImpl.class.
		getName() );

	public static DAOFactory getDAOFactory()
		throws Exception
	{
		try
		{
			return new BasicDAOFactory();
		}
		catch ( Exception e )
		{
			logger.error( e );
			return null;
		}
	}
}
