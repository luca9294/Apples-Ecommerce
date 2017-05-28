/**
 * KeysManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package helper;

public class KeysManagerServiceLocator extends org.apache.axis.client.Service implements helper.KeysManagerService {

    public KeysManagerServiceLocator() {
    }


    public KeysManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public KeysManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for KeysManager
    private java.lang.String KeysManager_address = "http://54.201.0.83:8080/apples-ecommerce-keystore/services/KeysManager";

    public java.lang.String getKeysManagerAddress() {
        return KeysManager_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String KeysManagerWSDDServiceName = "KeysManager";

    public java.lang.String getKeysManagerWSDDServiceName() {
        return KeysManagerWSDDServiceName;
    }

    public void setKeysManagerWSDDServiceName(java.lang.String name) {
        KeysManagerWSDDServiceName = name;
    }

    public helper.KeysManager getKeysManager() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(KeysManager_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getKeysManager(endpoint);
    }

    public helper.KeysManager getKeysManager(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            helper.KeysManagerSoapBindingStub _stub = new helper.KeysManagerSoapBindingStub(portAddress, this);
            _stub.setPortName(getKeysManagerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setKeysManagerEndpointAddress(java.lang.String address) {
        KeysManager_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (helper.KeysManager.class.isAssignableFrom(serviceEndpointInterface)) {
                helper.KeysManagerSoapBindingStub _stub = new helper.KeysManagerSoapBindingStub(new java.net.URL(KeysManager_address), this);
                _stub.setPortName(getKeysManagerWSDDServiceName());
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
        if ("KeysManager".equals(inputPortName)) {
            return getKeysManager();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://helper", "KeysManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://helper", "KeysManager"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("KeysManager".equals(portName)) {
            setKeysManagerEndpointAddress(address);
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
