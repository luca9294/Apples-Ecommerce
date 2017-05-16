/**
 * CustomerInt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package interfaces;

public interface CustomerInt extends java.rmi.Remote {
    public interfaces.CustomerObject find(int arg0) throws java.rmi.RemoteException;
    public boolean delete() throws java.rmi.RemoteException;
    public interfaces.CustomerObject modify(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6, java.lang.String arg7, java.lang.String arg8, java.lang.String arg9, java.lang.String arg10) throws java.rmi.RemoteException;
}
