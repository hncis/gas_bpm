package org.uengine.smcp.twister.engine.priv.core.definition.impl.dao;

import java.util.List;
import org.smartcomps.twister.common.persistence.DBSessionException;


public class PersistentDataAccess {

	public static Object create(Object po)
		throws DBSessionException
	{
		return po;
	}

	public static Object update(Object po)
		throws DBSessionException
	{
		return po;
	}

	public static Object remove(Object po)
		throws DBSessionException
	{
		return po;
	}

	public static Object findById(Long id, Class classToLoad)
		throws DBSessionException, org.smartcomps.twister.common.persistence.FinderException
	{
		return null;
	}

	public static List findAll(Class classToLoad)
		throws DBSessionException
	{
		return null;
	}

}
