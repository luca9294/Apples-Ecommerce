package db;

import java.rmi.RemoteException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
import helper.KeysManagerProxy;


@WebService(endpointInterface = "interfaces.LoginServiceInt") 
public class LoginService implements interfaces.LoginServiceInt {
	private String errorString;
	private CustomerObject customer = null;
	private String[] keys;


private void getKeys() {		
	keys = new String[2];
	try{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = keyGen.generateKeyPair();
		PublicKey pub = pair.getPublic();
		PrivateKey pri = pair.getPrivate();
		byte[] bPub = pub.getEncoded();
		byte[] bPri = pri.getEncoded();
		keys[0] = Base64.encode(bPub);
		keys[1] = Base64.encode(bPri);
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();	
		}
	}

	@Override
	public boolean createNewUser(String salutation, String name,
			String surename, String country, String province,
			String city, String street,	String streetNo, String zip, int customer_id,
			String email, String pwd
			) {
		getCustomerByMail(email);
		if (customer != null){
			errorString = "There is already an user with that email! " + customer.getId();
			return false;
		}	
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
			preparedStatement.setString(12, keys[0]);
			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {				
				customer = new CustomerObject(resultSet.getInt(1), salutation,
						name, surename, country, province, city,
						street, streetNo, zip,email,pwd, keys[0]);
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

		if (customer != null){
			KeysManagerProxy kmp = new KeysManagerProxy();
			try {
				kmp.insertNewKey(customer.getId() + "", keys[1]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
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
		   String pk = "";
		   helper.KeysManagerProxy kmp = new helper.KeysManagerProxy();
		   try {
			pk = kmp.getPrivatekey(customer.getId() + "");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   String painPwd = getDecryptedString(pk, customer.getPwd());
		   String insPwd = getDecryptedString(pk, pwd);
		   if (insPwd.equals(painPwd)){
			   getKeys();
			   insertNewKey(customer.getId(), keys[0]);
			   String newPwd = getEncryptedString(painPwd, keys[0]);
			   insertNewPwd(customer.getId(), newPwd);
			   try {
				kmp.updatePrivateKey(customer.getId() + "", keys[1]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   keys = null;
			   return customer.getId();
		   }
		   
		   else{
			   customer = null;
			   return 0;
		   }
		   
		   
		}
	}

	//Get encrypted pwd using the public key given
	private String getDecryptedString(String privateKey, String inputString){
		String dencryptedStr = ""; 
		byte[] bPri   = Base64.decode(privateKey); 
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bPri);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate((keySpec));
			Cipher cipher = Cipher.getInstance("RSA"); 
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] byteArray = cipher.doFinal(Base64.decode(inputString));
			dencryptedStr = Base64.encode(byteArray);
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
		return dencryptedStr;
	}
	
	
	
	public String getEncryptedString(String toEncrpy, String pbKey){
		String result = "";
		byte[] byteKey = Base64.decode(pbKey);
	    X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
	    try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pKey = kf.generatePublic(X509publicKey);
		    Cipher cipher = Cipher.getInstance("RSA");   
		    cipher.init(Cipher.ENCRYPT_MODE, pKey);  
		    result =  Base64.encode(cipher.doFinal(Base64.decode(toEncrpy)));
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	return result;
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
	
	
	
	private boolean insertNewKey(int customerId, String publicKey)  {
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"UPDATE customer "
						+ "SET key = ?"
						+ "WHERE customer_id=?");
				preparedStatement.setString(1, publicKey);
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
	
	
	private boolean insertNewPwd(int customerId, String pwd)  {
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	    try {
				connection.setAutoCommit(false);
				preparedStatement = connection.prepareStatement(
						"UPDATE customer "
						+ "SET pwd = ?"
						+ "WHERE customer_id=?");
				preparedStatement.setString(1, pwd);
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
		getKeys();
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
}