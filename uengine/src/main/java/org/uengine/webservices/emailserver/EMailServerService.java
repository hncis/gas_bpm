/**
 * EMailServerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.emailserver;

public interface EMailServerService extends javax.xml.rpc.Service {

	/**
	 * 
	 * @uml.property name="eMailServerAddress"
	 */
	public java.lang.String getEMailServerAddress();

	/**
	 * 
	 * @uml.property name="eMailServer"
	 * @uml.associationEnd 
	 * @uml.property name="eMailServer" multiplicity="(0 1)"
	 */
	public org.uengine.webservices.emailserver.EMailServer getEMailServer()
		throws javax.xml.rpc.ServiceException;

    public org.uengine.webservices.emailserver.EMailServer getEMailServer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
