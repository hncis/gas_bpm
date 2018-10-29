package org.uengine.smcp.twister.engine.priv.core.definition;

import org.smartcomps.twister.common.configuration.EngineConfiguration;
import org.smartcomps.twister.common.persistence.DBSessionException;
import org.smartcomps.twister.engine.exception.InstantiationException;
import org.smartcomps.twister.engine.exception.LinkAlreadyBoundException;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.*;
import org.uengine.smcp.twister.engine.priv.core.definition.impl.dao.*;

import java.util.List;
import java.util.Set;

/**
 * Creates appropriate activities or activity specific objects, setting default values.
 */
public class ActivityFactory {

	/**
	 * Adds an assignment to an assign activity, creating the associated Assign persistent
	 * instance.
	 * @param assign the Assign activity to add assignment for
	 * @param fromType the type of the "from" part of the assignment (see Assignment interface)
	 * @param toType the type of the "to" part of the assignment (see Assignment interface)
	 * @return the newly create Assignment instance (usually not useful)
	 * @throws DBSessionException
	 * @throws DBSessionException
	 * @see org.smcp.twister.engine.priv.core.definition.Assignment
	 */
	public static Assignment addAssignment(Assign assign, int fromType, int toType) throws DBSessionException {
		AssignmentImpl instance = new AssignmentImpl();
		instance.setFromType(fromType);
		instance.setToType(toType);
		assign.addAssignment(instance);

		instance = (AssignmentImpl) ActivityDAO.create(instance);
        ActivityDAO.update(assign);

		return instance;
	}

    /**
     * Adds a correlation reference (CorrelationRef) to an Invoke activity.
     * @param invoke
     * @param set
     * @param initiate
     * @param pattern
     * @return
     * @throws DBSessionException
     */
    public static CorrelationRef addCorrelationRef(Invoke invoke, String set, boolean initiate, int pattern) throws DBSessionException {
        CorrelationRef ref = createCorrelationRef(set, initiate, pattern);
        invoke.addCorrelation(ref);
        ActivityDAO.update(invoke);
        return ref;
    }

    /**
     * Adds a correlation reference (CorrelationRef) to a Receive activity.
     * @param receive
     * @param set
     * @param initiate
     * @param pattern
     * @return
     * @throws DBSessionException
     */
    public static CorrelationRef addCorrelationRef(Receive receive, String set, boolean initiate, int pattern) throws DBSessionException {
        CorrelationRef ref = createCorrelationRef(set, initiate, pattern);
        receive.addCorrelation(ref);
        ActivityDAO.update(receive);
        return ref;
    }

    /**
     * Adds a MessageEvent to a Pick structured activity triggering the execution of
     * the provided activity when an appropriate message is received.
     * @param pick
     * @param activity
     * @param partnerLink
     * @param portType
     * @param operation
     * @param variable
     * @param correlations
     * @return
     * @throws DBSessionException
     */
    public static MessageEvent addMessageEvent(Pick pick, Activity activity, String partnerLink, String portType, String operation, String variable, Set correlations) throws DBSessionException {
        MessageEventImpl instance = new MessageEventImpl();
        instance.setOperation(operation);
        instance.setPartnerLink(partnerLink);
        instance.setPortType(portType);
        instance.setVariable(variable);
        instance.setCorrelations(correlations);

        instance = (MessageEventImpl) ActivityDAO.create(instance);
        pick.addMessageEvent(instance, activity);
        return instance;
    }

    /**
     * Adds an AlarmEvent to a Pick structured activity triggering the execution of
     * the provided activity when the corresponding alarm is reached.
     * @param pick
     * @param activity
     * @param timeExpression
     * @param type
     * @return
     * @throws DBSessionException
     */
    public static AlarmEvent addAlarmEvent(Pick pick, Activity activity, String timeExpression, int type) throws DBSessionException {
		AlarmEventImpl instance = new AlarmEventImpl();
		instance.setTimeExpression(timeExpression);
		instance.setType(type);

		instance = (AlarmEventImpl) ActivityDAO.create(instance);
        pick.addAlarmEvent(instance, activity);
		return instance;
	}

    /**
     * Creates a new persistent activity instance implementing the provided interface and
     * assigns it to the provided container. <br>
     * IMPORTANT : when you create a container and then create several activities using
     * this container, the order used to create activities is the order of addition in the
     * container. For example when you create a Sequence, the order in which you create
     * sub-activities will be the order of activities in the sequence.
     * @param activityInterface the interface the returned instance has to implement
     * @param container the container to register the created activity under
     * @return a persistent implementation of the provided interface
     */
    public static Activity createActivity(Class activityInterface, StructuredActivity container) throws DBSessionException {
        if (!activityInterface.isInterface()) {
            throw new java.lang.IllegalArgumentException("Method ActivityFactory.createActivity takes an interface for parameter, not a class.");
        }

        ActivityImpl instance = null;
        try {
            instance = getActivityInstance(activityInterface);
        } catch (InstantiationException e) {
            throw new DBSessionException(e);
        }
        container.addActivity(instance);
		instance.setContainer(container);
        instance = (ActivityImpl) ActivityDAO.create(instance);

        return instance;
    }

    /**
     * Creates a new persistent activity instance implementing the provided interface and
     * assigns it to the provided process, only needed once in a process creation. <br>
     * IMPORTANT : when you create a container and then create several activities using
     * this container, the order used to create activities is the order of addition in the
     * container. For example when you create a Sequence, the order in which you create
     * sub-activities will be the order of activities in the sequence.
     * @param activityInterface the interface the returned instance has to implement
     * @param process the process to register the created activity under
     * @return a persistent implementation of the provided interface
     */
    public static Activity createActivity(Class activityInterface, TwisterProcess process) throws DBSessionException {
        if (!activityInterface.isInterface()) {
            throw new java.lang.IllegalArgumentException("Method ActivityFactory.createActivity takes an interface for parameter, not a class.");
        }

        ActivityImpl instance = null;
        try {
            instance = getActivityInstance(activityInterface);
        } catch (InstantiationException e) {
            throw new DBSessionException(e);
        }
        ((ProcessImpl)process).setActivity(instance);
		instance.setProcess(process);
        instance = (ActivityImpl) ActivityDAO.create(instance);

        return instance;
    }

	/**
	 * Creates a MessageEvent persistent implementation initializing it with the provided values.
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param variable
	 * @param correlations
	 * @return a MessageEvent persistent implementation
	 * @throws DBSessionException
	 * @throws DBSessionException
     * @deprecated use addMessageEvent on Pick instead
	 */
	public static MessageEvent createMessageEvent(String partnerLink, String portType, String operation, String variable, Set correlations) throws DBSessionException {
		MessageEventImpl instance = new MessageEventImpl();
		instance.setOperation(operation);
		instance.setPartnerLink(partnerLink);
		instance.setPortType(portType);
		instance.setVariable(variable);
		instance.setCorrelations(correlations);

		instance = (MessageEventImpl) ActivityDAO.create(instance);
		return instance;
	}

	/**
	 * Creates an AlarmEvent persistent implementation initializing it with the provided values.
	 * @param timeExpression
	 * @param type
	 * @return an AlarmEvent persistent implementation
	 * @throws DBSessionException
	 * @throws DBSessionException
     * @deprecated use addAlarmEvent on Pick instead
	 */
	public static AlarmEvent createAlarmEvent(String timeExpression, int type) throws DBSessionException, DBSessionException {
		AlarmEventImpl instance = new AlarmEventImpl();
		instance.setTimeExpression(timeExpression);
		instance.setType(type);

		instance = (AlarmEventImpl) ActivityDAO.create(instance);
		return instance;
	}

	public static CorrelationRef createCorrelationRef(String set, boolean initiate, int pattern) throws DBSessionException {
		CorrelationRefImpl instance = new CorrelationRefImpl();
		instance.setInitiate(initiate);
		instance.setPattern(pattern);
		instance.setSet(set);

		instance = (CorrelationRefImpl) ActivityDAO.create(instance);
		return instance;
	}

    /**
     * Binds an activity as the source of a Link, eventually creating that Link if
     * it doesn't exist.
     * @param linkName
     * @param sourceActivity
     */
    public static void bindSource(String linkName, Activity sourceActivity) throws LinkAlreadyBoundException {

    }

    /**
     * Binds an activity as the destination of a Link, eventually creating that Link if
     * it doesn't exist.
     * @param linkName
     * @param sourceActivity
     */
    public static void bindDestination(String linkName, Activity sourceActivity) throws LinkAlreadyBoundException {

    }

    /**
     * Find receives interested in the provided invoker paramaters.
     * @param partnerLink
     * @param portType
     * @param operation
     * @return List of Receive
     * @throws DBSessionException
     */
    public static List findReceivesByInvoker(String partnerLink, String portType, String operation) throws DBSessionException {
        return ActivityDAO.findReceivesByInvoker(partnerLink, portType, operation);
    }

    /**
     * Finds Picks and according MessageEvents interested in the provided
     * invoker paramters.
     * @param partnerLink
     * @param portType
     * @param operation
     * @return List of Object[] containing the Pick as obj[0] and the MessageEvent as obj[1]
     * @throws DBSessionException
     */
    public static List findPickEventsByInvoker(String partnerLink, String portType, String operation) throws DBSessionException {
        return ActivityDAO.findPickEventsByInvoker(partnerLink, portType, operation);
    }

    /**
     * Does the bulk work of finding the right class from the interface and instantiating it.
     * @param activityInterface
     * @return
     * @throws InstantiationException
     */
    private static ActivityImpl getActivityInstance(Class activityInterface) throws InstantiationException {
        String activityClassName = EngineConfiguration.getActivityImplementation(activityInterface.getName());

        ActivityImpl instance = null;
        try {
            instance = (ActivityImpl) Class.forName(activityClassName).newInstance();
        } catch (java.lang.InstantiationException e) {
            throw new InstantiationException("Could not instantiate an activity implementation " + activityClassName + " for interface " + activityInterface, e);
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Could not instantiate an activity implementation " + activityClassName + " for interface " + activityInterface, e);
        } catch (ClassNotFoundException e) {
            throw new InstantiationException("The implementation class " + activityClassName + " for the interface " + activityInterface + " could not be found", e);
        } catch (ClassCastException e) {
            throw new InstantiationException("The instance returned from the interface " + activityInterface + " is not an Activity implementation : " + activityClassName, e);
        }
        return instance;
    }

}
