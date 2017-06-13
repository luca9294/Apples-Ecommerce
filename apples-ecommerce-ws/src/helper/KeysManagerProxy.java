package helper;

public class KeysManagerProxy implements helper.KeysManager {
  private String _endpoint = null;
  private helper.KeysManager keysManager = null;
  
  public KeysManagerProxy() {
    _initKeysManagerProxy();
  }
  
  public KeysManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initKeysManagerProxy();
  }
  
  private void _initKeysManagerProxy() {
    try {
      keysManager = (new helper.KeysManagerServiceLocator()).getKeysManager();
      if (keysManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)keysManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)keysManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (keysManager != null)
      ((javax.xml.rpc.Stub)keysManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public helper.KeysManager getKeysManager() {
    if (keysManager == null)
      _initKeysManagerProxy();
    return keysManager;
  }
  
  public boolean updatePrivateKey(java.lang.String customerId, java.lang.String privateKey) throws java.rmi.RemoteException{
    if (keysManager == null)
      _initKeysManagerProxy();
    return keysManager.updatePrivateKey(customerId, privateKey);
  }
  
  public java.lang.String getPrivatekey(java.lang.String customerId) throws java.rmi.RemoteException{
    if (keysManager == null)
      _initKeysManagerProxy();
    return keysManager.getPrivatekey(customerId);
  }
  
  public boolean insertCCNewKey(java.lang.String customerId, java.lang.String privateKey) throws java.rmi.RemoteException{
    if (keysManager == null)
      _initKeysManagerProxy();
    return keysManager.insertCCNewKey(customerId, privateKey);
  }
  
  public boolean insertNewKey(java.lang.String customerId, java.lang.String privateKey) throws java.rmi.RemoteException{
    if (keysManager == null)
      _initKeysManagerProxy();
    return keysManager.insertNewKey(customerId, privateKey);
  }
  
  
}