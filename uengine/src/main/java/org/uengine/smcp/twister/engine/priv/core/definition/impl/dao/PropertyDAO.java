package org.uengine.smcp.twister.engine.priv.core.definition.impl.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smartcomps.twister.common.persistence.PersistentDataAccess;

/**
 * Data accessor for PropertyImpl class.
 * @see org.smartcomps.twister.engine.priv.core.definition.impl.PropertyImpl
 */
public class PropertyDAO extends PersistentDataAccess {

	/**
	 * 
	 * @uml.property name="log"
	 * @uml.associationEnd 
	 * @uml.property name="log" multiplicity="(1 1)"
	 */
	private Log log = LogFactory.getLog(PropertyDAO.class);

}
