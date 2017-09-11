package db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import Serializables.CustomerObject;
import connection.ConnectionManager;
import helper.CustomerUtilities;
import helper.KeysManagerProxy;


@WebService(endpointInterface = "interfaces.LoginServiceInt") 
public class LoginService implements interfaces.LoginServiceInt {
	private String errorString;
	private CustomerObject customer = null;
	private String[] keys;

	@Override
	public boolean createNewUser(String firstname,
			String lastname, String email, int phoneNumber, String organization,
			String city, String address, String zip		
			) {

		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"INSERT INTO customer(firstname, lastname, city, address, zip, email, num, organization)"+
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, city);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, zip);
			preparedStatement.setString(6, email);
			preparedStatement.setString(7, phoneNumber+"");
			preparedStatement.setString(8, organization);
			// Retrieve the result of RETURNING statement to get the current id.
		
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		
		return true;
	}
	//Check whether an user with that same email exists
	private boolean existCookieID (String id) {
		boolean result = false;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM cookie WHERE cookie_id = ? ");
			preparedStatement.setString(1, id);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				result = true;
			}
			else {
				result = false;
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

	//Check whether an user with that same email exists

	@Override
	public String getError() {
		return errorString;
	}

	
	public String getEncryptedString(String toEncrpy, String pbKey){
		return CustomerUtilities.getEncryptedString(toEncrpy, pbKey);
	}
	
	

	//It returns a new token
	@Override
	public String getCookieToken(){

		int randomNum = (int) (Math.pow(10, 8) + (Math.random() * (Math.pow(10, 6)-1)));
		return (randomNum + "");
	}

	@Override
	public int loginCookie(String cookieId)  {
        int id = 0;
		if (!this.existCookieID(cookieId)) {
			id =  -1;
		}
		try {
			id = getCustomerIdFromToken(cookieId);
		} catch (SQLException e) {
			return 0;
		}
		return id;
	}
	
	@Override
	public String updateCookieToken(int customerId, String cookieId) throws SQLException{
		
		int randomNum = (int) (Math.pow(10, 8) + (Math.random() * (Math.pow(10, 6)-1)));

		Connection dbConnection=null; 
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "UPDATE cookie SET cookie_id = ? "
				                  + " WHERE customer_id = ? AND cookie_id = ?";

		try {
			dbConnection = ConnectionManager.connect();
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			preparedStatement.setString(1, (randomNum + ""));
			preparedStatement.setInt(2, customerId);
			preparedStatement.setString(3, cookieId);

			// execute update SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is updated to cookie table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return randomNum + "";
	}
	
	public int getCustomerIdFromToken(String cookieId) throws SQLException {
		ResultSet resultSet;
		PreparedStatement preparedStatement=null;
		Connection connection = null;
		int customerId = -1;
		try {
			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT customer_id FROM cookie WHERE cookie_id = ?");
			preparedStatement.setString(1, cookieId);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return 0;
			
			customerId = resultSet.getInt(1);
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			ConnectionManager.close(connection);
		}		
		return customerId;		
	}

	//Inserts the cookie in the database and links it direct with the db
	@Override
	public boolean insertNewToken(int customerId, String token)  {
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"INSERT INTO cookie (cookie_id,customer_id) "+
								"VALUES (?, ?) " );
				preparedStatement.setString(1, token);
				preparedStatement.setInt(2, customerId);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    		return true;
	}
	
	
	@Override
	public boolean updateToken(int customerId, String token) {
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"UPDATE cookie SET cookie_id = ? "+
								"WHERE customer_id = ?" );
				preparedStatement.setInt(2, customerId);
				preparedStatement.setString(1, token);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    		return true;
	}

	@Override
	public String getPublicKey() {
		keys = CustomerUtilities.getKeys();
		return keys[0];
	}

	@Override
	public String getPublicKeyFromEmail(String email) {
		ResultSet resultSet;
		String pKey = "";
		PreparedStatement preparedStatement=null;
		Connection connection = null;
		try {
			connection = ConnectionManager.connect();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer WHERE email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				pKey = resultSet.getString("key");
				preparedStatement.close();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			ConnectionManager.close(connection);
		}		
		return pKey;		
	}

	@Override
	public boolean logout(String cookie_id) {
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"DELETE FROM cookie  "+
								"WHERE cookie_id = ?" );
				preparedStatement.setString(1, cookie_id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    		return true;
	}
	@Override
	public int login(String email, String pwd) {
		// TODO Auto-generated method stub
		return 0;
	}
}