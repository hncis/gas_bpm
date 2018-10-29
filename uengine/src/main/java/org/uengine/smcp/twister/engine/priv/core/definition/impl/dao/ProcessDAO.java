package org.uengine.smcp.twister.engine.priv.core.definition.impl.dao;

//import net.sf.hibernate.Hibernate;
//import net.sf.hibernate.HibernateException;
//import net.sf.hibernate.Session;
//import net.sf.hibernate.type.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.persistence.DBSessionManager;
//import org.smartcomps.twister.common.persistence.PersistentDataAccess;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.ProcessImpl;

import java.util.List;

public class ProcessDAO extends PersistentDataAccess {

    private static Log log = LogFactory.getLog(ProcessDAO.class);

    public static ProcessImpl findByName(String name, String namespace) throws DBSessionException {
       return null;
    }

}
