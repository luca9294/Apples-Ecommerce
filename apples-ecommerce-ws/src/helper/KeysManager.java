/**
 * KeysManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package helper;

public interface KeysManager extends java.rmi.Remote {
    public java.lang.String getPrivatekey(java.lang.String customerId) throws java.rmi.RemoteException;
    public boolean updatePrivateKey(java.lang.String customerId, java.lang.String privateKey) throws java.rmi.RemoteException;
    public boolean insertNewKey(java.lang.String customerId, java.lang.String privateKey) throws java.rmi.RemoteException;
}
