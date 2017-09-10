package helper;

import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.axis.encoding.Base64;

import Serializables.CustomerObject;
import connection.ConnectionManager;

public class CustomerUtilities {

	//Get encrypted pwd using the public key given
  	public static String getDecryptedString(String privateKey, String inputString){
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
  			e.printStackTrace();
  		} catch (InvalidKeySpecException e) {
  			e.printStackTrace();
  		} catch (NoSuchPaddingException e) {
  			e.printStackTrace();
  		} catch (InvalidKeyException e) {
  			e.printStackTrace();
  		} catch (IllegalBlockSizeException e) {
  			e.printStackTrace();
  		} catch (BadPaddingException e) {
  			e.printStackTrace();
  		}
  		return dencryptedStr;
  	}
  	
  	public static String[] getKeys() {		
  		String[] keys = new String[2];
  		try{
  			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
  			KeyPair pair = keyGen.generateKeyPair();
  			PublicKey pub = pair.getPublic();
  			PrivateKey pri = pair.getPrivate();
  			byte[] bPub = pub.getEncoded();
  			byte[] bPri = pri.getEncoded();
  			keys[0] = Base64.encode(bPub);
  			keys[1] = Base64.encode(bPri);
  		} catch (NoSuchAlgorithmException e){
  			e.printStackTrace();	
  		}
  		return keys;
  	}
  	
  	public static boolean insertNewKey(int customerId, String publicKey)  {
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
				e.printStackTrace();
				return false;
			}
	    		return true;
	}
  	
  	public static String getEncryptedString(String toEncrpy, String pbKey){
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
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	    	return result;
	}
  	
  	public static boolean insertNewPwd(int customerId, String pwd)  {
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
				e.printStackTrace();
				return false;
			}
	    		return true;
	}
  	/*
  	public static CustomerObject updateCustomerKeys(CustomerObject oldCustomerObject, String encryptedPassword) {
  		CustomerObject customer = oldCustomerObject;
  		String pk = "";
		String[] keys;
		helper.KeysManagerProxy kmp = new helper.KeysManagerProxy();
		try {
			pk = kmp.getPrivatekey(String.valueOf(customer.getId()));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		String painPwd = CustomerUtilities.getDecryptedString(pk, customer.getPwd());
		String insPwd = CustomerUtilities.getDecryptedString(pk, encryptedPassword);
		if (!insPwd.isEmpty() && !painPwd.isEmpty() && insPwd.equals(painPwd)){
			keys = CustomerUtilities.getKeys();
			CustomerUtilities.insertNewKey(customer.getId(), keys[0]);
			String newPwd = CustomerUtilities.getEncryptedString(painPwd, keys[0]);
			CustomerUtilities.insertNewPwd(customer.getId(), newPwd);
			try {
				kmp.updatePrivateKey(customer.getId() + "", keys[1]);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			keys = null;
			return customer;
		} else {
			customer = null;
			return customer;
		}
		
		*/
  	
}
