package db;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import org.apache.axis.encoding.Base64;
import Serializables.CustomerObject;
import connection.ConnectionManager;


@WebService(endpointInterface = "interfaces.LoginServiceInt") 
public class LoginService implements interfaces.LoginServiceInt {
	private String errorString;
	private CustomerObject customer = null;

	@Override
	public String getPublicKey() {		
		String publicKey = "";
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			KeyPair pair = keyGen.generateKeyPair();
			PublicKey pub = pair.getPublic();
			byte[] b = pub.getEncoded();
			publicKey = Base64.encode(b);
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();	
		}
		return publicKey;
	}

	@Override
	public boolean createNewUser(String salutation, String name,
			String surename, String country, String province,
			String city, String street,	String streetNo, String zip, int customer_id,
			String email, String pwd
			) {
		getCustomerByMail(email);

		if (customer != null){
			errorString = "There is already an user with that email!";
			return false;
		}	
		LoginService ls = new LoginService();
		String pub = ls.getPublicKey();
		byte[] publicBytes = Base64.decode(pub);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			Cipher cipher = Cipher.getInstance("RSA");   
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);  
			byte[] byteArray = cipher.doFinal(pwd.getBytes());
			pwd = Base64.encode(byteArray);

		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"INSERT INTO customer (salutation, firstname, lastname, " +
							"country, province, city, street, " +
							"street_no, zip, email, pwd, key) " +
							"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
					"RETURNING customer_id");
			preparedStatement.setString(1, salutation);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, surename);
			preparedStatement.setString(4, country);
			preparedStatement.setString(5, province);
			preparedStatement.setString(6, city);
			preparedStatement.setString(7, street);
			preparedStatement.setString(8, streetNo);
			preparedStatement.setString(9, zip);
			preparedStatement.setString(10, email);
			preparedStatement.setString(11, pwd);
			preparedStatement.setString(12, pub);
			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				customer = new CustomerObject(resultSet.getInt(1), salutation,
						name, surename, country, province, city,
						street, streetNo, zip,email,pwd, pub);
				connection.commit();					
			}
			else {
				connection.rollback();
				return false;
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}

		if (customer != null)
			return true;
		else
			return false;
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
	private  void getCustomerByMail (String email) {
		CustomerObject customer;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer WHERE email = ?");
			preparedStatement.setString(1, email);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				customer = new CustomerObject(resultSet.getInt(1), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(2),
						resultSet.getString(5), resultSet.getString(6),
						resultSet.getString(7), resultSet.getString(8),
						resultSet.getString(9), resultSet.getString(10),
						resultSet.getString(12), resultSet.getString(13),resultSet.getString(10));
				this.customer = customer;
			}
			else
				customer = null;

			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
	}

	@Override
	public String getError() {
		return errorString;
	}

	//Returns -1 user does not exist. 0 pwd is wrong
	@Override
	public int login(String email, String pwd) {
		getCustomerByMail(email);
		//user does not exists
		if  (customer == null)
			return -1;
		else 
		{
			if (customer.isPwdMatching(getEncryptedString(customer.getKey(),pwd)))
				return customer.getId();
			else
				return 0;
		}
	}

	//Get encrypted pwd using the public key given
	private String getEncryptedString(String publickey, String inputString){
		String encryptedStr = ""; 
		byte[] b = Base64.decode(publickey); 
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			Cipher cipher = Cipher.getInstance("RSA"); 
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] byteArray = cipher.doFinal(inputString.getBytes());
			encryptedStr = Base64.encode(byteArray);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedStr;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public String updateCookieToken(int customerId) throws SQLException{
		
		int randomNum = (int) (Math.pow(10, 8) + (Math.random() * (Math.pow(10, 6)-1)));

		Connection dbConnection=null; 
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "UPDATE cookie SET cookie_id = ? "
				                  + " WHERE customer_id = ?";

		try {
			dbConnection = ConnectionManager.connect();
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			preparedStatement.setString(1, (randomNum + ""));
			preparedStatement.setInt(2, customerId);

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
			resultSet.next();
			customerId = resultSet.getInt(1);
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}		
		return customerId;		
	}

	//Inserts the cookie in the database and links it direct with the db
	@Override
	public boolean insertNewToken(int customerId, String token)  {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"INSERT INTO cookie (cookie_id, customer_id) "+
								"VALUES (?, ?) " );
				preparedStatement.setInt(1, customerId);
				preparedStatement.setString(2, token);
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

	@Override
	public boolean updateToken(int customerId, String token) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"UPDATE cookie SET cookie_id = ? "+
								"WHERE customer_id = ?" );
				preparedStatement.setInt(2, customerId);
				preparedStatement.setString(1, token);
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

}

