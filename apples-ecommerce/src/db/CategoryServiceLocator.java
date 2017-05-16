/**
 * CategoryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package db;

public class CategoryServiceLocator extends org.apache.axis.client.Service implements db.CategoryService {

    public CategoryServiceLocator() {
    }


    public CategoryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CategoryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CategoryPort
    private java.lang.String CategoryPort_address = "http://54.202.224.165:8080/apples-ecommerce-ws/Category";

    public java.lang.String getCategoryPortAddress() {
        return CategoryPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CategoryPortWSDDServiceName = "CategoryPort";

    public java.lang.String getCategoryPortWSDDServiceName() {
        return CategoryPortWSDDServiceName;
    }

    public void setCategoryPortWSDDServiceName(java.lang.String name) {
        CategoryPortWSDDServiceName = name;
    }

    public interfaces.CategoryInt getCategoryPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CategoryPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCategoryPort(endpoint);
    }

    public interfaces.CategoryInt getCategoryPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            db.CategoryServiceSoapBindingStub _stub = new db.CategoryServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCategoryPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCategoryPortEndpointAddress(java.lang.String address) {
        CategoryPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (interfaces.CategoryInt.class.isAssignableFrom(serviceEndpointInterface)) {
                db.CategoryServiceSoapBindingStub _stub = new db.CategoryServiceSoapBindingStub(new java.net.URL(CategoryPort_address), this);
                _stub.setPortName(getCategoryPortWSDDServiceName());
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
        if ("CategoryPort".equals(inputPortName)) {
            return getCategoryPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://db/", "CategoryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://db/", "CategoryPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CategoryPort".equals(portName)) {
            setCategoryPortEndpointAddress(address);
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
