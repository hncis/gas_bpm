/**
 * UEngineProcessManagerServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.processmanager.impl;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

public class UEngineProcessManagerServiceImpl implements org.uengine.webservices.processmanager.UEngineProcessManagerService{
	
	ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
	
    public java.lang.String initializeProcess(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			String instanceId = pm.initializeProcess(in0, in1);
			pm.applyChanges();

			return instanceId;
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public void executeProcess(java.lang.String in0) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			pm.executeProcess(in0);
			pm.applyChanges();
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public void executeProcessByWorkitem(java.lang.String in0, org.uengine.kernel.ResultPayload in1) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			pm.executeProcessByWorkitem(in0, in1);
			pm.applyChanges();
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public String intializeAndExecuteProcessByWorkitem(java.lang.String in0, org.uengine.kernel.ResultPayload in1) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
        	String instanceId = pm.initializeProcess(in0);
			pm.executeProcessByWorkitem(instanceId, in1);
			pm.applyChanges();
			
			return instanceId;
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public void setProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.Object in3) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			pm.setProcessVariable(in0, in1, in2, (Serializable)in3);
			pm.applyChanges();
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public java.lang.Object getProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			Object result = pm.getProcessVariable(in0, in1, in2);
			pm.applyChanges();
			
			return result;
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
    }

    public void putRoleMapping(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException {
    }

    public java.lang.String getRoleMapping(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
        return null;
    }

    public void completeWorkitem(java.lang.String in0, java.lang.String in1, java.lang.String in2, org.uengine.kernel.ResultPayload in3) throws java.rmi.RemoteException {
        try {
        	ProcessManagerRemote pm = processManagerFactory.getProcessManager();
			pm.completeWorkitem(in0, in1, in2, in3);
			pm.applyChanges();
		} catch (Exception e) {
			throw new RemoteException("WebServiceRemote:", e);
		}
   	
    }

}
