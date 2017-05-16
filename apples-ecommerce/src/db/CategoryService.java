/**
 * CategoryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package db;

public interface CategoryService extends javax.xml.rpc.Service {
    public java.lang.String getCategoryPortAddress();

    public interfaces.CategoryInt getCategoryPort() throws javax.xml.rpc.ServiceException;

    public interfaces.CategoryInt getCategoryPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
