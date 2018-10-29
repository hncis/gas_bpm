/**
 * UEngineProcessManagerServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.processmanager;

public class UEngineProcessManagerServiceSoapBindingImpl implements org.uengine.webservices.processmanager.UEngineProcessManagerService{
    public java.lang.String initializeProcess(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
    	System.out.println("wow!");
    	return "123";
    }

    public void executeProcess(java.lang.String in0) throws java.rmi.RemoteException {
    }

    public void executeProcessByWorkitem(java.lang.String in0, org.uengine.kernel.ResultPayload in1) throws java.rmi.RemoteException {
    }

    public void setProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.Object in3) throws java.rmi.RemoteException {
    }

    public java.lang.Object getProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException {
        return null;
    }

    public void putRoleMapping(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException {
    }

    public java.lang.String getRoleMapping(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
        return null;
    }

    public void completeWorkitem(java.lang.String in0, java.lang.String in1, java.lang.String in2, org.uengine.kernel.ResultPayload in3) throws java.rmi.RemoteException {
    }

}
