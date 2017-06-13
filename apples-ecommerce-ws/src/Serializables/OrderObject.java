package Serializables;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrderObject")
public class OrderObject {
	@XmlElement(name = "order_id")
	private int order_id;
	@XmlElement(name = "payment")
	private String  payment;
	@XmlElement(name = "total")
	private int total;
	@XmlElement(name = "status")
	private int status;
	@XmlElement(name = "customer_id")
	private int customer_id;
  //private OrderEntries[]

	public OrderObject() {
	}

	public OrderObject(int customer_id, int total, String payment, int status, int order_id) {
		this.customer_id = customer_id;
		this.total = total;
		this.payment = payment;
		this.status = status;
		this.order_id = order_id;
	}

	public int getCustomerId() {
		return customer_id;
	}

	public int getTotal() {
		return total;
	}

	public String getPayment() {
		return payment;
	}

	public int getOrder_id() {
		return order_id;
	}

	public int getStatus() {
		return status;
	}
}

