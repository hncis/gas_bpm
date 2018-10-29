package org.uengine.ui.list.datamodel;

import java.sql.*;

public abstract class BasicDAO
{
	protected Connection con;

	public BasicDAO()
	{
	}

	public void setConnection( Connection con )
	{
		this.con = con;
	}
}
