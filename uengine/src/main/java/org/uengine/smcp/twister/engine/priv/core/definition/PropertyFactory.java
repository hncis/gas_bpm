package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.common.persistence.DBSessionException;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.PropertyImpl;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.dao.*;

/**
 * A factory to create or get specific property instances.
 */
public class PropertyFactory {

    public Property createProperty(String name, String type) throws DBSessionException {
        PropertyImpl prop = new PropertyImpl();
        prop.setName(name);
        prop.setType(type);

        prop = (PropertyImpl) PropertyDAO.create(prop);

        return prop;
    }
}
