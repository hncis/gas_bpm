package org.uengine.smcp.twister.engine.priv.core.definition.impl.dao;

//import net.sf.hibernate.HibernateException;
//import net.sf.hibernate.Query;
//import net.sf.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.persistence.DBSessionManager;
//import org.smartcomps.twister.common.persistence.PersistentDataAccess;
import org.smartcomps.twister.engine.priv.core.definition.impl.StructuredActivityImpl;

import java.util.List;

public class ActivityDAO extends PersistentDataAccess{

    public static StructuredActivityImpl findContainer(Long containerId) throws DBSessionException {
        return null;
    }

    public static List findReceivesByInvoker(String partnerLink, String portType, String operation) throws DBSessionException {
        return null;
    }

    public static List findPickEventsByInvoker(String partnerLink, String portType, String operation) throws DBSessionException {
        return null;
    }
}
