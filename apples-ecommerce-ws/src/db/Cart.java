package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.jws.WebService;

import Serializables.CartEntryObject;
import connection.ConnectionManager;

@WebService(endpointInterface = "interfaces.CartInt")
public class Cart implements interfaces.CartInt {

	@Override
	public boolean addCartEntry(CartEntryObject co) {
		
		boolean result = false; 
		if(this.checkExistance(co.getCartId(), co.getProductId())){

			result = this.updateProductQuantity(co.getCartId(), co.getProductId(), co.getQuantity());
		}
		else {
			result = this.addProductToCart(co.getCartId(), co.getProductId(), co.getQuantity());
		}

		return result;
	}

	/**
	 * Check whether the specified product is already present in a certain cart
	 * @param cart_id
	 * @param product_id
	 * @return
	 */
	private boolean checkExistance(int cart_id, int product_id) {		
		boolean  result = false;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT product_id FROM cart WHERE cart_id = ?");
			preparedStatement.setInt(1, cart_id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				result = true;

			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return result;
	}

	/**
	 * Updates the quantity of the specified product
	 * @param product_id
	 */
	private boolean updateProductQuantity(int cart_id,int product_id, int quantity){
	
		boolean result = false;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE cart SET quantity = quantity + ?"+
							"WHERE product_id = ? "
							+ "and cart_id = ?" );

			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, product_id);
			preparedStatement.setInt(3, cart_id);
			resultSet = preparedStatement.executeQuery();

			preparedStatement.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}

		return result;
	}



	/**
	 * Add a new product to the chart
	 * @param cart_id
	 * @param product_id
	 * @param quantity
	 * @return
	 */
	private boolean addProductToCart(int cart_id,int product_id, int quantity) {

		boolean result=false;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"INSERT INTO cart (cart_id, product_id, quantity, date" +
					"VALUES (?, ?, ?,GETDATE())");
			preparedStatement.setInt(1, cart_id);
			preparedStatement.setInt(2, product_id);
			preparedStatement.setInt(3, quantity);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				connection.commit();	
				result = true;
			}
			else {
				connection.rollback();
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return result;
	}

	@Override
	public CartEntryObject[] getCartContent(int cart_id) {
		
		CartEntryObject[] a = new CartEntryObject[1000];
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM cart WHERE cart_id = ? ");
			preparedStatement.setInt(1, cart_id);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			int index=0;
			while(resultSet.next()) {				
				CartEntryObject o = new CartEntryObject(resultSet.getInt("cart_id"),resultSet.getInt("product_id"),resultSet.getInt("quantity"),resultSet.getDate("date"));
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
	
	@Override
	public String getGUUID() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		return randomUUIDString;
	}
}