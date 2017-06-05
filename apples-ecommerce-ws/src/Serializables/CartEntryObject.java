package Serializables;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CarEntryObject")
public class CartEntryObject {

	@XmlElement(name = "cart_id")
	private int cart_id;
	@XmlElement(name = "product_id")
	private int product_id;
	@XmlElement(name = "quantity")
	private int quantity;
	@XmlElement(name = "cart_date")
	private String cart_date;
	
	public CartEntryObject(){
		
	}
	public CartEntryObject(int cart_id, int product_id, int quantity, String cart_date){
		this.cart_id = cart_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.cart_date = cart_date;
	}
	
	public int getCartId(){
		return cart_id;
	}
	
	public int getProductId(){
		return product_id;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public String getDate(){
		return cart_date;
	}
	
	@Override
	public String toString() {
		return "CartObject: " + cart_id + ", product: " + product_id + ", quantity: " + quantity + ", cart_date: " + cart_date;
	}
}
