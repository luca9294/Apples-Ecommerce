package interfaces;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface OrderInt {
	
	@WebMethod
	/**
	 * Add a news Order
	 * @param cart_id, customer_id
	 * @return
	 */
	public boolean addOrder (int cart_id, int customer_id);

	@WebMethod
	/**
	 * Change the current status of the order
	 * @param cart_id, newStatus
	 * @return
	 */
	public boolean changeOrderStatus (int order_id, int newStatus);
	
	 @WebMethod
	/**
	 * Delete Order
	 * @param order_id
	 * @return
	 */
	public boolean deleteOrder(int order_id);
	
		 @WebMethod
	/**
	 * Return Orders current customer
	 * @param cart_id
	 * @return
	 */
	public OrderObject[] getOrders(int customer_id);
	
		@WebMethod
	/**
	 * Return a new GUUID
	 * @return
	 */
	public String getGUUID();
	
	
	
	
	
}
