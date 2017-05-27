package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionKeyDb;
import connection.ConnectionManager;

public class KeysHelper {
	private String username, password;
	

	private String getPrivatekey (String customerId) {
		String result = "";
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionKeyDb.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT customer_private_key FROM keys WHERE customer_id = ? ");
			preparedStatement.setString(1, customerId);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				result = resultSet.getString("customer_private_key");
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionKeyDb.close(connection);
		}
		return result;
	}

	public boolean insertNewKey(String customerId, String privateKey)  {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionKeyDb.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"INSERT INTO keys (customer_id, customer_private_key) "+
								"VALUES (?, ?) " );
				preparedStatement.setString(1, customerId);
				preparedStatement.setString(2, privateKey);
				resultSet = preparedStatement.executeQuery();
				preparedStatement.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    		return true;
	}
	

	public boolean updatePrivateKey(String customerId, String privateKey) throws SQLException{
		

		Connection dbConnection=null; 
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "UPDATE keys SET customer_private_key = ? "
				                  + " WHERE customer_id = ?";

		try {
			dbConnection = ConnectionKeyDb.connect();
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			preparedStatement.setString(1, (privateKey));
			preparedStatement.setString(2, customerId);

			// execute update SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is updated to cookie table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return  true;
	}

}
