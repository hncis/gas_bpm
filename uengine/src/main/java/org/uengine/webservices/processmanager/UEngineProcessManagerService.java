/**
 * UEngineProcessManagerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.processmanager;

public interface UEngineProcessManagerService extends java.rmi.Remote {
    public java.lang.String initializeProcess(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void executeProcess(java.lang.String in0) throws java.rmi.RemoteException;
    public void executeProcessByWorkitem(java.lang.String in0, org.uengine.kernel.ResultPayload in1) throws java.rmi.RemoteException;
    public void setProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.Object in3) throws java.rmi.RemoteException;
    public java.lang.Object getProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void putRoleMapping(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.String getRoleMapping(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void completeWorkitem(java.lang.String in0, java.lang.String in1, java.lang.String in2, org.uengine.kernel.ResultPayload in3) throws java.rmi.RemoteException;
}
