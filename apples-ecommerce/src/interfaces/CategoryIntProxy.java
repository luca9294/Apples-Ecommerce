package interfaces;

public class CategoryIntProxy implements interfaces.CategoryInt {
  private String _endpoint = null;
  private interfaces.CategoryInt categoryInt = null;
  
  public CategoryIntProxy() {
    _initCategoryIntProxy();
  }
  
  public CategoryIntProxy(String endpoint) {
    _endpoint = endpoint;
    _initCategoryIntProxy();
  }
  
  private void _initCategoryIntProxy() {
    try {
      categoryInt = (new db.CategoryServiceLocator()).getCategoryPort();
      if (categoryInt != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)categoryInt)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)categoryInt)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (categoryInt != null)
      ((javax.xml.rpc.Stub)categoryInt)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public interfaces.CategoryInt getCategoryInt() {
    if (categoryInt == null)
      _initCategoryIntProxy();
    return categoryInt;
  }
  
  public java.lang.String[] getCategories() throws java.rmi.RemoteException{
    if (categoryInt == null)
      _initCategoryIntProxy();
    return categoryInt.getCategories();
  }
  
  
}