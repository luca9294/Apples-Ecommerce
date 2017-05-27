package interfaces;

import java.util.LinkedList;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.ProductObject;

@WebService
public interface CategoryInt {
	@WebMethod
	public String[] getCategories();
	
	@WebMethod
	public ProductObject[] getProducts(int cat_id);
}
