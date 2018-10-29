/**
 * UEngineProcessManagerServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.processmanager;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.uengine.kernel.ResultPayload;

public class UEngineProcessManagerServiceServiceLocator extends org.apache.axis.client.Service implements org.uengine.webservices.processmanager.UEngineProcessManagerServiceService {

    // Use to get a proxy class for uEngineProcessManagerService
    private final java.lang.String uEngineProcessManagerService_address = "http://localhost/axis/services/uEngineProcessManagerService";

    public java.lang.String getuEngineProcessManagerServiceAddress() {
        return uEngineProcessManagerService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String uEngineProcessManagerServiceWSDDServiceName = "uEngineProcessManagerService";

    public java.lang.String getuEngineProcessManagerServiceWSDDServiceName() {
        return uEngineProcessManagerServiceWSDDServiceName;
    }

    public void setuEngineProcessManagerServiceWSDDServiceName(java.lang.String name) {
        uEngineProcessManagerServiceWSDDServiceName = name;
    }

    public org.uengine.webservices.processmanager.UEngineProcessManagerService getuEngineProcessManagerService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(uEngineProcessManagerService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getuEngineProcessManagerService(endpoint);
    }

    public org.uengine.webservices.processmanager.UEngineProcessManagerService getuEngineProcessManagerService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.uengine.webservices.processmanager.UEngineProcessManagerServiceSoapBindingStub _stub = new org.uengine.webservices.processmanager.UEngineProcessManagerServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getuEngineProcessManagerServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.uengine.webservices.processmanager.UEngineProcessManagerService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.uengine.webservices.processmanager.UEngineProcessManagerServiceSoapBindingStub _stub = new org.uengine.webservices.processmanager.UEngineProcessManagerServiceSoapBindingStub(new java.net.URL(uEngineProcessManagerService_address), this);
                _stub.setPortName(getuEngineProcessManagerServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("uEngineProcessManagerService".equals(inputPortName)) {
            return getuEngineProcessManagerService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "uEngineProcessManagerServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("uEngineProcessManagerService"));
        }
        return ports.iterator();
    }

    
    public static void main(String[] args) throws ServiceException, RemoteException, MalformedURLException{
    	UEngineProcessManagerServiceServiceLocator upmsl = new UEngineProcessManagerServiceServiceLocator();
    	UEngineProcessManagerService upms = upmsl.getuEngineProcessManagerService(new java.net.URL("http://localhost:5002/axis/services/uEngineProcessManagerService"));
    	upms.completeWorkitem("1", "1", "1", new ResultPayload());
    	
    }
}
