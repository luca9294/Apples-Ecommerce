package Serializables;

import java.util.*;
import Serializables.CartEntryObject;
public class CartObject {
	
	//Static instance to implement singleton
	private static CartObject myCart = null;
	
	private int customer_id, cart_id;
	private ArrayList<CartEntryObject> a;
	
	private CartObject() {		
		this.a = new ArrayList<CartEntryObject>();
	}

	public static  CartObject getCartInstance () {
		if (myCart == null)  {
			myCart = new CartObject();
		}
		
		return myCart;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}	
}
