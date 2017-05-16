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
	    SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
	    keyGen.initialize(1024, random);
	    KeyPair pair = keyGen.generateKeyPair();
	    PublicKey pub = pair.getPublic();
	    byte[] b = pub.getEncoded();
	    publicKey = Base64.encode(b);
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();	
		}
		catch (NoSuchProviderException e){
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
    
    	if (existUser(email)){
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
    private boolean existUser (String email) {
    	boolean result = false;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer WHERE email = ? ");
			preparedStatement.setString(1, email);
			
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
				customer = new CustomerObject(resultSet.getInt(0), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(1),
						resultSet.getString(4), resultSet.getString(5),
						resultSet.getString(6), resultSet.getString(7),
						resultSet.getString(8), resultSet.getString(9),
						resultSet.getString(11), resultSet.getString(12),resultSet.getString(9));
				this.customer = customer;
			}

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
	
	@Override
	public boolean login(String email, String pwd) {
		if  (!existUser(email))
			return false;
			return false;
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
		
}


