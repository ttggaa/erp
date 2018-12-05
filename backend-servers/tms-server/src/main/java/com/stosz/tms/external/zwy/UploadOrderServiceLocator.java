/**
 * UploadOrderServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.zwy;

public class UploadOrderServiceLocator extends org.apache.axis.client.Service implements UploadOrderService {

    public UploadOrderServiceLocator() {
    }


    public UploadOrderServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UploadOrderServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UploadOrder
    private java.lang.String UploadOrder_address = "http://ceosgatewaytest.sinoair.com/services/UploadOrder";

    public java.lang.String getUploadOrderAddress() {
        return UploadOrder_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UploadOrderWSDDServiceName = "UploadOrder";

    public java.lang.String getUploadOrderWSDDServiceName() {
        return UploadOrderWSDDServiceName;
    }

    public void setUploadOrderWSDDServiceName(java.lang.String name) {
        UploadOrderWSDDServiceName = name;
    }

    public UploadOrder_PortType getUploadOrder() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UploadOrder_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUploadOrder(endpoint);
    }

    public UploadOrder_PortType getUploadOrder(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            UploadOrderSoapBindingStub _stub = new UploadOrderSoapBindingStub(portAddress, this);
            _stub.setPortName(getUploadOrderWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUploadOrderEndpointAddress(java.lang.String address) {
        UploadOrder_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (UploadOrder_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                UploadOrderSoapBindingStub _stub = new UploadOrderSoapBindingStub(new java.net.URL(UploadOrder_address), this);
                _stub.setPortName(getUploadOrderWSDDServiceName());
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
        if ("UploadOrder".equals(inputPortName)) {
            return getUploadOrder();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.iemisgateway.sinoair.com", "UploadOrderService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.iemisgateway.sinoair.com", "UploadOrder"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UploadOrder".equals(portName)) {
            setUploadOrderEndpointAddress(address);
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
