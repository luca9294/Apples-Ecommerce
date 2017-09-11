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
			result = this.addProductToCart(co.getCartId(), co.getProductId(), co.getQuantity(), co.getDate());
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
		PreparedStatement preparedStatement=null;
		Connection connection = null;
		try {
			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(
					"SELECT product_id FROM cart WHERE cart_id = ?");
			preparedStatement.setInt(1, cart_id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(resultSet.getInt("product_id")==product_id) {
					result = true;
				}
			}		
			System.out.println(result);
			preparedStatement.close();
			connection.setAutoCommit(true);			
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
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			
			if(quantity == 0) {
				preparedStatement = connection.prepareStatement("DELETE FROM cart WHERE product_id = ? ");
				preparedStatement.setInt(1, product_id);
				
				preparedStatement.executeUpdate(); 
		        connection.close();


			}
			preparedStatement = connection.prepareStatement(
					"UPDATE cart SET quantity = quantity + ?"+
							"WHERE product_id = ? "
							+ "and cart_id = ?" );

			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, product_id);
			preparedStatement.setInt(3, cart_id);
			preparedStatement.executeUpdate();

			preparedStatement.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}

		return result;
	}
    
    
	
    @Override
	/**
	 * Updates the quantity of the specified product
	 * @param product_id
	 */
	public boolean updateCart(int cart_id,int product_id, int quantity){
	
		boolean result = false;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			
			if(quantity == 0) {
				preparedStatement = connection.prepareStatement("DELETE FROM cart WHERE product_id = ? ");
				preparedStatement.setInt(1, product_id);
				
				preparedStatement.executeUpdate(); 
		        connection.close();


			}
			else{
			preparedStatement = connection.prepareStatement(
					"UPDATE cart SET quantity = ?"+
							"WHERE product_id = ? "
							+ "and cart_id = ?" );

			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, product_id);
			preparedStatement.setInt(3, cart_id);
			preparedStatement.executeUpdate();

			preparedStatement.close();}
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
	private boolean addProductToCart(int cart_id,int product_id, int quantity, String date) {

		boolean result=false;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"INSERT INTO cart (cart_id, product_id, quantity, cart_date)" +
					"VALUES (?, ?, ?, ?)");
			preparedStatement.setInt(1, cart_id);
			preparedStatement.setInt(2, product_id);
			preparedStatement.setInt(3, quantity);
			preparedStatement.setString(4, date);


			// Retrieve the result of RETURNING statement to get the current id.
			int a = preparedStatement.executeUpdate();
			System.out.println("Eseguito " + a);
			preparedStatement.close();
			
			connection.setAutoCommit(true);
			result = true;

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
				CartEntryObject o = new CartEntryObject(resultSet.getInt("cart_id"),resultSet.getInt("product_id"),resultSet.getInt("quantity"),resultSet.getString("cart_date"));
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
		int randomNum = (int) (Math.pow(10, 8) + (Math.random() * (Math.pow(10, 6)-1)));
		return (randomNum + "");
	}

	@Override
	public boolean removeCartObject(int cart_id) {
		// TODO Auto-generated method stub
		return false;
	}
}
