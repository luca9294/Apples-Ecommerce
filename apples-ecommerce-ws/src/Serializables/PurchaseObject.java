package Serializables;

import java.io.Serializable;
import java.sql.Date;

public class PurchaseObject implements Serializable {

	private static final long serialVersionUID = -5577579081118070434L;

	private int purchase_id;
	private int customer_id;
	private int product_id;
	private int  total;
	private Date purchase_date;
	private int purchase_status;
	
		
	public PurchaseObject(int purchase_id,int customer_id,int product_id,int total,
			Date purchase_date, int purchase_status){
		this.purchase_id = purchase_id;
		this.customer_id = customer_id;
		this.product_id = product_id;
		this.total = total;
		this.purchase_status = purchase_status;
		this.purchase_date = purchase_date;

	}
	
	public int getId() {
		return purchase_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public int getTotal() {
		return total;
	}

	public Date getPurchase_date() {
		return purchase_date;
	}

	public int getPurchaseStatus() {
		return purchase_status;
	}

	
}