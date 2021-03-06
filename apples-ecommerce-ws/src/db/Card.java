package db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebService;

import Serializables.CardObject;
import connection.ConnectionManager;
import helper.CustomerUtilities;
import helper.KeysManagerProxy;
import interfaces.CardInt;

@WebService(endpointInterface = "interfaces.CardInt") 
public class Card implements CardInt {
	private String[] keys;

	@Override
	public boolean AddCard(CardObject co) {
		boolean result=false;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"INSERT INTO card (cname, key, number, vvc,month,year,email,last)" +
					"VALUES (?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, co.getcName());
			preparedStatement.setString(2, co.getKey());
			preparedStatement.setString(3, co.getNumber());
			preparedStatement.setString(4, co.getVvc());
			preparedStatement.setInt(5, co.getExpMonth());
			preparedStatement.setInt(6, co.getExpYear());
			preparedStatement.setString(7, co.getEmail());
			preparedStatement.setString(8, co.getLastChats()+"");
			
			// Retrieve the result of RETURNING statement to get the current id.
			preparedStatement.executeUpdate();
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
	public CardObject[] getCards(int customer_id) {
		/*CardObject[] result = new CardObject[1000];
		PreparedStatement preparedStatement;
		ResultSet rs;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM card WHERE customer_id=?");

			preparedStatement.setInt(1, customer_id);
			//Retrieve the result of RETURNING statement to get the current id.
			rs = preparedStatement.executeQuery();
	
			int count = 0;
			while (rs.next()){
				CardObject co = new CardObject(customer_id,Integer.parseInt(rs.getString("month")), Integer.parseInt(rs.getString("year")),rs.getString("key"), 
						rs.getString("number"), rs.getString("vvc"),rs.getString("cname") ,Integer.parseInt( rs.getString("last")));
			   result[count] = co;
			   count++;
			}

			connection.setAutoCommit(true);
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}

	 return result;*/
		return null;

	}

	@Override
	public String getPublicKey() {
		keys = CustomerUtilities.getKeys();
		return keys[0];
	}


}
