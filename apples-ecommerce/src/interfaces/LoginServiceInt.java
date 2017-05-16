/**
 * LoginServiceInt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package interfaces;

public interface LoginServiceInt extends java.rmi.Remote {
    public boolean createNewUser(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6, java.lang.String arg7, java.lang.String arg8, int arg9, java.lang.String arg10, java.lang.String arg11) throws java.rmi.RemoteException;
    public boolean login(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String getPublicKey() throws java.rmi.RemoteException;
    public java.lang.String getError() throws java.rmi.RemoteException;
}
