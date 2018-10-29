/**
 * UEngineProcessManagerServiceSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.uengine.webservices.processmanager;

public class UEngineProcessManagerServiceSoapBindingSkeleton implements org.uengine.webservices.processmanager.UEngineProcessManagerService, org.apache.axis.wsdl.Skeleton {
    private org.uengine.webservices.processmanager.UEngineProcessManagerService impl;
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
        };
        _oper = new org.apache.axis.description.OperationDesc("initializeProcess", _params, new javax.xml.namespace.QName("", "initializeProcessReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "initializeProcess"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("initializeProcess") == null) {
            _myOperations.put("initializeProcess", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("initializeProcess")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("executeProcess", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "executeProcess"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("executeProcess") == null) {
            _myOperations.put("executeProcess", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("executeProcess")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://kernel.uengine.org", "ResultPayload"), org.uengine.kernel.ResultPayload.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("executeProcessByWorkitem", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "executeProcessByWorkitem"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("executeProcessByWorkitem") == null) {
            _myOperations.put("executeProcessByWorkitem", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("executeProcessByWorkitem")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"), java.lang.Object.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setProcessVariable", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "setProcessVariable"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setProcessVariable") == null) {
            _myOperations.put("setProcessVariable", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setProcessVariable")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getProcessVariable", _params, new javax.xml.namespace.QName("", "getProcessVariableReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "getProcessVariable"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getProcessVariable") == null) {
            _myOperations.put("getProcessVariable", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getProcessVariable")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("putRoleMapping", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "putRoleMapping"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("putRoleMapping") == null) {
            _myOperations.put("putRoleMapping", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("putRoleMapping")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getRoleMapping", _params, new javax.xml.namespace.QName("", "getRoleMappingReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "getRoleMapping"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getRoleMapping") == null) {
            _myOperations.put("getRoleMapping", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getRoleMapping")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://kernel.uengine.org", "ResultPayload"), org.uengine.kernel.ResultPayload.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("completeWorkitem", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://processmanager.webservices.uengine.org", "completeWorkitem"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("completeWorkitem") == null) {
            _myOperations.put("completeWorkitem", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("completeWorkitem")).add(_oper);
    }

    public UEngineProcessManagerServiceSoapBindingSkeleton() {
        this.impl = new org.uengine.webservices.processmanager.UEngineProcessManagerServiceSoapBindingImpl();
    }

    public UEngineProcessManagerServiceSoapBindingSkeleton(org.uengine.webservices.processmanager.UEngineProcessManagerService impl) {
        this.impl = impl;
    }
    public java.lang.String initializeProcess(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.initializeProcess(in0, in1);
        return ret;
    }

    public void executeProcess(java.lang.String in0) throws java.rmi.RemoteException
    {
        impl.executeProcess(in0);
    }

    public void executeProcessByWorkitem(java.lang.String in0, org.uengine.kernel.ResultPayload in1) throws java.rmi.RemoteException
    {
        impl.executeProcessByWorkitem(in0, in1);
    }

    public void setProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.Object in3) throws java.rmi.RemoteException
    {
        impl.setProcessVariable(in0, in1, in2, in3);
    }

    public java.lang.Object getProcessVariable(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException
    {
        java.lang.Object ret = impl.getProcessVariable(in0, in1, in2);
        return ret;
    }

    public void putRoleMapping(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException
    {
        impl.putRoleMapping(in0, in1, in2);
    }

    public java.lang.String getRoleMapping(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getRoleMapping(in0, in1);
        return ret;
    }

    public void completeWorkitem(java.lang.String in0, java.lang.String in1, java.lang.String in2, org.uengine.kernel.ResultPayload in3) throws java.rmi.RemoteException
    {
        impl.completeWorkitem(in0, in1, in2, in3);
    }

}
