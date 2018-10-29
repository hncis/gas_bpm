/**
 * EMailServerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.emailserver;

public class EMailServerServiceLocator extends org.apache.axis.client.Service implements org.uengine.webservices.emailserver.EMailServerService {

    // Use to get a proxy class for EMailServer
    private final java.lang.String EMailServer_address = "http://localhost:8086/axis/services/EMailServer";

    public java.lang.String getEMailServerAddress() {
        return EMailServer_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EMailServerWSDDServiceName = "EMailServer";

	/**
	 * 
	 * @uml.property name="eMailServerWSDDServiceName"
	 */
	public java.lang.String getEMailServerWSDDServiceName() {
		return EMailServerWSDDServiceName;
	}

	/**
	 * 
	 * @uml.property name="eMailServerWSDDServiceName"
	 */
	public void setEMailServerWSDDServiceName(java.lang.String name) {
		EMailServerWSDDServiceName = name;
	}


    public org.uengine.webservices.emailserver.EMailServer getEMailServer() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EMailServer_address);
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getEMailServer(endpoint);
    }

    public org.uengine.webservices.emailserver.EMailServer getEMailServer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.uengine.webservices.emailserver.EMailServerSoapBindingStub _stub = new org.uengine.webservices.emailserver.EMailServerSoapBindingStub(portAddress, this);
            _stub.setPortName(getEMailServerWSDDServiceName());
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
            if (org.uengine.webservices.emailserver.EMailServer.class.isAssignableFrom(serviceEndpointInterface)) {
                org.uengine.webservices.emailserver.EMailServerSoapBindingStub _stub = new org.uengine.webservices.emailserver.EMailServerSoapBindingStub(new java.net.URL(EMailServer_address), this);
                _stub.setPortName(getEMailServerWSDDServiceName());
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
        if ("EMailServer".equals(inputPortName)) {
            return getEMailServer();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://emailserver.webservices.hyunse.com", "EMailServerService");
    }

	/**
	 * 
	 * @uml.property name="ports"
	 * @uml.associationEnd 
	 * @uml.property name="ports" multiplicity="(0 -1)" elementType="javax.xml.namespace.QName"
	 */
	private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("EMailServer"));
        }
        return ports.iterator();
    }

}
