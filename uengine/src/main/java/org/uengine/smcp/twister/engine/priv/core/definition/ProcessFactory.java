package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.common.persistence.CreationException;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.common.persistence.FinderException;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.CorrelationSetImpl;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.ProcessImpl;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.PropertyAliasImpl;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.PropertyImpl;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.dao.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ProcessFactory {

	/**
	 * Creates a new Process persistance instance.
	 * @param name of the Process to create
	 * @param namespace
     * @return TwisterProcess initiated with the provided name
	 * @throws DBSessionException
	 * @throws DBSessionException
	 */
    public static TwisterProcess createProcess(String name, String namespace) throws DBSessionException, CreationException {
        try {
            getByName(name, namespace);
            throw new CreationException("There's already a process named " + name);
        } catch (FinderException e) { }
        ProcessImpl process = new ProcessImpl();
        process.setName(name);
        process.setNamespace(namespace);
        process = (ProcessImpl) ProcessDAO.create(process);
        return process;
    }

    public static CorrelationSet addCorrelation(TwisterProcess process, String correlationName, String correlationProps) throws DBSessionException, FinderException {
        CorrelationSetImpl correlationSet = new CorrelationSetImpl();
        correlationSet.setName(correlationName);
        correlationSet.setPropertiesString(correlationProps);

        TwisterProcess persistentProcess = getByName(process.getName(), process.getNamespace());
        ((ProcessImpl)persistentProcess).addCorrelationSet(correlationSet);

        ProcessDAO.create(correlationSet);
//        ProcessDAO.update(persistentProcess);

        return correlationSet;
    }

    public static Property addProperty(TwisterProcess process, String name,
                                String type) throws DBSessionException, FinderException {

        PropertyImpl property = new PropertyImpl();
        property.setName(name);
        property.setType(type);

        TwisterProcess persistentProcess = getByName(process.getName(), process.getNamespace());
        ((ProcessImpl)persistentProcess).addProperty(property);

        ProcessDAO.create(property);

        return property;
    }

    /**
     * Adds a persistent property alias to a Property.
     * @param property
     * @param msgType
     * @param part
     * @param query
     * @return newly created PropertyAlias
     * @throws DBSessionException
     */
    public static PropertyAlias addPropertyAlias(Property property, String msgType, String part,
                                String query) throws DBSessionException {

        PropertyAliasImpl alias = new PropertyAliasImpl();
        alias.setMessageType(msgType);
        alias.setPart(part);
        alias.setQuery(query);
        property.addAlias(alias);

        ProcessDAO.create(alias);

        return alias;
    }

	/**
	 * Finds a persistent Process from its name.
	 * @param name
	 * @param namespace
     * @return TwisterProcess
	 * @throws DBSessionException
	 * @throws DBSessionException
	 */
    public static TwisterProcess getByName(String name, String namespace) throws DBSessionException, FinderException {
        TwisterProcess process = ProcessDAO.findByName(name, namespace);
        if (process == null) throw new FinderException("Could not find a process with name : " + namespace + name);
        return process;
    }

	/**
	 * FindAll ProcessNames in DB. The process name is the concatenation of the namespace and the name.
	 * @return Collection of Process Names
	 * @throws DBSessionException
	 * @throws FinderException
	 */
    public static Collection findAllProcessNames() throws DBSessionException, FinderException {
        Collection processes = ProcessDAO.findAll(ProcessImpl.class);
        if(processes.size()==0) {
            throw new FinderException("No process in DB");
        }
        Collection res = new HashSet();
        for (Iterator it = processes.iterator(); it.hasNext();) {
            ProcessImpl process = (ProcessImpl) it.next();
            res.add(process.getNamespace()+process.getName());
        }
        return res;
    }

}
