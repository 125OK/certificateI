/**
 * CrjWeixinGXServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gxcrj.www.services.WeixinService;

public class CrjWeixinGXServiceLocator extends org.apache.axis.client.Service implements com.gxcrj.www.services.WeixinService.CrjWeixinGXService {

    public CrjWeixinGXServiceLocator() {
    }


    public CrjWeixinGXServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CrjWeixinGXServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WeixinService
    private java.lang.String WeixinService_address = "http://www.gxcrj.com/services/WeixinService";

    public java.lang.String getWeixinServiceAddress() {
        return WeixinService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WeixinServiceWSDDServiceName = "WeixinService";

    public java.lang.String getWeixinServiceWSDDServiceName() {
        return WeixinServiceWSDDServiceName;
    }

    public void setWeixinServiceWSDDServiceName(java.lang.String name) {
        WeixinServiceWSDDServiceName = name;
    }

    public com.gxcrj.www.services.WeixinService.CrjWeixinGX getWeixinService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WeixinService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWeixinService(endpoint);
    }

    public com.gxcrj.www.services.WeixinService.CrjWeixinGX getWeixinService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gxcrj.www.services.WeixinService.WeixinServiceSoapBindingStub _stub = new com.gxcrj.www.services.WeixinService.WeixinServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getWeixinServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWeixinServiceEndpointAddress(java.lang.String address) {
        WeixinService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gxcrj.www.services.WeixinService.CrjWeixinGX.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gxcrj.www.services.WeixinService.WeixinServiceSoapBindingStub _stub = new com.gxcrj.www.services.WeixinService.WeixinServiceSoapBindingStub(new java.net.URL(WeixinService_address), this);
                _stub.setPortName(getWeixinServiceWSDDServiceName());
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
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WeixinService".equals(inputPortName)) {
            return getWeixinService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.gxcrj.com/services/WeixinService", "CrjWeixinGXService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.gxcrj.com/services/WeixinService", "WeixinService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WeixinService".equals(portName)) {
            setWeixinServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
