package org.uengine.processdesigner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
import java.awt.*;

import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultProcessInstance;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.UEngineException;
import org.uengine.kernel.designer.*;

import org.uengine.processmanager.SimulatorTransactionContext;
import org.uengine.processmanager.ProcessTransactionContext;

import org.metaworks.*;
/**
 * @author Jinyoung Jang
 */

public class SimulatorProcessInstance extends DefaultProcessInstance{

	public SimulatorProcessInstance(ProcessDefinition procDefinition, String instanceId, Map options) throws Exception {
		super(procDefinition, instanceId, options);
	}
	
}