package db;

import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.ArrayList;
import org.apache.axis.encoding.Base64;

import Serializables.CustomerObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;

import connection.ConnectionManager;
import helper.CustomerUtilities;
import interfaces.CustomerInt;


@WebService(endpointInterface = "interfaces.CustomerInt")  
public class Customer implements CustomerInt {
	 
	private CustomerObject currentCustomer;
	
	public Customer(){};

    @Override
	public CustomerObject modify(String salutation, String name,
			String surname, String country, String province,
			String city, String street,	String streetNo, String zip, String email, String pwd){
		CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE customer SET salutation = ? , firstname = ? , lastname = ? , " +
							"country = ? , province = ? , city = ?, email = ? , " +
							"\"streetNo\" = ? , zip = ?, email = ? , pwd = ?"+
							"WHERE address_id = "+this.currentCustomer.getId()+
							"RETURNING customer_id;");
			preparedStatement.setString(1, salutation);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, surname);
			preparedStatement.setString(4, country);
			preparedStatement.setString(5, province);
			preparedStatement.setString(6, city);
			preparedStatement.setString(7, street);
			preparedStatement.setString(8, streetNo);
			preparedStatement.setString(9, zip);
			preparedStatement.setString(10, email);
			preparedStatement.setString(11, pwd);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customer = new CustomerObject(resultSet.getInt(1),salutation,name,surname,country,province,
						city,street,street,zip,email,pwd,resultSet.getString(10));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return customer;
	}	
	
    @Override
	public boolean delete(){
		boolean success = false;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE customer_id = "+this.currentCustomer.getId()+";");		
			if(preparedStatement.executeUpdate() > 0){
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}		
		return success;
	}


    @WebResult(name="CustomerObject")
    @Override 
	public CustomerObject find(int id, String encryptedPassword) {
		CustomerObject customer = findById(id);
		String pk = "";
		String[] keys;
		helper.KeysManagerProxy kmp = new helper.KeysManagerProxy();
		try {
			pk = kmp.getPrivatekey(String.valueOf(id));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		String painPwd = CustomerUtilities.getDecryptedString(pk, customer.getKey());
		String insPwd = CustomerUtilities.getDecryptedString(pk, encryptedPassword);
		if (insPwd.equals(painPwd)){
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
	}
    
    private CustomerObject findById(int id) {
    	CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer WHERE customer_id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				customer = new CustomerObject(id, resultSet.getString("salutation"),
						resultSet.getString("firstname"), resultSet.getString("lastname"),
						resultSet.getString("country"), resultSet.getString("province"),
						resultSet.getString("city"), resultSet.getString("street"),
						resultSet.getString("street"), resultSet.getString("zip"),
						resultSet.getString("email"), resultSet.getString("pwd"),resultSet.getString("key"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return customer;
    }
}