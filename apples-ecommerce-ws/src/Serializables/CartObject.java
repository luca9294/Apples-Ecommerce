package Serializables;

import java.sql.Date;

public class CartObject {

	private int cart_id;
	private int product_id;
	private int total;
	private Date cart_date;
	
	public CartObject(int cart_id, int product_id, int total, Date cart_date){
		this.cart_id = cart_id;
		this.product_id = product_id;
		this.total = total;
		this.cart_date = cart_date;
	}
	
	public int getCartId(){
		return cart_id;
	}
	
	public int getProductId(){
		return product_id;
	}
	
	public int getTotalId(){
		return total;
	}
	
	public Date cart_date(){
		return cart_date;
	}
}
