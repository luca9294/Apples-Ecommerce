package db;

import java.sql.*;

import Serializables.CustomerObject;

import javax.jws.WebResult;
import javax.jws.WebService;

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
    	
    	/*
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
		
		*/
    	return null;
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
    	/*
		CustomerObject customer = findById(id);
		return CustomerUtilities.updateCustomerKeys(customer, encryptedPassword);
		*/
    	
    	return null;
	}
    
    @WebResult(name="CustomerObject")
    @Override 
	public CustomerObject findByCookie(int id, String cookieId) {
    	/*
    	CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer LEFT JOIN cookie ON customer.customer_id = cookie.customer_id WHERE cookie.cookie_id = ?");
			preparedStatement.setString(1, cookieId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next() && (resultSet.getInt("customer_id") == id)) {
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
		
		*/
    	
    	return null;
   	}
    
    private CustomerObject findById(int id) {
    	
    	/*
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
		
		*/
    	
    	return null;
    }
}