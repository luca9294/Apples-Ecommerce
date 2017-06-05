package interfaces;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.CartEntryObject;

@WebService
public interface CartInt {
	
	@WebMethod
	/**
	 * Add a new entry to the cart, if the product is already in the cart, updates the quantity
	 * @param co
	 * @return
	 */
	public boolean addCartEntry (CartEntryObject co);
//	public boolean removeCartObject ()
	
	@WebMethod
	/**
	 * Returns the content of the specified cart
	 * @param cart_id
	 * @return
	 */
	public CartEntryObject[] getCartContent(int cart_id);
	
	@WebMethod
	/**
	 * Return a new GUUID
	 * @return
	 */
	public String getGUUID();
}
