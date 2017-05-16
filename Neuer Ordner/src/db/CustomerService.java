/**
 * CustomerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package db;

public interface CustomerService extends javax.xml.rpc.Service {
    public java.lang.String getCustomerPortAddress();

    public interfaces.CustomerInt getCustomerPort() throws javax.xml.rpc.ServiceException;

    public interfaces.CustomerInt getCustomerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
