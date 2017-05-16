package interfaces;

public class LoginServiceIntProxy implements interfaces.LoginServiceInt {
  private String _endpoint = null;
  private interfaces.LoginServiceInt loginServiceInt = null;
  
  public LoginServiceIntProxy() {
    _initLoginServiceIntProxy();
  }
  
  public LoginServiceIntProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoginServiceIntProxy();
  }
  
  private void _initLoginServiceIntProxy() {
    try {
      loginServiceInt = (new db.LoginServiceServiceLocator()).getLoginServicePort();
      if (loginServiceInt != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loginServiceInt)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loginServiceInt)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loginServiceInt != null)
      ((javax.xml.rpc.Stub)loginServiceInt)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public interfaces.LoginServiceInt getLoginServiceInt() {
    if (loginServiceInt == null)
      _initLoginServiceIntProxy();
    return loginServiceInt;
  }
  
  public boolean createNewUser(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6, java.lang.String arg7, java.lang.String arg8, int arg9, java.lang.String arg10, java.lang.String arg11) throws java.rmi.RemoteException{
    if (loginServiceInt == null)
      _initLoginServiceIntProxy();
    return loginServiceInt.createNewUser(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
  }
  
  public boolean login(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (loginServiceInt == null)
      _initLoginServiceIntProxy();
    return loginServiceInt.login(arg0, arg1);
  }
  
  public java.lang.String getPublicKey() throws java.rmi.RemoteException{
    if (loginServiceInt == null)
      _initLoginServiceIntProxy();
    return loginServiceInt.getPublicKey();
  }
  
  public java.lang.String getError() throws java.rmi.RemoteException{
    if (loginServiceInt == null)
      _initLoginServiceIntProxy();
    return loginServiceInt.getError();
  }
  
  
}