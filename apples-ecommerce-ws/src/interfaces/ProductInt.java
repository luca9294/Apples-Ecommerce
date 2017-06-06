package interfaces;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.ProductObject;;

@WebService
public interface ProductInt{
	
	@WebMethod
	/**
	 * Add a new entry to the cart, if the product is already in the cart, updates the quantity
	 * @param co
	 * @return
	 */
	public ProductObject findProduct (int product_id);
	

}
