/**
 * EMailServerSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.emailserver;

public class EMailServerSoapBindingSkeleton implements org.uengine.webservices.emailserver.EMailServer, org.apache.axis.wsdl.Skeleton {

	/**
	 * 
	 * @uml.property name="impl"
	 * @uml.associationEnd 
	 * @uml.property name="impl" multiplicity="(1 1)"
	 */
	private org.uengine.webservices.emailserver.EMailServer impl;

    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("sendMail", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://emailserver.webservices.hyunse.com", "sendMail"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("sendMail") == null) {
            _myOperations.put("sendMail", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("sendMail")).add(_oper);
    }

    public EMailServerSoapBindingSkeleton() {
        this.impl = new org.uengine.webservices.emailserver.EMailServerSoapBindingImpl();
    }

    public EMailServerSoapBindingSkeleton(org.uengine.webservices.emailserver.EMailServer impl) {
        this.impl = impl;
    }
    public void sendMail(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException
    {
        impl.sendMail(in0, in1, in2, in3);
    }

}
