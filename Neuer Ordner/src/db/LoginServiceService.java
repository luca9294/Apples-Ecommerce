/**
 * LoginServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package db;

public interface LoginServiceService extends javax.xml.rpc.Service {
    public java.lang.String getLoginServicePortAddress();

    public interfaces.LoginServiceInt getLoginServicePort() throws javax.xml.rpc.ServiceException;

    public interfaces.LoginServiceInt getLoginServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
