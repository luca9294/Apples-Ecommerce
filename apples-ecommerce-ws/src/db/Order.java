package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.jws.WebService;

import Serializables.OrderObject;
import connection.ConnectionManager;

@WebService(endpointInterface = "interfaces.OrderInt")
public class Order implements interfaces.OrderInt {
  
	/**
	 * Cut Cart values to Order Table
	 * @param cart_id
	 * @param product_id
	 * @return
	 */
	@Override
  public boolean addOrder (int order_id, int cart_id, int customer_id, int lastChars) {
		ResultSet resultSet;
		PreparedStatement preparedStatement=null;
		Connection connection = null;
    try {
			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
		  "INSERT order (order_id,customer_id, payment_id, product_id, quantity, status)"
		  + " SELECT ?, ?, ?, product_id, quantity, ?"
		  + "FROM cart WHERE cart_id = ?");
		    preparedStatement.setInt(1, order_id);
			preparedStatement.setInt(2, customer_id);
			preparedStatement.setInt(3, lastChars);
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, cart_id);
			preparedStatement.executeUpdate();
			connection.close();

			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
		    "DELETE FROM order WHERE cart_id = ?");
			preparedStatement.setInt(1, cart_id);
			preparedStatement.executeUpdate();
			connection.close();
			}
		catch (SQLException e) {
			  e.printStackTrace();
			  return false;
		} finally {
			ConnectionManager.close(connection);
		}
    return true;
	}
	
		/**
	 * Delets Order
	 * @param cart_id
	 * @param product_id
	 * @return
	 */
	@Override
	public boolean deleteOrder(int order_id){
	  ResultSet resultSet;
		PreparedStatement preparedStatement=null;
		Connection connection = null;
    try {
			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("DELETE FROM order WHERE order_id = ?");
			preparedStatement.setInt(1, order_id);
			preparedStatement.executeUpdate();
			connection.close();
			}
		catch (SQLException e) {
			  e.printStackTrace();
			  return false;
		} finally {
			ConnectionManager.close(connection);
		}
    return true;
	}
	
			/**
	 * Get Orders
	 * @param cart_id
	 * @param product_id
	 * @return
	 */
	@Override
	public  OrderObject[] getOrders(int customer_id){
    OrderObject[] a = new OrderObject[1000];
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT order_id, payment_id, status, FROM order WHERE customer_id = ?  GROUP BY order_id");
			preparedStatement.setInt(1, customer_id);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			int index=0;
			while(resultSet.next()) {				
				OrderObject o = new OrderObject(resultSet.getInt("customer_id"),getTotal(resultSet.getInt("order_id")),resultSet.getInt("payment_id")+"",resultSet.getInt("status"),resultSet.getInt("order_id"));				
				a[index]=o;
				index++;
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}

		return a;
	}
	
	 /**
	 * Get Total
	 * @param order_id
	 * @return
	 */
	private int getTotal(int order_id){
	    int total = 0;
	    ResultSet resultSet;
		  PreparedStatement preparedStatement;
		  Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
			"SELECT * FROM order WHERE order_id = ? " +
			"JOIN  products ON order.product_id = products.product_id");
			preparedStatement.setInt(1, order_id);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {				
			  int quantity = resultSet.getInt("quantity"); 
			  int price   =   resultSet.getInt("price");
			  int mult = quantity * price;
			  total += mult;
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		  return total;
		}
	  
	
	@Override
	public String getGUUID() {
		int randomNum = (int) (Math.pow(10, 8) + (Math.random() * (Math.pow(10, 6)-1)));
		return (randomNum + "");
	}

	@Override
	public boolean changeOrderStatus(int order_id, int newStatus) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
